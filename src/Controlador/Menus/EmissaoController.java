/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Menus;

import Controlador.Home.HomeController;
import Controlador.Home.HomeEmissaoController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class EmissaoController implements Initializable {
    @FXML
    private Pane pprincipal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        HomeEmissaoController.setPane(pprincipal);
        AdicionaPainel("/vista/homeEmissao.fxml");
        
    }    
    
    
    private void AdicionaPainel( String caminho )
    {
       //remove todos os paineis que estao associados ao painel principal
       pprincipal.getChildren().removeAll();
       
        try {
            //adiciona o painel filho ao principa
            Parent fxml = (Parent)FXMLLoader.load(getClass().getResource(caminho));
            pprincipal.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
