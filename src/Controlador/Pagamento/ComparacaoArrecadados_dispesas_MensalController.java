/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pagamento;

import com.jfoenix.controls.JFXComboBox;
import definicoes.DefinicoesUnidadePreco;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import modelo.Meses;
import modelo.Pagamento;
import modelo.Pagamento_Salario;
import modelo.PagaramAlunos;
import modelo.Saida;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class ComparacaoArrecadados_dispesas_MensalController implements Initializable {

    @FXML
    private JFXComboBox<String> cb_ano;
    @FXML
    private Label lbl_total_arrecadado;
    @FXML
    private Label lbl_total_dispesas;
    @FXML
    private JFXComboBox<String> cb_mes;
    @FXML
    private PieChart bar_chart;

    
    private double valor;
    private double valor2;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCampos();
    }    

    @FXML
    private void SelecionaAno_InicializaValores(ActionEvent event) 
    {
        String ano = cb_ano.getSelectionModel().getSelectedItem();
        String mes = cb_mes.getSelectionModel().getSelectedItem();
        if( mes != null && ano != null  )
        {
            Total_Arrecadados(ano, mes);
            TotalDispesas(ano, Meses.Nome_Mes_toDoisDigitos(mes));
            GerarGrafico(valor, valor2);
        }
        
    }

    @FXML
    private void SelecionaMes_InicializaAno(ActionEvent event) 
    {
        String mes = cb_mes.getSelectionModel().getSelectedItem();
        if( mes != null )
        {
            //Inicializa_Ano
            cb_ano.setItems(matricula_confirmacao.ListaDosAnosMatriculados());
            
        }
    }
    
 //Inicializa as combos Box
    private void InicializaCampos()
    {
        cb_mes.setItems(Meses.Listar_Meses());
    }
    
    
    private void Total_Arrecadados( String ano, String mes )
    {
        ObservableList<PagaramAlunos> lista = Pagamento.TotalPagamentosMensal(ano,mes);
        int soma = 0;
        int soma_multa = 0;
        for( PagaramAlunos p : lista )
        {
            soma += DefinicoesUnidadePreco.StringToInteger(p.getValor());
            soma_multa += DefinicoesUnidadePreco.StringToInteger(p.getMulta());
        }
        
        soma += soma_multa;
        lbl_total_arrecadado.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+soma));
        valor2 = soma;
        
    }
    private void TotalDispesas( String ano, String mes )
    {
        ObservableList<Pagamento_Salario> lista = Pagamento_Salario.ListarTodos_pagamentosMensal(ano, mes);
        int soma = 0;
        for( Pagamento_Salario p : lista )
            soma += DefinicoesUnidadePreco.StringToInteger(p.getValor());
       
        Saida.CarregaDadosTabela_por_Ano_Mes(mes,ano);
        soma += Saida.valorTotalInteger();
        lbl_total_dispesas.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+soma));
        valor = soma;
    }
    
    private void GerarGrafico( double valor , double valor2)
    {
        ObservableList<Data> list = FXCollections.observableArrayList();
        list.add(new Data("Dispesas",valor));
        list.add(new Data("Receitas",valor2));
        bar_chart.getData().setAll(list);
    }

    @FXML
    private void Imprimir(MouseEvent event) {
        
      //verifica se os valores ja foram gerados
      if( !lbl_total_arrecadado.getText().equals(""))
      {
          Alert a = new Alert(AlertType.CONFIRMATION,"Deseja continuar imprimindo?");
          a.setTitle("Imprimir");
          Optional<ButtonType> op = a.showAndWait();
        
            if( ButtonType.OK == op.get() )
            {
                a.close();
                a = new Alert(AlertType.INFORMATION,"Por favor aguarde...");
                a.show();
            }
      }
      else
      {
           Alert a = new Alert(AlertType.WARNING,"Sem dados para imprimir");
          a.setTitle("Sem dados");
          a.show();
      }
        
        
        
    }
    
}
