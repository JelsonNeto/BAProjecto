/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pagamento;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import definicoes.DefinicoesData;
import definicoes.DefinicoesPane;
import definicoes.DefinicoesUnidadePreco;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.FaltaFuncionario;
import modelo.Funcionario;
import modelo.MesAno;
import modelo.Meses;
import modelo.Pagamento_Salario;
import modelo.Usuario;
import modelo.Valor_salario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class RegistrarPagamentoController implements Initializable {
    @FXML
    private JFXDatePicker data_pagamento;
    @FXML
    private  JFXTextField txt_funcao;
    @FXML
    private JFXTextField txt_salario;
    @FXML
    private JFXComboBox<String> cb_mes;
    @FXML
    private JFXComboBox<String> cb_nome;
    @FXML
    private JFXComboBox<String> cb_funcao;
    @FXML
    private Label lbl_totak_faltas;
    @FXML
    private Label lbl_anolectivo;
    @FXML
    private Label lbl_desconto;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Pane panel_lateral;
    @FXML
    private Label lbl_bonus;

    
    private boolean activo = false;
    private static int bonus= 0;
    private static int desconto_especial = 0;
    private static String motivo_desconto = "-";
    private static String motivo_bonus = "-";
    private static String nome_selecionado = null;
    private static String funcao_selecionada= null;
    @FXML
    private JFXButton btn_registrar;
    @FXML
    private Label lateral_bonus;
    @FXML
    private Label lateral_desconto;

   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCombo();
        Seleciona_Dados_Atraves_dajanelaBonus();
        if( bonus == 0 && desconto_especial == 0 ) //Feito isso porque ...
        {
            lateral_bonus.setDisable(true);
            lateral_desconto.setDisable(true);
        }
        
    }    
    
    private void InicializaCombo()
    {
        String funcao[] ={"Director geral","Director Pedagógico","Director Administrativo","Subdirector Administrativo","Secretário(a)","Secretário(a) Pedagógico(a)","Chefe de secretaria","Contabilista","Coordenador(a)","Professor(a)","Jardineiro","Motorista","Cozinheira","Limpeza", "Segurança","Vigilante"};
        cb_funcao.setItems( FXCollections.observableArrayList(Arrays.asList(funcao)));
        lbl_anolectivo.setText(MesAno.Get_AnoActualCobranca());
        
        
    }

    @FXML
    private void Seleciona_funcao_Inicializa_nome(ActionEvent event)
    {
        String funcao = cb_funcao.getSelectionModel().getSelectedItem();
        if( funcao != null )
        {
            cb_nome.setItems(Funcionario.ListaFuncionarios_por_funcao(funcao));
            txt_funcao.setText(funcao);
            txt_salario.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(Valor_salario.ObterSalario_por_funcao(funcao)));
            funcao_selecionada = funcao;
            
            if("0,00 Akz".equalsIgnoreCase(txt_salario.getText()))
            {
                btn_registrar.setDisable(true);
                Alert a = new Alert(AlertType.WARNING,"O valor do salário ainda não definido.");
                a.setTitle("Salario indefinido");
                a.show();
            }
            else
                btn_registrar.setDisable(false);
        }
    }
    
     @FXML
    private void seleciona_nome_inicializa_mes(ActionEvent event) 
    {
        String funcionario = cb_nome.getSelectionModel().getSelectedItem();
        if( funcionario != null )
        {
            int codfuncionario = Funcionario.NametoCode(cb_nome.getSelectionModel().getSelectedItem());
            cb_mes.getItems().add(Meses.ObterMes(Pagamento_Salario.Obter_ultimo_mes_pago(codfuncionario, MesAno.Get_AnoActualCobranca() )));
            nome_selecionado = funcionario;
            lateral_bonus.setDisable(false);
            lateral_desconto.setDisable(false);
        }
    }

    
    @FXML
    private void registrar(ActionEvent event) 
    {
        
        Auxiliar_Preenchimento();
        
    }
     @FXML
    private void show_panel_lateral(MouseEvent event) 
    {
        if( activo )
        {
            panel_lateral.setVisible(false);
            activo= false;
        }
        else
        {
            panel_lateral.setVisible(true);
            activo = true;
        }
        
    }
    
    @FXML
    private void submenu_bonus(MouseEvent event) 
    {
        String nome = cb_nome.getSelectionModel().getSelectedItem();
        String funcao = cb_funcao.getSelectionModel().getSelectedItem();
        if( nome != null  )
        {
             
             DefinicoesPane dp = new DefinicoesPane();
             Submenu_bonus_controller.setNome(nome);
             Submenu_bonus_controller.setFuncao(funcao);
             Submenu_bonus_controller.setDesconto(""+getDesconto_especial());
             dp.CallOtherWindow("submenu_aplicar_bonus",AnchorPane);
        }
          Desactiva_menu_Lateral();
        
    }

    @FXML
    private void submenu_desconto(MouseEvent event) 
    {
        String nome = cb_nome.getSelectionModel().getSelectedItem();
        String funcao = cb_funcao.getSelectionModel().getSelectedItem();
        if( nome != null  )
        {
            Desactiva_menu_Lateral();
            DefinicoesPane dp = new DefinicoesPane();
            Submenu_aplicar_descontoController.setNome(nome);
            Submenu_aplicar_descontoController.setFuncao(funcao);
            Submenu_aplicar_descontoController.setBonus(""+getBonus());
            dp.CallOtherWindow("submenu_aplicar_desconto",AnchorPane);
        }
        Desactiva_menu_Lateral();
    }
 


    @FXML
    private void submenu_showinfo(MouseEvent event) 
    {
        Desactiva_menu_Lateral();
    }
    
    @FXML
    private void SelecionaMes_InicializaFaltas_Desconto(ActionEvent event) 
    {
        
        String mes = cb_mes.getSelectionModel().getSelectedItem();
        String valor_desconto = Valor_salario.Obter_Desconto_Por_Funcao(cb_funcao.getSelectionModel().getSelectedItem());
        if( mes != null )
        {
           
           //Calculo do desconto por numero de faltas
           int quantidade =  FaltaFuncionario.Quantidade_Falta_mes_Anolectivo(DefinicoesData.retornaPadrao(mes), MesAno.Get_AnoActualCobranca());
           lbl_totak_faltas.setText(String.valueOf(quantidade));
           int desconto = quantidade * Integer.parseInt(valor_desconto);
           lbl_desconto.setText((DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(desconto+getDesconto_especial()))));
        
           
           //Aplicacao do bonus
           int salario_inteiro = DefinicoesUnidadePreco.ChangeFromStringToInt(txt_salario.getText());
           int salario_com_bonus = salario_inteiro+DefinicoesUnidadePreco.ChangeFromStringToInt(lbl_bonus.getText());
           
           //Aplicacao do desconto por falta e especial
           int Salario_final = salario_com_bonus-DefinicoesUnidadePreco.StringToInteger(lbl_desconto.getText());
           txt_salario.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(Salario_final)));
        
        }
        
    }
    
    @FXML
    private void Close(MouseEvent event) {
        
        Stage stage = (Stage)AnchorPane.getScene().getWindow();
        stage.close();
    }
    
