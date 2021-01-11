/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javafx.scene.control.TextField;

/**
 *
 * @author Familia Neto
 */
public class Pauta_modelo {
    
    private int numeracao;
    private int codmatricula_c;
    private String nome;
    private TextField mac;
    private TextField cp;
    private TextField ct;

    public int getNumeracao() {
        return numeracao;
    }

    public void setNumeracao(int numeracao) {
        this.numeracao = numeracao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodmatricula_c() {
        return codmatricula_c;
    }

    public void setCodmatricula_c(int codmatricula_c) {
        this.codmatricula_c = codmatricula_c;
    }
    

    public TextField getMac() {
        return mac;
    }

    public void setMac(TextField mac) {
        this.mac = mac;
    }

    public TextField getCp() {
        return cp;
    }

    public void setCp(TextField cp) {
        this.cp = cp;
    }

    public TextField getCt() {
        return ct;
    }

    public void setCt(TextField ct) {
        this.ct = ct;
    }
    
    
}
