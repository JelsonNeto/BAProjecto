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
public class Modelo_privilegio {
    
    private int coduser;
    private int leitura;
    private int insercao;
    private int eliminacao;
    private int impreesao;
    private int actualizacao;
    private int cod_admin;
    

    public Modelo_privilegio() {
    }

    
    
    public Modelo_privilegio(int coduser, int leitura, int insercao, int eliminacao, int impreesao, int actualizacao, int cod_admin) {
        this.coduser = coduser;
        this.leitura = leitura;
        this.insercao = insercao;
        this.eliminacao = eliminacao;
        this.impreesao = impreesao;
        this.actualizacao = actualizacao;
        this.cod_admin = cod_admin;
    }

    public int getCoduser() {
        return coduser;
    }

    public void setCoduser(int coduser) {
        this.coduser = coduser;
    }

    public int getLeitura() {
        return leitura;
    }

    public void setLeitura(int leitura) {
        this.leitura = leitura;
    }

    public int getInsercao() {
        return insercao;
    }

    public void setInsercao(int insercao) {
        this.insercao = insercao;
    }

    public int getEliminacao() {
        return eliminacao;
    }

    public void setEliminacao(int eliminacao) {
        this.eliminacao = eliminacao;
    }

    public int getImpreesao() {
        return impreesao;
    }

    public void setImpreesao(int impreesao) {
        this.impreesao = impreesao;
    }

    public int getActualizacao() {
        return actualizacao;
    }

    public void setActualizacao(int actualizacao) {
        this.actualizacao = actualizacao;
    }

    public int getCod_admin() {
        return cod_admin;
    }

    public void setCod_admin(int cod_admin) {
        this.cod_admin = cod_admin;
    }
    
    
    public boolean Adicionar()
    {
        String sql = "insert into privilegios_usuario values('"+this.getCoduser()+"','"+this.getLeitura()+"','"+this.getInsercao()+"','"+this.getEliminacao()+"','"+this.getActualizacao()+"','"+this.getImpreesao()+"')";
        return OperacoesBase.Inserir(sql);
    }
   
    

    public static int Obter_Insercao(int codfuncionario )
   {
       ResultSet rs = OperacoesBase.Consultar("select * from privilegios_usuario where codfuncionario = '"+codfuncionario+"'");
       int valor = 0; 
       try {
            while( rs.next())
            {
               valor = Integer.parseInt(rs.getString("insercao"));
            }} catch (SQLException ex) {
            Logger.getLogger(Licenca.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valor;
   }
    
    public static int Obter_Leitura(int codfuncionario )
   {
       ResultSet rs = OperacoesBase.Consultar("select * from privilegios_usuario where codfuncionario = '"+codfuncionario+"'");
       int valor = 0; 
       try {
            while( rs.next())
            {
               valor = Integer.parseInt(rs.getString("leitura"));
            }} catch (SQLException ex) {
            Logger.getLogger(Licenca.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valor;
   }
    
    public static int Obter_Eliminacao(int codfuncionario )
   {
       ResultSet rs = OperacoesBase.Consultar("select * from privilegios_usuario where codfuncionario = '"+codfuncionario+"'");
       int valor = 0; 
       try {
            while( rs.next())
            {
               valor = Integer.parseInt(rs.getString("eliminacao"));
            }} catch (SQLException ex) {
            Logger.getLogger(Licenca.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valor;
   }
    
    public static int Obter_Actualizacao(int codfuncionario )
   {
       ResultSet rs = OperacoesBase.Consultar("select * from privilegios_usuario where codfuncionario = '"+codfuncionario+"'");
       int valor = 0; 
       try {
            while( rs.next())
            {
               valor = Integer.parseInt(rs.getString("actualizacao"));
            }} catch (SQLException ex) {
            Logger.getLogger(Licenca.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valor;
   }
    
    public static int Obter_Impressao(int codfuncionario )
   {
       ResultSet rs = OperacoesBase.Consultar("select * from privilegios_usuario where codfuncionario = '"+codfuncionario+"'");
       int valor = 0; 
       try {
            while( rs.next())
            {
               valor = Integer.parseInt(rs.getString("impreensao"));
            }} catch (SQLException ex) {
            Logger.getLogger(Licenca.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valor;
   }
    
    public static ObservableList UsuariosComPrevilegios()
    {
        ResultSet rs = OperacoesBase.Consultar("select username from privilegios_usuario inner join usuario using(coduser)");
        ObservableList lista = FXCollections.observableArrayList();
        try {
             while( rs.next())
             {
                 lista.add(rs.getString("username"));
              }} catch (SQLException ex) {
             Logger.getLogger(Licenca.class.getName()).log(Level.SEVERE, null, ex);
         }

         return lista;
    }
    
     

    
  
           
    
}
