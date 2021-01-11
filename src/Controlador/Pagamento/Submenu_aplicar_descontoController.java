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
import modelo.MesAno;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class Submenu_aplicar_descontoController implements Initializable {
    @FXML
    private AnchorPane pane;
    @FXML
    private Label lbl_ano;
    @FXML
    private JFXTextField txt_motivo;
    @FXML
    private JFXTextField txt_desconto;
    @FXML
    private Label lbl_erro_desconto;
    @FXML
    private Label lbl_nome;

    
    private static String nome;
    private static String funcao;
    private DefinicoesPane dp;
    private static String bonus;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dp = new DefinicoesPane();
        InicializaCampos();
    }    


    @FXML
    private void verifica_dados(KeyEvent event) 
    {
        
        if( Character.isLetter(event.getCharacter().charAt(0)))
        {
            event.consume();
            lbl_erro_desconto.setVisible(true);
        }
        else
            lbl_erro_desconto.setVisible(false);
    }
    
    @FXML
    private void Close(MouseEvent event) 
    {
        DefinicoesPane.Fechar_Janela(pane);
    }

    @FXML
    private void Aplicar(ActionEvent event) 
    {
        int valor = Integer.parseInt((!txt_desconto.getText().equals(""))?txt_desconto.getText():"0");
        if( valor == 0 || txt_motivo.getText().equalsIgnoreCase(""))
        {
            Alert a = new Alert(AlertType.WARNING,"Existem campos vazios");
            a.setTitle("Campos vazios");
            a.show();
        }
        else
        {
             RegistrarPagamentoController.setDesconto_especial(valor);
             RegistrarPagamentoController.setNome_selecionado(nome);
             RegistrarPagamentoController.setFuncao_selecionada(funcao);
             RegistrarPagamentoController.setMotivo_desconto(txt_motivo.getText());
             dp.CallOtherWindow("RegistrarPagamento", pane);
        }
        
    }
    
    
//****************
//**********
    
    private void InicializaCampos()
    {
        lbl_nome.setText(nome);
        lbl_ano.setText(MesAno.Get_AnoActualCobranca());
    }

    public static void setNome(String nome) {
        Submenu_aplicar_descontoController.nome = nome;
    }

    public static void setBonus(String bonus) {
        Submenu_aplicar_descontoController.bonus = bonus;
    }
    
    

    public static void setFuncao(String funcao) {
        Submenu_aplicar_descontoController.funcao = funcao;
    }
    
    
    
    
}
