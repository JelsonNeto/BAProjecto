/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Menus;

import definicoes.Backup;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class BackupController implements Initializable {

   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void FazerBackup(MouseEvent event)
    {
         Alert a = new Alert(AlertType.INFORMATION,"Backup a ser efectuado, por favor aguarde");
         a.show();
        boolean valor = Backup.FazerBackup();
        if( valor )
        {
            a = new Alert(AlertType.INFORMATION,"Backup efectuado com sucesso");
            a.show();
        }
        else
        {
            a = new  Alert(AlertType.ERROR,"Erro ao fazer o backip");
            a.show();
        }
    }

    
    
}
