/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Layout.ControllerProfessor;

import Controlador.Home.HomeController;
import Controlador.Home.PaginaHomeController;
import Controlador.Menus.EstudanteController;
import Controlador.Pagamento.PagamentoController;
import Controlador.Usuario.loginControler;
import definicoes.DefinicoesPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Configuracao;

/**
 *
 * @author Familia Neto
 */
public class HomeProfessorController implements Initializable {
    
     /**
   * FXML , é uma anotação utilizada para vincular os campos utilizados no sceneBuilder dentro do 
   * ficheiro fxml
   * 
   * Cada anotação FXML , esta acompanhada com o respectivo identificador do campo a vincular
   * 
   */
    
    @FXML private Pane pPerfil;
    @FXML private Label llperfil;
    @FXML private Pane PNotas;
    @FXML private Label lNotas;
    @FXML private Pane pListaAlunos;
    @FXML private Label lLista;
    @FXML private Pane principal;
    @FXML private Pane pHome;
    @FXML private Label lHome;
    @FXML private Label tipo_usuario;
    @FXML private AnchorPane anchorpane;
    @FXML private ImageView imagem_user;
    @FXML private Label usuario_activo;
    @FXML private Label txt_tipo;
    @FXML private Label txt_nome;
    @FXML private ImageView logo_foto;
    
    private  String nomeFuncionario;
    private String imagemPath;
    private static boolean pagina_activa = false;
    private static AnchorPane Painel_principal;
    DefinicoesPane dp;
   
    
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // Inicializa_CamposEscola();
      //  PaginaHomeController.setPane(principal);
      //  PaginaHomeController.setNomeUser(nomeFuncionario);
       // AdicionaPainel("/vista/paginaHome.fxml");
         dp = new DefinicoesPane();
        InicializaPaginaHome();
    }    
    
    
    
   
    @FXML
    public void ChangeColorHome( MouseEvent event )
    {
        this.setColor(pHome, lHome);
        ResetColor(pListaAlunos, lLista);
        ResetColor(PNotas, lNotas);
        ResetColor(pPerfil, llperfil);
        PaginaHomeController.setPane(principal);
        AdicionaPainel("/vista/PainelProfessor/paginahome_principal.fxml");
    }
    
    @FXML
    private void ChangeColorNotas(MouseEvent event) {
        
        this.setColor(PNotas, lNotas);
        ResetColor(pHome, lHome);
        ResetColor(pListaAlunos, lLista);
        ResetColor(pPerfil, llperfil);
        dp.setPane(principal);
        dp.setPath("/vista/PainelProfessor/gestaonotas.fxml");
        dp.AddPane();
    }

    @FXML
    private void ChangeColorPerfil(MouseEvent event) {
        
        this.setColor(pPerfil, llperfil);
        ResetColor(PNotas, lNotas);
        ResetColor(pHome, lHome);
        ResetColor(pListaAlunos, lLista);
        PaginaHomeController.setPane(principal);
        AdicionaPainel("/vista/PainelProfessor/perfilProfessor.fxml");
    }

    @FXML
    private void ChangeColorlista(MouseEvent event) {
        
        this.setColor(pListaAlunos, lLista);
        ResetColor(PNotas, lNotas);
        ResetColor(pHome, lHome);
        ResetColor(pPerfil, llperfil);
        dp.setPane(principal);
        dp.setPath("/vista/PainelProfessor/listaestudantes.fxml");
        dp.AddPane();
       
    }
    
    
     
    public void Close( MouseEvent event )
    {
        Alert alert = new Alert( Alert.AlertType.INFORMATION , "Deseja encerrar o aplicativo?" );
        alert.show();
       
    }
    
    @FXML
    public void Fechar( MouseEvent event ) throws IOException
    {
        if( pagina_activa )
        {
            
            dp.setPane(Painel_principal);
            dp.setPath("/vista/PainelProfessor/paginahome2.fxml");
            dp.AddPane();
            pagina_activa = false;
        }
        else
        {
            dp.setPane(Painel_principal);
            dp.setPath("/vista/PainelProfessor/paginahome.fxml");
            dp.AddPane();
            pagina_activa = true;
        }
        
        
        
    }
    
  
/*******************************************METODOS AUXILIARES*******************************************************************************/
  //metodos operacionais
    private void setColor( Pane p , Label l )
    {
       String styleP = "-Fx-Background-Color : #ffffff";
       String syleL = "-Fx-Text-Fill: #006699";
       
        p.setStyle(styleP);
        l.setStyle(syleL);
    
    }
    
    private void ResetColor( Pane p , Label l )
    {
       String styleP = "-Fx-Background-Color : #006699";
       String syleL = "-Fx-Text-Fill: #ffffff";
       
        p.setStyle(styleP);
        l.setStyle(syleL);
    
    }

    public static void setPainel_principal(AnchorPane Painel_principal) {
        HomeProfessorController.Painel_principal = Painel_principal;
    }
    
    
   
   
  private void AdicionaPainel( String caminho )
   {
      
       //remove todos os paineis que estao associados ao painel principal
       principal.getChildren().removeAll();
       
        try {
            //adiciona o painel filho ao principa
            Parent fxml = (Parent)FXMLLoader.load(getClass().getResource(caminho));
            principal.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }

    public void setUsuario_activo( String texto ) {
        this.usuario_activo.setText(texto);
        nomeFuncionario = texto;
         
    }

    public void setImagemPath(String imagemPath) {
        this.imagemPath = imagemPath;
         imagem_user.setImage( new Image(imagemPath));
    }
   
   public void SetTipoUser( String tipo )
   {
       tipo_usuario.setText(tipo);
       
   }

  
   private void InicializaPaginaHome()
   {
       DefinicoesPane dp = new DefinicoesPane("/vista/PainelProfessor/paginahome_principal.fxml", principal);
       dp.AddPane();
   }
   
   private void Inicializa_CamposEscola()
   {
       Configuracao c = Configuracao.DadosConfiguracao();
       if( c!= null )
       {
           logo_foto.setImage( new Image("/icones/"+c.getFotografia()));
           txt_nome.setText(c.getNomeescola());
           txt_tipo.setText(c.getTipoescola());
       }
   }

    

    
}
