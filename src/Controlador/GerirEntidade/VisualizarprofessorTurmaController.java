/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.GerirEntidade;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.MesAno;
import modelo.ProfessorTurma;
import modelo.Turma;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class VisualizarprofessorTurmaController implements Initializable {
    @FXML
    private TableView<ProfessorTurma> tabela;
    @FXML
    private TableColumn<ProfessorTurma, String> coluna_nomeprofessor;
    @FXML
    private TableColumn<ProfessorTurma, String> coluna_disciplina;
    @FXML
    private Label lbl_curso;
    @FXML
    private Label lbl_classe;
    @FXML
    private Label lbl_turma;
    @FXML
    private Label lbl_anolectivo;
    @FXML
    private Label lbl_total;

    
    private static Turma turma;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaLabels();
        CarregaTabela();
    }    

    public static void setTurma(Turma Turma) {
        VisualizarprofessorTurmaController.turma = Turma;
    }
    
    
/*******************METODOS OPERACIONAIS*********************************************************************/
    private void InicializaLabels()
    {
        lbl_turma.setText(turma.getNome_turma());
        lbl_classe.setText(turma.getClasse());
        lbl_curso.setText(turma.getNome_curso());
        lbl_anolectivo.setText(MesAno.Get_AnoActualCobranca());
    }
   
    private void SetColunas()
    {
        coluna_disciplina.setCellValueFactory( new PropertyValueFactory<>("disciplina"));
        coluna_nomeprofessor.setCellValueFactory( new PropertyValueFactory<>("nome_professor"));
    }
    
    private void CarregaTabela()
    {
        
       SetColunas();
       tabela.setItems(Turma.Turma_Professores(turma.getCodigo()));
       lbl_total.setText(String.valueOf(tabela.getItems().size()));
    }
}
