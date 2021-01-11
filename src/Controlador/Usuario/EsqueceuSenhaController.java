/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Usuario;

import Bd.OperacoesBase;
import Validacoes.validarUsuario;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class EsqueceuSenhaController implements Initializable {
    @FXML
    private ComboBox<String> tipo_user;

    private static Pane pane;
    @FXML private TextField txt_nomeUsuario;
    @FXML private PasswordField txt_senhaActual;
    @FXML private PasswordField txt_novaSenha;
    @FXML private PasswordField txt_senhaAdmin;
    @FXML private PasswordField txt_csenha;
    @FXML
    private ComboBox<String> cb_nome;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
        tipo_user.setItems( FXCollections.observableArrayList(Arrays.asList("Admin" , "Tesoureiro")));
        InicializaNomes();
    }    

    @FXML
    public void back(MouseEvent event) {
        
        try {
            Parent fx = FXMLLoader.load(getClass().getResource("/vista/homeLogin.fxml"));
            pane.getChildren().setAll(fx);
        } catch (IOException ex) {
            Logger.getLogger(EsqueceuSenhaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    public void Redifinir( ActionEvent event )
    {
        String senhaNova = txt_novaSenha.getText();
        String csenha = txt_csenha.getText();
        String nome = cb_nome.getSelectionModel().getSelectedItem();
        String tipo = tipo_user.getSelectionModel().getSelectedItem();
        String senhaAdmin = txt_senhaAdmin.getText();
        String username = txt_nomeUsuario.getText();
        String senhaActual = txt_senhaActual.getText();
        
        if( !validarUsuario.EstaoVaziosRedifinirSenha( nome , tipo , senhaNova, csenha , senhaAdmin, username , senhaActual ) )
        {
            
             if( Usuario.verificarExistencia(nome, username, tipo, senhaActual) )
             {
                 if( Usuario.VerificaSenha_Admin(senhaAdmin))
                 {
                     if( senhaNova.equalsIgnoreCase(csenha))
                     {
                         Actualizar();
                         Limpar();
                         
                     }
                     else
                     {
                         Alert a = new Alert(AlertType.ERROR,"As senhas não correspondem");
                         a.show();
                     }
                 }
                 else
                 {
                      Alert a = new Alert(AlertType.ERROR,"Esta senha de Admin não existe.");
                      a.show();
                 }
             }
             else
             {
                  Alert a = new Alert(AlertType.ERROR,"Usuario Inexistente.");
                  a.show();
             }
            
        }
        else
        {
             Alert a = new Alert(AlertType.ERROR,"Existem campos vazios ");
              a.show();
        }
        
       
    }
    
 /******************************METODOS OPERACIONAIS***********************************/
    private void Actualizar()
    {
        String novaSenha = txt_novaSenha.getText();
        String nome = cb_nome.getSelectionModel().getSelectedItem();
        boolean valor =  OperacoesBase.Actualizar("update usuario set senha = '"+novaSenha+"' where coduser = '"+Usuario.NameToCode(nome)+"'");
       
        if( valor )
        {
            Alert a = new Alert(AlertType.INFORMATION,"Senha Redifinida com sucesso");
            a.show();
        }
        else
        {
             Alert a = new Alert(AlertType.ERROR,"Ocorreu algum erro");
            a.show();
        }
    }
    
    private void InicializaNomes()
    {
        cb_nome.setItems(Usuario.listaNomesUsers());
    }
   
    private void Limpar()
    {
        txt_csenha.clear();
        txt_senhaActual.clear();
        txt_senhaAdmin.clear();
        cb_nome.getSelectionModel().select(null);
        txt_nomeUsuario.clear();
        txt_novaSenha.clear();
        tipo_user.getSelectionModel().select(null);
    }
    
    public static void setPane(Pane pane) {
        EsqueceuSenhaController.pane = pane;
    }
}
