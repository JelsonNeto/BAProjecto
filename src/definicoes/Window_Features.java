/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package definicoes;

import Controlador.Usuario.loginControler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Familia Neto
 */
public class Window_Features {
   
    private String fxml_path;

    public Window_Features(String fxml_path) {
        this.fxml_path = fxml_path;
    }

    public Window_Features() {
    }
    
    

    public String getFxml_path() {
        return fxml_path;
    }
    
     public void setFxml_path(String fxml_path) {
        this.fxml_path = fxml_path;
    }
    
   public  void Call()
   {
         
         FXMLLoader fxml = new FXMLLoader( getClass().getResource(getFxml_path()) );
         
        try {
             Parent root = (Parent) fxml.load();
             Stage stage = new Stage();
             stage.setScene( new Scene(root));
             stage.initStyle(StageStyle.UNDECORATED);
             stage.show();
        } catch (IOException ex) {
            Logger.getLogger(loginControler.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

   public void Close( AnchorPane anchorPane )
   {
      Stage stage =  (Stage)anchorPane.getScene().getWindow();
      stage.close();
   }
   
   
}
