/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Pagamento;

import Bd.OperacoesBase;
import Controlador.Preco.VisualizarprecoController;
import Validacoes.validarPreco;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import definicoes.DefinicoesPane;
import definicoes.DefinicoesUnidadePreco;
import dialogos.Controlador.editarPreco;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import modelo.Classe;
import modelo.Curso;
import modelo.Modelo_privilegio;
import modelo.Preco;
import modelo.Usuario;
import modelo.VisualizaPreco;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class DefinicoesGerais_estudanteController implements Initializable {
    @FXML
    private JFXComboBox<String> curso;
    @FXML
    private JFXComboBox<String> classe;
    @FXML
    private JFXComboBox<String> efeito;
    @FXML
    private JFXTextField txt_valor_multa;
    @FXML
    private JFXTextField valor;
    @FXML private TableColumn<VisualizaPreco, String> efeito_coluna;
    @FXML private TableColumn<VisualizaPreco, String> curso_coluna;
    @FXML private TableColumn<VisualizaPreco, String> classe_coluna;
    @FXML private TableColumn<VisualizaPreco, String> multa_coluna;
    @FXML private TableColumn<VisualizaPreco, String> preco_coluna;
    @FXML private TableView<VisualizaPreco> tabela;
    @FXML
    private Label lbl_erro_valor;
    @FXML
    private Label lbl_erro_multa;
    @FXML
    private Label lbl_total;
    
    
    private VisualizaPreco preco;
    private boolean activo = false;
    @FXML
    private Pane panel_lateral;
    private Label lbl_eliminar;
    @FXML
    private JFXTextField txt_pesquisar;
    @FXML
    private JFXButton btn_adicionar;
    private DefinicoesPane dp = new DefinicoesPane();
    @FXML
    private HBox paneEditarPreco;
    
    private VisualizaPreco objVisualizar;
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCurso();
        InicializaPeriodoEEfeito();
        //InicializarTabela("select * from preco");
       // Verifica_Privilegios();
    }    

    @FXML
    private void InicializaClasse(ActionEvent event) 
    {
        
        String nomecurso = curso.getSelectionModel().getSelectedItem();
        if( nomecurso!= null )
          FiltarAsClasses(nomecurso);
        
    }

    @FXML
    private void SelecionaClasse_InicializaEfeito(ActionEvent event) 
    {
        
        String classe_var = classe.getSelectionModel().getSelectedItem();
        if( classe_var != null )
        {
            int codcurso = Curso.NameToCode(curso.getSelectionModel().getSelectedItem());
            efeito.getItems().removeAll(Preco.listaPreco(codcurso, classe_var));
        }
        else
            InicializaPeriodoEEfeito();
    }

    @FXML
    private void SelecionaEfeito_visualizaMulta(ActionEvent event) 
    {
        String efeito_selected = efeito.getSelectionModel().getSelectedItem();
        if( "Propina".equals(efeito_selected))
            txt_valor_multa.setDisable(false);
        else
        {
            txt_valor_multa.setText("0");
            txt_valor_multa.setDisable(true);
        }
    }

    @FXML
    private void ValidaValor(KeyEvent event)
    {
        if( Character.isLetter(event.getCharacter().charAt(0)))
        {
            event.consume();
            lbl_erro_valor.setVisible(true);
        }
        else
            lbl_erro_valor.setVisible(false);
    }
    
     @FXML
    private void ValidaMulta(KeyEvent event) 
    {
        if( Character.isLetter(event.getCharacter().charAt(0)))
        {
            event.consume();
            lbl_erro_multa.setVisible(true);
        }
        else
            lbl_erro_multa.setVisible(false);
    }
    

    @FXML
    private void Preencher(ActionEvent event) 
    {
        int codcurso = Curso.NameToCode(curso.getSelectionModel().getSelectedItem());
        String classe_preco = classe.getSelectionModel().getSelectedItem();
        String efeito_preco = efeito.getSelectionModel().getSelectedItem();
        String valor_pre = ( !"".equals(valor.getText()))? (valor.getText()) : "0";
        Alert a;
        if( validarPreco.EstaoVazios( classe.getSelectionModel().getSelectedItem() , curso.getSelectionModel().getSelectedItem() , efeito.getSelectionModel().getSelectedItem(), valor.getText() ) )
        {
          
            a = new Alert( Alert.AlertType.ERROR,"Existem campos vazios");
            a.show();
        }
        else
        {
              Preco p = new Preco();
              p.setValor_multa(txt_valor_multa.getText());
              p.setCodigo(p.RetornaUltimoCodigo());
              p.setClasse(classe_preco);
              p.setCodcurso(codcurso);
              p.setEfeito(efeito_preco);
              p.setValor(valor_pre);
             
             if(p.Adicionar())
             {
                 a = new Alert(Alert.AlertType.INFORMATION, "Preço Adicionado com sucesso.");
                 a.show();
                 Limpar();
                 InicializarTabela("select * from preco");
              
             }
             else
             {
                 a = new Alert(Alert.AlertType.ERROR,"Erro ao Adicionar o preço");
                 a.show();
             }
              
        }
    }

    @FXML
    private void Pesquisar(MouseEvent event) 
    {
        Pesquisar();
    }

    @FXML
    private void show_panel_lateral(MouseEvent event) 
    {
       activo =  DefinicoesPane.Habilita_Desabilita_Pane_Lateral(panel_lateral, activo);
    }
    private void submenu_Eliminar(MouseEvent event) 
    {
        VisualizaPreco p = tabela.getSelectionModel().getSelectedItem();
        if( p!= null )
        {
            boolean valor_Eliminar = p.Eliminar();
            if( valor_Eliminar )
                InicializarTabela("select * from preco");
            else
            {
                Alert a = new Alert(Alert.AlertType.ERROR,"Erro ao eliminar o preço.");
                a.show();
            }
             
        }
        activo = DefinicoesPane.Desabilita_Panel_Lateral(panel_lateral, activo);
    }

    @FXML
    private void submenu_showverdetalhes(MouseEvent event) 
    {
         activo = DefinicoesPane.Desabilita_Panel_Lateral(panel_lateral, activo);
    }

    @FXML
    private void submenu_showinfo(MouseEvent event) 
    {
         activo = DefinicoesPane.Desabilita_Panel_Lateral(panel_lateral, activo);
    }
   
    
    
    
