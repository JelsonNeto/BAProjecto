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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Familia Neto
 */
public class Devedor {
    
    private int codigo;
    private int codaluno;
    private int codmatricula;
    private String mes;
    private int valor_multa;
    private int valor_propina;

    public int getCodaluno() {
        return codaluno;
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

    public void setCodaluno(int codaluno) {
        this.codaluno = codaluno;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMes() {
        return mes;
    }

    public int getValor_multa() {
        return valor_multa;
    }

    public int getValor_propina() {
        return valor_propina;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public void setValor_multa(int valor_multa) {
        this.valor_multa = valor_multa;
    }

    public void setValor_propina(int valor_propina) {
        this.valor_propina = valor_propina;
    }
    
    
    
    public void Adicionar()
    {
        String sql = "insert into Devedor values('"+this.getCodigo()+"' , '"+this.getCodmatricula()+"', '"+getMes()+"', '"+getValor_multa()+"','"+getValor_propina()+"')";
        OperacoesBase.Inserir(sql);
    }
    
    public static int LastCode()
    {
        String sql = "select * from Devedor";
        ResultSet rs = OperacoesBase.Consultar(sql);
        int codigo = 0;
        try {
            while( rs.next() )
            {
                codigo = rs.getInt("codigo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Devedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return  ++codigo;
    }
    /**
     * Descrição: Lista os nomes de todos os alunos que têm dividas, filtrando pela classe e o curso.
     * @param classe
     * @param curso
     * @return ObservableList<String> que contém os nomes dos estudantes cadastrados na tabela divida.
     */
     public static ObservableList<String> FiltraEstudantes( String classe, String curso )
    {
        ResultSet rs = OperacoesBase.Consultar("select distinct nome from aluno inner join matricula_confirmacao using(codaluno) inner join Devedor using(codmatricula_c) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where nome_curso = '"+curso+"' and nome_classe = '"+classe+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"'");
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Devedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
        
    }//fim do metodo
    
     public static boolean EDevedor( int codmatricula )
    {
        String sql = "select * from Devedor where codmatricula_c = '"+codmatricula+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        int codigo = 0;
        try {
            while( rs.next() )
            {
                codigo = rs.getInt("codigo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Devedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return  codigo != 0;
    }
    
}
