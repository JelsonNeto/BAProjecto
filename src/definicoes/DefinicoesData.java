/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package definicoes;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import modelo.MesAno;

/**
 *
 * @author Familia Neto
 */
public class DefinicoesData {
    
    
    public static String retornaPadrao( String mes)
    {
       if("Janeiro".equalsIgnoreCase(mes))
           return "%-01-%";
       else
           if("Fevereiro".equalsIgnoreCase(mes))
               return "%-02-%";
       else
               if("Março".equalsIgnoreCase(mes))
                   return "%-03-%";
       else
                   if("Abril".equalsIgnoreCase(mes))
                       return "%-04-%";
       else
                       if("Maio".equalsIgnoreCase(mes))
                           return "%-05-%";
       else
                           if("Junho".equalsIgnoreCase(mes))
                               return "%-06-%";
       else
                               if("Julho".equalsIgnoreCase(mes))
                                    return "%-07-%";
       else
                                   if("Agosto".equalsIgnoreCase(mes))
                                       return "%-08-%";
       else
                                       if("Setembro".equalsIgnoreCase(mes))
                                           return "%-09-%";
       else
                                           if("Outubro".equalsIgnoreCase(mes))
                                               return "%-10-%";
       else
                                               if("Novembro".equalsIgnoreCase(mes))
                                                   return "%-11-%";
       else
                                                   if("Dezembro".equalsIgnoreCase(mes))
                                                       return "%-12-%";
       
       return "";
       
    }
    /**
     * 
     * @param date
     * @return 
     * Converte a data vinda da base de dados de String para LocalDate
     */
     public static  LocalDate StringtoLocalDate( String date )
    {
        String dados[] = date.split("-");
        int ano = Integer.parseInt(dados[0]);
        int dia = Integer.parseInt(dados[2]);
        int mes = Integer.parseInt(dados[1]);
        System.out.println(date);
        return   LocalDate.of(ano, mes, dia);
        
    }
    
     public static LocalDate StringtoLocalDate2 ( String date )
     {
        String dados[] = date.split("/");
        int ano = Integer.parseInt(dados[2]);
        int dia = Integer.parseInt(dados[0]);
        int mes = Integer.parseInt(dados[1]);
        
        return   LocalDate.of(ano, mes, dia);
     
     }
     
    /**
     * 
     * @param data , Passa como parametro uma data
     * @return o ano
     */
    public static String RetornaAnoData( String data )
    {
        String dados[] = data.split("-");
        return dados[0];
    }
    /**
     * 
     * @param data
     * @return 
     */
    public static int RetornaDiaData( String data )
    {
        String dados[] = data.split("-");
        return Integer.parseInt(dados[2]);
    }
    /**
     * 
     * @param data ,Bd
     * @return 
     */
    public static int RetornaMes( String data )
    {
        
        String dados[] = data.split("-");
        return Integer.parseInt(dados[1]);
    }

   
     public static LocalDate RetornaDatadoSistema()
     {
         Date date = new Date();
         SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
         LocalDate d = DefinicoesData.StringtoLocalDate2(formato.format(date));
         
         return d;
         
     }
    
     public static boolean RegexHora( String valor )
     {
       return valor.matches("\\d\\d:\\d\\d:\\d\\d");
     }
     
     
     public static String retornaTrimestre()
     {
         int mes = RetornaMes(String.valueOf(RetornaDatadoSistema()));
         if( mes >= 2 && mes<=5 )
             return "Iº";
         else
             if( mes >=5 && mes <=7 )
                 return "IIº";
         else
                 return "IIIº";
         
     }
     
     public static String retornaTrimestre( String data )
     {
         int mes = RetornaMes(String.valueOf(data));
         if( mes >= 2 && mes<=5 )
             return "Iº";
         else
             if( mes >=5 && mes <=7 )
                 return "IIº";
         else
                 return "IIIº";
         
     }
     
     public static String RetornaHoraSistema()
     {
         Date d = new Date();
         SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        
         return format.format(d);
     }

    public static int RetornaIdade( LocalDate datanas)
    {
        int ano_data = Integer.parseInt(DefinicoesData.RetornaAnoData(String.valueOf(datanas)));
        int ano_actual = Integer.parseInt(MesAno.Get_AnoActualCobranca());
        return (ano_actual - ano_data);
    }
    
    public static String Retorna_Hora_do_Sistema()
    {
        Date d = new Date();
        String v[] = d.toString().split(" ");
        return v[3];
    }
   
}
