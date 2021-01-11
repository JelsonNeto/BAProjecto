/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioFinanceiro;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.MesAno;
import modelo.Pagamento;
import modelo.PagaramAlunos;
import modelo.Turma;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class TotalpagamentomesespecificoController implements Initializable {

    
    
    @FXML private TableColumn<PagaramAlunos, String> coluna_aluno;
    @FXML private TableColumn<PagaramAlunos, String> coluna_classe;
    @FXML private TableColumn<PagaramAlunos, String> coluna_turma;
    @FXML private TableColumn<PagaramAlunos, String> coluna_curso;
    @FXML private TableColumn<PagaramAlunos, String>coluna_data;
    @FXML private TableColumn<PagaramAlunos, String> coluna_valor;
    @FXML private TableColumn<PagaramAlunos, String>  coluna_efeito;
    @FXML private TableView<PagaramAlunos> tabela;
    
    @FXML private ComboBox<String> cb_mes;
    @FXML private Label txt_total;
    
    private static String ano;
    @FXML
    private Label txt_multas;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaMes();
    }    

    @FXML
    private void AlunosPagaram(ActionEvent event) 
    {
        String mes = cb_mes.getSelectionModel().getSelectedItem();
        if( mes != null )
        {
            String anolectivo = MesAno.Get_AnoActualCobranca();
            InicializaTabela(Pagamento.ListaAlunosPagaramAllDatas(mes, anolectivo));
        }
       
        
    }
    
    @FXML
    private void Imprimir(ActionEvent event) 
    {
        String mes = cb_mes.getSelectionModel().getSelectedItem();
        if( mes != null )
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

    
    
/********************************METODOS OPERACIONAIS***************************************************/
     private void InicializaMes()
    {
         String meses_exame[] = {"Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro","Dezembro"};//meses normais
         cb_mes.setItems(FXCollections.observableArrayList(Arrays.asList(meses_exame)));
    }
    
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
        
        tabela.setItems(ListaAlunosPagaramAllDatas);
        Total();
    }
      
    private void Total()
    {
        
        int soma = 0;
        int soma_multa = 0;
        for( PagaramAlunos a : tabela.getItems() )
        {
            soma+= DefinicoesUnidadePreco.StringToInteger(a.getValor());
            soma_multa+= DefinicoesUnidadePreco.StringToInteger(a.getMulta());
        }
        soma+=soma_multa;
        String somaUnidade = DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(soma));
        txt_total.setText(somaUnidade);
        txt_multas.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(soma_multa)));
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
    
    private boolean Imprimir()
   {
        String path = "C:\\RelatorioGenix\\RelatorioPagamentosAlunos.jrxml";
        Alert a = new Alert(Alert.AlertType.INFORMATION,"A gerar o relatorio , por favor aguarde.");
        ano = MesAno.Get_AnoActualCobranca();
        String mes = cb_mes.getSelectionModel().getSelectedItem();
        String padrao = DefinicoesData.retornaPadrao(mes);
        a.show();
        HashMap h;
        h = new HashMap();
        h.put("mes", mes);
        h.put("padrao", padrao);
        h.put("ano", ano);
         
       return DefinicoesImpressaoRelatorio.ImprimirRelatorio(h, path);
   }
}
