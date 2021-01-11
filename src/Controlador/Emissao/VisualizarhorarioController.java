/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Emissao;

import Bd.OperacoesBase;
import definicoes.DefinicoesAdicionarDialogo;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import modelo.Classe;
import modelo.Curso;
import modelo.Horario;
import modelo.MesAno;
import modelo.Turma;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class VisualizarhorarioController implements Initializable {
   
    @FXML private ComboBox<String> cb_curso;
    @FXML private ComboBox<String> cb_turma;
    @FXML private ComboBox<String> cb_classe;
    @FXML private TableView<Horario> tabela;
    @FXML private TableColumn<Horario, String> coluna_tempo;
    @FXML private TableColumn<Horario, String> coluna_segunda;
    @FXML private TableColumn<Horario, String> coluna_terca;
    @FXML private TableColumn<Horario, String> coluna_quarta;
    @FXML private TableColumn<Horario, String> coluna_quinta;
    @FXML private TableColumn<Horario, String> coluna_sexta;
    @FXML private Button btn_imprimir;
    @FXML private ImageView imagem1;
    
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializarCurso();
    }    

    @FXML
    private void SelecionaOCurso_InicializaClasse(ActionEvent event) 
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        if( curso != null )
            cb_classe.setItems(Classe.ClassesPorCurso(curso));
    }

    @FXML
    private void SelecionaClasse_InicializaTurma(ActionEvent event) 
    {
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        if( classe != null && curso != null )
            cb_turma.setItems(Turma.ListaTurmasRelaClasse_CodCurso(classe, Curso.NameToCode(curso)));
    }
    @FXML
    private void SelecionaTurmaInicializarAno(ActionEvent event) 
    {
        String turma = cb_turma.getSelectionModel().getSelectedItem();
        if( turma != null )
            CarregaTabela("select * from horario where codturma = '"+Turma.NameToCode(turma)+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"'");
    }

   

    @FXML
    private void imprimir(ActionEvent event) 
    {
        Alert a;
        int codturma = Turma.NameToCode(cb_turma.getSelectionModel().getSelectedItem());
        String path = "C:\\RelatorioGenix\\Horario.jrxml";
        String ano = MesAno.Get_AnoActualCobranca();
        if( tabela.getItems().size() > 0 )
        {
            a = new Alert(Alert.AlertType.INFORMATION , "A imprimir o Horario, por favor aguarde!");
            a.show();
            HashMap h = new HashMap();
            h.put("codturma", codturma);
            h.put("anolectivo", ano);
            
            boolean valor = definicoes.DefinicoesImpressaoRelatorio.ImprimirRelatorio(h, path);
            if( valor )
            {
                a.setContentText("Horario gerado com sucesso.");
                a.show();
            }
            else
            {
                a.setAlertType(Alert.AlertType.ERROR);
                 a.setContentText("Erro ao gerar o Horario.");
                 a.show();
            }
        }
    }

    @FXML
    private void AbirInfo(MouseEvent event) 
    {
        DefinicoesAdicionarDialogo d = new DefinicoesAdicionarDialogo();
        d.AddDialogo("/dialogos/adicionarEstudanteAjuda.fxml");
    }
    
/******************************METODOS OPERACIONAIS*****************************************************/
    private void InicializarCurso()
    {
        cb_curso.setItems(Curso.ListaCursos());
    }
    
    
    private void CarregaTabela( String sql )
    {
        ObservableList<Horario> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        ConfiguraColunas();
        try {
            while( rs.next() )
            {
                Horario h = new Horario();
                h.setTempo(rs.getString("tempo"));
                h.setCodturma(rs.getInt("codturma"));
                h.setDia1(rs.getString("dia1"));
                h.setDia2(rs.getString("dia1"));
                h.setDia3(rs.getString("dia1"));
                h.setDia4(rs.getString("dia1"));
                h.setDia5(rs.getString("dia1"));
                
                lista.add(h);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarhorarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tabela.setItems(lista);
    }
    
    private void ConfiguraColunas()
    {
        coluna_tempo.setCellValueFactory( new PropertyValueFactory<>("tempo"));
        coluna_segunda.setCellValueFactory( new PropertyValueFactory<>("dia1"));
        coluna_terca.setCellValueFactory( new PropertyValueFactory<>("dia2"));
        coluna_quarta.setCellValueFactory( new PropertyValueFactory<>("dia3"));
        coluna_quinta.setCellValueFactory( new PropertyValueFactory<>("dia4"));
        coluna_sexta.setCellValueFactory( new PropertyValueFactory<>("dia5"));
    }
}
