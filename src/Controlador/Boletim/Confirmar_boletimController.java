/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Boletim;

import Bd.OperacoesBase;
import definicoes.DefinicoesImpressaoRelatorio;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.Boletin;
import modelo.Disciplina;
import modelo.Minipauta_Trimestral;
import modelo.Turma;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class Confirmar_boletimController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private static Minipauta_Trimestral mt;
    @FXML
    private TableView<Minipauta_Trimestral> tabela;
    @FXML
    private TableColumn<Minipauta_Trimestral, String> coluna_disciplina;
    @FXML
    private TableColumn<Minipauta_Trimestral, String>coluna_mac;
    @FXML
    private TableColumn<Minipauta_Trimestral, String> coluna_cp;
    @FXML
    private TableColumn<Minipauta_Trimestral, String>coluna_ct;
    @FXML
    private Label lbl_nome;
    @FXML
    private Label lbl_curso;
    @FXML
    private Label lbl_classe;
    @FXML
    private Label lbl_trimestre;
    @FXML
    private Label lbl_anolectivo;
    @FXML
    private Label lbl_turma;
    @FXML
    private AnchorPane Pane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCampos();
        InicializaTabela();
    }    

    public static void setMt(Minipauta_Trimestral mt) {
        Confirmar_boletimController.mt = mt;
    }
    
    
    @FXML
    private void Gerar(ActionEvent event) 
    {
        Inserir_Boletim();
        
        //chama relatorio
        DefinicoesImpressaoRelatorio.ImprimirRelatorio(null, "C:\\RelatorioGenix\\Boletim_Turma.jrxml");
    }
    
    @FXML
    private void Close(MouseEvent event) 
    {
        Stage stage = (Stage)Pane.getScene().getWindow();
        stage.close();
    }
    
    private void InicializaCampos()
    {
        lbl_nome.setText(mt.getNome());
        lbl_curso.setText(mt.getCurso());
        lbl_turma.setText(mt.getTurma());
        lbl_trimestre.setText(mt.getTrimestre());
        lbl_classe.setText(mt.getClasse());
        lbl_anolectivo.setText(mt.getAnolectivo());
    }
    
    private void InicializaTabela()
    {
          Confi_colunas();
          int codturma = Turma.NameToCode(lbl_turma.getText());
          ObservableList<Minipauta_Trimestral> lista = FXCollections.observableArrayList();
        
          for( String disciplina : Minipauta_Trimestral.Disciplinas_Ja_Registradas(Turma.NameToCode(lbl_turma.getText()), lbl_trimestre.getText(), lbl_anolectivo.getText()) )
          {
              Minipauta_Trimestral m = new Minipauta_Trimestral();
              m.setDisciplina(disciplina);
              m.setMac(Minipauta_Trimestral.Obter_Mac(mt.getCodmatricula_c(), Disciplina.NameToCode(disciplina, lbl_curso.getText(),lbl_classe.getText()), lbl_trimestre.getText(), lbl_anolectivo.getText(), codturma));
              m.setCp(Minipauta_Trimestral.Obter_Cp(mt.getCodmatricula_c(), Disciplina.NameToCode(disciplina, lbl_curso.getText(),lbl_classe.getText()), lbl_trimestre.getText(), lbl_anolectivo.getText() , codturma));
              m.setCt(Minipauta_Trimestral.Obter_Ct(mt.getCodmatricula_c(), Disciplina.NameToCode(disciplina, lbl_curso.getText(),lbl_classe.getText()), lbl_trimestre.getText(), lbl_anolectivo.getText(), codturma));
          
              lista.add(m);
          }
          
          tabela.setItems(lista);
    }
    
    private void Confi_colunas()
    {
        coluna_disciplina.setCellValueFactory( new PropertyValueFactory<>("disciplina"));
        coluna_mac.setCellValueFactory( new PropertyValueFactory<>("mac"));
        coluna_ct.setCellValueFactory( new PropertyValueFactory<>("ct"));
        coluna_cp.setCellValueFactory( new PropertyValueFactory<>("cp"));
    }
    
    
    private void Inserir_Boletim()
    {
        //Elimina os dados Existentes
         OperacoesBase.Inserir("truncate table boletin1_4");
        
        int codmatricula_c = mt.getCodmatricula_c();
        String trimestre = mt.getTrimestre();
        String anolectivo = mt.getAnolectivo();
        for( Minipauta_Trimestral m: tabela.getItems() )
        {
            int codigo = Boletin.RetornarUltimoCodigo();
            String cp = m.getCp();
            String mac = m.getMac();
            String ct = m.getCt();
            String disciplina = m.getDisciplina();
            
            Boletin b = new Boletin();
            b.setAno(anolectivo);
            b.setCodmatricula(codmatricula_c);
            b.setCodigo(codigo);
            b.setCp(cp);
            b.setCt(ct);
            b.setMac(mac);
            b.setTrimestre(trimestre);
            b.setDisciplina(disciplina);
            
            b.Adicionar();
        }
    }

    

    
}
