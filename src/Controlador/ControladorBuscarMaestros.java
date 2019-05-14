package Controlador;

import util.CargaTablas;
import util.SQLSelect;
import Modelos.VariablesGlobales;

import Vista.CreaUsuarios;
import Vista.Empresas;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import util.Internacionalizacion;

public class ControladorBuscarMaestros {
    private static ControladorBuscarMaestros controladorBuscarMaestros;
    private CargaTablas cargatabla = null;
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
//    private final ModelInventario modelInventario = ModelInventario.getModelInventario();
//    private final ModelAsientoManual modelAsientoManual = ModelAsientoManual.getModelAsientoManual();
//    private final ModelDocumentos modelDocumentos = ModelDocumentos.getModelDocumentos();
//    private final ModelVehiculo modelVehiculo = ModelVehiculo.getModelVehiculo();
//    private final ModelTipoCliPro modelTipoCliPro = ModelTipoCliPro.getmodelTipoCliPro();
//    private final ModelProductos modelProductos = ModelProductos.getModelProductos();
//    private final ModelMaestroReportesSistema modelMaestroReportesSistema = ModelMaestroReportesSistema.getModelMaestroReportesSistema();
//    private final ModelOrdenReparacion modelOrdenReparacion = ModelOrdenReparacion.getModelOrdenReparacion();
//
//    private final Categorias categorias = null;
//    private final Productos productos = null;
//    private final DescuentosArticulos descuentos = null;
    private final Empresas empresas = null;
//    private final UbicacionProductos ubicacion = null;
    
    private JTable tableMaestro;
    private ResultSet rs;
    private String tipomae = "",proveedor = "";
    
    private ControladorBuscarMaestros() {
    }
    
    public static ControladorBuscarMaestros getControladorBuscarMaestros(){
        if (controladorBuscarMaestros == null){
            controladorBuscarMaestros = new ControladorBuscarMaestros();
        }

        return controladorBuscarMaestros;
    }

