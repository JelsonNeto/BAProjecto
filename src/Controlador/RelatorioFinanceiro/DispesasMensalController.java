/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioFinanceiro;

import com.jfoenix.controls.JFXComboBox;
import definicoes.DefinicoesUnidadePreco;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import modelo.Meses;
import modelo.Pagamento_Salario;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class DispesasMensalController implements Initializable {

    @FXML
    private Label lbl_v_dg;
    @FXML
    private Label lbl_v_professor;
    @FXML
    private JFXComboBox<String> cb_anolectivo;
    @FXML
    private Label lbl_v_dp;
    @FXML
    private Label bl_v_sec;
    @FXML
    private Label lbl_v_c;
    @FXML
    private Label lbl_v_l;
    @FXML
    private Label lbl_v_s;
    @FXML
    private Label lbl_total;
    @FXML
    private JFXComboBox<String> cb_mes;
    @FXML
    private Label lbl_v_admin;
    @FXML
    private Label lbl_v_coordenador;
    @FXML
    private Label lbl_v_sub_Admin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Inicializa_Campos();
    }    

    @FXML
    private void Seleciona_Ano_inicializaCampos(ActionEvent event) 
    {
         
        String ano = cb_anolectivo.getSelectionModel().getSelectedItem();
        String mes = cb_mes.getSelectionModel().getSelectedItem();
        if( ano != null && mes != null )
        {
            SetCampos(mes,ano);
        }
        
    }
    
    @FXML
    private void Seleciona_mes_inicializaAno(ActionEvent event) 
    {
          String mes = cb_mes.getSelectionModel().getSelectedItem();
          if( mes != null )
              InicializaAno();
    }
    
    
/*******************************************************************************/
    
     private void Inicializa_Campos()
    {
        cb_mes.setItems(Meses.Listar_Meses());
    }
     
     private void InicializaAno()
     {
           cb_anolectivo.setItems(matricula_confirmacao.ListaDosAnosMatriculados());
     }
    
    private void SetCampos(String mes, String ano )
    {
      //  String funcao[] ={"Secretário(a) Pedagógico(a)","Chefe de secretaria","Jardineiro","Motorista","Cozinheira","Limpeza", "Segurança","Vigilante"};
        int valorp = Pagamento_Salario.TotalSalario_por_funcao("Professor(a)",mes ,ano);
        int valorl = Pagamento_Salario.TotalSalario_por_funcao("Limpeza",mes ,ano);
        int valordg = Pagamento_Salario.TotalSalario_por_funcao("Director geral", mes, ano);
        int valordp = Pagamento_Salario.TotalSalario_por_funcao("Director Pedagógico",mes, ano);
        int valor_sec = Pagamento_Salario.TotalSalario_por_funcao("Secretário(a)", mes,ano);
        int valor_cont = Pagamento_Salario.TotalSalario_por_funcao("Contabilista", mes,ano);
        int valors = Pagamento_Salario.TotalSalario_por_funcao("Segurança", mes,ano);
        int valor_coord = Pagamento_Salario.TotalSalario_por_funcao("Coordenador(a)", mes,ano);
        int d_adamin = Pagamento_Salario.TotalSalario_por_funcao("Director Administrativo", mes,ano);
        int sub_admin = Pagamento_Salario.TotalSalario_por_funcao("Subdirector Administrativo", mes,ano);
        int total = valordg+valorl+valorp+valors+valor_sec+valor_cont+valordp+valor_coord+d_adamin+sub_admin;
        
        lbl_v_dg.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+valordg));
        lbl_v_professor.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+valorp));
        lbl_v_l.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+valorl));
        lbl_v_s.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+valors));
        lbl_v_dp.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+valordp));
        lbl_v_c.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+valor_cont));
        bl_v_sec.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+valor_sec));
        lbl_v_coordenador.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+valor_coord));
        lbl_v_admin.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+d_adamin));
        lbl_v_sub_Admin.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+sub_admin));
        
        lbl_total.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+total));
    }

    @FXML
    private void Imprimir(MouseEvent event) {
    }

    
    
}
