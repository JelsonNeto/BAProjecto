/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Home;

import Controlador.RelatorioFinanceiro.RelatorioFinanceiroController;
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
import Controlador.Pagamento.PagamentoController;
import Controlador.RelatorioPedagogico.RelatorioPedagogicoController;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class HomeRelatorioController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private static Pane pane;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public static void setPane(Pane pane) {
        HomeRelatorioController.pane = pane;
    }
    
    
    @FXML
    public void showRelatorioPedagogico( MouseEvent event )
    {
       RelatorioPedagogicoController.setPane(pane);
        AddPane("/vista/relatorioPedagogico.fxml");
    }
    
    public void showRelaFinanceiro( MouseEvent event )
    {
        RelatorioFinanceiroController.setPane(pane);
        AddPane("/vista/relatorioFinanceiro.fxml");
    }
    
    private  void AddPane( String caminho )
    {
            
            pane.getChildren().removeAll();
           
        
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource(caminho));
            pane.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(PagamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
    }
    
}
