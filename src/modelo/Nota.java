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
import Controlador.Notas.SomatorioNotaController;

/**
 *
 * @author Familia Neto
 */
public class Nota {
   
    private int codigo;
    private int codmatricula;
    private int coddisciplina;
    private String valor;
    private String trimestre;
    private String descricao;
    private String ano_lectivo;
    private String nome_aluno;
    private String disciplina;
    private String cap;
    private String ce;
    private String mac1;
    private String cp1;
    private String ct1;
    private String mac2;
    private String cp2;
    private String ct2;
    private String mac3;
    private String cp3;
    private String ct3;
    private String curso;
    private String classe;
    private String professor; //Ver notas no perfil do aluno
    private int quantidade; //posto aqui para ser utilizado na classe somatorioNota

    public int getCodmatricula() {
        return codmatricula;
    }
    

    

    public int getCoddisciplina() {
        return coddisciplina;
    }

    public String getAno_lectivo() {
        return ano_lectivo;
    }

    public String getClasse() {
        return classe;
    }

    public String getCp1() {
        return cp1;
    }

    public String getCt1() {
        return ct1;
    }

    public String getMac1() {
        return mac1;
    }

    public String getCp2() {
        return cp2;
    }

    public String getCp3() {
        return cp3;
    }

    public String getCt2() {
        return ct2;
    }

    public String getCt3() {
        return ct3;
    }

    public String getMac2() {
        return mac2;
    }

    public String getMac3() {
        return mac3;
    }

    public String getCap() {
        return cap;
    }

    public String getCe() {
        return ce;
    }

    

    public String getProfessor() {
        return professor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    
    
    
    public String getCurso() {
        return curso;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public String getNome_aluno() {
        return nome_aluno;
    }

    
    
    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTrimestre() {
        return trimestre;
    }

    public String getValor() {
        return valor;
    }

    public void setCp1(String cp1) {
        this.cp1 = cp1;
    }

    public void setCt1(String ct1) {
        this.ct1 = ct1;
    }

    public void setMac1(String mac1) {
        this.mac1 = mac1;
    }

    public void setCp2(String cp2) {
        this.cp2 = cp2;
    }

    public void setCp3(String cp3) {
        this.cp3 = cp3;
    }

    public void setCt2(String ct2) {
        this.ct2 = ct2;
    }

    public void setCt3(String ct3) {
        this.ct3 = ct3;
    }

    public void setMac2(String mac2) {
        this.mac2 = mac2;
    }

    public void setMac3(String mac3) {
        this.mac3 = mac3;
    }

    public void setCodmatricula(int codmatricula) {
        this.codmatricula = codmatricula;
    }

    
    
    

    public void setCoddisciplina(int coddisciplina) {
        this.coddisciplina = coddisciplina;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
     

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public void setNome_aluno(String nome_aluno) {
        this.nome_aluno = nome_aluno;
    }
    
    

    public void setAno_lectivo(String ano_lectivo) {
        this.ano_lectivo = ano_lectivo;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public void setCe(String ce) {
        this.ce = ce;
    }

    
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTrimestre(String trimestre) {
        this.trimestre = trimestre;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    
    
    public boolean Adicionar()
    {
        String sql = "insert into nota values('"+this.getCodigo()+"' , '"+this.getCodmatricula()+"' , '"+this.getCoddisciplina()+"' , '"+this.getTrimestre()+"','"+this.getValor()+"', '"+this.getDescricao()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
/*****************************************************Metodoos Operacionais****************************************/
    public static int RetornaUltimoId()
    {
        String sql = "select codnota from nota";
        ResultSet rs = OperacoesBase.Consultar(sql);
        int valor = 0;
        try {
            while( rs.next() )
            {
               valor =  rs.getInt("codnota");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Nota.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++valor;
    }
    
    public static String NotaProvaProfessorPorDisciplina( String nomeD , String anoLe , String trimestre, int codaluno )
    {
        String descricao = "Prova do Professor";
        String sql = "select valor from nota inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) where coddisciplina = '"+Disciplina.NameToCode(nomeD)+"' and codaluno =  '"+codaluno+"' and trimestre = '"+trimestre+"' and anolectivo = '"+anoLe+"' and descricao = '"+descricao+"'";
        double cp = 0.0;
        int quant= 0;
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                cp += Double.parseDouble(rs.getString("valor"));
                quant++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Nota.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        SomatorioNotaController.quantidade = quant;
        return String.valueOf(cp);
    }
    
    
    
      
    public static String AvaliacaoPorDisciplina( String nomeD , String anoLe , String trimestre, int codaluno )
    {   
        String descricao = "Avaliação";
        String sql = "select valor from nota inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) where coddisciplina = '"+Disciplina.NameToCode(nomeD)+"' and codaluno =  '"+codaluno+"' and trimestre = '"+trimestre+"' and anolectivo = '"+anoLe+"' and descricao = '"+descricao+"'";
        double mac = 0.0;
        ResultSet rs = OperacoesBase.Consultar(sql);
        int quant = 0;
        try {
            while( rs.next() )
            {
               mac += Double.parseDouble(rs.getString("valor"));
               quant++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Nota.class.getName()).log(Level.SEVERE, null, ex);
        }
        SomatorioNotaController.quantidade = quant;
        return String.valueOf(mac);
    }
    
    
    
    public static String NotaProvaEscolaPorDisciplina( String nomeD , String anoLe , String trimestre, int codaluno )
    {
        String descricao = "Prova da escola";
        String sql = "select valor from nota inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) where coddisciplina = '"+Disciplina.NameToCode(nomeD)+"' and codaluno =  '"+codaluno+"' and trimestre = '"+trimestre+"' and anolectivo = '"+anoLe+"' and descricao = '"+descricao+"'";
        double total = 0.0;
        int quant= 0;
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                total += Double.parseDouble(rs.getString("valor"));
                quant++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Nota.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        SomatorioNotaController.quantidade = quant;
        return String.valueOf(total);
    }
    
 /**************************Listas**************************************/
    public static ObservableList<String> ListaNotaProvaProfessorPorDisciplina( String nomeD , String anoLe , String trimestre, int codaluno )
    {
        String descricao = "Prova do Professor";
        String sql = "select valor from nota where coddisciplina = '"+Disciplina.NameToCode(nomeD)+"' and codaluno =  '"+codaluno+"' and trimestre = '"+trimestre+"' and ano_lectivo = '"+anoLe+"' and descricao = '"+descricao+"'";
        ObservableList<String> lista  = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
               lista.add(rs.getString("valor"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Nota.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }

}