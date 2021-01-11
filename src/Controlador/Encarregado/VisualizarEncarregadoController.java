/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Encarregado;

import Controlador.Encarregado.ActualizarEncarregadoController;
import Bd.OperacoesBase;
import definicoes.DefinicoesData;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Encarregado;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class VisualizarEncarregadoController implements Initializable {
    
     @FXML
    private TableColumn<Encarregado, String> coluna_nomepai;
    @FXML private TableColumn<Encarregado, String> coluna_nomemae;
    @FXML private TableColumn<Encarregado, String> coluna_ocupacao;
    @FXML private TableColumn<Encarregado, String> coluna_endereco;
    @FXML private TableColumn<Encarregado, String> coluna_contato;
    @FXML private TableView<Encarregado> tabela;
    @FXML private Button actualizar;
    @FXML private Button Eliminar;
    @FXML private TextField txt_pesquisa;
    @FXML private ComboBox<String> cb_opcoesPesquisa;

    private static Pane principal;
    @FXML
    private TableColumn<?, ?> coluna_nome1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        configuraTabela("select * from encarregado");
        InicializarPesquisarPor();
        
    }    

    @FXML
    private void actualizar(ActionEvent event)
    {
        Encarregado e = tabela.getSelectionModel().getSelectedItem();
        if( e != null )
        {
            ActualizarEncarregadoController.setE(e);
            AddPane("/vista/ActualizarEncarregado.fxml");
        }
    }

    @FXML
    private void Eliminar(ActionEvent event) 
    {
        Alert a = new Alert(AlertType.CONFIRMATION, "Tem a certeza que deseja eliminar?");
        Optional<ButtonType> tipo = a.showAndWait();
        if( tipo.get() == ButtonType.OK )
        {
            ObservableList<Encarregado> lista = tabela.getSelectionModel().getSelectedItems();
            Eliminar_Encarregado_alunos();
            for( Encarregado e : lista )
            {
                String sql = "delete from encarregado where codencarregado = '"+e.getCodigo()+"' ";
                OperacoesBase.Eliminar(sql);
            }
        }
        configuraTabela("select * from encarregado");
    }


    @FXML
    private void Pesquisar(MouseEvent event) 
    {
        String valor = cb_opcoesPesquisa.getSelectionModel().getSelectedItem();
        if( valor != null )
        {
            configuraTabela(pesquisa());
        }
        else
        {
            Alert a = new Alert(AlertType.ERROR ,"Selecione uma opção" );
            a.show();
        }
    }
   
  /****************************Metodos Auxiliares********************************/
    private void configuraTabela( String sql )
    {
        
        SetColuna();
        ObservableList<Encarregado> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                Encarregado e = new Encarregado();
                e.setCodigo(rs.getInt("codencarregado"));
                e.setNomepai(rs.getString("nomepai"));
                e.setNomemae(rs.getString("nomemae"));
                e.setEndereco(rs.getString("endereco"));
                e.setOcupacao(rs.getString("ocupacao"));
                e.setContacto(rs.getString("contacto"));
                e.setSexo(rs.getString("sexo"));
                e.setDatanasc(DefinicoesData.StringtoLocalDate(rs.getString("datanascimento")));
                
                lista.add(e);
            }
            
            tabela.setItems(lista);
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarEncarregadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void SetColuna()
    {
        coluna_contato.setCellValueFactory( new PropertyValueFactory<>("contacto"));
        coluna_nomepai.setCellValueFactory( new PropertyValueFactory<>("nomepai"));
        coluna_nomemae.setCellValueFactory( new PropertyValueFactory<>("nomemae"));
        coluna_ocupacao.setCellValueFactory( new PropertyValueFactory<>("ocupacao"));
        coluna_endereco.setCellValueFactory( new PropertyValueFactory<>("endereco"));
        
    }
 
    private void InicializarPesquisarPor()
    {
        String valores[] = {"Nome completo" , "Data de Nascimento", "contacto" , "Atualizar"};
        cb_opcoesPesquisa.setItems(FXCollections.observableArrayList(Arrays.asList(valores)));
                
    }
    
    private String pesquisa()
    {
        String valor = cb_opcoesPesquisa.getSelectionModel().getSelectedItem();
        String txt = txt_pesquisa.getText();
        String sql="";
        
        if( "Atualizar".equalsIgnoreCase(valor))
            {
                txt_pesquisa.setText("");
                return "select * from encarregado";
            }
         else
            
        if( valor != null && !"".equalsIgnoreCase(txt) )
        {
            if( "Nome completo".equalsIgnoreCase(valor))
                sql = "select * from encarregado where nome = '"+txt+"'";
            else
                if(  "Data de Nascimento".equalsIgnoreCase(valor))
                    sql = "select * from encarregado where datanascimento = '"+txt+"'";
            else
                    if( "contacto".equalsIgnoreCase(valor) )
                        sql = "select * from encarregado where contacto = '"+txt+"'";
            
            return sql;
        }
          
        return null;
    }
    
     
    private void AddPane( String caminho )
    {
        
        principal.getChildren().removeAll();
        
        Parent fxml = null;
        try {
            fxml = FXMLLoader.load(getClass().getResource(caminho));
        } catch (IOException ex) {
            Logger.getLogger(VisualizarEncarregadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        principal.getChildren().add(fxml);
        
    }
  

    public static void setPrincipal(Pane principal) {
        VisualizarEncarregadoController.principal = principal;
    }
    
    private void Eliminar_Encarregado_alunos()
    {
        String sql = "truncate table encarregado_aluno";
        OperacoesBase.Eliminar(sql);
    }
    
}
