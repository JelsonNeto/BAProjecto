/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Home;

import Controlador.Menus.EstudanteController;
import Controlador.Menus.RHController;
import Controlador.Notas.NotasController;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Controlador.Pagamento.PagamentoController;
import Controlador.Usuario.DefinicoesController;
import Controlador.Usuario.loginControler;
import definicoes.DefinicoesData;
import modelo.Activacao;
import modelo.Configuracao;
import modelo.MesAno;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Jelson Neto
 */
public class HomeController implements Initializable{

    
  /**
   * FXML , é uma anotação utilizada para vincular os campos utilizados no sceneBuilder dentro do 
   * ficheiro fxml
   * 
   * Cada anotação FXML , esta acompanhada com o respectivo identificador do campo a vincular
   * 
   */
    
    @FXML private Pane principal;
    @FXML private Pane pHome;
    @FXML private Pane pCadastrar;
    @FXML private Pane pEstudante;
    @FXML private Pane pPagamento;
    @FXML private Pane pDefinicoes;
    @FXML private Pane pNotas;
    @FXML private Pane pEmissao;
    @FXML private Label lHome;
    @FXML private Label lEstudante;
    @FXML private Label lDefinicoes;
    @FXML private Label lCadastrar;
    @FXML private Label lPagamento; 
    @FXML private Label tipo_usuario;
    @FXML private Label lNotas;
    @FXML private Label lEmissao;
    @FXML private AnchorPane anchorpane;
    @FXML private ImageView imagem_user;
    @FXML private Label usuario_activo;
    @FXML private Pane pMatricula;
    @FXML private Label lmatricula;
    
    @FXML private Label txt_tipo;
    @FXML private Label txt_nome;
    @FXML private ImageView logo_foto;
    
