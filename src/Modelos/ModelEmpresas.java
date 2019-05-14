package Modelos;

//import Controlador.ControladorBarButton;
import Controlador.ControladorBarButton;
import Controlador.ControladorEmpresas;
import Vista.Empresas;
import static Vista.Empresas.elementos;
import Vista.ListaDeMaestros;
import static Vista.MenuPrincipal.escritorioERP;
import app.bolivia.swing.JCTextField;
import java.awt.Dimension;
import java.awt.Image;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXComboBox;
import util.Internacionalizacion;

public class ModelEmpresas extends conexion{
    private Empresas empresas; 
    private static ModelEmpresas modelEmpresas;
    private Object aThis, aThisDos;
    private JTextField txtCodigo,txtNombre,txtCodsuc,txtIdsuc,txtNomsuc,txtTelefono,txtCorreo,txtFax,txtPagWeb;
    private JFormattedTextField txtRif;
    private JTextArea txtDireccion,txtDirsuc;
    private JCheckBox chkActivo,chkSucursal,chkEmpPredeterminada;
    
    private JTextField tfNombPrecio1, tfNombPrecio2, tfNombPrecio3, tfNombPrecio4, tfNombPrecio5, tfLongConsec,
                       userComprobElectro, urlApiRest, urlAccesToken, ubicArchCertificado, tfUltimoConseCbtElectron, tfUltimoConseTiqueteElectronico, 
                       tfUltimoConseNotaCreditoElectronica, tfUltimoConseNotaDebitoElectronica, tfUltimoConseReceptor;
    private JCTextField tfCorreoOrigen, tfServidorSmtp, tfAsuntoCorreo, tfServidorPuerto;
    private JComboBox cbServidorPuerto;
    private JTextArea taCuerpoCorreo;
    private JPasswordField tfPassMail;
    private JFormattedTextField tfUtilidPrecio1, tfUtilidPrecio2, tfUtilidPrecio3, tfUtilidPrecio4, tfUtilidPrecio5;   
    private JCheckBox chkUtilCosto, chkCodConsecutivo, chkUsaFactElectronica, chkCodConsecutivoGrupSubGrup, chkVisualizaUltimoRegForm;
    private JPanel pnConfFactElectronica;
    private JXComboBox cbProveedServicio, cbTipIdentificador, cbEstado, cbMunicipio, cbParroquia, cbSector, cbEstado1, cbMunicipio1, cbParroquia1, 
                       cbSector1;
    private JButton btnUbicacionArchCertificado;
    private JPasswordField claveComprobElectro, claveArchCerticado;
    
    private JTable tbTabla,tbSucursal;
    private JTabbedPane tabPestaña;
    private JComboBox cbEmpresa, cmbMoneda;
    private JchomboBox cmbPais;
    private JLabel jFondo_Preview;
    
    public boolean lAgregar = false, lAgregarSuc = false;;
    private ResultSet rs,rsSuc;
    private JButton btnAgregar,btnModificar,btnGuardar,btnEliminar,btnCancelar,btnBuscar,btnSalir, btn_buscarimg; //btnAtras,btnSiguiente,
    private int Pestaña = 0;
    private JDesktopPane desktop;
    private String origen, rutaFoto, nombreFoto;
    private static String codigoEmpresa = "";
    
    private CallableStatement cs = null;
    private PreparedStatement ps = null;
    
    private final ControladorEmpresas controladorEmpresas = new ControladorEmpresas();
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    private final ControladorBarButton controladorBarButton = new ControladorBarButton();

    private ModelEmpresas(){
        
    }

    public static ModelEmpresas getModelEmpresas(){
        if(modelEmpresas == null){
            modelEmpresas= new ModelEmpresas();
        }
        
        return modelEmpresas;
    }

    public void setVista(Object aThis){
        this.aThis = aThis;
    }
    
    public Object getVista(){
        return aThis;
    }

    public void setVistaDos(Object aThis){
        this.aThisDos = aThis;
    }
    
    public Object getVistaDos(){
        return aThisDos;
    }

    public boolean islAgregar() {
        return lAgregar;
    }

    public void setlAgregar(boolean lAgregar) {
        this.lAgregar = lAgregar;
    }
    
    public void setComponent(JTextField codigo,JFormattedTextField rif,JTextField nombre,JTextArea direccion,JCheckBox activo,
                             JTable tabla,JTabbedPane tab,JComboBox comboemp,JTextField codsur,JTextField idsur,
                             JTextField nomsur,JTextArea dirsur,JTable tablasur,JCheckBox sucursal, JComboBox cmbMoneda,
                             JchomboBox cmbPais,JTextField txtTelefono, JTextField txtCorreo, JTextField txtFax, JTextField txtPagWeb,
                             JXComboBox cbTipIdentificador, JXComboBox cbEstado, JXComboBox cbMunicipio, JXComboBox cbParroquia, 
                             JXComboBox cbSector, JXComboBox cbEstado1, JXComboBox cbMunicipio1, JXComboBox cbParroquia1, 
                             JXComboBox cbSector1, JCheckBox chkEmpPredeterminada){
        this.txtCodigo    = codigo;
        this.txtRif       = rif;
        this.txtNombre    = nombre;
        this.txtDireccion = direccion;
        this.chkActivo    = activo;
        this.tbTabla      = tabla;
        this.tabPestaña   = tab;
        this.cbEmpresa    = comboemp;
        this.txtCodsuc    = codsur;
        this.txtIdsuc     = idsur;
        this.txtNomsuc    = nomsur;
        this.txtDirsuc    = dirsur;
        this.tbSucursal   = tablasur;
        this.chkSucursal  = sucursal;
        this.cmbMoneda    = cmbMoneda;
        this.cmbPais      = cmbPais;
        this.txtTelefono  = txtTelefono;
        this.txtCorreo    = txtCorreo;
        this.txtFax       = txtFax;
        this.txtPagWeb    = txtPagWeb;
        this.cbTipIdentificador   = cbTipIdentificador;
        this.cbEstado             = cbEstado;
        this.cbMunicipio          = cbMunicipio;
        this.cbParroquia          = cbParroquia;
        this.cbSector             = cbSector;
        this.cbEstado1            = cbEstado1;
        this.cbMunicipio1         = cbMunicipio1;
        this.cbParroquia1         = cbParroquia1;
        this.cbSector1            = cbSector1;
        this.chkEmpPredeterminada = chkEmpPredeterminada;
    }
    
    public void setComponentConfMailOrigen(JCTextField tfCorreoOrigen, JPasswordField tfPassMail, JCTextField tfServidorSmtp, 
                                           JCTextField tfAsuntoCorreo, JCTextField tfServidorPuerto, JTextArea taCuerpoCorreo,
                                           JComboBox cbServidorPuerto) {
        this.tfCorreoOrigen   = tfCorreoOrigen;
        this.tfPassMail       = tfPassMail;
        this.tfServidorSmtp   = tfServidorSmtp;
        this.tfAsuntoCorreo   = tfAsuntoCorreo;
        this.tfServidorPuerto = tfServidorPuerto;
        this.taCuerpoCorreo   = taCuerpoCorreo;
        this.cbServidorPuerto = cbServidorPuerto;
    }
    
    public void setComponentConfEmp(JTextField nombPrecio1, JTextField nombPrecio2, JTextField nombPrecio3, JTextField nombPrecio4,
                                    JTextField nombPrecio5, JFormattedTextField utilidPrecio1, JFormattedTextField utilidPrecio2,
                                    JFormattedTextField utilidPrecio3, JFormattedTextField utilidPrecio4, JFormattedTextField utilidPrecio5,
                                    JCheckBox utilCosto, JCheckBox consecutivo, JTextField longConsec, JCheckBox consecGrupSubGrup,
                                    JCheckBox visualizaUltimoRegForm, JLabel jFondo_Preview){
        this.tfNombPrecio1     = nombPrecio1;
        this.tfNombPrecio2     = nombPrecio2;
        this.tfNombPrecio3     = nombPrecio3;
        this.tfNombPrecio4     = nombPrecio4;
        this.tfNombPrecio5     = nombPrecio5;
        this.tfUtilidPrecio1   = utilidPrecio1;
        this.tfUtilidPrecio2   = utilidPrecio2;
        this.tfUtilidPrecio3   = utilidPrecio3;
        this.tfUtilidPrecio4   = utilidPrecio4;
        this.tfUtilidPrecio5   = utilidPrecio5;
        this.chkUtilCosto      = utilCosto;
        this.chkCodConsecutivo = consecutivo;
        this.tfLongConsec      = longConsec;
        this.chkCodConsecutivoGrupSubGrup = consecGrupSubGrup;
        this.chkVisualizaUltimoRegForm    = visualizaUltimoRegForm;
        this.jFondo_Preview               = jFondo_Preview;
    }
    
