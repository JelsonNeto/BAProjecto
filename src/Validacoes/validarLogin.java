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
public class validarLogin {
    
    public static boolean EstaoVazios( String n, String s )
    {
        return "".equals(s) || "".equals(n);
        
    }
}
