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
public class PagaramAlunos {
    
    private String nome;
    private String classe;
    private String turma;
    private String valor;
    private String curso;
    private String efeito;
    private LocalDate data;
    private String multa;
    private String periodo;
    private String valorAPagar;

    public String getPeriodo() {
        return periodo;
    }

    public String getValorAPagar() {
        return valorAPagar;
    }

    public void setValorAPagar(String valorAPagar) {
        this.valorAPagar = valorAPagar;
    }
    

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getClasse() {
        return classe;
    }

    public String getCurso() {
        return curso;
    }

    

    public String getMulta() {
        return multa;
    }

    public void setMulta(String multa) {
        this.multa = multa;
    }
    public LocalDate getData() {
        return data;
    }

  

    public String getNome() {
        return nome;
    }

    public String getTurma() {
        return turma;
    }

    public String getValor() {
        return valor;
    }

    public String getEfeito() {
        return efeito;
    }

    public void setEfeito(String efeito) {
        this.efeito = efeito;
    }
    
    

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    
    
}
