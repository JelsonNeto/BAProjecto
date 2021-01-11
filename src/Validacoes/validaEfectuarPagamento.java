/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Validacoes;

import Bd.OperacoesBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import modelo.MesAno;

/**
 *
 * @author Familia Neto
 */
public class validaEfectuarPagamento {
    
   
  
    public static boolean NaoEstaoVazios( ListView<String> lista_meses , TextField txt_identificacao , Label txt_bi , Label txt_nome_funcionario , TextField txt_total_apagar , TextField txt_valor_pago , ComboBox<String> cb_efeito , TextField turma , TextField curso , TextField classe , TextField periodo,ComboBox<String> cb_tipo_pagamento, DatePicker data )
    {
        if( !"Mão".equals(cb_tipo_pagamento.getSelectionModel().getSelectedItem()))
            return  lista_meses != null && (!"".equals(txt_identificacao.getText()) || !"".equals(turma.getText())) && !"".equals(txt_bi.getText())  && !"".equals(txt_total_apagar.getText()) && !"".equals(txt_valor_pago.getText())  && cb_efeito != null && !"".equals(curso.getText())  && !"".equals(classe.getText()) && !"".equals(periodo.getText()) && cb_tipo_pagamento != null && data.getValue() != null ;
        return lista_meses != null && !"".equals(txt_bi.getText()) &&    !"".equals(txt_total_apagar.getText()) && !"".equals(txt_valor_pago.getText())  && cb_efeito != null && !"".equals(turma.getText())  && !"".equals(curso.getText()) && !"".equals(classe.getText()) && !"".equals(periodo.getText())  && data.getValue() != null ;
    }
    
    public static boolean VerificaSeJaExisteNumero(String forma , String text) 
    {
        int codigo = 0;
          if( !"Cash".equals(text) )
          {
                ResultSet rs = OperacoesBase.Consultar("select codpagamento from pagamento where forma_pagamento_valor = '"+text+"'"); 
                try {
                    while( rs.next() )
                     {
                         codigo = rs.getInt("codpagamento");
                     }
                } catch (SQLException ex) {
                 Logger.getLogger(validaEfectuarPagamento.class.getName()).log(Level.SEVERE, null, ex);
                     }
          }
        return codigo != 0;
    }

    public static boolean ExisteVazios( int codaluno, int codfuncionario, String efeito, int valor_apagar, int valor_pago, LocalDate data, ObservableList<String> lista_mes) {
   
        return codaluno == 0 || codfuncionario == 0  || "".equals(efeito) || valor_apagar == 0 || valor_pago == 0 || data == null || lista_mes == null;
    }
    
  public static String Retorna_Forma_selected( ComboBox<String> cb_tipo_pagamento,TextField txt_identificacao  )
  {
          String forma_selected = null;
           //verifica se pretende pagar com o tpa ou bolderon
            if("Banco".equalsIgnoreCase(cb_tipo_pagamento.getSelectionModel().getSelectedItem()))
                forma_selected ="Banco -"+txt_identificacao.getText(); //inicializa a variavel com a opcao de banco
            if( "TPA".equals(cb_tipo_pagamento.getSelectionModel().getSelectedItem()))
               forma_selected ="Tpa -"+txt_identificacao.getText(); //selecionou o pagamento por Tpa
            if( "Cash".equals(cb_tipo_pagamento.getSelectionModel().getSelectedItem()))
               forma_selected = cb_tipo_pagamento.getSelectionModel().getSelectedItem();
            
        return forma_selected;
  }
  
  public static boolean JaConfirmou(int codaluno)
  {
      String ano = MesAno.Get_AnoActualCobranca();
      String efeito = "Confirmação";
      ResultSet rs = OperacoesBase.Consultar("select codpagamento from pagamento where descricao = '"+efeito+"' and codaluno = '"+codaluno+"' ano_referencia = '"+ano+"' ");
           int codigo = 0; 
           try {
               while( rs.next() )
                {
                    codigo = rs.getInt("codpagamento");
                }
           } catch (SQLException ex) {
            Logger.getLogger(validaEfectuarPagamento.class.getName()).log(Level.SEVERE, null, ex);
                }
        
        return codigo != 0;
      
  }
  
