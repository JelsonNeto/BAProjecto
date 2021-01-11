/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pauta;

import Bd.OperacoesBase;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Estudante;
import modelo.MesAno;
import modelo.Nota;
import modelo.Pauta1_4;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class Paula1_4Controller implements Initializable,Runnable {
    
    @FXML private TableColumn<Pauta1_4, String> coluna_nome;
    @FXML private TableColumn<Pauta1_4, String> coluna_macPort;
    @FXML private TableColumn<Pauta1_4, String> coluna_cpPort;
    @FXML private TableColumn<Pauta1_4, String> coluna_ctPort;
    @FXML private TableColumn<Pauta1_4, String> coluna_macMeio;
    @FXML private TableColumn<Pauta1_4, String> coluna_cpMeio;
    @FXML private TableColumn<Pauta1_4, String> coluna_ctMeio;
    @FXML private TableColumn<Pauta1_4, String> coluna_macMat;
    @FXML private TableColumn<Pauta1_4, String> coluna_ctMat;
    @FXML private TableColumn<Pauta1_4, String>coluna_macMusical;
    @FXML private TableColumn<Pauta1_4, String> coluna_cpMusica;
    @FXML private TableColumn<Pauta1_4, String> coluna_ctMusica;
    @FXML private TableColumn<Pauta1_4, String> coluna_macPraticas;
    @FXML private TableColumn<Pauta1_4, String> coluna_cpPraticas;
    @FXML private TableColumn<Pauta1_4, String> coluna_ctPraticas;
    @FXML private TableColumn<Pauta1_4, String> coluna_macEFisica;
    @FXML private TableColumn<Pauta1_4, String> coluna_cpEFisica;
    @FXML private TableColumn<Pauta1_4, String> coluna_ctEFisica;
    @FXML private TableColumn<Pauta1_4, String> coluna_macEmp;
    @FXML private TableColumn<Pauta1_4, String> coluna_cpEmp;
    @FXML private TableColumn<Pauta1_4, String> coluna_ctEmp;
    @FXML private TableColumn<Pauta1_4, String> coluna_obs;
    @FXML private TableColumn<Pauta1_4, String>  coluna_cpMat;
    @FXML private TableView<Pauta1_4> tabela;
    
    private static String curso;
    private static String classe;
    private static int codturma;
    private static Label txt_total;
    private Thread t ;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       ConfigColunas();
       t = new Thread(this);
       t.start();
    }    
   
