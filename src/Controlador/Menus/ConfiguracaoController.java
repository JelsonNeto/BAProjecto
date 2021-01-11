/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Menus;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import modelo.Configuracao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class ConfiguracaoController implements Initializable {
   
    @FXML private ComboBox<String> cb_tipo;
    @FXML private ComboBox<String> cb_nivel;
    @FXML private TextField txt_nome;
    @FXML private ImageView imagem;
    
    String foto = "";
    @FXML
    private Button i;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Inicializa_Tipo();
    }    

    @FXML
    private void openchooser(ActionEvent event) 
    {
        
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Apenas arquivos de imagem", "png", "jpeg", "jpg");
        chooser.getExtensionFilters().add(extensionFilter);
        File file = chooser.showOpenDialog(null);
        
        if( file != null )
            foto = file.getName();
        imagem.setImage( new Image("/icones/"+foto));
        
    }
    
    @FXML
    public void Adicionar( ActionEvent event )
    {
        Preencher();
    }
    
    @FXML
    private void AbirInfo(MouseEvent event) {
    }
    
/**********************************METODOS OPERACIONAIS***************************************************/
    private void Inicializa_Tipo()
    {
        String valor[] = {"Complexo Escolar", "Colégio Particular"," Escola Comparticipada"};
        String valor2[] = {"ENSINO PRIMÁRIO", "ENSINO PRIMÁRIO , Iº E IIº CICLO DO ENSINO SECUNDÁRIO"};
        cb_tipo.setItems(FXCollections.observableArrayList(Arrays.asList(valor)));
        cb_nivel.setItems(FXCollections.observableArrayList(Arrays.asList(valor2)));
    }

    private void Preencher() 
    {
        Alert a;
        String nome = txt_nome.getText();
        String tipo = cb_tipo.getSelectionModel().getSelectedItem();
        String nivel= cb_nivel.getSelectionModel().getSelectedItem();
        if( tipo != null && nivel != null && !nome.equalsIgnoreCase("") && !foto.equalsIgnoreCase("") )
        {
            Configuracao c =new Configuracao();
            c.setFotografia(foto);
            c.setNomeescola(nome);
            c.setTipoescola(tipo);
            c.setTipo_nivel(nivel);
            c.setNome_relatorios(tipo.toUpperCase()+" "+nome.toUpperCase());
            if(c.Adicionar())
            {
                Limpar();
                a = new Alert(Alert.AlertType.INFORMATION, "Dados adicionados com sucesso.");
                a.show();
            }
        }
        
        
    }
    
    private void Limpar()
    {
        txt_nome.setText("");
        cb_tipo.getSelectionModel().select(null);
        cb_nivel.getSelectionModel().select(null);
    }

   
}
