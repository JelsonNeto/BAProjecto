/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Validacoes;

import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Familia Neto
 */
public class validarUsuario {

    
    
    
    public static boolean EstaoVazios( JFXComboBox t1 , TextField t2 , PasswordField p1, ComboBox b1 )
    {
        
        return t1.getSelectionModel().getSelectedItem() == null || "".equals(t2.getText()) || "".equals(p1.getText()) || b1.getSelectionModel().getSelectedItem() == null;
    }
    
    public static boolean EstaoVazios( TextField t1 , TextField t2 , PasswordField p1,  DatePicker data )
    {
        
        return "".equals(t1.getText()) || "".equals(t2.getText()) || "".equals(p1.getText())  || EstaVaziaData(data);
    }
    
    
    private static boolean EstaVaziaData( DatePicker data )
    {
        
       return data.getValue() == null;
        
    }

    public static boolean EstaoVaziosRedifinirSenha(String nome, String tipo, String senhaNova, String csenha, String senhaAdmin, String username, String senhaActual) {

        return nome == null || "".equalsIgnoreCase(senhaNova) || tipo == null || "".equalsIgnoreCase(csenha) || "".equalsIgnoreCase(senhaAdmin) && "".equalsIgnoreCase(username) || "".equalsIgnoreCase(senhaActual);
    }
    
    public static boolean Vazio_Config2( TextField nif , TextField iban , DatePicker data , TextField nome , TextField bi , ComboBox<String> funcao , TextField usuario , TextField senha)
    {
        return "".equalsIgnoreCase(nif.getText()) || "".equalsIgnoreCase(iban.getText()) || "".equalsIgnoreCase(nome.getText()) || "".equalsIgnoreCase(usuario.getText()) || "".equalsIgnoreCase(senha.getText()) || funcao.getSelectionModel().getSelectedItem() == null||data.getValue() == null;
    }
    
    
    
}
