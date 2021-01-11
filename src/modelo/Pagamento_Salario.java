/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Bd.OperacoesBase;
import definicoes.DefinicoesData;
import definicoes.DefinicoesUnidadePreco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Familia Neto
 */
public class Pagamento_Salario {
    
    private int codpagamento;
    private int codfuncionario;
    private int codsalario;
    private int coduser;
    private int codmes;
    private LocalDate data;
    private String anolectivo;
    private String valor;
    private String bonus;
    private String motivo_desconto;
    private String desconto_falta;
    private String motivo_bonus;
    private String nomefuncionario;
    private String mes;
    private String funcao;
    private String desconto;
    private String salario_base;

    public Pagamento_Salario() {
    }

    public String getDesconto_falta() {
        return desconto_falta;
    }

    public void setDesconto_falta(String desconto_falta) {
        this.desconto_falta = desconto_falta;
    }

    public String getMotivo_bonus() {
        return motivo_bonus;
    }

    public void setMotivo_bonus(String motivo_bonus) {
        this.motivo_bonus = motivo_bonus;
    }

    public Pagamento_Salario(int codpagamento, int codfuncionario, int codsalario, int coduser, int codmes, LocalDate data, String anolectivo, String valor, String bonus, String motivo , String desconto , String salario_base) {
        this.codpagamento = codpagamento;
        this.codfuncionario = codfuncionario;
        this.codsalario = codsalario;
        this.coduser = coduser;
        this.codmes = codmes;
        this.data = data;
        this.anolectivo = anolectivo;
        this.valor = valor;
        this.bonus = bonus;
        this.motivo_desconto =motivo;
        this.desconto = desconto;
        this.salario_base = salario_base;
    }

    public String getFuncao() {
        return funcao;
    }

    public String getDesconto() {
        return desconto;
    }

    public void setDesconto(String desconto) {
        this.desconto = desconto;
    }

    
    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getBonus() {
        return bonus;
    }

    public String getMotivo_desconto() {
        return motivo_desconto;
    }

    public void setMotivo_desconto(String motivo_desconto) {
        this.motivo_desconto = motivo_desconto;
    }

    

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

   
 

    public int getCodpagamento() {
        return codpagamento;
    }

    public void setCodpagamento(int codpagamento) {
        this.codpagamento = codpagamento;
    }

    public int getCodfuncionario() {
        return codfuncionario;
    }

    public void setCodfuncionario(int codfuncionario) {
        this.codfuncionario = codfuncionario;
    }

    public int getCodsalario() {
        return codsalario;
    }

    public void setCodsalario(int codsalario) {
        this.codsalario = codsalario;
    }

    public int getCoduser() {
        return coduser;
    }

    public void setCoduser(int coduser) {
        this.coduser = coduser;
    }

    public int getCodmes() {
        return codmes;
    }

