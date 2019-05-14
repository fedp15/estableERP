package Controlador;

import Modelos.Bitacora;
import util.CargaTablas;
import util.SQLQuerys;
import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import util.CodigoConsecutivo;
import util.Internacionalizacion;
import util.ValidaCodigo;

public class ControladorCategorias extends conexion{
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final CargaTablas cargatabla = new CargaTablas();
    String ordenCateg="1";
    
    private ResultSet rs_codigo, rs_tree, rsId;

    public ControladorCategorias() {
        idioma.setLocale(VarGlobales.getIdioma());
    }

    public void cargaTabla(JTable tablaCateg, JTable tablaSubCateg, JTabbedPane jTabbedPane){
        String SQL = "";
        
        if (jTabbedPane.getSelectedIndex()==0){
            SQL = "SELECT CLA_ID_CATEG,CLA_NOMBRE,CLA_IMG FROM dnclasificacion WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND SUB_CLA_ID='0'";
            
            String[] columnas = {idioma.loadLangHeaderTable().getString("id"),idioma.loadLangHeaderTable().getString("nombre"),
                             idioma.loadLangHeaderTable().getString("img")};
        
            cargatabla.cargatablas(tablaCateg,SQL,columnas);
            tablaCateg.getSelectionModel().setSelectionInterval(tablaCateg.getRowCount()-1, tablaCateg.getRowCount()-1);
        }else{
            SQL = "SELECT CLA_ID_CATEG,CLA_NOMBRE, @claPadre:=SUB_CLA_ID AS SUB_CLA_ID," +
                  "(SELECT CLA_NOMBRE FROM dnclasificacion WHERE CLA_EMPRESA='000001' AND CLA_ID_CATEG=@claPadre LIMIT 1) AS CLA_PADRE " +
                  "FROM dnclasificacion WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND SUB_CLA_ID<>'0'";
            
            String[] columnas = {idioma.loadLangHeaderTable().getString("id"),idioma.loadLangHeaderTable().getString("nombre"),
                                 idioma.loadLangHeaderTable().getString("id"), idioma.loadLangHeaderTable().getString("categPadre")};
        
            cargatabla.cargatablas(tablaSubCateg,SQL,columnas);
            tablaSubCateg.getSelectionModel().setSelectionInterval(tablaSubCateg.getRowCount()-1, tablaSubCateg.getRowCount()-1);
        }
    }

    public String codConsecutivo(JTabbedPane jTabbedPane){
        String consecutivo = null;
                
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        consecutivo = codigo.CodigoConsecutivo("CLA_ID_CATEG","dnclasificacion",0,"");
        
        return consecutivo;
    }
    
