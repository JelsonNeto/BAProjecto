/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pagamento;

import Controlador.RelatorioFinanceiro.GSaidasController;
import com.jfoenix.controls.JFXButton;
import definicoes.DefinicoesGerais;
import definicoes.DefinicoesPane;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.MesAno;
import modelo.Modelo_privilegio;
import modelo.Pagamento_Salario;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class PagamentoFuncionarioController implements Initializable {
    @FXML
    private Pane panePreviuos;
    @FXML
    private Pane paneNext;
    @FXML
    private Label txt_total;
    @FXML
    private Label txt_totalgeral;
    @FXML
    private Label txt_filas;
    @FXML
    private TextField txt_pesquisa;
    @FXML
    private ComboBox<String> cb_pesquisa;

    @FXML
    private TableView<Pagamento_Salario> tabela;
    @FXML
    private TableColumn<Pagamento_Salario, Integer> coluna_codigo;
    @FXML
    private TableColumn<Pagamento_Salario, String> coluna_nome;
    @FXML
    private TableColumn<Pagamento_Salario, String> coluna_funcao;
    @FXML
    private TableColumn<Pagamento_Salario, String> coluna_mes;
    @FXML
    private TableColumn<Pagamento_Salario, String> coluna_valor;
    @FXML
    private TableColumn<Pagamento_Salario, LocalDate> coluna_datapagamento;
    @FXML
    private Pane panel_lateral;
    
    private DefinicoesPane dp;
    private static Pane pane;
    private boolean activo;
    @FXML
    private JFXButton btn_regisrra;
    @FXML
    private JFXButton btn_impressao;
    
   String sql_geral = "select * from view_pagamento_funcionario inner join salario_funcionario using(codsalario) where ano_lectivo = '"+MesAno.Get_AnoActualCobranca()+"'";
            
    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        dp = new DefinicoesPane(pane);
        tabela.setDisable(true);
        InicializaCombos();
    }    

    @FXML
    private void PreviuosDados(MouseEvent event) {
    }

    @FXML
    private void NextDados(MouseEvent event) {
    }

    @FXML
    private void Pesquisar(MouseEvent event) {
        
        String opcao = cb_pesquisa.getSelectionModel().getSelectedItem();
        if( opcao != null )
        {
           String sql = "select * from view_pagamento_funcionario inner join salario_funcionario using(codsalario)  where ano_lectivo = '"+MesAno.Get_AnoActualCobranca()+"'";
            switch (opcao) {
                case "Nome":
                    sql = "select * from view_pagamento_funcionario inner join salario_funcionario using(codsalario)  where nome like '"+txt_pesquisa.getText()+"%' and ano_lectivo = '"+MesAno.Get_AnoActualCobranca()+"'";
                    break;
                case "Código funcionário":
                    sql = "select * from view_pagamento_funcionario inner join salario_funcionario using(codsalario) where codigo_escola_f = '"+txt_pesquisa.getText()+"' and ano_lectivo = '"+MesAno.Get_AnoActualCobranca()+"'";
                    break;
                default:
                    break;
            }
            
            Carregar_Tabela("Parametro",sql);
        }
            
    }

    @FXML
    private void registrar_pagamento(ActionEvent event) 
    {
        dp.CallOtherWindow("registrarPagamento", null);
    }
    
    @FXML
    private void Load_tabela(ActionEvent event) 
    {
        Carregar_Tabela("",sql_geral);
        if( DefinicoesGerais.TemDadosTabela(tabela) )
            tabela.setDisable(false);
        else
        {
            tabela.setDisable(true);
            Alert a = new Alert(Alert.AlertType.INFORMATION,"Sem pagamentos registrados");
            a.setTitle("Pagamentos");
            a.show();
        }
    }
    
    @FXML
    private void VerRegistros(ActionEvent event) 
    {
        Pagamento_Salario ps = tabela.getSelectionModel().getSelectedItem();
        if( ps != null )
        {
             Vermais_salarioController.setPs(ps);
             Vermais_salarioController.setPane(pane);
             dp.setPath("/vista/vermais_salario.fxml");
             dp.AddPane();
        }
        
    }

    
    @FXML
    private void imprimir_recibo(ActionEvent event) {
    }
    
    
    @FXML
    private void show_panel_lateral(MouseEvent event) 
    {
        activo = DefinicoesPane.Habilita_Desabilita_Pane_Lateral(panel_lateral, activo);
    }

    @FXML
    private void submenu_RegistrarSaidas(MouseEvent event) 
    {
        dp.setPath("/vista/GSaidas.fxml");
        dp.AddPane();
        GSaidasController.setPane(pane);
       activo =  DefinicoesPane.Desabilita_Panel_Lateral(panel_lateral, activo);
    }

    private void submenu_VisualizarSaidas(MouseEvent event) 
    {
        activo =  DefinicoesPane.Desabilita_Panel_Lateral(panel_lateral, activo);
    }

    @FXML
    private void submenu_showinfo(MouseEvent event) 
    {
        activo =  DefinicoesPane.Desabilita_Panel_Lateral(panel_lateral, activo);
    }

    
/************************************METODOS OPERACIONAIS*******************************************************/
    
    private void Carregar_Tabela( String opcao , String sql )
    {
        if( "Parametro".equalsIgnoreCase(opcao) )
        {
            Setcolunas();
            tabela.setItems(Pagamento_Salario.ListarTodos_Parametro(sql));
            txt_totalgeral.setText(""+tabela.getItems().size());
        }
        else
        {
            Setcolunas();
            tabela.setItems(Pagamento_Salario.ListarTodos());
            txt_totalgeral.setText(""+tabela.getItems().size()); 
        }
        
    }
    
    private void Setcolunas()
    {
        coluna_codigo.setCellValueFactory( new PropertyValueFactory<>("codpagamento"));
        coluna_nome.setCellValueFactory( new PropertyValueFactory<>("nomefuncionario"));
        coluna_funcao.setCellValueFactory( new PropertyValueFactory<>("funcao"));
        coluna_mes.setCellValueFactory( new PropertyValueFactory<>("mes"));
        coluna_datapagamento.setCellValueFactory( new PropertyValueFactory<>("data"));
        coluna_valor.setCellValueFactory( new PropertyValueFactory<>("valor"));
    }

    public static void setPane(Pane pane) {
        PagamentoFuncionarioController.pane = pane;
    }

    
     private void Verifica_Privilegios()
    {
        int codfuncionario = Usuario.Obter_CodFuncionario(Usuario.NameToCode(Usuario.getUsuario_activo()));
        if( Modelo_privilegio.Obter_Insercao(codfuncionario) == 0 )
             btn_regisrra.setDisable(true);
        else
            btn_regisrra.setDisable(false);
        
        if( Modelo_privilegio.Obter_Impressao(codfuncionario) == 0 )
             btn_impressao.setDisable(true);
        else
            btn_impressao.setDisable(false);
                
             
    }

    private void InicializaCombos() {
        
        cb_pesquisa.setItems(FXCollections.observableArrayList(Arrays.asList("Código funcionário","Nome")));
    }

    @FXML
    private void back(MouseEvent event) {
        
        DefinicoesPane dp = new DefinicoesPane(pane);
        dp.setPath("/vista/homePagamento_geral.fxml");
        dp.AddPane();
        
    }
   

    
}
