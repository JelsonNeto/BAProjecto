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
public class Professor  {

   
    
    private int codigo;
    private String nome;
    private String datanascimento;
    private String sexo;
    private String foto;
    private String bi_cedula;
    private String status;
    private String tipo_professor;
    private String anolectivo;
    private int codfuncionario;
    

    public int getCodigo() {
        return codigo;
    }

    public String getDatanascimento() {
        return datanascimento;
    }

    public int getCodfuncionario() {
        return codfuncionario;
    }

    public void setCodfuncionario(int codfuncionario) {
        this.codfuncionario = codfuncionario;
    }

    
  
    public String getBi_cedula() {
        return bi_cedula;
    }

    public String getAnolectivo() {
        return anolectivo;
    }

    
    
    public String getNome() {
        return nome;
    }

    public String getSexo() {
        return sexo;
    }

    public String getFoto() {
        return foto;
    }

    public String getStatus() {
        return status;
    }

    public String getTipo_professor() {
        return tipo_professor;
    }

    
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setBi_cedula(String bi_cedula) {
        this.bi_cedula = bi_cedula;
    }

    public void setAnolectivo(String anolectivo) {
        this.anolectivo = anolectivo;
    }

    public void setDatanascimento(String datanascimento) {
        this.datanascimento = datanascimento;
    }

    
    
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTipo_professor(String tipo_professor) {
        this.tipo_professor = tipo_professor;
    }
    
    
    public boolean Adicionar()
    {
        String sql = "insert into professor values('"+this.getCodigo()+"', '"+this.getNome()+"','"+this.getFoto()+"', '"+this.getTipo_professor()+"','"+this.getCodfuncionario()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
     public boolean Actualizar()
    {
        String sql = "update professor set nome_professor =  '"+this.getNome()+"', datanascimento =  '"+this.getDatanascimento()+"' ,sexo = '"+this.getSexo()+"', fotografia = '"+this.getFoto()+"', bi_cedula = '"+this.getBi_cedula()+"' , status = '"+this.getStatus()+"' , tipo_professor = '"+this.getTipo_professor()+"'  where codprofessor = '"+this.getCodigo()+"'";
        return OperacoesBase.Actualizar(sql);
    }
     
     public boolean eliminar()
     {
         String sql= "delete from professor where codprofessor = '"+this.getCodigo()+"'";
         return OperacoesBase.Eliminar(sql);
     }
    
   
/*******************************************Metodos Operacionais*****************************************************************/
   
    /**
     * 
     * @return 
     */
    public static int RetornarUltimoCodProfessor()
    {
        
        ResultSet rs = OperacoesBase.Consultar("select * from professor order by codprofessor");
        int valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codprofessor");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Professor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++valor;
    }
    
    public static boolean JaAssociadoADisciplina( int codprofessor , String anolectivo )
    {
        String sql = "select nome_professor from professor inner join professor_disciplina using(codprofessor) where codprofessor = '"+codprofessor+"' and anolectivo = '"+anolectivo+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        int contador = 0;
        try {
            while( rs.next() )
            {
                contador++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Professor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contador !=0;
    }
    
    public static String DisciplinaNomeProfessor( int cod )
    {
        String sql = "select nome_professor from professor inner join professor_disciplina using(codprofessor) inner join disciplina using(coddisciplina) where coddisciplina = '"+cod+"'";
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
            
    public static String DisciplinaNomeProfessor( int cod, int codclasse  , int codcurso  )
    {
        String sql = "select nome_professor from professor inner join professor_disciplina using(codprofessor) inner join disciplina using(coddisciplina) where coddisciplina = '"+cod+"' and codclasse = '"+codclasse+"' and codcurso = '"+codcurso+"' ";
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
   
    public static ObservableList<String> codigoProfessor_to_ListaDisciplinas( int codprofessor, String anolectivo )
    {
        String sql = "select coddisciplina from disciplina inner join turma_professor using(coddisciplina) where codprofessor = '"+codprofessor+"' and anolectivo = '"+anolectivo+"'";
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
    
    
   
    public static int ObterCurso_CodProfesor_Disciplina( int cod, String disciplina )
    {
        String sql = "select codcurso from disciplina inner join professor_curso using(coddisciplina) where codprofessor = '"+cod+"' and nome_disciplina = '"+disciplina+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                 return rs.getInt("codcurso");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Professor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public static int ObterClasse_CodProfesor_Disciplina( int cod, String disciplina )
    {
        String sql = "select codclasse from disciplina inner join professor_curso using(coddisciplina) where codprofessor = '"+cod+"' and nome_disciplina = '"+disciplina+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                 return rs.getInt("codclasse");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Professor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
   
    
     public static String CodeToName( int code )
    {
    
        ResultSet rs = OperacoesBase.Consultar("select nome_professor from professor where codprofessor = '"+code+"'");
        String nome  = "";
        try {
            while( rs.next() )
            {
                nome = rs.getString("nome_professor");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Professor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nome;
    
        
    }
     
    public static String CodeToFoto( int code )
    {
        ResultSet rs = OperacoesBase.Consultar("select fotografia from professor where codprofessor = '"+code+"'");
        String nome  = "";
        try {
            while( rs.next() )
            {
                nome = rs.getString("fotografia");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Professor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nome;
    
        
    }
    
    public static String NameToBi( String nome_professor )
    {
        ResultSet rs = OperacoesBase.Consultar("select bi_cedula  from professor where nome_professor = '"+nome_professor+"'");
        String nome  = "";
        try {
            while( rs.next() )
            {
                nome = rs.getString("bi_cedula");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Professor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nome;

    }

    public static String codeToSexo( int code  )
    {
        ResultSet rs = OperacoesBase.Consultar("select sexo  from professor where codprofessor = '"+code+"'");
        String classe  = "";
        try {
            while( rs.next() )
            {
                classe = rs.getString("sexo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Professor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classe;

    }
    
     public static String codeToDataNasc( int code  )
    {
        ResultSet rs = OperacoesBase.Consultar("select datanascimento  from professor where codprofessor = '"+code+"'");
        String classe  = "";
        try {
            while( rs.next() )
            {
                classe = rs.getString("datanascimento");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Professor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classe;

    }
    
      public static int NameToCode(String text) 
      {
        ResultSet rs = OperacoesBase.Consultar("select codprofessor  from professor where nome_professor = '"+text+"'");
        int codigo = 0;
        try {
            while( rs.next() )
            {
                codigo = rs.getInt("codprofessor");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Professor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codigo;

      }
      
      public static String CodeToStatus( int code ) 
      {
        ResultSet rs = OperacoesBase.Consultar("select status from professor where codprofessor = '"+code+"'");
        String valor = "Sem Status";
        try {
            while( rs.next() )
            {
                valor = rs.getString("status");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Professor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;

      }
      
      public static String CodeToTipo( int code ) 
      {
        ResultSet rs = OperacoesBase.Consultar("select tipo_professor from professor where codprofessor = '"+code+"'");
        String valor = "Sem tipo";
        try {
            while( rs.next() )
            {
                valor = rs.getString("tipo_professor");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Professor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;

      }
    
        public static ObservableList<String> CursoClasseToListaProfessores( int codcurso , int codclasse )
        {
               String sql= "select distinct nome_professor from professor inner join professor_curso using(codprofessor) where codcurso = '"+codcurso+"' and codclasse = '"+codclasse+"'";
               ObservableList<String> lista = FXCollections.observableArrayList();
               ResultSet rs = OperacoesBase.Consultar(sql);
            try {
                while( rs.next() )
                {
                    lista.add(rs.getString("nome_professor"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Professor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return lista;
        }
        
        public static Professor ObterProfessor_pelo_CodFuncionario( int codfuncionario )
        {
            String funcao_var = "Professor(a)";
            String sql = "select * from professor inner join funcionario using(codfuncionario) where codfuncionario = '"+codfuncionario+"' and funcao = '"+funcao_var+"'";
            ResultSet rs = OperacoesBase.Consultar(sql);
            try {
                while( rs.next() )
                {
                    Professor p = new Professor();
                    p.setCodigo(rs.getInt("codprofessor"));
                    p.setNome(rs.getString("nome_professor"));
                    p.setFoto(rs.getString("fotografia"));
                    p.setTipo_professor(rs.getString("tipo_professor"));
                    p.setBi_cedula(rs.getString("bi_cedula"));
                    p.setDatanascimento(rs.getString("data_nascimento"));
                    p.setStatus(rs.getString("status"));
                    p.setSexo(rs.getString("sexo"));
                    
                    return p;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Professor.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
        
         public static ObservableList<String> Lista_Professores_Nomes()
        {
            String funcao_var = "Professor(a)";
            ObservableList<String> lista_nomes = FXCollections.observableArrayList();
            String sql = "select * from professor inner join funcionario using(codfuncionario) where funcao = '"+funcao_var+"'";
            ResultSet rs = OperacoesBase.Consultar(sql);
            try {
                while( rs.next() )
                {
                    Professor p = new Professor();
                    p.setCodigo(rs.getInt("codprofessor"));
                    p.setNome(rs.getString("nome_professor"));
                    p.setFoto(rs.getString("fotografia"));
                    p.setTipo_professor(rs.getString("tipo_professor"));
                    p.setBi_cedula(rs.getString("bi_cedula"));
                    p.setDatanascimento(rs.getString("data_nascimento"));
                    p.setStatus(rs.getString("status"));
                    p.setSexo(rs.getString("sexo"));
                    
                    lista_nomes.add(p.getNome());
                }
            } catch (SQLException ex) {
                Logger.getLogger(Professor.class.getName()).log(Level.SEVERE, null, ex);
            }
            return lista_nomes;
        }
        
       /* public static ObservableList<Professor> Lista_Professores_paraTabela()
        {
            
        }*/
        
}
