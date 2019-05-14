
package Modelos;

import Controlador.ControladorCrearUsuarios;
import Vista.CreaUsuarios;
import Vista.ListaDeMaestros;
import static Vista.MenuPrincipal.escritorioERP;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import util.Internacionalizacion;

public class ModelCreaUsuarios {
    private CreaUsuarios crearUsuarios; 
    private static ModelCreaUsuarios modelCreaUsuarios;
    
    private Object aThis;
    private JButton btnAgregar, btnModificar, btnGuardar, btnEliminar, btnCancelar, btnBuscar, btnAtras, btnSiguiente, btnSalir, btnBuscaImg;
    private JLabel lbImgFondo;
    private JTextField txtCodigo, txtNombre, txtClaveUser, txtUsuario, txtCorreo, txtRutaImg;
    private JPasswordField txtClaveUser2;
    private JCheckBox chkActivo, chkDeldoc; //chkAndroid, chkWeb,
    private JTable tbTabla;
    private JComboBox cbCargo, cbTipo;
    
    private boolean lAgregar;
    private ResultSet rs;
    private String claveUser = "", Clave="", origen;
    private JDesktopPane desktop;
    
    private final ControladorCrearUsuarios controladorCrearUsuarios = new ControladorCrearUsuarios();
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();

    private ModelCreaUsuarios(){
        
    }

    public static ModelCreaUsuarios getModelGrupoPermisos(){
        if(modelCreaUsuarios == null){
            modelCreaUsuarios = new ModelCreaUsuarios();
        }
        return modelCreaUsuarios;
    }

    public void setVista(Object aThis){
        this.aThis = aThis;
    }
    
