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

public class SpConfigEmpresa extends conexion{
    private static SpConfigEmpresa spConfigEmpresa;
    private CallableStatement cs = null;
    private ResultSet rsConfEmpresa;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();

    private SpConfigEmpresa() {
    }
    
    public static SpConfigEmpresa getSpConfigEmpresa(){
        if (spConfigEmpresa == null){
            spConfigEmpresa = new SpConfigEmpresa();
        }

        return spConfigEmpresa;
    }
    
    public ResultSet callStoreProcedureConfEmpresa(String op, String codEmp, String nombPrecio1, String nombPrecio2, String nombPrecio3,
                                                   String nombPrecio4, String nombPrecio5, String utilidadPrecio1, String utilidadPrecio2,
                                                   String utilidadPrecio3, String utilidadPrecio4, String utilidadPrecio5, String calcUtilCosto,
                                                   String consecutivo, String logConsecTaller, String usaFactElectronica, 
                                                   String proveedServicio, String userComprobElectro, String claveComprobElectro, String urlApiRest, 
                                                   String ubicArchCertificado, String claveArchCerticado, String ultimoConseCbtElectron,
                                                   String consecutivoGrupoSubGrupo, String visualizaUltimoRegForm, String ultimoConseTiqueteElectronico, 
                                                   String ultimoConseNotaCreditoElectronica, String ultimoConseNotaDebitoElectronica, String correoOrigen, 
                                                   String claveCorreoOrigen, String servidorSmtp, String puertoServidorSmtp, String asuntoCorreo, 
                                                   String cuerpoCorreo, String urlAccesToken, String consecReceptor){
        String sql = "{call sp_confiEmpresa(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        boolean lMovPersona = false;
        
        try{
            VarGlobales.setCodEmpresaConsulta(codEmp);
            creaConexion();
            VarGlobales.setCodEmpresaConsulta("");
            
            cs = conn.prepareCall(sql);

            if(op.equals("SELECT")){
                cs.setString(1, op);
                cs.setString(2, codEmp);
                cs.setString(3, "");
                cs.setString(4, "");
                cs.setString(5, "");
                cs.setString(6, "");
                cs.setString(7, "");
                cs.setString(8, "");
                cs.setString(9, "");
                cs.setString(10, "");
                cs.setString(11, "");
                cs.setString(12, "");
                cs.setBoolean(13, true);
                cs.setBoolean(14, true);
                cs.setInt(15, 0);
                cs.setBoolean(16, true);
                cs.setString(17, "");
                cs.setString(18, "");
                cs.setString(19, "");
                cs.setString(20, "");
                cs.setString(21, "");
                cs.setString(22, "");
                cs.setString(23, "");
                cs.setBoolean(24, true);
                cs.setBoolean(25, true);
                cs.setString(26, "");
                cs.setString(27, "");
                cs.setString(28, "");
                cs.setString(29, "");
                cs.setString(30, "");
                cs.setString(31, "");
                cs.setString(32, "");
                cs.setString(33, "");
                cs.setString(34, "");
                cs.setString(35, "");
                cs.setString(36, "");
                
                rsConfEmpresa = consultarStoreProcedure(cs);
            }
            
            if(op.equals("REPLACE")){
                cs.setString(1, op);
                cs.setString(2, codEmp);
                cs.setString(3, nombPrecio1);
                cs.setString(4, nombPrecio2);
                cs.setString(5, nombPrecio3);
                cs.setString(6, nombPrecio4);
                cs.setString(7, nombPrecio5);
                
                VarGlobales.setValorBigDecimal(utilidadPrecio1);
                cs.setString(8, ""+VarGlobales.getValorBigDecimal());
                VarGlobales.setValorBigDecimal(utilidadPrecio2);
                cs.setString(9, ""+VarGlobales.getValorBigDecimal());
                VarGlobales.setValorBigDecimal(utilidadPrecio3);
                cs.setString(10, ""+VarGlobales.getValorBigDecimal());
                
                VarGlobales.setValorBigDecimal(utilidadPrecio4);
                cs.setString(11, ""+VarGlobales.getValorBigDecimal());
                VarGlobales.setValorBigDecimal(utilidadPrecio5);
                cs.setString(12, ""+VarGlobales.getValorBigDecimal());
                
                if (calcUtilCosto.equals("1")){
                    cs.setBoolean(13, true);
                }else{
                    cs.setBoolean(13, false);
                }
                
                if (consecutivo.equals("1")){
                    cs.setBoolean(14, true);
                }else{
                    cs.setBoolean(14, false);
                }
                
                cs.setInt(15, Integer.valueOf(logConsecTaller));
                
                if (usaFactElectronica.equals("1")){
                    cs.setBoolean(16, true);
                }else{
                    cs.setBoolean(16, false);
                }
                cs.setString(17, proveedServicio);
                cs.setString(18, userComprobElectro);
                cs.setString(19, claveComprobElectro);
                cs.setString(20, urlApiRest);
                cs.setString(21, ubicArchCertificado);
                cs.setString(22, claveArchCerticado);
                cs.setString(23, ultimoConseCbtElectron);
                
                if (consecutivoGrupoSubGrupo.equals("1")){
                    cs.setBoolean(24, true);
                }else{
                    cs.setBoolean(24, false);
                }
                
                if (visualizaUltimoRegForm.equals("1")){
                    cs.setBoolean(25, true);
                }else{
                    cs.setBoolean(25, false);
                }
                cs.setString(26, ultimoConseTiqueteElectronico);
                cs.setString(27, ultimoConseNotaCreditoElectronica);
                cs.setString(28, ultimoConseNotaDebitoElectronica);
                cs.setString(29, correoOrigen);
                cs.setString(30, claveCorreoOrigen);
                cs.setString(31, servidorSmtp);
                cs.setString(32, puertoServidorSmtp);
                cs.setString(33, asuntoCorreo);
                cs.setString(34, cuerpoCorreo);
                cs.setString(35, urlAccesToken);
                cs.setString(36, consecReceptor);
                    
                insertDeleteUpdate_StoreProcedure(cs);
            }

            if(op.equals("DELETE")){
                
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SpConfigEmpresa.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rsConfEmpresa;
    }
}
