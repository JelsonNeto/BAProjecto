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
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Familia Neto
 */
public class matricula_confirmacao {
    
    private int codmatricula;
    private int codaluno;
    private int codturma;
    private String anolectivo;
    private String periodo;

    public String getAnolectivo() {
        return anolectivo;
    }

    public int getCodaluno() {
        return codaluno;
    }

    public int getCodmatricula() {
        return codmatricula;
    }

    public int getCodturma() {
        return codturma;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
    

    public void setAnolectivo(String anolectivo) {
        this.anolectivo = anolectivo;
    }

    public void setCodaluno(int codaluno) {
        this.codaluno = codaluno;
    }

    public void setCodmatricula(int codmatricula) {
        this.codmatricula = codmatricula;
    }

    public void setCodturma(int codturma) {
        this.codturma = codturma;
    }
    
    
    public int RetornaUltimoCodigo()
    {
        int valor = 0;
        ResultSet rs = OperacoesBase.Consultar("select * from matricula_confirmacao order by codmatricula_c");
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codmatricula_c");
            }
        } catch (SQLException ex) {
            Logger.getLogger(matricula_confirmacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++valor;
    } 
    
    public boolean adicionar()
    {
         String sql = "insert into matricula_confirmacao values('"+this.getCodmatricula()+"', '"+this.getCodaluno()+"','"+this.getCodturma()+"','"+this.getAnolectivo()+"')";
         return OperacoesBase.Inserir(sql);
    }
    
    public boolean actualizarTurma()
    {
        String sql = "update matricula_confirmacao set codturma= '"+this.getCodturma()+"' where codaluno= '"+this.getCodaluno()+"' and anlectivo= '"+this.getAnolectivo()+"'";
        return OperacoesBase.Actualizar(sql);
    }
    
    public boolean Eliminar()
    {
        String sql = "delete from matricula_confirmacao where codaluno = '"+this.codaluno+"' and anolectivo = '"+this.anolectivo+"'";
        return OperacoesBase.Eliminar(sql);
    }
    
    
/***************************************METODOS OPERACIONAIS*************************************************/

    public static int CodAlunoToCodMatricula( int codaluno )
    {
        int anoActual = Integer.parseInt(MesAno.Get_AnoActualCobranca());
        int anoPesquisa = (anoActual == 2021)?2020:2020; 
        int valor = 0;
        ResultSet rs = OperacoesBase.Consultar("select codmatricula_c from matricula_confirmacao where codaluno = '"+codaluno+"' and anolectivo = '"+anoPesquisa+"' ");
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codmatricula_c");
            }
        } catch (SQLException ex) {
            Logger.getLogger(matricula_confirmacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    } 
    
   public static ObservableList<String> ListaDosAnosMatriculados()
   {
       String sql = "select distinct anolectivo from matricula_confirmacao order by anolectivo asc";
       ObservableList<String> listaAnos = FXCollections.observableArrayList();
       ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                String ano = rs.getString("anolectivo");
                listaAnos.add(ano);
            }} catch (SQLException ex) {
            Logger.getLogger(matricula_confirmacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaAnos;
   }
   
   
   
   
   public static ObservableList<String> ListaDosAnosMatriculadosAluno( int codaluno )
   {
       String sql = "select anolectivo from matricula_confirmacao where codaluno = '"+codaluno+"'";
       ObservableList<String> listaAnos = FXCollections.observableArrayList();
       ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                String ano = rs.getString("anolectivo");
                  listaAnos.add(ano);
               }
            } catch (SQLException ex) {
            Logger.getLogger(matricula_confirmacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaAnos;
   }
   
   public static boolean JaMatriculado( int codaluno , String anolectivo )
   {
       String descricao = "Propina";
       String sql = "select * from pagamento inner join matricula_confirmacao using( codmatricula_c ) inner join aluno using(codaluno) where codaluno = '"+codaluno+"' and descricao !='"+descricao+"'  and anolectivo = '"+anolectivo+"'";
       int valor = 0;
       ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                ++valor;
            }} catch (SQLException ex) {
            Logger.getLogger(matricula_confirmacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valor != 0;
   }
   
   public static int GetTurma( int codaluno , String anolectivo )
   {
        String sql = "select codturma from matricula_confirmacao inner join aluno using(codaluno) where anolectivo = '"+anolectivo+"'";
       int valor = 0;
       ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codturma");
            }} catch (SQLException ex) {
            Logger.getLogger(matricula_confirmacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valor;
   }
   
   public static ObservableList<Integer> ListaDosAlunosMatriculadosAluno_por_Turma( int codturma , String anolectivo )
   {
       String sql = "select codmatricula_c from matricula_confirmacao where codturma = '"+codturma+"' and anolectivo = '"+anolectivo+"'";
       ObservableList<Integer> listaAnos = FXCollections.observableArrayList();
       ResultSet rs = OperacoesBase.Consultar(sql);
        try 
        {
                while( rs.next() )
                {
                     listaAnos.add(rs.getInt("codmatricula_c"));
                }
            } catch (SQLException ex) {
            Logger.getLogger(matricula_confirmacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaAnos;
   }
   
   public static ObservableList<matricula_confirmacao> ListaDosAlunosMatriculadosAluno_por_Curso_Classe( int codcurso , int codclasse , String anolectivo )
   {
       String sql = "select * from matricula_confirmacao inner join turma using(codturma) where codcurso = '"+codcurso+"' and codclasse = '"+codclasse+"' and anolectivo = '"+anolectivo+"'";
       ObservableList<matricula_confirmacao> lista_estudantes = FXCollections.observableArrayList();
       ResultSet rs = OperacoesBase.Consultar(sql);
        try 
        {
                while( rs.next() )
                {
                     matricula_confirmacao m = new matricula_confirmacao();
                     m.setCodmatricula(rs.getInt("codmatricula_c"));
                     m.setCodturma(rs.getInt("codturma"));
                     m.setPeriodo(rs.getString("periodo"));
                     
                     lista_estudantes.add(m);
                }
            } catch (SQLException ex) {
            Logger.getLogger(matricula_confirmacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista_estudantes;
   }
   
   public static int codmatricula_c_para_codaluno( int codmatricula_c , String anolectivo )
   {
       String sql = "select codaluno from aluno inner join matricula_confirmacao using(codaluno) where codmatricula_c = '"+codmatricula_c+"' and anolectivo = '"+anolectivo+"'";
       ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                return rs.getInt("codaluno");
            }} catch (SQLException ex) {
            Logger.getLogger(matricula_confirmacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
   }
   
   
//========================================CONFIRMACAO=============================================
   public static ObservableList<Estudante> ListaDosAlunosConfirmados_anoLectivo( String anolectivo )
   {
       String sql = "select * from view_confirmados where descricao = 'Confirmação' and anolectivo = '"+anolectivo+"'";
       ObservableList<Estudante> lista_estudantes = FXCollections.observableArrayList();
       ResultSet rs = OperacoesBase.Consultar(sql);
        try 
        {
                while( rs.next() )
                {
                     Estudante e = new Estudante();
               
                    e.setCedula_bi(rs.getString("bi_cedula"));
                    e.setCodigo(rs.getInt("codaluno"));
                    e.setNome(rs.getString("nome"));
                    e.setSexo(rs.getString("sexo"));
                    e.setFoto(rs.getString("fotografia"));
                    e.setDatanas(StringToLocalDate(rs.getString("datanasc")));
                    e.setTipo(rs.getString("tipo_Aluno"));
                    e.setStatus(rs.getString("status"));
                    e.setCurso(rs.getString("nome_curso"));
                    e.setTurma(rs.getString("nome_turma"));
                    e.setCodturma(Turma.NameToCode(e.getTurma()));
                    e.setPeriodo(rs.getString("periodo"));
                    e.setClasse(rs.getString("nome_classe"));
                    e.setAnolectivo(MesAno.Get_AnoActualCobranca());
                    lista_estudantes.add(e);
                }
            } catch (SQLException ex) {
            Logger.getLogger(matricula_confirmacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista_estudantes;
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
