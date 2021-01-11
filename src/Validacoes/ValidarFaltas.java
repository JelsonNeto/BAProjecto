/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validacoes;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 *
 * @author Familia Neto
 */
public class ValidarFaltas {

    public static boolean EstaoVazios(ComboBox<String> cb_turma, DatePicker data, ComboBox<String> cb_disciplina) 
    {
        return cb_turma.getSelectionModel().getSelectedItem() == null || data.getValue() == null || cb_disciplina.getSelectionModel().getSelectedItem() == null;
    }
    
     public static boolean EstaoVazios(TextField txt_hora, DatePicker data, ComboBox<String> cb_disciplina) 
    {
        return "".equalsIgnoreCase(txt_hora.getText()) || data.getValue() == null || cb_disciplina.getSelectionModel().getSelectedItem() == null;
    }
}
