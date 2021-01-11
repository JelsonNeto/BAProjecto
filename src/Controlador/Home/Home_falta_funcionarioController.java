/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Home;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import definicoes.DefinicoesData;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.FaltaFuncionario;
import modelo.Funcionario;
import modelo.MesAno;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class Home_falta_funcionarioController implements Initializable, Runnable {
    
    @FXML
    private Pane panel_lateral;
    @FXML
    private Label lbl_eliminar;
    
    
    private boolean activo = false;
    @FXML
    private JFXComboBox<String> cb_funcao;
    @FXML
    private JFXComboBox<String> cb_nome;
    @FXML
    private JFXTextField txt_bi;
    @FXML
    private JFXDatePicker data_falta;
    @FXML
    private JFXButton btn_cadastrar;
    @FXML
    private JFXButton btn_actualizar;
    @FXML
    private TableView<FaltaFuncionario> tabela;
    @FXML
    private TableColumn<FaltaFuncionario, String> coluna_nome;
    @FXML
    private TableColumn<FaltaFuncionario, String>  coluna_bi;
    @FXML
    private TableColumn<FaltaFuncionario, String>  coluna_funcao;
    @FXML
    private TableColumn<FaltaFuncionario, String>  coluna_data;
    @FXML
    private Label lbl_total;
    @FXML
    private Label lbl_anolectivo;
    @FXML
    private Label lbl_erroData;
    @FXML
    private Label lbl_justificarFaltas;
    @FXML
    private TableColumn<FaltaFuncionario, String> coluna_estado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Iniciliza_Combos();
        Thread t= new Thread(this);
        t.start();
    }    

    @FXML
    private void show_panel_lateral(MouseEvent event) {
        
       Desactiva_Activa_janela_PressHamburguer();
    }

    @FXML
    private void submenu_carregaTabela(MouseEvent event) 
    {
        Desactiva_JanelaLateral();
        Carregar_Tabela();
    }

    @FXML
    private void submenu_Eliminar(MouseEvent event) 
    {
        FaltaFuncionario f = tabela.getSelectionModel().getSelectedItem();
        if( f !=null )
        {
            f.Eliminacao_Filtrada_codigo();
            Carregar_Tabela();
        }
        else
            lbl_eliminar.setDisable(true);
        Desactiva_JanelaLateral();
        
    }

    @FXML
    private void submenu_showverdetalhes(MouseEvent event) 
    {
        Desactiva_JanelaLateral();
    }

    @FXML
    private void submenu_showinfo(MouseEvent event) 
    {
       Desactiva_JanelaLateral();
    }
    
    @FXML
    private void submenu_showJustificar(MouseEvent event) 
    {
        Desactiva_JanelaLateral();
        FaltaFuncionario f = tabela.getSelectionModel().getSelectedItem();
        if( f != null  )
        {
           Alert a = new Alert(AlertType.CONFIRMATION,"Pretende continuar com a operação?");
           a.setTitle("Justificação de Falta");
           Optional<ButtonType> opcao =  a.showAndWait();
           
           if( opcao.get() == ButtonType.OK )
           {
               boolean valor = f.Justicar_Falta();
               
               if( valor )
               {
                   Alert a2 = new Alert(AlertType.INFORMATION,"Falta Justificada");
                   a2.show();
                   Carregar_Tabela();
               }
           }
            
        }
        
        
    }
    
     @FXML
    private void SelecionaFuncao_InicializaNomes(ActionEvent event)
    {
         String funcao = cb_funcao.getSelectionModel().getSelectedItem();
        if( funcao != null )
        {
            cb_nome.setItems(Funcionario.ListaFuncionarios_por_funcao(funcao));
        }
    }

    @FXML
    private void SelecionaNomes_InicializaBi(ActionEvent event) 
    {
        int codfuncionario = Funcionario.NametoCode(cb_nome.getSelectionModel().getSelectedItem());
        if( codfuncionario != -1 )
        {
            txt_bi.setText(Funcionario.code_to_Bi(codfuncionario));
        }
    }
    
     @FXML
    private void Seleciona_Tabela(MouseEvent event) {
        
        FaltaFuncionario f  = tabela.getSelectionModel().getSelectedItem();
        if( f != null )
        {
            lbl_eliminar.setDisable(false);
            if( f.getEstado_falta() == 0 )
              lbl_justificarFaltas.setDisable(false);
            else
                lbl_justificarFaltas.setDisable(true);
        }
        else
        {
            lbl_eliminar.setDisable(true);
            lbl_justificarFaltas.setDisable(true);
        }
        
    }

    @FXML
    private void registrar(ActionEvent event) 
    {
        Alert a;
        if( txt_bi.getText().equals("") || cb_funcao.getSelectionModel().getSelectedItem() == null || cb_nome.getSelectionModel().getSelectedItem() == null || data_falta.getValue() == null )
        {
            a = new Alert(Alert.AlertType.ERROR,"Existem campos vazios");
            a.show();
        }
        else
        {
            
            int codigo = FaltaFuncionario.UltimoCodigo();
            int codfuncionario = Funcionario.NametoCode(cb_nome.getSelectionModel().getSelectedItem());
            LocalDate data =  data_falta.getValue();
            String trimestre = DefinicoesData.retornaTrimestre(String.valueOf(data));
            String ano_lectivo  = MesAno.Get_AnoActualCobranca();
            int codmes = DefinicoesData.RetornaMes(String.valueOf(data));
            int estado_falta = 0;
            
            //Obtendo os dados das datas a fim de verifica-las
            int ano1 = Integer.parseInt(DefinicoesData.RetornaAnoData(String.valueOf(data)));
            int ano_armazenado = Integer.parseInt(DefinicoesData.RetornaAnoData(FaltaFuncionario.Ultima_Data_Registrada()));
            int mes1 = DefinicoesData.RetornaMes(String.valueOf(data));
            int mes_armazenado = DefinicoesData.RetornaMes(FaltaFuncionario.Ultima_Data_Registrada());
            int dia1 = DefinicoesData.RetornaDiaData(String.valueOf(data));
            int dia_armazenado = DefinicoesData.RetornaDiaData(FaltaFuncionario.Ultima_Data_Registrada());
            
            
           //verifica se a falta ja foi cadastrada nesta data para esse funcionario
            if( !FaltaFuncionario.Verificar_Falta_Existe(codfuncionario, data) )
            {
                //verifica se os anos sao iguais
                if( ano1 == ano_armazenado  )
                {
                    if( mes1 == mes_armazenado )
                    {
                        if( dia1 > dia_armazenado )
                        {
                            FaltaFuncionario f = new FaltaFuncionario(codigo, codfuncionario, data, trimestre, ano_lectivo,codmes,estado_falta);
                            if(f.Adicionar())
                            {
                                a = new Alert(Alert.AlertType.INFORMATION, "Falta Registrada com sucesso");
                                a.show();
                                Limpar();
                                Carregar_Tabela();
                                lbl_erroData.setVisible(false);
                            }
                            else{
                                 a = new Alert(Alert.AlertType.ERROR, "Erro  ao registrar a falta");
                                 a.show();
                            }
                        }
                        else
                        {
                           lbl_erroData.setText("O dia esta incorrecto");
                           lbl_erroData.setVisible(true);
                        }
                    }else
                      {
                        if( mes1 > mes_armazenado )
                         {
                              FaltaFuncionario f = new FaltaFuncionario(codigo, codfuncionario, data, trimestre, ano_lectivo,codmes,estado_falta);
                              if(f.Adicionar())
                              {
                                 a = new Alert(Alert.AlertType.INFORMATION, "Falta Registrada com sucesso");
                                 a.show();
                                 Limpar();
                                 Carregar_Tabela();
                                 lbl_erroData.setVisible(false);
                              }
                              else{
                                  a = new Alert(Alert.AlertType.ERROR, "Erro  ao registrar a falta");
                                  a.show();
                              }
                         }
                        else
                        {
                            
                            lbl_erroData.setText("O mês esta incorrecto");
                            lbl_erroData.setVisible(true);
                        }
                      }
                }
                     else
                     {
                        if( ano1 > ano_armazenado )
                        {
                             FaltaFuncionario f = new FaltaFuncionario(codigo, codfuncionario, data, trimestre, ano_lectivo,codmes,estado_falta);
                            if(f.Adicionar())
                            {
                                a = new Alert(Alert.AlertType.INFORMATION, "Falta Registrada com sucesso");
                                a.show();
                                Limpar();
                                Carregar_Tabela();
                                lbl_erroData.setVisible(false);
                            }
                            else{
                                 a = new Alert(Alert.AlertType.ERROR, "Erro  ao registrar a falta");
                                 a.show();
                            }
                        }
                        else
                         {
                           lbl_erroData.setText("O ano esta incorrecto");
                           lbl_erroData.setVisible(true);
                        }
                            
                     }                   
               
            }
            else
            {
                a = new Alert(Alert.AlertType.WARNING,"Esta falta ja foi registrada");
                a.show();
                lbl_erroData.setVisible(false);
            }
            
        }
        
    }
    

    @FXML
    private void actualizar(ActionEvent event) {
    }
    
    
 /*******************METODOS OPERACIONAIS******************************************************/
    private void Iniciliza_Combos()
    {
        String funcao[] ={"Director geral","Director Pedagógico","Director Administrativo","Subdirector Administrativo","Secretário(a)","Secretário(a) Pedagógico(a)","Chefe de secretaria","Contabilista","Coordenador(a)","Professor(a)","Jardineiro","Motorista","Cozinheira","Limpeza", "Segurança","Vigilante"};
        cb_funcao.setItems( FXCollections.observableArrayList(Arrays.asList(funcao)));
        lbl_anolectivo.setText(MesAno.Get_AnoActualCobranca());
        lbl_erroData.setVisible(false);
    }
    
    private void Desactiva_JanelaLateral()
    {
         if( activo )
        {
            panel_lateral.setVisible(false);
            activo = false;
        }
    }
    
    private void Desactiva_Activa_janela_PressHamburguer()
    {
        if( !activo )
        {
            panel_lateral.setVisible(true);
            activo = true;
        }
        else
        {
            panel_lateral.setVisible(false);
            activo = false;
        }
    }
    
    private void Limpar()
    {
        txt_bi.clear();
        cb_funcao.getSelectionModel().clearSelection();
        cb_nome.getSelectionModel().clearSelection();
        data_falta.setValue(null);
    }
    
    private void Carregar_Tabela()
    {
        SetColunas();
        tabela.setItems(FaltaFuncionario.ListaFaltasFaltaFuncionarios_Tabela());
        lbl_total.setText(String.valueOf(tabela.getItems().size())+" Registros");
    }
    private void SetColunas()
    {
        coluna_bi.setCellValueFactory( new PropertyValueFactory<>("bi_cedula"));
        coluna_data.setCellValueFactory( new PropertyValueFactory<>("data"));
        coluna_funcao.setCellValueFactory( new PropertyValueFactory<>("funcao"));
        coluna_nome.setCellValueFactory( new PropertyValueFactory<>("nome_funcionario"));
        coluna_estado.setCellValueFactory( new PropertyValueFactory<>("estado"));
    }

    @Override
    public void run() 
    {
           String data_sistema = DefinicoesData.RetornaDatadoSistema().toString();
            int dia_sistema = DefinicoesData.RetornaDiaData(data_sistema);
            for( FaltaFuncionario f : FaltaFuncionario.Lista_NaoJustificadas()  )
            {
                int dia_falta = DefinicoesData.RetornaDiaData(f.getData().toString());
                if( dia_sistema > dia_falta )
                    FaltaFuncionario.Confirmar_Faltas(f);
            }
               
    }

  

   

   
}