/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Emissao;

import Bd.OperacoesBase;
import Validacoes.ValidarHorario;
import definicoes.DefinicoesAdicionarDialogo;
import definicoes.DefinicoesPane;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Classe;
import modelo.Curso;
import modelo.Disciplina;
import modelo.Horario;
import modelo.MesAno;
import modelo.Turma;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class CriarhorarioController implements Initializable {
    
    @FXML private ComboBox<String> cb_curso;
    @FXML private ComboBox<String> cb_turma;
    @FXML private ComboBox<String> cb_classe;
   //Tempos 
    @FXML private TextField txt_tempo1;
    @FXML private TextField txt_tempo2;
    @FXML private TextField txt_tempo3;
    @FXML private TextField txt_tempo4;
    @FXML private TextField txt_tempo5;
    @FXML private TextField txt_tempo6;
  //Dias de semana - Segunda Feira
    @FXML private ComboBox<String> cb_dia1_seg;
    @FXML private ComboBox<String> cb_dia2_seg;
    @FXML private ComboBox<String> cb_dia3_seg;
    @FXML private ComboBox<String> cb_dia4_seg;
    @FXML private ComboBox<String> cb_dia5_seg;
    @FXML private ComboBox<String> cb_dia6_seg;
  //Dias de semana - Terca Feira
    @FXML private ComboBox<String> cb_dia1_ter;
    @FXML private ComboBox<String> cb_dia2_ter;
    @FXML private ComboBox<String> cb_dia3_ter;
    @FXML private ComboBox<String> cb_dia4_ter;
    @FXML private ComboBox<String> cb_dia5_ter;
    @FXML private ComboBox<String> cb_dia6_ter;
    //Dias de semana - Quarta Feira
    @FXML private ComboBox<String> cb_dia1_qua;
    @FXML private ComboBox<String> cb_dia2_qua;
    @FXML private ComboBox<String> cb_dia3_qua;
    @FXML private ComboBox<String> cb_dia4_qua;
    @FXML private ComboBox<String> cb_dia5_qua;
    @FXML private ComboBox<String> cb_dia6_qua;
    //Dias de semana - Quinta Feira
    @FXML private ComboBox<String> cb_dia1_qui;
    @FXML private ComboBox<String> cb_dia2_qui;
    @FXML private ComboBox<String> cb_dia3_qui;
    @FXML private ComboBox<String> cb_dia4_qui;
    @FXML private ComboBox<String> cb_dia5_qui;
    @FXML private ComboBox<String> cb_dia6_qui;
    //Dias de semana - Sexta Feira
    @FXML private ComboBox<String> cb_dia1_sex;
    @FXML private ComboBox<String> cb_dia2_sex;
    @FXML private ComboBox<String> cb_dia3_sex;
    @FXML private ComboBox<String> cb_dia4_sex;
    @FXML private ComboBox<String> cb_dia5_sex;
    @FXML private ComboBox<String> cb_dia6_sex;
    
    private static Pane pane;
    
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializarCurso();
    }    

    @FXML
    private void SelecionaOCurso_InicializaClasse(ActionEvent event) 
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        if( curso != null )
            cb_classe.setItems(Classe.ClassesPorCurso(curso));
    }

    @FXML
    private void SelecionaTurmaInicializarAno(ActionEvent event) 
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        String turma = cb_turma.getSelectionModel().getSelectedItem();
        if( turma != null )
        {
            if( Horario.HorarioJaExistente(Turma.NameToCode(turma), MesAno.Get_AnoActualCobranca()))
            {
                Alert a = new Alert(AlertType.CONFIRMATION, "Ja existe um horario para esta turma. Deseja sobrepor?");
                Optional<ButtonType> acao = a.showAndWait();
                if( acao.get() == ButtonType.OK)
                {
                    OperacoesBase.Eliminar("delete from horario where codturma = '"+Turma.NameToCode(turma)+"' and anolectivo = '"+MesAno.Get_AnoActualCobranca()+"'");
                    InicializaDisciplinas();
                    desabilita(false);
                }
                else
                {
                    desabilita(true);
                }
                
            }
            else
            {
                desabilita(false);
                InicializaDisciplinas();
            }            
        }
        
        
    }

    @FXML
    private void SelecionaClasse_InicializaTurma(ActionEvent event) 
    {
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        if( classe != null && curso != null )
            cb_turma.setItems(Turma.ListaTurmasRelaClasse_CodCurso(classe, Curso.NameToCode(curso)));
    }

    @FXML
    private void visualizarHorario(ActionEvent event) 
    {
        DefinicoesPane d = new DefinicoesPane("/vista/visualizarhorario.fxml", pane);
        d.AddPane();
    }
    
    public static void setPane(Pane pane) 
    {
        CriarhorarioController.pane = pane;
    }
    
    @FXML
    private void Adicionar( ActionEvent event )
    {
        Preencher();
    }

    @FXML
    private void AbirInfo(MouseEvent event) 
    {
        DefinicoesAdicionarDialogo d = new DefinicoesAdicionarDialogo();
        d.AddDialogo("/dialogos/adicionarEstudanteAjuda.fxml");
    }
    
    
   
