/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Layout.ControllerProfessor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class PerfilProfessorController implements Initializable {
    @FXML
    private ImageView imagem;
    @FXML
    private Label txt_nome;
    @FXML
    private Label txt_bi;
    @FXML
    private Label txt_datanasc;
    @FXML
    private Label txt_sexo;
    @FXML
    private Label txt_anolectivo;
    @FXML
    private Label txt_status;
    @FXML
    private Label txt_tipoprofessor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void VerNotas(MouseEvent event) {
    }

    @FXML
    private void FaltasAluno(MouseEvent event) {
    }

    @FXML
    private void EmitirComunicado(MouseEvent event) {
    }

    @FXML
    private void solicitar_passe(MouseEvent event) {
    }
    
}
