/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioFinanceiro;

import Bd.OperacoesBase;
import Bd.conexao;
import definicoes.DefinicoesData;
import definicoes.DefinicoesUnidadePreco;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Classe;
import modelo.Classe_Curso;
import modelo.Curso;
import modelo.Estudante;
import modelo.MesAno;
import modelo.NaoPagaramTurmaMes;
import modelo.Pagamento;
import modelo.PagaramAlunos;
import modelo.Preco;
import modelo.RelatorioGlobal;
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
public class RelatorioMensal2Controller implements Initializable {
   
   /******************Alunos que pagaram vaariaveis************************/
    @FXML private TableView<PagaramAlunos> tabela;
    @FXML private TableColumn<PagaramAlunos, String> coluna_aluno;
    @FXML private TableColumn<PagaramAlunos, String> coluna_classe;
    @FXML private TableColumn<PagaramAlunos, String> coluna_turma;
    @FXML private TableColumn<PagaramAlunos, String> coluna_curso;
    @FXML private TableColumn<PagaramAlunos, String>coluna_data;
    @FXML private TableColumn<PagaramAlunos, String> coluna_valor;
    @FXML private ComboBox<String> cb_mes;
    @FXML private TableColumn<PagaramAlunos, String>  coluna_efeito;
    @FXML private Label txt_total;
    @FXML private ComboBox<String> cb_cursoNaoPagaram1;
    @FXML private ComboBox<String> cb_classeNaoPagaram1;
    @FXML private ComboBox<String> cb_turmaNaoPagaram1;
    
    private String ano;
  /******************Fim das Variaveis dos Alunos que pagaram vaariaveis************************/  
    
   /******************Alunos que Nao pagaram vaariaveis************************/  
    @FXML private TableColumn<NaoPagaramTurmaMes, String> coluna_cursoNaoPagaram;
    @FXML private TableColumn<NaoPagaramTurmaMes, String> coluna_classeNaoPagaram;
    @FXML private TableColumn<NaoPagaramTurmaMes, String>  coluna_turmaNaoPagaram;
    @FXML private TableColumn<NaoPagaramTurmaMes, String>  coluna_alunoNaoPagaram;
    @FXML private TableColumn<NaoPagaramTurmaMes, String>  coluna_mesNaoPagaram;
    @FXML private TableColumn<NaoPagaramTurmaMes, String> coluna_valorNaoPagaram;
    @FXML private TableColumn<NaoPagaramTurmaMes, Integer> coluna_numerarioNaoPagaram;
    
    @FXML private ComboBox<String> cb_mesNaoPagaram;
    @FXML private TableView<NaoPagaramTurmaMes> tabelaNaoPagaram;
    @FXML private ComboBox<String> cb_cursoNaoPagaram;
    @FXML private ComboBox<String> cb_classeNaoPagaram;
    @FXML private ComboBox<String> cb_turmaNaoPagaram;
    @FXML  private Label txt_totalNaoPagaram;
   
    private static int total;
    private static int preco;
    private static int numerario = 0;
/******************Fim das Variaveis dos Alunos que Nao pagaram vaariaveis************************/  

