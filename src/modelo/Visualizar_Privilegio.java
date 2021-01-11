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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Familia Neto
 */
public class Visualizar_Privilegio {
    
    private String nome_func;
    private String leitura;
    private String isercao;
    private String eliminacao;
    private String actualizacao;
    private String impressao;
    private String usuario_responsavel;
    private String funcao;

    public String getNome_func() {
        return nome_func;
    }

    public void setNome_func(String nome_func) {
        this.nome_func = nome_func;
    }

    public String getLeitura() {
        return leitura;
    }

    public void setLeitura(String leitura) {
        this.leitura = leitura;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getIsercao() {
        return isercao;
    }

    public void setIsercao(String isercao) {
        this.isercao = isercao;
    }

    public String getEliminacao() {
        return eliminacao;
    }

    public void setEliminacao(String eliminacao) {
        this.eliminacao = eliminacao;
    }

    public String getActualizacao() {
        return actualizacao;
    }

    public void setActualizacao(String actualizacao) {
        this.actualizacao = actualizacao;
    }

    public String getImpressao() {
        return impressao;
    }

    public void setImpressao(String impressao) {
        this.impressao = impressao;
    }

    public String getUsuario_responsavel() {
        return usuario_responsavel;
    }

    public void setUsuario_responsavel(String usuario_responsavel) {
        this.usuario_responsavel = usuario_responsavel;
    }
    
    
    public static ObservableList<Visualizar_Privilegio> PreencheTabela()
    {
        ResultSet rs = OperacoesBase.Consultar("select * from privilegios_usuario");
        ObservableList<Visualizar_Privilegio> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                Visualizar_Privilegio vp = new Visualizar_Privilegio();
                vp.setActualizacao(Converte(rs.getInt("actualizacao")));
                vp.setIsercao(Converte(rs.getInt("insercao")));
                vp.setEliminacao(Converte(rs.getInt("eliminacao")));
                vp.setImpressao(Converte(rs.getInt("impressao")));
                vp.setLeitura(Converte(rs.getInt("leitura")));
                vp.setNome_func(Funcionario.cod_to_Name(Usuario.Obter_CodFuncionario(rs.getInt("coduser"))));
                vp.setFuncao(Funcionario.cod_to_Funcao(Usuario.Obter_CodFuncionario(rs.getInt("coduser"))));
                
                lista.add(vp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Visualizar_Privilegio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    
    
    private static String Converte( int valor )
    {
        if( valor == 0 )
            return "Não";
        else
            if( valor == 1 )
                return "Parcial";
        return "Total";
    }
}
