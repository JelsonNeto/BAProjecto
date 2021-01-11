/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Notas;

import Controlador.RelatorioPedagogico.RelatorioPedagogicoController;
import Validacoes.Valida_UsuarioActivo;
import definicoes.DefinicoesPane;
import java.io.IOException;
import java.net.URL;
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

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class NotasController implements Initializable {
   
    @FXML private Pane notasPrincipal;

    private static String nomeUser;
    private static String tipoUser;
    private static DefinicoesPane dp;
    @FXML
    private Label lbl_lancar;
    @FXML
    private Label lbl_consultar;
    @FXML
    private Label lbl_relatorios;
    
    
    private static Label l_lancar;
    private static Label l_consultar;
    private static Label l_relatorios;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        l_consultar = lbl_consultar;
        l_lancar = lbl_lancar;
        l_relatorios = lbl_relatorios;
        dp = new DefinicoesPane(notasPrincipal);
        //Valida_UsuarioActivo.Pedagogico_Notas(true);
    }    

    private void Adicionar(MouseEvent event) {
        
        dp.setPath("/vista/adicionarNotas.fxml");
        dp.AddPane();
    }

    private void AddVisualizar(MouseEvent event) {
        
        VisualizarNotasController.setNomeUser(nomeUser);
        VisualizarNotasController.setTipoUser(tipoUser);
        dp.setPath("/vista/visualizarNotas.fxml");
        dp.AddPane();
        
    }
    
    @FXML
    private void lancar_minipauta(MouseEvent event) 
    {
        dp.setPath("/vista/lancarminipauta.fxml");
        dp.AddPane();
    }

    @FXML
    private void consultar_minipauta(MouseEvent event) 
    {
        ConsultarminipautaController.setPane(notasPrincipal);
        dp.setPath("/vista/consultarminipauta.fxml");
        dp.AddPane();
    }
    
    @FXML
    private void show_Rel_pedagogico(MouseEvent event) 
    {
        RelatorioPedagogicoController.setPane(notasPrincipal);
        dp.setPath("/vista/relatorioPedagogico.fxml");
        dp.AddPane();
    }
      
    
    
/**************************************METODOS AUXILIARES************************************************************************************/
      private void AdicionaPainel( String caminho )
   {
      
       //remove todos os paineis que estao associados ao painel principal
       notasPrincipal.getChildren().removeAll();
       
        try {
            //adiciona o painel filho ao principa
            Parent fxml = (Parent)FXMLLoader.load(getClass().getResource(caminho));
            notasPrincipal.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(NotasController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }

    public static void setNomeUser(String nomeUser) {
        NotasController.nomeUser = nomeUser;
    }

    public static void setTipoUser(String tipoUser) {
        NotasController.tipoUser = tipoUser;
    }

    public static Label getL_consultar() {
        return l_consultar;
    }

    public static Label getL_lancar() {
        return l_lancar;
    }

    public static Label getL_relatorios() {
        return l_relatorios;
    }

    
    

  
      
}