    /***********************Variaveis do relatorio Global****************************************/
    @FXML private TableColumn<RelatorioGlobal, String> coluna_classeGlobal;
    @FXML private TableColumn<RelatorioGlobal, String> coluna_propinaGlobal;
    @FXML private TableColumn<RelatorioGlobal, Integer> coluna_alunospagosGlobal;
    @FXML private TableColumn<RelatorioGlobal, Integer> coluna_alunosnaopagosGlobal;
    @FXML private TableColumn<RelatorioGlobal, String> coluna_valoresnpagosGlobal;
    @FXML private TableColumn<RelatorioGlobal, String> coluna_valoresPagoGlobal;
    @FXML private TableColumn<RelatorioGlobal, String> coluna_totalGlobal;
    @FXML private ComboBox<String> cb_mesGlobal;
    @FXML private TableView<RelatorioGlobal> tabelaGlobal;
    @FXML private TableColumn<RelatorioGlobal, String> coluna_cursoGlobal;
    @FXML private TableColumn<RelatorioGlobal, Integer> coluna_totalalunoGlobal;
    @FXML private ComboBox<String> cb_periodoGlobal;
    @FXML
    private TableColumn<?, ?> coluna_totalaluno;
    

/******************Fim das variaveis do relatorio Global*****************************************/

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        InicializaMes();
        InicializaCurso();
        Inicializa_Periodo();
    }    

    @FXML
    private void Imprimir(ActionEvent event) 
    {
        HashMap h;
        String mes = cb_mes.getSelectionModel().getSelectedItem();
        String path = "C:\\RelatorioGenix\\RelatorioPagamentosAlunos.jrxml";
        String pathSemPadrao = "C:\\RelatorioGenix\\RelatorioPagamentosAlunosSemPadrao.jrxml";
        String turma = cb_turmaNaoPagaram1.getSelectionModel().getSelectedItem();
        String padrao = DefinicoesData.retornaPadrao(mes);
        ano = MesAno.Get_AnoActualCobranca();
        Alert a = new Alert(Alert.AlertType.INFORMATION,"A gerar o relatorio , por favor aguarde.");
        if( mes != null && tabela.getItems().size() > 0 )
        {
            if( turma == null  )
            {
               a.show();
               h = new HashMap();
               h.put("mes", mes);
               h.put("padrao", padrao);
               h.put("ano", ano);
               CallEmitirRelatorio(h , path);
            }
            else
            {
                a.show();
                int codturma = Turma.NameToCode(turma);
                h = new HashMap();
                h.put("mes", mes);
                h.put("descricao","Propina");
                h.put("ano", ano);
                h.put("codturma",codturma);
                CallEmitirRelatorio(h, pathSemPadrao);
            }
         } 
    }
    
     @FXML
    public void ImprimirNaoPagaram( ActionEvent event )
    {
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Gerando o  Recibo, por favor Aguarde");
        a.show();
        HashMap has = new HashMap<>();
        String path = "C:\\RelatorioGenix\\NaoPagaramMensalNovo.jrxml" ;
        CallEmitirRelatorio(has ,path );
      
    }
    

    //Alunos Pagaram Mensal--Erro no nome do metodo
    @FXML
    private void AlunosPagaram(ActionEvent event)
    {
        String mes = cb_mes.getSelectionModel().getSelectedItem();
        String anolectivo = MesAno.Get_AnoActualCobranca();
        int codturma = Turma.NameToCode(cb_turmaNaoPagaram1.getSelectionModel().getSelectedItem());
        if( mes != null && codturma >0 )
        {
            InicializaTabela(FiltraAlunosData(Pagamento.ListaAlunosPagaramFiltroPorTurmas(codturma, mes)));
        }
        else
            if( mes != null && codturma <=0  )
                InicializaTabela(Pagamento.ListaAlunosPagaramAllDatas(mes, anolectivo));
    }

   //Nao pagaram mensal 
    @FXML
    private void AlunosNaoPagaram2(ActionEvent event)
    { String mes = cb_mesNaoPagaram.getSelectionModel().getSelectedItem();
        int codturma = Turma.NameToCode(cb_turmaNaoPagaram.getSelectionModel().getSelectedItem());
        if( mes !=  null )
        {
            numerario = 0;
            
            ObservableList<String> listaGeral = Estudante.ListaAlunosGeralTurma(codturma);//retorna a lista geral de alunos de uma turma especifica que ja efectuaram o pagamento de propina pelo mesnos uma vez
            ObservableList<String> listaPagaramMes = Pagamento.ListaAlunosPagaramFiltraTurma(codturma , mes);//retorna a lista de alunos que ja pagaram o mes especifico
            RetornaAlunos( listaGeral ,  listaPagaramMes );
            
        }
    }

    
    @FXML
    private void SelecionaCurso_InicializaClasse(ActionEvent event) 
    {
        String curso = cb_cursoNaoPagaram.getSelectionModel().getSelectedItem();
        if( curso != null )
        {
            cb_classeNaoPagaram.setItems(Classe.ClassesPorCurso(curso));
        }
    }

    @FXML
    private void SelecionaClasse_InicializaTurma(ActionEvent event) 
    {
        String classe = cb_classeNaoPagaram.getSelectionModel().getSelectedItem();
        if( classe != null )
        {
            cb_turmaNaoPagaram.setItems(Turma.ListaTurmasRelaClass(classe));
        }
    }

    @FXML
    private void selecionaTurma_InicializaMes(ActionEvent event) 
    {
        String classe = cb_classeNaoPagaram.getSelectionModel().getSelectedItem();
        if( classe != null  )
        {
            String turma = cb_turmaNaoPagaram.getSelectionModel().getSelectedItem();
            if( turma != null )
            {
                InicializaMeses(classe); //inicializa os meses
            }
            else
                cb_mes.setItems(null);
        }
    }

    //**Metodos publicos da janela Relatorio Global***********************************/
    
    @FXML
    private void EmitirRelatorioGlobal(ActionEvent event)
    {
        if( tabelaGlobal.getItems().size() > 0 )
        {
             Alert a = new Alert(Alert.AlertType.INFORMATION, "Gerando o  Recibo, por favor Aguarde");
             a.show();
            HashMap h = new HashMap();
            String path = "C:\\RelatorioGenix\\RelatorioGlobal2.jrxml";
            CallEmitirRelatorio(h,path );
        }
        else
        {
            Alert a = new Alert(Alert.AlertType.ERROR, "Não é possivel gerar o relatorio porque a não existem dados.");
            a.show();
        }
        
    }

    @FXML
    private void SelecionaMes_InicializaTabela(ActionEvent event) 
    {
       String mes = cb_mesGlobal.getSelectionModel().getSelectedItem();
       String periodo = cb_periodoGlobal.getSelectionModel().getSelectedItem();
       if( mes != null )
       {
           //Elimina os dados JA Existentes na tabela 
           OperacoesBase.Eliminar("truncate table relatorioGlobal");
           InicializaTabela(mes , periodo);
       }
    }

    @FXML
    private void SelecionaPeriodo_InicializaMes(ActionEvent event) 
    {
        String periodo = cb_periodoGlobal.getSelectionModel().getSelectedItem();
       if( periodo != null )
       {
          cb_mesGlobal.setItems( FXCollections.observableArrayList(Arrays.asList("Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro","Dezembro")));
       }
    }
    
    
    
