/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Bd.OperacoesBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Familia Neto
 */
public class Horario {
    
    
    private String tempo;
    private String anolectivo;
    private String dia1; //
    private String dia2;//
    private String dia3;// Os dias referenciam os codigos das disciplinas neste dia
    private String dia4;//
    private String dia5;//
    private int codturma;

    public Horario() {
    }

    public String getAnolectivo() {
        return anolectivo;
    }

    public int getCodturma() {
        return codturma;
    }

    public String getDia1() {
        return dia1;
    }

    public String getDia2() {
        return dia2;
    }

    public String getDia3() {
        return dia3;
    }

    public String getDia4() {
        return dia4;
    }

    public String getDia5() {
        return dia5;
    }

   
    public String getTempo() {
        return tempo;
    }

    public void setAnolectivo(String anolectivo) {
        this.anolectivo = anolectivo;
    }

    public void setCodturma(int codturma) {
        this.codturma = codturma;
    }

    public void setDia5(String dia5) {
        this.dia5 = dia5;
    }

    public void setDia4(String dia4) {
        this.dia4 = dia4;
    }

    public void setDia3(String dia3) {
        this.dia3 = dia3;
    }

    public void setDia2(String dia2) {
        this.dia2 = dia2;
    }

    public void setDia1(String dia1) {
        this.dia1 = dia1;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }
    
   public boolean adicionar()
   {
       String sql = "insert into horario values('"+this.getTempo()+"', '"+this.getDia1()+"','"+this.getDia2()+"','"+this.getDia3()+"','"+this.getDia4()+"','"+this.getDia5()+"','"+this.getCodturma()+"','"+this.getAnolectivo()+"')";
       return OperacoesBase.Inserir(sql);
   }
    
 /***********************METODOS OPERACIONAIS***********************************************************/
   
   
   public static boolean HorarioJaExistente( int codturma , String anolectivo )
   {
       String sql ="select * from horario where codturma = '"+codturma+"' and anolectivo = '"+anolectivo+"'";
       ResultSet rs = OperacoesBase.Consultar(sql);
       int valor = 0;
        try {
            while( rs.next() )
            {
                ++valor;
            }} catch (SQLException ex) {
            Logger.getLogger(Horario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valor != 0;
   }
}
