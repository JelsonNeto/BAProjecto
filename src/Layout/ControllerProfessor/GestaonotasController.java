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
public class GestaonotasController implements Initializable {
    @FXML
    private Pane panel_principal;

    
    private DefinicoesPane dp;
    @FXML
    private ImageView imagem1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dp = new DefinicoesPane();
        dp.setPane(panel_principal);
    }    

    @FXML
    private void show_preencher(MouseEvent event) {
        
        dp.setPath("/vista/PainelProfessor/preenchernotas.fxml");
        dp.AddPane();
        
    }

    @FXML
    private void show_consultar(MouseEvent event) {
        
        dp.setPath("/vista/PainelProfessor/consultarminipauta.fxml");
        dp.AddPane();
        
    }
    
    @FXML
    private void AbirInfo(MouseEvent event) {
    }

   
    
    
}
