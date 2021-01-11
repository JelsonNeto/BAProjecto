/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Estudante;

import Bd.OperacoesBase;
import definicoes.DefinicoesAdicionarDialogo;
import definicoes.DefinicoesPane;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Classe;
import modelo.Curso;
import modelo.Estudante;
import modelo.MesAno;
import modelo.Turma;
import modelo.matricula_confirmacao;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class RegistroMatriculadoController implements Initializable {
    
    
    @FXML private TableView<Estudante> tabela;
    @FXML private TableColumn<Estudante, Integer> coluna_codigo;
    @FXML private TableColumn<Estudante, String> coluna_nome;
    @FXML private TableColumn<Estudante, String>  coluna_Bi;
    @FXML private TableColumn<Estudante, LocalDate>  coluna_Data;
    @FXML private TableColumn<Estudante, String>  coluna_curso;
    @FXML private TableColumn<Estudante, String>  coluna_periodo;
    @FXML private TableColumn<Estudante, String>  coluna_classe;
    @FXML private TableColumn<Estudante, String>  coluna_turma;
    @FXML private TextField txt_pesquisa;
    @FXML private ComboBox<String> cb_pesquisa;
    @FXML  private ComboBox<String> cb_ano;
    @FXML private Label txt_total;
    @FXML private Pane panePreviuos;
    @FXML private Pane paneNext;
    @FXML private Label txt_totalgeral;
    @FXML private Label txt_filas;
    
    
    private static Pane pane;
    private static Pane panelBack;
    private static String nomeUser;
    private static String tipoUser;
    private static int NumeroFila;
    private static final int indiceFinalParticao=0;
    private static final int var = 30;
    private static int size;
    private static  ObservableList<Estudante> listaEstudanteExportar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaAnos_Pesquisa();
    }    

    @FXML
    private void CallVerPerifil(ActionEvent event) 
    {
        Estudante e = tabela.getSelectionModel().getSelectedItem();
        DefinicoesPane d = new DefinicoesPane("/vista/perfilAluno.fxml",pane);
        if( e != null )
        {   
            PerfilAlunoController.setEstudante(e);
            PerfilAlunoController.setPane(pane);
            PerfilAlunoController.setNomeUser(nomeUser);
            PerfilAlunoController.setAnolectivo(cb_ano.getSelectionModel().getSelectedItem());
            d.AddPane();
        }
    }

    @FXML
    private void Pesquisar(MouseEvent event) 
    {
        Selecionar_Pesquisar();
    }

    @FXML
    private void ExportarExcel(ActionEvent event) {
    }

    @FXML
    private void PreviuosDados(MouseEvent event) {
    }

    @FXML
    private void NextDados(MouseEvent event) {
    }
    
    @FXML
    private void AbirInfo(MouseEvent event) {
        
        DefinicoesAdicionarDialogo d = new DefinicoesAdicionarDialogo();
        d.AddDialogo("/dialogos/adicionarEstudanteAjuda.fxml");
        
    }
    
    
