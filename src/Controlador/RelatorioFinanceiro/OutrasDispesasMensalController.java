/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioFinanceiro;

import com.jfoenix.controls.JFXComboBox;
import definicoes.DefinicoesData;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import modelo.Meses;
import modelo.Saida;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class OutrasDispesasMensalController implements Initializable {

    @FXML
    private JFXComboBox<String> cb_mes;
    @FXML
    private JFXComboBox<String> cb_anolectivo;
    @FXML
    private TableView<Saida> tabela;
    @FXML
    private TableColumn<Saida, String> coluna_dispesa;
    @FXML
    private TableColumn<Saida, String> coluna_valor;
    @FXML
    private TableColumn<Saida, String> coluna_data;
    @FXML
    private TableColumn<Saida, String> coluna_funcioario;
    @FXML
    private Label txt_total;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cb_mes.setItems(Meses.Listar_Meses());
    }    

    
    @FXML
    private void Seleciona_mes_inicializaAno(ActionEvent event)
    {
        String mes = cb_mes.getSelectionModel().getSelectedItem();
        if( mes != null )
          cb_anolectivo.setItems(matricula_confirmacao.ListaDosAnosMatriculados());
    }

    
    @FXML
    private void Seleciona_Ano_inicializaCampos(ActionEvent event) 
    {
        String mes = cb_mes.getSelectionModel().getSelectedItem();
        String ano = cb_anolectivo.getSelectionModel().getSelectedItem();
        if( mes != null && ano != null )
        {
            SetColunas();
            tabela.setItems(Saida.CarregaDadosTabela_por_Ano_Mes(Meses.Nome_Mes_toDoisDigitos(mes), ano));
            txt_total.setText(Saida.valorTotal());
        }
        
        
    }

   
    
/******************************************************************************************/
    private void SetColunas()
    {
        coluna_data.setCellValueFactory( new PropertyValueFactory<>("data_saida"));
        coluna_dispesa.setCellValueFactory( new PropertyValueFactory<>("efeito"));
        coluna_valor.setCellValueFactory( new PropertyValueFactory<>("valor"));
        coluna_funcioario.setCellValueFactory( new PropertyValueFactory<>("nome_funcionario"));
    }

    @FXML
    private void Imprimir(MouseEvent event) {
    }
}
