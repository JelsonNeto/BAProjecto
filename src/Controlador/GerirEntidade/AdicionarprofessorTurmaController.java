/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.GerirEntidade;

import Bd.OperacoesBase;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import modelo.Classe;
import modelo.Curso;
import modelo.Disciplina;
import modelo.MesAno;
import modelo.Professor;
import modelo.ProfessorTurma;
import modelo.Turma;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class AdicionarprofessorTurmaController implements Initializable {
   
    @FXML
    private Label lbl_turma;
    @FXML
    private Label lbl_curso;
    @FXML
    private Label lbl_classe;
    @FXML
    private ListView<String> listaprofessores;
    @FXML
    private ListView<String> listadsciplinas;
    @FXML
    private ListView<String> lista_professor_disciplina;
    @FXML
    private Label lbl_msg_ja_associada;
    
    
    private static Turma turma;
  
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCampos();
        lbl_msg_ja_associada.setVisible(false);
    }    

    
    @FXML
    private void SelecionaProf_disciplina(MouseEvent event) {
        
        String prof = listaprofessores.getSelectionModel().getSelectedItem();
        String disc = listadsciplinas.getSelectionModel().getSelectedItem();
        
        if( prof != null && disc != null )
        {
                if( !ProfessorTurma.DisciplinaJaAssociada(turma.getCodigo(), Disciplina.NameToCode(disc, turma.getNome_curso(), turma.getClasse()), MesAno.Get_AnoActualCobranca()))
                {   
                    //Esconde a mensagem
                    lbl_msg_ja_associada.setVisible(false);
                    
                    if( !lista_professor_disciplina.getItems().contains(prof+"-->"+disc))
                        if( lista_professor_disciplina.getItems().size() == 0 )
                                lista_professor_disciplina.getItems().add(prof+"-->"+disc);
                    else
                            if( !JaAdicionado(lista_professor_disciplina.getItems(), disc))
                                 lista_professor_disciplina.getItems().add(prof+"-->"+disc);
               }
                else
                    lbl_msg_ja_associada.setVisible(true);
        }
    }
    
    @FXML
    private void EliminaItens(MouseEvent event) {
        
        String valor= lista_professor_disciplina.getSelectionModel().getSelectedItem();
        if( valor != null )
            lista_professor_disciplina.getItems().remove(valor);
        
    }

    @FXML
    private void Adicionar(ActionEvent event) {
       
        if(Preencher())
        {
            Alert a= new Alert(Alert.AlertType.INFORMATION, "Professores Adicionados a turma com sucesso");
            a.show();
            Limpar();
            RemoveProfessores_e_DisciplinasJaCadastradas();
            ImprimiMensagem_SemDados();
        }
    }
   
    
    public static void setTurma(Turma turma) {
        AdicionarprofessorTurmaController.turma = turma;
    }
    
    
    private void InicializaCampos()
    {
        lbl_turma.setText(turma.getNome_turma());
        lbl_curso.setText(turma.getNome_curso());
        lbl_classe.setText(turma.getClasse());
        
        //inicializa_Listaprofessores
        listaprofessores.setItems(Professor.CursoClasseToListaProfessores(Curso.NameToCode(turma.getNome_curso()), Classe.NameToCode(turma.getClasse())));
        
        //listadisciplina
        listadsciplinas.setItems(Disciplina.ListaDisciplinasCurso_Classe(turma.getNome_curso(), turma.getClasse()));
        RemoveProfessores_e_DisciplinasJaCadastradas();
        ImprimiMensagem_SemDados();
       
    }

    private boolean Preencher()
    {
        ObservableList<String> listaGeral = lista_professor_disciplina.getItems();
        boolean controla = false;
        for( String valor : listaGeral )
        {
            String array[] = valor.split("-->");
            String professor = array[0];
            String disciplina = array[1];
            
            ProfessorTurma pt = new ProfessorTurma();
            pt.setCoddisciplina(Disciplina.NameToCode(disciplina, turma.getNome_curso(), turma.getClasse()));
            pt.setCodprofessor(Professor.NameToCode(professor));
            pt.setAnolectivo(MesAno.Get_AnoActualCobranca());
            
            String sql= "insert into turma_professor values('"+turma.getCodigo()+"', '"+pt.getCodprofessor()+"', '"+pt.getAnolectivo()+"', '"+pt.getCoddisciplina()+"')";
            if( professor != null && disciplina != null )
            {
               if( ProfessorTurma.DisciplinaJaAssociada(turma.getCodigo(), pt.getCoddisciplina(),pt.getAnolectivo()))
               {
                   Alert a = new Alert(Alert.AlertType.WARNING, "Disciplina ja Associada a um professor");
                   a.show();
               }
               else
               controla =  OperacoesBase.Inserir(sql);
            }
            
        }
         
        return controla;
    }

   //Verifiva se o professor e a sua respectiva disciplina ja foram clicados na janela
   private boolean JaAdicionado( ObservableList<String> valorList, String disc )
   {
       String discpString = "";
       boolean controla = false;
        for( String vaString : valorList )
        {
            String[] disString = vaString.split("-->");
            discpString = disString[1];
            if( discpString.equalsIgnoreCase(disc) )
            {
                controla = true;
                break;
            }
            
        }
        return controla;
   }
  
   private void Limpar()
   {
       listadsciplinas.getSelectionModel().select(null);
       listaprofessores.getSelectionModel().select(null);
       lista_professor_disciplina.getItems().clear();
   }
  
   private void RemoveProfessores_e_DisciplinasJaCadastradas()
   {
        //remove os professores ja associados.
        listaprofessores.getItems().removeAll(ProfessorTurma.Lista_ProfessoresJaAssociada(turma.getCodigo(), "2019"));
        listadsciplinas.getItems().removeAll(ProfessorTurma.Lista_Disciplinas_JaAssociada(turma.getCodigo(), "2019"));
   }
   
   private void ImprimiMensagem_SemDados()
   {
        lbl_msg_ja_associada.setText((listadsciplinas.getItems().isEmpty() && listaprofessores.getItems().isEmpty())?"Não existem professores nem disciplinas para associar nesta turma.":"");
        lbl_msg_ja_associada.setVisible((listadsciplinas.getItems().isEmpty() && listaprofessores.getItems().isEmpty()));
   }
   
}
