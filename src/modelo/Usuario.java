package modelo;

import Bd.OperacoesBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Usuario {

    //variaveis de instancia
    private int codigo;//codigo do registro
    private int coduser;
    private String nome;
    private String tipo;
    private LocalDate datanascimento;
    private String foto;
    private String senha;
    private String username;
    private String sexo;
    private String acao; //registroUser
    private String hora;//registroUser
    private LocalDate data;//registroUser
    private String inicio;
    private String fim;
    private String dataString;
    private int codfuncionario;
    
    private static String usuario_activo;

    public Usuario() {
    }

    

    public int getCodigo() {
        return codigo;
    }

    public String getAcao() {
        return acao;
    }

    public int getCodfuncionario() {
        return codfuncionario;
    }

    public void setCodfuncionario(int codfuncionario) {
        this.codfuncionario = codfuncionario;
    }
    
    

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    
    public LocalDate getDatanascimento() {
        return datanascimento;
    }

    public int getCoduser() {
        return coduser;
    }

    
    public String getSexo() {
        return sexo;
    }

    public LocalDate getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }

    public String getFoto() {
        return foto;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUsername() {
        return username;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setCoduser(int coduser) {
        this.coduser = coduser;
    }

    public static void setUsuario_activo(String usuario_activo) {
        Usuario.usuario_activo = usuario_activo;
    }

  
   
    public void setDatanascimento(LocalDate datanascimento) {
        this.datanascimento = datanascimento;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }
    
    

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static String getUsuario_activo() {
        return usuario_activo;
    }
    
    
    
    
    /**
     * Operacoes auxiliares da classe
     * 
     * @return 
     */
    
    
    //Adiciona usuarios na base de dados
    public boolean Adicionar()
    {
        String sql = "Insert into usuario values('"+this.getCodigo()+"' , '"+this.getTipo()+"','"+this.getUsername()+"' , '"+this.getSenha()+"' , '"+this.getFoto()+"','"+this.getCodfuncionario()+"')";
        return OperacoesBase.Inserir(sql);
    }
    
    
    public ResultSet Consultar( String sql )
    {
        return OperacoesBase.Consultar(sql);
    }
    
    public boolean Eliminar()
    {
        String sql = "delete from registroUser where codigo = '"+this.getCodigo()+"'";
        return OperacoesBase.Eliminar(sql);
    }
    
/*********************************Metodos Estaticos*******************************/
       public static int NameToCode( String nome )
    {
    
        ResultSet rs = OperacoesBase.Consultar("select coduser from usuario where username = '"+nome+"'");
        int valor = 0;
        try {
            while( rs.next() )
            {
                valor = rs.getInt("coduser");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    
        
    }
       
    public static String CodeToName( int code )
    {
    
        ResultSet rs = OperacoesBase.Consultar("select nome from usuario inner join funcionario using(codfuncionario) where coduser = '"+code+"'");
        String nome  = "";
        try {
            while( rs.next() )
            {
                nome = rs.getString("nome");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nome;
    
        
     }
       
       
    public static String CodeUserToUsername( int code )
    {
    
        ResultSet rs = OperacoesBase.Consultar("select username from usuario where coduser = '"+code+"'");
        String nome  = "";
        try {
            while( rs.next() )
            {
                nome = rs.getString("username");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nome;
    
        
     }
    
    
    public static int RetornaUltimoCodigoUser()
    {
        
        ResultSet Consultar = OperacoesBase.Consultar( "select coduser from usuario order by coduser");
        int valor = 0;
        try
        {
            while( Consultar.next())
            {
                valor =  Consultar.getInt("coduser");
            }
            
        }catch( SQLException e)
        {
            e.printStackTrace();
        }
        
        //configurando ocodigo do usuario vindo da base de dados
        return ++valor;
    }
    
    public static int Obter_CodFuncionario( int coduser )
    {
        
        ResultSet Consultar = OperacoesBase.Consultar( "select codfuncionario from usuario where coduser = '"+coduser+"'");
        int valor = 0;
        try
        {
            while( Consultar.next())
            {
                valor =  Consultar.getInt("codfuncionario");
            }
            
        }catch( SQLException e)
        {
            e.printStackTrace();
        }
        
        //configurando ocodigo do usuario vindo da base de dados
        return valor;
    }
    
    public static int Funcionario_User_Activo()
    {
        return Obter_CodFuncionario(Usuario.NameToCode(getUsuario_activo()));
    }
    
    public static String tipo_Usuario_Activo()
    {
        return CodeToTipo(Usuario.NameToCode(Usuario.getUsuario_activo()));
    }
    
    
    public static boolean verificarExistencia( String nome , String username , String tipo , String senha )
    {
         ResultSet rs = OperacoesBase.Consultar("select coduser from usuario where nome = '"+nome+"' and username = '"+username+"' and tipo = '"+tipo+"' and senha = '"+senha+"'");
         int coduser  = 0;
         try {
            while( rs.next() )
            {
                coduser = rs.getInt("coduser");
            }
         } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
         }
         return coduser != 0;
    }
    
    public static  boolean VerificaSenha_Admin( String senha )
    {
         String tipo = "Admin";
         ResultSet rs = OperacoesBase.Consultar("select coduser from usuario where tipo = '"+tipo+"' and senha = '"+senha+"'");
         int codigo  = 0;
         try {
            while( rs.next() )
            {
                codigo = rs.getInt("coduser");
            }
         } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
         }
         return codigo != 0;
        
    }
    
    public static ObservableList<String> listaNomesUsers()
    {
        
         ResultSet rs = OperacoesBase.Consultar("select username from usuario");
         ObservableList<String> lista = FXCollections.observableArrayList();
         try {
            while( rs.next() )
            {
                lista.add(rs.getString("username"));
            }
         } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
         }
         return lista;
    }
    
    public static ObservableList<Usuario> listaUsers()
    {
        
         ResultSet rs = OperacoesBase.Consultar("select * from usuario");
         ObservableList<Usuario> lista = FXCollections.observableArrayList();
         try {
            while( rs.next() )
            {
                Usuario e = new Usuario();
                e.setNome(rs.getString("nome"));
                e.setCodigo(rs.getInt("coduser"));
                e.setSenha(rs.getString("senha"));
                
                lista.add(e);
            }
         } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
         }
         return lista;
    }
    
    public static String CodeToFotografia( int code  )
    {
        ResultSet rs = OperacoesBase.Consultar("select foto from usuario where coduser = '"+code+"'");
        String fotografia  = "";
        try {
            while( rs.next() )
            {
                fotografia = rs.getString("foto");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fotografia;
    }
    
    public static String CodeToTipo( int code  )
    {
        ResultSet rs = OperacoesBase.Consultar("select tipo from usuario where coduser = '"+code+"'");
        String tipo  = "";
        try {
            while( rs.next() )
            {
                tipo = rs.getString("tipo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tipo;
    }
    
    public static Usuario Obter_Usuario_por_codigo( int valor )
    {
        
         ResultSet rs = OperacoesBase.Consultar("select * from usuario where coduser= '"+valor+"'");
         try {
            while( rs.next() )
            {
                Usuario e = new Usuario();
                e.setNome(rs.getString("nome"));
                e.setCodigo(rs.getInt("coduser"));
                e.setUsername(rs.getString("username"));
                
              return e;
            }
         } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }
    
    public static boolean EAdmin( int code )
    {
         String tipo = "Admin";
         ResultSet rs = OperacoesBase.Consultar("select tipo from usuario where coduser = '"+code+"'");
         int codigo  = 0;
         try {
            while( rs.next() )
            {
               tipo = rs.getString("tipo");
            }
         } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
         }
         return tipo.equals("Admin");
        
    }
    
    public static int Retorna_Privilegio_leitura()
    {
        int coduser = Usuario.NameToCode(Usuario.getUsuario_activo());
        String sql = "select leitura privilegios_usuario where coduser= '"+coduser+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
         int codigo  = 0;
         try {
            while( rs.next() )
            {
               codigo = rs.getInt("leitura");
            }
         } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
         }
         return codigo;
        
    }
    
    public static int Retorna_Privilegio_Impressao()
    {
        int coduser = Usuario.NameToCode(Usuario.getUsuario_activo());
        String sql = "select impressao privilegios_usuario where coduser= '"+coduser+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
         int codigo  = 0;
         try {
            while( rs.next() )
            {
               codigo = rs.getInt("impressao");
            }
         } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
         }
         return codigo;
        
    }
    public static int Retorna_Privilegio_Eliminacao(  )
    {
        int coduser = Usuario.NameToCode(Usuario.getUsuario_activo());
        String sql = "select eliminacao from privilegios_usuario where coduser= '"+coduser+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
         int codigo  = 0;
         try {
            while( rs.next() )
            {
               codigo = rs.getInt("eliminacao");
            }
         } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
         }
         return codigo;
        
    }
    public static int Retorna_Privilegio_Actualizacao( )
    {
        int coduser = Usuario.NameToCode(Usuario.getUsuario_activo());
        String sql = "select actualizacao from privilegios_usuario where coduser= '"+coduser+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
         int codigo  = 0;
         try {
            while( rs.next() )
            {
               codigo = rs.getInt("actualizacao");
            }
         } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
         }
         return codigo;
        
    }
    public static int Retorna_Privilegio_Insercao()
    {
        int coduser = Usuario.NameToCode(Usuario.getUsuario_activo());
        String sql = "select insercao from privilegios_usuario where coduser= '"+coduser+"'";
        ResultSet rs = OperacoesBase.Consultar(sql);
         int codigo  = 0;
         try {
            while( rs.next() )
            {
               codigo = rs.getInt("insercao");
            }
         } catch (SQLException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
         }
         return codigo;
        
    }
 
    
}
