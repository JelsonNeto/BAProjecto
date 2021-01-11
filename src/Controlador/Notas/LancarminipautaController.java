/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Notas;

import Validacoes.ValidarNotas;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import definicoes.DefinicoesData;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import modelo.Classe;
import modelo.Curso;
import modelo.Disciplina;
import modelo.Estudante;
import modelo.MesAno;
import modelo.Minipauta_Trimestral;
import modelo.Pauta_modelo;
import modelo.Turma;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class LancarminipautaController implements Initializable {
    @FXML
    private TableView<Pauta_modelo> tabela;
    @FXML
    private ComboBox<String> cb_curso;
    @FXML
    private ComboBox<String> cb_classe;
    @FXML
    private ComboBox<String> cb_turma;
    @FXML
    private ComboBox<String> cb_disciplina;
    @FXML
    private Label lbl_nomeProfessor;
    @FXML
    private Label lbl_anolectivo;
    @FXML
    private TableColumn<Pauta_modelo, Integer> coluna_numeracao;
    @FXML
    private TableColumn<Pauta_modelo, String> coluna_nome;
    @FXML
    private TableColumn<Pauta_modelo, TextField> coluna_mac;
    @FXML
    private TableColumn<Pauta_modelo, TextField> coluna_cp;
    @FXML
    private TableColumn<Pauta_modelo, TextField> coluna_ct;
    @FXML
    private JFXComboBox<String> cb_trimestre;
    @FXML
    private Label lbl_erro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCurso();
        lbl_erro.setVisible(false);
    }    

    @FXML
    private void selecionacurso_inicializaClasse(ActionEvent event) {
        
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        if( curso != null)
            cb_classe.setItems(Classe.ClassesPorCurso(curso));
    }

    @FXML
    private void selecionaClasse_inicializaturma(ActionEvent event) {
        
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        if( curso!= null && classe!= null )
            cb_turma.setItems(Turma.ListaTurmasRelaClasse_CodCurso(classe, Curso.NameToCode(curso)));
    }
    
    @FXML
    private void SelecionaTurma_incializaTrimestre(ActionEvent event) 
    {
         String turma = cb_turma.getSelectionModel().getSelectedItem();
         if( turma != null )
         {
             InicializaTrimestre();
         }
    }

    @FXML
    private void SelecionaTrimestre_inicializaDisciplina(ActionEvent event) 
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        String trimestre = cb_trimestre.getSelectionModel().getSelectedItem();
        if( trimestre != null )
        {
            cb_disciplina.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
            Reinicializa_ComboDisc();
        }
    }


      @FXML
    private void selecionaDisciplina_inicializaTabela(ActionEvent event) {
        
        String disciplina = cb_disciplina.getSelectionModel().getSelectedItem();
        int codturma = Turma.NameToCode(cb_turma.getSelectionModel().getSelectedItem());
        if( disciplina!=null )
        {
            CarregarTabela(Estudante.ListaGeralAlunosMatriculadosporTurma(codturma));
        }
    }
    
    @FXML
    private void LancarMinipauta(ActionEvent event) 
    {
        
        Adicionar();
        
    }

  
    
    
    
/******************************************************************************************/
 // Metodos Operacionais
