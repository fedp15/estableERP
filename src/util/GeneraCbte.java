package util;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneraCbte extends conexion{
    String cbte = "";
    ResultSet rs_cbte = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    
    public String generarNumeroCbte(String maestro, String numdoc, String tipdoc){
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        cbte = codigo.CodigoConsecutivo("comp_numero", "dncomprobante", 10, "WHERE comp_empresa='"+VarGlobales.getCodEmpresa()+"'");
        
        if(cbte == null){
            cbte = "0000000001";
        }
        return cbte;
    }
    
    public String getCbte(String maestro, String numdoc, String tipdoc){
        cbte = "";
        String sql = "SELECT * FROM dncomprobante WHERE comp_empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
                     "comp_docnum='"+numdoc+"' AND comp_tipodoc='"+tipdoc+"'";
        try {
            rs_cbte = consultar(sql);
            
            try {
                if(rs_cbte.getRow()>0){
                    cbte = rs_cbte.getString("comp_numero");
                }else{
//                CodigoConsecutivo codigo = new CodigoConsecutivo();
//        
//                cbte = codigo.CodigoConsecutivo("comp_numero", "dncomprobante", 10, "WHERE comp_empresa='"+VarGlobales.getCodEmpresa()+"'");
//                if(cbte == null){
//                    cbte = "0000000001";
//                }
                }
            } catch (SQLException ex) {
                Logger.getLogger(GeneraCbte.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    rs_cbte.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(GeneraCbte.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GeneraCbte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cbte;
    }
    
    public void creaCbte(String numero, String maestro,String numdoc,String tipdoc, String monto,
                         Boolean lActualizar, String numpag, String fecha){
        Date date = new Date();
        //String fecha = sdf.format(date);
        String sql;
        ResultSet rs_busca = null;
        
        String idComp = "";
        
        ResultSet rsIdPers;
        try {
            rsIdPers = consultar("SELECT pers_id FROM dnpersonas WHERE rif='"+maestro+"';");
            maestro = rsIdPers.getString("pers_id");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GeneraCbte.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            rs_busca = consultar("SELECT max(comp_id)+1 as id FROM dncomprobante");
            
            if(rs_busca.getRow()>0){
                idComp = rs_busca.getString("id");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GeneraCbte.class.getName()).log(Level.SEVERE, null, ex);
            idComp = "1";
        }finally{
            try {
                rs_busca.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(GeneraCbte.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        if(lActualizar){
            sql = "SELECT * FROM dncomprobante WHERE comp_empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
                  "comp_numero='"+numero+"'";
                    
            try {
                rs_busca = consultar(sql);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GeneraCbte.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if(rs_busca.getRow()>0){
                    String monto_t = String.valueOf(rs_busca.getDouble("comp_monto_total")+Double.valueOf(monto));
                    
                    sql = "UPDATE dncomprobante SET comp_monto_total="+monto_t+" WHERE "+
                          "comp_empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
                          "comp_numero='"+numero+"'";
                    
                    System.err.println("Actualizar Cbte: "+sql);
                    SQLQuerys update = new SQLQuerys();
                    update.SqlUpdate(sql);
                }else{
                    if(maestro.equals("")){
                        sql = "INSERT INTO dncomprobante (comp_id,comp_empresa,comp_numero,pers_id,comp_tipodoc,comp_docnum,"+
                              "comp_fecha,comp_monto_total,comp_status,comp_activo) "+
                              "VALUES "+
                              "("+idComp+",'"+VarGlobales.getCodEmpresa()+"','"+numero+"',null,"+
                              "'"+tipdoc+"','"+numdoc+"',"+
                              "'"+fecha+"',"+monto+",0,1)";
                    }else{
                        sql = "INSERT INTO dncomprobante (comp_id,comp_empresa,comp_numero,pers_id,comp_tipodoc,comp_docnum,"+
                              "comp_fecha,comp_monto_total,comp_status,comp_activo) "+
                              "VALUES "+
                              "("+idComp+",'"+VarGlobales.getCodEmpresa()+"','"+numero+"','"+maestro+"',"+
                              "'"+tipdoc+"','"+numdoc+"',"+
                              "'"+fecha+"',"+monto+",0,1)";
                    }
                    
                    System.err.println("Insertar Cbte: "+sql);
                    SQLQuerys insertar = new SQLQuerys();
                    insertar.SqlInsert(sql);
                }
            } catch (SQLException ex) {
                Logger.getLogger(GeneraCbte.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    rs_busca.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(GeneraCbte.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else{
            if(!numpag.equals("")){
                if(maestro.equals("")){
                    sql = "INSERT INTO dncomprobante (comp_id,comp_empresa,comp_numero,pers_id,comp_tipodoc,comp_docnum,"+
                                "comp_fecha,comp_monto_total,comp_status,comp_activo,comp_pagoid) "+
                                "VALUES "+
                                "("+idComp+",'"+VarGlobales.getCodEmpresa()+"',null,'"+maestro+"',"+
                                "'"+tipdoc+"','"+numdoc+"',"+
                                "'"+fecha+"',"+monto+",0,1,"+numpag+")";
                }else{
                    sql = "INSERT INTO dncomprobante (comp_id,comp_empresa,comp_numero,pers_id,comp_tipodoc,comp_docnum,"+
                                "comp_fecha,comp_monto_total,comp_status,comp_activo,comp_pagoid) "+
                                "VALUES "+
                                "("+idComp+",'"+VarGlobales.getCodEmpresa()+"','"+numero+"','"+maestro+"',"+
                                "'"+tipdoc+"','"+numdoc+"',"+
                                "'"+fecha+"',"+monto+",0,1,"+numpag+")";
                }
            }else{
                if(maestro.equals("")){
                    sql = "INSERT INTO dncomprobante (comp_id,comp_empresa,comp_numero,pers_id,comp_tipodoc,comp_docnum,"+
                                "comp_fecha,comp_pagoid,comp_monto_total,comp_status,comp_activo) "+
                                "VALUES "+
                                "("+idComp+",'"+VarGlobales.getCodEmpresa()+"','"+numero+"',null,"+
                                "'"+tipdoc+"','"+numdoc+"',"+
                                "'"+fecha+"',0,"+monto+",0,1)";
                }else{
                    sql = "INSERT INTO dncomprobante (comp_id,comp_empresa,comp_numero,pers_id,comp_tipodoc,comp_docnum,"+
                                "comp_fecha,comp_pagoid,comp_monto_total,comp_status,comp_activo) "+
                                "VALUES "+
                                "("+idComp+",'"+VarGlobales.getCodEmpresa()+"','"+numero+"','"+maestro+"',"+
                                "'"+tipdoc+"','"+numdoc+"',"+
                                "'"+fecha+"',0,"+monto+",0,1)";
                }
            }
            SQLQuerys insertar = new SQLQuerys();
            insertar.SqlInsert(sql);
        }
    }
    
    public void updateCbte(String numero, String monto){
        String sql = "UPDATE dncomprobante SET comp_monto_total="+monto+" "+
                     "WHERE "+
                     "comp_empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
                     "comp_numero='"+numero+"'";
        SQLQuerys update = new SQLQuerys();
        update.SqlUpdate(sql);
    }
    
    public void updateDocCbte(String cliente, String numdoc, String tipdoc,String cbte,int orden){
        String sql;
        
        if(cliente.equals("")){
            sql = "UPDATE dninventario SET inv_compcontab='"+cbte+"' "+
                    "WHERE inv_empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
                    "inv_numdoc='"+numdoc+"' AND "+
                    "inv_coddoc='"+tipdoc+"' AND inv_orden="+orden+"";
        }else{
            sql = "UPDATE dninventario SET inv_compcontab='"+cbte+"' "+
                    "WHERE inv_empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
                    "pers_id='"+cliente+"' AND inv_numdoc='"+numdoc+"' AND "+
                    "inv_coddoc='"+tipdoc+"' AND inv_orden="+orden+"";
        }        
        System.err.println("Actualizando DocCbte: "+sql);
        SQLQuerys act_cbte = new SQLQuerys();
        act_cbte.SqlUpdate(sql);
    }
    
    public void updatePagCbte(String cliente, String numdoc, String tipdoc,String cbte, String id){
        String sql = "UPDATE dnpagos SET pag_cbte='"+cbte+"' "+
                     "WHERE pag_empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
                     "id_pers='"+cliente+"' AND pag_numfac='"+numdoc+"' AND "+
                     "pag_coddoc='"+tipdoc+"' AND pag_id="+id+"";
        
        System.err.println("Actualizando PagoCbte: "+sql);
        SQLQuerys act_cbte = new SQLQuerys();
        act_cbte.SqlUpdate(sql);
    }
    
    public Double getMontoDoc(String maestro, String numdoc, String tipdoc, int orden){
        String sql = ""; 
        ResultSet rs = null;
        if(maestro.equals("")){        
            sql = "SELECT SUM((inv_precio*inv_cantid*inv_canund)+inv_mtoiva) AS TOTAL FROM dninventario "+
                    "WHERE inv_empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
                    "inv_numdoc='"+numdoc+"' AND "+
                    "inv_coddoc='"+tipdoc+"' AND inv_orden="+orden+"";
        }else{
            sql = "SELECT SUM((inv_precio*inv_cantid*inv_canund)+inv_mtoiva) AS TOTAL FROM dninventario "+
                    "WHERE inv_empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
                    "pers_id='"+maestro+"' AND inv_numdoc='"+numdoc+"' AND "+
                    "inv_coddoc='"+tipdoc+"' AND inv_orden="+orden+"";
        }
        Double monto = 0.00;
        System.err.println("Monto: "+sql);
        try {
            rs = consultar(sql);
            try {
                if(rs.getRow()>0){
                    monto = rs.getDouble("TOTAL");
                }
            } catch (SQLException ex) {
                Logger.getLogger(GeneraCbte.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    rs.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(GeneraCbte.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GeneraCbte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return monto;
    }
}
