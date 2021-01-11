/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import Bd.OperacoesBase;

/**
 *
 * @author Familia Neto
 */
public class VisualizaPreco {
    
    private int codigo;
    private int valor;
    private int codcurso;
    private String classe;
    private String valor_multa;
    private String efeito;
    private String curso;
    private String valor_tabela;

    public VisualizaPreco() {
    }

    public String getClasse() {
        return classe;
    }

    public int getCodcurso() {
        return codcurso;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getEfeito() {
        return efeito;
    }

    public String getValor_multa() {
        return valor_multa;
    }

    
   

    public int getValor() {
        return valor;
    }

    public String getCurso() {
        return curso;
    }

    public String getValor_tabela() {
        return valor_tabela;
    }

    public void setValor_multa(String valor_multa) {
        this.valor_multa = valor_multa;
    }

    
    
    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setValor_tabela(String valor_tabela) {
        this.valor_tabela = valor_tabela;
    }

    
    public void setCodcurso(int codcurso) {
        this.codcurso = codcurso;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setEfeito(String efeito) {
        this.efeito = efeito;
    }

  

    public void setCurso(String curso) {
        this.curso = curso;
    }
    

    public void setValor(int valor) {
        this.valor = valor;
    }
    
  
    public boolean Eliminar()
    {
        String sql= "delete from preco where codpreco = '"+this.getCodigo()+"'";
        return OperacoesBase.Eliminar(sql);
    }
    
}
