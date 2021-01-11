/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioFinanceiro;

import Bd.OperacoesBase;
import Bd.conexao;
import definicoes.DefinicoesData;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Classe;
import modelo.Estudante;
import modelo.MesAno;
import modelo.Pagamento;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class RelatorioAnual2Controller implements Initializable {
    
    @FXML private TableColumn<Pagamento, Integer> coluna_pagamento;
    @FXML private TableColumn<Pagamento, String> coluna_descricao;
    @FXML private TableColumn<Pagamento, String> coluna_preco;
    @FXML private TableColumn<Pagamento, String> coluna_valorPago;
    @FXML private ComboBox<String> cb_curso;
    @FXML private ComboBox<String> cb_classe;
    @FXML private ComboBox<String> cb_aluno;
    @FXML private TextField txt_cedulaBi;
    @FXML private TextField txt_nome;
    @FXML private TableView<Pagamento> tabela;
    @FXML private TableColumn<Pagamento, String> coluna_mes;

    @FXML private Button imprimir;
    @FXML private Label total;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        IncializaCurso();
        imprimir.setDisable(true);
    }    

    @FXML
    private void SelecionaCurso_InicializaClasse(ActionEvent event)
    {
        
        String nome_curso = cb_curso.getSelectionModel().getSelectedItem();
        if( !"".equals(nome_curso) )
        {
            cb_classe.setDisable(false);
            cb_classe.setItems(Classe.ClassesPorCurso(nome_curso));
            
        }
        else
            cb_classe.setDisable(true);
    }

    @FXML
    private void SelecionaClasse_InicializaMes_e_Alunos(ActionEvent event)
    {
        String meses_array[]= null;//Array principal
        String meses_normais[] = { "Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro"};//meses normais
        String meses_exames[] = { "Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro","Dezembro"};//meses das classes de exame
        
        if( EClassedeExame())//verifica se a classe do aluno selecionado e de exame
            meses_array = meses_exames; //se for, atribui a refencia do array de exames ao array principal
        else
            meses_array = meses_normais; //se nao for,atribui a referencia do array normal ao array principal
       
        if( !"".equals(cb_classe.getSelectionModel().getSelectedItem()) )
        {
           
            cb_aluno.setDisable(false);
            Inicializa_Aluno();
        }
        else
        {
            cb_aluno.setDisable(true);
        }
    }

    @FXML
    private void SelecionaAluno_InicializaBi_e_Nome_Tabela(ActionEvent event)
    {
        String nome = cb_aluno.getSelectionModel().getSelectedItem();
        if( !"".equals(nome))
        {
              //elimina os dados
            OperacoesBase.Eliminar("truncate table extractoAnualAluno");
            txt_nome.setText(nome);
            txt_cedulaBi.setText(Estudante.NameToBi(nome));
            ConfiguraTabela(Estudante.NameToCode(nome));
            
            if( tabela.getItems().isEmpty())
                imprimir.setDisable(true);
            else
                imprimir.setDisable(false);
        }
    }

    @FXML
    private void ExtractoAnual(ActionEvent event) 
    {
        Alert a = new Alert(AlertType.INFORMATION,"A gerar o relatório, por favor aguarde...");
        a.show();
        String path = "C:\\RelatorioGenix\\extratoAluno.jrxml";
        int codaluno = Estudante.NameToCode(cb_aluno.getSelectionModel().getSelectedItem());
        String ano = MesAno.Get_AnoActualCobranca();
        HashMap h = new HashMap<>();
        h.put("codaluno", codaluno);
        h.put("ano", ano);
        CallEmitirRelatorio(h, path);
    }
    
