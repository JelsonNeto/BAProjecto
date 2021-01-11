/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Pagamento;

import Controlador.RelatorioFinanceiro.EmitirReciboController;
import Bd.OperacoesBase;
import com.jfoenix.controls.JFXButton;
import definicoes.DefinicoesGerais;
import definicoes.DefinicoesUnidadePreco;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Curso;
import modelo.Estudante;
import modelo.MesAno;
import modelo.RegistroUsuario;
import modelo.Turma;
import modelo.Usuario;
import modelo.VisualizarPagamento;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class VisualizarPagamentoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    private static Pane pane;
    @FXML
    private TableView<VisualizarPagamento> tabela;
    @FXML
    private TableColumn<VisualizarPagamento, String> coluna_nome;
    @FXML
    private TableColumn<VisualizarPagamento, String> coluna_bi;
    @FXML
    private TableColumn<VisualizarPagamento, String> coluna_curso;
    @FXML
    private TableColumn<VisualizarPagamento, String> coluna_turma;
    @FXML
    private TableColumn<VisualizarPagamento, String> coluna_efeito;
    @FXML
    private TableColumn<VisualizarPagamento, String> coluna_preco;
    @FXML
    private TableColumn<VisualizarPagamento, String> coluna_pago;
    @FXML
    private TableColumn<VisualizarPagamento, String> coluna_mes;
    @FXML
    private TableColumn<VisualizarPagamento, LocalDate> coluna_data;
    @FXML
    private TableColumn<VisualizarPagamento, String> coluna_funcionario;
    @FXML
    private ComboBox<String> cb_opcoesPesquisa;
    @FXML
    private TextField txt_pesquisa;
    @FXML
    private Button actualizar;
    @FXML
    private Button Eliminar;
    
    private VisualizarPagamento vpagamento;
    private static ObservableList<String> meses;
    private static ObservableList<Integer> lista_codigo;
    private static ObservableList<String> lista_descricao;
    private static ObservableList<Integer> lista_preco;
     private static ObservableList<Integer> lista_multa;
    private static ObservableList<Integer> lista_valorPago;
    private static String tipoUser;
    private static String nomeUser;
    private static int indiceParticionamento;
    private static String fpagamento;
    @FXML
    private JFXButton btn_recibo;
    @FXML
    private JFXButton btn_detalhes;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
        tabela.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        meses = FXCollections.observableArrayList();
        lista_codigo = FXCollections.observableArrayList();
        lista_descricao = FXCollections.observableArrayList();
        lista_multa = FXCollections.observableArrayList();
        lista_preco = FXCollections.observableArrayList();
        lista_valorPago = FXCollections.observableArrayList();
        InicializaOpcoesPesquisa();
        configuraColunas();
        //CheckTipo();
        //Verifica_Privilegios();
    }    

    @FXML
    private void back(MouseEvent event) {
        
        
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/vista/homePagamento.fxml"));
            pane.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(VisualizarPagamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML
    private void GerarRecibo(ActionEvent event)
    {
        ObservableList<VisualizarPagamento> lista = tabela.getSelectionModel().getSelectedItems();
        for( VisualizarPagamento vpagamento_var : lista )
        {
            meses.add(vpagamento_var.getMes());
            lista_codigo.add(vpagamento_var.getCodpagamento());
            lista_descricao.add(vpagamento_var.getEfeito());
            lista_preco.add(DefinicoesUnidadePreco.StringToInteger(vpagamento_var.getValor_apagar()));
            lista_valorPago.add(DefinicoesUnidadePreco.StringToInteger(vpagamento_var.getValor_pago()));
            lista_multa.add(vpagamento_var.getMulta());
            EmitirReciboController.setCodaluno(Estudante.NameToCode(vpagamento_var.getNome_aluno()));
            EmitirReciboController.setNomefuncionario(vpagamento_var.getNome_funcionario());
            EmitirReciboController.setData(vpagamento_var.getData());
            fpagamento = vpagamento_var.getFormapagamento();
        }
           EmitirReciboController.id = 1;
           EmitirReciboController.setLista_Multa(lista_multa);
           EmitirReciboController.setLista_descricao(lista_descricao);
           EmitirReciboController.setLista_preco(lista_preco);
           EmitirReciboController.setLista_valorPago(lista_valorPago);
           EmitirReciboController.setFpagamento(tipoUser);
           EmitirReciboController.setMes(meses);
           EmitirReciboController.setFpagamento(fpagamento);
           EmitirReciboController.setLista_codigo(lista_codigo);
           EmitirReciboController.setPane(pane);
           CallEmitirRecibo();
    }
    

    public static  void setPane(Pane p) {
          pane = p;
    }
    
    
    
    private void InicializaTabela()
    {
       PreencheCamposPagamento("select * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where anolectivo = '"+MesAno.Get_AnoActualCobranca()+"' order by nome asc");
    }
    
    private void configuraColunas()
    {
      
        coluna_bi.setCellValueFactory( new PropertyValueFactory<>("bi"));
        coluna_nome.setCellValueFactory( new PropertyValueFactory<>("nome_aluno"));
        coluna_data.setCellValueFactory( new PropertyValueFactory<>("data"));
        coluna_mes.setCellValueFactory( new PropertyValueFactory<>("mes"));
        coluna_preco.setCellValueFactory( new PropertyValueFactory<>("valor_apagar"));
        coluna_pago.setCellValueFactory( new PropertyValueFactory<>("valor_pago"));
        coluna_efeito.setCellValueFactory( new PropertyValueFactory<>("efeito"));
        coluna_turma.setCellValueFactory( new PropertyValueFactory<>("turma"));
        coluna_funcionario.setCellValueFactory( new PropertyValueFactory<>("nome_funcionario"));
        coluna_curso.setCellValueFactory( new PropertyValueFactory<>("curso"));
      
    }
    
    
    private void PreencheCamposPagamento( String sql )
    {
        
        ObservableList<VisualizarPagamento> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                vpagamento = new VisualizarPagamento();
                vpagamento.setBi(Estudante.NameToBi(Estudante.CodeToName(rs.getInt("codaluno"))));
                vpagamento.setMes(rs.getString("mes_referente"));
                vpagamento.setData(StringtoLocalDate(rs.getString("data")));
                vpagamento.setNome_funcionario(Usuario.CodeToName(rs.getInt("cod_funcionario")));
                vpagamento.setNome_aluno(Estudante.CodeToName(rs.getInt("codaluno")));
                vpagamento.setValor_apagar(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("valor_a_pagar")));
                vpagamento.setValor_pago(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("valor_pago")));
                vpagamento.setEfeito(rs.getString("descricao"));
                vpagamento.setTurma(Turma.CodeToName(Turma.NomeAlunoToCodTurma(Estudante.CodeToName(rs.getInt("codaluno")))));
                vpagamento.setCurso(Curso.NameAlunoNameCurso(Estudante.CodeToName(rs.getInt("codaluno"))));
                vpagamento.setCodpagamento(rs.getInt("codpagamento"));
                vpagamento.setAno(rs.getString("ano_referencia"));
                vpagamento.setFormapagamento(rs.getString("forma_pagamento_valor"));
                vpagamento.setMulta(DefinicoesUnidadePreco.ChangeFromStringToInt(rs.getString("multa")));
                vpagamento.setCodescola(rs.getString("codigo_escola"));
                
                lista.add(vpagamento);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VisualizarPagamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       // ParticionaElementos( lista );
        tabela.setItems(lista);
        if( !DefinicoesGerais.TemDadosTabela(tabela))
        {
            tabela.setDisable(true);
            Alert a = new Alert(AlertType.INFORMATION,"Nenhum pagamento registrado");
            a.setTitle("Tabela Vazia");
            a.show();
        }
        else
            tabela.setDisable(false);
    }
    
    
    private  LocalDate StringtoLocalDate( String date )
    {
        String dados[] = date.split("-");
        int ano = Integer.parseInt(dados[0]);
        int dia = Integer.parseInt(dados[2]);
        int mes = Integer.parseInt(dados[1]);
        
        return   LocalDate.of(ano, mes, dia);
        
    }
   
    public static void setTipoUser(String tipoUser) {
        VisualizarPagamentoController.tipoUser = tipoUser;
    }
    
    public void CheckTipo()
    {
        if( Usuario.Retorna_Privilegio_Eliminacao()==2)
            Eliminar.setDisable(false);
        else
            Eliminar.setDisable(true);
        if( Usuario.Retorna_Privilegio_Actualizacao()==2)
            actualizar.setDisable(false);
        else
            actualizar.setDisable(true);
    }

    @FXML
    private void actualizar(ActionEvent event) 
    {
        VisualizarPagamento vp = tabela.getSelectionModel().getSelectedItem();
        if( vp != null )
        {
            EfectuarPagamento2Controller.setVp(vp);
            EfectuarPagamento2Controller.setPane(pane);
            EfectuarPagamento2Controller.setNomeuser(nomeUser);
            AddPane("/vista/efectuarPagamento2.fxml");
        }
        
    }

    @FXML
    private void Eliminar(ActionEvent event) 
    {
        Alert a = new Alert(AlertType.CONFIRMATION,"Deseja continuar?");
        Optional<ButtonType> opcao = a.showAndWait();
        if( ButtonType.OK == opcao.get())
        {
            ObservableList<VisualizarPagamento> listap = tabela.getSelectionModel().getSelectedItems();
            for( VisualizarPagamento p : listap ) 
            {
                OperacoesBase.Eliminar("delete from pagamento where codpagamento = '"+p.getCodpagamento()+"'");
                RegistroUsuario.AddRegistro("Eliminou o registro de pagamento dados :Aluno"+p.getNome_aluno()+":Efeito:"+p.getEfeito()+":Data:"+String.valueOf(p.getData()));
            }
            InicializaTabela();
            
        }
       
    }

    @FXML
    private void verInformacoes(ActionEvent event) {
        
        
        VisualizarPagamento p = tabela.getSelectionModel().getSelectedItem();
        if( p != null )
        {
            VerinformacoespagamentoController.setUserType(tipoUser);
            VerinformacoespagamentoController.setVpagamento(p);
            VerinformacoespagamentoController.setPane(pane);
            AddPane("/vista/verinformacoespagamento.fxml");
        }
        
    }
    
    @FXML
    private void Pesquisar( MouseEvent event )
    {
        Selecao();
    }

    @FXML
    private void TabelaClicada(MouseEvent event) 
    {
        VisualizarPagamento vp = tabela.getSelectionModel().getSelectedItem();
        if( vp != null )
            HabilitaDisabilitaBtns(false);
        else
            HabilitaDisabilitaBtns(true);
    }
    
    public static void setNomeUser(String nomeUser) {
        VisualizarPagamentoController.nomeUser = nomeUser;
    }
    
    
 /*******************************METODOS AUXILIARES*************************************/   
    private void AddPane( String path)
    {    
        try {
            Parent p = FXMLLoader.load( getClass().getResource(path) );
            pane.getChildren().removeAll();
            pane.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(VisualizarPagamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void InicializaOpcoesPesquisa()
    {
        String opcoes[] = {"Código estudante","Nome aluno" , "Ano" , "Mês", "Data", "Turma"};
        cb_opcoesPesquisa.setItems( FXCollections.observableArrayList(Arrays.asList(opcoes)));
    }
    
    private void Selecao()
    {
        String sql= "";
        String valor = cb_opcoesPesquisa.getSelectionModel().getSelectedItem();
        if( "Nome Aluno".equalsIgnoreCase(valor))
            sql = "select * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) where nome = '"+txt_pesquisa.getText()+"' and anolectivo= '"+MesAno.Get_AnoActualCobranca()+"' order by nome asc";
        else
            if("Ano".equalsIgnoreCase(valor))
                sql = "select * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) where ano_referencia = '"+txt_pesquisa.getText()+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"'  order by nome asc ";
        else
                if("Mês".equalsIgnoreCase(valor))
                    sql = "select * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) where mes_referente = '"+txt_pesquisa.getText()+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"' order by nome asc ";
        else
                    if("Data".equalsIgnoreCase(valor))
                        sql = "select * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) where data = '"+txt_pesquisa.getText()+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"' order by nome asc ";
        else
                        if("Turma".equalsIgnoreCase(valor))
                            sql = "select * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) inner join turma using(codturma) where nome_turma = '"+txt_pesquisa.getText()+"' and ano_referencia = '"+MesAno.Get_AnoActualCobranca()+"' order by nome asc ";
        else
                            if("Código estudante".equalsIgnoreCase(valor))
                                sql = "select * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) where codigo_escola = '"+txt_pesquisa.getText()+"' and anolectivo= '"+MesAno.Get_AnoActualCobranca()+"'";
        
        if(!"".equalsIgnoreCase(sql) && !"".equalsIgnoreCase(txt_pesquisa.getText()))
        {
            configuraColunas();
            PreencheCamposPagamento(sql);
        }
        else
        {
            Alert a = new Alert(AlertType.WARNING,"Campos de Pesquisa vazios.");
            a.show();
        }
    }
    

    private void CallEmitirRecibo() {
        
         //elimina os dados que estao nesta tabela
        OperacoesBase.Eliminar("truncate table recibo");
        
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/vista/emitirRecibo.fxml"));
            pane.getChildren().removeAll();
            pane.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(VisualizarPagamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @FXML
    private void CarregarTabela(ActionEvent event) 
    {
        InicializaTabela();
    }

    @FXML
    private void PreviuosDados(MouseEvent event) {
    }

    @FXML
    private void NextDados(MouseEvent event) {
    }

    private void ParticionaElementos(ObservableList<VisualizarPagamento> lista) 
    {
        ObservableList<VisualizarPagamento> elementos;
        if( lista.size() > 50 && lista.size() <=100 )
        {
            elementos = FXCollections.observableArrayList();
            for( int i= 0 ; i< 50; i++ )
                elementos.add(lista.get(i));
        }
    }
    
    private void Verifica_Privilegios()
    {
        
        if( Usuario.Retorna_Privilegio_Eliminacao() == 2 )
             Eliminar.setDisable(false);
        else
            Eliminar.setDisable(true);
        if( Usuario.Retorna_Privilegio_Actualizacao() == 2)
            actualizar.setDisable(false);
        else
            actualizar.setDisable(true);
                
             
    }
    
    private void HabilitaDisabilitaBtns( boolean valor )
    {
           btn_detalhes.setDisable(valor);
            btn_recibo.setDisable(valor);
            Eliminar.setDisable(valor);
    }

    

    
}
