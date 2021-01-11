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
public class EmitirRecibo {
    
    private String mes;
    private String valor;
    private String preco;
    private String descricao;
    private String multa_S;
    private int multa;
    private int quant;

    public String getDescricao() {
        return descricao;
    }

    public String getMes() {
        return mes;
    }

    public String getPreco() {
        return preco;
    }

    public int getQuant() {
        return quant;
    }

    public String getMulta_S() {
        return multa_S;
    }

    
    public String getValor() {
        return valor;
    }

    public int getMulta() {
        return multa;
    }

    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public void setMulta_S(String multa_S) {
        this.multa_S = multa_S;
    }
    

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public void setMulta(int multa) {
        this.multa = multa;
    }

    
    
    public void setValor(String valor) {
        this.valor = valor;
    }
    
    
/******************************************Metodo auxiliar da tabeala recibo **********************/
    public static int LastCodeRecibo()
    {
       
        String sql = "select codrecibo from recibo";
       ResultSet rs =  OperacoesBase.Consultar(sql);
       int valor = 0;
        try {
            while( rs.next()  )
            {
                valor = rs.getInt("codrecibo");
            }} catch (SQLException ex) {
            Logger.getLogger(EmitirRecibo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ++valor ;
    }
    
}
