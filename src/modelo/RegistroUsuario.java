/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Bd.OperacoesBase;
import definicoes.DefinicoesData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Familia Neto
 */
public class RegistroUsuario {
    
    private int codigo;
    private int coduser;
    private String acao;
    private LocalDate data;
    private int cod_a;
    

    public int getCodigo() {
        return codigo;
    }

    public int getCod_a() {
        return cod_a;
    }

    public void setCod_a(int cod_a) {
        this.cod_a = cod_a;
    }

    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCoduser() {
        return coduser;
    }

    public void setCoduser(int coduser) {
        this.coduser = coduser;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

     
    
    public boolean adicionar()
    {
        String sql ="insert into registroUser values('"+this.getCodigo()+"', '"+this.getCoduser()+"', '"+this.getData()+"', '"+this.getAcao()+"','"+this.getCod_a()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
    
            
    
/**********************METODOS AUXILIARES*******************************/
    /**** @return *******************************/
     public int RetornaUltimoCodigo()
    {
        int valor = 0;
        ResultSet rs = OperacoesBase.Consultar("select * from registroUser order by codigo");
        try {
            while( rs.next() )
            {
                valor = rs.getInt("codigo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistroUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++valor;
    }
     
    public static void AddRegistro( String acao)
    {
        RegistroUsuario r = new RegistroUsuario();
        r.setCodigo(r.RetornaUltimoCodigo());
        r.setCoduser(Usuario.NameToCode(Usuario.getUsuario_activo()));
        r.setAcao(acao);
        r.adicionar();
    }
}
