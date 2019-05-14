package stored_procedure;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpStatusCbtsElectronicos extends conexion{
    private static SpStatusCbtsElectronicos spStatusCbtesElectronicos;
    private CallableStatement cs = null;
    private ResultSet rsConfEmpresa;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();

    private SpStatusCbtsElectronicos() {
    }
    
    public static SpStatusCbtsElectronicos getSpStatusCbtesElectronicos(){
        if (spStatusCbtesElectronicos == null){
            spStatusCbtesElectronicos = new SpStatusCbtsElectronicos();
        }

        return spStatusCbtesElectronicos;
    }
    
    public ResultSet callStoreProcedureStatusCbtesElectronicos(String op, String tipoCbteElectronico, String nombreCbteElectonico, boolean envioCbteElectronico, 
                                                   String numDoc, String idFiscalEmisor, String claveCbteElectronico, String idFiscalReceptor, 
                                                   String correoReceptor, int codeReponse, String pathFileJson, String fchCreacion, String fchEnvio,
                                                   String msjError, int situacionCbteElectronico, String ind_estado, String numConsecutivoCbte,
                                                   String detalleMensajeHacienda, String jsonRespuesta, String origen, String numConsecutivoReceptor,
                                                   String respuestaReceptor, String tipoEmisor, String tipoReceptor){
        String sql = "{call sp_status_cbtes_electronicos(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        boolean lMovPersona = false;
        
        try{
            creaConexion();
            cs = conn.prepareCall(sql);

            if(op.equals("SELECT")){
                cs.setString(1, op);
                cs.setString(2, "");
                cs.setString(3, "");
                cs.setBoolean(4, true);
                cs.setString(5, "");
                cs.setString(6, "");
                cs.setString(7, claveCbteElectronico);
                cs.setString(8, "");
                cs.setString(9, "");
                cs.setInt(10, 0);
                cs.setString(11, "");
                cs.setString(12, "");
                cs.setBoolean(13, true);
                cs.setString(14, "");
                cs.setInt(15, 0);
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
                
                rsConfEmpresa = consultarStoreProcedure(cs);
            }
            
            if(op.equals("REPLACE")){
                cs.setString(1, op);
                cs.setString(2, tipoCbteElectronico);
                cs.setString(3, nombreCbteElectonico);
                cs.setBoolean(4, envioCbteElectronico);
                cs.setString(5, numDoc);
                cs.setString(6, idFiscalEmisor);
                cs.setString(7, claveCbteElectronico);
                cs.setString(8, idFiscalReceptor);
                cs.setString(9, correoReceptor);
                cs.setInt(10, codeReponse);
                cs.setString(11, pathFileJson);
                cs.setString(12, fchCreacion);
                cs.setString(13, fchEnvio);
                cs.setString(14, msjError);
                cs.setInt(15, situacionCbteElectronico);
                cs.setString(16, ind_estado);
                cs.setString(17, numConsecutivoCbte);
                cs.setString(18, detalleMensajeHacienda);
                cs.setString(19, jsonRespuesta);
                cs.setString(20, origen);
                cs.setString(21, numConsecutivoReceptor);
                cs.setString(22, respuestaReceptor);
                cs.setString(23, tipoEmisor);
                cs.setString(24, tipoReceptor);
                cs.setString(25, VarGlobales.getCodEmpresa());
                cs.setString(26, VarGlobales.getCodigoEquipo());
                    
                insertDeleteUpdate_StoreProcedure(cs);
            }

            if(op.equals("DELETE")){
                
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SpStatusCbtsElectronicos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rsConfEmpresa;
    }
}
