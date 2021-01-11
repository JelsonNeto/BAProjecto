/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import Bd.OperacoesBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Familia Neto
 */
public class Classe {

    public static  void InserirClasseBd() {

        String array_p[]= null;
        String primaria[] = {"Iniciação","1ª classe","2ª classe","3ª classe","4ª classe","5ª classe","6ª classe"};
        String secundaria[] = {"7ª classe","8ª classe","9ª classe"};
        String medio[]  = {"10ª classe","11ª classe","12ª classe"} ;
        String nome = Curso.LastName();
        int codcurso =  Curso.NameToCode(nome);
        
        if( "Primária".equalsIgnoreCase(nome))
                array_p = primaria;
        else
                if( "Ensino Secundário Iº Ciclo".equalsIgnoreCase(nome))
                    array_p = secundaria;
        else
                   if( !"".equalsIgnoreCase(nome))
                       array_p = medio;
         
        for( String valor : array_p  )
        {
            int codclasse = NameToCode(valor);
            OperacoesBase.Inserir("insert into curso_classe values( '"+codcurso+"' , '"+codclasse+"' )");
        }
        
    }

    public static String CodeToName(int codclasse) 
    {
         String valor = "";
         ResultSet rs = OperacoesBase.Consultar("select nome_classe from classe where codclasse = '"+codclasse+"'"); //consulta
        try {
            while( rs.next() )
                valor = rs.getString("nome_classe");  //adiciona os nome dos cursos na lista
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return valor;
    }
    
    
    private int retornaUltimoId()
    {
         int codigo=0;
         ResultSet rs = OperacoesBase.Consultar("select codclasse from classe"); //consulta
        try {
            while( rs.next() )
                codigo = rs.getInt("codclasse");  //adiciona os nome dos cursos na lista
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return ++codigo;
    }
    
    public static int NameToCode( String nome )
    {
         int codigo=0;
         ResultSet rs = OperacoesBase.Consultar("select codclasse from classe where nome_classe = '"+nome+"'"); //consulta
        try {
            while( rs.next() )
                codigo = rs.getInt("codclasse");  //adiciona os nome dos cursos na lista
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return codigo;
    }
    
    public static ObservableList<String> ClassesPorCurso( String curso )
    {
        int codcurso = Curso.NameToCode(curso);
        ResultSet rs = OperacoesBase.Consultar("select nome_classe from classe inner join curso_classe using(codclasse) inner join curso using(codcurso) where codcurso = '"+codcurso+"'");
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome_classe"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Classe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public static ObservableList<Classe_Curso> ListaClassesExistentes_Curso()
    {
        ResultSet rs = OperacoesBase.Consultar("select nome_classe,nome_curso from classe inner join curso_classe using(codclasse) inner join curso using(codcurso) order by codclasse");
        ObservableList<Classe_Curso> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                Classe_Curso c = new Classe_Curso();
                c.setClasse(rs.getString("nome_classe"));
                c.setCurso(rs.getString("nome_curso"));
                
                lista.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Classe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public static ObservableList<String> ListaClassesExistente()
    {
        ResultSet rs = OperacoesBase.Consultar("select nome_classe from classe");
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            while( rs.next() )
            {
                lista.add(rs.getString("nome_classe"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Classe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public static String NameAlunoToClasse( String nome )
    {
        ResultSet rs = OperacoesBase.Consultar("select nome_classe from view_matricula  where codaluno = '"+Estudante.NameToCode(nome)+"'");
        try {
            while( rs.next() )
            {
                return rs.getString("nome_classe");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Classe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
    
    public static String DisciplinaToClasse( int cod )
    {
       
        String sql = "select codclasse from disciplina where coddisciplina = '"+cod+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                return Classe.CodeToName(rs.getInt("codclasse"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Classe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
    
    public static String[] Meses_ClasseExame( String classe )
    {
        String meses_array[]= null;//Array principal
        String meses_normais[] = { "Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro"};//meses normais
        String meses_exames[] = { "Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro", "Outubro","Novembro","Dezembro"};//meses das classes de exame
        
        if( classe != null )
        {
            if( EClassedeExame(classe))
                meses_array = meses_exames;
            else
                meses_array = meses_normais;
            return meses_array;
        }
        return null;
    }
    
     /**
      * Verifica se uma classe e de xame ou nao.....As classes de exame sao 9 6 e 12
      * @param code
      * @return 
      */
    private static boolean EClassedeExame( String classe )
    {
        return "9ª classe".equalsIgnoreCase(classe) || "6ª classe".equalsIgnoreCase(classe) || "12ª classe".equalsIgnoreCase(classe);
    }//fim do metodo
  
}
