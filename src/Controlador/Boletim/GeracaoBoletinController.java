/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Boletim;

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
public class GeracaoBoletinController implements Initializable {
    
    @FXML private Pane principal;
    private static String nomeUser;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Adicionar(MouseEvent event) 
    {
        //EmitirBoletinController.setNomeUser(nomeUser);
        AdicionaPainel("/vista/emitirBoletin.fxml");
    }

    @FXML
    private void AddVisualizar(MouseEvent event) 
    {
        AdicionaPainel("/vista/visualizarBoletin.fxml");
    }
    
     @FXML
    private void EmitirBoletim_Turma(MouseEvent event) 
    {
        AdicionaPainel("/vista/emitirBoletin_turma.fxml");
    }
    

    public static void setNomeUser(String nomeUser) {
        GeracaoBoletinController.nomeUser = nomeUser;
    }
    
    
    
    
    
/***********************************METODOS OPERACIONAIS***********************************************************/
    
   private void AdicionaPainel( String caminho )
   {
       //remove todos os paineis que estao associados ao painel principal
       principal.getChildren().removeAll();
       
        try {
            //adiciona o painel filho ao principa
            Parent fxml = (Parent)FXMLLoader.load(getClass().getResource(caminho));
            principal.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(GeracaoBoletinController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }

   
}
