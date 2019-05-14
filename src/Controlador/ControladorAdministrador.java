package Controlador;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import Vista.Login;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorAdministrador extends conexion{
    private ResultSet rsCount, rsCategorias, rsProductos;
    private int numCateg = 0, numProd = 0, idCateg = 0;
    
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    
    public ControladorAdministrador() {
    
    }
    
    public int cantidaOpcAdmin(){
        try {
            String sql = "SELECT COUNT(*) AS OPC_ADMIN FROM dnpermisologia " +
                         "INNER JOIN dnmenu ON MEN_ID=MIS_MENU " +
                         "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                         "MIS_PERMISO="+VarGlobales.getGrupPermiso()+" AND MIS_POS=1 AND MIS_ACTIVO=1";
            
            rsCount = consultar(sql);
            numProd = rsCount.getInt("OPC_ADMIN");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ControladorAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return numProd;
    }
    
    public ResultSet opcAdministrador(){
        try {
            String sql = "SELECT MEN_NOMBRE,MEN_ID,MIS_POS,MIS_ACTIVO,MEN_ICON,MEN_URL FROM dnpermisologia " +
                         "INNER JOIN dnmenu ON MEN_ID=MIS_MENU " +
                         "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                         "MIS_PERMISO="+VarGlobales.getGrupPermiso()+" AND MIS_POS=1 AND MIS_ACTIVO=1 "+
                         "AND MEN_ID<>18 ORDER BY MEN_ORDEN_POS";
            
            rsCategorias = consultar(sql);
            rsCategorias.first();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ControladorAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rsCategorias;
    }
    
    public int validaApertura(String diaSis){
        ResultSet rs = null;
        int hora, minutos, dia, result=0;
        String horaSis, jornada="", turno="", sql="", pcName="", caja="";
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar horario = new GregorianCalendar();
        
        hora = horario.get(Calendar.HOUR_OF_DAY);
        minutos = horario.get(Calendar.MINUTE);
        dia = horario.get(Calendar.DAY_OF_WEEK);
        horaSis = String.valueOf(hora)+":"+String.valueOf(minutos);
        
        Date h = null;
        Date fch = new Date();
        String fecha = sdf.format(fch);
        
        InetAddress direccion = null;
        try {
            direccion = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        pcName = direccion.getHostName();
        
        try {
            h = formato.parse(horaSis);
        } catch (ParseException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        sql = "SELECT CAJ_NUMCAJ FROM dncaja WHERE CAJ_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
              "CAJ_PCNAME='"+pcName+"' AND CAJ_MACPC='"+VarGlobales.getMacPc()+"'";
        System.out.println(sql);
        try {
            rs = consultar(sql);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if(rs.getRow()>0){
                caja = rs.getString("CAJ_NUMCAJ");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        sql = "SELECT JOR_CODIGO,JOR_TURDESCRI,JOR_TURINI,JOR_TURFIN FROM dnjornada WHERE "+
              "JOR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND JOR_"+diaSis.toUpperCase()+"=1 ORDER BY JOR_TURDESCRI";
        System.out.println(sql);
        try {
            rs = consultar(sql);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if(rs.getRow()>0){
                rs.beforeFirst();
                while(rs.next()){
                    if(!rs.getString("JOR_TURINI").equals("") && !rs.getString("JOR_TURFIN").equals("")){
                        try {
                            if(h.after(formato.parse(rs.getString("JOR_TURINI"))) && h.before(formato.parse(rs.getString("JOR_TURFIN")))){
                                jornada = rs.getString("JOR_CODIGO");
                                turno = rs.getString("JOR_TURDESCRI");
                                
                                break;
                            }
                        } catch (ParseException ex) {
                            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!jornada.equals("") && !turno.equals("")){
            sql = "SELECT * FROM dnaperturacaja WHERE APE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                  "APE_CAJA='"+caja+"' AND APE_JORNADA='"+jornada+"' AND APE_TURNO='"+turno+"' AND "+
                  "APE_FECHA='"+fecha+"'";
            System.out.println(sql);
            try {
                rs = consultar(sql);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if(rs.getRow()>0){
                    result = 1;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
}