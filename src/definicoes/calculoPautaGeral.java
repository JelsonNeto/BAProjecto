/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package definicoes;

import modelo.Nota;

/**
 *
 * @author Familia Neto
 */
public class calculoPautaGeral {
    
    
     public static String RetornaCap( String disc , String ano  , int codaluno )//Cap
    {
        double m1 = Double.parseDouble(Nota.AvaliacaoPorDisciplina(disc, ano, "Iº", codaluno));
        double m2 = Double.parseDouble(Nota.AvaliacaoPorDisciplina(disc, ano, "IIº", codaluno));
        double m3 = Double.parseDouble(Nota.AvaliacaoPorDisciplina(disc, ano, "IIIº", codaluno)); 
        double cp1 = Double.parseDouble(Nota.NotaProvaProfessorPorDisciplina(disc, ano, "Iº", codaluno));
        double cp2 = Double.parseDouble(Nota.NotaProvaProfessorPorDisciplina(disc, ano, "IIº", codaluno));
        double cp3 = Double.parseDouble(Nota.NotaProvaProfessorPorDisciplina(disc, ano, "IIIº", codaluno)); 
        
        double ct1= (m1+cp1)/2;
        double ct2 = (m2+cp2)/2;
        double ct3 = (m3+cp3)/2;
        
       
        return String.valueOf(ct1+ct2+ct3);
    } 
    
        
    public static String RetornaGeralCe( double cap, String disc , String ano  , int codaluno )
    {
        
        double ce = Double.parseDouble(Nota.NotaProvaEscolaPorDisciplina(disc, ano, "IIIº", codaluno)); 
       
        return String.valueOf(ce);
    }
    
    public static String RetornaCfGeral( String cap , String cp )
    {
        double valor1 = Double.parseDouble(cap);
        double valor2 = Double.parseDouble(cp);
        
        return String.valueOf((valor1+valor2)/2);
    }

}
