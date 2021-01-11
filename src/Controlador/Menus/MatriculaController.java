/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Menus;

import Controlador.Estudante.RegistroMatriculadoController;
import Controlador.Estudante.VisualizarEstudanteMatriculadoController;
import Validacoes.Valida_UsuarioActivo;
import definicoes.DefinicoesPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class MatriculaController implements Initializable {
    
    @FXML
    private Pane Principal;
    @FXML
    private Label lbl_visualizar_maticula;
    @FXML
    private Label lbl_confirmar;
    @FXML
    private Label lbl_registro_matriculas;
    @FXML
    private Label lbl_visualizar_confirmados;
    
    private static Label l_visualizar;
    private static Label l_confirmar;
    private static Label l_registro;
    private static Label l_confirmados;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        l_confirmar = lbl_confirmar;
        l_registro = lbl_registro_matriculas;
        l_visualizar = lbl_visualizar_maticula;
        l_confirmados = lbl_visualizar_confirmados;
         if( Usuario.CodeToTipo(Usuario.NameToCode(Usuario.getUsuario_activo())).equals("Secretário(a)") || Usuario.EAdmin(Usuario.NameToCode(Usuario.getUsuario_activo())))
             Valida_UsuarioActivo.Pedagogico_Matricula(false);
         else
             Valida_UsuarioActivo.Pedagogico_Matricula(true);
    }    

    @FXML
    private void VisualizarMatriculados(MouseEvent event) 
    {
        DefinicoesPane d = new DefinicoesPane("/vista/visualizarEstudanteMatriculado.fxml",Principal);
        VisualizarEstudanteMatriculadoController.setPane(Principal);
        d.AddPane();
       
    }

    @FXML
    private void ConfirmaMatricula(MouseEvent event) 
    {
        DefinicoesPane d = new DefinicoesPane("/vista/ActualizacaoAnualEstudante.fxml",Principal);
        
        d.AddPane();
        
    }

    @FXML
    private void RegistroMatricula(MouseEvent event)
    {
        DefinicoesPane d = new DefinicoesPane("/vista/RegistroMatricula.fxml",Principal);
        RegistroMatriculadoController.setPane(Principal);
        d.AddPane();
    }
    
    @FXML
    private void VisualizarConfirmados(MouseEvent event) 
    {
        DefinicoesPane d = new DefinicoesPane("/vista/visualizar_confirmados.fxml",Principal);
        VisualizarEstudanteMatriculadoController.setPane(Principal);
        d.AddPane();
    }
    

    public static Label getL_confirmar() {
        return l_confirmar;
    }

    public static Label getL_registro() {
        return l_registro;
    }

    public static Label getL_visualizar() {
        return l_visualizar;
    }
    
    
    
    
}
