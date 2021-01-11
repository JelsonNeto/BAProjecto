/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Bd;

import java.sql.ResultSet;

/**
 *
 * @author Familia Neto
 */
public class OperacoesBase {
    
   
    public static boolean Inserir(  String sql )
    {
      return conexao.NonResultSetQuery(sql) != 0;
        
    }
    
    public static boolean Eliminar( String sql )
    {
        return conexao.NonResultSetQuery(sql) != 0;
    }
    
    public static ResultSet Consultar( String sql )
    {
          return conexao.ResultSetQuery(sql);
    }
    
    public static boolean Actualizar( String sql )
    {
        return conexao.NonResultSetQuery(sql) != 0;
    }
    
}
