/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Home;

import Controlador.Boletim.GeracaoBoletinController;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import Controlador.Menus.PresencaFaltasController;
import Validacoes.Valida_UsuarioActivo;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class PaginaHomeController implements Initializable {

    @FXML private Label lbl_data;
    private static Pane pane;
    private static String nomeUser;
    @FXML private Pane pane_encarregado;
    @FXML private Pane pane_pauta;
    @FXML private Pane pane_boletim;
    @FXML private Pane pane_faltas;
    
    private static Pane p_encarregado;
    private static Pane p_pauta;
    private static Pane p_boletim;
    private static Pane p_faltas;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        p_encarregado = pane_encarregado;
        p_boletim= pane_boletim;
        p_faltas = pane_faltas;
        p_pauta = pane_pauta;
        SetData_Actual();
        /*if( !Usuario.tipo_Usuario_Activo().equals("secretário") || !Usuario.tipo_Usuario_Activo().equals("Director Geral") || !Usuario.tipo_Usuario_Activo().equals("Director Pedagogico") || !Usuario.tipo_Usuario_Activo().equals("Admin"))
            Valida_UsuarioActivo.Pedagogico_Home(true);
        else
            Valida_UsuarioActivo.Pedagogico_Home(false);*/
        //System.out.println(Usuario.tipo_Usuario_Activo());
            

    }    

    
    
    @FXML
    public void showEncarregado( MouseEvent event  )
    {
        AdicionaPainel("/vista/encarregado.fxml");
    }
    
    @FXML
    public void showinformacoesBoletins( MouseEvent event )
    {
        GeracaoBoletinController.setNomeUser(nomeUser);
        AdicionaPainel("/vista/geracaoBoletin.fxml");
    }
    
    @FXML
    public void showMiniPauta( MouseEvent event )
    {
        AdicionaPainel("/vista/MiniPauta.fxml");
    }
    
    @FXML
    public void showPresencaFaltas( MouseEvent event )
    {
        PresencaFaltasController.setPane(pane);
        AdicionaPainel("/vista/presencaFaltas.fxml");
    }
    
    
    public static void setPane(Pane pane) {
        PaginaHomeController.pane = pane;
    }

    public static void setNomeUser(String nomeUser) {
        PaginaHomeController.nomeUser = nomeUser;
    }
    
    
    
/**************************METODOS OPERACIONAIS************************************/
   private void AdicionaPainel( String caminho )
   {
       //remove todos os paineis que estao associados ao painel principal
       pane.getChildren().removeAll();
       
        try {
            //adiciona o painel filho ao principa
            Parent fxml = (Parent)FXMLLoader.load(getClass().getResource(caminho));
            pane.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(PaginaHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }

   private void SetData_Actual()
   {
       Date date = new Date();
       SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
       lbl_data.setText(format.format(date));
   }

    public static Pane getP_boletim() {
        return p_boletim;
    }

    public static Pane getP_faltas() {
        return p_faltas;
    }

    public static Pane getP_pauta() {
        return p_pauta;
    }

    public static Pane getP_encarregado() {
        return p_encarregado;
    }

   
   
   
   
    
    
}
