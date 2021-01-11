/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Pagamento;

import Controlador.Pagamento.PagamentosematrasoController;
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

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class ManipulacoesExtraController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Pane principal;
    private static Pane pane;
    private static String tipoUser;
    private static String nome_funcionario;
    @FXML
    private Pane PanePagaDef;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
       DisableDefinicoesPagamento();
            
    }    
    
    @FXML
    public void showAtraso( MouseEvent event )
    {
        PagamentosematrasoController.setNome_funcionario(nome_funcionario);
        PagamentosematrasoController.setPane(principal);
        AddPane("/vista/pagamentosematraso.fxml");
    }
    
    @FXML
    public void showDadosGerais( MouseEvent event )
    {
        AddPane("/vista/addMesAnoCobranca.fxml");
    }
    
    @FXML
    public void showdefinicoes( MouseEvent event )
    {
        AddPane("/vista/definicoespagamento.fxml");
    }
    
   
    
    private void AddPane( String caminho )
    {
        
        principal.getChildren().removeAll();
        
        Parent fxml = null;
        try {
            fxml = FXMLLoader.load(getClass().getResource(caminho));
        } catch (IOException ex) {
            Logger.getLogger(ManipulacoesExtraController.class.getName()).log(Level.SEVERE, null, ex);
        }
        principal.getChildren().add(fxml);
        
    }

    @FXML
    private void back(MouseEvent event) {
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/vista/homePagamento.fxml"));
            pane.getChildren().setAll(root);
        } catch (IOException ex) {
            Logger.getLogger(ManipulacoesExtraController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public static void setPane(Pane pane) {
        ManipulacoesExtraController.pane = pane;
    }

    public static void setTipoUser(String tipoUser) {
        ManipulacoesExtraController.tipoUser = tipoUser;
       
    }
   
    private void DisableDefinicoesPagamento()
    {
        if (!"Admin".equals(tipoUser)) {
            PanePagaDef.setDisable(true);
        }
    }

    public static void setNome_funcionario(String nome_funcionario) {
        ManipulacoesExtraController.nome_funcionario = nome_funcionario;
    }
            
    
    
}
