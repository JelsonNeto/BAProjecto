/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Estudante;

import Bd.OperacoesBase;
import Controlador.Pagamento.PagamentoController;
import Validacoes.validarEstudante;
import definicoes.DefinicoesAdicionarDialogo;
import definicoes.DefinicoesData;
import definicoes.DefinicoesPane;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.*;
/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class AdicionarEstudanteController implements Initializable {
   

    @FXML private ImageView imagem;
    @FXML private RadioButton masculino;
    @FXML private RadioButton femenino;
    @FXML private ComboBox<String> curso;
    @FXML private ComboBox<String> classe;
    @FXML private ComboBox<String> periodo;
    @FXML private ComboBox<String> turma;
    @FXML private ComboBox<String> cb_tipoEstudante;
    @FXML private TextField nome;
    @FXML private TextField cedula;
    @FXML private DatePicker data;
    @FXML private ToggleGroup toogle;
    
    private String foto = "activeUser.png";
    private static String nomeUser;
    private static Pane pane;
    private DefinicoesAdicionarDialogo d;
    @FXML
    private ImageView imagem1;
    
  
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        InicializaComboxs();
        d = new DefinicoesAdicionarDialogo();
    	
    }    
    
    @FXML
    public void AbrirChoser( ActionEvent event  )
    {
       
        FileChooser file = new FileChooser();
        
        FileChooser.ExtensionFilter f1 = new FileChooser.ExtensionFilter("Apenas arquivos de imagem", "png", "jpeg", "jpg");
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
         foto = "activeUser.png";
       }
       
    }
    
    @FXML
    public void SelecionaClasse_InicializaTurma( ActionEvent event )
    {
        String classe_var = classe.getSelectionModel().getSelectedItem();
        String periodo_var = periodo.getSelectionModel().getSelectedItem();
        int codcurso = Curso.NameToCode(curso.getSelectionModel().getSelectedItem());
        FiltraAturma(classe_var , codcurso, periodo_var);
    }
    
    @FXML
    public void Save( ActionEvent event )
    {
        String nome_curso = curso.getSelectionModel().getSelectedItem();
        FiltarAsClasses(nome_curso);
    }
    
    
    @FXML
    public void Adicionar( ActionEvent event  )
    {
        String foto_aluno =  foto;
        String nome_aluno  = nome.getText() ;
        String bi = cedula.getText();
        LocalDate datanas = data.getValue();
        String sexo = RetornaSexo();
        String periodo_aluno = periodo.getSelectionModel().getSelectedItem();
        String nome_curso = curso.getSelectionModel().getSelectedItem();
        String nome_turma = turma.getSelectionModel().getSelectedItem();
        String classe_aluno = classe.getSelectionModel().getSelectedItem();
        String tipo_aluno = cb_tipoEstudante.getSelectionModel().getSelectedItem();
        
        Alert a;
        if( validarEstudante.EstaoVazios( foto_aluno, nome_aluno,bi,datanas ,sexo,periodo_aluno,nome_curso,nome_turma,classe_aluno, tipo_aluno ) )
        {
           
        }
        else
        {
            if( !validarEstudante.JaExisteAluno(nome_aluno) )
            {
                if(  !validarEstudante.JaExisteBiCedula(bi))
                 {
                        Estudante e = new Estudante();
                        matricula_confirmacao c = new matricula_confirmacao();
                       
                        //Preenche os dados do estudante
                        e.setCodigo(e.RetornaUltimoCodigo());
                        e.setNome(nome_aluno);
                        e.setDatanas(datanas);
                        e.setFoto(foto_aluno);
                        e.setPeriodo(periodo_aluno);
                        e.setSexo(sexo);
                        e.setCedula_bi(bi);
                        e.setTipo(tipo_aluno);
                        e.setStatus("Activo");
                        String codigo_escola = "FCB-E"+e.getCodigo();
                        e.setCodigo_escola(codigo_escola);
                        
                        
                       boolean valor =  e.Adicionar();
                       RegistroUsuario.AddRegistro("Inserção do aluno :"+e.getNome());
                       if( valor )
                       {
                           //Preenche os dados da matricula
                           c.setCodmatricula(c.RetornaUltimoCodigo());
                           c.setCodaluno(e.getCodigo());
                           c.setCodturma(Turma.NameToCode(nome_turma));
                           c.setAnolectivo(MesAno.Get_AnoActualCobranca());
                           boolean verificaAdicionar = c.adicionar();
                           if( verificaAdicionar )
                           {
                               d.AddDialogo("/dialogos/cadastroEstudanteYes.fxml");
                               Limpar(); 
                           }
                           else
                               d.AddDialogo("/dialogos/erroEstudante.fxml");
                       }
                       else
                       {
                            d.AddDialogo("/dialogos/erroEstudante.fxml");
                            Limpar(); 
                       }
                      
                }
                else
                {
                      d.AddDialogo("/dialogos/biExistente.fxml");
                }
            }
            else
            {
                 d.AddDialogo("/dialogos/AlunoExistente.fxml");
            }
        }
    }
    
    @FXML
    private void AbrirEfectuarPagamento(MouseEvent event) 
    {
         
        String path = "/vista/pagamento.fxml";
        PagamentoController.setNome(nomeUser);
        DefinicoesPane d = new DefinicoesPane(path,pane);
        d.AddPane();
        
    }

    @FXML
    private void AbirInfo(MouseEvent event) 
    {
        d.AddDialogo("/dialogos/adicionarEstudanteAjuda.fxml");
    }
    
    
 
