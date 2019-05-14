package util;

import Modelos.VariablesGlobales;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricardo
 */
public class CrearVistaAsientosContables extends Modelos.conexion{
    private ResultSet rs = null, rsCampos = null;
    private String SQL, construyeVista;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private SQLQuerys insertarconex = null;

    public CrearVistaAsientosContables() {
        insertarconex = new SQLQuerys();
    }
    
    public void armarVista(){
        try {
            int orden = 1;

            SQL = "select etiqueta,ccont_transaccion,nomb_camp from maestro_tablas_campos \n" +
                  "inner join dnconfig_contable on ccont_monto=etiqueta\n" +
                  "group by etiqueta,ccont_transaccion\n" +
                  "union\n" +
                  "select nombre as etiqueta,ccont_transaccion,formula as nomb_camp from formula \n" +
                  "inner join dnconfig_contable on ccont_monto=nombre\n" +
                  "group by nombre,ccont_transaccion\n" +
                  "order by ccont_transaccion";
            System.out.println(SQL);
            
            //cargatabla.cargatablas(tabla,SQL,columnas);
            rs = consultar(SQL);
            
            if(rs.getRow()>0){
                rs.beforeFirst();
                
                int i = 0;
                while(rs.next()){
                    i++;
                        
                    String etiqueta = rs.getString("etiqueta");
                    String transaccion = rs.getString("ccont_transaccion");
                    String campo = rs.getString("nomb_camp");
                    String estadoOperacion = "";
                        
                    if (transaccion.equals("Aplicacion") || transaccion.equals("Cobro") || transaccion.equals("Pago")){
                        estadoOperacion = "1";
                    }else if (transaccion.equals("Reverso Cobro") || transaccion.equals("Reverso Pago") || transaccion.equals("Anular aplicacion")){
                        estadoOperacion = "0";
                    }
                     
                    if (i==1){
                        construyeVista = "select inv_id,inv_empresa as comp_empresa,\n" +
                                         "	 (SELECT ccont_transaccion FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "               and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa) as ccont_transaccion,\n" +
                                         "	 inv_activo,(SELECT comp_activo FROM dncomprobante WHERE comp_numero=inv_compcontab and comp_empresa=inv_empresa and comp_tipodoc=inv_coddoc and \n" +
                                         "		            (comp_empresa IS NOT NULL and inv_empresa IS NOT NULL)) as comp_activo,\n" +
                                         "	 inv_status_action,\n" +
                                         "	 (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "               and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa) as cuenta,\n" +
                                         "       (SELECT cta_nombre FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "               and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa) as cta_nombre,\n" +
                                         "		 '"+etiqueta+"' as Descripcion, \n" +
                                         "		 inv_descripcion,\n" +
                                         "	 if(ISNULL(inv_cuenta), '', inv_cuenta) as cuentaDirecta,\n" +
                                         "	 if((SELECT doc_saldo_inicial FROM dndocumentos WHERE doc_codigo=inv_coddoc and doc_empresa=inv_empresa)=1,"+campo+",0) as saldoInicial,\n" +
                                         "   	 if((SELECT doc_saldo_inicial FROM dndocumentos WHERE doc_codigo=inv_coddoc and doc_empresa=inv_empresa)=0 and (SELECT ccont_debe_haber FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)='Debe',SUM("+campo+"), 0) as Montos_Debe, \n" +
                                         "	 if((SELECT doc_saldo_inicial FROM dndocumentos WHERE doc_codigo=inv_coddoc and doc_empresa=inv_empresa)=0 and (SELECT ccont_debe_haber FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)='Haber', SUM("+campo+"), 0)as Montos_Haber,\n" +
                                         "     	 inv_coddoc,\n" +
                                         "     	 (SELECT doc_descri FROM  dndocumentos WHERE doc_codigo=inv_coddoc and doc_empresa=inv_empresa) as doc_descri,\n" +
                                         "		 inv_numdoc,\n" +
                                         "	 (SELECT ccont_debe_haber FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "               and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa) as ccont_debe_haber, \n" +
                                         "	         inv_compcontab as compcontab,\n" +
                                         "	 (SELECT comp_fecha FROM dncomprobante WHERE comp_numero=inv_compcontab and comp_empresa=inv_empresa and comp_tipodoc=inv_coddoc and \n" +
                                         "		 (comp_empresa IS NOT NULL and inv_empresa IS NOT NULL)) as comp_fecha, \n" +
                                         "	 (SELECT comp_status FROM dncomprobante WHERE comp_numero=inv_compcontab and comp_empresa=inv_empresa and comp_tipodoc=inv_coddoc and \n" +
                                         "		 (comp_empresa IS NOT NULL and inv_empresa IS NOT NULL)) as Aprobado, \n" +
                                         "	 (SELECT doc_contabiliza FROM dndocumentos WHERE doc_codigo=inv_coddoc and doc_empresa=inv_empresa) as doc_contabiliza,\n" +
                                         "       if((SELECT ccont_debe_haber FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)='Debe', \n" +
                                         "			if((SELECT cta_debe FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                                 and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)='+', 1, -1), \n" +
                                         "       if((SELECT cta_haber FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)='-',-1,1)) as signo,\n" +
                                         "	 (SELECT cta_nivel FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "               and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa) as cta_nivel, \n" +
                                         "   	 SUBSTRING((SELECT cta_nivel_2 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                         and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa), 1,1) as cta_nivel_1, \n" +
                                         "	 (SELECT cta_nivel_2 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "               and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa) as cta_nivel_2, \n" +
                                         "       if((SELECT cta_nivel_2 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)='', '', \n" +
                                         "			  (select cta_nombre from dncta where cta_codigo=(SELECT cta_nivel_2 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                                and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa))) as cta_nombre_n2, \n" +
                                         "       if(ISNULL((SELECT cta_nivel_3 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)),'',\n" +
                                         "			  (SELECT cta_nivel_3 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                                and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)) as cta_nivel_3, \n" +
                                         "	 if(ISNULL((SELECT cta_nivel_4 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)),'',\n" +
                                         "			  (SELECT cta_nivel_4 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                                and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)) as cta_nivel_4, \n" +
                                         "	 if(ISNULL((SELECT cta_nivel_5 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)),'',\n" +
                                         "			  (SELECT cta_nivel_5 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                                and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)) as cta_nivel_5,\n" +
                                         "       if(ISNULL((SELECT cta_nivel_6 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='TOTAL' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)),'',\n" +
                                         "			  (SELECT cta_nivel_6 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                                and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)) as cta_nivel_6, \n" +
                                         "       if(ISNULL((SELECT cta_nivel_7 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)),'',\n" +
                                         "			  (SELECT cta_nivel_7 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                                and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)) as cta_nivel_7, \n" +
                                         "	 if(ISNULL((SELECT cta_nivel_8 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)),'',\n" +
                                         "       		  (SELECT cta_nivel_8 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                                and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)) as cta_nivel_8,\n" +
                                         "   	 (SELECT codigo FROM dnpersonas WHERE dnpersonas.pers_id=dninventario.pers_id) as codPersona,\n" +
                                         "	 (SELECT razon_social FROM dnpersonas WHERE dnpersonas.pers_id=dninventario.pers_id) as nombPersona,\n" +
                                         "	 (SELECT doc_saldo_inicial FROM dndocumentos WHERE doc_codigo=inv_coddoc and doc_empresa=inv_empresa) as doc_saldo_inicial,\n" +
                                         "	 if(ISNULL((SELECT ban_descri FROM dnbancos WHERE ban_codigo=(SELECT pag_banco FROM dnpagos WHERE pag_compcontab=inv_compcontab and pag_empresa=inv_empresa) and ban_empresa=inv_empresa)),\n" +
                                         "		    '', (SELECT ban_descri FROM dnbancos WHERE ban_codigo=(SELECT pag_banco FROM dnpagos WHERE pag_compcontab=inv_compcontab and pag_empresa=inv_empresa) and ban_empresa=inv_empresa)) as ban_descri\n" +
                                         "from dninventario \n" +
                                         "where inv_activo="+estadoOperacion+" and ISNULL(inv_cuenta)\n" +
                                         "group by inv_id";
