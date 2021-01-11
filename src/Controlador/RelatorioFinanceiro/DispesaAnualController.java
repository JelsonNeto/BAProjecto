/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioFinanceiro;

import com.jfoenix.controls.JFXComboBox;
import definicoes.DefinicoesGerais;
import definicoes.DefinicoesUnidadePreco;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Lucros_Dispesas;
import modelo.MesAno;
import modelo.Meses;
import modelo.Saida;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia-Neto
 */
public class DispesaAnualController implements Initializable {

    @FXML
    private JFXComboBox<String> cb_ano;
    @FXML
    private TableView<Lucros_Dispesas> tabela;
    @FXML
    private TableColumn<Lucros_Dispesas, String> coluna_mes;
    @FXML
    private TableColumn<Lucros_Dispesas, String> coluna_dispesa;
    @FXML
    private LineChart<?, ?> grafico;
    @FXML
    private NumberAxis g_x;
    @FXML
    private CategoryAxis g_y;
    @FXML
    private Label lbl_saidas;
    @FXML
    private Label total_geral;
    
    private int totalgeral;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaAno();
    }    

    @FXML
    private void SelecionaAno_InicializaCampos(ActionEvent event) {
        
        totalgeral = 0;
        String ano = cb_ano.getSelectionModel().getSelectedItem();
        if( ano != null )
        {
            Gerar_Grafico();
            
        }
    }

    @FXML
    private void Imprimir(MouseEvent event) {
    }
    
    
 //============================================
    private void Gerar_Grafico()
    {
       CarregaTabela();
       XYChart.Series set = new XYChart.Series<>();
       for( Lucros_Dispesas l : tabela.getItems()  )
       {
           set.getData().add( new XYChart.Data<>(l.getMes(),l.getDispesa_i()));
       }
        grafico.getData().setAll(set);
    }
    
    private void InicializaAno()
    {
        cb_ano.setItems(matricula_confirmacao.ListaDosAnosMatriculados());
    }
    
     private void CarregaTabela()
    {
        SetColunas();
        tabela.setItems(Calculo_Dados());
        if( !DefinicoesGerais.TemDadosTabela(tabela) )
        {
             tabela.setDisable(true);
             Alert a = new Alert(Alert.AlertType.INFORMATION, "Sem dados para imprimir");
             a.setTitle("Sem dados");
             a.show();
        }
        else
        {
            //Calcula o total
            totalgeral += Lucros_Dispesas.TotalSaida_Int(cb_ano.getSelectionModel().getSelectedItem());
            //exibicao
            tabela.setDisable(false);
            lbl_saidas.setText(Lucros_Dispesas.TotalSaida(cb_ano.getSelectionModel().getSelectedItem()));
            total_geral.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+totalgeral));
        }
    }
     
    private void SetColunas()
    {
        coluna_mes.setCellValueFactory( new PropertyValueFactory<>("mes"));
        coluna_dispesa.setCellValueFactory( new PropertyValueFactory<>("dispesa_s"));
    }
    
     private ObservableList<Lucros_Dispesas> Calculo_Dados()
    {
        ObservableList<Lucros_Dispesas> lista = FXCollections.observableArrayList();
        String ano = cb_ano.getSelectionModel().getSelectedItem();
        for( String mes : Meses.Listar_Meses() )
        {
            Lucros_Dispesas l  = new Lucros_Dispesas();
            l.setMes(mes);
            l.setDispesa_s(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+Lucros_Dispesas.Total_Dispesas_Integer(mes, MesAno.Get_AnoActualCobranca())));
            l.setDispesa_i(Lucros_Dispesas.Total_Dispesas_Integer(mes,ano ));
            totalgeral += l.getDispesa_i();
            lista.add(l);
            
        }
        return lista;
    }
     
    
    
            
    
}
