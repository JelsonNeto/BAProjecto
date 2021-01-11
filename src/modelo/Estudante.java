/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import Bd.OperacoesBase;
import Controlador.Estudante.VisualizarEstudanteMatriculadoController;
import definicoes.DefinicoesData;
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
public class Estudante {

    private int codigo;
    private String codigo_escola;
    private String tipo;
    private String nome;
    private LocalDate datanas;
    private String foto;
    private String sexo;
    private String periodo;
    private String turma;
    private String curso;
    private String cedula_bi;
    private String classe;
    private String status;
    private int codturma;
    private int sala;
    private String professor;
    private int idade;
    private String dataPagamento;
    private String anolectivo;
    
    public Estudante() {
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

    public int getIdade() {
        return idade;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public String getAnolectivo() {
        return anolectivo;
    }

    
    
    public String getClasse() {
        return classe;
    }

    public String getStatus() {
        return status;
    }

    public int getSala() {
        return sala;
    }

    public String getProfessor() {
        return professor;
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

    public String getTipo() {
        return tipo;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public void setAnolectivo(String anolectivo) {
        this.anolectivo = anolectivo;
    }

    public String getCodigo_escola() {
        return codigo_escola;
    }

    public void setCodigo_escola(String codigo_escola) {
        this.codigo_escola = codigo_escola;
    }

   
    
    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setCodturma(int codturma) {
        this.codturma = codturma;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSala(int sala) {
        this.sala = sala;
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

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

   
    public boolean Adicionar()
    {
        String sql = "Insert into aluno values('"+this.getCodigo()+"', '"+this.getNome()+"','"+this.getSexo()+"','"+this.getCedula_bi()+"','"+this.getDatanas()+"', '"+this.getFoto()+"' , '"+this.getTipo()+"','"+this.getStatus()+"','"+this.getCodigo_escola()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
    public boolean  Actualizar()
    {
        String sql = "update aluno set nome = '"+this.getNome()+"', sexo = '"+this.getSexo()+"', bi_cedula = '"+this.getCedula_bi()+"', datanasc = '"+this.getDatanas()+"', fotografia = '"+this.getFoto()+"', tipo_Aluno = '"+this.getTipo()+"', status = '"+this.getStatus()+"' where codaluno = '"+this.getCodigo()+"'";
        return OperacoesBase.Actualizar(sql);
    }
    
     public boolean  ActualizarDadosEstudanteMatricula()
    {
        String sql = "update aluno set  classe = '"+this.getClasse()+"' ,codturma = '"+this.getCodturma()+"',periodo = '"+this.getPeriodo()+"' , curso =  '"+this.getCurso()+"' where codaluno = '"+this.getCodigo()+"'";
        return OperacoesBase.Actualizar(sql);
    }
    
    public int RetornaUltimoCodigo()
    {
        int valor = 0;
        ResultSet rs = OperacoesBase.Consultar("select * from aluno order by codaluno");
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codaluno");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++valor;
    }
    
    public boolean Eliminar()
    {
        String sql = "delete from aluno where codaluno = '"+this.getCodigo()+"'";
        return OperacoesBase.Eliminar(sql);
    }
    
/********************************Metodos Auxiliares******************************************************/
    public static int NameToCode( String nome )
    {
    
        ResultSet rs = OperacoesBase.Consultar("select codaluno from aluno where nome = '"+nome+"'");
        int valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codaluno");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    
        
    }
       
    public static String CodeToName( int code )
    {
    
        ResultSet rs = OperacoesBase.Consultar("select nome from aluno where codaluno = '"+code+"'");
        String nome  = "";
        try {
            while( rs.next() )
            {
                nome = rs.getString("nome");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nome;
    
        
    }
    
      public static int Codigo_Escola_to_codigoBd( String code )
    {
    
        ResultSet rs = OperacoesBase.Consultar("select codaluno from aluno where codigo_escola = '"+code+"'");
        int codigo  = 0;
        try {
            while( rs.next() )
            {
                codigo = rs.getInt("codaluno");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codigo;
    
        
    }
    
     public static String CodeToFoto( int code )
    {
    
        ResultSet rs = OperacoesBase.Consultar("select fotografia from aluno where codaluno = '"+code+"'");
        String nome  = "";
        try {
            while( rs.next() )
            {
                nome = rs.getString("fotografia");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nome;
    
        
    }
    
    public static String NameToBi( String nome_aluno )
    {
    
        ResultSet rs = OperacoesBase.Consultar("select bi_cedula  from aluno where nome = '"+nome_aluno+"'");
        String nome  = "";
        try {
            while( rs.next() )
            {
                nome = rs.getString("bi_cedula");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nome;

    }

    public static String codeToClasse( int code  )
    {
        int ano = Integer.parseInt(MesAno.Get_AnoActualCobranca());
        int anoPesquisa = (ano == 2021)?2020:2020;
        ResultSet rs = OperacoesBase.Consultar("select nome_classe  from matricula_confirmacao inner join turma using(codturma) inner join classe using(codclasse) where codaluno = '"+code+"' and anolectivo ='"+anoPesquisa+"'");
        String classe  = "";
        try {
            while( rs.next() )
            {
                classe = rs.getString("nome_classe");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classe;

    }
    
    public static String codeToPeriodo( int code  )
    {
        ResultSet rs = OperacoesBase.Consultar("select periodo  from aluno inner join matricula_confirmacao using(codaluno) inner join turma using(codturma) where codaluno = '"+code+"'");
        String classe  = "";
        try {
            while( rs.next() )
            {
                classe = rs.getString("periodo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classe;

    }
    
    public static String codeToTurma( int code  )
    {
        ResultSet rs = OperacoesBase.Consultar("select nome_turma  from aluno inner join matricula_confirmacao using(codaluno) inner join turma using(codturma) where codaluno = '"+code+"'");
        String classe  = "";
        try {
            while( rs.next() )
            {
                classe = rs.getString("nome_turma");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classe;

    }
     
    
    public static String codeToCurso( int code  )
    {
        int ano = Integer.parseInt(MesAno.Get_AnoActualCobranca());
        int anoPesquisa = (ano == 2021)?2020:2020;
        
        ResultSet rs = OperacoesBase.Consultar("select nome_curso  from aluno inner join matricula_confirmacao using(codaluno) inner join turma using(codturma) inner join curso using(codcurso) where codaluno = '"+code+"' and anolectivo = '"+anoPesquisa+"'");
        String classe  = "";
        try {
            while( rs.next() )
            {
                classe = rs.getString("nome_curso");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classe;

    }
    
    
    public static String codeToSexo( int code  )
    {
        ResultSet rs = OperacoesBase.Consultar("select sexo  from aluno where codaluno = '"+code+"'");
        String classe  = "";
        try {
            while( rs.next() )
            {
                classe = rs.getString("sexo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classe;

    }
    
    
     public static String codeToDataNasc( int code  )
    {
        ResultSet rs = OperacoesBase.Consultar("select datanasc  from aluno where codaluno = '"+code+"'");
        String classe  = "";
        try {
            while( rs.next() )
            {
                classe = rs.getString("datanasc");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classe;

    }
     
       public static String codeToBi( int code  )
    {
        ResultSet rs = OperacoesBase.Consultar("select bi_cedula  from aluno where codaluno = '"+code+"'");
        String nome  = "";
        try {
            while( rs.next() )
            {
                nome = rs.getString("bi_cedula");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nome;

    }
    
    public static ObservableList<Estudante> ListaEstudantesParametros( int codturma  )
    {
        ResultSet rs = OperacoesBase.Consultar("select * from aluno where  codturma = '"+codturma+"'");
        ObservableList<Estudante> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                Estudante e = new Estudante();
                e.setNome(rs.getString("nome"));
                e.setCedula_bi("bi_cedula");
                e.setTipo(rs.getString("tipo_aluno"));
                
                lista.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;

    }
    
    
     public static ObservableList<Estudante> ListaEstudantesParametros( String curso , String classe, int codturma  )
    {
        ResultSet rs = OperacoesBase.Consultar("select * from aluno inner join matricula_confirmacao using(codaluno) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where nome_curso = '"+curso+"' and nome_classe = '"+classe+"' and codturma = '"+codturma+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"' order by nome asc");
        ObservableList<Estudante> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                Estudante e = new Estudante();
                e.setNome(rs.getString("nome"));
                e.setCedula_bi("bi_cedula");
                e.setTipo(rs.getString("tipo_aluno"));
                
                lista.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;

    }
    
     public static boolean EstudanteNormal( int codaluno  )
    {
        ResultSet rs = OperacoesBase.Consultar("select tipo_aluno from aluno where  codaluno = '"+codaluno+"'");
        String tipo = "Normal";
        try {
            while( rs.next() )
            {
               tipo = rs.getString("tipo_aluno");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Normal".equalsIgnoreCase(tipo);

    }
    
    
    public static boolean ListaEstudanteMatricula_Confirmacao( int codaluno , String efeito)
    {
        int ano = Integer.parseInt(MesAno.Get_AnoActualCobranca());
        String efeito2 = "Matricula";
        String sql = "select nome from aluno inner join matricula_confirmacao using(codaluno) inner join  pagamento using(codmatricula_c) where codaluno = '"+codaluno+"' and (descricao = '"+efeito+"' or descricao = '"+efeito2+"' ) and ano_referencia = '"+(ano)+"' order by nome asc";
        ResultSet rs = OperacoesBase.Consultar(sql);
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista.size() > 0;
    }
    
    public static boolean ListaEstudante_Ja_Matricula_Uma_Vez( int codaluno , String efeito)
    {
        String sql = "select nome from aluno inner join matricula_confirmacao using(codaluno) inner join  pagamento using(codmatricula_c) where codaluno = '"+codaluno+"' and descricao = '"+efeito+"' order by nome asc";
        ResultSet rs = OperacoesBase.Consultar(sql);
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista.size() > 0;
    }
    
    
    
    public static String GetClasse( int codigo , String anolectivo )
    {
        String sql = "select nome_classe from aluno inner join matricula_confirmacao using(codaluno) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where codaluno = '"+codigo+"' order by nome";
        ResultSet rs = OperacoesBase.Consultar(sql);
        String classe = "";
        try {
            while( rs.next() )
            {
                classe = rs.getString("nome_classe");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classe;
    }
    
    public static ObservableList<String> Meses_Pago( int codigo )
    {
        String ano = MesAno.Get_AnoActualCobranca();
        String efeito = "Propina";
        String sql = "select mes_referente from aluno inner join matricula_confirmacao using(codaluno) inner join pagamento using(codmatricula_c) where codaluno = '"+codigo+"' and descricao = '"+efeito+"' and ano_referencia = '"+ano+"' order by nome asc";
        ResultSet rs = OperacoesBase.Consultar(sql);
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("mes_referente"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
     public static ObservableList<String> FiltraEstudantes( String classe )
    {
        ResultSet rs = OperacoesBase.Consultar("select nome from aluno where classe = '"+classe+"' order by nome");
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
        
    }//fim do metodo
    
     public  static ObservableList<String> ListaAlunosGeral()
     {
        String sql = "select * from aluno order by nome asc";
        ResultSet rs = OperacoesBase.Consultar(sql);
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
     }
     
     public  static ObservableList<String> ListaAlunosGeralTurma( int codturma )
     {
         String descricao = "Matricula";
        String sql = "select nome  from aluno inner join matricula_confirmacao using(codaluno) inner join pagamento using(codmatricula_c) inner join turma using(codturma) where codturma = '"+codturma+"' and descricao = '"+descricao+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"' order by nome asc";
        ResultSet rs = OperacoesBase.Consultar(sql);
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
     }
     
     public static ObservableList<String> ListaGeralAlunosMatriculadosporTurma( int codturma )
     {
        String descricao = "Propina";
        String sql = "select nome  from view_matricula where codturma = '"+codturma+"' and descricao != '"+descricao+"' and anolectivo ='"+String.valueOf(Integer.parseInt(MesAno.Get_AnoActualCobranca()))+"' order by nome asc";
        ResultSet rs = OperacoesBase.Consultar(sql);
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
     }
      
     
      public static ObservableList<String> ListaGeralAlunosMatriculadosporTurma_Anolectivo( int codturma, String anolectivo )
     {
        String descricao = "Propina";
        String sql = "select nome  from view_matricula where codturma = '"+codturma+"' and descricao != '"+descricao+"' and anolectivo ='"+anolectivo+"' order by nome asc";
        ResultSet rs = OperacoesBase.Consultar(sql);
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
     }
     
    
   
     public  static ObservableList<String> ListaAlunosGeralPorCurso_Classe( String curso , String classe , String periodo )
     {
        String sql = "select nome from aluno inner join matricula_confirmacao using(codaluno) inner join pagamento p using(codmatricula_c) inner join turma t using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where p.descricao != 'Propina' and nome_curso = '"+curso+"' and nome_classe = '"+classe+"' and t.periodo = '"+periodo+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"' order by nome asc";
        ResultSet rs = OperacoesBase.Consultar(sql);
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
     }
     
     public static int QuantidadeMatriculados( String anolectivo )
     {
         String descricao = "Propina";
         String sql = "select * from view_matricula where anolectivo = '"+anolectivo+"' and descricao != '"+descricao+"'";
         ObservableList<Estudante> lista  = FXCollections.observableArrayList();
         ResultSet rs = OperacoesBase.Consultar(sql);
         try {
            while( rs.next() )
            {
                Estudante e = new Estudante();
                e.setCodigo(rs.getInt("codaluno"));
                lista.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudante.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         return lista.size();
     }
     
     
     public static int QuantidadeEstudanteGeral()
     {
         return ListaAlunosGeral().size();
     }
     
    
}
