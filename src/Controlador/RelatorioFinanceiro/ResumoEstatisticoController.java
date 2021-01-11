/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioFinanceiro;

import Bd.OperacoesBase;
import Bd.conexao;
import definicoes.DefinicoesUnidadePreco;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Classe;
import modelo.Classe_Curso;
import modelo.Curso;
import modelo.Estudante;
import modelo.Pagamento;
import modelo.Preco;
import modelo.RelatorioGlobal;
import net.sf.jasperreports.engine.JRException;
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
public class ResumoEstatisticoController implements Initializable {
    @FXML
    private TableView<RelatorioGlobal> tabela;
    @FXML
    private TableColumn<RelatorioGlobal, Integer> coluna_totalaluno;
    @FXML
    private TableColumn<RelatorioGlobal, Integer> coluna_alunospagos;
    @FXML
    private TableColumn<RelatorioGlobal, String> coluna_valoresPago;
    @FXML
    private TableColumn<RelatorioGlobal, Integer> coluna_alunosnaopagos;
    @FXML
    private TableColumn<RelatorioGlobal, String> coluna_valoresnpagos;
    @FXML
    private TableColumn<RelatorioGlobal, String> coluna_total;

    private static String mes;
    private static String periodoSelected;
    private static ObservableList<RelatorioGlobal> Listarg;
    @FXML
    private TableColumn<RelatorioGlobal, String> coluna_periodo;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        Total();
    }    
   
    
    @FXML
    private void CallRelatorio()
    {
        if( tabela.getItems().size() > 0 )
        {
            
            HashMap has = new HashMap<>();
            Relatorio(has);
        }
      
    }
    
