/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author Familia Neto
 */
public class GlobalMensalFinanceiro {
    
    private String classe;
    private int propina;
    private String curso;
    private int confirmacao;
    private int matricula;
    private String precoPropina;
    private String valorPago;
    private String valorNaoPago;
    private int quantAlunospago;
    private int quantAlunosNaoPago;
    private String total;

    public String getClasse() {
        return classe;
    }

    public int getConfirmacao() {
        return confirmacao;
    }

    public String getCurso() {
        return curso;
    }

    public int getMatricula() {
        return matricula;
    }

    public String getPrecoPropina() {
        return precoPropina;
    }

    public int getPropina() {
        return propina;
    }

    public int getQuantAlunosNaoPago() {
        return quantAlunosNaoPago;
    }

    public int getQuantAlunospago() {
        return quantAlunospago;
    }

    public String getTotal() {
        return total;
    }

    public String getValorNaoPago() {
        return valorNaoPago;
    }

    public String getValorPago() {
        return valorPago;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setConfirmacao(int confirmacao) {
        this.confirmacao = confirmacao;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public void setPrecoPropina(String precoPropina) {
        this.precoPropina = precoPropina;
    }

    public void setPropina(int propina) {
        this.propina = propina;
    }

    public void setQuantAlunosNaoPago(int quantAlunosNaoPago) {
        this.quantAlunosNaoPago = quantAlunosNaoPago;
    }

    public void setQuantAlunospago(int quantAlunospago) {
        this.quantAlunospago = quantAlunospago;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setValorNaoPago(String valorNaoPago) {
        this.valorNaoPago = valorNaoPago;
    }

    public void setValorPago(String valorPago) {
        this.valorPago = valorPago;
    }
    
    
            
}
