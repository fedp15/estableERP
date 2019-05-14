package Controlador;

import Modelos.Bitacora;
import util.CargaComboBox;
import util.CargaTablas;
import util.SQLQuerys;
import util.ValidaCodigo;
import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import util.CodigoConsecutivo;
import util.Internacionalizacion;
import org.apache.commons.codec.digest.DigestUtils;

public class ControladorCrearUsuarios extends conexion{
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final  CargaTablas cargatabla = new CargaTablas();
    
    private static ControladorCrearUsuarios controladorCrearUsuarios;
    
    private ResultSet rs, rs_count, rs_codigo, rs_permiso;
    private int Reg_count;
    private String codigo, descrip, activo, tabla, clase, Clave="";
    private Boolean lAgregar, lPass=false;

    private JLabel imgDesktUser;
    private JTextField tfCodigo, tfNombre, tfUsuario, tfCorreo, tfRutaImg;
    private JPasswordField pfClave1;
    private JCheckBox chkActivo, chkDeldoc; //accAndroid, accWeb, 
    private JComboBox cbCargo, cbTipo;

    public ControladorCrearUsuarios(){
        
    }
    
    public static ControladorCrearUsuarios getControladorCrearUsuarios(){
        if (controladorCrearUsuarios == null){
            controladorCrearUsuarios = new ControladorCrearUsuarios();
        }

        return controladorCrearUsuarios;
    }
    
    public void setComponentes(JTextField codigo, JTextField nombre, JPasswordField clave1, 
                               JTextField usuario, JTextField correo, JTextField rutaImg, JCheckBox activo, 
                               JComboBox combo1, JComboBox combo2, JLabel imgDesktUser, JCheckBox deldoc){
        this.tfCodigo = codigo;
        this.setTfNombre(nombre);
//        this.setTfClave(clave);
        this.pfClave1 = clave1;
        this.setTfUsuario(usuario);
        this.setTfCorreo(correo);
        this.setTfRutaImg(rutaImg);
        
        this.chkActivo    = activo;
//        this.accAndroid   = accAndroid;
//        this.accWeb       = accWeb;
        this.cbCargo      = combo1;
        this.cbTipo       = combo2;
        this.imgDesktUser = imgDesktUser;
        this.chkDeldoc    = deldoc;
    }
    
    public void setPass(Boolean lPass){
        if (!VarGlobales.getIdUser().equals(tfCodigo.getText())){
            pfClave1.setVisible(true); pfClave1.setText(Clave);
            this.lPass = lPass;
        }
    }
    
