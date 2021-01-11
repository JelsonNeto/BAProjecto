/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioFinanceiro;

import definicoes.DefinicoesUnidadePreco;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import modelo.Classe;
import modelo.Curso;
import modelo.MesAno;
import modelo.Pagamento;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class ArrecadadosClasseController implements Initializable {
    @FXML
    private ComboBox<String> cb_curso;
    @FXML
    private Label total_matricula;
    @FXML
    private Label total_propina;
    @FXML
    private Label total_geral;
    @FXML
    private Label txt_ano;
    @FXML
    private ComboBox<String> cb_classe;
    @FXML
    private Label total_confirmacao;
    
    
    private static int t_matricula = 0;
    private static int t_propina = 0;
    private static int t_confirmacao;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCursos();
    }    

    @FXML
    private void SelecionaCurso_InicializaClasse(ActionEvent event) 
    {
         String curso = cb_curso.getSelectionModel().getSelectedItem();
         cb_classe.setItems(Classe.ClassesPorCurso(curso));
    }
    
     @FXML
    private void SelecionaClasse_FazerCalculos(ActionEvent event)
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        if( curso != null  && classe != null )
        {
            TotalPagamentoMatricula();
            TotalPagamentoPropina();
            TotalPagamentoConfirmacao();
            TotalGeral();
            txt_ano.setText(MesAno.Get_AnoActualCobranca());
        }
    }
    
/******************************METODOS OPERACIONAIS******************************************************/
   
    private void InicializaCursos()
    {
        cb_curso.setItems(Curso.ListaCursos());
    }
    
    
    private void TotalPagamentoConfirmacao()
    {
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        ObservableList<Pagamento> listaMatriculaP = Pagamento.PagamentosReferente_Descricao_Actual_Classe(classe,"Confirmação"); //Ano corrente
        int soma = 0;
        for( Pagamento p : listaMatriculaP )
        {
            soma += p.getValor_Pago();
        }
       t_confirmacao = soma;
        total_confirmacao.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(soma)));
    }
    
    private void TotalPagamentoMatricula()
    {
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        ObservableList<Pagamento> listaMatriculaP = Pagamento.PagamentosReferente_Descricao_Actual_Classe(classe,"Matricula"); //Ano corrente
        int soma = 0;
        for( Pagamento p : listaMatriculaP )
        {
            soma += p.getValor_Pago();
        }
        t_matricula = soma;
        total_matricula.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(soma)));
    }
    
    
    private void TotalPagamentoPropina()
    {
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        ObservableList<Pagamento> listaMatriculaP = Pagamento.PagamentosReferentePropinaAnoActual_Classe(classe); //Ano corrente
        int soma = 0;
        for( Pagamento p : listaMatriculaP )
        {
            soma += p.getValor_Pago();
        }
        t_propina = soma;
        total_propina.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(soma)));
    }
    
    private void TotalGeral()
    {
        int soma = t_matricula + t_propina+t_confirmacao;
        total_geral.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(soma)));
    }

   
}
