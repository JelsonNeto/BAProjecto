/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dialogos.Controlador;

import Bd.OperacoesBase;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.VisualizaPreco;

/**
 * FXML Controller class
 *
 * @author JN
 */
public class editarPreco implements Initializable {

    @FXML
    private DialogPane pane;
    @FXML
    private Label lbl_curso;
    @FXML
    private Label lbl_classe;
    @FXML
    private JFXTextField txt_novoPreco;
    
    private static VisualizaPreco visualizarPreco;
    @FXML
    private Label lbl_precoActual;
    @FXML
    private Label lbl_emolumento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setCampos();
    }    

    @FXML
    private void Fechar(MouseEvent event) {
       Stage stage = (Stage)pane.getScene().getWindow();
       stage.close();
    }

    @FXML
    private void ActualizarPreco(ActionEvent event) {
        if(txt_novoPreco.getText().isEmpty()){
            Alert a = new Alert(AlertType.WARNING, "Por favor, insira o novo preço");
            a.show();
        }
        else
        {
          boolean feito =   OperacoesBase.Actualizar("update preco set valor= '"+txt_novoPreco.getText()+"' where preco.codpreco = '"+visualizarPreco.getCodigo()+"'");
         if(feito)
         {
             Alert a = new Alert(AlertType.INFORMATION, "Preço alterado com sucesso");
             a.show();
         }else
         {
              Alert a = new Alert(AlertType.ERROR, "Ocorreu um erro");
             a.show();
         }
        }
    }

    public static void setVisualizarPreco(VisualizaPreco visualizarPreco) {
        editarPreco.visualizarPreco = visualizarPreco;
    }
    
   private void setCampos() {
        this.lbl_classe.setText(visualizarPreco.getClasse());
        lbl_curso.setText(visualizarPreco.getCurso());
        lbl_emolumento.setText(visualizarPreco.getEfeito());
        lbl_precoActual.setText(visualizarPreco.getValor_tabela());
    }

    @FXML
    private void Verifica(KeyEvent event) {
        if(!Character.isDigit(event.getCharacter().charAt(0)))
            event.consume();
    }
    
    
}
