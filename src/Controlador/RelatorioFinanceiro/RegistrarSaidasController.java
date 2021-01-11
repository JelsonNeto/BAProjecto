/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioFinanceiro;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Saida;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class RegistrarSaidasController implements Initializable {

    @FXML
    private JFXComboBox<String> cb_efeito;
    @FXML
    private JFXTextField txt_efeito;
    @FXML
    private JFXTextField txt_valor;
    @FXML
    private JFXDatePicker data;
    @FXML
    private AnchorPane anchor_pane;
    @FXML
    private Label lbl_erro_valor;
    @FXML
    private Label lbl_msg;
    
    private String efeito;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaEfeitos();
    }    

    @FXML
    private void Close(MouseEvent event) 
    {
        Stage stage = (Stage)anchor_pane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void Registrar(ActionEvent event) 
    {
        Preencher();
    }

    @FXML
    private void SelecionaEfeito(ActionEvent event) 
    {
        
        String efeito_var = cb_efeito.getSelectionModel().getSelectedItem();
        if( efeito_var.equals("Outro") )
        {
            txt_efeito.setDisable(false);
            efeito = txt_efeito.getText();
        }
        else
        {
            txt_efeito.setDisable(true);
            efeito = efeito_var;
        }
        
    }
    
    @FXML
    private void Verificar(KeyEvent event) 
    {
        
        if( Character.isLetter(event.getCharacter().charAt(0)))
        {
            event.consume();
            lbl_erro_valor.setVisible(true);
        }
        else
            lbl_erro_valor.setVisible(false);
        
    }
    
    
    
    
/******************************************************/
    private void InicializaEfeitos()
    {
        String valor[]= {"Agua","Energia Eletrica","Alimentação","Outro"};
        cb_efeito.setItems(FXCollections.observableArrayList(Arrays.asList(valor)));
        lbl_erro_valor.setVisible(false);
    }

    private void Preencher()
    {
        String valor = txt_valor.getText();
        String data_var = data.getValue().toString();
        int coduser = Usuario.NameToCode(Usuario.getUsuario_activo());
        
        Saida s = new Saida(coduser, efeito, valor, data_var,0);
        if(s.Adicionar())
        {
            lbl_msg.setVisible(true);
            Limpar();
        }
        
    }

    private void Limpar() 
    {
        
        txt_efeito.clear();
        txt_valor.clear();
        data.setValue(null);
        cb_efeito.getSelectionModel().clearSelection();
        
    }
    
}
