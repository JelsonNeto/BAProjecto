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
public class NaoPagaramTurmaMes {

    private int codigo;
    private String curso;
    private String turma;
    private String nome;
    private int valor;
    private String classe;
    private String mes;

    public String getClasse() {
        return classe;
    }

    public String getCurso() {
        return curso;
    }

    public String getMes() {
        return mes;
    }

    public int getCodigo() {
        return codigo;
    }

    
    
    public String getNome() {
        return nome;
    }

    public String getTurma() {
        return turma;
    }

    public int getValor() {
        return valor;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    
    
    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
    
     public static int LastCodeRecibo()
    {
       
        String sql = "select codrelatorio from relatorioAlunosNaoPagaramMensal";
       ResultSet rs =  OperacoesBase.Consultar(sql);
       int valor = 0;
        try {
            while( rs.next()  )
            {
                valor = rs.getInt("codrelatorio");
            }} catch (SQLException ex) {
            Logger.getLogger(NaoPagaramTurmaMes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ++valor ;
    }
     
}
