package stored_procedure;

import Modelos.conexion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricardo
 */
public class sp_asientos_contables extends conexion{
    private CallableStatement stmt;
    private ResultSet rs, rsStoredProcedure, rsIntermedio;
    //private String etiqueta = "", transaccion = "", campo = "", estadoOperacion = "",
    private String query = "";
    private static int j = 0;;

    public sp_asientos_contables() {

    }
    
    public ResultSet getAsientos(String codEmpresa, String  numDoc, String tipDoc, int activo, String transaccion, 
                                 String etiqueta, String campo, String estadoOperacion, String fechaDesde, String fechaHasta){
        try {
            creaConexion();
            
//            String sql = "SELECT ccont_empresa,ccont_num_config,ccont_documento,ccont_transaccion,ccont_cuenta,ccont_monto,ccont_activo,\n"+
//                         "       nomb_camp,etiqueta,doc_cobros,doc_pagos FROM dnconfig_contable \n" +
//                         "INNER JOIN maestro_tablas_campos on etiqueta=ccont_monto\n" +
//                         "INNER JOIN dndocumentos ON doc_empresa=ccont_empresa AND doc_codigo=ccont_documento \n"+
//                         "LEFT JOIN formula on nombre=ccont_monto\n" +
//                         "WHERE ccont_empresa='"+codEmpresa+"' AND ccont_documento='"+tipDoc+"'  AND ccont_activo="+activo;
//            
//            rs = consultar(sql);
            
//            if(rs.getRow()>0){
//                rs.beforeFirst();
//                
//                int i = 0;
//                while(rs.next()){
//                    i++;

//                    etiqueta = rs.getString("etiqueta");
//                    transaccion = rs.getString("ccont_transaccion");
//                    campo = rs.getString("nomb_camp");
//                    estadoOperacion = "";
//
//                    if (transaccion.equals("Aplicacion") || transaccion.equals("Cobro") || transaccion.equals("Pago")){
//                        estadoOperacion = "1";
//                        transaccion = "";
//                        
//                        if (activo==1 && rs.getInt("doc_cobros")==0 && rs.getInt("doc_pagos")==0){
//                            transaccion="Aplicacion";
//                        }
//                        
//                        if (activo==1 && rs.getInt("doc_cobros")==1){
//                            transaccion="Cobro";
//                        }
//                        
//                        if (activo==1 && rs.getInt("doc_pagos")==1){
//                            transaccion="Pago";
//                        }
//                    }else if (transaccion.equals("Reverso Cobro") || transaccion.equals("Reverso Pago") || transaccion.equals("Anular aplicacion")){
//                        estadoOperacion = "0";
//                        transaccion = "";
//                        
//                        if (activo==0 && rs.getInt("doc_cobros")==0 && rs.getInt("doc_pagos")==0){
//                            transaccion="Anular aplicacion";
//                        }
//                        
//                        if (activo==0 && rs.getInt("doc_cobros")==1){
//                            transaccion="Reverso Cobro";
//                        }
//                        
//                        if (activo==0 && rs.getInt("doc_pagos")==1){
//                            transaccion="Reverso Pago";
//                        }
//                    }
                    
                    //String query = "{CALL sp_asientos_contables(?, ?, ?, ?, ?, ?, ?)}";
                    if (!transaccion.equals("")){
//                        if (i==1){
                            query = "{CALL sp_asientos_contables('"+codEmpresa+"', '"+numDoc+"', '"+tipDoc+"', "+
                                                                "'"+transaccion+"', '"+etiqueta+"', '"+campo+"', "+
                                                                 estadoOperacion+", '"+fechaDesde+"', '"+fechaHasta+"')}";
                            stmt = conn.prepareCall(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    
//                          stmt.setString(1, codEmpresa);
//                          stmt.setString(2, numDoc);
//                          stmt.setString(3, tipDoc);
//                          stmt.setString(4, transaccion);
//                        stmt.setString(5, tipoMonto);
//                          stmt.setString(6, campo);
//                          stmt.setInt(7, activo);
                    
                            rsStoredProcedure = stmt.executeQuery();
//                            ResultSetToArray(rsStoredProcedure);
//                        }else{
//                            query = "{CALL sp_asientos_contables('"+codEmpresa+"', '"+numDoc+"', '"+tipDoc+"', "
//                                                                 + "'"+transaccion+"', '"+etiqueta+"', '"+campo+"', "+estadoOperacion+")}";
//                            
//                            stmt = conn.prepareCall(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
//                        
//                            rsIntermedio = stmt.executeQuery();
//                                
//                            rsStoredProcedure.moveToInsertRow();
//            
//                            rsStoredProcedure.updateString("inv_id", rsIntermedio.getString("inv_id"));
//                            rsStoredProcedure.updateString("comp_empresa", rsIntermedio.getString("comp_empresa"));
//                            rsStoredProcedure.updateString("ccont_transaccion", rsIntermedio.getString("ccont_transaccion"));
//                            rsStoredProcedure.updateString("inv_activo", rsIntermedio.getString("inv_activo"));
//                            rsStoredProcedure.updateString("comp_activo", rsIntermedio.getString("comp_activo"));
//                            rsStoredProcedure.updateString("inv_status_action", rsIntermedio.getString("inv_status_action"));
//                            rsStoredProcedure.updateString("cuenta", rsIntermedio.getString("cuenta"));
//                            rsStoredProcedure.updateString("cta_nombre", rsIntermedio.getString("cta_nombre"));
//                            rsStoredProcedure.updateString("Descripcion", rsIntermedio.getString("Descripcion"));
//                            rsStoredProcedure.updateString("inv_descripcion", rsIntermedio.getString("inv_descripcion"));
//                            rsStoredProcedure.updateString("cuentaDirecta", rsIntermedio.getString("cuentaDirecta"));
//                            rsStoredProcedure.updateString("saldoInicial", rsIntermedio.getString("saldoInicial"));
//                            rsStoredProcedure.updateString("Montos_Debe", rsIntermedio.getString("Montos_Debe"));
//                            rsStoredProcedure.updateString("Montos_Haber", rsIntermedio.getString("Montos_Haber"));
//                            rsStoredProcedure.updateString("inv_coddoc", rsIntermedio.getString("inv_coddoc"));
//                            rsStoredProcedure.updateString("doc_descri", rsIntermedio.getString("doc_descri"));
//                            rsStoredProcedure.updateString("inv_numdoc", rsIntermedio.getString("inv_numdoc"));
//                            rsStoredProcedure.updateString("ccont_debe_haber", rsIntermedio.getString("ccont_debe_haber"));
//                            rsStoredProcedure.updateString("compcontab", rsIntermedio.getString("compcontab"));
//                            rsStoredProcedure.updateString("fecha", rsIntermedio.getString("fecha"));
//                            rsStoredProcedure.updateString("Aprobado", rsIntermedio.getString("Aprobado"));
//                            rsStoredProcedure.updateString("doc_contabiliza", rsIntermedio.getString("doc_contabiliza"));
//                            rsStoredProcedure.updateString("signo", rsIntermedio.getString("signo"));
//                            rsStoredProcedure.updateString("cta_nivel", rsIntermedio.getString("cta_nivel"));
//                            rsStoredProcedure.updateString("cta_nivel_1", rsIntermedio.getString("cta_nivel_1"));
//                            rsStoredProcedure.updateString("cta_nivel_2", rsIntermedio.getString("cta_nivel_2"));
//                            rsStoredProcedure.updateString("cta_nombre_n2", rsIntermedio.getString("cta_nombre_n2"));
//                            rsStoredProcedure.updateString("cta_nivel_3", rsIntermedio.getString("cta_nivel_3"));
//                            rsStoredProcedure.updateString("cta_nivel_4", rsIntermedio.getString("cta_nivel_4"));
//                            rsStoredProcedure.updateString("cta_nivel_5", rsIntermedio.getString("cta_nivel_5"));
//                            rsStoredProcedure.updateString("cta_nivel_6", rsIntermedio.getString("cta_nivel_6"));
//                            rsStoredProcedure.updateString("cta_nivel_7", rsIntermedio.getString("cta_nivel_7"));
//                            rsStoredProcedure.updateString("cta_nivel_8", rsIntermedio.getString("cta_nivel_8"));
//                            rsStoredProcedure.updateString("codPersona", rsIntermedio.getString("codPersona"));
//                            rsStoredProcedure.updateString("nombPersona", rsIntermedio.getString("nombPersona"));
//                            rsStoredProcedure.updateString("doc_saldo_inicial", rsIntermedio.getString("doc_saldo_inicial"));
//                            rsStoredProcedure.updateString("ban_descri", rsIntermedio.getString("ban_descri"));
//            
//                            rsStoredProcedure.insertRow();
//                            rsStoredProcedure.moveToCurrentRow();
//                        
//                            rsIntermedio.close();
//                        }
//                    }
//                }
                
                rsStoredProcedure.last();
                //System.err.println("Registros: "+rs.getRow());
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(sp_asientos_contables.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            //try {
                //rs.close();
                //conn.close();
            //} catch (SQLException ex) {
            //    Logger.getLogger(sp_asientos_contables.class.getName()).log(Level.SEVERE, null, ex);
            //}
        }
        
        return rsStoredProcedure;
    }
    
    private Object[][] ResultSetToArray(ResultSet rs){
        Object obj[][]=null;
 
        try{
            rs.last();
 
            ResultSetMetaData rsmd = rs.getMetaData();
 
            int numCols = rsmd.getColumnCount();
            int numFils =rs.getRow();
 
            obj=new Object[numFils][numCols];
 
            rs.beforeFirst();
 
            while (rs.next()){
                for (int i=0;i<numCols;i++){
                    obj[j][i]=rs.getObject(i+1);
                }
                
                j++;
            }
        }catch(Exception e){
 
        }
 
        System.err.println(obj);
        return obj;
    }
}
