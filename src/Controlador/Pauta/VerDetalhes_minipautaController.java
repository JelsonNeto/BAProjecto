/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pauta;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
public class VerDetalhes_minipautaController implements Initializable {
    @FXML
    private TableView<Minipauta_Trimestral> tabela;
    @FXML
    private TableColumn<Minipauta_Trimestral, String> coluna_nome;
    @FXML
    private TableColumn<Minipauta_Trimestral, String> coluna_mac;
    @FXML
    private TableColumn<Minipauta_Trimestral, String> coluna_cp;
    @FXML
    private TableColumn<Minipauta_Trimestral, String> coluna_ct;
    @FXML
    private TableColumn<Minipauta_Trimestral, String> coluna_disciplina;
    
    private static Minipauta_Trimestral mt;
    @FXML
    private JFXComboBox<String> cb_pesquisar;
    @FXML
    private JFXTextField txt_pesquisar;
    @FXML
    private Pane panePreviuos;
    @FXML
    private Label lbl_total;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CarregaTabela();
        OpcoesPesquisa();
    }    
    
       @FXML
    private void Pesquisar(MouseEvent event) {
    }

    
 //
 //

    private void CarregaTabela()
    {
        SetColunas();
        tabela.setItems(Minipauta_Trimestral.Lista_Minipauta_Trimestrals(mt.getCoddisciplina(), mt.getTrimestre(), mt.getAnolectivo()));
        lbl_total.setText(""+tabela.getItems().size());
    }
    
    private void SetColunas()
    {
        coluna_nome.setCellValueFactory( new PropertyValueFactory<>("nome"));
        coluna_cp.setCellValueFactory( new PropertyValueFactory<>("cp"));
        coluna_ct.setCellValueFactory( new PropertyValueFactory<>("ct"));
        coluna_mac.setCellValueFactory( new PropertyValueFactory<>("mac"));
        coluna_disciplina.setCellValueFactory( new PropertyValueFactory<>("disciplina"));
    }
    
    public static void setMt(Minipauta_Trimestral mt) {
        VerDetalhes_minipautaController.mt = mt;
    }

    private void OpcoesPesquisa()
    {
        String opcoes[] ={"Ano lectivo" , "Trimestre", "Disciplina", "Estudante"};
        cb_pesquisar.setItems( FXCollections.observableArrayList( Arrays.asList(opcoes)));
    }
 
    
}
