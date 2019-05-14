package Modelos;

import Controlador.ControladorMoneda;
import Vista.Empresas;
import Vista.ListaDeMaestros;
import Vista.Moneda;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXComboBox;
import util.Internacionalizacion;

public class ModelMoneda {
    private boolean lAgregar;
    private ResultSet rs;
//    private static ModelTipoContacto modelTipoContacto;
    private Object aThis, otherThis;
    private JTextField txtCodigo, txtDescri, txtNomen, txtValor;
    private JDateChooser dFecha;
    private JTable tbTabla;
    private JLabel lbCalculo;
    private JCheckBox chkActivo;
    private JButton btnAgregar,btnModificar,btnGuardar,btnEliminar,btnCancelar,btnBuscar,btnAtras,btnSiguiente,btnSalir;
    private JXComboBox cbIsoMoneda;
    private JRadioButton rbDivide, rbMultiplica;
    private JDesktopPane desktopForm;
    
    private String orgCodEmp="", orgCall = "";

    private static ModelMoneda modelMoneda;
    private ControladorMoneda controladorMoneda = new ControladorMoneda();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    
    public ModelMoneda() {
    }
    
    public static ModelMoneda getModelMoneda(){
        if(modelMoneda==null){
            modelMoneda = new ModelMoneda();
        }
        return modelMoneda;
    }
    
    public void setVista(Object aThis){
        this.aThis = aThis;
    }
    
    public Object getVista(){
        return aThis;
    }

    public Object getOtherThis() {
        return otherThis;
    }

    public void setOtherThis(Object otherThis) {
        this.otherThis = otherThis;
    }

    public String getOrgCall() {
        return orgCall;
    }

    public void setOrgCall(String orgCall) {
        this.orgCall = orgCall;
    }

    public JTable getTbTabla() {
        return tbTabla;
    }

    public String getOrgCodEmp() {
        return orgCodEmp;
    }

