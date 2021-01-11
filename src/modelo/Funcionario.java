/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Bd.OperacoesBase;
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
public class Funcionario {

   
    
    private int codfuncionario;
    private String nome;
    private String bi_cedula;
    private LocalDate data_nascimento;
    private String status;
    private String funcao;
    private String anoAdmissao;
    private String sexo;
    private String codigo_escola_f;

    public Funcionario() {
    }

    public Funcionario(int codfuncionario, String nome, String bi_cedula, LocalDate data_nascimento, String status, String funcao, String anoAdmissao, String sexo, String codigo_escola_f) {
        this.codfuncionario = codfuncionario;
        this.nome = nome;
        this.bi_cedula = bi_cedula;
        this.data_nascimento = data_nascimento;
        this.status = status;
        this.funcao = funcao;
        this.anoAdmissao = anoAdmissao;
        this.sexo = sexo;
        this.codigo_escola_f = codigo_escola_f;
    }

   

    public String getCodigo_escola_f() {
        return codigo_escola_f;
    }

    public void setCodigo_escola_f(String codigo_escola_f) {
        this.codigo_escola_f = codigo_escola_f;
    }

   

    
    
    public int getCodfuncionario() {
        return codfuncionario;
    }

    public void setCodfuncionario(int codfuncionario) {
        this.codfuncionario = codfuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    
    
    public String getBi_cedula() {
        return bi_cedula;
    }

    public void setBi_cedula(String bi_cedula) {
        this.bi_cedula = bi_cedula;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getAnoAdmissao() {
        return anoAdmissao;
    }

    public void setAnoAdmissao(String anoAdmissao) {
        this.anoAdmissao = anoAdmissao;
    }

  
    
    
    public boolean Adicionar()
    {
        String sql = "insert into funcionario values('"+this.getCodfuncionario()+"','"+this.getNome()+"','"+this.getBi_cedula()+"','"+this.getData_nascimento()+"','"+this.getFuncao()+"','"+this.getStatus()+"','"+this.getAnoAdmissao()+"','"+this.getSexo()+"','"+this.getCodigo_escola_f()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
    public boolean Eliminar()
    {
        String sql = "delete from funcionario where codfuncionario = '"+this.getCodfuncionario()+"'";
        return OperacoesBase.Eliminar(sql);
    }
    
    public boolean Actualizar() 
    {
        String sql = "update funcionario set nome='"+this.getNome()+"',bi_cedula='"+this.getBi_cedula()+"',data_nascimento='"+this.getData_nascimento()+"',funcao='"+this.getFuncao()+"',status='"+this.getStatus()+"',ano_admissao='"+this.getAnoAdmissao()+"' ,sexo ='"+this.getSexo()+"' where codfuncionario = '"+this.getCodfuncionario()+"'";
        return OperacoesBase.Actualizar(sql);
    }

  //*************************METODOS OPERACIONAIS*******************************************   
    public static int UltimoCodigo()
    {
        int codigo = 0;
        String sql = "select codfuncionario from funcionario";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                codigo = rs.getInt("codfuncionario");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Funcionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++codigo;
    }
    
    public static Funcionario Obter_Funcionario( int codfuncionario )
    {
        String sql = "select * from funcionario where codfuncionario = '"+codfuncionario+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        Funcionario f = null;
        try {
            while( rs.next() )
            {
                f= new Funcionario();
                f.setNome(rs.getString("nome"));
                f.setBi_cedula(rs.getString("bi_cedula"));
                f.setData_nascimento(DefinicoesData.StringtoLocalDate(rs.getString("data_nascimento")));
                f.setFuncao(rs.getString("funcao"));
                f.setStatus(rs.getString("status"));
                f.setAnoAdmissao(rs.getString("ano_admissao"));
                f.setSexo(rs.getString("sexo"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Funcionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return f;
    }
    
    public static ObservableList<Funcionario> ListaFuncionarios_paraTabela()
    {
        ObservableList<Funcionario> lista = FXCollections.observableArrayList();
        String sql = "select * from funcionario";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                Funcionario f= new Funcionario();
                f.setCodfuncionario(rs.getInt("codfuncionario"));
                f.setNome(rs.getString("nome"));
                f.setBi_cedula(rs.getString("bi_cedula"));
                f.setData_nascimento(DefinicoesData.StringtoLocalDate(rs.getString("data_nascimento")));
                f.setFuncao(rs.getString("funcao"));
                f.setStatus(rs.getString("status"));
                f.setAnoAdmissao(rs.getString("ano_admissao"));
                f.setSexo(rs.getString("sexo"));
                f.setCodigo_escola_f(rs.getString("codigo_escola_f"));
                
                lista.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Funcionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public static String code_to_Bi( int code )
    {
        String sql = "select bi_cedula from funcionario where codfuncionario = '"+code+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        String bi = null;
        try {
            while( rs.next() )
            {
                bi = rs.getString("bi_cedula");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Funcionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return bi;
    }
    
    public static String code_to_DataNasc( int code )
    {
        String sql = "select data_nascimento from funcionario where codfuncionario = '"+code+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        String data = null;
        try {
            while( rs.next() )
            {
                data = rs.getString("data_nascimento");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Funcionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }
    
    public static ObservableList<String> ListaFuncionarios_por_funcao( String funcao  )
    {
        ObservableList<String> lista = FXCollections.observableArrayList();
        String sql = "select * from funcionario where funcao = '"+funcao+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Funcionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public static int NametoCode( String nome )
    {
        String sql ="select codfuncionario from funcionario where nome = '"+nome+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        int cod = -1;
        try {
            while( rs.next() )
            {
                cod = rs.getInt("codfuncionario");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Funcionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cod;
    }

    public static ObservableList<String> ListaFuncionarios_Funcao_Professores_Nomes()
    {
        ObservableList<String> lista = FXCollections.observableArrayList();
        String funcao = "Professor(a)";
        String sql = "select * from funcionario where funcao = '"+funcao+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Funcionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
     public static String cod_to_Name(int aInt) {
         
         
        String sql ="select nome from funcionario where codfuncionario = '"+aInt+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        String nome = "";
        try {
            while( rs.next() )
            {
                nome = rs.getString("nome");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Funcionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nome;
         
    }
     
     public static String cod_to_Funcao(int aInt) {
         
         
        String sql ="select funcao from funcionario where codfuncionario = '"+aInt+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        String nome = "";
        try {
            while( rs.next() )
            {
                nome = rs.getString("funcao");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Funcionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nome;
         
    }
    
    
}
