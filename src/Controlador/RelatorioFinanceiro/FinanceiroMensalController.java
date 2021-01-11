/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioFinanceiro;

import Controlador.Pagamento.PagamentoController;
import definicoes.DefinicoesAdicionarDialogo;
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
public class FinanceiroMensalController implements Initializable {
    @FXML
    private ImageView imagem1;
    private static Pane pane;
    private DefinicoesPane dp;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        PagamentoController.Alterar_Titulo_Janela("Área de Finanças - Relatórios - Mensal");
        dp = new DefinicoesPane(pane);
    }    

    @FXML
    private void AbirInfo(MouseEvent event) {
        
        DefinicoesAdicionarDialogo d = new DefinicoesAdicionarDialogo();
        d.AddDialogo("/dialogos/adicionarEstudanteAjuda.fxml");
        
    }

    
    @FXML
    private void back(MouseEvent event) {
        
        dp.setPath("/vista/relatorioFinanceiro.fxml");
        dp.AddPane();
    }

    public static void setPane(Pane pane) {
        FinanceiroMensalController.pane = pane;
    }
    
    
    
    
}