/***********************METODOS AUXILIARES*****************************************/
    private void ConfigColunas()
    {
        coluna_cpEFisica.setCellValueFactory(new PropertyValueFactory<>("cpEFisica"));
        coluna_cpEmp.setCellValueFactory(new PropertyValueFactory<>("cpEmp"));
        coluna_cpMeio.setCellValueFactory(new PropertyValueFactory<>("cpMeio"));
        coluna_cpMusica.setCellValueFactory(new PropertyValueFactory<>("cpMusical"));
        coluna_cpPort.setCellValueFactory(new PropertyValueFactory<>("cpPort"));
        coluna_cpPraticas.setCellValueFactory(new PropertyValueFactory<>("cpPraticas"));
        coluna_cpMat.setCellValueFactory(new PropertyValueFactory<>("cpMat"));
        //
        coluna_ctEFisica.setCellValueFactory(new PropertyValueFactory<>("ctEFisica"));
        coluna_ctEmp.setCellValueFactory(new PropertyValueFactory<>("ctEmp"));
        coluna_ctMeio.setCellValueFactory(new PropertyValueFactory<>("ctMeio"));
        coluna_ctMat.setCellValueFactory(new PropertyValueFactory<>("ctMat"));
        coluna_ctPort.setCellValueFactory(new PropertyValueFactory<>("ctPort"));
        coluna_ctPraticas.setCellValueFactory(new PropertyValueFactory<>("ctPraticas"));
        coluna_ctMusica.setCellValueFactory(new PropertyValueFactory<>("ctMusical"));
        //
        coluna_obs.setCellValueFactory(new PropertyValueFactory<>("obs"));
        coluna_macPraticas.setCellValueFactory(new PropertyValueFactory<>("macPraticas"));
        coluna_macMeio.setCellValueFactory(new PropertyValueFactory<>("macMeio"));
        coluna_macPort.setCellValueFactory(new PropertyValueFactory<>("macPort"));
        coluna_macMusical.setCellValueFactory(new PropertyValueFactory<>("macMusical"));
        coluna_macEFisica.setCellValueFactory(new PropertyValueFactory<>("macEFisica"));
        coluna_macMat.setCellValueFactory(new PropertyValueFactory<>("macMat"));
        coluna_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        coluna_macEmp.setCellValueFactory(new PropertyValueFactory<>("macEmp"));
    }
    
    
    private void ConfigTabela( String curso , String classe , int codturma )
    {
        //elimina os dados ja existentes na tabela
         OperacoesBase.Eliminar("truncate table minipauta1_4");
        
        ObservableList<Pauta1_4> lista = FXCollections.observableArrayList();
        for( String nome_aluno : Estudante.ListaAlunosGeralTurma(codturma) )
        {
            Pauta1_4 p = new Pauta1_4();
            p.setNome(nome_aluno);
            //p.setCpPort(Disciplina.ListaDisciplinaCurso(curso));
            p.setMacPort(RetornaCap("L.Portuguesa", MesAno.Get_AnoActualCobranca(), Estudante.NameToCode(nome_aluno)));
            p.setMacEFisica(RetornaCap("E.Fisica", MesAno.Get_AnoActualCobranca(), Estudante.NameToCode(nome_aluno)));
            p.setMacEmp(RetornaCap("E.M.P", MesAno.Get_AnoActualCobranca(), Estudante.NameToCode(nome_aluno)));
            p.setMacMat(RetornaCap("Matematica", MesAno.Get_AnoActualCobranca(), Estudante.NameToCode(nome_aluno)));
            p.setMacMeio(RetornaCap("E.Meio", MesAno.Get_AnoActualCobranca(), Estudante.NameToCode(nome_aluno)));
            p.setMacMusical(RetornaCap("E.Musical", MesAno.Get_AnoActualCobranca(), Estudante.NameToCode(nome_aluno)));
            p.setMacPraticas(RetornaCap("A.Praticas", MesAno.Get_AnoActualCobranca(), Estudante.NameToCode(nome_aluno)));
            //
            p.setCpPort(RetornaGeralCe(Double.parseDouble(p.getMacPort()),"L.Portuguesa", MesAno.Get_AnoActualCobranca(), Estudante.NameToCode(nome_aluno)));
            p.setCpEFisica(RetornaGeralCe(Double.parseDouble(p.getMacEFisica()),"E.Fisica", MesAno.Get_AnoActualCobranca(), Estudante.NameToCode(nome_aluno)));
            p.setCpEmp(RetornaGeralCe(Double.parseDouble(p.getMacEmp()),"E.M.P", MesAno.Get_AnoActualCobranca(), Estudante.NameToCode(nome_aluno)));
            p.setCpMat(RetornaGeralCe(Double.parseDouble(p.getMacMat()),"Matematica", MesAno.Get_AnoActualCobranca(), Estudante.NameToCode(nome_aluno)));
            p.setCpMeio(RetornaGeralCe(Double.parseDouble(p.getMacMeio()),"E.Meio", MesAno.Get_AnoActualCobranca(), Estudante.NameToCode(nome_aluno)));
            p.setCpMusical(RetornaGeralCe(Double.parseDouble(p.getMacMusical()),"E.Musical", MesAno.Get_AnoActualCobranca(), Estudante.NameToCode(nome_aluno)));
            p.setCpPraticas(RetornaGeralCe(Double.parseDouble(p.getMacPraticas()),"A.Praticas", MesAno.Get_AnoActualCobranca(), Estudante.NameToCode(nome_aluno)));
           //
            p.setCtEFisica(RetornaCfGeral( p.getCpEFisica(), p.getMacEFisica() ));
            p.setCtPort(RetornaCfGeral( p.getCpEFisica(), p.getMacPort()));
            p.setCtEmp(RetornaCfGeral( p.getCpEFisica() , p.getMacEmp()));
            p.setCtMat(RetornaCfGeral( p.getCpEFisica(), p.getMacMat()));
            p.setCtMeio(RetornaCfGeral( p.getCpEFisica(), p.getMacMeio()));
            p.setCtMusical(RetornaCfGeral( p.getCpEFisica(), p.getMacMusical()));
            p.setCtPraticas(RetornaCfGeral( p.getCpEFisica(), p.getMacPraticas()));
            p.setObs("N/Apto");
            
            lista.add(p);
            p.Adicionar();
           // Inserir_Minipauta();//Insereri as notas em ordem as disciplinas listadas o ficheiro do jasasperReport
        }
        tabela.setItems(lista);
    }

    public static void setCurso(String curso) {
        Paula1_4Controller.curso = curso;
    }

    public static void setClasse(String classe) {
        Paula1_4Controller.classe = classe;
    }

    public static void setCodturma(int codturma) {
        Paula1_4Controller.codturma = codturma;
    }


    public static void setTxt_total(Label txt_total) {
        Paula1_4Controller.txt_total = txt_total;
    }
    
    private String RetornaCap( String disc , String ano  , int codaluno )//Cap
    {
        double m1 = Double.parseDouble(Nota.AvaliacaoPorDisciplina(disc, ano, "Iº", codaluno));
        double m2 = Double.parseDouble(Nota.AvaliacaoPorDisciplina(disc, ano, "IIº", codaluno));
        double m3 = Double.parseDouble(Nota.AvaliacaoPorDisciplina(disc, ano, "IIIº", codaluno)); 
        double cp1 = Double.parseDouble(Nota.NotaProvaProfessorPorDisciplina(disc, ano, "Iº", codaluno));
        double cp2 = Double.parseDouble(Nota.NotaProvaProfessorPorDisciplina(disc, ano, "IIº", codaluno));
        double cp3 = Double.parseDouble(Nota.NotaProvaProfessorPorDisciplina(disc, ano, "IIIº", codaluno)); 
        
        double ct1= (m1+cp1)/2;
        double ct2 = (m2+cp2)/2;
        double ct3 = (m3+cp3)/2;
        
       
        return String.valueOf(ct1+ct2+ct3);
    } 
    
    private String RetornaGeralCe( double cap, String disc , String ano  , int codaluno )
    {
        
        double ce = Double.parseDouble(Nota.NotaProvaEscolaPorDisciplina(disc, ano, "IIIº", codaluno)); 
       
        return String.valueOf(ce);
    }
    
    private String RetornaCfGeral( String cap , String cp )
    {
        double valor1 = Double.parseDouble(cap);
        double valor2 = Double.parseDouble(cp);
        
        return String.valueOf((valor1+valor2)/2);
    }

    @Override
    public void run() 
    {
         ConfigTabela(curso, classe, codturma);
    }
    
}
