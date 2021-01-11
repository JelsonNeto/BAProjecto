/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pagamento;

import com.jfoenix.controls.JFXComboBox;
import definicoes.DefinicoesUnidadePreco;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
public class ComparacaoArrecadados_dispesas_anualController implements Initializable {
    @FXML
    private JFXComboBox<String> cb_ano;
    @FXML
    private Label lbl_total_arrecadado;
    @FXML
    private Label lbl_total_dispesas;
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
        if( ano != null )
        {
            Total_Arrecadados(ano);
            TotalDispesas(ano);
            GerarGrafico(valor, valor2);
        }
    }
    
//*********
//********
    private void InicializaCampos()
    {
        cb_ano.setItems(matricula_confirmacao.ListaDosAnosMatriculados());
    }
    
    private void Total_Arrecadados( String ano )
    {
        ObservableList<PagaramAlunos> lista = Pagamento.TotalPagamentos(ano);
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
    private void TotalDispesas( String ano )
    {
        ObservableList<Pagamento_Salario> lista = Pagamento_Salario.ListarTodos_pagamentos(ano);
        int soma = 0;
        for( Pagamento_Salario p : lista )
            soma += DefinicoesUnidadePreco.StringToInteger(p.getValor());
       
        Saida.CarregaDadosTabela_por_Ano(ano);
        soma += Saida.valorTotalInteger();
        lbl_total_dispesas.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+soma));
        valor = soma;
    }
    
    private void GerarGrafico( double valor , double valor2)
    {
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        list.add(new PieChart.Data("Dispesas",valor));
        list.add(new PieChart.Data("Receitas",valor2));
        bar_chart.getData().setAll(list);
    }

    @FXML
    private void Imprimir(MouseEvent event) {
    }
    
}
