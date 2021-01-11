/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.GerirEntidade;

import Controlador.Professor.PerfilProfessorController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import definicoes.DefinicoesCores;
import definicoes.DefinicoesGerais;
import definicoes.DefinicoesPane;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Funcionario;
import modelo.Professor;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class CFuncionarioController implements Initializable {
    @FXML
    private Pane panel_lateral;

    private boolean activo = false;
    @FXML
    private JFXTextField txt_nome;
    @FXML
    private JFXTextField txt_anoadmissao;
    @FXML
    private JFXComboBox<String> cb_funcao;
    @FXML
    private JFXComboBox<String> cb_status;
    @FXML
    private JFXTextField txt_bi_cedula;
    @FXML
    private JFXDatePicker data_nasc;
    @FXML
    private TableView<Funcionario> tabela;
    @FXML
    private TableColumn<Funcionario, String> coluna_nome;
    @FXML
    private TableColumn<Funcionario, String> coluna_funcao;
    @FXML
    private TableColumn<Funcionario, String>coluna_status;
    @FXML
    private TableColumn<Funcionario, String> coluna_ano_admissao;
    @FXML
    private Label lbl_total;
    
    
    private static Funcionario funcionario = null; //Vai ajudar tambem a obter o funcionario da janela detalhes-funcionario
    @FXML
    private JFXButton btn_adicionar;
    @FXML
    private JFXButton btn_actualizar;
    @FXML
    private Label lbl_eliminar;
    @FXML
    private ToggleGroup sexo;
    @FXML
    private JFXRadioButton rb_masculino;
    @FXML
    private JFXRadioButton rb_femenino;
    
    private DefinicoesPane dp;
    private static Pane pane_cadastrar;
    @FXML
    private Label lbl_erro;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Inicializa_Campos();
        MudaCor();
        dp = new DefinicoesPane();
    }    

    @FXML
    private void show_panel_lateral(MouseEvent event) 
    {
        
      Desactiva_Activa_janela_PressHamburguer();
        
    }

    @FXML
    private void submenu_carregaTabela(MouseEvent event) 
    {
         Desactiva_JanelaLateral();
         CarregarTabela();
    }
   
    @FXML
    private void submenu_Eliminar(MouseEvent event) 
    {
        Desactiva_JanelaLateral();
         
         Alert a = new Alert(Alert.AlertType.CONFIRMATION,"Tem a certeza que deseja eliminar?");
         Optional<ButtonType> acao =  a.showAndWait();
         if( acao.get() == ButtonType.OK )
         {
             Funcionario f = tabela.getSelectionModel().getSelectedItem();
             if( f.Eliminar() )
             {
                 a.setAlertType(Alert.AlertType.INFORMATION);
                 a.setContentText("Registro eliminado com sucesso");
                 a.show();
                 Limpar();
             }
             CarregarTabela();
         }
    }

    @FXML
    private void submenu_showinfo(MouseEvent event) 
    {
        Desactiva_JanelaLateral();
    }

    @FXML
    private void submenu_showverdetalhes(MouseEvent event) 
    {
        Desactiva_JanelaLateral();
        Funcionario funcionario_var = tabela.getSelectionModel().getSelectedItem();
        if( funcionario_var != null)
        {
                Detalhes_funcionarioController.setCadastro_pane(pane_cadastrar);
                Detalhes_funcionarioController.setFuncionario(funcionario_var);
                dp.setPane(pane_cadastrar);
                dp.setPath("/vista/detalhes_funcionario.fxml");
                dp.AddPane();
        }
    }

    @FXML
    private void cadastrar(ActionEvent event) 
    {
        auxiliar_Preenchimento("Adicionar");
    }
    
    private void submenu_showJustificar(MouseEvent event)
    {
        Desactiva_JanelaLateral();
    }
 
    @FXML
    private void actualizar(ActionEvent event) 
    {
         auxiliar_Preenchimento("Actualizar");
    }

    @FXML
    private void SetCampos(MouseEvent event) 
    {
        funcionario = tabela.getSelectionModel().getSelectedItem();
        EditaCampos(funcionario);
    }
    
    
    @FXML
    private void Valida_ano(KeyEvent event) 
    {
        if( !Character.isDigit(event.getCharacter().charAt(0)))
        {
            event.consume();
            lbl_erro.setVisible(true);
        }
        else
            lbl_erro.setVisible(false);
    }
    
    

    
    
