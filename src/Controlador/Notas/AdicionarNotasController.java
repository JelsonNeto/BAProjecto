/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Notas;

import Controlador.Estudante.AdicionarEstudanteController;
import Validacoes.ValidarNotas;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Classe;
import modelo.Curso;
import modelo.Disciplina;
import modelo.Encarregado;
import modelo.Estudante;
import modelo.MesAno;
import modelo.Nota;
import modelo.Turma;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class AdicionarNotasController implements Initializable{
   
    @FXML private ComboBox<String> cb_nome;
    @FXML private TextArea txtarea_notas;
    @FXML private TextField txt_dataNascimento;
    @FXML private TextField txt_cedula;
    @FXML private TextField txt_encarregado;
    @FXML private ComboBox<String> cb_curso;
    @FXML private ComboBox<String> cb_classe;
    @FXML private ComboBox<String> cb_turma;
    @FXML private ListView<String> lista_disciplinas;
    @FXML private ComboBox<String> cb_trimestre;
    @FXML private ComboBox<String> cb_tipo;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        Inicializa_Curso();
        InicializaTrimestre();
    }    

    @FXML
    private void Adicionar(ActionEvent event)
    {
        Preencher();
    }
    
    @FXML
    private void SelecionaCurso_InicializaClasse( ActionEvent event )
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        if( curso != null )
        {
            Inicializa_Classes(curso);
        }
    }
    @FXML
    private void SelecionaClasse_InicializaTurma( ActionEvent event )
    {
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        if( classe != null )
        {
            Inicializa_Turma(classe, curso);
        }
    }
    
    @FXML
    private void SelecionaTurma_InicializaAlunos( ActionEvent event )
    {
        String turma = cb_turma.getSelectionModel().getSelectedItem();
        if( turma != null )
        {
            InicializaEstudantes(turma);
        }
    }
    

    @FXML
    private void SelecionaEstudante_InicializaBi(ActionEvent event)
    {
        String nome = cb_nome.getSelectionModel().getSelectedItem();
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        if( nome != null )
        {
            InicializaDataNasc_Bi_Disciplinas(nome , curso , classe);
            Inicializa_Encarregado(nome);
            InicializaTipo();
        }
         else
        {
            txt_cedula.setText("");
            txt_dataNascimento.setText("");
        }
    }
    
