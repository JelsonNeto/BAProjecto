/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Notas;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Disciplina;
import modelo.Estudante;
import modelo.MesAno;
import modelo.Nota;
import modelo.Professor;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class SomatorioNotaController implements Initializable {
    
    
    private static Estudante estudante;
    private static String descricao = "Prova do Professor";
    public static String ano = MesAno.Get_AnoActualCobranca();
    public static String trimestre = "Iº";
    public static int quantidade= 0;
    
    @FXML private ComboBox<String> cb_pesquisa;
    @FXML private TableColumn<Nota, String> coluna_professor;
    @FXML private TableColumn<Nota, Integer> coluna_quantidade;
    @FXML private TableColumn<Nota, String> coluna_somaNota;
    @FXML private TableColumn<Nota, String> coluna_descricao;
    @FXML private TableColumn<Nota, String>  coluna_disciplina;
    @FXML private TableView<Nota> tabela;
    @FXML private ComboBox<String> cb_trimestre;
    @FXML private ComboBox<String> cb_ano;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        CarregaTabela();
        InicializaAno();
        
    }    

    @FXML
    private void SelectOpcao(ActionEvent event) 
    {
        
        String valor = cb_pesquisa.getSelectionModel().getSelectedItem();
        String ano_var = cb_ano.getSelectionModel().getSelectedItem();
        String trimestre_var = cb_trimestre.getSelectionModel().getSelectedItem();
        if( valor != null )
        {
            if( "Avaliação".equalsIgnoreCase(valor) )
            {
                trimestre = trimestre_var;
                ano = ano_var;
                descricao = valor;
            }
        else
            if( "Prova do Professor".equalsIgnoreCase(valor) )
            {
                 trimestre = trimestre_var;
                 ano = ano_var;
                 descricao = valor;
            }
        else
            {
                trimestre = trimestre_var;
                ano = ano_var;
                descricao = "Prova da Escola";
            }
        }
        CarregaTabela();
    }
    
     @FXML
    private void seleciona_ano_InicializaTrimestre(ActionEvent event)
    {
        String valor = cb_ano.getSelectionModel().getSelectedItem();
        if( valor != null )
        {
            InicializaTrimestre();
        }
    }
    
    
     @FXML
    private void seleciona_Trimestre_InicializaDescricao(ActionEvent event)
    {
        String valor = cb_trimestre.getSelectionModel().getSelectedItem();
        if( valor != null )
        {
            InicializaCombo();
        }
    }

   
/****************METODOS AUXILIARES*******************************************/
    private void Setcolunas()
    {
        coluna_descricao.setCellValueFactory( new PropertyValueFactory<>("descricao"));
        coluna_quantidade.setCellValueFactory( new PropertyValueFactory<>("quantidade"));
        coluna_disciplina.setCellValueFactory( new PropertyValueFactory<>("disciplina"));
        coluna_professor.setCellValueFactory( new PropertyValueFactory<>("professor"));
        coluna_somaNota.setCellValueFactory( new PropertyValueFactory<>("valor"));
    }

    public static void setEstudante(Estudante estudante) {
        SomatorioNotaController.estudante = estudante;
    }
    
    
    
    private void CarregaTabela()
    {
        
        Setcolunas();
        ObservableList<Nota> lista = FXCollections.observableArrayList();
        for( String dis : Disciplina.ListaDisciplinasCurso_Classe(estudante.getCurso(),estudante.getClasse()) )
        {
            Nota n = new Nota();
            n.setValor(retornaSoma(descricao , dis));
            n.setDisciplina(dis);
            n.setQuantidade(quantidade);
            n.setDescricao(descricao);
            n.setProfessor(Professor.DisciplinaNomeProfessor(Disciplina.NameToCode(dis)));
            
            lista.add(n);//adiciona na lista
        }
        
        tabela.setItems(lista);
        
    }
    
    
    private String retornaSoma( String descricao, String disc )
    {
        if( "Avaliação".equalsIgnoreCase(descricao) )
           return  Nota.AvaliacaoPorDisciplina(disc, ano, trimestre, estudante.getCodigo());
        else
            if( "Prova do Professor".equalsIgnoreCase(descricao) )
             return Nota.NotaProvaProfessorPorDisciplina(disc, ano, trimestre, estudante.getCodigo());
        else
            if( "Prova da Escola".equalsIgnoreCase(descricao) )
                return Nota.NotaProvaEscolaPorDisciplina(disc, ano, trimestre, quantidade);
        return null;
    }
    
    private void InicializaCombo()
    {
        
        String valor[] = {"Avaliação", "Prova do Professor", "Prova da Escola"};
        cb_pesquisa.setItems(FXCollections.observableArrayList(Arrays.asList(valor)));
    }

    private void InicializaTrimestre()
    {
        String valor[] = {"Iº", "IIº","IIIº"};
        cb_trimestre.setItems(FXCollections.observableArrayList(Arrays.asList(valor)));
    }
    
   private void InicializaAno()
   {
       String ano_var[] = {"2019","2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031"};
       cb_ano.setItems(FXCollections.observableArrayList(Arrays.asList(ano_var)));
   }
   
    
}
