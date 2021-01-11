/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Estudante;

import Controlador.Notas.VerNotasController;
import definicoes.DefinicoesImpressaoRelatorio;
import definicoes.DefinicoesPane;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Devedor;
import modelo.Encarregado;
import modelo.Estudante;
import modelo.MesAno;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class PerfilAlunoController implements Initializable {
    @FXML
    private ImageView imagem;
    @FXML
    private Label txt_nome;
    @FXML
    private Label txt_bi;
    @FXML
    private Label txt_datanasc;
    @FXML
    private Label txt_sexo;
    @FXML
    private Label txt_curso;
    @FXML
    private Label txt_classe;
    @FXML
    private Label txt_turma;
    @FXML
    private Label txt_periodo;
    @FXML
    private Label txt_tipoAluno;
    @FXML
    private Label txt_status;
    @FXML
    private Label txt_devedor;
    @FXML
    private Label txt_encarregado;
    @FXML
    private Label txt_codigo;
    @FXML
    private Label txt_anolectivo;
    
    
    private static Estudante estudante;
    private static Pane pane;
    private static String nomeUser;
    private static String anolectivo;
   
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        SetDados();
        
    }    

    public static void setEstudante(Estudante estudante) {
        PerfilAlunoController.estudante = estudante;
    }
  
    private void SetDados()
    {
        
        if( estudante != null )
        {
            txt_codigo.setText(String.valueOf(estudante.getCodigo()));
            txt_nome.setText(estudante.getNome());
            txt_bi.setText(estudante.getCedula_bi());
            txt_classe.setText(estudante.getClasse());
            txt_curso.setText(estudante.getCurso());
            txt_datanasc.setText(String.valueOf(estudante.getDatanas()));
            txt_periodo.setText(estudante.getPeriodo());
            txt_sexo.setText(estudante.getSexo());
            txt_turma.setText(estudante.getTurma());
            txt_tipoAluno.setText(estudante.getTipo());
            txt_encarregado.setText(Encarregado.codalunoNomeEncarregado(estudante.getCodigo()));
            txt_anolectivo.setText((anolectivo == null)?estudante.getAnolectivo():MesAno.Get_AnoActualCobranca());//O ano lectivo que vem do aluno com o metodo get é o ano actaul
            InicializaStatus( estudante.getStatus() );
            InicializaDevedor(estudante.getCodigo());
            InicializaFoto(estudante.getFoto());
        }
        
    }
    
    private void InicializaFoto( String fotografia )
    {
        if( !"Sem Foto".equalsIgnoreCase(fotografia))
        {
           String caminho = "/icones/"+fotografia;
           imagem.setImage( new Image(caminho));
        }
        else
             imagem.setImage( new Image("/icones/activeUser.png"));
    }

    @FXML
    private void VerNotas(MouseEvent event)
    {
        VerNotasController.setE(estudante);
        VerNotasController.setPane(pane);
        VerNotasController.setNomeUser(nomeUser);
        VerNotasController.setAnolectivo(anolectivo);
        AddPane("/vista/verNotas.fxml");
    }
    
    @FXML
    private void FaltasAluno(MouseEvent event) 
    {
        FaltasAlunoController.setE(estudante);
        FaltasAlunoController.setPane(pane);
        FaltasAlunoController.setNomeUser(nomeUser);
        FaltasAlunoController.setAnolectivo((anolectivo== null)?MesAno.Get_AnoActualCobranca():anolectivo);
        AddPane("/vista/FaltasAluno.fxml");
    }
    
     @FXML
    private void Back(MouseEvent event)
    {
        VisualizarEstudanteController.setPane(pane);
        VisualizarEstudanteController.setNomeUser(nomeUser);
        AddPane((anolectivo==null)?"/vista/visualizarEstudanteMatriculado.fxml":"/vista/RegistroMatricula.fxml");
    }
    
    @FXML
    private void EmitirComunicado(MouseEvent event) 
    {
        EmitirComunicadosAlunoController.setEstudante(estudante);
        EmitirComunicadosAlunoController.setPane(pane);
        EmitirComunicadosAlunoController.setNomeUser(nomeUser);
        AddPane("/vista/emitirComunicadosAluno.fxml");
    }
    
    @FXML
    private void EmitirCartao(MouseEvent event) {
       
        //Chamada do Alert
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Por favor Aguarde...");
        a.show();
        
        HashMap h = new HashMap();
        h.put("codturma", estudante.getCodturma());
        h.put("anolectivo", estudante.getAnolectivo());
        h.put("codmatricula", matricula_confirmacao.CodAlunoToCodMatricula(estudante.getCodigo()));
        String path= "C:\\RelatorioGenix\\CartaoEscolar_Individual.jrxml";
        boolean valor =DefinicoesImpressaoRelatorio.ImprimirRelatorio(h, path);
        
        if( valor )
        {
            a.setContentText("Cartão Gerado com sucesso");
            a.show();
        }
        else
        {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Erro ao tentar gerar o cartão");
            a.show();
        }
    }


 /***************************METODOS AUXILIAR***************************************/
    /***
     * 
     * @param path 
     */ 
    public void AddPane( String path )
    {
             pane.getChildren().removeAll();
        try {
            Parent p = FXMLLoader.load(getClass().getResource(path) );
            pane.getChildren().add(p);
        } catch (IOException ex) {
            Logger.getLogger(VisualizarEstudanteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setPane(Pane pane) {
        PerfilAlunoController.pane = pane;
    }

    private void InicializaStatus(String status) 
    {
        
        if( "Desistente".equalsIgnoreCase(status) )
        {
            txt_status.setText(status);
            txt_status.setStyle( "-Fx-Text-Fill: #ee2227");
        }
        else
            if( "Activo".equalsIgnoreCase(status) )
            {
                txt_status.setText(status);
                txt_status.setStyle( "-Fx-Text-Fill:#0ee464");
            }
        else
            {
                txt_status.setText(status);
                txt_status.setStyle( "-Fx-Text-Fill:green");
            }
        
    }

    private void InicializaDevedor( int codaluno )
    {
        if( Devedor.EDevedor(codaluno))
        {
            txt_devedor.setText("Sim");
            txt_devedor.setStyle( "-Fx-Text-Fill:#ee2227");
        }
        else
        {
            txt_devedor.setText("Não");
            txt_devedor.setStyle("-Fx-Text-Fill:#0ee464");
        }
    }

    public static void setNomeUser(String nomeUser) {
        PerfilAlunoController.nomeUser = nomeUser;
    }

    public static void setAnolectivo(String anolectivo) {
        PerfilAlunoController.anolectivo = anolectivo;
    }

    

    
}
