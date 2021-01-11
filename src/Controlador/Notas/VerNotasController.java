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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Disciplina;
import modelo.Estudante;
import modelo.MesAno;
import modelo.Nota;
import modelo.Professor;
import Controlador.Estudante.PerfilAlunoController;
import modelo.Minipauta_Trimestral;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class VerNotasController implements Initializable {
    
    @FXML private TableColumn<Minipauta_Trimestral, String> coluna_disciplina;
    @FXML private TableColumn<Minipauta_Trimestral, String> coluna_professor;
    @FXML private TableColumn<Minipauta_Trimestral, String> coluna_mac;
    @FXML private TableColumn<Minipauta_Trimestral, String> coluna_cp;
    @FXML private TableColumn<Minipauta_Trimestral, String> coluna_ct;
    @FXML private TableView<Minipauta_Trimestral> tabela;
    @FXML private TextField txt_nome;
    @FXML private TextField txt_curso;
    @FXML private TextField txt_classe;
    @FXML private TextField txt_turno;
    @FXML private ComboBox<String> cb_ano;
    @FXML private ComboBox<String> cb_trimestre;
    @FXML private ComboBox<String> cb_pesquisa;
    @FXML private TextField txt_pesquisa;

    
    private static Estudante e;
    private static String sql ;
    private static Pane pane;
    private static String nomeUser;
    private static String anolectivo;
    private static final String trimestre = "Iº";
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaDadosAlunos();
        Inicializa_Trimestre_Ano();
        CarregaTabela("select * from minipauta_trimestral where codmatricula_c = '"+matricula_confirmacao.CodAlunoToCodMatricula(e.getCodigo())+"' and trimestre = '"+trimestre+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"'");
        SetOpcoes();
    }    

    public static void setE(Estudante e) {
        VerNotasController.e = e;
    }
    
    
     @FXML
    private void SelectOpcao(ActionEvent event)
    {
       if( "Refresh".equals(cb_pesquisa.getSelectionModel().getSelectedItem()) )
           CarregaTabela("select * from minipauta_trimestral where codmatricula_c = '"+matricula_confirmacao.CodAlunoToCodMatricula(e.getCodigo())+"' and trimestre = '"+trimestre+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"'");
    }

    @FXML
    private void Pesquisar(MouseEvent event) 
    {
        if( !txt_pesquisa.getText().isEmpty() )
        {
            int coddisciplina = Disciplina.NameToCode(txt_pesquisa.getText());
            sql = "select * from minipauta_trimestral where codmatricula_c = '"+matricula_confirmacao.CodAlunoToCodMatricula(e.getCodigo())+"' and trimestre = '"+trimestre+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"' and coddisciplina = '"+coddisciplina+"'";
            CarregaTabela(sql);
        }
       
    }
    
    @FXML
    private void SelecionaAno( ActionEvent event )
    {
        String ano = cb_ano.getSelectionModel().getSelectedItem();
        if( ano != null )
        {
            sql = "select * from minipauta_trimestral where codmatricula_c = '"+matricula_confirmacao.CodAlunoToCodMatricula(e.getCodigo())+"' and trimestre = '"+trimestre+"' and anolectivo = '"+ano+"'";
            CarregaTabela(sql);
        }
    }
    
    @FXML
    private void SelecionaTrimestre( ActionEvent event )
    {
        String ano = cb_ano.getSelectionModel().getSelectedItem();
        String tr = cb_trimestre.getSelectionModel().getSelectedItem();
        if( tr != null )
        {
            sql = "select * from minipauta_trimestral where codmatricula_c = '"+matricula_confirmacao.CodAlunoToCodMatricula(e.getCodigo())+"' and trimestre = '"+tr+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"'";
            if( ano != null )
            {
                sql = "select * from minipauta_trimestral where codmatricula_c = '"+matricula_confirmacao.CodAlunoToCodMatricula(e.getCodigo())+"' and trimestre = '"+tr+"' and anolectivo = '"+ano+"'";
            }
            
            CarregaTabela(sql);
        }
    }
    
    private void VerSomatorio(ActionEvent event) 
    {
        SomatorioNotaController.setEstudante(e);
        AddPane("/vista/somatorioNota.fxml");
    }
    
    @FXML
    private void Back( MouseEvent event )
    {
        if( e != null )
        {
            PerfilAlunoController.setEstudante(e);
            PerfilAlunoController.setPane(pane);
            PerfilAlunoController.setNomeUser(nomeUser);
            AddPane("/vista/perfilAluno.fxml");
        }
        
    }
    
/****************************METODOS AUXILIARES*****************************/
 /**
  * Inicializa os combos
  */
    private void InicializaDadosAlunos()
    {
        txt_nome.setText(e.getNome());
        txt_classe.setText(e.getClasse());
        txt_curso.setText(e.getCurso());
        txt_turno.setText(e.getPeriodo());
    }
            
    private void Inicializa_Trimestre_Ano()
    {
        String valor[] = {"Iº", "IIº","IIIº"};
        cb_trimestre.setItems(FXCollections.observableArrayList(Arrays.asList(valor)));
        cb_ano.setItems(matricula_confirmacao.ListaDosAnosMatriculadosAluno(e.getCodigo()));
    }
    
    private void Setcolunas()
    {
         coluna_disciplina.setCellValueFactory( new PropertyValueFactory<>("disciplina"));
       //coluna_professor.setCellValueFactory( new PropertyValueFactory<>("professor"));
         coluna_ct.setCellValueFactory( new PropertyValueFactory<>("ct"));
         coluna_mac.setCellValueFactory( new PropertyValueFactory<>("mac"));
         coluna_cp.setCellValueFactory( new PropertyValueFactory<>("cp"));
        
    }

    public static void setPane(Pane pane) {
        VerNotasController.pane = pane;
    }
    
    
    
    private void CarregaTabela(String sql)
    {
        Setcolunas();
        ResultSet rs = OperacoesBase.Consultar(sql);
        ObservableList<Minipauta_Trimestral> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                Minipauta_Trimestral p  = new Minipauta_Trimestral();
                p.setNome(Estudante.CodeToName(matricula_confirmacao.codmatricula_c_para_codaluno((rs.getInt("codmatricula_c")), anolectivo)));
                p.setDisciplina(Disciplina.CodeToName(rs.getInt("coddisciplina")));
                p.setMac(rs.getString("mac"));
                p.setCp(rs.getString("cp"));
                p.setCt(rs.getString("ct"));
                lista.add(p);
               
            }
            
            tabela.setItems(lista);
        } catch (SQLException ex) {
            Logger.getLogger(VerNotasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void SetOpcoes()
    {
        String valor[] = {"Disciplina" , "Refresh"};
        cb_pesquisa.setItems(FXCollections.observableArrayList(Arrays.asList(valor)));
    }

   public void AddPane( String path )
    {
         
        try {
            Parent p = FXMLLoader.load( getClass().getResource(path) );
            pane.getChildren().removeAll();
            pane.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(VerNotasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setNomeUser(String nomeUser) {
        VerNotasController.nomeUser = nomeUser;
    }

    public static void setAnolectivo(String anolectivo) {
        VerNotasController.anolectivo = anolectivo;
        System.out.println(anolectivo);
    }
    
    
}
