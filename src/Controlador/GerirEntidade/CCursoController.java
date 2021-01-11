/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.GerirEntidade;

import Bd.OperacoesBase;
import Validacoes.validarCurso;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Classe;
import modelo.Curso;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class CCursoController implements Initializable {

      
    @FXML private TableView<Curso> tabela;
    @FXML private TableColumn<Curso, String> coluna_nome;
    @FXML private TextField nome;
    @FXML private Button actualizar;
    @FXML private Button adicionar;
    @FXML private TableColumn<Curso, Integer> coluna_codigo;
    @FXML private ComboBox<String> cb_nivel;
    private Curso curso;

    private static String Armazena_nivel;
    
    /**
     * Initializes the controller class.
     */
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        LoadTabela();
        Inicializa_ComboNivel();
        nome.setDisable(true);
    }    
    
    @FXML
     public void SelecionaNivel(ActionEvent event) {
        String nivel = cb_nivel.getSelectionModel().getSelectedItem();
        if ("Ensino Secundário IIº Ciclo".equalsIgnoreCase(nivel)) {
            nome.setText("");
            nome.setDisable(false);
        } else {
            nome.setText(nivel);
            nome.setDisable(true);
        }
    }
     
    @FXML
    public void AdicionarCurso(ActionEvent event) {
        PreencherCampos();

    }

    /**
     * Este metodo foi creiando para ajudar no preenchimento dos campos da
     * janela . Não recebe qualquer um argumento.
     */
    private void PreencherCampos() {

        curso = new Curso();
        Alert a;
        if (validarCurso.EstaoVazios(nome, cb_nivel.getSelectionModel().getSelectedItem())) {
             a = new Alert(AlertType.ERROR,"Exisem campos Vazios");
             a.show();
        } else {

            if (!validarCurso.VerificaExistencia(nome.getText())) {

                curso.setNome(nome.getText());
                curso.setCodigo(curso.RetornarUltimoCodTurma());

                //limpa os campos
                nome.clear();
                if(curso.Adicionar()) //adiciona na base de dados
                {
                     a = new Alert(AlertType.INFORMATION,"Curso cadastrado com sucesso");
                     a.show();
                     LoadTabela();
                     Classe.InserirClasseBd();
                }
                else
                {
                    a = new Alert(AlertType.ERROR,"Erro ao tentar cadastrar o curso");
                    a.show();
                }
               Inicializa_ComboNivel();
            } else {
                a = new Alert(AlertType.ERROR,"Curso ja cadastrado");
                a.show();
            }

        }
    }

    private void LoadTabela() {

        ConfigurarColunas(); //Colunas da tabela ja configuradas
        ObservableList<Curso> lista = FXCollections.observableArrayList();

        //Consulta
        ResultSet rs = OperacoesBase.Consultar("Select * from Curso");
        try {
            while (rs.next()) {
                curso = new Curso();

                //Preenchimento
                curso.setNome(rs.getString("nome_curso"));
                curso.setCodigo(rs.getInt("codcurso"));

                lista.add(curso);

            }

            tabela.setItems(lista);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private void ConfigurarColunas() {

        coluna_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        coluna_codigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));

    }

    @FXML
    public void Editar(ActionEvent e) {
        
        Alert a;
        curso = tabela.getSelectionModel().getSelectedItem();
        if (curso != null) {

            nome.setText(curso.getNome());
            tabela.getSelectionModel().select(null); //disfaz a selecao
            actualizar.setDisable(false);
            adicionar.setDisable(true);

        } else {
           a = new Alert(AlertType.ERROR,"Exisem campos Vazios");
           a.show();
        }

    }

    @FXML
    public void Actualizar(ActionEvent event) {

        Alert a;
        boolean valor = OperacoesBase.Actualizar("update curso set nome_curso = '" + this.nome.getText() + "' where codcurso = '" + curso.getCodigo() + "'");
        if (valor) {
            a = new Alert(AlertType.INFORMATION,"Actualização efectuada com sucesso");
            a.show();
            Inicializa_ComboNivel();
        } else {
            a = new Alert(AlertType.ERROR,"Erro ao tentar actualizar o curso");
            a.show();
        }
        actualizar.setDisable(true);
        adicionar.setDisable(false);
        Limpar();
        LoadTabela();

    }

    @FXML
    public void Eliminar(ActionEvent e){
        
        Alert a;
        curso = tabela.getSelectionModel().getSelectedItem();
      if (curso != null) {
            if(!OperacoesBase.Eliminar("delete  from curso where codcurso= '" + curso.getCodigo() + "'"))
            {
                a = new Alert(AlertType.ERROR,"Exisem campos Vazios");
                a.show();
            }
            LoadTabela();
            Inicializa_ComboNivel();
        } else {
            a = new Alert(AlertType.ERROR,"Selecione a tabela");
            a.show();
        }
      
     
    }

    private void Limpar() {
        nome.setText("");
    }

    private void Inicializa_ComboNivel() {

        String niveis[] = {"Primária", "Ensino Secundário Iº Ciclo", "Ensino Secundário IIº Ciclo"};
        cb_nivel.setItems(FXCollections.observableArrayList(Arrays.asList(niveis)));

        for (String valor : validarCurso.retornaCurso()) {
            for (String valor_i : niveis) {
                if (valor.equalsIgnoreCase(valor_i)) {
                    cb_nivel.getItems().remove(valor);
                    break;
                }

            }
        }

    }
  
}