    public void setCodmes(int codmes) {
        this.codmes = codmes;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getNomefuncionario() {
        return nomefuncionario;
    }

    public void setNomefuncionario(String nomefuncionario) {
        this.nomefuncionario = nomefuncionario;
    }

   
    

    public String getAnolectivo() {
        return anolectivo;
    }

    public void setAnolectivo(String anolectivo) {
        this.anolectivo = anolectivo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getSalario_base() {
        return salario_base;
    }

    public void setSalario_base(String salario_base) {
        this.salario_base = salario_base;
    }
    
    
    
    
    public boolean Adicionar()
    {
        String sql = "insert into pagamento_salario values('"+this.getCodpagamento()+"','"+this.getCodsalario()+"','"+this.getCodfuncionario()+"','"+this.getCoduser()+"','"+this.getAnolectivo()+"','"+this.getCodmes()+"','"+this.getValor()+"','"+this.getData()+"','"+this.getBonus()+"','"+this.getDesconto()+"','"+this.getMotivo_desconto()+"','"+this.getSalario_base()+"','"+this.getDesconto_falta()+"','"+this.getMotivo_bonus()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
    public static int Obter_ultimo_mes_pago( int codfuncionario , String ano_lectivo )
    {
        int cod = 1;
        String sql = "select codmes from pagamento_salario  where codfuncionario = '"+codfuncionario+"' and ano_lectivo = '"+ano_lectivo+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while(rs.next() )
            {
                cod = rs.getInt("codmes");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pagamento_Salario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++cod; 
    }
    
      public static int Ultimocodigo()
    {
         int codigo = 0;
        String sql = "select codpagamento from pagamento_salario";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                codigo = rs.getInt("codpagamento");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pagamento_Salario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++codigo;
    }
      
    public static ObservableList<Pagamento_Salario> ListarTodos()
    {
        String sql  = "select * from pagamento_salario inner join funcionario using(codfuncionario) inner join meses using(codmes) inner join salario_funcionario using(codsalario)";
        ObservableList<Pagamento_Salario> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                Pagamento_Salario ps = new Pagamento_Salario();
                ps.setCodpagamento(rs.getInt("codpagamento"));
                ps.setCodfuncionario(rs.getInt("codfuncionario"));
                ps.setNomefuncionario(rs.getString("nome"));
                ps.setMes(rs.getString("mes"));
                ps.setBonus(rs.getString("bonus"));
                ps.setDesconto(rs.getString("desconto"));
                ps.setSalario_base(rs.getString("salario_base"));
                ps.setData(DefinicoesData.StringtoLocalDate(rs.getString("data_pagamento")));
                ps.setValor(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("valor")));
                ps.setFuncao(rs.getString("funcao"));
                ps.setCoduser(rs.getInt("coduser"));
                ps.setMotivo_desconto(rs.getString("motivo_desconto"));
                ps.setMotivo_bonus(rs.getString("motivo_bonus"));
                
                lista.add(ps);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pagamento_Salario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    public static ObservableList<Pagamento_Salario> ListarTodos_Parametro( String sql )
    {
        ObservableList<Pagamento_Salario> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                Pagamento_Salario ps = new Pagamento_Salario();
                ps.setCodpagamento(rs.getInt("codpagamento"));
                ps.setCodfuncionario(rs.getInt("codfuncionario"));
                ps.setNomefuncionario(rs.getString("nome"));
                ps.setMes(rs.getString("mes"));
                ps.setBonus(rs.getString("bonus"));
                ps.setDesconto(rs.getString("desconto"));
                ps.setSalario_base(rs.getString("salario_base"));
                ps.setData(DefinicoesData.StringtoLocalDate(rs.getString("data_pagamento")));
                ps.setValor(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("valor")));
                ps.setFuncao(rs.getString("funcao"));
                ps.setCoduser(rs.getInt("coduser"));
                ps.setMotivo_desconto(rs.getString("motivo_desconto"));
                ps.setMotivo_bonus(rs.getString("motivo_bonus"));
                
                lista.add(ps);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pagamento_Salario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public static ObservableList<Pagamento_Salario> ListarTodos_pagamentos( String ano )
    {
        String sql  = "select * from pagamento_salario inner join funcionario using(codfuncionario) inner join meses using(codmes) inner join salario_funcionario using(codsalario) where ano_lectivo = '"+ano+"'";
        ObservableList<Pagamento_Salario> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                Pagamento_Salario ps = new Pagamento_Salario();
                ps.setCodpagamento(rs.getInt("codpagamento"));
                ps.setNomefuncionario(rs.getString("nome"));
                ps.setMes(rs.getString("mes"));
                ps.setData(DefinicoesData.StringtoLocalDate(rs.getString("data_pagamento")));
                ps.setValor(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("valor")));
                ps.setFuncao(rs.getString("funcao"));
                
                lista.add(ps);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pagamento_Salario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public static ObservableList<Pagamento_Salario> ListarTodos_pagamentosMensal( String ano, String mes )
    {
        String sql  = "select * from pagamento_salario inner join funcionario using(codfuncionario) inner join meses using(codmes) inner join salario_funcionario using(codsalario) where codmes = '"+mes+"' and  ano_lectivo = '"+ano+"'";
        ObservableList<Pagamento_Salario> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                Pagamento_Salario ps = new Pagamento_Salario();
                ps.setCodpagamento(rs.getInt("codpagamento"));
                ps.setNomefuncionario(rs.getString("nome"));
                ps.setMes(rs.getString("mes"));
                ps.setData(DefinicoesData.StringtoLocalDate(rs.getString("data_pagamento")));
                ps.setValor(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("valor")));
                ps.setFuncao(rs.getString("funcao"));
                
                lista.add(ps);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pagamento_Salario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    
    public static int TotalSalario_por_funcao( String funcao , String ano_lectivo )
    {
        int soma = 0;
        String sql = "select valor from pagamento_salario inner join funcionario using(codfuncionario) where funcao = '"+funcao+"' and ano_lectivo = '"+ano_lectivo+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                soma+=rs.getInt("valor");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pagamento_Salario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return soma;
    }
    
     public static int TotalSalario_por_funcao( String funcao , String mes,  String ano_lectivo )
    {
        int soma = 0;
        String sql = "select valor from pagamento_salario inner join funcionario using(codfuncionario) where funcao = '"+funcao+"' and codmes = '"+Meses.NameToCode(mes)+"' and ano_lectivo = '"+ano_lectivo+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                soma+=rs.getInt("valor");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pagamento_Salario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return soma;
    }
    
      
    
   
}
