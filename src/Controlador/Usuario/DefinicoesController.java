/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Usuario;

import Controlador.Usuario.RegistroController;
import Validacoes.Valida_UsuarioActivo;
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
public class DefinicoesController implements Initializable {
    @FXML
    private Pane principal;
    @FXML
    private Label menuUsuario;
    @FXML
    private Label menuBackup;
    @FXML
    private Label menuConfiguracao;
    @FXML
    private Label lbl_registro;
    
    private static String nomeUser;
    private static String tipoUser;
    private static Label l_usuario;
    private static Label l_backup;
    private static Label l_config;
    private static Label l_registro;
    
  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        l_backup= menuBackup;
        l_config = menuConfiguracao;
        l_registro = lbl_registro;
        l_usuario = menuUsuario;
        Valida_Usuario();
    }    
    
    
    
    @FXML
    public void showUsuario( MouseEvent event )
    {
        AddPane("/vista/usuario.fxml");
    }
          
    @FXML
    public void showEspecificacoes( MouseEvent event )
    {
        AddPane("/vista/especificacoesSistema.fxml");
    }
   
    @FXML
    public void showsobreAutor( MouseEvent event )
    {
        AddPane("/vista/sobreAutor.fxml");
    }
    
    @FXML
    private void showregistro( MouseEvent event )
    {
         RegistroController.setNomeUser(nomeUser);
         RegistroController.setTipoUser(nomeUser);
         AddPane("/vista/registro.fxml");
    }
    
    
    @FXML
    private void showbackup(MouseEvent event)
    {
        AddPane("/vista/backup.fxml");
    }
    
    @FXML
    private void showconfig(MouseEvent event) 
    {
        AddPane("/vista/configuracao.fxml");
    }

     
    
     private void AddPane( String caminho )
    {
        
            principal.getChildren().removeAll();
        
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource(caminho));
            principal.getChildren().add(fxml);
        } catch (IOException ex) {
            Logger.getLogger(DefinicoesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
    }

    public static void setNomeUser(String nomeUser) {
        DefinicoesController.nomeUser = nomeUser;
    }

    public static void setTipoUser(String tipoUser) {
        DefinicoesController.tipoUser = tipoUser;
    }

    public static Label getL_backup() {
        return l_backup;
    }

    public static Label getL_config() {
        return l_config;
    }

    public static Label getL_registro() {
        return l_registro;
    }

    public static Label getL_usuario() {
        return l_usuario;
    }
    
    private void Valida_Usuario()
    {
        if( Usuario.tipo_Usuario_Activo().equalsIgnoreCase("Admin") || Usuario.tipo_Usuario_Activo().equalsIgnoreCase("Director Geral") || Usuario.tipo_Usuario_Activo().equalsIgnoreCase("Director Pedagógico") )
            Valida_UsuarioActivo.Definicoes(false);
        else
            Valida_UsuarioActivo.Definicoes(true);
        
    }
   
}
