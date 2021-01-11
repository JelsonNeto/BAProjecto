/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Menus;

import Controlador.GerirEntidade.CFuncionarioController;
import Controlador.GerirEntidade.CTurmaController;
import definicoes.DefinicoesCores;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class CadastrarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML
    private Pane cprincipal;
    @FXML
    private Label lbl_sala;
    @FXML
    private Label lbl_curso;
    @FXML
    private Label lbl_turma;
    @FXML
    private Label lbl_disciplina;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        JanelaInicial();
    }    
    
    
    @FXML
    public void ShowSala( MouseEvent event )
    {
        JanelaInicial();
        
    }
    
    @FXML
    public void showTurma( MouseEvent event )
    {
        DefinicoesCores.SetCorLabel("#006699",lbl_turma );
        DefinicoesCores.Underline(lbl_turma, true);
        DefinicoesCores.SetCorLabel("#000000",lbl_sala );
        DefinicoesCores.Underline(lbl_sala, false);
        DefinicoesCores.SetCorLabel("#000000",lbl_disciplina );
        DefinicoesCores.Underline(lbl_disciplina, false);
        DefinicoesCores.SetCorLabel("#000000",lbl_curso );
        DefinicoesCores.Underline(lbl_curso, false);
        CTurmaController.setPane(cprincipal);
        AddPane("/vista/cTurma.fxml");
    }
    
    @FXML
    public void showCurso( MouseEvent event )
    {
        DefinicoesCores.SetCorLabel("#006699",lbl_curso );
        DefinicoesCores.Underline(lbl_curso, true);
        DefinicoesCores.SetCorLabel("#000000",lbl_turma );
        DefinicoesCores.Underline(lbl_turma, false);
        DefinicoesCores.SetCorLabel("#000000",lbl_disciplina );
        DefinicoesCores.Underline(lbl_disciplina, false);
        DefinicoesCores.SetCorLabel("#000000",lbl_sala );
        DefinicoesCores.Underline(lbl_sala, false);
        AddPane("/vista/cCurso.fxml");
    }
    
    @FXML
    public void showDisciplina( MouseEvent event )
    {
        DefinicoesCores.SetCorLabel("#006699",lbl_disciplina );
        DefinicoesCores.Underline(lbl_disciplina, true);
        DefinicoesCores.SetCorLabel("#000000",lbl_turma );
        DefinicoesCores.Underline(lbl_turma, false);
        DefinicoesCores.SetCorLabel("#000000",lbl_sala );
        DefinicoesCores.Underline(lbl_sala, false);
        DefinicoesCores.SetCorLabel("#000000",lbl_curso );
        DefinicoesCores.Underline(lbl_curso, false);
        AddPane("/vista/CDisciplina.fxml");
    }
    
    private void AddPane( String caminho )
    {
        
        this.cprincipal.getChildren().removeAll();
        
        try {
            Parent fxml =  FXMLLoader.load(getClass().getResource(caminho));
            this.cprincipal.getChildren().add(fxml);
        } catch (IOException ex) {
            Logger.getLogger(CadastrarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void JanelaInicial()
    {
        DefinicoesCores.SetCorLabel("#006699",lbl_sala );
        DefinicoesCores.Underline(lbl_sala, true);
        DefinicoesCores.SetCorLabel("#000000",lbl_turma );
        DefinicoesCores.Underline(lbl_turma, false);
        DefinicoesCores.SetCorLabel("#000000",lbl_disciplina );
        DefinicoesCores.Underline(lbl_disciplina, false);
        DefinicoesCores.SetCorLabel("#000000",lbl_curso );
        DefinicoesCores.Underline(lbl_curso, false);
        AddPane("/vista/cSala.fxml");
    }

 
   
}
