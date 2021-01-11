/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Encarregado;

import Bd.OperacoesBase;
import Validacoes.ValidaEncarregado;
import java.io.IOException;

import java.net.URL;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
public class AddEncarregadoController implements Initializable {
  
    @FXML  private DatePicker datanasc;
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
    @FXML private TextField txt_nome_mae;
    @FXML private TextField txt_nomepai;
    
    private Encarregado e;
    private static ObservableList<String> lista_nomes;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    	Inicializa_Curso();
    	lista_selecionar.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lista_nomes = FXCollections.observableArrayList();
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
    private void Adicionar(ActionEvent event)
    {
       Alert a;   
        if( Preencher() )
        {
            if( !Validacoes.ValidaEncarregado.VerificaExistencia( e.getCodigo() ))
            {
                if( e.Adicionar() )
                {
                    AddDialogo("/dialogos/encarregadoYes.fxml");
                    AdicionaNaTabela_EncarregadoAluno();
                    Limpar();
                    System.out.println("teste");

                }
                else
                {
                     a = new Alert(AlertType.ERROR , "Ocorreu um erro ao cadastrar o encarregado");
                    a.show();
                }
                
            }
            else
            {
                 a = new Alert(AlertType.INFORMATION , "Este encarregado ja existe.");
                 a.show();
            }
        } 
    }
    
/***************************Metodos Auxiliares****************************************************************/
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
        lista_selecionar.setItems(Estudante.ListaGeralAlunosMatriculadosporTurma(Turma.NameToCode(turma)));
    }
    
    private boolean Preencher()
    {
        Alert alert = new Alert(AlertType.NONE);
        if( ValidaEncarregado.ExistemCamposVazios( txt_nomepai , txt_nome_mae , datanasc ,ocupacao , endereco , contactos , cb_classe ,cb_curso ,cb_turma, lista_selecionados , lista_selecionar ) )
        {
            
            //Exibe uma mensagem de erro caso estejem vaxios
            alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Existem campos vazios");
            alert.show();
            
            return false; //retorna falso
        }
        else
        {
            e = new Encarregado();
            e.setCodigo(Encarregado.retornaUltimoCodigo());
            e.setNomepai(txt_nomepai.getText());
            e.setNomemae(txt_nome_mae.getText());
            e.setContacto(contactos.getText());
            e.setDatanasc(datanasc.getValue());
            e.setEndereco(endereco.getText());
            e.setOcupacao(ocupacao.getText());
            e.setSexo(retornaSexo());
            System.out.println(e.getNomemae());
            return true;
        }
    }
    
    private void AdicionaNaTabela_EncarregadoAluno()
    {
        for( String valor : lista_selecionados.getItems() )
        {
            String sql = "insert into encarregado_aluno values('"+e.getCodigo()+"' , '"+Estudante.NameToCode(valor)+"')";
            OperacoesBase.Inserir(sql);
        }
    }
    
    private void Limpar()
    {
        txt_nomepai.setText("");
        txt_nome_mae.setText("");
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
    
    private String retornaSexo()
    {
        String sexo = "Femenino";
        if( masc.isSelected() )
            sexo = "Masculino";
        
        return sexo;
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
            Logger.getLogger(AddEncarregadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
