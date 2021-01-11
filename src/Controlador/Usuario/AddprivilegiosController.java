/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Usuario;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import definicoes.DefinicoesGerais;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Funcionario;
import modelo.Modelo_privilegio;
import modelo.Usuario;
import modelo.Visualizar_Privilegio;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class AddprivilegiosController implements Initializable {

    @FXML
    private JFXComboBox<String> cb_nome;
    @FXML
    private JFXComboBox<String> cb_leitura;
    @FXML
    private JFXComboBox<String> cb_insercao;
    @FXML
    private JFXComboBox<String> cb_eliminacao;
    @FXML
    private JFXComboBox<String> cb_actualizacao;
    @FXML
    private JFXComboBox<String> cb_impressao;
    @FXML
    private TableView<Visualizar_Privilegio> tabela;
    @FXML
    private TableColumn<Visualizar_Privilegio, String> colluna_usuario;
    @FXML
    private TableColumn<Visualizar_Privilegio, String> coluna_funcao;
    @FXML
    private TableColumn<Visualizar_Privilegio, String>coluna_leitura;
    @FXML
    private TableColumn<Visualizar_Privilegio, String> coluna_impressao;
    @FXML
    private TableColumn<Visualizar_Privilegio, String>coluna_actualizacao;
    @FXML
    private TableColumn<Visualizar_Privilegio, String> coluna_insercao;
    @FXML
    private TableColumn<Visualizar_Privilegio, String> coluna_eliminacao;
    @FXML
    private JFXTextField txt_bi;
    
    
    private int codfuncionario_var = 0;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicizalizaNomes();
        CarregaTabela();
    }    

   @FXML
    private void SelecionafNome_inicializaCampos(ActionEvent event) 
    {
        String nome = cb_nome.getSelectionModel().getSelectedItem();
        if( nome != null)
        {
            Inicializa_Combos();
            txt_bi.setText(Funcionario.code_to_Bi(Usuario.Obter_CodFuncionario(Usuario.NameToCode(nome))));
        }
    }

    @FXML
    private void Registrar(ActionEvent event) 
    {
        Preencher();
        CarregaTabela();
    }
    
    @FXML
    private void Editar(MouseEvent event) 
    {
       /* Visualizar_Privilegio vp = tabela.getSelectionModel().getSelectedItem();
        if(  vp != null )
        {
            
        }*/
    }
    
    
/*****************************************************************************************************/
    private void Inicializa_Combos()
    {
        String valores[] = {"Não","Parcial","Total"};
        cb_actualizacao.setItems(FXCollections.observableArrayList(Arrays.asList(valores)));
        cb_eliminacao.setItems(FXCollections.observableArrayList(Arrays.asList(valores)));
        cb_impressao.setItems(FXCollections.observableArrayList(Arrays.asList(valores)));
        cb_insercao.setItems(FXCollections.observableArrayList(Arrays.asList(valores)));
        cb_leitura.setItems(FXCollections.observableArrayList(Arrays.asList(valores)));
        
        cb_actualizacao.setDisable(false);
        cb_eliminacao.setDisable(false);
        cb_impressao.setDisable(false);
        cb_leitura.setDisable(false);
        cb_insercao.setDisable(false);
    }
    
    private void Preencher()
    {
        int coduser = Usuario.NameToCode(cb_nome.getSelectionModel().getSelectedItem());
        int leitura = GetValor(cb_leitura.getSelectionModel().getSelectedItem());
        int insercao = GetValor(cb_insercao.getSelectionModel().getSelectedItem());
        int eliminacao = GetValor(cb_eliminacao.getSelectionModel().getSelectedItem());
        int actualizacao = GetValor(cb_actualizacao.getSelectionModel().getSelectedItem());
        int impressao = GetValor(cb_impressao.getSelectionModel().getSelectedItem());
        int cod_admin = Usuario.NameToCode(Usuario.getUsuario_activo());
        
        
        Modelo_privilegio mp = new Modelo_privilegio(coduser, leitura, insercao, eliminacao, impressao, actualizacao, cod_admin);
        if( coduser > 0 || !txt_bi.getText().equals(""))
        {
             if(mp.Adicionar())
            {
                Alert a = new Alert(AlertType.INFORMATION,"Privilégio(s) adicionado(s) com sucesso");
                a.show();
                Limpar();
            }
            else
            {
                Alert a = new Alert(AlertType.ERROR,"Erro ao adicionar os privilégios");
                a.show();
            }
        }
        else
        {
                Alert a = new Alert(AlertType.WARNING,"Existem campos vazios");
                a.show();
        }
       
    }
    
    
    private void Limpar()
    {
        cb_actualizacao.getSelectionModel().clearSelection();
        cb_eliminacao.getSelectionModel().clearSelection();
        cb_impressao.getSelectionModel().clearSelection();
        cb_insercao.getSelectionModel().clearSelection();
        cb_leitura.getSelectionModel().clearSelection();
        cb_nome.getSelectionModel().clearSelection();
    }
    
    private int GetValor( String valor )
    {
        if( valor == null )
            return 0;
        if( "Parcial".equals(valor))
            return 1;
        else
            return 2;
    }
    
    
    private void SetColuna()
    {
        coluna_actualizacao.setCellValueFactory( new PropertyValueFactory<>("actualizacao"));
        coluna_insercao.setCellValueFactory( new PropertyValueFactory<>("isercao"));
        coluna_eliminacao.setCellValueFactory( new PropertyValueFactory<>("eliminacao"));
        coluna_impressao.setCellValueFactory( new PropertyValueFactory<>("impressao"));
        coluna_leitura.setCellValueFactory( new PropertyValueFactory<>("leitura"));
        coluna_funcao.setCellValueFactory( new PropertyValueFactory<>("funcao"));
        colluna_usuario.setCellValueFactory( new PropertyValueFactory<>("nome_func"));
    }
    
    private void CarregaTabela()
    {
        SetColuna();
        tabela.setItems(Visualizar_Privilegio.PreencheTabela());
    }
    
    private void InicizalizaNomes()
    {
        //Remover os que ja tem privilegios
        cb_nome.setItems(Usuario.listaNomesUsers());
        cb_nome.getItems().removeAll(Modelo_privilegio.UsuariosComPrevilegios());
        
    }

  

    
    
}
