/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pagamento;

import com.jfoenix.controls.JFXTextField;
import definicoes.DefinicoesPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.MesAno;

/**
 *
 * @author Familia Neto
 */
public class Submenu_bonus_controller implements Initializable {
    @FXML
    private AnchorPane pane;
    @FXML
    private Label lbl_ano;
    @FXML
    private JFXTextField txt_motivo;
    @FXML
    private JFXTextField txt_bonus;
    @FXML
    private Label lbl_erro_bonus;
    @FXML
    private Label lbl_nome;
    
    private static  String nome;
    private static String funcao;
    private static String desconto;
    private DefinicoesPane dp;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        dp = new DefinicoesPane();
        Inicializa_campos();
        
    }

    @FXML
    private void Close(MouseEvent event) 
    {
        
        Stage stage = (Stage)pane.getScene().getWindow();
        stage.close();
        dp.CallOtherWindow("RegistrarPagamento", pane);
    }

    @FXML
    private void Aplicar(ActionEvent event) 
    {
        
        int valor = Integer.parseInt((!txt_bonus.getText().equals(""))?txt_bonus.getText():"0");
        if( valor == 0 || txt_motivo.getText().equalsIgnoreCase(""))
        {
            Alert a = new Alert(AlertType.WARNING,"Existem campos vazios");
            a.setTitle("Campos vazios");
            a.show();
        }
        else
        {
             RegistrarPagamentoController.setBonus(valor);
             RegistrarPagamentoController.setDesconto_especial(0);
             RegistrarPagamentoController.setNome_selecionado(nome);
             RegistrarPagamentoController.setFuncao_selecionada(funcao);
             RegistrarPagamentoController.setMotivo_bonus(txt_motivo.getText());
        
             dp.CallOtherWindow("RegistrarPagamento", pane);
        }
        
        
        
        
    }

    @FXML
    private void verifica_dados(KeyEvent event) 
    {
        if( Character.isLetter(event.getCharacter().charAt(0)))
        {
            event.consume();
            lbl_erro_bonus.setVisible(true);
        }
        else
            lbl_erro_bonus.setVisible(false);
    }

/**********************METODOS OPERACIONAIS*********************************************************************/

    public static void setFuncao(String funcao) {
        Submenu_bonus_controller.funcao = funcao;
    }

    public static void setNome(String nome) {
        Submenu_bonus_controller.nome = nome;
    }

    public static void setDesconto(String desconto) {
        Submenu_bonus_controller.desconto = desconto;
    }

    
    private void Inicializa_campos() 
    {
        
        lbl_nome.setText(nome);
        lbl_ano.setText(MesAno.Get_AnoActualCobranca());
        
    }
    
    
}
