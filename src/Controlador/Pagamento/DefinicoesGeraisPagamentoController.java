/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pagamento;

import definicoes.DefinicoesPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class DefinicoesGeraisPagamentoController implements Initializable {

    
    private static Pane pane;
    private DefinicoesPane dp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dp = new DefinicoesPane(pane);
    }    

   

    public static void setPane(Pane pane) {
        DefinicoesGeraisPagamentoController.pane = pane;
    }

    @FXML
    private void back(MouseEvent event) {
        
        dp.setPath("/vista/homePagamento_geral.fxml");
        dp.AddPane();
    }
    
    
    
}
