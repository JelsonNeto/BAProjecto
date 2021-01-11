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
public class Pauta1_4 {
    
    private String nome;
    private String macPort;
    private String cpPort;
    private String ctPort;
    private String macMeio;
    private String cpMeio;
    private String ctMeio;
    private String macMat;
    private String cpMat;
    private String ctMat;
    private String macMusical;
    private String cpMusical;
    private String ctMusical;
    private String macPraticas;
    private String cpPraticas;
    private String ctPraticas;
    private String macEFisica;
    private String cpEFisica;
    private String ctEFisica;
    private String macEmp;
    private String cpEmp;
    private String ctEmp;
    private String obs;

    public String getCpEFisica() {
        return cpEFisica;
    }

    public String getCpEmp() {
        return cpEmp;
    }

    public String getCpMat() {
        return cpMat;
    }

    public String getCpMeio() {
        return cpMeio;
    }

    public String getCpMusical() {
        return cpMusical;
    }

    public String getCpPort() {
        return cpPort;
    }

    public String getCpPraticas() {
        return cpPraticas;
    }

    public String getNome() {
        return nome;
    }

    
    
    public String getCtEFisica() {
        return ctEFisica;
    }

    public String getCtEmp() {
        return ctEmp;
    }

    public String getCtMat() {
        return ctMat;
    }

    public String getCtMeio() {
        return ctMeio;
    }

    public String getCtMusical() {
        return ctMusical;
    }

    public String getCtPort() {
        return ctPort;
    }

    public String getCtPraticas() {
        return ctPraticas;
    }

    public String getMacEFisica() {
        return macEFisica;
    }

    public String getMacEmp() {
        return macEmp;
    }

    public String getMacMat() {
        return macMat;
    }

    public String getMacMeio() {
        return macMeio;
    }

    public String getMacMusical() {
        return macMusical;
    }

    public String getMacPort() {
        return macPort;
    }

    public String getMacPraticas() {
        return macPraticas;
    }

    public String getObs() {
        return obs;
    }

    public void setCpEFisica(String cpEFisica) {
        this.cpEFisica = cpEFisica;
    }

    public void setCpEmp(String cpEmp) {
        this.cpEmp = cpEmp;
    }

    public void setCpMat(String cpMat) {
        this.cpMat = cpMat;
    }

    public void setCpMeio(String cpMeio) {
        this.cpMeio = cpMeio;
    }

    public void setCpMusical(String cpMusical) {
        this.cpMusical = cpMusical;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    

    public void setCpPort(String cpPort) {
        this.cpPort = cpPort;
    }

    public void setCpPraticas(String cpPraticas) {
        this.cpPraticas = cpPraticas;
    }

    public void setCtEFisica(String ctEFisica) {
        this.ctEFisica = ctEFisica;
    }

    public void setCtEmp(String ctEmp) {
        this.ctEmp = ctEmp;
    }

    public void setCtMat(String ctMat) {
        this.ctMat = ctMat;
    }

    public void setCtMeio(String ctMeio) {
        this.ctMeio = ctMeio;
    }

    public void setCtMusical(String ctMusical) {
        this.ctMusical = ctMusical;
    }

    public void setCtPort(String ctPort) {
        this.ctPort = ctPort;
    }

    public void setCtPraticas(String ctPraticas) {
        this.ctPraticas = ctPraticas;
    }

    public void setMacEFisica(String macEFisica) {
        this.macEFisica = macEFisica;
    }

    public void setMacEmp(String macEmp) {
        this.macEmp = macEmp;
    }

    public void setMacMat(String macMat) {
        this.macMat = macMat;
    }

    public void setMacMeio(String macMeio) {
        this.macMeio = macMeio;
    }

    public void setMacMusical(String macMusical) {
        this.macMusical = macMusical;
    }

    public void setMacPort(String macPort) {
        this.macPort = macPort;
    }

    public void setMacPraticas(String macPraticas) {
        this.macPraticas = macPraticas;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
    
    
    public void Adicionar()
    {
        String sql = "insert into minipauta1_4 values('"+this.getNome()+"','"+this.getMacPort()+"','"+this.getCpPort()+"','"+this.getCtPort()+"','"+this.getObs()+"','"+this.getMacMat()+"','"+this.getCpMat()+"','"+this.getCtMat()+"','"+this.getMacEFisica()+"','"+this.getCpEFisica()+"','"+this.getCtEFisica()+"','"+this.getMacEmp()+"','"+this.getCpEmp()+"','"+this.getCtEmp()+"','"+this.getMacMeio()+"','"+this.getCpMeio()+"','"+this.getCtMeio()+"','"+this.getMacMusical()+"','"+this.getCpMusical()+"','"+this.getCtMusical()+"','"+this.getMacPraticas()+"','"+this.getCpPraticas()+"','"+this.getCtPraticas()+"')";
        OperacoesBase.Inserir(sql);
    }
    
    
}
