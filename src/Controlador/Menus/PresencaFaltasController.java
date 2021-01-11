/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Menus;

import definicoes.DefinicoesPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class PresencaFaltasController implements Initializable {

    
  
    @FXML
    private Pane Principal;
    
    private static Pane pane;
    private DefinicoesPane dp;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dp = new DefinicoesPane(Principal);
    }    

    @FXML
    private void back(MouseEvent event) {
        
        dp.setPane(pane);
        dp.setPath("/vista/paginaHome.fxml");
        dp.AddPane();
        
    }

    
      @FXML
    private void Professor(MouseEvent event) {
        
          dp.setPath("/vista/home_falta_funcionario.fxml");
          dp.AddPane();
          
        
    }

    @FXML
    private void Estudante(MouseEvent event) 
    {
        AdicionaPainel("/vista/PresencaEstudante.fxml");
    }
   
    
 /**************************METODOS OPERACIONAIS*****************************************************************************************************/
   private void AdicionaPainel( String caminho )
   {
       //remove todos os paineis que estao associados ao painel principal
       Principal.getChildren().removeAll();
       
        try {
            //adiciona o painel filho ao principa
            Parent fxml = (Parent)FXMLLoader.load(getClass().getResource(caminho));
            Principal.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(PresencaFaltasController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
   
    private void AdicionaPainelPrincipal( String caminho )
   {
       //remove todos os paineis que estao associados ao painel principal
       pane.getChildren().removeAll();
       
        try {
            //adiciona o painel filho ao principa
            Parent fxml = (Parent)FXMLLoader.load(getClass().getResource(caminho));
            pane.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(PresencaFaltasController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
   
   public static void setPane(Pane pane) {
        PresencaFaltasController.pane = pane;
    }
    
  
    
}
