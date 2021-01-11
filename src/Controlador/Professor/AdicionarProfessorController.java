/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Professor;

import Bd.OperacoesBase;
import Controlador.Estudante.AdicionarEstudanteController;
import Validacoes.ValidaProfessor;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Classe;
import modelo.Curso;
import modelo.Funcionario;
import modelo.MesAno;
import modelo.Professor;
import modelo.RegistroUsuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class AdicionarProfessorController implements Initializable {
   
    

    @FXML private ImageView imagem;
    @FXML private JFXListView<String> lista_curso;
    @FXML private JFXComboBox<String> classe;
   
    
    private static String nomeUser;
    private static String imagemNome ="activeUser.png";
    private static ObservableList<String> lista_disciplinas;
    private static ObservableList<String> lista2_curso;
    private static ObservableList<String> lista_classe;
    @FXML
    private JFXComboBox<String> cb_nome;
    @FXML
    private JFXTextField txt_datanascimento;
    @FXML
    private JFXTextField txt_bi;
    @FXML
    private JFXComboBox<String> cb_area_formacao;
    @FXML
    private JFXComboBox<String> cb_nivel_academico;
    @FXML
    private JFXListView<String> lista_curso_selecionado;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializarListaCurso();
        lista2_curso = FXCollections.observableArrayList();
        lista_classe = FXCollections.observableArrayList();
    }    

    @FXML
    private void AbrirChoser(ActionEvent event) 
    {
           
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleção de fotografia");
        FileChooser.ExtensionFilter extensios = new FileChooser.ExtensionFilter("Apenas imagens", "png", "jpeg", "jpg");
        chooser.getExtensionFilters().add(extensios);
        File ficheiro = chooser.showOpenDialog(null);
        
        if( ficheiro != null )
        {
             imagemNome = ficheiro.getName();
             imagem.setImage( new Image("/icones/"+imagemNome));
        }
        else
             imagem.setImage( new Image("/icones/"+imagemNome));
       
    }
    
    @FXML
    private void SelecionaNome_Inicializa_Bi_data(ActionEvent event) 
    {
        String nome = cb_nome.getSelectionModel().getSelectedItem();
        if( nome != null )
        {
            int codfuncionario = Funcionario.NametoCode(nome);
            txt_bi.setText(Funcionario.code_to_Bi(codfuncionario));
            txt_datanascimento.setText(Funcionario.code_to_DataNasc(codfuncionario));
        }
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
    private void SelecionaClasse_Inicializa_Lista_Curso_selecionado(ActionEvent event) 
    {
        String classe_var = classe.getSelectionModel().getSelectedItem();
        String curso = lista_curso.getSelectionModel().getSelectedItem();
        if( classe_var != null )
        {
            String valor = curso+" ==> "+classe_var;
            lista_curso_selecionado.getItems().add(valor);
        }
    }
    
   
    @FXML
    private void Adicionar(ActionEvent event) 
    {
        Preencher();
    }
    
    @FXML
    private void Elimina_dados_lista_curso_selecionado(MouseEvent event) 
    {
        String valor = lista_curso_selecionado.getSelectionModel().getSelectedItem();
        if( valor!= null )
          lista_curso_selecionado.getItems().remove(valor);
    }

    
    
/************************************METODOS OPERACIONAIS******************************************************************/
    private void InicializarListaCurso()
    {
        lista_curso.setItems(Curso.ListaCursos());
        ObservableList<String> lista_nomes = Professor.Lista_Professores_Nomes();
        cb_nome.setItems(lista_nomes);
        
        //Inicializa a area de Formacao e Nivel
        String area_formacao[] = {"Ciências Sociais","Ciências Educacionais","Ciências Exatas", "Engenharia"};
        String nivel[] = {"Técnico Médio","Bacharel","Licenciado", "Mestre", "Doutor"};
        cb_area_formacao.setItems(FXCollections.observableArrayList(Arrays.asList(area_formacao)));
        cb_nivel_academico.setItems(FXCollections.observableArrayList(Arrays.asList(nivel)));
        
        //Muda_Cores_Radio
       // DefinicoesCores.MudarCor_Selecao_RadioButton(femenino, masculino);
    }
    
    private void Inicializa_Classes( String curso )
    {
        classe.setItems(Classe.ClassesPorCurso(curso));
    }
    
  
    private void Preencher()
    {
        if( !ValidaProfessor.EstaoVazios(cb_nome , txt_bi , txt_datanascimento , lista_curso_selecionado))
        {
            if( !ValidaProfessor.JaExiste( cb_nome , txt_bi ))
            {
              
                Professor p = new Professor();
                p.setCodigo(Professor.RetornarUltimoCodProfessor());
                p.setCodfuncionario(Funcionario.NametoCode(cb_nome.getSelectionModel().getSelectedItem()));
                p.setNome(cb_nome.getSelectionModel().getSelectedItem());
                p.setBi_cedula(txt_bi.getText());
                p.setDatanascimento(txt_datanascimento.getText());
                p.setFoto(imagemNome);
                p.setStatus("Sem Status");
                p.setTipo_professor("Sem tipo");
                
                if( p.Adicionar() )
                {
                     AddDialogo("/dialogos/professorYes.fxml");
                     RegistroUsuario.AddRegistro("Inseriu o professor: "+p.getNome());
                     Inserir_Professor_Curso_Classe(p.getCodigo(), lista_curso_selecionado.getItems());
                     Limpar();
                }
                else
                {
                     Alert a = new Alert(AlertType.ERROR , "Erro ao adicionar o professor.");
                     a.show();
                }
            }
            else
            {
                Alert a = new Alert(AlertType.ERROR , "Esse professor ja foi cadastrado");
                a.show();
            }
        }
        else
        {
            Alert a = new Alert(AlertType.ERROR , "Existem Campos Vazios");
            a.show();
            
        }
    }
    
    private void Inserir_Professor_Curso_Classe( int codprofessor , ObservableList<String> lista )
    {
       
        String anolectivo = MesAno.Get_AnoActualCobranca();
        for( String curso_classe : lista )
        {
            String valores[]  = curso_classe.split(" ==> ");
            int codcurso =  Curso.NameToCode(valores[0]);
            int codclasse = Classe.NameToCode(valores[1]);
            
            String sql = "insert into professor_curso values('"+codprofessor+"','"+codcurso+"','"+codclasse+"','"+anolectivo+"')";
            OperacoesBase.Inserir(sql);
        }
    }
    
     
     private void Limpar()
     {
         cb_nome.getSelectionModel().select(null);
         txt_bi.clear();
         txt_datanascimento.clear();
         lista_curso.setItems(null);
         imagem.setImage( new Image("/icones/activeUser.png"));
         cb_area_formacao.getSelectionModel().clearSelection();
         cb_nivel_academico.getSelectionModel().clearSelection();
         cb_nome.getSelectionModel().clearSelection();
         classe.getSelectionModel().clearSelection();;
         lista_curso_selecionado.setItems(null);
     }

     private void AddDialogo( String path )
    {
        FXMLLoader parent = new FXMLLoader(getClass().getResource(path));
        try {
            Parent root = (Parent)parent.load();
            Stage stage = new Stage();
            stage.setScene( new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AdicionarEstudanteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setNomeUser(String nomeUser) {
        AdicionarProfessorController.nomeUser = nomeUser;
    }

    private void EliminaCurso_Classe( int posicao) 
    {
        lista2_curso.remove(posicao);
        lista_classe.remove(posicao);
    }

   
    

     
   
}
