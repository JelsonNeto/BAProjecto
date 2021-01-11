/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package definicoes;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Familia Neto
 */
public class Backup {
    private static int valor= 2;
    
    public static boolean FazerBackup()
    {
        
        ArrayList<String> comandos = new ArrayList<>();
        String dir = "C:\\PastaBackup";
        File file = new File(dir);
        
       
        File[] flist = file.listFiles();
        
        if( flist.length >= 0 )
        {
            
            comandos.add("C:\\Program Files\\PostgreSQL\\10\\bin\\pg_dump.exe");
            
            comandos.add("-h");
            comandos.add("localhost");
            comandos.add("-p");
            comandos.add("5432");
            comandos.add("-U");
            comandos.add("postgres");
            comandos.add("-F");
            comandos.add("c");
            comandos.add("-b");
            comandos.add("-v");
            comandos.add("-f");
            
            comandos.add("C:\\PastaBackup\\+"+(valor++)+" "+getTime()+".backup");
            comandos.add("EscolaGenix");
            ProcessBuilder pb = new ProcessBuilder(comandos);
            
            pb.environment().put("PGPASSWORD", "capatamiguel");
            
            try {
                Process process = pb.start();
                BufferedReader r = new BufferedReader( new InputStreamReader(process.getErrorStream()));
                String line = r.readLine();
                while( line != null )
                {
                    System.err.println(line);
                    line = r.readLine();
                    
                }
                r.close();
                
                process.waitFor();
                process.destroy();
                return true;
                
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Backup.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
      
        return false;
    }
    
    private static String getTime()
    {
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("ddMMyyyy HHmm");
        return f.format(d);
    }
}
