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
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Familia Neto
 */
public class PaginahomePrincipalController implements  Initializable{
    @FXML
    private AnchorPane Panel_principal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        DefinicoesPane dp = new DefinicoesPane("/vista/PainelProfessor/paginahome2.fxml", Panel_principal);
        dp.AddPane();
        
        PaginahomeController.setPane(Panel_principal);
        HomeProfessorController.setPainel_principal(Panel_principal);
    }
    
}
