/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.RelatorioPedagogico;

import Bd.OperacoesBase;
import Bd.conexao;
import definicoes.DefinicoesUnidadePreco;
import java.io.IOException;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import modelo.Classe;
import modelo.Classe_Curso;
import modelo.Curso;
import modelo.Estudante;
import modelo.Pagamento;
import modelo.Preco;
import modelo.RelatorioGlobal;
import modelo.Turma;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import Controlador.RelatorioFinanceiro.ResumoEstatisticoController;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class RelatorioglobalMensalFinanceiroController implements Initializable {
    
    @FXML private TableColumn<RelatorioGlobal, String> coluna_classe;
    @FXML private TableColumn<RelatorioGlobal, String> coluna_propina;
    @FXML private TableColumn<RelatorioGlobal, Integer> coluna_alunospagos;
    @FXML private TableColumn<RelatorioGlobal, Integer> coluna_alunosnaopagos;
    @FXML private TableColumn<RelatorioGlobal, String> coluna_valoresnpagos;
    @FXML private TableColumn<RelatorioGlobal, String> coluna_valoresPago;
    @FXML private TableColumn<RelatorioGlobal, String> coluna_total;
    @FXML private ComboBox<String> cb_mes;
    @FXML private TableView<RelatorioGlobal> tabela;
    @FXML private TableColumn<RelatorioGlobal, String> coluna_curso;
    @FXML private TableColumn<RelatorioGlobal, Integer> coluna_totalaluno;
    @FXML private ComboBox<String> cb_periodo;

    
    private static Pane pane;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // T
        cb_periodo.setItems(FXCollections.observableArrayList(Arrays.asList("Manhã" , "Tarde")));
    }    
    
    
   @FXML
   private void SelecionaMes_InicializaTabela()
   {
       
       String mes = cb_mes.getSelectionModel().getSelectedItem();
       String periodo = cb_periodo.getSelectionModel().getSelectedItem();
       if( mes != null )
       {
           //Elimina os dados JA Existentes na tabela 
           OperacoesBase.Eliminar("truncate table relatorioGlobal");
           InicializaTabela(mes , periodo);
       }
       
   }
    
   @FXML
   private void SelecionaPeriodo_InicializaMes()
   {
       String periodo = cb_periodo.getSelectionModel().getSelectedItem();
       if( periodo != null )
       {
          cb_mes.setItems( FXCollections.observableArrayList(Arrays.asList("Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro","Dezembro")));
       }
   }
   
   
   @FXML
   private void CallResumo()
   {
       String mes = cb_mes.getSelectionModel().getSelectedItem();
       String periodo = cb_periodo.getSelectionModel().getSelectedItem();
       ObservableList<RelatorioGlobal> listaRg = tabela.getItems();
       
       if( mes != null && listaRg != null )
       {
           ResumoEstatisticoController.setMes(mes);
           ResumoEstatisticoController.setPeriodoSelected(periodo);
           ResumoEstatisticoController.setListarg(listaRg);
           AddPane("/vista/resumoEstatistico.fxml");
       }
   }
   
   @FXML
   private void EmitirRelatorio()
   {
       if(tabela.getItems().size()> 0)
       {
          HashMap has = new HashMap<>(); 
           Relatorio(has);
       }
   }
   
