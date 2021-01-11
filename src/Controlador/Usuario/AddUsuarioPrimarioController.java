/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Usuario;

import Validacoes.validarUsuario;
import definicoes.DefinicoesPane;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class AddUsuarioPrimarioController implements Initializable {
    
    @FXML
    private ImageView imagem;
    @FXML
    private TextField txt_nome;
    @FXML
    private DatePicker datanascimento;
    @FXML
    private TextField txt_username;
    @FXML
    private PasswordField txt_senha;
    @FXML
    private RadioButton masculino;
    @FXML
    private RadioButton femenino;
    @FXML
    private ImageView imagem1;

    
    private String foto_nome = "/icones/activeUser.png";
    private Usuario user;
    private Alert alert;
    @FXML
    private AnchorPane janela_principal;
    @FXML
    private ToggleGroup too;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void OpenImage(ActionEvent event) {
        
        
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(null);
        
        if( file != null )
        {
            foto_nome = "/icones/"+file.getName();
            imagem.setImage( new Image(foto_nome));
        }
        else
            imagem.setImage( new Image(foto_nome));
    }

    @FXML
    private void Adicionar(ActionEvent event) {
        
        if(Preencher())
        {
            DefinicoesPane dp = new DefinicoesPane();
            dp.CallOtherWindow("BemVindo", janela_principal);
        }
    }

    @FXML
    private void AbirInfo(MouseEvent event) {
    }
    
    
 /****************************************METODOS OPERACIONAIS**********************************************/
     private boolean Preencher(  )
    {
        
        alert  = new Alert(Alert.AlertType.WARNING , "Existem campos vazios");
        boolean valor = validarUsuario.EstaoVazios(txt_nome, txt_username, txt_senha,datanascimento);
        if( valor  )
        {
            alert.show();
        }
        
     
        else
        {
            user = new Usuario();
            String sexo = Retorna_Sexo();
           
            
            //preenchendo os dados do usuario
            user.setCodigo(Usuario.RetornaUltimoCodigoUser());
            user.setNome(txt_nome.getText());
            user.setSexo(sexo);
            user.setDatanascimento(datanascimento.getValue());
            
            
            user.setFoto(foto_nome);
            user.setTipo("Admin");
            user.setUsername(txt_username.getText());
            user.setSenha(txt_senha.getText());
            
         
             //inserindo os dados na base
             if(user.Adicionar())
             {
                  alert.setAlertType(Alert.AlertType.INFORMATION);
                  alert.setContentText("Dados inseridos com sucesso!");
                  alert.setTitle("Dados Inseridos");
                  alert.show();
                  Limpar();
                  return true;
             }
             else
             {
                  alert.setAlertType(Alert.AlertType.ERROR);
                  alert.setContentText("Erro ao adicionar o usuario");
                  alert.setTitle("Erro no cadastro");
                  alert.show();
                  return  false;
             }
             
            
          
        }
        
        return false;
    
    }
     
    private String Retorna_Sexo()
    {
        String msg = "Masculino";
        if( masculino.isSelected())
            return msg;
        else
            return  "Femenino";
    }
    
    private void Limpar()
    {
         //Limpando os campos            
          txt_nome.clear();
          txt_senha.clear();
          txt_username.clear();
          ResetImagem();
    }
    
    private void ResetImagem()
    {
        
        imagem.setImage( new Image("/icones/activeUser.png"));
        
    }
}
