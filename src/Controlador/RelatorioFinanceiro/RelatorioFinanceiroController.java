/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioFinanceiro;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import Controlador.Pagamento.PagamentoController;
import definicoes.DefinicoesPane;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class RelatorioFinanceiroController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private static Pane pane;
    private DefinicoesPane dp;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        PagamentoController.Alterar_Titulo_Janela("Área de Finanças - Relatórios");
        dp = new DefinicoesPane(pane);
    }    

    @FXML
    private void showRelatorioMensal(MouseEvent event) {
        
        FinanceiroMensalController.setPane(pane);
        AddPane("/vista/FinanceiroMensal.fxml");
        
    }

    @FXML
    private void showRelaAnual(MouseEvent event) {
        
        FinanceiroAnualController.setPane(pane);
        AddPane("/vista/financeiroAnual.fxml");
        
    }
    
    
/******************************METODOS AUXILIARES***************************************************/
    private  void AddPane( String caminho )
    {
            
            pane.getChildren().removeAll();
           
        
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource(caminho));
            pane.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(PagamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
    }

    public static void setPane(Pane pane) {
        RelatorioFinanceiroController.pane = pane;
    }

    @FXML
    private void back(MouseEvent event) {
        
        dp.setPath("/vista/homePagamento_geral.fxml");
        dp.AddPane();
        
    }
    
    
}