    public void setOrgCodEmp(String orgCodEmp) {
        this.orgCodEmp = orgCodEmp;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtDescri() {
        return txtDescri;
    }

    public JTextField getTxtNomen() {
        return txtNomen;
    }

    public JTextField getTxtValor() {
        return txtValor;
    }
    
    public void setButton(JButton agregar,JButton modificar,JButton grabar,JButton eliminar,JButton cancelar,JButton buscar,JButton atras,JButton siguiente,JButton salir){
        this.btnAgregar   = agregar;
        this.btnModificar = modificar;
        this.btnGuardar   = grabar;
        this.btnEliminar  = eliminar;
        this.btnCancelar  = cancelar;
        this.btnBuscar    = buscar;
        this.btnAtras     = atras;
        this.btnSiguiente = siguiente;
        this.btnSalir     = salir;
    }
    
    public void setComponent(JTextField Codigo, JTextField Descri, JTextField Nomen,
                             JTextField Valor, JDateChooser Fecha, JTable Tabla, 
                             JCheckBox Activo, JXComboBox cbIsoMoneda, JRadioButton rbMultiplica, 
                             JRadioButton rbDivide, JLabel lbCalculo){
        this.txtCodigo    = Codigo;
        this.txtDescri    = Descri;
        this.txtNomen     = Nomen;
        this.txtValor     = Valor;
        this.dFecha       = Fecha;
        this.tbTabla      = Tabla;
        this.chkActivo    = Activo;
        this.cbIsoMoneda  = cbIsoMoneda;
        this.rbMultiplica = rbMultiplica;
        this.rbDivide     = rbDivide;
        this.lbCalculo    = lbCalculo;
    }
    
    public void cargaTabla(){
        controladorMoneda.cargaTabla(tbTabla);
    }
    
    public void habilitar(String accion){
        if(accion.equals("Inicializa")){
            //txtCodigo.setEnabled(true); 
            txtDescri.setEnabled(true);
            txtNomen.setEnabled(true);
            txtValor.setEnabled(true);
            chkActivo.setEnabled(true);
            chkActivo.setSelected(true);
            dFecha.setEnabled(true);
            dFecha.setDate(new Date());
            
            cbIsoMoneda.setEnabled(true);
            rbMultiplica.setEnabled(true);
            rbDivide.setEnabled(true);
            
            if(!getOrgCodEmp().isEmpty()){
                rbMultiplica.setVisible(false);
                rbDivide.setVisible(false);
                lbCalculo.setVisible(false);
            }else{
                rbMultiplica.setVisible(true);
                rbDivide.setVisible(true);
                lbCalculo.setVisible(true);
            }
            
            limpiar();
            
            lAgregar = true;
            
            txtDescri.requestFocus();
        }else if (accion=="Modificar"){
            txtDescri.setEnabled(true);
            txtNomen.setEnabled(true);
            txtValor.setEnabled(true);
            dFecha.setEnabled(true);
            chkActivo.setEnabled(true);
            
            cbIsoMoneda.setEnabled(true);
            rbMultiplica.setEnabled(true);
            rbDivide.setEnabled(true);
            
            lAgregar = false;
        }
    }
    
    public void deshabilitar(){
        txtCodigo.setEnabled(false); txtDescri.setEnabled(false);
        txtNomen.setEnabled(false);  txtValor.setEnabled(false);
        chkActivo.setEnabled(false); dFecha.setEnabled(false);  
        
        cbIsoMoneda.setEnabled(false);
        rbMultiplica.setEnabled(false); rbDivide.setEnabled(false);
    }
    
    public void posicion_botones_1(){
        btnAgregar.setEnabled(true);   btnAgregar.setVisible(true); 
        btnModificar.setVisible(true); btnBuscar.setVisible(true); btnAtras.setVisible(true);
        btnSiguiente.setVisible(true); btnSalir.setVisible(true); btnEliminar.setVisible(true);
        
        btnGuardar.setVisible(false); btnCancelar.setVisible(false);
    }
    
    public void posicion_botones_2(){
        btnAgregar.setEnabled(false); btnAgregar.setVisible(false);
        
        if (VarGlobales.isVisualizaUltimoRegForm()){
            btnModificar.setVisible(false); btnBuscar.setVisible(false); btnAtras.setVisible(false);
            btnSiguiente.setVisible(false); btnSalir.setVisible(false); btnEliminar.setVisible(false);
        }else{
            btnModificar.setVisible(false); btnBuscar.setVisible(true); btnAtras.setVisible(false);
            btnSiguiente.setVisible(false); btnSalir.setVisible(false); btnEliminar.setVisible(true);
        }
        
        btnGuardar.setVisible(true); btnCancelar.setVisible(true);
    }
    
    public void limpiar(){
        txtDescri.setText(""); txtNomen.setText("");
        txtValor.setText("0,00");
        
        cbIsoMoneda.setSelectedIndex(0);
        rbMultiplica.setSelected(false); rbDivide.setSelected(false);
    }
    
    public void setConsecutivo(){
        txtCodigo.setText(controladorMoneda.codConsecutivo());
    }
    
    public void actualizaJTable(int item){
        cargaDatos(item);
        tbTabla.getSelectionModel().setSelectionInterval(item, item);
    }
    
    public void cargaDatos(int Row){
        String code = null;
        Boolean lOperacion = false;
        if (Row>=0){
            code = (String) tbTabla.getValueAt(Row,0).toString().trim();
            lOperacion = true;
        }
        rs = controladorMoneda.ejecutaHilo(code,lOperacion);
        mostrar();
    }
    
    public void ejecutaHilo(){
        cargaDatos(-1);
    }
    
    public void mostrar(){
        try {
            if(rs.getRow()>0){
                txtCodigo.setText(rs.getString("MON_CODIGO"));
                txtDescri.setText(rs.getString("MON_NOMBRE"));
                txtNomen.setText(rs.getString("MON_NOMENC"));
                
                String valor = ""+VarGlobales.getValorBigDecimalFormat(rs.getString("MON_VALOR"));
                txtValor.setText(valor);
                //txtValor.setText(rs.getString("MON_VALOR"));
                
                System.err.println(VarGlobales.getMonedaEmpresa()+" - "+rs.getString("MON_NOMENC"));
                try {
                    if(VarGlobales.getMonedaEmpresa().equals(rs.getString("MON_NOMENC"))){
                        rbMultiplica.setVisible(false);
                        rbDivide.setVisible(false);
                        lbCalculo.setVisible(false);
                    }else{
                        rbMultiplica.setVisible(true);
                        rbDivide.setVisible(true);
                        lbCalculo.setVisible(true);
                    }
                } catch (Exception e) {
                }
                
                if(rs.getBoolean("MON_ACTIVO")){
                    chkActivo.setSelected(true);
                }else{
                    chkActivo.setSelected(false);
                }
                
                if(rs.getString("mon_multi_div").equals("*")){
                    rbMultiplica.setSelected(true);
                    rbDivide.setSelected(false);
                }
                if(rs.getString("mon_multi_div").equals("/")){
                    rbMultiplica.setSelected(false);
                    rbDivide.setSelected(true);
                }
                
                cbIsoMoneda.setSelectedItem((String)rs.getString("mon_cod_iso"));
                
                if(rs.getString("MON_FCHVIG")!=null){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date fch = null;

                    try {
                        fch = sdf.parse(rs.getString("MON_FCHVIG"));
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    dFecha.setDate(fch);
                }else{
                    dFecha.setDate(null);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelMoneda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void action_bt_grabar(){
        if(txtDescri.getText().equals("")){
            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgAddDescrip"), 
                                          idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(txtNomen.getText().equals("")){
            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgNomen"), 
                                          idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(txtValor.getText().equals("")){
            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgValor"), 
                                          idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String codIso = cbIsoMoneda.getSelectedItem().toString();
        if(codIso.equals(" ")){
            
            return;
        }
        if(dFecha.getDate()==null){
            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgIntroFecha"), 
                                          idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }
        String activo;
        if(chkActivo.isSelected()){
            activo = "1";
        }else{
            activo = "0";
        }
        Date date = dFecha.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fch = sdf.format(date);
        String fechavigen = fch, multiplicaDivide = "";
        
        if (rbMultiplica.isSelected()){
            multiplicaDivide = "*";
        }
        if (rbDivide.isSelected()){
            multiplicaDivide = "/";
        }
        
        controladorMoneda.save(txtCodigo.getText(), txtDescri.getText(), txtNomen.getText(),
                               txtValor.getText(), fechavigen, activo, lAgregar, multiplicaDivide, 
                               codIso, getOrgCodEmp());
        
        if (VarGlobales.isVisualizaUltimoRegForm()){
            posicion_botones_1();
            deshabilitar();
            cargaTabla();
        }else{
            habilitar("Inicializa");
            posicion_botones_2();
            setConsecutivo();
            
            cargaTabla();
        }
    }
    
    public void action_bt_eliminar(){
        String codigo = txtCodigo.getText();
        
        int salir = JOptionPane.showConfirmDialog(null,idioma.loadLangMsg().getString("MsgEliminarCode")+" "+codigo+"?", 
                                                  idioma.loadLangMsg().getString("MsgTituloNotif"),
                                                  JOptionPane.YES_NO_OPTION);
        
        if(salir == 0) {
            controladorMoneda.delete(txtCodigo.getText(), tbTabla);
            cargaTabla();
            
            if (VarGlobales.isVisualizaUltimoRegForm()){
                if(tbTabla.getRowCount()>0){
                    ejecutaHilo();
                }else{
                    Moneda acti = (Moneda) aThis;
                    acti.dispose();
                    setVista(null);
                }
            }else{
                habilitar("Inicializa");
                posicion_botones_2();
                setConsecutivo();
            
                cargaTabla();
            }
        }
    }
    
    public void action_bt_cancelar(){
        int eleccion = 0;
        
        if(lAgregar){
            eleccion = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgCancelAdd")+"?", 
                                                     idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                                     JOptionPane.YES_NO_OPTION);
        }else{
            eleccion = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgCancelEdit")+"?", 
                                                     idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                                     JOptionPane.YES_NO_OPTION);
        }
        if(eleccion == 0){
            if (VarGlobales.isVisualizaUltimoRegForm()){
                if (tbTabla.getRowCount()==0){
                    Moneda tipo = (Moneda) getVista();
                    tipo.dispose();
                    setVista(null);

                    if (modelMoneda.getOrgCall().equals("Empresa")){
                        Empresas empresas = (Empresas) modelMoneda.getOtherThis();
                        empresas.requestFocus();

                        ModelEmpresas modelEmpresas = ModelEmpresas.getModelEmpresas();
                        modelEmpresas.getCmbMoneda().requestFocus();
                    }
                }else{
                    limpiar();
                    deshabilitar();
                    posicion_botones_1();
                    ejecutaHilo();
                }
            }else{
                Moneda tipo = (Moneda) getVista();
                tipo.dispose();
                setVista(null);
            }
        }
    }

    public void actionBtnBuscar(){
        ListaDeMaestros buscarUbicacion = new ListaDeMaestros("dnmoneda", "", "");
        buscarUbicacion.setModal(true);
        
        Dimension desktopSize = desktopForm.getSize();
        Dimension jInternalFrameSize = buscarUbicacion.getSize();
        buscarUbicacion.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

//        desktopForm.add(buscarUbicacion);
        buscarUbicacion.toFront();
        buscarUbicacion.setVisible(true);
    }
    
    public void buscarMoneda(String codigo){
        if (VarGlobales.isVisualizaUltimoRegForm()){
            lAgregar = true;
            rs = controladorMoneda.buscarMoneda(codigo, lAgregar);
            mostrar();
        }else{
            lAgregar = false;
            rs = controladorMoneda.buscarMoneda(codigo, lAgregar);
            mostrar();
        }
    }
    
    public void setDesktop(JDesktopPane desktopForm) {
        this.desktopForm = desktopForm;
    }
}