    public ResultSet ejecutaHilo(String codigo, Boolean lAgregar, JTabbedPane jTabbedPane){
        try {
            String SQLCodDoc="", SQL="";
            
            if (jTabbedPane.getSelectedIndex()==0){
                SQLCodDoc = "SELECT CLA_ID_CATEG FROM dnclasificacion WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                            "SUB_CLA_ID='0' ORDER BY CLA_ID_CATEG DESC LIMIT 1 ";
            }else{
                SQLCodDoc = "SELECT CLA_ID_CATEG FROM dnclasificacion WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                            "SUB_CLA_ID<>'0' ORDER BY CLA_ID_CATEG DESC LIMIT 1 ";
            }
                
            rs_codigo = consultar(SQLCodDoc);
            try {
                //if(lAgregar==false){
                    codigo=rs_codigo.getString("CLA_ID_CATEG").trim();
                //}
            } catch (SQLException ex) {
                Logger.getLogger(ControladorCategorias.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (jTabbedPane.getSelectedIndex()==0){
                SQL = "SELECT * FROM dnclasificacion WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND CLA_ID_CATEG='"+codigo+"'";
            }else{
                SQL = "SELECT CLA_ID_CATEG,CLA_NOMBRE, @claPadre:=SUB_CLA_ID AS SUB_CLA_ID," +
                      "(SELECT CLA_NOMBRE FROM dnclasificacion WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                      "CLA_ID_CATEG=@claPadre LIMIT 1) AS CLA_PADRE,CLA_IMG " +
                      "FROM dnclasificacion WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND SUB_CLA_ID<>'0'"+
                      " AND CLA_ID_CATEG='"+codigo+"'";
            }

            try {
                rs = consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorCategorias.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void grabar(String codigo, String nombre, String rutaImgOrg, JTable tabla, Boolean lAgregar, JTabbedPane jTabbedPane,
                       String codSubCategoria) throws ClassNotFoundException, SQLException{
        String sqlInsert = "", sqlUpdate = "", tipo = "", ImagCat="", subClaId = "";
        
//        if (!rutaImg.equals("")){
//            tipo = rutaImg.substring(rutaImg.length()-4,rutaImg.length());
//            tipo = tipo.replace(".", "");
//            File carpeta = new File(System.getProperty("user.dir")+"/build/classes/fotos_categorias/");
//            carpeta.mkdirs();
//            
//            String destino = carpeta.getAbsolutePath()+"/"+nombre.trim()+"."+tipo.trim();
//        
//            if(!rutaImgOrg.equals("")){
//                if(tipo.trim().equals("jpg") || tipo.trim().equals("jpeg") || tipo.trim().equals("png") || tipo.trim().equals("bmp")){
//                    CopiarFicheros copiar = new CopiarFicheros();
//                   
//                    try {
//                        copiar.CopiarFicheros(rutaImgOrg,destino);
//                    } catch (IOException ex) {
//                        Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//            
//            ImagCat = nombre.trim()+"."+tipo.trim();
//        }else{
//            ImagCat = "";
//        }
        
        if (lAgregar==true){
            SQLQuerys insertar = new SQLQuerys();
            if (jTabbedPane.getSelectedIndex()==0){
                sqlInsert = "INSERT INTO dnclasificacion (CLA_EMPRESA,CLA_ID_CATEG,SUB_CLA_ID,CLA_NOMBRE,CLA_IMG) "+
                                                "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+
                                                          codigo+"','0','"+nombre+"','"+ImagCat+"');";
            }else{
                ordenCateg="1";
                String SQL = "SELECT CLA_ORDEN+1 AS CLA_ORDEN FROM dnclasificacion WHERE SUB_CLA_ID='"+codigo+"'";

                rs = consultar(SQL);
                if (rs.getRow()>0){
                    ordenCateg = rs.getString("CLA_ORDEN");
                }
                
                if(VarGlobales.isConsecutivoGrupoSubGrupo()){
                    subClaId = codigo+"."+ordenCateg;
                }else{
                    //subClaId = codigo+codSubCategoria;
                    subClaId = codSubCategoria;
                }
                
                sqlInsert = "INSERT INTO dnclasificacion (CLA_EMPRESA,CLA_ID_CATEG,SUB_CLA_ID,CLA_NOMBRE,CLA_ORDEN,CLA_IMG) "+
                                                "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+
                                                         subClaId+"','"+codigo+"','"+nombre+"',"+ordenCateg+","+
                                                         "'"+ImagCat+"');";
            }

            insertar.SqlInsert(sqlInsert);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Registro", "Se creo la Categoria ''"+nombre.toUpperCase()+"'' Correctamente");    
        }else if (lAgregar==false){
            SQLQuerys modificar = new SQLQuerys();
            
            if (jTabbedPane.getSelectedIndex()==0){
                sqlUpdate = "UPDATE dnclasificacion SET CLA_NOMBRE='"+nombre+"',CLA_IMG='"+ImagCat+"' "+
                            "WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND CLA_ID_CATEG='"+codigo+"'";
                
                //if (!VarGlobales.getCodEmpresa().equals("000001")){
                //    WsCategorias wsCategorias = new WsCategorias();
                //    wsCategorias.setParametros("ActualizaCategoria", "", "");
                //    wsCategorias.start();
                //}
            }else{
                String idCateg    = (String) tabla.getValueAt(tabla.getSelectedRow(),2);
                String idSubCateg = (String) tabla.getValueAt(tabla.getSelectedRow(),0);
                sqlUpdate = "UPDATE dnclasificacion SET SUB_CLA_ID='"+idCateg+"',CLA_NOMBRE='"+nombre+"',CLA_IMG='"+ImagCat+"' "+
                            "WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND CLA_ID_CATEG='"+idSubCateg+"'";
            }
            
            modificar.SqlUpdate(sqlUpdate);
            
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Modificación de Registro", "Se Modifico la Categoria ''"+nombre.toUpperCase()+"'' Correctamente");
        }
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        cargaTabla(tabla, tabla, jTabbedPane);
        //-------------------------------------------------------------------
    }

    public void delete(String codigo, String nombre, JTable tabla, JTabbedPane jTabbedPane) throws ClassNotFoundException, SQLException{
        int eliminado;
        String msj = "";
        
        int salir = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgEliminarCode")+" "+codigo+" ("+nombre+")?");
        
        if (jTabbedPane.getSelectedIndex()==0){
            msj = idioma.loadLangComponent().getString("lbTituloFormCategorias");
        }else{
            msj = idioma.loadLangComponent().getString("lbTituloFormSubCategorias");
        }   
        
        String sqlCategProduc = "SELECT * FROM dnproducto WHERE PRO_CATEGORIAS='"+codigo+"'";
        rs = consultar(sqlCategProduc);
        
        if (rs.getRow()==0){
            String SQL = "SELECT * FROM dnclasificacion WHERE SUB_CLA_ID='"+codigo+"'";
            rs = consultar(SQL);
        
            if (rs.getRow()==0){
                if ( salir == 0) {
                    SQLQuerys eliminar = new SQLQuerys();
                    eliminar.SqlDelete("DELETE FROM dnclasificacion WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND CLA_ID_CATEG='"+codigo+"'",
                                       msj, idioma.loadLangComponent().getString("lbTituloFormProductos"));

                    ejecutaHilo("", false, jTabbedPane);
                    cargaTabla(tabla, tabla, jTabbedPane);
                }
            }else{
                JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgEliminacionCategNegado1"),
                                          idioma.loadLangMsg().getString("MsgTituloNotif"), JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgEliminacionCategNegado2"),
                                          idioma.loadLangMsg().getString("MsgTituloNotif"), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public ResultSet menuTree(){
        try {
            String SQL = "SELECT CLA_ID_CATEG,CLA_NOMBRE,CLA_IMG FROM dnclasificacion WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND SUB_CLA_ID='0'";
            
            rs_tree = consultar(SQL);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs_tree;
    }
    
    public ResultSet subMenuTree(String codigo){
        try {
            String SQL = "SELECT CLA_ID_CATEG,CLA_NOMBRE,CLA_IMG FROM dnclasificacion \n"+
                         "WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND SUB_CLA_ID='"+codigo+"'";
            
            rs_tree = consultar(SQL);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs_tree;
    }
    
    public ResultSet idCategoria(String catPad, String subCat){
        String sqlId = "";
        try {
            String sql = "SELECT @Id:=CLA_ID_CATEG AS CLA_ID,SUB_CLA_ID,CLA_NOMBRE FROM DNCLASIFICACION "+
                         "WHERE CLA_NOMBRE='"+catPad+"'"+
                         "HAVING COUNT((SELECT COUNT(*) FROM DNCLASIFICACION WHERE CLA_NOMBRE='"+subCat+"' "+
                                       "AND SUB_CLA_ID=@Id)) > 0";

            rsId = consultar(sql);
            
            if(catPad.equals(subCat)){
                //sqlId = "SELECT * FROM dnclasificacion WHERE CLA_NOMBRE='"+subCat+"' AND SUB_CLA_ID='"+rsId.getString("SUB_CLA_ID")+"'";
                sqlId = "SELECT cla_id,@idCateg:=sub_cla_id AS codGrupo,(SELECT cla_nombre FROM dnclasificacion WHERE cla_id_categ=@idCateg) AS nombGrupo,\n" +
                        "       cla_id_categ as codSubGrupo,cla_nombre AS nombSubGrupo,cla_orden FROM dnclasificacion \n"+
                        "WHERE CLA_NOMBRE='"+subCat+"' AND SUB_CLA_ID='"+rsId.getString("SUB_CLA_ID")+"';";
            }else{
                //sqlId = "SELECT * FROM dnclasificacion WHERE CLA_NOMBRE='"+subCat+"' AND SUB_CLA_ID='"+rsId.getString("CLA_ID")+"'";
                sqlId = "SELECT cla_id,@idCateg:=sub_cla_id AS codGrupo,(SELECT cla_nombre FROM dnclasificacion \n"+
                        "       WHERE sub_cla_id='"+rsId.getString("SUB_CLA_ID")+"' AND cla_id_categ=@idCateg) AS nombGrupo,\n" +
                        "       cla_id_categ, cla_nombre,cla_orden FROM dnclasificacion \n"+
                        "WHERE CLA_NOMBRE='"+subCat+"' AND SUB_CLA_ID='"+rsId.getString("CLA_ID")+"';";
            }

            rsId = consultar(sqlId);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ControladorCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rsId;
    }
    
//************************************ Para el Menu Tree Pricipal ************************************
    public ResultSet menuTreePrincipal(){
        try {
            //String SQL = "SELECT CLA_ID_CATEG,CLA_NOMBRE,CLA_IMG FROM dnclasificacion WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND SUB_CLA_ID='0'";
            String SQL = "SELECT MEN_ID,SUB_MEN_ID,MEN_NOMBRE,MEN_DESCRIPCION,MEN_URL,MEN_ORDEN,MEN_TIPO, \n"+
                         "MEN_IMG,MEN_EXTERNO,MEN_ESTMENU FROM DNPERMISOLOGIA \n"+
                         "INNER JOIN DNMENU ON MIS_MENU=MEN_ID \n"+
                         "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+VarGlobales.getGrupPermiso()+" AND \n"+
                         "SUB_MEN_ID=0 AND MEN_TIPO=2 AND MIS_ACTIVO='1' AND MEN_ESTMENU=1 ORDER BY MEN_ORDEN ASC;";
            
            rs_tree = consultar(SQL);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs_tree;
    }
    
    public ResultSet subMenuTreePrincipal(String codigo){
        try {
            //String SQL = "SELECT CLA_ID_CATEG,CLA_NOMBRE,CLA_IMG FROM dnclasificacion WHERE CLA_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND SUB_CLA_ID='"+codigo+"'";
            String SQL = "SELECT MEN_ID,SUB_MEN_ID,MEN_NOMBRE,MEN_DESCRIPCION,MEN_URL,MEN_ORDEN,MEN_TIPO, \n"+
                         "MEN_IMG,MEN_EXTERNO,MEN_ESTMENU FROM DNPERMISOLOGIA \n"+
                         "INNER JOIN DNMENU ON MIS_MENU=MEN_ID \n"+
                         "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND SUB_MEN_ID="+codigo+" AND \n"+
                         "MIS_PERMISO="+VarGlobales.getGrupPermiso()+" AND MEN_TIPO=2 AND MIS_ACTIVO='1' AND MEN_ESTMENU=1 ORDER BY MEN_ORDEN ASC;";
            
            rs_tree = consultar(SQL);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs_tree;
    }
    
    public int validaCodigo(String codigoGrupo, String codigoSubGrupo, String org){
        int existe = 0;
        String sql = "";
        
        if (org.equals("Grupo")){
            sql = "SELECT COUNT(*) AS REGISTROS FROM dnclasificacion WHERE cla_empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
                  "sub_cla_id = '0' AND cla_id_categ='"+codigoGrupo.toUpperCase()+"';";
        }else{
            sql = "SELECT COUNT(*) AS REGISTROS FROM dnclasificacion \n" +
                  "WHERE cla_empresa='"+VarGlobales.getCodEmpresa()+"' AND sub_cla_id='"+codigoGrupo.toUpperCase()+"' AND "+
                  "cla_id_categ='"+codigoSubGrupo.toUpperCase()+"';";
        }
        
        ValidaCodigo validar = new ValidaCodigo();
        existe = validar.ValidaCodigo(sql, true, "");
        
        return existe;
    }
}