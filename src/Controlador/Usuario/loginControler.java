package Controlador.Usuario;

import Controlador.Home.HomeLoginController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import modelo.Configuracao;

public class loginControler implements Initializable {
 
    @FXML
    private Pane principal;
    @FXML
    private AnchorPane pane;
    @FXML
    private Label txt_tipo;
    @FXML
    private Label txt_escola;
    @FXML
    private ImageView foto_logo;
  
    private static String redifinir_senha = "";
	
    
        
    @Override
     public void initialize(URL arg0, ResourceBundle arg1) {

         InicializaCampos();
        try {
            Parent root = FXMLLoader.load(getClass().getResource((redifinir_senha.equalsIgnoreCase("")?"/vista/homeLogin.fxml":redifinir_senha)));
            if( redifinir_senha.equalsIgnoreCase("") )
                HomeLoginController.setPane(principal);
            else
                EsqueceuSenhaController.setPane(principal);
            principal.getChildren().add(root);
        } catch (IOException ex) {
            Logger.getLogger(loginControler.class.getName()).log(Level.SEVERE, null, ex);
        }
       
     }

   
     private void InicializaCampos()
     {
        Configuracao c =  Configuracao.DadosConfiguracao();
        if( c != null )
        {
            foto_logo.setImage( new Image("/icones/"+c.getFotografia()));
            txt_escola.setText(c.getNomeescola());
            txt_tipo.setText(c.getTipoescola());
        }
        //Teste();
     }
   
     
     

    public static void setRedifinir_senha(String redifinir_senha) {
        loginControler.redifinir_senha = redifinir_senha;
    }
     
     
}
