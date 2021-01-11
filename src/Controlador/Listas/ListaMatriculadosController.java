/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Listas;

import Bd.OperacoesBase;
import Bd.conexao;
import definicoes.DefinicoesData;
import definicoes.DefinicoesImpressaoRelatorio;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Classe;
import modelo.Curso;
import modelo.Estudante;
import modelo.MesAno;
import modelo.Pagamento;
import modelo.Professor;
import modelo.Turma;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class ListaMatriculadosController implements Initializable {
    
    @FXML private TableView<Estudante> tabela;
    @FXML private TableColumn<Estudante, Integer> coluna_codigo;
    @FXML private TableColumn<Estudante, String> coluna_nome;
    @FXML private TableColumn<Estudante, String> coluna_bi;
    @FXML private TableColumn<Estudante, String> coluna_sexo;
    @FXML private TableColumn<Estudante, String> coluna_data;
    @FXML private TableColumn<Estudante, String> coluna_idade;
    @FXML private TableColumn<Estudante, String> coluna_sala;
    @FXML private TableColumn<Estudante, String> coluna_tipo;
    @FXML private ComboBox<String> cb_periodo;
    @FXML private ComboBox<String> cb_turma;
    @FXML private ComboBox<String> cb_classe;
    @FXML private ComboBox<String> cb_curso;
    @FXML private ComboBox<String> cb_ano;
    @FXML private Label txt_total;
    @FXML private Label txt_professor;

    private ObservableList<Estudante> AlunosConfirmados;
    private static String nomeProfessor;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCombos();
        AlunosConfirmados = FXCollections.observableArrayList();
    }    
    
    @FXML
    private void SelecionaCurso_InicializaClasse( ActionEvent event )
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        if( curso != null )
            InicializaClasse(curso);
    }
    
    @FXML
    private void SelecionaClasse_InicializaPeriodo( ActionEvent event )
    {
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        if( classe != null )
            InicializaPeriodo();
    }
    
     @FXML
    private void SelecionaPeriodo_InicializaTurma( ActionEvent event )
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem(); ;
        String classe = cb_classe.getSelectionModel().getSelectedItem();;
        String periodo= cb_periodo.getSelectionModel().getSelectedItem();
        
        if( curso != null && classe != null && periodo != null )
        {
            InicializaTurma(curso, classe, periodo);
        }
        
    }
    
    @FXML
    private void SelecionaTurmaInicializaTabela( ActionEvent event )
    {
        String turma = cb_turma.getSelectionModel().getSelectedItem();
        //Apaga os dados existentes previamente
        OperacoesBase.Eliminar("truncate table listaNominalAlunos");
        if( turma != null )
        {
            String efeito = "Matricula";
            String ano = MesAno.Get_AnoActualCobranca();
            AlunosConfirmados = Pagamento.ListaAlunosPagaramConfirmacaoFiltroPorTurmas(Turma.NameToCode(turma), ano);
            ConfigTabela("select distinct * from aluno inner join turma using(codturma) inner join pagamento using(codaluno) where codturma='"+Turma.NameToCode(turma)+"' and descricao = '"+efeito+"' and ano_referencia = '"+ano+"' order by nome asc");
        }
    }
    
     @FXML
    private void emitirBoletin(ActionEvent event) 
    {
        Alert a = new Alert(AlertType.INFORMATION, "A gerar o relatorio, por favor aguarde...");
        a.show();
        String path = "C:\\RelatorioGenix\\ListaNominalDeAlunos.jrxml";
        DefinicoesImpressaoRelatorio.ImprimirRelatorio(null,path );
     /*   try {
            Desktop.getDesktop().open( new File(file));
        } catch (IOException ex) {
            Logger.getLogger(EmitirBoletinController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
/********************METODOS AUXILIARES****************************************/
    private void InicializaCombos()
    {
       
        String ano[] = {"2019",  "2020", "2021","2022","2023","2024","2025","2026","2027"};
        cb_ano.setItems(FXCollections.observableArrayList(Arrays.asList(ano)));
        
        cb_curso.setItems(Curso.ListaCursos());
    }
    
    private void InicializaPeriodo()
    {
         String periodo[] = {"Manhã", "Tarde"};
         cb_periodo.setItems(FXCollections.observableArrayList(Arrays.asList(periodo)));
    }
    
    private void InicializaClasse( String curso )
    {
        cb_classe.setItems(Classe.ClassesPorCurso(curso));
    }
    
    private void InicializaTurma( String curso , String classe, String periodo )
    {
        cb_turma.setItems(Turma.ListaTurmasRelaClasse_CodCurso_Periodo(classe,Curso.NameToCode(curso), periodo));
    }
 
    private void ConfigTabela( String sql )
    {
        ConfiguraColunas();
        
        ObservableList<Estudante> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
               Estudante e = new Estudante();
               e.setCodigo(rs.getInt("codaluno"));
               e.setNome(rs.getString("nome"));
               e.setSexo(rs.getString("sexo"));
               e.setCedula_bi(rs.getString("bi_cedula"));
               e.setTipo(rs.getString("tipo_Aluno"));
               e.setSala(rs.getInt("codsala"));
               e.setClasse(rs.getString("classe"));
               e.setPeriodo(rs.getString("periodo"));
               e.setCurso(rs.getString("curso"));
               e.setDatanas(DefinicoesData.StringtoLocalDate(rs.getString("datanasc")));
               e.setIdade(DefinicoesData.RetornaIdade(e.getDatanas()));
               e.setProfessor(Professor.CodeToName(rs.getInt("codprofessor")));
               //e.setDatanas(DefinicoesData.StringtoLocalDate(rs.getString("data")));
               nomeProfessor = e.getProfessor();
               lista.add(e);
               InserirListaNominal(e.getNome(), nomeProfessor, e.getClasse(), MesAno.Get_AnoActualCobranca(), cb_turma.getSelectionModel().getSelectedItem(), e.getSala(), String.valueOf(e.getDatanas()), rs.getString("data"),e.getPeriodo(), e.getSexo(), e.getCurso());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ListaMatriculadosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if( AlunosConfirmados.size() > 0 )
        {
            lista.addAll(AlunosConfirmados);
            AdicionaConfirmadosNaListaTabela();
        }
        tabela.setItems(lista);
        txt_total.setText(String.valueOf(tabela.getItems().size()));
        txt_professor.setText(nomeProfessor);
    }
     
    private void ConfiguraColunas()
    {
        
        coluna_bi.setCellValueFactory( new PropertyValueFactory<>("cedula_bi"));
        coluna_data.setCellValueFactory( new PropertyValueFactory<>("datanas"));
        coluna_sala.setCellValueFactory( new PropertyValueFactory<>("sala"));
        coluna_codigo.setCellValueFactory( new PropertyValueFactory<>("codigo"));
        coluna_tipo.setCellValueFactory( new PropertyValueFactory<>("tipo"));
        coluna_nome.setCellValueFactory( new PropertyValueFactory<>("nome"));
        coluna_idade.setCellValueFactory( new PropertyValueFactory<>("idade"));
        coluna_sexo.setCellValueFactory( new PropertyValueFactory<>("sexo"));
    }
    
    
    
    private void InserirListaNominal( String nomeA , String nomeProfessor, String classe, String ano , String turma , int sala, String dataNasc, String dataPagamento, String periodo, String sexo, String curso )
    {
        String sql= "insert into listaNominalAlunos values('"+nomeA+"', '"+nomeProfessor+"', '"+dataPagamento+"', '"+classe+"','"+ano+"', '"+sexo+"','"+sala+"', '"+periodo+"', '"+turma+"','"+dataNasc+"', '"+curso+"')";
        OperacoesBase.Inserir(sql);
    }
   
    private void AdicionaConfirmadosNaListaTabela()
    {
        
        for( Estudante e:  AlunosConfirmados)
        {
            InserirListaNominal(e.getNome(), e.getProfessor(), e.getClasse(), MesAno.Get_AnoActualCobranca(), e.getTurma(), e.getSala(), String.valueOf(e.getDatanas()), String.valueOf(e.getDataPagamento()), e.getPeriodo(), e.getSexo(), e.getCurso());
        }
        
    }
}
