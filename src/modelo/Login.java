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

/**
 *
 * @author Familia Neto
 */
public class Login {
    
    private String username;
    private String senha;
    private String tipo;

    public Login() {
    }

    public String getSenha() {
        return senha;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUsername() {
        return username;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public boolean VerificaUsuario()
    {
      ResultSet rs = OperacoesBase.Consultar("select * from usuario where username = '"+this.getUsername()+"' and senha = '"+this.getSenha()+"'");
      int valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("coduser");
            } } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valor != 0;
    }
    
}
