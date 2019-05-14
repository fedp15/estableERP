package Controlador;

import Modelos.Bitacora;
import util.CargaTablas;
import util.SQLQuerys;
import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import util.CodigoConsecutivo;
import util.Internacionalizacion;

public class ControladorCargos extends conexion{
    Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    CargaTablas cargatabla = new CargaTablas();
    
    private ResultSet rs_codigo, rs_tree, rsId;

    public ControladorCargos() {
        idioma.setLocale(VarGlobales.getIdioma());
    }

    public void cargaTabla(JTable tablaCar,JTabbedPane jTabbedPane){
        String SQL = "";
        
        if (jTabbedPane.getSelectedIndex()==0){
            SQL = "SELECT CAR_CODIGO,CAR_DESCRI FROM dncargos WHERE CAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
            
            String[] columnas = {idioma.loadLangHeaderTable().getString("id"),idioma.loadLangHeaderTable().getString("nombre")};
        
            cargatabla.cargatablas(tablaCar,SQL,columnas);
            tablaCar.getSelectionModel().setSelectionInterval(tablaCar.getRowCount()-1, tablaCar.getRowCount()-1);
        }
    }

    public String codConsecutivo(){
        String consecutivo = null;
                
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        consecutivo = codigo.CodigoConsecutivoCargos("CAR_CODIGO","dncargos",0,"");
        
        return consecutivo;
    }
    
    public ResultSet ejecutaHilo(String codigo, Boolean lAgregar, JTabbedPane jTabbedPane){
        try {
            String SQLCodDoc="", SQL="";
            
            if (jTabbedPane.getSelectedIndex()==0){
                SQLCodDoc = "SELECT CAR_CODIGO FROM dncargos WHERE CAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
                            "ORDER BY CAR_ID DESC LIMIT 1 ";
            }

            rs_codigo = consultar(SQLCodDoc);
            try {
                if(lAgregar==false){
                    codigo=rs_codigo.getString("CAR_CODIGO").trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorCargos.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (jTabbedPane.getSelectedIndex()==0){
                SQL = "SELECT * FROM dncargos WHERE CAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND CAR_CODIGO='"+codigo+"'";
            }
System.err.println("SQLCodDoc: "+SQL);
            try {
                rs = consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorCargos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorCargos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void grabar(String codigo, String nombre, JTable tabla, Boolean lAgregar, JTabbedPane jTabbedPane){
        String sqlInsert = "", sqlUpdate = "", tipo = "", ImagCat="";
                
        if (lAgregar==true){
            SQLQuerys insertar = new SQLQuerys();
            if (jTabbedPane.getSelectedIndex()==0){
                sqlInsert = "INSERT INTO dncargos (CAR_EMPRESA,CAR_CODIGO,CAR_DESCRI) "+
                                                "VALUES ('"+VarGlobales.getCodEmpresa()+"',"+
                                                          codigo+",'"+nombre+"');";
                System.out.println("Insertando Registro de Cargos"+sqlInsert);
            }

            insertar.SqlInsert(sqlInsert);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Registro", "Se creo el Cargo ''"+nombre.toUpperCase()+"'' Correctamente");    
            
            
        }else if (lAgregar==false){
            SQLQuerys modificar = new SQLQuerys();
            
            if (jTabbedPane.getSelectedIndex()==0){
                sqlUpdate = "UPDATE dncargos SET CAR_DESCRI='"+nombre+"' "+
                            "WHERE CAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND CAR_CODIGO='"+codigo+"'";
            }
            
            modificar.SqlUpdate(sqlUpdate);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Modificación de Registro", "Se Modifico el Cargo ''"+nombre.toUpperCase()+"'' Correctamente");
        }
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        cargaTabla(tabla, jTabbedPane);
        //-------------------------------------------------------------------
    }

    public void delete(String codigo, String nombre, JTable tabla, JTabbedPane jTabbedPane){
        int eliminado;
        String msj = "";
        
        int salir = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgEliminarCode")+" "+codigo+" ("+nombre+")?");
        
        if (jTabbedPane.getSelectedIndex()==0){
            msj = idioma.loadLangComponent().getString("lbTituloFormCargos");
        }   
        
        if ( salir == 0) {
            SQLQuerys eliminar = new SQLQuerys();
            eliminar.SqlDelete("DELETE FROM dncargos WHERE CAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND CAR_CODIGO='"+codigo+"'",
                               msj, idioma.loadLangComponent().getString("lbTituloFormProductos"));
            
            ejecutaHilo("", false, jTabbedPane);
            cargaTabla(tabla, jTabbedPane);
        }
    }
    
    public ResultSet menuTree(){
        try {
            String SQL = "SELECT CAR_CODIGO,CAR_DESCRI FROM dncargos WHERE CAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
            
            rs_tree = consultar(SQL);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorCargos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs_tree;
    }
    
    public ResultSet subMenuTree(String codigo){
        try {
            String SQL = "SELECT CAR_CODIGO,CAR_DESCRI FROM dncargos WHERE CAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
            
            rs_tree = consultar(SQL);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorCargos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs_tree;
    }
    
    public ResultSet idCargos(String catPad, String subCat){
        String sqlId = "";
        try {
            String sql = "SELECT @Id:=CAR_CODIGO AS CAR_CODIGO,CAR_DESCRI FROM dncargos "+
                         "WHERE CAR_DESCRI='"+catPad+"'";

            rsId = consultar(sql);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorCargos.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return rsId;
    }
}