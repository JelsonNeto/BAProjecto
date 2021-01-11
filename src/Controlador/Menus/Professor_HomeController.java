/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Menus;

import Controlador.Professor.VprofessorController;
import definicoes.DefinicoesCores;
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
public class Professor_HomeController implements Initializable {

    @FXML
    private Label lbl_adicionar;
    @FXML
    private Label lbl_visualizar;

    @FXML
    private Pane panel_professor;
    
    private DefinicoesPane dp;
    private static Label label_adicionar;
    private static Label label_visualizar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dp = new DefinicoesPane(panel_professor);
        Inicializar_Adicionar();
        label_adicionar = lbl_adicionar;
        label_visualizar = lbl_visualizar;
    }    

    @FXML
    private void showAdicionar(MouseEvent event) {
        
         DefinicoesCores.Underline(lbl_adicionar, true);
         DefinicoesCores.Underline(lbl_visualizar, false);
         dp.setPath("/vista/addProfessor.fxml");
         dp.AddPane();
    }

    @FXML
    private void showVisualizar(MouseEvent event)
    {
        
         DefinicoesCores.Underline(lbl_adicionar, false);
         DefinicoesCores.Underline(lbl_visualizar, true);
         VprofessorController.setPaneBack(panel_professor);
         dp.setPath("/vista/Vprofessor.fxml");
         dp.AddPane();
    }
    
//=====================METODOS AUXILIARES===============================
     private void Inicializar_Adicionar()
    {
        DefinicoesCores.Underline(lbl_adicionar, true);
        dp.setPath("/vista/addProfessor.fxml");
        dp.AddPane();
    }

    public static Label getLabel_adicionar() {
        return label_adicionar;
    }

    public static Label getLabel_visualizar() {
        return label_visualizar;
    }

    
     
     
}