  public static boolean JaMatriculou(int codaluno)
  {
      String ano = MesAno.Get_AnoActualCobranca();
      String efeito = "Matricula";
      ResultSet rs = OperacoesBase.Consultar("select codpagamento from pagamento where descricao = '"+efeito+"' and codaluno = '"+codaluno+"' ano_referencia = '"+ano+"' ");
           int codigo = 0; 
           try {
               while( rs.next() )
                {
                    codigo = rs.getInt("codpagamento");
                }
           } catch (SQLException ex) {
            Logger.getLogger(validaEfectuarPagamento.class.getName()).log(Level.SEVERE, null, ex);
                }
        
        return codigo != 0;
      
  }

    public static boolean VerificaVaziosAtraso(ComboBox<String> cb_tipo_pagamento, TextField txt_bolderon, TextField txt_identificacao, TextField txt_valorpago, DatePicker data) {

        if( !"Mão".equals(cb_tipo_pagamento.getSelectionModel().getSelectedItem()))
          return ("".equals(txt_bolderon.getText()) || "".equals(txt_identificacao.getText())) && data.getValue() == null && "".equals(txt_valorpago.getText());
        else
            return "".equals(txt_valorpago.getText()) && data.getValue() == null;
    }

  
    public static boolean ExisteVaziosCampos(String selectedItem, String selectedItem0, String selectedItem1, String selectedItem2, String selectedItem3, String selectedItem4, TextField txt_bolderon, TextField txt_identificacao, TextField txt_total_apagar, TextField txt_valor_pago , LocalDate data) {

       return selectedItem == null && selectedItem0 == null && selectedItem1 == null && selectedItem2 == null && selectedItem3 == null && selectedItem4 == null &&  ( txt_bolderon.getText().equals("") || txt_identificacao.getText().equals("") ) && txt_total_apagar.getText().equals("") && txt_valor_pago.getText().equals("") && data == null;
    }

    public static boolean NaoEstaoVaziosPagarDivida(ListView<String> lista_meses_selecionados, TextField txt_identificacao, TextField txt_bi, TextField txt_totalapagar, TextField txt_valorpago, ComboBox<String> cb_tipo_pagamento, TextField txt_bi0, TextField txt_curso, TextField txt_classe, TextField txt_nome, ComboBox<String> cb_tipo_pagamento0, DatePicker data) 
    {
        if(cb_tipo_pagamento.getSelectionModel().getSelectedItem() == null)
            return false;
        if(cb_tipo_pagamento.getSelectionModel().getSelectedItem().equalsIgnoreCase("Cash"))
             return lista_meses_selecionados.getItems().size() >0 && !"".equalsIgnoreCase(txt_bi.getText()) && !"".equalsIgnoreCase(txt_classe.getText()) && !"".equalsIgnoreCase(txt_curso.getText()) && !"".equalsIgnoreCase(txt_nome.getText()) && !"".equalsIgnoreCase(txt_totalapagar.getText()) && !"".equalsIgnoreCase(txt_valorpago.getText());

        return lista_meses_selecionados.getItems().size() >0 && !"".equalsIgnoreCase(txt_bi.getText()) && !"".equalsIgnoreCase(txt_classe.getText()) && !"".equalsIgnoreCase(txt_curso.getText()) && !"".equalsIgnoreCase(txt_identificacao.getText()) && !"".equalsIgnoreCase(txt_nome.getText()) && !"".equalsIgnoreCase(txt_totalapagar.getText()) && !"".equalsIgnoreCase(txt_valorpago.getText());
    }
  
  
}
