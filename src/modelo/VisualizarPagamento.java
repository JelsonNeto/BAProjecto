/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.time.LocalDate;

/**
 *
 * @author Familia Neto
 */
public class VisualizarPagamento {
    
    private int codpagamento;
    private String nome_aluno;
    private String bi;
    private String nome_funcionario;
    private String mes;
    private LocalDate data;
    private String efeito;
    private String turma;
    private String curso;
    private String valor_pago;
    private String valor_apagar;
    private String ano;
    private String formapagamento;
    private int multa;
    private String codescola;
    
    public VisualizarPagamento() {
    }

    public String getBi() {
        return bi;
    }

    public String getCurso() {
        return curso;
    }

    public LocalDate getData() {
        return data;
    }

    public String getEfeito() {
        return efeito;
    }

    public String getCodescola() {
        return codescola;
    }

    public void setCodescola(String codescola) {
        this.codescola = codescola;
    }
    
    
    public String getFormapagamento() {
        return formapagamento;
    }

    public int getMulta() {
        return multa;
    }

    
    
    public String getMes() {
        return mes;
    }

    public int getCodpagamento() {
        return codpagamento;
    }

    
    public String getNome_aluno() {
        return nome_aluno;
    }

    public String getNome_funcionario() {
        return nome_funcionario;
    }

    public String getAno() {
        return ano;
    }

    
    
    public String getTurma() {
        return turma;
    }

    public String getValor_pago() {
        return valor_pago;
    }

    

    public String getValor_apagar() {
        return valor_apagar;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setMulta(int multa) {
        this.multa = multa;
    }

    
    
    
    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setEfeito(String efeito) {
        this.efeito = efeito;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void setFormapagamento(String formapagamento) {
        this.formapagamento = formapagamento;
    }

    
    
    public void setMes(String mes) {
        this.mes = mes;
    }

    public void setCodpagamento(int codpagamento) {
        this.codpagamento = codpagamento;
    }

    
    public void setNome_aluno(String nome_aluno) {
        this.nome_aluno = nome_aluno;
    }

    public void setNome_funcionario(String nome_funcionario) {
        this.nome_funcionario = nome_funcionario;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public void setValor_pago(String valor_pago) {
        this.valor_pago = valor_pago;
    }

    

    public void setValor_apagar(String valor_apagar) {
        this.valor_apagar = valor_apagar;
    }
    
    
    
}
