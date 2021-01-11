/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.GerirEntidade;

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
 * @author Familia Neto
 */
public class CProfessorController implements Initializable {

    @FXML
    private Pane panel_professor;
    @FXML
    private Label lbl_adicionar;
    @FXML
    private Label lbl_visualizar;
    
    private DefinicoesPane dp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        dp = new DefinicoesPane(panel_professor);
        
       // Inicializar_Adicionar();
    }    

    @FXML
    private void showAdicionar(MouseEvent event) 
    {
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
         dp.setPath("/vista/Vprofessor.fxml");
         dp.AddPane();
    }
    

 /*************************METODOS OPERACIONAIS*********************************************************************/
    private void Inicializar_Adicionar()
    {
        DefinicoesCores.Underline(lbl_adicionar, true);
        dp.setPath("/vista/addProfessor.fxml");
        dp.AddPane();
    }
}