    public void cargarTabla(Object aThis, JTable table, String tabla, String tipmae, String prov){
        String[] column = null;
        tipomae = tipmae;
        proveedor = prov;
        cargatabla = new CargaTablas();
        tableMaestro = table;
        
        String SQL="";
        System.out.println("Entra aqui: "+tabla);
            
//        if (aThis instanceof Categorias) {
//            switch (tabla){
//                case "DNCLASIFICACION":{
//                    SQL = "SELECT CLA_ID_CATEG,CLA_NOMBRE,CLA_IMG FROM dnclasificacion WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                          "sub_cla_id='0'";
//            
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("id"),idioma.loadLangHeaderTable().getString("nombre"),
//                                         idioma.loadLangHeaderTable().getString("img")};
//        
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(3, "id", "nombre", "img", true);
//                
//                    break;
//                }
//                case "DNCLASIFICACION_PADRE":{
//                    SQL = "SELECT CLA_ID_CATEG,CLA_NOMBRE,CLA_IMG FROM dnclasificacion WHERE "+
//                          "CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND SUB_CLA_ID='0'";
//            
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("id"),idioma.loadLangHeaderTable().getString("nombre"),
//                                         idioma.loadLangHeaderTable().getString("img")};
//        
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(3, "id", "nombre", "img", true);
//                
//                    break;
//                }
//                case "DNCLASIFICACION_HIJO":{
//                    SQL = "SELECT CLA_ID_CATEG,CLA_NOMBRE,CLA_IMG FROM dnclasificacion WHERE "+
//                          "CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND SUB_CLA_ID<>'0'";
//            
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("id"),idioma.loadLangHeaderTable().getString("nombre"),
//                                         idioma.loadLangHeaderTable().getString("img")};
//        
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(3, "id", "nombre", "img", true);
//                
//                    break;
//                }
//            }
//        }else if (aThis instanceof Productos) {
//            switch (tabla) {
//                case "DNMARCA":
//                {
//                        SQL = "SELECT MAR_CODIGO,MAR_DESCRI FROM dnmarca WHERE MAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND mar_vehiculo=0 ORDER BY MAR_DESCRI";
//                        String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                        column = columnas;
//                        cargatabla.cargatablas(table,SQL,column); 
//                        
//                        dimensionCeldas(2, "codigo", "nombre", "", false);
//                        
//                        break;
//                }   
//                case "dnpersonas":
//                {    
//                    String idRol = "";
//
//                    idRol="3";
//                
//                    SQL = "SELECT PERS_ID,IF(NOMBRE IS NULL,RAZON_SOCIAL,IF(nombre='',razon_social,nombre)) AS NOMBRE,RIF FROM dnpersonas \n" +
//                          "INNER JOIN rel_people_roles on person_father_id = pers_id\n" +
//                          "INNER JOIN adminconfigestableerp.dnrol on adminconfigestableerp.dnrol.rol_id=person_rol_father\n" +
//                          "WHERE id_company = '"+VarGlobales.getCodEmpresa()+"' AND person_rol_father = "+idRol+" \n" +
//                          "HAVING nombre IS NOT NULL ORDER BY NOMBRE;";
//            
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),
//                    idioma.loadLangHeaderTable().getString("rif")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(3, "codigo", "nombre", "rif", false);
//                    
//                    break;
//                }
//                case "DNPRODUCTO":
//                {
//                    SQL = "SELECT PRO_CODIGO,PRO_NOMBRE,PRO_MARCA FROM dnproducto WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY PRO_NOMBRE";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),
//                                         idioma.loadLangHeaderTable().getString("marcas")};
//        
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                    
//                    dimensionCeldas(3, "codigo", "nombre", "marcas", true);
//                    break;
//                }
//                case "GRUPO":
//                {
//                    SQL = "SELECT CLA_ID_CATEG,CLA_NOMBRE FROM dnclasificacion WHERE cla_empresa='"+VarGlobales.getCodEmpresa()+"' AND SUB_CLA_ID='0';";
//            
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("id"),idioma.loadLangHeaderTable().getString("nombre")};
//        
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "id", "nombre", "", true);
//                    break;
//                }
//                case "SUBGRUPO":
//                {   
//                    SQL = "SELECT CLA_ID_CATEG,CLA_NOMBRE FROM dnclasificacion WHERE cla_empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                          "sub_cla_id = '"+modelProductos.getCodGrupo()+"'";
//            
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("id"),idioma.loadLangHeaderTable().getString("nombre")};
//        
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "id", "nombre", "", true);
//                    break;
//                }
//            }
////        }else if (aThis instanceof DescuentosArticulos) {
////            if (tabla.equals("DNPRODUCTO")){
////                SQL = "SELECT PRO_CODIGO,PRO_NOMBRE,PRO_MARCA FROM dnproducto WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY  PRO_NOMBRE";
////            
////                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),idioma.loadLangHeaderTable().getString("marcas")};
////        
////                column = columnas;
////                cargatabla.cargatablas(table,SQL,column); 
////                
////                dimensionCeldas(3, "codigo", "nombre", "marcas", true);
////            }
////            if(tabla.equals("DNDESCUENTOS")){
////                SQL = "SELECT DES_CODIGO,DES_DESCRI FROM dndescuentos WHERE DES_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY DES_DESCRI";
////            
////                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
////        
////                column = columnas;
////                cargatabla.cargatablas(table,SQL,column); 
////                
////                dimensionCeldas(2, "codigo", "nombre","", false);
////            }
//        }else if  (aThis instanceof Inventario) {
//            switch (tabla) {
//                case "DNINVENTARIO":
//                {
//                    SQL = "SELECT INV_NUMDOC, COUNT(*) AS ITEM, SUM(INV_CANTID) AS UNIDADES FROM dninventario "+
//                          "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND INV_NUMDOC<>'' \n" +
//                          "GROUP BY INV_NUMDOC";
//            
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("documento"),idioma.loadLangHeaderTable().getString("items"),idioma.loadLangHeaderTable().getString("unidades")};
//                    //idioma.loadLangHeaderTable().getString("producto");
//            
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                    
//                    dimensionCeldas(3, "documento", "items", "unidades", false);
//            
//                    break;
//                }   
//                case "DNPRODUCTO":
//                {
//                    if (!modelInventario.getTxtDescri().getText().equals("")){
//                        SQL = "SELECT PRO_CODIGO,PRO_NOMBRE,PRO_MARCA FROM dnproducto WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                              "PRO_NOMBRE LIKE '%"+modelInventario.getTxtDescri().getText()+"%' ORDER BY PRO_NOMBRE";
//                    }else{
//                        if (modelInventario.getChkBuscaIzq().isSelected()==false){
//                            SQL = "SELECT PRO_CODIGO,PRO_NOMBRE,PRO_MARCA FROM dnproducto WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY PRO_CODIGO";
//                        }else{
//                            SQL = "SELECT PRO_CODIGO,PRO_NOMBRE,PRO_MARCA FROM dnproducto WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                  "PRO_CODIGO LIKE '"+modelInventario.getTxtCodigo().getText()+"%' ORDER BY PRO_NOMBRE";
//                        }
//                    }
//            
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),idioma.loadLangHeaderTable().getString("marcas")};
//                    idioma.loadLangHeaderTable().getString("producto");
//            
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column);
//                    
//                    dimensionCeldas(3, "codigo", "nombre", "marcas", true);
//                    break;
//                }
//            }
//        }else if (aThis instanceof MaestroDeReportesSistema) {
//            String idRol = "";
//            
//            if(tabla.equals("DNPERSONAS")){
//                if(modelMaestroReportesSistema.iscCxC()){
//                    idRol="2";
//                }
//                if(modelMaestroReportesSistema.iscCxP()){
//                    idRol="3";
//                }
//                
//                if (modelMaestroReportesSistema.getcTipDoc().equals("DEV") || modelMaestroReportesSistema.getcTipDoc().equals("DVC")){
//                    SQL = "SELECT DISTINCT dnpersonas.pers_id,IF(NOMBRE IS NULL,RAZON_SOCIAL,IF(nombre='',razon_social,nombre)) AS NOMBRE,RIF FROM dnpersonas \n" +
//                          "INNER JOIN rel_people_roles on person_father_id = pers_id\n" +
//                          "INNER JOIN adminconfigestableerp.dnrol on adminconfigestableerp.dnrol.rol_id=person_rol_father\n" +
//                          "INNER JOIN dninventario ON dninventario.pers_id=dnpersonas.pers_id AND "+
//                            (modelDocumentos.iscCxC() ? "dninventario.inv_coddoc='FAV'" : "dninventario.inv_coddoc='FAC'")+"\n"+
//                          "WHERE id_company = '"+VarGlobales.getCodEmpresa()+"' AND person_rol_father = "+idRol+" \n" +
//                          "HAVING nombre IS NOT NULL ORDER BY NOMBRE;";
//                }else{
//                    SQL = "SELECT DISTINCT PERS_ID,IF(NOMBRE IS NULL,RAZON_SOCIAL,IF(nombre='',razon_social,nombre)) AS NOMBRE,RIF FROM dnpersonas \n" +
//                          "INNER JOIN rel_people_roles on person_father_id = pers_id\n" +
//                          "INNER JOIN adminconfigestableerp.dnrol on adminconfigestableerp.dnrol.rol_id=person_rol_father\n" +
//                          "WHERE id_company = '"+VarGlobales.getCodEmpresa()+"' AND person_rol_father = "+idRol+" \n" +
//                          "HAVING nombre IS NOT NULL ORDER BY NOMBRE;";
//                }
//            
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),
//                                     idioma.loadLangHeaderTable().getString("rif")};
//                
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//                
//                dimensionCeldas(3, "codigo", "nombre", "rif", false);
//            }
//            if(tabla.equals("DNINVENTARIO")){
//                String tipDoc = modelMaestroReportesSistema.getcTipDoc();
//                
//                if(modelMaestroReportesSistema.getTfCodPersona().getText().isEmpty()){
//                    SQL = "SELECT INV_NUMDOC,dnpersonas.razon_social,dnpersonas.rif FROM dninventario \n" +
//                          "INNER JOIN dnpersonas ON dnpersonas.pers_id=dninventario.pers_id \n"+
//                          "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
//                          //"inv_coddoc='"+tipDoc+"' AND INV_ACTIVO=1 \n"+
//                          (modelMaestroReportesSistema.getOrigen().equals("ReporteFacturasVencidas") || modelMaestroReportesSistema.getOrigen().equals("CuentasCobrar") || 
//                            modelMaestroReportesSistema.getOrigen().equals("PagosAbonos")
//                               ? (modelMaestroReportesSistema.getOrigen().equals("PagosAbonos") ? " AND (numdoc_orig IS NOT NULL) AND (inv_status='Pagado' OR inv_status='Abono') " : 
//                                     " AND (inv_status<>'Pagado' AND inv_status<>'Abono') ") : "AND inv_coddoc='"+tipDoc+"' \n")+
//                          "GROUP BY INV_NUMDOC \n"+
//                          "ORDER BY CAST(REPLACE(INV_NUMDOC,'-','') AS DECIMAL)";  
//                }else{
//                    SQL = "SELECT INV_NUMDOC,dnpersonas.razon_social,dnpersonas.rif FROM dninventario \n" +
//                          "INNER JOIN dnpersonas ON dnpersonas.pers_id=dninventario.pers_id \n"+
//                          "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' "+
//                          (modelMaestroReportesSistema.getOrigen().equals("ReporteFacturasVencidas") || modelMaestroReportesSistema.getOrigen().equals("CuentasCobrar") || 
//                            modelMaestroReportesSistema.getOrigen().equals("PagosAbonos")
//                               ? (modelMaestroReportesSistema.getOrigen().equals("PagosAbonos") ? " AND (numdoc_orig IS NOT NULL) AND (inv_status='Pagado' OR inv_status='Abono') AND " : 
//                                     " AND (inv_status<>'Pagado' AND inv_status<>'Abono') AND ") : "AND inv_coddoc='"+tipDoc+"' AND \n")+
//                          "dnpersonas.rif='"+modelMaestroReportesSistema.getTfCodPersona().getText()+"' \n"+
//                          "GROUP BY INV_NUMDOC \n"+
//                          "ORDER BY CAST(REPLACE(INV_NUMDOC,'-','') AS DECIMAL)";  
//                }
//
//                System.out.println("Listar Documentos: "+SQL);
//                
//                String[] columnas = {idioma.loadLangHeaderTable().getString("documento"),idioma.loadLangHeaderTable().getString("nombre"), 
//                                     idioma.loadLangHeaderTable().getString("rif")};
//                
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//                
//                dimensionCeldas(2, "documento", "nombre", "status", false);
//            }
//        }else if (aThis instanceof Facturacion){
//            String idRol = "";
//            
//            if(tabla.equals("DNPERSONAS")){
//                if(modelDocumentos.iscCxC()){
//                    idRol="2";
//                }
//                if(modelDocumentos.iscCxP()){
//                    idRol="3";
//                }
//                
//                if (modelDocumentos.getcTipDoc().equals("DEV") || modelDocumentos.getcTipDoc().equals("DVC")){
//                    SQL = "SELECT DISTINCT dnpersonas.pers_id,IF(NOMBRE IS NULL,RAZON_SOCIAL,IF(nombre='',razon_social,nombre)) AS NOMBRE,RIF FROM dnpersonas \n" +
//                          "INNER JOIN rel_people_roles on person_father_id = pers_id\n" +
//                          "INNER JOIN adminconfigestableerp.dnrol on adminconfigestableerp.dnrol.rol_id=person_rol_father\n" +
//                          "INNER JOIN dninventario ON dninventario.pers_id=dnpersonas.pers_id AND "+
//                            (modelDocumentos.iscCxC() ? "dninventario.inv_coddoc='FAV'" : "dninventario.inv_coddoc='FAC'")+"\n"+
//                          "WHERE id_company = '"+VarGlobales.getCodEmpresa()+"' AND person_rol_father = "+idRol+" \n" +
//                          "HAVING nombre IS NOT NULL ORDER BY NOMBRE;";
//                }else{
//                    SQL = "SELECT DISTINCT PERS_ID,IF(NOMBRE IS NULL,RAZON_SOCIAL,IF(nombre='',razon_social,nombre)) AS NOMBRE,RIF FROM dnpersonas \n" +
//                          "INNER JOIN rel_people_roles on person_father_id = pers_id\n" +
//                          "INNER JOIN adminconfigestableerp.dnrol on adminconfigestableerp.dnrol.rol_id=person_rol_father\n" +
//                          //"WHERE person_rol_father = "+idRol+" \n" +
//                          "WHERE id_company = '"+VarGlobales.getCodEmpresa()+"' AND person_rol_father = "+idRol+" \n" +
//                          "HAVING nombre IS NOT NULL ORDER BY NOMBRE;";
//                }
//            
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),
//                                     idioma.loadLangHeaderTable().getString("rif")};
//                
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//                
//                dimensionCeldas(3, "codigo", "nombre", "rif", false);
//            }
//            if(tabla.equals("DNINVENTARIO")){
//                if(tipomae.equals("POS")){
//                    SQL = "SELECT INV_NUMDOC,INV_FECHA FROM dninventario "+
//                          "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                          "INV_CODMAE='"+proveedor+"' AND "+
//                          "INV_CODDOC='"+tipomae+"' AND "+
//                          "INV_STATUS='Vendido' AND "+  
//                          "INV_IMPORT=0 AND "+  
//                          "INV_ACTIVO=1 "+
//                          "GROUP BY INV_NUMDOC "+
//                          "ORDER BY INV_NUMDOC";
//                }else{
//                    if(tipomae.equals("Doc")){
//                        String tipDoc = proveedor; //el nombre de la variable en este caso recibe otro valor cuando se
//                                                   // llama desde el fomulario de Facturacion (Documentos)
//                        
//                        SQL = "SELECT INV_NUMDOC,dnpersonas.razon_social,inv_status FROM dninventario \n" +
//                              "INNER JOIN dnpersonas ON dnpersonas.pers_id=dninventario.pers_id \n"+
//                              "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                              //"inv_coddoc='"+tipDoc+"' AND INV_ACTIVO=1 \n"+
//                              "inv_coddoc='"+tipDoc+"' \n"+
//                              "GROUP BY INV_NUMDOC \n"+
//                              "ORDER BY CAST(REPLACE(INV_NUMDOC,'-','') AS DECIMAL)";  
//                    }else{
//                        SQL = "SELECT INV_NUMDOC,INV_FECHA,inv_status FROM dninventario \n"+
//                              "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                              "INV_CODMAE='"+proveedor+"' AND INV_CODDOC='"+tipomae+"' AND "+
//                              //"INV_IMPORT=0 AND INV_ACTIVO=1 \n"+
//                              "INV_IMPORT=0 \n"+
//                              "GROUP BY INV_NUMDOC \n"+
//                              "ORDER BY INV_NUMDOC";  
//                    }
//                }
//                System.out.println("Listar Documentos: "+SQL);
//                
//                String[] columnas = {idioma.loadLangHeaderTable().getString("documento"),idioma.loadLangHeaderTable().getString("nombre"), 
//                                     idioma.loadLangHeaderTable().getString("status")};
//                
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//                
//                dimensionCeldas(2, "documento", "nombre", "status", false);
//            }
//            if(tabla.equals("DNCONFIGIMPORT")){
//                SQL = "SELECT IMP_DOCIMP,DOC_DESCRI FROM dnconfigimport "+
//                      "INNER JOIN DNDOCUMENTOS ON IMP_DOCIMP=DOC_CODIGO AND IMP_EMPRESA=DOC_EMPRESA "+
//                      "WHERE IMP_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                      "IMP_CODDOC='"+tipomae+"' AND IMP_SELECT=1 ORDER BY DOC_DESCRI";
//                
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//                
//                dimensionCeldas(2, "codigo", "nombre", "", false);
//            }
//            if(tabla.equals("import")){
//                if(modelDocumentos.iscCxC()){
//                    idRol="2";
//                }
//                if(modelDocumentos.iscCxP()){
//                    idRol="3";
//                }
//                
//                if (modelDocumentos.getcTipDoc().equals("DEV") || modelDocumentos.getcTipDoc().equals("DVC")){
//                    SQL = "SELECT DISTINCT dnpersonas.pers_id,IF(NOMBRE IS NULL,RAZON_SOCIAL,IF(nombre='',razon_social,nombre)) AS NOMBRE,RIF,"+
//                          "inv_numdoc FROM dnpersonas \n" +
//                          "INNER JOIN rel_people_roles on person_father_id = pers_id\n" +
//                          "INNER JOIN adminconfigestableerp.dnrol on adminconfigestableerp.dnrol.rol_id=person_rol_father\n" +
//                          "INNER JOIN dninventario ON dninventario.pers_id=dnpersonas.pers_id AND "+
//                            (modelDocumentos.iscCxC() ? "dninventario.inv_coddoc='FAV'" : "dninventario.inv_coddoc='FAC'")+"\n"+
//                          "WHERE rif='"+modelDocumentos.getTfRif().getText()+"' AND id_company = '"+VarGlobales.getCodEmpresa()+"' AND \n"+
//                          "person_rol_father = "+idRol+" \n" +
//                          "HAVING nombre IS NOT NULL ORDER BY NOMBRE;";    
//                }
//                
//                String[] columnas = {idioma.loadLangHeaderTable().getString("id"), idioma.loadLangHeaderTable().getString("nombre"),
//                                     idioma.loadLangHeaderTable().getString("rif"), idioma.loadLangHeaderTable().getString("documento")};
//                
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//                
//                dimensionCeldas(4, "id", "nombre", "rif", false);
//            }
//            if (tabla.equals("DNPRODUCTO")){
//                SQL = "SELECT PRO_CODIGO,PRO_NOMBRE,PRO_MARCA FROM dnproducto WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY PRO_NOMBRE";
//                System.out.println("Reporte: "+SQL);
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),idioma.loadLangHeaderTable().getString("marcas")};
//        
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//                
//                dimensionCeldas(3, "codigo", "nombre", "marcas", true);
//            }
//        }else if (aThis instanceof CuentasPorCobrarPagar){
//            String idRol = "";
//            
//            if(tabla.equals("DNPERSONAS")){
//                //if(modelDocumentos.iscCxC()){
//                    idRol="2";
//                //}
//                //if(modelDocumentos.iscCxP()){
//                //    idRol="3";
//                //}
//                
//                SQL = "SELECT DISTINCT PERS_ID,IF(NOMBRE IS NULL,RAZON_SOCIAL,IF(nombre='',razon_social,nombre)) AS NOMBRE,RIF FROM dnpersonas \n" +
//                      "INNER JOIN rel_people_roles on person_father_id = pers_id\n" +
//                      "INNER JOIN adminconfigestableerp.dnrol on adminconfigestableerp.dnrol.rol_id=person_rol_father\n" +
//                      "WHERE id_company = '"+VarGlobales.getCodEmpresa()+"' AND person_rol_father = "+idRol+" \n" +
//                      "HAVING nombre IS NOT NULL ORDER BY NOMBRE;";
//            
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),
//                                     idioma.loadLangHeaderTable().getString("rif")};
//                
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//                
//                dimensionCeldas(3, "codigo", "nombre", "rif", false);
//            }
//        }else if (aThis instanceof VisualizarCbteElectronicos){
//            String idRol = "";
//            
//            if(tabla.equals("DNPERSONAS")){
//                SQL = "SELECT DISTINCT id_fiscal_receptor,dnpersonas.razon_social FROM status_cbtes_electronicos \n" +
//                      "INNER JOIN dnpersonas ON rif=id_fiscal_receptor;";
//            
//                String[] columnas = {idioma.loadLangHeaderTable().getString("rif"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//                
//                dimensionCeldas(2, "rif", "nombre", "", false);
//            }
//        }else if (aThis instanceof ReporteVentas){
//            if(tabla.equals("DNVENDEDOR")){
//                SQL = "SELECT VEN_CODIGO,VEN_NOMBRE FROM DNVENDEDOR "+
//                      "WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                      "VEN_ACTIVO=1 "+
//                      " UNION "+
//                      "SELECT OPE_NUMERO,OPE_NOMBRE FROM DNUSUARIOS "+
//                      "WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                      "OPE_STATUS='Activo' ORDER BY VEN_NOMBRE";
//                
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//                
//                dimensionCeldas(2, "codigo", "nombre", "", false);
//            }
//        }else if (aThis instanceof POS){
//            if(tabla.equals("DNVENDEDOR")){
//                SQL = "SELECT VEN_CODIGO,VEN_NOMBRE FROM DNVENDEDOR "+
//                      "WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                      "VEN_ACTIVO=1 ORDER BY VEN_NOMBRE";
//                
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//                
//                dimensionCeldas(2, "codigo", "nombre", "", false);
//            }
//        }else if (aThis instanceof ReporteStock) {
//            if (tabla.equals("DNPRODUCTO")){
//                SQL = "SELECT PRO_CODIGO,PRO_NOMBRE,PRO_MARCA FROM dnproducto WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY PRO_NOMBRE";
//                System.out.println("Reporte: "+SQL);
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),idioma.loadLangHeaderTable().getString("marcas")};
//        
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//                
//                dimensionCeldas(3, "codigo", "nombre", "marcas", true);
//            }
//        }else if (aThis instanceof ReporteCodBarra) {
//            if (tabla.equals("DNPRODUCTO")){
//                SQL = "SELECT PRO_CODIGO,PRO_NOMBRE,PRO_MARCA FROM dnproducto WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY PRO_NOMBRE";
//                System.out.println("Reporte: "+SQL);
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),idioma.loadLangHeaderTable().getString("marcas")};
//        
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//                
//                dimensionCeldas(3, "codigo", "nombre", "marcas", true);
//            }
//        }else if (aThis instanceof UnidadMedida){
//            if(tabla.equals("DNUNDMEDIDA")){
//                //SQL = "SELECT MED_CODIGO,MED_DESCRI FROM dnundmedida WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY MED_DESCRI";
//                SQL = "SELECT MED_CODIGO,MED_DESCRI FROM dnundmedida ORDER BY MED_DESCRI";
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//                
//                dimensionCeldas(2, "codigo", "nombre", "marcas", true);
//            }
//        }else if (aThis instanceof BancosInsPagos){
//            if(tabla.equals("DNBANCOS")){
//                SQL = "SELECT BAN_CODIGO,BAN_DESCRI FROM dnbancos WHERE BAN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY BAN_DESCRI";
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//                
//                dimensionCeldas(2, "codigo", "nombre", "", false);
//            }else if(tabla.equals("DNINSTRUMENTOPAGO")){
//                
//                SQL = "SELECT INS_CODIGO,INS_DESCRI FROM dninstrumentopago WHERE (INS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' OR ISNULL(INS_EMPRESA)) \n"+
//                      "AND pais='"+VarGlobales.getPais()+"' ORDER BY INS_DESCRI";
//                        
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//                
//                dimensionCeldas(2, "codigo", "nombre", "", false);
//            }
//        }else if (aThis instanceof Jornadas){
//            SQL = "SELECT DISTINCT JOR_CODIGO,JOR_DESCRI FROM dnjornada WHERE JOR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY JOR_DESCRI";
//            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//            column = columnas;
//            cargatabla.cargatablas(table,SQL,column); 
//            
//            dimensionCeldas(2, "codigo", "nombre", "", false);
//        }else if(aThis instanceof Cargos){
//            SQL = "SELECT CAR_CODIGO,CAR_DESCRI FROM adminconfigestableerp.dncargos WHERE CAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY CAR_DESCRI";
//            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//            
//            column = columnas;
//            cargatabla.cargatablas(table,SQL,column); 
//            
//            dimensionCeldas(2, "codigo", "nombre", "", false);
//        }else 
        if (aThis instanceof CreaUsuarios){
            SQL = "SELECT DISTINCT dnusuarios.ope_numero,OPE_USUARIO FROM dnusuarios\n" +
                  "inner join relac_usuario_grupo_permisos on relac_usuario_grupo_permisos.ope_numero=dnusuarios.ope_numero\n" +
                  "WHERE emp_codigo='"+VarGlobales.getCodEmpresa()+"'";
            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
                
            column = columnas;
            cargatabla.cargatablas(table,SQL,column); 
            
            dimensionCeldas(2, "codigo", "nombre", "", false);
//        }
//        else if(aThis instanceof CreaProveedor){
//            SQL = "SELECT MAE_CODIGO,MAE_NOMBRE FROM dnmaestro WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY MAE_NOMBRE";
//            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//            column = columnas;
//            cargatabla.cargatablas(table,SQL,column); 
//            
//            dimensionCeldas(2, "codigo", "nombre", "", false);
        }else if(aThis instanceof Empresas){
            SQL = "SELECT EMP_CODIGO,EMP_NOMBRE FROM adminconfigestableerp.dnempresas ORDER BY EMP_NOMBRE";
            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
                
            column = columnas;
            cargatabla.cargatablas(table,SQL,column); 
            
            dimensionCeldas(2, "codigo", "nombre", "", false);
//        }
//        else if(aThis instanceof Marcas){
//                SQL = "SELECT MAR_CODIGO,MAR_DESCRI FROM dnmarca WHERE MAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND mar_vehiculo=0 ORDER BY MAR_DESCRI";
//                //System.out.println("PRINT CONTROLADOR BUSCAR MAESTRO METODO CARGA TABLA: "+SQL);
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//
//                dimensionCeldas(2, "codigo", "nombre", "", false);
//         }else if(aThis instanceof MarcasVehiculos){
//                SQL = "SELECT MAR_CODIGO,MAR_DESCRI FROM dnmarca WHERE MAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND mar_vehiculo=1 ORDER BY MAR_DESCRI";
//                //System.out.println("PRINT CONTROLADOR BUSCAR MAESTRO METODO CARGA TABLA: "+SQL);
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//
//                dimensionCeldas(2, "codigo", "nombre", "", false);
//         }else if(aThis instanceof ModeloVehiculo){
//            switch(tabla){
//                case "DNMARCA":{
//                    SQL = "SELECT MAR_CODIGO,MAR_DESCRI FROM dnmarca WHERE MAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND mar_vehiculo=1 ORDER BY MAR_DESCRI";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//            
//                    break;
//                }
//                case "MODELO_VEHICULO":{
//                    SQL = "SELECT codigoMarca,codigo, descri FROM modelo_vehiculo WHERE empresa='"+VarGlobales.getCodEmpresa()+"' ORDER BY id";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("marcas"),idioma.loadLangHeaderTable().getString("codigo"),
//                                         idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(3, "marcas", "codigo", "nombre", false);
//                    
//                    break;
//                }
//                case "MODELO_VEHICULO_2":{
//                    SQL = "SELECT codigo,descri,codigoMarca FROM modelo_vehiculo WHERE empresa='"+VarGlobales.getCodEmpresa()+"' ORDER BY id";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),
//                                         idioma.loadLangHeaderTable().getString("marcas")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(3, "codigo", "nombre", "marcas", false);
//                    
//                    break;
//                }
//            }
//         }else if(aThis instanceof CategoriasVehiculos){
//                SQL = "SELECT codigo,descri FROM categorias_vehiculo WHERE empresa='"+VarGlobales.getCodEmpresa()+"' ORDER BY id";
//
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//
//                dimensionCeldas(2, "codigo", "nombre", "", false);
//         }else if(aThis instanceof Motor){
//            switch(tabla){
//                case "MOTOR":{
//                    SQL = "SELECT codigo,nombre_motor FROM motor WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND activo=1 ORDER BY id";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//            
//                    break;
//                }
//                case "TIPO_MOTOR":{
//                    SQL = "SELECT codigo,nombre_tipo_motor FROM tipo_motor WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND activo=1 ORDER BY id";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//                    
//                    break;
//                }
//                case "MARCA":{
//                    SQL = "SELECT MAR_CODIGO,MAR_DESCRI FROM dnmarca WHERE MAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND mar_vehiculo=1 ORDER BY MAR_DESCRI";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//            
//                    break;
//                }
//                case "TIPO_CILINDRADA":{
//                    SQL = "SELECT codigo,nombre_tipo_cilindrada FROM tipo_cilindrada WHERE empresa='"+VarGlobales.getCodEmpresa()+"' ORDER BY id";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//                    
//                    break;
//                }
//                case "COMBUSTIBLE":{
//                    SQL = "SELECT codigo,nombre_combustible FROM combustible WHERE empresa='"+VarGlobales.getCodEmpresa()+"' ORDER BY id";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//                    
//                    break;
//                }
//            }
//         }else if(aThis instanceof Vehiculo){
//            switch(tabla){
//                case "VEHICULO":{
//                    SQL = "SELECT placa,codmotor FROM vehiculo WHERE empresa='"+VarGlobales.getCodEmpresa()+"' ORDER BY id";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//            
//                    break;
//                }
//                case "DNPERSONAS":{
//                    String idRol = "";
//
//                    idRol="2";
//
//                    SQL = "SELECT RIF,IF(NOMBRE IS NULL,RAZON_SOCIAL,IF(nombre='',razon_social,nombre)) AS NOMBRE FROM dnpersonas \n" +
//                          "INNER JOIN rel_people_roles on person_father_id = pers_id\n" +
//                          "INNER JOIN adminconfigestableerp.dnrol on adminconfigestableerp.dnrol.rol_id=person_rol_father\n" +
//                          "WHERE id_company = '"+VarGlobales.getCodEmpresa()+"' AND person_rol_father = "+idRol+" \n" +
//                          "HAVING nombre IS NOT NULL ORDER BY NOMBRE;";
//
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//                    
//                    break;
//                }
//                case "DNMARCA":{
//                    SQL = "SELECT MAR_CODIGO,MAR_DESCRI FROM dnmarca WHERE MAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND mar_vehiculo=1 ORDER BY MAR_DESCRI";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//            
//                    break;
//                }
//                case "MODELO_VEHICULO":{
//                    try {
//                        ResultSet rsFiltroMarca = modelVehiculo.buscaMarcaVehiculoFiltro(modelVehiculo.getTfMarcaVeh().getText());
//                    
//                        SQL = "SELECT codigo,descri,codigoMarca FROM modelo_vehiculo WHERE empresa='"+VarGlobales.getCodEmpresa()+"' "+
//                              (rsFiltroMarca.getRow()==0 ? "" : " AND codigoMarca='"+rsFiltroMarca.getString("mar_codigo"))+"' ORDER BY id";
//                    
//                        String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),
//                            idioma.loadLangHeaderTable().getString("marcas")};
//                    
//                        column = columnas;
//                        cargatabla.cargatablas(table,SQL,column);
//                    
//                        dimensionCeldas(3, "codigo", "nombre", "marcas", false);
//                    } catch (SQLException ex) {
//                        Logger.getLogger(ControladorBuscarMaestros.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    
//                    break;
//                }
//                case "CATEGORIAS_VEHICULO":{
//                    SQL = "SELECT codigo,descri FROM categorias_vehiculo WHERE empresa='"+VarGlobales.getCodEmpresa()+"' ORDER BY id";
//
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//                    
//                    break;
//                }
//                case "MOTOR":{
//                    SQL = "SELECT codigo,nombre_motor FROM motor WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND activo=1 ORDER BY id";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//            
//                    break;
//                }
//                case "TIPO_MOTOR":{
//                    SQL = "SELECT codigo,nombre_tipo_motor FROM tipo_motor WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND activo=1 ORDER BY id";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//                    
//                    break;
//                }
//                case "TRANSMISION":{
//                    SQL = "SELECT codigo,nombre_transmision FROM transmision WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND activo=1 ORDER BY id";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//                    
//                    break;
//                }
//                case "TRACCION":{
//                    SQL = "SELECT codigo,nombre_traccion FROM traccion WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND activo=1 ORDER BY id";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),
//                                         idioma.loadLangHeaderTable().getString("marcas")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(3, "codigo", "nombre", "marcas", false);
//                    
//                    break;
//                }
//                case "TIPO_CILINDRADA":{
//                    SQL = "SELECT codigo,nombre_tipo_cilindrada FROM tipo_cilindrada WHERE empresa='"+VarGlobales.getCodEmpresa()+"' ORDER BY id";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//                    
//                    break;
//                }
//                case "MODELO_CILINDRADA":{
//                    SQL = "SELECT codigo,nombre_modelo_cilindrada FROM modelo_cilindrada WHERE empresa='"+VarGlobales.getCodEmpresa()+"' ORDER BY id";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//                    
//                    break;
//                }
//                case "COMBUSTIBLE":{
//                    SQL = "SELECT codigo,nombre_combustible FROM combustible WHERE empresa='"+VarGlobales.getCodEmpresa()+"' ORDER BY id";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//                    
//                    break;
//                }
//            }
//         }else if(aThis instanceof OrdenReparacion){
//            switch(tabla){
//                case "ORDE_REPARACION":{
//                    String tipDoc = proveedor; //el nombre de la variable en este caso recibe otro valor cuando se
//                                                   // llama desde el fomulario de Facturacion (Documentos)
//                                                   
//                    SQL = "SELECT INV_NUMDOC,dnpersonas.razon_social,placa FROM dninventario \n" +
//                          "INNER JOIN dnpersonas ON dnpersonas.pers_id=dninventario.pers_id \n"+
//                          "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND inv_coddoc='"+tipDoc+"' \n"+
//                          "GROUP BY INV_NUMDOC \n"+
//                          "ORDER BY CAST(REPLACE(INV_NUMDOC,'-','') AS DECIMAL)";  
//                    
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),
//                                         idioma.loadLangHeaderTable().getString("placa")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(3, "codigo", "nombre", "placa", false);
//            
//                    break;
//                }
//                case "VEHICULO":{
//                    SQL = "SELECT placa,dnpersonas.razon_social FROM vehiculo \n" +
//                          "INNER JOIN dnpersonas ON dnpersonas.pers_id=vehiculo.codclien \n"+
//                          "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' ORDER BY id";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//            
//                    break;
//                }
//                case "TECNICO":{
//                    SQL = "SELECT tec_codigo,tec_nombre FROM tecnico WHERE tec_empresa = '"+VarGlobales.getCodEmpresa()+"'";
//            
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),
//                                         idioma.loadLangHeaderTable().getString("nombre")};
//                                 
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//            
//                    break;
//                }
//                case "USUARIO":{
//                    SQL = "SELECT dnusuarios.ope_numero,ope_nombre FROM dnusuarios \n"+
//                          "inner join relac_usuario_grupo_permisos on relac_usuario_grupo_permisos.ope_numero=dnusuarios.ope_numero\n" +
//                          "WHERE emp_codigo = '"+VarGlobales.getCodEmpresa()+"' AND ope_activo=1";
//            
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),
//                                         idioma.loadLangHeaderTable().getString("nombre")};
//                                 
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//            
//                    break;
//                }
//                case "import":{
//                    String tipDoc = proveedor; //el nombre de la variable en este caso recibe otro valor cuando se
//                                               // llama desde el fomulario de Facturacion (Documentos)
//                            
//                    SQL = "SELECT INV_NUMDOC,dnpersonas.razon_social,placa FROM dninventario \n" +
//                          "INNER JOIN dnpersonas ON dnpersonas.pers_id=dninventario.pers_id \n"+
//                          "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND inv_coddoc='"+tipDoc+"' AND "+
//                          //"rif='"+modelOrdenReparacion.getCodPersona()+"' AND inv_import=0\n"+
//                          "inv_import=0\n"+
//                          "GROUP BY INV_NUMDOC \n"+
//                          "ORDER BY CAST(REPLACE(INV_NUMDOC,'-','') AS DECIMAL)";  
//                    
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),
//                                         idioma.loadLangHeaderTable().getString("placa")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(3, "codigo", "nombre", "placa", false);
//                    
//                    break;
//                }
//            }
//         }else if(aThis instanceof TipoCliPro){
//            SQL = "SELECT id_type_person,name FROM adminconfigestableerp.type_person WHERE pais='"+VarGlobales.getPais()+"' AND "+
//                  "id_rol="+modelTipoCliPro.getIdRol()+" ORDER BY id_type_person";
//                
//            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//            column = columnas;
//            cargatabla.cargatablas(table,SQL,column); 
//
//            dimensionCeldas(2, "codigo", "nombre", "", false);
////         }else if (aThis instanceof Impuestos){
////            SQL = "SELECT tiva_codigo,tiva_descri FROM dntipiva WHERE tiva_empresa='"+VarGlobales.getCodEmpresa()+"' ORDER BY tiva_id";
////
////            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
////            column = columnas;
////            cargatabla.cargatablas(table,SQL,column); 
////
////            dimensionCeldas(2, "codigo", "nombre", "", false);
////         }else if (aThis instanceof TipoContacto){
////            SQL = "SELECT tcon_codigo,tcon_descri FROM dntipcontacto ORDER BY tcon_id";
////
////            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
////            column = columnas;
////            cargatabla.cargatablas(table,SQL,column); 
////
////            dimensionCeldas(2, "codigo", "nombre", "", false);
////         }else if (aThis instanceof Moneda){
////            //SQL = "SELECT mon_codigo, mon_nombre FROM dnmoneda WHERE mon_empresa='"+VarGlobales.getCodEmpresa()+"'";
////            SQL = "SELECT mon_codigo, mon_nombre FROM dnmoneda";
////
////            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
////            column = columnas;
////            cargatabla.cargatablas(table,SQL,column); 
////
////            dimensionCeldas(2, "codigo", "nombre", "", false);
//         }else if(aThis instanceof TipoMotor){
//                SQL = "SELECT codigo,nombre_tipo_motor FROM tipo_motor WHERE empresa='"+VarGlobales.getCodEmpresa()+"' ORDER BY id";
//                
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//
//                dimensionCeldas(2, "codigo", "nombre", "", false);
//         }else if(aThis instanceof Transmision){
//                SQL = "SELECT codigo,nombre_transmision FROM transmision WHERE empresa='"+VarGlobales.getCodEmpresa()+"' ORDER BY id";
//                
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//
//                dimensionCeldas(2, "codigo", "nombre", "", false);
//         }else if(aThis instanceof Traccion){
//                SQL = "SELECT codigo,nombre_traccion FROM traccion WHERE empresa='"+VarGlobales.getCodEmpresa()+"' ORDER BY id";
//                
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//
//                dimensionCeldas(2, "codigo", "nombre", "", false);
//         }else if(aThis instanceof TipoCilindrada){
//                SQL = "SELECT codigo,nombre_tipo_cilindrada FROM tipo_cilindrada WHERE empresa='"+VarGlobales.getCodEmpresa()+"' ORDER BY id";
//                
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//
//                dimensionCeldas(2, "codigo", "nombre", "", false);
//         }else if(aThis instanceof ModeloCilindrada){
//                SQL = "SELECT codigo,nombre_modelo_cilindrada FROM modelo_cilindrada WHERE empresa='"+VarGlobales.getCodEmpresa()+"' ORDER BY id";
//                
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//
//                dimensionCeldas(2, "codigo", "nombre", "", false);
//         }else if(aThis instanceof Combustible){
//                SQL = "SELECT codigo,nombre_combustible FROM combustible WHERE empresa='"+VarGlobales.getCodEmpresa()+"' ORDER BY id";
//                
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//
//                dimensionCeldas(2, "codigo", "nombre", "", false);
//         }else if(aThis instanceof UbicacionProductos){
//                SQL = "SELECT UBI_CODIGO,UBI_DESCRI FROM DNUBICACION_PRODUCTOS WHERE UBI_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY UBI_DESCRI";
//                //System.out.println("PRINT CONTROLADOR BUSCAR MAESTRO METODO CARGA TABLA: "+SQL);
//                String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                column = columnas;
//                cargatabla.cargatablas(table,SQL,column); 
//
//                dimensionCeldas(2, "codigo", "nombre", "", false);
//                        
//                   
//         }else if(aThis instanceof ReporteEtiquetasCajas){
//            SQL = "SELECT INV_NUMDOC, COUNT(*) AS ITEM, SUM(INV_CANTID) AS UNIDADES FROM dninventario "+
//                  "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND INV_NUMDOC<>'' \n" +
//                  "GROUP BY INV_NUMDOC";
//            
//            String[] columnas = {idioma.loadLangHeaderTable().getString("documento"),idioma.loadLangHeaderTable().getString("items"),idioma.loadLangHeaderTable().getString("unidades")};
//            //idioma.loadLangHeaderTable().getString("producto");
//            
//            column = columnas;
//            cargatabla.cargatablas(table,SQL,column); 
//            
//            dimensionCeldas(3, "documento", "items", "unidades", false);
//        }else if (aThis instanceof RelacionDoc_BL) {
//            switch (tabla) {
//                case "DNINVENTARIO":
//                    SQL = "SELECT INV_NUMDOC, COUNT(*) AS ITEM, SUM(INV_CANTID) AS UNIDADES FROM dninventario "+
//                          "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND INV_NUMDOC<>'' \n" +
//                          "GROUP BY INV_NUMDOC";
//            
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("documento"),idioma.loadLangHeaderTable().getString("items"),idioma.loadLangHeaderTable().getString("unidades")};
//                    //idioma.loadLangHeaderTable().getString("producto");
//            
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                    
//                    dimensionCeldas(3, "documento", "items", "unidades", false);
//            
//                    break;
//                case "DNMAESTRO":
//                    SQL = "SELECT MAE_CODIGO,MAE_NOMBRE,MAE_RIF FROM dnmaestro WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY MAE_NOMBRE";
//                    String[] columnas2 = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),
//                                         idioma.loadLangHeaderTable().getString("rif")};
//                    column = columnas2;
//                    cargatabla.cargatablas(table,SQL,column); 
//                    
//                    dimensionCeldas(3, "codigo", "nombre", "rif", false);
//                    
//                    break;
//                case "DNRELACION_DOCUMENTOS":
//                    SQL = "SELECT RELD_NUMREL,DOC_DESCRI,RELD_TIPDOC FROM dnrelacion_documentos \n" +
//                          "INNER JOIN dndocumentos ON DOC_CODIGO=RELD_TIPDOC \n"+
//                          "WHERE RELD_EMPRESA='"+VarGlobales.getCodEmpresa()+"' GROUP BY RELD_NUMREL";
//                    
//                    String[] columnas3 = {idioma.loadLangHeaderTable().getString("documento"),idioma.loadLangHeaderTable().getString("nombDocRelac"),
//                                         idioma.loadLangHeaderTable().getString("tipoDoc")};
//                    column = columnas3;
//                    cargatabla.cargatablas(table,SQL,column); 
//                    
//                    dimensionCeldas(3, "documento", "nombDocRelac", "tipoDoc", false);
//                    
//                    break;    
//            }           
//        }else if(aThis instanceof CambiarClave){
//            SQL = "SELECT OPE_NUMERO,OPE_NOMBRE FROM dnusuarios WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY OPE_NOMBRE";
//            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//            column = columnas;
//            cargatabla.cargatablas(table,SQL,column); 
//                
//            dimensionCeldas(2, "codigo", "nombre", "", false);
        }
