/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Menus;

import Controlador.Encarregado.VisualizarEncarregadoController;
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
public class EncarregadoController implements Initializable {
    
    
    @FXML
    private Pane principal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Adicionar(MouseEvent event) 
    {
        AdicionaPainel("/vista/addEncarregado.fxml");
    }

    @FXML
    private void AddVisualizar(MouseEvent event) {
        
        VisualizarEncarregadoController.setPrincipal(principal);
        AdicionaPainel("/vista/VisualizarEncarregado.fxml");
    }
    
/****************************************************Metodos Auxiliares***************************************************************/

   private void AdicionaPainel( String caminho )
   {
       //remove todos os paineis que estao associados ao painel principal
       principal.getChildren().removeAll();
       
        try {
            //adiciona o painel filho ao principa
            Parent fxml = (Parent)FXMLLoader.load(getClass().getResource(caminho));
            principal.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(EncarregadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }

}
