/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Usuario;

import Bd.OperacoesBase;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class VisualizarUsuario2Controller implements Initializable {
    @FXML
    private TableView< Usuario > tabela;
    @FXML
    private TableColumn<Usuario,String> coluna_nome;
    @FXML
    private TableColumn<Usuario, String> coluna_sexo;
    @FXML
    private TableColumn<Usuario, LocalDate> coluna_datanas;
    @FXML
    private TableColumn<Usuario, String> coluna_tipo;
    @FXML
    private TableColumn<Usuario, String> coluna_foto;
    @FXML
    private TableColumn<Usuario, String> coluna_username;
    @FXML
    private TextField txt_pesquisar;
    @FXML
    private ComboBox<String> cb_pesquisar;

    
    private static String nomeUser;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        CarregarTabela("select * from Usuario inner join funcionario using(codfuncionario)");
        Inicializa_Pesquisar();
        tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }    
    
    
    @FXML
    public void PesquisarPor( MouseEvent event )
    {
        Selecao();
    }
    
    
    
    
    private void CarregarTabela( String sql )
    {
        ConfigurarColunas();
        ObservableList<Usuario> lista =  FXCollections.observableArrayList();
        
        try {
            ResultSet rs = OperacoesBase.Consultar(sql);
            while( rs.next() )
            {
                Usuario user = new Usuario();
                
               StringtoLocalDate(rs.getString("data_nascimento"));
               user.setCodigo(rs.getInt("coduser"));
               user.setDatanascimento(StringtoLocalDate(rs.getString("data_nascimento")));
               user.setFoto(rs.getString("foto"));
               user.setNome(rs.getString("nome"));
               user.setSenha(rs.getString("senha"));
               user.setSexo(rs.getString("sexo"));
               user.setTipo(rs.getString("tipo"));
               user.setUsername(rs.getString("username"));
          
               lista.add(user);
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarUsuario2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tabela.setItems(lista);
        
    }
    
    
    private void ConfigurarColunas()
    {
        
        //configuracao das colunas
        coluna_nome.setCellValueFactory(    new PropertyValueFactory<>("nome"));
        coluna_sexo.setCellValueFactory(    new PropertyValueFactory<>("sexo"));
        coluna_foto.setCellValueFactory(    new PropertyValueFactory<>("foto"));
        coluna_datanas.setCellValueFactory( new PropertyValueFactory<>("datanascimento"));
        coluna_tipo.setCellValueFactory(    new PropertyValueFactory<>("tipo"));
        coluna_username.setCellValueFactory( new PropertyValueFactory<>("username"));
        
    }
    
    private  LocalDate StringtoLocalDate( String date )
    {
        String dados[] = date.split("-");
        int ano = Integer.parseInt(dados[0]);
        int dia = Integer.parseInt(dados[2]);
        int mes = Integer.parseInt(dados[1]);
        
        return   LocalDate.of(ano, mes, dia);
        
    }
    
    @FXML
    private void Eliminar()
    {
        
        ObservableList<Usuario> lista = tabela.getSelectionModel().getSelectedItems();
        Alert a = new Alert(AlertType.ERROR, "Impossivel Eliminar este Usuario");
        
        for( Usuario user : lista )
        {
            if( user.getNome().equalsIgnoreCase(nomeUser) )
                  a.show();
           else
                OperacoesBase.Inserir("delete from usuario where coduser = '"+user.getCodigo()+"'");
        }
        
        CarregarTabela("select * from Usuario");
    }
    
    
    private void Inicializa_Pesquisar()
    {
        String valor[] = {"Nome completo","Username"};
        cb_pesquisar.setItems(FXCollections.observableArrayList(Arrays.asList(valor)));
    }
    
    
    private void Selecao()
    {
        String sql = "";
        String valor = cb_pesquisar.getSelectionModel().getSelectedItem();
        if("Nome completo".equalsIgnoreCase(valor))
            sql = "select * from usuario where nome = '"+txt_pesquisar.getText()+"'";
        else
            if("Username".equalsIgnoreCase(valor))
                sql = "select * from usuario where username = '"+txt_pesquisar.getText()+"'";
        
        if( !"".equalsIgnoreCase(sql) && !"".equalsIgnoreCase(valor))
        {
            ConfigurarColunas();
            CarregarTabela(sql);
        }
    }

    public static void setNomeUser(String nomeUser) {
        VisualizarUsuario2Controller.nomeUser = nomeUser;
    }
    
    
    
}
