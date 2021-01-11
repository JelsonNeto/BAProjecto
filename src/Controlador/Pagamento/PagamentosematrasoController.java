/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Pagamento;

import Bd.OperacoesBase;
import Controlador.Home.HomePagamentoController;
import definicoes.DefinicoesData;
import definicoes.DefinicoesPane;
import definicoes.DefinicoesUnidadePreco;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Classe;
import modelo.Curso;
import modelo.Devedor;
import modelo.Estudante;
import modelo.MesAno;
import modelo.Preco;
import modelo.Turma;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class PagamentosematrasoController implements Initializable {
    @FXML
    private ListView<String> lista_estudantes;
    private ComboBox<String> cb_curso;
    private ComboBox<String> cb_classe;
    @FXML
    private ListView<String> lista_meses;
    @FXML
    private TextField txt_valor_multa;
    @FXML
    private TextField txt_turma;
    @FXML
    private TextField txt_tota_apagar;
    @FXML
    private TextField txt_mes_cobranca;
    @FXML
    private TextField txt_anio_cobranca;
    @FXML
    private DatePicker data_pagamento;
    @FXML
    private TextField txt_bi;
    @FXML
    private Button pagar;
    @FXML
    private TextField txt_pesquisa;
    @FXML
    private Pane panel_lateral;
    
    
    /**
     * Initializes the controller class.
     */
    private static Pane pane;
    private static String nome_funcionario;
    private String preco_valor;
    private String multa;
    private int codaluno; //Para conseguir obter a classe do aluno
    private boolean activo = false;
    private static boolean actualizou = false;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        data_pagamento.setDisable(true);
        data_pagamento.setValue(DefinicoesData.RetornaDatadoSistema());
        lista_meses.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        pagar.setDisable(true);
    }        
    /**
     * 
     * @param event
     */
   @FXML
    public void retorna_AlunosQpagaram( MouseEvent event )
    {
        
        String nome_selected = lista_estudantes.getSelectionModel().getSelectedItem();
        int codigo = Estudante.NameToCode(nome_selected);
        codaluno = codigo; //Para conseguir obter a classe deste aluno
        String classe = Estudante.codeToClasse(codigo);
        String curso = Estudante.codeToCurso(codigo);
        int codcurso = Curso.NameToCode(curso);
        String nome_Turma = Turma.CodeToName(Turma.NomeAlunoToCodTurma(nome_selected));
        String mes_cobranca = MesAno.Get_MesActualCobranca();
        String ano_cobranca = MesAno.Get_AnoActualCobranca();
        String bi = Estudante.NameToBi(nome_selected);
        preco_valor = Preco.ValorPreco(classe, codcurso, "Propina" );
        
        if( nome_selected != null )
        {
           lista_meses.setItems(retornaMeses());
           BigDecimal valor_multa = new BigDecimal(Preco.RetornaValor_Multa(classe, codcurso));
           txt_valor_multa.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(valor_multa.toString()));
           txt_turma.setText(nome_Turma);
           txt_mes_cobranca.setText(mes_cobranca);
           txt_anio_cobranca.setText(ano_cobranca);
           txt_bi.setText(bi);
           multa = "Akz "+valor_multa.toString()+",00";
        }
        else
           Limpar();
        
        
    }//fim do metodo
    /**
     * 
     * @param codigo , codigo do aluno ao qual vai ser retornado a lista de meses de propina ja pagos.
     * @return ObservableList<String>, retorna uma lista observavel contendo os meses  que o aluno com o codigo passado como parametro ja pagou de propinas  
     */
   
  /**
      * Verifica se uma classe e de xame ou nao.....As classes de exame sao 9 6 e 12
      * @param code
      * @return 
      */
    private boolean EClassedeExame( String classe )
    {
        return "9ª classe".equalsIgnoreCase(classe) || "6ª classe".equalsIgnoreCase(classe) || "12ª classe".equalsIgnoreCase(classe);
    }//fim do metodo
    
   
    
    @FXML
    public void Seleciona_Meses_CalculaValor( MouseEvent event )
    {
        
        ObservableList<String> lista = lista_meses.getSelectionModel().getSelectedItems();
        if( lista.size() > 0 )
        {
            int v_multa = DefinicoesUnidadePreco.StringToInteger(txt_valor_multa.getText())*lista.size();
            int v_preco = DefinicoesUnidadePreco.StringToInteger(preco_valor);
            int p_total = v_preco*lista.size();
            int valor_total = p_total+v_multa;
            txt_tota_apagar.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(valor_total)));
            pagar.setDisable(false);
        }
        else
            pagar.setDisable(true);
    }
    
   @FXML
   private void Pesquisar( MouseEvent event )
   {
       ProcuraAluno();
   }
    
    @FXML
    public void CallPagarDivida( ActionEvent event )
    {
        
        try {
            
            //Passa os valores na janela
            Preenche_camposPagarDidiva();
            
            Parent p = FXMLLoader.load(getClass().getResource("/vista/pagardivida.fxml"));
            pane.getChildren().removeAll();
            pane.getChildren().setAll(p);
        } catch (IOException ex) {
            Logger.getLogger(PagamentosematrasoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    @FXML
    private void show_panel_lateral(MouseEvent event) {
        
        if( activo )
        {
            panel_lateral.setVisible(false);
            activo = false;
        }
        else
        {
            panel_lateral.setVisible(true);
            activo = true;
        }
        
    }

    @FXML
    private void submenu_carregaTabela(MouseEvent event) 
    {
        
        DefinicoesPane dp = new DefinicoesPane();
        dp.CallOtherWindow("submenu_actualizar_tabela_devedor",null);
        
        panel_lateral.setVisible(false);
        activo = false;
        
    }

    @FXML
    private void submenu_showinfo(MouseEvent event) {
    }
   
    
/**********************************METODOS AUXILIARES*******************************************************************/
    
    private void Limpar()
    {
        txt_anio_cobranca.clear();
        txt_bi.clear();
        txt_mes_cobranca.clear();
        txt_tota_apagar.clear();
        txt_turma.clear();
        txt_valor_multa.clear();
    }
    
    private String PrecoStringtoInteger( String preco )
    {
            String np = preco.replace("Akz", "");
            String np1 = np.replace(",00","");
            String np2 = np1.trim();
         
            return np2;
    }
  

    public static void setPane(Pane pane) {
        PagamentosematrasoController.pane = pane;
    }
    
    
    private void Preenche_camposPagarDidiva()
    {
        PagardividaController.setCodaluno(Estudante.NameToCode(lista_estudantes.getSelectionModel().getSelectedItem()));
        PagardividaController.setLista_mes(lista_meses.getSelectionModel().getSelectedItems());
        PagardividaController.setBi_cedula(txt_bi.getText());
        PagardividaController.setNome_funcionario(nome_funcionario);
        PagardividaController.setPane(pane);
        PagardividaController.setValor_apagar(txt_tota_apagar.getText());
        PagardividaController.setDate(data_pagamento.getValue());
        PagardividaController.setMulta(DefinicoesUnidadePreco.ChangeFromStringToInt(DefinicoesUnidadePreco.ReplaceUnidade(multa)));
    }

    public static void setNome_funcionario(String nome_funcionario) {
        PagamentosematrasoController.nome_funcionario = nome_funcionario;
    }
    
    private ObservableList<String> retornaMeses()
    {
        int codaluno = Estudante.NameToCode(lista_estudantes.getSelectionModel().getSelectedItem());
        ObservableList<String> meses_pago  = Estudante.Meses_Pago(codaluno);
        ObservableList<String> meses_NaoPago = OperacionalMeses(ClasseExame(), meses_pago);
         
        return meses_NaoPago;
    }
    
    private ObservableList<String> OperacionalMeses( String meses[] , ObservableList<String> meses_pago )
    {
         int indice = MesAno.IndiceMesActual(meses);
         ObservableList<String> meses_NaoPago = FXCollections.observableArrayList();
         for( int i = 0 ; i <= indice ; i++ )
         {
             int conta = 0;
             if( !meses_pago.isEmpty() )
             {
                   for( String valor : meses_pago )
                   {
                       if( valor.equalsIgnoreCase(meses[i]))
                       {
                           conta = 0;
                           break;
                       }
                       else
                           conta++;
                   }

                   if( conta > 0 )
                       meses_NaoPago.add(meses[i]);
             }
             else
                 meses_NaoPago.add(meses[i]);
         }
         
         return meses_NaoPago;
         
    }
    
    private String[] ClasseExame()
    {
        
        String meses_array[]= null;//Array principal
        String meses_normais[] = { "Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro"};//meses normais
        String meses_exames[] = { "Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro","Dezembro"};//meses das classes de exame
        //String valor = Estudante.
        String classe = Estudante.codeToClasse(codaluno);
        
        if( classe != null )
        {
            if( EClassedeExame(classe))
                meses_array = meses_exames;
            else
                meses_array = meses_normais;
            return meses_array;
        }
        return null;
    }
    
    private boolean PesquisaAluno( String codigo_escola )
    {
        ObservableList<String> lista = FXCollections.observableArrayList();
        String sql = "select nome from view_aluno_devedor where codigo_escola= '"+codigo_escola+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PagamentosematrasoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lista_estudantes.setItems(lista);
        return lista_estudantes.getItems().size()> 0;//True se tiver estudante
    }

    private void Desabilita_Campos( boolean valor)
    {
        txt_pesquisa.setDisable(valor);
        cb_classe.setDisable(valor);
        cb_curso.setDisable(valor);
        lista_estudantes.setDisable(valor);
        lista_meses.setDisable(valor);
    }

    public static void setActualizou(boolean actualizou) {
        PagamentosematrasoController.actualizou = actualizou;
    }
   
    private void ProcuraAluno()
    {
        
        String codigoescola = txt_pesquisa.getText();
        //verifica se nao esta vazia
        if( !"".equalsIgnoreCase(codigoescola) )
        {
            boolean valor = PesquisaAluno(codigoescola);
            if( !valor )
            {
                 Alert a = new Alert(AlertType.WARNING,"Estudante não encontrado");
                 a.setTitle("Busca de devedor");
                 a.show();   
            }
            
            codaluno = Estudante.Codigo_Escola_to_codigoBd(codigoescola);
        }
        else
        {
            Alert a = new Alert(AlertType.WARNING,"Campo esta vazio");
            a.setTitle("Busca vazia");
            a.show();
        }
        
    }

    @FXML
    private void back(MouseEvent event) {
        
        DefinicoesPane dp = new DefinicoesPane(pane);
        HomePagamentoController.SetPane(pane);
        dp.setPath("/vista/homePagamento.fxml");
        dp.AddPane();
    }
    
    
}
