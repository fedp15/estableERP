package stored_procedure;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Internacionalizacion;

public class SpCxC extends conexion{
    private static SpCxC spCxC;
    private CallableStatement cs = null;
    private ResultSet rsCxC;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();

    private SpCxC() {
    }
    
    public static SpCxC getSpCxC(){
        if (spCxC == null){
            spCxC = new SpCxC();
        }

        return spCxC;
    }
                
    public ResultSet callStoreProcedureCxC(String op, String idFiscal){
        String sql = "{call sp_saldos_cxp(?,?,?,?,?,?)}";
        boolean lMovPersona = false;
        
        try{
            creaConexion();
            cs = conn.prepareCall(sql);

            if(op.equals("Limites_Creditos") || op.equals("Doc_CXP")){
                cs.setString(1, VarGlobales.getCodEmpresa());
                cs.setString(2, op);
                cs.setString(3, idFiscal);
                cs.setString(4, "");
                cs.setString(5, "");
                cs.setString(6, "");
                
                rsCxC = consultarStoreProcedure(cs);
            }

            if(op.equals("Documentos_vencidos")){
                cs.setString(1, VarGlobales.getCodEmpresa());
                cs.setString(2, op);
                cs.setString(3, idFiscal);
                cs.setString(4, "");
                cs.setString(5, "");
                cs.setString(6, "");
                
                rsCxC = consultarStoreProcedure(cs);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SpCxC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rsCxC;
    }
}