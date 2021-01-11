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
public class Encarregado {
    
    private int codigo;
    private String nomepai;
    private String nomemae;                                  
    private LocalDate datanasc;
    private String ocupacao;
    private String endereco;
    private String contacto;
    private String sexo;

    public int getCodigo() {
        return codigo;
    }

    public String getNomepai() {
        return nomepai;
    }

    public void setNomepai(String nomepai) {
        this.nomepai = nomepai;
    }

    public String getNomemae() {
        return nomemae;
    }

    public void setNomemae(String nomemae) {
        this.nomemae = nomemae;
    }

    public String getContacto() {
        return contacto;
    }

    public LocalDate getDatanasc() {
        return datanasc;
    }

    public String getEndereco() {
        return endereco;
    }

   

    public String getOcupacao() {
        return ocupacao;
    }

    public String getSexo() {
        return sexo;
    }

    
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setDatanasc(LocalDate datanasc) {
        this.datanasc = datanasc;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

   

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    
    
    
    public boolean Adicionar()
    {
        String sql = "insert into encarregado values('"+this.getCodigo()+"' ,'"+this.getDatanasc()+"', '"+this.getOcupacao()+"', '"+this.getEndereco()+"' , '"+this.getContacto()+"' , '"+this.getSexo()+"' , '"+this.getNomepai()+"' , '"+this.getNomemae()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
      
    public boolean Actualizar()
    {
        String sql = "update encarregado set datanascimento = '"+this.getDatanasc()+"' ,ocupacao =  '"+this.getOcupacao()+"' , endereco =  '"+this.getEndereco()+"',contacto =  '"+this.getContacto()+"' ,sexo =  '"+this.getSexo()+"' , nomepai = '"+this.getNomepai()+"' , nomemae = '"+this.getNomemae()+"'  where codencarregado = '"+this.getCodigo()+"'";
        return OperacoesBase.Actualizar(sql);
    }
    
    
/********************************Metodos Auxiliares************************************************************************************/
    /**
     * 
     * @return 
     */
    public static int retornaUltimoCodigo()
    {
         ResultSet rs = OperacoesBase.Consultar("select * from encarregado order by codencarregado");
        int valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codencarregado");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Encarregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++valor;
    }
            
    public static ObservableList<String> ListaAlunosPorEncarregado( int codencarregado )
    {
        String sql = "select a.nomepai from encarregado inner join encarregado_aluno using( codencarregado ) inner join aluno a using(codaluno) where codencarregado = '"+codencarregado+"'";
        ObservableList<String> lista  = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nomepai"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Encarregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public static String NomeAlunosToEncarregado( int codencarregado )
    {
        String sql = "select e.nomepai from encarregado e inner join encarregado_aluno using( codencarregado ) inner join aluno a using(codaluno) where codaluno = '"+codencarregado+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        
        try {
            while( rs.next() )
            {
                return rs.getString("nomepai");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Encarregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
  
    public static String codalunoNomeEncarregado( int codaluno )
    {
        String sql = "select e.nomepai from encarregado e inner join encarregado_aluno using(codencarregado) inner join aluno using(codaluno) where codaluno = '"+codaluno+"' ";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                return rs.getString("nomepai");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Encarregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Sem Encarregado Cadastrado";
    }
  
}
