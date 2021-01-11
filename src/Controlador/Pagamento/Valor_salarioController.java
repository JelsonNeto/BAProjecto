/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pagamento;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Valor_salario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class Valor_salarioController implements Initializable {
    @FXML
    private TableView<Valor_salario> tabela;
    @FXML
    private TableColumn<Valor_salario, String> coluna_funcao;
    @FXML
    private TableColumn<Valor_salario, String> coluna_valor;
    @FXML
    private TableColumn<Valor_salario, String> coluna_desconto;
    @FXML
    private JFXComboBox<String> cb_funcao;
    @FXML
    private JFXTextField txt_valor;
    @FXML
    private JFXButton btn_cadastrar;
    @FXML
    private JFXButton btn_actualizar;
    @FXML
    private JFXButton btn_eliminar;
    @FXML
    private Label lbl_total;
    @FXML
    private JFXTextField txt_desconto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCombo();
        CarregarTabela();
    }    

    @FXML
    private void cadastrar(ActionEvent event) 
    {
        Auxiliar_preenchimento();
        CarregarTabela();
    }

    @FXML
    private void actualizar(ActionEvent event) {
    }

    @FXML
    private void eliminar(ActionEvent event) {
    }
    
    
  //
  private void Auxiliar_preenchimento( )
  {
       Alert a;
      if( txt_valor.getText().equals("") || cb_funcao.getSelectionModel().getSelectedItem() == null || txt_desconto.getText().equals("") )
      {
          a = new Alert(Alert.AlertType.WARNING,"Exisrem campos vazios");
          a.show();
      }
      else
      {
          int codigo = Valor_salario.Ultimocodigo();
          String funcao = cb_funcao.getSelectionModel().getSelectedItem();
          String valor = txt_valor.getText();
          String desconto = txt_desconto.getText();
          
          Valor_salario v = new Valor_salario(codigo, funcao, valor, desconto);
          boolean verifica = v.Adicionar();
          if( verifica )
          {
              a = new Alert(Alert.AlertType.INFORMATION,"Valor adicionado com sucesso!");
              a.show();
              txt_valor.clear();
              cb_funcao.getSelectionModel().select(null);
          }
          else
          {
              a = new Alert(Alert.AlertType.ERROR,"Erro ao tentar cadastrar o valor");
              a.show();
          }
      }
  }
  
  private void Setcolunas()
  {
      coluna_funcao.setCellValueFactory(new PropertyValueFactory<>("funcao"));
      coluna_valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
      coluna_desconto.setCellValueFactory(new PropertyValueFactory<>("desconto"));
  }
  
  private void CarregarTabela()
  {
      Setcolunas();
      tabela.setItems(Valor_salario.ListaValores());
  }
  
  private void InicializaCombo()
    {
        String funcao[] ={"Director geral","Director Pedagógico","Director Administrativo","Subdirector Administrativo","Secretário(a)","Secretário(a) Pedagógico(a)","Chefe de secretaria","Contabilista","Coordenador(a)","Professor(a)","Jardineiro","Motorista","Cozinheira","Limpeza", "Segurança","Vigilante"};
        cb_funcao.setItems( FXCollections.observableArrayList(Arrays.asList(funcao)));
        cb_funcao.getItems().removeAll(Valor_salario.Obter_Funcao_jaCadastrada());
    }

  
}
