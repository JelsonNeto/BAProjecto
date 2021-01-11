/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import Bd.OperacoesBase;
import definicoes.DefinicoesData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Familia Neto
 */
public class FaltaFuncionario {

    private int estado_falta;
    private int codigo;
    private int codfuncionario;
    private int codmes;
    private String mes;
    private String bi_cedula;
    private LocalDate data;
    private String trimestre;
    private String ano_lectivo;
    private String nome_funcionario;
    private String funcao;
    private String estado;

    public FaltaFuncionario() {
    }

    public FaltaFuncionario(int codigo, int codfuncionario, LocalDate data, String trimestre, String ano_lectivo, int codmes, int estado) {
        this.codigo = codigo;
        this.codfuncionario = codfuncionario;
        this.data = data;
        this.trimestre = trimestre;
        this.ano_lectivo = ano_lectivo;
        this.codmes = codmes;
        estado_falta = estado;
    }

    public int getEstado_falta() {
        return estado_falta;
    }

    public void setEstado_falta(int estado_falta) {
        this.estado_falta = estado_falta;
    }

    
    public int getCodigo() {
        return codigo;
    }

    public String getBi_cedula() {
        return bi_cedula;
    }

    public void setBi_cedula(String bi_cedula) {
        this.bi_cedula = bi_cedula;
    }

    public int getCodmes() {
        return codmes;
    }

    public String getMes() {
        return mes;
    }

