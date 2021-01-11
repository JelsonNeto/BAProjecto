/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.GerirEntidade;

import Bd.OperacoesBase;
import Validacoes.ValidarDisciplina;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import modelo.Classe;
import modelo.Curso;
import modelo.Disciplina;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class CDisciplinaController implements Initializable {
  
    private TextField nome_disciplina;
    @FXML private ComboBox<String> cb_curso;
    @FXML private ComboBox<String> cb_classe;
    @FXML private Button btn_adicionar;
    @FXML private Button btn_actualizar;
    @FXML private Button btn_eliminar;
    @FXML private Button btn_editar;
    @FXML private TableView<Disciplina> tabela;
    @FXML private TableColumn<Disciplina, String> coluna_nome;
    @FXML private TableColumn<Disciplina, String>nome_curso_coluna;
    @FXML private TableColumn<Disciplina, String> coluna_classe;
    @FXML private TextField txt_pesquisa;
    @FXML private ComboBox<String> cb_pesquisa;
    @FXML private TextArea txtarea_disicplinas;
    
    private Disciplina d;
    private static int codigo;
    
    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
         InicializaCurso();
         Inicializa_Pesquisar();
         ConfiguraTabela("select * from disciplina order by coddisciplina");
         btn_eliminar.setDisable(false);
         btn_editar.setDisable(false);
    }    

    @FXML
    private void SelecionaCurso_InicializaClasse(ActionEvent event) 
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        if( curso != null )
        {
            Inicializa_Classe(curso);
        }
        
    }

    @FXML
    private void Adicionar(ActionEvent event)
    {
        Alert a;
        boolean valor = false;
        if( Preencher() )
        {
            int codclasse = Classe.NameToCode(cb_classe.getSelectionModel().getSelectedItem());
            int codcurso = Curso.NameToCode(cb_curso.getSelectionModel().getSelectedItem());
            String disciplinas[] = txtarea_disicplinas.getText().split(",");
            if(  disciplinas.length == 0  )
            {
                a = new Alert(AlertType.WARNING,"Erro ao preencher as disciplinas ou nenhuma disciplina digitada.");
                a.show();
            }
            else
            {
                  for( String nome_disc :  disciplinas )
                  {
                      d.setCoddisciplina(d.retornaUltimoCodigo());
                      d.setNome(nome_disc.trim());
                      if( !ValidarDisciplina.VerificaExistencia(d.getNome() ,codcurso , codclasse ))
                      {
                           valor = d.adicionar();                      
                      }
                      else
                      {
                          a = new Alert(AlertType.INFORMATION,"Disciplina ja existente");
                          a.show();
                      }
                   }
                
                  if( valor )
                  {
                        a = new Alert(AlertType.INFORMATION,"Disciplinas adicionadas com Sucesso");
                        a.show();
                        ConfiguraTabela("select * from disciplina order by coddisciplina");
                        Limpar();
                  }
                  else
                  {
                      a = new Alert(AlertType.ERROR,"Erro ao cadastrar as Disciplinas");
                      a.show();
                  }
                
            }
          
       }
    }

    @FXML
    private void Eliminar(ActionEvent event)
    {
        Alert a;
        ObservableList<Disciplina> lista = tabela.getSelectionModel().getSelectedItems();
        if( lista.size() > 0 )
        {
            for( Disciplina d : lista )
            {
                String sql = "delete from disciplina where coddisciplina = '"+d.getCoddisciplina()+"'";
                if(!OperacoesBase.Eliminar(sql))
                {
                    a = new Alert(AlertType.ERROR, "Erro ao eliminar a Disciplina");
                    a.show();
                }
             }
        }
        ConfiguraTabela("select * from disciplina order by coddisciplina");
    }  

    @FXML
    private void Actualizar(ActionEvent event) 
    {
        Alert a;
        if( Preencher()  )
        {
            d.setCoddisciplina(codigo);
            d.setNome(txtarea_disicplinas.getText());
            if( d.Actualizar() )
            {
                a = new Alert(AlertType.INFORMATION,"Disciplina actualizada com sucesso.");
                a.show();
                Limpar();
            }
            else
            {
                a = new Alert(AlertType.ERROR, "Erro ao actualizar a disciplina");
                a.show();
            }
        }
        codigo = 0;
        ConfiguraTabela("select * from disciplina order by coddisciplina");
        btn_actualizar.setDisable(true);
        btn_adicionar.setDisable(false);
    }

    @FXML
    private void Editar(ActionEvent event) 
    {
        SetCampos();
        btn_adicionar.setDisable(true);
        btn_actualizar.setDisable(false);
        
    }

    @FXML
    private void Pesquisar(MouseEvent event)
    {
        selecao_Pesquisar();
    }
    
