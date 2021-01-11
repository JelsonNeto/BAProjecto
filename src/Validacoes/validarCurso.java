/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Validacoes;

import Bd.OperacoesBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

/**
 *
 * @author Familia Neto
 */
public class validarCurso {

    
    
    public static boolean EstaoVazios( TextField t1 ,String valor  )
    {
        
        return "".equals(t1.getText()) || valor == null;
        
    }
    
    public static boolean VerificaExistencia( String nomecurso )
    {
        
        String sql = "select codcurso from curso where nome_curso = '"+nomecurso+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        
        int codcurso = 0;
        try {
            while(  rs.next()  )
            {
                codcurso = rs.getInt("codcurso");
            }
        } catch (SQLException ex) {
            Logger.getLogger(validarTurma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codcurso != 0;
        
        
    }
    
    public static ObservableList<String> retornaCurso()
    {
        String sql = "select *from curso";
        ResultSet rs = OperacoesBase.Consultar(sql);
       ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while(  rs.next()  )
            {
               lista.add(rs.getString("nome_curso"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(validarTurma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
}
