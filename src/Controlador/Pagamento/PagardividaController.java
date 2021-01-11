/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Pagamento;

import Controlador.RelatorioFinanceiro.EmitirRecibo2Controller;
import Bd.OperacoesBase;
import Validacoes.validaEfectuarPagamento;
import definicoes.DefinicoesUnidadePreco;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import modelo.Curso;
import modelo.Estudante;
import modelo.MesAno;
import modelo.Pagamento;
import modelo.Preco;
import modelo.Usuario;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class PagardividaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private static Pane pane;
    private static int codaluno;
    private static String bi_cedula;
    private static ObservableList<String> lista_mes;
    private static String nome_funcionario;
    private static String valor_apagar;
    private static LocalDate date;
    private static int multa;
    @FXML
    private TextField txt_nome;
    @FXML
    private TextField txt_bi;
    @FXML
    private TextField txt_curso;
    @FXML
    private TextField txt_classe;
    @FXML
    private ListView<String> lista_meses_selecionados;
    @FXML
    private DatePicker data;
    @FXML
    private TextField txt_funcionario;
    @FXML
    private TextField txt_totalapagar;
    @FXML
    private TextField txt_valorpago;
    @FXML
    private ComboBox<String> cb_tipo_pagamento;
    @FXML
    private TextField txt_identificacao;
    @FXML
    private Button botao;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Preenche_campos();
    }    
   
    private void Preenche_campos()
    {
        //verifica se os valores passados nao sao nulos
      /*  if( lista_mes != null && codaluno != 0 && !"".equals(nome_funcionario) && "".equals(valor_apagar))
        {
            Configura_valores();
        }*/
        data.setDisable(true);
        Configura_valores();
        InicializaTiposPagamento();
    }
    
    
    private void Configura_valores()
    {
        int valor = DefinicoesUnidadePreco.ChangeFromStringToInt(valor_apagar);
        
        //adicionando os valores nos respectivos campos
        txt_nome.setText( Estudante.CodeToName(codaluno) );
        txt_bi.setText(Estudante.NameToBi(Estudante.CodeToName(codaluno)));
        txt_classe.setText(Estudante.codeToClasse(codaluno));
        txt_curso.setText(Estudante.codeToCurso(codaluno));
        txt_funcionario.setText(nome_funcionario);
        txt_totalapagar.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(valor)));
        lista_meses_selecionados.setItems(lista_mes);
        data.setValue(date);
        lista_meses_selecionados.setItems(lista_mes);
        
    }
    
    
      private void InicializaTiposPagamento()
    {
        cb_tipo_pagamento.setDisable(false); //abilita a comboBox tipo de pagamantos
        String tipo[] = {"Tpa" , "Banco" , "Cash"}; //Cria as opces de pagamento
        ObservableList<String> lista = FXCollections.observableArrayList(Arrays.asList(tipo)); //cria lista de pagamentos e adiciona as opcoes
        cb_tipo_pagamento.setItems(lista); //adiciona a lista com as opcoes na cb_tipo
        
    }//fim do metodo
    
      
       /**
     * Este metodo foi criado para permitir a activacao dos campos bolderom e numero de identificacao
     * @param event , 
     */
    @FXML
    public void SelecionaOTipoPagamento( ActionEvent event )
    {
        String tipo_selected = cb_tipo_pagamento.getSelectionModel().getSelectedItem();
        if( tipo_selected != null )
        {
            if( "Banco".equalsIgnoreCase(tipo_selected)) //caso o tipo de pagamento seja o por Banco
            {
                txt_identificacao.setDisable(false); //e desabilitado
                txt_identificacao.setText("");
            }
            else //Caso o pagamento que foi efectuado sej ao por TPA
                if( "TPA".equalsIgnoreCase(tipo_selected) )
            {
                txt_identificacao.setDisable(false); //Este e habilitado
                txt_identificacao.setText("");
            }
            else
                {
                     txt_identificacao.setDisable(true); //desabilita este campo
                }
        }
        else //caso o tipo de pagamento for nullo ,entao os campos sao todos desabilitados
        {
            txt_identificacao.setDisable(true); //desabilita este campo 
        }
        
    }//fim do metodo
    @FXML
    private void PermiteApenasInteiro( KeyEvent event )
    {
        if( !Character.isDigit(event.getCharacter().charAt(0)) )
            event.consume();
    }
    
    
    /**
     * -----------------------Metodos Auxiliares. --------------------------------------------------------------
     * @param codaluno
     */
    public static void setCodaluno(int codaluno) {
        PagardividaController.codaluno = codaluno;
    }

    public static void setLista_mes(ObservableList<String> lista_mes) {
        PagardividaController.lista_mes = lista_mes;
    }

    public static void setNome_funcionario(String nome_funcionario) {
        PagardividaController.nome_funcionario = nome_funcionario;
    }

    public static void setPane(Pane pane) {
        PagardividaController.pane = pane;
    }

    public static void setValor_apagar(String valor_apagar) {
        PagardividaController.valor_apagar = valor_apagar;
    }

    public static void setBi_cedula(String bi_cedula) {
        PagardividaController.bi_cedula = bi_cedula;
    }

    public static void setDate(LocalDate date) {
        PagardividaController.date = date;
    }

    public static void setMulta(int multa) {
        PagardividaController.multa = multa;
    }

    
    
    @FXML
    public void PreencheCampos( ActionEvent event )
    {
        
            String efeito = "Propina";
            String tipo_pagamento = validaEfectuarPagamento.Retorna_Forma_selected(cb_tipo_pagamento,txt_identificacao);
            int valor_a_pagar = DefinicoesUnidadePreco.ChangeFromStringToInt(DefinicoesUnidadePreco.ReplaceUnidade(txt_totalapagar.getText()));
            int valor_pago = DefinicoesUnidadePreco.ChangeFromStringToInt(DefinicoesUnidadePreco.ReplaceUnidade(txt_valorpago.getText()));
            LocalDate dataLocal = data.getValue();
           
            
            Pagamento p = new Pagamento();
            p.setCodigo(Pagamento.UltimocodePagamento());
            p.setCodmatricula_c(matricula_confirmacao.CodAlunoToCodMatricula(codaluno));
            p.setCodfuncionario(Usuario.NameToCode(txt_funcionario.getText()));
            p.setEfeito(efeito);
            p.setForma_pagamento(tipo_pagamento);
            p.setValor_Pago(DefinicoesUnidadePreco.ChangeFromStringToInt(Preco.ValorPreco( Estudante.codeToClasse(codaluno) ,Curso.NameToCode(Estudante.codeToCurso(codaluno)), efeito)));
            p.setValor_apagar(DefinicoesUnidadePreco.ChangeFromStringToInt(Preco.ValorPreco( Estudante.codeToClasse(codaluno) ,Curso.NameToCode(Estudante.codeToCurso(codaluno)), efeito)));
            p.setData(dataLocal);
            p.setAno(MesAno.Get_AnoActualCobranca());
            p.setMulta(multa);
           
          //verifica os campos vazios
          if( validaEfectuarPagamento.NaoEstaoVaziosPagarDivida(lista_meses_selecionados, txt_identificacao, txt_bi, txt_totalapagar, txt_valorpago, cb_tipo_pagamento, txt_bi, txt_curso, txt_classe, txt_nome, cb_tipo_pagamento, data) )
          {
            if( !validaEfectuarPagamento.VerificaSeJaExisteNumero(cb_tipo_pagamento.getSelectionModel().getSelectedItem(), tipo_pagamento) )
            {
                //verifica se os valores a se pagar estao correctos
                if( valor_a_pagar == valor_pago )
               {
                    
                     boolean pagamento_controla = false; //variavel que verifica se a insercao foi bem feita
                     for( String valor : lista_mes ) //Percorre a lista de meses a pagar
                     {
                        
                         //configura os atributos
                          int codpagamento = Pagamento.UltimocodePagamento();
                          p.setCodigo(codpagamento);
                          p.setMes(valor);
                          pagamento_controla =  p.adicionar();
                          if( pagamento_controla )
                              EliminarDivida(p.getCodmatricula_c(), p.getMes());
                     }
                    
                     if( pagamento_controla )
                     {
                         Alert a = new Alert(AlertType.INFORMATION,"Pagamento efectuado com sucesso");
                         a.show();
                         EmitirRecibo2Controller.setNomeFuncionario(Usuario.CodeToName(p.getCodfuncionario()));
                         EmitirRecibo2Controller.setCodaluno(codaluno);
                         EmitirRecibo2Controller.setMes(p.getMes());
                         EmitirRecibo2Controller.setFpagamento(tipo_pagamento);
                         EmitirRecibo2Controller.setData(dataLocal);
                         Limpar();
                     }
                      else
                      {
                          Alert a = new Alert(AlertType.ERROR, "Ocorreu um erro ao tentar fazer o pagamento");
                          a.show();
                      }
               } else
                {
                    Alert a = new Alert(AlertType.WARNING, "O valor que deve pagar nao corresponde com que pagou");
                    a.show();
                 }
                
            }
            else
            {
                Alert a = new Alert(AlertType.WARNING, "Este numero de Bolderon ou Tpa ja foi usado");
                a.show();
            }
         }//Fim do if que verifica se estao vazios
          else
          {
              Alert a = new Alert(AlertType.WARNING, "Existem campos vazios");
              a.show();
          }
          
          
        }
    
     private void Limpar()
    {
        txt_bi.clear();
        txt_identificacao.clear();
        txt_nome.clear();
        txt_classe.clear();
        txt_totalapagar.clear();
        txt_valorpago.clear();
        txt_classe.clear();
        cb_tipo_pagamento.setItems(null);
        txt_curso.clear();
        txt_funcionario.clear();
        lista_meses_selecionados.setItems(null);
        data.setValue(null);
    }

    
    private void EliminarDivida(int codaluno, String mes) 
    {
         
        OperacoesBase.Eliminar("delete from Devedor where codmatricula_c = '"+matricula_confirmacao.CodAlunoToCodMatricula(codaluno)+"' and mes = '"+mes+"'");
    }
     
    @FXML
     public void CallEmitirRecibo( ActionEvent event )
    {
         //elimina os dados que estao nesta tabela
        OperacoesBase.Eliminar("truncate table recibo");
        
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/vista/emitirRecibo2.fxml"));
            pane.getChildren().removeAll();
            pane.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(PagardividaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          
    }
}