/******************************METODOS OPERACIONAIS**************************************************************/
  
    private void InicializaAnos_Pesquisa()
    {
        cb_ano.setItems(matricula_confirmacao.ListaDosAnosMatriculados());
        String opcoes[] = {"Nome do Aluno","Turma","Curso","Classe"};
        cb_pesquisa.setItems(FXCollections.observableArrayList(Arrays.asList(opcoes)));
    }
    
    private void ConfiguraTabela( String sql )
    {
     
        ConfiguraColunas(); //configura as colunas da tabela
        ResultSet rs = OperacoesBase.Consultar(sql);
        ObservableList<Estudante> lista = FXCollections.observableArrayList();
        listaEstudanteExportar = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                Estudante e = new Estudante();
               
                e.setCedula_bi(rs.getString("bi_cedula"));
                e.setCodigo(rs.getInt("codaluno"));
                e.setNome(rs.getString("nome"));
                e.setSexo(rs.getString("sexo"));
                e.setFoto(rs.getString("fotografia"));
                e.setDatanas(StringToLocalDate(rs.getString("datanasc")));
                e.setTipo(rs.getString("tipo_Aluno"));
                e.setStatus(rs.getString("status"));
                e.setCurso(rs.getString("nome_curso"));
                e.setTurma(rs.getString("nome_turma"));
                e.setPeriodo(rs.getString("periodo"));
                e.setClasse(rs.getString("nome_classe"));
                e.setAnolectivo(MesAno.Get_AnoActualCobranca());
                lista.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarEstudanteMatriculadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        size = lista.size();
        tabela.setItems(lista);
        txt_total.setText(String.valueOf(lista.size()));
        txt_totalgeral.setText(String.valueOf(Estudante.QuantidadeMatriculados(cb_ano.getSelectionModel().getSelectedItem())));
        listaEstudanteExportar = lista;
        Disable_EnablePrevious_Next();
    }
    
    
    
     private void ConfiguraColunas()
    {
        coluna_Bi.setCellValueFactory( new PropertyValueFactory<>("cedula_bi"));
        coluna_Data.setCellValueFactory( new PropertyValueFactory<>("datanas"));
        coluna_codigo.setCellValueFactory( new PropertyValueFactory<>("codigo"));
        coluna_nome.setCellValueFactory( new PropertyValueFactory<>("nome"));
        coluna_curso.setCellValueFactory( new PropertyValueFactory<>("curso"));
        coluna_classe.setCellValueFactory( new PropertyValueFactory<>("classe"));
        coluna_turma.setCellValueFactory( new PropertyValueFactory<>("turma"));
        coluna_periodo.setCellValueFactory( new PropertyValueFactory<>("periodo"));
    }
     
    private LocalDate StringToLocalDate( String data )
    {
        String array_data[] = data.split("-");
        int year =  Integer.parseInt(array_data[0]);
        int month = Integer.parseInt(array_data[1]);
        int day   = Integer.parseInt(array_data[2]);
        
        LocalDate d = LocalDate.of(year, month, day);
        return d;
    }

    public static void setPane(Pane pane) {
        RegistroMatriculadoController.pane = pane;
    }
    
    
     private void Selecionar_Pesquisar()
    {
        String descricao = "Propina";
        String sql = "";
        String valor = cb_pesquisa.getSelectionModel().getSelectedItem();
        if("Nome do Aluno".equalsIgnoreCase(valor))
            sql = "select * from view_matricula where nome like '"+txt_pesquisa.getText()+"%' and descricao != '"+descricao+"' and anolectivo = '"+cb_ano.getSelectionModel().getSelectedItem()+"' order by nome asc limit 40";
        else
            if("Bi ou Cédula".equalsIgnoreCase(valor))
                sql = "select * from view_matricula where bi_cedula = '"+txt_pesquisa.getText()+"' and descricao != '"+descricao+"' and anolectivo = '"+cb_ano.getSelectionModel().getSelectedItem()+"' order by nome asc limit 40";
        else
                if("Curso".equalsIgnoreCase(valor)) 
                    sql = "select * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where anolectivo = '"+cb_ano.getSelectionModel().getSelectedItem()+"' and descricao != '"+descricao+"' and codcurso = '"+Curso.NameToCode(txt_pesquisa.getText())+"' order by nome asc limit 40";
        else
                    if( "Classe".equalsIgnoreCase(valor) )
                        sql = "select * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where anolectivo = '"+cb_ano.getSelectionModel().getSelectedItem()+"' and descricao != '"+descricao+"' and codclasse = '"+Classe.NameToCode(txt_pesquisa.getText())+"' order by nome asc limit 40";
        else
                        if("Turma".equalsIgnoreCase(valor))
                            sql = "select * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where anolectivo = '"+cb_ano.getSelectionModel().getSelectedItem()+"' and descricao != '"+descricao+"' and codturma = '"+Turma.NameToCode(txt_pesquisa.getText())+"' order by nome asc limit 40";
              
        
        if( !"".equalsIgnoreCase(txt_pesquisa.getText()) && !"".equalsIgnoreCase(valor))
        {
            ConfiguraColunas();
            ConfiguraTabela(sql);
        }
    }
     
      private boolean ExportarParaExcel()
    {
        
        XSSFWorkbook  wb = new XSSFWorkbook();//cria a ponte entre o programa e o excel
        XSSFSheet  folha = wb.createSheet();
        XSSFRow cabecalho = folha.createRow(0);
        cabecalho.createCell(0).setCellValue("Codigo do Estudante");
        cabecalho.createCell(1).setCellValue("Nome");
        cabecalho.createCell(2).setCellValue("Sexo");
        cabecalho.createCell(3).setCellValue("Data de Nascimento");
        cabecalho.createCell(6).setCellValue("Tipo de Estudante");
        cabecalho.createCell(7).setCellValue("Status");
        
        int index = 1;
        for( Estudante e : listaEstudanteExportar )
        {
            XSSFRow linha = folha.createRow(index);
            linha.createCell(0).setCellValue(e.getCodigo());
            linha.createCell(1).setCellValue(e.getNome());
            linha.createCell(2).setCellValue(e.getSexo());
            linha.createCell(3).setCellValue(String.valueOf(e.getDatanas()));
            linha.createCell(4).setCellValue(e.getPeriodo());
            linha.createCell(5).setCellValue(e.getClasse());
            linha.createCell(6).setCellValue(e.getTipo());
            linha.createCell(7).setCellValue(e.getStatus());
            
            index++;
        }
        
        try {
            FileOutputStream file = new FileOutputStream("C:\\FicheirosExcel\\ListaAlunos.xlsx");
            wb.write(file);
            file.close();
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VisualizarEstudanteMatriculadoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VisualizarEstudanteMatriculadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void Disable_EnablePrevious_Next()
    {
         if( tabela.getItems().isEmpty() )
         {
             paneNext.setDisable(true);
             panePreviuos.setDisable(true);
         }
         else
         {
             paneNext.setDisable(false);
             panePreviuos.setDisable(false);
         }
    }

    
}
