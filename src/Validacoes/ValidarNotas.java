/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validacoes;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author Familia Neto
 */
public class ValidarNotas {

    public static boolean EstaoVazios(TextField txt_cedula, TextField txt_dataNascimento, ComboBox<String> cb_tipo, TextField txt_encarregado, TextArea txtarea_notas, ComboBox<String> cb_classe, ComboBox<String> cb_curso, ComboBox<String> cb_nome, ComboBox<String> cb_trimestre, ComboBox<String> cb_turma, ListView<String> lista_disciplinas, TextArea txtarea_notas0) 
    {
        return "".equalsIgnoreCase(txt_cedula.getText()) || "".equalsIgnoreCase(txt_encarregado.getText()) || cb_tipo.getSelectionModel().getSelectedItem() == null || "".equalsIgnoreCase(txt_dataNascimento.getText()) || cb_classe.getSelectionModel().getSelectedItem() == null || cb_curso.getSelectionModel().getSelectedItem() == null || cb_nome.getSelectionModel().getSelectedItem() == null || cb_trimestre.getSelectionModel().getSelectedItem() == null || cb_turma.getSelectionModel().getSelectedItem() == null || lista_disciplinas.getItems().size() <= 0 || txtarea_notas.getText().equalsIgnoreCase("");
    }
    
    public static boolean ENumero( String valor )
    {
        return valor.matches("[0-9]+");
    }
    
    public static boolean verifica_notas_por_curso_nivel( String valor , String curso )
    {
        double nota = Double.parseDouble(valor);
        if( "Primária".equals(curso))
        {   
            return nota >=0.0 && nota<= 10;
        }
        else
            return nota >=0.0 && nota <=20;
    }
    
}