/********************METODOS OPERACIONAIS*******************************/
    private void InicializaComboxs()
    {
        InicializaCurso();
        InicializaPeriodo();
        InicializaCombo_TipoEstudante();
    }
    
    private void FiltarAsClasses( String curso  )
    { 
        ObservableList<String> lista = Classe.ClassesPorCurso(curso);
        classe.setItems(lista);
        
    }
    
    
    private void InicializaCurso()
    {
        ObservableList<String> lista = FXCollections.observableArrayList();
        String sql = "select nome_curso from curso";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome_curso"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdicionarEstudanteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        curso.setItems(lista);
        
    }
    
    
    private void InicializaPeriodo()
    {
        ObservableList<String> lista = FXCollections.observableArrayList();
        String periodo_array[] = {"Manhã" , "Tarde"};
        lista.addAll(Arrays.asList(periodo_array));
        
        this.periodo.setItems(lista);
        
    }
    
    private void InicializaCombo_TipoEstudante()
    {
        String valor [] = {"Normal" , "Transferido"};
        cb_tipoEstudante.setItems(FXCollections.observableArrayList(Arrays.asList(valor)));
    }
    
    private void FiltraAturma( String classe , int codcurso, String periodo )
    {
         ObservableList<String> lista = Turma.ListaTurmasRelaClasse_CodCurso_Periodo(classe , codcurso, periodo);
         turma.setItems(lista);
    }
    
   private String RetornaSexo()
   {
       String sexo = "Masculino";
       if( !masculino.isSelected())
           sexo = "Femenino";
       return sexo;
       
   }

   private void Limpar()
   {
       nome.clear();
       cedula.clear();
       masculino.setSelected(true);
       femenino.setSelected(false);
       curso.getSelectionModel().select(null);
       periodo.getSelectionModel().select(null);
       classe.getSelectionModel().select(null);
       turma.getSelectionModel().select(null);
       cb_tipoEstudante.getSelectionModel().select(null);
       data.setValue(null);
       String caminString = "/icones/"+foto;
       imagem.setImage( new Image(caminString));
   }

    public static void setPane(Pane pane) {
        AdicionarEstudanteController.pane = pane;
    }

    public static void setNomeUser(String nomeUser) {
        AdicionarEstudanteController.nomeUser = nomeUser;
    }

}
