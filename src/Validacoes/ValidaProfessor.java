/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validacoes;

import Bd.OperacoesBase;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 *
 * @author Familia Neto
 */
public class ValidaProfessor {
    
    
    public static boolean EstaoVazios(JFXComboBox nome, JFXTextField  cedula, JFXTextField  data, ObservableList<String> lista_disciplinas , ComboBox<String> status , ComboBox<String> tipo) 
    {
        return nome.getSelectionModel().getSelectedItem() == null || "".equalsIgnoreCase(cedula.getText()) || data.getText() == null || lista_disciplinas.size() == 0 || status.getSelectionModel().getSelectedItem() == null || tipo.getSelectionModel().getSelectedItem() == null ;
    }
    
     public static boolean EstaoVazios(JFXComboBox nome, JFXTextField  cedula, JFXTextField  data, ObservableList<String> lista_disciplinas) 
    {
         return nome.getSelectionModel().getSelectedItem() == null || "".equalsIgnoreCase(cedula.getText()) || data.getText() == null  || lista_disciplinas.size() == 0;
    }
     
    public static boolean EstaoVazios(JFXComboBox nome, JFXTextField  cedula, JFXTextField  data, JFXListView<String> lista) 
    {
         return nome.getSelectionModel().getSelectedItem() == null || "".equalsIgnoreCase(cedula.getText()) || data.getText() == null  || lista.getItems().size() == 0;
    }

    public static boolean JaExiste(JFXComboBox nome, JFXTextField cedula) 
    {
        String sql = "select codprofessor from professor inner join funcionario using(codfuncionario) where nome_professor = '"+nome.getSelectionModel().getSelectedItem()+"' and bi_cedula = '"+cedula.getText()+"' ";
        ResultSet rs = OperacoesBase.Consultar(sql);
        int cod = 0;
        try {
            while( rs.next() )
            {
                cod = rs.getInt("codprofessor");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ValidaProfessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cod != 0;
        
    }
    
    
    
}
