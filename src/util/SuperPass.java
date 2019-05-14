package util;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SuperPass {
    private ResultSet rs;
    private Boolean lExist = false;
    
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    conexion conet = new conexion();
    
    public Boolean Validar(String clave){
        String user = "1";
        int pos = clave.indexOf("-");
        user = clave.substring(0, pos);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date hoy = new Date();
        Date desde,hasta;
        
        String sql="SELECT * FROM dnsuperclave WHERE SUP_CLAVE='"+clave+"' AND SUP_USER='"+user+"' AND "+
                   "SUP_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        System.out.println(sql);
        try {
            rs = conet.consultar(sql);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SuperPass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            if(rs.getRow()>0){
                if(rs.getInt("SUP_EXPIRA")==1){
                    try {
                        desde = sdf.parse(rs.getString("SUP_FCHDES"));
                        hasta = sdf.parse(rs.getString("SUP_FCHHAS"));
                        if(hoy.after(desde) && hoy.before(hasta)){
                            lExist = true;
                        }else{
                            lExist = false;
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(SuperPass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }else{ 
                    lExist = true;
                }
            }
        } catch (SQLException ex) {        
            Logger.getLogger(SuperPass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lExist;
    }
}