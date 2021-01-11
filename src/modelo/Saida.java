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
public class Saida {
    
    private int codsaida;
    private int coduser;
    private String efeito;
    private String valor;
    private String data_saida;
    private String nome_funcionario;
    private int valiadada=0;
    private int cod_user_valida = 0;
    private String exibe_validacao;
    private String data_aprovacao;
    private static int valorTotal = 0;

    public Saida()
    {
        
    }
            
    public Saida( int coduser, String efeito, String valor, String data_saida, int validada) {
        this.coduser = coduser;
        this.efeito = efeito;
        this.valor = valor;
        this.data_saida = data_saida;
        this.valiadada = validada;
    }

    public int getCodsaida() {
        return codsaida;
    }

    public String getData_aprovacao() {
        return data_aprovacao;
    }

    public void setData_aprovacao(String data_aprovacao) {
        this.data_aprovacao = data_aprovacao;
    }
    
    

    public void setCodsaida(int codsaida) {
        this.codsaida = codsaida;
    }

    public int getCoduser() {
        return coduser;
    }

    public void setCoduser(int coduser) {
        this.coduser = coduser;
    }

    public String getEfeito() {
        return efeito;
    }

    public void setEfeito(String efeito) {
        this.efeito = efeito;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getData_saida() {
        return data_saida;
    }

    public void setData_saida(String data_saida) {
        this.data_saida = data_saida;
    }

    public String getNome_funcionario() {
        return nome_funcionario;
    }

    public void setNome_funcionario(String nome_funcionario) {
        this.nome_funcionario = nome_funcionario;
    }

    public int getValiadada() {
        return valiadada;
    }

    public void setValiadada(int valiadada) {
        this.valiadada = valiadada;
    }

    public String getExibe_validacao() {
        return exibe_validacao;
    }

    public void setExibe_validacao(String exibe_validacao) {
        this.exibe_validacao = exibe_validacao;
    }

    public int getCod_user_valida() {
        return cod_user_valida;
    }

    public void setCod_user_valida(int cod_user_valida) {
        this.cod_user_valida = cod_user_valida;
    }
    
    
    public boolean Adicionar()
    {
        setCodsaida(this.UltimoCodigoSaida());
        String sql = "insert into saida values('"+this.getEfeito()+"','"+this.getValor()+"','"+this.getData_saida()+"','"+this.getCoduser()+"','"+getCodsaida()+"','"+getValiadada()+"', '"+getData_aprovacao()+"','"+getCod_user_valida()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
    public boolean Eliminar()
    {
        String sql = "delete from saida where cod_saida = '"+this.getCodsaida()+"'";
        return OperacoesBase.Eliminar(sql);
    }
    
    public boolean AutorizarSaida()
    {
        setCod_user_valida(Usuario.NameToCode(Usuario.getUsuario_activo()));
        String sql = "update saida set validada = 1 , cod_user_valida = '"+getCod_user_valida()+"' , data_validacao = '"+getData_aprovacao()+"' where cod_saida = '"+this.getCodsaida()+"'";
        return OperacoesBase.Actualizar(sql);
    }
    
    public boolean Preenche_Recibo()
    {
        String sql= "insert into Recibo_saida values('"+getCodsaida()+"','"+getEfeito()+"','"+getValor()+"','0','"+Usuario.Funcionario_User_Activo()+"','"+getCod_user_valida()+"','"+getData_saida()+"','"+getData_aprovacao()+"','"+getExibe_validacao()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
 /******************************************/
    
    public static ObservableList<Saida> CarregaDadosTabela()
    {
        valorTotal = 0;
        String sql="select * from saida order by cod_saida";
        ResultSet rs = OperacoesBase.Consultar(sql);
        ObservableList<Saida> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                Saida s = new Saida();
                s.setCodsaida(rs.getInt("cod_saida"));
                s.setEfeito(rs.getString("efeito"));
                s.setValor(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("valor")));
                s.setData_saida(rs.getString("data_saida"));
                s.setNome_funcionario(Usuario.CodeToName(rs.getInt("coduser")));
                s.setValiadada(rs.getInt("validada"));
                s.setExibe_validacao((s.getValiadada() == 0)?"Não Autorizada":"Autorizada");
                lista.add(s);
                
                valorTotal += rs.getInt("valor");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Saida.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
     public static ObservableList<Saida> CarregaDadosTabela_por_Ano( String ano )
    {
        valorTotal = 0;
        String sql="select * from saida where data_saida like '"+ano+"-%-%'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        ObservableList<Saida> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                Saida s = new Saida();
                s.setCodsaida(rs.getInt("cod_saida"));
                s.setEfeito(rs.getString("efeito"));
                s.setValor(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("valor")));
                s.setData_saida(rs.getString("data_saida"));
                s.setNome_funcionario(Usuario.CodeToName(rs.getInt("coduser")));
                s.setValiadada(rs.getInt("validada"));
                s.setExibe_validacao((s.getValiadada() == 0)?"Não Autorizada":"Autorizada");
                lista.add(s);
                
                valorTotal += rs.getInt("valor");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Saida.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
     
     
     
    public static ObservableList<Saida> CarregaDadosTabela_por_Ano_Mes( String mes,String ano )
    {
        valorTotal = 0;
        String sql="select * from saida where data_saida like '"+ano+"-"+mes+"-%'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        ObservableList<Saida> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                Saida s = new Saida();
                s.setCodsaida(rs.getInt("cod_saida"));
                s.setEfeito(rs.getString("efeito"));
                s.setValor(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("valor")));
                s.setData_saida(rs.getString("data_saida"));
                s.setNome_funcionario(Usuario.CodeToName(rs.getInt("coduser")));
                s.setValiadada(rs.getInt("validada"));
                s.setExibe_validacao((s.getValiadada() == 0)?"Não Autorizada":"Autorizada");
                lista.add(s);
                
                valorTotal += rs.getInt("valor");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Saida.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    private int UltimoCodigoSaida()
    {
        String sql = "select cod_Saida from saida";
        ResultSet rs = OperacoesBase.Consultar(sql);
        int valor = 0;
        try {
            while( rs.next() )
            {
              valor = rs.getInt("cod_saida");
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Saida.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++valor;
    }
    
    
    
     public static int valorTotalInteger()
     {
         return valorTotal;
     }
     
     public static String valorTotal()
     {
         return DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(valorTotal));
     }
     
     public static int valorTotal_Integer()
     {
         return valorTotal;
     }
}
