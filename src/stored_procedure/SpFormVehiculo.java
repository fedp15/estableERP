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

public class SpFormVehiculo extends conexion{
    private static SpFormVehiculo spFormVehiculo;
    private CallableStatement cs = null;
    private ResultSet rsFormVehiculo;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();

    private SpFormVehiculo() {
    }
    
    public static SpFormVehiculo getFormVehiculo(){
        if (spFormVehiculo == null){
            spFormVehiculo = new SpFormVehiculo();
        }

        return spFormVehiculo;
    }
    
    public ResultSet callStoreProcedureVehiculo(String op, String op2, String op3, String placa, String idPers, String codMarca, String codModelo,
                                                String codCategoriaVeh, String codMotor, int activo, String codTipoMotor, String codTransmision,
                                                String codTraccion, String codTipCilind, String codCombust, String ano, String color, int capasidad, 
                                                String region, String serial_1, String serial_2, String serial_3, String foto_1, String foto_2, 
                                                String foto_3, String foto_4, String notas, String fecha){
        String sql = "{call sp_formVehiculo(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        boolean lMovPersona = false;
        
        try{
            creaConexion();
            cs = conn.prepareCall(sql);

            if(op.equals("SELECT")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, "");
                cs.setString(4, "");
                cs.setString(5, placa);
                cs.setString(6, "");
                cs.setString(7, "");
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
                cs.setInt(18, 0);
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
                cs.setBoolean(29, true);
                cs.setString(30, op2);
                cs.setString(31, op3);
                
                rsFormVehiculo = consultarStoreProcedure(cs);
            }
            
            if(op3.equals("LIKE")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, "");
                cs.setString(4, "");
                cs.setString(5, placa);
                cs.setString(6, idPers);
                cs.setString(7, codMarca);
                cs.setString(8, codModelo);
                cs.setString(9, codCategoriaVeh);
                cs.setString(10, codMotor);
                cs.setString(11, codTipoMotor);
                cs.setString(12, codTransmision);
                cs.setString(13, codTraccion);
                cs.setString(14, codTipCilind);
                cs.setString(15, codCombust);
                cs.setString(16, ano);
                cs.setString(17, color);
                cs.setInt(18, capasidad);
                cs.setString(19, region);
                cs.setString(20, serial_1);
                cs.setString(21, serial_2);
                cs.setString(22, serial_3);
                cs.setString(23, foto_1);
                cs.setString(24, foto_2);
                cs.setString(25, foto_3);
                cs.setString(26, foto_4);
                cs.setString(27, notas);
                cs.setString(28, fecha);
                cs.setBoolean(29, true);
                cs.setString(30, op2);
                cs.setString(31, op3);
                
                rsFormVehiculo = consultarStoreProcedure(cs);
            }
            
            if(op.equals("INSERT") || op.equals("UPDATE") || op.equals("UPDATE_PLACA")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, VarGlobales.getIdUser());
                cs.setString(4, VarGlobales.getMacPc());
                
                cs.setString(5, placa);
                cs.setString(6, idPers);
                cs.setString(7, codMarca);
                cs.setString(8, codModelo);
                cs.setString(9, codCategoriaVeh);
                cs.setString(10, codMotor);
                cs.setString(11, codTipoMotor);
                cs.setString(12, codTransmision);
                cs.setString(13, codTraccion);
                cs.setString(14, codTipCilind);
                cs.setString(15, codCombust);
                cs.setString(16, ano);
                cs.setString(17, color);
                cs.setInt(18, capasidad);
                cs.setString(19, region);
                cs.setString(20, serial_1);
                cs.setString(21, serial_2);
                cs.setString(22, serial_3);
                cs.setString(23, foto_1);
                cs.setString(24, foto_2);
                cs.setString(25, foto_3);
                cs.setString(26, foto_4);
                cs.setString(27, notas);
                cs.setString(28, fecha);

                if (activo==1){
                    cs.setBoolean(29, true);
                }else{
                    cs.setBoolean(29, false);
                }
                
                cs.setString(30, op2);
                cs.setString(31, op3);
                    
                insertDeleteUpdate_StoreProcedure(cs);
            }

            if(op.equals("DELETE")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, "");
                cs.setString(4, "");
                cs.setString(5, placa);
                cs.setString(6, "");
                cs.setString(7, "");
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
                cs.setInt(18, 0);
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
                cs.setBoolean(29, true);
                cs.setString(30, op2);
                cs.setString(31, op3);

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
            Logger.getLogger(SpFormVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rsFormVehiculo;
    }
}