    public void save(String codigo, String descrip, String activo, JTable tabla, Boolean lAgregar,
                     String deldoc, String origen){
//        String accandroid = ""; String accweb = ""; 
        
        if (chkActivo.isSelected()==true){
            activo = "1";
        }else{
            activo = "0";
        }
        
//        if (accWeb.isSelected()==true){
//            accweb = "1";
//        }else{
//            accweb = "0";
//        }
        
//        if (accAndroid.isSelected()==true){
//            accandroid = "1";
//        }else{
//            accandroid = "0";
//        }

//        if (this.Bandera==true){
//            JOptionPane.showMessageDialog(null, (String) Msg.get(3));
//            return;
//        }
        if ("".equals(getTfCodigo().getText())){
            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgAddCode")+" "+idioma.loadLangComponent().getString("lbTituloFormUsuario"),
                                          "Notificación", JOptionPane.WARNING_MESSAGE);
            getTfCodigo().requestFocus();
            return;
        }
        if ("".equals(getTfNombre().getText())){
            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgAddName"), "Notificación",
                                          JOptionPane.WARNING_MESSAGE);
            getTfNombre().requestFocus();
            return;
        }
        if ("".equals(getTfUsuario().getText())){
            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgAddUserSist"), "Notificación",
                                          JOptionPane.WARNING_MESSAGE);
            getTfUsuario().requestFocus();
            return;
        }
        
        if (lPass==true){
            if ("".equals(pfClave1.getText())){
                JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgAddClave"));
                pfClave1.requestFocus();
                return;
            }
        }
        
        Clave = pfClave1.getText();
        Clave = DigestUtils.md5Hex(Clave);
        
        if (lAgregar==true){
            // Llama la Clase Insert para Guardar los Registros
            SQLQuerys insertar = new SQLQuerys();
            
            String sql = "INSERT INTO dnusuarios(OPE_NOMBRE,OPE_CARGO,OPE_CLAVE,OPE_MAPTAB,OPE_MAPMNU,OPE_LUNES,OPE_MARTES,"+
                                                 "OPE_MIERCOLES,OPE_JUEVES,OPE_VIRNES,OPE_SABADO,OPE_DOMINGO,OPE_LUNAIN,"+
                                                 "OPE_LUNAFI,OPE_LUNPIN,OPE_LUNPFIN,OPE_MARAIN,OPE_MARAFI,OPE_MARPIN,OPE_MARPFI,"+
                                                 "OPE_MIEAIN,OPE_MIEAFI,OPE_MIEPIN,OPE_MIEPFI,OPE_JUEAIN,OPE_JUEAFI,OPE_JUEPIN,"+
                                                 "OPE_JUEPFI,OPE_VIEAIN,OPE_VIEAFI,OPE_VIEPIN,OPE_VIEPFI,OPE_SABAIN,OPE_SABAFI,"+
                                                 "OPE_SABPIN,OPE_SABPFI,OPE_DOMAIN,OPE_DOMAFI,OPE_DOMPIN,OPE_DOMPFI,"+
                                                 "OPE_ACTIVO,OPE_STATUS,OPE_CODVEN,OPE_CODZON,OPE_USUARIO,"+
                                                 "OPE_CORREO,OPE_RUTAIMG,OPE_IMGPERFIL,OPE_USUDAT,OPE_DELDOC) "+
                                       "VALUES ('"+descrip.toUpperCase()+"','"+cbCargo.getSelectedItem().toString()+"','"+Clave+"',"+
                                               "'','','0','0','0','0','0','0','0', '', '', '', '', '', '', '', '',"+
                                               "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '',"+
                                               //"'"+accweb+"','"+accandroid+"','"+activo+"',"+
                                               "'"+activo+"',"+
                                               "'Activo', '', '','"+getTfUsuario().getText().toUpperCase()+"','"+getTfCorreo().getText()+"',"+
                                               "'"+getTfRutaImg().getText().replace("\\", "/").trim()+"', '', '',"+deldoc+");";

            insertar.SqlInsert(sql);
                
            try {
                sql = "SELECT OPE_NUMERO FROM dnusuarios ORDER BY OPE_NUMERO DESC LIMIT 1 ";
                rs_codigo = consultar(sql);
                
                codigo=rs_codigo.getString("OPE_NUMERO").trim();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ControladorCrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            sql = "INSERT INTO relac_usuario_grupo_permisos (emp_codigo, ope_numero, per_id, activo) "+
                  "VALUES ('"+VarGlobales.getCodEmpresa()+"', '"+codigo+"',"+cbTipo.getSelectedItem().toString().substring(0, 4)+", "+
                          "'"+activo+"');";
            
            insertar.SqlInsert(sql);
            
//            if(origen.equals("Welcome")){
//                try {
//                    ResultSet rsExistPermisos = consultar("SELECT * FROM dnpermisologia WHERE mis_empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                            "mis_permiso="+cbTipo.getSelectedItem().toString().substring(0, 4));
//                    
//                    if (rsExistPermisos.getRow()==0){
//                        ProgressBarRefrescar pbr = new ProgressBarRefrescar("2", cbTipo.getSelectedItem().toString().substring(0, 4),
//                                VarGlobales.getCodEmpresa(),
//                                idioma.loadLangComponent().getString("lbTituloProgresBar"));
//                        
//                        Dimension desktopSize = desktop.getSize();
//                        Dimension jInternalFrameSize = pbr.getSize();
//                        pbr.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//                        
//                        desktop.add(pbr);
//                        pbr.toFront();
//                        pbr.setVisible(true);
//                    }
//                } catch (ClassNotFoundException | SQLException ex) {
//                    Logger.getLogger(ControladorCrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
            
//            jTxtAct_Codigo.requestFocus();
            //CodConsecutivo();
            Bitacora registo_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                 VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                 "Inserción de Nuevo Usuario", "Usuario "+getTfUsuario().getText()+" Insertado Correctamente");
            
            
//            WsCrearUsuarios wsCrearUsuarios = new WsCrearUsuarios();
//            wsCrearUsuarios.setParametros("GuardarUsuario");
//            wsCrearUsuarios.start();
        }else if (lAgregar==false){
            SQLQuerys modificar = new SQLQuerys();
            String sql = "UPDATE dnusuarios SET OPE_NOMBRE='"+descrip.toUpperCase()+"',OPE_CARGO='"+cbCargo.getSelectedItem().toString()+"',\n"+
//                                               "OPE_CLAVE='"+Clave+"',OPE_ACCWEB='"+accweb+"',OPE_ACCANDROID='"+accandroid+"',OPE_ACTIVO='"+activo+"',\n"+
                                               "OPE_CLAVE='"+Clave+"',OPE_ACTIVO='"+activo+"',\n"+
                                               "OPE_STATUS='Activo',OPE_USUARIO='"+getTfUsuario().getText().toUpperCase()+"',\n"+
                                               "OPE_CORREO='"+getTfCorreo().getText()+"',\n"+
                                               "OPE_RUTAIMG='"+getTfRutaImg().getText().replace("\\", "/").trim()+"',OPE_DELDOC="+deldoc+" \n"+
                         "WHERE OPE_NUMERO='"+codigo+"';";
            
            modificar.SqlUpdate(sql);
            
            sql = "UPDATE relac_usuario_grupo_permisos SET per_id="+cbTipo.getSelectedItem().toString().substring(0, 4)+"\n"+
                  "WHERE  ope_numero='"+codigo+"';";
            
            modificar.SqlUpdate(sql);
            
            Bitacora modifica_user = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                  VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                  "Modificación Usuario", "Usuario "+getTfUsuario().getText()+" Modificado Correctamente");
            
