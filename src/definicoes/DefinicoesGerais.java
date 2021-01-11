/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package definicoes;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import static java.lang.Math.E;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Familia Neto
 */
public class DefinicoesGerais {
    
    public static String DevolveSexo( JFXRadioButton radio_masculino )
    {
        return ( radio_masculino.isSelected() )?"Masculino":"Femenino";
    }
    
    public static void Inicializar_Funcoes(JFXComboBox<String> cb )
    {
         String valores[] ={ "Professor","Limpeza", "Segurança", "Director geral","Director Pedagógico","Secretário(a)","Contabilista"};
        cb.setItems(FXCollections.observableArrayList(Arrays.asList(valores)));
    }
    
    public static void Inicializar_Funcoes_Privilegios(JFXComboBox<String> cb )
    {
         String valores[] ={"Admin"};
        cb.setItems(FXCollections.observableArrayList(Arrays.asList(valores)));
    }
    
    public static boolean TemDadosTabela( TableView tabela )
    {
        return tabela.getItems().size() > 0;
    }
            
    
  
  
}
