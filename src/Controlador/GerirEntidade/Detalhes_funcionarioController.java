/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.GerirEntidade;

import definicoes.DefinicoesData;
import definicoes.DefinicoesPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.FaltaFuncionario;
import modelo.Funcionario;
import modelo.MesAno;
import modelo.Meses;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class Detalhes_funcionarioController implements Initializable {
    @FXML
    private ImageView imagem;
    @FXML
    private Label txt_nome;
    @FXML
    private Label txt_bi;
    @FXML
    private Label txt_datanasc;
    @FXML
    private Label txt_sexo;
    @FXML
    private Label txt_curso;
    @FXML
    private Label txt_classe;
    
    private static Pane cadastro_pane;
    private static Funcionario funcionario;
    private DefinicoesPane dp;
    @FXML
    private Label txt_funcao;
    @FXML
    private Label txt_anoadminssao;
    @FXML
    private Label txt_status;
    @FXML
    private Label txt_anocorrente;
    @FXML
    private Label txt_qtd_salarios_atraso;
    @FXML
    private Label n_meses_npagos;
    @FXML
    private Label txt_codigo;
    @FXML
    private Label txt_totaFaltas;
    @FXML
    private Label txt_falta_mes_corrente;
    @FXML
    private Label txt_mes_corrente;

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
    private void back(MouseEvent event) 
    {
        dp.setPane(cadastro_pane);
        dp.setPath("/vista/CFuncionario.fxml");
        dp.AddPane();
    }

    public static void setCadastro_pane(Pane cadastro_pane) {
        Detalhes_funcionarioController.cadastro_pane = cadastro_pane;
    }

    public static void setFuncionario(Funcionario funcionario) {
        Detalhes_funcionarioController.funcionario = funcionario;
    }

    @FXML
    private void EmitirCartao(MouseEvent event) {
    }

    private void InicializaCampos() {
        
        txt_nome.setText(funcionario.getNome());
        txt_bi.setText(funcionario.getBi_cedula());
        txt_datanasc.setText(funcionario.getData_nascimento().toString());
        txt_sexo.setText(funcionario.getSexo());
        txt_status.setText(funcionario.getStatus());
        txt_anoadminssao.setText(funcionario.getAnoAdmissao());
        txt_funcao.setText(funcionario.getFuncao());
        txt_anocorrente.setText(MesAno.Get_AnoActualCobranca());
        txt_totaFaltas.setText(String.valueOf(FaltaFuncionario.Quantidade_Falta_Anolectivo_por_funcionario(funcionario.getCodfuncionario(), MesAno.Get_AnoActualCobranca())));
        txt_falta_mes_corrente.setText(String.valueOf(FaltaFuncionario.Quantidade_Falta_mes_Anolectivo_por_funcionario(funcionario.getCodfuncionario(), DefinicoesData.retornaPadrao(Meses.ObterMes(DefinicoesData.RetornaMes(DefinicoesData.RetornaDatadoSistema().toString()))), MesAno.Get_AnoActualCobranca())));
        txt_mes_corrente.setText(Meses.ObterMes(DefinicoesData.RetornaMes(DefinicoesData.RetornaDatadoSistema().toString())));
        txt_codigo.setText(funcionario.getCodigo_escola_f());
    }
    
    
}
