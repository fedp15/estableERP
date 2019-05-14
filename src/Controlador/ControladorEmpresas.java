package Controlador;

import Modelos.Bitacora;
import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.io.File;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import stored_procedure.SpConfigEmpresa;
import util.CargaComboBox;
import util.CargaTablas;
import util.CodigoConsecutivo;
import util.Internacionalizacion;
import util.SQLQuerys;
import util.ValidaCodigo;

public class ControladorEmpresas extends conexion{
    private ResultSet rs, rs_count, rs_codigo, rs_opc_menus;;
    
    private final CargaTablas cargatabla = new CargaTablas();
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
//    private final ControladorGrupoPermisos controladorGrupoPermisos = new ControladorGrupoPermisos();
    //private final WsPermisologias wsPermisologias = WsPermisologias.getWsPermisologias();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    private final SpConfigEmpresa configEmpresa = SpConfigEmpresa.getSpConfigEmpresa();
    
    //conexion conet = new conexion();

    private int Reg_count;
    private String codigo="", nombre="", rif="", direccion="", activo="", tabla="", clase="", tipomenu="", telefono="",
                   correo="", moneda="", pais="", fax="", pagWeb="", tipoIdent, estado, municip, parroq, sector, empPredeterminada="";
    private Boolean lAgregar;
    private JTextField tfCodigo, tfNombre, tfRif, tfDireccion;
    private JCheckBox chkActivo;
    
    private CallableStatement cs = null;
    private PreparedStatement ps = null;
    
    public ControladorEmpresas(){
        
    }
    
