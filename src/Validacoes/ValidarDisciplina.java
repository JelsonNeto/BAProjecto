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
import javafx.scene.control.TextArea;

/**
 *
 * @author Familia Neto
 */
public class ValidarDisciplina {

    public static boolean ExistemCamposVazio(TextArea t1, ComboBox<String> c1, ComboBox<String> c2) 
    {
       return "".equals(t1.getText())|| null == c1.getSelectionModel().getSelectedItem() || null == c2.getSelectionModel().getSelectedItem();

    }

    public static boolean VerificaExistencia(String nome, int codcurso, int codclasse) 
    {
        
         String sql = "select coddisciplina from disciplina where nome_disciplina = '"+nome+"' and codclasse = '"+codclasse+"' and codcurso = '"+codcurso+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        int valor = 0;
        try {
            while(  rs.next()  )
            {
                valor = rs.getInt("coddisciplina");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ValidarDisciplina.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valor != 0;

    }
    
}
