/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Preco;

import Bd.OperacoesBase;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.VisualizaPreco;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class VisualizarprecoController implements Initializable {
    
    @FXML private TableColumn<VisualizaPreco, String> efeito_coluna;
    @FXML private TableColumn<VisualizaPreco, String> curso_coluna;
    @FXML private TableColumn<VisualizaPreco, String> classe_coluna;
    @FXML private TableColumn<VisualizaPreco, String> multa_coluna;
    @FXML private TableColumn<VisualizaPreco, String> preco_coluna;
    @FXML private TableView<VisualizaPreco> tabela;

    
    private VisualizaPreco preco;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        InicializarTabela();
        
    }    
    
    
    private void InicializarTabela()
    {
        //Chama as configuracoes das colunas e cria uma lista que contera os dados
        ObservableList<VisualizaPreco> lista =  FXCollections.observableArrayList();
        SetColunas();
        
        ResultSet rs = OperacoesBase.Consultar("select * from preco");
        try {
            while( rs.next() )
            {
              
                //instancias da classe BigDecimal
                BigDecimal valor_preco = new BigDecimal(rs.getString("valor"));
                BigDecimal valor_multa = new BigDecimal(rs.getString("valor_multa"));
                
                preco = new VisualizaPreco();
                preco.setCodcurso(rs.getInt("codcurso"));
                preco.setCodigo(rs.getInt("codpreco"));
                preco.setCurso(IntCursoToName(rs.getInt("codcurso")));
                preco.setEfeito(rs.getString("efeito"));
                preco.setValor_tabela("Akz "+valor_preco.toString()+",00");
                preco.setClasse(rs.getString("classe"));
                preco.setValor_multa("Akz "+valor_multa.toString()+",00");
                lista.add(preco);
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarprecoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        tabela.setItems(lista);
        
        
    }
    /**
     * Configuracao das colunas......
     */
    private void SetColunas()
    {
         efeito_coluna.setCellValueFactory( new PropertyValueFactory<>("efeito"));
         curso_coluna.setCellValueFactory( new PropertyValueFactory<>("curso"));
         classe_coluna.setCellValueFactory( new PropertyValueFactory<>("classe"));
         multa_coluna.setCellValueFactory( new PropertyValueFactory<>("valor_multa"));
         preco_coluna.setCellValueFactory( new PropertyValueFactory<>("valor_tabela"));
         
        
    }
  
    /**
     * Converter um valor inteiro para o nome
     */
    private String IntCursoToName( int codigo )
    {
        
        String nome = "";
        ResultSet rs = OperacoesBase.Consultar("select nome_curso from curso where codcurso = '"+codigo+"'");
        try {
            while( rs.next() )
            {
               nome = rs.getString("nome_curso");
            }
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarprecoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nome;
    }
    
    /**
     * Mostrar dados
     * @param event
     */
 /*   public void ShowDatas( ActionEvent event )
    {
       
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/vista/definicoespagamento.fxml"));
        try {
          Parent root = (Parent) fxml.load();
          DefinicoespagamentoController dc = fxml.getController();
          dc.setAdicionar("/vista/adicionarpreco.fxml");
                  
          
        } catch (IOException ex) {
            Logger.getLogger(VisualizarprecoController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }*/

    @FXML
    private void Eliminar(ActionEvent event) {
        
        VisualizaPreco vp = tabela.getSelectionModel().getSelectedItem();
        if( vp != null )
        {
            OperacoesBase.Eliminar("delete from preco where codpreco = '"+vp.getCodigo()+"'");
            InicializarTabela();
        }
        
    }
    
    
    
}
