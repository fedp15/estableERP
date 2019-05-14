package stored_procedure;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpXmlFacturaElectronicaCRC extends conexion{
    private static SpXmlFacturaElectronicaCRC spXmlFacturaElectronicaCRC;
    private CallableStatement cs = null;
    private ResultSet spDatosXml;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();

    private SpXmlFacturaElectronicaCRC() {
    }
    
    public static SpXmlFacturaElectronicaCRC getSpXmlFacturaElectronicaCRC(){
        if (spXmlFacturaElectronicaCRC == null){
            spXmlFacturaElectronicaCRC = new SpXmlFacturaElectronicaCRC();
        }

        return spXmlFacturaElectronicaCRC;
    }
    
    public ResultSet callStoreProcedureConfEmpresa(String nunDoc, String tipDoc){
        String sql = "{call sp_datos_xml_factura_electronica_CRC(?,?,?)}";
        boolean lMovPersona = false;
        
        try{
            creaConexion();
            cs = conn.prepareCall(sql);

            cs.setString(1, VarGlobales.getCodEmpresa());
            cs.setString(2, nunDoc);
            cs.setString(3, tipDoc);
               
            spDatosXml = consultarStoreProcedure(cs);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SpXmlFacturaElectronicaCRC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return spDatosXml;
    }
}
