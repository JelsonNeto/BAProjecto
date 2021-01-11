/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Validacoes;

import java.time.LocalDate;

/**
 *
 * @author Familia Neto
 */
public class validarPagamento {
    
    
    public static boolean EstaoVazios( String nome ,  String efeito , String ano ,String mes , LocalDate data , String funcionario , String valor_pagar , String  valor_pago )
    {
        
        return  nome == null || efeito == null || mes == null || data ==null || data == null || funcionario == null || valor_pagar == null || valor_pago ==null;
        
        
    }
    
}
