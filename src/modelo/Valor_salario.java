/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Bd.OperacoesBase;
import definicoes.DefinicoesUnidadePreco;
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
public class Valor_salario {
    
    private int codigo;
    private String funcao;
    private String valor;
    private String desconto;

    public Valor_salario() {
    }

    public Valor_salario(int codigo, String funcao, String valor , String desconto) {
        this.codigo = codigo;
        this.funcao = funcao;
        this.valor = valor;
        this.desconto = desconto;
    }

    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getFuncao() {
        return funcao;
    }

    public String getDesconto() {
        return desconto;
    }

    public void setDesconto(String desconto) {
        this.desconto = desconto;
    }
    
    

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    public boolean Adicionar()
    {
        String sql = "insert into salario_funcionario values('"+this.getCodigo()+"', '"+this.getFuncao()+"','"+this.getValor()+"','"+this.getDesconto()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
    public boolean Actualizar()
    {
        String sql = "update salario_funcionario set funcao = '"+this.getFuncao()+"' , valor = '"+this.getValor()+"', desconto = '"+this.getDesconto()+"'";
        return OperacoesBase.Actualizar(sql);
    }
    
    public static int Ultimocodigo()
    {
         int codigo = 0;
        String sql = "select codsalario from salario_funcionario";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                codigo = rs.getInt("codsalario");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Valor_salario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++codigo;
    }
    
    public static ObservableList<Valor_salario> ListaValores()
    {
       ObservableList<Valor_salario> lista = FXCollections.observableArrayList();
        String sql = "select * from salario_funcionario";
        ResultSet rs= OperacoesBase.Consultar(sql);
        try {
            while( rs.next())
            {
                Valor_salario s= new Valor_salario();
                s.setCodigo(rs.getInt("codsalario"));
                s.setFuncao(rs.getString("funcao"));
                s.setValor(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("valor")));
                s.setDesconto(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("desconto")));

                lista.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Valor_salario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public static String ObterSalario_por_funcao( String funcao )
    {
        String sql = "select valor from salario_funcionario where funcao = '"+funcao+"'";
        ResultSet rs= OperacoesBase.Consultar(sql);
        String valor = "0";
        try {
            while( rs.next())
            {
                valor = rs.getString("valor");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Valor_salario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }
    
    public static int Funcao_to_Code( String funcao )
    {
        String sql = "select codsalario from  salario_funcionario where funcao = '"+funcao+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        int cod = -1;
        try {
            while( rs.next() )
            {
                cod = rs.getInt("codsalario");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Valor_salario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cod;
    }
    
    public static String Obter_Desconto_Por_Funcao( String funcao )
    {
        String valor = "0";
         String sql = "select desconto from  salario_funcionario where funcao = '"+funcao+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                valor = rs.getString("desconto");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Valor_salario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
        
    }
    
     public static ObservableList<String> Obter_Funcao_jaCadastrada()
    {
        String sql = "select funcao from  salario_funcionario";
        ObservableList<String> lista= FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("funcao"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Valor_salario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
        
    }
}