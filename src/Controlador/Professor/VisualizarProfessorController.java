/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Professor;

import Controlador.Professor.ActualizarProfessorController;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.MesAno;
import modelo.Professor;
import modelo.RegistroUsuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class VisualizarProfessorController implements Initializable {
    
    @FXML private TextField txt_pesquisa;
    @FXML private ComboBox<String> cb_pesquisa;
    @FXML private TableView<Professor> tabela;
    @FXML private TableColumn<Professor, String> coluna_nome;
    @FXML private TableColumn<Professor, String>  coluna_Bi;
    @FXML private TableColumn<Professor, LocalDate>  coluna_data;
    @FXML private TableColumn<Professor, String>  coluna_sexo;
    @FXML private Button eliminar;
    @FXML private Button actualizar;

    
    private static Pane pane;
    private static Pane paneBack;
    private static int codigo;
    private static String tipoUser;
    private static String nomeUser;
    private static ObservableList<Professor> professorExportar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Desabilita();
        Inicializa_Pesquisar();
        tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }    

    @FXML
    private void Pesquisar(MouseEvent event) 
    {
        Selecionar_Pesquisar();
    }

    @FXML
    private void CallActualizar(ActionEvent event)
    {
        Professor p = tabela.getSelectionModel().getSelectedItem();
        if( p!= null )
        {
            ActualizarProfessorController.setProfessor(p);
            ActualizarProfessorController.setNomeUser(nomeUser);
            AddPane("/vista/actualizarProfessor.fxml");
        }
    }

    @FXML
    private void Eliminar(ActionEvent event) 
    {
         ObservableList<Professor> lp = tabela.getSelectionModel().getSelectedItems();
         if( lp.size() > 0 )
         {
             Eliminar();
         }
    }

    @FXML
    private void CallVerPerifil(ActionEvent event) 
    {
        Professor p = tabela.getSelectionModel().getSelectedItem();
        if( p != null )
        {
            PerfilProfessorController.setProfessor(p);
            PerfilProfessorController.setPane(pane);
            AddPane("/vista/perfilProfessor.fxml");
            
        }
    }
    
    @FXML
    private void CarregarTabela(ActionEvent event) 
    {
        ConfiguraTabela("select * from professor inner join funcionario using(codfuncionario)");
    }

    public static void setPane(Pane pane) {
        VisualizarProfessorController.pane = pane;
    }   
/************8**********8********METODOS OPERACIONAIS************8******8****************************8*******/
 
    
    private void ConfiguraTabela( String sql )
    {
        
        ConfiguraColunas();
        
        ObservableList<Professor> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                Professor p = new Professor();
                p.setCodigo(rs.getInt("codprofessor"));
                p.setNome(rs.getString("nome_professor"));
                p.setBi_cedula(rs.getString("bi_cedula"));
                p.setFoto(rs.getString("fotografia"));
                p.setSexo(rs.getString("sexo"));
                lista.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarProfessorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        professorExportar = lista;
        tabela.setItems(lista);
    }
    
    private void ConfiguraColunas()
    {
        coluna_Bi.setCellValueFactory( new PropertyValueFactory<>("bi_cedula"));
        coluna_nome.setCellValueFactory( new PropertyValueFactory<>("nome"));
        coluna_data.setCellValueFactory( new PropertyValueFactory<>("datanascimento"));
        coluna_sexo.setCellValueFactory( new PropertyValueFactory<>("sexo"));
        
    }
    
    private void Eliminar()
    {
        String nome="";
        ObservableList<Professor> lp = tabela.getSelectionModel().getSelectedItems();
        Alert a = new Alert(AlertType.CONFIRMATION, "Tem a certeza que deseja eliminar?");
        Optional<ButtonType> botao = a.showAndWait();
        //Elimina as relações deste professor
        
        if( botao.get() == ButtonType.OK )
        {
            for( Professor p : lp )
            {
                codigo = p.getCodigo();
                if( Professor.JaAssociadoADisciplina(p.getCodigo(), MesAno.Get_AnoActualCobranca()))
                {
                   a= new Alert(AlertType.INFORMATION,"Professor ja associado a disciplina(s),impossivel eliminar.");
                   a.show();
                }
                else
                {
                    if(!p.eliminar())
                     {
                       a = new Alert(AlertType.ERROR,"Erro ao tentar eliminar o professor");
                       a.show();
                    }
                    else
                    {
                        RegistroUsuario.AddRegistro("Eliminou o professor :"+p.getNome());
                    }
                }
               
            }//fim do for
                 ConfiguraTabela("select * from professor inner join funcionario using(codfuncionario)");
            
        }
        
    }
    
    private void AddPane( String path)
    {    
        try {
            Parent p = FXMLLoader.load( getClass().getResource(path) );
            pane.getChildren().removeAll();
            pane.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(VisualizarProfessorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    private void Inicializa_Pesquisar()
    {
        String valor[] = {"Nome do professor" , "Bi ou Cédula"};
        cb_pesquisa.setItems( FXCollections.observableArrayList(Arrays.asList(valor)));
    }
    
    private void Selecionar_Pesquisar()
    {
        String sql = "select * from professor";
        String valor = cb_pesquisa.getSelectionModel().getSelectedItem();
        if("Nome do professor".equalsIgnoreCase(valor))
            sql = "select * from professor where nome_professor = '"+txt_pesquisa.getText()+"'";
        else
            if("Bi ou Cédula".equalsIgnoreCase(valor))
                sql = "select * from professor where bi_cedula = '"+txt_pesquisa.getText()+"'";
        ConfiguraTabela(sql);
    }

    

    public static void setNomeUser(String nomeUser) {
        VisualizarProfessorController.nomeUser = nomeUser;
    }

    public static void setTipoUser(String tipoUser) {
        VisualizarProfessorController.tipoUser = tipoUser;
    }
    
    private void Desabilita()
    {
        if(!"Admin".equalsIgnoreCase(tipoUser))
        {
            actualizar.setDisable(true);
            eliminar.setDisable(true);
        }
        else
        {
            actualizar.setDisable(false);
            eliminar.setDisable(false);
        }
    }
   
}
