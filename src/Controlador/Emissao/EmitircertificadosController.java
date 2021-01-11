/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Emissao;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Classe;
import modelo.Curso;
import modelo.Disciplina;
import modelo.Estudante;
import modelo.MesAno;
import modelo.MiniPauta;
import modelo.Professor;
import modelo.Turma;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class EmitircertificadosController implements Initializable {
   
    @FXML private TableView<MiniPauta> tabela;
    @FXML private TableColumn<MiniPauta, String> coluna_disciplina;
    @FXML private TableColumn<MiniPauta, String> coluna_professor;
    @FXML private TableColumn<MiniPauta, String> coluna_cap;
    @FXML private TableColumn<MiniPauta, String> coluna_ce;
    @FXML private TableColumn<MiniPauta, String> coluna_cf;
    @FXML private ComboBox<String> cb_curso;
    @FXML private ComboBox<String> cb_classe;
    @FXML private ComboBox<String> cb_turma;
    @FXML private ComboBox<String> cb_ano;
    @FXML private ComboBox<String> cb_nome;
    private String curso;
    private String classe;
    @FXML
    private Label lbl_classificacao;
  
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCurso();
    }    

    @FXML
    private void SelecionaOCurso_InicializaClasse(ActionEvent event) 
    {
        String curso_var = cb_curso.getSelectionModel().getSelectedItem();
        curso = curso_var;
        if( curso_var != null )
            cb_classe.setItems(Classe.ClassesPorCurso(curso));
    }

    @FXML
    private void SelecionaClasse_InicializaTurma(ActionEvent event) 
    {
        String classe_var = cb_classe.getSelectionModel().getSelectedItem();
        classe = classe_var;
        int codcurso = Curso.NameToCode(cb_curso.getSelectionModel().getSelectedItem());
        if( classe_var != null )
            cb_turma.setItems(Turma.ListaTurmasRelaClasse_CodCurso(classe,codcurso ));
    }
    
    
    @FXML
    private void SelecionaTurmaInicializarAno(ActionEvent event) 
    {
         String turma = cb_turma.getSelectionModel().getSelectedItem();
        if( turma != null )
            InicializaAno();
    }
    
    @FXML
    private void SelecionaAno_InicializaAluno(ActionEvent event) 
    {
        String ano = cb_ano.getSelectionModel().getSelectedItem();
        int codturma = Turma.NameToCode(cb_turma.getSelectionModel().getSelectedItem());
        if( codturma > 0  )
        {
            cb_nome.setItems(Estudante.ListaGeralAlunosMatriculadosporTurma_Anolectivo(codturma , ano));
        }
    }
    
    @FXML
    private void selecionaAluno_InicializaTabela(ActionEvent event) 
    {
        String nome = cb_nome.getSelectionModel().getSelectedItem();
        if( nome != null )
        {
            InicializaTabela();
            AprovadoReprovado();
        }
        
    }


    @FXML
    private void emitir(ActionEvent event) {
    }
    
/*******************************METODOS OPERACIONEIS****************************************************/
    private void InicializaCurso()
    {
        cb_curso.setItems(Curso.ListaCursos());
    }
    
    private void InicializaAno()
    {
      String mes = MesAno.Get_MesActualCobranca();
      String ano = MesAno.Get_AnoActualCobranca();
      cb_ano.setItems(matricula_confirmacao.ListaDosAnosMatriculados());
      if( "Dezembro".equalsIgnoreCase(mes) )
         cb_ano.getItems().remove(ano);
    }
    
    private void InicializaTabela()
    {
        SetColunas();
        ObservableList<String> disciplinas  = Disciplina.DisciplinasCurso_Classe(curso, classe);
        ObservableList<MiniPauta> lista = FXCollections.observableArrayList();
        int codmatricula = matricula_confirmacao.CodAlunoToCodMatricula(Estudante.NameToCode(cb_nome.getSelectionModel().getSelectedItem()));
        for( String valor : disciplinas )
        {
            MiniPauta m = new MiniPauta();
            m.setDisciplina(valor);
            m.setNomeProfessor(Turma.Turma_DisciplinaNomeProfessor(Turma.NameToCode(cb_turma.getSelectionModel().getSelectedItem()),Disciplina.NameToCode(valor, curso, classe) ,cb_ano.getSelectionModel().getSelectedItem()));
            m.setCap(MiniPauta.retornaCap(codmatricula, Disciplina.NameToCode(valor), cb_ano.getSelectionModel().getSelectedItem()));
            m.setCe(MiniPauta.retornaCE(codmatricula, Disciplina.NameToCode(valor), cb_ano.getSelectionModel().getSelectedItem()));
            m.setCf(MiniPauta.retornaCF(codmatricula, Disciplina.NameToCode(valor), cb_ano.getSelectionModel().getSelectedItem()));
            lista.add(m);
        }
        
        tabela.setItems(lista);
    }

    private void AprovadoReprovado()
    {
        if (Aprovados_Direito()) 
        {
            lbl_classificacao.setStyle("-Fx-Text-Fill:green");
            lbl_classificacao.setText("Aprovado");
        }
        
        
    }
   
    
    private boolean Aprovados_Direito()
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem() ;
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        
        return curso.equals("Primária") && (classe.equals("1ª classe")||classe.equals("3ª classe")||classe.equals("5ª classe"));
        
    }
    
    private void SetColunas()
    {
        coluna_disciplina.setCellValueFactory( new PropertyValueFactory<>("disciplina"));
        coluna_cap.setCellValueFactory( new PropertyValueFactory<>("cap"));
        coluna_ce.setCellValueFactory( new PropertyValueFactory<>("ce"));
        coluna_cf.setCellValueFactory( new PropertyValueFactory<>("cf"));
        coluna_professor.setCellValueFactory( new PropertyValueFactory<>("nomeProfessor"));
    }

    
}