    public String codConsecutivo(String origen,String empresa){
        String consecutivo = "";
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        if(origen.equals("empresa")){
            VarGlobales.setlBaseDatosConfiguracion(true);
            
            consecutivo = codigo.CodigoConsecutivo("EMP_CODIGO","DNEMPRESAS",6,"");
            if(consecutivo == null){
                consecutivo = "000001";
            }
        }else if(origen.equals("sucursal")){
            consecutivo = codigo.CodigoConsecutivo("SUC_CODIGO","DNSUCURSAL",3,"WHERE SUC_EMPRESA='"+empresa+"'");
            if(consecutivo == null){
                consecutivo = "001";
            }
        }
        
        System.out.println("CODIGO CONSECUTIVO "+consecutivo);
        VarGlobales.setlBaseDatosConfiguracion(false);
        
        return consecutivo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getCodPais(String nombPais){
        //String sql = "SELECT pai_id FROM dnpais WHERE pai_nombre LIKE '"+nombPais+"%';", codPais="";
        VarGlobales.setlBaseDatosConfiguracion(true);
        
        String sql = "SELECT pai_id FROM dnpais WHERE pai_abrpai LIKE '"+nombPais+"%';", codPais="";
        
        try {
            ResultSet rsCodPais = consultar(sql);
            
            if (rsCodPais.getRow()>0){
                codPais = rsCodPais.getString("pai_id");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ControladorEmpresas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        VarGlobales.setlBaseDatosConfiguracion(false);
        
        return codPais;
    }
    
    public void cargaTablas(JTable tabla, String origen, Boolean lSuc, String empresa){
        if(origen.equals("empresa")){
            VarGlobales.setlBaseDatosConfiguracion(true);
            
            String SQL = "SELECT EMP_CODIGO,EMP_NOMBRE,EMP_RIF FROM dnempresas";
            String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),idioma.loadLangHeaderTable().getString("id")};
            cargatabla.cargatablas(tabla,SQL,columnas);
        }else if(origen.equals("sucursal")){
            String SQL = "";
            if(lSuc){
                SQL = "SELECT SUC_EMPRESA,SUC_CODIGO,SUC_ID,SUC_NOMBRE FROM dnsucursal WHERE SUC_EMPRESA='"+empresa+"'";
            }else{
                SQL = "SELECT SUC_EMPRESA,SUC_CODIGO,SUC_ID,SUC_NOMBRE FROM dnsucursal";
            }
            String[] columnas = {idioma.loadLangHeaderTable().getString("empresa"),idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("id"),idioma.loadLangHeaderTable().getString("nombre")};
            cargatabla.cargatablas(tabla,SQL,columnas);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(1);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(1);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(1);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(45);
        }
        
        VarGlobales.setlBaseDatosConfiguracion(false);
    }
    
    public ResultSet ejecutaHilo(String codigo,Boolean lAgregar,String origen,String empresa){
        String sql = "";
        rs = null;
       
        try {
            //sql = "SELECT EMP_CODIGO FROM DNEMPRESAS WHERE EMP_CODIGO='"+VarGlobales.getCodEmpresa()+"' ORDER BY EMP_CODIGO DESC LIMIT 1";
            if(origen.equals("empresa")){
                //sql = "SELECT EMP_CODIGO FROM DNEMPRESAS ORDER BY EMP_CODIGO DESC LIMIT 1";
                VarGlobales.setlBaseDatosConfiguracion(true);
                
                this.codigo = codigo;
                creaConexion();
                sql = "{call sp_formEmpresas(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                
                callStoreProcedure(sql, "SELECT");
                
                VarGlobales.setlBaseDatosConfiguracion(false);
            }else if(origen.equals("sucursal")){
                sql = "SELECT SUC_CODIGO FROM dnsucursal WHERE SUC_EMPRESA='"+empresa+"' ORDER BY SUC_CODIGO DESC LIMIT 1";
                rs = consultar(sql);
            }     
                
            if(lAgregar==false){
                try {
                    if(rs.getRow()>0){
                        if(origen.equals("empresa")){
                            this.codigo = rs.getString("emp_codigo");
                        }else if(origen.equals("sucursal")){
                            this.codigo = rs.getString("SUC_CODIGO");
                        }
                    }else{
                        return rs;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorEmpresas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
            if(origen.equals("empresa")){
                //sql = "SELECT emp_codigo,emp_nombre,emp_rif,emp_direccion,emp_activo FROM dnempresas WHERE EMP_CODIGO='"+codigo+"'";
                VarGlobales.setlBaseDatosConfiguracion(true);
                creaConexion();
                sql = "{call sp_formEmpresas(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                
                callStoreProcedure(sql, "SELECT");
                VarGlobales.setlBaseDatosConfiguracion(false);
            }else if(origen.equals("sucursal")){
                sql = "SELECT * FROM dnsucursal WHERE SUC_CODIGO='"+codigo+"' AND SUC_EMPRESA='"+empresa+"'";
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ControladorEmpresas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public ResultSet buscaConfEmpresa(String op, String codEmp){
        ResultSet rsConfEmp = configEmpresa.callStoreProcedureConfEmpresa(op, codEmp, "", "", "", "", "", "", "", "", "", "", "", "", "",
                                                                          "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                                                                          "", "", "", "", "");
        
        return rsConfEmp;
    }
    
    public void save(String codigo, String nombre, String rif, String direccion, String activo, JTable tabla, Boolean lAgregar,
                     String origen, String empresa, JTable tabSucursal, int activoSuc, String telefono, String correo, String moneda,
                     String pais, String fax, String pagWeb, String tipoIdent, String estado, String municip, String parroq, String sector,
                     String empPredeterminada, String rutaFoto, String nombreFoto){
        
        int existe = 0;
        existe = validaCodigo(rif);
        
        if(lAgregar == true){
            if(existe > 0){
                tfRif.setText("");
                tfRif.requestFocus();
                    
                JOptionPane.showMessageDialog(null,idioma.loadLangMsg().getString("MsgRifExist"), 
                                              idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                              JOptionPane.WARNING_MESSAGE);
                    
                return;
            }
        }
        
        try {
            JTable table = null;
            
            this.codigo            = codigo;
            this.nombre            = nombre;
            this.direccion         = direccion;
            this.rif               = rif;
            this.activo            = activo;
            this.empPredeterminada = empPredeterminada;
            this.telefono          = telefono;
            this.correo            = correo;
            this.moneda            = moneda;
            this.pais              = pais;
            this.fax               = fax;
            this.pagWeb            = pagWeb;
            this.tipoIdent         = tipoIdent;
            this.estado            = estado;
            this.municip           = municip;
            this.parroq            = parroq;
            this.sector            = sector;

            SQLQuerys modificar = new SQLQuerys();
            
            if(origen.equals("empresa")){
                if (lAgregar==true){
                    table = tabla;
                
                    VarGlobales.setlBaseDatosConfiguracion(true);
                    creaConexion();
                    String sql = "{call sp_formEmpresas(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                
                    callStoreProcedure(sql, "INSERT");
                    
                    sql = "UPDATE DNEMPRESAS SET emp_moneda='"+moneda.substring(0, moneda.indexOf("-")-1).trim()+"'\n"+
                          "WHERE emp_codigo='"+codigo+"';";
                    modificar.SqlUpdate(sql);
                    
                    VarGlobales.setlBaseDatosConfiguracion(false);
                    
                    Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                         VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                         "Inserción de Nuevo Registro", "Se creo la Empresa ''"+codigo+
                                                         " Correctamente");
                }else if (lAgregar==false){
                    table = tabla;
                    VarGlobales.setlBaseDatosConfiguracion(true);
                    
                    creaConexion();
                    String sql = "{call sp_formEmpresas(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                
                    callStoreProcedure(sql, "UPDATE");
                    
                    sql = "UPDATE DNEMPRESAS SET emp_moneda='"+moneda.substring(0, moneda.indexOf("-")-1).trim()+"'\n"+
                          "WHERE emp_codigo='"+codigo+"';";
                    modificar.SqlUpdate(sql);
                    
                    VarGlobales.setlBaseDatosConfiguracion(false);
            
                    Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                         VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                         "Modificación de Registro", "Se Modifico la Empresa ''"+codigo+
                                                         " Correctamente");
                }
                
                try {
                    if(!rutaFoto.isEmpty()){
                        VarGlobales.guardarImagen(rutaFoto, nombreFoto, codigo);
                    }
                } catch (Exception e) {
                    Logger.getLogger(ControladorEmpresas.class.getName()).log(Level.SEVERE, null, e);
                }
            }else if(origen.equals("sucursal")){
                table = tabSucursal;
                if (lAgregar==true){
                    String sqlInsert = "INSERT INTO dnsucursal (SUC_EMPRESA,SUC_USER,SUC_MACPC,SUC_CODIGO,SUC_NOMBRE,SUC_ID,SUC_DIRECCION,SUC_ACTIVO) "+
                                            "VALUES ('"+empresa+"','"+VarGlobales.getIdUser()+"','"+VarGlobales.getMacPc()+"','"+
                                                    ""+codigo.toUpperCase()+"','"+nombre+"',"+
                                                    "'"+rif+"','"+direccion+"',"+activoSuc+")";
                    System.out.println("Controlador Sucursal Insertar"+sqlInsert);

                    modificar.SqlInsert(sqlInsert);
            
                    Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                        VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                        "Inserción de Nuevo Registro", "Se creo la Sucursal ''"+codigo+
                                                        " Correctamente");
                }else if (lAgregar==false){
                    String sql = "UPDATE dnsucursal SET SUC_NOMBRE='"+nombre+"',"+
                                                        "SUC_ID='"+rif+"',"+
                                                        "SUC_DIRECCION='"+direccion+"',"+
                                                        "SUC_ACTIVO="+activoSuc+" "+
                                 "WHERE SUC_CODIGO='"+codigo+"' AND "+
                                 "SUC_EMPRESA='"+empresa+"'";
                    System.out.println("Controlador Sucursal Modificar:   "+sql);
                    modificar.SqlUpdate(sql);
            
                    Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                         VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                         "Modificación de Registro", "Se Modifico la Sucursal ''"+codigo+
                                                         " Correctamente");
            
                //if(!VarGlobales.getCodEmpresa().equals("000001")){
//                    WsEmpresas wsEmpresas = new WsEmpresas("ActualizaEmpresa");
//                    wsEmpresas.start();
                //}
                }
            }
        
            //---------- Refresca la Tabla para vizualizar los ajustes ----------
            cargaTablas(table, origen, true, empresa);
            table.getSelectionModel().setSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
            //-------------------------------------------------------------------
        } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ControladorEmpresas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveConfigEmpresa(String origen, String op, String codEmp, String nombPrecio1, String nombPrecio2, String nombPrecio3, String nombPrecio4, 
                                  String nombPrecio5, String utilPrecio1, String utilPrecio2, String utilPrecio3, String utilPrecio4, 
                                  String utilPrecio5, String utilCosto, String consecutivo, String logConsecTaller, String usaFactElectronica, 
                                  String proveedServicio, String userComprobElectro, String claveComprobElectro, String urlApiRest, 
                                  String ubicArchCertificado, String claveArchCerticado, String ultimoConseCbtElectron, String codConsecutivoGrupSubGrup,
                                  String visualizaUltimoRegForm, String ultimoConseTiqueteElectronico, String ultimoConseNotaCreditoElectronica,
                                  String ultimoConseNotaDebitoElectronica, String correoOrigen, String claveCorreoOrigen, String servidorSmtp,
                                  String puertoServidorSmtp, String asuntoCorreo, String cuerpoCorreo, String urlAccesToken, String conseReceptor){
         
        if(origen.equals("confEmpresa")){
            configEmpresa.callStoreProcedureConfEmpresa(op, codEmp, nombPrecio1, nombPrecio2, nombPrecio3, nombPrecio4, nombPrecio5, 
                                                        utilPrecio1, utilPrecio2, utilPrecio3, utilPrecio4, utilPrecio5, utilCosto,
                                                        consecutivo, logConsecTaller, usaFactElectronica, proveedServicio, userComprobElectro, 
                                                        claveComprobElectro, urlApiRest, ubicArchCertificado, claveArchCerticado,
                                                        ultimoConseCbtElectron, codConsecutivoGrupSubGrup, visualizaUltimoRegForm,
                                                        ultimoConseTiqueteElectronico, ultimoConseNotaCreditoElectronica,
                                                        ultimoConseNotaDebitoElectronica, correoOrigen, claveCorreoOrigen, servidorSmtp, 
                                                        puertoServidorSmtp, asuntoCorreo, cuerpoCorreo, urlAccesToken, conseReceptor);
             
            ResultSet rsConfEmp = configEmpresa.callStoreProcedureConfEmpresa("SELECT", VarGlobales.getCodEmpresa(), "", "", "", "", "", 
                                                                              "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                                                                              "", "", "", "", "", "", "", "", "", "", "", "", "", "");
             
            try {
                if(rsConfEmp.getRow()>0){
                    VarGlobales.setConfigEmpresa(rsConfEmp.getString("nom_precio_1"),
                                                 rsConfEmp.getString("nom_precio_2"),
                                                 rsConfEmp.getString("nom_precio_3"),
                                                 rsConfEmp.getString("nom_precio_4"),
                                                 rsConfEmp.getString("nom_precio_5"),
                                                 rsConfEmp.getString("utilidad_prec_1"),
                                                 rsConfEmp.getString("utilidad_prec_2"),
                                                 rsConfEmp.getString("utilidad_prec_3"),
                                                 rsConfEmp.getString("utilidad_prec_4"),
                                                 rsConfEmp.getString("utilidad_prec_5"),
                                                 rsConfEmp.getBoolean("calc_utilid_costo"),
                                                 rsConfEmp.getBoolean("codigo_consecutivo"),
                                                 rsConfEmp.getInt("long_consec_tallar"),
                                                 rsConfEmp.getBoolean("usa_facturaelectronica"),
                                                 rsConfEmp.getString("proveedor_servico"),
                                                 rsConfEmp.getString("usuario"),
                                                 rsConfEmp.getString("clave"),
                                                 rsConfEmp.getString("urlApiRest"),
                                                 rsConfEmp.getString("ubicacionarchivocertificado"),
                                                 rsConfEmp.getString("clavearchivocertificado"),
                                                 rsConfEmp.getString("consecutivoCbteElectrico"),
                                                 rsConfEmp.getBoolean("codigo_consecutivo_grupo_subgrupo"),
                                                 rsConfEmp.getBoolean("visualizarUltimoRegForm"),
                                                 rsConfEmp.getString("consecutivoNotaDebitoElectrico"),
                                                 rsConfEmp.getString("consecutivoNotaCreditoElectrico"),
                                                 rsConfEmp.getString("consecutivoTiqueElectrico"),
                                                 rsConfEmp.getString("urlAccesToken"),
                                                 rsConfEmp.getString("consecutivoReceptor"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorEmpresas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }
    
    public void delete(String codigo, JTable tabla, String origen, String empresa){
        int eliminado;

        VarGlobales.setlBaseDatosConfiguracion(true);
        
        SQLQuerys eliminar = new SQLQuerys();
        eliminar.SqlDelete("DELETE FROM DNEMPRESAS WHERE EMP_CODIGO='"+VarGlobales.getCodEmpresa()+"' AND EMP_CODIGO='"+codigo+"'","","");
            
        ValidaCodigo consulta = new ValidaCodigo();
        eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNEMPRESAS WHERE EMP_CODIGO='"+VarGlobales.getCodEmpresa()+"' AND "+
                                              "EMP_CODIGO='"+codigo+"'",true,"");
        
        VarGlobales.setlBaseDatosConfiguracion(false);
        
        ejecutaHilo("", false, origen, empresa);
        cargaTabla(tabla);
    }
    
    public void cargaTabla(JTable tabla){
        VarGlobales.setlBaseDatosConfiguracion(true);
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        //String SQL = "SELECT EMP_CODIGO,EMP_NOMBRE,EMP_RIF FROM DNEMPRESAS WHERE EMP_CODIGO='"+VarGlobales.getCodEmpresa()+"'";
        String SQL = "SELECT EMP_CODIGO,EMP_NOMBRE,EMP_RIF FROM DNEMPRESAS";
        String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"),idioma.loadLangHeaderTable().getString("nombre"),idioma.loadLangHeaderTable().getString("rif")};
        //-------------------------------------------------------------------
        cargatabla.cargatablas(tabla,SQL,columnas);        
        
        VarGlobales.setlBaseDatosConfiguracion(false);
    }
    
    
//    public ResultSet Cargardatos(String codigo, Boolean lAgregar, String origen, String empresa) throws ClassNotFoundException, SQLException{
//        this.codigo = codigo;
//        this.lAgregar = lAgregar;
//        String SQL = "";
//        
//        if(origen.equals("empresa")){
//            SQL = "SELECT * FROM dnempresas WHERE EMP_CODIGO='"+VarGlobales.getCodEmpresa()+"'";
//        }else if(origen.equals("sucursal")){
//            SQL = "SELECT * FROM dnsucursal WHERE SUC_CODIGO='"+codigo+"' AND SUC_EMPRESA='"+empresa+"'";
//        }
//        rs = conet.consultar(SQL);
//        
//        String SQL_Reg = "";
//        if(origen.equals("empresa")){
//            SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM dnempresa WHERE EMP_CODIGO='"+VarGlobales.getCodEmpresa()+"'";
//        }else if(origen.equals("sucursal")){
//            SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM dnsucursal WHERE SUC_CODIGO='"+codigo+"' AND SUC_EMPRESA='"+empresa+"'";
//        }
//        Reg_count = conet.Count_Reg(SQL_Reg);
//        
//        if (Reg_count==1){
//            rs.next();
//        }else{
//            rs.last();
//        }
//        ResultSet resultSet = ejecutaHilo(codigo, lAgregar, origen, empresa);
//        return resultSet;
//    }
   
    
    public int validaCodigo(String codigo){
        int valida = 0;
        
        VarGlobales.setlBaseDatosConfiguracion(true);
        
        ValidaCodigo consulta = new ValidaCodigo();
        valida = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNEMPRESAS WHERE EMP_CODIGO='"+VarGlobales.getCodEmpresa()+"' AND "+
                                       "emp_rif='"+codigo.toUpperCase()+"'",true,"");
        
        VarGlobales.setlBaseDatosConfiguracion(false);
        
        return valida;
    }
    
    public DefaultComboBoxModel cargaCombo(){
        VarGlobales.setlBaseDatosConfiguracion(true);
        
        String sql = "SELECT DISTINCT EMP_CODIGO AS DATO1 FROM dnempresas";
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
        
        VarGlobales.setlBaseDatosConfiguracion(false);
        
        return mdl; 
    }
    
    public final DefaultComboBoxModel cargaComboMoneda() {
        String sql = "SELECT CONCAT(mon_nomenc,' - ',mon_nombre) AS DATO1 FROM dnmoneda";

        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));    
        
        return mdl;
    }
    
    public ResultSet callStoreProcedure(String sql, String op){
        try {
            cs = conn.prepareCall(sql);
            
            if(op.equals("SELECT")){
                cs.setString(1, op);
                cs.setString(2, codigo);
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
                cs.setString(2, codigo);
                cs.setString(3, VarGlobales.getIdUser());
                cs.setString(4, VarGlobales.getMacPc());
                cs.setString(5, nombre);
                cs.setString(6, rif);
                cs.setString(7, direccion);
                cs.setString(8, activo);
                cs.setString(9, telefono);
                cs.setString(10, correo);
                //if (moneda.isEmpty()){
                    cs.setString(11, "");
                //}else{
                //    cs.setString(11, moneda.substring(0, moneda.indexOf("-")-1).trim());
                //}
                cs.setString(12, pais);
                cs.setString(13, fax);
                cs.setString(14, pagWeb);
                cs.setString(15, tipoIdent);
                cs.setString(16, estado);
                cs.setString(17, municip);
                cs.setString(18, parroq);
                cs.setString(19, sector);
                cs.setString(20, empPredeterminada);
                
                insertDeleteUpdate_StoreProcedure(cs);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ControladorEmpresas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
}