    public Object getVista(){
        return aThis;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public JDesktopPane getDesktop() {
        return desktop;
    }

    public void setDesktop(JDesktopPane desktop) {
        this.desktop = desktop;
    }
    
    public void setJTextField(JTextField codUser, JTextField nombUser, JPasswordField claveUser2, JTextField usuario, 
                              JTextField correo, JTextField rutaImg){
        this.txtCodigo     = codUser;
        this.txtNombre     = nombUser;
//        this.txtClaveUser  = claveUser;
        this.txtClaveUser2 = claveUser2;
        this.txtUsuario    = usuario;
        this.txtCorreo     = correo;
        this.txtRutaImg    = rutaImg;
    }
    
    public void setJTable(JTable tabla){
        this.tbTabla = tabla;
    }
    
    //public void setJCheckBox(JCheckBox activo, JCheckBox accAndroid, JCheckBox accWeb, JCheckBox deldoc){
    public void setJCheckBox(JCheckBox activo, JCheckBox deldoc){
        this.chkActivo  = activo;
//        this.chkAndroid = accAndroid;
//        this.chkWeb     = accWeb;
        this.chkDeldoc  = deldoc;
    }
    
    public void setJComboBox(JComboBox cargo, JComboBox tipo) {
        this.cbCargo = cargo;
        this.cbTipo  = tipo;
    }
    
    public void setImgDesktop(JLabel fondoPreview) {
        this.lbImgFondo = fondoPreview;
    }

    public void setButtonOther(JButton buscarimg) {
        this.btnBuscaImg = buscarimg;
    }

    public void setButton(JButton agregar,JButton modificar,JButton grabar,JButton eliminar,JButton cancelar,JButton buscar,JButton atras,JButton adelante,JButton salir){
        this.btnAgregar   = agregar;
        this.btnModificar = modificar;
        this.btnGuardar   = grabar;
        this.btnEliminar  = eliminar;
        this.btnCancelar  = cancelar;
        this.btnBuscar    = buscar;
        this.btnAtras     = atras;
        this.btnSiguiente = adelante;
        this.btnSalir     = salir;
    }
           
    public void getCodConsecutivo(){
//        try {
//            mostrarImagen(null);
            txtCodigo.setText(controladorCrearUsuarios.codConsecutivo());
            
            //if(!VarGlobales.getCodEmpresa().equals("000001")){
            //wsPermisologias.setParametros("BuscaPermiso", VarGlobales.getCodEmpresa(), txtCodigo.getText(), txtNombre.getText(),
            //                              txtNombre.getText(), "", "", "");
            //wsPermisologias.start(); //inicia hilo1
            //}
//        } catch (SQLException ex) {
//            Logger.getLogger(ModelCreaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    public void habilitar(String accion){
        if (accion.equals("Inicializa")){
            //txtCodigo.setEnabled(true); 
            txtNombre.setEnabled(true); txtUsuario.setEnabled(true); txtCorreo.setEnabled(true);
            txtRutaImg.setEnabled(true); //txtClaveUser.setEnabled(true); 
            txtClaveUser2.setEnabled(true);
            
            cbCargo.setEnabled(true); cbTipo.setEnabled(true);
            btnBuscaImg.setEnabled(true);
            
            chkActivo.setEnabled(true); 
//            chkAndroid.setEnabled(true); chkWeb.setEnabled(true);
            chkDeldoc.setEnabled(true);
            
            limpiar();
            controladorCrearUsuarios.comboTipusuario();
            txtNombre.requestFocus();
                          
            lAgregar = true;
        }else if (accion.equals("Modificar")){
            //txtCodigo.setEnabled(false); 
            txtNombre.setEnabled(true); txtUsuario.setEnabled(true); txtCorreo.setEnabled(true);
            txtRutaImg.setEnabled(true); //txtClaveUser.setEnabled(true); 
            txtClaveUser2.setEnabled(true);
            
            cbCargo.setEnabled(true); cbTipo.setEnabled(true);
            btnBuscaImg.setEnabled(true);
        
            chkActivo.setEnabled(true); 
//            chkAndroid.setEnabled(true); chkWeb.setEnabled(true);
            chkDeldoc.setEnabled(true);
            
            txtNombre.requestFocus();
            
            lAgregar = false;
        }
    }
    
    public void deshabilitar(){
        txtCodigo.setEnabled(false); 
        txtNombre.setEnabled(false); txtUsuario.setEnabled(false); txtCorreo.setEnabled(false);
        txtRutaImg.setEnabled(false); txtClaveUser2.setEnabled(false); //txtClaveUser.setVisible(true);
        //txtClaveUser.setEnabled(false);
            
        cbCargo.setEnabled(false); cbTipo.setEnabled(false);
        btnBuscaImg.setEnabled(false);
        
        chkActivo.setEnabled(false); 
//        chkAndroid.setEnabled(false); chkWeb.setEnabled(false);
        chkDeldoc.setEnabled(false);
    }
    
    private void limpiar(){        
        txtNombre.setText(""); txtUsuario.setText("");  txtCorreo.setText("");  txtRutaImg.setText(""); 
        txtClaveUser2.setText(""); 
        chkActivo.setSelected(true); 
//        chkAndroid.setEnabled(false); chkWeb.setEnabled(false);
        chkDeldoc.setSelected(false);
        
        Clave = "";
    }
    
    public void posicion_botones_1(){
        btnAgregar.setEnabled(true);
        
        btnModificar.setVisible(true); btnBuscar.setVisible(true); btnAtras.setVisible(true);
        btnSiguiente.setVisible(true); btnSalir.setVisible(true); btnEliminar.setVisible(true);
        
        btnGuardar.setVisible(false); btnCancelar.setVisible(false);
    }
    
    public void posicion_botones_2(){
        btnAgregar.setEnabled(false);
        
        btnModificar.setVisible(false); btnBuscar.setVisible(false); btnAtras.setVisible(false);
        btnSiguiente.setVisible(false); btnSalir.setVisible(false); btnEliminar.setVisible(false);
        
        btnGuardar.setVisible(true); btnCancelar.setVisible(true);
    }
    
    public void barButtonState1(){
        posicion_botones_1();
    }
    
    public void barButtonState2(){
        posicion_botones_2();
    }
    
    public void actualizaJtable(int item){
        cargaDatos(item);
        getTbTabla().getSelectionModel().setSelectionInterval(item, item);
    }
    
    public void cargaDatos(int Row){
        String code = null;
        Boolean lOperacion = false;
        if (Row>=0){
            code = (String) getTbTabla().getValueAt(Row,0).toString().trim();
            lOperacion = true;
        }
        
        ejecutaHilo(code,lOperacion);
    }
    
    public void ejecutaHilo(String codigo,Boolean lAgregar){
        rs = controladorCrearUsuarios.ejecutaHilo(codigo, lAgregar);
        
        if(lAgregar==false){
            try {
                claveUser = rs.getString("OPE_CLAVE").trim();
        
                if (tbTabla.getRowCount()>=0){
                    //mostrarImagen(rs.getString("OPE_RUTAIMG"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorCrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void mostrarImagen(String rutaImg) throws SQLException{
        //if (Reg_count > 0){
            String ruta_img_fondo = rutaImg; 
            
            try{
                if (rutaImg==null || ruta_img_fondo.trim().isEmpty()){
                    Image preview = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"/build/classes/imagenes/fondo_dnet.png");
                    ImageIcon icon = new ImageIcon(preview.getScaledInstance(lbImgFondo.getWidth(), lbImgFondo.getHeight(), Image.SCALE_AREA_AVERAGING));
                    lbImgFondo.setIcon(icon);
                }else{
                    Image preview = Toolkit.getDefaultToolkit().getImage(rs.getString("OPE_RUTAIMG").trim());
                    ImageIcon icon = new ImageIcon(preview.getScaledInstance(lbImgFondo.getWidth(), lbImgFondo.getHeight(), Image.SCALE_AREA_AVERAGING));
                    lbImgFondo.setIcon(icon);
                }
            }catch (Exception ex){
                Image preview = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"/build/classes/imagenes/fondo_dnet.png");
                ImageIcon icon = new ImageIcon(preview.getScaledInstance(lbImgFondo.getWidth(), lbImgFondo.getHeight(), Image.SCALE_AREA_AVERAGING));
                lbImgFondo.setIcon(icon);
            }
        //}
    }
    
    public void cargaTabla(){
//        controladorCrearUsuarios.setComponentes(txtCodigo, txtNombre, txtClaveUser2, txtUsuario, txtCorreo, txtRutaImg,
//                                                chkActivo, chkAndroid, chkWeb, cbCargo, cbTipo, lbImgFondo, chkDeldoc);
        controladorCrearUsuarios.setComponentes(txtCodigo, txtNombre, txtClaveUser2, txtUsuario, txtCorreo, txtRutaImg,
                                                chkActivo, cbCargo, cbTipo, lbImgFondo, chkDeldoc);
        controladorCrearUsuarios.cargaTabla(tbTabla);
    }
    
    public void action_bt_agregar(){
        lAgregar = true;
    }
    
    public void action_bt_modificar(){
        lAgregar = false;
    }
    
    public void action_bt_grabar(){
        String codigo, nombre, rif, direccion="", activo, deldoc;
        
        codigo  = txtCodigo.getText();
        nombre = txtNombre.getText();
                
        if (chkActivo.isSelected()){
            activo = "1";
        } else{
            activo = "0";
        }
        if(chkDeldoc.isSelected()){
            deldoc = "1";
        }else{
            deldoc = "0";
        }
        if (codigo.equals("")){
            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgAddCode"), "Notificación",
                                          JOptionPane.WARNING_MESSAGE);
            txtCodigo.requestFocus();
            return;
        }
        if (nombre.equals("")){
            JOptionPane.showMessageDialog(null,idioma.loadLangMsg().getString("MsgAddDescrip"), "Notificación",
                                          JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return;
        }
        if (cbTipo.getSelectedItem().toString().trim().equals("")){
            //JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgAddUserSist"));
            JOptionPane.showMessageDialog(null, "Debe indicar el grupo de Permisologias al cual pertenese este Usuario", "Notificación",
                                          JOptionPane.WARNING_MESSAGE);
            cbTipo.requestFocus();
            
            return;
        }
        
        if(lAgregar == true){
            int existe = 0;
            //existe = controladorGrupoPermisos.validaCodigo(codigo);
        
            if(existe > 0){
                txtCodigo.setText("");
                txtCodigo.requestFocus();
                return;
            }
        }    
        controladorCrearUsuarios.save(codigo, nombre, activo, tbTabla, lAgregar, deldoc, origen);
        posicion_botones_1();
        deshabilitar();
    }
    
    public void action_bt_eliminar(){
        String codigo = txtCodigo.getText().trim();
        
        if(codigo.trim().isEmpty()){
            codigo = (String) tbTabla.getValueAt(tbTabla.getSelectedRow(),0).toString().trim();
        }        
        int salir = JOptionPane.showConfirmDialog(null,idioma.loadLangMsg().getString("MsgDelEmp")+codigo+"?");
        
        if(salir == 0){
            controladorCrearUsuarios.delete(codigo, tbTabla);
        }
    }
    
    public void action_bt_cancelar(){
        int eleccion = 0;
        
        if (lAgregar==true){
            eleccion = JOptionPane.showConfirmDialog(null,idioma.loadLangMsg().getString("MsgCancelAdd"));
        }else{
            eleccion = JOptionPane.showConfirmDialog(null,idioma.loadLangMsg().getString("MsgCancelEdit"));
        }
        if(eleccion==0){
            //ejecutaHilo("", false);
            
//            try {
//                if(rs.getRow()<=0){
                if(tbTabla.getRowCount()<=0){
                    crearUsuarios = (CreaUsuarios) getVista();
                    crearUsuarios.dispose();
                    setVista(null);
                }else{
                    posicion_botones_1();
                    deshabilitar();
                }
//            } catch (SQLException ex) {
//                Logger.getLogger(ModelCreaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
    }
    
    public void actionBtnBuscarCarPadre(){
        ListaDeMaestros buscarUsuarios = new ListaDeMaestros("DNUSUARIOS", "", getOrigen());
        buscarUsuarios.setModal(true);

        Dimension desktopSize = escritorioERP.getSize();
        Dimension jInternalFrameSize = buscarUsuarios.getSize();
        buscarUsuarios.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

//        escritorioERP.add(buscarUsuarios);
        buscarUsuarios.toFront();
        buscarUsuarios.setVisible(true);
    }
    
    public void buscaUsuario(String codigo){
        int row = 0;
        for(int i=0;i<tbTabla.getRowCount();i++){
            if(codigo.equals(tbTabla.getValueAt(i,0))){
                row = i;
                tbTabla.getSelectionModel().setSelectionInterval(i, i);
            
                break;
            }
        }
        
        ejecutaHilo(codigo, true);
        
        //this.codigo.setText((String)tbTabla.getValueAt(row,0));
        //this.nombre.setText((String)tbTabla.getValueAt(row,1));
        //this.clave.setText((String)tbTabla.getValueAt(row,2));
        //this.usuario.setText((String)tbTabla.getValueAt(row,1));
    }

    public JTable getTbTabla() {
        return tbTabla;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public void setBtnAgregar(JButton btnAgregar) {
        this.btnAgregar = btnAgregar;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public void setBtnModificar(JButton btnModificar) {
        this.btnModificar = btnModificar;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JButton getBtnAtras() {
        return btnAtras;
    }

    public void setBtnAtras(JButton btnAtras) {
        this.btnAtras = btnAtras;
    }

    public JButton getBtnSiguiente() {
        return btnSiguiente;
    }

    public void setBtnSiguiente(JButton btnSiguiente) {
        this.btnSiguiente = btnSiguiente;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public JTextField getTxtRutaImg() {
        return txtRutaImg;
    }

    public JCheckBox getChkActivo() {
        return chkActivo;
    }

//    public JCheckBox getChkAndroid() {
//        return chkAndroid;
//    }
//
//    public JCheckBox getChkWeb() {
//        return chkWeb;
//    }

    public String getClaveUser() {
        return claveUser;
    }   

    public JComboBox getCbCargo() {
        return cbCargo;
    }

    public JComboBox getCbTipo() {
        return cbTipo;
    }

    public JPasswordField getTxtClaveUser2() {
        return txtClaveUser2;
    }
}