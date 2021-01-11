/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Pagamento;

import Controlador.Home.HomePagamentoController;
import Controlador.Home.HomePagamento_geralController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class PagamentoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private  Pane pprincipal;
    
    private static String nome;
    private static String tipoUser;
    @FXML
    private  Label lbl_titulo_pagamento;
    @FXML
    private Pane principal;
    
    private static Label label;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        label = lbl_titulo_pagamento;
        HomePagamentoController.SetPane(pprincipal);
        HomePagamentoController.setNomeUser(nome);
        HomePagamentoController.setTipoUser(tipoUser);
        HomePagamento_geralController.setPanel(pprincipal);
        AddPane("/vista/homePagamento_geral.fxml");
        
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
     
     public void show(ActionEvent e )
     {
         System.out.println(nome);
     }

    public static void setNome(String nome) {
        PagamentoController.nome = nome;
    }

    public static void setTipoUser(String tipoUser) {
        PagamentoController.tipoUser = tipoUser;
        
    }

    public Pane getPprincipal() {
        return pprincipal;
    }

   public static void Alterar_Titulo_Janela( String texto )
   {
       label.setText(texto);
   }
   
    
     
  
}