/******************METODOS AUXILIARES **************************************************/
    /**
     * 
     * @param mes
     */
    public static void setMes(String mes) {
        ResumoEstatisticoController.mes = mes;
    }

    public static void setListarg(ObservableList<RelatorioGlobal> Listarg) {
        ResumoEstatisticoController.Listarg = Listarg;
    }

    public static void setPeriodoSelected(String periodoSelected) {
        ResumoEstatisticoController.periodoSelected = periodoSelected;
    }
   
    private void Total()
    {
        
        //elimona os dados da tabela na base de dados
        OperacoesBase.Eliminar("truncate table RelatorioResumo");
        SetColunas();
        
        int totalAlunos = 0;
        int totalPagos = 0;
        int totalNpagos = 0;
        int totalValoresPago = 0;
        int totalValoresNaoPago = 0;
        int totalGeral = 0;
        for( RelatorioGlobal rg : Listarg )
        {
            totalAlunos += rg.getTotalAlunos();
            totalPagos  += rg.getQuantAlunosPagaram();
            totalNpagos += rg.getQuantAlunosNaoPagaram();
            totalValoresPago += rg.getValorpago();
            totalValoresNaoPago += rg.getValorNaoPago();
            totalGeral  += rg.getTotal();
        }
         
        RelatorioGlobal r = new RelatorioGlobal();
        r.setTotalAlunos(totalAlunos);
        r.setQuantAlunosPagaram(totalPagos);
        r.setQuantAlunosNaoPagaram(totalNpagos);
        r.setValorpago(totalValoresPago);
        r.setValorNaoPago(totalValoresNaoPago);
        r.setTotal(totalGeral);
        r.setPeriodo(periodoSelected);
        
        AddicionarResumo(r.getTotalAlunos(),periodoSelected, r.getQuantAlunosPagaram(), r.getValorpago(), r.getQuantAlunosNaoPagaram(), r.getValorNaoPago(), r.getTotal(),mes);
        
        //chama o outros dados...
        String periodo = "Manhã";
        if( periodo.equalsIgnoreCase(periodoSelected))
            periodo = "Tarde";
        
        //reinicializa as variaveis
        totalAlunos = 0;
        totalGeral = 0;
        totalNpagos = 0;
        totalPagos = 0;
        totalValoresNaoPago = 0;
        totalValoresPago = 0;
        
        ObservableList<RelatorioGlobal> listaOutrosDados = ObterOutrosDados(mes, periodo);
        for( RelatorioGlobal rg : listaOutrosDados )
        {
            totalAlunos += rg.getTotalAlunos();
            totalPagos  += rg.getQuantAlunosPagaram();
            totalNpagos += rg.getQuantAlunosNaoPagaram();
            totalValoresPago += rg.getValorpago();
            totalValoresNaoPago += rg.getValorNaoPago();
            totalGeral  +=  rg.getTotal();
        }
        
        RelatorioGlobal r2 = new RelatorioGlobal();
        r2.setTotalAlunos(totalAlunos);
        r2.setQuantAlunosPagaram(totalPagos);
        r2.setQuantAlunosNaoPagaram(totalNpagos);
        r2.setValorpago(totalValoresPago);
        r2.setValorNaoPago(totalValoresNaoPago);
        r2.setTotal(totalGeral);
        r2.setPeriodo(periodo);
        
        AddicionarResumo(r2.getTotalAlunos(),periodo, r2.getQuantAlunosPagaram(), r2.getValorpago(), r2.getQuantAlunosNaoPagaram(), r2.getValorNaoPago(), r2.getTotal(),mes);
        
        
        ObservableList<RelatorioGlobal> lista = FXCollections.observableArrayList();
        lista.add(r);
        lista.add(r2);
        tabela.setItems(lista);
    }
    
   
    private void SetColunas()
    {
        coluna_totalaluno.setCellValueFactory( new PropertyValueFactory<>("totalAlunos"));
        coluna_alunospagos.setCellValueFactory( new PropertyValueFactory<>("quantAlunosPagaram"));
        coluna_alunosnaopagos.setCellValueFactory( new PropertyValueFactory<>("quantAlunosNaoPagaram"));
        coluna_valoresPago.setCellValueFactory( new PropertyValueFactory<>("valorpago"));
        coluna_valoresnpagos.setCellValueFactory( new PropertyValueFactory<>("valorNaoPago"));
        coluna_periodo.setCellValueFactory( new PropertyValueFactory<>("periodo"));
        coluna_total.setCellValueFactory( new PropertyValueFactory<>("total"));
        
    }
    
   

    private ObservableList<RelatorioGlobal>   ObterOutrosDados( String mes , String periodo)
    {
        ObservableList<RelatorioGlobal> listarg = FXCollections.observableArrayList();
        
        for( Classe_Curso cc : ListaClasse() )
        {
            RelatorioGlobal rg = new RelatorioGlobal();
            rg.setClasse(cc.getClasse());
            rg.setCurso(cc.getCurso());
            rg.setPeriodo(periodo);
            rg.setPrecoPropina(DefinicoesUnidadePreco.ChangeFromStringToInt(Preco.ValorPreco(cc.getClasse(), Curso.NameToCode(cc.getCurso()), "Propina")));
            rg.setMatriculados(Pagamento.QuantidadeMatriculados(mes, rg.getCurso(), rg.getClasse()));
            rg.setConfirmados(Pagamento.QuantidadeConfirmados(mes, rg.getCurso(), rg.getClasse()));
            rg.setQuantAlunosPagaram(Pagamento.ListaAlunosPagaram(mes, rg.getCurso(),rg.getClasse() , rg.getPeriodo()).size());
            rg.setQuantAlunosNaoPagaram(Pagamento.ListaAlunosNaoPagaram(mes, rg.getCurso(), rg.getClasse() ,rg.getPeriodo()).size());
            rg.setValorpago(Pagamento.ValoresPagoAlunos(mes, rg.getCurso(), rg.getClasse() , rg.getPeriodo()));
            rg.setValorNaoPago(Pagamento.ValoresNaoPagos(Pagamento.ListaAlunosNaoPagaram(mes, rg.getCurso(), rg.getClasse(), rg.getPeriodo())) );
            rg.setTotal(rg.getValorpago()+rg.getValorNaoPago());
            rg.setTotalAlunos(Estudante.ListaAlunosGeralPorCurso_Classe(rg.getCurso(), rg.getClasse() ,rg.getPeriodo()).size());
            listarg.add(rg);
        }
        
        return listarg;
    }
    
    private ObservableList<Classe_Curso> ListaClasse() 
    {
       return Classe.ListaClassesExistentes_Curso();
        
    }
    
    private int UltimoIdRelatorio()
    {
        ResultSet rs = OperacoesBase.Consultar("select codrelatoio from relatorioResumo order by codrelatoio");
        int valor = 0;
        try {
            while( rs.next() )
            {
               valor = rs.getInt("codrelatoio");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResumoEstatisticoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ++valor;
    }
    
    
     private void Relatorio( HashMap parametros )
    {
        conexao.Conectar();
        try {
            JasperReport jasper  = JasperCompileManager.compileReport("C:\\RelatorioGenix\\RelatorioResumo.jrxml");
            JasperPrint print = JasperFillManager.fillReport(jasper, parametros, conexao.ObterConection());
            
            JasperViewer view = new JasperViewer(print);
            view.setVisible(true);
            view.toFront();
        } catch (JRException ex) {
            Logger.getLogger(ResumoEstatisticoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     private void AddicionarResumo( int totalAluno, String periodo, int alunopago, int valorpago, int alunosnaopago , int valor_np , int total , String mes  )
     {
         OperacoesBase.Inserir("insert into RelatorioResumo values('"+this.UltimoIdRelatorio()+"' , '"+alunopago+"' , '"+alunosnaopago+"' , '"+valorpago+"','"+valor_np+"' , '"+total+"' , '"+totalAluno+"' , '"+periodo+"' , '"+mes+"')");
     }
}
