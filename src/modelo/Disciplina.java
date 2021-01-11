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
public class Disciplina {
    
    private int coddisciplina;
    private String nome;
    private int codcurso;
    private int codclasse;
    private String nomeClasse;
    private String nomeCurso;

    public int getCodclasse() {
        return codclasse;
    }

    public int getCodcurso() {
        return codcurso;
    }

    public int getCoddisciplina() {
        return coddisciplina;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeClasse() {
        return nomeClasse;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }
    
    

    public void setCodclasse(int codclasse) {
        this.codclasse = codclasse;
    }

    public void setCodcurso(int codcurso) {
        this.codcurso = codcurso;
    }

    public void setCoddisciplina(int coddisciplina) {
        this.coddisciplina = coddisciplina;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }
    
    
    
    
    public boolean adicionar()
    {
       String sql=  "insert into disciplina values('"+this.getCoddisciplina()+"' , '"+this.getNome()+"' , '"+this.getCodcurso()+"' , '"+this.getCodclasse()+"')";
       return OperacoesBase.Inserir(sql);
    }
    
    public boolean Actualizar()
    {
        String sql = "update disciplina set nome_disciplina = '"+this.getNome()+"', codcurso = '"+this.getCodcurso()+"', codclasse = '"+this.getCodclasse()+"' where coddisciplina = '"+this.getCoddisciplina()+"'";
        return OperacoesBase.Actualizar(sql);
    }
    
    public boolean EliminarTudo()
    {
        String sql = "delete from disciplina";
        return OperacoesBase.Eliminar(sql);
    }
    
    public int retornaUltimoCodigo()
    {
        String sql = "select coddisciplina from disciplina order by coddisciplina";
        int valor = 0;
        ResultSet rs =  OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                valor = rs.getInt("coddisciplina");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Disciplina.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ++valor;
    }
    
    
/**********************Metodos Auxiliares*****************************************************************/
    /**
     * 
     * @param curso
     * @return 
     */
    public static ObservableList<String> ListaDisciplinaCurso( String curso)
    {
        int codcurso = Curso.NameToCode(curso);
        
        String sql = "select nome_disciplina from disciplina where codcurso = '"+codcurso+"'";
        ObservableList<String> lista  = FXCollections.observableArrayList();
        ResultSet rs =  OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome_disciplina"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Disciplina.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    
    public static ObservableList<String> ListaDisciplinasCurso_Classe( String curso , String classe )
    {
        int codcurso_local = Curso.NameToCode(curso);
        int codclasse_local = Classe.NameToCode(classe);
        
        String sql = "select nome_disciplina from disciplina where codcurso = '"+codcurso_local+"' and codclasse = '"+codclasse_local+"'";
        ObservableList<String> lista  = FXCollections.observableArrayList();
        ResultSet rs =  OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome_disciplina"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Disciplina.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public static ObservableList<String> DisciplinasCurso_Classe( String curso , String classe )
    {
        int codcurso_local = Curso.NameToCode(curso);
        int codclasse_local = Classe.NameToCode(classe);
        
        String sql = "select nome_disciplina from disciplina where codcurso = '"+codcurso_local+"' and codclasse = '"+codclasse_local+"'";
        ObservableList<String> lista  = FXCollections.observableArrayList();
        ResultSet rs =  OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome_disciplina"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Disciplina.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
   
    
   public  ObservableList<Disciplina> ListaDisciplinas()
   {
       ObservableList<Disciplina> lista = FXCollections.observableArrayList();
       String sql = "select * from disciplina";
       ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                Disciplina d = new Disciplina();
                
                this.setCoddisciplina(rs.getInt("coddisciplina"));
                this.setNome(rs.getString("nome_disciplina"));
                this.setNomeCurso(Curso.CodeToName((rs.getInt("codcurso"))));
                this.setNomeClasse(Classe.CodeToName(rs.getInt("codclasse")));
                
                lista.add(d);
            }} catch (SQLException ex) {
            Logger.getLogger(Disciplina.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
   }
   
   public static int NameToCode( String nome )
   {
       ResultSet rs = OperacoesBase.Consultar("select coddisciplina from disciplina where nome_disciplina = '"+nome+"'");
        try {
            while( rs.next() )
            {
               
                return rs.getInt("coddisciplina");
                
            }} catch (SQLException ex) {
            Logger.getLogger(Disciplina.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
   }
   
    public static int NameToCode( String nome ,String curso ,  String classe )
   {
       ResultSet rs = OperacoesBase.Consultar("select coddisciplina from disciplina where codclasse = '"+Classe.NameToCode(classe)+"' and codcurso = '"+Curso.NameToCode(curso)+"' and nome_disciplina = '"+nome+"'");
        try {
            while( rs.next() )
            {
               
                return rs.getInt("coddisciplina");
                
            }} catch (SQLException ex) {
            Logger.getLogger(Disciplina.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
   }
    
  
   
   
    public static String CodeToName( int code )
   {
       ResultSet rs = OperacoesBase.Consultar("select nome_disciplina from disciplina where coddisciplina = '"+code+"'");
        try {
            while( rs.next() )
            {
               
                return rs.getString("nome_disciplina");
                
            }} catch (SQLException ex) {
            Logger.getLogger(Disciplina.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
   }
}
