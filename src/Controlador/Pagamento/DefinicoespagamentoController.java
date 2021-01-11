/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pagamento;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class DefinicoespagamentoController implements Initializable {
    @FXML
    private Pane principal;
    @FXML
    private ImageView imagem1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void showAdd(MouseEvent event) {
        
        AddPane("/vista/adicionarpreco.fxml");
    }

    @FXML
    private void showView(MouseEvent event) {
        AddPane("/vista/visualizarpreco.fxml");
    }

    @FXML
    private void showAddMesAno(MouseEvent event) {
        AddPane("/vista/addMesAnoCobranca.fxml");
    }
    
    private void AddPane( String path )
    {
       
        principal.getChildren().removeAll();
        
        try {
           
            Parent fxml = FXMLLoader.load(getClass().getResource(path));
            
            principal.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(DefinicoespagamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void AbirInfo(MouseEvent event) {
    }
    
}
