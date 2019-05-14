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

public class SpFormModeloVehiculo extends conexion{
    private static SpFormModeloVehiculo spFormModeloVehiculo;
    private CallableStatement cs = null;
    private ResultSet rsFormModeloVehiculo;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();

    private SpFormModeloVehiculo() {
    }
    
    public static SpFormModeloVehiculo getFormModeloVehiculo(){
        if (spFormModeloVehiculo == null){
            spFormModeloVehiculo = new SpFormModeloVehiculo();
        }

        return spFormModeloVehiculo;
    }
    
    public ResultSet callStoreProcedureMarcas(String op, String op2, String op3, String codigoMarca, String codigo, String nombre, int activo,
                                              String nombreMarca){
        String sql = "{call sp_formModeloVehiculos(?,?,?,?,?,?,?,?,?,?,?)}";
        boolean lMovPersona = false;
        
        try{
            creaConexion();
            cs = conn.prepareCall(sql);

            if(op.equals("SELECT")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, "");
                cs.setString(4, "");
                cs.setString(5, codigoMarca);
                cs.setString(6, codigo);
                cs.setString(7, "");
                cs.setBoolean(8, true);
                cs.setString(9, op2);
                cs.setString(10, "");
                cs.setString(11, "");
                
                rsFormModeloVehiculo = consultarStoreProcedure(cs);
            }
            
            if(op3.equals("LIKE")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, "");
                cs.setString(4, "");
                cs.setString(5, codigoMarca);
                cs.setString(6, codigo);
                cs.setString(7, nombre);
                cs.setBoolean(8, true);
                cs.setString(9, op2);
                cs.setString(10, op3);
                cs.setString(11, nombreMarca);
                
                rsFormModeloVehiculo = consultarStoreProcedure(cs);
            }
            
            if(op.equals("INSERT") || op.equals("UPDATE")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, VarGlobales.getIdUser());
                cs.setString(4, VarGlobales.getMacPc());
                cs.setString(5, codigoMarca);
                cs.setString(6, codigo);
                cs.setString(7, nombre);

                if (activo==1){
                    cs.setBoolean(8, true);
                }else{
                    cs.setBoolean(8, false);
                }
                
                cs.setString(9, op2);
                cs.setString(10, "");
                cs.setString(11, codigoMarca);
                    
                insertDeleteUpdate_StoreProcedure(cs);
            }

            if(op.equals("DELETE")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, VarGlobales.getIdUser());
                cs.setString(4, VarGlobales.getMacPc());
                cs.setString(5, codigoMarca);
                cs.setString(6, codigo);
                cs.setString(7, nombre);
                cs.setBoolean(8, true);
                cs.setString(9, op2);
                cs.setString(10, "");
                cs.setString(11, "");

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
            Logger.getLogger(SpFormModeloVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rsFormModeloVehiculo;
    }
}
