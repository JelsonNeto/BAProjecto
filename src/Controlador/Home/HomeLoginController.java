/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Home;

import Bd.OperacoesBase;
import Validacoes.validarLogin;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Login;
import Controlador.Usuario.EsqueceuSenhaController;
import Controlador.Usuario.loginControler;
import definicoes.DefinicoesData;
import java.time.LocalDate;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import modelo.Activacao;
import modelo.Licenca;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class HomeLoginController implements Initializable {
  
  
    @FXML
    private static Pane pane;
    @FXML 
    private AnchorPane principal;
    @FXML
    private TextField nome;
    @FXML
    private PasswordField senha;
    @FXML private Button btn_adicionar;
    @FXML private Label lbl_fp;

    
    private String fotouser = "";
    private String tipo_usuario = "";
    @FXML
    private Label lbl_msg_licenca1;
    @FXML
    private Label lbl_msg_licenca2;
  
      
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ShowMensagem(verificaLicenca());
    }    

     @FXML
     public void AddPane()
     {
        Preencher();
      
     }
     
    @FXML
     public void close( MouseEvent event )
     {
          Stage stage =(Stage) principal.getScene().getWindow();
          stage.close();
     }
     
     private void CallHome()
     {
         
         FXMLLoader fxml = new FXMLLoader( getClass().getResource("/vista/home.fxml") );
         tipo_usuario = Usuario.CodeToTipo(Usuario.NameToCode(nome.getText()));
         
        try {
             Parent root = (Parent) fxml.load();
             HomeController hc = fxml.getController();
             hc.setUsuario_activo(nome.getText());
             hc.setImagemPath(fotouser);
             hc.SetTipoUser(tipo_usuario);
             
             //Configura o usuario Activo
             Usuario.setUsuario_activo(nome.getText());
             
             Stage stage = new Stage();
             stage.setScene( new Scene(root));
             stage.initStyle(StageStyle.UNDECORATED);
             stage.show();
        } catch (IOException ex) {
            Logger.getLogger(loginControler.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     
    private void ObterImagem( String nomeuser )
    {
        
        ResultSet rs = OperacoesBase.Consultar("select foto from usuario where username = '"+nomeuser+"'"); 
        try
        {
            while( rs.next() )
            {
                fotouser = rs.getString("foto");
            }
            
        }catch( SQLException e )
        {
            e.printStackTrace();
        }
        
    }

  
     @SuppressWarnings("empty-statement")
     private void Preencher()
     {
         
         String nome_user = nome.getText();
         String senha_user = senha.getText();
         
         Alert a;
         if( validarLogin.EstaoVazios(nome_user, senha_user) )
         {
             
             a = new Alert(Alert.AlertType.ERROR,"Existem campos vazios.");
             a.show();
         }
         else
         {
             Login l = new Login();
             l.setUsername(nome_user);
             l.setSenha(senha_user);
             ObterImagem(nome_user);
               
             boolean valor = l.VerificaUsuario();
             if( valor )
             {               
               //Preenche_Activacao_Usuario();
               Stage stage = (Stage)principal.getScene().getWindow();
               stage.close();
               CallHome();
                  
             }
             else
             {
                a = new Alert(Alert.AlertType.ERROR, "Usuario não existe");
                 a.show();
             }
             
         }
     }
     
     
   
     
    @FXML
    private void Esqueceu_senha(MouseEvent event) {
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/vista/redifinirSenha.fxml"));
            EsqueceuSenhaController.setPane(pane);
            pane.getChildren().removeAll();
            pane.getChildren().add(root);
        } catch (IOException ex) {
            Logger.getLogger(loginControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public static void setPane(Pane pane) {
        HomeLoginController.pane = pane;
    }

    public static Pane getPane() {
        return pane;
    }

    private boolean verificaLicenca()
    {
       LocalDate data1 =  DefinicoesData.RetornaDatadoSistema();
       LocalDate data2 = DefinicoesData.StringtoLocalDate(Licenca.RetornaLicenca().getDataExpiracao());
       String tipo_var  = Licenca.RetornaLicenca().getTipo();
       if( data1!=  null && data2 != null && !tipo_var.equalsIgnoreCase("Permanente"))
       {   
           int ano1 = Integer.parseInt(DefinicoesData.RetornaAnoData(String.valueOf(data1)));
           int ano2ParseInt = Integer.parseInt(DefinicoesData.RetornaAnoData(String.valueOf(data2)));
            if(  DefinicoesData.RetornaDiaData(String.valueOf(data1)) >= DefinicoesData.RetornaDiaData(String.valueOf(data2)) && DefinicoesData.RetornaMes(String.valueOf(data1)) == DefinicoesData.RetornaMes(String.valueOf(data2)) && ano1 == ano2ParseInt )
            {
                DisabilitaTudo_ouNAO(true);
                return true;
            }
            else
                if(DefinicoesData.RetornaMes(String.valueOf(data1)) > DefinicoesData.RetornaMes(String.valueOf(data2)) && ano1 == ano2ParseInt  )
                {
                    DisabilitaTudo_ouNAO(true);
                    return true;
                }
                else
                    if( ano1 > ano2ParseInt )
                    {
                        DisabilitaTudo_ouNAO(true);
                        return true;
                    }
            else
              DisabilitaTudo_ouNAO(false);    
       }
       else
           DisabilitaTudo_ouNAO(false);
       return false;
    }
    
    
    private void DisabilitaTudo_ouNAO( boolean valor )
    {
        nome.setDisable(valor);
        senha.setDisable(valor);
        btn_adicionar.setDisable(valor);
        lbl_fp.setDisable(valor);
    }
    
    
    private void ShowMensagem( boolean valor )
    {
        Alert a;
        if( valor )
        {
            a = new Alert(Alert.AlertType.INFORMATION, "A sua licença de utilização expirou.");
            a.show();
        }
        else
        {
            lbl_msg_licenca1.setVisible(false);
            lbl_msg_licenca2.setVisible(false);
        }
    }
    
    
    private void Preenche_Activacao_Usuario()
    {
        int coduser = Usuario.NameToCode(nome.getText());
        String inicio = DefinicoesData.Retorna_Hora_do_Sistema();
        String fim = "0";
        
        Activacao a = new Activacao(coduser,inicio,fim);
        a.Adicionar();
        
        //Passa a activacao para a Home
        HomeController.Obter_Inicio_Activacao(inicio);
    }
}

