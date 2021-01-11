/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioFinanceiro;

import Bd.OperacoesBase;
import definicoes.DefinicoesUnidadePreco;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Classe;
import modelo.Classe_Curso;
import modelo.Curso;
import modelo.Estudante;
import modelo.Pagamento;
import modelo.Preco;
import modelo.RelatorioGlobal;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class DadosglobaismensalController implements Initializable {
    
    
    @FXML private TableColumn<RelatorioGlobal, String> coluna_classeGlobal;
    @FXML private TableColumn<RelatorioGlobal, String> coluna_propinaGlobal;
    @FXML private TableColumn<RelatorioGlobal, Integer> coluna_alunospagosGlobal;
    @FXML private TableColumn<RelatorioGlobal, Integer> coluna_alunosnaopagosGlobal;
    @FXML private TableColumn<RelatorioGlobal, String> coluna_valoresnpagosGlobal;
    @FXML private TableColumn<RelatorioGlobal, String> coluna_valoresPagoGlobal;
    @FXML private TableColumn<RelatorioGlobal, String> coluna_cursoGlobal;
    @FXML private TableColumn<RelatorioGlobal, Integer> coluna_totalalunoGlobal;
    @FXML private TableColumn<RelatorioGlobal, String> coluna_totalGlobal;
    @FXML private TableView<RelatorioGlobal> tabelaGlobal;
    @FXML private ComboBox<String> cb_periodoGlobal;
    @FXML private ComboBox<String> cb_mes;
    @FXML private TableColumn<?, ?> coluna_totalaluno;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Inicializa_Periodo();
    }    

     @FXML
    private void SelecionaPeriodo_InicializaMes(ActionEvent event) 
    {
        
       String periodo = cb_periodoGlobal.getSelectionModel().getSelectedItem();
       if( periodo != null )
       {
          cb_mes.setItems( FXCollections.observableArrayList(Arrays.asList("Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro","Dezembro")));
       }
    }
    
    @FXML
    private void SelecionaMes_InicializaTabela(ActionEvent event) 
    {
       String mes = cb_mes.getSelectionModel().getSelectedItem();
       String periodo = cb_periodoGlobal.getSelectionModel().getSelectedItem();
       if( mes != null )
       {
           //Elimina os dados JA Existentes na tabela 
           OperacoesBase.Eliminar("truncate table relatorioGlobal");
           //Alert
           Alert a = new Alert(AlertType.INFORMATION,"Processando..");
           a.setTitle("Processando");
           a.show();
           InicializaTabela(mes , periodo);
           a.close();
       }
    }

    @FXML
    private void Imprimir(ActionEvent event) 
    {
        
    }

/************************************METODOS OPERACIONEIS*********************************************/
     private void ConfiguraColunas()
    {
        coluna_classeGlobal.setCellValueFactory( new PropertyValueFactory<>("classe"));
        coluna_cursoGlobal.setCellValueFactory( new PropertyValueFactory<>("curso"));
        coluna_propinaGlobal.setCellValueFactory( new PropertyValueFactory<>("precoPropina"));
        coluna_alunospagosGlobal.setCellValueFactory( new PropertyValueFactory<>("quantAlunosPagaram"));
        coluna_alunosnaopagosGlobal.setCellValueFactory( new PropertyValueFactory<>("quantAlunosNaoPagaram"));
        coluna_valoresPagoGlobal.setCellValueFactory( new PropertyValueFactory<>("valorpago"));
        coluna_valoresnpagosGlobal.setCellValueFactory( new PropertyValueFactory<>("valorNaoPago"));
        coluna_totalalunoGlobal.setCellValueFactory( new PropertyValueFactory<>("totalAlunos"));
        coluna_totalGlobal.setCellValueFactory( new PropertyValueFactory<>("total"));
        
    }
    
     private void InicializaTabela( String mes , String periodo)
    {
        ConfiguraColunas();
        ObservableList<RelatorioGlobal> listarg = FXCollections.observableArrayList();
        for( Classe_Curso cc : ListaClasse() )
        {
            RelatorioGlobal rg = new RelatorioGlobal();
            rg.setClasse(cc.getClasse());
            rg.setCurso(cc.getCurso());
            rg.setPeriodo(periodo);
            rg.setPrecoPropina(DefinicoesUnidadePreco.ChangeFromStringToInt(Preco.ValorPreco(cc.getClasse(), Curso.NameToCode(cc.getCurso()), "Propina")));
            rg.setQuantAlunosPagaram(Pagamento.ListaAlunosPagaram(mes, rg.getCurso(),rg.getClasse() , rg.getPeriodo()).size());
            rg.setQuantAlunosNaoPagaram(Pagamento.ListaAlunosNaoPagaram(mes, rg.getCurso(), rg.getClasse() ,rg.getPeriodo()).size());
            rg.setValorpago(Pagamento.ValoresPagoAlunos(mes, rg.getCurso(), rg.getClasse() , rg.getPeriodo()));
            rg.setValorNaoPago(Pagamento.ValoresNaoPagos(Pagamento.ListaAlunosNaoPagaram(mes, rg.getCurso(), rg.getClasse(), rg.getPeriodo())));
            rg.setTotal(rg.getValorpago()+rg.getValorNaoPago());
            rg.setTotalAlunos(Estudante.ListaAlunosGeralPorCurso_Classe(rg.getCurso(), rg.getClasse() ,rg.getPeriodo()).size());
            listarg.add(rg);
            
            
            //adiciona na base de dados afim de se criar o relatorio
            AddRelatorio(rg.getTotalAlunos(), rg.getClasse(), rg.getCurso(), rg.getPrecoPropina(), rg.getMatriculados(), rg.getConfirmados(), rg.getQuantAlunosPagaram(), rg.getQuantAlunosNaoPagaram(), rg.getValorpago(), rg.getValorNaoPago(), rg.getTotal(), cb_mes.getSelectionModel().getSelectedItem());
        }
        
        tabelaGlobal.setItems(listarg);
    }
    
      private int UltimoIdRelatorio()
    {
        ResultSet rs = OperacoesBase.Consultar("select codrelatorio from relatorioGlobal order by codrelatorio");
        int valor = 0;
        try {
            while( rs.next() )
            {
               valor = rs.getInt("codrelatorio");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RelatorioMensal2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ++valor;
    }
    
    private void AddRelatorio(int totalaluno,  String classe , String curso ,int prop ,  int matr , int conf , int alunospagos , int alunosnaopago , int valorespago , int valoresNaopago , int totalgeral , String mes  )
    {
        OperacoesBase.Inserir("insert into relatorioGlobal  values( '"+this.UltimoIdRelatorio()+"', '"+classe+"' , '"+curso+"' ,  '"+matr+"' , '"+conf+"', '"+alunospagos+"' , '"+alunosnaopago+"' , '"+valorespago+"' , '"+valoresNaopago+"' , '"+totalgeral+"' , '"+totalaluno+"', '"+mes+"','"+prop+"' )");
    }

    private ObservableList<Classe_Curso> ListaClasse() 
    {
       return Classe.ListaClassesExistentes_Curso();
        
    }
   
    private void Inicializa_Periodo()
    {
         cb_periodoGlobal.setItems(FXCollections.observableArrayList(Arrays.asList("Manhã" , "Tarde")));
    }

   
}
