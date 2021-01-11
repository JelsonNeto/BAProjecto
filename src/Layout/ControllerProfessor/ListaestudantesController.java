/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Layout.ControllerProfessor;

import definicoes.DefinicoesPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class ListaestudantesController implements Initializable {
    @FXML
    private Pane panel_principal;
    @FXML
    private ImageView imagem1;

    private DefinicoesPane dp;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dp = new DefinicoesPane(panel_principal);
    }    

    @FXML
    private void show_visualizar(MouseEvent event) {
        dp.setPath("/vista/PainelProfessor/consultarminipauta.fxml");
        dp.AddPane();
    }

    @FXML
    private void AbirInfo(MouseEvent event) {
    }
    
}
