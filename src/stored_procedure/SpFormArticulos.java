package stored_procedure;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Internacionalizacion;

public class SpFormArticulos extends conexion{
    private static SpFormArticulos spFormArticulos;
    private CallableStatement cs = null;
    private ResultSet rsFormArticulos;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();

    private SpFormArticulos() {
    }
    
    public static SpFormArticulos getFormArticulos(){
        if (spFormArticulos == null){
            spFormArticulos = new SpFormArticulos();
        }

        return spFormArticulos;
    }
                
    public ResultSet callStoreProcedureArticulos(String op, String op2, String op3, String grupo, String subGrupo, String marca, String proveedor,
                                                 String codArticulo, String nombreArticulo, String descripArticulo, String tipPrecio, 
                                                 String codUnd, String tipIva, String imgArticulo, String activo, String isService, String existMinima, 
                                                 String existMaxima, String verificaExistencias){
        String sql = "{call sp_formArticulo(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        boolean lMovPersona = false;
        
        try{
            creaConexion();
            cs = conn.prepareCall(sql);

            if(op.equals("SELECT")){
                cs.setString(1, op);
                cs.setString(2, op2);
                cs.setString(3, op3);
                cs.setString(4, VarGlobales.getCodEmpresa());
                cs.setString(5, "");
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
                cs.setString(18, "");
                cs.setString(19, "");
                cs.setString(20, "");
                cs.setString(21, "");
                cs.setString(22, "");
                
                rsFormArticulos = consultarStoreProcedure(cs);
            }
            
            if(op.equals("SELECT_PRODUCTO")){
                cs.setString(1, op);
                cs.setString(2, op2);
                cs.setString(3, op3);
                cs.setString(4, VarGlobales.getCodEmpresa());
                cs.setString(5, "");
                cs.setString(6, "");
                cs.setString(7, "");
                cs.setString(8, "");
                cs.setString(9, codArticulo);
                cs.setString(10, "");
                cs.setString(11, "");
                cs.setString(12, "");
                cs.setString(13, "");
                cs.setString(14, "");
                cs.setString(15, "");
                cs.setString(16, "");
                cs.setString(17, "");
                cs.setString(18, "");
                cs.setString(19, "");
                cs.setString(20, "");
                cs.setString(21, "");
                cs.setString(22, "");
                
                rsFormArticulos = consultarStoreProcedure(cs);
            }
            
            if(op3.equals("LIKE")){
                cs.setString(1, op);
                cs.setString(2, op2);
                cs.setString(3, op3);
                cs.setString(4, VarGlobales.getCodEmpresa());
                cs.setString(5, grupo);
                cs.setString(6, subGrupo);
                cs.setString(7, marca);
                cs.setString(8, proveedor);
                cs.setString(9, codArticulo);
                cs.setString(10, "");
                cs.setString(11, "");
                cs.setString(12, "");
                cs.setString(13, "");
                cs.setString(14, "");
                cs.setString(15, "");
                cs.setString(16, "");
                cs.setString(17, "");
                cs.setString(18, "");
                cs.setString(19, "");
                cs.setString(20, "");
                cs.setString(21, "");
                cs.setString(22, "");
                
                rsFormArticulos = consultarStoreProcedure(cs);
            }
            
            if(op.equals("INSERT") || op.equals("UPDATE")){
                cs.setString(1, op);
                cs.setString(2, op2);
                cs.setString(3, op3);
                cs.setString(4, VarGlobales.getCodEmpresa());
                cs.setString(5, grupo);
                cs.setString(6, subGrupo);
                cs.setString(7, marca);
                cs.setString(8, proveedor);
                cs.setString(9, codArticulo);
                cs.setString(10, VarGlobales.getIdUser());
                cs.setString(11, VarGlobales.getMacPc());
                cs.setString(12, nombreArticulo);
                cs.setString(13, descripArticulo);
                cs.setString(14, tipPrecio);
                cs.setString(15, codUnd);
                cs.setString(16, tipIva);
                cs.setString(17, imgArticulo);
                cs.setString(18, activo);
                cs.setString(19, isService);
                cs.setString(20, existMinima);
                cs.setString(21, existMaxima);
                cs.setString(22, verificaExistencias);
                    
                insertDeleteUpdate_StoreProcedure(cs);
            }

            if(op.equals("DELETE")){
                cs.setString(1, op);
                cs.setString(2, op2);
                cs.setString(3, op3);
                cs.setString(4, VarGlobales.getCodEmpresa());
                cs.setString(5, grupo);
                cs.setString(6, subGrupo);
                cs.setString(7, marca);
                cs.setString(8, proveedor);
                cs.setString(9, codArticulo);
                cs.setString(10, "");
                cs.setString(11, "");
                cs.setString(12, "");
                cs.setString(13, "");
                cs.setString(14, "");
                cs.setString(15, "");
                cs.setString(16, "");
                cs.setString(17, "");
                cs.setString(18, "");
                cs.setString(19, "");
                cs.setString(20, "");
                cs.setString(21, "");
                cs.setString(22, "");

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
            Logger.getLogger(SpFormArticulos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rsFormArticulos;
    }
}