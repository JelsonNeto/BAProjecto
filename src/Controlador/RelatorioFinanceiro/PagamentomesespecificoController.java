/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioFinanceiro;

import Bd.OperacoesBase;
import definicoes.DefinicoesData;
import definicoes.DefinicoesImpressaoRelatorio;
import definicoes.DefinicoesUnidadePreco;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Classe;
import modelo.Curso;
import modelo.MesAno;
import modelo.Pagamento;
import modelo.PagaramAlunos;
import modelo.Turma;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class PagamentomesespecificoController implements Initializable {
    
    @FXML private TableColumn<PagaramAlunos, String> coluna_aluno;
    @FXML private TableColumn<PagaramAlunos, String> coluna_classe;
    @FXML private TableColumn<PagaramAlunos, String> coluna_turma;
    @FXML private TableColumn<PagaramAlunos, String> coluna_curso;
    @FXML private TableColumn<PagaramAlunos, String>coluna_data;
    @FXML private TableColumn<PagaramAlunos, String> coluna_valor;
    @FXML private TableColumn<PagaramAlunos, String>  coluna_efeito;
    @FXML private TableView<PagaramAlunos> tabela;
    @FXML private ComboBox<String> cb_curso;
    @FXML private ComboBox<String> cb_classe;
    @FXML private ComboBox<String> cb_turma;
    @FXML private ComboBox<String> cb_mes;
    @FXML private Label txt_total;
    
    private String ano;
   
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCurso();
    }    
    @FXML
    private void SelecionaCurso_InicializaClassePagaram(ActionEvent event) 
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        if( curso != null )
        {
            cb_classe.setItems(Classe.ClassesPorCurso(curso));
        }
    }
    @FXML
    private void SelecionaClasse_InicializaTurmaPagaram(ActionEvent event) 
    {
         String classe = cb_classe.getSelectionModel().getSelectedItem();
        if( classe != null )
        {
            cb_turma.setItems(Turma.ListaTurmasRelaClass(classe));
        }
    }
    @FXML
    private void selecionaTurma_InicializaMesPagaram(ActionEvent event) 
    {
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        if( classe != null  )
        {
            String turma = cb_turma.getSelectionModel().getSelectedItem();
            if( turma != null )
            {
                InicializaMeses(classe); //inicializa os meses
            }
            else
                cb_mes.setItems(null);
        }
    }

    @FXML
    private void AlunosPagaram(ActionEvent event) 
    {
        Alert a = new Alert(AlertType.INFORMATION,"A Pesquisar os dados ,por favor aguarde...");
        a.show();
        
        String mes = cb_mes.getSelectionModel().getSelectedItem();
        String anolectivo = MesAno.Get_AnoActualCobranca();
        int codturma = Turma.NameToCode(cb_turma.getSelectionModel().getSelectedItem());
        System.out.println(codturma);
        System.out.println(mes);
        if( mes != null)
        {
            InicializaTabela(Pagamento.ListaAlunosPagaramFiltroPorTurmas(codturma, mes));
            //Faz o truncate table e inserção na tabela de relatótio
            OperacoesBase.Eliminar("delete from relatoriopagamentomensal");
            Pagamento.InserirEmRelatorioMensal(tabela.getItems());
            a.close();
        }
       
        
    }
    
    
     @FXML
    private void PreviuosDados(MouseEvent event) {
    }

    @FXML
    private void NextDados(MouseEvent event) {
    }
    
    @FXML
    private void ImprimirRelatorio(MouseEvent event) 
    {
        if( tabela.getItems().size() > 0 )
        {
            if( Imprimir() )
            {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Relatório gerado com sucesso.");
                a.show();
            }
            else
            {
                Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao gerar o Relatório");
                a.show();
            }
        }
    }
   

 /****************************METODOS OPERACIONAIS********************************************************/
     private void Setcolunas()
    {
        
        coluna_aluno.setCellValueFactory( new PropertyValueFactory<>("nome"));
        coluna_classe.setCellValueFactory( new PropertyValueFactory<>("classe"));
        coluna_curso.setCellValueFactory( new PropertyValueFactory<>("curso"));
        coluna_data.setCellValueFactory( new PropertyValueFactory<>("data"));
        coluna_turma.setCellValueFactory( new PropertyValueFactory<>("turma"));
        coluna_valor.setCellValueFactory( new PropertyValueFactory<>("valor"));
        coluna_efeito.setCellValueFactory( new PropertyValueFactory<>("efeito"));
    }
     
        private void InicializaTabela(ObservableList<PagaramAlunos> ListaAlunosPagaramAllDatas) {

        Setcolunas();
        
            System.out.println(ListaAlunosPagaramAllDatas.size());
        tabela.setItems(ListaAlunosPagaramAllDatas);
        Total();
    }

    
    private void Total()
    {
        
        int soma = 0;
        for( PagaramAlunos a : tabela.getItems() )
        {
            soma+= DefinicoesUnidadePreco.StringToInteger(a.getValor());
        }
        String somaUnidade = DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(soma));
        txt_total.setText(String.valueOf(somaUnidade));
    }
    
    private ObservableList<PagaramAlunos> FiltraAlunosData(ObservableList<PagaramAlunos> ListaAlunosPagaramAllDatas)
    {
        ano = "";
        ObservableList<PagaramAlunos> lista = FXCollections.observableArrayList();
        for( PagaramAlunos a : ListaAlunosPagaramAllDatas )
        {
            if( DefinicoesData.RetornaAnoData(String.valueOf(a.getData())).equalsIgnoreCase(MesAno.Get_AnoActualCobranca()))
            {
                lista.add(a);
                ano = DefinicoesData.RetornaAnoData(String.valueOf(a.getData()));
            }
        }
         return lista;
    }
   
   
    private boolean EClassedeExame( String classe )
    {
        return "9ª classe".equalsIgnoreCase(classe) || "6ª classe".equalsIgnoreCase(classe) || "12ª classe".equalsIgnoreCase(classe);
    }
    
      private  void InicializaMeses( String classe )
    {
        String array_Meses[]= null;
        String meses_normais[] = {"Janeiro", "Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro","Dezembro"};//meses normais
        String meses_exame[] = { "Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro","Dezembro"};//meses normais
        
        if( EClassedeExame(classe))
            array_Meses = meses_exame;
        else
            if( !EClassedeExame(classe) )
                array_Meses = meses_normais;
        
        cb_mes.setItems(FXCollections.observableArrayList(Arrays.asList(array_Meses)));
    }
   
   private void InicializaCurso()
    {
        cb_curso.setItems(Curso.ListaCursos());
    }

   
   
   private boolean Imprimir()
   {
        String path = "C:\\RelatorioGenix\\RelatorioPagamentosAlunosSemPadrao.jrxml";
        Alert a = new Alert(Alert.AlertType.INFORMATION,"A gerar o relatorio , por favor aguarde.");
        String turma = cb_turma.getSelectionModel().getSelectedItem();
        ano = MesAno.Get_AnoActualCobranca();
        String mes = cb_mes.getSelectionModel().getSelectedItem();
        a.show();
        int codturma = Turma.NameToCode(turma);
        HashMap h;
        h = new HashMap();
        h.put("mes", mes);
        h.put("descricao","Propina");
        h.put("ano", ano);
        h.put("codturma",codturma);
        h.put("total",txt_total.getText());
         
       return DefinicoesImpressaoRelatorio.ImprimirRelatorio(h, path);
   }

    

   
}
