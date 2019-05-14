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

public class SpFormMarcas extends conexion{
    private static SpFormMarcas spFormMarcas;
    private CallableStatement cs = null;
    private ResultSet rsFormMarcas;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();

    private SpFormMarcas() {
    }
    
    public static SpFormMarcas getSpFormMarcas(){
        if (spFormMarcas == null){
            spFormMarcas = new SpFormMarcas();
        }

        return spFormMarcas;
    }
    
    public ResultSet callStoreProcedureMarcas(String op, String formOrg, String codigo, String nombre, int activo){
        String sql = "{call sp_formMarcas(?,?,?,?,?,?,?,?)}";
        boolean lMovPersona = false;
        
        try{
            creaConexion();
            cs = conn.prepareCall(sql);

            if(op.equals("SELECT")){
                cs.setString(1, op);
                cs.setString(2, formOrg);
                cs.setString(3, VarGlobales.getCodEmpresa());
                cs.setString(4, "");
                cs.setString(5, "");
                cs.setString(6, codigo);
                cs.setString(7, nombre);
                cs.setBoolean(8, true);
                
                rsFormMarcas = consultarStoreProcedure(cs);
            }
            
            if(op.equals("LIKE")){
                cs.setString(1, op);
                cs.setString(2, formOrg);
                cs.setString(3, VarGlobales.getCodEmpresa());
                cs.setString(4, "");
                cs.setString(5, "");
                cs.setString(6, codigo);
                cs.setString(7, nombre);
                cs.setBoolean(8, true);
                
                rsFormMarcas = consultarStoreProcedure(cs);
            }
            
            if(op.equals("INSERT") || op.equals("UPDATE")){
                cs.setString(1, op);
                cs.setString(2, formOrg);
                cs.setString(3, VarGlobales.getCodEmpresa());
                cs.setString(4, VarGlobales.getIdUser());
                cs.setString(5, VarGlobales.getMacPc());
                cs.setString(6, codigo);
                cs.setString(7, nombre);

                if (activo==1){
                    cs.setBoolean(8, true);
                }else{
                    cs.setBoolean(8, false);
                }
                    
                insertDeleteUpdate_StoreProcedure(cs);
            }

            if(op.equals("DELETE")){
                cs.setString(1, op);
                cs.setString(2, formOrg);
                cs.setString(3, VarGlobales.getCodEmpresa());
                cs.setString(4, VarGlobales.getIdUser());
                cs.setString(5, VarGlobales.getMacPc());
                cs.setString(6, codigo);
                cs.setString(7, nombre);
                cs.setBoolean(8, true);

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
            Logger.getLogger(SpFormMarcas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rsFormMarcas;
    }
}
