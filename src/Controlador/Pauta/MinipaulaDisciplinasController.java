/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pauta;

import Bd.OperacoesBase;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Estudante;
import modelo.MesAno;
import modelo.MiniPauta;
import modelo.Minipauta_Trimestral;
import modelo.Nota;
import modelo.Turma;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class MinipaulaDisciplinasController implements Initializable, Runnable {
    
    @FXML private TableView<MiniPauta> tabela;
    @FXML private TableColumn<MiniPauta, String> nome;
    @FXML private TableColumn<MiniPauta, String>mac1;
    @FXML private TableColumn<MiniPauta, String> cp1;
    @FXML private TableColumn<MiniPauta, String> ct1;
    @FXML private TableColumn<MiniPauta, String> mac2;
    @FXML private TableColumn<MiniPauta, String> cp2;
    @FXML private TableColumn<MiniPauta, String>  ct2;
    @FXML private TableColumn<MiniPauta, String>mac3;
    @FXML private TableColumn<MiniPauta, String> cp3;
    @FXML private TableColumn<MiniPauta, String>  ct3;
    @FXML private TableColumn<MiniPauta, String>cap;
    @FXML private TableColumn<MiniPauta, String> ce;
    @FXML private TableColumn<MiniPauta, String> cf;
    @FXML private Label disciplina;
    @FXML private Label classe;
    @FXML private Label curso;
    @FXML private  Label turma;
    
    private static String Disciplina;
    private static String turma_var;
    private static String classe_var;
    private static String curso_var;
    private static Thread t1; 
    
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Inicializa_Disciplina();
        t1 = new Thread(this);
        t1.start();
    }    

    public static void setDisciplina(String Disciplina) {
        MinipaulaDisciplinasController.Disciplina = Disciplina;
    }

    public static void setTurma_var(String turma_var) {
        MinipaulaDisciplinasController.turma_var = turma_var;
    }

    public static void setClasse_var(String classe_var) {
        MinipaulaDisciplinasController.classe_var = classe_var;
    }

    public static void setCurso_var(String curso_var) {
        MinipaulaDisciplinasController.curso_var = curso_var;
    }
    
    @FXML
    private void Imprimir(ActionEvent event) 
    {
        
        
        //Obtencao dos valores
        Alert a;
        int codd = modelo.Disciplina.NameToCode(Disciplina);
        int codturma = Turma.NameToCode(turma_var);
        String ano = MesAno.Get_AnoActualCobranca();
        boolean valor = MiniPauta.JaExisteMiniPautaNaBd(codd);
        if( valor )
        {
             a = new Alert(Alert.AlertType.CONFIRMATION,"Esta MiniPauta ja Existe , Deseja actualizar os valores?");
             Optional<ButtonType> acao = a.showAndWait();
             if( acao.get()== ButtonType.OK)
             {
                 //Elimina os dados que ja estavam la
                 OperacoesBase.Eliminar("delete from minipauta where coddisciplina = '"+codd+"'");
                 Inserir_BaseDados(tabela.getItems());
             }
        }
        else
        {
            Inserir_BaseDados(tabela.getItems());
        }
        a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Por favor Aguarde...");
        a.show();
        HashMap m = new HashMap();
        m.put("codturma", codturma);
        m.put("anolectivo", ano);
        m.put("coddisciplina", codd);
        definicoes.DefinicoesImpressaoRelatorio.ImprimirRelatorio(m, "C:\\RelatorioGenix\\minipauta.jrxml");
        
    }
    
 /**********************************----------METODOS OPERACIONAIS--------------------------***************/

    private void Inicializa_Disciplina()
    {
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Aguarde enquanto a tabela esta a carregar.");
        a.show();
        disciplina.setText(Disciplina);
        turma.setText(turma_var);  
        curso.setText(curso_var);
        classe.setText(classe_var);
    }
    
    private void ListaAlunosTurma()
    {
        int codturma = Turma.NameToCode(turma_var);
        ObservableList<String> alunos =  Estudante.ListaGeralAlunosMatriculadosporTurma(codturma);
        ObservableList<MiniPauta> le = FXCollections.observableArrayList();
       for( String nome : alunos )
       {
           MiniPauta p  = new MiniPauta();
           p.setNome(nome);
           p.setCodmatricula_c(matricula_confirmacao.CodAlunoToCodMatricula(Estudante.NameToCode(nome)));
           p.setDisciplina(Disciplina);
           p.setCoddisciplina(modelo.Disciplina.NameToCode(Disciplina));
           p.setMac1(Minipauta_Trimestral.Obter_Mac(matricula_confirmacao.CodAlunoToCodMatricula(Estudante.NameToCode(nome)), p.getCoddisciplina(),"Iº" , MesAno.Get_AnoActualCobranca(), codturma));
           p.setMac2(Minipauta_Trimestral.Obter_Mac(matricula_confirmacao.CodAlunoToCodMatricula(Estudante.NameToCode(nome)), p.getCoddisciplina(),"IIº" , MesAno.Get_AnoActualCobranca(), codturma));
           p.setMac3(Minipauta_Trimestral.Obter_Mac(matricula_confirmacao.CodAlunoToCodMatricula(Estudante.NameToCode(nome)), p.getCoddisciplina(),"IIIº" , MesAno.Get_AnoActualCobranca() , codturma));
           p.setCp1(Minipauta_Trimestral.Obter_Cp(matricula_confirmacao.CodAlunoToCodMatricula(Estudante.NameToCode(nome)), p.getCoddisciplina(),"Iº" , MesAno.Get_AnoActualCobranca() , codturma));
           p.setCp2(Minipauta_Trimestral.Obter_Cp(matricula_confirmacao.CodAlunoToCodMatricula(Estudante.NameToCode(nome)), p.getCoddisciplina(),"IIº" , MesAno.Get_AnoActualCobranca(), codturma));
           p.setCp3(Minipauta_Trimestral.Obter_Cp(matricula_confirmacao.CodAlunoToCodMatricula(Estudante.NameToCode(nome)), p.getCoddisciplina(),"IIIº" , MesAno.Get_AnoActualCobranca(), codturma));
           p.setCt1(Minipauta_Trimestral.Obter_Ct(matricula_confirmacao.CodAlunoToCodMatricula(Estudante.NameToCode(nome)), p.getCoddisciplina(),"Iº" , MesAno.Get_AnoActualCobranca() , codturma));
           p.setCt2(Minipauta_Trimestral.Obter_Ct(matricula_confirmacao.CodAlunoToCodMatricula(Estudante.NameToCode(nome)), p.getCoddisciplina(),"IIº" , MesAno.Get_AnoActualCobranca(), codturma));
           p.setCt3(Minipauta_Trimestral.Obter_Ct(matricula_confirmacao.CodAlunoToCodMatricula(Estudante.NameToCode(nome)), p.getCoddisciplina(),"IIIº" , MesAno.Get_AnoActualCobranca(), codturma));
           p.setCap(String.valueOf(Double.parseDouble(p.getCt1())+Double.parseDouble(p.getCt2())+Double.parseDouble(p.getCt3())));
           p.setCe(Calculace(Estudante.NameToCode(nome)));
           p.setCf( String.valueOf((Double.parseDouble(p.getCe())+Double.parseDouble(p.getCap()))/2));
           le.add(p);
           
       }
       configColuna();
       tabela.setItems(le);
    }
    
    private String Calculace( int codaluno )
    {
       return  Nota.NotaProvaEscolaPorDisciplina(Disciplina, MesAno.Get_AnoActualCobranca(), "IIIº", codaluno);
    }
    
    
    private void configColuna()
    {
        nome.setCellValueFactory( new PropertyValueFactory<>("nome"));
        mac1.setCellValueFactory( new PropertyValueFactory<>("mac1"));
        mac2.setCellValueFactory( new PropertyValueFactory<>("mac2"));
        mac3.setCellValueFactory( new PropertyValueFactory<>("mac3"));
        cp1.setCellValueFactory( new PropertyValueFactory<>("cp1"));
        cp2.setCellValueFactory( new PropertyValueFactory<>("cp2"));
        cp3.setCellValueFactory( new PropertyValueFactory<>("cp3"));
        ct1.setCellValueFactory( new PropertyValueFactory<>("ct1"));
        ct2.setCellValueFactory( new PropertyValueFactory<>("ct2"));
        ct3.setCellValueFactory( new PropertyValueFactory<>("ct3"));
        cap.setCellValueFactory( new PropertyValueFactory<>("cap"));
        ce.setCellValueFactory( new PropertyValueFactory<>("ce"));
        cf.setCellValueFactory( new PropertyValueFactory<>("cf"));
    }

    
    private void Inserir_BaseDados( ObservableList<MiniPauta> listaMiniPautas )
    {
        
        listaMiniPautas.stream().forEach((miniPauta) -> {
            miniPauta.adicionar();
        });
        
    }
   
    @Override
    public void run() 
    {
        
        ListaAlunosTurma();
    }

    
}
