/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Menus;

import Controlador.Pagamento.PagamentoController;
import Controlador.Home.HomeRelatorioController;
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
public class RelatorioController implements Initializable {
    @FXML
    private Pane principal;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        HomeRelatorioController.setPane(principal);
        AddPane("/vista/homeRelatorio.fxml");
        
    }    

    private  void AddPane( String caminho )
    {
            
            principal.getChildren().removeAll();
           
        
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource(caminho));
            principal.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(PagamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
    }
}
