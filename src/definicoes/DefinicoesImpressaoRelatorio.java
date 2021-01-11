/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package definicoes;

import Bd.conexao;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Familia Neto
 */
public class DefinicoesImpressaoRelatorio {
    
    
    
    public static boolean ImprimirRelatorio(  HashMap parametros , String path  )
    {
         try
        {
            conexao.Conectar();
            JasperReport report = JasperCompileManager.compileReport(path);
            JasperPrint print = JasperFillManager.fillReport(report, parametros, conexao.ObterConection());
            JasperViewer jv = new JasperViewer(print , false);
            jv.setVisible(true);
            jv.toFront();
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
         return false;
    }
    
    
}
