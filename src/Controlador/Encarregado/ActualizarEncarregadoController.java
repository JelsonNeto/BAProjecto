/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Encarregado;

import Bd.OperacoesBase;
import Validacoes.ValidaEncarregado;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import modelo.Classe;
import modelo.Curso;
import modelo.Encarregado;
import modelo.Estudante;
import modelo.Turma;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class ActualizarEncarregadoController implements Initializable {
    
    @FXML private DatePicker datanasc;
    @FXML private TextField ocupacao;
    @FXML private TextField endereco;
    @FXML private TextField contactos;
    @FXML private RadioButton feme;
    @FXML private RadioButton masc;
    @FXML private ListView<String> lista_selecionados;
    @FXML private ListView<String> lista_selecionar;
    @FXML private ComboBox<String> cb_curso;
    @FXML private ComboBox<String> cb_classe;
    @FXML private ComboBox<String> cb_turma;
    @FXML private ToggleGroup toogle;
    @FXML private TextField txt_nomepai;
    @FXML private TextField txt_nomemae;
    
    
    private static Encarregado e;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Inicializa_Curso();
        InicializaListaAlunos();
        InicializaCampos();
        lista_selecionar.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
    private void SelecionaEstudante_AddListaSelecionado( MouseEvent event )
    {
    	ObservableList<String> nomes = lista_selecionar.getSelectionModel().getSelectedItems();
    	lista_selecionados.setItems(nomes);
    }
    @FXML
    private void actualizar(ActionEvent event) 
    {
        if( Preencher() )
        {
          if(e.Actualizar())
          {
              Alert a = new Alert(AlertType.INFORMATION, "Encarregado actualizado com sucesso.");
              a.show();
              AdicionaNaTabela_EncarregadoAluno();
              Limpar();
          }
        }
        
    }
    
    @FXML
    private void eliminarAlunos(ActionEvent event) 
    {
        if( lista_selecionados.getItems().size() > 1 )
        {
            ObservableList<String> lista = lista_selecionados.getSelectionModel().getSelectedItems();
            for( String valor : lista )
            {
                String sql = "delete from encarregado_aluno where codaluno = '"+Estudante.NameToCode(valor)+"'";
                OperacoesBase.Eliminar(sql);
            }
            lista_selecionados.getItems().removeAll(lista);
            
        }
        else
        {
            lista_selecionados.setDisable(true);
        }
    }

    
/*************************Metodos Auxiliares****************************/
    
    /**
     * 
     * @param e
     */
    public static void setE(Encarregado e) {
        ActualizarEncarregadoController.e = e;
    }
    
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
    
    private void InicializaCampos()
    {
        txt_nomepai.setText(e.getNomepai());
        txt_nomemae.setText(e.getNomemae());
        contactos.setText(e.getContacto());
        endereco.setText(e.getEndereco());
        datanasc.setValue(e.getDatanasc());
        ocupacao.setText(e.getOcupacao());
        if(  "Masculino".equalsIgnoreCase(e.getSexo()))
        {
            masc.setSelected(true);
            feme.setSelected(false);
        }
        else
        {
            masc.setSelected(false);
            feme.setSelected(true);
        }
        
    }
    
    private void InicializaListaAlunos()
    {
        lista_selecionados.setItems(Encarregado.ListaAlunosPorEncarregado(e.getCodigo()));
        lista_selecionar.setItems(Encarregado.ListaAlunosPorEncarregado(e.getCodigo()));
    }
    
    private void InicializaEstudantes( String turma )
    {
        lista_selecionar.setItems(Estudante.ListaAlunosGeralTurma(Turma.NameToCode(turma)));
    }
    
    private boolean Preencher()
    {
        Alert alert = new Alert(Alert.AlertType.NONE);
        if( ValidaEncarregado.ExistemCamposVazios( txt_nomepai , txt_nomemae , datanasc ,ocupacao , endereco , contactos , cb_classe ,cb_curso ,cb_turma, lista_selecionados , lista_selecionar ) )
        {
            
            //Exibe uma mensagem de erro caso estejem vaxios
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Existem campos vazios");
            alert.show();
            
            return false; //retorna falso
        }
        else
        {
            Encarregado e1 = new Encarregado();
            e1.setCodigo(e.getCodigo());
            e1.setNomepai(txt_nomepai.getText());
            e1.setNomemae(txt_nomemae.getText());
            e1.setContacto(contactos.getText());
            e1.setDatanasc(datanasc.getValue());
            e1.setEndereco(endereco.getText());
            e1.setOcupacao(ocupacao.getText());
            e1.setSexo(retornaSexo());
            e = e1;
            return true;
            
            
        }
    }
        
     private String retornaSexo()
     {
        String sexo = "Femenino";
        if( masc.isSelected() )
            sexo = "Masculino";
        
        return sexo;
      }
    
     private void AdicionaNaTabela_EncarregadoAluno()
    {
        for( String valor : lista_selecionados.getItems() )
        {
            String sql = "update encarregado_aluno set codaluno = '"+Estudante.NameToCode(valor)+"' where codencarregado = '"+e.getCodigo()+"'";
            OperacoesBase.Inserir(sql);
        }
    }
     
    private void Limpar()
    {
        txt_nomepai.setText("");
        txt_nomemae.setText("");
        datanasc.setValue(null);
        contactos.setText("");
        endereco.setText("");
        ocupacao.setText("");
        cb_classe.getSelectionModel().select(null);
        cb_curso.getSelectionModel().select(null);
        cb_turma.getSelectionModel().select(null);
        lista_selecionados.setItems(null);
        lista_selecionar.setItems(null);
        feme.setSelected(false);
        masc.setSelected(true);
    }

    
}