/*                      
                        construyeVista = "select inv_id,comp_empresa,ccont_transaccion,inv_activo,comp_activo,inv_status_action,ccont_cuenta as cuenta,cta_nombre,'"+etiqueta+"' as Descripcion,inv_descripcion, \n"+
                                         "       if(ISNULL(inv_cuenta), '', inv_cuenta) as cuentaDirecta,if(doc_saldo_inicial=1,"+campo+",0) as saldoInicial,\n" +
                                         "       if(doc_saldo_inicial=0 and ccont_debe_haber='Debe',SUM("+campo+"), 0) as Montos_Debe, if(doc_saldo_inicial=0 and ccont_debe_haber='Haber', SUM("+campo+"), 0)as Montos_Haber,\n" +
                                         "	 inv_coddoc,doc_descri,inv_numdoc,ccont_debe_haber,inv_compcontab as compcontab, comp_fecha, comp_status as Aprobado, doc_contabiliza,\n"+
                                         "       if(ccont_debe_haber='Debe', if(cta_debe='+', 1, -1), if(cta_haber='-',-1,1)) as signo, cta_nivel, \n"+
                                         "       SUBSTRING(cta_nivel_2, 1,1) as cta_nivel_1, cta_nivel_2, if(cta_nivel_2='', '', (select cta_nombre from dncta where cta_codigo=cta_nivel_2)) as cta_nombre_n2, \n"+
                                         "       if(ISNULL(cta_nivel_3),'',cta_nivel_3) as cta_nivel_3, if(ISNULL(cta_nivel_4),'',cta_nivel_4) as cta_nivel_4, if(ISNULL(cta_nivel_5),'',cta_nivel_5) as cta_nivel_5,\n"+
                                         "       if(ISNULL(cta_nivel_6),'',cta_nivel_6) as cta_nivel_6, if(ISNULL(cta_nivel_7),'',cta_nivel_7) as cta_nivel_7, if(ISNULL(cta_nivel_8),'',cta_nivel_8) as cta_nivel_8,\n"+
                                         "       dnpersonas.codigo as codPersona,razon_social as nombPersona,doc_saldo_inicial,if(ISNULL(ban_descri), '', ban_descri) as ban_descri from dninventario \n" +
                                         "inner join dnconfig_contable on ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                                and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa\n"+
                                         "inner join dndocumentos on doc_codigo=inv_coddoc and doc_empresa=inv_empresa\n" +
                                         "inner join dncomprobante on comp_numero=inv_compcontab and comp_empresa=inv_empresa\n" +
                                         "inner join dncta on ccont_cuenta=cta_codigo and cta_empresa=inv_empresa\n"+
                                         "left join dnpersonas on dnpersonas.pers_id=dninventario.pers_id\n"+
                                         "left join dnpagos on pag_compcontab=inv_compcontab and pag_empresa=inv_empresa\n" +
                                         "left join dnbancos on dnbancos.ban_codigo=pag_banco and ban_empresa=inv_empresa\n"+
                                         "where inv_activo="+estadoOperacion+" and ISNULL(inv_cuenta)\n" +
                                         "group by inv_id";
*/
                    }else{
                        construyeVista = construyeVista+"\n union \n";

                        construyeVista = construyeVista+"select inv_id,inv_empresa as comp_empresa,\n" +
                                         "	 (SELECT ccont_transaccion FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "               and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa) as ccont_transaccion,\n" +
                                         "	 inv_activo,(SELECT comp_activo FROM dncomprobante WHERE comp_numero=inv_compcontab and comp_empresa=inv_empresa and comp_tipodoc=inv_coddoc and \n" +
                                         "		            (comp_empresa IS NOT NULL and inv_empresa IS NOT NULL)) as comp_activo,\n" +
                                         "	 inv_status_action,\n" +
                                         "	 (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "               and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa) as cuenta,\n" +
                                         "       (SELECT cta_nombre FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "               and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa) as cta_nombre,\n" +
                                         "		 '"+etiqueta+"' as Descripcion, \n" +
                                         "		 inv_descripcion,\n" +
                                         "	 if(ISNULL(inv_cuenta), '', inv_cuenta) as cuentaDirecta,\n" +
                                         "	 if((SELECT doc_saldo_inicial FROM dndocumentos WHERE doc_codigo=inv_coddoc and doc_empresa=inv_empresa)=1,"+campo+",0) as saldoInicial,\n" +
                                         "   	 if((SELECT doc_saldo_inicial FROM dndocumentos WHERE doc_codigo=inv_coddoc and doc_empresa=inv_empresa)=0 and (SELECT ccont_debe_haber FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)='Debe',SUM("+campo+"), 0) as Montos_Debe, \n" +
                                         "	 if((SELECT doc_saldo_inicial FROM dndocumentos WHERE doc_codigo=inv_coddoc and doc_empresa=inv_empresa)=0 and (SELECT ccont_debe_haber FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)='Haber', SUM("+campo+"), 0)as Montos_Haber,\n" +
                                         "     	 inv_coddoc,\n" +
                                         "     	 (SELECT doc_descri FROM  dndocumentos WHERE doc_codigo=inv_coddoc and doc_empresa=inv_empresa) as doc_descri,\n" +
                                         "		 inv_numdoc,\n" +
                                         "	 (SELECT ccont_debe_haber FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "               and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa) as ccont_debe_haber, \n" +
                                         "	         inv_compcontab as compcontab,\n" +
                                         "	 (SELECT comp_fecha FROM dncomprobante WHERE comp_numero=inv_compcontab and comp_empresa=inv_empresa and comp_tipodoc=inv_coddoc and \n" +
                                         "		 (comp_empresa IS NOT NULL and inv_empresa IS NOT NULL)) as comp_fecha, \n" +
                                         "	 (SELECT comp_status FROM dncomprobante WHERE comp_numero=inv_compcontab and comp_empresa=inv_empresa and comp_tipodoc=inv_coddoc and \n" +
                                         "		 (comp_empresa IS NOT NULL and inv_empresa IS NOT NULL)) as Aprobado, \n" +
                                         "	 (SELECT doc_contabiliza FROM dndocumentos WHERE doc_codigo=inv_coddoc and doc_empresa=inv_empresa) as doc_contabiliza,\n" +
                                         "       if((SELECT ccont_debe_haber FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)='Debe', \n" +
                                         "			if((SELECT cta_debe FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                                 and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)='+', 1, -1), \n" +
                                         "       if((SELECT cta_haber FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)='-',-1,1)) as signo,\n" +
                                         "	 (SELECT cta_nivel FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "               and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa) as cta_nivel, \n" +
                                         "   	 SUBSTRING((SELECT cta_nivel_2 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                         and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa), 1,1) as cta_nivel_1, \n" +
                                         "	 (SELECT cta_nivel_2 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "               and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa) as cta_nivel_2, \n" +
                                         "       if((SELECT cta_nivel_2 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)='', '', \n" +
                                         "			  (select cta_nombre from dncta where cta_codigo=(SELECT cta_nivel_2 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                                and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa))) as cta_nombre_n2, \n" +
                                         "       if(ISNULL((SELECT cta_nivel_3 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)),'',\n" +
                                         "			  (SELECT cta_nivel_3 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                                and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)) as cta_nivel_3, \n" +
                                         "	 if(ISNULL((SELECT cta_nivel_4 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)),'',\n" +
                                         "			  (SELECT cta_nivel_4 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                                and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)) as cta_nivel_4, \n" +
                                         "	 if(ISNULL((SELECT cta_nivel_5 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)),'',\n" +
                                         "			  (SELECT cta_nivel_5 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                                and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)) as cta_nivel_5,\n" +
                                         "       if(ISNULL((SELECT cta_nivel_6 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='TOTAL' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)),'',\n" +
                                         "			  (SELECT cta_nivel_6 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                                and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)) as cta_nivel_6, \n" +
                                         "       if(ISNULL((SELECT cta_nivel_7 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)),'',\n" +
                                         "			  (SELECT cta_nivel_7 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                                and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)) as cta_nivel_7, \n" +
                                         "	 if(ISNULL((SELECT cta_nivel_8 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                  and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)),'',\n" +
                                         "       		  (SELECT cta_nivel_8 FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                         "                                and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa)) as cta_nivel_8,\n" +
                                         "   	 (SELECT codigo FROM dnpersonas WHERE dnpersonas.pers_id=dninventario.pers_id) as codPersona,\n" +
                                         "	 (SELECT razon_social FROM dnpersonas WHERE dnpersonas.pers_id=dninventario.pers_id) as nombPersona,\n" +
                                         "	 (SELECT doc_saldo_inicial FROM dndocumentos WHERE doc_codigo=inv_coddoc and doc_empresa=inv_empresa) as doc_saldo_inicial,\n" +
                                         "	 if(ISNULL((SELECT ban_descri FROM dnbancos WHERE ban_codigo=(SELECT pag_banco FROM dnpagos WHERE pag_compcontab=inv_compcontab and pag_empresa=inv_empresa) and ban_empresa=inv_empresa)),\n" +
                                         "		    '', (SELECT ban_descri FROM dnbancos WHERE ban_codigo=(SELECT pag_banco FROM dnpagos WHERE pag_compcontab=inv_compcontab and pag_empresa=inv_empresa) and ban_empresa=inv_empresa)) as ban_descri\n" +
                                         "from dninventario \n" +
                                         "where inv_activo="+estadoOperacion+" and ISNULL(inv_cuenta)\n" +
                                         "group by inv_id";
