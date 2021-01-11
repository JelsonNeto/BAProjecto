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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 *
 * @author Familia Neto
 */
public class ValidaEncarregado {

    public static boolean ExistemCamposVazios(TextField txt_nome , TextField txt_nome1, DatePicker datanasc, TextField ocupacao, TextField endereco, TextField contactos, ComboBox<String> cb_classe, ComboBox<String> cb_curso, ComboBox<String> cb_turma, ListView<String> lista_selecionados, ListView<String> lista_selecionar)
    {
        return  "".equalsIgnoreCase(txt_nome.getText()) || "".equalsIgnoreCase(txt_nome1.getText()) || datanasc == null || "".equalsIgnoreCase(ocupacao.getText()) || "".equalsIgnoreCase(endereco.getText()) || cb_classe == null || cb_curso == null || cb_turma == null || lista_selecionados.getItems().size() == 0 || lista_selecionar.getItems().size() == 0;
    }

    public static boolean VerificaExistencia(int codigo) 
    {
         
        String sql = "select nomepai from encarregado where codencarregado = '"+codigo+"' ";
        ResultSet rs = OperacoesBase.Consultar(sql);
        String valor = "";
        try {
            while(  rs.next()  )
            {
                valor = rs.getString("nomepai");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ValidaEncarregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return !"".equalsIgnoreCase(valor);
    }
    
}
