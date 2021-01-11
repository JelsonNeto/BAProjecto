/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Professor;

import Bd.OperacoesBase;
import Validacoes.ValidaProfessor;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import modelo.Classe;
import modelo.Curso;
import modelo.Disciplina;
import modelo.Professor;
import modelo.RegistroUsuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class ActualizarProfessorController implements Initializable {
    @FXML
    private TextField nome;
    @FXML
    private DatePicker data;
    @FXML
    private TextField cedula;
    @FXML
    private RadioButton masculino;
    @FXML
    private ToggleGroup toogleProfessor;
    @FXML
    private RadioButton femenino;
    @FXML
    private ListView<String> lista_curso;
    @FXML
    private ComboBox<String> classe;
    @FXML
    private ListView<String> lista_disciplina;
    @FXML
    private ImageView imagem;
    @FXML
    private ListView<String> lista_disiciplinas_selected;
    @FXML
    private ComboBox<String> cb_status;
    @FXML
    private ComboBox<String> cb_tipo;

    
    private static String nomeUser;
    private static Professor professor;
    private String imageNome = "activeUser.png";
    private static ObservableList<String> lista_disciplinas;
    private static ObservableList<String> lista_disciplinasLoadedInicialmente;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lista_disciplinasLoadedInicialmente = FXCollections.observableArrayList();
        SetCampos();
        lista_disciplina.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lista_disciplinas = FXCollections.observableArrayList();
        
    }    

    @FXML
    private void SelecionaCurso_InicializaClasse(MouseEvent event) 
    {
         ObservableList<String> listaCurso = lista_curso.getSelectionModel().getSelectedItems();
        if( listaCurso.size() > 0 )
        {
            for (String curso : listaCurso) {
                Inicializa_Classes(curso);
            }
        }
    }

    @FXML
    private void SelecionaClasse_InicializaDisciplina(ActionEvent event)
    {
        ObservableList<String> listaCurso = lista_curso.getSelectionModel().getSelectedItems();
        String classe_valor = classe.getSelectionModel().getSelectedItem();
        if( classe_valor != null )
        {
            for (String curso : listaCurso) 
            {
              Inicializa_Disciplina(curso, classe_valor);
            }
        }
    }

    @FXML
    private void SelecionaDisciplina_InicializaDisciplinasSelected(MouseEvent event) 
    {
        ObservableList<String> lista = lista_disciplina.getSelectionModel().getSelectedItems();
        if (lista.size() > 0)
            lista_disciplinas.addAll(lista);
        lista_disiciplinas_selected.setItems(lista_disciplinas);
        if( lista_disiciplinas_selected.getItems().size() == lista_disciplinasLoadedInicialmente.size() )
            lista_disciplina.setDisable(true);
        
    }

    @FXML
    private void actualizar(ActionEvent event) 
    {
        //Preencher();
    }

    @FXML
    private void AbrirChoser(ActionEvent event)
    {
          
       
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(null);
        
        if( file != null )
        {
           String caminho = "/icones/"+file.getName();
           imagem.setImage( new Image(caminho));
           imageNome = file.getName();
        }
        else
           imagem.setImage(new Image("/icones/"+imageNome));
    }

    @FXML
    private void EliminarDisciplinas_Selected(MouseEvent event)
    {
        ObservableList<String> lista = lista_disiciplinas_selected.getSelectionModel().getSelectedItems();
        lista_disiciplinas_selected.getItems().removeAll(lista);
        if( lista_disiciplinas_selected.getItems().size() < lista_disciplinasLoadedInicialmente.size() )
            lista_disciplina.setDisable(false);
    }

    public static void setProfessor(Professor professor) {
        ActualizarProfessorController.professor = professor;
    }
    
    
 /*********************METODOS AUXILIARES*********************************************/
   
    
  
    
    private void SetCampos()
    {
        nome.setText(professor.getNome());
        cedula.setText(professor.getBi_cedula());
       // data.setValue(professor.getDatanascimento());
       // lista_disiciplinas_selected.setItems(Professor.codigoProfessorListaDisciplinas(professor.getCodigo()));
      //  lista_disciplinasLoadedInicialmente.addAll(Professor.codigoProfessorListaDisciplinas(professor.getCodigo()));
        lista_curso.setItems(Curso.ListaCursos());
        masculino.setSelected(true);
        femenino.setSelected(false);
        InicializaFoto(professor.getFoto());
        if( "Femenino".equalsIgnoreCase(professor.getSexo()) )
        {
            femenino.setSelected(true);
            masculino.setSelected(false);
        }
        
       //Inicializa comboBox Status e tipo de professor
        String valor_status[] = {"Activo", "Não activo"};
        String valor_tipo[] = {"Colaborador", "Efectivo"};
        cb_status.setItems(FXCollections.observableArrayList(Arrays.asList(valor_status)));
        cb_tipo.setItems(FXCollections.observableArrayList(Arrays.asList(valor_tipo)));
    }
    
    
    private void InicializaFoto( String foto )
    {
        if( !"".equalsIgnoreCase(foto))
        {
            imagem.setImage( new Image("/icones/"+foto));
        }
        else
            imagem.setImage( new Image("/icones/activeUser.png"));
            
    }
    
    private void Inicializa_Classes( String curso )
    {
        classe.setItems(Classe.ClassesPorCurso(curso));
    }
    
    private void Inicializa_Disciplina( String curso , String classe )
    {
        lista_disciplina.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
    }
    
   /*  private void Preencher()
    {
        if( !ValidaProfessor.EstaoVazios(nome , cedula , data , lista_disciplinas, cb_status, cb_tipo))
        {
            if( !ValidaProfessor.JaExiste( nome , cedula ))
            {
              
                Professor p = new Professor();
                p.setCodigo(professor.getCodigo());
                p.setNome(nome.getText());
                p.setBi_cedula(cedula.getText());
                p.setSexo(RetornaSexo());
                p.setDatanascimento(data.getValue());
                p.setFoto(imageNome);
                p.setStatus(cb_status.getSelectionModel().getSelectedItem());
                p.setTipo_professor(cb_tipo.getSelectionModel().getSelectedItem());
                
                
                if( p.Actualizar())
                {
                     Alert a = new Alert(Alert.AlertType.INFORMATION , "Professor Actualizado com sucesso.");
                     a.show();
                     ObservableList<String> lista = lista_disiciplinas_selected.getItems();
                     Update_ProfessorDisciplina(p.getCodigo() , lista );
                     RegistroUsuario.AddRegistro(nomeUser,"Actualizou os dados do professor: "+p.getNome());
                     Limpar();
                     
                }
                else
                {
                     Alert a = new Alert(Alert.AlertType.ERROR , "Erro ao actualizar o professor.");
                     a.show();
                }
            }
            else
            {
                Alert a = new Alert(Alert.AlertType.ERROR , "Esse professor ja foi cadastrado");
                a.show();
            }
        }
        else
        {
            Alert a = new Alert(Alert.AlertType.ERROR , "Existem Campos Vazios");
            a.show();
            
        }
    }*/
     
     private String RetornaSexo()
     {
        String sexo = "Femenino";
        if( masculino.isSelected() )
            sexo = "Masculino";
        
        return sexo;
      }
     
     private void Limpar()
     {
         nome.clear();
         cedula.clear();
         data.setValue(null);
         femenino.setSelected(false);
         masculino.setSelected(true);
         lista_curso.getSelectionModel().select(null);
         cb_status.getSelectionModel().select(null);
         cb_tipo.getSelectionModel().select(null);
         lista_disciplina.setItems(null);
         lista_disiciplinas_selected.setItems(null);
         imagem.setImage( new Image("/icones/activeUser.png"));
     }
     
    private void Update_ProfessorDisciplina( int codprofessor , ObservableList<String> lista )
    {
          int pos = 0;
          for( String nome_var : lista )
          {
             int coddisc = Disciplina.NameToCode(nome_var);
             String curso = Curso.DisciplinaToCurso(coddisc);
             String classe_var = Classe.DisciplinaToClasse(coddisc);
             String sql = "update professor_disciplina set coddisciplina = '"+coddisc+"', curso= '"+curso+"',classe =  '"+classe_var+"'  where codprofessor = '"+codprofessor+"' and coddisciplina = '"+Disciplina.NameToCode(lista_disciplinasLoadedInicialmente.get(pos++))+"'";
             OperacoesBase.Actualizar(sql);
           }
        
    }

    public static void setNomeUser(String nomeUser) {
        ActualizarProfessorController.nomeUser = nomeUser;
    }
    
    
    
}
