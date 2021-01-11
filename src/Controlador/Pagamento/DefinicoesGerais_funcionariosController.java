/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pagamento;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import definicoes.DefinicoesPane;
import definicoes.DefinicoesUnidadePreco;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Modelo_privilegio;
import modelo.Usuario;
import modelo.Valor_salario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class DefinicoesGerais_funcionariosController implements Initializable {
    @FXML
    private JFXComboBox<String> cb_funcao;
    @FXML
    private JFXTextField txt_valor;
    @FXML
    private JFXTextField txt_desconto;
    @FXML
    private JFXButton btn_cadastrar;
    @FXML
    private TableView<Valor_salario> tabela;
    @FXML
    private TableColumn<Valor_salario, String> coluna_funcao;
    @FXML
    private TableColumn<Valor_salario, String> coluna_valor;
    @FXML
    private TableColumn<Valor_salario, String> coluna_desconto;
    @FXML
    private Label lbl_total;
    @FXML
    private Pane panel_lateral;
    @FXML
    private Label lbl_eliminar;
    @FXML
    private Label lbl_Actualizar;

    private  boolean activo = false;
    private Valor_salario valor;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCombo();
        CarregarTabela();
        //Verifica_Privilegios();
    }    

    @FXML
    private void cadastrar(ActionEvent event) 
    {
        Auxiliar_preenchimento();
        CarregarTabela();
    }

    @FXML
    private void Pesquisar(MouseEvent event) {
    }

    @FXML
    private void show_panel_lateral(MouseEvent event) 
    {
        activo = DefinicoesPane.Habilita_Desabilita_Pane_Lateral(panel_lateral, activo);
    }
    
    @FXML
    private void submenu_Eliminar(MouseEvent event) 
    {
        activo = DefinicoesPane.Desabilita_Panel_Lateral(panel_lateral, activo);
    }

    @FXML
    private void submenu_showvActualizar(MouseEvent event) 
    {
            activo = DefinicoesPane.Desabilita_Panel_Lateral(panel_lateral, activo);
            Alert a;
            if( txt_valor.getText().equals("") || cb_funcao.getSelectionModel().getSelectedItem() == null || txt_desconto.getText().equals("") )
            {
                a = new Alert(Alert.AlertType.WARNING,"Existem campos vazios");
                a.show();
            }
            else
            {
                Valor_salario v= new Valor_salario(valor.getCodigo(), cb_funcao.getSelectionModel().getSelectedItem(), txt_valor.getText(), txt_desconto.getText());
                boolean verifica = v.Actualizar();
                if( verifica )
                {
                    a = new Alert(AlertType.INFORMATION,"Actualização feita com sucesso.");
                    a.show();
                    Limpar();
                    CarregarTabela();
                }
            }
             btn_cadastrar.setDisable(false);
    }

    @FXML
    private void submenu_showinfo(MouseEvent event) 
    {
         activo = DefinicoesPane.Desabilita_Panel_Lateral(panel_lateral, activo);
    }
    
    
    @FXML
    private void Editar_tabela(MouseEvent event) 
    {
         valor = tabela.getSelectionModel().getSelectedItem();
        if(  valor!= null)
        {
            txt_valor.setText(""+DefinicoesUnidadePreco.StringToInteger(valor.getValor()));
            txt_desconto.setText(""+DefinicoesUnidadePreco.StringToInteger(valor.getDesconto()));
            cb_funcao.getItems().add(valor.getFuncao());
            cb_funcao.getSelectionModel().select(valor.getFuncao());
            btn_cadastrar.setDisable(true);
        }
        else
            btn_cadastrar.setDisable(false);
        
    }

 
    
//****************************
//***************************
    private void InicializaCombo()
    {
        String funcao[] ={"Director geral","Director Pedagógico","Director Administrativo","Subdirector Administrativo","Secretário(a)","Secretário(a) Pedagógico(a)","Chefe de secretaria","Contabilista","Coordenador(a)","Professor(a)","Jardineiro","Motorista","Cozinheira","Limpeza", "Segurança","Vigilante"};
        cb_funcao.setItems( FXCollections.observableArrayList(Arrays.asList(funcao)));
        cb_funcao.getItems().removeAll(Valor_salario.Obter_Funcao_jaCadastrada());
    }
    
  private void CarregarTabela()
  {
      Setcolunas();
      tabela.setItems(Valor_salario.ListaValores());
      lbl_total.setText(Valor_Total());
  }
  
  private void Setcolunas()
  {
      coluna_funcao.setCellValueFactory(new PropertyValueFactory<>("funcao"));
      coluna_valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
      coluna_desconto.setCellValueFactory(new PropertyValueFactory<>("desconto"));
  }
  
  private void Auxiliar_preenchimento( )
  {
       Alert a;
      if( txt_valor.getText().equals("") || cb_funcao.getSelectionModel().getSelectedItem() == null || txt_desconto.getText().equals("") )
      {
          a = new Alert(Alert.AlertType.WARNING,"Existem campos vazios");
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
  
  private String Valor_Total()
  {
      int valor = 0;
      for( Valor_salario v : tabela.getItems() )
      {
          
          valor += DefinicoesUnidadePreco.StringToInteger(v.getValor());
      }
      
      return DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(valor));
  }

  
    
    private void Limpar()
    {
        cb_funcao.setItems(null);
        txt_desconto.clear();
        txt_valor.clear();
        tabela.getSelectionModel().select(null);
        InicializaCombo();
    }
    
    private void Verifica_Privilegios()
    {
        int codfuncionario = Usuario.Obter_CodFuncionario(Usuario.NameToCode(Usuario.getUsuario_activo()));
        
        if( Modelo_privilegio.Obter_Insercao(codfuncionario) == 2 )
             btn_cadastrar.setDisable(false);
        else
            btn_cadastrar.setDisable(true);
        
        if( Modelo_privilegio.Obter_Eliminacao(codfuncionario) == 2 )
             lbl_eliminar.setDisable(false);
        else
            lbl_eliminar.setDisable(true);
        
        if( Modelo_privilegio.Obter_Actualizacao(codfuncionario) == 2 )
             lbl_Actualizar.setDisable(false);
        else
            lbl_Actualizar.setDisable(true);
        
        
    }
 
  
    
    
}
