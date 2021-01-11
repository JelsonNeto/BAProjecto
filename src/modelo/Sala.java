/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import Bd.OperacoesBase;
import java.sql.ResultSet;

/**
 *
 * @author Familia Neto
 */
public class Sala {

    
    private Integer codigo;
    private String desc;
    private Integer capacidade;

    public Sala() {
       
    }

    public int getCapacidade() {
        return capacidade;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDesc() {
        return desc;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public void setCodigo( int codigo ) {
        this.codigo = codigo;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
   
    
    //metodos operacionais
    public boolean AdicionarSala()
    {
        String sql = "Insert into sala values( '"+this.getCodigo()+"' , '"+this.getDesc()+"','"+this.getCapacidade()+"' ) ";
        return OperacoesBase.Inserir(sql);
    }
    //consultar os dados
    public ResultSet Consultar()
    {
        String sql  = "select  * from sala order by codsala";
        return OperacoesBase.Consultar(sql);
        
    }
    
    public ResultSet Consultar( String sql )
    {
        return OperacoesBase.Consultar(sql);
        
    }
    
    
    
}