/******************************METODOS AUXILIARES DA PARTE DE VISUALIZACAO DOS ALUNOS QUE PAGARAM - MENSAL*************************/
    private void InicializaMes()
    {
         String meses_exame[] = {"Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro","Dezembro"};//meses normais
         cb_mes.setItems(FXCollections.observableArrayList(Arrays.asList(meses_exame)));
    }
    
    private void Setcolunas()
    {
        
        coluna_aluno.setCellValueFactory( new PropertyValueFactory<>("nome"));
        coluna_classe.setCellValueFactory( new PropertyValueFactory<>("classe"));
        coluna_curso.setCellValueFactory( new PropertyValueFactory<>("curso"));
        coluna_data.setCellValueFactory( new PropertyValueFactory<>("data"));
        coluna_turma.setCellValueFactory( new PropertyValueFactory<>("turma"));
        coluna_valor.setCellValueFactory( new PropertyValueFactory<>("valor"));
        coluna_efeito.setCellValueFactory( new PropertyValueFactory<>("efeito"));
    }

    
    private void InicializaTabela(ObservableList<PagaramAlunos> ListaAlunosPagaramAllDatas) {

        Setcolunas();
        
        tabela.setItems(ListaAlunosPagaramAllDatas);
        Total();
    }

    
    private void Total()
    {
        
        int soma = 0;
        for( PagaramAlunos a : tabela.getItems() )
        {
            soma+= DefinicoesUnidadePreco.StringToInteger(a.getValor());
        }
        String somaUnidade = DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(soma));
        txt_total.setText(String.valueOf(somaUnidade));
    }
    
    private ObservableList<PagaramAlunos> FiltraAlunosData(ObservableList<PagaramAlunos> ListaAlunosPagaramAllDatas)
    {
        ano = "";
        ObservableList<PagaramAlunos> lista = FXCollections.observableArrayList();
        for( PagaramAlunos a : ListaAlunosPagaramAllDatas )
        {
            if( DefinicoesData.RetornaAnoData(String.valueOf(a.getData())).equalsIgnoreCase(MesAno.Get_AnoActualCobranca()))
            {
                lista.add(a);
                ano = DefinicoesData.RetornaAnoData(String.valueOf(a.getData()));
            }
        }
         return lista;
    }
   
   
    private boolean EClassedeExame( String classe )
    {
        return "9ª classe".equalsIgnoreCase(classe) || "6ª classe".equalsIgnoreCase(classe) || "12ª classe".equalsIgnoreCase(classe);
    }
 /***
  * 
  * METODOS AUXILIARES UTILIZADOS PARA A PARTE DOS DADOS DOS ALUNOS QUE NAO PAGARAM
  * ESPACOS
  * ESPACOS
  ***/
