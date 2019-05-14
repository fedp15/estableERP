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

public class SpFormMotor extends conexion{
    private static SpFormMotor spFormMotor;
    private CallableStatement cs = null;
    private ResultSet rsFormMotor;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();

    private SpFormMotor() {
    }
    
    public static SpFormMotor getFormMotor(){
        if (spFormMotor == null){
            spFormMotor = new SpFormMotor();
        }

        return spFormMotor;
    }
    
    public ResultSet callStoreProcedureMotor(String op, String op2, String op3, String codigo, String nombre, String codigoTipMotor, String codigoMarcaVeh,
                                             String codigoTipCilind, String codigoCombust, int activo){
        String sql = "{call sp_formMotor(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
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
                cs.setString(6, "");
                cs.setString(7, "");
                cs.setString(8, "");
                cs.setString(9, "");
                cs.setString(10, "");
                cs.setBoolean(11, true);
                cs.setString(12, op2);
                cs.setString(13, op3);
                
                rsFormMotor = consultarStoreProcedure(cs);
            }
            
            if(op3.equals("LIKE")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, "");
                cs.setString(4, "");
                cs.setString(5, codigo);
                cs.setString(6, nombre);
                cs.setString(7, codigoTipMotor);
                cs.setString(8, codigoMarcaVeh);
                cs.setString(9, codigoTipCilind);
                cs.setString(10, codigoCombust);
                cs.setBoolean(11, true);
                cs.setString(12, op2);
                cs.setString(13, op3);
                
                rsFormMotor = consultarStoreProcedure(cs);
            }
            
            if(op.equals("INSERT") || op.equals("UPDATE")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, VarGlobales.getIdUser());
                cs.setString(4, VarGlobales.getMacPc());
                cs.setString(5, codigo);
                cs.setString(6, nombre);
                cs.setString(7, codigoTipMotor);
                cs.setString(8, codigoMarcaVeh);
                cs.setString(9, codigoTipCilind);
                cs.setString(10, codigoCombust);

                if (activo==1){
                    cs.setBoolean(11, true);
                }else{
                    cs.setBoolean(11, false);
                }
                
                cs.setString(12, op2);
                cs.setString(13, op3);
                    
                insertDeleteUpdate_StoreProcedure(cs);
            }

            if(op.equals("DELETE")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, "");
                cs.setString(4, "");
                cs.setString(5, codigo);
                cs.setString(6, "");
                cs.setString(7, "");
                cs.setString(8, "");
                cs.setString(9, "");
                cs.setString(10, "");
                cs.setBoolean(11, true);
                cs.setString(12, op2);
                cs.setString(13, op3);

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
            Logger.getLogger(SpFormMotor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rsFormMotor;
    }
}