    public void setComponentConfEmpFactElectron(JCheckBox chkUsaFactElectronica, JPanel pnConfFactElectronica, JXComboBox cbProveedServicio, 
                                                JTextField userComprobElectro, JPasswordField claveComprobElectro, JTextField urlApiRest, 
                                                JTextField ubicArchCertificado, JButton btnUbicacionArchCertificado, 
                                                JPasswordField claveArchCerticado, JTextField tfUltimoConseCbtElectron, 
                                                JTextField tfUltimoConseTiqueteElectronico, JTextField tfUltimoConseNotaCreditoElectronica, 
                                                JTextField tfUltimoConseNotaDebitoElectronica, JTextField urlAccesToken, 
                                                JTextField tfUltimoConseReceptor) {
        this.chkUsaFactElectronica               = chkUsaFactElectronica;
        this.pnConfFactElectronica               = pnConfFactElectronica;
        this.cbProveedServicio                   = cbProveedServicio;
        this.userComprobElectro                  = userComprobElectro;
        this.claveComprobElectro                 = claveComprobElectro;
        this.urlApiRest                          = urlApiRest;
        this.urlAccesToken                       = urlAccesToken;
        this.ubicArchCertificado                 = ubicArchCertificado;
        this.btnUbicacionArchCertificado         = btnUbicacionArchCertificado;
        this.claveArchCerticado                  = claveArchCerticado;
        this.tfUltimoConseCbtElectron            = tfUltimoConseCbtElectron;
        this.tfUltimoConseTiqueteElectronico     = tfUltimoConseTiqueteElectronico;
        this.tfUltimoConseNotaCreditoElectronica = tfUltimoConseNotaCreditoElectronica;
        this.tfUltimoConseNotaDebitoElectronica  = tfUltimoConseNotaDebitoElectronica;
        this.tfUltimoConseReceptor               = tfUltimoConseReceptor;
    }
    
    public void setButton(JButton agregar,JButton modificar,JButton grabar,JButton eliminar,JButton cancelar,JButton buscar,
                          JButton salir, JButton btn_buscarimg){
        this.btnAgregar    = agregar;
        this.btnModificar  = modificar;
        this.btnGuardar    = grabar;
        this.btnEliminar   = eliminar;
        this.btnCancelar   = cancelar;
        this.btnBuscar     = buscar;
        //this.btnAtras     = atras;
        //this.btnSiguiente = adelante;
        this.btnSalir      = salir;
        this.btn_buscarimg = btn_buscarimg;
    }

    public JDesktopPane getDesktop() {
        return desktop;
    }