/******************************************************************************************/
    
    private void InicializaCurso()
    {
        cb_curso.setItems(Curso.ListaCursos());
        lbl_anolectivo.setText(MesAno.Get_AnoActualCobranca());
    }

    private void CarregarTabela(ObservableList<String> ListaGeralAlunosMatriculadosporTurma) 
    {
        
        SetColunas();
        int contador = 1;
        ObservableList<Pauta_modelo> lista = FXCollections.observableArrayList();
        for (String nome_estudantes : ListaGeralAlunosMatriculadosporTurma) {
            
            Pauta_modelo m  = new Pauta_modelo();
            //criacao das textFieds
            JFXTextField mac = new JFXTextField();
            JFXTextField cp = new JFXTextField();
            JFXTextField ct = new JFXTextField();
            
            
            mac.setPromptText("Digite o Mac aqui.");
            cp.setPromptText("Digite o Cp aqui.");
            ct.setPromptText("Digite o Ct aqui.");
            
            m.setCodmatricula_c(matricula_confirmacao.CodAlunoToCodMatricula(Estudante.NameToCode(nome_estudantes)));
            m.setNumeracao(contador++);
            m.setNome(nome_estudantes);
            m.setMac(mac);
            m.setCp(cp);
            m.setCt(ct);
            lista.add(m);
        }
        
        tabela.setItems(lista);
    }
    
    private void SetColunas()
    {
        coluna_numeracao.setCellValueFactory( new PropertyValueFactory<>("numeracao"));
        coluna_nome.setCellValueFactory( new PropertyValueFactory<>("nome"));
        coluna_mac.setCellValueFactory( new PropertyValueFactory<>("mac"));
        coluna_cp.setCellValueFactory( new PropertyValueFactory<>("cp"));
        coluna_ct.setCellValueFactory( new PropertyValueFactory<>("ct"));
    }
    
    private void Adicionar()
    {
        boolean verifica = false;
        String trimestre = cb_trimestre.getSelectionModel().getSelectedItem();
        String anolectivo  = MesAno.Get_AnoActualCobranca();
        int codigo = Minipauta_Trimestral.UltimoCodigo();
        int coddisciplina = Disciplina.NameToCode(cb_disciplina.getSelectionModel().getSelectedItem(), cb_curso.getSelectionModel().getSelectedItem(), cb_classe.getSelectionModel().getSelectedItem());
       if( cb_classe.getSelectionModel().getSelectedItem() == null || cb_curso.getSelectionModel().getSelectedItem() == null || cb_disciplina.getSelectionModel().getSelectedItem() == null || cb_trimestre.getSelectionModel().getSelectedItem() == null || cb_turma.getSelectionModel().getSelectedItem() == null)
       {
           Alert a = new Alert(Alert.AlertType.WARNING,"Existem Campos Vazios");
           a.show();
       }
       else
       {
           for( Pauta_modelo p : tabela.getItems() )
            {
               
                if( !p.getCp().getText().equals("") && !p.getMac().getText().equals("") && !p.getCt().getText().equals(""))
                {
                    if( Verifica_Dados())
                    {
                        if( Verifica_Notas(cb_curso.getSelectionModel().getSelectedItem()) )
                        {
                              Minipauta_Trimestral mt = new Minipauta_Trimestral(codigo, p.getCodmatricula_c(), coddisciplina, trimestre, anolectivo, p.getMac().getText(), p.getCp().getText(), p.getCt().getText());
                              verifica = mt.Adicionar();
                              codigo = Minipauta_Trimestral.UltimoCodigo();
                             lbl_erro.setVisible(false);
                        }
                          
                        else
                        {
                            lbl_erro.setText("Notas invalidas, Primária [0.0 - 10] , outros[0.0 - 20] ).");
                            lbl_erro.setVisible(true);
                        }
                      
                        
                    }
                    else
                    {
                        lbl_erro.setText("As notas só podem ser valores numéricos.");
                        lbl_erro.setVisible(true);
                    }
                    
                }
                else{
                    lbl_erro.setText("Existem campos vazios");
                    lbl_erro.setVisible(true);
                }
                
            }

            if( verifica )
            {
                Alert a = new Alert(Alert.AlertType.INFORMATION,"Pauta Registrada com sucesso");
                a.show();
                Limpar();
            }
       }

    }
    
    
    private void Limpar()
    {
        cb_classe.getSelectionModel().clearSelection();
        cb_curso.getSelectionModel().clearSelection();
        cb_turma.getSelectionModel().clearSelection();
        cb_disciplina.getSelectionModel().clearSelection();
        tabela.setItems(null);
    }
    
    private void Reinicializa_ComboDisc()
    {
        int codturma = Turma.NameToCode(cb_turma.getSelectionModel().getSelectedItem());
        String trimestre = cb_trimestre.getSelectionModel().getSelectedItem();
        String anolectivo = MesAno.Get_AnoActualCobranca();
        cb_disciplina.getItems().removeAll(Minipauta_Trimestral.Disciplinas_Ja_Registradas(codturma, trimestre, anolectivo));
    }

  private void InicializaTrimestre()
    {
        String [] valor = {"Iº", "IIº","IIIº"};
        cb_trimestre.setItems(FXCollections.observableArrayList(Arrays.asList(valor)));
    }
    
  private boolean Verifica_Dados()
   {
        int quant = 0;
        for( Pauta_modelo p : tabela.getItems() )
        {
            if( ValidarNotas.ENumero(p.getCp().getText()) && ValidarNotas.ENumero(p.getCp().getText()) && ValidarNotas.ENumero(p.getCp().getText()))
            {
                quant++;
            }
            else
                return false;
        }
        
        return quant==tabela.getItems().size();
    }
  
  private boolean Verifica_Notas( String curso)
  {
      int quant  = 0;
      for( Pauta_modelo P : tabela.getItems() )
      {
          if( ValidarNotas.verifica_notas_por_curso_nivel(P.getCp().getText(), curso) && ValidarNotas.verifica_notas_por_curso_nivel(P.getMac().getText(), curso) && ValidarNotas.verifica_notas_por_curso_nivel(P.getCt().getText(), curso)  )
          {
              quant++;
          }
          else
              return false;
      }
      
      return quant == tabela.getItems().size();
  }
    
}