/**********************************METODOS AUXILIARES PARA OS ALUNOS QUE NAO PAGARAM MENSAL ******************************************************/
   
   
   
    private void RetornaAlunos(ObservableList<String> listaGeral, ObservableList<String> listaPagaramMes)
    {
        listaGeral.removeAll(listaPagaramMes);//Remove da lista dos alunos que ja pagaram pelo menos um mes de propina, aqueles alunos que ja pagaram o mes especificado
        RetornaCurso( listaGeral );

    }
   
    private void RetornaCurso(ObservableList<String> listaGeral)
    {
        //elimina os dados da tabela, para gerar outro relatorio com dados actualizados
        OperacoesBase.Eliminar("truncate table relatorioAlunosNaoPagaramMensal");
        SetColunas();
        
        
        ObservableList<NaoPagaramTurmaMes> lista = FXCollections.observableArrayList();
        String anolectivo = MesAno.Get_AnoActualCobranca();
        for( String nome : listaGeral )
        {
            
            NaoPagaramTurmaMes np = new NaoPagaramTurmaMes();
            np.setNome(nome);
            np.setClasse(Estudante.GetClasse(Estudante.NameToCode(nome), anolectivo));
            np.setCurso(Curso.NameAlunoNameCurso(nome));
            np.setTurma(Turma.CodeToName(Turma.NomeAlunoToCodTurma(nome)));
            np.setValor(DefinicoesUnidadePreco.ChangeFromStringToInt(Preco.ValorPreco( np.getClasse() , Curso.NameToCode(np.getCurso()), "Propina")));
            np.setMes(cb_mesNaoPagaram.getSelectionModel().getSelectedItem());
            np.setCodigo(++numerario);
            preco = np.getValor();
            
            AdicionarTabelaRelatorioCorrespondente( np.getNome() , np.getCurso(), np.getClasse() , np.getMes() , np.getTurma() , np.getValor());
            
            lista.add(np);
        }
        
        tabelaNaoPagaram.setItems(lista);
        total = preco * listaGeral.size();
        String somaUnidade = DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(total));
        txt_totalNaoPagaram.setText(String.valueOf(somaUnidade));
    }
    
     
    private  void InicializaMeses( String classe )
    {
        String array_Meses[]= null;
        String meses_normais[] = { "Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro"};//meses normais
        String meses_exame[] = { "Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro","Dezembro"};//meses normais
        
        if( EClassedeExame(classe))
            array_Meses = meses_exame;
        else
            if( !EClassedeExame(classe) )
                array_Meses = meses_normais;
        
        cb_mesNaoPagaram.setItems(FXCollections.observableArrayList(Arrays.asList(array_Meses)));
    }
   
   private void InicializaCurso()
    {
        cb_cursoNaoPagaram.setItems(Curso.ListaCursos());
        cb_cursoNaoPagaram1.setItems(Curso.ListaCursos());
    }
   
   private void SetColunas()
    {
        
        coluna_alunoNaoPagaram.setCellValueFactory( new PropertyValueFactory<>("nome"));
        coluna_classeNaoPagaram.setCellValueFactory( new PropertyValueFactory<>("classe"));
        coluna_cursoNaoPagaram.setCellValueFactory( new PropertyValueFactory<>("curso"));
        coluna_mesNaoPagaram.setCellValueFactory( new PropertyValueFactory<>("mes"));
        coluna_turmaNaoPagaram.setCellValueFactory( new PropertyValueFactory<>("turma"));
        coluna_valorNaoPagaram.setCellValueFactory( new PropertyValueFactory<>("valor"));
        coluna_numerarioNaoPagaram.setCellValueFactory( new PropertyValueFactory<>("codigo"));
    }
   
   private void AdicionarTabelaRelatorioCorrespondente(String nome, String curso, String classe, String mes, String turma, int valor) {

        String sql = "insert into relatorioAlunosNaoPagaramMensal values( '"+NaoPagaramTurmaMes.LastCodeRecibo()+"' , '"+nome+"' , '"+classe+"', '"+curso+"' ,'"+turma+"' , '"+mes+"', '"+valor+"' )";
        OperacoesBase.Inserir(sql);
      
    }
    