/*********************************METODOS AUXILIZARES******************************************************/

    
    
    
    private void Auxiliar_Preenchimento()
    {
        
        Alert a;
        if( txt_funcao.getText().equals("") || txt_salario.getText().equals("")|| cb_mes.getSelectionModel().getSelectedItem() == null || cb_nome.getSelectionModel().getSelectedItem() == null || data_pagamento.getValue() == null )
        {
            a = new Alert(Alert.AlertType.WARNING,"Existem campos vazios");
            a.show();
        }
        else
        {
                    btn_registrar.setDisable(false);
                    int codpagamento = Pagamento_Salario.Ultimocodigo();
                    int codfuncionario = Funcionario.NametoCode(cb_nome.getSelectionModel().getSelectedItem());
                    int codsalario = Valor_salario.Funcao_to_Code(txt_funcao.getText());
                    int codmes = Meses.NameToCode(cb_mes.getSelectionModel().getSelectedItem());
                    int coduser = Usuario.NameToCode(Usuario.getUsuario_activo());
                    String salario_base = Valor_salario.ObterSalario_por_funcao(cb_funcao.getSelectionModel().getSelectedItem());
                    String anolectivo = MesAno.Get_AnoActualCobranca();
                    LocalDate data = data_pagamento.getValue();
                    String valor = String .valueOf(DefinicoesUnidadePreco.ChangeFromStringToInt(salario_base)+getBonus()-DefinicoesUnidadePreco.StringToInteger(lbl_desconto.getText()));
                    String desconto_falta = (lbl_totak_faltas.getText().equalsIgnoreCase("0"))?"Não Aplicado":"Aplicado";
                    
                    Pagamento_Salario ps = new Pagamento_Salario(codpagamento, codfuncionario, codsalario, coduser, codmes, data, anolectivo, valor, String.valueOf(DefinicoesUnidadePreco.StringToInteger(lbl_bonus.getText())),motivo_desconto,String.valueOf(DefinicoesUnidadePreco.StringToInteger(lbl_desconto.getText())),salario_base);
                    ps.setFuncao(txt_funcao.getText());
                    ps.setMotivo_bonus(motivo_bonus);
                    ps.setDesconto_falta(desconto_falta);
                    if(ps.Adicionar())
                    {
                        a = new Alert(Alert.AlertType.INFORMATION, "Registro adicionado com sucesso");
                        a.show();
                        Limpar();
                        ResetDados();
                    }
                    else
                    {
                        a = new Alert(Alert.AlertType.ERROR,"Erro ao adicionar o registro de pagamento");
                        a.show();
                    }
            
        }
        
        
    }
    
        private void Limpar()
        {
            txt_funcao.clear();
            txt_salario.clear();
            cb_funcao.getSelectionModel().clearSelection();
            cb_mes.getSelectionModel().clearSelection();
            cb_mes.getItems().remove(0);
            cb_nome.getSelectionModel().clearSelection();
            data_pagamento.setValue(null);
        }


    private void Desactiva_menu_Lateral()
    {
        panel_lateral.setVisible(false);
        activo = false;
    }

    public static void setDesconto_especial(int desconto_especial) {
        RegistrarPagamentoController.desconto_especial = desconto_especial;
    }

    public static void setBonus(int bonus) {
        RegistrarPagamentoController.bonus = bonus;
    }

    public static int getBonus() {
        return bonus;
    }

    public static int getDesconto_especial() {
        return desconto_especial;
    }

    public static String getNome_selecionado() {
        return nome_selecionado;
    }

    public static void setNome_selecionado(String nome_selecionado) {
        RegistrarPagamentoController.nome_selecionado = nome_selecionado;
    }

    public static void setFuncao_selecionada(String funcao_selecionada) {
        RegistrarPagamentoController.funcao_selecionada = funcao_selecionada;
    }

    public static void setMotivo_bonus(String motivo_bonus) {
        RegistrarPagamentoController.motivo_bonus = motivo_bonus;
    }

    public static void setMotivo_desconto(String motivo_desconto) {
        RegistrarPagamentoController.motivo_desconto = motivo_desconto;
    }
    
    

    
    
    public static String getFuncao_selecionada() {
        return funcao_selecionada;
    }

    private void Seleciona_Dados_Atraves_dajanelaBonus() 
    {
        
        if( nome_selecionado != null && funcao_selecionada != null )
        {
            cb_funcao.getSelectionModel().select(funcao_selecionada);
            cb_nome.setItems(Funcionario.ListaFuncionarios_por_funcao(funcao_selecionada));
            cb_nome.getSelectionModel().select(nome_selecionado);
            txt_funcao.setText(funcao_selecionada);
            txt_salario.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(Valor_salario.ObterSalario_por_funcao(funcao_selecionada)));
            //incializa_Mes
            int codfuncionario = Funcionario.NametoCode(cb_nome.getSelectionModel().getSelectedItem());
            cb_mes.getItems().add(Meses.ObterMes(Pagamento_Salario.Obter_ultimo_mes_pago(codfuncionario, MesAno.Get_AnoActualCobranca() )));
        
            lbl_bonus.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(bonus)));
            
        }
        
        
    }

    private void ResetDados() 
    {
        nome_selecionado = null;
        funcao_selecionada = null;
        setBonus(0);
        setDesconto_especial(0);
        lbl_bonus.setText("0");
        lbl_desconto.setText("0");
        motivo_bonus = "-";
        motivo_bonus = "-";
    }

  
    
    
    
   
}
