/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Estudante;

import definicoes.DefinicoesGerais;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Estudante;
import modelo.MesAno;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class Visualizar_confirmadosController implements Initializable {

    @FXML private TableView<Estudante> tabela;
    @FXML private TableColumn<Estudante, Integer> coluna_codigo;
    @FXML private TableColumn<Estudante, String> coluna_nome;
    @FXML private TableColumn<Estudante, String> coluna_Bi;
    @FXML private TableColumn<Estudante, LocalDate> coluna_Data;
    @FXML private TextField txt_pesquisa;
    @FXML private ComboBox<String> cb_pesquisa;
    @FXML private Label txt_total;
    @FXML private Label txt_totalgeral;
    @FXML private Pane panePreviuos;
    @FXML private Pane paneNext;
    @FXML  private Label txt_filas;
    @FXML private Label txt_anolectivo;
    @FXML private TableColumn<Estudante, String> coluna_curso;
    @FXML private TableColumn<Estudante, String> coluna_periodo;
    @FXML private TableColumn<Estudante, String> coluna_classe;
    @FXML private TableColumn<Estudante, String> coluna_turma;
    @FXML private Button btn_imprimir;
    @FXML private ImageView imagem1;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tabela.setDisable(true);
    }    

    @FXML
    private void CallVerPerifil(ActionEvent event) {
    }

    @FXML
    private void Pesquisar(MouseEvent event) {
    }

    @FXML
    private void ExportarExcel(ActionEvent event) {
    }

    @FXML
    private void CarregarTabela(ActionEvent event) {
        CarregarTabela();
    }

    @FXML
    private void PreviuosDados(MouseEvent event) {
    }

    @FXML
    private void NextDados(MouseEvent event) {
    }

    @FXML
    private void imprimir(ActionEvent event) {
    }

    @FXML
    private void AbirInfo(MouseEvent event) {
    }
    
    
    private void CarregarTabela()
    {
        ConfiguraColunas();
        tabela.setItems(matricula_confirmacao.ListaDosAlunosConfirmados_anoLectivo(MesAno.Get_AnoActualCobranca()));
        txt_total.setText(String.valueOf(tabela.getItems().size()));
        txt_totalgeral.setText(String.valueOf(tabela.getItems().size()));
        if( DefinicoesGerais.TemDadosTabela(tabela) )
            tabela.setDisable(false);
        else
        {
            tabela.setDisable(true);
            Alert a = new Alert(AlertType.INFORMATION,"Nenhuma confirmação registrada");
            a.setTitle("Confirmações");
            a.show();
        }
        
        
    }
    
    private void ConfiguraColunas()
    {
        
        coluna_Bi.setCellValueFactory( new PropertyValueFactory<>("cedula_bi"));
        coluna_Data.setCellValueFactory( new PropertyValueFactory<>("datanas"));
        coluna_codigo.setCellValueFactory( new PropertyValueFactory<>("codigo"));
        coluna_nome.setCellValueFactory( new PropertyValueFactory<>("nome"));
        coluna_curso.setCellValueFactory( new PropertyValueFactory<>("curso"));
        coluna_classe.setCellValueFactory( new PropertyValueFactory<>("classe"));
        coluna_turma.setCellValueFactory( new PropertyValueFactory<>("turma"));
        coluna_periodo.setCellValueFactory( new PropertyValueFactory<>("periodo"));
    }
    
    
   
    
}
