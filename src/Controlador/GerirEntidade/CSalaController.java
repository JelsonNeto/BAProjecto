/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.GerirEntidade;

import Bd.OperacoesBase;
import Validacoes.validarSala;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import modelo.Sala;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class CSalaController implements Initializable {

      

    @FXML private TextField capacidade;
    @FXML private TableView<Sala> tabela;
    @FXML private TableColumn<Sala , Integer> coluna_numero;
    @FXML private TableColumn<Sala , Integer> coluna_capacidade;
    @FXML private Button actualizar;
    @FXML private Button adicionar;
    @FXML private Button eliminar;
    @FXML private TextArea txt_nsalas;
    @FXML private Button btn_editar;
    
    private Sala sala;
   

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       sala = new Sala();
      CarregarDadosNaTabela();
    }    
    
    
    @FXML
     public void AdicionarSala()
    {
        PreencheSala();
        CarregarDadosNaTabela();
      
    }
   
    
    private boolean PreencheSala()
    {
        Alert a;
       boolean valor =  validarSala.VerificarVazio(txt_nsalas, capacidade);
       if( !valor )
        {
           
            String salas[] = txt_nsalas.getText().split(",");
            String capacidades[] = capacidade.getText().split(",");
            int pos = 0;
            if( salas.length != capacidades.length )
            {
                a = new Alert(AlertType.WARNING,"O número de salas deve corresponder ao de capacidades.");
                a.show();
            }
            else
            {
                    boolean verifica = false;
                    for( String sala_numero : salas )
                   {
                       if( !validarSala.VerificaExistencia(sala_numero))
                       {
                           sala.setCodigo( Integer.parseInt(  sala_numero) );
                           sala.setCapacidade(Integer.parseInt( capacidades[pos++]));
                       if(sala.AdicionarSala())
                            verifica= true;
                       
                       else
                           verifica = false;

                       }
                       else
                       {
                           a = new Alert(AlertType.WARNING,"Sala Ja existente");
                           a.show();

                           return false;
                       }

                   } //Final do for 
                    
                    if( verifica )
                    {
                        a = new Alert(AlertType.INFORMATION,"Sala adicionada com sucesso");
                        a.show();
                        Limpar();
                    }
                    else
                    {
                        a = new Alert(AlertType.ERROR,"Erro ao adicionar a(s) sala(s).");
                        a.show();
                           
                    }
            }           
        }
       else
       {
    	   a = new Alert(AlertType.WARNING,"Existem Campos Vazios");
           a.show();
    	   return false;
       }
       
       return true;
    }
   
  
    
    private void Limpar()
    {
        txt_nsalas.setText("");
        capacidade.setText("");
        sala = null;
    }
    
    
    @FXML
    public void Validacao( KeyEvent event )
    {
        if( Character.isLetter(event.getCharacter().charAt(0)))
    	 event.consume();
        
    }
   
    private void CarregarDadosNaTabela()
    {
    	configurarColunas();
       	ObservableList<Sala> lista = FXCollections.observableArrayList();
    	
    	//obtendo os dados da base
    	ResultSet rs = sala.Consultar();
    	try {
	      while( rs.next() )
	       {
		 sala = new Sala();
				
		 sala.setCodigo( rs.getInt("codsala"));
		 sala.setCapacidade( rs.getInt("capacidade"));
		 
		//criando a lista de objectos pessoa
   		lista.add(sala);

				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	//actualizar a tabela com a lista de items
    	tabela.setItems(lista);
    	
    }
    
    private void configurarColunas()
    {
    	
    	//configurar as colunas da tabela
    	coluna_numero.setCellValueFactory( new PropertyValueFactory<>("codigo"));
    	coluna_capacidade.setCellValueFactory( new PropertyValueFactory<>("capacidade"));
    	
    }
    
    @FXML
    public void getDadosFromTable( ActionEvent event )
    {
        
        sala = tabela.getSelectionModel().getSelectedItem();
     
        if( sala != null)
        {
            capacidade.setText(String.valueOf(sala.getCapacidade()));
            txt_nsalas.setText(String.valueOf(sala.getCodigo()));
            tabela.getSelectionModel().select(null);
            
            actualizar.setDisable(false);
            adicionar.setDisable(true);
        
        }
        else
        {
            Alert a = new Alert(Alert.AlertType.ERROR,"Nenhum dado selecionado");
            a.show();
        }
    }
    
    
    @FXML
    public void Actualizar( ActionEvent event )
    {
        Alert a;
        boolean valor = false;
        if( !"".equalsIgnoreCase(capacidade.getText()) && !"".equalsIgnoreCase(txt_nsalas.getText()))
        {
           sala.setCapacidade(Integer.parseInt(capacidade.getText()));
           // sala.setCodigo( Integer.parseInt(numero_sala.getText()));
           // sala.setDesc(descricao.getText());
            valor  = OperacoesBase.Actualizar("Update sala set  descricao ='"+sala.getDesc()+"', capacidade = '"+sala.getCapacidade()+"',codsala= '"+txt_nsalas.getText()+"'  where codsala = '"+sala.getCodigo()+"'");
        
             if( valor )
             {
                 a = new Alert(Alert.AlertType.INFORMATION,"Dados actualizados com sucesso");
                 a.show();
                 CarregarDadosNaTabela();
              
                  adicionar.setDisable(false);
                  actualizar.setDisable(true);
                 // numero_sala.setDisable(false);
                  //Limpar os campos
                  Limpar();
             }
            else
            {
                a = new Alert(Alert.AlertType.ERROR,"Erro ao actualizar");
                a.show();
            }
        }
        else
        {
            a = new Alert(Alert.AlertType.WARNING, "Existem campos Vazios");
            a.show();
        }
        
    }
    
    @FXML
    public void Eliminar( ActionEvent e )
    {
        
        sala = tabela.getSelectionModel().getSelectedItem();
        Alert a;
        if( sala != null )
        {
            
            boolean valor = OperacoesBase.Eliminar("delete from sala where codsala = '"+sala.getCodigo()+"'");

            if( valor )
            {
                CarregarDadosNaTabela();
            }
            else
            {
                a = new Alert(AlertType.ERROR,"Erro ao tentar eliminar a sala");
                a.show();
            }
        }
        else
        {
            a = new Alert(Alert.AlertType.ERROR,"A tabela nao foi selecionada");
            a.show();
                    
        }
    }

    
}
