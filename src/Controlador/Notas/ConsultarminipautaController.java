/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Notas;

import Controlador.Pauta.VerDetalhes_minipautaController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import definicoes.DefinicoesPane;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Minipauta_Trimestral;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class ConsultarminipautaController implements Initializable {
    @FXML
    private TableView<Minipauta_Trimestral> tabela;
    @FXML
    private TableColumn<Minipauta_Trimestral, String> coluna_disciplina;
    @FXML
    private TableColumn<Minipauta_Trimestral, String> coluna_professor;
    @FXML
    private TableColumn<Minipauta_Trimestral, String> coluna_turma;
    @FXML
    private TableColumn<Minipauta_Trimestral, String> coluna_curso;
    @FXML
    private TableColumn<Minipauta_Trimestral, String> coluna_classe;

    
    private static Pane pane;
    @FXML
    private Label lbl_total;
    @FXML
    private JFXButton btn_ver;
    @FXML
    private JFXButton btn_editar;
    @FXML
    private JFXComboBox<String> cb_pesquisar;
    @FXML
    private JFXTextField txt_pesquisar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        OpcoesPesquisa();
        
    }    
    
    @FXML
    private void VerDetalhes(ActionEvent event) 
    {
        Minipauta_Trimestral t = tabela.getSelectionModel().getSelectedItem();
        if( t!= null )
        {
             DefinicoesPane dp = new DefinicoesPane();
             dp.setPane(pane);
             VerDetalhes_minipautaController.setMt(t);
             dp.setPath("/vista/VerDetalhes_minipauta.fxml");
             dp.AddPane();
        }
       
        
    }
    
    @FXML
    private void EditarMiniPauta(ActionEvent event) {
    }
    
    @FXML
    private void TabelaSelecionada(MouseEvent event) 
    {
        Minipauta_Trimestral mt = tabela.getSelectionModel().getSelectedItem();
        if( mt != null )
        {
            btn_editar.setDisable(false);
            btn_ver.setDisable(false);
        }
        else
        {
            btn_editar.setDisable(true);
            btn_ver.setDisable(true);
        }
    }


    @FXML
    private void Carregar(ActionEvent event) 
    {
        if( tabela.getItems().size() <= 0 )
             CarregaTabela();
    }
   
    
    @FXML
    private void Pesquisar(MouseEvent event) {
    }

 //
 //
    
    private void CarregaTabela()
    {
        SetColunas();
        tabela.setItems(Minipauta_Trimestral.MiniPautas_Ja_Cadastradas("Iº","2019"));
        lbl_total.setText(""+tabela.getItems().size()+" Registros");
    }
    
    private void SetColunas()
    {
        coluna_classe.setCellValueFactory( new PropertyValueFactory<>("classe"));
        coluna_curso.setCellValueFactory( new PropertyValueFactory<>("curso"));
        coluna_disciplina.setCellValueFactory( new PropertyValueFactory<>("disciplina"));
        coluna_professor.setCellValueFactory( new PropertyValueFactory<>("professor"));
        coluna_turma.setCellValueFactory( new PropertyValueFactory<>("turma"));
    }

    public static void setPane(Pane pane) {
        ConsultarminipautaController.pane = pane;
    }

    private void OpcoesPesquisa()
    {
        String opcoes[] ={"Ano lectivo" , "Trimestre", "Disciplina"};
        cb_pesquisar.setItems( FXCollections.observableArrayList( Arrays.asList(opcoes)));
    }
    

    
    
}
