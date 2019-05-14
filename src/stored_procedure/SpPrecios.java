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

public class SpPrecios extends conexion{
    private static SpPrecios spPrecios;
    private CallableStatement cs = null;
    private ResultSet rsPrecios;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();

    private SpPrecios() {
    }
    
    public static SpPrecios getSpPrecios(){
        if (spPrecios == null){
            spPrecios = new SpPrecios();
        }

        return spPrecios;
    }
    
    public ResultSet callStoreProcedureCostos(String op, String codPro, String codPrecio, String tipPrecio,
                                              String costoActual, String costoAnterior, String costoPromedio, String utilidad,
                                              String comision, String precioVenta, String und, String activo){
        String sql = "{call sp_precios(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        boolean lMovPersona = false;
        
        try{
            creaConexion();
            cs = conn.prepareCall(sql);
        
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fch = sdf.format(date);
            String fechavigen = fch;

            if(op.equals("SELECT")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, "");
                cs.setString(4, "");
                cs.setString(5, "");
                cs.setString(6, codPro);
                cs.setString(7, tipPrecio);
                cs.setString(8, "");
                cs.setString(9, "");
                cs.setString(10, "");
                cs.setString(11, "");
                cs.setString(12, "");
                cs.setString(13, "");
                cs.setString(14, "");
                cs.setString(15, "");
                cs.setBoolean(16, true);
                
                rsPrecios = consultarStoreProcedure(cs);
            }
            
            if(op.equals("INSERT") || op.equals("UPDATE")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, VarGlobales.getIdUser());
                cs.setString(4, VarGlobales.getMacPc());
                cs.setString(5, codPrecio);
                cs.setString(6, codPro);
                cs.setString(7, tipPrecio);
                
                VarGlobales.setValorBigDecimal(costoActual);
                cs.setString(8, ""+VarGlobales.getValorBigDecimal());
                VarGlobales.setValorBigDecimal(costoAnterior);
                cs.setString(9, ""+VarGlobales.getValorBigDecimal());
                VarGlobales.setValorBigDecimal(costoPromedio);
                cs.setString(10, ""+VarGlobales.getValorBigDecimal());
                
                VarGlobales.setValorBigDecimal(utilidad);
                cs.setString(11, ""+VarGlobales.getValorBigDecimal());
                VarGlobales.setValorBigDecimal(comision);
                cs.setString(12, ""+VarGlobales.getValorBigDecimal());
                VarGlobales.setValorBigDecimal(precioVenta);
                cs.setString(13, ""+VarGlobales.getValorBigDecimal());
                    
                cs.setString(14, und);
                cs.setString(15, fch);
                if (activo.equals("1")){
                    cs.setBoolean(16, true);
                }else{
                    cs.setBoolean(16, false);
                }
                    
                insertDeleteUpdate_StoreProcedure(cs);
            }

            if(op.equals("DELETE")){
                
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SpPrecios.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rsPrecios;
    }
}
