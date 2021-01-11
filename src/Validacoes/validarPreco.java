/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Validacoes;

/**
 *
 * @author Familia Neto
 */
public class validarPreco {

    
    public static boolean EstaoVazios(String selectedItem, String selectedItem0, String selectedItem1, String text) {
    
        return selectedItem == null || selectedItem0 == null || selectedItem1 == null || text.equalsIgnoreCase("");
      
    }

    
}
