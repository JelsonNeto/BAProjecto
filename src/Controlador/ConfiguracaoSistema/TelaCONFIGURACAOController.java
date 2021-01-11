/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.ConfiguracaoSistema;

import definicoes.DefinicoesPane;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.Configuracao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class TelaCONFIGURACAOController implements Initializable {
    
    @FXML private ComboBox<String> cb_tipo;
    @FXML private TextField txt_nome;
    @FXML private ComboBox<String> cb_nivel;
    @FXML private ImageView imagem;
    @FXML private AnchorPane anchorpane;
    
    private String nomeFoto= "";
    private static Configuracao con;
    @FXML
    private TextField txt_slogan;
    @FXML
    private TextField txt_localizacao;
    @FXML
    private TextField txt_telefone;
    @FXML
    private TextField txt_email;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Inicializa_Tipo();
        InicializaCamposEditar();
    }    

    @FXML
    private void OpenChooser(ActionEvent event) {
        
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(null);
        
        if( file != null )
           nomeFoto = file.getName();
        imagem.setImage( new Image("/icones/"+nomeFoto));
            
    }

    @FXML
    private void Adicionar(ActionEvent event) {
       
        Preencher();
       
    }
    
    @FXML
    private void fechar(MouseEvent event) {
        
       Stage stage =  (Stage)anchorpane.getScene().getWindow();
       stage.close();
        
    }
    
    
/*****************************METODOS AUXILIARES************************************************************/
    
    private void Inicializa_Tipo()
    {
        String valor[] = {"Complexo Escolar", "Colégio Particular"," Escola Comparticipada"};
        String valor2[] = {"ENSINO PRIMÁRIO", "ENSINO PRIMÁRIO , Iº E IIº CICLO DO ENSINO SECUNDÁRIO"};
        cb_tipo.setItems(FXCollections.observableArrayList(Arrays.asList(valor)));
        cb_nivel.setItems(FXCollections.observableArrayList(Arrays.asList(valor2)));
    }
    
     private void Preencher() 
    {
        Alert a;
        String nome = txt_nome.getText();
        String tipo = cb_tipo.getSelectionModel().getSelectedItem();
        String nivel= cb_nivel.getSelectionModel().getSelectedItem();
        String slogan = txt_slogan.getText();
        String localizacao = txt_localizacao.getText();
        String telefone = txt_telefone.getText();
        String email = txt_email.getText();
        
        if( tipo != null && nivel != null && !nome.equalsIgnoreCase("") && !nomeFoto.equalsIgnoreCase("") )
        {
            Configuracao c =new Configuracao();
            c.setFotografia(nomeFoto);
            c.setNomeescola(nome);
            c.setTipoescola(tipo);
            c.setTipo_nivel(nivel);
            c.setEmail(email);
            c.setSlogan(slogan);
            c.setLocalizacao(localizacao);
            c.setTelefone(telefone);
            c.setNome_relatorios(tipo.toUpperCase()+" "+nome.toUpperCase());
            if(c.Adicionar())
            {
                Limpar();
                a = new Alert(Alert.AlertType.INFORMATION, "Dados adicionados com sucesso.");
                a.show();
                ChamaJanela();
            }
        }
        else
        {
            a = new Alert(Alert.AlertType.ERROR,"Existem Campos Vazios");
            a.show();
        }
    }
     
     
   private void Limpar()
    {
        txt_nome.setText("");
        cb_tipo.getSelectionModel().select(null);
        cb_nivel.getSelectionModel().select(null);
        txt_email.clear();
        txt_localizacao.clear();
        txt_slogan.clear();
        txt_telefone.clear();
    }
    
   private void ChamaJanela()
   {
       DefinicoesPane dp = new DefinicoesPane();
       dp.CallOtherWindow("TelaConfiguracao2", anchorpane);
   }

    public static void setCon(Configuracao con) {
        TelaCONFIGURACAOController.con = con;
    }

    private void InicializaCamposEditar() {
        
        if( con != null )
        {
            txt_nome.setText(con.getNomeescola());
            cb_nivel.getSelectionModel().select(con.getTipo_nivel());
            cb_tipo.getSelectionModel().select(con.getTipoescola());
            imagem.setImage( new Image("/icones/"+con.getFotografia()));
            nomeFoto = con.getFotografia();
        }
        
        
    }
  
   
}
