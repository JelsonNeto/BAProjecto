/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Loading;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Controlador.Home.HomeController;
import Controlador.Usuario.loginControler;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class CarregarController implements Initializable{
    
    @FXML
    private ProgressBar progress;
    private int valor = 0;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    @SuppressWarnings("empty-statement")
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
     TimerTask t = new TimerTask() {

         @Override
         public void run() {
             
             if( valor == 9 )
             {
                  progress.setProgress(1);
                  CallHome();
             }
             else
               progress.setProgress(Double.parseDouble("0."+(valor++)));
             
         }
     };
     
    Timer  timer = new Timer();
    timer.schedule(t, 0 , 1000);
     
    }    
    
    
     private void CallHome()
     {
         
         FXMLLoader fxml = new FXMLLoader( getClass().getResource("/vista/login.fxml") );
         
        try {
             Parent root = (Parent) fxml.load();
             Stage stage = new Stage();
             stage.setScene( new Scene(root));
             stage.initStyle(StageStyle.UNDECORATED);
             stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CarregarController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
    
  
}

