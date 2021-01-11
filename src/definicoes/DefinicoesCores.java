/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package definicoes;

import com.jfoenix.controls.JFXRadioButton;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;

/**
 *
 * @author Familia Neto
 */
public class DefinicoesCores {
    
    public static void SetCorLabel( String cor , Label label )
    {
         String estilo = "-Fx-Text-Fill:"+cor;
         label.setStyle(estilo);
    }
    
    public static void Underline( Label label , boolean valor )
    {
        label.setUnderline(valor);
    }
    
    public static void MudarCor_Selecao_RadioButton( JFXRadioButton radio1 ,JFXRadioButton radio2 )
    {
        radio1.setSelectedColor(Color.rgb(0, 102, 153));
        radio2.setSelectedColor(Color.rgb(0, 102, 153));
    }
    
  
}
