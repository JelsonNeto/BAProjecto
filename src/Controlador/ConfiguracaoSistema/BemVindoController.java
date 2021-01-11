/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.ConfiguracaoSistema;

import definicoes.DefinicoesPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import modelo.Configuracao;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class BemVindoController implements Initializable {
   
    @FXML
    private AnchorPane principal;
    @FXML
    private ImageView imagem;
    @FXML
    private Label lbl_nome;
    @FXML
    private Label lbl_tipo;
    @FXML
    private Label lbl_nivel;
    @FXML
    private Label username;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaLabels();
    }    


    @FXML
    private void Adicionar(ActionEvent event) {
        
        DefinicoesPane dp = new DefinicoesPane();
        dp.CallOtherWindow("login", principal);
        
    }
    
    @FXML
    private void Editar(MouseEvent event) {
        
        TelaCONFIGURACAOController.setCon(Configuracao.DadosConfiguracao());
        DefinicoesPane dp = new DefinicoesPane();
        dp.CallOtherWindow("telaCONFIGURACAO", principal);
    }
    
/****************************METODOS AUXILIARES******************************************************/
    private void InicializaLabels()
    {
        Configuracao con = Configuracao.DadosConfiguracao();
        lbl_nivel.setText(con.getTipo_nivel());
        lbl_nome.setText(con.getNomeescola());
        lbl_tipo.setText(con.getTipoescola());
       // username.setText(Usuario.Obter_Usuario_por_codigo(Usuario.RetornaUltimoCodigoUser()).getUsername());
        imagem.setImage( new Image("/icones/"+con.getFotografia()));
    }

    
}
