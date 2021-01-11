/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Estudante;

import Validacoes.validarEstudante;
import definicoes.DefinicoesData;
import definicoes.DefinicoesImpressaoRelatorio;
import definicoes.DefinicoesPane;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import modelo.Classe;
import modelo.Curso;
import modelo.Estudante;
import modelo.RegistroUsuario;
import modelo.Turma;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class ActualizarEstudanteController implements Initializable {
    @FXML
    private TextField nome;
    @FXML
    private DatePicker data;
    @FXML
    private TextField cedula;
    @FXML
    private RadioButton masculino;
    @FXML
    private RadioButton femenino;
    @FXML
    private ImageView imagem;
    @FXML private ComboBox<String> cb_tipoEstudante;
    @FXML private ComboBox<String> cb_status;
    /**
     * Initializes the controller class.
     */
    
    private static String nomeUser;
    private static Estudante estudante;
    private static Pane pane;
    private String foto = "Sem Foto";
    private int codigo;
    @FXML
    private ToggleGroup toogle1;
    
    
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        SetDadosCampo();
    }    

/***********************Metodos Operacionais**********************************************/
    @FXML
    private void AbrirChoser(ActionEvent event) 
    {
          FileChooser file = new FileChooser();
          FileChooser.ExtensionFilter f1 = new FileChooser.ExtensionFilter("Apenas arquivos imagens", "png", "jpg", "jpeg");
          file.getExtensionFilters().add(f1);
          File ficheiro =  file.showOpenDialog(null);
          
          if(  ficheiro != null  )
            {

                String caminho = "/icones/"+ficheiro.getName();
                imagem.setImage( new Image(caminho));
                foto = ficheiro.getName();
            }
            else
            {
              foto = "Sem foto";
            }
    }

    @FXML
    private void actualizar(ActionEvent event) {
        
       Preencher();
    }

    public static void setEstudante(Estudante estudante) {
        ActualizarEstudanteController.estudante = estudante;
    }
    
    public void SetDadosCampo()
    {
        
        if(  estudante != null )
        {
            codigo = estudante.getCodigo();
            nome.setText(estudante.getNome());
            data.setValue(estudante.getDatanas());
            cedula.setText(estudante.getCedula_bi());
            InicializaFoto(estudante.getFoto());
            InicializaTipoAluno(estudante.getTipo());
            InicializaStatus( estudante.getStatus() );
            if( "Femenino".equalsIgnoreCase(estudante.getSexo()))
            {
                femenino.setSelected(true);
                masculino.setSelected(false);
            }
            else
            {
                femenino.setSelected(false);
                masculino.setSelected(true);
            }
 
        }
        
    }

    
/**************************Metodos Auxiliares da classe**********************************/  

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
    
    private void InicializaTipoAluno( String tipo )
    {
        cb_tipoEstudante.setItems(FXCollections.observableArrayList( Arrays.asList( new String[]{"Normal", "Transferido"} )));
        cb_tipoEstudante.getSelectionModel().select(tipo);
    }
    
    private void Limpar()
    {
       nome.clear();
       cedula.clear();
       masculino.setSelected(true);
       femenino.setSelected(false);
       cb_status.getSelectionModel().select(null);
       data.setValue(null);
       ResetFoto();
    }
    
   private String RetornaSexo()
   {
       String sexo = "Masculino";
       if( !masculino.isSelected())
           sexo = "Femenino";
       return sexo;
       
   }
   
    private void Preencher()
    {
        String foto_aluno =  foto;
        String nome_aluno  = nome.getText() ;
        String bi = cedula.getText();
        LocalDate datanas = data.getValue();
        String sexo = RetornaSexo();
        String tipo = cb_tipoEstudante.getSelectionModel().getSelectedItem();
        String status = cb_status.getSelectionModel().getSelectedItem();
        boolean valor = false;
        
        Alert a;
        if( validarEstudante.EstaoVaziosActualizar(foto_aluno, nome_aluno,bi,datanas ,sexo,tipo , status ) )
        {
            a = new Alert(AlertType.ERROR, "Existem campos vazios");
            a.show();
        }
        else
        {
            if( !validarEstudante.JaExisteAluno_Actualizacao(nome_aluno, codigo) )
            {
                if(  !validarEstudante.JaExisteBiCedula(bi , codigo))
                 {
                        Estudante e = new Estudante();
                        e.setCodigo(codigo);
                        e.setNome(nome_aluno);
                        e.setDatanas(datanas);
                        e.setFoto(foto_aluno);
                        e.setSexo(sexo);
                        e.setCedula_bi(bi);
                        e.setCodturma(Turma.NameToCode(e.getTurma()));
                        e.setTipo(tipo);
                        e.setStatus(status);
                        
                        //verifica se o estudante mudou a turma
                      /*  if( matricula_confirmacao.GetTurma(e.getCodigo(), MesAno.Get_AnoActualCobranca())!= e.getCodturma() )
                           if( matricula_confirmacao.JaMatriculado(e.getCodigo(), MesAno.Get_AnoActualCobranca()))
                           {
                               Alert a = new 
                           }*/
                        valor =  e.Actualizar();
                       RegistroUsuario.AddRegistro("Actualização do aluno :"+e.getNome());
                       if( valor )
                        { 
                            a = new Alert(AlertType.INFORMATION,"Actualização Efectuada com sucesso");
                            a.show();
                            Limpar(); 
                            BackToViewStudent();
                        }
                       else
                       {
                            a = new Alert(AlertType.ERROR,"Erro ao actualizar");
                            a.show();
                           
                       }
                }
                else
                {
                     a = new Alert(AlertType.ERROR,"Este numero de Bi ou cedula ja existe");
                     a.show();
                           
                }
            }
            else
            {
                 a = new Alert(AlertType.INFORMATION,"Este Aluno ja Existe");
                 a.show();
                           
            }
        }
    }
    
    private void ResetFoto()
    {
        imagem.setImage( new Image( "/icones/activeUser.png"));
    }

    public static void setPane(Pane pane) {
        ActualizarEstudanteController.pane = pane;
    }

    public static void setNomeUser(String nomeUser) {
        ActualizarEstudanteController.nomeUser = nomeUser;
    }
    
    
    
    
    public void BacktoCaller()
    {
          
        try {
            Parent p = FXMLLoader.load( getClass().getResource("/vista/visualizarEstudante.fxml") );
            pane.getChildren().removeAll();
            pane.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(ActualizarEstudanteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
  
     private void InicializaStatus( String valor_var)
     {
         String valor[] = {"Activo", "Desistente", "Finalista"};
         cb_status.setItems(FXCollections.observableArrayList(Arrays.asList(valor)));
         cb_status.getSelectionModel().select(valor_var);
     }

     private void BackToViewStudent()
     {
         String path = "/vista/VisualizarEstudantesCadastrados.fxml";
         DefinicoesPane d = new DefinicoesPane( path, pane);
         d.AddPane();
     }

}
