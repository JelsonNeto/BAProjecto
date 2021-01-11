/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Usuario;

import Controlador.Usuario.VisualizarUsuario2Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class UsuarioController implements Initializable {
    @FXML
    private Pane principal;

    private static String nomeUser;
    @FXML
    private Label lbl_adicionar;
    @FXML
    private Label lbl_privilegios;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Vefica_Privilegios();
    }    
    
    @FXML
    public void showAddUser( MouseEvent event )
    {
        AddPane("/vista/AddUsuario.fxml");
    }
    
    @FXML
    public void showViewUser( MouseEvent event )
    {
        //VisualizarUsuario2Controller.setNomeUser(nomeUser);
        AddPane("/vista/visualizarUsuario2.fxml");
    }
    
     @FXML
    private void showPrivilegios(MouseEvent event) 
    {
        AddPane("/vista/addprivilegios.fxml");
    }
    
     public void showActiveUser( MouseEvent event )
    {
        AddPane("/vista/usuarioactivo.fxml");
    }
    
   
    public void showBasedados( MouseEvent event )
    {
        AddPane("/vista/basedados.fxml");
    }
    
     private void AddPane( String caminho )
    {
        
        principal.getChildren().removeAll();
        
        Parent fxml = null;
        try {
            fxml = FXMLLoader.load(getClass().getResource(caminho));
        } catch (IOException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        principal.getChildren().add(fxml);
        
    }

    public static void setNomeUser(String nomeUser) {
        UsuarioController.nomeUser = nomeUser;
    }

   private void Vefica_Privilegios()
   {
       
       
       if( Usuario.tipo_Usuario_Activo().equalsIgnoreCase("Admin") || Usuario.tipo_Usuario_Activo().equalsIgnoreCase("Director Geral") )
       {
           lbl_privilegios.setDisable(false);
       }
       else
       {
           lbl_privilegios.setDisable(true);
           if( Usuario.Retorna_Privilegio_Insercao() < 2 )
                lbl_adicionar.setDisable(true);
           else
                lbl_adicionar.setDisable(false);
       }
               
   }
     
     
    
}
