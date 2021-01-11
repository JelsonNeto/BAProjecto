/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Usuario;

import Validacoes.validarUsuario;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import modelo.Funcionario;
import modelo.Modelo_privilegio;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class AddUsuarioController implements Initializable {
    @FXML
    private ImageView imagem;
    @FXML
    private PasswordField txt_senha;
    @FXML
    private JFXComboBox<String> cb_tipo;
    @FXML
    private JFXComboBox<String> cb_funcao;
    @FXML
    private JFXTextField txt_bi;
    @FXML
    private JFXTextField txt_nome_usuario;
    @FXML
    private JFXComboBox<String> cb_nome;
    
    
    private Usuario user;
    private Alert alert;
    private String caminho = "/icones/activeUser.png";
    private ObservableList<String> caracters;
  
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        LoadComboBox();
        caracters = FXCollections.observableArrayList();
    }    
    
    
    
    
    @FXML
    public void Adicionar( ActionEvent event )
    {
        Preencher();
    }
    
     @FXML
    private void Selecionafuncao_InicializaNome(ActionEvent event) 
    {
        String funcao = cb_funcao.getSelectionModel().getSelectedItem();
        if( funcao != null )
        {
            cb_nome.setItems(Funcionario.ListaFuncionarios_por_funcao(funcao));
            cb_nome.setDisable(false);
        }
    }
    
    @FXML
    private void SelecionaNome_InicializaCampos(ActionEvent event) 
    {
        String funcao = cb_funcao.getSelectionModel().getSelectedItem();
        String nome = cb_nome.getSelectionModel().getSelectedItem();
        if( nome != null && funcao != null )
        {
            Inicializa_Campos(nome, funcao);
            txt_nome_usuario.setDisable(false);
            txt_senha.setDisable(false);
            cb_tipo.setDisable(false);
        }
    }
    
/****************************************************************************************/
    
    
    private void Preencher(  )
    {
        
        alert  = new Alert(AlertType.WARNING , "Existem campos vazios");
        boolean valor = validarUsuario.EstaoVazios(cb_nome, txt_nome_usuario, txt_senha, cb_tipo);
        if( valor  )
        {
            alert.show();
        }
        
     
        else
        {
            user = new Usuario();            
            //preenchendo os dados do usuario
            user.setCodigo(Usuario.RetornaUltimoCodigoUser());
            user.setNome(cb_nome.getSelectionModel().getSelectedItem());
            user.setSexo("Masculino");
            user.setDataString(Funcionario.code_to_DataNasc(Funcionario.NametoCode(user.getNome())));
            user.setFoto(caminho);
            user.setTipo(cb_tipo.getSelectionModel().getSelectedItem());
            user.setUsername(txt_nome_usuario.getText());
            user.setSenha(txt_senha.getText());
            user.setCodfuncionario(Funcionario.NametoCode(user.getNome()));
            
         
             //inserindo os dados na base
             if(user.Adicionar())
             {
                   //Limpando os campos            
                   txt_nome_usuario.clear();
                   txt_senha.clear();
                   cb_funcao.getSelectionModel().clearSelection();
                   cb_nome.getSelectionModel().clearSelection();
                   cb_tipo.getSelectionModel().clearSelection();
                   ResetImagem();

                   alert.setAlertType(AlertType.INFORMATION);
                   alert.setContentText("Dados inseridos com sucesso!");
                   alert.setTitle("Dados Inseridos");
                   alert.show();
                   
                   if( user.getTipo().equals("Admin")  ) //Preenche os privilegios
                   {
                       Modelo_privilegio mp = new Modelo_privilegio(user.getCodigo(), 2, 2, 2, 2, 2, 2);
                       mp.Adicionar();
                   }
             }
             else
             {
                   alert.setAlertType(AlertType.ERROR);
                   alert.setContentText("Erro ao Inserir dos dados!");
                   alert.setTitle("Erro");
                   alert.show();
             }
            
         
            
        }
        
        
    
    }
    
    
    
    private void LoadComboBox()
    {
        String funcao[] ={"Director geral","Director Pedagógico","Secretário(a)","Contabilista"};
        cb_funcao.setItems( FXCollections.observableArrayList(Arrays.asList(funcao)));
    }
    
    private void Inicializa_Campos( String nome , String funcao)
    {
        txt_bi.setText(Funcionario.code_to_Bi(Funcionario.NametoCode(nome)));
        cb_tipo.setItems(FXCollections.observableArrayList(Arrays.asList((funcao.equals("Director geral") || funcao.equals("Director Pedagógico"))?new String[]{funcao,"Admin"}:new String[]{funcao})));
    }
   
    
    @FXML
    public void OpenImage( ActionEvent event )
    {
        
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(null);
        
        if( file != null )
             caminho ="/icones/"+file.getName();
        imagem.setImage(new Image(caminho));
    }
 
   
    private void ResetImagem()
    {
        
        imagem.setImage( new Image("/icones/activeUser.png"));
        
    }

   

   
    
}
