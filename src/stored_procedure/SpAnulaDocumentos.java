package stored_procedure;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpAnulaDocumentos extends conexion{
    private static SpAnulaDocumentos spAnulaDocumentos;
    private CallableStatement cs = null;
    private ResultSet rsConfEmpresa;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();

    private SpAnulaDocumentos() {
    }
    
    public static SpAnulaDocumentos getSpAnulaDocumentos(){
        if (spAnulaDocumentos == null){
            spAnulaDocumentos = new SpAnulaDocumentos();
        }

        return spAnulaDocumentos;
    }
    
    public String callStoreProcedureAnulacionDocumentos(String codDoc, String numDoc, boolean devolucion, boolean notaCreditoDebito,
                                                   String codDocDevolucion, String codDocNotaCreditoDebito){
        String sql = "{call sp_anulacion_documentos(?,?,?,?,?,?,?,?,?)}";
        String numDocDev = "", numDocNc = "";
        
        try{
            creaConexion();
            cs = conn.prepareCall(sql);
            
            cs.setString(1, VarGlobales.getCodEmpresa());
            cs.setString(2, codDoc);
            cs.setString(3, numDoc);
            if (devolucion){
                cs.setString(4, "1");
            }else{
                cs.setString(4, "0");
            }
            if (notaCreditoDebito){
                cs.setString(5, "1");
            }else{
                cs.setString(5, "0");
            }
            cs.setString(6, codDocDevolucion);
            cs.setString(7, codDocNotaCreditoDebito);
            
            Date dateNuevaFechaDoc = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String nuevaFechaDoc = sdf.format(dateNuevaFechaDoc);
            cs.setString(8, nuevaFechaDoc);
                   
            insertDeleteUpdate_StoreProcedure(cs);
            
            numDocDev = cs.getString(9);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SpAnulaDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //return rsConfEmpresa;
        return numDocDev;
    }
}
