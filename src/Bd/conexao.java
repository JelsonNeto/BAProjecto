/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jelson Neto
 */
public class conexao {
    
    private static final String Driver = "org.postgresql.Driver";
    private static final String url = "jdbc:postgresql://localhost:5432/bd_francisco";
    private static final String pass = "1234";
    private static final String user = "postgres";
    private static Connection connection;
    private static PreparedStatement statement;
    
     
    public static Connection  Conectar()
    {
        try {
            Class.forName(Driver);
            connection =  DriverManager.getConnection(url, user, pass);
            
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao conectar-se com a base de dados.");
        }
        
        return connection;
    }
    
    public static void TerminarConexao()
    {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao terminar a conexao com a base de dados.");
        }
    }
    
    
    public static Connection ObterConection()
    {
        return connection;
    }
    
    public static int NonResultSetQuery( String sql )
    {
        int valor = 0;
        Conectar();
        try {
            statement =  connection.prepareStatement(sql);
            valor = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        TerminarConexao();
        return valor;
    }
    
    public static ResultSet ResultSetQuery( String sql )
    {
        ResultSet rs = null;
        Conectar();
        try {
              statement = connection.prepareStatement(sql);
              rs  = statement.executeQuery();
                 
        } catch (SQLException ex) {
            Logger.getLogger(conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        TerminarConexao();
        return rs;
    }
    
    
}
