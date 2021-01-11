/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Estudante;

import Bd.OperacoesBase;
import Validacoes.ValidarFaltas;
import definicoes.DefinicoesData;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import javafx.scene.layout.Pane;
import modelo.Disciplina;
import modelo.Estudante;
import modelo.FaltaAluno;
import modelo.MesAno;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class FaltasAlunoController implements Initializable {
  
    @FXML private ToggleGroup toogle;
    @FXML private RadioButton rb_ausente;
    @FXML private RadioButton rc_atrasado;
    @FXML private TextField txt_nome;
    @FXML private ComboBox<String> cb_disciplina;
    @FXML private TextField txt_hora;
    @FXML private DatePicker data;
    @FXML private TableView<FaltaAluno> tabela;
    @FXML private TableColumn<FaltaAluno, String> coluna_disciplina;
    @FXML private TableColumn<FaltaAluno, String>coluna_situacao;
    @FXML private TableColumn<FaltaAluno, String>coluna_hora;
    @FXML private TableColumn<FaltaAluno, String>coluna_data;
    @FXML private TextField txt_total;
    @FXML private Button adicionar;
    @FXML private Button eliminar;
    @FXML private Button actualizar;
    @FXML private Button imprimir;
    @FXML private TextField txt_pesquisa;
    @FXML private ComboBox<String> cb_pesquisa;
    
    
    
    private static Estudante e;
    private static Pane pane;
    private static String sql;
    private static String nomeUser;
    private static String anolectivo;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        InicializaEstudante();
        DisabilitaCampo_AnoLectivo(); //verifica se o anolectivo passado é o ano actual ou ano passado.Caso seja passado, então ja não pode adicionar faltas neste ano.
        InicializaCampos();
        InicializaPesquisar();
        ConfiguraTabela("select * from view_falta_aluno where codaluno = '"+e.getCodigo()+"' and anolectivo='"+anolectivo+"'");
        DesabilitaActualizar_Eliminar();
    }    

    @FXML
    private void AdicionarSala(ActionEvent event)
    {
        Preencher();
    }

    @FXML
    private void Eliminar(ActionEvent event) 
    {
        ObservableList<FaltaAluno> f = tabela.getSelectionModel().getSelectedItems();
        Alert a = new Alert(AlertType.CONFIRMATION, "Tem a certeza que deseja eliminar esse registro?");
        Optional<ButtonType> acao = a.showAndWait();
        
        if( ButtonType.OK == acao.get())
           EliminarListaSelected(f);
        else
        {
            Alert a1 = new Alert(AlertType.ERROR,"Operação cancelada");
            a1.show();
        }
        ConfiguraTabela("select * from view_falta_aluno where codaluno = '"+e.getCodigo()+"' and anolectivo='"+anolectivo+"'");
        adicionar.setDisable(false);
        DesabilitaActualizar_Eliminar();
    }

    @FXML
    private void Actualizar(ActionEvent event) 
    {
        FaltaAluno f = tabela.getSelectionModel().getSelectedItem();
        if( f != null )
        {
            Actualizar(f);
        }
        ConfiguraTabela("select * from view_falta_aluno where codaluno = '"+e.getCodigo()+"' and anolectivo='"+anolectivo+"'");
        actualizar.setDisable(true);
        adicionar.setDisable(false);
        DesabilitaActualizar_Eliminar();
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
            Alert a = new Alert(AlertType.ERROR,"Existem campos de pesquisa vazios!");
            a.show();
        }
            
        
    }

    @FXML
    private void ConfiguraHora( KeyEvent event )
    {
        if ( Character.isLetter(event.getCharacter().charAt(0))) {
            event.consume();
        }
    }
    
    public static void setE(Estudante e) {
        FaltasAlunoController.e = e;
    }
    
