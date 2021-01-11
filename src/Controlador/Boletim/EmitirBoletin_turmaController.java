/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Boletim;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import definicoes.DefinicoesPane;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Classe;
import modelo.Curso;
import modelo.Estudante;
import modelo.MesAno;
import modelo.Minipauta_Trimestral;
import modelo.Turma;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class EmitirBoletin_turmaController implements Initializable {
    @FXML
    private JFXComboBox<String> cb_curso;
    @FXML
    private JFXComboBox<String> cb_classe;
    @FXML
    private JFXComboBox<String> cb_turma;
    @FXML
    private JFXComboBox<String> cb_trimestre;
    @FXML
    private TableView<Minipauta_Trimestral> tabela;
    @FXML
    private TableColumn<Minipauta_Trimestral,String>coluna_curso;
    @FXML
    private TableColumn<Minipauta_Trimestral,String> coluna_classe;
    @FXML
    private TableColumn<Minipauta_Trimestral,String> coluna_turma;
    @FXML
    private TableColumn<Minipauta_Trimestral,String> coluna_trimestre;
    @FXML
    private TableColumn<Minipauta_Trimestral, String> coluna_estudante;
    @FXML
    private JFXButton btn_emitir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCurso();
    }    

    @FXML
    private void selecionacurso_inicializaClasse(ActionEvent event) 
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        if( curso != null)
            cb_classe.setItems(Classe.ClassesPorCurso(curso));
    }

    @FXML
    private void selecionaClasse_inicializaturma(ActionEvent event) 
    {
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
    private void SelecionaTrimestre_Tabela(ActionEvent event) 
    {
        String trimestre= cb_trimestre.getSelectionModel().getSelectedItem();
        if( trimestre != null )
        {
            CarregaTabela();
        }
    }
    
    @FXML
    private void EmitirBoletim(ActionEvent event) 
    {
        
        Minipauta_Trimestral mt = tabela.getSelectionModel().getSelectedItem();
        if( mt != null )
        {
             DefinicoesPane dp = new DefinicoesPane();
             Confirmar_boletimController.setMt(mt);
             dp.CallOtherWindow("confirmar_boletim", null);
        
        }
        else
        {
            Alert a = new Alert(Alert.AlertType.WARNING,"Sem Dados para mostrar");
            a.show();
        }
        
       
    }
   
    

    
/******************************************************************************************/
 // Metodos Operacionais
/******************************************************************************************/
    
    private void InicializaCurso()
    {
        cb_curso.setItems(Curso.ListaCursos());
    }

    private void InicializaTrimestre()
    {
        String [] valor = {"Iº", "IIº","IIIº"};
        cb_trimestre.setItems(FXCollections.observableArrayList(Arrays.asList(valor)));
    }
    
    private void CarregaTabela()
    {
        SetColunas();
        String trimestre = cb_trimestre.getSelectionModel().getSelectedItem();
        String anolectivo = MesAno.Get_AnoActualCobranca();
        int codturma = Turma.NameToCode(cb_turma.getSelectionModel().getSelectedItem());
        ObservableList<Minipauta_Trimestral> l_mt = FXCollections.observableArrayList();
         
        for( int codmatricula_c :  matricula_confirmacao.ListaDosAlunosMatriculadosAluno_por_Turma(codturma, anolectivo) )
        {
              Minipauta_Trimestral mt = new Minipauta_Trimestral();
              mt.setNome(Estudante.CodeToName(matricula_confirmacao.codmatricula_c_para_codaluno(codmatricula_c, anolectivo)));
              mt.setCurso(cb_curso.getSelectionModel().getSelectedItem());
              mt.setClasse(cb_classe.getSelectionModel().getSelectedItem());
              mt.setTrimestre(trimestre);
              mt.setTurma(cb_turma.getSelectionModel().getSelectedItem());
              mt.setAnolectivo(anolectivo);
              mt.setCodmatricula_c(codmatricula_c);
              l_mt.add(mt);
        }
        
        tabela.setItems(l_mt);
    }

    private void SetColunas()
    {
        coluna_estudante.setCellValueFactory( new PropertyValueFactory<>("nome"));
        coluna_curso.setCellValueFactory( new PropertyValueFactory<>("curso"));
        coluna_classe.setCellValueFactory( new PropertyValueFactory<>("classe"));
        coluna_trimestre.setCellValueFactory( new PropertyValueFactory<>("trimestre"));
        coluna_turma.setCellValueFactory( new PropertyValueFactory<>("turma"));
    }
    
   /* private boolean Adicionar_Dados_Emitir()
    {
            Minipauta_Trimestral m =tabela.getSelectionModel().getSelectedItem();
            if( m != null )
            {
                
            }
    }*/

    
}
