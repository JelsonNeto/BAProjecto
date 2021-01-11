/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Professor;

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
import Controlador.Home.HomeController;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class ProfessorController implements Initializable {
   
    @FXML private Pane professorPrincipal;
    
    
    private static Pane panelBack;
    private static String nomeUser;
    private static String tipoUser;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Adicionar(MouseEvent event) {
        
        String path = "/vista/adicionarProfessor.fxml";
        AdicionarProfessorController.setNomeUser(nomeUser);
        AdicionaPainel(path);
    }

    @FXML
    private void AddVisualizar(MouseEvent event) {
        
        String path = "/vista/visualizarProfessor.fxml";
        VisualizarProfessorController.setPane(professorPrincipal);
        VisualizarProfessorController.setNomeUser(nomeUser);
        VisualizarProfessorController.setTipoUser(tipoUser);
        AdicionaPainel(path);
        
    }
    
    @FXML
    private void AddProfTurma(MouseEvent event) {
        
        String path= "/vista/associarProf_Turma.fxml";
        AdicionaPainel(path);
    }

    public static void setPanelBack(Pane panelBack) {
        ProfessorController.panelBack = panelBack;
    }
    
    
   private void AdicionaPainel( String caminho )
   {
      
       //remove todos os paineis que estao associados ao painel principal
       professorPrincipal.getChildren().removeAll();
       
        try {
            //adiciona o painel filho ao principa
            Parent fxml = (Parent)FXMLLoader.load(getClass().getResource(caminho));
            professorPrincipal.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }

    public static void setNomeUser(String nomeUser) {
        ProfessorController.nomeUser = nomeUser;
    }

    public static void setTipoUser(String tipoUser) {
        ProfessorController.tipoUser = tipoUser;
    }

    

   
}
