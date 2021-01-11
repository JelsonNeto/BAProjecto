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
import Controlador.Preco.AdicionarprecoController;

/**
 *
 * @author Familia Neto
 */
public class Preco {
    
    private int codigo;
    private String valor;
    private int codcurso;
    private String classe;
    private String valor_multa;
    private String efeito;
    private String curso;
    private String valor_tabela;

    public Preco() {
    }

    public String getClasse() {
        return classe;
    }

    public int getCodcurso() {
        return codcurso;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getEfeito() {
        return efeito;
    }

    public String getValor_multa() {
        return valor_multa;
    }

    public String getValor() {
        return valor;
    }

    public String getCurso() {
        return curso;
    }

    public String getValor_tabela() {
        return valor_tabela;
    }

    public void setValor_multa(String valor_multa) {
        this.valor_multa = valor_multa;
    }

    
    
    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setValor_tabela(String valor_tabela) {
        this.valor_tabela = valor_tabela;
    }

    
    public void setCodcurso(int codcurso) {
        this.codcurso = codcurso;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setEfeito(String efeito) {
        this.efeito = efeito;
    }

  

    public void setCurso(String curso) {
        this.curso = curso;
    }
    

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    
    public boolean Adicionar()
    {
        String sql = "Insert into preco values( '"+this.getCodigo()+"','"+this.getCodcurso()+"', '"+this.getClasse()+"','"+this.getEfeito()+"', '"+this.getValor()+"', '"+this.getValor_multa()+"' )";
        return OperacoesBase.Inserir(sql);
    }
    
  
    public int RetornaUltimoCodigo()
    {
        ResultSet rs = OperacoesBase.Consultar("select codpreco from preco");
        int id = 0;
        try {
            while( rs.next() )
            {
                id = rs.getInt("codpreco");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdicionarprecoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++id;
    }
    
    public static String RetornaValor_Multa( String classe , int codcurso)
    {
        String efeito = "Propina";
        ResultSet rs = OperacoesBase.Consultar("select valor_multa from preco where classe = '"+classe+"' and codcurso = '"+codcurso+"' and efeito ='"+efeito+"'");
        String valor = "0";
        try {
            while( rs.next() )
            {
                valor = rs.getString("valor_multa");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdicionarprecoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }
    
    public static String ValorPreco( String classe , int codcurso, String descricao)
    {
        String efeito_selected = descricao;
        ResultSet rs = OperacoesBase.Consultar("select valor from preco where classe = '"+classe+"' and codcurso = '"+codcurso+"' and efeito = '"+efeito_selected+"'");
        String valor = "0";
        try {
            while( rs.next() )
            {
                valor = rs.getString("valor");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdicionarprecoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }
    
    
    public static ObservableList<String> listaPreco( int  codcurso , String classe )
    {
        
        String sql = "select efeito from preco where codcurso = '"+codcurso+"' and classe = '"+classe+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("efeito"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Preco.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    
}
