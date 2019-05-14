package Vista;

import Controlador.ControladorEmpresas;
import Listener.ListenerBtnEmpresas;
import Modelos.JchomboBox;
import Modelos.ModelEmpresas;
import Modelos.VariablesGlobales;
import Modelos.conexion;
import Modelos.jComboRenderer;
import static Vista.MenuPrincipal.escritorioERP;
import static util.ColorApp.colorForm;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.json.JSONException;
import org.json.JSONObject;
import util.CargaComboBox;
import util.CargaTablas;
import util.FullSelectorListener;
import util.Internacionalizacion;
import util.LimitarCaracteres;
import util.SQLSelect;

public class Empresas extends javax.swing.JInternalFrame {
    public int fila, atras=-2, adelante=0;
    private SQLSelect Registros;
    public static boolean Bandera = false;
    private boolean lAgregar, lModificar;
    private ResultSet rs, rs_count, rs_codigo;
    private int Reg_count;
    private String codigo="", origen, pais="", idTemp = "", claveCorreo = "", claveCorreoOculto = "";
    private Vector Msg, header_table;
    public static Vector elementos = new Vector();
    
    private JDesktopPane desktop;
    private JchomboBox jComboIdioma;
    
    private HttpResponse response;
    private HttpEntity entity;
    private JSONObject result;

    CargaTablas cargatabla = null;
    conexion conet = new conexion();
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final ModelEmpresas modelEmpresas = ModelEmpresas.getModelEmpresas();
    private final ControladorEmpresas controladorEmpresas = new ControladorEmpresas();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    
    public Empresas(String origen) {
        initComponents();
        
        jComboIdioma = new JchomboBox(VarGlobales.getBanderas().length);
        jComboRenderer render = new jComboRenderer(VarGlobales.getBanderas(), elementos);
        jComboIdioma.setRenderer(render);
        
        pnConfFactElectronica.setVisible(false);
        
        inicializaClase();
        
        this.origen = origen;
        
        if(origen.equals("ERP")){
            desktop = escritorioERP;
        }else{
//            desktop = escritorio;
        }
        modelEmpresas.setOrigen(origen);
        modelEmpresas.setDesktop(desktop);
                
        jPanel1.setBackground(Color.decode(colorForm)); jPanel4.setBackground(Color.decode(colorForm));
        
//        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
//        jLabel3.setForeground(Color.decode(colorText)); jLabel4.setForeground(Color.decode(colorText));
//        jLabel6.setForeground(Color.decode(colorText)); jLabel7.setForeground(Color.decode(colorText));
//        jLabel8.setForeground(Color.decode(colorText)); jLabel9.setForeground(Color.decode(colorText));
//        jLabel10.setForeground(Color.decode(colorText)); 
//        
//        jChkbox_suc.setForeground(Color.decode(colorText));
        
//        bt_agregar.setForeground(Color.decode(colorText));
//        bt_Modificar.setForeground(Color.decode(colorText));
//        bt_save.setForeground(Color.decode(colorText));
//        bt_Eliminar.setForeground(Color.decode(colorText));
//        bt_cancel.setForeground(Color.decode(colorText));
//        bt_salir.setForeground(Color.decode(colorText));
//        bt_Atras.setForeground(Color.decode(colorText));
//        bt_Siguiente.setForeground(Color.decode(colorText));
//        bt_find.setForeground(Color.decode(colorText));
        
        this.setTitle("Empresas");
        jLabel1.setText(idioma.loadLangComponent().getString("lbCodigo"));
        jLabel2.setText(idioma.loadLangComponent().getString("lbNombre"));
        jLabel3.setText(idioma.loadLangComponent().getString("lbDireccion"));
        jLabel4.setText(idioma.loadLangComponent().getString("lbEmpRif"));
        jLabel5.setText(idioma.loadLangComponent().getString("lbTituloEmpSuc"));
        jLabel6.setText(idioma.loadLangComponent().getString("lbEmpresa"));
        jLabel7.setText(idioma.loadLangComponent().getString("lbCodigo"));
        jLabel8.setText(idioma.loadLangComponent().getString("lbId"));
        jLabel9.setText(idioma.loadLangComponent().getString("lbNombre"));
        jLabel10.setText(idioma.loadLangComponent().getString("lbDireccion"));
        
        jTpanel_emp.setTitleAt(0, idioma.loadLangComponent().getString("lbTabEmpresas"));
        jTpanel_emp.setTitleAt(1, idioma.loadLangComponent().getString("lbTabSucursales"));
        
        lbEstados.setText(idioma.loadLangComponent().getString("lbEstados"));
        lbMunucipios.setText(idioma.loadLangComponent().getString("lbMunicipio"));
        lbParroquias.setText(idioma.loadLangComponent().getString("lbParroquia"));
        lbSector.setText(idioma.loadLangComponent().getString("lbSector"));
        
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(idioma.loadLangComponent().getString("lbTabSucursales")));
        
        elementos.add(idioma.loadLangIdiomasSist().getString("es_US"));
        elementos.add(idioma.loadLangIdiomasSist().getString("es_VE"));
        elementos.add(idioma.loadLangIdiomasSist().getString("es_CR"));
        
        modelEmpresas.cargaTabla(false);
        modelEmpresas.validaTablas(0, true);
        
        jCheckEmp_Activo.setText(idioma.loadLangComponent().getString("chkActivo"));
        jChkbox_suc.setText(idioma.loadLangComponent().getString("chkActivo"));
        
        bt_agregar.setText(idioma.loadLangComponent().getString("btnAgregar")); bt_Modificar.setText(idioma.loadLangComponent().getString("btnModificar"));
        bt_Eliminar.setText(idioma.loadLangComponent().getString("btnEliminar")); bt_salir.setText(idioma.loadLangComponent().getString("btnSalir")); 
//        bt_Siguiente.setText(idioma.loadLangComponent().getString("btnSiguiente")); bt_Atras.setText(idioma.loadLangComponent().getString("btnAnterior")); 
        bt_save.setText(idioma.loadLangComponent().getString("btnGrabar")); bt_cancel.setText(idioma.loadLangComponent().getString("btnCancelar"));
        bt_find.setText(idioma.loadLangComponent().getString("btnBuscar")); 
        
        bt_agregar.setActionCommand("Agregar"); bt_Modificar.setActionCommand("Modificar");
        bt_save.setActionCommand("Grabar"); bt_cancel.setActionCommand("Cancelar");
        bt_Eliminar.setActionCommand("Eliminar"); bt_find.setActionCommand("Buscar");
//        bt_Atras.setActionCommand("Anterior"); bt_Siguiente.setActionCommand("Adelante"); 
        bt_salir.setActionCommand("Salir");
        
    //Limitar();
        bt_agregar.addActionListener(new ListenerBtnEmpresas());
        bt_Modificar.addActionListener(new ListenerBtnEmpresas());
        bt_save.addActionListener(new ListenerBtnEmpresas());
        bt_Eliminar.addActionListener(new ListenerBtnEmpresas());
        bt_cancel.addActionListener(new ListenerBtnEmpresas());
        bt_salir.addActionListener(new ListenerBtnEmpresas());
//        bt_Atras.addActionListener(new ListenerBtnEmpresas());
//        bt_Siguiente.addActionListener(new ListenerBtnEmpresas());
        bt_find.addActionListener(new ListenerBtnEmpresas());
        
        int Item = (int) jComboIdioma.getSelectedItem();        
        pais = (String) elementos.get(Item);
        
        if((pais.equals("Costa Rica (es_CR)"))){
            cargaCbmTipoIdentificador();
        }
        
        cargaCmbEstados(modelEmpresas.getCodPais(pais.substring(0,pais.indexOf("(")-1).trim()));
        
        reagrupaElementos();
        
        jComboIdioma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idTemp.equals("") || idTemp.equals(" -        - ")){
                    idTemp = jTxtEmp_Rif.getText();
                }
                jTxtEmp_Rif.setText("");
                int Item = (int) jComboIdioma.getSelectedItem();
                
                pais = (String) elementos.get(Item);
                
                if((pais.equals("Costa Rica (es_CR)"))){
                    jTxtEmp_Rif.setFormatterFactory(null);
                    jTxtEmp_Rif.setText(idTemp);
                    
                    cargaCbmTipoIdentificador();
                    //cargaCmbEstados(modelEmpresas.getCodPais(pais.substring(0,pais.indexOf("(")-1).trim()));
                    cargaCmbEstados(modelEmpresas.getCodPais(pais.substring(pais.length()-3,pais.length()-1).trim()));
                }
                
                if((pais.equals("English (en_US)"))){
                    //jTxtEmp_Rif.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("############"))));
                    jTxtEmp_Rif.setFormatterFactory(null);
                    jTxtEmp_Rif.setText(idTemp);
                }
                
                if(pais.equals("Español (es_VE)")){
                    try {
                        jTxtEmp_Rif.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("U-########-#")));
                        jTxtEmp_Rif.setText(idTemp);
                        
                        cargaCbmTipoIdentificador();
                        //cargaCmbEstados(modelEmpresas.getCodPais(pais.substring(0,pais.indexOf("(")-1).trim()));
                        cargaCmbEstados(modelEmpresas.getCodPais(pais.substring(pais.length()-3,pais.length()-1).trim()));
                    } catch (java.text.ParseException ex) {
                        ex.printStackTrace();
                    }
                }
                
                if(modelEmpresas.lAgregar){
                    jTxtEmp_Rif.setText("");
                    jTxtEmp_Rif.requestFocus();
                }
                
                CargarIdioma();
                reagrupaElementos();
                
                Item = (int) jComboIdioma.getSelectedItem();        
                pais = (String) elementos.get(Item);

                //cargaCmbEstados(modelEmpresas.getCodPais(pais.substring(0,pais.indexOf("(")-1).trim()));
                cargaCmbEstados(modelEmpresas.getCodPais(pais.substring(pais.length()-3,pais.length()-1).trim()));
            }
        });
        
        AutoCompleteDecorator.decorate(this.cbTipIdentificador);
        