//            WsCrearUsuarios wsCrearUsuarios = new WsCrearUsuarios();
//            wsCrearUsuarios.setParametros("ActualizaUsuario");
//            wsCrearUsuarios.start();
        }
        
        //---------- Refresca la Tabla para vizualizar los ajustes ----------
        cargaTabla(tabla);
        //-------------------------------------------------------------------
    }
    
    public void delete(String codigo, JTable tabla){
        int eliminado;
        int salir = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgDeleteUser")+" "+
                                                        getTfNombre().getText()+"?");
        
        if ( salir == 0) {
            SQLQuerys eliminar = new SQLQuerys();
            String sql = "DELETE FROM dnusuarios WHERE OPE_NUMERO='"+codigo+"'";
            eliminar.SqlDelete(sql, "", "");

            ValidaCodigo consulta = new ValidaCodigo();
            eliminado = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM dnusuarios WHERE OPE_NUMERO='"+codigo+"'",true,"");
            
            cargaTabla(tabla);
        }
    }
    
    public int cancelar(Boolean lAgregar, String clase){
        int eleccion = 0;
        
        if (lAgregar==true){
            eleccion = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgCancelAdd")+" "+
                                                           idioma.loadLangComponent().getString("lbTituloFormUsuario")+"?");
            
        }else if (lAgregar==false){
            eleccion = JOptionPane.showConfirmDialog(null, (String) idioma.loadLangMsg().getString("MsgCancelEdit")+" "+
                                                                    idioma.loadLangComponent().getString("lbTituloFormUsuario")+"?");
        }
        
        return eleccion;
    }
    
    public void cargaTabla(JTable tabla){
        String SQL = "SELECT DISTINCT dnusuarios.ope_numero,OPE_USUARIO, REPEAT('*', LENGTH(OPE_CLAVE)) AS OPE_CLAVE,OPE_CARGO,per_id\n" +
                     "FROM dnusuarios\n" +
                     "inner join relac_usuario_grupo_permisos on relac_usuario_grupo_permisos.ope_numero=dnusuarios.ope_numero\n"+
                     "WHERE emp_codigo='"+VarGlobales.getCodEmpresa()+"'";

        String[] columnas = {idioma.loadLangHeaderTable().getString("codigo"), idioma.loadLangHeaderTable().getString("usuario"),
                            idioma.loadLangHeaderTable().getString("contraseña"), idioma.loadLangHeaderTable().getString("cargo"),
                            idioma.loadLangHeaderTable().getString("tipoUsuario")};
        
        cargatabla.cargatablas(tabla,SQL,columnas);
    }
    
    public String codConsecutivo(){
        String consecutivo = null;
                
        CodigoConsecutivo codigo = new CodigoConsecutivo();
        consecutivo = codigo.CodigoConsecutivo("OPE_NUMERO","DNUSUARIOS",1,"");
        
        if (consecutivo==null){
            consecutivo="1";
        }
        
//        WsCrearUsuarios wsCrearUsuarios = new WsCrearUsuarios();
//        wsCrearUsuarios.setParametros("BuscaUsuario");
//        wsCrearUsuarios.start();
        
        return consecutivo;
    }
    
    public ResultSet Cargardatos(String codigo, Boolean lAgregar) throws ClassNotFoundException, SQLException{
        this.codigo = codigo;
        this.lAgregar = lAgregar;
        
        String SQL = "SELECT ACT_CODIGO,ACT_NOMBRE FROM DNACTIVIDAD_ECO WHERE ACT_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        rs = consultar(SQL);

        String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM DNACTIVIDAD_ECO WHERE ACT_EMPRESA='"+VarGlobales.getCodEmpresa()+"'";
        Reg_count = Count_Reg(SQL_Reg);
        
        if (Reg_count==1){
            rs.next();
        }else{
            rs.last();
        }
        
        ResultSet resultSet = ejecutaHilo(codigo, lAgregar);
        return resultSet;
    }
    
    public ResultSet ejecutaHilo(String codigo, Boolean lAgregar){
        comboTipusuario();
        
        try {
            String SQLCodTipMae="";
            
            SQLCodTipMae = "SELECT OPE_NUMERO FROM dnusuarios ORDER BY OPE_NUMERO DESC LIMIT 1 ";
                
            rs_codigo = consultar(SQLCodTipMae);
            try {
                if(lAgregar==false){
                    codigo=rs_codigo.getString("OPE_NUMERO").trim();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorCrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQL = "SELECT * FROM dnusuarios \n"+
                         "left join relac_usuario_grupo_permisos on relac_usuario_grupo_permisos.ope_numero=dnusuarios.ope_numero \n"+
                         "WHERE dnusuarios.OPE_NUMERO='"+codigo+"' and relac_usuario_grupo_permisos.emp_codigo='"+VarGlobales.getCodEmpresa()+"'";

            try {
                rs = consultar(SQL);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorCrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM dnusuarios WHERE OPE_NUMERO='"+codigo+"'";
            Reg_count = Count_Reg(SQLReg);
            mostrar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorCrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ControladorCrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return rs;
    }

    public void mostrar(){
        if (Reg_count > 0){
            try {
                getTfCodigo().setText(rs.getString("OPE_NUMERO").trim()); getTfNombre().setText(rs.getString("OPE_NOMBRE").trim());
                
                if (rs.getBoolean("OPE_ACTIVO")==true){
                    chkActivo.setSelected(true);
                }else{
                    chkActivo.setSelected(false);
                }
                
                getTfUsuario().setText(rs.getString("OPE_USUARIO").trim());
                if (rs.getString("OPE_CARGO")==null || rs.getString("OPE_CARGO").trim().isEmpty()){
                    cbCargo.setSelectedItem(0); 
                }else{
                    cbCargo.setSelectedItem(rs.getString("OPE_CARGO").trim()); 
                }
                getTfCorreo().setText(rs.getString("OPE_CORREO").trim());
                
//                if (rs.getBoolean("OPE_ACCWEB")==true){
//                    accWeb.setSelected(true);
//                }else{
//                    accWeb.setSelected(false);
//                }
//                if (rs.getBoolean("OPE_ACCANDROID")==true){
//                    accAndroid.setSelected(true);
//                }else{
//                    accAndroid.setSelected(false);
//                }
                if(rs.getBoolean("OPE_DELDOC")==true){
                    chkDeldoc.setSelected(true);
                }else{
                    chkDeldoc.setSelected(false);
                }
                
                if (rs.getString("per_id")==null || rs.getString("per_id").trim().isEmpty()){
                }else{
                    String SQL = "SELECT CONCAT(PER_ID,' - ', PER_NOMBRE) AS OPE_TIPO_DESKTOP FROM DNPERMISO_GRUPAL "+
                                 "WHERE PER_ID="+rs.getString("per_id").trim()+"";  //PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND 
                    
                    try {
                        rs_permiso = consultar(SQL);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ControladorCrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    cbTipo.setSelectedItem(rs_permiso.getString("OPE_TIPO_DESKTOP").trim());
                }
                
                Clave = rs.getString("OPE_CLAVE").trim();
                
                int LongClave = Clave.length();
                String clave_codif = "";
                
                for (int i=0; i<LongClave; i++){
                    clave_codif= clave_codif+"*";
                }
                
                pfClave1.setText(clave_codif);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorCrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorCrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void comboTipusuario(){
        String sql = "SELECT CONCAT(PER_ID,' - ', PER_NOMBRE) AS DATO1 FROM DNPERMISO_GRUPAL WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"';";
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
        this.cbTipo.setModel(mdl); 
    }

    public JTextField getTfCodigo() {
        return tfCodigo;
    }

    public JTextField getTfNombre() {
        return tfNombre;
    }

    public void setTfNombre(JTextField tfNombre) {
        this.tfNombre = tfNombre;
    }

    public JTextField getTfUsuario() {
        return tfUsuario;
    }

    public void setTfUsuario(JTextField tfUsuario) {
        this.tfUsuario = tfUsuario;
    }

    public JTextField getTfCorreo() {
        return tfCorreo;
    }

    public void setTfCorreo(JTextField tfCorreo) {
        this.tfCorreo = tfCorreo;
    }

    public JTextField getTfRutaImg() {
        return tfRutaImg;
    }

    public void setTfRutaImg(JTextField tfRutaImg) {
        this.tfRutaImg = tfRutaImg;
    }
}