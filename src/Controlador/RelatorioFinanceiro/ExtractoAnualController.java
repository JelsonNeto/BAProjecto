/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioFinanceiro;

import Bd.OperacoesBase;
import definicoes.DefinicoesData;
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
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class ExtractoAnualController implements Initializable {
  
    
    @FXML private TableColumn<Pagamento, Integer> coluna_pagamento;
    @FXML private TableColumn<Pagamento, String> coluna_descricao;
    @FXML private TableColumn<Pagamento, String> coluna_preco;
    @FXML private TableColumn<Pagamento, String> coluna_valorPago;
    @FXML private TableView<Pagamento> tabela;
    @FXML private TableColumn<Pagamento, String> coluna_mes;
    @FXML private ComboBox<String> cb_curso;
    @FXML private ComboBox<String> cb_classe;
    @FXML private ComboBox<String> cb_aluno;
    @FXML private TextField txt_cedulaBi;
    @FXML private TextField txt_nome;
    @FXML private Button imprimir;
    @FXML private Label total;

    
    private ObservableList<Pagamento> lista_Pagamento_Ireport;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        IncializaCurso();
        imprimir.setDisable(true);
        lista_Pagamento_Ireport = FXCollections.observableArrayList();
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
        Alert a = new Alert(Alert.AlertType.INFORMATION,"A gerar o relatório, por favor aguarde...");
        a.show();
        String path = "C:\\RelatorioGenix\\extractoPagamentoAluno.jrxml";
        AdicionarTabela_Extracto();
        HashMap h = new HashMap<>();
        DefinicoesImpressaoRelatorio.ImprimirRelatorio(h, path);
    }
    
/***************************METODOS OPERACIONAIS*************************************************/
    
      private void RetornaAlunos(String classe)
    {

        ResultSet rs = OperacoesBase.Consultar("select nome from aluno inner join matricula_confirmacao using(codaluno) inner join pagamento using(codmatricula_c) inner join turma using(codturma) inner join classe using(codclasse) where descricao !='Propina' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"' and nome_classe = '"+classe+"' order by nome asc");
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
        ResultSet rs = OperacoesBase.Consultar("select * from pagamento inner join matricula_confirmacao using(codmatricula_c) inner join aluno using(codaluno)  where codaluno = '"+codaluno+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"'");
        ObservableList<Pagamento> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                Pagamento p = new Pagamento();
                
                p.setCodigo(rs.getInt("codpagamento"));
                p.setEfeito(rs.getString("descricao"));
                p.setValor_apagar((DefinicoesUnidadePreco.ChangeFromStringToInt(rs.getString("valor_a_pagar"))));
                p.setValor_Pago(DefinicoesUnidadePreco.ChangeFromStringToInt(rs.getString("valor_pago")));
                p.setMes(rs.getString("mes_referente"));
                p.setData(DefinicoesData.StringtoLocalDate(rs.getString("data")));
                p.setAno(rs.getString("ano_referencia"));
                p.setValorPago(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(p.getValor_Pago())));
                p.setValorApagar(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(p.getValor_apagar())));
                p.setCodmatricula_c(matricula_confirmacao.CodAlunoToCodMatricula(codaluno));
                valorTotal +=p.getValor_Pago();
                //AdicionaExtractoAnual(p.getCodigo(),p.getCodAluno() , p.getEfeito() , p.getMes() , p.getValor_apagar(), p.getValor_Pago() , p.getData() , p.getAno());
                lista.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RelatorioAnual2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Utilizada para adicionar os valores do pagamento na tabela de extratcto anula para o ireport
        lista_Pagamento_Ireport = lista;
        tabela.setItems(lista);
        String somaUnidade = DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(valorTotal));
        total.setText(String.valueOf(somaUnidade));
   }
    
    
    private void SetColunas()
    {
        coluna_pagamento.setCellValueFactory( new PropertyValueFactory<>("codigo"));
        coluna_descricao.setCellValueFactory( new PropertyValueFactory<>("efeito"));
        coluna_preco.setCellValueFactory( new PropertyValueFactory<>("valorApagar"));
        coluna_valorPago.setCellValueFactory( new PropertyValueFactory<>("valorPago"));
        coluna_mes.setCellValueFactory( new PropertyValueFactory<>("mes"));
    }
    
  
    private void AdicionarTabela_Extracto()
    {
      //Elimina os dados ja existentes
        OperacoesBase.Eliminar("Truncate table extractoanualAluno");
      lista_Pagamento_Ireport.forEach(e->{
          String sql = "Insert into extractoAnualAluno values('"+Pagamento.UltimocodeExtratoAnualAluno()+"', '"+e.getCodigo()+"', '"+e.getCodmatricula_c()+"', '"+e.getEfeito()+"', '"+e.getMes()+"','"+e.getData()+"', '"+e.getAno()+"', '"+total.getText()+"','"+e.getValorApagar()+"', '"+e.getValorPago()+"')";
          OperacoesBase.Inserir(sql);
      });
      
    }
    
}
