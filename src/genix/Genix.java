/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genix;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Configuracao;

/**
 *
 * @author Jelson Neto
 */
public class Genix extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        String view = (Configuracao.ConfiguracaoJaFeita())?"login.fxml":"TelaConfiguracao.fxml";
        //String view = "/vista/PainelProfessor/homeProfessor.fxml";
        Parent root = FXMLLoader.load(getClass().getResource("/vista/"+view));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
