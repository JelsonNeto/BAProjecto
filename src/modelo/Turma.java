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
public class Turma {
    
    
    private int codigo;
    private String nome_turma;
    private int codSala;
    private String nome_curso;
    private String classe;
    private int codclasse;
    private int codcurso;
    private String professor;
    private int codprofessor;
    private String periodo;
    
    public Turma() {
    }

    public int getCodSala() {
        return codSala;
    }

    public String getClasse() {
        return classe;
    }

    
    public String getNome_curso() {
        return nome_curso;
    }

    public int getCodcurso() {
        return codcurso;
    }

    public int getCodprofessor() {
        return codprofessor;
    }

    public String getPeriodo() {
        return periodo;
    }

    

    public int getCodigo() {
        return codigo;
    }

    public String getNome_turma() {
        return nome_turma;
    }

    public int getCodclasse() {
        return codclasse;
    }

    public String getProfessor() {
        return professor;
    }

    
    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public void setCodprofessor(int codprofessor) {
        this.codprofessor = codprofessor;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
    
    public void setCodSala(int codSala) {
        this.codSala = codSala;
    }

    public void setNome_curso(String nome_curso) {
        this.nome_curso = nome_curso;
    }

    public void setCodcurso(int codcurso) {
        this.codcurso = codcurso;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNome_turma(String nome_turma) {
        this.nome_turma = nome_turma;
    }

    public void setCodclasse(int codclasse) {
        this.codclasse = codclasse;
    }

    public void show()
    {
        System.out.println(getClasse());
        System.out.println(getCodclasse());
        System.out.println(getCodSala());
        System.out.println(getCodcurso());
        System.out.println(getCodigo());
        System.out.println(getNome_turma());
    }
    
/***************************METODOS AUXILIARES*********************************************************************************/
    /**
     * @param valor
     * @return 
     */
    public boolean Actualizar(int valor )
    {
        String sql = "update turma set nome_turma ='"+this.getNome_turma()+"' , codsala = '"+this.getCodSala()+"' , codcurso = '"+this.getCodcurso()+"', codclasse = '"+this.getCodclasse()+"', codprofessor = '"+this.getCodprofessor()+"', periodo = '"+this.getPeriodo()+"' where codturma = '"+valor+"'";
        return OperacoesBase.Actualizar(sql);
    }
    
    public boolean Adicionar()
    {
        String sql = "Insert into turma values('"+this.getCodigo()+"' ,'"+this.getNome_turma()+"' , '"+this.getCodSala()+"', '"+this.getCodcurso()+"' , '"+this.getCodclasse()+"', '"+this.getCodprofessor()+"', '"+this.getPeriodo()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
    public int RetornarUltimoCodTurma()
    {
        
        ResultSet rs = OperacoesBase.Consultar("select * from turma order by codturma");
        int valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codturma");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++valor;
    }
    
    
  
    public boolean Eliminar( int valor )
    {
        String sql = "delete from turma where codturma = '"+valor+"'";
        return OperacoesBase.Eliminar(sql);
    }
    
    
     public static int NameToCode( String nome )
    {
    
        ResultSet rs = OperacoesBase.Consultar("select codturma from turma where nome_turma = '"+nome+"'");
        int valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codturma");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    
    }
     
     public static String CodTurmaToPeriodo( int codturma )
     {
        ResultSet rs = OperacoesBase.Consultar("select periodo from Turma where codturma = '"+codturma+"'");
        String valor = "";
        try {
            while( rs.next() )
            {
                valor = rs.getString("periodo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
     }
     
      public static String CodTurmaToCurso( int codturma )
     {
        ResultSet rs = OperacoesBase.Consultar("select codcurso from Turma where codturma = '"+codturma+"'");
        int valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codcurso");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Curso.CodeToName(valor);
     }
      
       public static String CodTurmaToClasse( int codturma )
     {
        ResultSet rs = OperacoesBase.Consultar("select codclasse from Turma where codturma = '"+codturma+"'");
        int  valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codclasse");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Classe.CodeToName(valor);
     }
     
     
     
     public static int NomeAlunoToCodTurma( String nome )
     {
        ResultSet rs = OperacoesBase.Consultar("select codturma  from aluno inner join matricula_confirmacao using(codaluno) inner join turma using(codturma) where anolectivo = '"+MesAno.Get_AnoActualCobranca()+"' and nome = '"+nome+"'");
        int valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codturma");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
     }
    
     
        public static String CodeToName( int code )
    {
    
        ResultSet rs = OperacoesBase.Consultar("select nome_turma from turma where codturma = '"+code+"'");
        String nome  = "";
        try {
            while( rs.next() )
            {
                nome = rs.getString("nome_turma");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nome;
    
        
    }
        
    public static String ClasseToNomeTurma( String classe )
    {
    
        ResultSet rs = OperacoesBase.Consultar("select nome_turma from turma where codclasse = '"+Classe.NameToCode(classe)+"'");
        String nome  = "";
        try {
            while( rs.next() )
            {
                nome = rs.getString("nome_turma");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nome;
    
        
    }
        
    public static ObservableList<String> ListaTurmasRelaClass( String classe )
    {
        int codclasse = Classe.NameToCode(classe);
        ObservableList<String> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar("select nome_turma from turma where codclasse = '"+codclasse+"' ");
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome_turma"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public static ObservableList<String> ListaTurmasRelaClasse_CodCurso( String classe , int codcurso )
    {
        int codclasse = Classe.NameToCode(classe);
        ObservableList<String> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar("select nome_turma from turma where codclasse = '"+codclasse+"' and codcurso = '"+codcurso+"' ");
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome_turma"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
     public static ObservableList<String> ListaTurmasRelaClasse_CodCurso_Periodo( String classe , int codcurso, String periodo )
    {
        int codclasse = Classe.NameToCode(classe);
        ObservableList<String> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar("select nome_turma from turma where codclasse = '"+codclasse+"' and codcurso = '"+codcurso+"' and periodo= '"+periodo+"' ");
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome_turma"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
     
     
    
    
    public static ObservableList<String> ListaTurmas()
    {
        ObservableList<String> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar("select nome_turma from turma ");
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome_turma"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public static ObservableList<ProfessorTurma> Turma_Professores( int codturma)
    {
        ObservableList<ProfessorTurma> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar("select * from professor inner join turma_professor using(codprofessor) inner join turma using(codturma) inner join disciplina using(coddisciplina) where codturma = '"+codturma+"' ");
        try {
            while( rs.next() )
            {
                ProfessorTurma p = new ProfessorTurma();
                p.setNome_professor(rs.getString("nome_professor"));
                p.setNomeTurma(rs.getString("nome_turma"));
                p.setDisciplina(rs.getString("nome_disciplina"));
                
                lista.add(p);
                        
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
     public static String Turma_DisciplinaNomeProfessor( int codturma, int coddisciplina ,  String anolectivo )
    {
        String sql = "select nome_professor from view_turma_disciplinas_professores where codturma = '"+codturma+"' and coddisciplina = '"+coddisciplina+"' and anolectivo = '"+anolectivo+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                return rs.getString("nome_professor");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Professor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Sem professor";
    }
    
    
    
}
