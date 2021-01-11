/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Home;

import Controlador.Pagamento.DefinicoesGeraisPagamentoController;
import Controlador.Pagamento.PagamentoFuncionarioController;
import Controlador.RelatorioFinanceiro.RelatorioFinanceiroController;
import Validacoes.Valida_UsuarioActivo;
import definicoes.DefinicoesPane;
import java.awt.Panel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Modelo_privilegio;
import modelo.Usuario;

/**
 *
 * @author Familia Neto
 */
public class HomePagamento_geralController implements Initializable {
    @FXML
    private Pane pprincipal;
    
    private static Pane pane;
    private DefinicoesPane dp;
    @FXML
    private Pane pane_estudante;
    @FXML
    private Pane pane_funcionario;
    @FXML
    private Pane pane_definicoes;
    @FXML
    private Pane pane_frelatorio;

    private static Pane p_e;
    private static Pane p_f;
    private static Pane p_d;
    private static Pane p_r;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        p_e = pane_estudante;
        p_f = pane_funcionario;
        p_d = pane_definicoes;
        p_r = pane_frelatorio;
        dp = new DefinicoesPane(pane);
       /* if( Usuario.CodeToTipo(Usuario.NameToCode(Usuario.getUsuario_activo())).equals("Contabilista") || Usuario.EAdmin(Usuario.NameToCode(Usuario.getUsuario_activo())))
              Valida_UsuarioActivo.Fincanceiro(false);
        else
             Valida_UsuarioActivo.Fincanceiro(true);
        
        Verifica_Privilegios();*/
    }

    
    
    @FXML
    private void showPagamento_Aluno(MouseEvent event) 
    {
        HomePagamentoController.setPane(pane);
        dp.setPath("/vista/homePagamento.fxml");
        dp.AddPane();
        
    }

    @FXML
    private void showPagamento_funcionario(MouseEvent event) 
    {
        PagamentoFuncionarioController.setPane(pane);
        dp.setPath("/vista/pagamentoFuncionario.fxml");
        dp.AddPane();
    }

    @FXML
    private void showPagamento_definicoes(MouseEvent event) 
    {
        DefinicoesGeraisPagamentoController.setPane(pane);
        dp.setPath("/vista/DefinicoesGeraisPagamento.fxml");
        dp.AddPane();
    }
    
      @FXML
    private void showRelaFinanceiro(MouseEvent event) 
    {
        //Home de relatorios ==== Nao apenas o financeiro
        RelatorioFinanceiroController.setPane(pane);
        dp.setPath("/vista/relatorioFinanceiro.fxml");
        dp.AddPane();
    }

    public static void setPanel(Pane pane) {
        HomePagamento_geralController.pane = pane;
    }

    public static Pane getP_d() {
        return p_d;
    }

    public static Pane getP_e() {
        return p_e;
    }

    public static Pane getP_f() {
        return p_f;
    }

    public static Pane getP_r() {
        return p_r;
    }

  
    private void Verifica_Privilegios()
    {
        int codfuncionario = Usuario.Obter_CodFuncionario(Usuario.NameToCode(Usuario.getUsuario_activo()));
        
        if( Modelo_privilegio.Obter_Leitura(codfuncionario) == 2 )
             p_d.setDisable(false);
        else
            p_d.setDisable(true);
        
             
    }
    
    
    
}
