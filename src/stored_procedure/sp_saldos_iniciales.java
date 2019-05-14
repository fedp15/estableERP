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
public class sp_saldos_iniciales extends conexion{
    private CallableStatement stmt;
    private ResultSet rs, rsStoredProcedure, rsIntermedio;
    //private String etiqueta = "", transaccion = "", campo = "", estadoOperacion = "",
    private String query = "";
    private static int j = 0;;

    public sp_saldos_iniciales() {

    }
    
    public ResultSet getBalanceGeneral(String codEmpresa, int activo, int nivel, String etiqueta, String campo, String reporte, String fecha){
        try {
            creaConexion();
            
            query = "CALL `sp_saldo_inicial`('"+codEmpresa+"', 'TOTAL', 'inv_total', '"+activo+"', '"+nivel+"', '"+reporte+"', '"+fecha+"');";
            
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
            Logger.getLogger(sp_saldos_iniciales.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rsStoredProcedure;
    }
}