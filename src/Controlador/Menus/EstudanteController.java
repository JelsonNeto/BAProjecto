/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador.Menus;

import Controlador.Estudante.VisualizarEstudanteController;
import Controlador.Estudante.AdicionarEstudanteController;
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
import Controlador.Home.HomeController;
import Validacoes.Valida_UsuarioActivo;
import javafx.scene.control.Label;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class EstudanteController implements Initializable {

    /**
     * Initializes the controller class.
     */

    private static Pane paneGeral;
    private static String nomeUser;
    private static String tipUser;
    private static Pane PanelBack;
    
    @FXML private Pane estudantePrincipal;
    @FXML
    private Label lbl_Adicionar;
    @FXML
    private Label lbl_visualizar;
    
    
    private static Label l_adicionar;
    private static Label l_visualiza;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        l_adicionar = lbl_Adicionar;
        l_visualiza = lbl_visualizar;
        if( Usuario.CodeToTipo(Usuario.NameToCode(Usuario.getUsuario_activo())).equals("Secretário(a)") || Usuario.EAdmin(Usuario.NameToCode(Usuario.getUsuario_activo())))
             Valida_UsuarioActivo.Pedagogico_Estudande(false);
        else
            Valida_UsuarioActivo.Pedagogico_Estudande(true);
    }    
    
    
    @FXML
    public void Adicionar( MouseEvent event )
    {
        AdicionarEstudanteController.setNomeUser(nomeUser);
        AdicionarEstudanteController.setPane(PanelBack);
        AdicionaPainel("/vista/AdicionarEstudante.fxml");
        
    }
    
    @FXML
    public void AddVisualizar( MouseEvent event  )
    {
        VisualizarEstudanteController.setPane(estudantePrincipal);
        VisualizarEstudanteController.setPanelBack(PanelBack);
        VisualizarEstudanteController.setNomeUser(nomeUser);
        VisualizarEstudanteController.setTipoUser(tipUser);
        AdicionaPainel("/vista/VisualizarEstudantesCadastrados.fxml");
    }
    
  /********************METODOS OPERACIONAIS********************************/
  
   private void AdicionaPainel( String caminho )
   {   //remove todos os paineis que estao associados ao painel principal
       estudantePrincipal.getChildren().removeAll();
       
        try {
            //adiciona o painel filho ao principa
            Parent fxml = FXMLLoader.load(getClass().getResource(caminho));
            estudantePrincipal.getChildren().add(fxml);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
 
   

    public static void setNomeUser(String nomeUser) {
        EstudanteController.nomeUser = nomeUser;
    }

    public static void setTipUser(String tipUser) {
        EstudanteController.tipUser = tipUser;
    }

    public static void setPanelBack(Pane PanelBack) {
        EstudanteController.PanelBack = PanelBack;
    }

    public static void setPaneGeral(Pane paneGeral) {
        EstudanteController.paneGeral = paneGeral;
    }

    public static Label getL_adicionar() {
        return l_adicionar;
    }

    public static Label getL_visualiza() {
        return l_visualiza;
    }

    
    
    
     
    
}
