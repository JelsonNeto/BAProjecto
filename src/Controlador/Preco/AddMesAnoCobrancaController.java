/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Preco;

import Bd.OperacoesBase;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.MesAno;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class AddMesAnoCobrancaController implements Initializable {
    @FXML
    private TextField txt_mes;
    @FXML
    private TextField txt_ano;
    @FXML
    private ComboBox<String> cb_opcao;
    @FXML
    private TableView<MesAno> tabela;
    @FXML
    private TableColumn<MesAno, String> coluna_mes;
    @FXML
    private TableColumn<MesAno, String> coluna_ano;
    @FXML
    private TableColumn<MesAno, String> coluna_dia_multa;
    
    private MesAno mesano;
   
    @FXML
    private TextField txt_dia_multa;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        Inicializar_Opcoes();
        Inicializar_Tabela();
    }    
    
    /**
     * 
     */
    private void Inicializar_Opcoes()
    {
       
        String opcoes[] = { "Mês" , "Ano", "Dia limite de Multa" };
        cb_opcao.setItems( FXCollections.observableArrayList(Arrays.asList(opcoes)) );
       
        
    }
    
    
    @FXML
    public void SelecionaOpcao_VisualizaMes_Ano( ActionEvent event )
    {
        String op_selected = cb_opcao.getSelectionModel().getSelectedItem();
        if( op_selected != null )
        {
            if( "Mês".equals(op_selected))
            {
                txt_mes.setDisable(false);
                txt_ano.setDisable(true);
                txt_dia_multa.setDisable(true);
            }
            else
                if( "Dia limite de Multa".equals(op_selected))
                {
                    txt_dia_multa.setDisable(false);
                    txt_mes.setDisable(true);
                    txt_ano.setDisable(true);
                }
                        
            else
            {
                txt_ano.setDisable(false);
                txt_mes.setDisable(true);
                txt_dia_multa.setDisable(true);
            }
            Limpar();
        }
        else
        {
            txt_ano.setDisable(true);
            txt_mes.setDisable(true);
            txt_dia_multa.setDisable(true);
        }
        
    }
    
    
    private void Limpar()
    {
        txt_ano.setText("");
        txt_mes.setText("");
        txt_dia_multa.setText("");
    }
    
    
    @FXML
    public void Adicionar_MesAno( ActionEvent event )
    {
        
        Preencher();
        Inicializar_Tabela();
         
    }
    
    
    private void Preencher()
    {
        String mes = txt_mes.getText();
        String ano = txt_ano.getText();
        String dia = txt_dia_multa.getText();
        Alert a;
        String op = cb_opcao.getSelectionModel().getSelectedItem();
        mesano  = new MesAno();
        if( op.equals("Mês"))
        {
            if( !"".equals(mes))
            {
                mesano.setMes(mes);
                mesano.Adicionar_Mes();
                txt_mes.setText("");
                a = new Alert(AlertType.CONFIRMATION , "Mes inserido com sucesso");
                a.show();
            }else
            {
                a = new Alert(AlertType.ERROR , "Existem campos vazios");
                a.show();
            }
        }
        else
        {
             if( !"".equals(ano))
            {
                mesano.setAno(ano);
                mesano.Adicionar_Ano();
                txt_ano.setText("");
                a = new Alert(AlertType.CONFIRMATION , "Ano inserido com sucesso");
                a.show();
            }else
                 if( !"".equals(dia))
                 {
                     mesano.setDia(dia);
                     mesano.Adicionar_dia();
                     txt_dia_multa.setText("");
                     a = new Alert(AlertType.CONFIRMATION , "Dia inserido com sucesso");
                    a.show();
                 }
             else
            {
                a = new Alert(AlertType.ERROR , "Existem campos vazios");
                a.show();
            }
        }
         
        

    }
    
    
    private void Inicializar_Tabela()
    {
        Configurar_colunas();
        ResultSet rs = OperacoesBase.Consultar("select mes from mes_cobranca");
        ResultSet rs2 = OperacoesBase.Consultar("select ano from ano_cobranca");
        ObservableList<MesAno> lista = FXCollections.observableArrayList();
        
        mesano = new MesAno();
        
        try {
            while( rs.next() )
            {
                mesano.setMes(rs.getString("mes"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddMesAnoCobrancaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            while( rs2.next() )
            {
                mesano.setAno(rs2.getString("ano"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddMesAnoCobrancaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        lista.add(mesano);
        tabela.setItems(lista);
        
    }
    
    
    private void Configurar_colunas()
    {
        
        coluna_ano.setCellValueFactory( new PropertyValueFactory<>("ano"));
        coluna_mes.setCellValueFactory( new PropertyValueFactory<>("mes"));
        coluna_dia_multa.setCellValueFactory(new PropertyValueFactory<>("dia_multa"));
    }
    
}