//        else if(aThis instanceof ComisionVendedor){
//            switch(tabla){
//                case "DNVENDEDOR":{
//                    SQL = "SELECT VEN_CODIGO,VEN_NOMBRE FROM dnvendedor WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY VEN_NOMBRE";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//            
//                    break;
//                }
//                case "DNVENDEDOR2":{
//                    SQL = "SELECT VEN_CODIGO,VEN_NOMBRE FROM dnvendedor WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY VEN_NOMBRE";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//                    
//                    break;
//                }
//            }
//        }else if (aThis instanceof GruposPermisos){
//            SQL = "SELECT PER_ID,PER_NOMBRE FROM dnpermiso_grupal WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY PER_NOMBRE";
//            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//            column = columnas;
//            cargatabla.cargatablas(table,SQL,column); 
//                
//            dimensionCeldas(2, "codigo", "nombre", "", false);
////        }else if (aThis instanceof Vendedor){
////            SQL = "SELECT VEN_CODIGO,VEN_NOMBRE FROM dnvendedor WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY VEN_NOMBRE";
////            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
////                
////            column = columnas;
////            cargatabla.cargatablas(table,SQL,column); 
////                
////            dimensionCeldas(2, "codigo", "nombre", "", false);
//        }else if (aThis instanceof Precio){
//            switch(tabla){
//                case "DNPRODUCTO":{
//                    SQL = "SELECT PRO_CODIGO,PRO_NOMBRE FROM dnproducto WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY PRO_NOMBRE";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//                    break;
//                }
//                case "DNLISTPRE":{
//                    //SQL = "SELECT LIS_CODIGO,LIS_DESCRI FROM dnlistpre WHERE LIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
//                    SQL = "SELECT LIS_CODIGO,LIS_DESCRI FROM dnlistpre";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//                    break;
//                }
//                case "DNUNDMEDIDA":{
//                    //SQL = "SELECT MED_CODIGO,MED_DESCRI FROM dnundmedida WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY MED_DESCRI";
//                    SQL = "SELECT MED_CODIGO,MED_DESCRI FROM dnundmedida ORDER BY MED_DESCRI";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//                    break;
//                }
//                case "DNPRECIO":{
//                    SQL = "SELECT PRE_CODIGO,PRE_CODPRO,PRE_CODLIS FROM dnprecio WHERE PRE_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("producto"),
//                                         idioma.loadLangHeaderTable().getString("tipo")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(3, "codigo", "producto", "tipo", false);
//                    break;
//                }
//            }
//        }else if (aThis instanceof TipoDocumentos){
//            SQL = "SELECT DOC_CODIGO,DOC_DESCRI FROM dndocumentos WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ORDER BY DOC_DESCRI";
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre")};
//                
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column); 
//                
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//        }else if (aThis instanceof ConfigAsientos){
//            SQL = "SELECT CCONT_NUM_CONFIG,DOC_DESCRI FROM dnconfig_contable "+
//                  "INNER JOIN dndocumentos ON CCONT_DOCUMENTO=DOC_CODIGO "+
//                  "WHERE CCONT_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                  "CCONT_ACTIVO=1 "+
//                  "GROUP BY CCONT_NUM_CONFIG";
//            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("documento")};
//                
//            column = columnas;
//            cargatabla.cargatablas(table,SQL,column); 
//                
//            dimensionCeldas(2, "codigo", "documento", "", false);
//        }else if (aThis instanceof AsientoManual){
//            switch(tabla){
//                case "DNINVENTARIO":{
//                    SQL = "SELECT inv_numdoc,inv_coddoc,inv_codmae FROM dninventario "+
//                          "WHERE inv_empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                          "inv_activo=1 AND inv_cuenta IS NOT NULL "+
//                          "GROUP BY inv_numdoc";
//
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("descrip"),
//                                         idioma.loadLangHeaderTable().getString("clipro")};
//
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column);
//                    dimensionCeldas(3, "codigo", "documento", "clipro", false);
//                    
//                    break;
//                }
//                case "DNPERSONAS":{
//                    if(modelAsientoManual.getlCxC()){
//                        SQL = "SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas "+
//                              "WHERE activo=1 ORDER BY nombre";
//                    }
//                    if(modelAsientoManual.getlCxP()){
//                        SQL = "SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas "+
//                              "WHERE activo=1 ORDER BY nombre";
//                    }
//                    String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),
//                                         idioma.loadLangHeaderTable().getString("nombre")};
//
//                    column = columnas;
//                    cargatabla.cargatablas(table,SQL,column);
//                    dimensionCeldas(2, "codigo", "nombre", "", false);
//                    
//                    break;
//                }
//            }
//        }else if(aThis instanceof ImprimirCheque){
//            SQL = "SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas "+
//                  "WHERE activo=1 ORDER BY nombre ";
//            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),
//                                 idioma.loadLangHeaderTable().getString("nombre")};
//                                 
//            column = columnas;
//            cargatabla.cargatablas(table,SQL,column); 
//                
//            dimensionCeldas(2, "codigo", "nombre", "", false);
//        }else if(aThis instanceof RetencionIVA){
//            SQL = "SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas "+
//                  "WHERE activo=1 AND contri=1 ORDER BY nombre";
//            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),
//                                 idioma.loadLangHeaderTable().getString("nombre")};
//                                 
//            column = columnas;
//            cargatabla.cargatablas(table,SQL,column); 
//                
//            dimensionCeldas(2, "codigo", "nombre", "", false);
//        }else if(aThis instanceof RetencionISLR){
//            switch(tabla){
//                case "DNPERSONAS":{
//                    SQL = "SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas "+
//                          "WHERE activo=1 ORDER BY nombre";
//                    break;
//                }
//                case "DNEMPRESAS":{
//                    SQL = "SELECT dnpersonas.pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas "+
//                          "INNER JOIN adminconfigestableerp.dnempresas ON dnpersonas.pers_id = adminconfigestableerp.dnempresas.pers_id "+
//                          "WHERE activo=1 ORDER BY nombre";
//                    break;
//                }
//            }
//            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),
//                                 idioma.loadLangHeaderTable().getString("nombre")};
//                                 
//            column = columnas;
//            cargatabla.cargatablas(table,SQL,column); 
//                
//            dimensionCeldas(2, "codigo", "nombre", "", false);
//        }else if(aThis instanceof reporteISLRlote){
//            SQL = "SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas "+
//                  "WHERE activo=1 ORDER BY nombre";
//            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),
//                                 idioma.loadLangHeaderTable().getString("nombre")};
//                                 
//            column = columnas;
//            cargatabla.cargatablas(table,SQL,column); 
//                
//            dimensionCeldas(2, "codigo", "nombre", "", false);
//        }else if(aThis instanceof RetencionIVAventas){
//            SQL = "SELECT dnpersonas.pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas "+
//                  "INNER JOIN adminconfigestableerp.dnempresas ON dnpersonas.pers_id = adminconfigestableerp.dnempresas.pers_id "+  
//                  "WHERE activo=1 ORDER BY nombre";
//            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),
//                                 idioma.loadLangHeaderTable().getString("nombre")};
//                                 
//            column = columnas;
//            cargatabla.cargatablas(table,SQL,column); 
//                
//            dimensionCeldas(2, "codigo", "nombre", "", false);
//        }else if(aThis instanceof PagoDocs){
//            SQL = "SELECT dnpersonas.pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas "+
//                  "WHERE activo=1 ORDER BY nombre";
//            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),
//                                 idioma.loadLangHeaderTable().getString("nombre")};
//                                 
//            column = columnas;
//            cargatabla.cargatablas(table,SQL,column); 
//                
//            dimensionCeldas(2, "codigo", "nombre", "", false);
//        }else if(aThis instanceof Proveedores){
//            SQL = "select DISTINCT rif,razon_social,condic,rif,contri,tip_per,\n" +
//                  "                ret_iva,observaciones,rol_nombre\n" +
//                  "from rel_people_roles\n" +
//                  "inner join dnpersonas on person_father_id = pers_id\n" +
//                  "inner join adminconfigestableerp.dnrol on adminconfigestableerp.dnrol.rol_id=person_rol_father\n" +
//                  "WHERE person_rol_father = "+tipmae+" AND id_company='"+VarGlobales.getCodEmpresa()+"' ORDER BY razon_social";
//            
//            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),
//                                 idioma.loadLangHeaderTable().getString("nombre")};
//                                 
//            column = columnas;
//            cargatabla.cargatablas(table,SQL,column); 
//                
//            dimensionCeldas(2, "codigo", "nombre", "", false);
//        }else if(aThis instanceof ReporteRetIVAventas){
//            switch(tabla){
//                case "DNEMPRESAS":{
//                    SQL = "SELECT emp_codigo,emp_nombre FROM adminconfigestableerp.dnempresas WHERE emp_activo=1 ORDER BY emp_nombre";
//                    break;
//                }
//                case "DNPERSONAS":{
//                    SQL = "SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas WHERE activo=1  ORDER BY nombre";
//                    break;
//                }
//            }
//            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),
//                                 idioma.loadLangHeaderTable().getString("nombre")};
//                                 
//            column = columnas;
//            cargatabla.cargatablas(table,SQL,column); 
//                
//            dimensionCeldas(2, "codigo", "nombre", "", false);
//        }else if(aThis instanceof Tecnico){
//            SQL = "SELECT tec_codigo,tec_nombre FROM tecnico WHERE tec_empresa = '"+VarGlobales.getCodEmpresa()+"'";
//            
//            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),
//                                 idioma.loadLangHeaderTable().getString("nombre")};
//                                 
//            column = columnas;
//            cargatabla.cargatablas(table,SQL,column); 
//                
//            dimensionCeldas(2, "codigo", "nombre", "", false);
//        }
    }
    
    public Object[][] buscarNombre(String nombre, Object aThis, String tabla){
        Object element[][] = null;
        SQLSelect elemen = new SQLSelect();
        
        nombre = nombre.replace("'", "\\'");
        
//        if (aThis instanceof Categorias) {
//            if (tabla.equals("DNCLASIFICACION")){
//                Object elementos[][] = elemen.SQLSelect("SELECT CLA_ID_CATEG,CLA_NOMBRE FROM dnclasificacion "+
//                                                        "WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "CLA_NOMBRE LIKE '%"+nombre+"%'");
//            
//                element = elementos;
//            }
//            if (tabla.equals("DNCLASIFICACION_PADRE")){
//                Object elementos[][] = elemen.SQLSelect("SELECT CLA_ID_CATEG,CLA_NOMBRE FROM dnclasificacion "+
//                                                        "WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "CLA_NOMBRE LIKE '%"+nombre+"%' AND SUB_CLA_ID='0'");
//            
//                element = elementos;
//            }
//            if (tabla.equals("DNCLASIFICACION_HIJO")){
//                Object elementos[][] = elemen.SQLSelect("SELECT CLA_ID_CATEG,CLA_NOMBRE FROM dnclasificacion "+
//                                                        "WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "CLA_NOMBRE LIKE '%"+nombre+"%' AND SUB_CLA_ID<>'0'");
//            
//                element = elementos;
//            }
//        }else if (aThis instanceof Productos) {
//            String campo = "";
//            
//            if (tabla.equals("DNMARCA")){
//                Object elementos[][] = elemen.SQLSelect("SELECT MAR_CODIGO,MAR_DESCRI FROM dnmarca "+
//                                                        "WHERE MAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "MAR_DESCRI LIKE '%"+nombre+"%' AND mar_vehiculo=0");
//            
//                element = elementos;
//            }if (tabla.equals("dnpersonas")){
//                campo="MAE_PROVEED=1";
//                Object elementos[][] = elemen.SQLSelect("SELECT PERS_ID,IF(NOMBRE IS NULL,RAZON_SOCIAL,IF(nombre='',razon_social,nombre)) AS NOMBRE,RIF FROM dnpersonas "+
//                                                        "WHERE (NOMBRE LIKE '%"+nombre+"%' OR RAZON_SOCIAL LIKE '%"+nombre+"%') AND pers_activo = 1");
//            
//                element = elementos;
//            }if (tabla.equals("GRUPO")){
//                Object elementos[][] = elemen.SQLSelect("SELECT CLA_ID_CATEG,CLA_NOMBRE FROM dnclasificacion "+
//                                                        "WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "CLA_NOMBRE LIKE '%"+nombre+"%' AND SUB_CLA_ID='0'");
//            
//                element = elementos;
//            }if (tabla.equals("SUBGRUPO")){
//                Object elementos[][] = elemen.SQLSelect("SELECT CLA_ID_CATEG,CLA_NOMBRE FROM dnclasificacion "+
//                                                        "WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "CLA_NOMBRE LIKE '%"+nombre+"%' AND sub_cla_id = '"+modelProductos.getCodGrupo()+"'");
//            
//                element = elementos;
//            }
////        }else if (aThis instanceof DescuentosArticulos) {
////            if (tabla.equals("DNPRODUCTO")){
////                Object elementos[][] = elemen.SQLSelect("SELECT PRO_CODIGO,PRO_NOMBRE,PRO_MARCA FROM dnproducto "+
////                                                        "WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
////                                                        "PRO_NOMBRE LIKE '%"+nombre+"%'");
////                
////                element = elementos;
////            }
////            if (tabla.equals("DNDESCUENTOS")){
////                Object elementos[][] = elemen.SQLSelect("SELECT DES_CODIGO,DES_DESCRI FROM dndescuentos "+
////                                                        "WHERE DES_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
////                                                        "DES_DESCRI LIKE '%"+nombre+"%'");
////                
////                element = elementos;
////            }
//        }else if (aThis instanceof Inventario) {
//            String[] column = null;
//            
//            String SQL = "SELECT PRO_CODIGO,PRO_NOMBRE,PRO_MARCA FROM dnproducto "+
//                         "WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                         "PRO_NOMBRE LIKE '%"+nombre+"%'";
//            
//            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),idioma.loadLangHeaderTable().getString("marcas")};
//            idioma.loadLangHeaderTable().getString("producto");
//            
//            column = columnas;
//            cargatabla.cargatablas(tableMaestro, SQL, column); 
//            
//            dimensionCeldas(3, "codigo", "nombre", "marcas", true);
//            
//            //Object elementos[][] = elemen.SQLSelect("SELECT PRO_CODIGO,PRO_NOMBRE,PRO_MARCA FROM dnproducto "+
//            //                                        "WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//            //                                        "PRO_NOMBRE LIKE '%"+nombre+"%'");
//            
//            //element = elementos;
//        }else if (aThis instanceof Facturacion){
//            if(tabla.equals("DNPERSONAS")){
//                String campo = "";
//                
//                if(tipomae.equals("0")){
//                    campo="MAE_PROVEED=1";
//                }else{
//                    campo="MAE_CLIENTE=1";
//                }
//                
//                Object elementos[][] = elemen.SQLSelect("SELECT PERS_ID,IF(NOMBRE IS NULL,RAZON_SOCIAL,IF(nombre='',razon_social,nombre)) AS NOMBRE,RIF FROM dnpersonas "+
//                                                        "WHERE (NOMBRE LIKE '%"+nombre+"%' OR RAZON_SOCIAL LIKE '%"+nombre+"%') AND pers_activo = 1");
//                element = elementos;
//            }
//        }else if (aThis instanceof CuentasPorCobrarPagar){
//            if(tabla.equals("DNPERSONAS")){
//                String campo = "";
//                
//                //if(tipomae.equals("0")){
//                //    campo="MAE_PROVEED=1";
//                //}else{
//                    campo="MAE_CLIENTE=1";
//                //}
//                
//                Object elementos[][] = elemen.SQLSelect("SELECT PERS_ID,IF(NOMBRE IS NULL,RAZON_SOCIAL,IF(nombre='',razon_social,nombre)) AS NOMBRE,RIF FROM dnpersonas "+
//                                                        "WHERE (NOMBRE LIKE '%"+nombre+"%' OR RAZON_SOCIAL LIKE '%"+nombre+"%') AND pers_activo = 1");
//                element = elementos;
//            }
//        }else if (aThis instanceof MaestroDeReportesSistema){
//            switch(tabla){
//                case "DNPERSONAS":{
//                    if(modelAsientoManual.getlCxC()){
//                        Object elementos[][] = elemen.SQLSelect("SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas "+
//                                                                "WHERE nombre LIKE '%"+nombre+"%' OR razon_social LIKE '%"+nombre+"%'");
//                    
//                        element = elementos;
//                    }
//                    if(modelAsientoManual.getlCxP()){
//                        Object elementos[][] = elemen.SQLSelect("SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas "+
//                                                                "WHERE nombre LIKE '%"+nombre+"%' OR razon_social LIKE '%"+nombre+"%'");
//                    
//                        element = elementos;
//                    }
//                    break;
//                }
//            }
//        }else if (aThis instanceof VisualizarCbteElectronicos){
//            if(tabla.equals("DNPERSONAS")){
//                String campo = "";
//                
//                campo="MAE_CLIENTE=1";
//                
//                Object elementos[][] = elemen.SQLSelect("SELECT DISTINCT id_fiscal_receptor,dnpersonas.razon_social FROM status_cbtes_electronicos \n" +
//                                                        "INNER JOIN dnpersonas ON rif=id_fiscal_receptor \n"+
//                                                        "WHERE (NOMBRE LIKE '%"+nombre+"%' OR RAZON_SOCIAL LIKE '%"+nombre+"%') ");
//                element = elementos;
//            }
//        }else if (aThis instanceof ReporteVentas){
//            if(tabla.equals("DNVENDEDOR")){
//                Object elementos[][] = elemen.SQLSelect("SELECT VEN_CODIGO,VEN_NOMBRE FROM DNVENDEDOR "+
//                                                        "WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "VEN_NOMBRE LIKE '%"+nombre+"%'"+
//                                                        "VEN_ACTIVO=1 "+
//                                                        " UNION "+
//                                                        "SELECT OPE_NUMERO,OPE_NOMBRE FROM DNUSUARIOS "+
//                                                        "WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "OPE_NOMBRE LIKE '%"+nombre+"%'"+
//                                                        "OPE_STATUS='Activo' ORDER BY VEN_CODIGO");
//                
//                element = elementos;
//            }
//        }else if (aThis instanceof POS){
//            if(tabla.equals("DNVENDEDOR")){
//                Object elementos[][] = elemen.SQLSelect("SELECT VEN_CODIGO,VEN_NOMBRE FROM dnvendedor "+
//                                                        "WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "VEN_NOMBRE LIKE '%"+nombre+"%' ");
//            
//                element = elementos;
//            }
//        }else if (aThis instanceof ReporteStock) {
//            if (tabla.equals("DNPRODUCTO")){
//                Object elementos[][] = elemen.SQLSelect("SELECT PRO_CODIGO,PRO_NOMBRE,PRO_MARCA FROM dnproducto "+
//                                                        "WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "PRO_NOMBRE LIKE '%"+nombre+"%'");
//                
//                element = elementos;
//            }
//        }else if (aThis instanceof ReporteCodBarra) {
//            if (tabla.equals("DNPRODUCTO")){
//                Object elementos[][] = elemen.SQLSelect("SELECT PRO_CODIGO,PRO_NOMBRE,PRO_MARCA FROM dnproducto "+
//                                                        "WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "PRO_NOMBRE LIKE '%"+nombre+"%'");
//                
//                element = elementos;
//            }
//        }else if (aThis instanceof UnidadMedida){
//            if(tabla.equals("DNUNDMEDIDA")){
//                Object elementos[][] = elemen.SQLSelect("SELECT MED_CODIGO,MED_DESCRI FROM dnundmedida "+
//                                                        //"WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "WHERE MED_DESCRI LIKE '%"+nombre+"%'");
//                
//                element = elementos;
//            }
//        }else if (aThis instanceof BancosInsPagos){
//            if(tabla.equals("DNBANCOS")){
//                Object elementos[][] = elemen.SQLSelect("SELECT BAN_CODIGO,BAN_DESCRI FROM dnbancos "+
//                                                        "WHERE BAN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "BAN_DESCRI LIKE '%"+nombre+"%'");
//                
//                element = elementos;
//            }else if(tabla.equals("DNINSTRUMENTOPAGO")){
//                Object elementos[][] = elemen.SQLSelect("SELECT INS_CODIGO,INS_DESCRI FROM dninstrumentopago \n"+
//                                                        "WHERE (INS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' OR ISNULL(INS_EMPRESA)) AND \n"+
//                                                        "INS_DESCRI LIKE '%"+nombre+"%' AND pais='"+VarGlobales.getPais()+"'");
//                
//                element = elementos;
//            }
//        }else if (aThis instanceof Jornadas){
//            Object elementos[][] = elemen.SQLSelect("SELECT DISTINCT JOR_CODIGO,JOR_DESCRI FROM dnjornada "+
//                                                    "WHERE JOR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "JOR_DESCRI LIKE '%"+nombre+"%'");
//                
//            element = elementos;
//        }else if(aThis instanceof Cargos){
//            Object elementos[][] = elemen.SQLSelect("SELECT CAR_CODIGO,CAR_DESCRI FROM adminconfigestableerp.dncargos "+
//                                                    "WHERE CAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "CAR_DESCRI LIKE '%"+nombre+"%'");
//                
//            element = elementos;
//        }else 
        if (aThis instanceof CreaUsuarios){
            Object elementos[][] = elemen.SQLSelect("SELECT DISTINCT dnusuarios.ope_numero,OPE_USUARIO FROM dnusuarios\n" +
                                                    "inner join relac_usuario_grupo_permisos on relac_usuario_grupo_permisos.ope_numero=dnusuarios.ope_numero\n" +
                                                    "WHERE emp_codigo='"+VarGlobales.getCodEmpresa()+"' AND OPE_USUARIO LIKE '%"+nombre+"%';");
                
            element = elementos;
//        }
//        else if(aThis instanceof CreaProveedor){
//            Object elementos[][] = elemen.SQLSelect("SELECT MAE_CODIGO,MAE_NOMBRE FROM dnmaestro "+
//                                                    "WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "MAE_NOMBRE LIKE '%"+nombre+"%'");
//                
//            element = elementos;
        }else if(aThis instanceof Empresas){
            Object elementos[][] = elemen.SQLSelect("SELECT EMP_CODIGO,EMP_NOMBRE FROM adminconfigestableerp.dnempresas "+
                                                    "WHERE EMP_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                                                    "EMP_NOMBRE LIKE '%"+nombre+"%'");
                
            element = elementos;
//        }
//        else if (aThis instanceof Marcas){
//                Object elementos[][] = elemen.SQLSelect("SELECT MAR_CODIGO,MAR_DESCRI FROM dnmarca "+
//                                                        "WHERE MAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "MAR_DESCRI LIKE '%"+nombre+"%' AND mar_vehiculo=0");
//            // System.out.println("PRINT CONTROLADOR BUSCAR MAESTRO METODO CARGA TABLA: "+element);
//                element = elementos;
//        }else if (aThis instanceof MarcasVehiculos){
//                Object elementos[][] = elemen.SQLSelect("SELECT MAR_CODIGO,MAR_DESCRI FROM dnmarca "+
//                                                        "WHERE MAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "MAR_DESCRI LIKE '%"+nombre+"%' AND mar_vehiculo=1");
//            // System.out.println("PRINT CONTROLADOR BUSCAR MAESTRO METODO CARGA TABLA: "+element);
//                element = elementos;
//        }else if (aThis instanceof ModeloVehiculo){
//            switch(tabla){
//                case "DNMARCA":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT MAR_CODIGO,MAR_DESCRI FROM dnmarca "+
//                                                            "WHERE MAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "MAR_DESCRI LIKE '%"+nombre+"%' AND mar_vehiculo=1");
//                    element = elementos;
//                    
//                    break;
//                }
//                case "MODELO_VEHICULO":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT codigoMarca,codigo,descri FROM modelo_vehiculo "+
//                                                            "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "descri LIKE '%"+nombre+"%'");
//                    element = elementos;
//                    
//                    break;
//                }
//                case "MODELO_VEHICULO_2":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT codigo,descri,codigoMarca FROM modelo_vehiculo "+
//                                                            "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "descri LIKE '%"+nombre+"%'");
//                    element = elementos;
//                    
//                    break;
//                }
//            }
//        }else if (aThis instanceof CategoriasVehiculos){
//            Object elementos[][] = elemen.SQLSelect("SELECT codigo,descri FROM categorias_vehiculo "+
//                                                    "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "descri LIKE '%"+nombre+"%'");
//            element = elementos;    
//        }else if (aThis instanceof UbicacionProductos){
//                Object elementos[][] = elemen.SQLSelect("SELECT UBI_CODIGO,UBI_DESCRI FROM DNUBICACION_PRODUCTOS "+
//                                                        "WHERE UBI_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "UBI_DESCRI LIKE '%"+nombre+"%'");
//             //System.out.println("PRINT CONTROLADOR BUSCAR MAESTRO METODO CARGA TABLA: "+element);
//                element = elementos;
//        }else if (aThis instanceof CambiarClave){
//            Object elementos[][] = elemen.SQLSelect("SELECT OPE_NUMERO,OPE_NOMBRE FROM dnusuarios "+
//                                                        "WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "OPE_NOMBRE LIKE '%"+nombre+"%'");
//            element = elementos;
        }
//        else if (aThis instanceof ComisionVendedor){
//            switch(tabla){
//                case "DNVENDEDOR":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT VEN_CODIGO,VEN_NOMBRE FROM dnvendedor "+
//                                                            "WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "VEN_NOMBRE LIKE '%"+nombre+"%'");
//                    element = elementos;
//                    
//                    break;
//                }
//                case "DNVENDEDOR2":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT VEN_CODIGO,VEN_NOMBRE FROM dnvendedor "+
//                                                            "WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "VEN_NOMBRE LIKE '%"+nombre+"%'");
//                    element = elementos;
//                    
//                    break;
//                }
//            }
//        }else if(aThis instanceof GruposPermisos){
//            Object elementos[][] = elemen.SQLSelect("SELECT PER_ID,PER_NOMBRE FROM dnpermiso_grupal "+
//                                                    "WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "PER_NOMBRE LIKE '%"+nombre+"%'");
//            element = elementos;
////        }else if(aThis instanceof Vendedor){
////            Object elementos[][] = elemen.SQLSelect("SELECT VEN_CODIGO,VEN_NOMBRE FROM dnvendedor "+
////                                                    "WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
////                                                    "VEN_NOMBRE LIKE '%"+nombre+"%'");
////            element = elementos;
//        }else if(aThis instanceof Precio){
//            switch(tabla){
//                case "DNPRODUCTO":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT PRO_CODIGO,PRO_NOMBRE FROM dnproducto "+
//                                                            "WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "PRO_NOMBRE LIKE '%"+nombre+"%'");
//                    element = elementos;
//                    break;
//                }
//                case "DNLISTPRE":{
////                    Object elementos[][] = elemen.SQLSelect("SELECT LIS_CODIGO,LIS_DESCRI FROM dnlistpre "+
////                                                            "WHERE LIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
////                                                            "LIS_DESCRI LIKE '%"+nombre+"%'");
//                    Object elementos[][] = elemen.SQLSelect("SELECT LIS_CODIGO,LIS_DESCRI FROM dnlistpre "+
//                                                            "WHERE LIS_DESCRI LIKE '%"+nombre+"%'");
//                    element = elementos;
//                    break;
//                }
//                case "DNUNDMEDIDA":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT MED_CODIGO,MED_DESCRI FROM dnundmedida "+
//                                                            //"WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "WHERE MED_DESCRI LIKE '%"+nombre+"%'");
//                    element = elementos;
//                    break;
//                }
//                case "DNPRECIO":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT PRE_CODIGO,PRE_CODPRO,PRE_CODLIS FROM dnprecio "+
//                                                            "WHERE PRE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "PRE_CODIGO LIKE '%"+nombre+"%'");
//                    element = elementos;
//                    break;
//                }
//            }
//        }else if(aThis instanceof TipoDocumentos){
//            Object elementos[][] = elemen.SQLSelect("SELECT DOC_CODIGO,DOC_DESCRI FROM dndocumentos "+
//                                                            "WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "DOC_DESCRI LIKE '%"+nombre+"%'");
//            element = elementos;
//        }else if(aThis instanceof AsientoManual){
//            switch(tabla){
//                case "DNPERSONAS":{
//                    if(modelAsientoManual.getlCxC()){
//                        Object elementos[][] = elemen.SQLSelect("SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas "+
//                                                                "WHERE nombre LIKE '%"+nombre+"%' OR razon_social LIKE '%"+nombre+"%'");
//                    
//                        element = elementos;
//                    }
//                    if(modelAsientoManual.getlCxP()){
//                        Object elementos[][] = elemen.SQLSelect("SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas "+
//                                                                "WHERE nombre LIKE '%"+nombre+"%' OR razon_social LIKE '%"+nombre+"%'");
//                    
//                        element = elementos;
//                    }
//                    break;
//                }
//            }
//        }else if(aThis instanceof ImprimirCheque){
//            Object elementos[][] = elemen.SQLSelect("SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas "+
//                                                    "WHERE razon_social LIKE '%"+nombre+"%' OR nombre LIKE '%"+nombre+"%'");
//                    
//            element = elementos;
//        }else if(aThis instanceof RetencionIVA){
//            Object elementos[][] = elemen.SQLSelect("SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas "+
//                                                    "WHERE razon_social LIKE '%"+nombre+"%' OR nombre LIKE '%"+nombre+"%' AND contri=1");
//                    
//            element = elementos;
//        }else if(aThis instanceof RetencionISLR){
//            switch(tabla){
//                case "DNPERSONAS":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas "+
//                                                            "WHERE razon_social LIKE '%"+nombre+"%' OR nombre LIKE '%"+nombre+"%'");
//                    
//                    element = elementos;
//                    break;
//                }
//                case "DNEMPRESAS":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT dnpersonas.pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas "+
//                                                            "INNER JOIN adminconfigestableerp.dnempresas ON dnpersonas.pers_id = adminconfigestableerp.dnempresas.pers_id "+
//                                                            "WHERE razon_social LIKE '%"+nombre+"%' OR nombre LIKE '%"+nombre+"%'");
//                    
//                    element = elementos;
//                    break;
//                }
//            }
//        }else if(aThis instanceof reporteISLRlote){
//            Object elementos[][] = elemen.SQLSelect("SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas "+
//                                                    "WHERE razon_social LIKE '%"+nombre+"%' OR nombre LIKE '%"+nombre+"%'");
//                    
//            element = elementos;
//        }else if(aThis instanceof RetencionIVAventas){
//            Object elementos[][] = elemen.SQLSelect("SELECT dnpersonas.pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre "+
//                                                    "FROM dnpersonas "+
//                                                    "INNER JOIN adminconfigestableerp.dnempresas ON dnpersonas.pers_id = adminconfigestableerp.dnempresas.pers_id "+
//                                                    "WHERE razon_social LIKE '%"+nombre+"%' OR nombre LIKE '%"+nombre+"%'");
//                    
//            element = elementos;
//        }else if(aThis instanceof PagoDocs){
//            Object elementos[][] = elemen.SQLSelect("SELECT dnpersonas.pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre "+
//                                                    "FROM dnpersonas "+
//                                                    "WHERE razon_social LIKE '%"+nombre+"%' OR nombre LIKE '%"+nombre+"%'");
//                    
//            element = elementos;
//        }else if(aThis instanceof Proveedores){
//            Object elementos[][] = elemen.SQLSelect("select DISTINCT pers_id,codigo,razon_social,condic,rif,contri,tip_per,\n" +
//                                                    "                ret_iva,observaciones,rol_nombre\n" +
//                                                    "from rel_people_roles\n" +
//                                                    "inner join dnpersonas on person_father_id = pers_id\n" +
//                                                    "inner join adminconfigestableerp.dnrol on adminconfigestableerp.dnrol.rol_id=person_rol_father\n" +
//                                                    "WHERE (razon_social LIKE '%"+nombre+"%' OR razon_social LIKE '%"+nombre.toUpperCase()+"%') and "+
//                                                    "person_rol_father = "+tipomae);
//                    
//            element = elementos;
//        }else if(aThis instanceof ReporteRetIVAventas){
//            switch(tabla){
//                case "DNEMPRESAS":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT emp_codigo,emp_nombre "+
//                                                    "FROM adminconfigestableerp.dnempresas "+
//                                                    "WHERE emp_nombre LIKE '%"+nombre+"%'");
//                    element = elementos;
//                    break;
//                }
//                case "DNPERSONAS":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT dnpersonas.pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre "+
//                                                    "FROM dnpersonas "+
//                                                    "WHERE razon_social LIKE '%"+nombre+"%' OR nombre LIKE '%"+nombre+"%'");
//                    element = elementos;
//                    break;
//                }
//            }
//        }else if(aThis instanceof Tecnico){
//            Object elementos[][] = elemen.SQLSelect("SELECT tec_codigo,tec_nombre,tec_cedula "+
//                                                    "FROM tecnico "+
//                                                    "WHERE tec_nombre LIKE '%"+nombre+"%'");
//            element = elementos;
//        }
            
        return element;
    }
    
    public Object[][] actionTabla(String codigo, String nombre, Object aThis, String tabla){
        Object element[][] = null;
        SQLSelect elemen = new SQLSelect();
        
//        if (aThis instanceof Categorias) {
//            if (tabla.equals("DNCLASIFICACION")){
//                Object elementos[][] = elemen.SQLSelect("SELECT CLA_ID_CATEG,CLA_NOMBRE FROM dnclasificacion "+
//                                                        "WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "CLA_ID_CATEG='"+codigo+"' AND CLA_NOMBRE='"+nombre+"'");
//            
//                element = elementos;
//            }
//            if (tabla.equals("DNCLASIFICACION_PADRE")){
//                Object elementos[][] = elemen.SQLSelect("SELECT CLA_ID_CATEG,CLA_NOMBRE FROM dnclasificacion "+
//                                                        "WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "CLA_ID_CATEG='"+codigo+"' AND CLA_NOMBRE='"+nombre+"' AND "+
//                                                        "SUB_CLA_ID='0'");
//            
//                element = elementos;
//            }
//            if (tabla.equals("DNCLASIFICACION_HIJO")){
//                Object elementos[][] = elemen.SQLSelect("SELECT CLA_ID_CATEG,CLA_NOMBRE FROM dnclasificacion "+
//                                                        "WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "CLA_ID_CATEG='"+codigo+"' AND CLA_NOMBRE='"+nombre+"' AND "+
//                                                        "SUB_CLA_ID<>'0'");
//            
//                element = elementos;
//            }
//        }else if (aThis instanceof Productos) {
//            if (tabla.equals("DNMARCA")){
//                Object elementos[][] = elemen.SQLSelect("SELECT MAR_CODIGO,MAR_DESCRI FROM dnmarca "+
//                                                        "WHERE MAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "MAR_CODIGO='"+codigo+"' AND MAR_DESCRI='"+nombre+"' AND mar_vehiculo=0");
//            
//                element = elementos;
//            }
//            if (tabla.equals("dnpersonas")){
//                Object elementos[][] = elemen.SQLSelect("SELECT PERS_ID,IF(NOMBRE IS NULL,RAZON_SOCIAL,IF(nombre='',razon_social,nombre)) AS NOMBRE,RIF FROM dnpersonas "+
//                                                        "WHERE PERS_ID='"+codigo+"' AND "+
//                                                        "(NOMBRE LIKE '%"+nombre+"%' OR RAZON_SOCIAL LIKE '%"+nombre+"%')");
//            
//                element = elementos;
//            }
////        }else if (aThis instanceof DescuentosArticulos) {
////            if (tabla.equals("DNPRODUCTO")){
////                Object elementos[][] = elemen.SQLSelect("SELECT PRO_CODIGO,PRO_NOMBRE,PRO_MARCA FROM dnproducto "+
////                                                        "WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
////                                                        "PRO_CODIGO='"+codigo+"' AND PRO_NOMBRE LIKE '%"+nombre+"%'");
////                
////                element = elementos;
////            }
////            if (tabla.equals("DNDESCUENTOS")){
////                Object elementos[][] = elemen.SQLSelect("SELECT DES_CODIGO,DES_DESCRI FROM dndescuentos "+
////                                                        "WHERE DES_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
////                                                        "DES_CODIGO='"+codigo+"' AND DES_DESCRI LIKE '%"+nombre+"%'");
////                
////                element = elementos;
////            }
//        }else if (aThis instanceof Inventario) {
//            if(tabla.equals("DNPRODUCTO")){
//                Object elementos[][] = elemen.SQLSelect("SELECT PRO_CODIGO,PRO_NOMBRE,PRO_MARCA FROM dnproducto "+
//                                                        "WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "PRO_CODIGO='"+codigo+"' AND PRO_NOMBRE LIKE '%"+nombre+"%'");
//            
//                element = elementos;
//            }else if(tabla.equals("DNINVENTARIO")){
//                Object elementos[][] = elemen.SQLSelect("SELECT INV_NUMDOC, COUNT(*) AS ITEM FROM dninventario "+
//                                                        "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND INV_NUMDOC<>'' AND INV_NUMDOC='"+codigo+"' \n" +
//                                                        "GROUP BY INV_NUMDOC");
//
//                element = elementos;
//            }
//        }else if (aThis instanceof Facturacion){
//            System.err.println("Listar Documentos "+tabla);
//            if(tabla.equals("DNPERSONAS")){
//                Object elementos[][] = elemen.SQLSelect("SELECT PERS_ID,IF(NOMBRE IS NULL,RAZON_SOCIAL,IF(nombre='',razon_social,nombre)) AS NOMBRE,RIF FROM dnpersonas "+
//                                                        "WHERE PERS_ID='"+codigo+"' AND "+
//                                                        "(NOMBRE LIKE '%"+nombre+"%' OR RAZON_SOCIAL LIKE '%"+nombre+"%')");
//            
//                element = elementos;
//            }else if(tabla.equals("DNINVENTARIO")){
//                if(tipomae.equals("POS")){
//                    Object elementos[][] = elemen.SQLSelect("SELECT INV_NUMDOC,INV_FECHA FROM dninventario "+
//                                                            "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "INV_NUMDOC='"+codigo+"' AND INV_CODDOC='"+tipomae+"' AND "+
//                                                            "INV_STATUS='Vendido' AND "+
//                                                            "INV_IMPORT=0 AND "+
//                                                            "INV_CODMAE='"+proveedor+"' "+
//                                                            "GROUP BY INV_NUMDOC");
//                
//                    element = elementos;
//                }else{
//                    if(tipomae.equals("Doc")){
//                        String tipDoc = proveedor; //el nombre de la variable en este caso recibe otro valor cuando se
//                                                   // llama desde el fomulario de Facturacion (Documentos)
//                        
//                        Object elementos[][] = elemen.SQLSelect("SELECT INV_NUMDOC,dnpersonas.razon_social FROM dninventario \n" +
//                                                                "INNER JOIN dnpersonas ON dnpersonas.pers_id=dninventario.pers_id \n"+
//                                                                "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                                "INV_NUMDOC='"+codigo+"' AND inv_coddoc='"+tipDoc+"' \n"+
//                                                                "GROUP BY INV_NUMDOC \n"+
//                                                                "ORDER BY CAST(REPLACE(INV_NUMDOC,'-','') AS DECIMAL)");
//                        
//                        element = elementos;
//                    }else{
//                        Object elementos[][] = elemen.SQLSelect("SELECT INV_NUMDOC,INV_FECHA FROM dninventario "+
//                                                                "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                                "INV_NUMDOC='"+codigo+"' AND INV_CODDOC='"+tipomae+"' AND "+
//                                                                "INV_IMPORT=0 AND "+
//                                                                "INV_CODMAE='"+proveedor+"' "+
//                                                                "GROUP BY INV_NUMDOC");
//                        
//                        element = elementos;
//                    }
//                }
//            }else if(tabla.equals("DNCONFIGIMPORT")){
//                Object elementos[][] = elemen.SQLSelect("SELECT DOC_CODIGO,DOC_DESCRI FROM dndocumentos "+
//                                                        "WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "DOC_CODIGO='"+codigo+"'");
//                
//                element = elementos;
//            }else if(tabla.equals("import")){
//                String idRol = "";
//                
//                if(modelDocumentos.iscCxC()){
//                    idRol="2";
//                }
//                if(modelDocumentos.iscCxP()){
//                    idRol="3";
//                }
//                
//                if (modelDocumentos.getcTipDoc().equals("DEV") || modelDocumentos.getcTipDoc().equals("DVC")){
//                    Object elementos[][] = elemen.SQLSelect("SELECT dnpersonas.pers_id,IF(NOMBRE IS NULL,RAZON_SOCIAL,IF(nombre='',razon_social,nombre)) AS NOMBRE,RIF,"+
//                                                            "inv_numdoc FROM dnpersonas \n" +
//                                                            "INNER JOIN rel_people_roles on person_father_id = pers_id\n" +
//                                                            "INNER JOIN adminconfigestableerp.dnrol on adminconfigestableerp.dnrol.rol_id=person_rol_father\n" +
//                                                            "INNER JOIN dninventario ON dninventario.pers_id=dnpersonas.pers_id AND "+
//                                                            (modelDocumentos.iscCxC() ? "dninventario.inv_coddoc='FAV'" : "dninventario.inv_coddoc='FAC'")+"\n"+
//                                                            "WHERE id_company = '"+VarGlobales.getCodEmpresa()+"' AND person_rol_father = "+idRol+" AND \n" +
//                                                            "inv_numdoc='"+codigo+"' "+
//                                                            "HAVING nombre IS NOT NULL ORDER BY NOMBRE;"); 
//                    
//                    element = elementos;
//                }else{
//                    
//                }
//            }else if (tabla.equals("DNPRODUCTO")){
//                Object elementos[][] = elemen.SQLSelect("SELECT PRO_CODIGO,PRO_NOMBRE,PRO_MARCA FROM dnproducto "+
//                                                        "WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "PRO_CODIGO='"+codigo+"' AND PRO_NOMBRE LIKE '%"+nombre+"%'");
//                
//                element = elementos;
//            }
//        }else if (aThis instanceof CuentasPorCobrarPagar){
//            System.err.println("Listar Documentos "+tabla);
//            if(tabla.equals("DNPERSONAS")){
//                Object elementos[][] = elemen.SQLSelect("SELECT PERS_ID,IF(NOMBRE IS NULL,RAZON_SOCIAL,IF(nombre='',razon_social,nombre)) AS NOMBRE,RIF FROM dnpersonas "+
//                                                        "WHERE PERS_ID='"+codigo+"' AND "+
//                                                        "(NOMBRE LIKE '%"+nombre+"%' OR RAZON_SOCIAL LIKE '%"+nombre+"%')");
//            
//                element = elementos;
//            }
//        }else if (aThis instanceof VisualizarCbteElectronicos){
//            if(tabla.equals("DNPERSONAS")){
//                Object elementos[][] = elemen.SQLSelect("SELECT DISTINCT id_fiscal_receptor,dnpersonas.razon_social FROM status_cbtes_electronicos \n" +
//                                                        "INNER JOIN dnpersonas ON rif=id_fiscal_receptor \n"+
//                                                        "WHERE id_fiscal_receptor ='"+codigo+"' AND (NOMBRE LIKE '%"+nombre+"%' OR RAZON_SOCIAL LIKE '%"+nombre+"%') ");
//            
//                element = elementos;
//            }
//        }else if (aThis instanceof ReporteVentas){
//            if(tabla.equals("DNVENDEDOR")){
//                Object elementos [][] = elemen.SQLSelect("SELECT VEN_CODIGO,VEN_NOMBRE FROM DNVENDEDOR "+
//                                                         "WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                         "VEN_NOMBRE LIKE '%"+nombre+"%' AND VEN_CODIGO='"+codigo+"' AND "+
//                                                         "VEN_ACTIVO=1 "+
//                                                         " UNION "+
//                                                         "SELECT OPE_NUMERO,OPE_NOMBRE FROM DNUSUARIOS "+
//                                                         "WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                         "OPE_NOMBRE LIKE '%"+nombre+"%' AND OPE_NUMERO='"+codigo+"' AND "+
//                                                         "OPE_STATUS='Activo' ORDER BY VEN_CODIGO");
//                
//                element = elementos;
//            }
//        }else if (aThis instanceof POS){
//            if(tabla.equals("DNVENDEDOR")){
//                Object elementos[][] = elemen.SQLSelect("SELECT VEN_CODIGO,VEN_NOMBRE FROM dnvendedor "+
//                                                        "WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "VEN_CODIGO='"+codigo+"' AND VEN_NOMBRE LIKE '%"+nombre+"%'");
//
//                element = elementos;
//            }
//        }else if (aThis instanceof ReporteStock) {
//            if (tabla.equals("DNPRODUCTO")){
//                Object elementos[][] = elemen.SQLSelect("SELECT PRO_CODIGO,PRO_NOMBRE,PRO_MARCA FROM dnproducto "+
//                                                        "WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "PRO_CODIGO='"+codigo+"' AND PRO_NOMBRE LIKE '%"+nombre+"%'");
//                
//                element = elementos;
//            }
//        }else if (aThis instanceof UnidadMedida){
//            if (tabla.equals("DNUNDMEDIDA")){
//                Object elementos[][] = elemen.SQLSelect("SELECT MED_CODIGO,MED_DESCRI FROM dnundmedida "+
//                                                        //"WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "WHERE MED_CODIGO='"+codigo+"' AND MED_DESCRI LIKE '%"+nombre+"%'");
//                
//                element = elementos;
//            }
////        }else if (aThis instanceof Impuestos){
////            Object elementos[][] = elemen.SQLSelect("SELECT tiva_codigo,tiva_descri FROM dntipiva "+
////                                                    "WHERE tiva_empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
////                                                    "tiva_codigo='"+codigo+"' AND tiva_descri LIKE '%"+nombre+"%'");
////                
////            element = elementos;
////        }else if (aThis instanceof TipoContacto){
////            Object elementos[][] = elemen.SQLSelect("SELECT tcon_codigo, tcon_descri FROM dntipcontacto "+
////                                                    "WHERE tcon_codigo='"+codigo+"' AND tcon_descri LIKE '%"+nombre+"%'");
////                
////            element = elementos;
////        }else if (aThis instanceof Moneda){
//////            Object elementos[][] = elemen.SQLSelect("SELECT mon_codigo, mon_nombre FROM dnmoneda WHERE mon_empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//////                                                    "mon_codigo='"+codigo+"' AND mon_nombre LIKE '%"+nombre+"%'");
////            Object elementos[][] = elemen.SQLSelect("SELECT mon_codigo, mon_nombre FROM dnmoneda WHERE "+
////                                                    "mon_codigo='"+codigo+"' AND mon_nombre LIKE '%"+nombre+"%'");
////                
////            element = elementos;
//        }else if (aThis instanceof BancosInsPagos){
//            if (tabla.equals("DNBANCOS")){
//                Object elementos[][] = elemen.SQLSelect("SELECT BAN_CODIGO,BAN_DESCRI FROM dnbancos "+
//                                                        "WHERE BAN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "BAN_CODIGO='"+codigo+"' AND BAN_DESCRI LIKE '%"+nombre+"%'");
//                
//                element = elementos;
//            }else if (tabla.equals("DNINSTRUMENTOPAGO")){
//                Object elementos[][] = elemen.SQLSelect("SELECT INS_CODIGO,INS_DESCRI FROM dninstrumentopago "+
//                                                        "WHERE (INS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' OR ISNULL(INS_EMPRESA)) AND \n"+
//                                                        "INS_CODIGO='"+codigo+"' AND INS_DESCRI LIKE '%"+nombre+"%' AND pais='"+VarGlobales.getPais()+"'");
//                
//                element = elementos;
//            }
//        }else if (tabla.equals("DNJORNADA")){
//            Object elementos[][] = elemen.SQLSelect("SELECT DISTINCT JOR_CODIGO,INS_DESCRI FROM dnjornada "+
//                                                    "WHERE JOR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "JOR_CODIGO='"+codigo+"' AND JOR_DESCRI LIKE '%"+nombre+"%'");
//                
//            element = elementos;
//        }else if (tabla.equals("DNUSUARIOS")){
//            Object elementos[][] = elemen.SQLSelect("SELECT OPE_NUMERO,OPE_NOMBRE FROM dnusuarios "+
//                                                    "WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "OPE_NUMERO='"+codigo+"' AND OPE_NOMBRE LIKE '%"+nombre+"%'");
//                
//            element = elementos;
//        }else if(aThis instanceof CreaProveedor){
//            Object elementos[][] = elemen.SQLSelect("SELECT MAE_CODIGO,MAE_NOMBRE FROM dnmaestro "+
//                                                    "WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "MAE_CODIGO='"+codigo+"' AND MAE_NOMBRE LIKE '%"+nombre+"%'");
//
//            element = elementos;
//        }else if(aThis instanceof Cargos){
//            Object elementos[][] = elemen.SQLSelect("SELECT CAR_CODIGO,CAR_DESCRI FROM adminconfigestableerp.dncargos "+
//                                                    "WHERE CAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "CAR_CODIGO='"+codigo+"' AND CAR_DESCRI LIKE '%"+nombre+"%'");
//
//            element = elementos;
//        }else 
        if(aThis instanceof Empresas){
            Object elementos[][] = elemen.SQLSelect("SELECT EMP_CODIGO,EMP_NOMBRE FROM adminconfigestableerp.dnempresas "+
                                                    "WHERE EMP_CODIGO='"+codigo+"' AND EMP_NOMBRE LIKE '%"+nombre+"%'");

            element = elementos;
//        }else if(aThis instanceof ReporteEtiquetasCajas){
//            Object elementos[][] = elemen.SQLSelect("SELECT INV_NUMDOC, COUNT(*) AS ITEM FROM dninventario "+
//                                                    "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND INV_NUMDOC<>'' AND INV_NUMDOC='"+codigo+"' \n" +
//                                                    "GROUP BY INV_NUMDOC");
//
//            element = elementos;
//        }else if (aThis instanceof RelacionDoc_BL) {
//            if(tabla.equals("DNMAESTRO")){
//                Object elementos[][] = elemen.SQLSelect("SELECT MAE_CODIGO,MAE_NOMBRE,MAE_RIF FROM dnmaestro "+
//                                                        "WHERE MAE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "MAE_CODIGO='"+codigo+"' AND MAE_NOMBRE LIKE '%"+nombre+"%'");
//            
//                element = elementos;
//            }else if(tabla.equals("DNINVENTARIO")){
//                Object elementos[][] = elemen.SQLSelect("SELECT INV_NUMDOC, COUNT(*) AS ITEM FROM dninventario "+
//                                                        "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND INV_NUMDOC<>'' AND INV_NUMDOC='"+codigo+"' \n" +
//                                                        "GROUP BY INV_NUMDOC");
//
//                element = elementos;
//            }else if(tabla.equals("DNRELACION_DOCUMENTOS")){
//                Object elementos[][] = elemen.SQLSelect("SELECT RELD_NUMREL,DOC_DESCRI,RELD_TIPDOC FROM dnrelacion_documentos \n" +
//                                                        "INNER JOIN dndocumentos ON DOC_CODIGO=RELD_TIPDOC \n"+
//                                                        "WHERE RELD_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND RELD_NUMREL='"+codigo+"' OR "+
//                                                        "RELD_NUMREL LIKE '%"+codigo+"%' GROUP BY RELD_NUMREL");
//
//                element = elementos;
//            }
//        }else if (aThis instanceof Marcas) {
//            Object elementos[][] = elemen.SQLSelect("SELECT MAR_CODIGO,MAR_DESCRI FROM dnmarca "+
//                                                    "WHERE MAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "MAR_CODIGO='"+codigo+"' AND MAR_DESCRI='"+nombre+"' AND mar_vehiculo=0");
//            
//            element = elementos;
//        }else if (aThis instanceof MarcasVehiculos) {
//            Object elementos[][] = elemen.SQLSelect("SELECT MAR_CODIGO,MAR_DESCRI FROM dnmarca "+
//                                                    "WHERE MAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "MAR_CODIGO='"+codigo+"' AND MAR_DESCRI='"+nombre+"' AND mar_vehiculo=1");
//            
//            element = elementos;
//        }else if (aThis instanceof ModeloVehiculo) {
//            switch(tabla){
//                case "DNMARCA":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT MAR_CODIGO,MAR_DESCRI FROM dnmarca "+
//                                                            "WHERE MAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "MAR_CODIGO='"+codigo+"' AND MAR_DESCRI='"+nombre+"' AND mar_vehiculo=1");
//                    element = elementos;
//
//                    break;
//                }
//                case "MODELO_VEHICULO":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT codigoMarca, codigo, descri FROM modelo_vehiculo "+
//                                                            "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "codigo='"+codigo+"' AND descri LIKE '%"+nombre+"%'");
//                    element = elementos;
//                    
//                    break;
//                }
//                case "MODELO_VEHICULO_2":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT codigo, descri, codigoMarca FROM modelo_vehiculo "+
//                                                            "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "codigo='"+codigo+"' AND descri LIKE '%"+nombre+"%'");
//                    element = elementos;
//                    
//                    break;
//                }
//            }
//        }else if (aThis instanceof Motor) {
//            switch(tabla){
//                case "MOTOR":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT codigo,nombre_motor FROM motor "+
//                                                            "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "codigo='"+codigo+"' AND nombre_motor='"+nombre+"' AND activo=1");
//                    element = elementos;
//
//                    break;
//                }
//                case "TIPO_MOTOR":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT codigo,nombre_tipo_motor FROM tipo_motor "+
//                                                            "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "codigo='"+codigo+"' AND nombre_tipo_motor='"+nombre+"' AND activo=1");
//                    element = elementos;
//                    
//                    break;
//                }
//                case "MARCA":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT MAR_CODIGO,MAR_DESCRI FROM dnmarca "+
//                                                            "WHERE MAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "MAR_CODIGO='"+codigo+"' AND MAR_DESCRI='"+nombre+"' AND mar_vehiculo=1");
//                    element = elementos;
//                    
//                    break;
//                }
//                case "TIPO_CILINDRADA":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT codigo,nombre_tipo_cilindrada FROM tipo_cilindrada "+
//                                                            "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "codigo='"+codigo+"' AND nombre_tipo_cilindrada='"+nombre+"' AND activo=1");
//                    element = elementos;
//                    
//                    break;
//                }
//                case "COMBUSTIBLE":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT codigo,nombre_combustible FROM combustible "+
//                                                            "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "codigo='"+codigo+"' AND nombre_combustible='"+nombre+"' AND activo=1");
//                    element = elementos;
//                    
//                    break;
//                }
//            }
//        }else if (aThis instanceof Vehiculo) {
//            switch(tabla){
//                case "VEHICULO":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT placa,codmotor FROM vehiculo "+
//                                                            "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "placa='"+codigo+"' AND codmotor='"+nombre+"'");
//                    element = elementos;
//
//                    break;
//                }
//                case "DNPERSONAS":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT RIF,IF(NOMBRE IS NULL,RAZON_SOCIAL,IF(nombre='',razon_social,nombre)) AS NOMBRE FROM dnpersonas "+
//                                                            "WHERE RIF='"+codigo+"' AND "+
//                                                            "(NOMBRE LIKE '%"+nombre+"%' OR RAZON_SOCIAL LIKE '%"+nombre+"%')");
//            
//                    element = elementos;
//                    
//                    break;
//                }
//                case "DNMARCA":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT MAR_CODIGO,MAR_DESCRI FROM dnmarca "+
//                                                            "WHERE MAR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "MAR_CODIGO='"+codigo+"' AND MAR_DESCRI='"+nombre+"' AND mar_vehiculo=1");
//                    element = elementos;
//
//                    break;
//                }
//                case "MODELO_VEHICULO":{
//                    try {
//                        ResultSet rsFiltroMarca = modelVehiculo.buscaMarcaVehiculoFiltro(modelVehiculo.getTfMarcaVeh().getText());
//                    
//                        Object elementos[][] = elemen.SQLSelect("SELECT codigo,descri,codigoMarca FROM modelo_vehiculo "+
//                                                                "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                                "descri LIKE '%"+nombre+"%'"+
//                                                                (rsFiltroMarca.getRow()==0 ? "" : "AND codigoMarca='"+rsFiltroMarca.getString("mar_codigo")+"'"));
//                    
//                        element = elementos;                    
//                    } catch (SQLException ex) {
//                        Logger.getLogger(ControladorBuscarMaestros.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    
//                    break;
//                }
//                case "CATEGORIAS_VEHICULO":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT codigo,descri FROM categorias_vehiculo "+
//                                                            "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "descri LIKE '%"+nombre+"%'");
//                    element = elementos;    
//                    
//                    break;
//                }
//                case "MOTOR":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT codigo,nombre_motor FROM motor "+
//                                                            "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "codigo='"+codigo+"' AND nombre_motor='"+nombre+"' AND activo=1");
//                    element = elementos;
//
//                    break;
//                }
//                case "TIPO_MOTOR":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT codigo,nombre_tipo_motor FROM tipo_motor "+
//                                                            "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "codigo='"+codigo+"' AND nombre_tipo_motor='"+nombre+"' AND activo=1");
//                    element = elementos;
//                    
//                    break;
//                }
//                case "TRANSMISION":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT codigo,nombre_transmision FROM transmision "+
//                                                            "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "codigo='"+codigo+"' AND nombre_transmision='"+nombre+"' AND activo=1");
//                    element = elementos;
//                    
//                    break;
//                }
//                case "TRACCION":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT codigo,nombre_traccion FROM traccion "+
//                                                            "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "codigo='"+codigo+"' AND nombre_traccion='"+nombre+"' AND activo=1");
//                    element = elementos;
//                    
//                    break;
//                }
//                case "TIPO_CILINDRADA":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT codigo,nombre_tipo_cilindrada FROM tipo_cilindrada "+
//                                                            "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "codigo='"+codigo+"' AND nombre_tipo_cilindrada='"+nombre+"' AND activo=1");
//                    element = elementos;
//                    
//                    break;
//                }
//                case "MODELO_CILINDRADA":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT codigo,nombre_modelo_cilindrada FROM modelo_cilindrada "+
//                                                            "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "codigo='"+codigo+"' AND nombre_modelo_cilindrada='"+nombre+"' AND activo=1");
//                    element = elementos;
//                    
//                    break;
//                }
//                case "COMBUSTIBLE":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT codigo,nombre_combustible FROM combustible "+
//                                                            "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "codigo='"+codigo+"' AND nombre_combustible='"+nombre+"' AND activo=1");
//                    element = elementos;
//                    
//                    break;
//                }
//            }
//        }else if (aThis instanceof OrdenReparacion) {
//            switch(tabla){
//                case "ORDE_REPARACION":{
//                    String tipDoc = proveedor; //el nombre de la variable en este caso recibe otro valor cuando se
//                                                   // llama desde el fomulario de Facturacion (Documentos)
//                                                   
//                    Object elementos[][] = elemen.SQLSelect("SELECT INV_NUMDOC,dnpersonas.razon_social FROM dninventario \n" +
//                                                            "INNER JOIN dnpersonas ON dnpersonas.pers_id=dninventario.pers_id \n"+
//                                                            "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "INV_NUMDOC='"+codigo+"' AND inv_coddoc='"+tipDoc+"' \n"+
//                                                            "GROUP BY INV_NUMDOC \n"+
//                                                            "ORDER BY CAST(REPLACE(INV_NUMDOC,'-','') AS DECIMAL)");
//                    element = elementos;
//
//                    break;
//                }
//                case "VEHICULO":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT placa,dnpersonas.razon_social FROM vehiculo \n" +
//                                                            "INNER JOIN dnpersonas ON dnpersonas.pers_id=vehiculo.codclien \n"+
//                                                            "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "placa='"+codigo+"' AND dnpersonas.razon_social='"+nombre+"'");
//                    element = elementos;
//
//                    break;
//                }
//                case "TECNICO":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT tec_codigo,tec_nombre FROM tecnico \n"+
//                                                    "WHERE tec_empresa = '"+VarGlobales.getCodEmpresa()+"' AND tec_codigo='"+codigo+"' AND "+
//                                                    "activo=1");
//                    element = elementos;
//
//                    break;
//                }
//                case "USUARIO":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT dnusuarios.ope_numero,ope_nombre FROM dnusuarios \n"+
//                                                            "inner join relac_usuario_grupo_permisos on relac_usuario_grupo_permisos.ope_numero=dnusuarios.ope_numero\n" +
//                                                            "WHERE emp_codigo = '"+VarGlobales.getCodEmpresa()+"' AND dnusuarios.ope_numero='"+codigo+"' AND "+
//                                                            "ope_activo=1");
//
//                    element = elementos;
//
//                    break;
//                }
//                case "import":{
//                    String tipDoc = proveedor; //el nombre de la variable en este caso recibe otro valor cuando se
//                                                   // llama desde el fomulario de Facturacion (Documentos)
//                                                   
//                    Object elementos[][] = elemen.SQLSelect("SELECT INV_NUMDOC,dnpersonas.razon_social FROM dninventario \n" +
//                                                            "INNER JOIN dnpersonas ON dnpersonas.pers_id=dninventario.pers_id \n"+
//                                                            "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "INV_NUMDOC='"+codigo+"' AND inv_coddoc='"+tipDoc+"' AND \n"+
//                                                            //"rif='"+modelOrdenReparacion.getCodPersona()+"' AND inv_import=0\n"+
//                                                            "inv_import=0\n"+
//                                                            "GROUP BY INV_NUMDOC \n"+
//                                                            "ORDER BY CAST(REPLACE(INV_NUMDOC,'-','') AS DECIMAL)");
//                    element = elementos;
//                    
//                    break;
//                }
//            }
//        }else if (aThis instanceof TipoCliPro) {
//            Object elementos[][] = elemen.SQLSelect("SELECT id_type_person,name FROM adminconfigestableerp.type_person "+
//                                                    "WHERE id_type_person="+codigo+" AND name LIKE '%"+nombre+"%' AND "+
//                                                    "pais='"+VarGlobales.getPais()+"' AND "+
//                                                    "id_rol="+modelTipoCliPro.getIdRol()+" ORDER BY id_type_person");
//            
//            element = elementos;
//        }else if (aThis instanceof TipoMotor) {
//            Object elementos[][] = elemen.SQLSelect("SELECT codigo,nombre_tipo_motor FROM tipo_motor "+
//                                                    "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "codigo='"+codigo+"' AND nombre_tipo_motor LIKE '%"+nombre+"%'");
//            
//            element = elementos;
//        }else if (aThis instanceof Transmision) {
//            Object elementos[][] = elemen.SQLSelect("SELECT codigo,nombre_transmision FROM transmision  "+
//                                                    "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "codigo='"+codigo+"' AND nombre_transmision LIKE '%"+nombre+"%'");
//            
//            element = elementos;
//        }else if (aThis instanceof Traccion) {
//            Object elementos[][] = elemen.SQLSelect("SELECT codigo,nombre_traccion FROM traccion "+
//                                                    "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "codigo='"+codigo+"' AND nombre_traccion LIKE '%"+nombre+"%'");
//            
//            element = elementos;
//        }else if (aThis instanceof TipoCilindrada) {
//            Object elementos[][] = elemen.SQLSelect("SELECT codigo,nombre_tipo_cilindrada FROM tipo_cilindrada "+
//                                                    "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "codigo='"+codigo+"' AND nombre_tipo_cilindrada LIKE '%"+nombre+"%'");
//            
//            element = elementos;
//        }else if (aThis instanceof ModeloCilindrada) {
//            Object elementos[][] = elemen.SQLSelect("SELECT codigo,nombre_modelo_cilindrada FROM modelo_cilindrada "+
//                                                    "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "codigo='"+codigo+"' AND nombre_modelo_cilindrada LIKE '%"+nombre+"%'");
//            
//            element = elementos;
//        }else if (aThis instanceof Combustible) {
//            Object elementos[][] = elemen.SQLSelect("SELECT codigo,nombre_combustible FROM combustible "+
//                                                    "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "codigo='"+codigo+"' AND nombre_combustible LIKE '%"+nombre+"%'");
//            
//            element = elementos;
//        }else if (aThis instanceof CategoriasVehiculos) {
//            Object elementos[][] = elemen.SQLSelect("SELECT codigo, descri FROM categorias_vehiculo "+
//                                                    "WHERE empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "codigo='"+codigo+"' AND descri LIKE '%"+nombre+"%'");
//            
//            element = elementos;
//        }else if (aThis instanceof UbicacionProductos) {
//            Object elementos[][] = elemen.SQLSelect("SELECT UBI_CODIGO,UBI_DESCRI FROM DNUBICACION_PRODUCTOS "+
//                                                    "WHERE UBI_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "UBI_CODIGO='"+codigo+"' AND UBI_DESCRI='"+nombre+"'");
//            
//            element = elementos;
//        }else if (aThis instanceof CambiarClave){
//            Object elementos[][] = elemen.SQLSelect("SELECT OPE_NUMERO,OPE_NOMBRE FROM dnusuarios "+
//                                                        "WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "OPE_NUMERO='"+codigo+"' AND OPE_NOMBRE='"+nombre+"'");
//            
//            element = elementos;
        }else if(aThis instanceof CreaUsuarios){
            Object elementos[][] = elemen.SQLSelect("SELECT DISTINCT dnusuarios.ope_numero,OPE_USUARIO FROM dnusuarios\n" +
                                                    "inner join relac_usuario_grupo_permisos on relac_usuario_grupo_permisos.ope_numero=dnusuarios.ope_numero\n" +
                                                    "WHERE emp_codigo='"+VarGlobales.getCodEmpresa()+"' AND OPE_NUMERO='"+codigo+"' AND OPE_NOMBRE LIKE '%"+nombre+"%';");
                
            element = elementos;
        }
//        else if(aThis instanceof ComisionVendedor){
//            switch(tabla){
//                case "DNVENDEDOR":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT VEN_CODIGO,VEN_NOMBRE FROM dnvendedor "+
//                                                            "WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "VEN_CODIGO='"+codigo+"' AND VEN_NOMBRE LIKE '%"+nombre+"%'");
//                    element = elementos;
//            
//                    break;
//                }
//                case "DNVENDEDOR2":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT VEN_CODIGO,VEN_NOMBRE FROM dnvendedor "+
//                                                            "WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "VEN_CODIGO='"+codigo+"' AND VEN_NOMBRE LIKE '%"+nombre+"%'");
//                    element = elementos;
//                    
//                    break;
//                }
//            }
//        }else if(aThis instanceof GruposPermisos){
//            Object elementos[][] = elemen.SQLSelect("SELECT PER_ID,PER_NOMBRE FROM dnpermiso_grupal "+
//                                                    "WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                    "PER_ID='"+codigo+"' AND PER_NOMBRE LIKE '%"+nombre+"%'");
//            element = elementos;
////        }else if(aThis instanceof Vendedor){
////            Object elementos[][] = elemen.SQLSelect("SELECT VEN_CODIGO,VEN_NOMBRE FROM dnvendedor "+
////                                                    "WHERE VEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
////                                                    "VEN_CODIGO='"+codigo+"' AND VEN_NOMBRE LIKE '%"+nombre+"%'");
////            element = elementos;
//        }else if(aThis instanceof Precio){
//            switch(tabla){
//                case "DNPRODUCTO":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT PRO_CODIGO,PRO_NOMBRE FROM dnproducto "+
//                                                            "WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "PRO_CODIGO='"+codigo+"' AND PRO_NOMBRE LIKE '%"+nombre+"%'");
//                    element = elementos;
//                    break;
//                }
//                case "DNLISTPRE":{
////                    Object elementos[][] = elemen.SQLSelect("SELECT LIS_CODIGO,LIS_DESCRI FROM dnlistpre "+
////                                                            "WHERE LIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
////                                                            "LIS_CODIGO='"+codigo+"' AND LIS_DESCRI LIKE '%"+nombre+"%'");
//                    Object elementos[][] = elemen.SQLSelect("SELECT LIS_CODIGO,LIS_DESCRI FROM dnlistpre "+
//                                                            "WHERE LIS_CODIGO='"+codigo+"' AND LIS_DESCRI LIKE '%"+nombre+"%'");
//                    element = elementos;
//                    break;
//                }
//                case "DNUNDMEDIDA":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT MED_CODIGO,MED_DESCRI FROM dnundmedida "+
//                                                            //"WHERE MED_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "WHERE MED_CODIGO='"+codigo+"' AND MED_DESCRI LIKE '%"+nombre+"%'");
//                    element = elementos;
//                    break;
//                }
//                case "DNPRECIO":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT PRE_CODIGO,PRE_CODPRO,PRE_CODLIS FROM dnprecio "+
//                                                            "WHERE PRE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "PRE_CODIGO='"+codigo+"'");
//                    element = elementos;
//                    break;
//                }
//            }
//        }else if(aThis instanceof TipoDocumentos){
//            Object elementos[][] = elemen.SQLSelect("SELECT DOC_CODIGO,DOC_DESCRI FROM dndocumentos "+
//                                                            "WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "DOC_CODIGO='"+codigo+"' AND DOC_DESCRI LIKE '%"+nombre+"%'");
//            element = elementos;
//        }else if(aThis instanceof ConfigAsientos){
//            Object elementos[][] = elemen.SQLSelect("SELECT CCONT_NUM_CONFIG,DOC_DESCRI,ccont_transaccion FROM dnconfig_contable \n"+
//                                                        "INNER JOIN dndocumentos ON CCONT_DOCUMENTO=DOC_CODIGO \n"+
//                                                        "WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "CCONT_NUM_CONFIG='"+codigo+"' AND "+
//                                                        "CCONT_ACTIVO=1 "+
//                                                        "GROUP BY CCONT_NUM_CONFIG");
//            element = elementos;
//        }else if(aThis instanceof AsientoManual){
//            switch(tabla){
//                case "DNINVENTARIO":{
//                    String tipdoc = modelAsientoManual.getTipdoc();
//                    
//                    Object elementos[][] = elemen.SQLSelect("SELECT inv_numdoc,inv_coddoc,inv_codmae FROM dninventario \n"+
//                                                        "WHERE inv_empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                        "inv_numdoc='"+codigo+"' AND "+
//                                                        "inv_coddoc='"+tipdoc+"' AND "+
//                                                        "inv_activo=1 "+
//                                                        "GROUP BY inv_numdoc");
//                    element = elementos;
//                    break;
//                }
//                case "DNPERSONAS":{
//                    if(modelAsientoManual.getlCxC()){
//                        Object elementos[][] = elemen.SQLSelect("SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas \n"+
//                                                                "WHERE pers_id='"+codigo+"' AND "+
//                                                                "activo=1");
//                        element = elementos;
//                    }
//                    if(modelAsientoManual.getlCxP()){
//                        Object elementos[][] = elemen.SQLSelect("SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas \n"+
//                                                                "WHERE pers_id='"+codigo+"' AND "+
//                                                                "activo=1");
//                        element = elementos;
//                    }
//                    break;
//                }
//            }
//        }else if(aThis instanceof MaestroDeReportesSistema){
//            switch(tabla){
//                case "DNINVENTARIO":{
//                    String tipdoc = modelMaestroReportesSistema.getcTipDoc();
//
//                    Object elementos[][] = elemen.SQLSelect("SELECT INV_NUMDOC,dnpersonas.razon_social FROM dninventario \n" +
//                                                            "INNER JOIN dnpersonas ON dnpersonas.pers_id=dninventario.pers_id \n"+
//                                                            "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                            "INV_NUMDOC='"+codigo+"' "+
//                                                            (modelMaestroReportesSistema.getOrigen().equals("ReporteFacturasVencidas")  || 
//                                                                modelMaestroReportesSistema.getOrigen().equals("CuentasCobrar") || 
//                                                                    modelMaestroReportesSistema.getOrigen().equals("PagosAbonos") ? 
//                                                                        (modelMaestroReportesSistema.getOrigen().equals("PagosAbonos") ?
//                                                                                " AND (numdoc_orig IS NOT NULL) AND (inv_status='Pagado' OR inv_status='Abono') " : 
//                                                                            " AND (inv_status<>'Pagado' AND inv_status<>'Abono') ")
//                                                                        : " AND inv_coddoc='"+tipdoc+"' \n")+
//                                                            "GROUP BY INV_NUMDOC \n"+
//                                                            "ORDER BY CAST(REPLACE(INV_NUMDOC,'-','') AS DECIMAL)");
//                    
//                    element = elementos;
//                    break;
//                }
//                case "DNPERSONAS":{
//                    if(modelMaestroReportesSistema.iscCxC()){
//                        Object elementos[][] = elemen.SQLSelect("SELECT pers_id, razon_social AS nombre FROM dnpersonas \n"+
//                                                                "WHERE pers_id='"+codigo+"' AND "+
//                                                                "pers_activo=1");
//                        element = elementos;
//                    }
//                    if(modelMaestroReportesSistema.iscCxP()){
//                        Object elementos[][] = elemen.SQLSelect("SELECT pers_id, razon_social AS nombre FROM dnpersonas \n"+
//                                                                "WHERE pers_id='"+codigo+"' AND "+
//                                                                "pers_activo=1");
//                        element = elementos;
//                    }
//                    break;
//                }
//            }
//        }else if(aThis instanceof ImprimirCheque){
//            Object elementos[][] = elemen.SQLSelect("SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas \n"+
//                                                    "WHERE pers_id='"+codigo+"' AND "+
//                                                    "activo=1");
//            element = elementos;
//        }else if(aThis instanceof RetencionISLR){
//            switch(tabla){
//                case "DNPERSONAS":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas \n"+
//                                                            "WHERE pers_id='"+codigo+"' AND "+
//                                                            "activo=1");
//                    element = elementos;
//                    break;
//                }
//                case "DNEMPRESAS":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT dnpersonas.pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas \n"+
//                                                            "INNER JOIN adminconfigestableerp.dnempresas ON dnpersonas.pers_id = adminconfigestableerp.dnempresas.pers_id "+
//                                                            "WHERE dnpersonas.pers_id='"+codigo+"' AND "+
//                                                            "activo=1");
//                    element = elementos;
//                    break;
//                }
//            }
//        }else if(aThis instanceof reporteISLRlote){
//            Object elementos[][] = elemen.SQLSelect("SELECT pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre FROM dnpersonas \n"+
//                                                    "WHERE pers_id='"+codigo+"' AND "+
//                                                    "activo=1");
//            element = elementos;
//        }else if(aThis instanceof RetencionIVAventas){
//            Object elementos[][] = elemen.SQLSelect("SELECT dnpersonas.pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre "+
//                                                    "FROM dnpersonas \n"+
//                                                    "INNER JOIN adminconfigestableerp.dnempresas ON dnpersonas.pers_id = adminconfigestableerp.dnempresas.pers_id "+
//                                                    "WHERE dnpersonas.pers_id='"+codigo+"' AND "+
//                                                    "activo=1");
//            element = elementos;
//        }else if(aThis instanceof PagoDocs){
//            Object elementos[][] = elemen.SQLSelect("SELECT dnpersonas.pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre "+
//                                                    "FROM dnpersonas \n"+
//                                                    "WHERE dnpersonas.pers_id='"+codigo+"' AND "+
//                                                    "activo=1");
//            element = elementos;
//        }else if(aThis instanceof Proveedores){
//            Object elementos[][] = elemen.SQLSelect("select DISTINCT rif,razon_social,condic,rif,contri,tip_per,\n" +
//                                                    "                ret_iva,observaciones,rol_nombre\n" +
//                                                    "from rel_people_roles\n" +
//                                                    "inner join dnpersonas on person_father_id = pers_id\n" +
//                                                    "inner join adminconfigestableerp.dnrol on adminconfigestableerp.dnrol.rol_id=person_rol_father\n" +
//                                                    "WHERE rif='"+codigo+"' and person_rol_father = "+tipomae);
//            element = elementos;
//        }else if(aThis instanceof ReporteRetIVAventas){
//            switch(tabla){
//                case "DNEMPRESAS":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT emp_codigo,emp_nombre "+
//                                                            "FROM adminconfigestableerp.dnempresas \n"+
//                                                            "WHERE emp_codigo='"+codigo+"' AND "+
//                                                            "emp_activo=1");
//                    element = elementos;
//                    break;
//                }
//                case "DNPERSONAS":{
//                    Object elementos[][] = elemen.SQLSelect("SELECT dnpersonas.pers_id,IF(nombre IS NULL,razon_social,IF(nombre='',razon_social,nombre)) AS nombre "+
//                                                            "FROM dnpersonas \n"+
//                                                            "WHERE dnpersonas.pers_id='"+codigo+"' AND "+
//                                                            "activo=1");
//                    element = elementos;
//                    break;
//                }
//            }        
//        }else if(aThis instanceof Tecnico){
//            Object elementos[][] = elemen.SQLSelect("SELECT tec_codigo,tec_nombre FROM tecnico \n"+
//                                                    "WHERE tec_empresa = '"+VarGlobales.getCodEmpresa()+"' AND tec_codigo='"+codigo+"' AND "+
//                                                    "activo=1");
//            element = elementos;
//        }
        
        return element;
    }
    
    private void dimensionCeldas(int numCelda, String identificador1, String identificador2, String identificador3,
                                 Boolean lOculCel3){
        switch (numCelda){
            case 2:
                tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador1)).setMinWidth(130);
                tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador1)).setMaxWidth(130);
        
                tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador2)).setMinWidth(450);
                tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador2)).setMaxWidth(450);
                
                break;
            case 3:
                tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador1)).setMinWidth(130);
                tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador1)).setMaxWidth(130);
        
                if (lOculCel3==true){
                    tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador2)).setMinWidth(470);
                    tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador2)).setMaxWidth(470);
                }else{
                    tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador2)).setMinWidth(350);
                    tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador2)).setMaxWidth(350);
                }
        
                if (lOculCel3==true){
                    tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador3)).setMinWidth(0);
                    tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador3)).setMaxWidth(0);
                }else{
                    tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador3)).setMinWidth(130);
                    tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador3)).setMaxWidth(130);
                }
                
                break;
            case 4:
                tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador1)).setMinWidth(50);
                tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador1)).setMaxWidth(50);
        
                tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador2)).setMinWidth(430);
                tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador2)).setMaxWidth(430);
                
                tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador3)).setMinWidth(80);
                tableMaestro.getColumn(idioma.loadLangHeaderTable().getString(identificador3)).setMaxWidth(80);
                    
                tableMaestro.getColumn(idioma.loadLangHeaderTable().getString("documento")).setMinWidth(90);
                tableMaestro.getColumn(idioma.loadLangHeaderTable().getString("documento")).setMaxWidth(90);
                
                break;
        }
    }
}