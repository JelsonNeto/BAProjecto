/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Listas;

import Bd.OperacoesBase;
import definicoes.DefinicoesData;
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
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Classe;
import modelo.Curso;
import modelo.Estudante;
import modelo.MesAno;
import modelo.Turma;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class ListaEstudantesController implements Initializable {
    
  
    @FXML
    private TableView<Estudante> tabelaTurma;
    @FXML
    private TableColumn<Estudante, String> coluna_nomeTurma;
    @FXML
    private TableColumn<Estudante, String> coluna_sexoTurma;
    @FXML
    private TableColumn<Estudante, String> coluna_biTurma;
    @FXML
    private TableColumn<Estudante, String> coluna_dataTurma;
    @FXML
    private TableColumn<Estudante, String> coluna_tipoTurma;
    @FXML
    private TableColumn<Estudante, String> coluna_statusTurma;
    @FXML
    private Label txt_totalTurma;
    @FXML
    private ComboBox<String>  cb_turmaTurma;
    @FXML
    private ComboBox<String>  cb_classeTurma;
    @FXML
    private ComboBox<String> cb_cursoTurma;
/****************METODOS AUXLIARES***********************************8*******/
    @FXML private TableView<Estudante> tabelaCurso;
    @FXML private TableColumn<Estudante, String> coluna_nomeCurso;
    @FXML private TableColumn<Estudante, String> coluna_sexoCurso;
    @FXML private TableColumn<Estudante, String> coluna_biCurso;
    @FXML private TableColumn<Estudante, String> coluna_dataCurso;
    @FXML private TableColumn<Estudante, String> coluna_tipoCurso;
    @FXML private TableColumn<Estudante, String> coluna_statusCurso;
    @FXML private Label txt_totalCurso;
    @FXML private ComboBox<String> cb_cursoCurso;
/******************METODOS AUXILIARES*****************************************/
    @FXML private TableView<Estudante> tabelaGeral;
    @FXML private TableColumn<Estudante, String> coluna_nomeGeral;
    @FXML private TableColumn<Estudante, String>coluna_sexoGeral;
    @FXML private TableColumn<Estudante, String>coluna_biGeral;
    @FXML private TableColumn<Estudante, String> coluna_dataGeral;
    @FXML private TableColumn<Estudante, String> coluna_tipoGeral;
    @FXML private TableColumn<Estudante, String>coluna_statusGeral;
    @FXML private Label txt_totalCurso1;

/*********************VARIAVEIS PARA A JANELA MATRICULA ************************/
    @FXML private TableView<Estudante> tabelaMatricula;
    @FXML private TableColumn<Estudante, String> coluna_nomeMatricula;
    @FXML private TableColumn<Estudante, String> coluna_sexoMatricula;
    @FXML private TableColumn<Estudante, String> coluna_biMatricula;
    @FXML private TableColumn<Estudante, String> coluna_dataMatricula;
    @FXML private TableColumn<Estudante, String> coluna_tipoMatricula;
    @FXML private TableColumn<Estudante, String> coluna_statusMatricula;
    @FXML private ComboBox<String> cb_cursoMatricula;
    @FXML private ComboBox<String> cb_classeMatricula;
    @FXML private ComboBox<String> cb_turmaMatricula;
    @FXML private ComboBox<String> cb_anoMatricula;
    @FXML private Label txt_totalMatricula;
    
/**********************VARIAVEIS PARA  A JANELA CONFIRMACAO*********************/
    @FXML private TableColumn<Estudante, String> coluna_nomeConfir;
    @FXML private TableColumn<Estudante, String> coluna_sexoConfir;
    @FXML private TableColumn<Estudante, String>coluna_biConfirm;
    @FXML private TableColumn<Estudante, String> coluna_dataConfirm;
    @FXML private TableColumn<Estudante, String>coluna_tipoConfirm;
    @FXML private TableColumn<Estudante, String>coluna_statusConfirm;
    @FXML private TableView<Estudante> tabelaConfirm;
    @FXML private Label txt_totalConfirm;
    @FXML
    private TableView<Estudante> tabelaDesist;
    @FXML
    private TableColumn<Estudante, String> coluna_nomeDesist;
    @FXML
    private TableColumn<Estudante, String> coluna_sexoDesist;
    @FXML
    private TableColumn<Estudante, String> coluna_biDesist;
    @FXML
    private TableColumn<Estudante, String> coluna_dataDesist;
    @FXML
    private TableColumn<Estudante, String> coluna_tipoDesist;
    @FXML
    private TableColumn<Estudante, String> coluna_statusDesist;
    @FXML
    private ComboBox<String> cb_statusDesistentes;
    @FXML
    private Label txt_totalDesist;
    @FXML
    private ComboBox<String> cb_cursoConfirm;
    @FXML
    private ComboBox<String> cb_classeConfirm;
    @FXML
    private ComboBox<String> cb_turmaConfirm;
    @FXML
    private ComboBox<String> cb_anoConfirm;
    
/******************VARIAVEIS DA LISTA DE ESTUDANTES POR CURSO******************/
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCurso();
        InicializaGeral();
        InicializaMatriculados();
        InicializaTabelaConfir();
    }    
    
    @FXML
    private void SelecionaCurso_InicializaClasse( ActionEvent event )
    {
        String curso = cb_cursoTurma.getSelectionModel().getSelectedItem();
        if( curso != null )
        {
            InicializaClasse(curso);
        }
    }
    
    @FXML
    private void SelecionaClasse_InicializaTurma( ActionEvent event )
    {
        String classe = cb_classeTurma.getSelectionModel().getSelectedItem();
        String curso = cb_cursoTurma.getSelectionModel().getSelectedItem();
        if( classe != null )
        {
            InicializaTurma(curso, classe);
        }
    }
    
    @FXML
    private void SelecionaTurma_InicializaTabela( ActionEvent event )
    {
        String turma = cb_turmaTurma.getSelectionModel().getSelectedItem();
        if( turma != null )
        {
            Setcoluna1();
            String sql = "select * from aluno where codturma = '"+Turma.NameToCode(turma)+"'";
            tabelaTurma.setItems(ConfiguraTabela(sql));
            txt_totalTurma.setText(String.valueOf(tabelaTurma.getItems().size()));
        }
    }
    
    @FXML
    private void SelecionaCursoInicializaTabela( ActionEvent event )
    {
        String curso = cb_cursoCurso.getSelectionModel().getSelectedItem();
        if( curso != null )
        {
            SetColunaCurso();
            String sql = "select * from aluno where curso = '"+curso+"'";
            tabelaCurso.setItems(ConfiguraTabela(sql));
            txt_totalCurso.setText(String.valueOf(tabelaTurma.getItems().size()));
        }
    }
    
    
    
    
 /*****************************METODOS AUXIALIARES DA LISTA DE TURMAS*********************/
    private void InicializaCurso()
    {
        cb_cursoTurma.setItems(Curso.ListaCursos());
        cb_cursoCurso.setItems(Curso.ListaCursos());//comboBox da lista de cursos
        cb_cursoConfirm.setItems(Curso.ListaCursos());
        cb_cursoMatricula.setItems(Curso.ListaCursos());
        cb_statusDesistentes.setItems(FXCollections.observableArrayList(Arrays.asList( new String[]{"Activo", "Desistente", "Finalista"})));
        cb_anoConfirm.setItems(FXCollections.observableArrayList(Arrays.asList( new String[]{"2019", "2020", "2021","2022","2023","2024","2025","2026", "2027","2028","2029", "2030"})));
        cb_anoMatricula.setItems(FXCollections.observableArrayList(Arrays.asList( new String[]{"2019", "2020", "2021","2022","2023","2024","2025","2026", "2027","2028","2029", "2030"})));
    }
    
   
    private void InicializaClasse( String valor )
    {
        cb_classeTurma.setItems(Classe.ClassesPorCurso(valor));
    }
    
    private void InicializaTurma( String curso , String classe )
    {
        cb_turmaTurma.setItems(Turma.ListaTurmasRelaClasse_CodCurso(classe, Curso.NameToCode(curso)));
    }
    private ObservableList<Estudante> ConfiguraTabela( String sql )
    {
        Setcoluna1();
        
        ObservableList<Estudante> lista  = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                Estudante e = new Estudante();
                e.setNome(rs.getString("nome"));
                e.setDatanas(DefinicoesData.StringtoLocalDate(rs.getString("datanasc")));
                e.setTipo(rs.getString("tipo_aluno"));
                e.setCedula_bi(rs.getString("bi_cedula"));
                e.setSexo(rs.getString("sexo"));
                e.setStatus(rs.getString("status"));
                
                lista.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ListaEstudantesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    
    private void Setcoluna1()
    {
        coluna_biTurma.setCellValueFactory( new PropertyValueFactory<>("cedula_bi"));
        coluna_nomeTurma.setCellValueFactory( new PropertyValueFactory<>("nome"));
        coluna_tipoTurma.setCellValueFactory( new PropertyValueFactory<>("tipo"));
        coluna_dataTurma.setCellValueFactory( new PropertyValueFactory<>("datanas"));
        coluna_sexoTurma.setCellValueFactory( new PropertyValueFactory<>("sexo"));
        coluna_statusTurma.setCellValueFactory( new PropertyValueFactory<>("status"));
    }
/*************************METODOS AUXIALIARES DA LISTA DE TURMAS*********************/
    
/******************METODOS AUXILIARES DA LSTA DE CURSOS******************************/
    private void SetColunaCurso()
    {
        coluna_biCurso.setCellValueFactory( new PropertyValueFactory<>("cedula_bi"));
        coluna_nomeCurso.setCellValueFactory( new PropertyValueFactory<>("nome"));
        coluna_tipoCurso.setCellValueFactory( new PropertyValueFactory<>("tipo"));
        coluna_dataCurso.setCellValueFactory( new PropertyValueFactory<>("datanas"));
        coluna_sexoCurso.setCellValueFactory( new PropertyValueFactory<>("sexo"));
        coluna_statusCurso.setCellValueFactory( new PropertyValueFactory<>("status"));
    }
    
     private void SetColunaGeral()
    {
        coluna_biGeral.setCellValueFactory( new PropertyValueFactory<>("cedula_bi"));
        coluna_nomeGeral.setCellValueFactory( new PropertyValueFactory<>("nome"));
        coluna_tipoGeral.setCellValueFactory( new PropertyValueFactory<>("tipo"));
        coluna_dataGeral.setCellValueFactory( new PropertyValueFactory<>("datanas"));
        coluna_sexoGeral.setCellValueFactory( new PropertyValueFactory<>("sexo"));
        coluna_statusGeral.setCellValueFactory( new PropertyValueFactory<>("status"));
    }
/********************MATRICULADOS**********************************************************/
    private void SetColunaMatricula()
    {
        coluna_biMatricula.setCellValueFactory( new PropertyValueFactory<>("cedula_bi"));
        coluna_nomeMatricula.setCellValueFactory( new PropertyValueFactory<>("nome"));
        coluna_tipoMatricula.setCellValueFactory( new PropertyValueFactory<>("tipo"));
        coluna_dataMatricula.setCellValueFactory( new PropertyValueFactory<>("datanas"));
        coluna_sexoMatricula.setCellValueFactory( new PropertyValueFactory<>("sexo"));
        coluna_statusMatricula.setCellValueFactory( new PropertyValueFactory<>("status"));
    }
    
    
    private void SetColunaConfirmado()
    {
        coluna_biConfirm.setCellValueFactory( new PropertyValueFactory<>("cedula_bi"));
        coluna_nomeConfir.setCellValueFactory( new PropertyValueFactory<>("nome"));
        coluna_tipoConfirm.setCellValueFactory( new PropertyValueFactory<>("tipo"));
        coluna_dataConfirm.setCellValueFactory( new PropertyValueFactory<>("datanas"));
        coluna_sexoConfir.setCellValueFactory( new PropertyValueFactory<>("sexo"));
        coluna_statusConfirm.setCellValueFactory( new PropertyValueFactory<>("status"));
    }
    
    private void SetColunaDesistentes()
    {
        coluna_biDesist.setCellValueFactory( new PropertyValueFactory<>("cedula_bi"));
        coluna_nomeDesist.setCellValueFactory( new PropertyValueFactory<>("nome"));
        coluna_tipoDesist.setCellValueFactory( new PropertyValueFactory<>("tipo"));
        coluna_dataDesist.setCellValueFactory( new PropertyValueFactory<>("datanas"));
        coluna_sexoDesist.setCellValueFactory( new PropertyValueFactory<>("sexo"));
        coluna_statusDesist.setCellValueFactory( new PropertyValueFactory<>("status"));
    }
     
    private void InicializaMatriculados()
    {
        SetColunaMatricula();
        String efeito = "Matricula";
        tabelaMatricula.setItems(ConfiguraTabela("select * from aluno inner join pagamento using(codaluno) where descricao = '"+efeito+"' and ano_referencia = '"+MesAno.Get_AnoActualCobranca()+"'"));
        txt_totalMatricula.setText(String.valueOf(tabelaMatricula.getItems().size()));
    }
    
     private void InicializaGeral()
     {
         SetColunaGeral();
         tabelaGeral.setItems(ConfiguraTabela("select * from aluno"));
         txt_totalCurso1.setText(String.valueOf(tabelaGeral.getItems().size()));
     }
     
     
     @FXML
     private void SelecionaCurso_InicializaTabela_E_Turma( ActionEvent event )
     {
         SetColunaMatricula();
         String efeito = "Matricula";
         String curso = cb_cursoMatricula.getSelectionModel().getSelectedItem();
         if( curso != null )
         {
             tabelaMatricula.setItems(ConfiguraTabela("select * from aluno inner join pagamento using(codaluno) where curso = '"+curso+"' and descricao = '"+efeito+"' and ano_referencia = '"+MesAno.Get_AnoActualCobranca()+"'"));
             txt_totalMatricula.setText(String.valueOf(tabelaMatricula.getItems().size()));
             cb_classeMatricula.setItems(Classe.ClassesPorCurso(curso));
         }
     }
     
     
     @FXML
     private void SelecionaClasse_E_InicializaTurmaTabela( ActionEvent event )
     {
         SetColunaMatricula();
         String efeito = "Matricula";
         String classe = cb_classeMatricula.getSelectionModel().getSelectedItem();
         String curso = cb_cursoMatricula.getSelectionModel().getSelectedItem();
         if( classe != null )
         {
             tabelaMatricula.setItems(ConfiguraTabela("select * from aluno inner join pagamento using(codaluno) where curso = '"+curso+"' and classe = '"+classe+"' and descricao = '"+efeito+"' and ano_referencia = '"+MesAno.Get_AnoActualCobranca()+"'"));
             txt_totalMatricula.setText(String.valueOf(tabelaMatricula.getItems().size()));
             cb_turmaMatricula.setItems(Turma.ListaTurmasRelaClasse_CodCurso(classe, Curso.NameToCode(curso)));
         }
     }
     
     @FXML
     private void InicializaTurma_ConfigTabela( ActionEvent event )
     {
         SetColunaMatricula();
         String efeito = "Matricula";
         String turma = cb_turmaMatricula.getSelectionModel().getSelectedItem();
         if( turma != null )
         {
             tabelaMatricula.setItems(ConfiguraTabela("select * from aluno inner join pagamento using(codaluno) where codturma = '"+Turma.NameToCode(turma)+"' and descricao = '"+efeito+"' and ano_referencia = '"+MesAno.Get_AnoActualCobranca()+"'"));
             txt_totalMatricula.setText(String.valueOf(tabelaMatricula.getItems().size()));
         
         }
     }
     
     @FXML
     private void SelecionaAno_InicializaTabela( ActionEvent event )
     {
        SetColunaMatricula();
        String efeito = "Matricula";
        String ano = cb_anoMatricula.getSelectionModel().getSelectedItem();
        if( ano != null )
        {
            tabelaMatricula.setItems(ConfiguraTabela("select * from aluno inner join pagamento using(codaluno) where descricao = '"+efeito+"' and ano_referencia = '"+ano+"'"));
            txt_totalMatricula.setText(String.valueOf(tabelaMatricula.getItems().size()));
        }
        
   
     }
     
     @FXML
     private void SelecionaCurso_InicializaTabela_E_TurmaConf( ActionEvent event )
     {
         SetColunaMatricula();
         String efeito = "Confirmação";
         String curso = cb_cursoConfirm.getSelectionModel().getSelectedItem();
         if( curso != null )
         {
             tabelaConfirm.setItems(ConfiguraTabela("select * from aluno inner join pagamento using(codaluno) where curso = '"+curso+"' and descricao = '"+efeito+"' and ano_referencia = '"+MesAno.Get_AnoActualCobranca()+"'"));
             txt_totalConfirm.setText(String.valueOf(tabelaMatricula.getItems().size()));
             cb_classeConfirm.setItems(Classe.ClassesPorCurso(curso));
         }
     }
     
     
     @FXML
     private void SelecionaClasse_E_InicializaTurmaTabelaConf( ActionEvent event )
     {
         SetColunaMatricula();
         String efeito = "Confirmação";
         String classe = cb_classeConfirm.getSelectionModel().getSelectedItem();
         String curso = cb_cursoConfirm.getSelectionModel().getSelectedItem();
         if( classe != null )
         {
             tabelaConfirm.setItems(ConfiguraTabela("select * from aluno inner join pagamento using(codaluno) where curso = '"+curso+"' and classe = '"+classe+"' and descricao = '"+efeito+"' and ano_referencia = '"+MesAno.Get_AnoActualCobranca()+"'"));
             txt_totalConfirm.setText(String.valueOf(tabelaMatricula.getItems().size()));
             cb_turmaConfirm.setItems(Turma.ListaTurmasRelaClasse_CodCurso(classe, Curso.NameToCode(curso)));
         }
     }
     
     @FXML
     private void InicializaTurma_ConfigTabelaConf( ActionEvent event )
     {
         SetColunaMatricula();
         String efeito = "Confirmação";
         String turma = cb_turmaMatricula.getSelectionModel().getSelectedItem();
         if( turma != null )
         {
             tabelaConfirm.setItems(ConfiguraTabela("select * from aluno inner join pagamento using(codaluno) where codturma = '"+Turma.NameToCode(turma)+"' and descricao = '"+efeito+"' and ano_referencia = '"+MesAno.Get_AnoActualCobranca()+"'"));
             txt_totalConfirm.setText(String.valueOf(tabelaMatricula.getItems().size()));
         
         }
     }
     
     @FXML
     private void SelecionaAno_InicializaTabelaConf( ActionEvent event )
     {
        SetColunaMatricula();
        String efeito = "Confirmação";
        String ano = cb_anoMatricula.getSelectionModel().getSelectedItem();
        if( ano != null )
        {
            tabelaConfirm.setItems(ConfiguraTabela("select * from aluno inner join pagamento using(codaluno) where descricao = '"+efeito+"' and ano_referencia = '"+ano+"'"));
            txt_totalConfirm.setText(String.valueOf(tabelaMatricula.getItems().size()));
        }
        
   
     }
     
 /*************************************************************************************/
     private void InicializaTabelaConfir()
     {
         SetColunaConfirmado();
         String efeito = "Confirmação";
         String ano = "2019";
         tabelaConfirm.setItems(ConfiguraTabela("select * from aluno inner join pagamento using(codaluno) where descricao = '"+efeito+"' and ano_referencia = '"+ano+"'"));
         txt_totalConfirm.setText(String.valueOf(tabelaConfirm.getItems().size()));
     }
     
     

    @FXML
    private void SelecionaStatusInicializaTabela(ActionEvent event) 
    {
        SetColunaDesistentes();
        String valor = cb_statusDesistentes.getSelectionModel().getSelectedItem();
        if( valor != null )
        {
            tabelaDesist.setItems(ConfiguraTabela("select * from aluno where status = '"+valor+"'"));
            txt_totalDesist.setText(String.valueOf(tabelaDesist.getItems().size()));
        }
    }
}