//*********************//
    //Metodos Operacionais
//*********************//
     private void InicializaCurso()
    {
        curso.setItems(Curso.ListaCursos());
        
    }
    
    
    private void InicializaPeriodoEEfeito()
    {
        String e[] = {"Matricula", "Propina" ,"Confirmação", "Certificado"};
        efeito.setItems( FXCollections.observableArrayList(Arrays.asList(e)));
    }
    
   
     private void FiltarAsClasses( String curso  )
    {
        ObservableList<String> lista = Classe.ClassesPorCurso(curso);
        classe.setItems(lista);
    }
    
   
    private void Limpar()
    {
        classe.getSelectionModel().select(null);
        curso.getSelectionModel().select(null);
        efeito.getSelectionModel().select(null);
        valor.setText("");
        valor.setDisable(true);
        txt_valor_multa.setText("");
    }

     private void InicializarTabela( String sql )
    {
        //Chama as configuracoes das colunas e cria uma lista que contera os dados
        ObservableList<VisualizaPreco> lista =  FXCollections.observableArrayList();
        SetColunas();
        
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
              
                //instancias da classe BigDecimal
                BigDecimal valor_preco = new BigDecimal(rs.getString("valor"));
                BigDecimal valor_multa = new BigDecimal(rs.getString("valor_multa"));
                
                preco = new VisualizaPreco();
                preco.setCodcurso(rs.getInt("codcurso"));
                preco.setCodigo(rs.getInt("codpreco"));
                preco.setCurso(IntCursoToName(rs.getInt("codcurso")));
                preco.setEfeito(rs.getString("efeito"));
                preco.setValor_tabela("Akz "+valor_preco.toString()+",00");
                preco.setClasse(rs.getString("classe"));
                preco.setValor_multa("Akz "+valor_multa.toString()+",00");
                lista.add(preco);
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarprecoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tabela.setItems(lista);
        lbl_total.setText(""+tabela.getItems().size());
        
    }
    /**
     * Configuracao das colunas......
     */
    private void SetColunas()
    {
         efeito_coluna.setCellValueFactory( new PropertyValueFactory<>("efeito"));
         curso_coluna.setCellValueFactory( new PropertyValueFactory<>("curso"));
         classe_coluna.setCellValueFactory( new PropertyValueFactory<>("classe"));
         multa_coluna.setCellValueFactory( new PropertyValueFactory<>("valor_multa"));
         preco_coluna.setCellValueFactory( new PropertyValueFactory<>("valor_tabela"));
         
        
    }
  
    /**
     * Converter um valor inteiro para o nome
     */
    private String IntCursoToName( int codigo )
    {
        
        String nome = "";
        ResultSet rs = OperacoesBase.Consultar("select nome_curso from curso where codcurso = '"+codigo+"'");
        try {
            while( rs.next() )
            {
               nome = rs.getString("nome_curso");
            }
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarprecoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nome;
    }

    
    public void Pesquisar()
    {
        if( !txt_pesquisar.getText().isEmpty() )
        {
             if( DefinicoesUnidadePreco.ENumero(txt_pesquisar.getText()))
                InicializarTabela("select * from preco where codpreco = '"+txt_pesquisar.getText()+"'");
             else
                 InicializarTabela("select * from preco inner join curso using(codcurso) where (efeito like  '%"+txt_pesquisar.getText()+"%' or nome_curso like  '%"+txt_pesquisar.getText()+"%')");
        }
        else
            InicializarTabela("select * from preco");
       
            
        
    }
    
    
    private void Verifica_Privilegios()
    {
        int codfuncionario = Usuario.Obter_CodFuncionario(Usuario.NameToCode(Usuario.getUsuario_activo()));
        
        if( Modelo_privilegio.Obter_Insercao(codfuncionario) == 2 )
             btn_adicionar.setDisable(false);
        else
            btn_adicionar.setDisable(true);
        
             
    }

    @FXML
    private void CarregarTabela(MouseEvent event) {
        Alert a = new Alert(AlertType.INFORMATION , "Por favor aguarde..");
        a.show();
        activo = DefinicoesPane.Desabilita_Panel_Lateral(panel_lateral, activo);
        InicializarTabela("select * from preco");
        if(this.tabela.getItems().size() > 0)
            a.close();
            
    }

    @FXML
    private void EditarPreco(MouseEvent event) {
        editarPreco.setVisualizarPreco(objVisualizar);
        dp.CallDialogs("editarPreco");
    }

    @FXML
    private void SelecionaTabela(MouseEvent event) {
        this.objVisualizar = tabela.getSelectionModel().getSelectedItem();
        if(objVisualizar != null)
             this.paneEditarPreco.setDisable(false);
        else
            this.paneEditarPreco.setDisable(true);
    }
    
}
