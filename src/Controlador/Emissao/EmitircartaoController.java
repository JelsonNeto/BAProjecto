/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Emissao;

import definicoes.DefinicoesAdicionarDialogo;
import definicoes.DefinicoesImpressaoRelatorio;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import modelo.Classe;
import modelo.Curso;
import modelo.Estudante;
import modelo.Pagamento;
import modelo.Turma;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class EmitircartaoController implements Initializable {
    
    @FXML private TableColumn<Estudante, String> nome;
    @FXML private TableColumn<Estudante, String>  bi;
    @FXML private TableColumn<Estudante, LocalDate>  datanasc;
    @FXML private TableColumn<Estudante, Integer>  sala;
    @FXML private TableColumn<Estudante, String>  periodo;
    @FXML private TableColumn<Estudante, String>  foto;
    @FXML private ComboBox<String> cb_curso;
    @FXML private ComboBox<String> cb_turma;
    @FXML private ComboBox<String> cb_classe;
    @FXML private ComboBox<String> cb_ano;
    @FXML private Label lbl_total;
    @FXML private TableView<Estudante> tabela;
    @FXML
    private ImageView imagem1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializarCursos();
    }    

   
    @FXML
    private void SelecionaOCurso_InicializaClasse(ActionEvent event) 
    {
       String curso = cb_curso.getSelectionModel().getSelectedItem();
       if(curso != null)
           cb_classe.setItems(Classe.ClassesPorCurso(curso));
    }

    @FXML
    private void SelecionaClasse_InicializaTurma(ActionEvent event) 
    {
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        if( classe != null )
            cb_turma.setItems(Turma.ListaTurmasRelaClass(classe));
    }
    
    @FXML
    private void SelecionaTurmaInicializarAno(ActionEvent event) 
    {
        String turma = cb_turma.getSelectionModel().getSelectedItem();
        if( turma != null )
         cb_ano.setItems(matricula_confirmacao.ListaDosAnosMatriculados());
    }

    @FXML
    private void SelecionaAnoInicializarTabelaAluno(ActionEvent event) 
    {
        String ano = cb_ano.getSelectionModel().getSelectedItem();
        if( ano != null)
            InicializaTabela();
    }
    
    @FXML
    private void emitir(ActionEvent event)
    {
        Alert a  = new Alert(AlertType.INFORMATION,"A gerar o(s) catão(ões),por favor aguarde.");
        a.show();
        String path = "C:\\RelatorioGenix\\CartaoEscolar.jrxml";
        HashMap h = new HashMap();
        h.put("codturma", Turma.NameToCode(cb_turma.getSelectionModel().getSelectedItem()));
        h.put("anolectivo",cb_ano.getSelectionModel().getSelectedItem());
        boolean valor = DefinicoesImpressaoRelatorio.ImprimirRelatorio(h, path);
        if( valor )
        {
            a= new Alert(AlertType.INFORMATION,"Cartão(ões) gerado(s) com sucesso.");
            a.show();
        }
    }
    
    @FXML
    private void AbirInfo(MouseEvent event)
    {
        DefinicoesAdicionarDialogo d = new DefinicoesAdicionarDialogo();
        d.AddDialogo("/dialogos/adicionarEstudanteAjuda.fxml");
    }


/**************************************METODOS OPERACIONAIS**********************************************/
    private void InicializarCursos()
    {
        cb_curso.setItems(Curso.ListaCursos());
    }
    
    private void InicializaTabela()
    {
        ConfigurarColunas();
        
        String turma = cb_turma.getSelectionModel().getSelectedItem();
        String ano = cb_ano.getSelectionModel().getSelectedItem();
        String sql = "select * from aluno inner join matricula_confirmacao using(codaluno) inner join pagamento using(codmatricula_c) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where descricao != 'Propina' and codturma = '"+Turma.NameToCode(turma)+"' and anolectivo = '"+ano+"'";
        ObservableList<Estudante> matriculados_confirmados = Pagamento.ListaAlunosMatriculados(sql);
        
        tabela.setItems(matriculados_confirmados);
        lbl_total.setText(String.valueOf(matriculados_confirmados.size()));
        
    }
    
    private void ConfigurarColunas()
    {
       bi.setCellValueFactory( new PropertyValueFactory<>("cedula_bi"));
       datanasc.setCellValueFactory( new PropertyValueFactory<>("datanas"));
       nome.setCellValueFactory( new PropertyValueFactory<>("nome"));
       periodo.setCellValueFactory( new PropertyValueFactory<>("periodo"));
       sala.setCellValueFactory( new PropertyValueFactory<>("sala"));
       foto.setCellValueFactory( new PropertyValueFactory<>("foto"));
    }

   
   
}