/*****************************************METODOS OPERACIONAIS************************************************/

    private void InicializarCurso()
    {
        cb_curso.setItems(Curso.ListaCursos());
    }
    
    
    private void Preencher()
    {
        Alert a;
        if(!ValidarHorario.EstaoVaziosTempos(txt_tempo1.getText(), txt_tempo2.getText(), txt_tempo3.getText(), txt_tempo4.getText(), txt_tempo5.getText(), txt_tempo6.getText()))
        {
            //Segunda feira
            if( !ValidarHorario.EstaoVaziosDisciplinasSegunda(cb_dia1_seg.getSelectionModel().getSelectedItem(), cb_dia2_seg.getSelectionModel().getSelectedItem(), cb_dia3_seg.getSelectionModel().getSelectedItem(), cb_dia4_seg.getSelectionModel().getSelectedItem(), cb_dia5_seg.getSelectionModel().getSelectedItem(), cb_dia6_seg.getSelectionModel().getSelectedItem()) )
            {
                //Terca Feira
                if( !ValidarHorario.EstaoVaziosDisciplinasSegunda(cb_dia1_ter.getSelectionModel().getSelectedItem(), cb_dia2_ter.getSelectionModel().getSelectedItem(), cb_dia3_ter.getSelectionModel().getSelectedItem(), cb_dia4_ter.getSelectionModel().getSelectedItem(), cb_dia5_ter.getSelectionModel().getSelectedItem(), cb_dia6_ter.getSelectionModel().getSelectedItem()) )
                {
                    //Quarta Feira
                    if( !ValidarHorario.EstaoVaziosDisciplinasSegunda(cb_dia1_qua.getSelectionModel().getSelectedItem(), cb_dia2_qua.getSelectionModel().getSelectedItem(), cb_dia3_qua.getSelectionModel().getSelectedItem(), cb_dia4_qua.getSelectionModel().getSelectedItem(), cb_dia5_qua.getSelectionModel().getSelectedItem(), cb_dia6_qua.getSelectionModel().getSelectedItem()) )
                    {
                        //Quinta feira
                             if(!ValidarHorario.EstaoVaziosDisciplinasSegunda(cb_dia1_qui.getSelectionModel().getSelectedItem(), cb_dia2_qui.getSelectionModel().getSelectedItem(), cb_dia3_qui.getSelectionModel().getSelectedItem(), cb_dia4_qui.getSelectionModel().getSelectedItem(), cb_dia5_qui.getSelectionModel().getSelectedItem(), cb_dia6_qui.getSelectionModel().getSelectedItem()))
                             {
                                 //Sexta Feira
                                 if( !ValidarHorario.EstaoVaziosDisciplinasSegunda(cb_dia1_sex.getSelectionModel().getSelectedItem(), cb_dia2_sex.getSelectionModel().getSelectedItem(), cb_dia3_sex.getSelectionModel().getSelectedItem(), cb_dia4_sex.getSelectionModel().getSelectedItem(), cb_dia5_sex.getSelectionModel().getSelectedItem(), cb_dia6_sex.getSelectionModel().getSelectedItem()) )
                                 {
                                     SetCampos();
                                 }
                                 else
                                 {
                                     a = new Alert(Alert.AlertType.ERROR,"Existem colunas na Sexta feira não selecionadas.");
                                     a.show();
                                 }
                             }
                             else
                             {
                                 a = new Alert(Alert.AlertType.ERROR,"Existem colunas na Quinra feira não selecionadas.");
                                 a.show();
                             }
                    }
                    else
                    {
                        a = new Alert(Alert.AlertType.ERROR,"Existem colunas na Quarta feira não selecionadas.");
                        a.show();
                    }
                    
                }
                else
                {
                    a = new Alert(Alert.AlertType.ERROR,"Existem colunas na Terça feira não selecionadas.");
                    a.show();
                }
            }
            else
            {
                 a = new Alert(Alert.AlertType.ERROR,"Existem colunas na Segunda feira não selecionadas.");
                 a.show();
            }
        }
        else
        {
            a = new Alert(Alert.AlertType.ERROR,"Existem Tempos vazios.");
            a.show();
        }
        
    }
    
    
    private void SetCampos()
    {
        
        String tempo1 = txt_tempo1.getText();
        String tempo2 = txt_tempo2.getText();
        String tempo3= txt_tempo3.getText();
        String tempo4 =txt_tempo4.getText();
        String tempo5 = txt_tempo5.getText();
        String tempo6 = txt_tempo6.getText();
        
        //segunda
        String se1 = cb_dia1_seg.getSelectionModel().getSelectedItem();
        String se2 = cb_dia2_seg.getSelectionModel().getSelectedItem();
        String se3 = cb_dia3_seg.getSelectionModel().getSelectedItem();
        String se4 = cb_dia4_seg.getSelectionModel().getSelectedItem();
        String se5 = cb_dia5_seg.getSelectionModel().getSelectedItem();
        String se6 = cb_dia6_seg.getSelectionModel().getSelectedItem();
        
        //terca
        String te1 = cb_dia1_ter.getSelectionModel().getSelectedItem();
        String te2 = cb_dia2_ter.getSelectionModel().getSelectedItem();
        String te3 = cb_dia3_ter.getSelectionModel().getSelectedItem();
        String te4 = cb_dia4_ter.getSelectionModel().getSelectedItem();
        String te5 = cb_dia5_ter.getSelectionModel().getSelectedItem();
        String te6 = cb_dia6_ter.getSelectionModel().getSelectedItem();
        
        //Quarta
        String qua1 = cb_dia1_qua.getSelectionModel().getSelectedItem();
        String qua2 = cb_dia2_qua.getSelectionModel().getSelectedItem();
        String qua3 = cb_dia3_qua.getSelectionModel().getSelectedItem();
        String qua4 = cb_dia4_qua.getSelectionModel().getSelectedItem();
        String qua5 = cb_dia5_qua.getSelectionModel().getSelectedItem();
        String qua6 = cb_dia6_qua.getSelectionModel().getSelectedItem();
        
        //Quinta
        String qui1 = cb_dia1_qui.getSelectionModel().getSelectedItem();
        String qui2 = cb_dia2_qui.getSelectionModel().getSelectedItem();
        String qui3 = cb_dia3_qui.getSelectionModel().getSelectedItem();
        String qui4 = cb_dia4_qui.getSelectionModel().getSelectedItem();
        String qui5 = cb_dia5_qui.getSelectionModel().getSelectedItem();
        String qui6 = cb_dia6_qui.getSelectionModel().getSelectedItem();
        
        //Sexta
        String sexta1 = cb_dia1_sex.getSelectionModel().getSelectedItem();
        String sexta2 = cb_dia2_sex.getSelectionModel().getSelectedItem();
        String sexta3 = cb_dia3_sex.getSelectionModel().getSelectedItem();
        String sexta4 = cb_dia4_sex.getSelectionModel().getSelectedItem();
        String sexta5 = cb_dia5_sex.getSelectionModel().getSelectedItem();
        String sexta6 = cb_dia6_sex.getSelectionModel().getSelectedItem();
       
     
      
        int verifica = 0;
        Horario h = new Horario();
        
        h.setCodturma(Turma.NameToCode(cb_turma.getSelectionModel().getSelectedItem()));
        h.setAnolectivo(MesAno.Get_AnoActualCobranca());
      
        //FILA 1
        h.setTempo(tempo1);
        h.setDia1(se1);
        h.setDia2(te1);
        h.setDia3(qua1);
        h.setDia4(qui1);
        h.setDia5(sexta1);
        verifica += (h.adicionar())?1:0;
        
        //FILA 2
        h.setTempo(tempo2);
        h.setDia1(se2);
        h.setDia2(te2);
        h.setDia3(qua2);
        h.setDia4(qui2);
        h.setDia5(sexta2);
        verifica += (h.adicionar())?1:0;
        
        //FILA 3
        h.setTempo(tempo3);
        h.setDia1(se3);
        h.setDia2(te3);
        h.setDia3(qua3);
        h.setDia4(qui3);
        h.setDia5(sexta3);
        verifica += (h.adicionar())?1:0;
        
        //FILA 4
        h.setTempo(tempo4);
        h.setDia1(se4);
        h.setDia2(te4);
        h.setDia3(qua4);
        h.setDia4(qui4);
        h.setDia5(sexta4);
        verifica += (h.adicionar())?1:0;
        
        //FILA 5
        h.setTempo(tempo5);
        h.setDia1(se5);
        h.setDia2(te5);
        h.setDia3(qua5);
        h.setDia4(qui5);
        h.setDia5(sexta5);
        verifica += (h.adicionar())?1:0;
        
        //FILA 5
        h.setTempo(tempo6);
       h.setDia1(se6);
        h.setDia2(te6);
        h.setDia3(qua6);
        h.setDia4(qui6);
        h.setDia5(sexta6);
        verifica += (h.adicionar())?1:0;
        
        if( verifica >=6 )
        {
            Alert a = new Alert(AlertType.INFORMATION, "Horario Criado com sucesso");
            a.show();
        }
        else
        {
            Alert a = new Alert(AlertType.ERROR, "Erro ao criar o Horario.");
            a.show();
        }
        
      Limpar();
    }
    
    
    private void InicializaDisciplinas()
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        
        cb_dia1_seg.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia2_seg.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia3_seg.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia4_seg.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia5_seg.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia6_seg.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia1_seg.getItems().add("Borla");
        cb_dia2_seg.getItems().add("Borla");
        cb_dia3_seg.getItems().add("Borla");
        cb_dia4_seg.getItems().add("Borla");
        cb_dia5_seg.getItems().add("Borla");
        cb_dia6_seg.getItems().add("Borla");
        
        
        cb_dia1_ter.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia2_ter.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia3_ter.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia4_ter.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia5_ter.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia6_ter.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia1_ter.getItems().add("Borla");
        cb_dia2_ter.getItems().add("Borla");
        cb_dia3_ter.getItems().add("Borla");
        cb_dia4_ter.getItems().add("Borla");
        cb_dia5_ter.getItems().add("Borla");
        cb_dia6_ter.getItems().add("Borla");
        
        
        cb_dia1_qua.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia2_qua.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia3_qua.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia4_qua.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia5_qua.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia6_qua.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia1_qua.getItems().add("Borla");
        cb_dia2_qua.getItems().add("Borla");
        cb_dia3_qua.getItems().add("Borla");
        cb_dia4_qua.getItems().add("Borla");
        cb_dia5_qua.getItems().add("Borla");
        cb_dia6_qua.getItems().add("Borla");
        
        
        cb_dia1_qui.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia2_qui.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia3_qui.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia4_qui.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia5_qui.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia6_qui.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia1_qui.getItems().add("Borla");
        cb_dia2_qui.getItems().add("Borla");
        cb_dia3_qui.getItems().add("Borla");
        cb_dia4_qui.getItems().add("Borla");
        cb_dia5_qui.getItems().add("Borla");
        cb_dia6_qui.getItems().add("Borla");
        
        
        cb_dia1_sex.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia2_sex.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia3_sex.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia4_sex.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia5_sex.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia6_sex.setItems(Disciplina.ListaDisciplinasCurso_Classe(curso, classe));
        cb_dia1_sex.getItems().add("Borla");
        cb_dia2_sex.getItems().add("Borla");
        cb_dia3_sex.getItems().add("Borla");
        cb_dia4_sex.getItems().add("Borla");
        cb_dia5_sex.getItems().add("Borla");
        cb_dia6_sex.getItems().add("Borla");
    }
    
    private void Limpar()
    {
        
        cb_dia1_seg.getSelectionModel().select(null);
        cb_dia2_seg.getSelectionModel().select(null);
        cb_dia3_seg.getSelectionModel().select(null);
        cb_dia4_seg.getSelectionModel().select(null);
        cb_dia5_seg.getSelectionModel().select(null);
        cb_dia6_seg.getSelectionModel().select(null);
        
        cb_dia1_ter.getSelectionModel().select(null);
        cb_dia2_ter.getSelectionModel().select(null);
        cb_dia3_ter.getSelectionModel().select(null);
        cb_dia4_ter.getSelectionModel().select(null);
        cb_dia5_ter.getSelectionModel().select(null);
        cb_dia6_ter.getSelectionModel().select(null);
        
        cb_dia1_qua.getSelectionModel().select(null);
        cb_dia2_qua.getSelectionModel().select(null);
        cb_dia3_qua.getSelectionModel().select(null);
        cb_dia4_qua.getSelectionModel().select(null);
        cb_dia5_qua.getSelectionModel().select(null);
        cb_dia6_qua.getSelectionModel().select(null);
        
        cb_dia1_qui.getSelectionModel().select(null);
        cb_dia2_qui.getSelectionModel().select(null);
        cb_dia3_qui.getSelectionModel().select(null);
        cb_dia4_qui.getSelectionModel().select(null);
        cb_dia5_qui.getSelectionModel().select(null);
        cb_dia6_qui.getSelectionModel().select(null);
        
        cb_dia1_sex.getSelectionModel().select(null);
        cb_dia2_sex.getSelectionModel().select(null);
        cb_dia3_sex.getSelectionModel().select(null);
        cb_dia4_sex.getSelectionModel().select(null);
        cb_dia5_sex.getSelectionModel().select(null);
        cb_dia6_sex.getSelectionModel().select(null);
        
        txt_tempo1.setText("");
        txt_tempo2.setText("");
        txt_tempo3.setText("");
        txt_tempo4.setText("");
        txt_tempo5.setText("");
        txt_tempo6.setText("");
    }

    private void desabilita( boolean valor ) 
    {
       
        cb_dia1_seg.setDisable(valor);
        cb_dia2_seg.setDisable(valor);
        cb_dia3_seg.setDisable(valor);
        cb_dia4_seg.setDisable(valor);
        cb_dia5_seg.setDisable(valor);
        cb_dia6_seg.setDisable(valor);
        
        cb_dia1_ter.setDisable(valor);
        cb_dia2_ter.setDisable(valor);
        cb_dia3_ter.setDisable(valor);
        cb_dia4_ter.setDisable(valor);
        cb_dia5_ter.setDisable(valor);
        cb_dia6_ter.setDisable(valor);
        
        cb_dia1_qua.setDisable(valor);
        cb_dia2_qua.setDisable(valor);
        cb_dia3_qua.setDisable(valor);
        cb_dia4_qua.setDisable(valor);
        cb_dia5_qua.setDisable(valor);
        cb_dia6_qua.setDisable(valor);
        
        cb_dia1_qui.setDisable(valor);
        cb_dia2_qui.setDisable(valor);
        cb_dia3_qui.setDisable(valor);
        cb_dia4_qui.setDisable(valor);
        cb_dia5_qui.setDisable(valor);
        cb_dia6_qui.setDisable(valor);
        
        cb_dia1_sex.setDisable(valor);
        cb_dia2_sex.setDisable(valor);
        cb_dia3_sex.setDisable(valor);
        cb_dia4_sex.setDisable(valor);
        cb_dia5_sex.setDisable(valor);
        cb_dia6_sex.setDisable(valor);
        
        txt_tempo1.setDisable(valor);
        txt_tempo2.setDisable(valor);
        txt_tempo3.setDisable(valor);
        txt_tempo4.setDisable(valor);
        txt_tempo5.setDisable(valor);
        txt_tempo6.setDisable(valor);
    }
    
}