    public void setDesktop(JDesktopPane desktop) {
        this.desktop = desktop;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
    
    public JTable getTabla(){
        return tbTabla;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public String getNombreFoto() {
        return nombreFoto;
    }

    public void setNombreFoto(String nombreFoto) {
        this.nombreFoto = nombreFoto;
    }
    
    public void getCodConsecutivo(){
        String empresa = "";
        if(tabPestaña.getSelectedIndex()==0){
            txtCodigo.setText(controladorEmpresas.codConsecutivo("empresa",""));
        }else if(tabPestaña.getSelectedIndex()==1){
            empresa = (String)cbEmpresa.getSelectedItem();
            if(empresa.equals("")){
                return;
            }
            txtCodsuc.setText(controladorEmpresas.codConsecutivo("sucursal", empresa));
        }
    }
    
    public void habilitar(String accion){
        if(tabPestaña.getSelectedIndex()==0){
            if (accion.equals("Inicializa")){
                txtNombre.setEnabled(true); txtRif.setEnabled(true);
                txtDireccion.setEnabled(true); txtTelefono.setEnabled(true);
                txtCorreo.setEnabled(true); txtFax.setEnabled(true); 
                txtPagWeb.setEnabled(true);
                
                chkActivo.setEnabled(true);  chkActivo.setSelected(true); chkEmpPredeterminada.setEnabled(true);
                
                cmbMoneda.setEnabled(true); cmbPais.setEnabled(true);
                cbTipIdentificador.setEnabled(true); cbEstado.setEnabled(true); 
                //cbMunicipio.setEnabled(true); cbParroquia.setEnabled(true); cbSector.setEnabled(true);
                
                cbEstado1.setVisible(false); cbMunicipio1.setVisible(false); cbParroquia1.setVisible(false); cbSector1.setVisible(false);
                
                btn_buscarimg.setEnabled(true);
                          
                limpiar();
                //txtRif.requestFocus();
                cbTipIdentificador.requestFocus();
                
                lAgregar = true;
            }else if (accion=="Modificar"){
                txtNombre.setEnabled(true); txtRif.setEnabled(true);
                txtDireccion.setEnabled(true); txtTelefono.setEnabled(true);
                txtCorreo.setEnabled(true); txtFax.setEnabled(true); 
                txtPagWeb.setEnabled(true);
                
                txtNombre.requestFocus();
                
                chkActivo.setEnabled(true); chkEmpPredeterminada.setEnabled(true);
            
                cmbMoneda.setEnabled(true); cmbPais.setEnabled(true);
                cbTipIdentificador.setEnabled(true); cbEstado.setEnabled(true); 
                cbMunicipio.setEnabled(true); cbParroquia.setEnabled(true); cbSector.setEnabled(true);
                
                cbEstado1.setVisible(false); cbMunicipio1.setVisible(false); cbParroquia1.setVisible(false); cbSector1.setVisible(false);
                
                btn_buscarimg.setEnabled(true);
                
                lAgregar = false;
            }
        }else if(tabPestaña.getSelectedIndex()==1){
            if (accion.equals("Inicializa")){
                cbEmpresa.setEnabled(true);
                
                txtNomsuc.setEnabled(true); txtIdsuc.setEnabled(true);
                txtDirsuc.setEnabled(true); chkSucursal.setEnabled(true);
                          
                limpiar();
                //lAgregar = true;
                lAgregarSuc = true;
            }else if (accion=="Modificar"){
                cbEmpresa.setEnabled(false);
                
                txtNomsuc.setEnabled(true); txtIdsuc.setEnabled(true);
                txtDirsuc.setEnabled(true); chkSucursal.setEnabled(true);
                txtNomsuc.requestFocus();
            
                //lAgregar = false;
                lAgregarSuc = false;
            }
        }
    }
    
    public void deshabilitar(){
        if(tabPestaña.getSelectedIndex()==0){
            txtNombre.setEnabled(false); txtRif.setEnabled(false);
            txtDireccion.setEnabled(false); txtTelefono.setEnabled(false);
            txtCorreo.setEnabled(false); txtFax.setEnabled(false); 
            txtPagWeb.setEnabled(false);
            
            chkActivo.setEnabled(false); chkEmpPredeterminada.setEnabled(false);
            
            cmbMoneda.setEnabled(false); cmbPais.setEnabled(false);
            cbTipIdentificador.setEnabled(false); cbEstado.setEnabled(false); cbMunicipio.setEnabled(false);
            cbParroquia.setEnabled(false); cbSector.setEnabled(false);
            
            btn_buscarimg.setEnabled(false);
        }else if(tabPestaña.getSelectedIndex()==1){
            cbEmpresa.setEnabled(true);
            
            txtNomsuc.setEnabled(false); txtIdsuc.setEnabled(false);
            txtDirsuc.setEnabled(false); chkSucursal.setEnabled(false);
        }
    }
    
    private void limpiar(){
        if(tabPestaña.getSelectedIndex()==0){
            txtNombre.setText(""); txtRif.setText("");
            txtDireccion.setText(""); txtTelefono.setText("");
            txtCorreo.setText(""); txtFax.setText(""); 
            txtPagWeb.setText("");
            
            ImageIcon iconLogo = new javax.swing.ImageIcon(getClass().getResource("/imagenes/select_imagen.png"));
            jFondo_Preview.setText("");
            jFondo_Preview.setIcon(iconLogo);
            jFondo_Preview.updateUI();
                                
            cmbMoneda.setSelectedIndex(0); 
            //cmbPais.setSelectedIndex(1);
            cmbPais.setSelectedItem(VarGlobales.getPais());
            
            try {
                cbTipIdentificador.setSelectedIndex(0); cbEstado.setSelectedIndex(0); cbMunicipio.setSelectedIndex(0);
                cbParroquia.setSelectedIndex(0); cbSector.setSelectedIndex(0);
                
                cbEstado1.setSelectedIndex(0); cbMunicipio1.setSelectedIndex(0); cbParroquia1.setSelectedIndex(0); cbSector1.setSelectedIndex(0);
            } catch (Exception e) {
            }
        }else if(tabPestaña.getSelectedIndex()==1){
            txtNomsuc.setText(""); txtIdsuc.setText("");
            txtDirsuc.setText(""); chkSucursal.setSelected(true);
        }
    }
    
    public void validaTablas(int clic, Boolean lInicio){
        if(lInicio){
            if(tabPestaña.getSelectedIndex()==0){
                chkActivo.setSelected(true);
                cargaMoneda();
                
                if(!lAgregar){
                    if (tbTabla.getRowCount()==0){
                        habilitar("Inicializa");
                        getCodConsecutivo();
                        posicion_botones_2();
                
                        txtCodigo.requestFocus();
                    }else{
                        posicion_botones_1();
                        deshabilitar();
                        btnGuardar.setVisible(false); btnCancelar.setVisible(false);
                        ejecutaHilo("", false);
            
                        tbTabla.getSelectionModel().setSelectionInterval(tbTabla.getRowCount()-1, tbTabla.getRowCount()-1);
                    }
                }
            }else if(tabPestaña.getSelectedIndex()==1){
                cargaCombo();

                if(!lAgregarSuc){
                    if (tbSucursal.getRowCount()==0){
                        habilitar("Inicializa");
                        getCodConsecutivo();
                        posicion_botones_2();
                
                        txtIdsuc.requestFocus();
                    }else{
                        posicion_botones_1();
                        deshabilitar();
                        //btnGuardar.setVisible(false); btnCancelar.setVisible(false);
                        ejecutaHilo("", false);
            
                        tbSucursal.getSelectionModel().setSelectionInterval(tbSucursal.getRowCount()-1, tbSucursal.getRowCount()-1);
                    }
                }
            }else if(tabPestaña.getSelectedIndex()==2){
                if(!lAgregar){
                    posicion_botones_1();
                    deshabilitar();
                    btnGuardar.setVisible(false); btnCancelar.setVisible(false);
                    ejecutaHilo("", false);
            
                    tbTabla.getSelectionModel().setSelectionInterval(tbTabla.getRowCount()-1, tbTabla.getRowCount()-1);
                }else{
                    chkUtilCosto.setSelected(false); chkCodConsecutivo.setSelected(false);
                    chkUsaFactElectronica.setSelected(false); chkCodConsecutivoGrupSubGrup.setSelected(false);
                    chkVisualizaUltimoRegForm.setSelected(false);
                
                    tfUltimoConseCbtElectron.setText("");
                    tfUltimoConseTiqueteElectronico.setText("");
                    tfUltimoConseNotaCreditoElectronica.setText("");
                    tfUltimoConseNotaDebitoElectronica.setText("");
                    tfUltimoConseReceptor.setText("");
                }
            }else if(tabPestaña.getSelectedIndex()==4){
                posicion_botones_1();
                deshabilitar();
                btnGuardar.setVisible(false); btnCancelar.setVisible(false);
                ejecutaHilo(txtCodigo.getText(), false);
            }
        }else{
            //if(Pestaña != clic){
                Pestaña = clic;
                if(tabPestaña.getSelectedIndex()==0){
                    chkActivo.setSelected(true);
                    cargaMoneda();
                
                    if (tbTabla.getRowCount()==0){
                        habilitar("Inicializa");
                        getCodConsecutivo();
                        posicion_botones_2();
                
                        txtCodigo.requestFocus();
                    }else{
                        posicion_botones_1();
                        deshabilitar();
                        btnGuardar.setVisible(false); btnCancelar.setVisible(false);
                        ejecutaHilo("", false);
            
                        tbTabla.getSelectionModel().setSelectionInterval(tbTabla.getRowCount()-1, tbTabla.getRowCount()-1);
                    }
                }else if(tabPestaña.getSelectedIndex()==1){
                    cargaCombo();
                    if (tbSucursal.getRowCount()==0){
                        habilitar("Inicializa");
                        getCodConsecutivo();
                        posicion_botones_2();
                
                        txtIdsuc.requestFocus();
                    }else{
                        posicion_botones_1();
                        deshabilitar();
                        //btnGuardar.setVisible(false); btnCancelar.setVisible(false);
                        ejecutaHilo("", false);
            
                        tbSucursal.getSelectionModel().setSelectionInterval(tbSucursal.getRowCount()-1, tbSucursal.getRowCount()-1);
                    }
                }else if(tabPestaña.getSelectedIndex()==2){
                    if (tbTabla.getRowCount()>0){
                        posicion_botones_1();
                        deshabilitar();
                        btnGuardar.setVisible(false); btnCancelar.setVisible(false);
                        ejecutaHilo(txtCodigo.getText(), false);

                        tbTabla.getSelectionModel().setSelectionInterval(tbTabla.getRowCount()-1, tbTabla.getRowCount()-1);
                    }else{
                        chkUtilCosto.setSelected(false); chkCodConsecutivo.setSelected(false);
                        chkUsaFactElectronica.setSelected(false); chkCodConsecutivoGrupSubGrup.setSelected(false);
                        chkVisualizaUltimoRegForm.setSelected(false);

                        tfUltimoConseCbtElectron.setText("");
                        tfUltimoConseTiqueteElectronico.setText("");
                        tfUltimoConseNotaCreditoElectronica.setText("");
                        tfUltimoConseNotaDebitoElectronica.setText("");
                        tfUltimoConseReceptor.setText("");
                    }
                }else if(tabPestaña.getSelectedIndex()==4){
                    posicion_botones_1();
                    deshabilitar();
                    btnGuardar.setVisible(false); btnCancelar.setVisible(false);
                    ejecutaHilo(txtCodigo.getText(), false);
                }
            //}
        }
    }
    
    public void posicion_botones_1(){
        if(tabPestaña.getSelectedIndex()==0){
            btnAgregar.setVisible(true);
        
            btnModificar.setVisible(true); btnBuscar.setVisible(true); 
            //btnAtras.setVisible(true); btnSiguiente.setVisible(true); 
            btnSalir.setVisible(true); btnEliminar.setVisible(true);
        
            btnGuardar.setVisible(false); btnCancelar.setVisible(false);
        }else if(tabPestaña.getSelectedIndex()==1){
            btnAgregar.setVisible(true);
        
            btnModificar.setVisible(true); btnBuscar.setVisible(false); 
            //btnAtras.setVisible(false); btnSiguiente.setVisible(false);
            btnSalir.setVisible(true); btnEliminar.setVisible(true);
        
            btnGuardar.setVisible(false); btnCancelar.setVisible(false);
        }
    }
    
    public void posicion_botones_2(){
        if(tabPestaña.getSelectedIndex()==0){
            btnAgregar.setVisible(false);
        
            btnModificar.setVisible(false); btnBuscar.setVisible(false); 
            //btnAtras.setVisible(false); btnSiguiente.setVisible(false); 
            btnSalir.setVisible(false); btnEliminar.setVisible(false);
        
            btnGuardar.setVisible(true); btnCancelar.setVisible(true);
            
            if (lAgregar){
                txtRif.requestFocus();
            }else{
                txtNombre.requestFocus();
            }
        }else if(tabPestaña.getSelectedIndex()==1){
            btnAgregar.setVisible(false);
        
            btnModificar.setVisible(false); btnBuscar.setVisible(false); 
            //btnAtras.setVisible(false); btnSiguiente.setVisible(false); 
            btnSalir.setVisible(false); btnEliminar.setVisible(false);
        
            btnGuardar.setVisible(true); btnCancelar.setVisible(true);
        }else if(tabPestaña.getSelectedIndex()==2){
            btnAgregar.setVisible(false);
        
            btnModificar.setVisible(false); btnBuscar.setVisible(false); 
            //btnAtras.setVisible(false); btnSiguiente.setVisible(false); 
            btnSalir.setVisible(false); btnEliminar.setVisible(false);
        
            btnGuardar.setVisible(true); btnCancelar.setVisible(true);
        }else if(tabPestaña.getSelectedIndex()==4){
            btnAgregar.setVisible(false);
        
            btnModificar.setVisible(false); btnBuscar.setVisible(false); 
            //btnAtras.setVisible(false); btnSiguiente.setVisible(false); 
            btnSalir.setVisible(false); btnEliminar.setVisible(false);
        
            btnGuardar.setVisible(true); btnCancelar.setVisible(true);
        }
    }
    
    public void barButtonState1(){
        //controladorBarButton.setButton(btnAgregar,btnModificar,btnGuardar,btnEliminar,btnCancelar,btnBuscar,btnAtras,btnSiguiente,btnSalir);
        controladorBarButton.setButton(btnAgregar,btnModificar,btnGuardar,btnEliminar,btnCancelar,btnBuscar,btnSalir);
        controladorBarButton.posicion_botones_1();
    }

    public void barButtonState2(){
        controladorBarButton.setButton(btnAgregar,btnModificar,btnGuardar,btnEliminar,btnCancelar,btnBuscar,btnSalir);
        controladorBarButton.posicion_botones_2();
    }
    
    public void actualizaJTable(int item){
        cargaDatos(item);
        if(tabPestaña.getSelectedIndex()==0){
            tbTabla.getSelectionModel().setSelectionInterval(item, item);
        }else if(tabPestaña.getSelectedIndex()==1){
            tbSucursal.getSelectionModel().setSelectionInterval(item, item);
        }
    }
    
    public void cargaDatos(int Row){
        String code = null;
        Boolean lOperacion = false;
        if (Row>=0){
            if(tabPestaña.getSelectedIndex()==0){
                code = (String) tbTabla.getValueAt(Row,0).toString().trim();
            }else if(tabPestaña.getSelectedIndex()==1){
                code = (String) tbSucursal.getValueAt(Row,1).toString().trim();
            }
            lOperacion = true;
        }
        
        ejecutaHilo(code,lOperacion);
    }
    
    public void ejecutaHilo(String codigo,Boolean lAgregar){
        String origen = "", empresa = "";
        rs = null; rsSuc = null;
        if(tabPestaña.getSelectedIndex()==0){
            origen = "empresa";
            rs = controladorEmpresas.ejecutaHilo(codigo, lAgregar, origen, empresa);
        }else if(tabPestaña.getSelectedIndex()==1){
            origen = "sucursal";
            empresa = (String)cbEmpresa.getSelectedItem();
            if(empresa.equals("")){
                return;
            }
            rsSuc = controladorEmpresas.ejecutaHilo(codigo, lAgregar, origen, empresa);
        }else if(tabPestaña.getSelectedIndex()==2){
            origen = "empresa";
            rs = controladorEmpresas.ejecutaHilo(codigo, lAgregar, origen, empresa);
        }else if(tabPestaña.getSelectedIndex()==4){
            origen = "empresa";
            rs = controladorEmpresas.ejecutaHilo(codigo, lAgregar, origen, empresa);
        }
        
        codigoEmpresa = txtCodigo.getText();
        mostrar();
    }
    
    public void mostrar(){
        if(tabPestaña.getSelectedIndex()==0){
            try {
                if (rs.getRow() > 0){
                    try {
                        limpiar();
                        
                        txtCodigo.setText(rs.getString("EMP_CODIGO")); 
                        txtNombre.setText(rs.getString("EMP_NOMBRE"));
                        txtDireccion.setText(rs.getString("EMP_DIRECCION"));
                        txtTelefono.setText(rs.getString("emp_telefono"));
                        txtCorreo.setText(rs.getString("emp_correo"));
                        txtFax.setText(rs.getString("emp_fax"));
                        txtPagWeb.setText(rs.getString("emp_paginaweb"));
                        
                        if(rs.getString("moneda")==null){
                            cmbMoneda.setSelectedItem(0);
                        }else{
                            cmbMoneda.setSelectedItem((String)rs.getString("moneda"));
                        }
                        
                        if(rs.getString("emp_pais")==null){
                            cmbPais.setSelectedItem(0);
                        }else{
                            int itemPais = 0;
                            String pais = (String)rs.getString("emp_pais");
                            
                            if(pais.equals("Costa Rica (es_CR)") || pais.equals("English (en_US)")){
                                //txtRif.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("############"))));
                                txtRif.setFormatterFactory(null);
                            }
                
                            if(pais.equals("Español (es_VE)")){
                                try {
                                    txtRif.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("U-########-#")));
                                } catch (java.text.ParseException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            
                            for (int i=0; i<elementos.size(); i++){
                                if (pais.equals((String) elementos.get(i))){
                                    itemPais = i;
                                    i=elementos.size();
                                }
                            }
                            
                            cmbPais.setSelectedItem(itemPais);
                        }
                        
                        cbTipIdentificador.setSelectedItem((String)rs.getString("emp_tipoidentificador"));
                        
                        try {
                            String estado = rs.getString("emp_estado");
                            String estadoNomb = estado.substring(0,estado.indexOf("-")-1).trim();
                            String estadoId = estado.substring(estado.indexOf("-")+1,estado.length()).trim();
                            cbEstado.setSelectedItem(estadoNomb);
                            cbEstado1.setSelectedItem(estadoId);
                        } catch (Exception e) {
                        }
                        
                        try {
                            String municipio = rs.getString("emp_municipio");
                            String municipioNomb = municipio.substring(0, municipio.indexOf("-")-1).trim();
                            String municipioId = municipio.substring(municipio.indexOf("-")+1,municipio.length()).trim();
                            cbMunicipio.setSelectedItem(municipioNomb);
                            cbMunicipio1.setSelectedItem(municipioId);
                        } catch (Exception e) {
                        }
                        
                        try {
                            String parroquia = rs.getString("emp_parroquia");
                            String parroquiaNomb = parroquia.substring(0, parroquia.indexOf("-")-1).trim();
                            String parroquiaId = parroquia.substring(parroquia.indexOf("-")+1, parroquia.length()).trim();
                            cbParroquia.setSelectedItem(parroquiaNomb);
                            cbParroquia1.setSelectedItem(parroquiaId);
                        } catch (Exception e) {
                        }
                        
                        cbEstado.setEnabled(false); cbMunicipio.setEnabled(false);
                        cbParroquia.setEnabled(false); cbSector.setEnabled(false);
                        
                        if(!rs.getString("emp_sector").isEmpty()){
                            String sector = rs.getString("emp_sector");
                            String sectorNomb = sector.substring(0, sector.indexOf("-")-1).trim();
                            String sectorId = sector.substring(sector.indexOf("-")+1, sector.length()).trim();
                            cbSector.setSelectedItem(sectorNomb);
                            cbSector1.setSelectedItem(sectorId);
                        }
                        
                        txtRif.setText(rs.getString("EMP_RIF").trim()); 
                        
                        if(rs.getBoolean("EMP_ACTIVO")){
                            chkActivo.setSelected(true);
                        }else{
                            chkActivo.setSelected(false);
                        }
                        
                        if(rs.getBoolean("emp_predeterminada")){
                            chkEmpPredeterminada.setSelected(true);
                        }else{
                            chkEmpPredeterminada.setSelected(false);
                        }
                        
                        ArrayList<Imagen> imagenes;
                        imagenes = VarGlobales.getImagenes(txtCodigo.getText());

                        if(imagenes.size()>0){
                            //ImageIcon icon = new ImageIcon(imagenes.get(0).getImagen().getScaledInstance(jFondo_Preview.getWidth(), jFondo_Preview.getHeight(), Image.SCALE_DEFAULT));
                            try {
                                //ImageIcon iconLogo = new javax.swing.ImageIcon(getClass().getResource("/imagenes/logos_empresas/"+imagenes.get(0).getNombre()));
                                System.err.println(System.getProperty("user.dir")+"\\build\\classes\\imagenes\\logos_empresas\\"+imagenes.get(0).getNombre());
                                ImageIcon iconLogo = new javax.swing.ImageIcon(System.getProperty("user.dir")+"\\build\\classes\\imagenes\\logos_empresas\\"+imagenes.get(0).getNombre());
                                ImageIcon tmpIcon = new ImageIcon(iconLogo.getImage().getScaledInstance(255, 255, Image.SCALE_DEFAULT));
            
                                jFondo_Preview.setText("");
                                jFondo_Preview.setIcon(tmpIcon);
                                jFondo_Preview.updateUI();
                            } catch (Exception e) {
                                Logger.getLogger(ModelEmpresas.class.getName()).log(Level.SEVERE, null, e);
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ModelEmpresas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModelEmpresas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(tabPestaña.getSelectedIndex()==1){
            try {
                if (rsSuc.getRow() > 0){
                    try {
                        txtCodsuc.setText(rsSuc.getString("SUC_CODIGO")); 
                        txtNomsuc.setText(rsSuc.getString("SUC_NOMBRE"));
                        txtIdsuc.setText(rsSuc.getString("SUC_ID").trim()); 
                        txtDirsuc.setText(rsSuc.getString("SUC_DIRECCION"));
//                        cbEmpresa.setSelectedItem((String)rsSuc.getString("SUC_EMPRESA"));
                        if(rsSuc.getInt("SUC_ACTIVO")==1){
                            chkSucursal.setSelected(true);
                        }else{
                            chkSucursal.setSelected(false);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ModelEmpresas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModelEmpresas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (tabPestaña.getSelectedIndex()==2){
            try {
                ResultSet rsConfEmp = controladorEmpresas.buscaConfEmpresa("SELECT", codigoEmpresa);
                
                if(rsConfEmp.getRow()>0){
                    if(rsConfEmp.getBoolean("calc_utilid_costo")){
                        chkUtilCosto.setSelected(true);
                    }else{
                        chkUtilCosto.setSelected(false);
                    }
                    
                    if(rsConfEmp.getBoolean("codigo_consecutivo")){
                        chkCodConsecutivo.setSelected(true);
                    }else{
                        chkCodConsecutivo.setSelected(false);
                    }
                    
                    if(rsConfEmp.getBoolean("codigo_consecutivo_grupo_subgrupo")){
                        chkCodConsecutivoGrupSubGrup.setSelected(true);
                    }else{
                        chkCodConsecutivoGrupSubGrup.setSelected(false);
                    }
                    
                    if(rsConfEmp.getBoolean("visualizarUltimoRegForm")){
                        chkVisualizaUltimoRegForm.setSelected(true);
                    }else{
                        chkVisualizaUltimoRegForm.setSelected(false);
                    }
                    
                    tfLongConsec.setText(rsConfEmp.getString("long_consec_tallar"));
                    
                    tfNombPrecio1.setText(rsConfEmp.getString("nom_precio_1"));
                    tfNombPrecio2.setText(rsConfEmp.getString("nom_precio_2"));
                    tfNombPrecio3.setText(rsConfEmp.getString("nom_precio_3"));
                    tfNombPrecio4.setText(rsConfEmp.getString("nom_precio_4"));
                    tfNombPrecio5.setText(rsConfEmp.getString("nom_precio_5"));
                    
                    tfUtilidPrecio1.setText(""+VarGlobales.getValorBigDecimalFormat(rsConfEmp.getString("utilidad_prec_1")));
                    tfUtilidPrecio2.setText(""+VarGlobales.getValorBigDecimalFormat(rsConfEmp.getString("utilidad_prec_2")));
                    tfUtilidPrecio3.setText(""+VarGlobales.getValorBigDecimalFormat(rsConfEmp.getString("utilidad_prec_3")));
                    tfUtilidPrecio4.setText(""+VarGlobales.getValorBigDecimalFormat(rsConfEmp.getString("utilidad_prec_4")));
                    tfUtilidPrecio5.setText(""+VarGlobales.getValorBigDecimalFormat(rsConfEmp.getString("utilidad_prec_5")));
                    
                    if(rsConfEmp.getBoolean("usa_facturaelectronica")){
                        chkUsaFactElectronica.setSelected(true);
                    }else{
                        chkUsaFactElectronica.setSelected(false);
                    }
                    
                    cbProveedServicio.setSelectedItem(rsConfEmp.getString("proveedor_servico"));
                    userComprobElectro.setText(rsConfEmp.getString("usuario"));
                    claveComprobElectro.setText(rsConfEmp.getString("clave"));
                    urlApiRest.setText(rsConfEmp.getString("urlApiRest"));
                    urlAccesToken.setText(rsConfEmp.getString("urlAccesToken"));
                    ubicArchCertificado.setText(rsConfEmp.getString("ubicacionarchivocertificado"));
                    claveArchCerticado.setText(rsConfEmp.getString("clavearchivocertificado"));
                    tfUltimoConseCbtElectron.setText(rsConfEmp.getString("consecutivoCbteElectrico"));
                    tfUltimoConseTiqueteElectronico.setText(rsConfEmp.getString("consecutivoNotaDebitoElectrico"));
                    tfUltimoConseNotaCreditoElectronica.setText(rsConfEmp.getString("consecutivoNotaCreditoElectrico"));
                    tfUltimoConseNotaDebitoElectronica.setText(rsConfEmp.getString("consecutivoNotaDebitoElectrico"));
                    tfUltimoConseReceptor.setText(rsConfEmp.getString("consecutivoReceptor"));
                }else{
                    chkUtilCosto.setSelected(false); chkCodConsecutivo.setSelected(false);
                    chkUsaFactElectronica.setSelected(false); chkCodConsecutivoGrupSubGrup.setSelected(false);
                    chkVisualizaUltimoRegForm.setSelected(false);
                    
                    tfLongConsec.setText("2");
                    
                    tfNombPrecio1.setText("Precio A");
                    tfNombPrecio2.setText("Precio B");
                    tfNombPrecio3.setText("");
                    tfNombPrecio4.setText("");
                    tfNombPrecio5.setText("");
                    
                    tfUtilidPrecio1.setText(""+VarGlobales.getValorBigDecimalFormat("0,00"));
                    tfUtilidPrecio2.setText(""+VarGlobales.getValorBigDecimalFormat("0,00"));
                    tfUtilidPrecio3.setText(""+VarGlobales.getValorBigDecimalFormat("0,00"));
                    tfUtilidPrecio4.setText(""+VarGlobales.getValorBigDecimalFormat("0,00"));
                    tfUtilidPrecio5.setText(""+VarGlobales.getValorBigDecimalFormat("0,00"));
                    
                    cbProveedServicio.setSelectedIndex(0);
                    userComprobElectro.setText("");
                    claveComprobElectro.setText("");
                    urlApiRest.setText("");
                    urlAccesToken.setText("");
                    ubicArchCertificado.setText("");
                    claveArchCerticado.setText("");
                    tfUltimoConseCbtElectron.setText("");
                    tfUltimoConseTiqueteElectronico.setText("");
                    tfUltimoConseNotaCreditoElectronica.setText("");
                    tfUltimoConseNotaDebitoElectronica.setText("");
                    tfUltimoConseReceptor.setText("");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModelEmpresas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (tabPestaña.getSelectedIndex()==4){
            try {
                ResultSet rsConfEmp = controladorEmpresas.buscaConfEmpresa("SELECT", codigoEmpresa);
                
                if(rsConfEmp.getRow()>0){
                    tfCorreoOrigen.setText(rsConfEmp.getString("correo_origen"));
                    tfPassMail.setText(rsConfEmp.getString("clave_correo_origen"));
                    tfServidorSmtp.setText(rsConfEmp.getString("servidor_smtp"));
                    tfAsuntoCorreo.setText(rsConfEmp.getString("asunto_correo"));
                    tfServidorPuerto.setText(rsConfEmp.getString("puerto_servidor_smtp"));
                    cbServidorPuerto.setSelectedItem(rsConfEmp.getString("puerto_servidor_smtp"));
                    
                    try {
                        if (!rsConfEmp.getString("cuerpo_correo").isEmpty() || rsConfEmp.getString("cuerpo_correo") != null){
                            taCuerpoCorreo.setText(rsConfEmp.getString("cuerpo_correo"));
                        }
                    } catch (Exception e) {
                        Logger.getLogger(ModelEmpresas.class.getName()).log(Level.SEVERE, null, e);
                    }
                }else{
                    tfCorreoOrigen.setText("");
                    tfPassMail.setText("");
                    tfServidorSmtp.setText("");
                    tfAsuntoCorreo.setText("");
                    tfServidorPuerto.setText("");
                    cbServidorPuerto.setSelectedIndex(0);
                    //taCuerpoCorreo.setText(rsConfEmp.getString(""));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModelEmpresas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String getCodPais(String nombPais){
        return controladorEmpresas.getCodPais(nombPais);
    }
    
    public String getCodEstado(String nombPais){
        return controladorEmpresas.getCodPais(nombPais);
    }
    
    public void cargaTabla(Boolean lSuc){
        String origen = "";
        if(tabPestaña.getSelectedIndex()==0){
            origen="empresa";
            controladorEmpresas.cargaTablas(tbTabla, origen, null, "");
        }else if(tabPestaña.getSelectedIndex()==1){
            origen="sucursal";
            controladorEmpresas.cargaTablas(tbSucursal, origen, lSuc,(String)cbEmpresa.getSelectedItem());
        }
    }
    
    public void buscaSuc(){
        cargaTabla(true);
        if (tbSucursal.getRowCount()==0){
            habilitar("Inicializa");
            getCodConsecutivo();
            posicion_botones_2();
                
            txtIdsuc.requestFocus();
        }else{
            posicion_botones_1();
            deshabilitar();
            btnGuardar.setVisible(false); btnCancelar.setVisible(false);
            ejecutaHilo("", false);
            
            tbSucursal.getSelectionModel().setSelectionInterval(tbSucursal.getRowCount()-1, tbSucursal.getRowCount()-1);
        }
    }
    
    public void action_bt_agregar(){
        if(tabPestaña.getSelectedIndex()==0){
            lAgregar = true;
        }else if(tabPestaña.getSelectedIndex()==1){
            lAgregarSuc = true;
        }
    }
    
    public void action_bt_modificar(){
        if(tabPestaña.getSelectedIndex()==0){
            lAgregar = false;
        }else if(tabPestaña.getSelectedIndex()==1){
            lAgregarSuc = false;
        }
    }
    
    public void action_bt_grabar(){
        String codigo="", nombre="", rif="", telefono="", correo="",direccion="", activo="",origen="",empresa="",
               moneda="", pais="", fax="", pagWeb="", utilSobreCosto="0", consecutivo="0", factElectro="0",
               tipoIdent="", estado="", municip="", parroq="", sector="", codConsecutivoGrupSubGrup="",
               visualizaUltimoRegForm="", empPredeterminada="";
        int act_suc = 0;
        
        if(tabPestaña.getSelectedIndex()==2){
            origen = "confEmpresa";
            
            if(chkUtilCosto.isSelected()){
                utilSobreCosto = "1";
            }
            
            if(chkCodConsecutivo.isSelected()){
                consecutivo = "1";
            }
            
            if(chkUsaFactElectronica.isSelected()){
                factElectro = "1";
            }
            
            if(chkCodConsecutivoGrupSubGrup.isSelected()){
                codConsecutivoGrupSubGrup = "1";
            }
            
            if(chkVisualizaUltimoRegForm.isSelected()){
                visualizaUltimoRegForm = "1";
            }
            
            controladorEmpresas.saveConfigEmpresa(origen, "REPLACE", codigoEmpresa, tfNombPrecio1.getText(), tfNombPrecio2.getText(), tfNombPrecio3.getText(), 
                                                  tfNombPrecio4.getText(), tfNombPrecio5.getText(), tfUtilidPrecio1.getText(), tfUtilidPrecio2.getText(), 
                                                  tfUtilidPrecio3.getText(), tfUtilidPrecio4.getText(), tfUtilidPrecio5.getText(), utilSobreCosto, 
                                                  consecutivo, tfLongConsec.getText(), factElectro, cbProveedServicio.getSelectedItem().toString(),
                                                  userComprobElectro.getText(), claveComprobElectro.getText(), urlApiRest.getText(), 
                                                  ubicArchCertificado.getText(), claveArchCerticado.getText(), tfUltimoConseCbtElectron.getText(),
                                                  codConsecutivoGrupSubGrup, visualizaUltimoRegForm, tfUltimoConseTiqueteElectronico.getText(),
                                                  tfUltimoConseNotaCreditoElectronica.getText(), tfUltimoConseNotaDebitoElectronica.getText(),
                                                  tfCorreoOrigen.getText(), tfPassMail.getText(), tfServidorSmtp.getText(), 
                                                  cbServidorPuerto.getSelectedItem().toString(), tfAsuntoCorreo.getText(), taCuerpoCorreo.getText(),
                                                  urlAccesToken.getText(), tfUltimoConseReceptor.getText());
            
            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("msgConfEmpresaSave"), 
                                                idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                                JOptionPane.WARNING_MESSAGE);
        }else if(tabPestaña.getSelectedIndex()==4){
            origen = "confEmpresa";
            
            if (tfCorreoOrigen.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "No ha ingresado el Correo de Origen", "Notificacion", JOptionPane.WARNING_MESSAGE);
                tfCorreoOrigen.requestFocus();

                return;
            }

            if (tfPassMail.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "No ha ingresado la contraseña el Correo de Origen", "Notificacion", JOptionPane.WARNING_MESSAGE);
                tfPassMail.requestFocus();

                return;
            }

            if (tfServidorSmtp.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "No ha ingresado el Servidor Smtp del Correo", "Notificacion", JOptionPane.WARNING_MESSAGE);
                tfServidorSmtp.requestFocus();

                return;
            }

            //if (tfServidorPuerto.getText().isEmpty()){
            if (cbServidorPuerto.getSelectedItem().toString().isEmpty()){
                JOptionPane.showMessageDialog(null, "No ha ingresado el puerto del Servidor Smtp del Correo", "Notificacion", JOptionPane.WARNING_MESSAGE);
                cbServidorPuerto.requestFocus();

                return;
            }
            
            if(chkUtilCosto.isSelected()){
                utilSobreCosto = "1";
            }
            
            if(chkCodConsecutivo.isSelected()){
                consecutivo = "1";
            }
            
            if(chkUsaFactElectronica.isSelected()){
                factElectro = "1";
            }
            
            if(chkCodConsecutivoGrupSubGrup.isSelected()){
                codConsecutivoGrupSubGrup = "1";
            }
            
            if(chkVisualizaUltimoRegForm.isSelected()){
                visualizaUltimoRegForm = "1";
            }
            
            controladorEmpresas.saveConfigEmpresa(origen, "REPLACE", codigoEmpresa, tfNombPrecio1.getText(), tfNombPrecio2.getText(), tfNombPrecio3.getText(), 
                                                  tfNombPrecio4.getText(), tfNombPrecio5.getText(), tfUtilidPrecio1.getText(), tfUtilidPrecio2.getText(), 
                                                  tfUtilidPrecio3.getText(), tfUtilidPrecio4.getText(), tfUtilidPrecio5.getText(), utilSobreCosto, 
                                                  consecutivo, tfLongConsec.getText(), factElectro, cbProveedServicio.getSelectedItem().toString(),
                                                  userComprobElectro.getText(), claveComprobElectro.getText(), urlApiRest.getText(), 
                                                  ubicArchCertificado.getText(), claveArchCerticado.getText(), tfUltimoConseCbtElectron.getText(),
                                                  codConsecutivoGrupSubGrup, visualizaUltimoRegForm, tfUltimoConseTiqueteElectronico.getText(),
                                                  tfUltimoConseNotaCreditoElectronica.getText(), tfUltimoConseNotaDebitoElectronica.getText(),
                                                  tfCorreoOrigen.getText(), tfPassMail.getText(), tfServidorSmtp.getText(), 
                                                  cbServidorPuerto.getSelectedItem().toString(), tfAsuntoCorreo.getText(), taCuerpoCorreo.getText(),
                                                  urlAccesToken.getText(), tfUltimoConseReceptor.getText());
            
            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("msgConfEmpresaSave"), 
                                                idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                                JOptionPane.WARNING_MESSAGE);
        }else if(tabPestaña.getSelectedIndex()==0){
            origen    = "empresa";
            codigo    = txtCodigo.getText();
            nombre    = txtNombre.getText();
            rif       = txtRif.getText();
            direccion = txtDireccion.getText();
            telefono  = txtTelefono.getText();
            correo    = txtCorreo.getText();
            moneda    = (String) cmbMoneda.getSelectedItem();
            int Item  = (int) cmbPais.getSelectedItem();
            pais      = (String) elementos.get(Item);
            fax       = txtFax.getText();
            pagWeb    = txtPagWeb.getText();

            tipoIdent = (String) cbTipIdentificador.getSelectedItem(); 
            if(tipoIdent==null){
                tipoIdent = "";
            }

            estado    = (String) cbEstado1.getSelectedItem();
                //estado    = estado.substring(estado.indexOf("-")+1,estado.length()).trim();
            municip   = (String) cbMunicipio1.getSelectedItem(); 
                //municip   = municip.substring(municip.indexOf("-")+1,municip.length()).trim();
            parroq    = (String) cbParroquia1.getSelectedItem(); 
                //parroq    = parroq.substring(parroq.indexOf("-")+1,parroq.length()).trim();
            try {
                if (!cbSector1.getSelectedItem().toString().trim().isEmpty()){
                    sector    = (String) cbSector1.getSelectedItem();
                        //sector    = sector.substring(sector.indexOf("-")+1,sector.length()).trim();
                }
            } catch (Exception e) {
                Logger.getLogger(ModelEmpresas.class.getName()).log(Level.SEVERE, null, e);
            }

            if (chkActivo.isSelected()==true){
                activo = "1";
            }else{
                activo = "0";
            }
            if (chkEmpPredeterminada.isSelected()==true){
                empPredeterminada = "1";
            }else{
                empPredeterminada = "0";
            }
            if (codigo.equals("")){
                JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgAddCode"), 
                                              idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                              JOptionPane.WARNING_MESSAGE);
                txtCodigo.requestFocus();
                return;
            }
            if (nombre.equals("")){
                JOptionPane.showMessageDialog(null,idioma.loadLangMsg().getString("MsgAddDescrip"), 
                                              idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                              JOptionPane.WARNING_MESSAGE);
                txtNombre.requestFocus();
                return;
            }
            if(moneda.equals("")){
                JOptionPane.showMessageDialog(null,idioma.loadLangMsg().getString("MsgMonedaEmp"), 
                                              idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                              JOptionPane.WARNING_MESSAGE);
                txtNombre.requestFocus();
                return;
            }
            if(tipoIdent.equals("")){
                JOptionPane.showMessageDialog(null,idioma.loadLangMsg().getString("MsgTipoIdentificador"), 
                                              idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                              JOptionPane.WARNING_MESSAGE);
                cbTipIdentificador.requestFocus();
                return;
            }
            if(estado.trim().equals("")){
                JOptionPane.showMessageDialog(null,idioma.loadLangMsg().getString("MsgEstadoEmpresa"), 
                                              idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                              JOptionPane.WARNING_MESSAGE);
                cbEmpresa.requestFocus();
                return;
            }
            if(municip.trim().equals("")){
                JOptionPane.showMessageDialog(null,idioma.loadLangMsg().getString("MsgMunicipioEmpresa"), 
                                              idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                              JOptionPane.WARNING_MESSAGE);
                cbMunicipio.requestFocus();
                return;
            }
            if(parroq.trim().equals("")){
                JOptionPane.showMessageDialog(null,idioma.loadLangMsg().getString("MsgParroquiaEmpresa"), 
                                              idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                              JOptionPane.WARNING_MESSAGE);
                cbParroquia.requestFocus();
                return;
            }

            if(lAgregar == true){
                int existe = 0;
                existe = controladorEmpresas.validaCodigo(rif);

                if(existe > 0){
                    txtCodigo.setText("");
                    txtCodigo.requestFocus();

                    JOptionPane.showMessageDialog(null,idioma.loadLangMsg().getString("MsgRifExist"), 
                                              idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                              JOptionPane.WARNING_MESSAGE);

                    return;
                }
            }
            
            controladorEmpresas.save(codigo, nombre, rif, direccion, activo, tbTabla, lAgregar, origen, empresa,
                                     tbSucursal, act_suc, telefono, correo, moneda, pais, fax, pagWeb, tipoIdent, 
                                     estado, municip, parroq, sector, empPredeterminada, getRutaFoto(), getNombreFoto());
        }else if(tabPestaña.getSelectedIndex()==1){
            origen = "sucursal";
            empresa = (String)cbEmpresa.getSelectedItem();
            if(empresa.equals("")){
                return;
            }

            codigo = txtCodsuc.getText();
            rif = txtIdsuc.getText();
            nombre = txtNomsuc.getText();
            direccion = txtDirsuc.getText();

            if(nombre.equals("")){
                JOptionPane.showMessageDialog(null,idioma.loadLangMsg().getString("MsgAddDescrip"));
                txtNomsuc.requestFocus();
                return; 
            }
            if(chkSucursal.isSelected()){
                act_suc = 1;
            }else{
                act_suc = 0;
            }
            
            controladorEmpresas.save(codigo, nombre, rif, direccion, activo, tbTabla, lAgregarSuc, origen, empresa,
                                     tbSucursal, act_suc, telefono, correo, moneda, pais, fax, pagWeb, tipoIdent, 
                                     estado, municip, parroq, sector, empPredeterminada, getRutaFoto(), getNombreFoto());
        }
        
        posicion_botones_1();
        deshabilitar();
    }
    
    public void action_bt_eliminar(){
        String codigo = "", origen = "", empresa = "";
        int salir = 1;
        
        if(tabPestaña.getSelectedIndex()==0){
            codigo = txtCodigo.getText().trim();
            origen = "empresa";
            
            if(codigo.trim().isEmpty()){
                codigo = (String) tbTabla.getValueAt(tbTabla.getSelectedRow(),0).toString().trim();
            }        
            salir = JOptionPane.showConfirmDialog(null,idioma.loadLangMsg().getString("MsgDelEmp")+codigo+"?");
        }else if(tabPestaña.getSelectedIndex()==1){
            codigo = txtCodsuc.getText().trim();
            origen = "sucursal";
            empresa = (String)cbEmpresa.getSelectedItem();
            if(empresa.equals("")){
                return;
            }
            if(codigo.trim().isEmpty()){
                codigo = (String) tbSucursal.getValueAt(tbTabla.getSelectedRow(),1).toString().trim();
            }        
            salir = JOptionPane.showConfirmDialog(null,idioma.loadLangMsg().getString("MsgDelEmp")+codigo+"?");
        }
        if(salir == 0){
            controladorEmpresas.delete(codigo, tbTabla, origen, empresa);
        }
    }
    
    public void action_bt_cancelar(){
        int eleccion = 0;
        String origen = "", empresa="";
        
//        if (lAgregar==true){
//            eleccion = JOptionPane.showConfirmDialog(null,idioma.loadLangMsg().getString("MsgCancelAdd"));
//        }else{
//            eleccion = JOptionPane.showConfirmDialog(null,idioma.loadLangMsg().getString("MsgCancelEdit"));
//        }
//        if(eleccion==0){
            setlAgregar(false);
            cargaTabla(false);
            
            if(tabPestaña.getSelectedIndex()==0){
                if(tbTabla.getRowCount()==0){
                    empresas = (Empresas) getVista();
                    empresas.dispose();
                    setVista(null);
                }else{
                    ejecutaHilo("", false);
                    posicion_botones_1();
                    deshabilitar();
                    
                    validaTablas(tabPestaña.getSelectedIndex(), islAgregar());
                }
            }else if(tabPestaña.getSelectedIndex()==1){
                if(tbSucursal.getRowCount()==0){
                    empresas = (Empresas) getVista();
                    empresas.dispose();
                    setVista(null);
                }else{
                    ejecutaHilo("", false);
                    posicion_botones_1();
                    deshabilitar();
                    
                    validaTablas(tabPestaña.getSelectedIndex(), islAgregar());
                }
            }else if(tabPestaña.getSelectedIndex()==2){
                empresas = (Empresas) getVista();
                empresas.dispose();
                setVista(null);
            }else if(tabPestaña.getSelectedIndex()==4){
                empresas = (Empresas) getVista();
                empresas.dispose();
                setVista(null);
            }
//        }
    }
    
    public void actionBtnBuscar(){
//        VarGlobales.setlBaseDatosConfiguracion(true);
        
        ListaDeMaestros buscarArticulos = new ListaDeMaestros("dnempresas","", "ERP");
        buscarArticulos.setModal(true);

        Dimension desktopSize = escritorioERP.getSize();
        Dimension jInternalFrameSize = buscarArticulos.getSize();
        buscarArticulos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

//        escritorioERP.add(buscarArticulos);
        buscarArticulos.toFront();
        buscarArticulos.setVisible(true);
        buscarArticulos.requestFocus();
        
        VarGlobales.setlBaseDatosConfiguracion(false);
    }
    
    public void buscaCodigo(String codigo){
        int row = 0;
        for(int i=0;i<getTbTabla().getRowCount();i++){
            if(codigo.equals(getTbTabla().getValueAt(i,0))){
                row = i;
                getTbTabla().getSelectionModel().setSelectionInterval(i, i);
            
                break;
            }
        }
        
        cargaDatos(row);
    }
    
    public void actionBtnBuscarCarPadre(){
        VarGlobales.setlBaseDatosConfiguracion(true);
        
        ListaDeMaestros buscarEmpresas = new ListaDeMaestros("DNEMPRESAS","", getOrigen());
        buscarEmpresas.setModal(true);

        Dimension desktopSize = desktop.getSize();
        Dimension jInternalFrameSize = buscarEmpresas.getSize();
        buscarEmpresas.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

//        desktop.add(buscarEmpresas);
        buscarEmpresas.toFront();
        buscarEmpresas.setVisible(true);
        
        VarGlobales.setlBaseDatosConfiguracion(false);
    }
    
    public void cargaCombo(){
        cbEmpresa.setModel(controladorEmpresas.cargaCombo());
        int items = cbEmpresa.getItemCount()-1;
        cbEmpresa.setSelectedIndex(items);
    }
    
    public void cargaMoneda(){
        DefaultComboBoxModel mdl = controladorEmpresas.cargaComboMoneda();
        
        cmbMoneda.setModel(mdl);
        cmbMoneda.setSelectedIndex(1);
    }

    /**
     * @return the tbTabla
     */
    public JTable getTbTabla() {
        return tbTabla;
    }

    /**
     * @param tbTabla the tbTabla to set
     */
    public void setTbTabla(JTable tbTabla) {
        this.tbTabla = tbTabla;
    }

    /**
     * @return the btnAgregar
     */
    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    /**
     * @param btnAgregar the btnAgregar to set
     */
    public void setBtnAgregar(JButton btnAgregar) {
        this.btnAgregar = btnAgregar;
    }

    /**
     * @return the btnModificar
     */
    public JButton getBtnModificar() {
        return btnModificar;
    }

    /**
     * @param btnModificar the btnModificar to set
     */
    public void setBtnModificar(JButton btnModificar) {
        this.btnModificar = btnModificar;
    }

    /**
     * @return the btnGuardar
     */
    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    /**
     * @param btnGuardar the btnGuardar to set
     */
    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    /**
     * @return the btnEliminar
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * @param btnEliminar the btnEliminar to set
     */
    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    /**
     * @return the btnCancelar
     */
    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    /**
     * @param btnCancelar the btnCancelar to set
     */
    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    /**
     * @return the btnBuscar
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * @param btnBuscar the btnBuscar to set
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    /**
     * @return the btnAtras
     */
//    public JButton getBtnAtras() {
//        return btnAtras;
//    }

    /**
     * @param btnAtras the btnAtras to set
     */
//    public void setBtnAtras(JButton btnAtras) {
//        this.btnAtras = btnAtras;
//    }

    /**
     * @return the btnSiguiente
     */
//    public JButton getBtnSiguiente() {
//        return btnSiguiente;
//    }

    /**
     * @param btnSiguiente the btnSiguiente to set
     */
//    public void setBtnSiguiente(JButton btnSiguiente) {
//        this.btnSiguiente = btnSiguiente;
//    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JFormattedTextField getTxtRif() {
        return txtRif;
    }

    public JTextArea getTxtDireccion() {
        return txtDireccion;
    }   

    public JCheckBox getChkActivo() {
        return chkActivo;
    }

    public JComboBox getCmbMoneda() {
        return cmbMoneda;
    }

    public void buscaEmpPredeterminada() {
        try {
            int resp = 0;
            creaConexion();
            String sql = "{call sp_formEmpresas(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            
            cs = conn.prepareCall(sql);

            cs.setString(1, "COUNT EMP_PREDETERMINADA");
            cs.setString(2, "");
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

            ResultSet rsEmpPredeterminada = consultarStoreProcedure(cs);
            
            if (rsEmpPredeterminada.getInt("REGISTROS")>0){
                creaConexion();
                sql = "{call sp_formEmpresas(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                
                cs = conn.prepareCall(sql);

                cs.setString(1, "GET_EMP_PREDETERMINADA");
                cs.setString(2, "");
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

                String empPredeterminada = "0";
                if (chkEmpPredeterminada.isSelected()){
                    empPredeterminada = "1";
                }
                cs.setString(20, empPredeterminada);

                ResultSet rsGetEmpPredeterminada = consultarStoreProcedure(cs);
                        
                if (!txtCodigo.getText().equals(rsGetEmpPredeterminada.getString("emp_codigo"))){
                    resp = JOptionPane.showConfirmDialog(null, "Ya existe una empresa Predeterminada. \n\n ¿Desea colocar esta empresa ahora como Predetermianda?",
                                                         "Consulta", JOptionPane.YES_NO_OPTION);

                    if (resp==0){
                        creaConexion();
                        sql = "{call sp_formEmpresas(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                        
                        cs = conn.prepareCall(sql);

                        cs.setString(1, "UPDATE PREDETERMINADA");
                        cs.setString(2, txtCodigo.getText());
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

                        empPredeterminada = "0";
                        if (chkEmpPredeterminada.isSelected()){
                            empPredeterminada = "1";
                        }
                        cs.setString(20, empPredeterminada);

                        insertDeleteUpdate_StoreProcedure(cs);
                    }else{
                        chkEmpPredeterminada.setSelected(false);
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ModelEmpresas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean validaLicencia(){
        boolean numEmpresas = false;
        
        try {
            String sqlNumEmpresas = "SELECT COUNT(*) AS numEmpresas FROM adminconfigestableerp.dnempresas;";
            ResultSet rsNumEmpresas = consultar(sqlNumEmpresas);
            
            if (VarGlobales.getNumEmpresas().equals("Si")){
                numEmpresas = true;
            }else{
                if (Integer.valueOf(VarGlobales.getNumEmpresas())>rsNumEmpresas.getInt("numEmpresas")){
                    numEmpresas = true;
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ModelEmpresas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return numEmpresas;
    }
}