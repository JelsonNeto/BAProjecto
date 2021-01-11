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

/**
 *
 * @author Familia Neto
 */
public class Licenca {
    
    private String licenca;
    private String tipo;
    private String dataExpiracao;

    
    public String getLicenca() {
        return licenca;
    }

    public void setLicenca(String licenca) {
        this.licenca = licenca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(String dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }
    
    
/************METODOS OPERACIONAI
     * @return S**********************************************/
   public static Licenca RetornaLicenca()
   {
       ResultSet rs = OperacoesBase.Consultar("select * from licenca");
        try {
            while( rs.next())
            {
                Licenca l = new Licenca();
                l.setTipo(rs.getString("tipolicenca"));
                l.setDataExpiracao(rs.getString("dataexpiracao"));
                l.setLicenca(rs.getString("codlicenca"));
                
                return l;
            }} catch (SQLException ex) {
            Logger.getLogger(Licenca.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
   }
}
