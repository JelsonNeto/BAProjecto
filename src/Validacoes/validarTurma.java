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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author Familia Neto
 */
public class validarTurma {

    public static boolean ExistemCamposVazio( TextField t1 , ComboBox c1 , ComboBox c2 , ComboBox c3, ComboBox c4 , ComboBox c5  )
    {
        return "".equals(t1.getText())|| null == c1.getSelectionModel().getSelectedItem() || null == c2.getSelectionModel().getSelectedItem() || c3.getSelectionModel().getSelectedItem() ==  null || c4.getSelectionModel().getSelectedItem() == null || c5.getSelectionModel().getSelectedItem() == null;
    }
    
    public static boolean VerificaExistencia( String nomeTurma, int codclasse )
    {
        
        String sql = "select codturma from turma where nome_turma = '"+nomeTurma+"' and codclasse = '"+codclasse+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        int valor = 0;
        try {
            while(  rs.next()  )
            {
                valor = rs.getInt("codturma");
            }
        } catch (SQLException ex) {
            Logger.getLogger(validarTurma.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valor != 0;
    }
    
    
    
    
}
