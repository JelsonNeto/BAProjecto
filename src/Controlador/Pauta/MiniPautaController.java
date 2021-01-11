/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pauta;

import Bd.conexao;
import definicoes.DefinicoesAdicionarDialogo;
import definicoes.DefinicoesPane;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Classe;
import modelo.Curso;
import modelo.Disciplina;
import modelo.Turma;
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
public class MiniPautaController implements Initializable {
  
   
    @FXML private ComboBox<String> cb_turma;
    @FXML private ComboBox<String> cb_classe;
    @FXML private ComboBox<String> cb_curso;
    @FXML private ComboBox<String> cb_disciplina;
    @FXML private ComboBox<String> cb_periodo;
    @FXML private Pane pautaPane;
    
    
    private DefinicoesAdicionarDialogo d;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Inicializa_Cursos_Ano();
        d = new DefinicoesAdicionarDialogo();
     
    }    

    @FXML
    private void SelecionaClasse_InicializaPeriodo(ActionEvent event) 
    {
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        if( classe != null )
             InicializaPeriodo();
    }

    @FXML
    private void SelecionaCurso_InicializaClasse(ActionEvent event)
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        if( curso != null )
            InicializaClasses(curso);
    }

    @FXML
    private void SelecionaPeriodo_InicializaTurma(ActionEvent event) 
    {
        String periodo = cb_periodo.getSelectionModel().getSelectedItem();
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        int codcurso = Curso.NameToCode(curso);
        if( periodo != null && curso != null && classe != null )
            Inicializa_Turma(codcurso, classe, periodo);
    }
    
    @FXML
    private void SelecionaTurma_InicializaDisciplina(ActionEvent event) 
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        String turma = cb_turma.getSelectionModel().getSelectedItem();
        if( turma != null )
        {
           
           cb_disciplina.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        }
    }
    
    @FXML
    private void SelecionaDisciplina_InicializaTabela(ActionEvent event) 
    {
         String dis = cb_disciplina.getSelectionModel().getSelectedItem();
         String turma = cb_turma.getSelectionModel().getSelectedItem();
         String curso = cb_curso.getSelectionModel().getSelectedItem();
         String classe = cb_classe.getSelectionModel().getSelectedItem();
         if( dis != null )
         {
             MinipaulaDisciplinasController.setDisciplina(dis);
             MinipaulaDisciplinasController.setTurma_var(turma);
             MinipaulaDisciplinasController.setCurso_var(curso);
             MinipaulaDisciplinasController.setClasse_var(classe);
             DefinicoesPane p = new DefinicoesPane("/vista/minipaulaDisciplinas.fxml", pautaPane);
             p.AddPane();
         }
    }
    
    @FXML
    private void AbirInfo(MouseEvent event) 
    {
        d.AddDialogo("/dialogos/MinipautaAjuda.fxml");
    }

   
/***********************METODOS AUXILIARES******************************************/
    private void Inicializa_Cursos_Ano()
    {
        cb_curso.setItems(Curso.ListaCursos());
    }
    
    private void InicializaPeriodo()
    {
         String periodo[] = {"Manhã", "Tarde"};
         cb_periodo.setItems(FXCollections.observableArrayList(Arrays.asList(periodo)));
    }
    
    
    private void InicializaClasses( String curso )
    {
        cb_classe.setItems(Classe.ClassesPorCurso(curso));
    }
    
    private void Inicializa_Turma( int codcurso , String classe , String periodo )
    {
        cb_turma.setItems(Turma.ListaTurmasRelaClasse_CodCurso_Periodo(classe, codcurso, periodo));
    }
 
    private boolean CallJasper( String path, HashMap hash )
    {
        try
        {
            conexao.Conectar();
            JasperReport report = JasperCompileManager.compileReport(path);
            JasperPrint print = JasperFillManager.fillReport(report, hash, conexao.ObterConection());
            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);
            view.toFront();
            
            return true;
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    
   
 
}
