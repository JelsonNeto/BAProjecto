/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Estudante;

import definicoes.DefinicoesAdicionarDialogo;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import modelo.Classe;
import modelo.Curso;
import modelo.Estudante;
import modelo.MesAno;
import modelo.Turma;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class ActualizacaoAnualEstudanteController implements Initializable {
    @FXML
    private ComboBox<String> cb_curso;
    @FXML
    private ComboBox<String> cb_turma;
    @FXML
    private ComboBox<String> cb_classe;
    @FXML
    private ListView<String> lista_estudantes;
    @FXML
    private ComboBox<String> cb_cursoNovo;
    @FXML
    private ComboBox<String> cb_classeNovo;
    @FXML
    private ComboBox<String> cb_turmaNovo;
    @FXML
    private ListView<String> lista_estudantesNovo;

    private ObservableList<String> lista;
    private HashSet<String> colecao_set;
    @FXML
    private ImageView imagem1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        lista = FXCollections.observableArrayList();
        colecao_set = new HashSet();
        InicializaCursos();
    }    

    @FXML
    private void SelecionaOCurso_InicializaClasse(ActionEvent event) 
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        if( curso != null )
            cb_classe.setItems(Classe.ClassesPorCurso(curso));
    }

    @FXML
    private void SelecionaTurmaInicializarAluno(ActionEvent event)
    {
        String turma = cb_turma.getSelectionModel().getSelectedItem();
        if( turma != null )
        {
            lista_estudantes.setItems(Estudante.ListaGeralAlunosMatriculadosporTurma(Turma.NameToCode(turma)));
        }
    }

    @FXML
    private void SelecionaClasse_InicializaTurma(ActionEvent event)
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        
        if( curso != null && classe != null )
            cb_turma.setItems(Turma.ListaTurmasRelaClasse_CodCurso(classe, Curso.NameToCode(curso)));
    }
    
     @FXML
    private void Seleciona_AlunoView(MouseEvent event) 
    {
         String valor = lista_estudantes.getSelectionModel().getSelectedItem();
         if( valor != null )
         {
             colecao_set.add(valor);
             PercorreSet( colecao_set);
             lista_estudantesNovo.setItems(lista);
         }
         
        
    }
    
    @FXML
    private void EliminarSelecionado(MouseEvent event) 
    {
        String valor = lista_estudantesNovo.getSelectionModel().getSelectedItem();
        lista_estudantesNovo.getItems().remove(valor);
        lista.remove(valor);
        colecao_set.remove(valor);
    }

    @FXML
    private void SelecionaCurso_InicializaClasse2( ActionEvent event )
    {
         String curso = cb_cursoNovo.getSelectionModel().getSelectedItem();
        if( curso != null )
            cb_classeNovo.setItems(Classe.ClassesPorCurso(curso));
    }
     @FXML
    private void SelecionaClasse_InicializaTurma2( ActionEvent event )
    {
        String curso = cb_cursoNovo.getSelectionModel().getSelectedItem();
        String classe = cb_classeNovo.getSelectionModel().getSelectedItem();
        
        if( curso != null && classe != null )
            cb_turmaNovo.setItems(Turma.ListaTurmasRelaClasse_CodCurso(classe, Curso.NameToCode(curso)));
    }
     @FXML
    private void SelecionaTurma_InicializaCondicao( ActionEvent event )
    {
      
    }
    
    @FXML
    private void Actualzar( ActionEvent event )
    {
        
        Actualizar();
        
    }

     @FXML
    private void AbirInfo(MouseEvent event) {
        
         DefinicoesAdicionarDialogo d = new DefinicoesAdicionarDialogo();
         d.AddDialogo("/dialogos/adicionarEstudanteAjuda.fxml");
        
    }
/*********************METODOS OPERACIONAIS**********************************************************/
    private void InicializaCursos()
    {
        cb_curso.setItems(Curso.ListaCursos());
        cb_cursoNovo.setItems(Curso.ListaCursos());
    }
   
    private void PercorreSet(HashSet<String> colecao_set) 
    {
        for( String valor1 : colecao_set )
        {
            int conta = 0;
            for( String valor2 : lista )
            {
                  if( valor1.equalsIgnoreCase(valor2))
                      ++conta;
            }
            if( conta == 0  )
            {
                lista.add(valor1);
            }
        }
        
    }

    private void Actualizar() 
    {
        boolean  valor_var = false;
        Alert a;
       for( String valor : lista_estudantesNovo.getItems() )
        {
            matricula_confirmacao m  = new matricula_confirmacao();
            m.setCodmatricula(m.RetornaUltimoCodigo());
            m.setCodaluno(Estudante.NameToCode(valor));
            m.setCodturma(Turma.NameToCode(cb_turmaNovo.getSelectionModel().getSelectedItem()));
            
            //conversao
            int ano = Integer.parseInt(MesAno.Get_AnoActualCobranca())+1;
            m.setAnolectivo(""+ano);
            valor_var = m.adicionar();
        }
      
        if( valor_var )
        {
            a = new Alert(Alert.AlertType.INFORMATION, "Dados actualizados com sucesso.");
            a.show();
            Limpar();
        }
        else
        {
             a = new Alert(Alert.AlertType.ERROR, "Erro em actualizar os dados.");
            a.show();   
        }
        
    }

   //Em desuso por enquanto......
  /*  private boolean Aprovado( int codaluno )
    {
       
       int verificaNegativa = 0;
       String ano = MesAno.Get_AnoActualCobranca();
       ObservableList<String> disciplinas = Disciplina.ListaDisciplinasCurso_Classe(cb_curso.getSelectionModel().getSelectedItem(), cb_classe.getSelectionModel().getSelectedItem());
       for( String disciplina  : disciplinas )
       {
           
           double cap = Double.parseDouble(calculoPautaGeral.RetornaCap(disciplina, ano, codaluno));
           double ce = Double.parseDouble(calculoPautaGeral.RetornaGeralCe(cap , disciplina, ano, codaluno));
           double cf = Double.parseDouble(calculoPautaGeral.RetornaCfGeral(String.valueOf(cap), String.valueOf(ce)));
           
           if( !EPrimaria())
               if( cf < 10 )
                   ++verificaNegativa;
           else
                   if( cf <4.5 )
                       ++verificaNegativa;
       }
       
         return verificaNegativa < 3;
    }
    
    private boolean EPrimaria()
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        return curso.equals("Primária");
    }

    private void DesabilitaCurso_Classe()
    {
        cb_cursoNovo.setDisable(true);
        cb_classeNovo.setDisable(true);
    }
    
    */

    private void Limpar() 
    {
        cb_classe.getSelectionModel().clearSelection();
        cb_classeNovo.getSelectionModel().clearSelection();
        cb_curso.getSelectionModel().clearSelection();
        cb_cursoNovo.getSelectionModel().clearSelection();
        
        lista_estudantes.setItems(null);
        lista_estudantesNovo.setItems(null);
    }

   
   
}
