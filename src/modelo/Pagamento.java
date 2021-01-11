/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import Bd.OperacoesBase;
import Controlador.Listas.ListaMatriculadosController;
import Validacoes.validaDevedor;
import definicoes.DefinicoesData;
import definicoes.DefinicoesUnidadePreco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Familia Neto
 */
public class Pagamento {

   
    private int codigo;
    private String efeito;
    private int codmatricula_c;
    private int multa;
    private int valor_apagar;
    private int valor_Pago;
    private String mes;
    private LocalDate data;
    private int codfuncionario;
    private String forma_pagamento;
    private String ano;
    private String curso;
    private String total;
    
    //Variaveis auxiliares para o extracto
    private String valorPago;
    private String valorApagar;
    
    public Pagamento() {
    }

    public int getCodmatricula_c() {
        return codmatricula_c;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    
    

    public String getValorPago() {
        return valorPago;
    }

    public void setValorPago(String valorPago) {
        this.valorPago = valorPago;
    }

    public String getValorApagar() {
        return valorApagar;
    }

    public void setValorApagar(String valorApagar) {
        this.valorApagar = valorApagar;
    }
   
    public String getForma_pagamento() {
        return forma_pagamento;
    }

    public String getAno() {
        return ano;
    }
 
    public int getCodfuncionario() {
        return codfuncionario;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getCurso() {
        return curso;
    }

    public int getMulta() {
        return multa;
    }

    
    public LocalDate getData() {
        return data;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setMulta(int multa) {
        this.multa = multa;
    }

    
    public String getEfeito() {
        return efeito;
    }

    public String getMes() {
        return mes;
    }

   
    public int getValor_Pago() {
        return valor_Pago;
    }

    public int getValor_apagar() {
        return valor_apagar;
    }

    public void setForma_pagamento(String forma_pagamento) {
        this.forma_pagamento = forma_pagamento;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void setCodmatricula_c(int codmatricula_c) {
        this.codmatricula_c = codmatricula_c;
    }

   
   
    public void setCodfuncionario(int codfuncionario) {
        this.codfuncionario = codfuncionario;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setEfeito(String efeito) {
        this.efeito = efeito;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

   
    public void setValor_Pago(int valor_Pago) {
        this.valor_Pago = valor_Pago;
    }

    public void setValor_apagar(int valor_apagar) {
        this.valor_apagar = valor_apagar;
    }
    
    public static int UltimocodePagamento()
    {
    
        ResultSet rs = OperacoesBase.Consultar("select codpagamento from pagamento order by codpagamento");
        int valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codpagamento");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++valor;
    }
    
    public boolean adicionar()
    {
     
      return  OperacoesBase.Inserir("insert into pagamento values('"+this.getCodigo()+"' , '"+this.getEfeito()+"' ,'"+this.getCodfuncionario()+"' , '"+this.getAno()+"' , '"+this.getForma_pagamento()+"', '"+this.getData()+"','"+this.getValor_apagar()+"', '"+this.getValor_Pago()+"', '"+this.getMulta()+"','"+this.getCodmatricula_c()+"','"+this.getMes()+"')");
    }
    
    /**
     * Este metodo retorna uma lista com os alunos que 
     * ja pagaram a mensalidade de um mes especifico
     * @param mes_referente
     * @return ObservableList<String> 
     */
    public static  ObservableList<String> ListaAlunosPagaram( String mes_referente )
    {   
        ResultSet rs = OperacoesBase.Consultar("select * from pagamento  inner join aluno using(codaluno) where  ano_referencia = '"+MesAno.Get_AnoActualCobranca()+"' and  mes_referente = '"+mes_referente+"'");
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    /**
     * DESCRIÇÃO DO METODO: Este metodo retorna uma lista( de alunos  ) com todos os pagamentos efectuados no mes passado por parametro e do anolectivo passado como parametro.
     * @param mes_referente-- Mes ao qual se vão retornar todos os pagamentos
     * @param anolectivo -- Ano lectivo tambem a fitrar
     * @return ObservableList do tipo PagaramAlunos
     */
    public static  ObservableList<PagaramAlunos> ListaAlunosPagaramAllDatas( String mes_referente, String anolectivo )
    {   String padrao = DefinicoesData.retornaPadrao(mes_referente);
        
        ResultSet rs = OperacoesBase.Consultar("select * from pagamento  inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where data like '"+padrao+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"'");
        ObservableList<PagaramAlunos> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                PagaramAlunos a = new PagaramAlunos();
                a.setClasse(rs.getString("nome_classe"));
                a.setCurso(rs.getString("nome_curso"));
                a.setData( DefinicoesData.StringtoLocalDate(rs.getString("data")) );
                a.setNome(rs.getString("nome"));
                a.setEfeito(rs.getString("descricao"));
                a.setValor(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("valor_pago")));
                a.setTurma(Turma.CodeToName(rs.getInt("codturma")));
                a.setMulta(rs.getString("multa"));
                lista.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pagamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    
    } //fim do metodo
    
    
    /**
     * Retorna a lista dos alunos efectuaram o pagamento( por turma ) da mensalidade( Nota: o ano de pagamento e sempre o ano actual) 
     * @param codturma --codigo da turma..
     * @param mes -- mes que se deseja filtrar o pagamento
     * @return Uma observableLista de alunos que cumprem com a condicao
     */ 
    public static  ObservableList<PagaramAlunos> ListaAlunosPagaramFiltroPorTurmas(int codturma , String mes )
    {  
        String descricao = "Propina";
        
        ResultSet rs = OperacoesBase.Consultar("select * from pagamento  inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where codturma = '"+codturma+"' and descricao = '"+descricao+"' and  anolectivo = '"+MesAno.Get_AnoActualCobranca()+"' and mes_referente = '"+mes+"' order by nome asc");
        ObservableList<PagaramAlunos> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                PagaramAlunos a = new PagaramAlunos();
                a.setClasse(rs.getString("nome_classe"));
                a.setPeriodo(rs.getString("periodo"));
                a.setCurso(rs.getString("nome_curso"));
                a.setData( DefinicoesData.StringtoLocalDate(rs.getString("data")) );
                a.setNome(rs.getString("nome"));
                a.setEfeito(rs.getString("descricao"));
                a.setValorAPagar(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("valor_a_pagar")));
                a.setMulta(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("multa")));
                a.setValor(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("valor_pago")));
                a.setTurma(Turma.CodeToName(rs.getInt("codturma")));
               
                lista.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pagamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    
    }
    
     public static  ObservableList<Estudante> ListaAlunosPagaramConfirmacaoFiltroPorTurmas(int codturma , String ano )
    {  
        String descricao = "Confirmação";
        
        ResultSet rs = OperacoesBase.Consultar("select distinct * from aluno inner join turma using(codturma) inner join pagamento using(codaluno)  where codturma = '"+codturma+"' and descricao = '"+descricao+"' and ano_referencia = '"+MesAno.Get_AnoActualCobranca()+"' order by nome asc");
        ObservableList<Estudante> lista = FXCollections.observableArrayList();
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
               e.setFoto(rs.getString("fotografia"));
               e.setDatanas(DefinicoesData.StringtoLocalDate(rs.getString("datanasc")));
               e.setDataPagamento(rs.getString("data"));
               e.setIdade(DefinicoesData.RetornaIdade(e.getDatanas()));
               e.setProfessor(Professor.CodeToName(rs.getInt("codprofessor")));
                lista.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pagamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    
    }
    
     /**
      * 
     * @param sql, Query sql que vai ser utlizada para retornar os alunos que estao matriculados em uma determinada turma e em uma determinados ano lectivo
     * @return Retorna uma lista do tipo Estudante
      */
    public static ObservableList<Estudante> ListaAlunosMatriculados( String sql )
    {
        
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
               e.setClasse(rs.getString("nome_classe"));
               e.setPeriodo(rs.getString("periodo"));
               e.setCurso(rs.getString("nome_curso"));
               e.setFoto(rs.getString("fotografia"));
               e.setDatanas(DefinicoesData.StringtoLocalDate(rs.getString("datanasc")));
               e.setIdade(DefinicoesData.RetornaIdade(e.getDatanas()));
               e.setProfessor(Professor.CodeToName(rs.getInt("codprofessor")));
               lista.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ListaMatriculadosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
     
     
    
    /**
     * Este metodo retorna uma lista contendo os alunos que ja efectuaram 
     * pelo menos um pagamento de propinas no ano corrente
     * @return ObservableList<String>
     */
    public static  ObservableList<String> ListaAlunosPagaram()
    {   
        String descricao = "Propina";
        ResultSet rs = OperacoesBase.Consultar("select * from pagamento  inner join aluno using(codaluno) where descricao = '"+descricao+"' and ano_referencia = '"+MesAno.Get_AnoActualCobranca()+"' order by nome asc");
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    
    }
    
    /**
     * Este metodo retorna uma lista que contem os alunos de uma turma
     * especifica que efectuaram o pagamemnto da propina de um mes especifico
     * @param codturma
     * @param mes
     * @return 
     */
    public static  ObservableList<String> ListaAlunosPagaramFiltraTurma( int codturma , String mes )
    {   
        String descricao = "Propina";
        ResultSet rs = OperacoesBase.Consultar("select * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) where codturma = '"+codturma+"' and descricao = '"+descricao+"' and ano_referencia = '"+MesAno.Get_AnoActualCobranca()+"' and mes_referente = '"+mes+"' order by nome asc");
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    
    }
    
    /**
     * Este metodo retorna o ultimo id utilizado mais um da tabela extractoAnualAluno,
     * afim de ser utilizado pelo proximo registro aa armazenar
     * @return int 
     */
    public static int UltimocodeExtratoAnualAluno()
    {
    
        ResultSet rs = OperacoesBase.Consultar("select codextrato from extractoAnualAluno order by codextrato");
        int valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codextrato");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pagamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++valor;
    }
    
   
    
    public static int  QuantidadeMatriculados( String mes , String curso , String classe )
    {
         String sql = "select * from  pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where descricao != 'Propina' and mes_referente = '"+mes+"' and nome_curso = '"+curso+"' and nome_classe = '"+classe+"' order by nome asc";
         ResultSet rs = OperacoesBase.Consultar(sql);
         double valor_pago = 0;
         int quantidade = 0;
        try {
            while( rs.next() )
            {
                 ++quantidade;
                 valor_pago += rs.getDouble("valor_pago");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pagamento.class.getName()).log(Level.SEVERE, null, ex);
        }
         RetornaValorMatricula( valor_pago );
        return quantidade;
    }
    
     public static int  QuantidadeConfirmados( String mes , String curso , String classe )
    {
         String sql = "select * from  pagamento  inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where descricao = 'Confirmação' and mes_referente = '"+mes+"' and nome_curso = '"+curso+"' and nome_classe = '"+classe+"' order by nome asc";
         ResultSet rs = OperacoesBase.Consultar(sql);
         int quantidade = 0;
         double valor_pago = 0;
        try {
            while( rs.next() )
            {
                 ++quantidade;
                 valor_pago += rs.getDouble("valor_pago");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pagamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        RetornaValorConfirmacao( valor_pago );
        return quantidade;
    }
     
     /**
     * Este metodo retorna uma lista com os alunos que 
     * ja pagaram a mensalidade de um mes especifico
     * @param mes_referente
     * @param curso
     * @param classe
     * @param periodo
     * @return ObservableList<String> 
     */
    public static  ObservableList<String> ListaAlunosPagaram( String mes_referente , String curso , String classe , String periodo )
    {   
        ResultSet rs = OperacoesBase.Consultar("select * from pagamento  inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) inner join turma t using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where t.periodo = '"+periodo+"' and  ano_referencia = '"+MesAno.Get_AnoActualCobranca()+"' and  mes_referente = '"+mes_referente+"' and nome_curso = '"+curso+"' and nome_classe = '"+classe+"' and descricao = 'Propina' order by nome asc");
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
     public static  ObservableList<String> ListaAlunosNaoPagaram( String mes_referente , String curso , String classe ,String periodo )
     {   
          ObservableList<String> pagaram =   Pagamento.ListaAlunosPagaram(mes_referente, curso, classe , periodo);
          ObservableList<String> alunosGeral = Estudante.ListaAlunosGeralPorCurso_Classe(curso, classe , periodo);
          alunosGeral.removeAll(pagaram);
          
          
          return alunosGeral;
     }
    /**
     * 
     * @param mes_referente
     * @param curso
     * @param classe
     * @param periodo
     * @return 
     */
    public static int ValoresPagoAlunos( String mes_referente , String curso , String classe , String periodo )
    {   
        ResultSet rs = OperacoesBase.Consultar("select valor_a_pagar from pagamento inner join matricula_confirmacao using(codmatricula_c)  inner join aluno using(codaluno) inner join turma t using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where  ano_referencia = '"+MesAno.Get_AnoActualCobranca()+"' and  mes_referente = '"+mes_referente+"' and nome_curso = '"+curso+"' and nome_classe = '"+classe+"' and t.periodo = '"+periodo+"' and descricao = 'Propina'");
        int valor_matricula  = DefinicoesUnidadePreco.ChangeFromStringToInt(Preco.ValorPreco(classe, Curso.NameToCode(curso), "Matricula")) * Pagamento.QuantidadeMatriculados(mes_referente, curso, classe);
        int valor_confirma  = DefinicoesUnidadePreco.ChangeFromStringToInt(Preco.ValorPreco(classe, Curso.NameToCode(curso), "Confirmação")) * Pagamento.QuantidadeConfirmados(mes_referente, curso, classe);
        
        int valor = 0;
        try {
            while( rs.next() )
            {
                valor += DefinicoesUnidadePreco.ChangeFromStringToInt(rs.getString("valor_a_pagar"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pagamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor_matricula+valor+valor_confirma;
    
    }
    
    public static double RetornaValorConfirmacao( double valor )
    {
        return valor;
    }
    
    public static double RetornaValorMatricula( double valor )
    {
        return valor;
    }
   
    
    public static int ValoresNaoPagos(ObservableList<String> alunosGeral) 
    {
        int preco = 0;
        for( String nome : alunosGeral )
        {
            int codaluno = Estudante.NameToCode(nome);
            String classe = Estudante.GetClasse( codaluno, MesAno.Get_AnoActualCobranca());
            String curso = Estudante.codeToCurso(codaluno);
            
           preco += DefinicoesUnidadePreco.ChangeFromStringToInt(Preco.ValorPreco(classe, Curso.NameToCode(curso), "Propina"));
        }
        
        return preco;
    }
  
    public static  ObservableList<Pagamento> PagamentosReferenteMatriculasAnoActual()
    {   
        ResultSet rs = OperacoesBase.Consultar("select * from pagamento where descricao = 'Matricula' and ano_referencia = '"+MesAno.Get_AnoActualCobranca()+"'");
        ObservableList<Pagamento> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                Pagamento p = new Pagamento();
                p.setCodigo(rs.getInt("codpagamento"));
                p.setValor_apagar(rs.getInt("valor_a_pagar"));
                p.setValor_Pago(rs.getInt("valor_pago"));
                p.setData(DefinicoesData.StringtoLocalDate(rs.getString("data")));
               
               lista.add(p);
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
 /***************************************METODOS DE PAGAMENTO PARA A JANELA DOS VALORES ARRECADADOS ANUAL****/
    
    public static  ObservableList<Pagamento> PagamentosReferente_Descricao_AnoActual_Curso(  String curso, String descricao )
    {   
        ResultSet rs = OperacoesBase.Consultar("select * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join turma using(codturma) inner join curso using(codcurso) where descricao = '"+descricao+"' and nome_curso = '"+curso+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"'");
        ObservableList<Pagamento> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                Pagamento p = new Pagamento();
                p.setCodigo(rs.getInt("codpagamento"));
                p.setValor_apagar(rs.getInt("valor_a_pagar"));
                p.setValor_Pago(rs.getInt("valor_pago"));
                p.setData(DefinicoesData.StringtoLocalDate(rs.getString("data")));
               
               lista.add(p);
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public static  ObservableList<Pagamento> PagamentosReferentePropinaAnoActual_Curso(  String curso )
    {   
        ResultSet rs = OperacoesBase.Consultar("select * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join turma using(codturma) inner join curso using(codcurso) where descricao = 'Propina' and nome_curso = '"+curso+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"'");
        ObservableList<Pagamento> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                Pagamento p = new Pagamento();
                p.setCodigo(rs.getInt("codpagamento"));
                p.setValor_apagar(rs.getInt("valor_a_pagar"));
                p.setValor_Pago(rs.getInt("valor_pago"));
                p.setData(DefinicoesData.StringtoLocalDate(rs.getString("data")));
               
               lista.add(p);
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
     public static  ObservableList<Pagamento> PagamentosReferente_Descricao_Actual_Classe(  String classe , String descricao  )
    {   
        ResultSet rs = OperacoesBase.Consultar("select * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join turma using(codturma) inner join classe using(codclasse) where descricao = '"+descricao+"' and nome_classe = '"+classe+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"'");
        ObservableList<Pagamento> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                Pagamento p = new Pagamento();
                p.setCodigo(rs.getInt("codpagamento"));
                p.setValor_apagar(rs.getInt("valor_a_pagar"));
                p.setValor_Pago(rs.getInt("valor_pago"));
                p.setData(DefinicoesData.StringtoLocalDate(rs.getString("data")));
               
               lista.add(p);
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
     
    public static  ObservableList<Pagamento> PagamentosReferentePropinaAnoActual_Classe(  String classe )
    {   
        ResultSet rs = OperacoesBase.Consultar("select * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join turma using(codturma) inner join classe using(codclasse) where descricao = 'Propina' and nome_classe = '"+classe+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"'");
        ObservableList<Pagamento> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                Pagamento p = new Pagamento();
                p.setCodigo(rs.getInt("codpagamento"));
                p.setValor_apagar(rs.getInt("valor_a_pagar"));
                p.setValor_Pago(rs.getInt("valor_pago"));
                p.setData(DefinicoesData.StringtoLocalDate(rs.getString("data")));
               
               lista.add(p);
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public static ObservableList<String> Lista_Meses_Pagos_por_aluno( int codmatricula )
    {
        ObservableList<String> lista = FXCollections.observableArrayList();
        String descricao = "Propina";
        String ano = MesAno.Get_AnoActualCobranca();
        String sql = "select mes_referente from pagamento where codmatricula_c = '"+codmatricula+"' and descricao = '"+descricao+"' and ano_referencia = '"+ano+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("mes_referente"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pagamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return lista;
        
    }
           
    
    public static boolean Devedor_Aluno( String mes ,  String nome_estudante ,  String curso , String classe , String periodo )
    {
       boolean contem =  ListaAlunosPagaram(mes, curso, classe, periodo).contains(nome_estudante);
       if( !contem )
       {
           int indice_mes_pagamento  = Meses.NameToCode(MesAno.Get_MesActualCobranca());
           int indice_mes_data = DefinicoesData.RetornaMes(DefinicoesData.RetornaDatadoSistema().toString());
           int dia_data = DefinicoesData.RetornaDiaData(DefinicoesData.RetornaDatadoSistema().toString());
           
           //verifica 
           int codmatricula = matricula_confirmacao.CodAlunoToCodMatricula(Estudante.NameToCode(nome_estudante));
           ObservableList<String> meses_nao_pagos =Meses.Listar_Meses_ate_mes_Atual(classe); ;
           meses_nao_pagos.removeAll(Lista_Meses_Pagos_por_aluno(codmatricula));
           if( meses_nao_pagos.size() > 1 )
           {
               int valor_propina = DefinicoesUnidadePreco.ChangeFromStringToInt(Preco.ValorPreco(classe, Curso.NameToCode(curso), "Propina"));
               int valor_multa   = DefinicoesUnidadePreco.ChangeFromStringToInt(Preco.RetornaValor_Multa(classe, Curso.NameToCode(curso)));
               for( String valor_mes : meses_nao_pagos )
               {
                   Devedor d = new Devedor();
                   d.setCodigo(Devedor.LastCode());
                   d.setCodmatricula(codmatricula);
                   d.setMes(valor_mes);
                   d.setValor_propina(valor_propina);
                   d.setValor_multa(valor_multa);
                   
                   if( !validaDevedor.verificaExistencia(codmatricula, valor_mes) )
                       d.Adicionar();
               }
               
               return true;
               
           }
           else
           {
                if( indice_mes_data == (indice_mes_pagamento) && dia_data >=Integer.parseInt(MesAno.Get_DiaMulta()) )
                  return true;
                else
                    return indice_mes_data > indice_mes_pagamento;
               
           }

       } 
       
       return false;
    }

   public static ObservableList<PagaramAlunos> TotalPagamentos( String ano )
   {
        ResultSet rs = OperacoesBase.Consultar("select * from pagamento  where  ano_referencia = '"+ano+"'");
        ObservableList<PagaramAlunos> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                PagaramAlunos a = new PagaramAlunos();
                a.setValor(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("valor_pago")));
                a.setMulta(rs.getString("multa"));
                lista.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pagamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
   }
   
   public static ObservableList<PagaramAlunos> TotalPagamentosMensal( String ano , String mes )
   {
        ResultSet rs = OperacoesBase.Consultar("select * from pagamento  where descricao = 'Propina' and mes_referente = '"+mes+"' and ano_referencia = '"+ano+"'");
        ObservableList<PagaramAlunos> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                PagaramAlunos a = new PagaramAlunos();
                a.setValor(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("valor_pago")));
                a.setMulta(rs.getString("multa"));
                System.out.println(a.getValor());
                lista.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pagamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
   }
   
    public static void InserirEmRelatorioMensal(ObservableList<PagaramAlunos> items) {
        items.stream().map((item) -> "insert into relatoriopagamentomensal values('"+item.getNome()+"','"+item.getCurso()+"','"+item.getClasse()+"','"+item.getPeriodo()+"','"+item.getTurma()+"','"+item.getEfeito()+"','"+item.getMulta()+"','"+item.getValorAPagar()+"','"+item.getValor()+"','"+item.getData()+"');").forEachOrdered((sql) -> {
            OperacoesBase.Inserir(sql);
        });
    }
  
    
}
