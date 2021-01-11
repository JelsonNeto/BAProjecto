/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Estudante;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Estudante;
import modelo.MesAno;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class EmitirComunicadosAlunoController implements Initializable {
    
    @FXML private ImageView imagem;
    @FXML private Label lbl_nome;
    @FXML private Label lbl_curso;
    @FXML private Label lbl_classe;
    @FXML private Label lbl_turma;
    @FXML private Label lbl_periodo;
    @FXML private Label lbl_ano;
    @FXML private ComboBox<String> cb_assunto;
    @FXML private Label lbl_encarregado;
    
    private static Estudante estudante;
    private static Pane pane;
    private static String nomeUser;
    private String foto;
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CarregarFoto();
        PreencherCampos();
    }    

    @FXML
    private void emitir(ActionEvent event) {
    }
    
     @FXML
    private void Back(MouseEvent event) 
    {
        PerfilAlunoController.setEstudante(estudante);
        PerfilAlunoController.setPane(pane);
        PerfilAlunoController.setNomeUser(nomeUser);
        AddPane("/vista/perfilAluno.fxml");
    }
    
    private void CarregarFoto()
    {
        if( estudante != null )
        {
            if( !"Sem Foto".equalsIgnoreCase(estudante.getFoto()))
           {
             String caminho = "/icones/"+estudante.getFoto();
             imagem.setImage( new Image(caminho));
           }
          else
             imagem.setImage( new Image("/icones/activeUser.png"));
        }
    }

    private void PreencherCampos()
    {
        lbl_nome.setText(estudante.getNome());
        lbl_classe.setText(estudante.getClasse());
        lbl_curso.setText(estudante.getCurso());
        //lbl_encarregado.setText(estudante.get);
        lbl_periodo.setText(estudante.getPeriodo());
        lbl_turma.setText(estudante.getTurma());
        lbl_ano.setText(MesAno.Get_AnoActualCobranca());
        
        String valor[] = {"Comunicado", "Convocatória"};
        cb_assunto.setItems( FXCollections.observableArrayList(Arrays.asList(valor)));
        
    }
    
    
    public static void setEstudante(Estudante estudante) {
         EmitirComunicadosAlunoController.estudante = estudante;
    }

    public static void setPane(Pane pane) {
        EmitirComunicadosAlunoController.pane = pane;
    }

    public static void setNomeUser(String nomeUser) {
        EmitirComunicadosAlunoController.nomeUser = nomeUser;
    }

    
    
   
            
      public void AddPane( String path )
    {
             pane.getChildren().removeAll();
        try {
            Parent p = FXMLLoader.load(getClass().getResource(path) );
            pane.getChildren().add(p);
        } catch (IOException ex) {
            Logger.getLogger(EmitirComunicadosAlunoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
