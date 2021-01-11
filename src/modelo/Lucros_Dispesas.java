/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Bd.OperacoesBase;
import definicoes.DefinicoesUnidadePreco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Familia Neto
 */
public class Lucros_Dispesas {
    
    private static int arrecadado;
    private static int dispesa;
    private static int lucro;
    private String mes;
    private String lucro_s;
    private String dispesa_s;
    private String receita_s;
    private int lucro_i;
    private int dispesa_i;
    private int receita_i;

    public String getLucro_s() {
        return lucro_s;
    }

    public int getDispesa_i() {
        return dispesa_i;
    }

    public void setDispesa_i(int dispesa_i) {
        this.dispesa_i = dispesa_i;
    }

    public int getReceita_i() {
        return receita_i;
    }

    public void setReceita_i(int receita_i) {
        this.receita_i = receita_i;
    }
    
    
    public void setLucro_s(String lucro_s) {
        this.lucro_s = lucro_s;
    }

    public String getDispesa_s() {
        return dispesa_s;
    }

    public void setDispesa_s(String dispesa_s) {
        this.dispesa_s = dispesa_s;
    }

    public int getLucro_i() {
        return lucro_i;
    }

    public void setLucro_i(int lucro_i) {
        this.lucro_i = lucro_i;
    }
    
    

    public String getReceita_s() {
        return receita_s;
    }

    public void setReceita_s(String receita_s) {
        this.receita_s = receita_s;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
    
    
    
 
 //======================Metodos operacionais==========================================//
    
    public static String Total_Receita( String mes , String ano )
    {
        String sql ="select sum(valor_pago) as valor , sum(multa) as multa from view_lista_pagamento where descricao = 'Propina' and mes_referente = '"+mes+"' and ano_referencia = '"+ano+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                arrecadado = rs.getInt("valor")+rs.getInt("multa");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Lucros_Dispesas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+arrecadado);
    }
    
    public static String Total_Dispesas( String mes , String ano )
    {
        String sql ="select sum(valor) as valor from pagamento_salario where codmes ='"+Meses.Nome_Mes_toDoisDigitos(mes)+"'  and ano_lectivo = '"+ano+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
               dispesa  = rs.getInt("valor");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Lucros_Dispesas.class.getName()).log(Level.SEVERE, null, ex);
        }
        Saida.CarregaDadosTabela_por_Ano_Mes(Meses.Nome_Mes_toDoisDigitos(mes),ano);
        dispesa += Saida.valorTotalInteger();
        return DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+dispesa);
    }
    
    public static int Total_Dispesas_Integer( String mes , String ano )
    {
        int valor = 0;
        String sql ="select sum(valor) as valor from pagamento_salario where codmes ='"+Meses.Nome_Mes_toDoisDigitos(mes)+"'  and ano_lectivo = '"+ano+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
               valor  = rs.getInt("valor");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Lucros_Dispesas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valor;
    }
    
    public static int Total_Receita_Integer( String mes , String ano )
    {
        int valor = 0;
        String sql ="select sum(valor_pago) as valor , sum(multa) as multa from view_lista_pagamento where mes_referente = '"+mes+"' and ano_referencia = '"+ano+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                valor = rs.getInt("valor")+rs.getInt("multa");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Lucros_Dispesas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valor;
    }
    
    public static int lucro_Integer( String mes, String ano )
    {
        int arr = Total_Receita_Integer(mes, ano);
        int dis = Total_Dispesas_Integer(mes, ano);
        
        return arr - dis;
    }

    
    public static String Total()
    {
        lucro = arrecadado-dispesa;
        return DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+lucro);
    }

    public static int getLucro() {
        return lucro;
    }
    
     public  static String TotalSaida(  String ano )
     {
         Saida.CarregaDadosTabela_por_Ano(ano);
         return Saida.valorTotal();
     }
     
      public  static int TotalSaida_Int(  String ano )
     {
         Saida.CarregaDadosTabela_por_Ano(ano);
         return Saida.valorTotalInteger();
     }
    
    
    
}
