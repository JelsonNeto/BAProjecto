/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pagamento;

import com.jfoenix.controls.JFXComboBox;
import definicoes.DefinicoesCores;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import modelo.Lucros_Dispesas;
import modelo.Meses;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class Ver_lucros_MensalController implements Initializable {

    @FXML
    private JFXComboBox<String> cb_ano;
    @FXML
    private Label lbl_total_arrecadados;
    @FXML
    private Label lbl_total_dispesas;
    @FXML
    private Label lbl_total_receitas;
    @FXML
    private JFXComboBox<String> cb_mes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCampos();
    }    

    @FXML
    private void Selecionames_InicializaAno(ActionEvent event) 
    {
        
        String mes = cb_mes.getSelectionModel().getSelectedItem();
        
        if( mes != null )
             cb_ano.setItems(matricula_confirmacao.ListaDosAnosMatriculados());        
    }
    
    
    @FXML
    private void SelecionaAno_InicializaDados(ActionEvent event) 
    {
        String mes = cb_mes.getSelectionModel().getSelectedItem();
        String ano = cb_ano.getSelectionModel().getSelectedItem();
        if( mes != null && ano != null )
        {
            lbl_total_arrecadados.setText(Lucros_Dispesas.Total_Receita(mes, ano));
            lbl_total_dispesas.setText(Lucros_Dispesas.Total_Dispesas(mes, ano));
            lbl_total_receitas.setText(Lucros_Dispesas.Total());
            int lucro = Lucros_Dispesas.getLucro();
            if( lucro >= 0 )
                DefinicoesCores.SetCorLabel("green", lbl_total_receitas);
            else
                DefinicoesCores.SetCorLabel("red", lbl_total_receitas);
        }
        
    }

    private void InicializaCampos() {
        
        cb_mes.setItems(Meses.Listar_Meses());
        
    }

    @FXML
    private void Imprimir(MouseEvent event) {
    }
    
  
   
    
}
