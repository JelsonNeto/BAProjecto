/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Validacoes;

import Bd.OperacoesBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Familia Neto
 */
public class validarEstudante {
    
    
    public static boolean EstaoVazios( String foto, String nome, String bi, LocalDate data, String sexo, String periodo , String curso, String turma , String classe, String tipo)
    {
        
        return "".equals(foto) || "".equals(bi) || "".equals(sexo) || "".equals(periodo) || null == curso || null == turma || null == classe || data == null || tipo == null;
              
    }
    
     public static boolean EstaoVaziosActualizar( String foto, String nome, String bi, LocalDate data, String sexo, String periodo , String curso, String turma , String classe, String tipo, String status )
    {
        
        return "".equals(foto) || "".equals(bi) || "".equals(sexo) || "".equals(periodo) || null == curso || null == turma || null == classe || data == null || tipo == null || status == null;
              
    }
    
    public static boolean JaExisteAluno( String nome)
    {
              int codigo = 0;
              ResultSet rs = OperacoesBase.Consultar("select codaluno from aluno where nome = '"+nome+"'"); 
                try {
                    while( rs.next() )
                     {
                         codigo = rs.getInt("codaluno");
                     }
                } catch (SQLException ex) {
                 Logger.getLogger(validaEfectuarPagamento.class.getName()).log(Level.SEVERE, null, ex);
                     }
        
        return codigo != 0;
        
    }
    
     public static boolean JaExisteAluno_Actualizacao( String nome , int codigo_aluno)
    {
              int codigo = 0;
              ResultSet rs = OperacoesBase.Consultar("select codaluno from aluno where nome = '"+nome+"' and codaluno != '"+codigo_aluno+"'"); 
                try {
                    while( rs.next() )
                     {
                         codigo = rs.getInt("codaluno");
                     }
                } catch (SQLException ex) {
                 Logger.getLogger(validaEfectuarPagamento.class.getName()).log(Level.SEVERE, null, ex);
                     }
        
        return codigo != 0;
        
    }
     
     public static boolean JaExisteBiCedula( String bi , int codigo_aluno)
    {
              int codigo = 0;
              ResultSet rs = OperacoesBase.Consultar("select codaluno from aluno where bi_cedula = '"+bi+"' and codaluno != '"+codigo_aluno+"'"); 
                try {
                    while( rs.next() )
                     {
                         codigo = rs.getInt("codaluno");
                     }
                } catch (SQLException ex) {
                 Logger.getLogger(validaEfectuarPagamento.class.getName()).log(Level.SEVERE, null, ex);
                     }
        
        return codigo != 0;
        
    }
    
    public static boolean JaExisteBiCedula( String bi )
    {
              int codigo = 0;
              ResultSet rs = OperacoesBase.Consultar("select codaluno from aluno where bi_cedula = '"+bi+"'"); 
                try {
                    while( rs.next() )
                     {
                         codigo = rs.getInt("codaluno");
                     }
                } catch (SQLException ex) {
                 Logger.getLogger(validaEfectuarPagamento.class.getName()).log(Level.SEVERE, null, ex);
                     }
        
        return codigo != 0;
        
    }

    public static boolean EstaoVaziosActualizar(String foto_aluno, String nome_aluno, String bi, LocalDate datanas, String sexo, String tipo, String status) {
    
             return "".equals(foto_aluno) || "".equals(bi) || "".equals(sexo) ||  datanas == null || tipo == null || status == null;

    }
}
