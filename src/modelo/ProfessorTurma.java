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
public class ProfessorTurma {
    
    private int coddisciplina;
    private int codprofessor;
    private String anolectivo;
    private String nome_professor;
    private String disciplina;
    private String nomeTurma;

    public String getAnolectivo() {
        return anolectivo;
    }

    public int getCoddisciplina() {
        return coddisciplina;
    }

    public int getCodprofessor() {
        return codprofessor;
    }

    public void setAnolectivo(String anolectivo) {
        this.anolectivo = anolectivo;
    }

    public void setCoddisciplina(int coddisciplina) {
        this.coddisciplina = coddisciplina;
    }

    public void setCodprofessor(int codprofessor) {
        this.codprofessor = codprofessor;
    }
    
    
    

    public String getNome_professor() {
        return nome_professor;
    }

    public void setNome_professor(String nome_professor) {
        this.nome_professor = nome_professor;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }
    
    public static boolean JaEstaAssociado( int coddisciplina, int codprofessor, String anolectivo )
    {
        String sql= "select * from turma where coddisciplina = '"+coddisciplina+"' and codprofessor= '"+codprofessor+"' and anolectivo= '"+anolectivo+"'";
        int cont = 0;
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                cont++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfessorTurma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cont != 0;
    }
    
    public static boolean DisciplinaJaAssociada( int codturma , int coddisciplina ,String anolectivo )
    {
        String sql= "select * from turma_professor where codturma = '"+codturma+"' and coddisciplina = '"+coddisciplina+"' and anolectivo= '"+anolectivo+"'";
        int cont = 0;
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                cont++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfessorTurma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cont != 0;
    }
    
    public static ObservableList<String> Lista_ProfessoresJaAssociada( int codturma ,String anolectivo )
    {
        String sql= "select nome_professor from turma_professor inner join professor using(codprofessor) where codturma = '"+codturma+"' and  anolectivo= '"+anolectivo+"'";
        ObservableList<String> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome_professor"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfessorTurma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public static ObservableList<String> Lista_Disciplinas_JaAssociada( int codturma ,String anolectivo )
    {
        String sql= "select nome_disciplina from turma_professor inner join disciplina using(coddisciplina) where codturma = '"+codturma+"' and  anolectivo= '"+anolectivo+"'";
        ObservableList<String> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome_disciplina"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfessorTurma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public static ObservableList<String> Lista_Turmas_JaAssociada_Professor( int codprofessor ,String anolectivo )
    {
        String sql= "select distinct nome_turma from turma_professor inner join turma using(codprofessor) where codprofessor = '"+codprofessor+"' and  anolectivo= '"+anolectivo+"'";
        ObservableList<String> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome_turma"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfessorTurma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public static ObservableList<String> codigoProfessor_to_ListaDisciplinas_porTurma( int codprofessor,int codturma ,  String anolectivo )
    {
        String sql = "select coddisciplina from disciplina inner join turma_professor using(coddisciplina) where codprofessor = '"+codprofessor+"' and codturma = '"+codturma+"' and anolectivo = '"+anolectivo+"'";
        ObservableList<String> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                 lista.add(Disciplina.CodeToName(rs.getInt("coddisciplina")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Professor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
