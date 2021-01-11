/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.GerirEntidade;

import Bd.OperacoesBase;
import Validacoes.validarTurma;
import definicoes.DefinicoesPane;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Classe;
import modelo.Curso;
import modelo.Professor;
import modelo.Turma;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class CTurmaController implements Initializable {

    
    @FXML private TableColumn<Turma,Integer> numero_sala_coluna; 
    @FXML private TableColumn<Turma, String> nome_curso_coluna;
    @FXML private TableColumn<Turma,String> nomeTurma_coluna;
    @FXML private TableColumn<Turma, String> coluna_classe;
    @FXML private TableColumn<Turma,String> coluna_directorTurma;
    @FXML private TextField nome_turma;
    @FXML private ComboBox<String> curso_turma;
    @FXML private ComboBox<String> cb_directorTurma;
    @FXML private ComboBox<Integer> sala_turma;
    @FXML private TableView<Turma> tabela;
    @FXML private Button btn_actualizar;
    @FXML private Button btn_adicionar;
    @FXML private ComboBox<String> cb_classe;
    @FXML private ComboBox<String> cb_periodo;
    @FXML private TextField txt_pesquisa;
    @FXML private ComboBox<String> cb_pesquisa;
    @FXML private Button btn_addprof;
    
    private static Pane pane;
    private Turma turma;
    private static int valor;
    @FXML
    private Button btn_viewprofessor;
    
    
      
    /**
     * Initializes the controller class.
     */
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        CarregarTabela("select * from turma inner join classe using(codclasse) inner join curso using(codcurso) order by codclasse");
        LoadCombos();
        btn_actualizar.setDisable(true);
        
    }    
   
    @FXML
    public void SelecionaCurso_InicializaClasse( ActionEvent event )
    {
        LoadClasses(curso_turma.getSelectionModel().getSelectedItem());
    }
     
    @FXML
    public void SelecionaClasse_InicializaProfessor( ActionEvent event )
    {
       LoadComboProfessor();//carrega os professor que estao disponiveis para este curso e esta sala
    }
    
    
    @FXML
    public void Editar( ActionEvent event )
    {
        turma = tabela.getSelectionModel().getSelectedItem();
        tabela.getSelectionModel().clearSelection(); //Cancelar selecao
        Alert a;
        if( turma != null )
        {
             String nome = turma.getNome_turma();
             String curso = turma.getNome_curso();
             String classe = turma.getClasse();
             String professor = turma.getProfessor();
             String periodo = turma.getPeriodo();
             int codsala  = turma.getCodSala();
             int codigo = turma.getCodigo();
             
             
            //configurar os valores dos campos da janela
             nome_turma.setText( nome );
             curso_turma.getSelectionModel().select(curso);
             sala_turma.getSelectionModel().select(--codsala);
             cb_classe.getSelectionModel().select(classe);
             cb_directorTurma.getSelectionModel().select(professor);
             cb_periodo.getSelectionModel().select(periodo);
             valor = codigo;
             
             btn_actualizar.setDisable(false);
             btn_adicionar.setDisable(true);
        }
        else
        {
            a = new Alert(AlertType.ERROR , "Selecione a tabela");
            a.show();
        }
        
    }
    
    
    @FXML
    public void Actualizar( ActionEvent event )
    {
       if(Preencher())
       {
           boolean  a =  turma.Actualizar(valor);
            if( a )
            {
                 CarregarTabela("select * from turma inner join classe using(codclasse) inner join curso using(codcurso) order by codturma");
                 System.out.println(Turma.NameToCode(turma.getNome_turma()));
                 turma = null;
                 btn_adicionar.setDisable(false);
                 btn_actualizar.setDisable(true);
                 Alert d = new  Alert(AlertType.INFORMATION,"Turma actualizada com sucesso");
                 d.show();
                 Limpar();
            }
            else
            {
                Alert d = new  Alert(AlertType.ERROR,"Erro ao actualizar a turma");
                d.show();
            }
       }           
    }
    
    
    @FXML
    public void Adicionar( ActionEvent event )
    {
        Alert a;
        if( Preencher() )
        {
            int codclasse = Classe.NameToCode(cb_classe.getSelectionModel().getSelectedItem());
            if( !validarTurma.VerificaExistencia(nome_turma.getText(), codclasse) ) 
            {
                    if( turma.Adicionar() )
                    {
                        a = new Alert(AlertType.INFORMATION,"Turma cadastrada com sucesso");
                        a.show();
                        CarregarTabela("select * from turma inner join classe using(codclasse) inner join curso using(codcurso) order by codturma");
                        Limpar();
                    }
                    else
                    {
                        a = new Alert(AlertType.ERROR,"Turma cadastrada com sucesso");
                        a.show();

                    }
            }
            else
            {
                a= new Alert(AlertType.WARNING,"Esta turma ja Existe");
                a.show();
            }
          }
            
        }
        
     
    
    
    @FXML
    public void Eliminar( ActionEvent event )
    {
        ObservableList<Turma> lista = tabela.getSelectionModel().getSelectedItems();
        Alert a = new Alert(AlertType.CONFIRMATION, "Tem a certeza que deseja eliminar?");
        Optional<ButtonType> p = a.showAndWait();
        for( Turma turma_var : lista )
            if(!turma_var.Eliminar(turma_var.getCodigo()))
            {
                a = new Alert(AlertType.ERROR,"Erro ao tentar eliminar a turma");
                a.show();
            }
        CarregarTabela("select * from turma inner join classe using(codclasse) inner join curso using(codcurso) order by codturma");

    }
    
    
    @FXML
    private void Pesquisar(MouseEvent event)
    {
        CarregarTabela(RetornaSql());
    }
   
    @FXML
    private void TabelaClicada(MouseEvent event) {
        
         turma = tabela.getSelectionModel().getSelectedItem();
         if( turma != null )
         {
             btn_addprof.setDisable(false);
             btn_viewprofessor.setDisable(false);
         }
         else
         {
             btn_addprof.setDisable(true);
             btn_viewprofessor.setDisable(true);
         }
    }

    
    @FXML
    private void Addprofessores(ActionEvent event) {
        
        definicoes.DefinicoesPane d = new DefinicoesPane("/vista/adicionarprofessorTurma.fxml", pane);
        AdicionarprofessorTurmaController.setTurma(turma);
        d.AddPane();
        
    }
    
     @FXML
    private void viewProfessores(ActionEvent event) {
        
        DefinicoesPane dp  = new DefinicoesPane("/vista/visualizarprofessorTurma.fxml", pane);
        VisualizarprofessorTurmaController.setTurma(turma);
        dp.AddPane();
    }
    