/*******************************Metodos Auxiliares****************************************************/
    private void InicializaCurso()
    {
        cb_curso.setItems(Curso.ListaCursos());
    }
    
    private void Inicializa_Classe( String curso )
    {
        cb_classe.setItems(Classe.ClassesPorCurso(curso));
    }
    
    
     private void ConfiguraTabela( String sql )
     {
         SetColunas();
        
         
       ObservableList<Disciplina> lista = FXCollections.observableArrayList(); 
       ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                d = new Disciplina();
                
                d.setCoddisciplina(rs.getInt("coddisciplina"));
                d.setNome(rs.getString("nome_disciplina"));
                d.setNomeCurso(Curso.CodeToName((rs.getInt("codcurso"))));
                d.setNomeClasse(Classe.CodeToName(rs.getInt("codclasse")));
                
                lista.add(d);
            }} catch (SQLException ex) {
            Logger.getLogger(CDisciplinaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tabela.setItems(lista);
     }
    
    private void SetColunas()
    {
        coluna_classe.setCellValueFactory( new PropertyValueFactory<>("nomeClasse"));
        coluna_nome.setCellValueFactory( new PropertyValueFactory<>("nome"));
        nome_curso_coluna.setCellValueFactory( new PropertyValueFactory<>("nomeCurso"));
        
    }
    
    private boolean Preencher()
    {
        
        d = new Disciplina(); //cria o objecto turma
        Alert a;
        if( ValidarDisciplina.ExistemCamposVazio(txtarea_disicplinas , cb_curso ,   cb_classe)) //verifica se os campos estão vazios
        {
            //Exibe uma mensagem de erro caso estejem vaxios
            a = new Alert(AlertType.ERROR,"Existem campos Vazios");
            a.show();
            return false; //retorna falso
        }
        else
        {
            //caso não estejem vazios entao os campos devem ser preenchidos
            d.setCodcurso(Curso.NameToCode(cb_curso.getSelectionModel().getSelectedItem())); //preenche o campo
            d.setCodclasse(Classe.NameToCode(cb_classe.getSelectionModel().getSelectedItem()));
         
        }
      
        return true;
    }

    private void Limpar()
    {
        
        txtarea_disicplinas.setText("");
        cb_classe.getSelectionModel().select(null);
        cb_curso.getSelectionModel().select(null);
        
    }
    
    private void Inicializa_Pesquisar()
    {
        String valor[] = {"Classe" , "Curso"};
        cb_pesquisa.setItems( FXCollections.observableArrayList(Arrays.asList(valor)));
    }
    
    
    private void selecao_Pesquisar()
    {
         String sql = "select * from disciplina order by coddisciplina";
         String valor = cb_pesquisa.getSelectionModel().getSelectedItem();
          if("Classe".equalsIgnoreCase(valor))
            sql = "select * from disciplina where codclasse = '"+Classe.NameToCode(txt_pesquisa.getText())+"' order by coddisciplina";
        else
            if("Curso".equalsIgnoreCase(valor))
                sql = "select * from disciplina where codcurso = '"+Curso.NameToCode(txt_pesquisa.getText())+"' order by coddisciplina";
        ConfiguraTabela(sql);
        
    }
    
    public void getDadosFromTable( ActionEvent event )
    {
        
        d = tabela.getSelectionModel().getSelectedItem();
     
        if( d != null)
        {
            txtarea_disicplinas.setText(String.valueOf(d.getNome()));
            cb_curso.getSelectionModel().select(Curso.CodeToName(d.getCodcurso()));
            cb_classe.getSelectionModel().select(Classe.CodeToName(d.getCodclasse()));
            
            tabela.getSelectionModel().select(null);
            
        }
        else
        {
            Alert a = new Alert(Alert.AlertType.ERROR,"Nenhum dado selecionado");
            a.show();
        }
    }
    
    private void SetCampos()
    {
        Alert a;
        d = tabela.getSelectionModel().getSelectedItem();
        if( d != null )
        {
            
           //preenchimento dos campos da janela
            txtarea_disicplinas.setText(d.getNome());
            InicializaCurso();
            cb_curso.getSelectionModel().select(d.getNomeCurso());
            cb_classe.getSelectionModel().select(d.getNomeClasse());
            codigo = d.getCoddisciplina();
        }
        else
        {
            a = new Alert(AlertType.INFORMATION,"Selecione a linha na tabela");
            a.show();
        }
        
    }

    @FXML
    private void ApenasaLetra(KeyEvent event) 
    {
         if( Character.isDigit(event.getCharacter().charAt(0)))
             btn_adicionar.setDisable(true);
         else
             btn_adicionar.setDisable(false);
        
    }
    
}
