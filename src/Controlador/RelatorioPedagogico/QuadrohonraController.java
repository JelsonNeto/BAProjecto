/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioPedagogico;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import modelo.Estudante;
import modelo.MiniPauta;
import modelo.Turma;
import modelo.matricula_confirmacao;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class QuadrohonraController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void CarregaTabela(ActionEvent event) {
    }
    
 
/*********************************METODOS OPERACIONAIS***************************************************************/
    private void VerificaDados()
    {
       
    }
    
    
    public void Estudante_MaiorMedia( int coddisciplina , int codaluno )
    {
        double ct = MiniPauta.retorna_ct1(coddisciplina, matricula_confirmacao.CodAlunoToCodMatricula(codaluno));
        System.out.println("Nome:"+Estudante.CodeToName(codaluno)+"==>Ct1:"+ct);
    }
}