/*********************************METODOS OPERACIONAIS********************************************/
    
     /**
     * Inicializa as comboBoxs com os valores vindo da base de dados..
     * Neste caso , nome dos cursos e numeros das salas
     */
    private void LoadCombos()
    {
        LoadComBoCurso(); //carrega os nomes dos cursos
        LoadComboSala(); //carrega os numeros das salas
        InicializaComboPesquisa_Periodo();//Carrega a comboBox de opcoes de pesquisa
    }
    
    
    /**
     * Inicializa a tabela com as informações ja cadastradas na base de dados
     */
    private void CarregarTabela( String sql )
    {
        
        ObservableList<Turma> lista = FXCollections.observableArrayList(); //cria uma lista observavel para adicionar os objectos turmas 
        
        //carega as colunas da tabela
        ConfiguraColunas();
        ResultSet rs = OperacoesBase.Consultar(sql); //dados a retornar na consulta
        try {
            while( rs.next() )
            {
                
                turma  = new Turma(); //cada vez que o resultSet aponta para uma nova tupla, um novo objecto turma é criado 
                
                //adicao dos dados
                turma.setCodSala(rs.getInt("codsala")); //adiciona o codigo da sala
                turma.setNome_turma(rs.getString("nome_turma")); //adiciona o nome da turma
                turma.setNome_curso(Curso.CodeToName(rs.getInt("codcurso"))); //adiciona o nome do curso
                turma.setCodigo(rs.getInt("codturma"));
                turma.setClasse(Classe.CodeToName(rs.getInt("codclasse")));
                turma.setProfessor(Professor.CodeToName(rs.getInt("codprofessor")));
                turma.setPeriodo(rs.getString("periodo"));
                lista.add(turma); //adiciona a turma com os campos preenchidos na lista
               
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        tabela.setItems(lista); //adiciiona os valores na tabela
        
    }
    
    /**
     * Metodo criado para inicializar as colunas da tabela
     */
    private void ConfiguraColunas()
    {
        //configura as colunas da tabela
        nomeTurma_coluna.setCellValueFactory( new PropertyValueFactory<>("nome_turma"));
        nome_curso_coluna.setCellValueFactory( new PropertyValueFactory<>("nome_curso"));
        numero_sala_coluna.setCellValueFactory( new PropertyValueFactory<>("codSala"));
        coluna_classe.setCellValueFactory( new PropertyValueFactory<>("classe"));
        coluna_directorTurma.setCellValueFactory( new PropertyValueFactory<>("professor"));
    }
    
    
   /**
    * Metodo utilizado para preencher os campos da janela 
    * @return True, caso os dados forem preenchidos.
    * @return false, caso os dados não estejem preenchidos.
    */
    private  boolean Preencher()
    {
        turma = new Turma(); //cria o objecto turma
        Alert a;
        if( validarTurma.ExistemCamposVazio(nome_turma , curso_turma , sala_turma,  cb_classe , cb_directorTurma, cb_periodo)) //verifica se os campos estão vazios
        {
            //Exibe uma mensagem de erro caso estejem vaxios
           a = new Alert(AlertType.ERROR,"Exisem campos Vazios");
           a.show();
           return false; //retorna falso
        }
        else
        {
            //caso não estejem vazios entao os campos devem ser preenchidos
            turma.setCodigo(turma.RetornarUltimoCodTurma());
            turma.setCodSala(sala_turma.getSelectionModel().getSelectedItem()); //preenche o campo
            turma.setCodcurso(Curso.NameToCode(curso_turma.getSelectionModel().getSelectedItem())); //preenche o campo
            turma.setNome_turma(nome_turma.getText()); //preenche o campo
            turma.setCodclasse(Classe.NameToCode(cb_classe.getSelectionModel().getSelectedItem()));
            turma.setCodprofessor(Professor.NameToCode(cb_directorTurma.getSelectionModel().getSelectedItem()));
            turma.setPeriodo(cb_periodo.getSelectionModel().getSelectedItem());
        }
        return true;  //retorna true
    }
    
    /**
     * Inicializa a comboBox curso, com os nomes dos cursos existentes na base de dados
     */
    private void LoadComBoCurso()
    {
        ObservableList<String> lista  = FXCollections.observableArrayList(); //cria umal lista em que cujo tipo de dado dos elementos sera string
        
        ResultSet rs = OperacoesBase.Consultar("select nome_curso from curso"); //consulta
        try {
            while( rs.next() )
                lista.add(rs.getString("nome_curso"));  //adiciona os nome dos cursos na lista
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        curso_turma.setItems(lista); //adiciona os valores lista na comboBox
        
    }
    
    /**
     * Inicializa a comboBox referente ao numero de salas
     */
    private void LoadComboSala()
    {
       
        ObservableList<Integer> lista  = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar("select codSala from sala order by codsala");
        
        try {
            while( rs.next() )
                lista.add(rs.getInt("codsala"));
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        sala_turma.setItems(lista);
        
    }
 
    
    private void LoadClasses( String curso )
    {   
        int codcurso = Curso.NameToCode(curso);
        ResultSet rs = OperacoesBase.Consultar("select nome_classe from classe inner join curso_classe using(codclasse) inner join curso using(codcurso) where codcurso = '"+codcurso+"'");
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
               lista.add(rs.getString("nome_classe"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CTurmaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cb_classe.setItems(lista);
        
    }
    
    private void Limpar()
    {
        nome_turma.clear();
        curso_turma.getSelectionModel().select(null);
        sala_turma.getSelectionModel().select(null);
        cb_classe.getSelectionModel().select(null);
        cb_directorTurma.getSelectionModel().select(null);
        cb_periodo.getSelectionModel().select(null);
    }

    private void LoadComboProfessor() 
    {
        String curso = curso_turma.getSelectionModel().getSelectedItem();
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        if( classe != null && curso != null )
          cb_directorTurma.setItems(Professor.CursoClasseToListaProfessores(Curso.NameToCode(curso), Classe.NameToCode(classe)));
    }

    private void InicializaComboPesquisa_Periodo()
    {
        String valor_array[] = {"Periodo", "Nome da Turma","Director de Turma","Sala", "Curso","Classe"};
        cb_pesquisa.setItems(FXCollections.observableArrayList(Arrays.asList(valor_array)));
        cb_periodo.setItems(FXCollections.observableArrayList(Arrays.asList( new String[]{"Manhã","Tarde"})));
    }
  
    private String RetornaSql()
    {
        String valor_var = cb_pesquisa.getSelectionModel().getSelectedItem();
        String sql ="select * from turma";
        if( "Periodo".equals(valor_var))
            sql = "select * from turma where periodo = '"+txt_pesquisa.getText()+"'";
        else
            if( "Nome da Turma".equalsIgnoreCase(valor_var))
                sql = "select * from turma where nome_turma = '"+txt_pesquisa.getText()+"'";
        else
                if("Director de Turma".equalsIgnoreCase(valor_var))
                    sql = "select * from turma where codprofessor = '"+Professor.NameToCode(txt_pesquisa.getText())+"'";
        else
                    if("Sala".equalsIgnoreCase(valor_var))
                        sql = "select * from turma where codsala = '"+Integer.parseInt(txt_pesquisa.getText())+"'";
        else
                        if( "Curso".equalsIgnoreCase(valor_var))
                            sql = "select * from turma where codcurso = '"+Curso.NameToCode(txt_pesquisa.getText())+"'";
        else
                            if( "Classe".equalsIgnoreCase(valor_var))
                                sql = "select * from turma where codclasse = '"+Classe.NameToCode(txt_pesquisa.getText())+"'";
        return sql;
    }

    public static void setPane(Pane pane) {
        CTurmaController.pane = pane;
    }

   

    

   
    
 
}
