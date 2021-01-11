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
public class FaltaAluno {
    
    private int codigo;
    private int codmatricula;
    private int coddisciplina;
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

    public int getCodmatricula() {
        return codmatricula;
    }

  
    public int getCoddisciplina() {
        return coddisciplina;
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

    public void setCodmatricula(int codmatricula) {
        this.codmatricula = codmatricula;
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
        String sql = "insert into falta_aluno values('"+this.getCodigo()+"', '"+this.getCodmatricula()+"', '"+this.getSituacao()+"', '"+this.getCoddisciplina()+"', '"+this.getData()+"', '"+this.getHora()+"', '"+this.getTrimestre()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
    public int retornaUltimoCodigo()
    {
        String sql = "select codigo from falta_aluno";
        int valor = 0;
        ResultSet rs =  OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codigo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FaltaAluno.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ++valor;
    }
    
/**********************METODOS AUXILIARES**************************************/
    public static int QuantidadeFalta( String anolectivo , int codaluno)
    {
        String situacao_var = "Ausente";
        String sql = "select codigo from view_falta_aluno where situacao = '"+situacao_var+"' and anolectivo='"+anolectivo+"' and codaluno = '"+codaluno+"'";
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
            Logger.getLogger(FaltaAluno.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return quant;
    }
}
