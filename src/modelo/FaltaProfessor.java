/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Bd.OperacoesBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Familia Neto
 */
public class FaltaProfessor {
    
     
    private int codigo;
    private int codprofessor;
    private int coddisciplina;
    private int codmes;
    private String mes;
    private String nome;
    private String disciplina;
    private String trimestre;
    private String ano;
    private String situacao;
    private LocalDate data;
    private String hora;

    public String getAno() {
        return ano;
    }

    public int getCodprofessor() {
        return codprofessor;
    }

  
    public int getCoddisciplina() {
        return coddisciplina;
    }

    public int getCodmes() {
        return codmes;
    }

    public String getMes() {
        return mes;
    }

    public void setCodmes(int codmes) {
        this.codmes = codmes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    
    public int getCodigo() {
        return codigo;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public LocalDate getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }

    
    
    public String getNome() {
        return nome;
    }

    public String getSituacao() {
        return situacao;
    }
    
    

    public String getTrimestre() {
        return trimestre;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void setCodprofessor(int codprofessor) {
        this.codprofessor = codprofessor;
    }


    public void setCoddisciplina(int coddisciplina) {
        this.coddisciplina = coddisciplina;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    
    

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTrimestre(String trimestre) {
        this.trimestre = trimestre;
    }
            
    
    public boolean Adicionar()
    {
        String sql = "insert into falta_professor values('"+this.getCodigo()+"', '"+this.getCodprofessor()+"', '"+this.getSituacao()+"', '"+this.getCoddisciplina()+"', '"+this.getData()+"', '"+this.getHora()+"', '"+this.getTrimestre()+"', '"+this.getAno()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
    public int retornaUltimoCodigo()
    {
        String sql = "select codigo from falta_professor";
        int valor = 0;
        ResultSet rs =  OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codigo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FaltaProfessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ++valor;
    }
    
/**********************METODOS AUXILIARES**************************************/
    public static int QuantidadeFalta()
    {
        String situacao_var = "Ausente";
        String sql = "select codigo from falta_professor where situacao = '"+situacao_var+"'";
        int valor = 0;
        int quant= 0;
        ResultSet rs =  OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codigo");
                quant++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FaltaProfessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return quant;
    }
    
   
   
}
