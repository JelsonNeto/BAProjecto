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
public class Configuracao {
    
    private String fotografia;
    private String nomeescola;
    private String tipoescola;
    private String tipo_nivel;
    private String nome_relatorios;
    private String slogan;
    private String localizacao;
    private String telefone;
    private String email;
    private String iban;
    private String nif;

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getFotografia() {
        return fotografia;
    }

    public String getNome_relatorios() {
        return nome_relatorios;
    }

    public void setNome_relatorios(String nome_relatorios) {
        this.nome_relatorios = nome_relatorios;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public String getNomeescola() {
        return nomeescola;
    }

    public void setNomeescola(String nomeescola) {
        this.nomeescola = nomeescola;
    }

    public String getTipoescola() {
        return tipoescola;
    }

    public void setTipoescola(String tipoescola) {
        this.tipoescola = tipoescola;
    }

    public String getTipo_nivel() {
        return tipo_nivel;
    }

    public void setTipo_nivel(String tipo_nivel) {
        this.tipo_nivel = tipo_nivel;
    }
    
    
    public boolean Adicionar()
    {
        OperacoesBase.Eliminar("truncate table configuracao_sistema");
        String sql = "insert into configuracao_sistema values('"+this.getFotografia()+"' , '"+this.getTipoescola()+"' , '"+this.getNomeescola()+"', '"+this.getTipo_nivel()+"' , '"+this.getNome_relatorios()+"','"+getSlogan()+"','"+getLocalizacao()+"','"+getTelefone()+"','"+getEmail()+"','"+getIban()+"','"+getNif()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
    public boolean Actualiza_Nif_Iban()
    {
        String sql = "update configuracao_sistema set nif = '"+getNif()+"' , iban = '"+getIban()+"'";
        return OperacoesBase.Actualizar(sql);
    }
    
     public static Configuracao DadosConfiguracao()
     {
          String sql = "select * from configuracao_sistema";
          ResultSet rs= OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                
                Configuracao c = new Configuracao();
                c.setFotografia(rs.getString("fotografia"));
                c.setNomeescola(rs.getString("nome_escola"));
                c.setTipoescola(rs.getString("tipo_escola"));
                c.setTipo_nivel(rs.getString("nivel"));
                c.setNome_relatorios(rs.getString("nomerelatorios"));
                return c;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Configuracao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
     }
     
     public static boolean ConfiguracaoJaFeita()
     {
           Configuracao con = DadosConfiguracao();
           if( con == null )
               return false;
           return !con.getNomeescola().equalsIgnoreCase("") && !con.getFotografia().equalsIgnoreCase("") && !con.getNome_relatorios().equalsIgnoreCase("") && !con.getTipoescola().equalsIgnoreCase("") && !con.getTipo_nivel().equalsIgnoreCase("");
     }
}
