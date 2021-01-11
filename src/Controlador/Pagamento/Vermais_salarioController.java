/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pagamento;

import definicoes.DefinicoesPane;
import definicoes.DefinicoesUnidadePreco;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Funcionario;
import modelo.MesAno;
import modelo.Pagamento_Salario;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class Vermais_salarioController implements Initializable {
    @FXML
    private ImageView imagem;
    @FXML
    private Label txt_nome;
    @FXML
    private Label txt_sexo;
    @FXML
    private Label txt_datanasc;
    @FXML
    private Label txt_bi;
    @FXML
    private Label lbl_status;
    @FXML
    private Label lbl_anoad;
    @FXML
    private Label lbl_funcao;

    
    private static Pagamento_Salario ps;
    @FXML
    private Label lbl_bonus;
    @FXML
    private Label lbl_salariobase;
    @FXML
    private Label lbl_mes;
    @FXML
    private Label lbl_data_pagamento;
    @FXML
    private Label lbl_valor;
    @FXML
    private Label lbl_desconto;
    @FXML
    private Label lbl_user;
    @FXML
    private Label lbl_anoc;
    @FXML
    private ImageView imagem1;
    
    /**
     * Initializes the controller class.
     */
    
    private static Pane pane;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCampos();
    }    
    
    
    
    @FXML
    private void Voltar(MouseEvent event) 
    {
        DefinicoesPane dp = new DefinicoesPane(pane);
        dp.setPath("/vista/pagamentoFuncionario.fxml");
        dp.AddPane();
    }
    
 //
 //
    private void InicializaCampos()
    {
        Funcionario f = Funcionario.Obter_Funcionario(ps.getCodfuncionario());
        txt_nome.setText(ps.getNomefuncionario());
        txt_bi.setText(f.getBi_cedula());
        txt_datanasc.setText(f.getData_nascimento().toString());
        txt_sexo.setText(f.getSexo());
        lbl_funcao.setText(ps.getFuncao());
        lbl_anoad.setText(f.getAnoAdmissao());
        lbl_status.setText(f.getStatus());
        
        
        lbl_bonus.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(ps.getBonus()));
        lbl_desconto.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(ps.getDesconto()));
        lbl_salariobase.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(ps.getSalario_base()));
        lbl_valor.setText(ps.getValor());
        lbl_mes.setText(ps.getMes());
        lbl_data_pagamento.setText(ps.getData().toString());
        lbl_anoc.setText(MesAno.Get_AnoActualCobranca());
        lbl_user.setText(Usuario.CodeToName(ps.getCoduser()));
    }

    public static void setPs(Pagamento_Salario ps) {
        Vermais_salarioController.ps = ps;
    }

    public static void setPane(Pane pane) {
        Vermais_salarioController.pane = pane;
    }
    
    


    
    
}
