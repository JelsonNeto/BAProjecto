/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioPedagogico;

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
public class RelatorioPedagogicoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private static Pane pane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void showInfoNotas(MouseEvent event) {
        AddPane("/vista/informacoesNotas.fxml");
    }

    @FXML
    private void showQuadroHonra(MouseEvent event) {
        AddPane("/vista/quadrohonra.fxml");
    }

    @FXML
    private void showExemplares(MouseEvent event) {
    }
      
   
    
    
    
/********************METODOS AUXILIARES**************************************************/
      public void AddPane( String path )
    {
        try {
            Parent p = FXMLLoader.load( getClass().getResource(path) );
            pane.getChildren().removeAll();
            pane.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(RelatorioPedagogicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setPane(Pane pane) {
        RelatorioPedagogicoController.pane = pane;
    }

    
      
}
