/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Boletim;

import Bd.OperacoesBase;
import Bd.conexao;
import definicoes.DefinicoesData;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Boletin;
import modelo.Classe;
import modelo.Curso;
import modelo.Disciplina;
import modelo.Estudante;
import modelo.Nota;
import modelo.RegistroUsuario;
import modelo.Turma;
import modelo.Usuario;
import modelo.matricula_confirmacao;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class EmitirBoletinController implements Initializable {
   
    @FXML private ComboBox<String> cb_nome;
    @FXML private ComboBox<String> cb_curso;
    @FXML private ComboBox<String> cb_turma;
    @FXML private ComboBox<String> cb_classe;
    @FXML private TextField txt_bi;
    @FXML private TextField txt_curso;
    @FXML private TextField txt_classe;
    @FXML private ComboBox<String> cb_trimestre;
    @FXML private ComboBox<String> cb_ano;
    @FXML private TextField txt_datanasc;
    @FXML private TableView<Boletin> tabela;
    @FXML private TableColumn<Boletin, String> coluna_disciplina;
    @FXML private TableColumn<Boletin, String> coluna_mac;
    @FXML private TableColumn<Boletin, String> coluna_cp;
    @FXML private TableColumn<Boletin, String> coluna_ct;
    @FXML private TextField txt_classificacao;
    
    //variaveis globais gerais
    private static String nomeAluno;
    private static String nomeUser;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCurso();
    }    

    @FXML
    private void SelecionaEstudante_InicializaBi(ActionEvent event)
    {
        SelecionaNome_InicializaOutros();
    }

    @FXML
    private void SelecionaOCurso_InicializaClasse(ActionEvent event) 
    {
        SelecionaCurso_Inicializa_Classe();
    }

    @FXML
    private void SelecionaTurmaInicializarAluno(ActionEvent event) 
    {
        SelecionaTurma_InicializaNome();
    }

    @FXML
    private void SelecionaClasse_InicializaTurma(ActionEvent event)
    {
        SelecionaClasse_InicializaTurma();
    }

    @FXML
    private void SelecionaTrimestre_InicializaDisciplinasNotas()
    {
        String ano_var = cb_ano.getSelectionModel().getSelectedItem();
        String trimestre_var = cb_trimestre.getSelectionModel().getSelectedItem();
        if( ano_var != null && trimestre_var != null )
        {
            Inicializa_ListaDisciplinas();
        }
    }
    
    @FXML
    private void emitirBoletin(ActionEvent event) 
    {
       String curso = cb_curso.getSelectionModel().getSelectedItem();
       if( curso != null )
       {
           RegistroUsuario.AddRegistro("Emissão do boletim do estudante:"+nomeAluno);
           Alert a = new Alert(Alert.AlertType.INFORMATION, "Gerando o  Boletim, por favor Aguarde");
           a.show();
           EmitirBoletim(retornaPath(curso));
          
       }
       else
       {
           Alert a = new Alert(Alert.AlertType.INFORMATION, "Impossivel emitir o boletim");
           a.show();
          
       }
     /*   try {
            Desktop.getDesktop().open( new File(file));
        } catch (IOException ex) {
            Logger.getLogger(EmitirBoletinController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
/************************************************METODOS OPERACIONAIS****************************************************************************/
    private void InicializaCurso()
    {
        cb_curso.setItems(Curso.ListaCursos());
    }
    
    private void SelecionaCurso_Inicializa_Classe()
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        if( curso != null )
           cb_classe.setItems(Classe.ClassesPorCurso(curso));
    }
    
    private void SelecionaClasse_InicializaTurma()
    {
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        int codcurso = Curso.NameToCode(cb_curso.getSelectionModel().getSelectedItem());
        if( classe != null && codcurso > 0 )
          cb_turma.setItems(Turma.ListaTurmasRelaClasse_CodCurso(classe, codcurso));
    }
    
    private void SelecionaTurma_InicializaNome()
    {
        String turma = cb_turma.getSelectionModel().getSelectedItem();
        if( turma != null )
          cb_nome.setItems(Estudante.ListaGeralAlunosMatriculadosporTurma(Turma.NameToCode(turma)));
    }
    
    private void SelecionaNome_InicializaOutros()
    {
        String nome = cb_nome.getSelectionModel().getSelectedItem();
        if( nome != null )
        {
            txt_bi.setText(Estudante.NameToBi(nome));
            txt_curso.setText(Estudante.codeToCurso(Estudante.NameToCode(nome)));
            txt_classe.setText(Estudante.codeToClasse(Estudante.NameToCode(nome)));
            txt_datanasc.setText(Estudante.codeToDataNasc(Estudante.NameToCode(nome)));
            Inicializa_AnoLectivo();
            InicializaTrimestre();
        }
    }
    
    private void InicializaTrimestre()
    {
        String [] valor = {"Iº", "IIº","IIIº"};
        cb_trimestre.setItems(FXCollections.observableArrayList(Arrays.asList(valor)));
        
    }
    private void Inicializa_AnoLectivo()
    {
        cb_ano.setItems(matricula_confirmacao.ListaDosAnosMatriculados());
    }
    
    private void Inicializa_ListaDisciplinas()
    {
         
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        if( curso != null && classe != null )
           ConfiguraTabela(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
    }
    
    private void ConfiguraTabela( ObservableList<String> Disciplinas )
    {
        //elimina os dados que ja existem na tabela
        OperacoesBase.Eliminar("truncate table boletin1_4");
        
        SetColunas();
        int codaluno_var = Estudante.NameToCode(cb_nome.getSelectionModel().getSelectedItem());
        String trimestre_var = cb_trimestre.getSelectionModel().getSelectedItem();
        String ano_var = cb_ano.getSelectionModel().getSelectedItem();

        ObservableList<Boletin> lista_Boletin = FXCollections.observableArrayList();
        for( String disc : Disciplinas )
        {
            Boletin b = new Boletin();
            b.setCodigo(Boletin.RetornarUltimoCodigo());
            b.setCodmatricula(matricula_confirmacao.CodAlunoToCodMatricula(codaluno_var));
            b.setDisciplina(disc);
            b.setMac(retornaMac(disc, ano_var, trimestre_var, codaluno_var));
            b.setCp(retornaCp(disc , ano_var, trimestre_var , codaluno_var));
            b.setCt(retornaCt(disc, ano_var, trimestre_var, codaluno_var));
            b.setTrimestre(trimestre_var);
            b.setAno(ano_var);
            b.setClassificacao("teste");
            
            lista_Boletin.add(b);
            nomeAluno = Estudante.CodeToName(codaluno_var);
            b.Adicionar();
        }
        tabela.setItems(lista_Boletin);
        
    }
    
    private void SetColunas()
    {
        coluna_cp.setCellValueFactory( new PropertyValueFactory<>("cp"));
        coluna_mac.setCellValueFactory( new PropertyValueFactory<>("mac"));
        coluna_ct.setCellValueFactory( new PropertyValueFactory<>("ct"));
        coluna_disciplina.setCellValueFactory( new PropertyValueFactory<>("disciplina"));
        
    }
    
    private String retornaMac( String disciplina, String ano ,String trimestre, int codaluno )
    {
       return Nota.AvaliacaoPorDisciplina(disciplina, ano, trimestre, codaluno);
    }
    
    private String  retornaCp( String disciplina , String ano , String trimestre , int codaluno )
    {
        return Nota.NotaProvaProfessorPorDisciplina(disciplina, ano, trimestre, codaluno);
    }
    
    private String retornaCt( String disciplina , String ano , String trimestre , int codaluno )
    {
         double mac_var = Double.parseDouble(Nota.AvaliacaoPorDisciplina(disciplina, ano, trimestre, codaluno));
         double cp_var = Double.parseDouble(Nota.NotaProvaProfessorPorDisciplina(disciplina, ano, trimestre, codaluno));
         double ct_var  = (mac_var+cp_var)/2;
         
         //retornaClassificacao(ct);
         return String.valueOf(ct_var);
         
    }
 
   

    private void retornaClassificacao(double ct)
    {
        if( ct >= 5 )
        {
            txt_classificacao.setText("Aprovado");
        }
        else
        {
            txt_classificacao.setText("Reprovado");
        }
    }

    
    
    
    private String EmitirBoletim( String path )
    {
         
        try
        {
            conexao.Conectar();
            JasperReport report = JasperCompileManager.compileReport(path);
            
            JasperPrint print = JasperFillManager.fillReport(report, new HashMap<>(), conexao.ObterConection());
           
            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);
            view.toFront();
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        conexao.TerminarConexao();
        return "";
    }
    

    public static void setNomeUser(String nomeUser) {
        EmitirBoletinController.nomeUser = nomeUser;
    }
    
    private String retornaPath( String curso  )
    {
        if( "Primária".equalsIgnoreCase(curso) )
            return "C:\\RelatorioGenix\\boletinAluno_Primeira_Quarta.jrxml";
        else
            if( "Ensino Secundário Iº Ciclo".equalsIgnoreCase(curso))
                return "C:\\RelatorioGenix\\BoletinNormal.jrxml";
        return "C:\\RelatorioGenix\\BoletinCurso.jrxml";
    }

}

