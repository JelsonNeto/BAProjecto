/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Bd.OperacoesBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Familia Neto
 */
public class Meses {
    
    private int codmes;
    private String mes;

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
    
    
   //
    public static String ObterMes( int codmes )
    {
        String sql = "select mes from  meses where codmes = '"+codmes+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                return rs.getString("mes");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Meses.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
   public static int NameToCode( String mes )
    {
        String sql = "select codmes from  meses where mes = '"+mes+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                return rs.getInt("codmes");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Meses.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
   
   public static String Nome_Mes_toDoisDigitos ( String mes )
    {
        String sql = "select codmes from  meses where mes = '"+mes+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                if( mes.equals("Outubro") || mes.equals("Novembro") || mes.equals("Dezembro"))
                   return ""+rs.getInt("codmes");
                else
                   return 0+""+rs.getInt("codmes");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Meses.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "0";
    }
   
   public static ObservableList<String> Listar_Meses_Filtra_Classe_Exame( String classe )
   {
       ObservableList<String> lista = FXCollections.observableArrayList();
       ResultSet rs = OperacoesBase.Consultar("select  * from meses order by codmes");
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("mes"));
            }} catch (SQLException ex) {
            Logger.getLogger(Meses.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lista.remove("Janeiro");
        lista.removeAll(Classe.Meses_ClasseExame(classe));
        return lista;
                
   }
   
   public static ObservableList<String> Listar_Meses_ate_mes_Atual( String classe )
   {
       ObservableList<String> lista = FXCollections.observableArrayList();
       ResultSet rs = OperacoesBase.Consultar("select * from meses");
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("mes"));
            }} catch (SQLException ex) {
            Logger.getLogger(Meses.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lista.subList(Meses.NameToCode(MesAno.Get_MesActualCobranca())-1,lista.size()).clear();
        return lista;
   }
   
    public static ObservableList<String> Listar_Meses()
   {
       ObservableList<String> lista = FXCollections.observableArrayList();
       ResultSet rs = OperacoesBase.Consultar("select * from meses order by codmes");
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("mes"));
            }} catch (SQLException ex) {
            Logger.getLogger(Meses.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
   }
   
}
