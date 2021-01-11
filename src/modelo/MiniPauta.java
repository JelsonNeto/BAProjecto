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
public class MiniPauta {
    
    private String nomeProfessor; //criado para ajudar no certificado
    private int codmatricula_c;
    private String disciplina;
    private int coddisciplina;
    private String nome;
    private String mac1;
    private String mac2;
    private String mac3;
    private String cp1;
    private String cp2;
    private String cp3;
    private String ct1;
    private String ct2;
    private String ct3;

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }
    private String cap;
    private String ce;
    private String cf;

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMac1() {
        return mac1;
    }

    public int getCodmatricula_c() {
        return codmatricula_c;
    }
    
    

    public void setMac1(String mac1) {
        this.mac1 = mac1;
    }

    public String getMac2() {
        return mac2;
    }

    public int getCoddisciplina() {
        return coddisciplina;
    }

    public void setCoddisciplina(int coddisciplina) {
        this.coddisciplina = coddisciplina;
    }

    public void setMac2(String mac2) {
        this.mac2 = mac2;
    }

    public String getMac3() {
        return mac3;
    }

    public void setMac3(String mac3) {
        this.mac3 = mac3;
    }

    public String getCp1() {
        return cp1;
    }

    public void setCp1(String cp1) {
        this.cp1 = cp1;
    }

    public String getCp2() {
        return cp2;
    }

    public void setCp2(String cp2) {
        this.cp2 = cp2;
    }

    public String getCp3() {
        return cp3;
    }

    public void setCp3(String cp3) {
        this.cp3 = cp3;
    }

    public String getCt1() {
        return ct1;
    }

    public void setCt1(String ct1) {
        this.ct1 = ct1;
    }

    public String getCt2() {
        return ct2;
    }

    public void setCt2(String ct2) {
        this.ct2 = ct2;
    }

    public String getCt3() {
        return ct3;
    }

    public void setCt3(String ct3) {
        this.ct3 = ct3;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCe() {
        return ce;
    }

    public void setCodmatricula_c(int codmatricula_c) {
        this.codmatricula_c = codmatricula_c;
    }

    
    
    public void setCe(String ce) {
        this.ce = ce;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }
    
    public boolean adicionar()
    {
        String sql = "insert into minipauta values('"+this.getCodmatricula_c()+"', '"+this.getCoddisciplina()+"', '"+this.getMac1()+"' , '"+this.getMac2()+"', '"+this.getMac3()+"', '"+this.getCp1()+"', '"+this.getCp2()+"','"+this.getCp3()+"','"+this.getCt1()+"','"+this.getCt2()+"', '"+this.getCt3()+"','"+this.getCap()+"', '"+this.getCe()+"', '"+this.getCf()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
    public static String retornaCap( int codmatricula , int coddisciplina , String anolectivo )
    {
        String sql = "select cap from minipauta inner join matricula_confirmacao using(codmatricula_c) where codmatricula_c = '"+codmatricula+"' and coddisciplina = '"+coddisciplina+"' and anolectivo = '"+anolectivo+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                return rs.getString("cap");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MiniPauta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "-";
    }
    
     public static String retornaCE( int codmatricula , int coddisciplina , String anolectivo )
    {
        String sql = "select ce from minipauta inner join matricula_confirmacao using(codmatricula_c) where codmatricula_c = '"+codmatricula+"' and coddisciplina = '"+coddisciplina+"' and anolectivo = '"+anolectivo+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                return rs.getString("ce");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MiniPauta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "-";
    }
     
      public static String retornaCF( int codmatricula , int coddisciplina , String anolectivo )
    {
        String sql = "select cf from minipauta inner join matricula_confirmacao using(codmatricula_c) where codmatricula_c = '"+codmatricula+"' and coddisciplina = '"+coddisciplina+"' and anolectivo = '"+anolectivo+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                return rs.getString("cf");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MiniPauta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "-";
    }
    
    public static boolean JaExisteMiniPautaNaBd( int coddisciplina )
    {
        String sql = "select count(*) as quantidade from minipauta where coddisciplina = '"+coddisciplina+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        int valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("quantidade");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MiniPauta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return  valor != 0;
    }
    
    public static double retorna_ct1( int coddisciplina , int codmatricula )
    {
        String sql = "select ct1  from minipauta where coddisciplina = '"+coddisciplina+"' and codmatricula_c = '"+codmatricula+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        double valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("ct1");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MiniPauta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return  valor;
    }
    
     public static double retorna_ct2( int coddisciplina , int codmatricula )
    {
        String sql = "select ct2  from minipauta where coddisciplina = '"+coddisciplina+"' and codmatricula_c = '"+codmatricula+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        double valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("ct2");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MiniPauta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return  valor;
    }
     
    public static double retorna_ct3( int coddisciplina , int codmatricula )
    {
        String sql = "select ct3  from minipauta where coddisciplina = '"+coddisciplina+"' and codmatricula_c = '"+codmatricula+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        double valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("ct3");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MiniPauta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return  valor;
    }
    
}