//        jLabel4.reshape(10, 40, 100, 18); jPanel1.add(jLabel4);
//        
//        jTxtEmp_Rif.reshape(120, 40, 150, 18); jPanel1.add(jTxtEmp_Rif);
//        jTxtEmp_Nombre.reshape(120, 70, 250, 18); jPanel1.add(jTxtEmp_Nombre);
//        tfTelefono.reshape(120, 100, 250, 18); jPanel1.add(tfTelefono);
//        tfCorreo.reshape(120, 130, 250, 18); jPanel1.add(tfCorreo);
//        jTxtEmp_Direccion.reshape(120, 160, 250, 8); jPanel1.add(jTxtEmp_Direccion);

        tfNomPrecio1.addFocusListener(new FullSelectorListener());
        tfNomPrecio2.addFocusListener(new FullSelectorListener());
        tfNomPrecio3.addFocusListener(new FullSelectorListener());
        tfNomPrecio4.addFocusListener(new FullSelectorListener());
        tfNomPrecio5.addFocusListener(new FullSelectorListener());
        tfUtilPrecio1.addFocusListener(new FullSelectorListener());
        tfUtilPrecio2.addFocusListener(new FullSelectorListener());
        tfUtilPrecio3.addFocusListener(new FullSelectorListener());
        tfUtilPrecio4.addFocusListener(new FullSelectorListener());
        tfUtilPrecio5.addFocusListener(new FullSelectorListener());
        
        tfUltimoConseFacturaElectronica.setDocument(new LimitarCaracteres(tfUltimoConseFacturaElectronica, 10));
        
        AutoCompleteDecorator.decorate(this.cbEstado);
        AutoCompleteDecorator.decorate(this.cbMunicipio);
        AutoCompleteDecorator.decorate(this.cbParroquia);
        AutoCompleteDecorator.decorate(this.cbSector);
        
        cbEstado1.setVisible(false); cbMunicipio1.setVisible(false); cbParroquia1.setVisible(false); cbSector1.setVisible(false);
        
        modelEmpresas.validaTablas(0, modelEmpresas.islAgregar());
        
        tfServidorSmtp.setToolTipText("gmail: smtp.gmail.com \n hotmail: smtp.live.com \n Outlook: smtp-mail.outlook.com \n "+
                                      "Office365: smtp.office365.com \n yahoo: smtp.mail.yahoo.com");
        tfServidorPuerto.setToolTipText("gmail: 465 o 587 \n hotmail: 25 o 465 \n Outlook: 587 \n Office365: 587 \n yahoo: 465 o 587");
        tfServidorPuerto.setVisible(false);
        AutoCompleteDecorator.decorate(this.cbServidorPuerto);
    }
    
     /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jTpanel_emp = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTxtEmp_Codigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTxtEmp_Nombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTxtEmp_Direccion = new javax.swing.JTextArea();
        jCheckEmp_Activo = new javax.swing.JCheckBox();
        jTxtEmp_Rif = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jFondo_Preview = new javax.swing.JLabel();
        btn_buscarimg = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        tfTelefono = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tfCorreo = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cmbMoneda = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        tfFax = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        tfPagWeb = new javax.swing.JTextField();
        btnMoneda = new javax.swing.JButton();
        lbEstados = new javax.swing.JLabel();
        cbEstado = new org.jdesktop.swingx.JXComboBox();
        lbMunucipios = new javax.swing.JLabel();
        cbMunicipio = new org.jdesktop.swingx.JXComboBox();
        lbParroquias = new javax.swing.JLabel();
        cbParroquia = new org.jdesktop.swingx.JXComboBox();
        lbSector = new javax.swing.JLabel();
        cbSector = new org.jdesktop.swingx.JXComboBox();
        lbTipoIdentificador = new javax.swing.JLabel();
        cbTipIdentificador = new org.jdesktop.swingx.JXComboBox();
        cbEstado1 = new org.jdesktop.swingx.JXComboBox();
        cbMunicipio1 = new org.jdesktop.swingx.JXComboBox();
        cbParroquia1 = new org.jdesktop.swingx.JXComboBox();
        cbSector1 = new org.jdesktop.swingx.JXComboBox();
        chkEmpPredeterminada = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jCombo_empresa = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTxt_idsur = new javax.swing.JTextField();
        jTxt_codsur = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTxt_nomsur = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTxtA_dirsur = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jChkbox_suc = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        chkUtilCosto = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        tfNomPrecio1 = new javax.swing.JTextField();
        tfNomPrecio2 = new javax.swing.JTextField();
        tfNomPrecio3 = new javax.swing.JTextField();
        tfNomPrecio4 = new javax.swing.JTextField();
        tfNomPrecio5 = new javax.swing.JTextField();
        tfUtilPrecio1 = new javax.swing.JFormattedTextField();
        tfUtilPrecio2 = new javax.swing.JFormattedTextField();
        tfUtilPrecio3 = new javax.swing.JFormattedTextField();
        tfUtilPrecio4 = new javax.swing.JFormattedTextField();
        tfUtilPrecio5 = new javax.swing.JFormattedTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        chkCodConsecutivo = new javax.swing.JCheckBox();
        tfLongConsec = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        chkUsaFactElectronica = new javax.swing.JCheckBox();
        pnConfFactElectronica = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        userComprobElectro = new javax.swing.JTextField();
        claveComprobElectro = new javax.swing.JPasswordField();
        urlApiRest = new javax.swing.JTextField();
        ubicArchCertificado = new javax.swing.JTextField();
        claveArchCerticado = new javax.swing.JPasswordField();
        cbProveedServicio = new org.jdesktop.swingx.JXComboBox();
        btnUbicacionArchCertificado = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        urlAccesToken = new javax.swing.JTextField();
        btnTestToken = new javax.swing.JButton();
        chkCodConsecutivoGrupSubGrup = new javax.swing.JCheckBox();
        chkVisualizaUltimoRegForm = new javax.swing.JCheckBox();
        panelConsecutivosDocElectronicos = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        tfUltimoConseNotaDebitoElectronica = new javax.swing.JTextField();
        tfUltimoConseNotaCreditoElectronica = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        tfUltimoConseFacturaElectronica = new javax.swing.JTextField();
        tfUltimoConseTiqueteElectronico = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        tfUltimoConseReceptor = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_sucursal = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        tfCorreoOrigen = new app.bolivia.swing.JCTextField();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        tfServidorSmtp = new app.bolivia.swing.JCTextField();
        jLabel43 = new javax.swing.JLabel();
        tfAsuntoCorreo = new app.bolivia.swing.JCTextField();
        jLabel44 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        taCuerpoCorreo = new javax.swing.JTextArea();
        jLabel45 = new javax.swing.JLabel();
        tfServidorPuerto = new app.bolivia.swing.JCTextField();
        jButton1 = new javax.swing.JButton();
        tfPassMail = new javax.swing.JPasswordField();
        cbServidorPuerto = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        bt_salir = new com.l2fprod.common.swing.JLinkButton();
        bt_agregar = new com.l2fprod.common.swing.JLinkButton();
        bt_Modificar = new com.l2fprod.common.swing.JLinkButton();
        bt_save = new com.l2fprod.common.swing.JLinkButton();
        bt_Eliminar = new com.l2fprod.common.swing.JLinkButton();
        bt_cancel = new com.l2fprod.common.swing.JLinkButton();
        bt_find = new com.l2fprod.common.swing.JLinkButton();

        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jTpanel_emp.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTpanel_empStateChanged(evt);
            }
        });
        jTpanel_emp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTpanel_empMouseClicked(evt);
            }
        });

        jPanel1.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Código:");

        jTxtEmp_Codigo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTxtEmp_Codigo.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Nombre:");

        jTxtEmp_Nombre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTxtEmp_Nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtEmp_NombreKeyPressed(evt);
            }
        });

        jLabel4.setText("Identificador Fiscal:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Dirección:");

        jTxtEmp_Direccion.setColumns(20);
        jTxtEmp_Direccion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTxtEmp_Direccion.setRows(5);
        jTxtEmp_Direccion.setPreferredSize(new java.awt.Dimension(180, 79));
        jScrollPane1.setViewportView(jTxtEmp_Direccion);

        jCheckEmp_Activo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jCheckEmp_Activo.setText("Activo");
        jCheckEmp_Activo.setOpaque(false);

        try {
            jTxtEmp_Rif.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("U-########-#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jTxtEmp_Rif.setFont(new java.awt.Font("Roboto Light", 2, 12)); // NOI18N
        jTxtEmp_Rif.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtEmp_RifFocusLost(evt);
            }
        });
        jTxtEmp_Rif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtEmp_RifActionPerformed(evt);
            }
        });
        jTxtEmp_Rif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtEmp_RifKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTxtEmp_RifKeyTyped(evt);
            }
        });

        jLabel11.setText("Logo Empresa:");

        jFondo_Preview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jFondo_Preview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/select_imagen.png"))); // NOI18N
        jFondo_Preview.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jFondo_Preview.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btn_buscarimg.setBackground(new java.awt.Color(255, 255, 255));
        btn_buscarimg.setText("Buscar");
        btn_buscarimg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarimgActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Teléfonos:");

        tfTelefono.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tfTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfTelefonoActionPerformed(evt);
            }
        });
        tfTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfTelefonoKeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Correo:");

        tfCorreo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tfCorreo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfCorreoKeyPressed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Moneda:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Pais:");

        cmbMoneda.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbMoneda.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMonedaItemStateChanged(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Fax:");

        tfFax.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tfFax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfFaxActionPerformed(evt);
            }
        });
        tfFax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfFaxKeyPressed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Pagina Web:");

        tfPagWeb.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tfPagWeb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfPagWebKeyPressed(evt);
            }
        });

        btnMoneda.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        btnMoneda.setText("[...]");
        btnMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMonedaActionPerformed(evt);
            }
        });

        lbEstados.setText("Estado:");
        lbEstados.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        cbEstado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbEstadoItemStateChanged(evt);
            }
        });

        lbMunucipios.setText("Municipio:");
        lbMunucipios.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        cbMunicipio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbMunicipioItemStateChanged(evt);
            }
        });

        lbParroquias.setText("Parroquia:");
        lbParroquias.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        cbParroquia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbParroquiaItemStateChanged(evt);
            }
        });

        lbSector.setText("Sector:");
        lbSector.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        cbSector.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbSectorItemStateChanged(evt);
            }
        });

        lbTipoIdentificador.setText("Tipo Idenficación:");
        lbTipoIdentificador.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        cbTipIdentificador.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTipIdentificadorItemStateChanged(evt);
            }
        });

        cbEstado1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbEstado1ItemStateChanged(evt);
            }
        });

        cbMunicipio1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbMunicipio1ItemStateChanged(evt);
            }
        });

        cbParroquia1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbParroquia1ItemStateChanged(evt);
            }
        });

        chkEmpPredeterminada.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chkEmpPredeterminada.setText("Empresa Predeterminada");
        chkEmpPredeterminada.setOpaque(false);
        chkEmpPredeterminada.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkEmpPredeterminadaStateChanged(evt);
            }
        });
        chkEmpPredeterminada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkEmpPredeterminadaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(595, 595, 595))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbTipoIdentificador))
                                .addGap(17, 17, 17)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTxtEmp_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(chkEmpPredeterminada))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(cbTipIdentificador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGap(19, 19, 19)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jTxtEmp_Rif, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jTxtEmp_Nombre, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tfCorreo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                                        .addComponent(tfTelefono, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tfFax, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))))
                            .addComponent(jLabel1)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cmbMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnMoneda))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addGap(125, 125, 125)
                                    .addComponent(jCheckEmp_Activo))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(190, 190, 190)
                                    .addComponent(btn_buscarimg)))
                            .addComponent(jFondo_Preview, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tfPagWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbMunucipios)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbParroquias)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cbParroquia, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbSector)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbSector, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cbEstado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbMunicipio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbParroquia1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbSector1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckEmp_Activo)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFondo_Preview, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTxtEmp_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(chkEmpPredeterminada)))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtEmp_Rif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(cbTipIdentificador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbTipoIdentificador))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTxtEmp_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfFax, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfPagWeb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(71, 71, 71)
                                .addComponent(jLabel15))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnMoneda)
                                    .addComponent(btn_buscarimg))))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbEstados)
                                .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbMunucipios)
                                .addComponent(cbMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbParroquias))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbParroquia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbSector)
                                .addComponent(cbSector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbEstado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbMunicipio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbParroquia1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbSector1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTpanel_emp.addTab("Empresas", jPanel1);

        jPanel2.setOpaque(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Empresa");

        jCombo_empresa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jCombo_empresa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCombo_empresaItemStateChanged(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Sucursales"));
        jPanel3.setOpaque(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Codigo:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("ID:");

        jTxt_idsur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTxt_codsur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTxt_codsur.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Nombre:");

        jTxt_nomsur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jTxtA_dirsur.setColumns(20);
        jTxtA_dirsur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTxtA_dirsur.setRows(3);
        jScrollPane4.setViewportView(jTxtA_dirsur);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Direccion:");

        jChkbox_suc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jChkbox_suc.setSelected(true);
        jChkbox_suc.setText("Activo");
        jChkbox_suc.setOpaque(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTxt_nomsur)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTxt_idsur, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                            .addComponent(jTxt_codsur))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jChkbox_suc))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 797, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTxt_codsur, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                        .addComponent(jChkbox_suc)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxt_idsur, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTxt_nomsur, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(135, 135, 135))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jCombo_empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCombo_empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTpanel_emp.addTab("Sucursales", jPanel2);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        chkUtilCosto.setBackground(new java.awt.Color(255, 255, 255));
        chkUtilCosto.setText("Emplear utilidad sobre el Costo en el Producto");

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Denominación de Precios y Utilidad"));

        jLabel19.setText("# 1");

        jLabel20.setText("# 2");

        jLabel21.setText("# 3");

        jLabel22.setText("# 4");

        jLabel23.setText("# 5");

        tfNomPrecio1.setText("Precio A");
        tfNomPrecio1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfNomPrecio1KeyPressed(evt);
            }
        });

        tfNomPrecio2.setText("Precio B");
        tfNomPrecio2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfNomPrecio2KeyPressed(evt);
            }
        });

        tfNomPrecio3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfNomPrecio3KeyPressed(evt);
            }
        });

        tfNomPrecio4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfNomPrecio4KeyPressed(evt);
            }
        });

        tfNomPrecio5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfNomPrecio5KeyPressed(evt);
            }
        });

        tfUtilPrecio1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfUtilPrecio1.setText("0,00");
        tfUtilPrecio1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfUtilPrecio1FocusLost(evt);
            }
        });
        tfUtilPrecio1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfUtilPrecio1KeyPressed(evt);
            }
        });

        tfUtilPrecio2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfUtilPrecio2.setText("0,00");
        tfUtilPrecio2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfUtilPrecio2FocusLost(evt);
            }
        });
        tfUtilPrecio2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfUtilPrecio2KeyPressed(evt);
            }
        });

        tfUtilPrecio3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfUtilPrecio3.setText("0,00");
        tfUtilPrecio3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfUtilPrecio3FocusLost(evt);
            }
        });
        tfUtilPrecio3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfUtilPrecio3KeyPressed(evt);
            }
        });

        tfUtilPrecio4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfUtilPrecio4.setText("0,00");
        tfUtilPrecio4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfUtilPrecio4FocusLost(evt);
            }
        });
        tfUtilPrecio4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfUtilPrecio4ActionPerformed(evt);
            }
        });
        tfUtilPrecio4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfUtilPrecio4KeyPressed(evt);
            }
        });

        tfUtilPrecio5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfUtilPrecio5.setText("0,00");
        tfUtilPrecio5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfUtilPrecio5FocusLost(evt);
            }
        });

        jLabel24.setText("%");

        jLabel25.setText("%");

        jLabel26.setText("%");

        jLabel27.setText("%");

        jLabel28.setText("%");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNomPrecio1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNomPrecio2))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNomPrecio3))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNomPrecio4))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNomPrecio5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfUtilPrecio1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(tfUtilPrecio2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(tfUtilPrecio3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel26))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(tfUtilPrecio4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(tfUtilPrecio5, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel24)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(tfNomPrecio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfUtilPrecio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(tfNomPrecio2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfUtilPrecio2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(tfNomPrecio3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfUtilPrecio3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(tfNomPrecio4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfUtilPrecio4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel27)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(tfNomPrecio5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfUtilPrecio5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chkCodConsecutivo.setBackground(new java.awt.Color(255, 255, 255));
        chkCodConsecutivo.setText("Código Consecutivo en Modulo de Taller");

        tfLongConsec.setText("2");
        tfLongConsec.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfLongConsecFocusLost(evt);
            }
        });

        jLabel29.setText("Longitud del Código Consecutivo");

        chkUsaFactElectronica.setBackground(new java.awt.Color(255, 255, 255));
        chkUsaFactElectronica.setText("Usar Facturación Electrónica");
        chkUsaFactElectronica.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkUsaFactElectronicaItemStateChanged(evt);
            }
        });

        pnConfFactElectronica.setBackground(new java.awt.Color(255, 255, 255));
        pnConfFactElectronica.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración de Conexión de Facturacion Electronica"));

        jLabel30.setText("Proveedor de Servicio:");

        jLabel31.setText("Usuario:");

        jLabel32.setText("Clave:");

        jLabel33.setText("Dirección URL Recepción:");

        jLabel34.setText("Archivo de Certificado:");

        jLabel35.setText("Clave del Archivo de Certificado:");

        claveComprobElectro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                claveComprobElectroActionPerformed(evt);
            }
        });

        cbProveedServicio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Ministerio de Hacienda" }));

        btnUbicacionArchCertificado.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        btnUbicacionArchCertificado.setText("[...]");
        btnUbicacionArchCertificado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbicacionArchCertificadoActionPerformed(evt);
            }
        });

        jLabel46.setText("Dirección URL Access Token:");

        btnTestToken.setText("Test Credenciales");
        btnTestToken.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestTokenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnConfFactElectronicaLayout = new javax.swing.GroupLayout(pnConfFactElectronica);
        pnConfFactElectronica.setLayout(pnConfFactElectronicaLayout);
        pnConfFactElectronicaLayout.setHorizontalGroup(
            pnConfFactElectronicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnConfFactElectronicaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnConfFactElectronicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnConfFactElectronicaLayout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(51, 51, 51)
                        .addComponent(ubicArchCertificado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUbicacionArchCertificado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnConfFactElectronicaLayout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(claveArchCerticado, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTestToken, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnConfFactElectronicaLayout.createSequentialGroup()
                        .addGroup(pnConfFactElectronicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32)
                            .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnConfFactElectronicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(urlAccesToken, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnConfFactElectronicaLayout.createSequentialGroup()
                                .addGroup(pnConfFactElectronicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbProveedServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(claveComprobElectro, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(168, Short.MAX_VALUE))
                            .addComponent(urlApiRest)
                            .addComponent(userComprobElectro)))))
        );
        pnConfFactElectronicaLayout.setVerticalGroup(
            pnConfFactElectronicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnConfFactElectronicaLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pnConfFactElectronicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(cbProveedServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(pnConfFactElectronicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(userComprobElectro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(pnConfFactElectronicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(claveComprobElectro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(pnConfFactElectronicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(urlApiRest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(pnConfFactElectronicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(urlAccesToken, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addGap(5, 5, 5)
                .addGroup(pnConfFactElectronicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(ubicArchCertificado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUbicacionArchCertificado))
                .addGap(4, 4, 4)
                .addGroup(pnConfFactElectronicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel35)
                    .addGroup(pnConfFactElectronicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(claveArchCerticado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTestToken)))
                .addGap(3, 3, 3))
        );

        chkCodConsecutivoGrupSubGrup.setBackground(new java.awt.Color(255, 255, 255));
        chkCodConsecutivoGrupSubGrup.setText("Código Consecutivo en Grupo y Sub-Grupos");

        chkVisualizaUltimoRegForm.setBackground(new java.awt.Color(255, 255, 255));
        chkVisualizaUltimoRegForm.setText("Visualizar ultimo Registro en Formularios");
        chkVisualizaUltimoRegForm.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkVisualizaUltimoRegFormItemStateChanged(evt);
            }
        });

        panelConsecutivosDocElectronicos.setBackground(new java.awt.Color(255, 255, 255));
        panelConsecutivosDocElectronicos.setBorder(javax.swing.BorderFactory.createTitledBorder("Ultimo N° Consecutivo"));

        jLabel39.setText("Nota Débito Electrónico de Sitema Anterior");

        tfUltimoConseNotaDebitoElectronica.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfUltimoConseNotaDebitoElectronicaKeyTyped(evt);
            }
        });

        tfUltimoConseNotaCreditoElectronica.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfUltimoConseNotaCreditoElectronicaKeyTyped(evt);
            }
        });

        jLabel38.setText("Nota Crédito Electrónico de Sitema Anterior");

        jLabel37.setText("Tiquete Electrónico de Sitema Anterior");

        jLabel36.setText("Factura Electrónica de Sitema Anterior");

        tfUltimoConseFacturaElectronica.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfUltimoConseFacturaElectronicaKeyTyped(evt);
            }
        });

        tfUltimoConseTiqueteElectronico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfUltimoConseTiqueteElectronicoKeyTyped(evt);
            }
        });

        jLabel47.setText("Receptor de Sitema Anterior");

        tfUltimoConseReceptor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfUltimoConseReceptorKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout panelConsecutivosDocElectronicosLayout = new javax.swing.GroupLayout(panelConsecutivosDocElectronicos);
        panelConsecutivosDocElectronicos.setLayout(panelConsecutivosDocElectronicosLayout);
        panelConsecutivosDocElectronicosLayout.setHorizontalGroup(
            panelConsecutivosDocElectronicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsecutivosDocElectronicosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelConsecutivosDocElectronicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConsecutivosDocElectronicosLayout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfUltimoConseTiqueteElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConsecutivosDocElectronicosLayout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfUltimoConseNotaCreditoElectronica, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConsecutivosDocElectronicosLayout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfUltimoConseNotaDebitoElectronica, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConsecutivosDocElectronicosLayout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfUltimoConseReceptor, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(panelConsecutivosDocElectronicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelConsecutivosDocElectronicosLayout.createSequentialGroup()
                    .addGap(34, 34, 34)
                    .addComponent(jLabel36)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tfUltimoConseFacturaElectronica, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelConsecutivosDocElectronicosLayout.setVerticalGroup(
            panelConsecutivosDocElectronicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsecutivosDocElectronicosLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelConsecutivosDocElectronicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(tfUltimoConseTiqueteElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(panelConsecutivosDocElectronicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(tfUltimoConseNotaCreditoElectronica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(panelConsecutivosDocElectronicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(tfUltimoConseNotaDebitoElectronica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelConsecutivosDocElectronicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(tfUltimoConseReceptor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(panelConsecutivosDocElectronicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelConsecutivosDocElectronicosLayout.createSequentialGroup()
                    .addGap(1, 1, 1)
                    .addGroup(panelConsecutivosDocElectronicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel36)
                        .addComponent(tfUltimoConseFacturaElectronica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(101, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(chkUtilCosto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chkCodConsecutivo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chkCodConsecutivoGrupSubGrup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(chkVisualizaUltimoRegForm)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(chkUsaFactElectronica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(90, 90, 90)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnConfFactElectronica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(tfLongConsec, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelConsecutivosDocElectronicos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(chkUtilCosto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chkCodConsecutivo)
                            .addComponent(tfLongConsec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29))
                        .addGap(0, 0, 0)
                        .addComponent(chkCodConsecutivoGrupSubGrup)
                        .addGap(0, 0, 0)
                        .addComponent(chkUsaFactElectronica)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkVisualizaUltimoRegForm)
                        .addGap(0, 29, Short.MAX_VALUE))
                    .addComponent(panelConsecutivosDocElectronicos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnConfFactElectronica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTpanel_emp.addTab("Configuración", jPanel7);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaMouseClicked(evt);
            }
        });
        Tabla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TablaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TablaKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla);

        jTable_sucursal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Empresa", "Codigo", "ID", "Nombre"
            }
        ));
        jTable_sucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_sucursalMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable_sucursal);

        jLabel5.setText("Empresas:");

        jLabel12.setText("Sucursales:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 892, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel12))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTpanel_emp.addTab("Lista", jPanel6);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        tfCorreoOrigen.setPlaceholder("correo@gmail.com");
        tfCorreoOrigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCorreoOrigenActionPerformed(evt);
            }
        });
        tfCorreoOrigen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfCorreoOrigenKeyPressed(evt);
            }
        });

        jLabel40.setText("Correo Electronico Origen:");

        jLabel41.setText("Clave del Correo Electronico:");

        jLabel42.setText("Servidor de Correo Origen:");

        tfServidorSmtp.setToolTipText("gmail: smtp.gmail.com");
        tfServidorSmtp.setPlaceholder("smtp.gmail.com");
        tfServidorSmtp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfServidorSmtpActionPerformed(evt);
            }
        });
        tfServidorSmtp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfServidorSmtpKeyPressed(evt);
            }
        });

        jLabel43.setText("Asunto del Correo:");

        tfAsuntoCorreo.setPlaceholder("Asunto del Correo de Origen");
        tfAsuntoCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfAsuntoCorreoActionPerformed(evt);
            }
        });
        tfAsuntoCorreo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfAsuntoCorreoKeyPressed(evt);
            }
        });

        jLabel44.setText("Cuerpo del Correo:");

        taCuerpoCorreo.setColumns(20);
        taCuerpoCorreo.setRows(5);
        taCuerpoCorreo.setText("<html>\n   <br> \n      Estimado(a)  por este medio le brindamos los Documentos correspondientes de su \n      Factura Electrónica Nº\n   <br> \n   <br> \n      Documento Emitido a través de: Estable ERP, una aplicacion de Reset Consultes \n   <br> \n      Visitenos en: <a href=\\\"http://www.resetconsultores.com\\\" target=\\\"_blank\\\"> www.resetconsultores.com </a>\n   <br> \n      Que pase un Buen Día.\n   <br> \n   <br> \n      <h2>GRACIAS POR SU TIEMPO...!!!</h2>\n</html>");
        jScrollPane5.setViewportView(taCuerpoCorreo);

        jLabel45.setText("Puerto:");

        tfServidorPuerto.setPlaceholder("Puerto de Salida del correo");
        tfServidorPuerto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfServidorPuertoActionPerformed(evt);
            }
        });
        tfServidorPuerto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfServidorPuertoKeyPressed(evt);
            }
        });

        jButton1.setText("Test Correo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tfPassMail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfPassMailKeyPressed(evt);
            }
        });

        cbServidorPuerto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "25", "465", "587" }));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfCorreoOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfServidorSmtp, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(jLabel41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfPassMail, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(396, 396, 396)
                                .addComponent(jLabel43))
                            .addComponent(jLabel44))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(tfServidorPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addComponent(jLabel45)
                                .addGap(2, 2, 2)
                                .addComponent(cbServidorPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tfAsuntoCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfCorreoOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41)
                    .addComponent(tfPassMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfServidorSmtp, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(tfAsuntoCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel44)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfServidorPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel45)
                        .addComponent(cbServidorPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTpanel_emp.addTab("Correo", jPanel9);

        jPanel5.setBackground(new java.awt.Color(105, 105, 105));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));

        bt_salir.setBackground(new java.awt.Color(105, 105, 105));
        bt_salir.setForeground(new java.awt.Color(255, 255, 255));
        bt_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/exit.png"))); // NOI18N
        bt_salir.setText("Cerrar");
        bt_salir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_salir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bt_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_salirActionPerformed(evt);
            }
        });

        bt_agregar.setBackground(new java.awt.Color(105, 105, 105));
        bt_agregar.setForeground(new java.awt.Color(255, 255, 255));
        bt_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add.png"))); // NOI18N
        bt_agregar.setText("Agregar");
        bt_agregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_agregar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        bt_Modificar.setBackground(new java.awt.Color(105, 105, 105));
        bt_Modificar.setForeground(new java.awt.Color(255, 255, 255));
        bt_Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/edit.png"))); // NOI18N
        bt_Modificar.setText("Modificar");
        bt_Modificar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_Modificar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        bt_save.setBackground(new java.awt.Color(105, 105, 105));
        bt_save.setForeground(new java.awt.Color(255, 255, 255));
        bt_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        bt_save.setText("Guardar");
        bt_save.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_save.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        bt_Eliminar.setBackground(new java.awt.Color(105, 105, 105));
        bt_Eliminar.setForeground(new java.awt.Color(255, 255, 255));
        bt_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete_2.png"))); // NOI18N
        bt_Eliminar.setText("Delete");
        bt_Eliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_Eliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        bt_cancel.setBackground(new java.awt.Color(105, 105, 105));
        bt_cancel.setForeground(new java.awt.Color(255, 255, 255));
        bt_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete_bar_butto_1.png"))); // NOI18N
        bt_cancel.setText("Cancelar");
        bt_cancel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_cancel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        bt_find.setBackground(new java.awt.Color(105, 105, 105));
        bt_find.setForeground(new java.awt.Color(255, 255, 255));
        bt_find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/find_bar_butto_1.png"))); // NOI18N
        bt_find.setText("Buscar");
        bt_find.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_find.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(bt_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(bt_Modificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(bt_save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(bt_Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(bt_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(bt_find, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bt_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bt_find, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_cancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_Eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_Modificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_agregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addComponent(bt_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addComponent(jTpanel_emp, javax.swing.GroupLayout.PREFERRED_SIZE, 917, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jTpanel_emp, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        inicializaClase();
    }//GEN-LAST:event_formInternalFrameActivated

    private void bt_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_salirActionPerformed
        
    }//GEN-LAST:event_bt_salirActionPerformed

    private void jTpanel_empMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTpanel_empMouseClicked
        modelEmpresas.validaTablas(jTpanel_emp.getSelectedIndex(), modelEmpresas.islAgregar());

        if(jTpanel_emp.getSelectedIndex()==2){
            modelEmpresas.posicion_botones_2();
        }
        
        if(jTpanel_emp.getSelectedIndex()==4){
            modelEmpresas.posicion_botones_2();
        }
    }//GEN-LAST:event_jTpanel_empMouseClicked

    private void jTpanel_empStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTpanel_empStateChanged
        //        modelEmpresas.validaTablas();
    }//GEN-LAST:event_jTpanel_empStateChanged

    private void jTable_sucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_sucursalMouseClicked
        modelEmpresas.actualizaJTable(jTable_sucursal.getSelectedRow());
    }//GEN-LAST:event_jTable_sucursalMouseClicked

    private void TablaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            modelEmpresas.actualizaJTable(Tabla.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            modelEmpresas.actualizaJTable(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyReleased

    private void TablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyPressed
        String descrip = ""; String activo = "";

        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            modelEmpresas.actualizaJTable(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyPressed

    private void TablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaMouseClicked
        modelEmpresas.actualizaJTable(Tabla.getSelectedRow());
    }//GEN-LAST:event_TablaMouseClicked

    private void tfLongConsecFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfLongConsecFocusLost
        VarGlobales.setValorBigInteger(tfLongConsec.getText());

        // 0 es igual  -  1 es mayor  -  -1 es menor
        if(VarGlobales.getValorBigInteger().compareTo(new BigInteger("10"))==1){
            JOptionPane.showMessageDialog(null, "La longitud no puede ser mayor a 100", "Notificacion", JOptionPane.INFORMATION_MESSAGE);
            tfLongConsec.requestFocus();
            tfLongConsec.setText("2");
        }

        if(VarGlobales.getValorBigInteger().compareTo(BigInteger.ZERO)==0 || tfLongConsec.getText().isEmpty()){
            tfLongConsec.setText("2");
        }
    }//GEN-LAST:event_tfLongConsecFocusLost

    private void tfUtilPrecio5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfUtilPrecio5FocusLost
        VarGlobales.setValorBigDecimal(tfUtilPrecio5.getText());

        if(VarGlobales.getValorBigDecimal().compareTo(BigDecimal.ZERO)==0 || tfUtilPrecio5.getText().isEmpty()){
            tfUtilPrecio5.setFormatterFactory(null);
            tfUtilPrecio5.setText(""+VarGlobales.getValorBigDecimalFormat("0.00"));
        }else{
            tfUtilPrecio5.setText(""+VarGlobales.getValorBigDecimalFormat(tfUtilPrecio5.getText()));
        }
    }//GEN-LAST:event_tfUtilPrecio5FocusLost

    private void tfUtilPrecio4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfUtilPrecio4KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB){
            tfNomPrecio5.requestFocus();
        }
    }//GEN-LAST:event_tfUtilPrecio4KeyPressed

    private void tfUtilPrecio4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfUtilPrecio4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfUtilPrecio4ActionPerformed

    private void tfUtilPrecio4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfUtilPrecio4FocusLost
        VarGlobales.setValorBigDecimal(tfUtilPrecio4.getText());

        if(VarGlobales.getValorBigDecimal().compareTo(BigDecimal.ZERO)==0 || tfUtilPrecio4.getText().isEmpty()){
            tfUtilPrecio4.setFormatterFactory(null);
            tfUtilPrecio4.setText(""+VarGlobales.getValorBigDecimalFormat("0.00"));
        }else{
            tfUtilPrecio4.setText(""+VarGlobales.getValorBigDecimalFormat(tfUtilPrecio4.getText()));
        }
    }//GEN-LAST:event_tfUtilPrecio4FocusLost

    private void tfUtilPrecio3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfUtilPrecio3KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB){
            tfNomPrecio4.requestFocus();
        }
    }//GEN-LAST:event_tfUtilPrecio3KeyPressed

    private void tfUtilPrecio3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfUtilPrecio3FocusLost
        VarGlobales.setValorBigDecimal(tfUtilPrecio3.getText());

        if(VarGlobales.getValorBigDecimal().compareTo(BigDecimal.ZERO)==0 || tfUtilPrecio3.getText().isEmpty()){
            tfUtilPrecio3.setFormatterFactory(null);
            tfUtilPrecio3.setText(""+VarGlobales.getValorBigDecimalFormat("0.00"));
        }else{
            tfUtilPrecio3.setText(""+VarGlobales.getValorBigDecimalFormat(tfUtilPrecio3.getText()));
        }
    }//GEN-LAST:event_tfUtilPrecio3FocusLost

    private void tfUtilPrecio2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfUtilPrecio2KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB){
            tfNomPrecio3.requestFocus();
        }
    }//GEN-LAST:event_tfUtilPrecio2KeyPressed

    private void tfUtilPrecio2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfUtilPrecio2FocusLost
        VarGlobales.setValorBigDecimal(tfUtilPrecio2.getText());

        if(VarGlobales.getValorBigDecimal().compareTo(BigDecimal.ZERO)==0 || tfUtilPrecio2.getText().isEmpty()){
            tfUtilPrecio2.setFormatterFactory(null);
            tfUtilPrecio2.setText(""+VarGlobales.getValorBigDecimalFormat("0.00"));
        }else{
            tfUtilPrecio2.setText(""+VarGlobales.getValorBigDecimalFormat(tfUtilPrecio2.getText()));
        }
    }//GEN-LAST:event_tfUtilPrecio2FocusLost

    private void tfUtilPrecio1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfUtilPrecio1KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB){
            tfNomPrecio2.requestFocus();
        }
    }//GEN-LAST:event_tfUtilPrecio1KeyPressed

    private void tfUtilPrecio1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfUtilPrecio1FocusLost
        VarGlobales.setValorBigDecimal(tfUtilPrecio1.getText());

        if(VarGlobales.getValorBigDecimal().compareTo(BigDecimal.ZERO)==0 || tfUtilPrecio1.getText().isEmpty()){
            tfUtilPrecio1.setFormatterFactory(null);
            tfUtilPrecio1.setText(""+VarGlobales.getValorBigDecimalFormat("0.00"));
        }else{
            tfUtilPrecio1.setText(""+VarGlobales.getValorBigDecimalFormat(tfUtilPrecio1.getText()));
        }
    }//GEN-LAST:event_tfUtilPrecio1FocusLost

    private void tfNomPrecio5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNomPrecio5KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB){
            tfUtilPrecio5.requestFocus();
        }
    }//GEN-LAST:event_tfNomPrecio5KeyPressed

    private void tfNomPrecio4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNomPrecio4KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB){
            tfUtilPrecio4.requestFocus();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tfNomPrecio4KeyPressed

    private void tfNomPrecio3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNomPrecio3KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB){
            tfUtilPrecio3.requestFocus();
        }
    }//GEN-LAST:event_tfNomPrecio3KeyPressed

    private void tfNomPrecio2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNomPrecio2KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB){
            tfUtilPrecio2.requestFocus();
        }
    }//GEN-LAST:event_tfNomPrecio2KeyPressed

    private void tfNomPrecio1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNomPrecio1KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB){
            tfUtilPrecio1.requestFocus();
        }
    }//GEN-LAST:event_tfNomPrecio1KeyPressed

    private void jCombo_empresaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCombo_empresaItemStateChanged
        if(jCombo_empresa.isEnabled()){
            String empresa = (String)jCombo_empresa.getSelectedItem();
            if(empresa.equals("")){
                jCombo_empresa.setSelectedIndex(1);
            }
            modelEmpresas.buscaSuc();
        }
    }//GEN-LAST:event_jCombo_empresaItemStateChanged

    private void btnMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMonedaActionPerformed
        Moneda moneda = new Moneda("Empresa", jTxtEmp_Codigo.getText());
        new Moneda(this);

        Dimension desktopSize = escritorioERP.getSize();
        Dimension jInternalFrameSize = moneda.getSize();
        moneda.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

        escritorioERP.add(moneda);
        moneda.toFront();
        moneda.setVisible(true);
    }//GEN-LAST:event_btnMonedaActionPerformed

    private void tfPagWebKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPagWebKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtEmp_Direccion.requestFocus();
        }
    }//GEN-LAST:event_tfPagWebKeyPressed

    private void tfFaxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfFaxKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            tfCorreo.requestFocus();
        }
    }//GEN-LAST:event_tfFaxKeyPressed

    private void tfFaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfFaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfFaxActionPerformed

    private void cmbMonedaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMonedaItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbMonedaItemStateChanged

    private void tfCorreoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCorreoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            tfPagWeb.requestFocus();
        }
    }//GEN-LAST:event_tfCorreoKeyPressed

    private void tfTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfTelefonoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            tfFax.requestFocus();
        }
    }//GEN-LAST:event_tfTelefonoKeyPressed

    private void tfTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfTelefonoActionPerformed

    private void btn_buscarimgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarimgActionPerformed
        buscarimagen();
    }//GEN-LAST:event_btn_buscarimgActionPerformed

    private void jTxtEmp_RifKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtEmp_RifKeyTyped
        char caracter = evt.getKeyChar();

        if(pais.equals("Español (es_VE)")){
            // Verificar si la tecla pulsada no es un digito
            if(((caracter < '0') || (caracter > '9')) &&
                ((caracter != 'v') & (caracter != 'm') & (caracter != 'p') & (caracter != 'r') &
                    (caracter != 'e') & (caracter != 'j') & (caracter != 'i') & (caracter != 'e')))
            {
                evt.consume();  // ignorar el evento de teclado
            }
        }
    }//GEN-LAST:event_jTxtEmp_RifKeyTyped

    private void jTxtEmp_RifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtEmp_RifKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtEmp_Nombre.requestFocus();
        }
    }//GEN-LAST:event_jTxtEmp_RifKeyPressed

    private void jTxtEmp_RifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtEmp_RifActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtEmp_RifActionPerformed

    private void jTxtEmp_RifFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtEmp_RifFocusLost
        int registros;

        if(pais.equals("Español (es_VE)")){
            if (" -        -".equals(jTxtEmp_Rif.getText().trim())){
                jTxtEmp_Rif.setText("");

                return;
            }
        }

        int existe = 0;
        existe = controladorEmpresas.validaCodigo(jTxtEmp_Rif.getText());

        if(existe > 0){
            jTxtEmp_Rif.setText("");
            jTxtEmp_Rif.requestFocus();

            JOptionPane.showMessageDialog(null,idioma.loadLangMsg().getString("MsgRifExist"),
                idioma.loadLangMsg().getString("MsgTituloNotif"),
                JOptionPane.WARNING_MESSAGE);

            return;
        }
    }//GEN-LAST:event_jTxtEmp_RifFocusLost

    private void jTxtEmp_NombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtEmp_NombreKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            tfTelefono.requestFocus();
        }
    }//GEN-LAST:event_jTxtEmp_NombreKeyPressed

    private void claveComprobElectroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_claveComprobElectroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_claveComprobElectroActionPerformed

    private void btnUbicacionArchCertificadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbicacionArchCertificadoActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")+"\\")); 
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo", "p12");
        fc.setFileFilter(filter);
        fc.setDialogTitle("Seleccione el Archivo de la Llave Criptográfica");
        int r = fc.showOpenDialog(null);
        
        if(r==JFileChooser.APPROVE_OPTION){
            File s = fc.getSelectedFile();
            String rutaArcLlaveCript = s.getAbsoluteFile().toString();
            
            ubicArchCertificado.setText(rutaArcLlaveCript);
        }
    }//GEN-LAST:event_btnUbicacionArchCertificadoActionPerformed

    private void chkUsaFactElectronicaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkUsaFactElectronicaItemStateChanged
        if (chkUsaFactElectronica.isSelected()){
            panelConsecutivosDocElectronicos.setVisible(true);
            
            pnConfFactElectronica.setVisible(true);
            //jLabel36.setVisible(true); tfUltimoConseCbtElectron.setVisible(true);
        }else{
            panelConsecutivosDocElectronicos.setVisible(false);
            
            pnConfFactElectronica.setVisible(false);
            //jLabel36.setVisible(false); tfUltimoConseCbtElectron.setVisible(false);
        }
    }//GEN-LAST:event_chkUsaFactElectronicaItemStateChanged

    private void cbEstadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbEstadoItemStateChanged
        cargaCmbMunicipios();
    }//GEN-LAST:event_cbEstadoItemStateChanged

    private void cbMunicipioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbMunicipioItemStateChanged
        cargaCmbParroquias();
    }//GEN-LAST:event_cbMunicipioItemStateChanged

    private void cbParroquiaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbParroquiaItemStateChanged
        cargaCmbSector();
    }//GEN-LAST:event_cbParroquiaItemStateChanged

    private void cbTipIdentificadorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTipIdentificadorItemStateChanged
        jTxtEmp_Rif.requestFocus();
    }//GEN-LAST:event_cbTipIdentificadorItemStateChanged

    private void tfUltimoConseFacturaElectronicaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfUltimoConseFacturaElectronicaKeyTyped
        char let=evt.getKeyChar();

        if(Character.isLetter(let)){
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tfUltimoConseFacturaElectronicaKeyTyped

    private void cbEstado1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbEstado1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEstado1ItemStateChanged

    private void cbMunicipio1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbMunicipio1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbMunicipio1ItemStateChanged

    private void cbParroquia1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbParroquia1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbParroquia1ItemStateChanged

    private void cbSectorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbSectorItemStateChanged
        try {
            String codSector = cbSector.getSelectedItem().toString();
            int codSectorIndex = cbSector.getSelectedIndex();
            cbSector1.setSelectedIndex(codSectorIndex);
            codSector = cbSector1.getSelectedItem().toString();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cbSectorItemStateChanged

    private void chkVisualizaUltimoRegFormItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkVisualizaUltimoRegFormItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_chkVisualizaUltimoRegFormItemStateChanged

    private void chkEmpPredeterminadaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkEmpPredeterminadaStateChanged
        //modelEmpresas.buscaEmpPredeterminada();
    }//GEN-LAST:event_chkEmpPredeterminadaStateChanged

    private void chkEmpPredeterminadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkEmpPredeterminadaMouseClicked
        if (chkEmpPredeterminada.isSelected()){
            modelEmpresas.buscaEmpPredeterminada();
        }
    }//GEN-LAST:event_chkEmpPredeterminadaMouseClicked

    private void tfUltimoConseTiqueteElectronicoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfUltimoConseTiqueteElectronicoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tfUltimoConseTiqueteElectronicoKeyTyped

    private void tfUltimoConseNotaCreditoElectronicaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfUltimoConseNotaCreditoElectronicaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tfUltimoConseNotaCreditoElectronicaKeyTyped

    private void tfUltimoConseNotaDebitoElectronicaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfUltimoConseNotaDebitoElectronicaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tfUltimoConseNotaDebitoElectronicaKeyTyped

    private void tfCorreoOrigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCorreoOrigenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCorreoOrigenActionPerformed

    private void tfServidorSmtpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfServidorSmtpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfServidorSmtpActionPerformed

    private void tfAsuntoCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfAsuntoCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfAsuntoCorreoActionPerformed

    private void tfServidorPuertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfServidorPuertoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfServidorPuertoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
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
        
        //File[] file = new File[];
        //tfServidorPuerto.getText(), 
        VarGlobales.sendTestMail(tfServidorSmtp.getText(), 
                                 cbServidorPuerto.getSelectedItem().toString(),
                                 tfCorreoOrigen.getText(), 
                                 tfPassMail.getText(),
                                 "Test de Correo - CUERPO DEL CORREO",
                                 "Test de Correo - ASUNTO", true, true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tfCorreoOrigenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCorreoOrigenKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            tfPassMail.requestFocus();
        }
    }//GEN-LAST:event_tfCorreoOrigenKeyPressed

    private void tfPassMailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPassMailKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            tfServidorSmtp.requestFocus();
        }
    }//GEN-LAST:event_tfPassMailKeyPressed

    private void tfServidorSmtpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfServidorSmtpKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            tfAsuntoCorreo.requestFocus();
        }
    }//GEN-LAST:event_tfServidorSmtpKeyPressed

    private void tfServidorPuertoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfServidorPuertoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            bt_save.requestFocus();
        }
    }//GEN-LAST:event_tfServidorPuertoKeyPressed

    private void tfAsuntoCorreoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfAsuntoCorreoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            tfServidorPuerto.requestFocus();
        }
    }//GEN-LAST:event_tfAsuntoCorreoKeyPressed

    private void btnTestTokenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestTokenActionPerformed
        try{
            HttpClient httpclient = new DefaultHttpClient();

            String IDP_URI = urlAccesToken.getText(), IDP_CLIENT_ID = "";
            
            if (IDP_URI.contains("stag")){
                IDP_CLIENT_ID = "api-stag";
            }else{
                IDP_CLIENT_ID = "api-prod";
            }
            
            if (IDP_URI.endsWith("/")){
                IDP_URI = IDP_URI + "token";
            }else{
                if (IDP_URI.endsWith("token")){

                }else{
                    IDP_URI = IDP_URI + "/token";
                }
            }

            HttpPost httppost = new HttpPost(IDP_URI);
            //HttpPost httppost = new HttpPost(variablesGlobales.getUrlApiRest() + "/token");

            //List<NameValuePair> nameValuePairs = new ArrayList<>(7);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
            nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
            nameValuePairs.add(new BasicNameValuePair("username"  , userComprobElectro.getText().trim()));
            nameValuePairs.add(new BasicNameValuePair("password"  , claveComprobElectro.getText().trim()));
            nameValuePairs.add(new BasicNameValuePair("client_id" , IDP_CLIENT_ID));

            if (VarGlobales.getConnectionStatus()){
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                response = httpclient.execute(httppost);
                entity = response.getEntity();
System.err.println(entity);
System.err.println(response.getStatusLine().getStatusCode());
System.err.println(response);
                switch (response.getStatusLine().getStatusCode()) {
                    case 200:
                        if (response != null) {
                            String retSrc = EntityUtils.toString(entity);

                            result = new JSONObject(retSrc); // Convert

                            System.out.println(result);
                            String acessToken = result.getString("access_token");
                            String refreshToken =result.getString("refresh_token");
                            
                            JOptionPane.showMessageDialog(null, "Las crendiales suministradas son correctas y se obuto el token de respuesta", "Respuesta Satisfact", JOptionPane.INFORMATION_MESSAGE);
                            
                            //********** Eliminar el token (Cerrar session) **********
                            IDP_URI = urlAccesToken.getText();
            
                            if (IDP_URI.endsWith("/")){
                                IDP_URI = IDP_URI + "logout";
                            }else{
                                if (IDP_URI.endsWith("token")){
                                    IDP_URI = IDP_URI.replace("token", "logout");
                                }else{
                                    IDP_URI = IDP_URI + "/logout";
                                }
                            }

                            httppost = new HttpPost(IDP_URI);

                            //List<NameValuePair> nameValuePairs = new ArrayList<>(7);
                            nameValuePairs = new ArrayList<NameValuePair>(2);
                            nameValuePairs.add(new BasicNameValuePair("refresh_token" , acessToken));
                            nameValuePairs.add(new BasicNameValuePair("client_id"     , IDP_CLIENT_ID));
                            
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                            // Execute HTTP Post Request
                            response = httpclient.execute(httppost);
                            entity = response.getEntity();
                            
                            System.err.println(entity);
                            System.err.println(response.getStatusLine().getStatusCode());
                            System.err.println(response);
                            
                            retSrc = EntityUtils.toString(entity);

                            result = new JSONObject(retSrc); // Convert
                            System.out.println(result);
                        }
                      break;
                    case 400:
                        // Se da si se detecta un error en las validaciones, por ejemplo: si intento enviar más de una
                        // vez un documento. El encabezado "X-Error-Cause" indica la causa del problema.
//                                JOptionPane.showMessageDialog(null, xErrorCause, "Notificacion", JOptionPane.WARNING_MESSAGE);
                        String msgError = response.toString();
                        msgError = msgError.substring(msgError.indexOf("X-Error-Cause"), msgError.length());
                        msgError = msgError.substring(0, msgError.indexOf(",")-1);

                        //String xErrorCause = response2.getHeaders("X-Error-Cause").toString();
                        String xErrorCause = msgError;
                        System.err.println("Error: "+xErrorCause);
                        String msjError = xErrorCause;

                        JOptionPane.showMessageDialog(null, xErrorCause, "Notificacion", JOptionPane.WARNING_MESSAGE);
                      break;
                    case 401:
                        String retSrc = EntityUtils.toString(entity);

                        result = new JSONObject(retSrc); // Convert
                        String msjError401 = result.getString("error_description");

                        msjError = msjError401;

                        JOptionPane.showMessageDialog(null, msjError401+"\n\n"+retSrc, "Notificacion", JOptionPane.WARNING_MESSAGE);
                      break;
                    case 404:
                        System.err.println("Entro aqui ERROR 404");
                        // Se da si se detecta un error en las validaciones, por ejemplo: si intento enviar más de una
                        // vez un documento. El encabezado "X-Error-Cause" indica la causa del problema.
//                                JOptionPane.showMessageDialog(null, xErrorCause, "Notificacion", JOptionPane.WARNING_MESSAGE);

                      break;
                }
            }else{
                JOptionPane.showMessageDialog(null, "Problema de Conexion a Internet", "Notificacion", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | JSONException ex) {
            Logger.getLogger(Empresas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnTestTokenActionPerformed

    private void tfUltimoConseReceptorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfUltimoConseReceptorKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tfUltimoConseReceptorKeyTyped
    
    private void inicializaClase(){
        modelEmpresas.setVista(this);
        modelEmpresas.setVistaDos(this);
        //modelEmpresas.setButton(bt_agregar, bt_Modificar, bt_save, bt_Eliminar, bt_cancel, bt_find, bt_Atras, bt_Siguiente, bt_salir);
        modelEmpresas.setButton(bt_agregar, bt_Modificar, bt_save, bt_Eliminar, bt_cancel, bt_find, bt_salir, btn_buscarimg);
        modelEmpresas.setComponent(jTxtEmp_Codigo,jTxtEmp_Rif,jTxtEmp_Nombre,jTxtEmp_Direccion,jCheckEmp_Activo,Tabla,jTpanel_emp,
                                   jCombo_empresa,jTxt_codsur,jTxt_idsur,jTxt_nomsur,jTxtA_dirsur,jTable_sucursal,jChkbox_suc,cmbMoneda,
                                   jComboIdioma,tfTelefono,tfCorreo,tfFax,tfPagWeb,cbTipIdentificador, cbEstado, cbMunicipio, cbParroquia, 
                                   cbSector, cbEstado1, cbMunicipio1, cbParroquia1, cbSector1, chkEmpPredeterminada);
        modelEmpresas.setComponentConfEmp(tfNomPrecio1, tfNomPrecio2, tfNomPrecio3, tfNomPrecio4, tfNomPrecio5, tfUtilPrecio1, 
                                          tfUtilPrecio2, tfUtilPrecio3, tfUtilPrecio4, tfUtilPrecio5, chkUtilCosto, chkCodConsecutivo, 
                                          tfLongConsec, chkCodConsecutivoGrupSubGrup, chkVisualizaUltimoRegForm, jFondo_Preview);
        modelEmpresas.setComponentConfMailOrigen(tfCorreoOrigen, tfPassMail, tfServidorSmtp, tfAsuntoCorreo, tfServidorPuerto, taCuerpoCorreo,
                                                 cbServidorPuerto);
        modelEmpresas.setComponentConfEmpFactElectron(chkUsaFactElectronica, pnConfFactElectronica, cbProveedServicio, userComprobElectro,
                                                      claveComprobElectro, urlApiRest, ubicArchCertificado, btnUbicacionArchCertificado, 
                                                      claveArchCerticado,tfUltimoConseFacturaElectronica, tfUltimoConseTiqueteElectronico,
                                                      tfUltimoConseNotaCreditoElectronica, tfUltimoConseNotaDebitoElectronica, urlAccesToken,
                                                      tfUltimoConseReceptor);
        
//        modelEmpresas.cargaTabla(false);
//        modelEmpresas.validaTablas(0, true);
    }
    
    public void buscarimagen(){
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")+"\\")); 
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagenes", "bmp", "jpge", "jpg", "png", "jpeg");
        fc.setFileFilter(filter);
        fc.setDialogTitle("Seleccione el Archivo de Imagen");
        int r = fc.showOpenDialog(null);
        
        if(r==JFileChooser.APPROVE_OPTION){
            File s = fc.getSelectedFile();
            String rutaFoto = s.getAbsoluteFile().toString();
            
            String ruta = fc.getSelectedFile().getAbsolutePath();
            String nombre = fc.getSelectedFile().getName();
            
            modelEmpresas.setRutaFoto(ruta);
            modelEmpresas.setNombreFoto(nombre);
                
            //jTxtOpe_RutaImg.setText(l);
            
            Image preview = Toolkit.getDefaultToolkit().getImage(rutaFoto);
            ImageIcon icon = new ImageIcon(preview.getScaledInstance(jFondo_Preview.getWidth(), jFondo_Preview.getHeight(), Image.SCALE_DEFAULT));
            jFondo_Preview.setIcon(icon);
                
            System.out.println(s);
        }
    }
    
    private void CargarIdioma() {
        switch ((int) jComboIdioma.getSelectedItem()) {
            case 2:
                idioma.setLocale("CR");
                VarGlobales.setIdioma("CR");
			
		break;
            case 1:
                idioma.setLocale("ES");
                VarGlobales.setIdioma("ES");
			
		break;
            case 0:
                idioma.setLocale("EN");
                VarGlobales.setIdioma("EN");
			
		break;
            default:
                break;
	}
        
        jLabel4.setText(idioma.loadLangComponent().getString("lbEmpRif"));
    }
 
    private void cargaCbmTipoIdentificador() {
        try {
            int Item = (int) jComboIdioma.getSelectedItem();        
            String pais = (String) elementos.get(Item), sql = "";
            
            VarGlobales.setlBaseDatosConfiguracion(true);
            
            if((pais.equals("Costa Rica (es_CR)"))){
                sql = "SELECT name AS DATO1 FROM type_person WHERE pais='"+pais+"' AND id_rol=3;";
            }
            if(pais.equals("Español (es_VE)")){
                sql = "SELECT name AS DATO1 FROM type_person WHERE pais='"+pais+"'";
            }
            DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
        
            cbTipIdentificador.setModel(mdl);
            cbTipIdentificador.setSelectedIndex(0);
            
            VarGlobales.setlBaseDatosConfiguracion(false);
        } catch (Exception e) {
        }
    }
    
//    private void cargaCmbPais() {
//        try {
//            String sql = "SELECT CONCAT(pai_nombre,' - ', pai_id) AS DATO1 FROM dnpais";
//            DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
//        
//            jComboIdioma.setModel(mdl);
//            jComboIdioma.setSelectedIndex(0);
//        } catch (Exception e) {
//        }
//    }
    
    private void cargaCmbEstados(String codPais) {
        try {
            if (!codPais.equals("")){
                VarGlobales.setlBaseDatosConfiguracion(true);
                
                String sql = "SELECT CONCAT(est_nombre) AS DATO1 FROM dnestados "+
                             "WHERE est_codpai='"+codPais+"'";
                DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
        
                cbEstado.setModel(mdl);
                cbEstado.setSelectedIndex(0);
        
                sql = "SELECT CONCAT(est_id) AS DATO1 FROM dnestados "+
                             "WHERE est_codpai='"+codPais+"'";
                mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
        
                cbEstado1.setModel(mdl);
                cbEstado1.setSelectedIndex(0);
                
                VarGlobales.setlBaseDatosConfiguracion(false);
            }
        } catch (Exception e) {
        }
    }
    
    private void cargaCmbMunicipios() {
        try {
            String codEstados = cbEstado.getSelectedItem().toString();
            int codEstadosIndex = cbEstado.getSelectedIndex();
            cbEstado1.setSelectedIndex(codEstadosIndex);
            codEstados = cbEstado1.getSelectedItem().toString();
        
            if (!codEstados.equals("")){
                VarGlobales.setlBaseDatosConfiguracion(true);
                
                String sql = "SELECT CONCAT(mun_nombre) AS DATO1 FROM dnmunicipios "+
                             "WHERE mun_codest='"+codEstados.substring(codEstados.indexOf("-")+1,codEstados.length()).trim()+"'";
                DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
        
                cbMunicipio.setModel(mdl);
                cbMunicipio.setSelectedIndex(0);
                cbMunicipio.setEnabled(true);
                
                sql = "SELECT CONCAT(mun_id) AS DATO1 FROM dnmunicipios "+
                             "WHERE mun_codest='"+codEstados.substring(codEstados.indexOf("-")+1,codEstados.length()).trim()+"'";
                mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
        
                cbMunicipio1.setModel(mdl);
                cbMunicipio1.setSelectedIndex(0);
                cbMunicipio1.setEnabled(true);
                
                VarGlobales.setlBaseDatosConfiguracion(false);
            }
        } catch (Exception e) {
        }
    }
    
    private void cargaCmbParroquias(){
        try {
            String codMunicipio = cbMunicipio.getSelectedItem().toString();
            int codMunicipioIndex = cbMunicipio.getSelectedIndex();
            cbMunicipio1.setSelectedIndex(codMunicipioIndex);
            codMunicipio = cbMunicipio1.getSelectedItem().toString();
        
            if (!codMunicipio.equals("")){
                VarGlobales.setlBaseDatosConfiguracion(true);
                
                String sql = "SELECT CONCAT(par_nombre) AS DATO1 FROM dnparroquias "+
                             "WHERE par_codmun='"+codMunicipio.substring(codMunicipio.indexOf("-")+1,codMunicipio.length()).trim()+"'";
                DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
        
                cbParroquia.setModel(mdl);
                cbParroquia.setSelectedIndex(0);
                cbParroquia.setEnabled(true);
                
                sql = "SELECT CONCAT(par_id) AS DATO1 FROM dnparroquias "+
                             "WHERE par_codmun='"+codMunicipio.substring(codMunicipio.indexOf("-")+1,codMunicipio.length()).trim()+"'";
                mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
        
                cbParroquia1.setModel(mdl);
                cbParroquia1.setSelectedIndex(0);
                cbParroquia1.setEnabled(true);
                
                VarGlobales.setlBaseDatosConfiguracion(false);
            }
        } catch (Exception e) {
        }
    }
    
    private void cargaCmbSector(){
        try {
            String codParroquia = cbParroquia.getSelectedItem().toString();
            int codParroquiaIndex = cbParroquia.getSelectedIndex();
            cbParroquia1.setSelectedIndex(codParroquiaIndex);
            codParroquia = cbParroquia1.getSelectedItem().toString();
        
            if (!codParroquia.equals("")){
                VarGlobales.setlBaseDatosConfiguracion(true);
                
                String sql = "SELECT CONCAT(sbs_nombre) AS DATO1 FROM dnsector "+
                             "WHERE sbs_codpar='"+codParroquia.substring(codParroquia.indexOf("-")+1,codParroquia.length()).trim()+"'";
                DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
        
                cbSector.setModel(mdl);
                cbSector.setSelectedIndex(0);
                cbSector.setEnabled(true);
                
                sql = "SELECT CONCAT(sbs_id) AS DATO1 FROM dnsector "+
                             "WHERE sbs_codpar='"+codParroquia.substring(codParroquia.indexOf("-")+1,codParroquia.length()).trim()+"'";
                mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
        
                cbSector1.setModel(mdl);
                cbSector1.setSelectedIndex(0);
                cbSector1.setEnabled(true);
                
                VarGlobales.setlBaseDatosConfiguracion(false);
            }
        } catch (Exception e) {
        }
    }
    
    private void reagrupaElementos(){
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(595, 595, 595))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbTipoIdentificador))
                                .addGap(17, 17, 17)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTxtEmp_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(chkEmpPredeterminada))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(cbTipIdentificador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGap(19, 19, 19)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jTxtEmp_Rif, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jTxtEmp_Nombre, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tfCorreo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                                        .addComponent(tfTelefono, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tfFax, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))))
                            .addComponent(jLabel1)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cmbMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnMoneda))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(125, 125, 125)
                                .addComponent(jCheckEmp_Activo))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jFondo_Preview, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_buscarimg)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tfPagWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbMunucipios)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbParroquias)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cbParroquia, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lbSector)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbSector, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cbEstado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbMunicipio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbParroquia1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbSector1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckEmp_Activo)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFondo_Preview, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTxtEmp_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(chkEmpPredeterminada)))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtEmp_Rif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(cbTipIdentificador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbTipoIdentificador))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTxtEmp_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfFax, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfPagWeb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(71, 71, 71)
                                .addComponent(jLabel15))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnMoneda)
                                    .addComponent(btn_buscarimg))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17)
                            .addComponent(jComboIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbEstados)
                                .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbMunucipios)
                                .addComponent(cbMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbParroquias))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbParroquia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbSector)
                                .addComponent(cbSector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbEstado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbMunicipio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbParroquia1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbSector1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla;
    private com.l2fprod.common.swing.JLinkButton bt_Eliminar;
    private com.l2fprod.common.swing.JLinkButton bt_Modificar;
    private com.l2fprod.common.swing.JLinkButton bt_agregar;
    private com.l2fprod.common.swing.JLinkButton bt_cancel;
    private com.l2fprod.common.swing.JLinkButton bt_find;
    private com.l2fprod.common.swing.JLinkButton bt_salir;
    private com.l2fprod.common.swing.JLinkButton bt_save;
    private javax.swing.JButton btnMoneda;
    private javax.swing.JButton btnTestToken;
    private javax.swing.JButton btnUbicacionArchCertificado;
    private javax.swing.JButton btn_buscarimg;
    private org.jdesktop.swingx.JXComboBox cbEstado;
    private org.jdesktop.swingx.JXComboBox cbEstado1;
    private org.jdesktop.swingx.JXComboBox cbMunicipio;
    private org.jdesktop.swingx.JXComboBox cbMunicipio1;
    private org.jdesktop.swingx.JXComboBox cbParroquia;
    private org.jdesktop.swingx.JXComboBox cbParroquia1;
    private org.jdesktop.swingx.JXComboBox cbProveedServicio;
    private org.jdesktop.swingx.JXComboBox cbSector;
    private org.jdesktop.swingx.JXComboBox cbSector1;
    private javax.swing.JComboBox<String> cbServidorPuerto;
    private org.jdesktop.swingx.JXComboBox cbTipIdentificador;
    private javax.swing.JCheckBox chkCodConsecutivo;
    private javax.swing.JCheckBox chkCodConsecutivoGrupSubGrup;
    private javax.swing.JCheckBox chkEmpPredeterminada;
    private javax.swing.JCheckBox chkUsaFactElectronica;
    private javax.swing.JCheckBox chkUtilCosto;
    private javax.swing.JCheckBox chkVisualizaUltimoRegForm;
    private javax.swing.JPasswordField claveArchCerticado;
    private javax.swing.JPasswordField claveComprobElectro;
    private javax.swing.JComboBox cmbMoneda;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckEmp_Activo;
    private javax.swing.JCheckBox jChkbox_suc;
    private javax.swing.JComboBox jCombo_empresa;
    public static javax.swing.JLabel jFondo_Preview;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable_sucursal;
    private javax.swing.JTabbedPane jTpanel_emp;
    private javax.swing.JTextArea jTxtA_dirsur;
    private javax.swing.JTextField jTxtEmp_Codigo;
    private javax.swing.JTextArea jTxtEmp_Direccion;
    private javax.swing.JTextField jTxtEmp_Nombre;
    private javax.swing.JFormattedTextField jTxtEmp_Rif;
    private javax.swing.JTextField jTxt_codsur;
    private javax.swing.JTextField jTxt_idsur;
    private javax.swing.JTextField jTxt_nomsur;
    private javax.swing.JLabel lbEstados;
    private javax.swing.JLabel lbMunucipios;
    private javax.swing.JLabel lbParroquias;
    private javax.swing.JLabel lbSector;
    private javax.swing.JLabel lbTipoIdentificador;
    private javax.swing.JPanel panelConsecutivosDocElectronicos;
    private javax.swing.JPanel pnConfFactElectronica;
    private javax.swing.JTextArea taCuerpoCorreo;
    private app.bolivia.swing.JCTextField tfAsuntoCorreo;
    private javax.swing.JTextField tfCorreo;
    private app.bolivia.swing.JCTextField tfCorreoOrigen;
    private javax.swing.JTextField tfFax;
    private javax.swing.JTextField tfLongConsec;
    private javax.swing.JTextField tfNomPrecio1;
    private javax.swing.JTextField tfNomPrecio2;
    private javax.swing.JTextField tfNomPrecio3;
    private javax.swing.JTextField tfNomPrecio4;
    private javax.swing.JTextField tfNomPrecio5;
    private javax.swing.JTextField tfPagWeb;
    private javax.swing.JPasswordField tfPassMail;
    private app.bolivia.swing.JCTextField tfServidorPuerto;
    private app.bolivia.swing.JCTextField tfServidorSmtp;
    private javax.swing.JTextField tfTelefono;
    private javax.swing.JTextField tfUltimoConseFacturaElectronica;
    private javax.swing.JTextField tfUltimoConseNotaCreditoElectronica;
    private javax.swing.JTextField tfUltimoConseNotaDebitoElectronica;
    private javax.swing.JTextField tfUltimoConseReceptor;
    private javax.swing.JTextField tfUltimoConseTiqueteElectronico;
    private javax.swing.JFormattedTextField tfUtilPrecio1;
    private javax.swing.JFormattedTextField tfUtilPrecio2;
    private javax.swing.JFormattedTextField tfUtilPrecio3;
    private javax.swing.JFormattedTextField tfUtilPrecio4;
    private javax.swing.JFormattedTextField tfUtilPrecio5;
    private javax.swing.JTextField ubicArchCertificado;
    private javax.swing.JTextField urlAccesToken;
    private javax.swing.JTextField urlApiRest;
    private javax.swing.JTextField userComprobElectro;
    // End of variables declaration//GEN-END:variables
}