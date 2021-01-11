package Validacoes;

import Bd.OperacoesBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class validarSala {

	
	
	public static boolean VerificarVazio(TextArea tx_area , TextField txt2   )
	{
		boolean verifica = false;
		if(  "".equals(txt2.getText()) || "".equals(tx_area.getText()))
			verifica = true;
		
		return verifica;
		
	}
	
	
	
	public static void LabelCampoVazio( Label label )
	{
	   label.setText("Este campo esta vazio");
	   label.setVisible(true);
	}
	
	
        public static boolean VerificaExistencia( String sala )
        {
            Integer numsala = Integer.parseInt(sala);
            ResultSet rs = OperacoesBase.Consultar("select codsala from sala where codsala = '"+numsala+"'");
            int valor = 0;
            
            try {
                while( rs.next() )
                {
                    valor = rs.getInt("codsala");
                }
            } catch (SQLException ex) {
                Logger.getLogger(validarSala.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return valor != 0;
        }
	
}
