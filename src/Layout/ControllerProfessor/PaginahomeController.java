/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Layout.ControllerProfessor;

import Controlador.Usuario.loginControler;
import definicoes.DefinicoesPane;
import definicoes.Window_Features;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class PaginahomeController implements Initializable {
    

    private static AnchorPane pane;
    private Window_Features wc;
    private DefinicoesPane dp;
    @FXML
    private Pane panel_deinicoes_usuario;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        wc = new Window_Features();
        dp = new DefinicoesPane();
        dp.setPane(pane);
    }    

    public static void setPane(AnchorPane pane) {
        PaginahomeController.pane = pane;
    }
 
    @FXML
    private void show_perfil(MouseEvent event) {
        
        dp.setPath("/vista/PainelProfessor/perfilProfessor.fxml");
        dp.AddPane();
        
    }

    @FXML
    private void show_alterarSenha(MouseEvent event) {
        
        loginControler.setRedifinir_senha("/vista/redifinirSenha.fxml");
        wc.setFxml_path("/vista/login.fxml");
        wc.Call();
        wc.Close(pane);
        
    }

    @FXML
    private void show_terminarSessao(MouseEvent event) {
        
      wc.Close(pane);
      wc.setFxml_path("/vista/login.fxml");
      wc.Call();
    }

   
    
}
