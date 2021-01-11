/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pagamento;

import Bd.OperacoesBase;
import com.jfoenix.controls.JFXComboBox;
import definicoes.DefinicoesGerais;
import definicoes.DefinicoesImpressaoRelatorio;
import definicoes.DefinicoesUnidadePreco;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Lucros_Dispesas;
import modelo.MesAno;
import modelo.Meses;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class Ver_Lucros_receitasController implements Initializable {
   
    @FXML
    private JFXComboBox<String> cb_ano;
    @FXML
    private LineChart<?, ?> grafico;
    @FXML
    private NumberAxis g_y;
    @FXML
    private CategoryAxis g_x;
    @FXML
    private TableView<Lucros_Dispesas> tabela;
    @FXML
    private TableColumn<Lucros_Dispesas, String> coluna_mes;
    @FXML
    private TableColumn<Lucros_Dispesas, String> coluna_receita;
    @FXML
    private TableColumn<Lucros_Dispesas, String>coluna_dispesa;
    @FXML
    private TableColumn<Lucros_Dispesas, String> coluna_lucro;
    @FXML
    private Label lbl_dis;
    @FXML
    private Label lbl_re;
    @FXML
    private Label lbl_lu;
    @FXML
    private Label lbl_p;
    
    private String totalLucro = "";
    private int rt;
    private int dt;
    private int lt;
    private int pt;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaAno();
        tabela.setDisable(true);
    }    

    @FXML
    private void SelecionaAno_InicializaCampos(ActionEvent event) 
    {
        ResetTotais();
        String ano = cb_ano.getSelectionModel().getSelectedItem();
        if( ano != null )
        {
            Gerar_Grafico();
            
        }
    }
    
//
    private void InicializaAno()
    {
        cb_ano.setItems(matricula_confirmacao.ListaDosAnosMatriculados());
    }
    
    
    private void Gerar_Grafico()
    {
       CarregaTabela();
       XYChart.Series set = new XYChart.Series<>();
       for( Lucros_Dispesas l : tabela.getItems()  )
       {
           set.getData().add( new XYChart.Data<>(l.getMes(),l.getLucro_i()));
       }
        grafico.getData().setAll(set);
    }
    
    
    private void CarregaTabela()
    {
        SetColunas();
        tabela.setItems(Calculo_Dados());
        if( !DefinicoesGerais.TemDadosTabela(tabela) )
        {
             tabela.setDisable(true);
             Alert a = new Alert(AlertType.INFORMATION, "Sem dados para imprimir");
             a.setTitle("Sem dados");
             a.show();
        }
        else
        {
            setTotais();
            tabela.setDisable(false);
        }
    }
    
    private ObservableList<Lucros_Dispesas> Calculo_Dados()
    {
        String ano = cb_ano.getSelectionModel().getSelectedItem();
        ObservableList<Lucros_Dispesas> lista = FXCollections.observableArrayList();
        int total = 0;
        for( String mes : Meses.Listar_Meses() )
        {
            Lucros_Dispesas l  = new Lucros_Dispesas();
            l.setMes(mes);
            l.setDispesa_s(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+Lucros_Dispesas.Total_Dispesas_Integer(mes, ano)));
            l.setReceita_s(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+Lucros_Dispesas.Total_Receita_Integer(mes, ano)));
            l.setLucro_s(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+Lucros_Dispesas.lucro_Integer(mes, ano)));
            l.setLucro_i(Lucros_Dispesas.lucro_Integer(mes, ano));
            l.setDispesa_i(Lucros_Dispesas.Total_Dispesas_Integer(mes, ano));
            l.setReceita_i(Lucros_Dispesas.Total_Receita_Integer(mes, ano));
            lista.add(l);
            rt += l.getReceita_i();
            dt += l.getDispesa_i();
            lt += (l.getLucro_i() > 0)?l.getLucro_i():0;
            pt += (l.getLucro_i() < 0)?Math.abs(l.getLucro_i()):0;
            total += l.getLucro_i();
            
        }
        totalLucro = DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+total);
        return lista;
    }
    
    
    private void SetColunas()
    {
        coluna_mes.setCellValueFactory( new PropertyValueFactory<>("mes"));
        coluna_dispesa.setCellValueFactory( new PropertyValueFactory<>("dispesa_s"));
        coluna_lucro.setCellValueFactory( new PropertyValueFactory<>("lucro_s"));
        coluna_receita.setCellValueFactory( new PropertyValueFactory<>("receita_s"));
    }

    @FXML
    private void Imprimir(MouseEvent event) {
        
        if( !DefinicoesGerais.TemDadosTabela(tabela) )
        {
             tabela.setDisable(true);
             Alert a = new Alert(AlertType.INFORMATION, "Sem dados para imprimir");
             a.setTitle("Sem dados");
             a.show();
        }
        else
        {
            Alert a  = new Alert(AlertType.INFORMATION, "Imprimindo o relatorio, por favor aguarde..");
            a.setTitle("Imprimindo");
            a.show();
            tabela.setDisable(false);
            boolean valor = Add_Relatorio_Lucro(tabela.getItems());
            
            if( valor )
            {
                HashMap h = new HashMap<>();
                h.put("total",totalLucro);
                //h.put("ano", MesAno.Get_AnoActualCobranca());
                String path = "C:\\RelatorioGenix\\relatorio_anual_lucro.jrxml";
               boolean v = DefinicoesImpressaoRelatorio.ImprimirRelatorio(h, path);
               if( v )
                   a.close();
               else
               {
                   Alert as  = new Alert(AlertType.INFORMATION, "Erro ao imprimir o relatorio.");
                    as.setTitle("Erro");
                    as.show();
               }
                   
            }
        }
    }

    private boolean Add_Relatorio_Lucro(ObservableList<Lucros_Dispesas> items) {
       
        boolean verifica= true;
        //Elimina os dados
        OperacoesBase.Eliminar("truncate table relatorio_lucro_anual");
        for( Lucros_Dispesas l : items )
        {
            String mes = l.getMes();
            String receita = l.getReceita_s();
            String dispesa = l.getDispesa_s();
            String lucro = l.getLucro_s();
            
           if(!AddRelatorio(mes, receita, dispesa, lucro))
               return false;
        }
         return verifica;
        
    }
    
    private boolean AddRelatorio( String mes , String receita , String dispesa , String lucro)
    {
        String sql  = "insert into relatorio_lucro_anual values('"+mes+"', '"+receita+"','"+dispesa+"','"+lucro+"')";
        return OperacoesBase.Inserir(sql);
    }

    private void ResetTotais() 
    {
        rt= 0;
        dt = 0;
        lt = 0;
        pt = 0;
    }

    private void setTotais() 
    {
        lbl_dis.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+dt));
        lbl_re.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+rt));
        lbl_lu.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+lt));
        lbl_p.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+pt));
    }
}
