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
public class ValidarHorario {
    
    
    public static boolean EstaoVaziosTempos( String t1 ,String t2,String t3,String t4,String t5 , String t6 )
    {
        return t1.equalsIgnoreCase("") || t2.equalsIgnoreCase("") || t3.equalsIgnoreCase("") || t4.equalsIgnoreCase("") || t5.equalsIgnoreCase("") || t6.equalsIgnoreCase("");
    }
    
    public static boolean EstaoVaziosDisciplinasSegunda(  String d1,String d2,String d3,String d4,String d5,String d6  )
    {
        return d1 == null || d2 == null || d3 == null|| d4 == null || d5 == null||d6 == null;
    }
    
    public static boolean EstaoVaziosDisciplinasTerca(  String d1,String d2,String d3,String d4,String d5,String d6  )
    {
        return d1 == null || d2 == null || d3 == null|| d4 == null || d5 == null||d6 == null;
    }
    
    public static boolean EstaoVaziosDisciplinasQuarta(  String d1,String d2,String d3,String d4,String d5,String d6  )
    {
        return d1 == null || d2 == null || d3 == null|| d4 == null || d5 == null||d6 == null;
    }
    
    public static boolean EstaoVaziosDisciplinasQuinta(  String d1,String d2,String d3,String d4,String d5,String d6  )
    {
        return d1 == null || d2 == null || d3 == null|| d4 == null || d5 == null||d6 == null;
    }
    
    public static boolean EstaoVaziosDisciplinasSexta(  String d1,String d2,String d3,String d4,String d5,String d6  )
    {
        return d1 == null || d2 == null || d3 == null|| d4 == null || d5 == null||d6 == null;
    }
}
