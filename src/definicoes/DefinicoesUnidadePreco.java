/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package definicoes;

/**
 *
 * @author Familia Neto
 */
public class DefinicoesUnidadePreco {

    public static int ChangeFromStringToInt( String valor )
    {
        if( "".equals(valor) )
            return 0;
        
        if( valor.contains(".") )
        {
            String np = valor.replace(".", "");
            String np2 = np.replace("Akz", "");
            String  np3 = np2.replace(",00", "");
            return Integer.parseInt(np3.trim());
        }
        else
            if( valor.contains("Akz") && valor.contains(",00"))
            {
                String np2 = valor.replace("Akz", "");
                String  np3 = np2.replace(",00", "");
                String np4 = np3.trim();
                return Integer.parseInt(np4);
            }
        else
            return Integer.parseInt(valor);
        
    }
    
    public static String ReplaceUnidade( String valor )
    {
        String np = valor.replace("Akz", "");
        String np1 = np.replace(",00", "");
        return np1.trim();
    }
    
    public static int ReplaceUnidade_SemPonto( String valor )
    {
        String np = valor.replace("Akz", "");
        String np1 = np.replace(",00", "");
        String np2 =  np1.trim();
        
        return Integer.parseInt(np2);
    }
    
    public static String RetornaValorEmUnidadeCorrecta( String valor )
    {
        StringBuilder sb = new StringBuilder();
        if( valor.length() == 4 )
        {
            sb.append(valor.charAt(0)).append(".");
            for( int i = 1 ; i < valor.length() ; i++ )
               sb.append(valor.charAt(i));
        }
        else
        {
            if( valor.length() == 5 )
            {
                 sb.append(valor.charAt(0)).append(valor.charAt(1)).append(".");
                 for( int i = 2 ; i < valor.length() ; i++ )
                     sb.append(valor.charAt(i));
            }
            else
            {
                    if( valor.length() == 6 )
                {
                     sb.append(valor.charAt(0)).append(valor.charAt(1)).append(valor.charAt(2)).append(".");
                     for( int i = 3 ; i < valor.length() ; i++ )
                         sb.append(valor.charAt(i));
                }
                    else
                        if( valor.length() == 7 )
                        {
                             sb.append(valor.charAt(0)).append(".");
                             for( int i = 1 ; i < valor.length() ; i++ )
                             {
                                 if( i == 3 )
                                     sb.append(valor.charAt(i)).append(".");
                                 else
                                    sb.append(valor.charAt(i));
                             }
                        }
                    else
                            if( valor.length() == 8 )
                            {
                                 sb.append(valor.charAt(0)).append(valor.charAt(1)).append(".");
                                 for( int i = 2 ; i < valor.length() ; i++ )
                                 {
                                     if( i == 4 )
                                         sb.append(valor.charAt(i)).append(".");
                                     else
                                         sb.append(valor.charAt(i));
                                 }  
                            }
                    else
                            if( valor.length() == 9 )
                            {
                                 sb.append(valor.charAt(0)).append(valor.charAt(1)).append(valor.charAt(2)).append(".");
                                    for( int i = 3 ; i < valor.length() ; i++ )
                                     {
                                        if( i == 5 )
                                          sb.append(valor.charAt(i)).append(".");
                                        else
                                          sb.append(valor.charAt(i));
                                       }
                            }
            }  
        }
        
        return (sb.toString().equalsIgnoreCase(""))?valor+",00 Akz":sb.toString()+",00 Akz";
    }
    
    public static String RetornaValorEmUnidadeCorrecta_Double( String valor )
    {
        StringBuilder sb = new StringBuilder();
        if( valor.length() == 4 )
        {
            sb.append(valor.charAt(0)).append(".");
            for( int i = 1 ; i < valor.length() ; i++ )
               sb.append(valor.charAt(i));
        }
        else
        {
            if( valor.length() == 5 )
            {
                 sb.append(valor.charAt(0)).append(valor.charAt(1)).append(".");
                 for( int i = 2 ; i < valor.length() ; i++ )
                     sb.append(valor.charAt(i));
            }
            else
            {
                    if( valor.length() == 6 )
                {
                     sb.append(valor.charAt(0)).append(valor.charAt(1)).append(valor.charAt(2)).append(".");
                     for( int i = 3 ; i < valor.length() ; i++ )
                         sb.append(valor.charAt(i));
                }
                    else
                        if( valor.length() == 7 )
                        {
                             sb.append(valor.charAt(0)).append(".");
                             for( int i = 1 ; i < valor.length() ; i++ )
                             {
                                 if( i == 3 )
                                     sb.append(valor.charAt(i)).append(".");
                                 else
                                    sb.append(valor.charAt(i));
                             }
                        }
                    else
                            if( valor.length() == 8 )
                            {
                                 sb.append(valor.charAt(0)).append(valor.charAt(1)).append(".");
                                 for( int i = 2 ; i < valor.length() ; i++ )
                                 {
                                     if( i == 4 )
                                         sb.append(valor.charAt(i)).append(".");
                                     else
                                         sb.append(valor.charAt(i));
                                 }  
                            }
                    else
                            if( valor.length() == 9 )
                            {
                                 sb.append(valor.charAt(0)).append(valor.charAt(1)).append(valor.charAt(2)).append(".");
                                    for( int i = 3 ; i < valor.length() ; i++ )
                                     {
                                        if( i == 5 )
                                          sb.append(valor.charAt(i)).append(".");
                                        else
                                          sb.append(valor.charAt(i));
                                       }
                            }
            }  
        }
        
        return valor;
    }
    
    
    public static int StringToInteger( String valor )
    {
        int retorno= 0;
        if( valor.contains(".") )
        {
            String valor2 = valor.replace(".", "");
            String valor3 = valor2.replace(",00 Akz", "");
            String re = valor3.trim();
            retorno = Integer.parseInt(re);
        }
        else
        {
             String valor1  = valor.replace(",00 Akz", "");
             retorno = Integer.parseInt(valor1.trim());
        }
        return retorno;
    }
    
  
    
    public static boolean ENumero( String valor )
    {
        return Character.isDigit(valor.charAt(0));
    }
}
