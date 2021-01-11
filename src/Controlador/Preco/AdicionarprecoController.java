/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Preco;

import Validacoes.validarPreco;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import modelo.Classe;
import modelo.Curso;
import modelo.Preco;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class AdicionarprecoController implements Initializable {
    @FXML
    private ComboBox<String> curso;
    @FXML
    private ComboBox<String> classe;
    @FXML
    private ComboBox<String> efeito;
    @FXML
    private TextField valor;
    @FXML
    private TextField txt_valor_multa;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        IncializaPeriodoEEfeito();
        InicializaCurso();
    }   
    
    @FXML
    public void Preencher( ActionEvent event )
    {
       
        int codcurso = Curso.NameToCode(curso.getSelectionModel().getSelectedItem());
        String classe_preco = classe.getSelectionModel().getSelectedItem();
        String efeito_preco = efeito.getSelectionModel().getSelectedItem();
        String valor_pre = ( !"".equals(valor.getText()))? (valor.getText()) : "0";
        Alert a;
        if( validarPreco.EstaoVazios( classe.getSelectionModel().getSelectedItem() , curso.getSelectionModel().getSelectedItem() , efeito.getSelectionModel().getSelectedItem(), valor.getText() ) )
        {
          
            a = new Alert( AlertType.ERROR,"Existem campos vazios");
            a.show();
        }
        else
        {
              Preco p = new Preco();
              p.setValor_multa(txt_valor_multa.getText());
              p.setCodigo(p.RetornaUltimoCodigo());
              p.setClasse(classe_preco);
              p.setCodcurso(codcurso);
              p.setEfeito(efeito_preco);
              p.setValor(valor_pre);
             
             if(p.Adicionar())
             {
                 a = new Alert(AlertType.CONFIRMATION, "Preço Adicionado com sucesso.");
                 a.show();
                 Limpar();
              
             }
             else
             {
                 a = new Alert(AlertType.ERROR,"Erro ao Adicionar o preço");
                 a.show();
             }
              
        }
        
    }
    
    
    public void SelecionaEfeito_visualizaMulta( ActionEvent event )
    {
        String efeito_selected = efeito.getSelectionModel().getSelectedItem();
        if( "Propina".equals(efeito_selected))
            txt_valor_multa.setDisable(false);
        else
        {
            txt_valor_multa.setText("0");
            txt_valor_multa.setDisable(true);
        }
    }
    
    @FXML
   public void ValidaValor( KeyEvent event )
   {
       if( Character.isLetter(event.getCharacter().charAt(0)))
           event.consume();
      
   }
    
    @FXML
    public void InicializaClasse( ActionEvent event )
    {
        String nomecurso = curso.getSelectionModel().getSelectedItem();
        FiltarAsClasses(nomecurso);
        
       
    }
    
    @FXML
    private void SelecionaClasse_InicializaEfeito( ActionEvent event )
    {
        String classe_var = classe.getSelectionModel().getSelectedItem();
        if( classe_var != null )
        {
            int codcurso = Curso.NameToCode(curso.getSelectionModel().getSelectedItem());
            efeito.getItems().removeAll(Preco.listaPreco(codcurso, classe_var));
        }
        else
        {
            IncializaPeriodoEEfeito();
        }

    }
    
/************************Metodos auxiliares**********************************************/
    private void InicializaCurso()
    {
        curso.setItems(Curso.ListaCursos());
    }
    
    
    private void IncializaPeriodoEEfeito()
    {
        String e[] = {"Matricula", "Propina" ,"Confirmação", "Certificado"};
        efeito.setItems( FXCollections.observableArrayList(Arrays.asList(e)));
    }
    
   
     private void FiltarAsClasses( String curso  )
    {
        ObservableList<String> lista = Classe.ClassesPorCurso(curso);
        classe.setItems(lista);
    }
    
   
    private void Limpar()
    {
        classe.getSelectionModel().select(null);
        curso.getSelectionModel().select(null);
        efeito.getSelectionModel().select(null);
        valor.setText("");
        valor.setDisable(true);
        txt_valor_multa.setText("");
    }
  
    
}