    public void setCodmes(int codmes) {
        this.codmes = codmes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
    
    

    public String getFuncao() {
        return funcao;
    }

    public String getNome_funcionario() {
        return nome_funcionario;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public void setNome_funcionario(String nome_funcionario) {
        this.nome_funcionario = nome_funcionario;
    }

    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodfuncionario() {
        return codfuncionario;
    }

    public void setCodfuncionario(int codfuncionario) {
        this.codfuncionario = codfuncionario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTrimestre() {
        return trimestre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    

    public void setTrimestre(String trimestre) {
        this.trimestre = trimestre;
    }

    public String getAno_lectivo() {
        return ano_lectivo;
    }

    public void setAno_lectivo(String ano_lectivo) {
        this.ano_lectivo = ano_lectivo;
    }
    
    
    public boolean Adicionar()
    {
        String sql = "insert into falta_funcionario values('"+this.getCodigo()+"','"+this.getCodfuncionario()+"','"+this.getData()+"','"+this.getAno_lectivo()+"','"+this.getTrimestre()+"','"+this.getCodmes()+"','"+this.getEstado_falta()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
    public boolean Eliminacao_Filtrada_codigo()
    {
        String sql = "delete from falta_funcionario where codigo ='"+this.getCodigo()+"'";
        return OperacoesBase.Eliminar(sql);
    }
    
    public boolean Justicar_Falta()
    {
        String sql = "update falta_funcionario set estado_falta = 1 where codigo ='"+this.getCodigo()+"' and codfuncionario = '"+this.getCodfuncionario()+"' and data_falta like '"+MesAno.Get_AnoActualCobranca()+"-%-%'" ;
        return OperacoesBase.Actualizar(sql);
    }
    
    
/******************METODOS OPERACICONAIS************************************************************/
    public static int UltimoCodigo()
    {
        String sql= "select codigo from falta_funcionario";
        ResultSet rs = OperacoesBase.Consultar(sql);
        int codigo = 0;
        try {
            while( rs.next() )
            {
                codigo = rs.getInt("codigo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FaltaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ++codigo;
    }
    
    public static ObservableList<FaltaFuncionario> ListaFaltasFaltaFuncionarios_Tabela()
    {
        ObservableList<FaltaFuncionario> lista = FXCollections.observableArrayList();
        String sql = "select * from falta_funcionario inner join funcionario using(codfuncionario)";
        ResultSet rs= OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                FaltaFuncionario f = new FaltaFuncionario();
                f.setCodigo(rs.getInt("codigo"));
                f.setAno_lectivo(rs.getString("ano_lectivo"));
                f.setCodfuncionario(rs.getInt("codfuncionario"));
                f.setTrimestre(rs.getString("trimestre"));
                f.setData(DefinicoesData.StringtoLocalDate(rs.getString("data_falta")));
                f.setNome_funcionario(rs.getString("nome"));
                f.setFuncao(rs.getString("funcao"));
                f.setBi_cedula(rs.getString("bi_cedula"));
                f.setEstado(RetornaEstado(rs.getInt("estado_falta")));
                f.setEstado_falta(rs.getInt("estado_falta"));
                lista.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FaltaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public static boolean Verificar_Falta_Existe( int codfuncionario , LocalDate data )
    {
        int contador= 0;
        String sql = "select * from falta_funcionario where codfuncionario = '"+codfuncionario+"' and data_falta = '"+data+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                contador++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FaltaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return contador >0;
    }
    
    public static String Ultima_Data_Registrada()
    {
        String data_falta = "0000-00-00";
        String sql = "select data_falta from falta_funcionario";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
                data_falta = rs.getString("data_falta");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FaltaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data_falta;
    }
    
     public static int Quantidade_Falta_mes_Anolectivo( String padrao , String anolectivo )
    {
        int quantidade = 0;
        String sql ="select count(codigo) as quantidade from falta_funcionario where data_falta like '"+padrao+"' and ano_lectivo = '"+anolectivo+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while (rs.next()) {
                quantidade = rs.getInt("quantidade");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FaltaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return quantidade;
    }
     
     public static int Quantidade_Falta_Anolectivo_por_funcionario( int codfuncionario , String anolectivo )
       {
        int quantidade = 0;
        String sql ="select count(codigo) as quantidade from falta_funcionario where codfuncionario =  '"+codfuncionario+"' and ano_lectivo = '"+anolectivo+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while (rs.next()) {
                quantidade = rs.getInt("quantidade");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FaltaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return quantidade;
      }
     
     public static int Quantidade_Falta_mes_Anolectivo_por_funcionario( int codfuncionario , String padrao , String anolectivo )
    {
        int quantidade = 0;
        String sql ="select count(codigo) as quantidade from falta_funcionario where codfuncionario = '"+codfuncionario+"' and data_falta like '"+padrao+"' and ano_lectivo = '"+anolectivo+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while (rs.next()) {
                quantidade = rs.getInt("quantidade");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FaltaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return quantidade;
    }
     
   private static String RetornaEstado( int valor )
   {
       String estado_var = "Não justificada";
       if( valor == 0 )
           return estado_var;
       if( valor == 1 )
           estado_var = "Justificada";
       else
           estado_var = "Confirmada";
       return estado_var;
       
   }
   
   private static boolean Actualiza_Estado( FaltaFuncionario f , int valor )
   {
       String sql = "update falta_funcionario set estado_falta = '"+valor+"' where codigo = '"+f.getCodigo()+"' and codfuncionario = '"+f.getCodfuncionario()+"'";
       return OperacoesBase.Actualizar(sql);
   }
    
   public static void Confirmar_Faltas( FaltaFuncionario f )
   {
          if( f.getEstado_falta() == 0 )
               Actualiza_Estado(f, 2);
       
   }
   
   public static ObservableList<FaltaFuncionario> Lista_NaoJustificadas()
   {
       String sql = "select * from falta_funcionario inner join funcionario using(codfuncionario) where estado_falta = 0 and data_falta like '"+MesAno.Get_AnoActualCobranca()+"-%-%'";
       ObservableList<FaltaFuncionario> lista = FXCollections.observableArrayList();
       ResultSet rs = OperacoesBase.Consultar(sql);
        try {
            while( rs.next() )
            {
               FaltaFuncionario f = new FaltaFuncionario();
                f.setCodigo(rs.getInt("codigo"));
                f.setAno_lectivo(rs.getString("ano_lectivo"));
                f.setCodfuncionario(rs.getInt("codfuncionario"));
                f.setTrimestre(rs.getString("trimestre"));
                f.setData(DefinicoesData.StringtoLocalDate(rs.getString("data_falta")));
                f.setNome_funcionario(rs.getString("nome"));
                f.setFuncao(rs.getString("funcao"));
                f.setBi_cedula(rs.getString("bi_cedula"));
                f.setEstado(RetornaEstado(rs.getInt("estado_falta")));
                f.setEstado_falta(rs.getInt("estado_falta"));
                lista.add(f);
                
                
            }} catch (SQLException ex) {
            Logger.getLogger(FaltaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
   }
    
    
}
