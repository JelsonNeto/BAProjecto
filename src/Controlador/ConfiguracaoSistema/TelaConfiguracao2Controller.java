/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.ConfiguracaoSistema;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import definicoes.DefinicoesGerais;
import definicoes.DefinicoesPane;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import modelo.Configuracao;
import modelo.Funcionario;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class TelaConfiguracao2Controller implements Initializable {

    @FXML
    private AnchorPane anchorpane;
    @FXML
    private JFXTextField txt_nif;
    @FXML
    private JFXTextField txt_iban;
    @FXML
    private JFXTextField txt_nome;
    @FXML
    private JFXTextField txt_bi_cedula;
    @FXML
    private JFXRadioButton rb_masculino;
    @FXML
    private JFXRadioButton rb_femenino;
    @FXML
    private JFXDatePicker data_nascimeto;
    @FXML
    private JFXComboBox<String> cb_funcao;
    @FXML
    private JFXTextField txt_username;
    @FXML
    private JFXPasswordField txt_senha;
    @FXML
    private Label lbl_erro_nif;
    @FXML
    private ToggleGroup genero;

    private DefinicoesPane dp;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lbl_erro_nif.setVisible(false);
        Inicializa_Campos();
        dp = new DefinicoesPane();
    }    

    @FXML
    private void fechar(MouseEvent event) {
    }

    @FXML
    private void Adicionar(ActionEvent event) 
    {
        boolean valor = Preencher();
        if( valor )
        {
            Alert a = new Alert(Alert.AlertType.INFORMATION,"Configuracao feita com sucesso");
            a.show();
            dp.CallOtherWindow("login", anchorpane);
        }
    }

    @FXML
    private void Validar(KeyEvent event) {
         
       
    }
 
    
//Metodos auxiliares
     private void Inicializa_Campos()
    {
        String funcao[] ={ "Professor","Limpeza", "Segurança", "Director geral","Director Pedagógico","Secretário(a)","Contabilista"};
        cb_funcao.setItems( FXCollections.observableArrayList(Arrays.asList(funcao)));
        rb_masculino.setSelected(true);
    }
     
     private boolean Preencher()
     {
         //Valida os campos - Vazios
          if( Validacoes.validarUsuario.Vazio_Config2(txt_nif, txt_iban, data_nascimeto, txt_nome, txt_bi_cedula, cb_funcao, txt_username, txt_senha) )
          {
              Alert a = new Alert(Alert.AlertType.INFORMATION,"Existem campos vazios");
              a.show();
          }
          else
          {
              String nif = txt_nif.getText();
              String iban = txt_iban.getText();
              String bi = txt_bi_cedula.getText();
              String nome = txt_nome.getText();
              String user= txt_username.getText();
              String senha = txt_senha.getText();
              LocalDate d = data_nascimeto.getValue();
              String funcao = cb_funcao.getSelectionModel().getSelectedItem();
              
              //Actualizacao do Iban e do nif
              Configuracao c = new Configuracao();
              c.setNif(nif);
              c.setIban(iban);
              boolean v = c.Actualiza_Nif_Iban();
              
              //Adiciona o funcionario
              Funcionario f = new Funcionario();
              f.setAnoAdmissao("1999");
              f.setCodfuncionario(Funcionario.UltimoCodigo());
              f.setBi_cedula(bi);
              f.setData_nascimento(d);
              f.setFuncao(funcao);
              f.setNome(nome);
              f.setSexo(DefinicoesGerais.DevolveSexo(rb_masculino));
              f.setStatus("Activo");
             
            if( v )
            {
                 boolean valor = f.Adicionar();
              
                //Adiciona usuario
                if( valor )
                {
                    Usuario u = new Usuario();
                    u.setCodfuncionario(f.getCodfuncionario());
                    u.setCodigo(Usuario.RetornaUltimoCodigoUser());
                    u.setUsername(user);
                    u.setSenha(senha);
                    u.setFoto("/icones/activeUser.png");
                    u.setTipo("Admin");
                    boolean t = u.Adicionar();
                    
                    if( t )
                    {
                        Limpar();
                        return true;
                    }
                }
                else
                {
                    Alert a = new Alert(Alert.AlertType.ERROR,"Erro ao inserir os dados");
                    a.show();
                }
            }
             
          } //fim do else / VAZIO
          
          return false;
     }
     
     private void Limpar()
     {
         txt_bi_cedula.clear();
         txt_iban.clear();
         txt_nif.clear();
         txt_nome.clear();
         txt_senha.clear();
         txt_username.clear();
         cb_funcao.getSelectionModel().clearSelection();
        data_nascimeto.setValue(null);
        
     }
     
}
