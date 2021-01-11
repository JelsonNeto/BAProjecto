/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Professor;

import Bd.OperacoesBase;
import Validacoes.ValidarFaltas;
import definicoes.DefinicoesData;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import modelo.Disciplina;
import modelo.Estudante;
import modelo.FaltaAluno;
import modelo.FaltaProfessor;
import modelo.MesAno;
import modelo.Professor;
import Controlador.Estudante.FaltasAlunoController;
import java.time.LocalDate;
import javafx.scene.control.Label;
import modelo.FaltaFuncionario;
import modelo.Funcionario;
import modelo.ProfessorTurma;
import modelo.Turma;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class FaltaprofessorController implements Initializable {

    
    @FXML
    private TextField txt_nome;
    @FXML
    private ComboBox<String> cb_disciplina;
    @FXML
    private DatePicker data;
    @FXML
    private TableView<FaltaProfessor> tabela;
    @FXML
    private TableColumn<FaltaProfessor, String> coluna_disciplina;
    private TableColumn<FaltaProfessor, String> coluna_situacao;
    private TableColumn<FaltaProfessor, String> coluna_hora;
    @FXML
    private TableColumn<FaltaProfessor, String> coluna_data;
    @FXML
    private TextField txt_total;
    @FXML
    private Button adicionar;
    @FXML
    private Button eliminar;
    @FXML
    private Button actualizar;
    @FXML
    private Button imprimir;
    @FXML
    private TextField txt_pesquisa;
    @FXML
    private ComboBox<String> cb_pesquisa;
    
    private static Professor professor;
    private static String sql = "select * from falta_professor";
    @FXML
    private ComboBox<String> cb_turma;
    @FXML
    private Label lbl_erroData;
    @FXML
    private TableColumn<?, ?> coluna_mes;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCampos();
        ConfiguraTabela("select * from falta_funcionario where codfuncionario = '"+professor.getCodfuncionario()+"'");
    }    

    private void ConfiguraHora(KeyEvent event)
    {
         if ( Character.isLetter(event.getCharacter().charAt(0))) {
            event.consume();
        }
    }

    @FXML
    private void SelecionaDadosTabela(MouseEvent event) 
    {
         SetDadosActualizar();
         actualizar.setDisable(false);
         txt_nome.setDisable(true);
           
    }

    @FXML
    private void AdicionarSala(ActionEvent event) 
    {
        Preencher();
    }

    @FXML
    private void Eliminar(ActionEvent event)
    {
        ObservableList<FaltaProfessor> f = tabela.getSelectionModel().getSelectedItems();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Tem a certeza que deseja eliminar esse registro?");
        Optional<ButtonType> acao = a.showAndWait();
        
        if( ButtonType.OK == acao.get())
           EliminarListaSelected(f);
        else
        {
            Alert a1 = new Alert(Alert.AlertType.ERROR,"Operação cancelada");
            a1.show();
        }
        ConfiguraTabela("select * from falta_aluno");
    }

    @FXML
    private void Actualizar(ActionEvent event) 
    {
        FaltaProfessor f = tabela.getSelectionModel().getSelectedItem();
        if( f != null )
        {
            Actualizar(f);
        }
        ConfiguraTabela("select * from falta_professor");
        actualizar.setDisable(true);
    }

    @FXML
    private void pesquisar(MouseEvent event)
    {
        String valor = cb_pesquisa.getSelectionModel().getSelectedItem();
        String t = txt_pesquisa.getText();
        if( valor != null && !"".equalsIgnoreCase(t))
        {
            SetSQL();
            ConfiguraTabela(sql);
        }
        else
        {
            Alert a = new Alert(Alert.AlertType.ERROR,"Existem campos de pesquisa vazios!");
            a.show();
        }
            
    }
     @FXML
    private void SelecionaTurma_inicializaDisciplina(ActionEvent event) 
    {
        String turma = cb_turma.getSelectionModel().getSelectedItem();
        if( turma != null )
        {
            cb_disciplina.setItems(ProfessorTurma.codigoProfessor_to_ListaDisciplinas_porTurma(professor.getCodigo(), Turma.NameToCode(turma), MesAno.Get_AnoActualCobranca()));
        }
                
    }

    public static void setProfessor(Professor professor) {
        FaltaprofessorController.professor = professor;
    }
    
   
    
 /**********************METODOS AUXILIARES************************************************/
    private void InicializaCampos()
    {
        lbl_erroData.setVisible(false);
        txt_nome.setText(professor.getNome());
        cb_turma.setItems(ProfessorTurma.Lista_Turmas_JaAssociada_Professor(professor.getCodigo(),MesAno.Get_AnoActualCobranca()));
    }
    
    
    
     private void Preencher()
    {
        Alert a;
        
        if( !ValidarFaltas.EstaoVazios( cb_turma, data, cb_disciplina ) )
        {    
            int codigo = FaltaFuncionario.UltimoCodigo();
            int codfuncionario = Funcionario.NametoCode(txt_nome.getText());
            LocalDate data_valor =  data.getValue();
            String trimestre = DefinicoesData.retornaTrimestre(String.valueOf(data_valor));
            String ano_lectivo  = MesAno.Get_AnoActualCobranca();
            int codmes = DefinicoesData.RetornaMes(String.valueOf(data_valor));
            int estado_falta = 0;
            
            //Obtendo os dados das datas a fim de verifica-las
            int ano1 = Integer.parseInt(DefinicoesData.RetornaAnoData(String.valueOf(data_valor)));
            int ano_armazenado = Integer.parseInt(DefinicoesData.RetornaAnoData(FaltaFuncionario.Ultima_Data_Registrada()));
            int mes1 = DefinicoesData.RetornaMes(String.valueOf(data_valor));
            int mes_armazenado = DefinicoesData.RetornaMes(FaltaFuncionario.Ultima_Data_Registrada());
            int dia1 = DefinicoesData.RetornaDiaData(String.valueOf(data_valor));
            int dia_armazenado = DefinicoesData.RetornaDiaData(FaltaFuncionario.Ultima_Data_Registrada());
                
              //verifica se a falta ja foi cadastrada nesta data para esse funcionario
            if( !FaltaFuncionario.Verificar_Falta_Existe(codfuncionario, data_valor) )
            {
                //verifica se os anos sao iguais
                if( ano1 == ano_armazenado || ano1 > ano_armazenado )
                {
                    if( mes1 == mes_armazenado || mes1 > mes_armazenado)
                    {
                        if( dia1 > dia_armazenado )
                        {
                            FaltaFuncionario f = new FaltaFuncionario(codigo, codfuncionario, data_valor, trimestre, ano_lectivo,codmes,estado_falta);
                            if(f.Adicionar())
                            {
                                a = new Alert(Alert.AlertType.INFORMATION, "Falta Registrada com sucesso");
                                a.show();
                                Limpar();
                                lbl_erroData.setVisible(false);
                            }
                            else{
                                 a = new Alert(Alert.AlertType.ERROR, "Erro  ao registrar a falta");
                                 a.show();
                            }
                        }
                        else
                        {
                           lbl_erroData.setText("O dia esta incorrecto");
                           lbl_erroData.setVisible(true);
                        }
                    }
                    else{
                       
                            lbl_erroData.setText("O mês esta incorrecto");
                            lbl_erroData.setVisible(true);
                    }
                }
                else
                {
                           lbl_erroData.setText("O ano esta incorrecto");
                           lbl_erroData.setVisible(true);
                }
               
            }
            else
            {
                a = new Alert(Alert.AlertType.WARNING,"Esta falta ja foi registrada");
                a.show();
                lbl_erroData.setVisible(false);
            }
            
        }
        else
        {
              a = new Alert(Alert.AlertType.ERROR,"Existem campos invalidos.");
              a.show();
        }
    }
    
     
     private void Limpar()
    {
        txt_pesquisa.clear();
        txt_total.clear();
        cb_disciplina.getSelectionModel().select(null);
        cb_turma.getSelectionModel().clearSelection();
        data.setValue(null);
    }
    
 
    public void ConfiguraTabela( String sql )
    {
        SetColunas();
        ResultSet rs = OperacoesBase.Consultar(sql);
        ObservableList<FaltaProfessor> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                FaltaProfessor f = new FaltaProfessor();
                f.setCodigo(rs.getInt("codigo"));
                f.setSituacao(rs.getString("situacao"));
                f.setDisciplina(Disciplina.CodeToName(rs.getInt("coddisciplina")));
                f.setData(DefinicoesData.StringtoLocalDate(rs.getString("data_falta")));
                f.setHora(rs.getString("hora"));
                
                lista.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FaltasAlunoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        txt_total.setText(String.valueOf(FaltaProfessor.QuantidadeFalta()));
        tabela.setItems(lista);
        
         if( tabela.getItems().size() > 0 )
        {
            imprimir.setDisable(false);
            actualizar.setDisable(false);
            eliminar.setDisable(false);
        }
        else
         {
             imprimir.setDisable(true);
             actualizar.setDisable(true);
             eliminar.setDisable(true);
         }
        
    }
    
    private void SetColunas()
    {
        coluna_data.setCellValueFactory( new PropertyValueFactory<>("data"));
        coluna_disciplina.setCellValueFactory( new PropertyValueFactory<>("disciplina"));
        //coluna_.setCellValueFactory( new PropertyValueFactory<>("hora"));
    }

    
    private void EliminarListaSelected(ObservableList<FaltaProfessor> f) 
    {
        for( FaltaProfessor falta : f )
        {
            String sql = "delete from falta_professor where codigo = '"+falta.getCodigo()+"'";
            OperacoesBase.Eliminar(sql);
        }
    }
    
     private void InicializaPesquisar()
    {
        String valor[] = {"Data", "Disciplina", "Situação"};
        cb_pesquisa.setItems(FXCollections.observableArrayList(Arrays.asList(valor)));
    }
    
    private void SetSQL()
    {
         String valor = txt_pesquisa.getText();
         String pesquisa = cb_pesquisa.getSelectionModel().getSelectedItem();
         if( "Disciplina".equalsIgnoreCase(pesquisa) )
         {
             sql = "select * from falta_professor where coddisciplina = '"+Disciplina.NameToCode(valor)+"'";
         }
         else
             if( "Data".equalsIgnoreCase(pesquisa))
             {
                 sql = "select * from falta_professor where data_falta = '"+valor+"'";
             }
         else
                 if( "Situação".equalsIgnoreCase(pesquisa))
                 {
                     sql = "select * from falta_professor where situacao = '"+valor+"'";
                 }
    }
    
    private void SetDadosActualizar()
    {
        FaltaProfessor f = tabela.getSelectionModel().getSelectedItem();
        if( f != null )
        {
            cb_disciplina.getSelectionModel().select(f.getDisciplina());
            data.setValue(f.getData());
        }
    }
    
    private void Actualizar( FaltaProfessor f )
    {
        if( !ValidarFaltas.EstaoVazios( cb_turma, data, cb_disciplina ) )
        {
               String actualizar_var = "update falta_professor set coddisciplina = '"+Disciplina.NameToCode(cb_disciplina.getSelectionModel().getSelectedItem())+"', data_falta = '"+data.getValue()+"'where codigo = '"+f.getCodigo()+"'";
                if( OperacoesBase.Actualizar(actualizar_var))
                {
                    Alert a = new Alert(Alert.AlertType.INFORMATION,"Actualização efectuada com sucesso!");
                    a.show();
                    Limpar();
                    txt_nome.setDisable(false);
                }
                else
                {
                    Alert a = new Alert(Alert.AlertType.ERROR,"Ocorreu um erro ao actualizar!");
                    a.show();
                    Limpar();
                    txt_nome.setDisable(false);
                }
            
        }
        else
        {
            Alert a = new Alert(Alert.AlertType.ERROR,"Existem campos invalidos.");
            a.show();
        }
    }

   
    
     
}
