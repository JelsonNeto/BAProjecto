/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package definicoes;

import Controlador.Estudante.AdicionarEstudanteController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Familia Neto
 */
public class DefinicoesAdicionarDialogo {
    
    
    public void AddDialogo( String path )
    {
        FXMLLoader parent = new FXMLLoader(getClass().getResource(path));
        try {
            Parent root = (Parent)parent.load();
            Stage stage = new Stage();
            stage.setScene( new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(DefinicoesAdicionarDialogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
