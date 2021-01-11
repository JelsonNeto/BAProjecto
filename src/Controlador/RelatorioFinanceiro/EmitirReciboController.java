/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.RelatorioFinanceiro;

import Bd.OperacoesBase;
import Controlador.Pagamento.VisualizarPagamentoController;
import definicoes.DefinicoesImpressaoRelatorio;
import definicoes.DefinicoesPane;
import definicoes.DefinicoesUnidadePreco;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.EmitirRecibo;
import modelo.Estudante;
import modelo.MesAno;
import modelo.RegistroUsuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class EmitirReciboController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private static Pane pane;
    private static int codaluno;
    private static LocalDate data;
    private static String nomefuncionario;
    private static String descricao;
    private static int preco;
    private static int valor_pago;
    private static int multa;
    private static ObservableList<Integer> Lista_codigo;
    private static ObservableList<String> Lista_meses;
    private static ObservableList<String> lista_descricao;
    private static ObservableList<Integer> Lista_preco;
    private static ObservableList<Integer> Lista_valorPago;
    private static ObservableList<Integer> Lista_Multa;
    public static  int id=0;
    private static int contador = 0;
    private static int total;
    private static int total_multa;
    private static String  fpagamento;
    private DefinicoesPane dp;
    
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
    private TableColumn<EmitirRecibo, String> coluna_multa;
    @FXML
    private Label lbl_valor_pago;
    @FXML
    private Label lbl_multa;
    private String anolectivo;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        Preenche_DadosAluno();
        dp = new DefinicoesPane(pane);
    }    

    public static void setCodaluno(int codaluno) {
        EmitirReciboController.codaluno = codaluno;
    }

    public static void setData(LocalDate data) {
        EmitirReciboController.data = data;
    }

    public static void setPane(Pane pane) {
        EmitirReciboController.pane = pane;
    }

    public static void setNomefuncionario(String nomefuncionario) {
        EmitirReciboController.nomefuncionario = nomefuncionario;
    }
    
    
    public static void setMes(ObservableList<String> mes_recibo) 
    {
        Lista_meses = mes_recibo;
    }

    public static void setLista_Multa(ObservableList<Integer> Lista_Multa) {
        EmitirReciboController.Lista_Multa = Lista_Multa;
    }

    public static void setLista_descricao(ObservableList<String> lista_descricao) {
        EmitirReciboController.lista_descricao = lista_descricao;
    }

    public static void setLista_meses(ObservableList<String> Lista_meses) {
        EmitirReciboController.Lista_meses = Lista_meses;
    }

    public static void setLista_preco(ObservableList<Integer> Lista_preco) {
        EmitirReciboController.Lista_preco = Lista_preco;
    }

    public static void setLista_valorPago(ObservableList<Integer> Lista_valorPago) {
        EmitirReciboController.Lista_valorPago = Lista_valorPago;
    }

    public static void setFpagamento(String fpagamento) {
        EmitirReciboController.fpagamento = fpagamento;
    }
   
    
    
 /*******************************************METODOS PRINCIPAIS***********************/
    
    
    private void Preenche_DadosAluno()
    {
        //verifica se os dados nao estao vazios
        if( codaluno != 0 && data != null )
        {
            if( id == 0 )
            {  Pesquisa_Dados(codaluno);
               InicializaTabela();
            }
            else
            {
                Pesquisa_Dados(codaluno);
                InicializaTabela_Lista();
            }
        }
       
    }
    
    /**
     * @Definição do metodo....Este metodo tem como função pesquisar os dados do aluno atravez do seu codigo.
     * E preenche os campos com os dados dos alunos..
     * @param codaluno
     */
    private void Pesquisa_Dados( int codaluno )
    {
        ResultSet rs = OperacoesBase.Consultar("select * from aluno inner join matricula_confirmacao using(codaluno) inner join turma using(codturma) inner join curso using(codcurso) inner join classe using(codclasse) where codaluno  = '"+codaluno+"'");
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
                this.anolectivo = rs.getString("anolectivo");
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
    
    private void InicializaTabela_Lista()
    {
        configuraColunas();
        AddDadosNaTabela_Lista();
    }
    
    private void configuraColunas()
    {
        coluna_descricao.setCellValueFactory( new PropertyValueFactory<>("descricao"));
        coluna_mes.setCellValueFactory( new PropertyValueFactory<>("mes"));
        coluna_pago.setCellValueFactory( new PropertyValueFactory<>("valor"));
        coluna_preco.setCellValueFactory( new PropertyValueFactory<>("preco"));
        coluna_multa.setCellValueFactory( new PropertyValueFactory<>("multa_S"));
    }
    
    private void AddDadosNaTabela()
    {
        total = 0;
        EmitirRecibo e = null;
        ObservableList<EmitirRecibo> lista = FXCollections.observableArrayList();
       // ResultSet rs = OperacoesBase.Consultar("select * from pagamento where codaluno = '"+codaluno+"' and data = '"+data+"' and descricao ='"+descricao+"'");
       // try {
         //   while( rs.next() )
           // {
          for( String mes  : Lista_meses )
          {   
               e = new EmitirRecibo();
               e.setDescricao(descricao);
               e.setMes(mes);
               e.setPreco(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(preco)));
               e.setValor(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(valor_pago)));
               e.setQuant( RetornaQuantidade(codaluno, e.getMes(), data, e.getDescricao()));
              // e.setMulta_S((Lista_Multa.size()>1)?DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+Lista_Multa.get(contador)):"0");
               e.setMulta(multa);
               String v_multa = (!e.getDescricao().equalsIgnoreCase("Propina"))?"-":DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+e.getMulta());
               lista.add(e);
               total_multa += e.getMulta();
               total += valor_pago;
               AdicionaNaTabelaRecibo( e.getMes() ,  e.getDescricao() , e.getPreco() ,  e.getQuant(), Lista_codigo.get(contador++) , codaluno , nomefuncionario, v_multa);
               
          }
          if( e != null )
              RegistroUsuario.AddRegistro("Emitiu o recibo do aluno :"+Estudante.CodeToName(codaluno)+" referente a "+descricao );
          tabela.setItems(lista);
          lbl_multa.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+total_multa));
          lbl_valor_pago.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+(total+total_multa)));
          contador = 0;
        }
    
     private void AddDadosNaTabela_Lista()
    {
       total = 0;
       total_multa = 0;
        EmitirRecibo e = null;
        ObservableList<EmitirRecibo> lista = FXCollections.observableArrayList();
       // ResultSet rs = OperacoesBase.Consultar("select * from pagamento where codaluno = '"+codaluno+"' and data = '"+data+"' and descricao ='"+descricao+"'");
       // try {
         //   while( rs.next() )
           // {
          for( String mes  : Lista_meses )
          {   
               e = new EmitirRecibo();
               e.setDescricao(lista_descricao.get(contador));
               e.setMes(mes);
               e.setPreco(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(Lista_preco.get(contador))));
               e.setValor(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(Lista_valorPago.get(contador))));
               e.setMulta(Lista_Multa.get(contador));
               e.setMulta_S(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+Lista_Multa.get(contador)));
               String v_multa = (!e.getDescricao().equalsIgnoreCase("Propina"))?"-":DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+e.getMulta());
               lista.add(e);
               total_multa += e.getMulta();
               total+= Lista_valorPago.get(contador);
               AdicionaNaTabelaRecibo( e.getMes() ,  e.getDescricao() , e.getPreco() ,  e.getQuant(), Lista_codigo.get(contador++) , codaluno , nomefuncionario, v_multa);
              
          }
          if( e != null )
               RegistroUsuario.AddRegistro("Emitiu o recibo do aluno :"+Estudante.CodeToName(codaluno)+" referente a "+descricao );
           tabela.setItems(lista);
           lbl_multa.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+total_multa));
           lbl_valor_pago.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(""+(total+total_multa)));
           contador = 0;
           id = 0;
}
    
    
    private int RetornaQuantidade( int codaluno , String mes , LocalDate data , String descricao )
    {
        
        int quantidade = 0;
        ResultSet rs = OperacoesBase.Consultar("select count(codpagamento) as quantidade from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno) where descricao = '"+descricao+"' and data = '"+data+"' and codaluno = '"+codaluno+"' and mes_referente = '"+mes+"' ");
        try {
            while( rs.next() )
            {
                quantidade = rs.getInt("quantidade");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmitirReciboController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quantidade;
    }
    
    
    @FXML
    public void Recibo( ActionEvent event )
    {
        boolean valor;
        Alert a = new Alert(AlertType.INFORMATION, "Gerando o  Recibo, por favor Aguarde");
        a.show();
        HashMap has = new HashMap<>();
        has.put("codaluno", codaluno);
        has.put("data", data);
        has.put("total", DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(total+total_multa)));
        has.put("anolectivo",this.anolectivo);
        if( multa ==  0 )
            valor = DefinicoesImpressaoRelatorio.ImprimirRelatorio(has,"C:\\RelatorioGenix\\recibo_fcb.jrxml" );//Sem multa
        else
            valor = DefinicoesImpressaoRelatorio.ImprimirRelatorio(has,"C:\\RelatorioGenix\\recibo_fcb.jrxml" );
        if( !valor )
        {
            a = new Alert(AlertType.ERROR, "Erro ao gerar o Recibo");
            a.show();
        }
        /*
        try {
            Desktop.getDesktop().open(new File(file));
        } catch (IOException ex) {
            Logger.getLogger(EmitirReciboController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
 
    private void AdicionaNaTabelaRecibo( String mes ,   String descricao , String preco , int quantidade, int codpagamento , int codAluno , String nomef, String multa  )
    {
        String sql = "insert into recibo values( '"+EmitirRecibo.LastCodeRecibo()+"' , '"+codpagamento+"' , '"+descricao+"' , '"+quantidade+"' ,  '"+mes+"' , '"+codAluno+"', '"+nomef+"',  '"+preco+"' ,  '"+preco+"' , '"+multa+"','"+data+"','"+fpagamento+"')";
        OperacoesBase.Inserir(sql);
        
    }

    public static void setDescricao(String descricao) {
        EmitirReciboController.descricao = descricao;
    }

    public static void setPreco(int preco) {
        EmitirReciboController.preco = preco;
    }

    public static void setValor_pago(int valor_pago) {
        EmitirReciboController.valor_pago = valor_pago;
    }

    public static void setLista_codigo(ObservableList<Integer> Lista_codigo) {
        EmitirReciboController.Lista_codigo = Lista_codigo;
    }

    
    
    public static void setMulta(int multa) {
        EmitirReciboController.multa = multa;
    }
    
    private void AddPane( String path)
    {    
        try {
            Parent p = FXMLLoader.load( getClass().getResource(path) );
            pane.getChildren().removeAll();
            pane.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(EmitirReciboController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void back(MouseEvent event) 
    {
        VisualizarPagamentoController.setPane(pane);
        dp.setPath("/vista/visualizarPagamento.fxml");
        dp.AddPane();
    }
    
   
}