/*
                        construyeVista = construyeVista+"select inv_id,comp_empresa,ccont_transaccion,inv_activo,comp_activo,inv_status_action,ccont_cuenta as cuenta,cta_nombre,"+
                                                        "'"+etiqueta+"' as Descripcion, inv_descripcion,if(ISNULL(inv_cuenta), '', inv_cuenta) as cuentaDirecta,if(doc_saldo_inicial=1,"+campo+",0) as saldoInicial,\n" +
                                                        "       if(doc_saldo_inicial=0 and ccont_debe_haber='Debe',SUM("+campo+"), 0) as Montos_Debe, if(doc_saldo_inicial=0 and ccont_debe_haber='Haber', SUM("+campo+"), 0)as Montos_Haber,\n" +
                                                        "       inv_coddoc,doc_descri,inv_numdoc,ccont_debe_haber, \n" +
                                                        "       inv_compcontab as compcontab, comp_fecha, comp_status as Aprobado, doc_contabiliza,\n"+
                                                        "       if(ccont_debe_haber='Debe', if(cta_debe='+', 1, -1), if(cta_haber='-',-1,1)) as signo, cta_nivel, \n"+
                                                        "       SUBSTRING(cta_nivel_2, 1,1) as cta_nivel_1, cta_nivel_2, if(cta_nivel_2='', '', (select cta_nombre from dncta where cta_codigo=cta_nivel_2)) as cta_nombre_n2, \n"+
                                                        "       if(ISNULL(cta_nivel_3),'',cta_nivel_3) as cta_nivel_3, if(ISNULL(cta_nivel_4),'',cta_nivel_4) as cta_nivel_4, if(ISNULL(cta_nivel_5),'',cta_nivel_5) as cta_nivel_5,\n"+
                                                        "       if(ISNULL(cta_nivel_6),'',cta_nivel_6) as cta_nivel_6, if(ISNULL(cta_nivel_7),'',cta_nivel_7) as cta_nivel_7, if(ISNULL(cta_nivel_8),'',cta_nivel_8) as cta_nivel_8,\n"+
                                                        "       dnpersonas.codigo as codPersona,razon_social as nombPersona,doc_saldo_inicial,if(ISNULL(ban_descri), '', ban_descri) as ban_descri from dninventario \n" +
                                                        "inner join dnconfig_contable on ccont_documento=inv_coddoc and ccont_transaccion='"+transaccion+"'\n" +
                                                        "                                and ccont_monto='"+etiqueta+"' and ccont_empresa=inv_empresa\n"+
                                                        "inner join dndocumentos on doc_codigo=inv_coddoc and doc_empresa=inv_empresa\n" +
                                                        "inner join dncomprobante on comp_numero=inv_compcontab and comp_empresa=inv_empresa\n" +
                                                        "inner join dncta on ccont_cuenta=cta_codigo and cta_empresa=inv_empresa\n"+
                                                        "left join dnpersonas on dnpersonas.pers_id=dninventario.pers_id\n"+
                                                        "left join dnpagos on pag_compcontab=inv_compcontab and pag_empresa=inv_empresa\n" +
                                                        "left join dnbancos on dnbancos.ban_codigo=pag_banco and ban_empresa=inv_empresa\n"+
                                                        "where inv_activo="+estadoOperacion+" and ISNULL(inv_cuenta)\n" +
                                                        "group by inv_id";
*/
                    }
                }
            
                rs = consultar("select * from dninventario where (inv_cuenta<>'' or inv_cuenta<>null)");
            
                if(rs.getRow()>0){
                    construyeVista = construyeVista+"\n union \n";
                    
                    construyeVista = construyeVista+"select inv_id,inv_empresa as comp_empresa,'Aplicacion' as ccont_transaccion,inv_activo,\n" +
                                     " 		 (SELECT comp_activo FROM dncomprobante WHERE comp_numero=inv_compcontab and comp_empresa=inv_empresa and comp_tipodoc=inv_coddoc and \n" +
                                     "		         (comp_empresa IS NOT NULL and inv_empresa IS NOT NULL)) as comp_activo,\n" +
                                     "		 inv_status_action,inv_cuenta as cuenta,\n" +
                                     "       (SELECT cta_nombre FROM dncta WHERE (SELECT ccont_cuenta FROM dnconfig_contable WHERE ccont_documento=inv_coddoc and ccont_transaccion='Aplicacion'\n" +
                                     "               and ccont_monto='TOTAL' and ccont_empresa=inv_empresa)=cta_codigo and cta_empresa=inv_empresa) as cta_nombre,\n" +
                                     "	     inv_descripcion as Descripcion,inv_descripcion,if(ISNULL(inv_cuenta), '', inv_cuenta) as cuentaDirecta,\n" +
                                     "	     if((SELECT doc_saldo_inicial FROM dndocumentos WHERE doc_codigo=inv_coddoc and doc_empresa=inv_empresa)=1,inv_total,0) as saldoInicial,\n" +
                                     "       if(inv_debe_haber=0 and (SELECT doc_saldo_inicial FROM dndocumentos WHERE doc_codigo=inv_coddoc and doc_empresa=inv_empresa)=0, inv_total, 0) as Montos_Debe, \n" +
                                     "       if(inv_debe_haber=1, inv_total, 0)as Montos_Haber,inv_coddoc,\n" +
                                     "       (SELECT doc_descri FROM  dndocumentos WHERE doc_codigo=inv_coddoc and doc_empresa=inv_empresa) as doc_descri,\n" +
                                     "       inv_numdoc,\n" +
                                     "       if(inv_debe_haber=0 and (SELECT doc_saldo_inicial FROM dndocumentos WHERE doc_codigo=inv_coddoc and doc_empresa=inv_empresa)=0, 'Debe', 'Haber') as ccont_debe_haber, \n" +
                                     "       inv_compcontab as compcontab,\n" +
                                     "       (SELECT comp_fecha FROM dncomprobante WHERE comp_numero=inv_compcontab and comp_empresa=inv_empresa and comp_tipodoc=inv_coddoc and \n" +
                                     "	             (comp_empresa IS NOT NULL and inv_empresa IS NOT NULL)) as comp_fecha, \n" +
                                     "       (SELECT comp_status FROM dncomprobante WHERE comp_numero=inv_compcontab and comp_empresa=inv_empresa and comp_tipodoc=inv_coddoc and \n" +
                                     "               (comp_empresa IS NOT NULL and inv_empresa IS NOT NULL)) as Aprobado, \n" +
                                     "	     (SELECT doc_contabiliza FROM dndocumentos WHERE doc_codigo=inv_coddoc and doc_empresa=inv_empresa) as doc_contabiliza,\n" +
                                     "       if(inv_debe_haber=0, if((SELECT cta_debe FROM dncta WHERE inv_cuenta=cta_codigo and cta_empresa=inv_empresa)='+', 1, -1), \n" +
                                     "				  if((SELECT cta_haber FROM dncta WHERE inv_cuenta=cta_codigo and cta_empresa=inv_empresa)='-',-1,1)) as signo,\n" +
                                     "	     (SELECT cta_nivel FROM dncta WHERE inv_cuenta=cta_codigo and cta_empresa=inv_empresa) as cta_nivel, \n" +
                                     "       SUBSTRING((SELECT cta_nivel_2 FROM dncta WHERE inv_cuenta=cta_codigo and cta_empresa=inv_empresa), 1,1) as cta_nivel_1, \n" +
                                     "       (SELECT cta_nivel_2 FROM dncta WHERE inv_cuenta=cta_codigo and cta_empresa=inv_empresa) as cta_nivel_2, \n" +
                                     "       if((SELECT cta_nivel_2 FROM dncta WHERE inv_cuenta=cta_codigo and cta_empresa=inv_empresa)='', '', (select cta_nombre from dncta where cta_codigo=inv_cuenta)) as cta_nombre_n2, \n" +
                                     "       if(ISNULL((SELECT cta_nivel_3 FROM dncta WHERE inv_cuenta=cta_codigo and cta_empresa=inv_empresa)),'',\n" +
                                     "		      (SELECT cta_nivel_3 FROM dncta WHERE inv_cuenta=cta_codigo and cta_empresa=inv_empresa)) as cta_nivel_3, \n" +
                                     "       if(ISNULL((SELECT cta_nivel_4 FROM dncta WHERE inv_cuenta=cta_codigo and cta_empresa=inv_empresa)),'',\n" +
                                     "                (SELECT cta_nivel_4 FROM dncta WHERE inv_cuenta=cta_codigo and cta_empresa=inv_empresa)) as cta_nivel_4, \n" +
                                     "       if(ISNULL((SELECT cta_nivel_5 FROM dncta WHERE inv_cuenta=cta_codigo and cta_empresa=inv_empresa)),'',\n" +
                                     "		      (SELECT cta_nivel_5 FROM dncta WHERE inv_cuenta=cta_codigo and cta_empresa=inv_empresa)) as cta_nivel_5,\n" +
                                     "       if(ISNULL((SELECT cta_nivel_6 FROM dncta WHERE inv_cuenta=cta_codigo and cta_empresa=inv_empresa)),'',\n" +
                                     "		      (SELECT cta_nivel_6 FROM dncta WHERE inv_cuenta=cta_codigo and cta_empresa=inv_empresa)) as cta_nivel_6, \n" +
                                     "       if(ISNULL((SELECT cta_nivel_7 FROM dncta WHERE inv_cuenta=cta_codigo and cta_empresa=inv_empresa)),'',\n" +
                                     "		      (SELECT cta_nivel_7 FROM dncta WHERE inv_cuenta=cta_codigo and cta_empresa=inv_empresa)) as cta_nivel_7, \n" +
                                     "       if(ISNULL((SELECT cta_nivel_8 FROM dncta WHERE inv_cuenta=cta_codigo and cta_empresa=inv_empresa)),'',\n" +
                                     "		      (SELECT cta_nivel_8 FROM dncta WHERE inv_cuenta=cta_codigo and cta_empresa=inv_empresa)) as cta_nivel_8,\n" +
                                     "       (SELECT codigo FROM dnpersonas WHERE dnpersonas.pers_id=dninventario.pers_id) as codPersona,\n" +
                                     "       (SELECT razon_social FROM dnpersonas WHERE dnpersonas.pers_id=dninventario.pers_id) as nombPersona,\n" +
                                     "       (SELECT doc_saldo_inicial FROM dndocumentos WHERE doc_codigo=inv_coddoc and doc_empresa=inv_empresa) as doc_saldo_inicial,\n" +
                                     "       if(ISNULL((SELECT ban_descri FROM dnbancos WHERE ban_codigo=(SELECT pag_banco FROM dnpagos WHERE pag_compcontab=inv_compcontab and pag_empresa=inv_empresa) and ban_empresa=inv_empresa)),\n" +
                                     "		       '', (SELECT ban_descri FROM dnbancos WHERE ban_codigo=(SELECT pag_banco FROM dnpagos WHERE pag_compcontab=inv_compcontab and pag_empresa=inv_empresa) and ban_empresa=inv_empresa)) as ban_descri\n" +
                                     "from dninventario \n" +
                                     "where inv_activo=1 and inv_cuenta IS NOT NULL\n" +
                                     "group by inv_id;";
/*
                    construyeVista = construyeVista+"select inv_id,comp_empresa,'Aplicacion' as ccont_transaccion,inv_activo,comp_activo,inv_status_action,inv_cuenta as cuenta,cta_nombre,inv_descripcion as Descripcion, \n" +
                                                    "       inv_descripcion,if(ISNULL(inv_cuenta), '', inv_cuenta) as cuentaDirecta,if(doc_saldo_inicial=1,inv_total,0) as saldoInicial,\n" +
                                                    "       if(inv_debe_haber=0 and doc_saldo_inicial=0, inv_total, 0) as Montos_Debe,if(inv_debe_haber=1, inv_total, 0)as Montos_Haber,inv_coddoc,doc_descri,inv_numdoc,\n" +
                                                    "       if(inv_debe_haber=0 and doc_saldo_inicial=0, 'Debe', 'Haber') as ccont_debe_haber, \n" +
                                                    "       inv_compcontab as compcontab, comp_fecha, comp_status as Aprobado, doc_contabiliza,\n" +
                                                    "       if(inv_debe_haber=0, if(cta_debe='+', 1, -1), if(cta_haber='-',-1,1)) as signo, cta_nivel, \n" +
                                                    "       SUBSTRING(cta_nivel_2, 1,1) as cta_nivel_1, cta_nivel_2, if(cta_nivel_2='', '', (select cta_nombre from dncta where cta_codigo=cta_nivel_2)) as cta_nombre_n2, \n"+
                                                    "       if(ISNULL(cta_nivel_3),'',cta_nivel_3) as cta_nivel_3, if(ISNULL(cta_nivel_4),'',cta_nivel_4) as cta_nivel_4, if(ISNULL(cta_nivel_5),'',cta_nivel_5) as cta_nivel_5,\n"+
                                                    "       if(ISNULL(cta_nivel_6),'',cta_nivel_6) as cta_nivel_6, if(ISNULL(cta_nivel_7),'',cta_nivel_7) as cta_nivel_7, if(ISNULL(cta_nivel_8),'',cta_nivel_8) as cta_nivel_8,\n"+
                                                    "       dnpersonas.codigo as codPersona,razon_social as nombPersona,doc_saldo_inicial,if(ISNULL(ban_descri), '', ban_descri) as ban_descri from dninventario \n" +
                                                    "inner join dndocumentos on doc_codigo=inv_coddoc and doc_empresa=inv_empresa\n" +
                                                    "inner join dncomprobante on comp_numero=inv_compcontab and comp_empresa=inv_empresa\n" +
                                                    "inner join dncta on inv_cuenta=cta_codigo\n" +
                                                    "left join dnpersonas on dnpersonas.pers_id=dninventario.pers_id\n" +
                                                    "left join dnpagos on pag_compcontab=inv_compcontab and pag_empresa=inv_empresa\n" +
                                                    "left join dnbancos on dnbancos.ban_codigo=pag_banco and ban_empresa=inv_empresa\n"+
                                                    "where inv_activo=1 \n" +
                                                    "group by inv_id";
                    //group by inv_id,inv_cuenta,inv_coddoc,inv_numdoc,inv_activo
*/
                }
                
                construyeVista = construyeVista+"\norder by inv_numdoc";
                System.out.println(construyeVista);
                
                String deleteView = "DROP VIEW if EXISTS asientos_contables";
