package stored_procedure;

import Modelos.conexion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricardo
 */
public class sp_balance_comprobacion extends conexion{
    private CallableStatement stmt;
    private ResultSet rs, rsStoredProcedure, rsIntermedio;
    //private String etiqueta = "", transaccion = "", campo = "", estadoOperacion = "",
    private String query = "";
    private static int j = 0;;

    public sp_balance_comprobacion() {

    }
    
    public ResultSet getBalanceComprobacion(String codEmpresa, int activo, String transaccion, String etiqueta, String campo,
                                            String fechaDesde, String fechaHasta){
        try {
            creaConexion();
            
            query = "CALL sp_balance_comprobacion('"+codEmpresa+"', 'TOTAL', 'inv_total', "+activo+", '"+fechaDesde+"',"+
                    "'"+fechaHasta+"');";
            //query = "{CALL sp_asientos_contables('"+codEmpresa+"', '"+numDoc+"', '"+tipDoc+"', "
            //                                  + "'"+transaccion+"', '"+etiqueta+"', '"+campo+"', "+estadoOperacion+")}";
            stmt = conn.prepareCall(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    
//                          stmt.setString(1, codEmpresa);
//                          stmt.setString(2, numDoc);
//                          stmt.setString(3, tipDoc);
//                          stmt.setString(4, transaccion);
//                        stmt.setString(5, tipoMonto);
//                          stmt.setString(6, campo);
//                          stmt.setInt(7, activo);
                    
            rsStoredProcedure = stmt.executeQuery();
            rsStoredProcedure.last();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(sp_balance_comprobacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rsStoredProcedure;
    }
}