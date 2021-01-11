/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Usuario;

import Bd.OperacoesBase;
import definicoes.DefinicoesData;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class RegistroController implements Initializable {
    
    @FXML private TableView<Usuario> tabela;
    @FXML private TableColumn<Usuario, String> coluna_codigo;
    @FXML private TableColumn<Usuario, String>  coluna_nome;
    @FXML private TableColumn<Usuario, String>  coluna_acao;
    @FXML private TableColumn<Usuario, String> coluna_data;
    @FXML private ImageView imagem;
    @FXML private ComboBox<String> cb_nome;
    @FXML private ComboBox<String> cb_acao;
    @FXML private DatePicker data;
    @FXML private Button eliminar;

    private static String nomeUser;
    private static String tipoUser;
    @FXML
    private Label lbl_inicio_activacao;
    @FXML
    private Label lb_fim_activacao;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        ConfigTabela("select * from registroUser where coduser = '"+Usuario.NameToCode(Usuario.getUsuario_activo())+"'");
        InicializaComboNome();
        tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        InicializaFotografia(nomeUser);
        InicializaAcao();
       // DesabilitaBotao();
    }    
    
    @FXML
    private void SelecionaUsuario_InicializaTabela( ActionEvent event )
    {
        String nome = cb_nome.getSelectionModel().getSelectedItem();
        if( nome != null )
        {
            ConfigTabela("select * from registroUser where coduser = '"+Usuario.NameToCode(nome)+"'");
            InicializaFotografia(nome);
        }
    }
    
    @FXML
    private void SelecionaAcao_InicializaTabela(  ActionEvent event )
    {
        String acao = cb_acao.getSelectionModel().getSelectedItem();
        int coduser = Usuario.NameToCode(cb_nome.getSelectionModel().getSelectedItem());
        if( acao != null )
        {
            ConfigTabela("select * from registroUser where coduser = '"+coduser+"' and acao like '"+retornaLike(acao)+"'");
        }
    }
    @FXML
    private void SelecionaData_InicializaTabela( ActionEvent event )
    {
        LocalDate d = data.getValue();
        if( d != null )
        {
            ConfigTabela("select * from registroUser where data_acao = '"+d+"'");
        }
    }
    
    
/****************************************************************************/
    private void SetColunas()
    {
        coluna_codigo.setCellValueFactory( new PropertyValueFactory<>("codigo"));
        coluna_acao.setCellValueFactory( new PropertyValueFactory<>("acao"));
        coluna_data.setCellValueFactory( new PropertyValueFactory<>("data"));
        coluna_nome.setCellValueFactory( new PropertyValueFactory<>("nome"));
    }
    
    
    private void ConfigTabela( String sql )
    {
        SetColunas();
        ObservableList<Usuario> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        String inicio = "";
        String fim = "";
        try {
            while( rs.next() )
            {
                Usuario e = new Usuario();
                e.setAcao(rs.getString("acao"));
                e.setCoduser(rs.getInt("coduser"));
                e.setNome(Usuario.CodeToName(rs.getInt("coduser")));
                e.setData(DefinicoesData.StringtoLocalDate(rs.getString("data_acao")));
                lista.add(e);
                
   
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tabela.setItems(lista);
        
     }

    public static void setNomeUser(String nomeUser) {
        RegistroController.nomeUser = nomeUser;
    }
    
    private void InicializaComboNome()
    {
        cb_nome.setItems(Usuario.listaNomesUsers());
        cb_nome.getSelectionModel().select(nomeUser);
    }
    
    private void InicializaAcao()
    {
        String valor[] = {"Inserção", "Eliminação","Actualização","Emissão"};
        cb_acao.setItems(FXCollections.observableArrayList(Arrays.asList(valor)));
    }
    
    private String retornaLike( String valor )
    {
        if( "Inserção".equalsIgnoreCase(valor))
            return "Inserção%";
        else
            if( "Eliminação".equalsIgnoreCase(valor) )
                return "Eliminação%";
        else
                if( "Actualização".equalsIgnoreCase(valor) )
                    return "Actualização%";
        else
                    if( "Emissão".equalsIgnoreCase(valor) )
                        return "Emitiu%";
        return null;
                        
    }

    private void InicializaFotografia( String nome )
    {
        String foto = Usuario.CodeToFotografia(Usuario.NameToCode(nome));
        if( !"".equalsIgnoreCase(foto))
           imagem.setImage( new Image(foto));
        else
           imagem.setImage( new Image("/icones/activeUser.png"));
    }

    @FXML
    private void Eliminar(ActionEvent event) 
    {
        ObservableList<Usuario> lista = tabela.getSelectionModel().getSelectedItems();
        int coduser = Usuario.NameToCode(cb_nome.getSelectionModel().getSelectedItem());
        Alert a = new Alert(AlertType.CONFIRMATION, "Tem a certeza que deseja eliminar?");
        Optional<ButtonType> acao = a.showAndWait();
        if( ButtonType.OK == acao.get() )
        {
            for( Usuario u : lista )
                u.Eliminar();
            ConfigTabela("select * from registroUser where coduser ='"+coduser+"'");
        }
    }

    public static void setTipoUser(String tipoUser) {
        RegistroController.tipoUser = tipoUser;
    }
    
    private void DesabilitaBotao()
    {
        if(!"Admin".equalsIgnoreCase(tipoUser))
            eliminar.setDisable(true);
        else
            eliminar.setDisable(false);
    }
   
}
