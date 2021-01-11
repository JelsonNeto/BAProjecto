/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Estudante;

import Bd.OperacoesBase;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Estudante;
import modelo.RegistroUsuario;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Controlador.Home.HomeController;
import Controlador.Notas.VerNotasController;
import definicoes.DefinicoesAdicionarDialogo;
import definicoes.DefinicoesGerais;
import definicoes.DefinicoesImpressaoRelatorio;
import java.util.HashMap;
import javafx.scene.image.ImageView;
import modelo.Classe;
import modelo.Curso;
import modelo.MesAno;
import modelo.Turma;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class VisualizarEstudanteMatriculadoController implements Initializable {
  
    @FXML private TableView<Estudante> tabela;
    @FXML private TableColumn<Estudante, Integer> coluna_codigo;
    @FXML private TableColumn<Estudante, String> coluna_nome;
    @FXML private TableColumn<Estudante, String> coluna_Bi;
    @FXML private TableColumn<Estudante, LocalDate> coluna_Data;
    @FXML private TextField txt_pesquisa;
    @FXML private ComboBox<String> cb_pesquisa;
    @FXML private Label txt_total;
    @FXML private Label txt_totalgeral;
    @FXML private Pane panePreviuos;
    @FXML private Pane paneNext;
    @FXML  private Label txt_filas;
    @FXML private Label txt_anolectivo;
    @FXML private TableColumn<Estudante, String> coluna_curso;
    @FXML private TableColumn<Estudante, String> coluna_periodo;
    @FXML private TableColumn<Estudante, String> coluna_classe;
    @FXML private TableColumn<Estudante, String> coluna_turma;
    @FXML private Button btn_imprimir;
    @FXML private ImageView imagem1;
    private Button actualizar;
    private Button eliminar;
   
    private static Pane pane;
    private static Pane panelBack;
    private static String nomeUser;
    private static String tipoUser;
    private static int NumeroFila;
    private static int indiceFinalParticao=0;
    private static final int var = 30;
    private static int size;
    private static  ObservableList<Estudante> listaEstudanteExportar;
   
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
       // VerificaUsuarioDisableBotoes();
        Disable_EnablePrevious_Next();
        Inicializa_Pesquisar();
        tabela.setDisable(true);
        tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        btn_imprimir.setDisable(true);
    }    
    
    
    private void InicializaTabela()
    {
        String descricao = "Propina";
        ConfiguraTabela("select * from view_matricula where anolectivo = '"+MesAno.Get_AnoActualCobranca()+"' and descricao != '"+descricao+"' order by nome asc limit 40");
        btn_imprimir.setDisable(true);
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
                e.setCodturma(Turma.NameToCode(e.getTurma()));
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
        txt_totalgeral.setText(String.valueOf(Estudante.QuantidadeMatriculados(MesAno.Get_AnoActualCobranca())));
        listaEstudanteExportar = lista;
        txt_anolectivo.setText(String.valueOf(MesAno.Get_AnoActualCobranca()));
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
        VisualizarEstudanteMatriculadoController.pane = pane;
    }
    
    public void CallActualizar( ActionEvent event )
    {
        Estudante e  = tabela.getSelectionModel().getSelectedItem();
        if( e != null )
        {
            ActualizarEstudanteController.setEstudante(e);
            ActualizarEstudanteController.setNomeUser(nomeUser);
            ActualizarEstudanteController.setPane(pane);
            AddPane("/vista/actualizarEstudante.fxml");
        }
    }
    
    @FXML
    public void CallVerPerifil( ActionEvent event )
    {
        Estudante e = tabela.getSelectionModel().getSelectedItem();
        if( e != null )
        {   
            PerfilAlunoController.setEstudante(e);
            PerfilAlunoController.setPane(pane);
            PerfilAlunoController.setNomeUser(nomeUser);
            PerfilAlunoController.setAnolectivo(null);
            AddPane("/vista/perfilAluno.fxml");
        }
    }
    
     @FXML
    private void imprimir(ActionEvent event) 
    {
       
        Alert a = new Alert(AlertType.INFORMATION, "A gerar a lista de matriculados, por favor aguarde...");
        a.show();
        int codturma = Turma.NameToCode(txt_pesquisa.getText());
        String anolectivo = MesAno.Get_AnoActualCobranca();
        String path ="C:\\RelatorioGenix\\ListaNominalDeAlunos.jrxml";
        HashMap hash = new HashMap();
        hash.put("codturma", codturma);
        hash.put("anolectivo",anolectivo);
        boolean valor = DefinicoesImpressaoRelatorio.ImprimirRelatorio(hash, path);
       if( valor )
       {
           a.setContentText("Lista gerada com sucesso.");
           a.show();
       }
       else
       {
           a.setContentText("Erro ao gerar a lista.");
           a.setAlertType(AlertType.ERROR);
           a.show();
       }
    }

    
    @FXML
    private void Pesquisar( MouseEvent event )
    {
         
        Selecionar_Pesquisar();
        Disabilita_Imprimir(txt_pesquisa.getText(), tabela);
    }
    
    private  void AddPane( String path )
    {
         
        try {
            Parent p = FXMLLoader.load( getClass().getResource(path) );
            pane.getChildren().removeAll();
            pane.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(VisualizarEstudanteMatriculadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private void AdicionaPainelaBack( String caminho )
   {   //remove todos os paineis que estao associados ao painel principal
       panelBack.getChildren().removeAll();
       
        try {
            //adiciona o painel filho ao principa
            Parent fxml = FXMLLoader.load(getClass().getResource(caminho));
            panelBack.getChildren().add(fxml);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
    
    
    public void Eliminar( ActionEvent event )
    {
       ObservableList <Estudante> lista = tabela.getSelectionModel().getSelectedItems();
       matricula_confirmacao m  = new matricula_confirmacao();
       Alert a = new Alert(AlertType.INFORMATION, "Tem a certeza que deseja eliminar?");
       Optional<ButtonType> bt = a.showAndWait();
      
       if( bt.get() == ButtonType.OK )
        {
            for( Estudante e : lista )
              {
                  //Se o aluno ainda não estiver matriculado, então pode ser eliminado( O aluno é considerado matriculado quando este fizera o pagamento da matricula do correspondente ano )
                 if( !matricula_confirmacao.JaMatriculado(e.getCodigo(), MesAno.Get_AnoActualCobranca()))
                 {
                     
                     m.setCodaluno(e.getCodigo());
                     m.setAnolectivo(MesAno.Get_AnoActualCobranca());
                     m.Eliminar();
                     if(!e.Eliminar())
                         AddDialogo("/dialogos/erroEliminarGeral.fxml");
                     else
                         RegistroUsuario.AddRegistro("Eliminação do aluno :"+e.getNome());
                        
                 }
                 else
                 {
                     a.setContentText("Aluno ja matriculado, impossivel eliminar");
                     a.show();
                 }
                 
                 
              }
              InicializaTabela();
       }
    }
    @FXML
    private void AbirInfo(MouseEvent event) {
        
        DefinicoesAdicionarDialogo d = new DefinicoesAdicionarDialogo();
        d.AddDialogo("/dialogos/visualizarMatricula.fxml");
        
    }
/*******************************METODOS OPERACIONAIS************************************************/
    private void Back( MouseEvent event )
    {
        AdicionaPainelaBack("/vista/estudante.fxml");
    }

    public static void setTipoUser(String tipoUser) {
        VisualizarEstudanteMatriculadoController.tipoUser = tipoUser;
    }
    
    
    private void Inicializa_Pesquisar()
    {
        String valor[] = {"Nome Aluno" , "Bi ou Cédula", "Curso", "Classe", "Turma"};
        cb_pesquisa.setItems( FXCollections.observableArrayList(Arrays.asList(valor)));
    }
    
    private void Selecionar_Pesquisar()
    {
        String sql = "";
        String descricao = "Matricula";
        String valor = cb_pesquisa.getSelectionModel().getSelectedItem();
        if("Nome Aluno".equalsIgnoreCase(valor))
            sql = "select distinct * from view_matricula where descricao ='"+descricao+"' and nome like '"+txt_pesquisa.getText()+"%' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"' order by nome asc limit 40";
        else
            if("Bi ou Cédula".equalsIgnoreCase(valor))
                sql = "select distinct * from view_matricula where bi_cedula like '"+txt_pesquisa.getText()+"%' and descricao = '"+descricao+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"' order by nome asc limit 40";
        else
                if("Curso".equalsIgnoreCase(valor)) 
                    sql = "select distinct * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where anolectivo = '"+MesAno.Get_AnoActualCobranca()+"' and descricao = '"+descricao+"' and codcurso = '"+Curso.NameToCode(txt_pesquisa.getText())+"' order by nome asc limit 40";
        else
                    if( "Classe".equalsIgnoreCase(valor) )
                        sql = "select distinct * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where anolectivo = '"+MesAno.Get_AnoActualCobranca()+"' and descricao = '"+descricao+"' and codclasse = '"+Classe.NameToCode(txt_pesquisa.getText())+"' order by nome asc limit 40";
        else
                        if("Turma".equalsIgnoreCase(valor))
                            sql = "select distinct * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where anolectivo = '"+MesAno.Get_AnoActualCobranca()+"' and descricao = '"+descricao+"' and codturma = '"+Turma.NameToCode(txt_pesquisa.getText())+"' order by nome asc limit 40";
              
        
        if( !"".equalsIgnoreCase(txt_pesquisa.getText()) && !"".equalsIgnoreCase(valor))
        {
            ConfiguraColunas();
            ConfiguraTabela(sql);
        }
    }

    public static void setNomeUser(String nomeUser) {
        VisualizarEstudanteMatriculadoController.nomeUser = nomeUser;
    }
    
    public static void setPanelBack(Pane panelBack) {
        VisualizarEstudanteMatriculadoController.panelBack = panelBack;
    }
    
    private void AddDialogo( String path )
    {
        FXMLLoader parent = new FXMLLoader(getClass().getResource(path));
        try {
            Parent root = (Parent)parent.load();
            Stage stage = new Stage();
            stage.setScene( new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(VisualizarEstudanteMatriculadoController.class.getName()).log(Level.SEVERE, null, ex);
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

    @FXML
    private void ExportarExcel(ActionEvent event) 
    {
        Alert a = new Alert(AlertType.INFORMATION,"A exportar, por favor aguarde...");
        a.show();
        if( ExportarParaExcel() )
        {
            a = new Alert(AlertType.INFORMATION,"Exportação concluida com exito.");
            a.show();
        }
        else
        {
            a = new Alert(AlertType.ERROR,"Erro ao exportar a lista");
            a.show();
        }
        
    }

    @FXML
    private void CarregarTabela(ActionEvent event) 
    {
       InicializaTabela();
       NumeroFila = 1;
       indiceFinalParticao = 0;
       ShowNumeroFilas();
       if( DefinicoesGerais.TemDadosTabela(tabela) )
            tabela.setDisable(false);
        else
        {
            tabela.setDisable(true);
            Alert a = new Alert(AlertType.INFORMATION,"Nenhuma matricula registrada");
            a.setTitle("Matriculas");
            a.show();
        }
       
    }
    
    private void VerificaUsuarioDisableBotoes()
    {
        if( !"Admin".equalsIgnoreCase(tipoUser))
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

    @FXML
    private void PreviuosDados(MouseEvent event)
    {
        indiceFinalParticao-=var;
        if( indiceFinalParticao < 0 )
        {
            indiceFinalParticao = 0;
            ShowNumeroFilas();
        }
        else
        {
            --NumeroFila;
            ConfiguraTabela("select * from view_matricula where anolectivo= '"+MesAno.Get_AnoActualCobranca()+"' order by nome limit 40 offset "+indiceFinalParticao);
            ShowNumeroFilas();
        }
    }

    @FXML
    private void NextDados(MouseEvent event) 
    {
        if( size < 10 )
            size = 0;
        else
          ++NumeroFila;
        ConfiguraTabela("select * from view_matricula where anolectivo= '"+MesAno.Get_AnoActualCobranca()+"' order by nome limit 40 offset "+(indiceFinalParticao+=size));
        ShowNumeroFilas();
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
  
    private void ShowNumeroFilas()
    {
        txt_filas.setText(String.valueOf(NumeroFila+" - "));
    }

   private void Disabilita_Imprimir( String turma, TableView<Estudante> tabela )
   {
       int codturma = Turma.NameToCode(turma);
       if( codturma != 0 && tabela.getItems().size()>0 )
           btn_imprimir.setDisable(false);
       else
           btn_imprimir.setDisable(true);
   }

   
    
}