/***********************************Metodos Auxiliares******************************************************************/
    private void Inicializa_Curso()
    {
        cb_curso.setItems(Curso.ListaCursos());
    }
    
    private void Inicializa_Classes( String curso )
    {
        cb_classe.setItems(Classe.ClassesPorCurso(curso));
    }
    
    private void Inicializa_Turma( String classe , String curso )
    {
        cb_turma.setItems(Turma.ListaTurmasRelaClasse_CodCurso(classe, Curso.NameToCode(curso)));
    }
    
    private void InicializaEstudantes( String turma )
    {
        cb_nome.setItems(Estudante.ListaGeralAlunosMatriculadosporTurma(Turma.NameToCode(turma)));
    }
   
    private void InicializaDataNasc_Bi_Disciplinas( String nome_aluno , String curso , String classe )
    {
        txt_dataNascimento.setText(Estudante.codeToDataNasc(Estudante.NameToCode(nome_aluno)));
        txt_cedula.setText(Estudante.NameToBi(nome_aluno));
        lista_disciplinas.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso,classe));
    }
    
    private void Inicializa_Encarregado( String nome )
    {
        int codaluno = Estudante.NameToCode(nome);
        txt_encarregado.setText(Encarregado.NomeAlunosToEncarregado(codaluno));
    }
   
    private void InicializaTrimestre()
    {
        String [] valor = {"Iº", "IIº","IIIº"};
        cb_trimestre.setItems(FXCollections.observableArrayList(Arrays.asList(valor)));
    }
    
    private void InicializaTipo()
    {
        String valor[] = {"Avaliação","Prova do Professor", "Prova da escola"};
        cb_tipo.setItems(FXCollections.observableArrayList(Arrays.asList(valor)));
    }
    
    
    private void Preencher()
    {
        int tamanho_lista_disc = lista_disciplinas.getItems().size();
        String[] array_notas = txtarea_notas.getText().split(",");
        Alert a;
        
        if( !ValidarNotas.EstaoVazios( txt_cedula , txt_dataNascimento , cb_tipo, txt_encarregado, txtarea_notas, cb_classe , cb_curso, cb_nome ,cb_trimestre, cb_turma , lista_disciplinas, txtarea_notas ) )
        {
            String curso = cb_curso.getSelectionModel().getSelectedItem();
            boolean valor_verifica_primaria = VerificaNotas(array_notas); 
            boolean valor_nprimaria = VerificaNotas_Nprimaria(array_notas);
        
            
             Nota n = new Nota();
             n.setCodmatricula(matricula_confirmacao.CodAlunoToCodMatricula(Estudante.NameToCode(cb_nome.getSelectionModel().getSelectedItem())));
             n.setTrimestre(cb_trimestre.getSelectionModel().getSelectedItem());
             n.setDescricao(cb_tipo.getSelectionModel().getSelectedItem());
             n.setAno_lectivo(MesAno.Get_AnoActualCobranca());
             ObservableList<String> lista_dis = lista_disciplinas.getItems();
             boolean valor = false;
             int indice = 0;
        
            if( tamanho_lista_disc == array_notas.length )
            {
              if( "Primária".equalsIgnoreCase(curso))
              {
                 if( valor_verifica_primaria )
                  {
                 
                        for( String disciplinas : lista_dis )
                        {
                            n.setCodigo(Nota.RetornaUltimoId());
                            n.setCoddisciplina(Disciplina.NameToCode(disciplinas));
                            n.setValor(disciplinas);
                            n.setValor(array_notas[indice++]);
                            valor = n.Adicionar();
                        }

                        if( valor )
                        {
                            a = new Alert(AlertType.INFORMATION,"Notas cadastradas com sucesso");
                            a.show();
                            Limpar();
                        }
                        else
                        {
                           a = new Alert(AlertType.ERROR,"Erro ao cadastrar as notas");
                           a.show();
                        }
                }
                else
                {
                    a = new Alert(AlertType.ERROR,"As notas para a Primária devem estar entre 0 e 10");
                    a.show();
                }
            }
              else
              {
                
                  if( valor_nprimaria )
                  {
                       for( String disciplinas : lista_dis )
                        {
                            n.setCodigo(Nota.RetornaUltimoId());
                            n.setCoddisciplina(Disciplina.NameToCode(disciplinas));
                            n.setValor(disciplinas);
                            n.setValor(array_notas[indice++]);
                            valor = n.Adicionar();
                        }

                        if( valor )
                        {
                            a = new Alert(AlertType.INFORMATION,"Notas cadastradas com sucesso");
                            a.show();
                            Limpar();
                        }
                        else
                        {
                           a = new Alert(AlertType.ERROR,"Erro ao cadastrar as notas");
                           a.show();
                        }
                  }
                  else
                  {
                      a = new Alert(AlertType.ERROR,"As notas não estão correctas.");
                      a.show();
                  }
              }
          }
            else
            {
                a = new Alert(AlertType.ERROR,"A quantidade de notas não corresponde com a quantidade de disciplinas.");
                a.show();
            }
        }
        else
        {
                a = new Alert(AlertType.ERROR,"Existem campos vazios");
                a.show();
        }
    }
    
    private void Limpar()
    {
        txt_cedula.clear();;
        txt_dataNascimento.clear();
        cb_tipo.getSelectionModel().select(null);
        txt_encarregado.clear();
        txtarea_notas.clear();
        cb_classe.getSelectionModel().select(null);
        cb_curso.getSelectionModel().select(null);
        cb_nome.getSelectionModel().select(null);
        cb_trimestre.getSelectionModel().select(null);
        cb_turma.getSelectionModel().select(null);
        lista_disciplinas.setItems(null);
    }
    
    private void AddDialogo( String path )
    {
        FXMLLoader parent = new FXMLLoader(getClass().getResource(path));
        try {
            Parent root = (Parent)parent.load();
            Stage stage = new Stage();
            stage.setScene( new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AdicionarEstudanteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean VerificaNotas( String[] notas)
    {
        boolean controla= false;
        for( String nota : notas )
        {
            if( Character.isLetter(nota.charAt(0)))
                return false;
            double valor = Double.parseDouble(nota);
            controla = valor >=0 && valor <=10;
        }
        return controla;
    }

    private boolean VerificaNotas_Nprimaria(String[] array_notas)
    {
        boolean verifica = false;
        for( String nota : array_notas )
        {
            if( Character.isLetter(nota.charAt(0)))
                return false;
            double valor = Double.parseDouble(nota);
            verifica = valor>=0 && valor <=20;
        }
        return verifica;
    }
}

