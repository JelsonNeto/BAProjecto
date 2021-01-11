/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioFinanceiro;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import definicoes.DefinicoesData;
import definicoes.DefinicoesPane;
import definicoes.DefinicoesUnidadePreco;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Modelo_privilegio;
import modelo.Saida;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class GSaidasController implements Initializable {

    @FXML
    private JFXComboBox<String> cb_efeito;
    @FXML
    private JFXTextField txt_efeito;
    @FXML
    private JFXTextField txt_valor;
    @FXML
    private JFXDatePicker data;
    @FXML
    private Label lbl_erro_valor;

    private String efeito;
    @FXML
    private ImageView imagem1;
    @FXML
    private TableView<Saida> tabela;
    @FXML
    private TableColumn<Saida, String> coluna_motivo;
    @FXML
    private TableColumn<Saida, String> coluna_valor;
    @FXML
    private TableColumn<Saida, String> coluna_data;
    @FXML
    private TableColumn<Saida, String> coluna_funcionario;
    @FXML
    private TableColumn<Saida, String> coluna_status;
    @FXML
    private JFXButton btn_eliminar;
    @FXML
    private JFXButton btn_adicionar;

    @FXML
    private JFXButton btn_eliminar1;
    @FXML
    private JFXCheckBox check_autorizar;
    
    //variaveis auxiliadoras
    private static Pane Pane;
    private String data_aprovacao;
    private int cod_funcionario_aprova;
    @FXML
    private Label total;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaEfeitos();
        CarregarTabela();
        //Verifica_Privilegios();
    }    

    @FXML
    private void SelecionaEfeito(ActionEvent event) 
    {
        
        String efeito_var = cb_efeito.getSelectionModel().getSelectedItem();
        if( "Outro".equalsIgnoreCase(efeito_var))
        {
            efeito = efeito_var;
            txt_efeito.setDisable(false);
        }
        else
        {
            txt_efeito.setDisable(true);
            efeito = efeito_var;
        }
        
    }

    @FXML
    private void Verificar(KeyEvent event) 
    {
        if( Character.isLetter(event.getCharacter().charAt(0)))
        {
            event.consume();
            lbl_erro_valor.setVisible(true);
        }
        else
            lbl_erro_valor.setVisible(false);
    }

    @FXML
    private void Registrar(ActionEvent event) 
    {
        Preencher();
    }
    
     @FXML
    private void Editar_Tabela(MouseEvent event)
    {
        
        Saida s = tabela.getSelectionModel().getSelectedItem();
        if( s != null )
               SetBotao(s);
        
    }

    @FXML
    private void Eliminar(ActionEvent event) 
    {
         Saida s = tabela.getSelectionModel().getSelectedItem();
           if( s != null )
           {
               Alert a = new Alert(Alert.AlertType.CONFIRMATION,"Tem a certeza que deseja eliminar?");
               Optional<ButtonType> acao =  a.showAndWait();
               if( ButtonType.OK == acao.get() )
               {
                    s.Eliminar();
                    CarregarTabela();
               }
               else
                   tabela.getSelectionModel().clearSelection();
                                 
               btn_eliminar.setDisable(true);

           }
    }
    
    @FXML
    private void Autorizar_saida(ActionEvent event) 
    {
        if( check_autorizar.isSelected() )
        {
            Alert a = new Alert(AlertType.CONFIRMATION, "Autorizar a saida?");
            Optional<ButtonType> acao = a.showAndWait();
            if( acao.get() == ButtonType.OK )
            {
                Saida s = tabela.getSelectionModel().getSelectedItem();
                if( s!= null )
                {
                    s.setData_aprovacao(DefinicoesData.RetornaDatadoSistema().toString());
                    boolean valor = s.AutorizarSaida();
                    if( valor )
                    {
                        Alert a1 = new Alert(AlertType.INFORMATION,"Saida autorizada com sucesso");
                        a1.show();
                        CarregarTabela();
                        check_autorizar.setSelected(false);
                        data_aprovacao = s.getData_aprovacao();
                        cod_funcionario_aprova = s.getCod_user_valida();
                    }
                    check_autorizar.setDisable(true);
                }
            }
        }
    }
    
     @FXML
    private void Voltar(MouseEvent event) 
    {
        DefinicoesPane dp = new DefinicoesPane(Pane);
        dp.setPath("/vista/pagamentoFuncionario.fxml");
        dp.AddPane();
    }

   
    
    
    
    /******************************************************/
    private void InicializaEfeitos()
    {
        String valor[]= {"Água","Energia Eletrica","Alimentação","Outro"};
        cb_efeito.setItems(FXCollections.observableArrayList(Arrays.asList(valor)));
        lbl_erro_valor.setVisible(false);
        btn_eliminar.setDisable(true);
        btn_eliminar1.setDisable(true);
        check_autorizar.setDisable(true);
    }
    
    
    private void SetBotao( Saida s )
    {
          
          btn_eliminar.setDisable(false);
          btn_eliminar1.setDisable(false);
          if( s.getValiadada() == 0 )
          {
              check_autorizar.setDisable(false);
              check_autorizar.setSelected(false);
          }
          else
          {
              check_autorizar.setDisable(true);
              check_autorizar.setSelected(true);
          }
    }

    private void Preencher()
    {
        String valor = txt_valor.getText();
        String data_var = (data.getValue() == null)?null:data.getValue().toString();
       //vefifica se existem dados vazios
       if( "".equals(valor) || data_var == null || efeito == null || (( "Outro").equalsIgnoreCase(efeito) && "".equalsIgnoreCase(txt_efeito.getText()) ))
       {
           Alert a = new Alert(AlertType.WARNING, "Existem campos vazios");
           a.setTitle("Campos vazio");
           a.show();
           
       }
       else
       {
            int coduser = Usuario.NameToCode(Usuario.getUsuario_activo());
            if( efeito.equals("Outro"))
                efeito = txt_efeito.getText();

            Saida s = new Saida(coduser, efeito, valor, data_var,0);
            if(s.Adicionar())
            {
                Alert a = new Alert(Alert.AlertType.INFORMATION,"Registro efectuado com sucesso.");
                a.show();
                Limpar();
                CarregarTabela();
            }
            else
            {
                Alert a = new Alert(Alert.AlertType.ERROR,"Erro ao registrar a saida.");
                a.show();
            }
       }
        
       
        
    }

    private void Limpar() 
    {
        
        txt_efeito.clear();
        txt_valor.clear();
        data.setValue(null);
        cb_efeito.getSelectionModel().clearSelection();
        
    }

   private void SetColunas()
   {
       coluna_data.setCellValueFactory(new PropertyValueFactory<>("data_saida"));
       coluna_funcionario.setCellValueFactory(new PropertyValueFactory<>("nome_funcionario"));
       coluna_motivo.setCellValueFactory(new PropertyValueFactory<>("efeito"));
       coluna_valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
       coluna_status.setCellValueFactory(new PropertyValueFactory<>("exibe_validacao"));
   }
    
   private void CarregarTabela()
   {
       SetColunas();
       tabela.setItems(Saida.CarregaDadosTabela());
       total.setText(Saida.valorTotal());
       
   }

    private void Verifica_Privilegios()
    {
        int codfuncionario = Usuario.Obter_CodFuncionario(Usuario.NameToCode(Usuario.getUsuario_activo()));
        if( Modelo_privilegio.Obter_Insercao(codfuncionario) == 0 )
             btn_adicionar.setDisable(true);
        else
            btn_adicionar.setDisable(false);
        
        if( Modelo_privilegio.Obter_Eliminacao(codfuncionario) == 2 )
             btn_eliminar.setDisable(false);
        else
            btn_eliminar.setDisable(true);
                
             
    }

    public static void setPane(Pane Pane) {
        GSaidasController.Pane = Pane;
    }
    
    private void Preenche_Recibo()
    {
        Saida s = tabela.getSelectionModel().getSelectedItem();
        if( s != null )
        {
            s.setCod_user_valida(cod_funcionario_aprova);
            s.setData_aprovacao(data_aprovacao);
            s.Preenche_Recibo();
        }
    }

    @FXML
    private void Imorimir(ActionEvent event) {
        Preenche_Recibo();  
    }

    

   
   
    
}
