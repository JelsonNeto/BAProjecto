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
public class Minipauta_Trimestral {
    
    private int codigo;
    private int codmatricula_c;
    private int coddisciplina;
    private String disciplina;
    private String nome;
    private String trimestre;
    private String anolectivo;
    private String professor;
    private String curso;
    private String classe;
    private String turma;
    private String mac;
    private String cp;
    private String ct;

    public Minipauta_Trimestral() {
    }

    public Minipauta_Trimestral(int codigo, int codmatricula_c, int coddisciplina, String trimestre, String anolectivo, String mac, String cp, String ct) {
        this.codigo = codigo;
        this.codmatricula_c = codmatricula_c;
        this.coddisciplina = coddisciplina;
        this.trimestre = trimestre;
        this.anolectivo = anolectivo;
        this.mac = mac;
        this.cp = cp;
        this.ct = ct;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodmatricula_c() {
        return codmatricula_c;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }
    
    

    public void setCodmatricula_c(int codmatricula_c) {
        this.codmatricula_c = codmatricula_c;
    }

    public int getCoddisciplina() {
        return coddisciplina;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getClasse() {
        return classe;
    }

    public String getCurso() {
        return curso;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public String getTurma() {
        return turma;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    
   
    public void setCoddisciplina(int coddisciplina) {
        this.coddisciplina = coddisciplina;
    }

    public String getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(String trimestre) {
        this.trimestre = trimestre;
    }

    public String getAnolectivo() {
        return anolectivo;
    }

    public void setAnolectivo(String anolectivo) {
        this.anolectivo = anolectivo;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

  
    
    public boolean Adicionar()
    {
        String sql = "insert into minipauta_trimestral values('"+this.getCodigo()+"','"+this.getCodmatricula_c()+"','"+this.getMac()+"','"+this.getCp()+"','"+this.getCt()+"','"+this.getTrimestre()+"','"+this.getAnolectivo()+"','"+this.getCoddisciplina()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
    
 //Metodos Operacionais
    public static int UltimoCodigo()
    {
        String sql = "select codigo from minipauta_trimestral";
        int codigo = 0;
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                codigo= rs.getInt("codigo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Minipauta_Trimestral.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ++codigo;
    }
    
 //Metodos Operacionais
    public static ObservableList<String> Disciplinas_Ja_Registradas( int codturma , String trimestre ,  String anolectivo )
    {
        String sql= "select distinct(coddisciplina) from minipauta_trimestral mt inner join matricula_confirmacao using(codmatricula_c) where codturma = '"+codturma+"' and trimestre = '"+trimestre+"' and mt.anolectivo = '"+anolectivo+"'";
        ObservableList<String> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                lista.add(Disciplina.CodeToName(rs.getInt("coddisciplina")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Minipauta_Trimestral.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public static String Obter_Mac( int codmatricula , int coddisciplina ,  String trimestre , String anolectivo , int codturma  )
    {
        String valor = "0.0";
        String sql= "select mac from minipauta_trimestral t inner join matricula_confirmacao using(codmatricula_c) where codmatricula_c = '"+codmatricula+"' and coddisciplina = '"+coddisciplina+"' and t.trimestre = '"+trimestre+"' and t.anolectivo = '"+anolectivo+"' and codturma = '"+codturma+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                valor = rs.getString("mac");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Minipauta_Trimestral.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valor;
    }
    
    public static String Obter_Ct( int codmatricula , int coddisciplina , String trimestre , String anolectivo, int codturma )
    {
        String valor = "0.0";
        String sql= "select cp from minipauta_trimestral t inner join matricula_confirmacao using(codmatricula_c) where codmatricula_c = '"+codmatricula+"' and coddisciplina = '"+coddisciplina+"' and t.trimestre = '"+trimestre+"' and t.anolectivo = '"+anolectivo+"' and codturma = '"+codturma+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                valor = rs.getString("cp");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Minipauta_Trimestral.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valor;
    }
    
    public static String Obter_Cp( int codmatricula ,int coddisciplina ,  String trimestre , String anolectivo, int codturma )
    {
        String valor = "0.0";
        String sql= "select ct from minipauta_trimestral t inner join matricula_confirmacao using(codmatricula_c) where codmatricula_c = '"+codmatricula+"' and coddisciplina = '"+coddisciplina+"' and t.trimestre = '"+trimestre+"' and t.anolectivo = '"+anolectivo+"' and codturma = '"+codturma+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                valor = rs.getString("ct");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Minipauta_Trimestral.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valor;
    }
    
    public static boolean Verifica_Se_jaExiste( int codmatricula , int coddisciplina , String trimestre, String anolectivo )
    {
        String sql = "select codigo from minipauta_trimestral where codmatricula_c = '"+codmatricula+"' and coddisciplina = '"+coddisciplina+"' and trimestre = '"+trimestre+"' and anolectivo='"+anolectivo+"'";
        int codigo = 0;
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                codigo = rs.getInt("codig");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Minipauta_Trimestral.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return codigo != 0;
    }
    
    public static ObservableList<Minipauta_Trimestral> MiniPautas_Ja_Cadastradas( String trimestre ,String anolectivo )
    {
        String sql = "select distinct coddisciplina,nome_disciplina,nome_turma,nome_curso,nome_classe from minipauta_trimestral mt inner join matricula_confirmacao using(codmatricula_c) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) inner join disciplina using(coddisciplina) where mt.anolectivo = '"+anolectivo+"' and mt.trimestre ='"+trimestre+"'";
        ObservableList<Minipauta_Trimestral> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                Minipauta_Trimestral m = new Minipauta_Trimestral();
                m.setCoddisciplina(rs.getInt("coddisciplina"));
                m.setCurso(rs.getString("nome_curso"));
                m.setClasse(rs.getString("nome_classe"));
                m.setDisciplina(rs.getString("nome_disciplina"));
                m.setProfessor(Professor.DisciplinaNomeProfessor(Disciplina.NameToCode(m.getDisciplina(), m.getCurso(), m.getClasse()), Classe.NameToCode(m.getClasse()), Curso.NameToCode(m.getCurso())));
                m.setTurma(rs.getString("nome_turma"));
                m.setTrimestre(trimestre);
                m.setAnolectivo(anolectivo);
                lista.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Minipauta_Trimestral.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public static ObservableList<Minipauta_Trimestral> Lista_Minipauta_Trimestrals( int coddisciplina, String trimestre, String anolectivo )
    {
        String sql = "select * from minipauta_trimestral where coddisciplina= '"+coddisciplina+"' and trimestre = '"+trimestre+"' and anolectivo= '"+anolectivo+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        ObservableList<Minipauta_Trimestral> lista= FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                
                Minipauta_Trimestral m = new Minipauta_Trimestral();
                m.setNome(Estudante.CodeToName(matricula_confirmacao.codmatricula_c_para_codaluno(rs.getInt("codmatricula_c"), anolectivo)));
                m.setMac(rs.getString("mac"));
                m.setCp(rs.getString("cp"));
                m.setCt(rs.getString("ct"));
                m.setDisciplina(Disciplina.CodeToName(coddisciplina));
                lista.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Minipauta_Trimestral.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
}