    private  String nomeFuncionario;
    private String imagemPath;
    private static String inicio_activacao;
    @FXML
    private Pane pRH;
    @FXML
    private Label lrh;
    @FXML
    private Label txt_nome1;
    @FXML
    private Label txt_anolectivo;
    
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Inicializa_CamposEscola();
        PaginaHomeController.setPane(principal);
        PaginaHomeController.setNomeUser(nomeFuncionario);
        AdicionaPainel("/vista/paginaHome.fxml");
       System.out.print(Usuario.tipo_Usuario_Activo());
        
    }    
    
    
    /**
     * Este metodo é utilizado para possivel acontecimento de um evento do tipo MouseEvento
     * @param event , armazna o objecto do evento
     * 
     * Este metodo foi criando para ajudar na troca de cores de fundo dos paineis e das labels.
     * Cada pane esta associado a um metodo especifico e sua label correspondente.
     */
    @FXML
    public void ChangeColorEstuda( MouseEvent event )
    {
        this.setColor(pEstudante, lEstudante);
        ResetColor(pHome, lHome);
        ResetColor(pNotas, lNotas);
        ResetColor(pPagamento, lPagamento);
        ResetColor(pDefinicoes, lDefinicoes);
        ResetColor(pCadastrar, lCadastrar);
        ResetColor(pMatricula, lmatricula);
        ResetColor(pEmissao, lEmissao);
        ResetColor(pRH, lrh);
        
        //Chama o metodo privado para adicionar o painel referente ao estudante na janela principal
        String caminho = "/vista/estudante.fxml";
        EstudanteController.setNomeUser(nomeFuncionario);
        EstudanteController.setTipUser(tipo_usuario.getText());
        EstudanteController.setPanelBack(principal);
        EstudanteController.setPaneGeral(principal);
        AdicionaPainel(caminho);
    }
    @FXML
    public void ChangeColorHome( MouseEvent event )
    {
        this.setColor(pHome, lHome);
        ResetColor(pEstudante, lEstudante);
        ResetColor(pPagamento, lPagamento);
        ResetColor(pDefinicoes, lDefinicoes);
        ResetColor(pCadastrar, lCadastrar);
        ResetColor(pMatricula, lmatricula);
        ResetColor(pEmissao, lEmissao);
        ResetColor(pNotas, lNotas);
        ResetColor(pRH, lrh);
        
        
        PaginaHomeController.setPane(principal);
        AdicionaPainel("/vista/paginaHome.fxml");
    }
    
    @FXML
    public void ChangeColorPaga( MouseEvent event )
    {
        this.setColor(pPagamento, lPagamento);
        ResetColor(pHome, lHome);
        ResetColor(pNotas, lNotas);
        ResetColor(pEstudante, lEstudante);
        ResetColor(pDefinicoes, lDefinicoes);
        ResetColor(pCadastrar, lCadastrar);
        ResetColor(pMatricula, lmatricula);
        ResetColor(pEmissao, lEmissao);
        ResetColor(pRH, lrh);
        
       PagamentoController.setNome(usuario_activo.getText());
       PagamentoController.setTipoUser(tipo_usuario.getText());
       AdicionaPainel("/vista/pagamento.fxml");
        
    }
    
    @FXML
    public void ChangeColorDef( MouseEvent event )
    {
        this.setColor(pDefinicoes, lDefinicoes);
        ResetColor(pHome, lHome);
        ResetColor(pNotas, lNotas);
        ResetColor(pEstudante, lEstudante);
        ResetColor(pPagamento, lPagamento);
        ResetColor(pCadastrar, lCadastrar);
        ResetColor(pMatricula, lmatricula);
        ResetColor(pEmissao, lEmissao);
        ResetColor(pRH, lrh);
        
        DefinicoesController.setNomeUser(nomeFuncionario);
        DefinicoesController.setTipoUser(tipo_usuario.getText());
        AdicionaPainel("/vista/definicoes.fxml");
    }
    
    @FXML
    public void ChangeColorCada( MouseEvent event )
    {
        this.setColor(pCadastrar, lCadastrar);
        ResetColor(pHome, lHome);
        ResetColor(pNotas, lNotas);
        ResetColor(pEstudante, lEstudante);
        ResetColor(pPagamento, lPagamento);
        ResetColor(pDefinicoes, lDefinicoes);
        ResetColor(pMatricula, lmatricula);
        ResetColor(pEmissao, lEmissao);
        ResetColor(pRH, lrh);
        
        
        
        //chama o metodo adicionar para adicionar uma nova jane ao painel principal
        String caminho = "/vista/Cadastrar.fxml"; 
        AdicionaPainel(caminho);
        
    }
    
   
    
    
    @FXML
    private void ChangeColorNotas( MouseEvent event )
    {
        this.setColor(pNotas, lNotas);
        ResetColor(pHome, lHome);
        ResetColor(pEstudante, lEstudante);
        ResetColor(pPagamento, lPagamento);
        ResetColor(pDefinicoes, lDefinicoes);
        ResetColor(pCadastrar, lCadastrar);
        ResetColor(pMatricula, lmatricula);
        ResetColor(pEmissao, lEmissao);
        ResetColor(pRH, lrh);
        
        
        String caminho = "/vista/notas.fxml";
        NotasController.setNomeUser(nomeFuncionario);
        NotasController.setTipoUser(tipo_usuario.getText());
        AdicionaPainel(caminho);
    }
    
    @FXML
    private void ChangeColorEmissao(MouseEvent event) 
    {
        this.setColor(pEmissao, lEmissao);
        ResetColor(pHome, lHome);
        ResetColor(pEstudante, lEstudante);
        ResetColor(pPagamento, lPagamento);
        ResetColor(pDefinicoes, lDefinicoes);
        ResetColor(pCadastrar, lCadastrar);
        ResetColor(pMatricula, lmatricula);
        ResetColor(pNotas, lNotas);
        ResetColor(pRH, lrh);
        
        
        String caminho = "/vista/emissao.fxml";
       // NotasController.setNomeUser(nomeFuncionario);
       // NotasController.setTipoUser(tipo_usuario.getText());
        AdicionaPainel(caminho);
    }
    
    @FXML
    private void changematricula(MouseEvent event) 
    {
        this.setColor(pMatricula, lmatricula);
        ResetColor(pHome, lHome);
        ResetColor(pEstudante, lEstudante);
        ResetColor(pPagamento, lPagamento);
        ResetColor(pDefinicoes, lDefinicoes);
        ResetColor(pCadastrar, lCadastrar);
        ResetColor(pEmissao, lEmissao);
        ResetColor(pNotas, lNotas);
        ResetColor(pRH, lrh);
        
        
        String caminho = "/vista/matricula.fxml";
       // NotasController.setNomeUser(nomeFuncionario);
       // NotasController.setTipoUser(tipo_usuario.getText());
        AdicionaPainel(caminho);
    }
     @FXML
    private void ChangeColorRH(MouseEvent event) 
    {
        this.setColor(pRH, lrh);
        ResetColor(pHome, lHome);
        ResetColor(pEstudante, lEstudante);
        ResetColor(pPagamento, lPagamento);
        ResetColor(pDefinicoes, lDefinicoes);
        ResetColor(pCadastrar, lCadastrar);
        ResetColor(pEmissao, lEmissao);
        ResetColor(pNotas, lNotas);
        ResetColor(pMatricula, lmatricula);
        
        
        String caminho = "/vista/RecursosHumanos.fxml";
        AdicionaPainel(caminho);
    }
   

    
    public void Close( MouseEvent event )
    {
        Alert alert = new Alert( AlertType.INFORMATION , "Deseja encerrar o aplicativo?" );
        alert.show();
       
    }
    
    @FXML
    public void Fechar( MouseEvent event )
    {
        Stage stage = (Stage)anchorpane.getScene().getWindow();
        stage.close();
        Actualiza_Fim_Activacao_User();
        CallLogin();
       
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

   private void CallLogin()
   {
         
         FXMLLoader fxml = new FXMLLoader( getClass().getResource("/vista/login.fxml") );
         
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

   /*
    @Override
    public void run() 
    {
        int mesActual = DefinicoesData.RetornaMes(String.valueOf(DefinicoesData.RetornaDatadoSistema()));
        int diaActual = DefinicoesData.RetornaDiaData(String.valueOf(DefinicoesData.RetornaDatadoSistema()));
        for( Pagamento p : Pagamento.PagamentosReferenteMatriculasAnoActual() )
        {
           int mesPagamento = DefinicoesData.RetornaMes(String.valueOf(p.getData()));

            if ( mesPagamento < mesActual ) {
                
                System.out.println("Adiciona");
            }
            else
            {
                int diaPagamento = (DefinicoesData.RetornaDiaData(String.valueOf(p.getData()))-diaActual);
                int rs = ( diaPagamento < 0 )?(diaPagamento*(-1)):diaPagamento;
                if( rs > 1 )
                    System.out.println("Adiciona");
                else
                    System.out.println("Nao Adiciona");
            }
        }
    }
   */

   private void Inicializa_CamposEscola()
   {
       Configuracao c = Configuracao.DadosConfiguracao();
       if( c!= null )
       {
           logo_foto.setImage( new Image("/icones/"+c.getFotografia()));
           txt_nome.setText(c.getNomeescola());
           txt_tipo.setText(c.getTipoescola());
       }
       txt_anolectivo.setText(MesAno.Get_AnoActualCobranca());
   }
   
   public static void Obter_Inicio_Activacao( String inicio )
   {
         inicio_activacao = inicio;
   }
   
   private void Actualiza_Fim_Activacao_User()
   {
      int coduser =  Usuario.NameToCode(Usuario.getUsuario_activo());
      String inicio = inicio_activacao; 
      String fim = DefinicoesData.RetornaHoraSistema();
      
     // Activacao a = new Activacao(coduser, inicio, fim);
      //a.Actualizar_Fim();
      
   }

   
  
}
