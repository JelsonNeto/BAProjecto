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
public class Activacao {
    
    private int cod_a;
    private int coduser;
    private String inicio;
    private String fim;

    public Activacao(int coduser, String inicio, String fim) {
        this.coduser = coduser;
        this.inicio = inicio;
        this.fim = fim;
    }

    public int getCod_a() {
        return cod_a;
    }

    public void setCod_a(int cod_a) {
        this.cod_a = cod_a;
    }

    
    
    public int getCoduser() {
        return coduser;
    }

    public void setCoduser(int coduser) {
        this.coduser = coduser;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }
    
    
    public boolean Adicionar()
    {
        String sql = "insert into activacao values('"+this.getCoduser()+"','"+this.getInicio()+"','"+this.getFim()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
    public boolean Actualizar_Fim()
    {
        String sql = "update activacao set fim  = '"+fim+"' where inicio= '"+inicio+"' and coduser = '"+coduser+"'";
        return OperacoesBase.Actualizar(sql);
    }
    
    public static int Retorna_UltimoCod_a()
    {
        String sql = "select cod_a from activacao";
        ResultSet rs = OperacoesBase.Consultar(sql);
        int cod = 0;
        try {
            while( rs.next() )
            {
                cod = rs.getInt("cod_a");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Activacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cod;
    }
}
