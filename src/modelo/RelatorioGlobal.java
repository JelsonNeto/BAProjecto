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
public class RelatorioGlobal {
    
    private String classe;
    private String curso;
    private String periodo;
    private int precoPropina;
    private int matriculados;
    private int confirmados;
    private int quantAlunosPagaram;
    private int quantAlunosNaoPagaram;
    private int valorpago;
    private int valorNaoPago;
    private int totalAlunos;
    private int total;
    
    public String getClasse() {
        return classe;
    }

    public String getCurso() {
        return curso;
    }

    public int getValorNaoPago() {
        return valorNaoPago;
    }

    public String getPeriodo() {
        return periodo;
    }

    public int getTotalAlunos() {
        return totalAlunos;
    }

    public int getTotal() {
        return total;
    }

    
     
    
    public int getPrecoPropina() {
        return precoPropina;
    }

    public int getValorpago() {
        return valorpago;
    }

    
    
    public int getMatriculados() {
        return matriculados;
    }

    public int getQuantAlunosNaoPagaram() {
        return quantAlunosNaoPagaram;
    }

    
    
    public int getConfirmados() {
        return confirmados;
    }

    public int getQuantAlunosPagaram() {
        return quantAlunosPagaram;
    }

    public void setQuantAlunosPagaram(int quantAlunosPagaram) {
        this.quantAlunosPagaram = quantAlunosPagaram;
    }

    public void setValorpago(int valorpago) {
        this.valorpago = valorpago;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    
    public void setQuantAlunosNaoPagaram(int quantAlunosNaoPagaram) {
        this.quantAlunosNaoPagaram = quantAlunosNaoPagaram;
    }

    public void setValorNaoPago(int valorNaoPago) {
        this.valorNaoPago = valorNaoPago;
    }

    public void setTotalAlunos(int totalAlunos) {
        this.totalAlunos = totalAlunos;
    }

    
    
    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setMatriculados(int matriculados) {
        this.matriculados = matriculados;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    
    
    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setPrecoPropina(int precoPropina) {
        this.precoPropina = precoPropina;
    }

    public void setConfirmados(int confirmados) {
        this.confirmados = confirmados;
    }

  
    
    
    
}
