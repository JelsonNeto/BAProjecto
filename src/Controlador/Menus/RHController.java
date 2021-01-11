/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Menus;

import Controlador.GerirEntidade.CFuncionarioController;
import definicoes.DefinicoesPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Familia-Neto
 */
public class RHController implements Initializable {

    @FXML
    private Label lbl_funcionario;
    @FXML
    private Label lbl_professor;
    
    @FXML
    private Pane rhprincipal;

    private DefinicoesPane dp;
    @FXML
    private Label lbl_informacoes;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dp = new DefinicoesPane(rhprincipal);
    }    

    @FXML
    private void funcionario(MouseEvent event) {
        
        CFuncionarioController.setPane_cadastrar(rhprincipal);
        dp.setPath("/vista/CFuncionario.fxml");
        dp.AddPane();
        
    }

    @FXML
    private void Professor(MouseEvent event) 
    {
         CFuncionarioController.setPane_cadastrar(rhprincipal);
         dp.setPath("/vista/Professor_Home.fxml");
         dp.AddPane();
    }

    @FXML
    private void InfoAdd(MouseEvent event) {
        
         CFuncionarioController.setPane_cadastrar(rhprincipal);
         dp.setPath("/vista/informacoesAdicionais.fxml");
         dp.AddPane();
    }

    
    
}
