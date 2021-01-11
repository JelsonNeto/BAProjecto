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
public class Curso {
    
    private int codigo;
    private String nome;

    public Curso() {
    }

    public int getCodigo() {
        return codigo;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
 
    /**************************************METODOS AUXILIARES*********************************************************************/
    public boolean Adicionar()
    {
       String sql = "Insert into curso values( '"+this.getCodigo()+"' ,'"+this.getNome()+"')";
       return OperacoesBase.Inserir(sql);
    }
    
    public ResultSet ConsultarAll()
    {
        String sql = "Select * from curso order by codcurso";
        return OperacoesBase.Consultar(sql);
    }
    
    public ResultSet ConsultarSome( String sql )
    {
        return OperacoesBase.Consultar(sql);
    }
    
    
    
    public int RetornarUltimoCodTurma()
    {
        
        ResultSet rs = OperacoesBase.Consultar("select * from curso");
        int valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codcurso");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++valor;
    }
    
    
    public static int NameToCode( String nome )
    {
    
        ResultSet rs = OperacoesBase.Consultar("select codcurso from curso where nome_curso = '"+nome+"'");
        int valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codcurso");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    
        
    }
    
    public static String CodeToName( int codcurso )
    {
       
        ResultSet rs = OperacoesBase.Consultar("select nome_curso from curso where codcurso = '"+codcurso+"'");
        String valor = "";
        try {
            while( rs.next() )
            {
                valor = rs.getString("nome_curso");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    
        
    }
    
    
    public static String NameAlunoNameCurso( String nome )
    {
    
        ResultSet rs = OperacoesBase.Consultar("select nome_curso  from aluno inner join matricula_confirmacao using(codaluno) inner join turma using(codturma) inner join curso using(codcurso) where anolectivo = '"+MesAno.Get_AnoActualCobranca()+"' and nome = '"+nome+"'");
        String curso = "";
        try {
            while( rs.next() )
            {
                curso = rs.getString("nome_curso");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return curso;
     
    }
    
     public static String LastName()
    {
    
        ResultSet rs = OperacoesBase.Consultar("select nome_curso from curso");
        String valor = "";
        try {
            while( rs.next() )
            {
                valor = rs.getString("nome_curso");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }
     
     public static ObservableList<String> ListaCursos()
     {
        ResultSet rs = OperacoesBase.Consultar("select nome_curso from curso");
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome_curso"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
     }
    
     public static String DisciplinaToCurso( int cod )
    {
       
        String sql = "select codcurso from disciplina where coddisciplina = '"+cod+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                return Curso.CodeToName(rs.getInt("codcurso"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
     
}
