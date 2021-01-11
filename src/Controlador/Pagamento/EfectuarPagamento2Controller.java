/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Pagamento;

import Bd.OperacoesBase;
import Controlador.Home.HomePagamentoController;
import Validacoes.ValidaData;
import Validacoes.validaDevedor;
import Validacoes.validaEfectuarPagamento;
import definicoes.DefinicoesData;
import definicoes.DefinicoesUnidadePreco;
import java.io.IOException;
import java.math.BigDecimal;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Curso;
import modelo.Devedor;
import modelo.Estudante;
import modelo.MesAno;
import modelo.Pagamento;
import modelo.Preco;
import modelo.RegistroUsuario;
import modelo.Usuario;
import Controlador.RelatorioFinanceiro.EmitirReciboController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import definicoes.DefinicoesAdicionarDialogo;
import definicoes.DefinicoesPane;
import java.awt.BorderLayout;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import modelo.Modelo_privilegio;
import modelo.VisualizarPagamento;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class EfectuarPagamento2Controller implements Initializable {
   
    @FXML private ComboBox<String> cb_tipo_pagamento;
    @FXML private TextField txt_identificacao;
    @FXML private ComboBox<String> cb_efeito;
    @FXML private ListView<String> lista_meses;
    @FXML private DatePicker data_pagamento;
    @FXML private TextField txt_total_apagar;
    @FXML private TextField txt_valor_pago;
    @FXML private Button adicionar;
    @FXML private ImageView imagem1;
    @FXML
    private Button btn_imprimir;
    @FXML
    private JFXTextField txt_codigo;
    @FXML
    private JFXComboBox<String> cb_anolectivo;
    @FXML
    private Label txt_nome;
    @FXML
    private Label txt_bicedula;
    private static String nomeuser;
    private static Pane pane;
    @FXML
    private TextField txt_curso;
    @FXML
    private TextField txt_classe;
    @FXML
    private TextField txt_turma;
    @FXML
    private TextField txt_periodo;
    @FXML
    private Label lbl_efectuar;
    
    private static VisualizarPagamento vp;
    private ObservableList<String> descricao;
    private ObservableList<String> mes;
    private ObservableList<Integer> Lista_preco;
    private ObservableList<Integer> Lista_valorPago;
    private ObservableList<Integer> Lista_codigo;
    private ObservableList<Integer> Lista_Multa;
    private int codigoAluno;
  
 
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        InicializaListas();
        InicializaFormaPagamento();
        data_pagamento.setValue(DefinicoesData.RetornaDatadoSistema());
        lista_meses.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        if( vp != null)
            InicializaCamposActualizar();
    }    

    
    
    @FXML
    private void Pesquisar(MouseEvent event) {
        
        String codigo_escola = txt_codigo.getText();
        if( !codigo_escola.equals("") )
        {
            int codigo = Estudante.Codigo_Escola_to_codigoBd(codigo_escola);
            this.codigoAluno = codigo;
            if( codigo > 0 )
            {
                InicializaDadosEstudante(codigo,codigo_escola);
                cb_tipo_pagamento.setDisable(false);
                InicializaFormaPagamento();
            }
            else
            {
                Alert a = new Alert(AlertType.INFORMATION,"Estudante não encontrado");
                a.show();
            }
            
        }
        else
        {
            Alert a = new Alert(AlertType.WARNING,"Código invalido");
            a.show();
        }
            
    }
   

  
    
    @FXML
    private void Valida_valorPago( KeyEvent event )
    {
        if( !Character.isDigit(event.getCharacter().charAt(0)))
            event.consume();
    }
    
  

    @FXML
    private void SelecionaOTipoPagamento(ActionEvent event) {
        
        String formaPagamento = cb_tipo_pagamento.getSelectionModel().getSelectedItem();
        if( formaPagamento != null )
        {
            SetTipoPagamento(formaPagamento);
            cb_anolectivo.setDisable(false);
        }
        else
        {
            cb_anolectivo.getSelectionModel().clearSelection();
            cb_anolectivo.setDisable(true);
            txt_identificacao.clear();
            txt_identificacao.setDisable(true);
        }
      
    }
    
    @FXML
    private void SelecionaAno_inicializaEfeito(ActionEvent event) {
        
        String ano = cb_anolectivo.getSelectionModel().getSelectedItem();
        if( ano != null )
        {
            //verifica se o estudante é devedor
            String nome = txt_nome.getText();
            Devedor_configuracao(nome , ano);
            
            //InicializaEfeito
            InicializaEfeito();
            cb_efeito.setDisable(false);
            
        }
    }


    @FXML
    private void SelecionaEfeito_VisualizaMesesNaoPago(ActionEvent event) {
        
        String valor = cb_efeito.getSelectionModel().getSelectedItem();
        if( valor != null )
        {
            BigDecimal valor_apagar = new BigDecimal(ObterPreco());
            if( "Propina".equalsIgnoreCase(valor))
            {
                lista_meses.setDisable(false);
                InicializaMesesApagar();
            }
            else
                if("Matricula".equalsIgnoreCase(valor))
                {
                      lista_meses.setDisable(false);
                      //lista_meses.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                      //lista_meses.setItems( FXCollections.observableArrayList(Arrays.asList(new String[]{ "Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro","Dezembro"})));
                     txt_total_apagar.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(valor_apagar.toString())); 
                }
            else
                    if( "Confirmação".equalsIgnoreCase(valor) )
                    {
                       lista_meses.setDisable(true);
                       String preco = DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta((Obter_Preco_Matricula_Confirmacao() == null)?"0":Obter_Preco_Matricula_Confirmacao());
                       txt_total_apagar.setText(preco);
                    }
            else
                    {
                        lista_meses.setDisable(true);
                        lista_meses.setItems(null);
                        txt_total_apagar.setText("Akz "+ObterPreco()+",00");
                    }
        }
        else
        {
            lista_meses.setItems(null);
        }
    }

   

    @FXML
    private void SelecionaMeses_CalculaPreco(MouseEvent event) {
        
         String efeito = cb_efeito.getSelectionModel().getSelectedItem();
         if( efeito.equalsIgnoreCase("Propina"))
         {
                 int quant = lista_meses.getSelectionModel().getSelectedItems().size();
                if( quant > 0 )
                {
                    BigDecimal preco = new BigDecimal(ObterPreco());
                    BigDecimal total = preco.multiply( new BigDecimal(String.valueOf(quant)));
                    txt_total_apagar.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(total.toString()));
                }
                else
                    txt_total_apagar.setText(DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta("0"));
         }
         
    }

    @FXML
    private void Preencher(ActionEvent event) {
        
       String formaPagamento = RetornaFormaPagamento( cb_tipo_pagamento.getSelectionModel().getSelectedItem() );

       if(validaEfectuarPagamento.NaoEstaoVazios(lista_meses, txt_identificacao, txt_bicedula, txt_nome, txt_total_apagar, txt_valor_pago, cb_efeito, txt_turma, txt_curso, txt_classe, txt_periodo, cb_tipo_pagamento, data_pagamento))
       {
            
            int codaluno = Estudante.NameToCode(txt_nome.getText());
            String efeito = cb_efeito.getSelectionModel().getSelectedItem();
            LocalDate data = data_pagamento.getValue();
            String anolectivo = MesAno.Get_AnoActualCobranca();
            int codfuncionario = Usuario.NameToCode(nomeuser);
            String valor_apagar = Preco.ValorPreco(Estudante.GetClasse(codaluno,anolectivo), Curso.NameToCode(Estudante.codeToCurso(codaluno)), efeito);
            String valor_pago = DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(txt_valor_pago.getText());
            ObservableList<String> meses = lista_meses.getSelectionModel().getSelectedItems();
            
            //configura os meses
            if( efeito.equalsIgnoreCase("Confirmação") || efeito.equalsIgnoreCase("Matricula") || efeito.equalsIgnoreCase("Certificado") )
                meses = FXCollections.observableArrayList(Arrays.asList("-"));
            
            ObservableList<String> mes_recibo = FXCollections.observableArrayList();
            ObservableList<Integer> codigo_recibo = FXCollections.observableArrayList();
            String ano = MesAno.Get_AnoActualCobranca();
                
                 if( txt_total_apagar.getText().equalsIgnoreCase(valor_pago))
                 {
                     Pagamento p = new Pagamento();
                     p.setAno(ano);
                     p.setCodfuncionario(codfuncionario);
                     p.setData(data);
                     p.setForma_pagamento(formaPagamento);
                     p.setValor_Pago(DefinicoesUnidadePreco.ChangeFromStringToInt(valor_apagar));
                     p.setValor_apagar(DefinicoesUnidadePreco.ChangeFromStringToInt(valor_apagar));
                     p.setEfeito(efeito);
                     p.setCodmatricula_c(matricula_confirmacao.CodAlunoToCodMatricula(codaluno));
                     p.setMulta(0);
                     int coda = p.getCodmatricula_c();
                     boolean v = false;
                     
                     //verifica se a data de pagamento esta correcta
                        if( ValidaData.AnoDataCorrecto(String.valueOf(p.getData()),p.getEfeito()))
                        {
                            //percorre os meses selecionados a fazer o pagamento e acaba de preencher os campos do objecto pagaemnto que não sãoa estaticos
                            //Neste caso, o mes e o codigo do pagamento
                                for( String mes : meses )
                                   {
                                      int codpagamento = Pagamento.UltimocodePagamento();
                                      p.setCodigo(codpagamento);
                                      p.setMes(mes);
                                      if( p.adicionar())
                                      {
                                          v = true; //verfica se a adicão foi bem feita
                                          Verifica_Efeito(efeito, codpagamento, mes);//verifica o efeito de pagamento efectuado e inseri na tabela correspondente( para alem de inserir na tabela pagaemnto )
                                          mes_recibo.add(mes);//Adiciona os meses que se fez o pagamento em um array de meses afim de se passar esses valores a janela de emissão de recibo 
                                          codigo_recibo.add(p.getCodigo());//adiciona tambem os codigos do pagamento.
                                      }

                                  }

                                  if( v ) //Caso a inserção seja bem efectuada
                                  {
                                      Alert a = new Alert(AlertType.INFORMATION,"Pagamento efectuado com sucesso");
                                      a.show();//Exibe a mensagem
                                      Limpar();//Limpa os campos
                                    //Passa os dados necessarios ao controlador da emissao de recibo  
                                    // E adiciona tambem o registro do pagamento na tabela de registro 
                                    
                                        EmitirReciboController.setNomefuncionario(Usuario.CodeToName(p.getCodfuncionario()));
                                        EmitirReciboController.setCodaluno(codaluno);
                                        EmitirReciboController.setDescricao(efeito);
                                        EmitirReciboController.setMes(mes_recibo);
                                        EmitirReciboController.setData(data);
                                        EmitirReciboController.setPane(pane);
                                        EmitirReciboController.setPreco(p.getValor_apagar());
                                        EmitirReciboController.setLista_codigo(codigo_recibo);
                                        EmitirReciboController.setMulta(p.getMulta());
                                        EmitirReciboController.setValor_pago(p.getValor_Pago());
                                        EmitirReciboController.setFpagamento(formaPagamento);
                                   
                                      RegistroUsuario.AddRegistro("Efectuou o pagamento do Aluno :"+Estudante.CodeToName(codaluno)); //adiciona o registro de pagamento na tabela de registro
                                      coda = 0;
                                  }
                                  else
                                  {
                                      Alert a = new Alert(AlertType.ERROR, "Ocorreu um erro ao efectuar o pagamento.");
                                      a.show();
                                      System.out.println(p.getMes());
                                  }
                        }
                        else
                        {
                              Alert a = new Alert(Alert.AlertType.ERROR, "A data que selecionou esta incorrecta.");
                              a.show();
                        }
                 }
                 else
                 {
                      Alert a = new Alert(Alert.AlertType.ERROR, "O valor a pagar não corresponde com o que pagou.");
                      a.show();
                 }
            }
        else
        {
            Alert a = new Alert(Alert.AlertType.ERROR, "Existem campos Vazios");
            a.show();
        }
    }

    @FXML
    private void CallEmitirRecibo(ActionEvent event) {
        
         //elimina os dados que estao nesta tabela
        OperacoesBase.Eliminar("truncate table recibo");
        
        try {
             EmitirReciboController.setPane(pane);
             EmitirReciboController.setNomefuncionario(nomeuser);
            Parent fxml = FXMLLoader.load(getClass().getResource("/vista/emitirRecibo.fxml"));
            pane.getChildren().removeAll();
            pane.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(EfectuarPagamento2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public static void setPane(Pane pane) {
        EfectuarPagamento2Controller.pane = pane;
    }

    public static void setNomeuser(String nomeuser) {
        EfectuarPagamento2Controller.nomeuser = nomeuser;
    }
      
    
    @FXML
    private void AbirInfo(MouseEvent event) {
      
        DefinicoesAdicionarDialogo d  = new DefinicoesAdicionarDialogo();
        d.AddDialogo("/dialogos/adicionarEstudanteAjuda.fxml");
    }
    
    
/*************************METODOS AUXILIARES**************************************************/

    
    private void InicializaFormaPagamento()
    {
         String ts[] = {"Tpa" , "Banco" , "Cash"};
         cb_tipo_pagamento.setItems( FXCollections.observableArrayList(Arrays.asList(ts)));
         String anolectivo [] = {MesAno.Get_AnoActualCobranca()};
         cb_anolectivo.setItems(FXCollections.observableArrayList(Arrays.asList(anolectivo)));
    
    }
    
    private void SetTipoPagamento( String valor )
    {
        if( "Tpa".equalsIgnoreCase(valor) || "Banco".equals(valor) )
        {
            txt_identificacao.setDisable(false);
        }
       
        else
            {
                 txt_identificacao.setDisable(true);
            }
    }
    
    private void InicializaEfeito()
    {
        String array_efeito[] = {"Matricula" , "Propina" , "Certificado" , "Confirmação"};
        cb_efeito.setItems(FXCollections.observableArrayList(Arrays.asList(array_efeito)));
        int codaluno =  Estudante.NameToCode(txt_nome.getText());
        
        //Se ja foi matriculado uma vez
       /* if( Estudante.ListaEstudante_Ja_Matricula_Uma_Vez( codaluno , "Matricula") )
        {
            cb_efeito.getItems().remove("Matricula");
        }
       if( Estudante.ListaEstudanteMatricula_Confirmacao(codaluno,"Confirmação"))
        {
               cb_efeito.getItems().remove("Matricula");
               cb_efeito.getItems().remove("Confirmação");
        }
        else
        {
            if( Estudante.ListaEstudanteMatricula_Confirmacao(codaluno,"Confirmação") )
            {
               cb_efeito.getItems().remove("Matricula");
               cb_efeito.getItems().remove("Confirmação");
            }
            
            else
            {
                cb_efeito.getItems().remove("Propina");
                cb_efeito.getItems().remove("Certificado");
            }
        }*/
    }
        
    private String[] ClasseExame()
    {
        String meses_array[]= null;//Array principal
        String meses_normais[] = {"Janeiro", "Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro","Dezembro"};//meses normais
        String meses_exames[] = { "Janeiro", "Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro","Dezembro"};//meses das classes de exame
        
        String classe = txt_classe.getText();
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
    
    private boolean EClassedeExame( String classe )
    {
        return "9ª classe".equalsIgnoreCase(classe) || "6ª classe".equalsIgnoreCase(classe) || "12ª classe".equalsIgnoreCase(classe);
    }
    
     private void InicializaMesesApagar()
     {
         int codaluno = this.codigoAluno;
         int codmatricula = matricula_confirmacao.CodAlunoToCodMatricula(codaluno);
         ObservableList<String> meses_Geral = FXCollections.observableArrayList(Arrays.asList(ClasseExame()));
         System.out.println(meses_Geral);
         ObservableList<String> meses_pago  = Estudante.Meses_Pago(codaluno);
         System.out.println(meses_pago);
         ObservableList<String> meses_NaoPago = EDevedor(ClasseExame(), meses_pago);
        
         if( !Estudante.EstudanteNormal(codaluno))
         {
             lista_meses.setItems(MesesAlunoTransferido(meses_Geral));
         }
         else
         {
             System.out.println(meses_NaoPago);
              if(  meses_NaoPago.size() <= 0)
               {
                   System.out.println("ok");
                   adicionar.setDisable(false);
                   lista_meses.setItems(meses_Geral);
                   lista_meses.getItems().removeAll(meses_pago);
                   
                   System.out.println(lista_meses);
              }
             else
             {
                 if( meses_NaoPago.size() <= 2 )
                 {
                     adicionar.setDisable(false);
                     lista_meses.setItems(meses_NaoPago);
                 }
         }
       }
     }
     
     private ObservableList<String> EDevedor( String meses[] , ObservableList<String> meses_pago )
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

    private String ObterPreco()
    {
        String classe = txt_classe.getText();
        int codcurso = Curso.NameToCode(txt_curso.getText());
        String efeito = cb_efeito.getSelectionModel().getSelectedItem();
        
        return Preco.ValorPreco(classe, codcurso, efeito);
        
    }
    
    private String Obter_Preco_Matricula_Confirmacao()
    {
        String classe = txt_classe.getText();
        int codcurso = Curso.NameToCode(txt_curso.getText());
        String efeito = cb_efeito.getSelectionModel().getSelectedItem();
        
        if( "Confirmação".equalsIgnoreCase(efeito) || "Matricula".equalsIgnoreCase(efeito))
            return Preco.ValorPreco(classe, codcurso, efeito);
        return null;
    }
   
    
    private String RetornaFormaPagamento(String  valor) {

        if( "Tpa".equalsIgnoreCase(valor) )
        {
            if( txt_identificacao.getText().equals("") )
                return null;
              return "Tpa "+txt_identificacao.getText();
        }
        else
            if( "Banco".equalsIgnoreCase(valor) )
            {
                if( txt_identificacao.getText().equals("") )
                    return null;
                return "Banco "+txt_identificacao.getText();
            }
        else
               if("Cash".equalsIgnoreCase(valor))
                   return "Cash";
        return null; 
    }
    
    private void Limpar()
    {
        txt_identificacao.clear();
        txt_identificacao.setDisable(true);
        txt_valor_pago.clear();
        txt_total_apagar.clear();
        cb_efeito.getSelectionModel().clearSelection();
        cb_tipo_pagamento.getSelectionModel().clearSelection();
        cb_anolectivo.getSelectionModel().clearSelection();
        
    }
    private void Adicionar_EmDevedor(int codaluno , int codmatricula) {
          
        Devedor d = new Devedor();
        d.setCodaluno(codaluno);
        d.setCodmatricula(codmatricula);
        d.setCodigo(Devedor.LastCode());
        if( !validaDevedor.verificaExistencia(codmatricula) )
             d.Adicionar();
    }

    private void Verifica_Efeito(String efeito, int UltimocodePagamento, String mes) 
    {

        if( "Matricula".equalsIgnoreCase(efeito))
            OperacoesBase.Inserir("insert into matricula values('"+UltimocodePagamento+"')");
        else
            if("Confirmação".equalsIgnoreCase(efeito))
                OperacoesBase.Inserir("insert into confirmacao values('"+UltimocodePagamento+"')");
        else
                if("Certificado".equalsIgnoreCase(efeito))
                     OperacoesBase.Inserir("insert into certificado values('"+UltimocodePagamento+"')");
        else
                    if("Propina".equalsIgnoreCase(efeito))
                         OperacoesBase.Inserir("insert into propina values('"+UltimocodePagamento+"', '"+mes+"')");
    }
    
    
     private ObservableList<String> MesesAlunoTransferido( ObservableList<String> meses )
     {
         int i = meses.indexOf(MesAno.Get_MesActualCobranca());
         return FXCollections.observableArrayList(meses.subList(i, meses.size()));
     }
   
    

    private void Inserir_em_devedor(int NameToCode, String Get_MesActualCobranca) 
    {
        
        Devedor d = new Devedor();
        d.setCodigo(Devedor.LastCode());
        d.setMes(Get_MesActualCobranca);
        d.setCodmatricula(matricula_confirmacao.CodAlunoToCodMatricula(NameToCode));
        d.setValor_propina(DefinicoesUnidadePreco.ChangeFromStringToInt(Preco.ValorPreco(txt_classe.getText(), Curso.NameToCode(""), "Propina")));
        d.setValor_multa(DefinicoesUnidadePreco.ChangeFromStringToInt(Preco.RetornaValor_Multa(txt_classe.getText(), Curso.NameToCode(""))));
       
        if( !validaDevedor.verificaExistencia(d.getCodmatricula(), d.getMes()))
              d.Adicionar();
    }

    private void DisabilitaTudo() 
    {
        
        cb_efeito.setDisable(true);
        cb_tipo_pagamento.setDisable(true);
        lista_meses.setDisable(true);
        adicionar.setDisable(true);
        
    }
    
    
    private void Verifica_Privilegios()
    {
        int codfuncionario = Usuario.Obter_CodFuncionario(Usuario.NameToCode(Usuario.getUsuario_activo()));
        if( Modelo_privilegio.Obter_Insercao(codfuncionario) == 0 )
             adicionar.setDisable(true);
        else
            adicionar.setDisable(false);
        
        if( Modelo_privilegio.Obter_Impressao(codfuncionario) == 0 )
             btn_imprimir.setDisable(true);
        else
            btn_imprimir.setDisable(false);
                
             
    }

    private void InicializaDadosEstudante(int codigo,  String codigo_escola ) {
        
            
            txt_nome.setText(Estudante.CodeToName(codigo));
            txt_bicedula.setText(Estudante.codeToBi(codigo));
            txt_classe.setText(Estudante.codeToClasse(codigo));
            txt_curso.setText(Estudante.codeToCurso(codigo));
            txt_periodo.setText(Estudante.codeToPeriodo(codigo));
            txt_turma.setText(Estudante.codeToTurma(codigo));
            
    }

    private void Devedor_configuracao(String nome, String ano) 
    {
        int codaluno = Estudante.NameToCode(nome);
        int codmatricula = matricula_confirmacao.CodAlunoToCodMatricula(codaluno);
        if( matricula_confirmacao.JaMatriculado(codmatricula, ano) && Pagamento.Devedor_Aluno(MesAno.Get_MesActualCobranca(), nome,txt_curso.getText(), txt_classe.getText(), txt_periodo.getText()))
        {
            Inserir_em_devedor(codaluno, ano);
            DisabilitaTudo();
            Alert a = new Alert(AlertType.WARNING,"Este estudante é devedor.");
            a.setTitle("Devedor");
            a.show();
        }      
        else
        {
            cb_efeito.setDisable(false);
            InicializaEfeito();
        }
        
        
        
    }

    @FXML
    private void back(MouseEvent event) {
        
        DefinicoesPane dp = new DefinicoesPane(pane);
        HomePagamentoController.setPane(pane);
        dp.setPath("/vista/homePagamento.fxml");
        dp.AddPane();
    }

    public static void setVp(VisualizarPagamento vp) {
        EfectuarPagamento2Controller.vp = vp;
    }

   
    

    private void InicializaCamposActualizar() 
    {
       if( vp != null)
       {
           lbl_efectuar.setText("Actualizar Pagamento");
           adicionar.setText("Actualizar");
           txt_bicedula.setText(vp.getBi());
           txt_classe.setText(vp.getCurso());
           txt_curso.setText(vp.getCurso());
           txt_nome.setText(vp.getNome_aluno());
           txt_periodo.setText(vp.getTurma());
           txt_turma.setText(vp.getTurma());
           txt_codigo.setText(vp.getCodescola());
           txt_total_apagar.setText(vp.getValor_apagar());
           txt_valor_pago.setText(vp.getValor_pago());
           
           //Inicializa as combos
           InicializaFormaPagamento();
           InicializaEfeito();
           lista_meses.getItems().add(vp.getMes());
           cb_tipo_pagamento.getSelectionModel().select(vp.getFormapagamento());
           cb_efeito.getSelectionModel().select(vp.getEfeito());
           
           //Desabilita os campos
           cb_efeito.setDisable(false);
           cb_tipo_pagamento.setDisable(false);
           cb_anolectivo.setDisable(false);
           
           
           
           vp = null;
       }
    }

    private void InicializaListas() {
        
        descricao = FXCollections.observableArrayList();
        Lista_Multa = FXCollections.observableArrayList();
        Lista_codigo = FXCollections.observableArrayList();
        Lista_preco = FXCollections.observableArrayList();
        Lista_valorPago = FXCollections.observableArrayList();
        mes = FXCollections.observableArrayList();
    }

    


}