//***************************METODOS AUXILIARES**********************************************************
    
    private boolean auxiliar_Preenchimento( String operacao )
    {
        Alert a;
        String msg1 = "";
        String msg2 = "";
        boolean valor = false;
        if( !Validacoes.ValidaFuncionario.CamposVazios(txt_nome, txt_bi_cedula ,txt_anoadmissao, cb_funcao,cb_status,data_nasc) )
        {
                int codigo = Funcionario.UltimoCodigo();
                String nome = txt_nome.getText();
                String bi_cedula = txt_bi_cedula.getText();
                String anoadmisso = txt_anoadmissao.getText();
                String funcao = cb_funcao.getSelectionModel().getSelectedItem();
                String status = cb_status.getSelectionModel().getSelectedItem();
                LocalDate data_nascimento = data_nasc.getValue();
                String sexo = DefinicoesGerais.DevolveSexo(rb_masculino);
                String codigo_escola_f = "GNX-F"+codigo;

                Funcionario f = new Funcionario(codigo, nome, bi_cedula, data_nascimento, status, funcao, anoadmisso,sexo,codigo_escola_f);
                if( "Adicionar".equals(operacao) )
                {
                    valor = f.Adicionar();
                    msg1="Adicionado";
                    msg2 = "adicionar";
                }
                else
                {
                    f.setCodfuncionario(funcionario.getCodfuncionario());
                    valor = f.Actualizar();
                    msg1 = "Actualizado";
                    msg2 = "actualizar";
                }
                
                if( valor )
                {
                     a = new Alert(Alert.AlertType.INFORMATION,"Funcionario "+msg1+" com sucesso!");
                     a.show();
                     Limpar();
                     CarregarTabela();
                     btn_actualizar.setDisable(true);
                     btn_adicionar.setDisable(false);
                }
                else{
                    a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Erro ao "+msg2+" o funcionario");
                    a.show();
                }
        }
        else{
             a = new Alert(Alert.AlertType.WARNING);
             a.setContentText("Existem campos Vazios");
             a.show();
        }
        return valor;
    }
    
    private void CarregarTabela()
    {
        SetColunas();
        tabela.setItems(Funcionario.ListaFuncionarios_paraTabela());
        lbl_total.setText(+tabela.getItems().size()+" Registro(s)");
    }
    
    private void SetColunas()
    {
        coluna_nome.setCellValueFactory( new PropertyValueFactory<>("nome"));
        coluna_status.setCellValueFactory( new PropertyValueFactory<>("status"));
        coluna_funcao.setCellValueFactory( new PropertyValueFactory<>("funcao"));
        coluna_ano_admissao.setCellValueFactory( new PropertyValueFactory<>("anoAdmissao"));
    }
    
 
    private void Limpar()
    {
        txt_anoadmissao.clear();
        txt_bi_cedula.clear();
        txt_nome.clear();
        data_nasc.setValue(null);
        cb_funcao.getSelectionModel().select(null);
        cb_status.getSelectionModel().select(null);
        rb_masculino.setSelected(true);
        rb_femenino.setSelected(false);
        
    }
    
    private void Inicializa_Campos()
    {
        String status[] = {"Activo", "Não Activo"};
        String funcao[] ={"Director geral","Director Pedagógico","Director Administrativo","Subdirector Administrativo","Secretário(a)","Secretário(a) Pedagógico(a)","Chefe de secretaria","Contabilista","Coordenador(a)","Professor(a)","Jardineiro","Motorista","Cozinheira","Limpeza", "Segurança","Vigilante"};
        cb_funcao.setItems( FXCollections.observableArrayList(Arrays.asList(funcao)));
        cb_status.setItems( FXCollections.observableArrayList(Arrays.asList(status)));
        rb_masculino.setSelected(true);
        lbl_erro.setVisible(false);
    }

    
    private void MudaCor()
    {
        DefinicoesCores.MudarCor_Selecao_RadioButton(rb_masculino, rb_femenino);
    }
    private void Desactiva_Activa_janela_PressHamburguer()
    {
        if( !activo )
        {
            panel_lateral.setVisible(true);
            activo = true;
        }
        else
        {
            panel_lateral.setVisible(false);
            activo = false;
        }
    }
    private void Desactiva_JanelaLateral()
    {
         if( activo )
        {
            panel_lateral.setVisible(false);
            activo = false;
        }
    }

    private void EditaCampos(  Funcionario funcionario )
    {
         if( funcionario!= null )
        {
            txt_nome.setText(funcionario.getNome());
            txt_bi_cedula.setText(funcionario.getBi_cedula());
            txt_anoadmissao.setText(funcionario.getAnoAdmissao());
            data_nasc.setValue(funcionario.getData_nascimento());
            cb_funcao.getSelectionModel().select(funcionario.getFuncao());
            cb_status.getSelectionModel().select(funcionario.getStatus());
            
            btn_actualizar.setDisable(false);
            btn_adicionar.setDisable(true);
            lbl_eliminar.setDisable(false);
           
        }
        else
        {
            btn_actualizar.setDisable(true);
            btn_adicionar.setDisable(false);
            lbl_eliminar.setDisable(true);
        }
    }
    public static void setPane_cadastrar(Pane pane_cadastrar) {
        CFuncionarioController.pane_cadastrar = pane_cadastrar;
    }

    private void Texto_Vazio(InputMethodEvent event) 
    {
        
         if( txt_anoadmissao.getText().equals(""))
         {
             lbl_erro.setText("Campo Vazio");
             lbl_erro.setVisible(true);
         }
    }

   

   
   
    
}