/**************Metodos operacionais************************************************************************************/
    private ObservableList<String> AlunosPagaram(String mes )
    {
        ObservableList<String> lista = Pagamento.ListaAlunosPagaram(mes);
        ObservableList<String> lista2 =  Estudante.ListaAlunosGeral();
        lista2.removeAll(lista);
        AlunosNaoPagaram(lista2);
        return lista;
    }
    
    private ObservableList<String> AlunosNaoPagaram( ObservableList<String> alunos )
    {
        return alunos;
    }
    
    private ObservableList<Classe_Curso> ListaClasse() 
    {
       return Classe.ListaClassesExistentes_Curso();
        
    }
     
    private void InicializaTabela( String mes , String periodo)
    {
        ConfiguraColunas();
        ObservableList<RelatorioGlobal> listarg = FXCollections.observableArrayList();
        for( Classe_Curso cc : ListaClasse() )
        {
            RelatorioGlobal rg = new RelatorioGlobal();
            rg.setClasse(cc.getClasse());
            rg.setCurso(cc.getCurso());
            rg.setPeriodo(periodo);
            rg.setPrecoPropina(DefinicoesUnidadePreco.ChangeFromStringToInt(Preco.ValorPreco(cc.getClasse(), Curso.NameToCode(cc.getCurso()), "Propina")));
            rg.setQuantAlunosPagaram(Pagamento.ListaAlunosPagaram(mes, rg.getCurso(),rg.getClasse() , rg.getPeriodo()).size());
            rg.setQuantAlunosNaoPagaram(Pagamento.ListaAlunosNaoPagaram(mes, rg.getCurso(), rg.getClasse() ,rg.getPeriodo()).size());
            rg.setValorpago(Pagamento.ValoresPagoAlunos(mes, rg.getCurso(), rg.getClasse() , rg.getPeriodo()));
            rg.setValorNaoPago(Pagamento.ValoresNaoPagos(Pagamento.ListaAlunosNaoPagaram(mes, rg.getCurso(), rg.getClasse(), rg.getPeriodo())));
            rg.setTotal(rg.getValorpago()+rg.getValorNaoPago());
            rg.setTotalAlunos(Estudante.ListaAlunosGeralPorCurso_Classe(rg.getCurso(), rg.getClasse() ,rg.getPeriodo()).size());
            listarg.add(rg);
            
            
            //adiciona na base de dados afim de se criar o relatorio
            AddRelatorio(rg.getTotalAlunos(), rg.getClasse(), rg.getCurso(), rg.getPrecoPropina(), rg.getMatriculados(), rg.getConfirmados(), rg.getQuantAlunosPagaram(), rg.getQuantAlunosNaoPagaram(), rg.getValorpago(), rg.getValorNaoPago(), rg.getTotal(), cb_mes.getSelectionModel().getSelectedItem());
        }
        
        tabela.setItems(listarg);
    }
    
    private void ConfiguraColunas()
    {
        coluna_classe.setCellValueFactory( new PropertyValueFactory<>("classe"));
        coluna_curso.setCellValueFactory( new PropertyValueFactory<>("curso"));
        coluna_propina.setCellValueFactory( new PropertyValueFactory<>("precoPropina"));
        coluna_alunospagos.setCellValueFactory( new PropertyValueFactory<>("quantAlunosPagaram"));
        coluna_alunosnaopagos.setCellValueFactory( new PropertyValueFactory<>("quantAlunosNaoPagaram"));
        coluna_valoresPago.setCellValueFactory( new PropertyValueFactory<>("valorpago"));
        coluna_valoresnpagos.setCellValueFactory( new PropertyValueFactory<>("valorNaoPago"));
        coluna_totalaluno.setCellValueFactory( new PropertyValueFactory<>("totalAlunos"));
        coluna_total.setCellValueFactory( new PropertyValueFactory<>("total"));
        
    }
    

    public static void setPane(Pane pane) {
        RelatorioglobalMensalFinanceiroController.pane = pane;
    }
    
    
    private void AddPane( String path )
    {
       
        pane.getChildren().removeAll();
        
        try {
           
            Parent fxml = FXMLLoader.load(getClass().getResource(path));
            
            pane.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(RelatorioglobalMensalFinanceiroController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int UltimoIdRelatorio()
    {
        ResultSet rs = OperacoesBase.Consultar("select codrelatorio from relatorioGlobal order by codrelatorio");
        int valor = 0;
        try {
            while( rs.next() )
            {
               valor = rs.getInt("codrelatorio");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RelatorioglobalMensalFinanceiroController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ++valor;
    }
    
    private void AddRelatorio(int totalaluno,  String classe , String curso ,int prop ,  int matr , int conf , int alunospagos , int alunosnaopago , int valorespago , int valoresNaopago , int totalgeral , String mes  )
    {
        OperacoesBase.Inserir("insert into relatorioGlobal  values( '"+this.UltimoIdRelatorio()+"', '"+classe+"' , '"+curso+"' ,  '"+matr+"' , '"+conf+"', '"+alunospagos+"' , '"+alunosnaopago+"' , '"+valorespago+"' , '"+valoresNaopago+"' , '"+totalgeral+"' , '"+totalaluno+"', '"+mes+"','"+prop+"' )");
    }
    
    private void Relatorio( HashMap parametros )
    {
        conexao.Conectar();
        try {
            JasperReport jasper  = JasperCompileManager.compileReport("C:\\RelatorioGenix\\RelatorioGlobal2.jrxml");
            JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conexao.ObterConection());
            
            JasperViewer view = new JasperViewer(print);
            view.setVisible(true);
            view.toFront();
        } catch (JRException ex) {
            Logger.getLogger(RelatorioglobalMensalFinanceiroController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String DropPonto( String valor )
    {
        if( valor.contains("."))
        {
            String np = valor.replace(".", "");
            return np.trim();
        }
        return valor;
    }
}