/***************************METODOS AUXILIARES*******************************************************/
    private void InicializaEstudante()
    {
        txt_nome.setText(e.getNome());
    }
    
    private void Preencher()
    {
        if( !ValidarFaltas.EstaoVazios( txt_hora, data, cb_disciplina ) )
        {
            if( DefinicoesData.RegexHora(txt_hora.getText()))
            {
                
                FaltaAluno f = new FaltaAluno();
                f.setCodmatricula(matricula_confirmacao.CodAlunoToCodMatricula(Estudante.NameToCode(txt_nome.getText())));
                f.setCodigo(f.retornaUltimoCodigo());
                f.setCoddisciplina(Disciplina.NameToCode(cb_disciplina.getSelectionModel().getSelectedItem()));
                f.setData(data.getValue());
                f.setHora(txt_hora.getText());
                f.setSituacao(retornaSituacao());
                f.setAno(MesAno.Get_AnoActualCobranca());
                f.setTrimestre(DefinicoesData.retornaTrimestre());
               boolean valor =  f.Adicionar();
                
               if( valor )
               {
                   Alert a = new Alert(Alert.AlertType.INFORMATION,"Registro Adicionado com sucesso.");
                   a.show();
                   Limpar();
                   ConfiguraTabela("select * from view_falta_aluno where codaluno = '"+e.getCodigo()+"' and anolectivo='"+anolectivo+"'");
               }
            }
            else
            {
                Alert a = new Alert(Alert.AlertType.ERROR,"Este formato de hora não é valido.");
                a.show();
            }
        }
        else
        {
              Alert a = new Alert(Alert.AlertType.ERROR,"Existem campos invalidos.");
              a.show();
        }
    }
    
    @FXML
    private void SelecionaDadosTabela( MouseEvent event )
    {
           SetDadosActualizar();
           actualizar.setDisable(false);
           eliminar.setDisable(false);
           adicionar.setDisable(true);
           txt_nome.setDisable(true);
    }
    
    @FXML
    private void Back(MouseEvent event)
    {
          if( e != null )
          {
            PerfilAlunoController.setEstudante(e);
            PerfilAlunoController.setPane(pane);
            PerfilAlunoController.setNomeUser(nomeUser);
            AddPane("/vista/perfilAluno.fxml");
          }
    }
      
    
    
    
    private void InicializaCampos()
    {
        txt_nome.setEditable(false);
        rb_ausente.setSelected(true);
        cb_disciplina.setItems(Disciplina.ListaDisciplinasCurso_Classe(e.getCurso(), e.getClasse()));
        cb_pesquisa.setItems(FXCollections.observableArrayList(Arrays.asList( new String[]{"Disciplina","Data"})));
    }
    
    private void Limpar()
    {
        txt_hora.clear();
        txt_pesquisa.clear();
        txt_total.clear();
        cb_disciplina.getSelectionModel().select(null);
        rb_ausente.setSelected(false);
        rc_atrasado.setSelected(false);
        data.setValue(null);
    }
    
    private String retornaSituacao()
    {
        String valor = "Ausente";
        if(rc_atrasado.isSelected())
            valor = "Atrasado";
        return valor;
    }
    
    public void ConfiguraTabela( String sql )
    {
        SetColunas();
        ResultSet rs = OperacoesBase.Consultar(sql);
        ObservableList<FaltaAluno> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                FaltaAluno f = new FaltaAluno();
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
        txt_total.setText(String.valueOf(FaltaAluno.QuantidadeFalta(anolectivo,Estudante.NameToCode(txt_nome.getText()))));
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
        coluna_situacao.setCellValueFactory( new PropertyValueFactory<>("situacao"));
        coluna_disciplina.setCellValueFactory( new PropertyValueFactory<>("disciplina"));
        coluna_hora.setCellValueFactory( new PropertyValueFactory<>("hora"));
    }

    private void EliminarListaSelected(ObservableList<FaltaAluno> f) 
    {
        for( FaltaAluno falta : f )
        {
            String sql = "delete from falta_Aluno where codigo = '"+falta.getCodigo()+"'";
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
             sql = "select * from falta_aluno where coddisciplina = '"+Disciplina.NameToCode(valor)+"'";
         }
         else
             if( "Data".equalsIgnoreCase(pesquisa))
             {
                 sql = "select * from falta_aluno where data_falta = '"+valor+"'";
             }
         else
                 if( "Situação".equalsIgnoreCase(pesquisa))
                 {
                     sql = "select * from falta_aluno where situacao = '"+valor+"'";
                 }
    }
    
    private void SetDadosActualizar()
    {
        FaltaAluno f = tabela.getSelectionModel().getSelectedItem();
        if( f != null )
        {
            txt_hora.setText(f.getHora());
            cb_disciplina.getSelectionModel().select(f.getDisciplina());
            data.setValue(f.getData());
            rc_atrasado.setSelected(true);
            rb_ausente.setSelected(false);
            if( "Ausente".equalsIgnoreCase(f.getSituacao()) )
            {
                rb_ausente.setSelected(true);
                rc_atrasado.setSelected(false);
            }
            
        }
    }
    
    private void Actualizar( FaltaAluno f )
    {
        if( !ValidarFaltas.EstaoVazios( txt_hora, data, cb_disciplina ) )
        {
            if( DefinicoesData.RegexHora(txt_hora.getText()))
            {
                String actualizar_var = "update falta_aluno set coddisciplina = '"+Disciplina.NameToCode(cb_disciplina.getSelectionModel().getSelectedItem())+"', data_falta = '"+data.getValue()+"',hora = '"+txt_hora.getText()+"', situacao = '"+retornaSituacao()+"' where codigo = '"+f.getCodigo()+"'";
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
                Alert a = new Alert(Alert.AlertType.ERROR,"Este formato de hora não é valido.");
                a.show();
            }
        }
        else
        {
            Alert a = new Alert(Alert.AlertType.ERROR,"Existem campos invalidos.");
            a.show();
        }
    }

      public void AddPane( String path )
    {
         
        try {
            Parent p = FXMLLoader.load( getClass().getResource(path) );
            pane.getChildren().removeAll();
            pane.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(FaltasAlunoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setPane(Pane pane) {
        FaltasAlunoController.pane = pane;
    }

    public static void setNomeUser(String nomeUser) {
        FaltasAlunoController.nomeUser = nomeUser;
    }

    public static void setAnolectivo(String anolectivo) {
        FaltasAlunoController.anolectivo = anolectivo;
    }
     
    private void DisabilitaCampo_AnoLectivo()
    {
        if( Integer.parseInt(anolectivo) < Integer.parseInt(MesAno.Get_AnoActualCobranca()))
        {
            adicionar.setDisable(true);
            txt_hora.setDisable(true);
            cb_disciplina.setDisable(true);
            data.setDisable(true);
        }
    }

    private void DesabilitaActualizar_Eliminar() 
    {
         actualizar.setDisable(true);
         eliminar.setDisable(true);
    }
     
}
