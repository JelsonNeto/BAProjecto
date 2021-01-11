/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pagamento;

import definicoes.DefinicoesPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import modelo.Classe;
import modelo.Curso;
import modelo.Estudante;
import modelo.MesAno;
import modelo.Pagamento;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class Submenu_actualizar_tabela_devedorController implements Initializable {
    @FXML
    private AnchorPane pane;
    @FXML
    private ComboBox<String> cb_curso;
    @FXML
    private ComboBox<String> cb_classe;
    @FXML
    private Label lbl_ano;
    
    private ObservableList<matricula_confirmacao> lista_alunos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCampos();
    }    

    

  
    @FXML
    private void SelecionaCurso_InicializaClasse(ActionEvent event) 
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        if ( curso != null )
            cb_classe.setItems(Classe.ClassesPorCurso(curso));
    }

    @FXML
    private void SelecionaClasse_PreparaDados(ActionEvent event) 
    {
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        if( classe!= null && curso!= null )
        {
           lista_alunos =  matricula_confirmacao.ListaDosAlunosMatriculadosAluno_por_Curso_Classe(Curso.NameToCode(curso), Classe.NameToCode(classe), MesAno.Get_AnoActualCobranca());
        }
    }

      @FXML
    private void Actualizar(ActionEvent event) {
        
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        
        if( curso!= null && classe!= null )
        {
            Alert a  = new Alert(AlertType.INFORMATION, "Actualização em Curso, por favor aguarde...");
            a.show();
            a.close();
            Actualizar_Devedor();
        }
        else
        {
            Alert a  = new Alert(AlertType.WARNING, "Existem campos vazios...");
            a.show();
        }
        
    }
    
    @FXML
    private void Close(MouseEvent event) 
    {
        DefinicoesPane.Fechar_Janela(pane);
    }

/***********************METODOS OPERACIONAIS**************************************************/
    private void InicializaCampos() {
        
        cb_curso.setItems(Curso.ListaCursos());
        lbl_ano.setText(MesAno.Get_AnoActualCobranca());
    }
    
    
    private void Actualizar_Devedor()
    {
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        boolean valor = false; 
        for( matricula_confirmacao m : lista_alunos )
           valor =  Pagamento.Devedor_Aluno(MesAno.Get_MesActualCobranca(),Estudante.CodeToName(matricula_confirmacao.codmatricula_c_para_codaluno(m.getCodmatricula(), MesAno.Get_AnoActualCobranca())), curso, classe, m.getPeriodo());
        
        if( valor )
        {
            Alert a = new Alert(AlertType.INFORMATION,"Actualização feita com sucesso.");
            a.show();
           
        }
        else
        {
            Alert a = new Alert(AlertType.WARNING, "Sem  estudantes devedores.");
            a.show();
        }
        
    }
}
