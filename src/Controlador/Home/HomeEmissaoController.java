/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Home;

import Controlador.Emissao.CriarhorarioController;
import Validacoes.Valida_UsuarioActivo;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class HomeEmissaoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private static Pane pane;
    @FXML
    private Pane pane_emissao;
    @FXML
    private Pane pane_horario;
    @FXML
    private Pane pane_declaracao;
    @FXML
    private Pane pane_comunicaddos;
    
    
    private static Pane pe;
    private static Pane ph;
    private static Pane pd;
    private static Pane pc;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        pe = pane_emissao;
        ph = pane_horario;
        pd = pane_declaracao;
        pc = pane_comunicaddos;
        
       // Valida_UsuarioActivo.Emissao(true);
    }    

    @FXML
    private void showCartao(MouseEvent event) 
    {
        AdicionaPainel("/vista/emitircartao.fxml");
    }

    @FXML
    private void showhorario(MouseEvent event) 
    {
        CriarhorarioController.setPane(pane);
        AdicionaPainel("/vista/criarhorario.fxml");
    }
    

    
    @FXML
    private void showCartaoCertificado(MouseEvent event)
    {
        AdicionaPainel("/vista/emitircertificados.fxml");
    }

    @FXML
    private void showComunicados(MouseEvent event) {
    }
    
      
    private void AdicionaPainel( String caminho )
    {
       //remove todos os paineis que estao associados ao painel principal
       pane.getChildren().removeAll();
       
        try {
            //adiciona o painel filho ao principa
            Parent fxml = (Parent)FXMLLoader.load(getClass().getResource(caminho));
            pane.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setPane(Pane pane) {
        HomeEmissaoController.pane = pane;
    }

    public static Pane getPc() {
        return pc;
    }

    public static Pane getPe() {
        return pe;
    }

    public static Pane getPh() {
        return ph;
    }

    public static Pane getPd() {
        return pd;
    }

    
    
    
    
}
