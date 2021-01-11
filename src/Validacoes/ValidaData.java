/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validacoes;

import Bd.OperacoesBase;
import definicoes.DefinicoesData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.MesAno;

/**
 *
 * @author Familia Neto
 */
public class ValidaData {

    public static boolean AnoDataCorrecto(String valueOf , String efeito) 
    {
        if( "Confirmação".equalsIgnoreCase(efeito) )
        {
            int ano_data_pagamento = Integer.parseInt(DefinicoesData.RetornaAnoData(valueOf));
            int ano_data_cobranca = Integer.parseInt(DefinicoesData.RetornaAnoData(MesAno.Get_AnoActualCobranca()));
            
            if( ano_data_cobranca >= (ano_data_pagamento-1) )
                return true;
        }
        return DefinicoesData.RetornaAnoData(valueOf).equalsIgnoreCase(MesAno.Get_AnoActualCobranca());
        
    }

    public static boolean DataExistenteIncorrecta(String valueOf) 
    {
          
        String sql = "select data from pagamento where ano_referencia  = '"+MesAno.Get_AnoActualCobranca()+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                
                if( valueOf.equalsIgnoreCase(rs.getString("data")))
                    return false;
                else
                    return !((DefinicoesData.RetornaDiaData(rs.getString("data")) >= DefinicoesData.RetornaDiaData(valueOf)) && (DefinicoesData.RetornaMes(rs.getString("data")) == DefinicoesData.RetornaMes(valueOf)));
                        
                                                 
            }
        } catch (SQLException ex) {
            Logger.getLogger(ValidaData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
}
