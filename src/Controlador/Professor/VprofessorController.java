/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Professor;

import Bd.OperacoesBase;
import Controlador.Menus.Professor_HomeController;
import definicoes.DefinicoesCores;
import definicoes.DefinicoesPane;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.MesAno;
import modelo.Professor;
import modelo.RegistroUsuario;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class VprofessorController implements Initializable {

    private ComboBox<String> cb_pesquisa;
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
    private static ObservableList<Professor> professorExportar;
    private DefinicoesPane dp;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dp = new DefinicoesPane(paneBack);
    }    

    @FXML
    private void CallActualizar(ActionEvent event) 
    {
        Professor professor = tabela.getSelectionModel().getSelectedItem();
        if( professor != null )
        {
            DefinicoesCores.Underline(Professor_HomeController.getLabel_adicionar(), true);
            DefinicoesCores.Underline(Professor_HomeController.getLabel_visualizar(), false);
            AddProfessorController.setActualizar(true);
            AddProfessorController.setProofessor(professor);
            //Muda os nomes dos campos e botao
            Professor_HomeController.getLabel_adicionar().setText("Actualizar");
            dp.setPath("/vista/addProfessor.fxml");
            dp.AddPane();
        }
        
         
    }

    @FXML
    private void Eliminar(ActionEvent event) {
        
        Professor p = tabela.getSelectionModel().getSelectedItem();
        if(  p != null)
        {
            eliminar.setDisable(false);
            Eliminar();
        }
        else
            eliminar.setDisable(true);
        
        
    }

    @FXML
    private void CallVerPerifil(ActionEvent event) {
    }

    @FXML
    private void CarregarTabela(ActionEvent event) 
    {
        ConfiguraTabela("select * from professor inner join funcionario using(codfuncionario)");
    }
    
     @FXML
    private void Editar(MouseEvent event) 
    {
        Professor p = tabela.getSelectionModel().getSelectedItem();
        if( p != null )
        {
            eliminar.setDisable(false);
            actualizar.setDisable(false);
        }
        else
        {
            eliminar.setDisable(true);
            actualizar.setDisable(true);
        }
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
                p.setDatanascimento(rs.getString("data_nascimento"));
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
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Tem a certeza que deseja eliminar?");
        Optional<ButtonType> botao = a.showAndWait();
        //Elimina as relações deste professor
        
        if( botao.get() == ButtonType.OK )
        {
            for( Professor p : lp )
            {
                codigo = p.getCodigo();
                if( Professor.JaAssociadoADisciplina(p.getCodigo(), MesAno.Get_AnoActualCobranca()))
                {
                   a= new Alert(Alert.AlertType.INFORMATION,"Professor ja associado a disciplina(s),impossivel eliminar.");
                   a.show();
                }
                else
                {
                    if(!p.eliminar())
                     {
                       a = new Alert(Alert.AlertType.ERROR,"Erro ao tentar eliminar o professor");
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
    
    /*private void Selecionar_Pesquisar()
    {
        String sql = "select * from professor";
        String valor = cb_pesquisa.getSelectionModel().getSelectedItem();
        if("Nome do professor".equalsIgnoreCase(valor))
            sql = "select * from professor where nome_professor = '"+txt_pesquisa.getText()+"'";
        else
            if("Bi ou Cédula".equalsIgnoreCase(valor))
                sql = "select * from professor where bi_cedula = '"+txt_pesquisa.getText()+"'";
        ConfiguraTabela(sql);
    }*/

    

  
    
    private void Desabilita()
    {
        if(Usuario.EAdmin(Usuario.NameToCode(Usuario.getUsuario_activo())))
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

    public static void setPaneBack(Pane paneBack) {
        VprofessorController.paneBack = paneBack;
    }

   
}