//                insertarconex.SqlInsert(deleteView);
                
                String createView = "CREATE VIEW asientos_contables AS\n"+construyeVista;
//                insertarconex.SqlInsert(createView);
                
//********************************************************************************************************************************************************
                CrearVistaBalanceGeneral balanceGeneral = new CrearVistaBalanceGeneral();
                balanceGeneral.armarVista();
                
                CrearVistaBalanceComprobacion balanceComprobacion = new CrearVistaBalanceComprobacion();
                balanceComprobacion.armarVista();
                
                CrearVistaEstadoResultados estadoResultados = new CrearVistaEstadoResultados();
                estadoResultados.armarVista();
                
                CrearVistaMayorAnalitico mayorAnalitico = new CrearVistaMayorAnalitico();
                mayorAnalitico.armarVista();
                
                CrearVistaTotalesCtaAno totalesCtaAno = new CrearVistaTotalesCtaAno();
                totalesCtaAno.armarVista();
                
                CrearVistaTotalesCtaMes totalesCtaMes = new CrearVistaTotalesCtaMes();
                totalesCtaMes.armarVista();
                
                CrearVistaCierreFiscal cierreFiscal = new CrearVistaCierreFiscal();
                cierreFiscal.armarVista();
//********************************************************************************************************************************************************
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CrearVistaAsientosContables.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ValidaContabilizacionCierreContable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}