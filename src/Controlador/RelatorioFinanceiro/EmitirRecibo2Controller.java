/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.RelatorioFinanceiro;

import Bd.OperacoesBase;
import definicoes.DefinicoesImpressaoRelatorio;
import definicoes.DefinicoesUnidadePreco;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import modelo.EmitirRecibo;
import modelo.MesAno;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class EmitirRecibo2Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private static Pane pane;
    private static int codaluno;
    private static String nomeFuncionario;
    private static LocalDate data;

    
    @FXML
    private TableColumn<EmitirRecibo, String> coluna_descricao;
    @FXML
    private TableColumn<EmitirRecibo, String> coluna_preco;
    @FXML
    private TableColumn<EmitirRecibo, String> coluna_pago;
    @FXML
    private TableColumn<EmitirRecibo, String> coluna_mes;
    @FXML
    private TextField txt_nome;
    @FXML
    private TextField txt_curso;
    @FXML
    private TextField txt_classe;
    @FXML
    private TextField txt_turno;
    @FXML
    private TableView<EmitirRecibo> tabela;
    @FXML
    private Label lbl_multa;
    private static String fpagamento;
    private int total;
    private int total_multa;
    private static String mes;
    @FXML
    private Label lbl_valor_pago;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        Preenche_DadosAluno();
        
    }    

    public static void setCodaluno(int codaluno) {
        EmitirRecibo2Controller.codaluno = codaluno;
    }

    public static void setData(LocalDate data) {
        EmitirRecibo2Controller.data = data;
    }

    public static void setPane(Pane pane) {
        EmitirRecibo2Controller.pane = pane;
    }

    public static void setNomeFuncionario(String nomeFuncionario) {
        EmitirRecibo2Controller.nomeFuncionario = nomeFuncionario;
    }

    public static void setFpagamento(String fpagamento) {
        EmitirRecibo2Controller.fpagamento = fpagamento;
    }

    
    
    private void Preenche_DadosAluno()
    {
        //verifica se os dados nao estao vazios
        if( codaluno != 0 && data != null )
        {
            Pesquisa_Dados(codaluno);
            InicializaTabela();
        }
       
        
    }
    
    
    private void Pesquisa_Dados( int codaluno )
    {
        ResultSet rs = OperacoesBase.Consultar("select * from aluno inner join  matricula_confirmacao using(codaluno) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where codaluno  = '"+codaluno+"'");
        String nome = "";
        String classe = "";
        String turno = "";
        String curso = "";
        try {
            while( rs.next() )
            {
                nome = rs.getString("nome");
                classe = rs.getString("nome_classe");
                curso = rs.getString("nome_curso");
                turno = rs.getString("periodo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmitirReciboController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //adiciona os dados
        txt_nome.setText(nome);
        txt_classe.setText(classe);
        txt_curso.setText(curso);
        txt_turno.setText(turno);
    }
    
    private void InicializaTabela()
    {
        configuraColunas();
        AddDadosNaTabela();
    }
    
    private void configuraColunas()
    {
        
        coluna_descricao.setCellValueFactory( new PropertyValueFactory<>("descricao"));
        coluna_mes.setCellValueFactory( new PropertyValueFactory<>("mes"));
        coluna_pago.setCellValueFactory( new PropertyValueFactory<>("valor"));
        coluna_preco.setCellValueFactory( new PropertyValueFactory<>("preco"));
    }
    
    private void AddDadosNaTabela()
    {
        total = 0;
        EmitirRecibo e;
        ObservableList<EmitirRecibo> lista = FXCollections.observableArrayList();
        ResultSet rs = OperacoesBase.Consultar("select * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) where codaluno = '"+codaluno+"' and data = '"+data+"' and mes_referente = '"+mes+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"'");
        try {
            while( rs.next() )
            {
               e = new EmitirRecibo();
               e.setDescricao(rs.getString("descricao"));
               e.setMes(rs.getString("mes_referente"));
               e.setPreco(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("valor_a_pagar")));
               e.setValor(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(rs.getString("valor_pago")));
               e.setMulta(DefinicoesUnidadePreco.ChangeFromStringToInt(rs.getString("multa")));
               e.setQuant( RetornaQuantidade(codaluno, e.getMes(), data, e.getDescricao()));
               total_multa += e.getMulta();
               total += (DefinicoesUnidadePreco.ChangeFromStringToInt(e.getValor())+(e.getMulta()));
               lista.add(e);
               
               AdicionaNaTabelaRecibo( e.getMes() ,  e.getDescricao() , e.getPreco()  ,e.getQuant(), rs.getInt("codpagamento") , codaluno , e.getMulta());
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmitirReciboController.class.getName()).log(Level.SEVERE, null, ex);
        }
        lbl_multa.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(lista.get(0).getMulta())));
        lbl_valor_pago.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+total));
        tabela.setItems(lista);
    }
    
    
    private int RetornaQuantidade( int codaluno , String mes , LocalDate data , String descricao )
    {
        
        int quantidade = 0;
        ResultSet rs = OperacoesBase.Consultar("select count(codpagamento) as quantidade from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) where descricao = '"+descricao+"' and data = '"+data+"' and codaluno = '"+codaluno+"' and mes_referente = '"+mes+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"' ");
        try {
            while( rs.next() )
            {
                quantidade = rs.getInt("quantidade");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmitirRecibo2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quantidade;
    }
    
    
    @FXML
    public void Recibo( ActionEvent event )
    {
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Gerando o  Recibo, por favor Aguarde");
        a.show();
        HashMap has = new HashMap<>();
        has.put("total", DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(total)));
        has.put("anolectivo", MesAno.Get_AnoActualCobranca());
        String path = "C:\\RelatorioGenix\\Recibo_Mensalidade.jrxml";
        DefinicoesImpressaoRelatorio.ImprimirRelatorio(has, path);
        a.close();
    }
    
    private void AdicionaNaTabelaRecibo( String mes ,   String descricao , String preco  ,int quantidade, int codpagamento , int codAluno, int multa )
    {
        String sql = "insert into recibo values( '"+EmitirRecibo.LastCodeRecibo()+"' , '"+codpagamento+"' , '"+descricao+"' , '"+quantidade+"' , '"+mes+"', '"+codAluno+"', '"+nomeFuncionario+"', '"+DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(preco)+"', '"+DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(preco)+"' ,'"+DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(multa))+"','"+data+"','"+fpagamento+"')";
        OperacoesBase.Inserir(sql);
    }

    public static void setMes(String mes) {
        EmitirRecibo2Controller.mes = mes;
    }
    
    
   
}