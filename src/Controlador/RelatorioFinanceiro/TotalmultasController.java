/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioFinanceiro;

import com.jfoenix.controls.JFXCheckBox;
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
import modelo.MesAno;
import modelo.Meses;
import modelo.Multas;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class TotalmultasController implements Initializable {

    @FXML
    private TableView<Multas> tabela;
    @FXML
    private TableColumn<Multas, String> coluna_curso;
    @FXML
    private TableColumn<Multas, String> coluna_turma;
    @FXML
    private TableColumn<Multas, String> coluna_classe;
    @FXML
    private TableColumn<Multas, String>  coluna_valor;
    @FXML
    private Label txt_total;
    @FXML
    private JFXComboBox<String> cb_mes;
    @FXML
    private JFXCheckBox activar;
    @FXML
    private JFXComboBox<String> cb_mes_inicial;
    @FXML
    private JFXComboBox<String> cb_mes_final;
    @FXML
    private TableColumn<Multas, String> coluna_mes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCampos();
    }    

   
    @FXML
    private void SelecionaMes_InicializaTabela(ActionEvent event) {
        
        String mes = cb_mes.getSelectionModel().getSelectedItem();
        if( mes != null )
        {
            
            CarregaTabela(mes , MesAno.Get_AnoActualCobranca());
        }
        
        
    }
    
    @FXML
    private void Activado(ActionEvent event) {
        
        if( activar.isSelected() )
        {
            cb_mes_inicial.setDisable(false);
            cb_mes_final.setDisable(false);
            cb_mes.setDisable(true);
        }
        else
        {
            cb_mes_inicial.setDisable(true);
            cb_mes_final.setDisable(true);
            cb_mes.setDisable(false);
        }
    }
    
    
    @FXML
    private void MesInicial_Inicializafinal(ActionEvent event) 
    {
        String mes = cb_mes_inicial.getSelectionModel().getSelectedItem();
        if( mes != null )
            cb_mes_final.setItems(Meses.Listar_Meses());
    }

    @FXML
    private void final_InicializaTabela(ActionEvent event) {
        
         int mes_i = Integer.parseInt(Meses.Nome_Mes_toDoisDigitos(cb_mes_inicial.getSelectionModel().getSelectedItem()));
         int mes_f = Integer.parseInt(Meses.Nome_Mes_toDoisDigitos(cb_mes_final.getSelectionModel().getSelectedItem()));
         if( mes_i > 0 && mes_f > 0  )
             CarregaTabela_Intervalo(mes_i, mes_f, MesAno.Get_AnoActualCobranca());
        
    }
    
    
    @FXML
    private void Imprimir(ActionEvent event) {
    }
    
    


    private void InicializaCampos() {
        
        cb_mes.setItems(Meses.Listar_Meses());
        cb_mes_inicial.setItems(Meses.Listar_Meses());
        
    }

    private void CarregaTabela( String mes , String ano  ) {
        
        tabela.setItems(Multas.Lista_Multas(mes, ano));
        txt_total.setText(Multas.Total());
        SetColunas();
        
    }
    
     private void CarregaTabela_Intervalo( int mes_i , int mes_f ,  String ano  ) {
        
        tabela.setItems(Multas.Lista_Multas_Intervalo(mes_i, mes_f, ano));
        txt_total.setText(Multas.Total());
        SetColunas();
    } 

    private void SetColunas() {
        
        coluna_curso.setCellValueFactory( new PropertyValueFactory<>("curso"));
        coluna_classe.setCellValueFactory( new PropertyValueFactory<>("classe"));
        coluna_turma.setCellValueFactory( new PropertyValueFactory<>("turma"));
        coluna_valor.setCellValueFactory( new PropertyValueFactory<>("valor"));
        coluna_mes.setCellValueFactory( new PropertyValueFactory<>("mes"));
    }

   

    
    
    
   
    
}
