/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pagamento;

import Bd.OperacoesBase;
import Controlador.Preco.AddMesAnoCobrancaController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import modelo.Classe;
import modelo.MesAno;
import modelo.Meses;
import modelo.Modelo_privilegio;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class DefinicoesGerais_mesAnoController implements Initializable {
    @FXML
    private JFXComboBox<String> cb_opcao;
    @FXML
    private JFXTextField txt_ano;
    @FXML
    private TableView<MesAno> tabela;
    @FXML
    private TableColumn<MesAno, String> coluna_mes;
    @FXML
    private TableColumn<MesAno, String> coluna_ano;

    private MesAno mesano;
    @FXML
    private JFXComboBox<String> cb_mes;
    @FXML
    private ImageView imagem1;
    @FXML
    private JFXButton btn_adicionar;
    @FXML
    private TableColumn<MesAno, String> coluna_dia;
    @FXML
    private JFXTextField txt_dia;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Inicializar_Opcoes();
        Inicializar_Tabela();
        //Verifica_Privilegios();
    }    

    @FXML
    private void SelecionaOpcao_VisualizaMes_Ano(ActionEvent event) 
    {
        
        String op_selected = cb_opcao.getSelectionModel().getSelectedItem();
        if( op_selected != null )
        {
            switch (op_selected) {
                case "Mês":
                    cb_mes.setDisable(false);
                    txt_ano.setDisable(true);
                    txt_dia.setDisable(true);
                    break;
                case "Ano":
                    txt_ano.setDisable(false);
                    cb_mes.setDisable(true);
                    txt_dia.setDisable(true);
                    break;
                default:
                    txt_dia.setDisable(false);
                    cb_mes.setDisable(true);
                    txt_ano.setDisable(true);
                    break;
            }
                    
        }
        else
        {
            txt_ano.setDisable(true);
            txt_dia.setDisable(true);
            cb_mes.setDisable(true);
        }
        
        
    }


    
    @FXML
    private void Adicionar(ActionEvent event) 
    {
        
        Preencher();
        
    }
    
 //******************************************************
 //******************************************************
   private void Inicializar_Opcoes()
    {
       
        String opcoes[] = { "Mês" , "Ano", "Dia" };
        cb_opcao.setItems( FXCollections.observableArrayList(Arrays.asList(opcoes)) );
        cb_mes.setItems(Meses.Listar_Meses());
    }
    
   private void Inicializar_Tabela()
    {
        Configurar_colunas();
        ResultSet rs = OperacoesBase.Consultar("select mes from mes_cobranca");
        ResultSet rs2 = OperacoesBase.Consultar("select ano from ano_cobranca");
        ResultSet r3 = OperacoesBase.Consultar("select * from dia_multa");
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
    
         try {
            while( r3.next() )
            {
                mesano.setDia(r3.getString("dia"));
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
        coluna_dia.setCellValueFactory( new PropertyValueFactory<>("dia"));
    }
    
    public void Limpar()
    {
        txt_ano.clear();
        cb_mes.getSelectionModel().clearSelection();
        cb_opcao.getSelectionModel().clearSelection();
        txt_dia.clear();
    }
    
     private void Preencher()
    {
        String mes = cb_mes.getSelectionModel().getSelectedItem();
        String ano = txt_ano.getText();
        String dia = txt_dia.getText();
        Alert a;
        String op = cb_opcao.getSelectionModel().getSelectedItem();
        mesano  = new MesAno();
        if( op.equals("Mês"))
        {
            if( !"".equals(mes))
            {
                mesano.setMes(mes);
                mesano.Adicionar_Mes();
                Limpar();
                a = new Alert(Alert.AlertType.INFORMATION , "Mes inserido com sucesso");
                a.show();
                Inicializar_Tabela();
            }else
            {
                a = new Alert(Alert.AlertType.ERROR , "Existem campos vazios");
                a.show();
            }
        }
        else
        if( "Dia".equals(op) )
        {
            if( !"".equals(dia) )
            {
                mesano.setDia(dia);
                mesano.Adicionar_dia();
                Limpar();
                a = new Alert(Alert.AlertType.INFORMATION , "Dia inserido com sucesso");
                a.show();
                Inicializar_Tabela();

            }
            else
            {
                a = new Alert(Alert.AlertType.ERROR , "Existem campos vazios");
                a.show();
            }
        }
        else
        {
             if( !"".equals(ano))
            {
                mesano.setAno(ano);
                mesano.Adicionar_Ano();
                Limpar();
                a = new Alert(Alert.AlertType.INFORMATION , "Ano inserido com sucesso");
                a.show();
                Inicializar_Tabela();
            }
             else
            {
                a = new Alert(Alert.AlertType.ERROR , "Existem campos vazios");
                a.show();
            }
        }

    }

    @FXML
    private void AbirInfo(MouseEvent event) {
    }

    private void Verifica_Privilegios()
    {
        int codfuncionario = Usuario.Obter_CodFuncionario(Usuario.NameToCode(Usuario.getUsuario_activo()));
        
        if( Modelo_privilegio.Obter_Insercao(codfuncionario) == 2 )
             btn_adicionar.setDisable(false);
        else
            btn_adicionar.setDisable(true);
        
             
    }
    
    
}
