/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import Bd.OperacoesBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

/**
 *
 * @author Familia Neto
 */
public class EfectuarPagamento {
    
    private int codigo;
    private String efeito;
    private String valor_pago;
    private String valor_apagar;
    private int codaluno;
    private int codfuncionario;
    private LocalDate data;
    private ObservableList<String> mes;
    private String ano_actual_cobranca;
    
    public EfectuarPagamento() {
    }

    public int getCodaluno() {
        return codaluno;
    }

    public int getCodfuncionario() {
        return codfuncionario;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getAno_actual_cobranca() {
        return ano_actual_cobranca;
    }

    
    public String getEfeito() {
        return efeito;
    }

    public String getValor_apagar() {
        return valor_apagar;
    }

    public String getValor_pago() {
        return valor_pago;
    }

    public LocalDate getData() {
        return data;
    }

    public ObservableList<String> getMes() {
        return mes;
    }


    public void setMes(ObservableList<String> mes) {
        this.mes = mes;
    }

    public void setAno_actual_cobranca(String ano_actual_cobranca) {
        this.ano_actual_cobranca = ano_actual_cobranca;
    }
   
    public void setCodaluno(int codaluno) {
        this.codaluno = codaluno;
    }

    public void setCodfuncionario(int codfuncionario) {
        this.codfuncionario = codfuncionario;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setEfeito(String efeito) {
        this.efeito = efeito;
    }

    public void setValor_apagar(String valor_apagar) {
        this.valor_apagar = valor_apagar;
    }

    public void setValor_pago(String valor_pago) {
        this.valor_pago = valor_pago;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
    
    
    
    
    public void show()
    {
        System.out.println(getCodigo());
        System.out.println(getCodaluno());
        System.out.println(getCodfuncionario());
        System.out.println(getEfeito());
        System.out.println(getValor_apagar());
        System.out.println(getValor_pago());
        System.out.println(getData());
        System.out.println(getMes());
         System.out.println(getAno_actual_cobranca());
    }
    
    
    public int ObtemUltimoCodigo()
    {
        int valor = 0;
        ResultSet rs = OperacoesBase.Consultar("select codpagamento from pagamento");
        try {
            while( rs.next())
            {
                valor = rs.getInt("codpagamento");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EfectuarPagamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ++valor;
    }
    
    
    
}
