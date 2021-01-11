/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validacoes;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

/**
 *
 * @author Familia Neto
 */
public class ValidaFuncionario {
    

    public static boolean CamposVazios(JFXTextField txt_nome, JFXTextField txt_bi_cedula, JFXTextField txt_anoadmissao, JFXComboBox<String> cb_funcao, JFXComboBox<String> cb_status, JFXDatePicker data_nasc) 
    {
        return txt_nome.getText().equals("") || txt_anoadmissao.getText().equals("")|| txt_bi_cedula.getText().equals("")|| cb_funcao.getSelectionModel().getSelectedItem() == null || cb_status.getSelectionModel().getSelectedItem() == null || data_nasc.getValue()== null;
    }
}
