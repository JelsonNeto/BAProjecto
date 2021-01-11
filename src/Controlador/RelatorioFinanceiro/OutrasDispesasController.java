/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioFinanceiro;

import com.jfoenix.controls.JFXComboBox;
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
import modelo.Saida;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class OutrasDispesasController implements Initializable {

    @FXML
    private JFXComboBox<String> cb_anolectivo;
    @FXML
    private TableView<Saida> tabela;
    @FXML
    private TableColumn<Saida, String> coluna_descricao;
    @FXML
    private TableColumn<Saida, String> coluna_valor;
    @FXML
    private TableColumn<Saida, String> coluna_data;
    @FXML
    private TableColumn<Saida, String> coluna_encarregado;
    @FXML
    private Label txt_total;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Anos_Disponiveis();
    }    

    @FXML
    private void Seleciona_Ano_inicializaCampos(ActionEvent event) 
    {
        String ano = cb_anolectivo.getSelectionModel().getSelectedItem();
        if( ano != null )
           CarregaTabela(ano);
    }
    
    
    
/****************METODOS PRIVADOS*************************************/
    private void Anos_Disponiveis()
    {
        cb_anolectivo.setItems(matricula_confirmacao.ListaDosAnosMatriculados());
        
    }
    
    private void SetColunas()
    {
        coluna_data.setCellValueFactory( new PropertyValueFactory<>("data_saida"));
        coluna_descricao.setCellValueFactory( new PropertyValueFactory<>("efeito"));
        coluna_valor.setCellValueFactory( new PropertyValueFactory<>("valor"));
        coluna_encarregado.setCellValueFactory( new PropertyValueFactory<>("nome_funcionario"));
    }
    
    private void CarregaTabela( String ano )
    {
        SetColunas();
        tabela.setItems(Saida.CarregaDadosTabela_por_Ano(ano));
        txt_total.setText(Saida.valorTotal());
    }

    @FXML
    private void Imprimir(MouseEvent event) {
    }
}
