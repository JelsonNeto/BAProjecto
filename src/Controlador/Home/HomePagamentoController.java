/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Home;

import Controlador.Pagamento.EfectuarPagamento2Controller;
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
import Controlador.Pagamento.ManipulacoesExtraController;
import Controlador.Pagamento.PagamentoController;
import Controlador.Pagamento.PagamentosematrasoController;
import Controlador.Pagamento.VisualizarPagamentoController;
import definicoes.DefinicoesPane;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class HomePagamentoController implements Initializable {
    
    
    @FXML
    private static Pane pprincipal;
    private static String nomeUser;
    private static String tipoUser;
    
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
 @FXML
    public void showEfectuarPagamento( MouseEvent event )
    {
      
        EfectuarPagamento2Controller.setPane(pprincipal);
        EfectuarPagamento2Controller.setNomeuser(nomeUser);
        AddPane("/vista/efectuarPagamento2.fxml");
        
       
    }
 @FXML
    public void showVisualizarPagamento( MouseEvent event )
    {
        VisualizarPagamentoController.setTipoUser(tipoUser);
        VisualizarPagamentoController.setPane(pprincipal);
        VisualizarPagamentoController.setNomeUser(nomeUser);
        AddPane("/vista/visualizarPagamento.fxml");
    }
    
    @FXML
    private void showpagaemnto_atraso(MouseEvent event) {
        
        PagamentosematrasoController.setPane(pprincipal);
        PagamentosematrasoController.setNome_funcionario(nomeUser);
        AddPane("/vista/pagamentosematraso.fxml");
        
    }
    
    private  void AddPane( String caminho )
    {
            
            pprincipal.getChildren().removeAll();
           
        
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource(caminho));
            pprincipal.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(PagamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
    }
    
    public static void SetPane( Pane p )
    {
        pprincipal  = p;
    }

    public static void setPane(Pane pane) {
        HomePagamentoController.pane = pane;
    }

    
    
    public static void setNomeUser(String nomeUser) {
        HomePagamentoController.nomeUser = nomeUser;
    }

    public static void setTipoUser(String tipoUser) {
        HomePagamentoController.tipoUser = tipoUser;
    }

    @FXML
    private void back(MouseEvent event) {
        
        dp.setPath("/vista/homepagamento_geral.fxml");
        dp.AddPane();
    }

   
    
}
