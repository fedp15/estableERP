package stored_procedure;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Internacionalizacion;
import util.SQLQuerys;

public class SpFormEmpresas extends conexion{
    private static SpFormEmpresas spFormEmpresas;
    private CallableStatement cs = null;
    private ResultSet rsFormEmpresas;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();

    private SpFormEmpresas() {
    }
    
    public static SpFormEmpresas getFormEmpresas(){
        if (spFormEmpresas == null){
            spFormEmpresas = new SpFormEmpresas();
        }

        return spFormEmpresas;
    }
                
    public ResultSet callStoreProcedureEmpresas(String op, ResultSet rsEmpresas){
        String sql = "{call sp_formEmpresas(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

        try {
            creaConexion();
            cs = conn.prepareCall(sql);
            
            if(op.equals("SELECT")){
                cs.setString(1, op);
                cs.setString(2, rsEmpresas.getString("emp_codigo"));
                cs.setString(3, "");
                cs.setString(4, "");
                cs.setString(5, "");
                cs.setString(6, "");
                cs.setString(7, "");
                cs.setString(8, "0");
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
                
                rs = consultarStoreProcedure(cs);
            }
            
            if(op.equals("INSERT") || op.equals("UPDATE")){
                cs.setString(1, op);
                cs.setString(2, rsEmpresas.getString("emp_codigo"));
                cs.setString(3, VarGlobales.getIdUser());
                cs.setString(4, VarGlobales.getMacPc());
                cs.setString(5, rsEmpresas.getString("emp_nombre"));
                cs.setString(6, rsEmpresas.getString("emp_rif"));
                cs.setString(7, rsEmpresas.getString("emp_direccion"));
                
                String activo = "0";
                if (rsEmpresas.getBoolean("emp_activo")){
                    activo = "1";
                }
                cs.setString(8, activo);
                cs.setString(9, rsEmpresas.getString("emp_telefono"));
                cs.setString(10, rsEmpresas.getString("emp_correo"));
                //if (moneda.isEmpty()){
                    cs.setString(11, "");
                //}else{
                //    cs.setString(11, moneda.substring(0, moneda.indexOf("-")-1).trim());
                //}
                cs.setString(12, rsEmpresas.getString("emp_pais"));
                cs.setString(13, rsEmpresas.getString("emp_fax"));
                cs.setString(14, rsEmpresas.getString("emp_paginaweb"));
                cs.setString(15, rsEmpresas.getString("emp_tipoidentificador"));
                cs.setString(16, rsEmpresas.getString("emp_estado"));
                cs.setString(17, rsEmpresas.getString("emp_municipio"));
                cs.setString(18, rsEmpresas.getString("emp_parroquia"));
                cs.setString(19, rsEmpresas.getString("emp_sector"));
                
                String predeterminada = "0";
                if (rsEmpresas.getBoolean("emp_predeterminada")){
                    predeterminada = "1";
                }
                cs.setString(20, predeterminada);
                
                System.err.println("Datos: "+rsEmpresas.getString("emp_codigo")+"\n"+
                                             rsEmpresas.getString("emp_nombre")+"\n"+
                                             rsEmpresas.getString("emp_rif")+"\n"+
                                             rsEmpresas.getString("emp_direccion")+"\n"+
                                             rsEmpresas.getString("emp_activo")+"\n"+
                                             rsEmpresas.getString("emp_telefono")+"\n"+
                                             rsEmpresas.getString("emp_correo")+"\n"+
                                             rsEmpresas.getString("emp_pais")+"\n"+
                                             rsEmpresas.getString("emp_fax")+"\n"+
                                             rsEmpresas.getString("emp_paginaweb")+"\n"+
                                             rsEmpresas.getString("emp_tipoidentificador")+"\n"+
                                             rsEmpresas.getString("emp_estado")+"\n"+
                                             rsEmpresas.getString("emp_municipio")+"\n"+
                                             rsEmpresas.getString("emp_parroquia")+"\n"+
                                             rsEmpresas.getString("emp_sector")+"\n"+
                                             rsEmpresas.getString("emp_predeterminada"));
                
                insertDeleteUpdate_StoreProcedure(cs);
                
                SQLQuerys modificar = new SQLQuerys();
                
//                sql = "UPDATE DNEMPRESAS SET emp_welcome='0' \n"+
//                      "WHERE emp_codigo='000001';";
//                modificar.SqlUpdate(sql);
                
                sql = "UPDATE DNEMPRESAS SET emp_moneda='"+rsEmpresas.getString("emp_moneda")+"', \n"+
                                            "nombre_foto_logo='"+rsEmpresas.getString("nombre_foto_logo")+"', \n"+
                                            "imagen='"+rsEmpresas.getString("imagen")+"' \n"+
                      "WHERE emp_codigo='"+rsEmpresas.getString("emp_codigo")+"';";
                modificar.SqlUpdate(sql);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SpFormEmpresas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rsFormEmpresas;
    }
}