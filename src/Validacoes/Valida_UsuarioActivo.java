/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validacoes;

import Controlador.Home.HomeEmissaoController;
import Controlador.Home.HomePagamento_geralController;
import Controlador.Home.PaginaHomeController;
import Controlador.Menus.EstudanteController;
import Controlador.Menus.MatriculaController;
import Controlador.Notas.NotasController;
import Controlador.Usuario.DefinicoesController;
import modelo.matricula_confirmacao;

/**
 *
 * @author Familia Neto
 */
public class Valida_UsuarioActivo {
    
    
    public static void Pedagogico_Home( boolean valor )
    {
        PaginaHomeController.getP_boletim().setDisable(valor);
        PaginaHomeController.getP_encarregado().setDisable(valor);
        PaginaHomeController.getP_faltas().setDisable(valor);
        PaginaHomeController.getP_pauta().setDisable(valor);
      
    }
    
    public static void Pedagogico_Notas( boolean valor )
    {
        NotasController.getL_consultar().setDisable(valor);
        NotasController.getL_lancar().setDisable(valor);
        NotasController.getL_relatorios().setDisable(valor);
    }
    
    public static void Pedagogico_Estudande( boolean valor )
    {
        EstudanteController.getL_adicionar().setDisable(valor);
        EstudanteController.getL_visualiza().setDisable(valor);
    }
    
    
    public static void Pedagogico_Matricula( boolean valor  )
    {
        MatriculaController.getL_confirmar().setDisable(valor);
        MatriculaController.getL_registro().setDisable(valor);
        MatriculaController.getL_visualizar().setDisable(valor);
    }
    
    public static void Fincanceiro( boolean valor  )
    {
        HomePagamento_geralController.getP_d().setDisable(valor);
        HomePagamento_geralController.getP_e().setDisable(valor);
        HomePagamento_geralController.getP_f().setDisable(valor);
        HomePagamento_geralController.getP_r().setDisable(valor);
    }
   
    
    public static void Definicoes( boolean valor )
    {
        DefinicoesController.getL_backup().setDisable(valor);
        DefinicoesController.getL_config().setDisable(valor);
        DefinicoesController.getL_registro().setDisable(valor);
        DefinicoesController.getL_usuario().setDisable(valor);
    }
    
    
    public static void Emissao( boolean valor )
    {
        HomeEmissaoController.getPc().setDisable(valor);
        HomeEmissaoController.getPd().setDisable(valor);
        HomeEmissaoController.getPe().setDisable(valor); 
        HomeEmissaoController.getPh().setDisable(valor);
    }
    
    
    
}
