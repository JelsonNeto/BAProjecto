/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dialogos.Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class AdicionarEstudanteAjudaController implements Initializable {
    @FXML
    private DialogPane dialogoPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    @FXML
    public void Close( MouseEvent event )
    {
       Stage stage = (Stage) dialogoPane.getScene().getWindow();
       stage.close();
    }
}
