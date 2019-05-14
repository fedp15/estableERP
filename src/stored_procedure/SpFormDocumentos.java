package stored_procedure;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Internacionalizacion;

public class SpFormDocumentos extends conexion{
    private static SpFormDocumentos spFormDocumentos;
    private CallableStatement cs = null;
    private ResultSet rsFormDocumentos;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();

    private SpFormDocumentos() {
    }
    
    public static SpFormDocumentos getFormDocumentos(){
        if (spFormDocumentos == null){
            spFormDocumentos = new SpFormDocumentos();
        }

        return spFormDocumentos;
    }
                
    public ResultSet callStoreProcedureDocumentos(String op, String tipdoc, String codPro, String numDoc, String idPersona, String und, String cantUnd,
                                                  String invLog, String invFis, String numItem, String cantidad, String precio, String mtoTotal,
                                                  String fecha, String fechaDoc, String mtoIva, String tipIva, String compVend, String condicPago,
                                                  String statusValor, String porcenDsctoArticulo, String mtoDesPro, String desdoc, 
                                                  String orden, String accion, String mtoBase, String numControl, String almacen, String naturalezaDscto,
                                                  String porcentDsctoDoc, String placaVehiculo, String kilometrajeVehiculo, String almaceDestino){
        String sql = "{call sp_formDocumentos(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        boolean lMovPersona = false;
        
        try{
            creaConexion();
            cs = conn.prepareCall(sql);

            if(op.equals("SELECT_TALLER") || op.equals("SELECT_DOC") || 
                    op.equals("SELECT_TABLA_TALLER") || op.equals("SELECT_TABLA_DOC")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, "");
                cs.setString(4, "");
                cs.setString(5, tipdoc);
                cs.setString(6, "");
                cs.setString(7, numDoc);
                cs.setString(8, "");
                cs.setString(9, "");
                cs.setString(10, "");
                cs.setString(11, "");
                cs.setString(12, "");
                cs.setString(13, "");
                cs.setString(14, "");
                cs.setString(15, "");
                cs.setString(16, "");
                cs.setString(17, "");
                cs.setString(18, "");
                cs.setString(19, "");
                cs.setString(20, "");
                cs.setString(21, "");
                cs.setString(22, "");
                cs.setString(23, "");
                cs.setString(24, "");
                cs.setString(25, "");
                cs.setString(26, "");
                cs.setString(27, "");
                cs.setString(28, "");
                cs.setString(29, "");
                cs.setString(30, "");
                cs.setString(31, "");
                cs.setString(32, "");
                cs.setString(33, "");
                cs.setString(34, "");
                cs.setString(35, "");
                cs.setString(36, "");
                
                rsFormDocumentos = consultarStoreProcedure(cs);
            }
            
            if(op.equals("INSERT_TALLER") || op.equals("INSERT_DOC") || op.equals("UPDATE_TALLER")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, VarGlobales.getIdUser());
                cs.setString(4, VarGlobales.getMacPc());
                cs.setString(5, tipdoc);
                cs.setString(6, codPro);
                cs.setString(7, numDoc);
                cs.setString(8, idPersona);
                cs.setString(9, und);
                cs.setString(10, cantUnd);
                cs.setString(11, invLog);
                cs.setString(12, invFis);
                cs.setString(13, numItem);
                cs.setString(14, cantidad);
                cs.setString(15, precio);
                cs.setString(16, mtoTotal);
                cs.setString(17, fecha);
                cs.setString(18, fechaDoc);
                cs.setString(19, mtoIva);
                cs.setString(20, tipIva);
                cs.setString(21, compVend);
                cs.setString(22, condicPago);
                cs.setString(23, statusValor);
                cs.setString(24, porcenDsctoArticulo);
                cs.setString(25, mtoDesPro);
                cs.setString(26, desdoc);
                cs.setString(27, orden);
                cs.setString(28, accion);
                cs.setString(29, mtoBase);
                cs.setString(30, numControl);
                cs.setString(31, almacen);
                cs.setString(32, naturalezaDscto);
                cs.setString(33, porcentDsctoDoc);
                cs.setString(34, placaVehiculo);
                cs.setString(35, kilometrajeVehiculo);
                cs.setString(36, almaceDestino);
                    
                insertDeleteUpdate_StoreProcedure(cs);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SpFormDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rsFormDocumentos;
    }
}