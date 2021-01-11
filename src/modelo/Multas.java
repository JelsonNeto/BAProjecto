/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Bd.OperacoesBase;
import static Validacoes.Valida_UsuarioActivo.Definicoes;
import definicoes.DefinicoesUnidadePreco;
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
public class Multas {
    
    private String curso;
    private String classe;
    private String turma;
    private String  valor;
    private String mes;
    private static int soma;


    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
    
    

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    
    
    public static ObservableList<Multas> Lista_Multas( String mes , String ano )
    {
        soma = 0;
        String sql = "select * from view_lista_pagamento where descricao = 'Propina' and mes_referente = '"+mes+"' and ano_referencia = '"+ano+"'";
        ObservableList<Multas> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next())
            {
                Multas m  = new Multas();
                m.setCurso(rs.getString("nome_curso"));
                m.setClasse(rs.getString("nome_classe"));
                m.setTurma(rs.getString("nome_turma"));
                m.setMes(rs.getString("mes"));
                m.setValor(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+rs.getInt("multa")));
                soma += rs.getInt("multa");
                lista.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Multas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public static ObservableList<Multas> Lista_Multas_Intervalo( int mes_i , int mes_f , String ano )
    {
        soma = 0;
        String sql = "select * from view_lista_pagamento where descricao = 'Propina' and codmes between '"+mes_i+"' and '"+mes_f+"'";
        ObservableList<Multas> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next())
            {
                Multas m  = new Multas();
                m.setCurso(rs.getString("nome_curso"));
                m.setClasse(rs.getString("nome_classe"));
                m.setTurma(rs.getString("nome_turma"));
                m.setMes(rs.getString("mes"));
                m.setValor(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+rs.getInt("multa")));
                soma += rs.getInt("multa");
                lista.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Multas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public static String Total()
    {
        return DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+soma);
    }
    
}
