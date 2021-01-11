/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package definicoes;

import Controlador.Usuario.loginControler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Familia Neto
 */
public class DefinicoesPane {
    
    private String path;
    private Pane pane;

    
    
    public DefinicoesPane() {
    }

    public DefinicoesPane( Pane pane )
    {
        this.pane = pane;
    }
    
    public DefinicoesPane(String path, Pane pane) {
        this.path = path;
        this.pane = pane;
    }

   

    public void setPath(String path) {
        this.path = path;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }
    
    
    
    public  void AddPane()
    {
        try {
            Parent p = FXMLLoader.load( getClass().getResource(this.path) );
            this.pane.getChildren().removeAll();
            this.pane.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(DefinicoesPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   public void CallOtherWindow( String nomeView, AnchorPane janelaFechar )
   {
         FXMLLoader fxml = new FXMLLoader( getClass().getResource("/vista/"+nomeView+".fxml"));
          try {
             Parent root = (Parent) fxml.load();
             Stage stage = new Stage();
             stage.setScene( new Scene(root));
             stage.initStyle(StageStyle.UNDECORATED);
             if( janelaFechar != null )
             {
                 Stage stage2 = (Stage)janelaFechar.getScene().getWindow();
                 stage2.close();
             }
            
             stage.show();
        } catch (IOException ex) {
            Logger.getLogger(loginControler.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
      public void CallDialogs( String nomeView)
   {
         FXMLLoader fxml = new FXMLLoader( getClass().getResource("/dialogos/"+nomeView+".fxml"));
          try {
             Parent root = (Parent) fxml.load();
             Stage stage = new Stage();
             stage.setScene( new Scene(root));
             stage.initStyle(StageStyle.UNDECORATED);
             stage.show();
        } catch (IOException ex) {
            Logger.getLogger(loginControler.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   public static void Fechar_Janela( AnchorPane pane )
   {
       if( pane != null )
       {
           Stage stage = (Stage)pane.getScene().getWindow();
           stage.close();
       }
       else
           System.out.println("O Pane esta vazio");
      
   }
    
   public static boolean Habilita_Desabilita_Pane_Lateral( Pane pane, boolean activo )
   {
       if( activo )
       {
           pane.setVisible(false);
           return false;
       }
       else
       {
           pane.setVisible(true);
           return true;
       }
   }
   
   public static boolean Desabilita_Panel_Lateral( Pane panel , boolean activo )
   {
       if( activo )
       {
           panel.setVisible(false);
           return false;
       }
       return true;
   }
   
}
