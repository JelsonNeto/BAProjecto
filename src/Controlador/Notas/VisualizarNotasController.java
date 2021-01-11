/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Notas;

import Bd.OperacoesBase;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import modelo.Classe;
import modelo.Curso;
import modelo.Disciplina;
import modelo.Estudante;
import modelo.MesAno;
import modelo.Nota;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class VisualizarNotasController implements Initializable {
   
    @FXML private TableColumn<Nota, String> coluna_aluno;
    @FXML private TableColumn<Nota, String> coluna_curso;
    @FXML private TableColumn<Nota, String> coluna_classe;
    @FXML private TableColumn<Nota, String> coluna_disciplina;
    @FXML private TableColumn<Nota, String> coluna_valor;
    @FXML private TableColumn<Nota, String> coluna_descricao; 
    @FXML private TableColumn<Nota, String> coluna_ano;
    @FXML private TableView<Nota> tabela;
    @FXML private ComboBox<String> cb_pesquisa;
    @FXML private TextField txt_pesquisa;
    @FXML private Button eliminar;
    @FXML private Button actualizar;

    private static String sql = "select * from nota";
    private static Pane pane;
    private static String nomeUser;
    private static String tipoUser;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        Inicializa_Pesquisa();
        Desabilita();
        tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }    

    @FXML
    private void CallActualizar(ActionEvent event) {
    }

    @FXML
    private void Eliminar(ActionEvent event) 
    {
        Eliminar();
    }

    @FXML
    private void SelectOpcao( ActionEvent event )
    {
        String opcao = cb_pesquisa.getSelectionModel().getSelectedItem();
        if( opcao != null )
        {
             
             if("Avaliação".equalsIgnoreCase(opcao))
               {
                     txt_pesquisa.setDisable(true);
                      String descricao = "Avaliação";
                      txt_pesquisa.setText(descricao);
                      sql = "select * from nota where descricao = '"+descricao+"'";
                }
             else
                  if( "Prova do Professor".equalsIgnoreCase(opcao) )
                    {
                        txt_pesquisa.setDisable(true);
                        String descricao = "Prova do Professor";
                        txt_pesquisa.setText(descricao);
                        sql = "select * from nota where descricao = '"+descricao+"'";
                    }
             else
                 if( "Prova da escola".equalsIgnoreCase(opcao))
                   {
                        txt_pesquisa.setDisable(true);
                        String descricao = "Prova da escola";
                        txt_pesquisa.setText(descricao);
                        sql = "select * from nota where descricao = '"+descricao+"'";
                    } 
             else
                 {
                     
                     txt_pesquisa.setDisable(false);
                     txt_pesquisa.setText("");
                 }
        }
        
    }
    
    
    @FXML
    private void Pesquisar(MouseEvent event) 
    {
        String txt = txt_pesquisa.getText();
        if( !"".equalsIgnoreCase(txt) )
        {
            SetSearch();
            ConfiguraTabela(sql);
        }
    }

     @FXML
    private void CaregarTabela(ActionEvent event) 
    {
        ConfiguraTabela("select * from nota inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where anolectivo = '"+MesAno.Get_AnoActualCobranca()+"'");
    }


/***************************METODOS AUXILIARES*******************************/
    private void ConfiguraTabela(String sql)
    {
        setColunas();
        ResultSet rs = OperacoesBase.Consultar(sql);
        ObservableList<Nota> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                Nota n = new Nota();
                n.setCodigo(rs.getInt("codnota"));
                n.setNome_aluno(Estudante.CodeToName(rs.getInt("codaluno")));
                n.setAno_lectivo(rs.getString("anolectivo"));
                n.setCurso(Curso.NameAlunoNameCurso(n.getNome_aluno()));
                n.setClasse(Classe.NameAlunoToClasse(n.getNome_aluno()));
                n.setDescricao(rs.getString("descricao"));
                n.setDisciplina( Disciplina.CodeToName(rs.getInt("coddisciplina")) );
                n.setValor(rs.getString("valor"));
                
                lista.add(n);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarNotasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        tabela.setItems(lista);
    }
    
    private void setColunas()
    {
        coluna_aluno.setCellValueFactory( new PropertyValueFactory<>("nome_aluno"));
        coluna_ano.setCellValueFactory( new PropertyValueFactory<>("ano_lectivo"));
        coluna_classe.setCellValueFactory( new PropertyValueFactory<>("classe"));
        coluna_curso.setCellValueFactory( new PropertyValueFactory<>("curso"));
        coluna_descricao.setCellValueFactory( new PropertyValueFactory<>("descricao"));
        coluna_disciplina.setCellValueFactory( new PropertyValueFactory<>("disciplina"));
        coluna_valor.setCellValueFactory( new PropertyValueFactory<>("valor"));
    }
    
    
    private void Eliminar()
    {
        
        ObservableList<Nota> lista = tabela.getSelectionModel().getSelectedItems();
        if( lista.size() > 0 )
        {
            
            Alert a = new Alert(AlertType.CONFIRMATION, "Tem a certeza que deseja eliminar?");
            Optional<ButtonType> opcoes = a.showAndWait();
            if( opcoes.get() == ButtonType.OK )
            {
                for( Nota n : lista )
                {
                    String sql_var = "delete from nota where codnota = '"+n.getCodigo()+"'";
                    OperacoesBase.Eliminar(sql_var);
                }
                ConfiguraTabela( "select * from nota" );
                
            }
        }
        else
        {
            Alert a = new Alert(AlertType.ERROR , "Nenhuma Linha selecionada.");
            a.show();
        }
        
    }
    
    private void Inicializa_Pesquisa()
    {
        String array_valor[] = {"Nome do Aluno" , "Classe" , "Curso", "Ano Lectivo", "Trimestre","Avaliação","Prova do Professor", "Prova da escola"};
        cb_pesquisa.setItems(FXCollections.observableArrayList(Arrays.asList(array_valor)));
    }
    
    private void SetSearch()
    {
        String opcao = cb_pesquisa.getSelectionModel().getSelectedItem();
        
        if( "Nome do Aluno".equalsIgnoreCase(opcao) )
        {
            String valor = txt_pesquisa.getText();
            sql = "select * from nota where codaluno = '"+Estudante.NameToCode(valor)+"' ";
        }
        else if( "Classe".equalsIgnoreCase(opcao) )
        {
            String valor = txt_pesquisa.getText();
            sql  = "select * from nota inner join aluno using(codaluno) where classe = '"+valor+"'";
        }
        else
            if( "Curso".equalsIgnoreCase(opcao) )
            {
                String valor = txt_pesquisa.getText();
                sql = "select * from nota inner join aluno using(codaluno) where curso = '"+valor+"'";
            }
        else
                if(  "Ano Lectivo".equalsIgnoreCase(opcao))
                {
                    String valor = txt_pesquisa.getText();
                    sql= "select * from nota where ano_lectivo = '"+valor+"'";
                }
        else
                    if( "Trimestre".equalsIgnoreCase(opcao) )
                    {
                        String valor = txt_pesquisa.getText();
                        sql = "select * from nota where trimestre = '"+valor+"'";
                    }
       
    }

    public void AddPane( String path )
    {
         
        try {
            Parent p = FXMLLoader.load( getClass().getResource(path) );
            pane.getChildren().removeAll();
            pane.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(VisualizarNotasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setPane(Pane pane) {
        VisualizarNotasController.pane = pane;
    }

   
    public static void setNomeUser(String nomeUser) {
        VisualizarNotasController.nomeUser = nomeUser;
    }

    public static void setTipoUser(String tipoUser) {
        VisualizarNotasController.tipoUser = tipoUser;
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
