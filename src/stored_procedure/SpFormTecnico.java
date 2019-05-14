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

public class SpFormTecnico extends conexion{
    private static SpFormTecnico formTecnico;
    private CallableStatement cs = null;
    private ResultSet rsFormTecnico;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();

    private SpFormTecnico() {
    }
    
    public static SpFormTecnico getFormTecnico(){
        if (formTecnico == null){
            formTecnico = new SpFormTecnico();
        }

        return formTecnico;
    }
    
    public ResultSet callStoreProcedureTecnico(String op, String op2, String op3, String codigo, String nombre, String cedula, String telefono,
                                             String celular, String direccion, String correo, String activo, String codPais, String codEstado,
                                             String codMunicipio, String codParroquia, String codSector, String tipoIdentificador){
        String sql = "{call sp_formTecnico(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        boolean lMovPersona = false;
        
        try{
            creaConexion();
            cs = conn.prepareCall(sql);

            if(op.equals("SELECT")){
                cs.setString(1, op);
                cs.setString(2, "");
                cs.setString(3, "");
                cs.setString(4, "");
                cs.setString(5, codigo);
                cs.setString(6, "");
                cs.setString(7, "");
                cs.setString(8, "");
                cs.setString(9, "");
                cs.setString(10, "");
                cs.setString(11, "1");
                cs.setString(12, "");
                cs.setString(13, "");
                cs.setString(14, "");
                cs.setString(15, "");
                cs.setString(16, "");
                cs.setString(17, "");
                cs.setString(18, "");
                
                rsFormTecnico = consultarStoreProcedure(cs);
            }
            
            if(op3.equals("LIKE")){
                cs.setString(1, op3);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, "");
                cs.setString(4, "");
                cs.setString(5, codigo);
                cs.setString(6, nombre);
                cs.setString(7, "");
                cs.setString(8, "");
                cs.setString(9, "");
                cs.setString(10, "");
                cs.setBoolean(11, true);
                cs.setString(12, "");
                cs.setString(13, "");
                cs.setString(14, "");
                cs.setString(15, "");
                cs.setString(16, "");
                cs.setString(17, "");
                cs.setString(18, "");
                
                rsFormTecnico = consultarStoreProcedure(cs);
            }
            
            if(op.equals("INSERT") || op.equals("UPDATE")){
                cs.setString(1, op);
                cs.setString(2, VarGlobales.getCodEmpresa());
                cs.setString(3, VarGlobales.getIdUser());
                cs.setString(4, VarGlobales.getMacPc());
                cs.setString(5, codigo);
                cs.setString(6, nombre);
                cs.setString(7, cedula);
                cs.setString(8, telefono);
                cs.setString(9, celular);
                cs.setString(10, direccion);
                cs.setString(11, correo);
                cs.setString(12, codPais);
                cs.setString(13, codEstado);
                cs.setString(14, codMunicipio);
                cs.setString(15, codParroquia);
                cs.setString(16, codSector);

                if (activo=="1"){
                    cs.setString(17, "1");
                }else{
                    cs.setString(17, "0");
                }
                
                cs.setString(18, tipoIdentificador);
                    
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
                cs.setString(11, "");
                cs.setString(12, "");
                cs.setString(13, "");
                cs.setString(14, "");
                cs.setString(15, "");
                cs.setString(16, "");
                
                if (activo=="1"){
                    cs.setString(17, "1");
                }else{
                    cs.setString(17, "0");
                }
                
                cs.setString(18, "");

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
            Logger.getLogger(SpFormTecnico.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rsFormTecnico;
    }
}