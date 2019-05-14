package stored_procedure;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class sp_costos extends conexion{
    private static sp_costos spCostos;
    private CallableStatement cs = null;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();

    private sp_costos() {
    }
    
    public static sp_costos getSpCostos(){
        if (spCostos == null){
            spCostos = new sp_costos();
        }

        return spCostos;
    }
    
    public ResultSet callStoreProcedureCostos(String op, String tipDOc, String codPro, boolean lRecalculo){
        String sql = "{call sp_costos(?,?,?,?,?)}";
        ResultSet rsCostos = null;
        
        try{
            creaConexion();
            cs = conn.prepareCall(sql);

            if(op.equals("SELECT MOVIMIENTO PRODUCTO") || op.equals("SELECT ULTIMO COSTO") || op.equals("SELECT REVERSO ULTIMO COSTO")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, codPro);
                cs.setString(4, tipDOc);
                cs.setBoolean(5, lRecalculo);
                
                rsCostos = consultarStoreProcedure(cs);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(sp_costos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rsCostos;
    }
}
