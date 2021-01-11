/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Validacoes;

import Bd.OperacoesBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Devedor;

/**
 *
 * @author Familia Neto
 */
public class validaDevedor {
    
    
    public static boolean verificaExistencia( int codmatricula )
    {
        
        String sql = "select * from Devedor where codmatricula_c = '"+codmatricula+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        int codigo = 0;
        try {
            while( rs.next() )
            {
                codigo = rs.getInt("codigo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(validaDevedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return  codigo != 0;
        
    }
    
    public static boolean verificaExistencia( int codmatricula , String mes )
    {
        
        String sql = "select * from Devedor where codmatricula_c = '"+codmatricula+"' and mes = '"+mes+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        int codigo = 0;
        try {
            while( rs.next() )
            {
                codigo = rs.getInt("codigo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(validaDevedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return  codigo != 0;
        
    }
    
}