/*************FIM DOS METODOS AXULIARES DOS QUE NAO PAGARAM E INICIAO DOS METODOS AUXILIARES GERAIS**************************************************/
/**************Inicio dos Metodos Auxiliares do Relatorio Global *****************************************************************/
    private void ConfiguraColunas()
    {
        coluna_classeGlobal.setCellValueFactory( new PropertyValueFactory<>("classe"));
        coluna_cursoGlobal.setCellValueFactory( new PropertyValueFactory<>("curso"));
        coluna_propinaGlobal.setCellValueFactory( new PropertyValueFactory<>("precoPropina"));
        coluna_alunospagosGlobal.setCellValueFactory( new PropertyValueFactory<>("quantAlunosPagaram"));
        coluna_alunosnaopagosGlobal.setCellValueFactory( new PropertyValueFactory<>("quantAlunosNaoPagaram"));
        coluna_valoresPagoGlobal.setCellValueFactory( new PropertyValueFactory<>("valorpago"));
        coluna_valoresnpagosGlobal.setCellValueFactory( new PropertyValueFactory<>("valorNaoPago"));
        coluna_totalalunoGlobal.setCellValueFactory( new PropertyValueFactory<>("totalAlunos"));
        coluna_totalGlobal.setCellValueFactory( new PropertyValueFactory<>("total"));
        
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
        
        tabelaGlobal.setItems(listarg);
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
            Logger.getLogger(RelatorioMensal2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ++valor;
    }
    
    private void AddRelatorio(int totalaluno,  String classe , String curso ,int prop ,  int matr , int conf , int alunospagos , int alunosnaopago , int valorespago , int valoresNaopago , int totalgeral , String mes  )
    {
        OperacoesBase.Inserir("insert into relatorioGlobal  values( '"+this.UltimoIdRelatorio()+"', '"+classe+"' , '"+curso+"' ,  '"+matr+"' , '"+conf+"', '"+alunospagos+"' , '"+alunosnaopago+"' , '"+valorespago+"' , '"+valoresNaopago+"' , '"+totalgeral+"' , '"+totalaluno+"', '"+mes+"','"+prop+"' )");
    }

    private ObservableList<Classe_Curso> ListaClasse() 
    {
       return Classe.ListaClassesExistentes_Curso();
        
    }
   
    private void Inicializa_Periodo()
    {
         cb_periodoGlobal.setItems(FXCollections.observableArrayList(Arrays.asList("Manhã" , "Tarde")));
    }
  /*************FIM DOS METODOS AXULIARES DO Relatorio Global Financeiro*************************************************/
 
   
   
   private boolean CallEmitirRelatorio( HashMap parametros , String path  )
   {
       
       try
        {
            conexao.Conectar();
            JasperReport report = JasperCompileManager.compileReport(path);
            
            JasperPrint print = JasperFillManager.fillReport(report, parametros, conexao.ObterConection()) ;
            JasperViewer jv = new JasperViewer(print , false);
            jv.setVisible(true);
            jv.toFront();
            
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
       conexao.TerminarConexao();
       return false;
       
   }

    @FXML
    private void SelecionaCurso_InicializaClassePagaram(ActionEvent event) 
    {
         String curso = cb_cursoNaoPagaram1.getSelectionModel().getSelectedItem();
         cb_classeNaoPagaram1.setItems(Classe.ClassesPorCurso(curso));
    }

    @FXML
    private void SelecionaClasse_InicializaTurmaPagaram(ActionEvent event)
    {
        String classe = cb_classeNaoPagaram1.getSelectionModel().getSelectedItem();
        int codcurso = Curso.NameToCode(cb_cursoNaoPagaram1.getSelectionModel().getSelectedItem());
        cb_turmaNaoPagaram1.setItems(Turma.ListaTurmasRelaClasse_CodCurso(classe, codcurso));
    }

    @FXML
    private void selecionaTurma_InicializaMesPagaram(ActionEvent event) 
    {
        int codturma = Turma.NameToCode(cb_turmaNaoPagaram1.getSelectionModel().getSelectedItem());
        if( codturma > 0 )
            InicializaMes();
    }

 
}
