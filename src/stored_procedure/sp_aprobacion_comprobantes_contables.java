package stored_procedure;

import Modelos.conexion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricardo
 */
public class sp_aprobacion_comprobantes_contables extends conexion{
    private CallableStatement stmt;
    private ResultSet rs, rsStoredProcedure, rsIntermedio;
    //private String etiqueta = "", transaccion = "", campo = "", estadoOperacion = "",
    private String query = "";
    private static int j = 0;;

    public sp_aprobacion_comprobantes_contables() {

    }
    
    public ResultSet getAprobacionCbtes(String codEmpresa, String tipDoc, String numCbte, int activo,
                                 String fechaDesde, String fechaHasta, String idGrupAsientos, String datosCargar){
        try {
            creaConexion();
            
            query = "{CALL sp_aprobacion_asientos_contables('"+codEmpresa+"', '"+tipDoc+"', '"+numCbte+"', "+activo+", "+
                                                           "'"+fechaDesde+"', '"+fechaHasta+"', '"+idGrupAsientos+"','"+datosCargar+"')}";
            stmt = conn.prepareCall(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    
            rsStoredProcedure = stmt.executeQuery();
                
            rsStoredProcedure.last();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(sp_aprobacion_comprobantes_contables.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            //try {
                //rs.close();
                //conn.close();
            //} catch (SQLException ex) {
            //    Logger.getLogger(sp_asientos_contables.class.getName()).log(Level.SEVERE, null, ex);
            //}
        }
        
        return rsStoredProcedure;
    }
}