/***********************************Metodos Auxiliares do Extracto anual do aluno***************************************************************/
     private void RetornaAlunos(String classe)
    {

        ResultSet rs = OperacoesBase.Consultar("select nome from aluno where classe = '"+classe+"' order by nome asc");
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RelatorioAnual2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        cb_aluno.setItems(lista);

    }
   
     /**
     * Este metodo foi criado para fazer a inicializacao dos cursos disponiveis na comboBox referente aos cursos
     * A comboBox cb_curso, tera estes cursos a sua disposição
     */
    private void IncializaCurso()
    {
        
        ResultSet rs = OperacoesBase.Consultar("select nome_curso from curso order by nome_curso");//Faz a consulta na base de dados
        ObservableList<String> lista = FXCollections.observableArrayList();//cria uma lista observavel em que lhe seraadicionada os cursos cadastrados na base de dados
        try {
            while( rs.next() )
            {
               lista.add(rs.getString("nome_curso"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RelatorioAnual2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cb_curso.setItems(lista);//Adiciona os cursos da base de dados
    }//Fim do metodo
    
    private boolean EClassedeExame()
    {
        return "9ª classe".equalsIgnoreCase(cb_classe.getSelectionModel().getSelectedItem()) || "6ª classe".equalsIgnoreCase(cb_classe.getSelectionModel().getSelectedItem()) || "12ª classe".equalsIgnoreCase(cb_classe.getSelectionModel().getSelectedItem());
    }//fim do metodo
    
    
    private void Inicializa_Aluno()
    {
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        if( !"".equals(classe) )
        {
            RetornaAlunos( classe );
        }
        else
            cb_aluno.setDisable(true);
    }
  
    
    
   private void ConfiguraTabela( int codaluno)
   {
       SetColunas();
        int valorTotal = 0;
        ResultSet rs = OperacoesBase.Consultar("select * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno)  where codaluno = '"+codaluno+"'");
        ObservableList<Pagamento> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                Pagamento p = new Pagamento();
                
                p.setCodigo(rs.getInt("codpagamento"));
                p.setEfeito(rs.getString("descricao"));
                p.setValor_apagar(DefinicoesUnidadePreco.ChangeFromStringToInt(rs.getString("valor_a_pagar")));
                p.setValor_Pago(DefinicoesUnidadePreco.ChangeFromStringToInt(rs.getString("valor_pago")));
                p.setMes(rs.getString("mes_referente"));
                //p.setCodAluno(rs.getInt("codaluno"));
                p.setData(DefinicoesData.StringtoLocalDate(rs.getString("data")));
                p.setAno(rs.getString("ano_referencia"));
                valorTotal +=p.getValor_Pago();
                
                //AdicionaExtractoAnual(p.getCodigo(),p.getCodAluno() , p.getEfeito() , p.getMes() , p.getValor_apagar(), p.getValor_Pago() , p.getData() , p.getAno());
                lista.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RelatorioAnual2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tabela.setItems(lista);
        String somaUnidade = DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(valorTotal));
        total.setText(String.valueOf(somaUnidade));
   }
    
    
    private void SetColunas()
    {
        coluna_pagamento.setCellValueFactory( new PropertyValueFactory<>("codigo"));
        coluna_descricao.setCellValueFactory( new PropertyValueFactory<>("efeito"));
        coluna_preco.setCellValueFactory( new PropertyValueFactory<>("valor_apagar"));
        coluna_valorPago.setCellValueFactory( new PropertyValueFactory<>("valor_Pago"));
        coluna_mes.setCellValueFactory( new PropertyValueFactory<>("mes"));
    }
    
  

    private void AdicionaExtractoAnual(int codpagamento, int codaluno ,  String efeito, String mes, int valor_apagar, int valor_Pago , LocalDate data , String ano)
    {
              
      String sql = "insert into extractoAnualAluno values( '"+Pagamento.UltimocodeExtratoAnualAluno()+"' , '"+codpagamento+"' ,  '"+codaluno+"' , '"+efeito+"', '"+mes+"' , '"+valor_apagar+"' ,'"+valor_Pago+"', '"+data+"', '"+ano+"'  )";      
      OperacoesBase.Inserir(sql);
    }
    
    private boolean CallEmitirRelatorio( HashMap parametros , String path  )
   {
       
       try
        {
            conexao.Conectar();
            JasperReport report = JasperCompileManager.compileReport(path);
            
            JasperPrint print = JasperFillManager.fillReport(report, parametros, conexao.ObterConection()) ;
            JasperViewer jv = new JasperViewer(print , false);
            jv.setVisible(true);
            jv.toFront();
            
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
       conexao.TerminarConexao();
       return false;
       
   }
   
}
