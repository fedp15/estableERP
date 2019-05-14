/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Internacionalizacion;
import util.SQLQuerys;

/**
 *
 * @author Andres
 */
public class ControladorCambiarClave extends conexion {
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    
    public ControladorCambiarClave(){
        
    }
    
    public ResultSet buscarClave(String coduser){
        ResultSet rs = null;
        String user = "1";
        String sql = "SELECT SUP_USER FROM dnsuperclave WHERE SUP_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                     "ORDER BY SUP_USER DESC LIMIT 1";
        try {
            rs = consultar(sql);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorCambiarClave.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if(coduser.equals("")){
                if(rs.getRow()>0){
                    user = rs.getString("SUP_USER");
                }
            }else{
                user = coduser;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCambiarClave.class.getName()).log(Level.SEVERE, null, ex);
        }
        sql = "SELECT * FROM dnsuperclave WHERE SUP_USER='"+user+"' AND "+
              "SUP_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        System.out.println(sql);
        try {
            rs = consultar(sql);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorCambiarClave.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void save(String clave,String user,String desde,String hasta,int expira,Boolean lAgregar){
        String sql = "";
        
        if(lAgregar){
            sql = "INSERT INTO dnsuperclave (SUP_EMPRESA,SUP_MACPC,SUP_USER,SUP_CLAVE,"+
                  "SUP_FCHDES,SUP_FCHHAS,SUP_EXPIRA,SUP_ACTIVO)VALUES("+
                  "'"+VarGlobales.getCodEmpresa()+"','"+VarGlobales.getMacPc()+"','"+
                  user+"','"+clave+"','"+desde+"','"+hasta+"',"+expira+",1)";
            System.out.println(sql);
            
            SQLQuerys insert = new SQLQuerys();
            insert.SqlInsert(sql);
        }else{
            sql = "UPDATE dnsuperclave SET SUP_CLAVE='"+clave+"',SUP_FCHDES='"+desde+"',"+
                  "SUP_FCHHAS='"+hasta+"',SUP_EXPIRA="+expira+" "+
                  "WHERE SUP_USER='"+user+"' AND "+
                  "SUP_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
            System.out.println(sql);
            
            SQLQuerys update = new SQLQuerys();
            update.SqlUpdate(sql);
        }
    }
    
    public Boolean validaClave(String clave,String user){
        String sql = "SELECT SUP_CLAVE FROM dnsuperclave WHERE SUP_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                     "SUP_USER='"+user+"' AND SUP_CLAVE='"+clave+"'";
        try {
            rs = consultar(sql);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorCambiarClave.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if(rs.getRow()>0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCambiarClave.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public String validaUsu(){
        String codigo = "";
        try {
            String sql = "SELECT OPE_NUMERO FROM dnusuarios WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                         "ORDER BY OPE_NUMERO DESC LIMIT 1";
            
            ResultSet rs = consultar(sql);
            
            try {
                if(rs.getRow()>0){
                    codigo = rs.getString("OPE_NUMERO");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorCambiarClave.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorCambiarClave.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codigo;
    }
}
