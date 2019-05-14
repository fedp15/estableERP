package Controlador;

import Modelos.Bitacora;
import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import util.CargaTablas;
import util.CodigoConsecutivo;
import util.Internacionalizacion;
import util.SQLQuerys;
import util.ValidaCodigo;

public class ControladorMoneda extends conexion{
    private ResultSet rs;
    private CallableStatement cs = null;
    private PreparedStatement ps = null;
    
    private String codigo = "", nombre = "", simbolo = "", valor = "", fecha = "", activo = "", orgCodEmp = "",
                   multiDiv = "", codIso = "";
    
    private final CargaTablas cargatabla = new CargaTablas();
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    
    public ControladorMoneda() {
    }
    
    public String codConsecutivo(){
        String consecutivo = "";
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        
        //consecutivo = codigo.CodigoConsecutivo("MON_CODIGO","DNMONEDA",6,"WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"'");
        consecutivo = codigo.CodigoConsecutivo("MON_CODIGO","DNMONEDA",6,"");
        if(consecutivo == null){
            consecutivo = "000001";
        }
        return consecutivo;
    }
    
    public void cargaTabla(JTable tabla){
        //String SQL = "SELECT MON_CODIGO,MON_NOMBRE,mon_nomenc FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        String SQL = "SELECT MON_CODIGO,MON_NOMBRE,mon_nomenc FROM DNMONEDA";
        String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("descrip"), "Simbolo"};
        
        cargatabla.cargatablas(tabla,SQL,columnas);  
    }
    
    public ResultSet ejecutaHilo(String codigo, Boolean lAgregar){
        try {
            String SQLCodTipMae="";
            //SQLCodTipMae = "SELECT MON_CODIGO FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY MON_CODIGO DESC LIMIT 1 ";
            SQLCodTipMae = "SELECT MON_CODIGO FROM DNMONEDA ORDER BY MON_CODIGO DESC LIMIT 1 ";
            
            rs = consultar(SQLCodTipMae);
            
            if(lAgregar==false){
                try {
                    codigo=rs.getString("MON_CODIGO").trim();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorMoneda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //String SQL = "SELECT * FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MON_CODIGO='"+codigo+"'";
            String SQL = "SELECT * FROM DNMONEDA WHERE MON_CODIGO='"+codigo+"'";
            rs = consultar(SQL);
            
            //String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNTIPOMAESTRO WHERE TMA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND TMA_CODIGO='"+codigo+"'";
            //Reg_count = conet.Count_Reg(SQLReg);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorMoneda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public ResultSet buscarMoneda(String codigo, Boolean lAgregar){
        try {
            //String SQL = "SELECT * FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MON_CODIGO='"+codigo+"'";
            String SQL = "SELECT * FROM DNMONEDA WHERE MON_CODIGO='"+codigo+"'";
            rs = consultar(SQL);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorMoneda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public void save(String codigo, String descri, String nomen, String valor, String fecha, 
                     String activo, Boolean lAgregar, String multiDiv, String codIso, String orgCodEmp){
        this.codigo   = codigo;
        this.nombre   = descri;
        this.simbolo  = nomen;
        
        valor = valor.replace(".", "");
        valor = valor.replace(",", ".");
        this.valor    = valor;
        
        this.fecha    = fecha;
        this.multiDiv = multiDiv;
        this.codIso   = codIso;
        this.activo   = activo;
        
        SQLQuerys update = new SQLQuerys();
        
        try {
            if (orgCodEmp.isEmpty()){
                this.orgCodEmp=VarGlobales.getCodEmpresa();
            }else{
                this.orgCodEmp=orgCodEmp;
            }
            
            if(lAgregar){
                for (int i=0; i<2; i++){
                    if (i==1){
                        VarGlobales.setlBaseDatosConfiguracion(true);
                    }else{
                        VarGlobales.setlBaseDatosConfiguracion(false);
                    }
                    
                    creaConexion();
                    String sql = "{call sp_formMoneda(?,?,?,?,?,?,?,?,?,?,?,?)}";
                
                    callStoreProcedure(sql, "INSERT");
                
                    String sqlupdate = "UPDATE dnmoneda SET mon_nomenc='"+simbolo+"'\n" +
                                       "WHERE MON_EMPRESA='"+this.orgCodEmp+"' AND MON_CODIGO='"+codigo+"';";
                
                    update.SqlUpdate(sqlupdate);
                }
                
                VarGlobales.setlBaseDatosConfiguracion(false);
                Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(),
                        VarGlobales.getIdUser(), VarGlobales.getUserName(),
                        "Inserción de Nuevo Registro", "Se creo la Moneda ''"+codigo+"'' Correctamente");
            }else{
                for (int i=0; i<2; i++){
                    if (i==1){
                        VarGlobales.setlBaseDatosConfiguracion(true);
                    }else{
                        VarGlobales.setlBaseDatosConfiguracion(false);
                    }
                    
                    creaConexion();
                    String sql = "{call sp_formMoneda(?,?,?,?,?,?,?,?,?,?,?,?)}";
                
                    callStoreProcedure(sql, "UPDATE");
            
                    String sqlupdate = "UPDATE dnmoneda SET mon_nomenc='"+simbolo+"'\n" +
                                       "WHERE MON_EMPRESA='"+this.orgCodEmp+"' AND MON_CODIGO='"+codigo+"';";
                
                    update.SqlUpdate(sqlupdate);
                }
                
                VarGlobales.setlBaseDatosConfiguracion(false);
                Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Modificación de Registro", "Se Modifico la moneda ''"+codigo+"'' Correctamente");
            }
        } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ControladorMoneda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(String codigo, JTable tabla){
        int eliminado;
    
        SQLQuerys eliminar = new SQLQuerys();
        eliminar.SqlDelete("DELETE FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MON_CODIGO='"+codigo+"'","Moneda","Moneda");

        ValidaCodigo consulta = new ValidaCodigo();
        eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNMONEDA WHERE MON_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MON_CODIGO='"+codigo+"'",true,"");
    }
    
    private void callStoreProcedure(String sql, String op){
        try {
            cs = conn.prepareCall(sql);
            
            if(op.equals("SELECT")){
                cs.setString(1, op);
                cs.setString(2, codigo);
                cs.setString(3, "");
                cs.setString(4, "");
                cs.setString(5, "");
                cs.setString(6, "");
                cs.setString(7, "");
                cs.setString(8, "");
                cs.setString(9, "");
                cs.setString(10, "");
                cs.setString(11, "");
                cs.setString(12, "0");
                
                rs = consultarStoreProcedure(cs);
            }
            
            if(op.equals("INSERT") || op.equals("UPDATE")){
                BigDecimal valorMoneda = new BigDecimal(valor);
                DecimalFormat df = new DecimalFormat("#,###.00");
                df.setParseBigDecimal(true);
                System.err.println(""+df.format(valorMoneda.doubleValue())+" - "+valorMoneda);
                
                cs.setString(1, op);
                cs.setString(2, orgCodEmp);
                cs.setString(3, VarGlobales.getIdUser());
                cs.setString(4, VarGlobales.getMacPc());
                cs.setString(5, codigo);
                cs.setString(6, nombre);
                cs.setString(7, "");
                cs.setString(8, ""+valorMoneda);
                cs.setString(9, multiDiv);
                cs.setString(10, codIso);
                cs.setString(11, fecha);
                cs.setString(12, activo);
                
                insertDeleteUpdate_StoreProcedure(cs);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ControladorMoneda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}