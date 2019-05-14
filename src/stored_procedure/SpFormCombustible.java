package stored_procedure;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import util.Internacionalizacion;

public class SpFormCombustible extends conexion{
    private static SpFormCombustible spFormCombustible;
    private CallableStatement cs = null;
    private ResultSet rsFormModeloVehiculo;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();

    private SpFormCombustible() {
    }
    
    public static SpFormCombustible getFormCombustible(){
        if (spFormCombustible == null){
            spFormCombustible = new SpFormCombustible();
        }

        return spFormCombustible;
    }
    
    public ResultSet callStoreProcedureCombustible(String op, String op2, String codigo, String nombre, int activo){
        String sql = "{call sp_formCombustible(?,?,?,?,?,?,?,?)}";
        boolean lMovPersona = false;
        
        try{
            creaConexion();
            cs = conn.prepareCall(sql);

            if(op.equals("SELECT")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, "");
                cs.setString(4, "");
                cs.setString(5, codigo);
                cs.setString(6, nombre);
                cs.setBoolean(7, true);
                cs.setString(8, op2);
                
                rsFormModeloVehiculo = consultarStoreProcedure(cs);
            }
            
            if(op.equals("INSERT") || op.equals("UPDATE")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, VarGlobales.getIdUser());
                cs.setString(4, VarGlobales.getMacPc());
                cs.setString(5, codigo);
                cs.setString(6, nombre);

                if (activo==1){
                    cs.setBoolean(7, true);
                }else{
                    cs.setBoolean(7, false);
                }
                
                cs.setString(8, op2);
                    
                insertDeleteUpdate_StoreProcedure(cs);
            }

            if(op.equals("DELETE")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, "");
                cs.setString(4, "");
                cs.setString(5, codigo);
                cs.setString(6, "");
                cs.setBoolean(7, true);
                cs.setString(8, op2);

                cs.executeQuery();
//                lMovPersona = cs.getBoolean(9);
//
//                if (lMovPersona){
//                    JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgEliminaPersonaMovimiento"),
//                                                        idioma.loadLangMsg().getString("MsgNotificacion"),
//                                                        JOptionPane.INFORMATION_MESSAGE);
//                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SpFormCombustible.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rsFormModeloVehiculo;
    }
}