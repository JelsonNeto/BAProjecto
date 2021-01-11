/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.RelatorioFinanceiro;

import Bd.OperacoesBase;
import definicoes.DefinicoesUnidadePreco;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.Classe;
import modelo.Curso;
import modelo.Estudante;
import modelo.MesAno;
import modelo.NaoPagaramTurmaMes;
import modelo.Pagamento;
import modelo.Preco;
import modelo.Turma;

/**
 * FXML Controller class
 *
 * @author Familia Neto
 */
public class NaopagarammensalidadeController implements Initializable {
    
    
    @FXML private TableColumn<NaoPagaramTurmaMes, String> coluna_aluno;
    @FXML private TableColumn<NaoPagaramTurmaMes, String>  coluna_classe;
    @FXML private TableColumn<NaoPagaramTurmaMes, String>  coluna_turma;
    @FXML private TableColumn<NaoPagaramTurmaMes, String>  coluna_curso;
    @FXML private TableColumn<NaoPagaramTurmaMes, String>  coluna_valor;
    @FXML private TableView<NaoPagaramTurmaMes> tabela;
    @FXML private ComboBox<String> cb_curso;
    @FXML private ComboBox<String> cb_classe;
    @FXML private ComboBox<String> cb_turma;
    @FXML private ComboBox<String> cb_mes;
    @FXML private Label txt_total;
    

    private static int total;
    private static int preco;
    private static int numerario = 0;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InicializaCurso();
    }    

    
    @FXML
    private void SelecionaCurso_InicializaClassePagaram(ActionEvent event) 
    {
        String curso = cb_curso.getSelectionModel().getSelectedItem();
        if( curso != null )
        {
            cb_classe.setItems(Classe.ClassesPorCurso(curso));
        }
    }

    @FXML
    private void SelecionaClasse_InicializaTurmaPagaram(ActionEvent event) 
    {
         String classe = cb_classe.getSelectionModel().getSelectedItem();
        if( classe != null )
        {
            cb_turma.setItems(Turma.ListaTurmasRelaClass(classe));
        }
    }
    
    
    @FXML
    private void selecionaTurma_InicializaMesNaoPagaram(ActionEvent event) 
    {
        String classe = cb_classe.getSelectionModel().getSelectedItem();
        if( classe != null  )
        {
            String turma = cb_turma.getSelectionModel().getSelectedItem();
            if( turma != null )
            {
                InicializaMeses(classe); //inicializa os meses
            }
            else
                cb_mes.setItems(null);
        }
    }
    
    
    @FXML
    private void PreviuosDados(MouseEvent event) 
    {
        
    }

    @FXML
    private void NextDados(MouseEvent event) 
    {
        
    }
    

    @FXML
    private void AlunosNaoPagaram(ActionEvent event) 
    {
        String mes = cb_mes.getSelectionModel().getSelectedItem();
        int codturma = Turma.NameToCode(cb_turma.getSelectionModel().getSelectedItem());
        if( mes !=  null )
        {
            numerario = 0;
            
            ObservableList<String> listaGeral = Estudante.ListaAlunosGeralTurma(codturma);//retorna a lista geral de alunos de uma turma especifica que ja efectuaram o pagamento de propina pelo menos uma vez
            ObservableList<String> listaPagaramMes = Pagamento.ListaAlunosPagaramFiltraTurma(codturma , mes);//retorna a lista de alunos que ja pagaram o mes especifico
            RetornaAlunos( listaGeral ,  listaPagaramMes );//Remove os alunos que efectuaram o pagamento, retornando apenas os que nao efecturaram
        }
    }
    
    @FXML
    private void Imprimir(MouseEvent event) {
    }

   
 /**************************METODOS OPERACIONAIS********************************************************/
 
    private void RetornaAlunos(ObservableList<String> listaGeral, ObservableList<String> listaPagaramMes)
    {
        listaGeral.removeAll(listaPagaramMes);//Remove da lista dos alunos que ja pagaram pelo menos um mes de propina, aqueles alunos que ja pagaram o mes especificado
        RetornaCurso( listaGeral );

    }
   
    private void RetornaCurso(ObservableList<String> listaGeral)
    {
        //elimina os dados da tabela, para gerar outro relatorio com dados actualizados
        OperacoesBase.Eliminar("truncate table relatorioAlunosNaoPagaramMensal");
        SetColunas();
        
        
        ObservableList<NaoPagaramTurmaMes> lista = FXCollections.observableArrayList();
        String anolectivo = MesAno.Get_AnoActualCobranca();
        for( String nome : listaGeral )
        {
            
            NaoPagaramTurmaMes np = new NaoPagaramTurmaMes();
            np.setNome(nome);
            np.setClasse(Estudante.GetClasse(Estudante.NameToCode(nome), anolectivo));
            np.setCurso(Curso.NameAlunoNameCurso(nome));
            np.setTurma(Turma.CodeToName(Turma.NomeAlunoToCodTurma(nome)));
            np.setValor(DefinicoesUnidadePreco.ChangeFromStringToInt(Preco.ValorPreco( np.getClasse() , Curso.NameToCode(np.getCurso()), "Propina")));
            np.setMes(cb_mes.getSelectionModel().getSelectedItem());
            np.setCodigo(++numerario);
            preco = np.getValor();
            
            AdicionarTabelaRelatorioCorrespondente( np.getNome() , np.getCurso(), np.getClasse() , np.getMes() , np.getTurma() , np.getValor());
            
            lista.add(np);
        }
        
        tabela.setItems(lista);
        total = preco * listaGeral.size();
        String somaUnidade = DefinicoesUnidadePreco.RetornaValorEmUnidadeCorrecta(String.valueOf(total));
        txt_total.setText(String.valueOf(somaUnidade));
    }
    
     
    private  void InicializaMeses( String classe )
    {
        String array_Meses[]= null;
        String meses_normais[] = { "Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro"};//meses normais
        String meses_exame[] = { "Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro","Dezembro"};//meses normais
        
        if( EClassedeExame(classe))
            array_Meses = meses_exame;
        else
            if( !EClassedeExame(classe) )
                array_Meses = meses_normais;
        
        cb_mes.setItems(FXCollections.observableArrayList(Arrays.asList(array_Meses)));
    }
   
   private void InicializaCurso()
    {
        cb_curso.setItems(Curso.ListaCursos());
    }
   
   private void SetColunas()
    {
        
        coluna_aluno.setCellValueFactory( new PropertyValueFactory<>("nome"));
        coluna_classe.setCellValueFactory( new PropertyValueFactory<>("classe"));
        coluna_curso.setCellValueFactory( new PropertyValueFactory<>("curso"));
        coluna_turma.setCellValueFactory( new PropertyValueFactory<>("turma"));
        coluna_valor.setCellValueFactory( new PropertyValueFactory<>("valor"));
    }
   
    private boolean EClassedeExame( String classe )
    {
        return "9ª classe".equalsIgnoreCase(classe) || "6ª classe".equalsIgnoreCase(classe) || "12ª classe".equalsIgnoreCase(classe);
    }
   private void AdicionarTabelaRelatorioCorrespondente(String nome, String curso, String classe, String mes, String turma, int valor) {

        String sql = "insert into relatorioAlunosNaoPagaramMensal values( '"+NaoPagaramTurmaMes.LastCodeRecibo()+"' , '"+nome+"' , '"+classe+"', '"+curso+"' ,'"+turma+"' , '"+mes+"', '"+valor+"' )";
        OperacoesBase.Inserir(sql);
      
    }

    
}
