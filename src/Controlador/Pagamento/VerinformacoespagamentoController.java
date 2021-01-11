/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Pagamento;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Curso;
import modelo.Estudante;
import modelo.VisualizarPagamento;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class VerinformacoespagamentoController implements Initializable {
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
    @FXML
    private Label txt_turma;
    @FXML
    private Label txt_periodo;
    @FXML
    private Label labl_efeito;
    @FXML
    private Label lbl_mes;
    @FXML
    private Label lbl_ano;
    @FXML
    private Label lbl_preco;
    @FXML
    private Label lbl_valorpago;
    @FXML
    private Label lbl_data;
    @FXML
    private Label lbl_formapagamento;
    @FXML
    private Label lbl_multa;
    @FXML
    private Label txt_nomefuncionario;
    
    private static VisualizarPagamento vpagamento;
    private static String userType;
    private static Pane pane;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        InicializaCampos();
    }    
    
    private void InicializaCampos()
    {
        labl_efeito.setText(vpagamento.getEfeito());
        lbl_ano.setText(vpagamento.getAno());
        lbl_data.setText(String.valueOf(vpagamento.getData()));
        lbl_mes.setText(vpagamento.getMes());
        lbl_preco.setText(vpagamento.getValor_apagar());
        lbl_valorpago.setText(vpagamento.getValor_pago());
        lbl_formapagamento.setText(vpagamento.getFormapagamento());
        txt_nomefuncionario.setText(vpagamento.getNome_funcionario());
        lbl_multa.setText(String.valueOf(vpagamento.getMulta()));
        txt_nome.setText(vpagamento.getNome_aluno());
        txt_bi.setText(Estudante.NameToBi(vpagamento.getNome_aluno()));
        txt_classe.setText(Estudante.codeToClasse(Estudante.NameToCode(vpagamento.getNome_aluno())));
        txt_curso.setText(Curso.NameAlunoNameCurso(vpagamento.getNome_aluno()));
        txt_datanasc.setText(Estudante.codeToDataNasc(Estudante.NameToCode(vpagamento.getNome_aluno())));
        //txt_periodo.setText(Estudante.codeToPeriodo(Estudante.NameToCode(vpagamento.getNome_aluno())));
        txt_turma.setText(vpagamento.getTurma());
        txt_sexo.setText(Estudante.codeToSexo(Estudante.NameToCode(vpagamento.getNome_aluno())));
        InicializaFoto(Estudante.CodeToFoto(Estudante.NameToCode(vpagamento.getNome_aluno())));
    }

    public static void setVpagamento(VisualizarPagamento vpagamento) {
        VerinformacoespagamentoController.vpagamento = vpagamento;
    }

    public static void setPane(Pane pane) {
        VerinformacoespagamentoController.pane = pane;
    }

    public static void setUserType(String userType) {
        VerinformacoespagamentoController.userType = userType;
    }
    
    
    
  @FXML
    public void Back( MouseEvent event )
    {
        pane.getChildren().removeAll();
        
        try {
           
            VisualizarPagamentoController.setPane(pane);
            VisualizarPagamentoController.setTipoUser(userType);
            Parent p = FXMLLoader.load(getClass().getResource("/vista/visualizarPagamento.fxml"));
            
            pane.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(VerinformacoespagamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    private void InicializaFoto(String CodeToFoto) 
    {
         if( !"Sem Foto".equalsIgnoreCase(CodeToFoto))
        {
           String caminho = "/icones/"+CodeToFoto;
           imagem.setImage( new Image(caminho));
        }
        else
             imagem.setImage( new Image("/icones/activeUser.png"));
    }
}
