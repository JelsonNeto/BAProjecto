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
public class Aluno_divida {
    
    private int codigo;
    private int codaluno;
    private String ano;
    private String valor_apagar;
    private String valor_multa;

    public Aluno_divida() {
    }

    public String getAno() {
        return ano;
    }

    public int getCodaluno() {
        return codaluno;
    }

    
    public int getCodigo() {
        return codigo;
    }


    public String getValor_apagar() {
        return valor_apagar;
    }

    public String getValor_multa() {
        return valor_multa;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


    public void setValor_apagar(String valor_apagar) {
        this.valor_apagar = valor_apagar;
    }

    public void setValor_multa(String valor_multa) {
        this.valor_multa = valor_multa;
    }

    public void setCodaluno(int codaluno) {
        this.codaluno = codaluno;
    }
    

    
    public void show()
    {
        System.out.println(getCodigo());
        System.out.println(getValor_apagar());
        System.out.println(getValor_multa());
    }
    
    
     public static int RetornarUltimoCodDivida()
     {
        
        ResultSet rs = OperacoesBase.Consultar("select coddivida from divida_alunos");
        int valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("coddivida");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++valor;
     }
    
     
     public  boolean JaExiste()
     {
        
        ResultSet rs = OperacoesBase.Consultar("select coddivida from divida_alunos where codaluno = '"+this.getCodaluno()+"'  and ano_referente = '"+this.getAno()+"'");
        int valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("coddivida");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          return valor != 0;
    
     }
     
     public  void Inserir()
     {
         OperacoesBase.Inserir("insert into divida_alunos values('"+this.getCodigo()+"','"+this.getCodaluno()+"' , '"+this.getAno()+"' , '"+this.getValor_apagar()+"', '"+this.getValor_multa()+"')");
     }
     
       public static ObservableList<String> RetornarListaEstudante()
     {
        ObservableList<String> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar("select codaluno from divida_alunos");
        try {
            while( rs.next() )
            {
                lista.add(Estudante.CodeToName(rs.getInt("codaluno")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
     }
    
}
