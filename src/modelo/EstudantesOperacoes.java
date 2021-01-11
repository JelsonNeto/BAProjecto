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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Familia Neto
 */
public class EstudantesOperacoes {
    
      
    private int codigo;
    private String nome;
    private LocalDate datanas;
    private String foto;
    private String sexo;
    private String periodo;
    private String turma;
    private String curso;
    private String cedula_bi;
    private String classe;
    private int codturma;
    
    private static int total = 0;
    public EstudantesOperacoes() {
    }

    public int getCodigo() {
        return codigo;
    }

   
    public String getCedula_bi() {
        return cedula_bi;
    }

    public int getCodturma() {
        return codturma;
    }

    
    public String getClasse() {
        return classe;
    }
    
    
    
    public String getCurso() {
        return curso;
    }

    public String getTurma() {
        return turma;
    }

    
    public LocalDate getDatanas() {
        return datanas;
    }

    public String getPeriodo() {
        return periodo;
    }

    public String getFoto() {
        return foto;
    }

    public String getNome() {
        return nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setCodturma(int codturma) {
        this.codturma = codturma;
    }

    public void setDatanas(LocalDate datanas) {
        this.datanas = datanas;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    
    public void setTurma(String turma) {
        this.turma = turma;
    }

    
    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCedula_bi(String cedula_bi) {
        this.cedula_bi = cedula_bi;
    }

    
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    
/*********************************GET estudantes para janela de informaco
     * @param curso*
     * @return ********/
      public static ObservableList<Estudante> listaAlunosPorCurso( String curso )
      {
          total = 0;
          ResultSet rs = OperacoesBase.Consultar("select * from aluno where curso = '"+curso+"'");
          ObservableList<Estudante> lista = FXCollections.observableArrayList();
          try {
            while( rs.next() )
            {
                Estudante e = new Estudante();
                
                e.setCodigo(rs.getInt("codaluno"));
                e.setCedula_bi(rs.getString("bi_cedula"));
                e.setClasse(rs.getString("classe"));
                e.setPeriodo(rs.getString("periodo"));
                e.setSexo(rs.getString("sexo"));
                e.setNome(rs.getString("nome"));
                e.setFoto(rs.getString("fotografia"));
                e.setDatanas(StringToLocalDate(rs.getString("datanasc")));
               
                lista.add(e);
                ++total;
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudantesOperacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
          
          return lista;
      
      }
      
        public static ObservableList<Estudante> listaAlunosPorTurma( String turma )
      {
          total = 0;
          int codturma = Turma.NameToCode(turma);
          ResultSet rs = OperacoesBase.Consultar("select * from aluno where codturma = '"+codturma+"'");
          ObservableList<Estudante> lista = FXCollections.observableArrayList();
          try {
            while( rs.next() )
            {
                Estudante e = new Estudante();
                
                e.setCodigo(rs.getInt("codaluno"));
                e.setCedula_bi(rs.getString("bi_cedula"));
                e.setClasse(rs.getString("classe"));
                e.setPeriodo(rs.getString("periodo"));
                e.setSexo(rs.getString("sexo"));
                e.setNome(rs.getString("nome"));
                e.setFoto(rs.getString("fotografia"));
                e.setDatanas(StringToLocalDate(rs.getString("datanasc")));
               
                lista.add(e);
                ++total;
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudantesOperacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
          
          return lista;
      
      }
        
         public static ObservableList<Estudante> listaAlunosPorclasse( String classe )
          {
          total = 0;
          ResultSet rs = OperacoesBase.Consultar("select * from aluno where classe = '"+classe+"'");
          ObservableList<Estudante> lista = FXCollections.observableArrayList();
          try {
            while( rs.next() )
            {
                Estudante e = new Estudante();
                
                e.setCodigo(rs.getInt("codaluno"));
                e.setCedula_bi(rs.getString("bi_cedula"));
                e.setClasse(rs.getString("classe"));
                e.setPeriodo(rs.getString("periodo"));
                e.setSexo(rs.getString("sexo"));
                e.setNome(rs.getString("nome"));
                e.setFoto(rs.getString("fotografia"));
                e.setDatanas(StringToLocalDate(rs.getString("datanasc")));
               
                lista.add(e);
                ++total;
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstudantesOperacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
          
          return lista;
      
      }
  
      public static int totalAlunos()
      {
          return total;
      }
      
    private static LocalDate StringToLocalDate( String data )
    {
        String array_data[] = data.split("-");
        int year =  Integer.parseInt(array_data[0]);
        int month = Integer.parseInt(array_data[1]);
        int day   = Integer.parseInt(array_data[2]);
        
        LocalDate d = LocalDate.of(year, month, day);
        return d;
    }
      
}
