/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javax.swing.Timer;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class LoadingController implements Initializable {
    @FXML
    private ProgressBar pb;
    
   
    private static int valor = 0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        CarregaBar c = new CarregaBar( pb );
        c.Carrega();
        
    }    
    
          
    
    
}

class CarregaBar {

    private ProgressBar pb;
    
    public CarregaBar( ProgressBar pb ) {
       
        this.pb = pb;
        
    }
    
    
    
    public void Carrega()
    {
        
        for( int i = 1; i <=11 ; i++)
        {
            try {

                Thread.sleep(100);
                 pb.setProgress(Double.parseDouble("0."+i));
                 
                 
            } catch (InterruptedException ex) {
                Logger.getLogger(CarregaBar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
}
