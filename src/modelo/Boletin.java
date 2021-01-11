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
public class Boletin {
    
    private int codigo;
    private int codmatricula;
    private String disciplina;
    private String classificacao;
    private String trimestre;
    private String ano;
    private String mac;
    private String cp;
    private String ct;

    public String getCp() {
        return cp;
    }

    public String getCt() {
        return ct;
    }

    public int getCodmatricula() {
        return codmatricula;
    }

    public void setCodmatricula(int codmatricula) {
        this.codmatricula = codmatricula;
    }

    
    public int getCodigo() {
        return codigo;
    }

    public String getClassificacao() {
        return classificacao;
    }
    
    public String getDisciplina() {
        return disciplina;
    }

    public String getMac() {
        return mac;
    }

    public String getAno() {
        return ano;
    }

    public String getTrimestre() {
        return trimestre;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void setTrimestre(String trimestre) {
        this.trimestre = trimestre;
    }
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public void Adicionar()
    {
       String sql = "insert into boletin1_4 values('"+this.getCodigo()+"','"+this.getDisciplina()+"','"+this.getCodmatricula()+"','"+this.getTrimestre()+"', '"+this.getAno()+"', '"+this.getMac()+"', '"+this.getCp()+"','"+this.getCt()+"')";
        OperacoesBase.Inserir(sql);
    }
    
/******************************METODOS OPERACIONAIS***********************************/
     public static int RetornarUltimoCodigo()
    {
        
        ResultSet rs = OperacoesBase.Consultar("select * from boletin1_4");
        int valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codigo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Boletin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++valor;
    }
    
}
