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
import Controlador.Pagamento.PagamentosematrasoController;

/**
 *
 * @author Familia Neto
 */
public class MesAno {
    
    private String mes;
    private String ano;
    private String dia;
    

    public String getAno() {
        return ano;
    }

    public String getMes() {
        return mes;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
    
    
    
    
    public void Adicionar_Mes()
    {
        Eliminar_Mes();
        OperacoesBase.Inserir("insert into mes_cobranca values( '"+this.getMes()+"' )");
    }
    
    
    public void Adicionar_Ano()
    {
        Eliminar_Ano();
        OperacoesBase.Inserir("insert into ano_cobranca values( '"+this.getAno()+"' )");
    }
    
    public void Adicionar_dia()
    {
        Eliminar_Dia();
        OperacoesBase.Inserir("insert into dia_multa values( '"+this.getDia()+"' )");
    }
    
    
    /**
     * 
     * @return 
     */
    private boolean Eliminar_Mes()
    {
        
       boolean  v =  OperacoesBase.Eliminar("delete from mes_cobranca");
       return v; 
    }//fim do metodo
    
   /**
    * 
    * @return 
    */ 
    private boolean Eliminar_Ano()
    {
        
       boolean  v =  OperacoesBase.Eliminar("delete from ano_cobranca");
       return v; 
    }
    
    
     private boolean Eliminar_Dia()
    {
        
       boolean  v =  OperacoesBase.Eliminar("delete from dia_multa");
       return v; 
    }
    
    
    public static String Get_MesActualCobranca()
    {
        ResultSet rs = OperacoesBase.Consultar("select mes from mes_cobranca");
        String mes = "";
        try {
            while( rs.next() )
            {
                mes = rs.getString("mes");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return mes;
   
    }
    
    
    public static String Get_AnoActualCobranca()
    {
        ResultSet rs = OperacoesBase.Consultar("select ano from ano_cobranca");
        String ano = "";
        try {
            while( rs.next() )
            {
                ano = rs.getString("ano");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return ano;
    }
    
     public static String Get_DiaMulta()
    {
        ResultSet rs = OperacoesBase.Consultar("select dia from dia_multa");
        String ano = "";
        try {
            while( rs.next() )
            {
                ano = rs.getString("dia");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return ano;
    }
    
     public static int  IndiceMesActual( String mes[] )
    {
        String mes_actual_cobranca = Get_MesActualCobranca();
        int indice = -1;
        for( int i = 0 ; i < mes.length ; i++ )
        {
            if( mes[i].equalsIgnoreCase(mes_actual_cobranca))
            {
                indice = i;
                break;
            }
           
        }
        
        return indice;
          
    }
    
}
