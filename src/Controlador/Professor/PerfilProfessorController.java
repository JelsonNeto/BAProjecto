/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Professor;

import Controlador.Professor.FaltaprofessorController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Classe;
import modelo.Curso;
import modelo.Disciplina;
import modelo.MesAno;
import modelo.Professor;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class PerfilProfessorController implements Initializable {
    @FXML
    private ImageView imagem;
    @FXML
    private Label txt_nome;
     @FXML
    private Label txt_curso;
    @FXML
    private Label txt_classe;
    
    @FXML
    private Label txt_bi;
    @FXML
    private Label txt_datanasc;
    @FXML
    private Label txt_sexo;
    @FXML
    private Label txt_status;
    @FXML
    private Label txt_tipoprofessor;
    @FXML
    private ListView<String> lista_disciplinas;
    
    private static Professor professor;
    private static Pane pane;
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCampos();
    }    

    @FXML
    private void FaltasProfessor(MouseEvent event)
    {
        FaltaprofessorController.setProfessor(professor);
        AddPane("/vista/Faltaprofessor.fxml");
        
    }

    public static void setProfessor(Professor professor) {
        PerfilProfessorController.professor = professor;
    }
    
    @FXML
    private void SelecionaDisciplina_InicializaCursoClasse( MouseEvent event  )
    {
        String valor = lista_disciplinas.getSelectionModel().getSelectedItem();
        if( valor != null )
        {
            InicializaCursoClasse(valor);
        }
    }

    public static void setPane(Pane pane) {
        PerfilProfessorController.pane = pane;
    }
    
    
/************************METODOS AUXILIARES*************************************************/
    private void InicializaCampos()
    {
        txt_nome.setText(professor.getNome());
        txt_bi.setText(professor.getBi_cedula());
        txt_datanasc.setText(professor.getDatanascimento());
        txt_sexo.setText(professor.getSexo());
        txt_tipoprofessor.setText(professor.getTipo_professor());
        txt_status.setText(professor.getStatus());
        InicializaFoto(professor.getFoto());
        lista_disciplinas.setItems(Professor.codigoProfessor_to_ListaDisciplinas(professor.getCodigo(), MesAno.Get_AnoActualCobranca()));
    }
    
    private void InicializaCursoClasse( String valor )
    {
        txt_curso.setText(Curso.CodeToName(Professor.ObterCurso_CodProfesor_Disciplina(professor.getCodigo(), valor)));
        txt_classe.setText(Classe.CodeToName(Professor.ObterClasse_CodProfesor_Disciplina(professor.getCodigo(), valor)));
    }
    
    public void AddPane( String path )
    {
        try {
            Parent p = FXMLLoader.load( getClass().getResource(path) );
            pane.getChildren().removeAll();
            pane.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(PerfilProfessorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void InicializaFoto( String fotografia )
    {
        if( !"".equalsIgnoreCase(fotografia))
        {
           String caminho = "/icones/"+fotografia;
           imagem.setImage( new Image(caminho));
        }
        else
             imagem.setImage( new Image("/icones/activeUser.png"));
    }
}
