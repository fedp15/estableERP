package Vista;

import Controlador.ControladorAdministrador;
import Controlador.ControladorCategorias;
import Modelos.Bitacora;
import Modelos.CargaMenu;
import Modelos.ModelMenuERP;
import Modelos.VariablesGlobales;
import com.l2fprod.common.swing.JLinkButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingWorker;
import util.Internacionalizacion;
import util.ValidarJornada;

public class MenuPrincipal extends javax.swing.JFrame {
    public static final int salir=1;
    public static String Maestro="";
    public static JMenu Menus;
    public static JMenuItem SubMenus, SubMenus2, SubMenus3, SubMenus4, SubMenus5;
    public static JMenuBar barra = new JMenuBar();//LA DECLARAMOS E INSTANCIAMOS.
    
    String hora, minutos, segundos, ampm, Form_Carga, TipDoc, Titulo;
    Calendar calendario;
    Thread h1;
    
    private boolean lSubMenu=false, lContraerMenu = false, lMenoShow = false, lMenoShow_2 = false;
    private boolean lMenu1=false, lMenu2 = false, lMenu3 = false, lMenu4 = false, lMenu5 = false, lMenu6 = false, lMenu7 = false, lMenu8 = false,
                    lMenu9 = false;
    private boolean lSubMenu1=false, lSubMenu2 = false, lSubMenu3 = false, lSubMenu4 = false, lSubMenu5 = false, lSubMenu6 = false, lSubMenu7 = false, 
                    lSubMenu8 = false;
    private boolean lSubSubMenu1=false, lSubSubMenu2 = false, lSubSubMenu3 = false, lSubSubMenu4 = false, lSubSubMenu5 = false, lSubSubMenu6 = false, 
                    lSubSubMenu7 = false, lSubSubMenu8 = false;
    private String Evento, metodo = "", clase="", Paramet="", pr="", Dato="", Dato1="", nombreMenu = "", origenDoc = "";
    private int veces=0, PosIniParamet=0, PosFinParamet=0, pos=0;
    private char _toCompare;
    private char [] caracteres;
    private ResultSet rs=null, rs_raiz=null, rs_hijos=null, rs_nietos=null, rs_bisnietos=null, rs_bisnietos1=null, rs_bisnietos2=null;
    private ResultSet rsTree, rsSubTree, rsSubTree2, rsSubTree3, rsSubTree4;
    private boolean lMetedo=false;
    private Vector Msg, elementos, menu;
    private String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
    private String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";

    private MenuPrincipal menuERP = null;

    private DefaultMutableTreeNode NodoSeleccionado;
    private String categPad = "", origen;
    
    private final ControladorAdministrador controladorAdministrador = new ControladorAdministrador();
    private final ControladorCategorias controladorCategorias = new ControladorCategorias();
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final ModelMenuERP modelMenuERP = ModelMenuERP.getModelMenuERP();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    
//    private final ModelMoneda modelMoneda = ModelMoneda.getModelMoneda();
//    private final ModelTipoContacto modelTipoContacto = ModelTipoContacto.getModelTipoContacto();
//    private final ModelProductos modelProductos = ModelProductos.getModelProductos();
//    private final ModelDocumentos modelDocumentos = ModelDocumentos.getModelDocumentos();

    private JLinkButton btnCbtesElectronicos = new com.l2fprod.common.swing.JLinkButton(), 
                        btnSalir = new com.l2fprod.common.swing.JLinkButton(),
                        btnAcusesRecibosCompras = new com.l2fprod.common.swing.JLinkButton();
    private JLabel lbStatus2 = new javax.swing.JLabel();
    private JLabel lbStatusInternet2 = new javax.swing.JLabel();
    public static JLabel lbRegistroComprobantes = new javax.swing.JLabel(), lbNumeroEquipo = new javax.swing.JLabel();
    private Dimension desktopSize, screenSize;
    private Dimension jInternalFrameSize;
    
    private JPopupMenu popupMenuConfiguracion = new JPopupMenu();
    private JMenuItem menuItemConfiguracion;
    private JPopupMenu popupMenuInventario = new JPopupMenu();
    private JPopupMenu popupMenuVentas = new JPopupMenu();
    private JPopupMenu popupMenuCompras = new JPopupMenu();
    private JPopupMenu popupMenuTaller = new JPopupMenu();
    private JPopupMenu popupMenuTesoreria = new JPopupMenu();
    private JPopupMenu popupMenuContabilidad = new JPopupMenu();
    
    private ActionListener actionListener_1, actionListener_2, actionListener_3, actionListener_4, actionListener_5,
                           actionListener_6, actionListener_7, actionListener_8, actionListener_9, actionListener_10,
                           actionListener_11, actionListener_12;
    
    private JPanel panelBtnVentas = new javax.swing.JPanel(), panelBtnCompras = new javax.swing.JPanel(), panelBtnInventario = new javax.swing.JPanel(),
                   panelBtnTaller = new javax.swing.JPanel(), panelBtnContabilidad = new javax.swing.JPanel(), panelBtnMaestrosSitema = new javax.swing.JPanel(),
                   panelBtnTesoreria = new javax.swing.JPanel();
    
    private final JFXPanel jfxPanel = new JFXPanel(), jfxPanelInventario = new JFXPanel(), jfxPanelVentas = new JFXPanel(), 
                  jfxPanelCompras = new JFXPanel(), jfxPanelContabilidad = new JFXPanel(), jfxPanelMaestrosSistema = new JFXPanel(), 
                  jfxPanelTaller = new JFXPanel(), jfxPanelTesoreria = new JFXPanel();
    private final double widthImage = 40, heightImage = 40; 
    
    private TaskActionMenu taskActionMenu;
    
    public MenuPrincipal() {
        initComponents();
        
        modelMenuERP.setVista(this);
        lbHora.setVisible(false); jLogoCli.setVisible(false); jLogoEmp.setVisible(false);
        this.setIconImage(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/icono_app.png").getImage());

//        CargaMenu MenuPrincipal = new CargaMenu();
//        try {
//            if (modelMenuERP.islReloadMenu()){
//                MenuPrincipal.CargaMenuPrincipal(false);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(MenuERP.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        setJMenuBar(barra);

        //escritorioERP.setBorder(new ImagenFondo(VarGlobales.getIdUser(), escritorioERP));
        
        escritorioERP.setBackground(java.awt.Color.WHITE);
        escritorioERP.setBackground(Color.white);
        
        if (VarGlobales.getConnectionStatus()){
            lbStatusInternet2.setForeground(new java.awt.Color(0, 255, 0));
            lbStatusInternet2.setFont(new java.awt.Font("Tahoma", 1, 13));
            lbStatusInternet2.setText("OnLine");
        }else{
            lbStatusInternet2.setForeground(new java.awt.Color(255, 0, 0));
            lbStatusInternet2.setFont(new java.awt.Font("Tahoma", 1, 13));
            lbStatusInternet2.setText("OffLine");
        }

        desktopSize = escritorioERP.getSize();
        jInternalFrameSize = escritorioERP.getSize();
        
        lbStatus.setVisible(false);
        lbStatusInternet.setVisible(false);
        
        lbStatus2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbStatus2.setText("Status Conexión:");
        
        Toolkit t = Toolkit.getDefaultToolkit();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        lbStatus2.setBounds(screenSize.width-325,screenSize.height-100,150,21);
        lbStatusInternet2.setBounds(screenSize.width-205,screenSize.height-100,150,21);
        
        escritorioERP.add(lbStatus2);
        escritorioERP.add(lbStatusInternet2);
        
        lbRegistroComprobantes.setForeground(new java.awt.Color(255, 0, 0));
        lbRegistroComprobantes.setFont(new java.awt.Font("Tahoma", 1, 13));
        lbRegistroComprobantes.setBounds(10,screenSize.height-100,950,21);
        
        escritorioERP.add(lbRegistroComprobantes);
        
        ResultSet rsCbtProcPent = modelMenuERP.buscaCbtProcesando();
        int numProcesando = 0, numPendiete = 0;
        String msjProcesando = "", msjPendiente = "", saltoLinea = "";
        
        try {
            if (rsCbtProcPent.getRow()>0){
                lbRegistroComprobantes.setVisible(true);
                
                rsCbtProcPent.beforeFirst();

                while(rsCbtProcPent.next()) {
                    if (rsCbtProcPent.getString("ind_estado").toLowerCase().equals("procesando")){
                        numProcesando++;
                    }
                    
                    if (rsCbtProcPent.getString("ind_estado").toLowerCase().equals("pendiente")){
                        numPendiete++;
                    }
                }
                
                if (numProcesando>0){
                    msjProcesando = "Tiene "+numProcesando+" comprobante(s) electronico(s) que estan con estado ''PROCESANDO''";
                }
                
                if (numPendiete>0){
                    if (numProcesando>0){
                        saltoLinea = "<br>";
                    }
                    msjPendiente = saltoLinea+"Tiene "+numPendiete+" comprobante(s) electronico(s) que estan con estado ''PENDIENTE'' y se requiere que se envie el XML al Ministerio de Hacienda";
                }
                
                lbRegistroComprobantes.setText("<html>"+msjProcesando+msjPendiente+"</html>");
            }else{
                lbRegistroComprobantes.setVisible(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //lbNumeroEquipo.setForeground(new java.awt.Color(255, 0, 0));
        if (lbRegistroComprobantes.isVisible()){
            if (numProcesando>0 && numPendiete>0){
                lbNumeroEquipo.setBounds(10,screenSize.height-125,550,21);
                lbRegistroComprobantes.setBounds(10,screenSize.height-100,950,41);
            }else{
                lbNumeroEquipo.setBounds(10,screenSize.height-120,550,21);
            }
        }else{
            lbNumeroEquipo.setBounds(10,screenSize.height-100,550,21);
        }
        lbNumeroEquipo.setText("<html>Equipo #: "+VarGlobales.getCodigoEquipo()+"</html>");
        lbNumeroEquipo.setFont(new java.awt.Font("Tahoma", 1, 13));
        
        escritorioERP.add(lbNumeroEquipo);
        
        if(VarGlobales.getPais().equals("Costa Rica (es_CR)")){
            btnCbtesElectronicos.setBackground(new java.awt.Color(105, 105, 105));
            //btnCbtesElectronicos.setForeground(new java.awt.Color(255, 255, 255));
            btnCbtesElectronicos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/factura_electronica_menu_principal_3.png"))); // NOI18N
            btnCbtesElectronicos.setText("Cbtes. Electronicos");
            btnCbtesElectronicos.setFont(new java.awt.Font("Tahoma", 1, 12));
            btnCbtesElectronicos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            btnCbtesElectronicos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btnCbtesElectronicos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            //btnCbtesElectronicos.setBounds(screenSize.width-155,20,154,114);
            btnCbtesElectronicos.setBounds(screenSize.width-185,20,200,114);
        
            escritorioERP.add(btnCbtesElectronicos);
        
            btnCbtesElectronicos.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
//                    VisualizarCbteElectronicos visualizarCbteElectronicos = new VisualizarCbteElectronicos("Ventas");
//
//                    Dimension desktopSize = escritorioERP.getSize();
//                    Dimension jInternalFrameSize = visualizarCbteElectronicos.getSize();
//                    visualizarCbteElectronicos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//                    escritorioERP.add(visualizarCbteElectronicos);
//                    visualizarCbteElectronicos.toFront();
//                    visualizarCbteElectronicos.setVisible(true);
                }
            });
            
            btnAcusesRecibosCompras.setBackground(new java.awt.Color(105, 105, 105));
            btnAcusesRecibosCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/recepcion_compra_64.png"))); // NOI18N
            btnAcusesRecibosCompras.setText("<html><center>Consulta de Cbtes. <br>de Compras</center></html>");
            btnAcusesRecibosCompras.setFont(new java.awt.Font("Tahoma", 1, 12));
            btnAcusesRecibosCompras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            btnAcusesRecibosCompras.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btnAcusesRecibosCompras.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btnAcusesRecibosCompras.setBounds(screenSize.width-185,135,200,114);
        
            escritorioERP.add(btnAcusesRecibosCompras);
        
            btnAcusesRecibosCompras.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
//                    VisualizarCbteElectronicos visualizarCbteElectronicos = new VisualizarCbteElectronicos("Compras");
//
//                    Dimension desktopSize = escritorioERP.getSize();
//                    Dimension jInternalFrameSize = visualizarCbteElectronicos.getSize();
//                    visualizarCbteElectronicos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//                    escritorioERP.add(visualizarCbteElectronicos);
//                    visualizarCbteElectronicos.toFront();
//                    visualizarCbteElectronicos.setVisible(true);
                }
            });
        }
        
        btnSalir.setBackground(new java.awt.Color(105, 105, 105));
        //btnCbtesElectronicos.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/exit_1.png"))); // NOI18N
        btnSalir.setText("       Salir       ");
        btnSalir.setFont(new java.awt.Font("Tahoma", 1, 12));
        btnSalir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.setBounds(screenSize.width-155,screenSize.height-180,154,114);

        escritorioERP.add(btnSalir);
            
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JInternalFrame[] array = escritorioERP.getAllFrames();
                int FormActivos = array.length;

                if (FormActivos>0){
                    JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgCloseForm"), 
                                                        idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int salir = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgSalirSistema"),
                                                            idioma.loadLangMsg().getString("MsgNotificacion"),
                                                            JOptionPane.YES_NO_OPTION);

                if(salir == 0){
                    System.exit(0);
                }
            }
        });
        
        //escritorioERP.setBorder((Border) new ImagenFondo(VarGlobales.getIdUser()));
        
        //escritorioERP.setBackground(java.awt.Color.WHITE);
        //escritorioERP.setBackground(Color.white);
        iniciarreloj();
        
        //******************* Validacion sobre el Boton Cerrar del la Barra de Titulo *******************
        setDefaultCloseOperation (WindowConstants.DO_NOTHING_ON_CLOSE);

	addWindowListener(new WindowAdapter(){
	    public void windowClosing(WindowEvent we){
                JInternalFrame[] array = escritorioERP.getAllFrames();
                int FormActivos = array.length;

                if (FormActivos>0){
                    JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgCloseForm"), 
                                                idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                                JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
	        int eleccion = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgSalirSistema"),
                                                             idioma.loadLangMsg().getString("MsgTituloNotif"),
                                                             JOptionPane.YES_NO_OPTION);

	        if ( eleccion == 0) {
                    Bitacora insertar = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), 
                                                     VarGlobales.getIdUser(), VarGlobales.getUserName(), 
                                                     "cerro sesión", "Usuario "+VarGlobales.getUserName().trim()+" ha cerrado sesión");
                    
                    System.exit(0);
	        }  
	    }
	});
//***********************************************************************************************    

        //jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("+text+"))); // NOI18N
        //escritorioERP.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
        escritorioERP.setLocation((desktopSize.width - jInternalFrameSize.width),(desktopSize.height- jInternalFrameSize.height));
        escritorioERP.setPreferredSize(new java.awt.Dimension(341, 141));
        //escritorioERP.setLocation((jInternalFrameSize.width)/2,(jInternalFrameSize.height)/2);
        MenuPrincipal.setDefaultLookAndFeelDecorated(true);
        
        this.setTitle("Bienvenidos al Sistema de Gestión Administrativa ["+VarGlobales.getCodEmpresa()+" - "+VarGlobales.getNomEmpresa()+"]");

        //cargaMenuTree();
        //cargaPopupMenu();
        evaluaConexion();
        evaluaEstadosCbtElectronicos();
        
        jScrollPane1.setVisible(false); 
        //panelBtn.setVisible(false); 
        //Animacion.Animacion.subir(-105, 0, 2, 2, panelBtn);
        
        //cargaMenuConfig();
        createSceneMenuConfig();
        //panelBtn.setLayout(new BorderLayout());
        //panelBtn.add(jfxPanel,BorderLayout.CENTER);
        panelBtn.setVisible(false);
        
        jfxPanel.setBackground(new java.awt.Color(255, 255, 255));
        jfxPanel.setPreferredSize(new Dimension(3323, 95));
        
        jfxPanelInventario.setBackground(new java.awt.Color(255, 255, 255));
        jfxPanelInventario.setPreferredSize(new Dimension(3323, 100));
        panelBtnInventario.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnInventario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout panelBtnLayout = new javax.swing.GroupLayout(panelBtnInventario);
        panelBtnInventario.setLayout(panelBtnLayout);
        panelBtnLayout.setHorizontalGroup(
            panelBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelBtnLayout.setVerticalGroup(
            panelBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 84, Short.MAX_VALUE)
        );
        
        panelBtnInventario.setBounds(0, 17, 3323, 100);
        escritorioERP.setLayer(panelBtnInventario, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorioERP.add(panelBtnInventario);
        panelBtnInventario.setVisible(false);
        
        creaSceneMenuInventario();
//        Animacion.Animacion.subir(20, -204, 12, 9, panelBtn);
        
        jfxPanelVentas.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnVentas.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnVentas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));

        panelBtnLayout = new javax.swing.GroupLayout(panelBtnVentas);
        panelBtnVentas.setLayout(panelBtnLayout);
        panelBtnLayout.setHorizontalGroup(
            panelBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelBtnLayout.setVerticalGroup(
            panelBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 84, Short.MAX_VALUE)
        );
        
        panelBtnVentas.setBounds(0, 17, 3323, 100);
        escritorioERP.setLayer(panelBtnVentas, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorioERP.add(panelBtnVentas);
        panelBtnVentas.setVisible(false);
        
        crearSceneMenuVentas();
        
        jfxPanelCompras.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnCompras.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnCompras.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));

        panelBtnLayout = new javax.swing.GroupLayout(panelBtnCompras);
        panelBtnCompras.setLayout(panelBtnLayout);
        panelBtnLayout.setHorizontalGroup(
            panelBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelBtnLayout.setVerticalGroup(
            panelBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 84, Short.MAX_VALUE)
        );
        
        panelBtnCompras.setBounds(0, 17, 3323, 100);
        escritorioERP.setLayer(panelBtnCompras, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorioERP.add(panelBtnCompras);
        panelBtnCompras.setVisible(false);
        
        crearSceneMenuCompras();
        
        jfxPanelTaller.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnTaller.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnTaller.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));

        panelBtnLayout = new javax.swing.GroupLayout(panelBtnTaller);
        panelBtnTaller.setLayout(panelBtnLayout);
        panelBtnLayout.setHorizontalGroup(
            panelBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelBtnLayout.setVerticalGroup(
            panelBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 84, Short.MAX_VALUE)
        );
        
        panelBtnTaller.setBounds(0, 17, 3323, 100);
        escritorioERP.setLayer(panelBtnTaller, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorioERP.add(panelBtnTaller);
        panelBtnTaller.setVisible(false);
        
        crearSceneMenuTaller();
        //panelBtnTaller.setLayout(new BorderLayout());
        //panelBtnTaller.add(jfxPanelTaller,BorderLayout.CENTER);
        
        jfxPanelTesoreria.setBackground(new java.awt.Color(255, 255, 255));
        jfxPanelTesoreria.setPreferredSize(new Dimension(3323, 100));
        panelBtnTesoreria.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnTesoreria.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        
        panelBtnLayout = new javax.swing.GroupLayout(panelBtnTesoreria);
        panelBtnTesoreria.setLayout(panelBtnLayout);
        panelBtnLayout.setHorizontalGroup(
            panelBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelBtnLayout.setVerticalGroup(
            panelBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 84, Short.MAX_VALUE)
        );
        
        panelBtnTesoreria.setBounds(0, 17, 3323, 100);
        escritorioERP.setLayer(panelBtnTesoreria, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorioERP.add(panelBtnTesoreria);
        panelBtnTesoreria.setVisible(false);
        
        createSceneMenuTesoreria();
        
        jfxPanelContabilidad.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnContabilidad.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnContabilidad.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));

        panelBtnLayout = new javax.swing.GroupLayout(panelBtnContabilidad);
        panelBtnContabilidad.setLayout(panelBtnLayout);
        panelBtnLayout.setHorizontalGroup(
            panelBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelBtnLayout.setVerticalGroup(
            panelBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 84, Short.MAX_VALUE)
        );
        
        panelBtnContabilidad.setBounds(0, 17, 3323, 100);
        escritorioERP.setLayer(panelBtnContabilidad, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorioERP.add(panelBtnContabilidad);
        panelBtnContabilidad.setVisible(false);
        
        crearSceneMenuContabilidad();
        
        jfxPanelMaestrosSistema.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnMaestrosSitema.setBackground(new java.awt.Color(255, 255, 255));
        panelBtnMaestrosSitema.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));

        panelBtnLayout = new javax.swing.GroupLayout(panelBtnMaestrosSitema);
        panelBtnMaestrosSitema.setLayout(panelBtnLayout);
        panelBtnLayout.setHorizontalGroup(
            panelBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelBtnLayout.setVerticalGroup(
            panelBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 84, Short.MAX_VALUE)
        );
        
        panelBtnMaestrosSitema.setBounds(0, 17, 3323, 100);
        escritorioERP.setLayer(panelBtnMaestrosSitema, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorioERP.add(panelBtnMaestrosSitema);
        panelBtnMaestrosSitema.setVisible(false);
        
        crearSceneMenuMaestros();
    }

//    public void cargaPopupMenu(){
//        rsTree = controladorCategorias.menuTreePrincipal();
//        
//        try {
//            rsTree.first();
//            int y, x, j, k;
//            
//            for(int i=0; i<rsTree.getRow(); i++){
//                rsSubTree = controladorCategorias.subMenuTreePrincipal(rsTree.getString("MEN_ID"));
//                rsSubTree.first();
//                        
//                for(y=0; y<rsSubTree.getRow(); y++){
//                    System.err.println(rsTree.getString("MEN_ID"));
//
//                    rsSubTree2 = controladorCategorias.subMenuTreePrincipal(rsSubTree.getString("MEN_ID"));
//                    rsSubTree2.first();
//                    
//                    for(x=0; x<rsSubTree2.getRow(); x++){
//                        
//                        switch (rsTree.getString("MEN_ID")){
//                            case "1":
//                                menuItemConfiguracion = new JMenuItem(rsSubTree2.getString("MEN_NOMBRE"));
//                                menuItemConfiguracion.setOpaque(true);
//                                menuItemConfiguracion.setBackground(Color.WHITE);
//                                //SubMenus.setForeground(Color.decode(colorText));
//                        
//                                popupMenuConfiguracion.add(menuItemConfiguracion);
//                            
//                                break;
//                        }
//                        
//                        rsSubTree3 = controladorCategorias.subMenuTreePrincipal(rsSubTree2.getString("MEN_ID"));
//                        rsSubTree3.first();
//                        
//                        for(j=0; j<rsSubTree3.getRow(); j++){
//                            switch (rsSubTree2.getString("MEN_ID")){
//                                case "1":
//                                    menuItemConfiguracion = new JMenuItem(rsSubTree3.getString("MEN_NOMBRE"));
//                                    popupMenuConfiguracion.add(menuItemConfiguracion);
//
//                                    break;
//                            }
//                        
//                            rsSubTree4 = controladorCategorias.subMenuTreePrincipal(rsSubTree3.getString("MEN_ID"));
//                            rsSubTree4.first();
//                        
//                            for(k=0; k<rsSubTree4.getRow(); k++){
//                                switch (rsSubTree3.getString("MEN_ID")){
//                                    case "1":
//                                        menuItemConfiguracion = new JMenuItem(rsSubTree4.getString("MEN_NOMBRE"));
//                                        popupMenuConfiguracion.add(menuItemConfiguracion);
//
//                                        break;
//                                }
//
//                                rsSubTree4.next();
//                            }
//                            k=0;
//                            
//                            rsSubTree3.next();
//                        }
//                        j=0;
//                    
//                        rsSubTree2.next();
//                    }
//                    x=0;
//                
//                    rsSubTree.next();
//                }
//                y=0;
//                
//                rsTree.next();
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//    public void cargaMenuTree(){
//        rsTree = controladorCategorias.menuTreePrincipal();
//        jTreeMenuSistema.removeAll();
//        
//        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Menu Administrativo");
//        raiz.removeAllChildren();
//        DefaultTreeModel treeModel = new DefaultTreeModel(raiz);
//        treeModel.reload(raiz);
//        
//        jTreeMenuSistema.setCellRenderer(new MyTreeCellRenderer());
//    }
    
    public class MiRendererTree extends JLabel implements TreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            //imgIconos = new ImageIcon[30];
            //imgIconos[0] = new ImageIcon(getClass().getResource("/horizont/controlhoras/image/icon/clock.png"));

            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;
            String texto = (String) nodo.getUserObject();
            boolean seleccionado = selected;
            
            if (!seleccionado) {
                setForeground(Color.black);
            } else {
                setForeground(Color.BLUE);
            }

            if (texto.equals("Salir")) {
                setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_salir.png"));
            } else if (texto.equals("Productos")) {
                setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_cerrado.png"));
            }

            setText(texto);
            
            return (this);
        }
    }
    
    public class MyTreeCellRenderer extends DefaultTreeCellRenderer{
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
            //altura de cada nodo
            tree.setRowHeight(22);

            //setOpaque(true);     
            setBackground(Color.WHITE);
            
            //color de texto
            if(selected){
                //setForeground(Color.RED);
                setForeground(Color.black);
            }else{
                setForeground(Color.black);
            }
            
            //-- Asigna iconos
            // si value es la raiz
            if ( tree.getModel().getRoot().equals( (DefaultMutableTreeNode) value ) ) {
                setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_cerrado.png"));
                }
                else if (((DefaultMutableTreeNode) value).getUserObject().equals("Configuración")){
                    setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/config_menu.png"));
                    //setFont(new java.awt.Font("Tahoma", 1, 13)); 
                }
                    else if (((DefaultMutableTreeNode) value).getUserObject().equals("Maestros del Sistema")){
                        setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/config_menu_2.png"));
                    }
                    else if (((DefaultMutableTreeNode) value).getUserObject().equals("Usuarios y Seguridad")){
                        setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_padres.png"));
                    }
                        else if (((DefaultMutableTreeNode) value).getUserObject().equals("Usuarios")){
                            setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_usuarios.png"));
                        }
                        else if (((DefaultMutableTreeNode) value).getUserObject().equals("Configurar Permisologias")){
                            setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_padres.png"));
                        }
                        else if (((DefaultMutableTreeNode) value).getUserObject().equals("Grupos de Permisos")){
                            setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_grupo_permisoa.png"));
                        }
                        else if (((DefaultMutableTreeNode) value).getUserObject().equals("Seguridad")){
                            setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_permisos.png"));
                        }
                else if (((DefaultMutableTreeNode) value).getUserObject().equals("Inventario")){
                    setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_padres.png"));
                }
                    else if (((DefaultMutableTreeNode) value).getUserObject().equals("Productos y Servicios")){
                        setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_productos.png"));
                    }
                else if (((DefaultMutableTreeNode) value).getUserObject().equals("Ventas")){
                    setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_padres.png"));
                }
                else if (((DefaultMutableTreeNode) value).getUserObject().equals("Compras")){
                    setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_padres.png"));
                }
                else if (((DefaultMutableTreeNode) value).getUserObject().equals("Taller")){
                    setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/config_menu_4.png"));
                }
                    else if (((DefaultMutableTreeNode) value).getUserObject().equals("Maestros de Taller")){
                        setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_padres.png"));
                    }
                        else if (((DefaultMutableTreeNode) value).getUserObject().equals("Vehículos")){
                            setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/vehiculo_menu.png"));
                        }
                        else if (((DefaultMutableTreeNode) value).getUserObject().equals("Otros")){
                            setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_padres.png"));
                        }
                    else if (((DefaultMutableTreeNode) value).getUserObject().equals("Anulaciones")){
                        setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_padres.png"));
                    }
                else if (((DefaultMutableTreeNode) value).getUserObject().equals("Produccion")){
                    setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_padres.png"));
                }
                    else if (((DefaultMutableTreeNode) value).getUserObject().equals("Empresa")){
                        setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_empresas.png"));
                    }
                    else if (((DefaultMutableTreeNode) value).getUserObject().equals("Imp Exp")){
                        setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_padres.png"));
                    }
                        else if (((DefaultMutableTreeNode) value).getUserObject().equals("Importar Plan de Cuentas")){
                            setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_import_export_excel.png"));
                        }
                        else if (((DefaultMutableTreeNode) value).getUserObject().equals("Importar Productos")){
                            setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_import_export_excel.png"));
                        }
                else if (((DefaultMutableTreeNode) value).getUserObject().equals("Tesoreria")){
                    setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_padres.png"));
                }
                    else if (((DefaultMutableTreeNode) value).getUserObject().equals("Bancos e Instrumentos de Pago")){
                        setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_bancos_inst_pago.png"));
                    }
                else if (((DefaultMutableTreeNode) value).getUserObject().equals("Ayuda")){
                    setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_padres.png"));
                }
                else if (((DefaultMutableTreeNode) value).getUserObject().equals("Contabilidad")){
                    setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_padres.png"));
                }
                else if(((DefaultMutableTreeNode) value).getUserObject().equals("Salir")){
                    setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_salir_2.png"));
                }
                else if(((DefaultMutableTreeNode) value).getUserObject().equals("Actualizar el Sistema")){
                    setIcon(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/menu_update_sistem.png"));
                }
    
            return this;
        }
    }
    
    public void iniciarreloj(){
       // h1 = new Thread(this);
       // h1.start();
    }
        
    public void run() {
        Thread ct = Thread.currentThread();
        while (ct == h1) {
            calcula();
            lbHora.setText(hora + ":" + minutos + ":" + segundos + " " + ampm);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
    
    public void calcula() {
        Calendar calendario = new GregorianCalendar();
        Date fechaHoraActual = new Date();

        calendario.setTime(fechaHoraActual);
        ampm = calendario.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";

        if (ampm.equals("PM")) {
            int h = calendario.get(Calendar.HOUR_OF_DAY) - 12;
            hora = h > 9 ? "" + h : "0" + h;
        } else {
            hora = calendario.get(Calendar.HOUR_OF_DAY) > 9 ? "" + calendario.get(Calendar.HOUR_OF_DAY) : "0" + calendario.get(Calendar.HOUR_OF_DAY);
        }
        
        minutos = calendario.get(Calendar.MINUTE) > 9 ? "" + calendario.get(Calendar.MINUTE) : "0" + calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND) > 9 ? "" + calendario.get(Calendar.SECOND) : "0" + calendario.get(Calendar.SECOND);
    }
    
    public void CambioUsuario(){
        dispose();
        Bitacora insertar = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(),
                                         VarGlobales.getIdUser(), VarGlobales.getUserName(),
                                         "cerro sesión", "Usuario "+VarGlobales.getUserName().trim()+" ha cerrado sesión para cambio de Usuario");

        try {
            new Login().setVisible(true);
        } catch (MalformedURLException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jScrollBar1 = new javax.swing.JScrollBar();
        escritorioERP = new javax.swing.JDesktopPane();
        jLogoCli = new javax.swing.JLabel();
        lbHora = new javax.swing.JLabel();
        jLogoEmp = new javax.swing.JLabel();
        jLogoBD = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeMenuSistema = new javax.swing.JTree();
        lbStatus = new javax.swing.JLabel();
        lbStatusInternet = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnConfiguracion = new com.l2fprod.common.swing.JLinkButton();
        btnInventario = new com.l2fprod.common.swing.JLinkButton();
        btnVentas = new com.l2fprod.common.swing.JLinkButton();
        btnCompras = new com.l2fprod.common.swing.JLinkButton();
        btnTaller = new com.l2fprod.common.swing.JLinkButton();
        btnProduccion = new com.l2fprod.common.swing.JLinkButton();
        btnTesoreria = new com.l2fprod.common.swing.JLinkButton();
        btnContabilidad = new com.l2fprod.common.swing.JLinkButton();
        btnMaestros = new com.l2fprod.common.swing.JLinkButton();
        panelBtn = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        escritorioERP.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        escritorioERP.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        escritorioERP.setMaximumSize(new java.awt.Dimension(1341, 941));

        jLogoCli.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLogoCli.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lbHora.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        lbHora.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbHora.setText("jLabel1");
        lbHora.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLogoEmp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLogoEmp.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLogoBD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jTreeMenuSistema.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTreeMenuSistemaMouseClicked(evt);
            }
        });
        jTreeMenuSistema.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeMenuSistemaValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTreeMenuSistema);

        lbStatus.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbStatus.setText("Status Conexión:");

        lbStatusInternet.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbStatusInternet.setForeground(new java.awt.Color(255, 0, 0));
        lbStatusInternet.setText("OnLine");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(102, 102, 102), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        jPanel1.setPreferredSize(new java.awt.Dimension(789, 25));

        btnConfiguracion.setBackground(new java.awt.Color(105, 105, 105));
        btnConfiguracion.setText("Configuración");
        btnConfiguracion.setFocusable(false);
        btnConfiguracion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnConfiguracion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConfiguracion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnConfiguracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfiguracionActionPerformed(evt);
            }
        });

        btnInventario.setBackground(new java.awt.Color(105, 105, 105));
        btnInventario.setText("Inventario");
        btnInventario.setFocusable(false);
        btnInventario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnInventario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInventario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarioActionPerformed(evt);
            }
        });

        btnVentas.setBackground(new java.awt.Color(105, 105, 105));
        btnVentas.setText("Ventas");
        btnVentas.setFocusable(false);
        btnVentas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnVentas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVentas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });

        btnCompras.setBackground(new java.awt.Color(105, 105, 105));
        btnCompras.setText("Compras");
        btnCompras.setFocusable(false);
        btnCompras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCompras.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCompras.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprasActionPerformed(evt);
            }
        });

        btnTaller.setBackground(new java.awt.Color(105, 105, 105));
        btnTaller.setText("Taller");
        btnTaller.setFocusable(false);
        btnTaller.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnTaller.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTaller.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTaller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTallerActionPerformed(evt);
            }
        });

        btnProduccion.setBackground(new java.awt.Color(105, 105, 105));
        btnProduccion.setText("Producción");
        btnProduccion.setFocusable(false);
        btnProduccion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnProduccion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduccion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProduccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProduccionActionPerformed(evt);
            }
        });

        btnTesoreria.setBackground(new java.awt.Color(105, 105, 105));
        btnTesoreria.setText("Tesoreria");
        btnTesoreria.setFocusable(false);
        btnTesoreria.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnTesoreria.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTesoreria.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTesoreria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTesoreriaActionPerformed(evt);
            }
        });

        btnContabilidad.setBackground(new java.awt.Color(105, 105, 105));
        btnContabilidad.setText("Contabilidad");
        btnContabilidad.setFocusable(false);
        btnContabilidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnContabilidad.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnContabilidad.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnContabilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContabilidadActionPerformed(evt);
            }
        });

        btnMaestros.setBackground(new java.awt.Color(105, 105, 105));
        btnMaestros.setText("Maestros del Sistema");
        btnMaestros.setFocusable(false);
        btnMaestros.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMaestros.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMaestros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaestrosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnConfiguracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnInventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnCompras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnTaller, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnProduccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnTesoreria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnContabilidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMaestros, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(btnInventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCompras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnContabilidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTaller, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProduccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTesoreria, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMaestros, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        panelBtn.setBackground(new java.awt.Color(255, 255, 255));
        panelBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout panelBtnLayout = new javax.swing.GroupLayout(panelBtn);
        panelBtn.setLayout(panelBtnLayout);
        panelBtnLayout.setHorizontalGroup(
            panelBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelBtnLayout.setVerticalGroup(
            panelBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 84, Short.MAX_VALUE)
        );

        escritorioERP.setLayer(jLogoCli, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorioERP.setLayer(lbHora, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorioERP.setLayer(jLogoEmp, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorioERP.setLayer(jLogoBD, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorioERP.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorioERP.setLayer(lbStatus, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorioERP.setLayer(lbStatusInternet, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorioERP.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorioERP.setLayer(panelBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout escritorioERPLayout = new javax.swing.GroupLayout(escritorioERP);
        escritorioERP.setLayout(escritorioERPLayout);
        escritorioERPLayout.setHorizontalGroup(
            escritorioERPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 3323, Short.MAX_VALUE)
            .addGroup(escritorioERPLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLogoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(escritorioERPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLogoEmp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbHora, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbStatusInternet, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLogoBD, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2118, 2118, 2118))
            .addComponent(panelBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        escritorioERPLayout.setVerticalGroup(
            escritorioERPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(escritorioERPLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(escritorioERPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(escritorioERPLayout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addGroup(escritorioERPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(escritorioERPLayout.createSequentialGroup()
                                .addComponent(jLogoEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbHora, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLogoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(escritorioERPLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(escritorioERPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, escritorioERPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbStatus)
                                        .addComponent(lbStatusInternet))
                                    .addComponent(jLogoBD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorioERP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(escritorioERP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTreeMenuSistemaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeMenuSistemaMouseClicked
        String Nombre_Menu = NodoSeleccionado.getUserObject().toString();
        
        actionMenu(Nombre_Menu);
        
        Nombre_Menu = "";
    }//GEN-LAST:event_jTreeMenuSistemaMouseClicked

    private void jTreeMenuSistemaValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeMenuSistemaValueChanged
        TreePath path = evt.getPath();
        Object [] nodos = path.getPath();

        String parentPath = path.getParentPath().toString().replace("[", "").replace("]", "");

        String arrayCateg[] = parentPath.split(",");

        int count=0;
        for(String catPad:arrayCateg){
            count++;

            if(count==arrayCateg.length){
                categPad = catPad.trim();
            }
        }

        NodoSeleccionado = (DefaultMutableTreeNode) nodos[nodos.length-1];
    }//GEN-LAST:event_jTreeMenuSistemaValueChanged

    private void btnConfiguracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfiguracionActionPerformed
        //cargaMenuConfig();
        createSceneMenuConfig();
        panelBtn.setLayout(new BorderLayout());
        panelBtn.add(jfxPanel,BorderLayout.CENTER);
        
        //btnConfiguracion.setForeground(new java.awt.Color(255, 255, 255));
        btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
        btnInventario.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnInventario.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnInventario);
        
        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnVentas.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnVentas);
        
        btnCompras.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnCompras.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnCompras);
        
        btnTaller.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnTaller.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTaller);
        
        btnTesoreria.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnTesoreria.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTesoreria);
        
        btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnContabilidad.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnContabilidad);
        
        btnMaestros.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnMaestrosSitema.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnMaestrosSitema);

        lMenu1 = true;
    
        if (!lMenoShow){
            if (lMenoShow_2){
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtn);
                btnCbtesElectronicos.setVisible(true);
                
                lMenoShow_2 = false;
                panelBtn.setVisible(false);
            }else{
                panelBtn.setVisible(true);
                Animacion.Animacion.bajar(-204, 26, 12, 10, panelBtn);
                btnCbtesElectronicos.setVisible(false);
            
                lMenoShow = true;
            }
        }else{
            if (lMenu2 || lMenu3 || lMenu4 || lMenu5 || lMenu6 || lMenu7 || lMenu8 || lMenu9){
                panelBtn.setVisible(true);
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtn);
                Animacion.Animacion.bajar(-204, 26, 12, 10, panelBtn);
                btnCbtesElectronicos.setVisible(false);
                
                lMenu2 = false; lMenu3 = false; lMenu4 = false; lMenu5 = false; 
                lMenu6 = false; lMenu7 = false; lMenu8 = false; lMenu9 = false;
                
                lMenoShow_2 = true;
            }else{
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtn);
                lMenoShow = false; lMenoShow_2 = false;
                panelBtn.setVisible(false);
                btnCbtesElectronicos.setVisible(true);
            }
            
//            lMenoShow = false;
        }
    }//GEN-LAST:event_btnConfiguracionActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
//        if (!lContraerMenu){
//            Animacion.Animacion.subir(20, -204, 1, 15, panelBtn);
//            lContraerMenu = true;
//        }
        //panelBtn.setVisible(false);
    }//GEN-LAST:event_formWindowActivated

    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
        //cargaMenuInventario();
        creaSceneMenuInventario();
        panelBtnInventario.setLayout(new BorderLayout());
        panelBtnInventario.add(jfxPanelInventario,BorderLayout.CENTER);
        
        //btnInventario.setForeground(new java.awt.Color(255, 255, 255));
        btnInventario.setForeground(new java.awt.Color(0, 0, 0));
        btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
        panelBtn.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtn);
        
        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnVentas.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnVentas);
        
        btnCompras.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnCompras.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnCompras);
        
        btnTaller.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnTaller.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTaller);
        
        btnTesoreria.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnTesoreria.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTesoreria);
        
        btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnContabilidad.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnContabilidad);
        
        btnMaestros.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnMaestrosSitema.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnMaestrosSitema);

        lMenu2 = true;
    
        if (!lMenoShow){
            if (lMenoShow_2){
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnInventario);
                btnCbtesElectronicos.setVisible(true);
                
                lMenoShow_2 = false;
                panelBtnInventario.setVisible(false);
            }else{
                panelBtnInventario.setVisible(true);
                Animacion.Animacion.bajar(-204, 26, 12, 10, panelBtnInventario);
                btnCbtesElectronicos.setVisible(false);
            
                lMenoShow = true;
            }
        }else{
            if (lMenu1 || lMenu3 || lMenu4 || lMenu5 || lMenu6 || lMenu7 || lMenu8 || lMenu9){
                panelBtnInventario.setVisible(true);
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnInventario);
                Animacion.Animacion.bajar(-204, 26, 12, 10, panelBtnInventario);
                btnCbtesElectronicos.setVisible(false);
                
                lMenu1 = false; lMenu3 = false; lMenu4 = false; lMenu5 = false; 
                lMenu6 = false; lMenu7 = false; lMenu8 = false; lMenu9 = false;
                
                lMenoShow_2 = true;
            }else{
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnInventario);
                lMenoShow = false; lMenoShow_2 = false;
                panelBtnInventario.setVisible(false);
                btnCbtesElectronicos.setVisible(true);
            }
            
//            lMenoShow = false;
        }
    }//GEN-LAST:event_btnInventarioActionPerformed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        //cargaMenuVentas();
        crearSceneMenuVentas();
        panelBtnVentas.setLayout(new BorderLayout());
        panelBtnVentas.add(jfxPanelVentas,BorderLayout.CENTER);
        
        //btnVentas.setForeground(new java.awt.Color(255, 255, 255));
        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
        btnInventario.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnInventario.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnInventario);
        
        btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
        panelBtn.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtn);
        
        btnCompras.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnCompras.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnCompras);
        
        btnTaller.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnTaller.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTaller);
        
        btnTesoreria.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnTesoreria.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTesoreria);
        
        btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnContabilidad.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnContabilidad);
        
        btnMaestros.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnMaestrosSitema.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnMaestrosSitema);

        lMenu3 = true;
    
        if (!lMenoShow){
            if (lMenoShow_2){
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnVentas);
                btnCbtesElectronicos.setVisible(true);
                
                lMenoShow_2 = false;
                panelBtnVentas.setVisible(false);
            }else{
                panelBtnVentas.setVisible(true);
                Animacion.Animacion.bajar(-204, 26, 12, 10, panelBtnVentas);
                btnCbtesElectronicos.setVisible(false);
            
                lMenoShow = true;
            }
        }else{
            if (lMenu1 || lMenu2 || lMenu4 || lMenu5 || lMenu6 || lMenu7 || lMenu8 || lMenu9){
                panelBtnVentas.setVisible(true);
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnVentas);
                Animacion.Animacion.bajar(-204, 26, 12, 10, panelBtnVentas);
                btnCbtesElectronicos.setVisible(false);
                
                lMenu1 = false; lMenu2 = false; lMenu4 = false; lMenu5 = false; 
                lMenu6 = false; lMenu7 = false; lMenu8 = false; lMenu9 = false;
                
                lMenoShow_2 = true;
            }else{
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnVentas);
                lMenoShow = false; lMenoShow_2 = false;
                panelBtnVentas.setVisible(false);
                btnCbtesElectronicos.setVisible(true);
            }
            
//            lMenoShow = false;
        }
    }//GEN-LAST:event_btnVentasActionPerformed

    private void btnComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprasActionPerformed
        //cargaMenuCompras();
        crearSceneMenuCompras();
        panelBtnCompras.setLayout(new BorderLayout());
        panelBtnCompras.add(jfxPanelCompras,BorderLayout.CENTER);
        
        //btnCompras.setForeground(new java.awt.Color(255, 255, 255));
        btnCompras.setForeground(new java.awt.Color(0, 0, 0));
        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnVentas.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnVentas);
        
        btnInventario.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnInventario.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnInventario);
        
        btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
        panelBtn.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtn);
        
        btnTaller.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnTaller.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTaller);
        
        btnTesoreria.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnTesoreria.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTesoreria);
        
        btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnContabilidad.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnContabilidad);
        
        btnMaestros.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnMaestrosSitema.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnMaestrosSitema);

        lMenu4 = true;
    
        if (!lMenoShow){
            if (lMenoShow_2){
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnCompras);
                btnCbtesElectronicos.setVisible(true);
                
                lMenoShow_2 = false;
                panelBtnCompras.setVisible(false);
            }else{
                panelBtnCompras.setVisible(true);
                Animacion.Animacion.bajar(-204, 26, 12, 10, panelBtnCompras);
                btnCbtesElectronicos.setVisible(false);
            
                lMenoShow = true;
            }
        }else{
            if (lMenu1 || lMenu2 || lMenu3 || lMenu5 || lMenu6 || lMenu7 || lMenu8 || lMenu9){
                panelBtnCompras.setVisible(true);
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnCompras);
                Animacion.Animacion.bajar(-204, 26, 12, 10, panelBtnCompras);
                btnCbtesElectronicos.setVisible(false);
                
                lMenu1 = false; lMenu2 = false; lMenu3 = false; lMenu5 = false; 
                lMenu6 = false; lMenu7 = false; lMenu8 = false; lMenu9 = false;
                
                lMenoShow_2 = true;
            }else{
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnCompras);
                lMenoShow = false; lMenoShow_2 = false;
                panelBtnCompras.setVisible(false);
                btnCbtesElectronicos.setVisible(true);
            }
            
//            lMenoShow = false;
        }
    }//GEN-LAST:event_btnComprasActionPerformed

    private void btnTallerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTallerActionPerformed
        //cargaMenuTaller();
        crearSceneMenuTaller();
//        panelBtn.setLayout(new BorderLayout());
//        panelBtn.add(jfxPanel,BorderLayout.CENTER);
        panelBtnTaller.setLayout(new BorderLayout());
        panelBtnTaller.add(jfxPanelTaller,BorderLayout.CENTER);
        
        //btnTaller.setForeground(new java.awt.Color(255, 255, 255));
        btnTaller.setForeground(new java.awt.Color(0, 0, 0));
        btnCompras.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnCompras.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnCompras);
        
        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnVentas.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnVentas);
        
        btnInventario.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnInventario.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnInventario);
        
        btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
        panelBtn.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtn);

        btnTesoreria.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnTesoreria.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTesoreria);
        
        btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnContabilidad.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnContabilidad);
        
        btnMaestros.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnMaestrosSitema.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnMaestrosSitema);

        lMenu5 = true;
        
        panelBtn.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtn);
    
        if (!lMenoShow){
            if (lMenoShow_2){
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTaller);
                btnCbtesElectronicos.setVisible(true);
                
                lMenoShow_2 = false;
                panelBtnTaller.setVisible(false);
            }else{
                panelBtnTaller.setVisible(true);
                Animacion.Animacion.bajar(-204, 26, 12, 10, panelBtnTaller);
                btnCbtesElectronicos.setVisible(false);
            
                lMenoShow = true;
            }
        }else{
            if (lMenu1 || lMenu2 || lMenu3 || lMenu4 || lMenu6 || lMenu7 || lMenu8 || lMenu9){
                panelBtnTaller.setVisible(true);
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTaller);
                Animacion.Animacion.bajar(-204, 26, 12, 10, panelBtnTaller);
                btnCbtesElectronicos.setVisible(false);
                
                lMenu1 = false; lMenu2 = false; lMenu3 = false; lMenu4 = false; 
                lMenu6 = false; lMenu7 = false; lMenu8 = false; lMenu9 = false;
                
                lMenoShow_2 = true;
            }else{
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTaller);
                lMenoShow = false; lMenoShow_2 = false;
                panelBtnTaller.setVisible(false);
                btnCbtesElectronicos.setVisible(true);
            }
            
//            lMenoShow = false;
        }
    }//GEN-LAST:event_btnTallerActionPerformed

    private void btnMaestrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaestrosActionPerformed
        //cargaSubMenuMaestros();
        crearSceneMenuMaestros();
        panelBtnMaestrosSitema.setLayout(new BorderLayout());
        panelBtnMaestrosSitema.add(jfxPanelMaestrosSistema,BorderLayout.CENTER);
        
        //btnMaestros.setForeground(new java.awt.Color(255, 255, 255));
        btnMaestros.setForeground(new java.awt.Color(0, 0, 0));
        btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
        panelBtn.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtn);
        
        btnInventario.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnInventario.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnInventario);
        
        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnVentas.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnVentas);
        
        btnCompras.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnCompras.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnCompras);
        
        btnTaller.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnTaller.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTaller);
        
        btnTesoreria.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnTesoreria.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTesoreria);
        
        btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnContabilidad.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnContabilidad);

        lMenu9 = true;
    
        if (!lMenoShow){
            if (lMenoShow_2){
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnMaestrosSitema);
                btnMaestros.setForeground(new java.awt.Color(0, 0, 0));
                btnCbtesElectronicos.setVisible(true);
                
                lMenoShow_2 = false;
                panelBtnMaestrosSitema.setVisible(false);
            }else{
                panelBtnMaestrosSitema.setVisible(true);
                Animacion.Animacion.bajar(-204, 26, 12, 10, panelBtnMaestrosSitema);
                btnCbtesElectronicos.setVisible(false);
            
                lMenoShow = true;
            }
        }else{
            if (lMenu1 || lMenu2 || lMenu3 || lMenu4 || lMenu5 || lMenu6 || lMenu7 || lMenu8){
                panelBtnMaestrosSitema.setVisible(true);
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnMaestrosSitema);
                Animacion.Animacion.bajar(-204, 26, 12, 10, panelBtnMaestrosSitema);
                btnCbtesElectronicos.setVisible(false);
                
                lMenu1 = false; lMenu2 = false; lMenu3 = false; lMenu4 = false; 
                lMenu5 = false; lMenu6 = false; lMenu7 = false; lMenu8 = false;
                
                lMenoShow_2 = true;
            }else{
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnMaestrosSitema);
                btnMaestros.setForeground(new java.awt.Color(0, 0, 0));
                lMenoShow = false; lMenoShow_2 = false;
                panelBtnMaestrosSitema.setVisible(false);
                btnCbtesElectronicos.setVisible(true);
            }
            
//            lMenoShow = false;
        }
    }//GEN-LAST:event_btnMaestrosActionPerformed

    private void btnContabilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContabilidadActionPerformed
        //cargaMenuContabilidad();
        crearSceneMenuContabilidad();
        panelBtnContabilidad.setLayout(new BorderLayout());
        panelBtnContabilidad.add(jfxPanelContabilidad,BorderLayout.CENTER);
        
        //btnContabilidad.setForeground(new java.awt.Color(255, 255, 255));
        btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
        btnTaller.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnTaller.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTaller);
        
        btnCompras.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnCompras.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnCompras);
        
        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnVentas.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnVentas);
        
        btnInventario.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnInventario.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnInventario);
        
        btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
        panelBtn.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtn);
        
        btnTesoreria.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnTesoreria.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTesoreria);
        
        btnMaestros.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnMaestrosSitema.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnMaestrosSitema);

        lMenu8 = true;
    
        if (!lMenoShow){
            if (lMenoShow_2){
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnContabilidad);
                btnCbtesElectronicos.setVisible(true);
                
                lMenoShow_2 = false;
                panelBtnContabilidad.setVisible(false);
            }else{
                panelBtnContabilidad.setVisible(true);
                Animacion.Animacion.bajar(-204, 26, 12, 10, panelBtnContabilidad);
                btnCbtesElectronicos.setVisible(false);
            
                lMenoShow = true;
            }
        }else{
            if (lMenu1 || lMenu2 || lMenu3 || lMenu4 || lMenu6 || lMenu7 || lMenu5 || lMenu9){
                panelBtnContabilidad.setVisible(true);
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnContabilidad);
                Animacion.Animacion.bajar(-204, 26, 12, 10, panelBtnContabilidad);
                btnCbtesElectronicos.setVisible(false);
                
                lMenu1 = false; lMenu2 = false; lMenu3 = false; lMenu4 = false; 
                lMenu6 = false; lMenu7 = false; lMenu5 = false; lMenu9 = false;
                
                lMenoShow_2 = true;
            }else{
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnContabilidad);
                lMenoShow = false; lMenoShow_2 = false;
                panelBtnContabilidad.setVisible(false);
                btnCbtesElectronicos.setVisible(true);
            }
            
//            lMenoShow = false;
        }
    }//GEN-LAST:event_btnContabilidadActionPerformed

    private void btnProduccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProduccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnProduccionActionPerformed

    private void btnTesoreriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTesoreriaActionPerformed
        //cargaMenuConfig();
        createSceneMenuTesoreria();
        panelBtnTesoreria.setLayout(new BorderLayout());
        panelBtnTesoreria.add(jfxPanelTesoreria,BorderLayout.CENTER);
        
        //btnConfiguracion.setForeground(new java.awt.Color(255, 255, 255));
        btnTesoreria.setForeground(new java.awt.Color(0, 0, 0));
        
        btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
        panelBtn.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnInventario);
        
        btnInventario.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnInventario.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnInventario);
        
        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnVentas.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnVentas);
        
        btnCompras.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnCompras.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnCompras);
        
        btnTaller.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnTaller.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTaller);
        
        btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnContabilidad.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnContabilidad);
        
        btnMaestros.setForeground(new java.awt.Color(0, 0, 0));
        panelBtnMaestrosSitema.setVisible(false);
        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnMaestrosSitema);

        lMenu7 = true;
    
        if (!lMenoShow){
            if (lMenoShow_2){
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTesoreria);
                btnCbtesElectronicos.setVisible(true);
                
                lMenoShow_2 = false;
                panelBtnTesoreria.setVisible(false);
            }else{
                panelBtnTesoreria.setVisible(true);
                Animacion.Animacion.bajar(-204, 26, 12, 10, panelBtnTesoreria);
                btnCbtesElectronicos.setVisible(false);
            
                lMenoShow = true;
            }
        }else{
            if (lMenu2 || lMenu3 || lMenu4 || lMenu5 || lMenu6 || lMenu1 || lMenu8 || lMenu9){
                panelBtnTesoreria.setVisible(true);
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTesoreria);
                Animacion.Animacion.bajar(-204, 26, 12, 10, panelBtnTesoreria);
                btnCbtesElectronicos.setVisible(false);
                
                lMenu2 = false; lMenu3 = false; lMenu4 = false; lMenu5 = false; 
                lMenu6 = false; lMenu1 = false; lMenu8 = false; lMenu9 = false;
                
                lMenoShow_2 = true;
            }else{
                Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTesoreria);
                lMenoShow = false; lMenoShow_2 = false;
                panelBtnTesoreria.setVisible(false);
                btnCbtesElectronicos.setVisible(true);
            }
            
//            lMenoShow = false;
        }
    }//GEN-LAST:event_btnTesoreriaActionPerformed

    class TaskActionMenu extends SwingWorker<Void, String> {
        @Override
        public Void doInBackground() {
            switch(nombreMenu){
                case "Productos y Servicios":
                    productos();

                    break;
                case "Ajuste de Inventario":
                    ajusteInventario();

                    break;
                case "Importar Productos":
                    importProductos();

                    break;
                case "Marcas de Productos":
                    marcas();

                    break;
                case "Grupos":
                    categorias("Grupos");

                    break;
                case "SubGrupos":
                    categorias("SubGrupos");

                    break;
                case "Unidades de Medida":
                    unidadMedida();

                    break;
                case "Almacen":
                    almacen();

                    break;
                case "Usuarios":
                    usuarios();

                    break;
                case "Cambio de Usuario":
                    cambioUsuario();

                    break;
                case "Grupos de Permisos":
                    grupoPermisos();

                    break;
                case "Seguridad":
                    permisologias();

                    break;
                case "Usuarios por Empresas":
                    permisologiasGrupoEmpresas();

                    break;
                case "Clientes":
                    personas(2);

                    break;
                case "Proveedores":
                    personas(3);

                    break;
                case "Nota de Crédito":
                    if (origenDoc.equals("VTA")){
                        documentos("CRE", nombreMenu);
                    }

                    break;
                case "Nota de Débito":
                    if (origenDoc.equals("VTA")){
                        documentos("DEB", nombreMenu);
                    }

                    break;
                case "Cuentas por Cobrar":
                    cuentasCobrar();

                    break;
                case "Facturación en Ventas":
                    documentos("FAV", nombreMenu);

                    break;
                case "Devolución de Venta":
                    documentos("DEV", nombreMenu);

                    break;
                case "Facturación en Compras":
                    documentos("FAC", nombreMenu);

                    break;
                case "Devolución de Compra":
                    documentos("DVC", nombreMenu);

                    break;
                case "Recepción de Compras":
                    documentos("NRC", nombreMenu);

                    break;
                case "Orden de Compras":
                    documentos("ORC", nombreMenu);

                    break;
                case "Estructura de Ctas":
                    estructura();

                    break;
                case "Plan de Cuentas":
                    plancta();

                    break;
                case "Importar Plan de Cuentas":
                    importPlanCtas();

                    break;
                case "Grupos de Asientos":
                    gruposAsientos();

                    break;
                case "Configuracion de Asientos":
                    configAsientos();

                    break;
                case "Asientos Contables":
                    asientosContables();

                    break;
                case "Aprobacion de Comprobantes":
                    apruebaComprobantes();

                    break;
                case "Asientos Directos":
                    asientoManual();

                    break;
                case "Tipo Cliente":
                    tipoCliPro(8);

                    break;
                case "Tipo Proveedor":
                    tipoCliPro(9);

                    break;
                case "Tipo de Contactos":
                    tipocontacto();

                    break;
                case "Actividad":
                    actividad();

                    break;
                case "Moneda":
                    moneda();

                    break;
                case "Impuestos":
                    impuestos();

                    break;
                case "Precios":
                    precio();

                    break;
                case "Config. Import. Documentos":
                    configImport();

                    break;
                case "Documentos de Ventas":
                    configdoc("Ventas");

                    break;
                case "Documentos de Compras":
                    configdoc("Compras");

                    break;
                case "Cierre Contable":
                    cierrecontable();

                    break;
                case "Cierre Fiscal":
                    cierreFiscal();

                    break;
                case "Balance General":
                    reportContabilidad("Balance General");

                    break;
                case "Balance de Comprobacion":
                    reportContabilidad("Balance de Comprobacion");

                    break;
                case "Mayor Analitico":
                    reportContabilidad("Mayor Analitico");

                    break;
                case "Libro Diario":
                    reportContabilidad("Libro Diario");

                    break;
                case "Estado de Resultados":
                    reportContabilidad("Estado de Resultados");

                    break;
                case "Imprimir Cheques":
                    imprimirCheques();

                    break;
                case "Generar txt de Retencion de IVA":
                    generarTxt();

                    break;
                case "Calculo de Retenciones de IVA":
                    reporteRetIVAvta();

                    break;
                case "Generar xml de ISLR":
                    generarXml();

                    break;
                case "Imprimir ISLR por lote":
                    imprimirISLR();

                    break;
                case "Libro de Compras":
                    libro("COM");

                    break;
                case "Libro de Ventas":
                    libro("VTA");

                    break;
                case "Bancos":
                    bancos("1");

                    break;
                case "Instrumentos Bancarios":
                    bancos("2");

                    break;
                case "Empresa":
                    empresa();

                    break;
                case "Identificador de Equipos":
                    identificadorEquipos();

                    break;
                case "Punto de Venta":
                    pos();

                    break;
                case "Actualizar el Sistema":

                    break;
                case "Auditorias":
                    auditorias();

                    break;
                case "Vendedores":
                    vendedores();

                    break;
                case "Descuentos":
                    descuentos();

                    break;
                case "Técnicos":
                    tecnico();

                    break;    
                case "Marcas de Vehículos":
                    marcaVehiculo();

                    break;
                case "Modelos de Vehículos":
                    modelosVehiculo();

                    break;
                case "Categorias de Vehículos":
                    categoriasVehiculo();

                    break;
                case "Vehículos":
                    vehiculo();

                    break;  
                case "Motor":
                    motor();

                    break;
                case "Tipo de Motor":
                    tipoMotor();

                    break;
                case "Transmision":
                    transmision();

                    break;
                case "Tracción":
                    traccion();

                    break;
                case "Tipo de Cilindrada":
                    marcaCilindrada();

                    break;
                case "Modelos de Cilindrada":
                    modeloCilindrada();

                    break;
                case "Combustible":
                    combustible();

                    break;
                case "Orden de Reparacion (Presupuesto)":
                    ordenRepacion("POR", "Orden de Reparacion (Presupuesto)");

                    break;
                case "Orden de Reparación":
                    ordenRepacion("ORR", "Orden de Reparación");

                    break;
                case "Cambio de Placa":
                    cambioPlaca();

                    break;
                case "Anular Requisición de Productos":
                    anularRequisicionProductos();

                    break;
                case "Reverso de Despachos":
                    reversoDespachos();

                    break;
                case "Anular Carga de Productos":
                    anularCargaProductos();

                    break;
                case "Reapertura de Orden de Reparación":
                    reaperturaOrdenReparacion();

                    break;
                case "Configuración Rprt. predeterminados":
                    configReportes();

                    break;
                case "Salir":
                    salir();

                    break;
           }

           nombreMenu = "";
       
            return null;
        }
        
        @Override
        public void done() {
            
        }
    }
    
    private void actionMenu(String Nombre_Menu) {
        switch(Nombre_Menu){
            case "Productos y Servicios":
                productos();
                                
                break;
            case "Ajuste de Inventario":
                ajusteInventario();
                                
                break;
            case "Importar Productos":
                importProductos();
                                
                break;
            case "Marcas de Productos":
                marcas();
                
                break;
            case "Grupos":
                categorias("Grupos");
                
                break;
            case "SubGrupos":
                categorias("SubGrupos");
                
                break;
            case "Unidades de Medida":
                unidadMedida();
                
                break;
            case "Almacen":
                almacen();
                
                break;
            case "Usuarios":
                usuarios();
                                
                break;
            case "Cambio de Usuario":
                cambioUsuario();
                                
                break;
            case "Grupos de Permisos":
                grupoPermisos();
                                
                break;
            case "Seguridad":
                permisologias();
                                
                break;
            case "Usuarios por Empresas":
                permisologiasGrupoEmpresas();
                                
                break;
            case "Clientes":
                personas(2);
                                
                break;
            case "Proveedores":
                personas(3);
                                
                break;
            case "Nota de Crédito":
                if (origenDoc.equals("VTA")){
                    documentos("CRE", nombreMenu);
                }

                break;
            case "Nota de Débito":
                if (origenDoc.equals("VTA")){
                    documentos("DEB", nombreMenu);
                }

                break;
            case "Facturación en Ventas":
                documentos("FAV", Nombre_Menu);
                                
                break;
            case "Devolución de Venta":
                documentos("DEV", Nombre_Menu);
                                
                break;
            case "Facturación en Compras":
                documentos("FAC", Nombre_Menu);
                                
                break;
            case "Devolución de Compra":
                documentos("DVC", Nombre_Menu);
                                
                break;
            case "Recepción de Compras":
                documentos("NRC", Nombre_Menu);
                                
                break;
            case "Orden de Compras":
                documentos("ORC", Nombre_Menu);
                                
                break;
            case "Estructura de Ctas":
                estructura();
                                
                break;
            case "Plan de Cuentas":
                plancta();
                                
                break;
            case "Importar Plan de Cuentas":
                importPlanCtas();
                                
                break;
            case "Grupos de Asientos":
                gruposAsientos();
                                
                break;
            case "Configuracion de Asientos":
                configAsientos();
                                
                break;
            case "Asientos Contables":
                asientosContables();
                                
                break;
            case "Aprobacion de Comprobantes":
                apruebaComprobantes();
                                
                break;
            case "Asientos Directos":
                asientoManual();
                                
                break;
            case "Tipo Cliente":
                tipoCliPro(8);
                                
                break;
            case "Tipo Proveedor":
                tipoCliPro(9);
                                
                break;
            case "Tipo de Contactos":
                tipocontacto();
                                
                break;
            case "Actividad":
                actividad();
                                
                break;
            case "Moneda":
                moneda();
                                
                break;
            case "Impuestos":
                impuestos();
                                
                break;
            case "Precios":
                precio();
                                
                break;
            case "Config. Import. Documentos":
                configImport();
                
                break;
            case "Documentos de Ventas":
                configdoc("Ventas");
                                
                break;
            case "Documentos de Compras":
                configdoc("Compras");
                                
                break;
            case "Cierre Contable":
                cierrecontable();
                                
                break;
            case "Cierre Fiscal":
                cierreFiscal();
                                
                break;
            case "Balance General":
                reportContabilidad("Balance General");
                                
                break;
            case "Balance de Comprobacion":
                reportContabilidad("Balance de Comprobacion");
                                
                break;
            case "Mayor Analitico":
                reportContabilidad("Mayor Analitico");
                                
                break;
            case "Libro Diario":
                reportContabilidad("Libro Diario");
                                
                break;
            case "Estado de Resultados":
                reportContabilidad("Estado de Resultados");
                                
                break;
            case "Imprimir Cheques":
                imprimirCheques();
                                
                break;
            case "Generar txt de Retencion de IVA":
                generarTxt();
                                
                break;
            case "Calculo de Retenciones de IVA":
                reporteRetIVAvta();
                                
                break;
            case "Generar xml de ISLR":
                generarXml();
                                
                break;
            case "Imprimir ISLR por lote":
                imprimirISLR();
                                
                break;
            case "Libro de Compras":
                libro("COM");
                                
                break;
            case "Libro de Ventas":
                libro("VTA");
                                
                break;
            case "Instrumentos Bancarios":
                bancos("2");
                                
                break;
            case "Empresa":
                empresa();
                                
                break;
            case "Identificador de Equipos":
                identificadorEquipos();
                                
                break;
            case "Punto de Venta":
                pos();
                                
                break;
            case "Actualizar el Sistema":
                            
                break;
            case "Auditorias":
                auditorias();
                
                break;
            case "Vendedores":
                vendedores();

                break;
            case "Descuentos":
                descuentos();
                
                break;
            case "Técnicos":
                tecnico();
                
                break;    
            case "Marcas de Vehículos":
                marcaVehiculo();
                
                break;
            case "Modelos de Vehículos":
                modelosVehiculo();
                
                break;
            case "Categorias de Vehículos":
                categoriasVehiculo();
                
                break;
            case "Vehículos":
                vehiculo();
                
                break;  
            case "Motor":
                motor();
                
                break;
            case "Tipo de Motor":
                tipoMotor();
                
                break;
            case "Transmision":
                transmision();
                
                break;
            case "Tracción":
                traccion();
                
                break;
            case "Tipo de Cilindrada":
                marcaCilindrada();
                
                break;
            case "Modelos de Cilindrada":
                modeloCilindrada();
                
                break;
            case "Combustible":
                combustible();
                
                break;
            case "Orden de Reparacion (Presupuesto)":
                ordenRepacion("POR", "Orden de Reparacion (Presupuesto)");
                
                break;
            case "Orden de Reparación":
                ordenRepacion("ORR", "Orden de Reparación");
                
                break;
            case "Cambio de Placa":
                cambioPlaca();
                
                break;
            case "Anular Requisición de Productos":
                anularRequisicionProductos();
                
                break;
            case "Reverso de Despachos":
                reversoDespachos();
                
                break;
            case "Anular Carga de Productos":
                anularCargaProductos();
                
                break;
            case "Reapertura de Orden de Reparación":
                reaperturaOrdenReparacion();
                
                break;
            case "Salir":
                salir();
                                
                break;
            case "Cuentas por Cobrar":
                cuentasCobrar();
                
                break;
       }
        
       Nombre_Menu = "";
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }
    
// ***************************** Eventos del Menu *****************************
    private void usuarios(){
        CreaUsuarios crearUsuarios = new CreaUsuarios("ERP");

        Dimension desktopSize = escritorioERP.getSize();
        Dimension jInternalFrameSize = crearUsuarios.getSize();
        crearUsuarios.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

        escritorioERP.add(crearUsuarios);
        crearUsuarios.toFront();
        crearUsuarios.setVisible(true);
    }
    
    private void grupoPermisos(){
//        GruposPermisos gruposPermisos = new GruposPermisos("ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = gruposPermisos.getSize();
//        gruposPermisos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(gruposPermisos);
//        gruposPermisos.toFront();
//        gruposPermisos.setVisible(true);
    }
    
    private void permisologias(){
        Permisologias permisologias = new Permisologias("ERP");

        Dimension desktopSize = escritorioERP.getSize();
        Dimension jInternalFrameSize = permisologias.getSize();
        permisologias.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

        escritorioERP.add(permisologias);
        permisologias.toFront();
        permisologias.setVisible(true);
    }
    
    private void permisologiasGrupoEmpresas(){
        PermisologiaUsuarioEmpresas permisologiaUsuarioEmpresas = new PermisologiaUsuarioEmpresas("ERP");

        Dimension desktopSize = escritorioERP.getSize();
        Dimension jInternalFrameSize = permisologiaUsuarioEmpresas.getSize();
        permisologiaUsuarioEmpresas.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

        escritorioERP.add(permisologiaUsuarioEmpresas);
        permisologiaUsuarioEmpresas.toFront();
        permisologiaUsuarioEmpresas.setVisible(true);
    }
    
    private void marcas(){
//        Marcas marcas = new Marcas(false, "ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = marcas.getSize();
//        marcas.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(marcas);
//        marcas.toFront();
//        marcas.setVisible(true);
    }
    
    private void unidadMedida(){
//        UnidadMedida unidadMedida = new UnidadMedida("ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = unidadMedida.getSize();
//        unidadMedida.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(unidadMedida);
//        unidadMedida.toFront();
//        unidadMedida.setVisible(true);
    }
    
    private void almacen(){
//        UbicacionProductos ubicacionProductos = new UbicacionProductos(false, "ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = ubicacionProductos.getSize();
//        ubicacionProductos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(ubicacionProductos);
//        ubicacionProductos.toFront();
//        ubicacionProductos.setVisible(true);
    }
    
    private void categorias(String org){
//        Dimension desktopSize, jInternalFrameSize;
//        
//        Categorias categorias = new Categorias(true, "ERP", org);
//
////        desktopSize = escritorio.getSize();
//        desktopSize = escritorioERP.getSize();
//        jInternalFrameSize = categorias.getSize();
//        categorias.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
////        escritorio.add(categorias);
//        escritorioERP.add(categorias);
//        categorias.toFront();
//        categorias.setVisible(true);
    }
    
    private void bancos(String pestana){
//        BancosInsPagos bancos = new BancosInsPagos("ERP", pestana);
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = bancos.getSize();
//        bancos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(bancos);
//        bancos.toFront();
//        bancos.setVisible(true);
    }
    
    private void descuentos(){
//        DescuentosArticulos desc = new DescuentosArticulos("ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = desc.getSize();
//        desc.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(desc);
//        desc.toFront();
//        desc.setVisible(true);
    }
    
    private void configdoc(String org){
//        TipoDocumentos tipdoc = new TipoDocumentos("ERP", org);
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = tipdoc.getSize();
//        tipdoc.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(tipdoc);
//        tipdoc.toFront();
//        tipdoc.setVisible(true);
    }
    
    private void configImport(){
//        ConfigImport configImport = new ConfigImport();
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = configImport.getSize();
//        configImport.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(configImport);
//        configImport.toFront();
//        configImport.setVisible(true);
    }
    
    private void configasiento(){
//        ConfigAsientos config = new ConfigAsientos("ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = config.getSize();
//        config.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(config);
//        config.toFront();
//        config.setVisible(true);
    }
    
    private void productos(){
//        Productos pro = new Productos(false, "ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = pro.getSize();
//        pro.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(pro);
//        pro.toFront();
//        pro.setVisible(true);
//        
//        modelProductos.getTfCodigo().requestFocus();
    }
    
    private void ajusteInventario(){
//        Inventario inventario = new Inventario("ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = inventario.getSize();
//        inventario.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(inventario);
//        inventario.toFront();
//        inventario.setVisible(true);
    }
    
    private void importProductos(){
//        CargaMasivaProductos cargaMasivaProductos = new CargaMasivaProductos("Importar Productos");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = cargaMasivaProductos.getSize();
//        cargaMasivaProductos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(cargaMasivaProductos);
//        cargaMasivaProductos.toFront();
//        cargaMasivaProductos.setVisible(true);
    }
    
    private void estructura(){
//        EstructuraCtas estr = new EstructuraCtas();
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = estr.getSize();
//        estr.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(estr);
//        estr.toFront();
//        estr.setVisible(true);
    }
    
    private void plancta(){
//        PlanDeCuentas plan = new PlanDeCuentas();
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = plan.getSize();
//        plan.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(plan);
//        plan.toFront();
//        plan.setVisible(true);
    }
    
    private void importPlanCtas(){
//        ProgressImportCtas impCta = new ProgressImportCtas();
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = impCta.getSize();
//        impCta.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(impCta);
//        impCta.toFront();
//        impCta.setVisible(true);
    }
    
    private void configAsientos(){
//        ConfigAsientos configAsientos = new ConfigAsientos("ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = configAsientos.getSize();
//        configAsientos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(configAsientos);
//        configAsientos.toFront();
//        configAsientos.setVisible(true);
    }
    
    private void gruposAsientos(){
//        GrupoAsientos grupoAsientos = new GrupoAsientos();
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = grupoAsientos.getSize();
//        grupoAsientos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(grupoAsientos);
//        grupoAsientos.toFront();
//        grupoAsientos.setVisible(true);
    }
    
    private void asientosContables(){
        //AsientosContables asientosContables = new AsientosContables();

        //Dimension desktopSize = escritorioERP.getSize();
        //Dimension jInternalFrameSize = asientosContables.getSize();
        //asientosContables.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

        //escritorioERP.add(asientosContables);
        //asientosContables.toFront();
        //asientosContables.setVisible(true);
        
        JOptionPane.showMessageDialog(null, "En desarrollo pronto para Mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void apruebaComprobantes(){
//        AprobacionComprobates aprobacionComprobates = new AprobacionComprobates();
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = aprobacionComprobates.getSize();
//        aprobacionComprobates.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(aprobacionComprobates);
//        aprobacionComprobates.toFront();
//        aprobacionComprobates.setVisible(true);
    }
    
    private void asientoManual(){
//        AsientoManual asientoManual = new AsientoManual("ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = asientoManual.getSize();
//        asientoManual.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(asientoManual);
//        asientoManual.toFront();
//        asientoManual.setVisible(true);
    }
    
    private void tipoCliPro(int idRol){
//        TipoCliPro tipo = new TipoCliPro(idRol);
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = tipo.getSize();
//        tipo.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(tipo);
//        tipo.toFront();
//        tipo.setVisible(true);
    }
    
    private void precio(){
//        Precio precio = new Precio("ERP", "");
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = precio.getSize();
//        precio.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(precio);
//        precio.toFront();
//        precio.setVisible(true);
    }
    
    private void tipocontacto(){
//        TipoContacto tipo = new TipoContacto();
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = tipo.getSize();
//        tipo.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(tipo);
//        tipo.toFront();
//        tipo.setVisible(true);
//        
//        modelTipoContacto.getTxtDescri().requestFocus();
    }
    
    private void cierrecontable(){
//        CierreContable cierre = new CierreContable("ERP");
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = cierre.getSize();
//        cierre.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(cierre);
//        cierre.toFront();
//        cierre.setVisible(true);
    }
    
    private void cierreFiscal(){
//        CierreFiscal cierreFiscal = new CierreFiscal();
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = cierreFiscal.getSize();
//        cierreFiscal.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(cierreFiscal);
//        cierreFiscal.toFront();
//        cierreFiscal.setVisible(true);
    }
    
    private void pos(){
        menuERP = (MenuPrincipal) modelMenuERP.getVista();
        menuERP.dispose();
        
        ResultSet jornada = null;
        int hora, minutos, dia, result,apertura,confirm;
        String horaSis, diaSis = "";
        Calendar horario = new GregorianCalendar();
        
        hora = horario.get(Calendar.HOUR_OF_DAY);
        minutos = horario.get(Calendar.MINUTE);
        dia = horario.get(Calendar.DAY_OF_WEEK);
        
        horaSis = String.valueOf(hora)+":"+String.valueOf(minutos);
        
        if(dia==1){
            diaSis = "Domingo";
        }
        if(dia==2){
            diaSis = "Lunes";
        }
        if(dia==3){
            diaSis = "Martes";
        }
        if(dia==4){
            diaSis = "Miercoles";
        }
        if(dia==5){
            diaSis = "Jueves";
        }
        if(dia==6){
            diaSis = "Viernes";
        }
        if(dia==7){
            diaSis = "Sabado";
        }
        
        ValidarJornada valida = new ValidarJornada(horaSis, diaSis);
        result = valida.consulta();
        
        if(result==1){
            apertura = controladorAdministrador.validaApertura(diaSis);
            
            if(apertura == 1){
//                POS pos = new POS();
//                pos.show();
//                pos.setExtendedState(pos.MAXIMIZED_BOTH); // Para mostrer el Formulario Maximizado
//                pos.setVisible(true);
            }else{
               confirm = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgNoHayApert"),idioma.loadLangMsg().getString("MsgTituloNotif"),JOptionPane.YES_NO_OPTION);
                if(confirm==0){
//                    ConfigSuper supervisor = new ConfigSuper();
//                    supervisor.Verifica("EMP_APERT", "NoAsignaApertura");
                } 
            }
        }
    }
    
    private void reportContabilidad(String reporte){
//        ReporteContabilidad reporteContabilidad = null;
//        
//        switch(reporte){
//            case "Balance General":
//                reporteContabilidad = new ReporteContabilidad("Balance General", "Balance General");
//                
//                break;
//            case "Balance de Comprobacion":
//                reporteContabilidad = new ReporteContabilidad("Balance de Comprobacion", "Balance de Comprobacion");
//                
//                break;
//            case "Mayor Analitico":
//                reporteContabilidad = new ReporteContabilidad("Mayor Analitico", "Mayor Analitico");
//                
//                break;
//            case "Libro Diario":
//                reporteContabilidad = new ReporteContabilidad("Libro Diario", "Libro Diario");
//                
//                break;
//            case "Estado de Resultados":
//                reporteContabilidad = new ReporteContabilidad("Estado de Resultados", "Estado de Resultados");
//                
//                break;
//        }
        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = reporteContabilidad.getSize();
//        reporteContabilidad.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(reporteContabilidad);
//        reporteContabilidad.toFront();
//        reporteContabilidad.setVisible(true);
    }
    
    private void imprimirCheques(){
//        ImprimirCheque cheques = new ImprimirCheque("ERP");
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = cheques.getSize();
//        cheques.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(cheques);
//        cheques.toFront();
//        cheques.setVisible(true);
    }
    
    private void generarTxt(){
//        txtRetencionesIVA txtIva = new txtRetencionesIVA();
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = txtIva.getSize();
//        txtIva.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(txtIva);
//        txtIva.toFront();
//        txtIva.setVisible(true);
    }
    
    private void reporteRetIVAvta(){
//        ReporteRetIVAventas repVta = new ReporteRetIVAventas();
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = repVta.getSize();
//        repVta.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(repVta);
//        repVta.toFront();
//        repVta.setVisible(true);
    }
    
    private void generarXml(){
//        xmlISLR xmlIslr = new xmlISLR();
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = xmlIslr.getSize();
//        xmlIslr.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(xmlIslr);
//        xmlIslr.toFront();
//        xmlIslr.setVisible(true);
    }
    
    private void imprimirISLR(){
//        reporteISLRlote repIslr = new reporteISLRlote("ERP");
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = repIslr.getSize();
//        repIslr.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(repIslr);
//        repIslr.toFront();
//        repIslr.setVisible(true);
    }
    
    private void libro(String origen){
//        LibroComVta libro = new LibroComVta(origen);
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = libro.getSize();
//        libro.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(libro);
//        libro.toFront();
//        libro.setVisible(true);
    }
    
    private void empresa(){
        Empresas empresas = new Empresas(original);
        
        Dimension desktopSize = escritorioERP.getSize();
        Dimension jInternalFrameSize = empresas.getSize();
        empresas.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

        escritorioERP.add(empresas);
        empresas.toFront();
        empresas.setVisible(true);
    }
    
    private void identificadorEquipos(){
//        IdentificadorEquipos identificadorEquipos = new IdentificadorEquipos();
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = identificadorEquipos.getSize();
//        identificadorEquipos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(identificadorEquipos);
//        identificadorEquipos.toFront();
//        identificadorEquipos.setVisible(true);
    }
    
    private void moneda(){
//        Moneda moneda = new Moneda("", "");
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = moneda.getSize();
//        moneda.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(moneda);
//        moneda.toFront();
//        moneda.setVisible(true);
//        
//        modelMoneda.getTxtDescri().requestFocus();
    }
    
    private void impuestos(){
//        Impuestos impuestos = new Impuestos("ERP");
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = impuestos.getSize();
//        impuestos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(impuestos);
//        impuestos.toFront();
//        impuestos.setVisible(true);
    }
    
    private void importarplan(){
//        ProgressImportCtas impCta = new ProgressImportCtas();
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = impCta.getSize();
//        impCta.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(impCta);
//        impCta.toFront();
//        impCta.setVisible(true);
    }
    
    private void personas(int idRol){
        //Proveedor cli = new Proveedor("ERP");
//        Proveedores cli = new Proveedores(idRol, false);
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = cli.getSize();
//        cli.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(cli);
//        cli.toFront();
//        cli.setVisible(true);
    }
    
    private void actividad(){
//        Actividad actividad = new Actividad();
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = actividad.getSize();
//        actividad.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(actividad);
//        actividad.toFront();
//        actividad.setVisible(true);
    }
    
    private void cuentasCobrar(){
//        CuentasPorCobrarPagar cuentasPorCobarPagar = new CuentasPorCobrarPagar("CxC");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = cuentasPorCobarPagar.getSize();
//        cuentasPorCobarPagar.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(cuentasPorCobarPagar);
//        cuentasPorCobarPagar.toFront();
//        cuentasPorCobarPagar.setVisible(true);
    }
    
    private void documentos(String tipdoc, String titulo){
//        Documentos docs = new Documentos(tipdoc, titulo, "ERP");
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = docs.getSize();
//        docs.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(docs);
//        docs.toFront();
//        docs.setVisible(true);

        if(VarGlobales.getPais().equals("Costa Rica (es_CR)") && tipdoc.equals("NRC")){
//            RecepcionDocElectronicos recepcionDocElectronicos = new RecepcionDocElectronicos();
//        
//            Dimension desktopSize = escritorioERP.getSize();
//            Dimension jInternalFrameSize = recepcionDocElectronicos.getSize();
//            recepcionDocElectronicos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//            escritorioERP.add(recepcionDocElectronicos);
//            recepcionDocElectronicos.toFront();
//            recepcionDocElectronicos.setVisible(true);
        }else{
//            Facturacion docs = new Facturacion(tipdoc, titulo);
//        
//            Dimension desktopSize = escritorioERP.getSize();
//            Dimension jInternalFrameSize = docs.getSize();
//            docs.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//            escritorioERP.add(docs);
//            docs.toFront();
//            docs.setVisible(true);
        }
    }
 
    private void auditorias(){
//        Auditorias auditorias = new Auditorias("ERP");
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = auditorias.getSize();
//        auditorias.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(auditorias);
//        auditorias.toFront();
//        auditorias.setVisible(true);
    }
    
    private void vendedores(){
//        Vendedor vendedor = new Vendedor("ERP");
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = vendedor.getSize();
//        vendedor.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(vendedor);
//        vendedor.toFront();
//        vendedor.setVisible(true);
    }
    
    private void salir(){
        JInternalFrame[] array = escritorioERP.getAllFrames();
        int FormActivos = array.length;

        if (FormActivos>0){
            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgCloseForm"), 
                                                idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                                JOptionPane.WARNING_MESSAGE);
            return;
        }
        int salir = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgSalirSistema"),
                                                    idioma.loadLangMsg().getString("MsgNotificacion"),
                                                    JOptionPane.YES_NO_OPTION);

        if(salir == 0){
            System.exit(0);
        }
    }
    
    private void cambioUsuario(){
        JInternalFrame[] array = escritorioERP.getAllFrames();
        int FormActivos = array.length;

        if (FormActivos>0){
            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgCloseForm"), 
                                                idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        menuERP = (MenuPrincipal) modelMenuERP.getVista();
        menuERP.dispose();
        
        modelMenuERP.setlReloadMenu(false);

        try {
            new Vista.Login().setVisible(true);
        } catch (MalformedURLException ex) {
            Logger.getLogger(CargaMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void tecnico(){
//        Tecnico tecnico = new Tecnico();
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = tecnico.getSize();
//        tecnico.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(tecnico);
//        tecnico.toFront();
//        tecnico.setVisible(true);
    }
    
    private void marcaVehiculo(){
//        MarcasVehiculos marcasVehiculos = new MarcasVehiculos(false, "ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = marcasVehiculos.getSize();
//        marcasVehiculos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(marcasVehiculos);
//        marcasVehiculos.toFront();
//        marcasVehiculos.setVisible(true);
    }
    
    private void modelosVehiculo(){
//        ModeloVehiculo modeloVehiculo = new ModeloVehiculo(false, "ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = modeloVehiculo.getSize();
//        modeloVehiculo.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(modeloVehiculo);
//        modeloVehiculo.toFront();
//        modeloVehiculo.setVisible(true);
    }
    
    private void categoriasVehiculo(){
//        CategoriasVehiculos categoriasVehiculos = new CategoriasVehiculos(false, "ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = categoriasVehiculos.getSize();
//        categoriasVehiculos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(categoriasVehiculos);
//        categoriasVehiculos.toFront();
//        categoriasVehiculos.setVisible(true);
    }
    
    private void vehiculo(){
//        Vehiculo vehiculos = new Vehiculo(false, "Vehiculo");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = vehiculos.getSize();
//        vehiculos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(vehiculos);
//        vehiculos.toFront();
//        vehiculos.setVisible(true);
    }
    
    private void motor(){
//        Motor motor = new Motor(false, "ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = motor.getSize();
//        motor.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(motor);
//        motor.toFront();
//        motor.setVisible(true);
    }
    
    private void tipoMotor(){
//        TipoMotor tipoMotor = new TipoMotor(false, "ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = tipoMotor.getSize();
//        tipoMotor.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(tipoMotor);
//        tipoMotor.toFront();
//        tipoMotor.setVisible(true);
    }
    
    private void transmision(){
//        Transmision transmision = new Transmision(false, "ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = transmision.getSize();
//        transmision.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(transmision);
//        transmision.toFront();
//        transmision.setVisible(true);
    }
    
    private void traccion(){
//        Traccion traccion = new Traccion(false, "ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = traccion.getSize();
//        traccion.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(traccion);
//        traccion.toFront();
//        traccion.setVisible(true);
    }
    
    private void marcaCilindrada(){
//        TipoCilindrada tipoCilindrada = new TipoCilindrada(false, "ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = tipoCilindrada.getSize();
//        tipoCilindrada.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(tipoCilindrada);
//        tipoCilindrada.toFront();
//        tipoCilindrada.setVisible(true);
    }
    
    private void modeloCilindrada(){
//        ModeloCilindrada modeloCilindrada = new ModeloCilindrada(false, "ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = modeloCilindrada.getSize();
//        modeloCilindrada.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(modeloCilindrada);
//        modeloCilindrada.toFront();
//        modeloCilindrada.setVisible(true);
    }
    
    private void combustible(){
//        Combustible combustible = new Combustible(false, "ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = combustible.getSize();
//        combustible.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(combustible);
//        combustible.toFront();
//        combustible.setVisible(true);
    }
    
    private void ordenRepacionPresupuesto(){
        JOptionPane.showMessageDialog(null, "En desarrollo pronto para Mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void ordenRepacion(String tipdoc, String titulo){
//        OrdenReparacion ordenReparacion = new OrdenReparacion(tipdoc, titulo);
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = ordenReparacion.getSize();
//        ordenReparacion.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(ordenReparacion);
//        ordenReparacion.toFront();
//        ordenReparacion.setVisible(true);
    }
    
    private void cambioPlaca(){
//        Vehiculo vehiculos = new Vehiculo(false, "CambioPlaca");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = vehiculos.getSize();
//        vehiculos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(vehiculos);
//        vehiculos.toFront();
//        vehiculos.setVisible(true);
    }
    
    private void configReportes(){
//        ConfigReportesPredeterminados configReportesPredeterminados = new ConfigReportesPredeterminados();
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = configReportesPredeterminados.getSize();
//        configReportesPredeterminados.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(configReportesPredeterminados);
//        configReportesPredeterminados.toFront();
//        configReportesPredeterminados.setVisible(true);
    }
    
    private void reaperturaOrdenReparacion(){
        JOptionPane.showMessageDialog(null, "En desarrollo pronto para Mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void anularCargaProductos(){
        JOptionPane.showMessageDialog(null, "En desarrollo pronto para Mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void reversoDespachos(){
        JOptionPane.showMessageDialog(null, "En desarrollo pronto para Mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void anularRequisicionProductos(){
        JOptionPane.showMessageDialog(null, "En desarrollo pronto para Mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void evaluaConexion(){
        boolean ejecutar = true;
        
        new Thread(new Runnable() {
            public void run() {
                try {
                    while(ejecutar){
                        Thread.sleep(10000);
                        
                        if (VarGlobales.getConnectionStatus()){
                            lbStatusInternet.setForeground(new java.awt.Color(0, 255, 0));
                            lbStatusInternet.setText("OnLine");
                        }else{
                            lbStatusInternet.setForeground(new java.awt.Color(255, 0, 0));
                            lbStatusInternet.setText("OffLine");
                        }
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    private void evaluaEstadosCbtElectronicos(){
        boolean ejecutar = true;
        
        new Thread(new Runnable() {
            public void run() {
                int numProcesando = 0, numPendiete = 0;
                String msjProcesando = "", msjPendiente = "", saltoLinea = "";
                            
                try {
                    while(ejecutar){
                        Thread.sleep(10000);
                        
                        try {
                            ResultSet rsCbtProcPent = modelMenuERP.buscaCbtProcesando();
                            
                            if (rsCbtProcPent.getRow()>0){
                                lbRegistroComprobantes.setVisible(true);

                                rsCbtProcPent.beforeFirst();
                                numProcesando = 0; numPendiete = 0;

                                while(rsCbtProcPent.next()) {
                                    if (rsCbtProcPent.getString("ind_estado").toLowerCase().equals("procesando")){
                                        numProcesando++;
                                    }

                                    if (rsCbtProcPent.getString("ind_estado").toLowerCase().equals("pendiente")){
                                        numPendiete++;
                                    }
                                }

                                if (numProcesando>0){
                                    msjProcesando = "Tiene "+numProcesando+" comprobante(s) electronico(s) que estan con estado ''PROCESANDO''";
                                }

                                if (numPendiete>0){
                                    if (numProcesando>0){
                                        saltoLinea = "<br>";
                                    }
                                    msjPendiente = saltoLinea+"Tiene "+numPendiete+" comprobante(s) electronico(s) que estan con estado ''PENDIENTE'' y se requiere que se envie el XML al Ministerio de Hacienda";
                                }

                                lbRegistroComprobantes.setText("<html>"+msjProcesando+msjPendiente+"</html>");
                            }else{
                                lbRegistroComprobantes.setVisible(false);
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        if (lbRegistroComprobantes.isVisible()){
                            if (numProcesando>0 && numPendiete>0){
                                lbNumeroEquipo.setBounds(10,screenSize.height-125,550,21);
                                lbRegistroComprobantes.setBounds(10,screenSize.height-100,950,41);
                            }else{
                                lbNumeroEquipo.setBounds(10,screenSize.height-120,550,21);
                            }
                        }else{
                            lbNumeroEquipo.setBounds(10,screenSize.height-100,550,21);
                        }
                        lbNumeroEquipo.setText("<html>Equipo #: "+VarGlobales.getCodigoEquipo()+"</html>");
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    private void createSceneMenuConfig(){
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/bar_button_menu.fxml"));
            HBox root = null;
            VBox vBox = new VBox();
            JFXButton subMenu1 = new JFXButton(); JFXButton subMenu2 = new JFXButton();
            JFXButton subMenu3 = new JFXButton(); JFXButton subMenu4 = new JFXButton();
            JFXButton subMenu = new JFXButton();
            Separator separador = new Separator(); Separator separador1 = new Separator();
            Separator separador2 = new Separator(); Separator separador3 = new Separator();
            
            Label lbGrupoPermiso = new Label(); Label lbSeguridad = new Label(); Label lbUsuarioEmpresas = new Label();
            
            boolean lViewMenu1 = false, lViewMenu2 = false, lViewMenu3 = false, lViewMenu4 = false, lViewMenu5 = false,
                    lViewMenu6 = false, lViewMenu7 = false;
            
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            root.setSpacing(10);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(MenuPrincipal.class.getResource("/Vista/styleLogin.css").toExternalForm());

            DropShadow shadow = new DropShadow();
                
            try{
                rsSubTree2 = controladorCategorias.subMenuTreePrincipal("1");
                rsSubTree2.beforeFirst();

                while(rsSubTree2.next()) {
                    switch (rsSubTree2.getString("MEN_NOMBRE")){
                        case "Cambio de Usuario":        
                            Image image = new Image(getClass().getResourceAsStream("/imagenes/user_change_menu_bar_48.png"), widthImage, heightImage, true, true);
                            subMenu1.setGraphic(new ImageView(image));
                            //subMenu1.setMaxSize(95, 95);
                            subMenu1.setPrefSize(85, 95);
                            //subMenu1.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu1.setText("Cambio de\nUsuario");
                            subMenu1.setTextAlignment(TextAlignment.CENTER);
                            subMenu1.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu1.setContentDisplay(ContentDisplay.TOP);
                            //subMenu1.setEffect(shadow);
                            separador.setOrientation(Orientation.VERTICAL);
                            
                            EventHandler actionListener = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Cambio de Usuario");
                                nombreMenu = "Cambio de Usuario";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtn);
                            };
                            
                            subMenu1.setOnAction(actionListener);
                            
                            lViewMenu1 = true;

                            break;
                        case "Usuarios y Seguridad":
//                            Image image1 = new Image(getClass().getResourceAsStream("/imagenes/cliente_menu_bar_4.png"), widthImage, heightImage, true, true);
//                            subMenu2 = new JFXButton();
//                            subMenu2.setGraphic(new ImageView(image1));
//                            subMenu2.setPrefSize(85, 75);
//                            subMenu2.setText(rsSubTree2.getString("MEN_NOMBRE"));
//                            subMenu2.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
//                            subMenu2.setContentDisplay(ContentDisplay.TOP);
//                            //subMenu2.setEffect(shadow);
//                            separador1.setOrientation(Orientation.VERTICAL);
//                            
//                            EventHandler actionListener1 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
//                                crearSceneSubUsuarios();
//
//                                lSubMenu1 = true;
//                            };
//                            
//                            subMenu2.setOnAction(actionListener1);
//                            
//                            lViewMenu2 = true;

                            break;
                        case "Actualizar el Sistema":
                            Image image2 = new Image(getClass().getResourceAsStream("/imagenes/download server.png"), widthImage, heightImage, true, true);
                            subMenu3.setGraphic(new ImageView(image2));
                            subMenu3.setPrefSize(85, 95);
                            //subMenu3.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu3.setText("Actualizar el\nSistema");
                            subMenu3.setTextAlignment(TextAlignment.CENTER);
                            subMenu3.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu3.setContentDisplay(ContentDisplay.TOP);
                            //subMenu3.setEffect(shadow);
                            separador2.setOrientation(Orientation.VERTICAL);
                            
                            EventHandler actionListener2 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Actualizar el Sistema");
                                nombreMenu = "Actualizar el Sistema";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtn);
                            };
                            
                            subMenu3.setOnAction(actionListener2);
                            
                            lViewMenu3 = true;

                            break;
                        case "Reportes Usuarios":
                            Image image3 = new Image(getClass().getResourceAsStream("/imagenes/user_files_menu_bar_48.png"), widthImage, heightImage, true, true);
                            subMenu4.setGraphic(new ImageView(image3));
                            subMenu4.setPrefSize(130, 95);
                            //subMenu4.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            //subMenu4.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu4.setText("Configuración Rprt.\npredeterminados");
                            subMenu4.setTooltip(new Tooltip("Configuración Rprt.\n predeterminados"));
                            subMenu4.setTextAlignment(TextAlignment.CENTER);
                            subMenu4.setContentDisplay(ContentDisplay.TOP);
                            //subMenu4.setEffect(shadow);
                            separador3.setOrientation(Orientation.VERTICAL);
                            
                            EventHandler actionListener3 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Reportes Usuarios");
                                nombreMenu = "Configuración Rprt. predeterminados";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtn);
                            };
                            
                            subMenu4.setOnAction(actionListener3);
                            
                            lViewMenu4 = true;

                            break;
                    }
                }
                
                Image imageBack = new Image(getClass().getResourceAsStream("/imagenes/arrows_back_menu_bar_32.png"));
                ImageView imageViewBack = new ImageView(imageBack);
                imageViewBack.setFitWidth(15);
                imageViewBack.setFitHeight(30);
                subMenu.setGraphic(imageViewBack);
                subMenu.setPrefSize(10, 95);
                //subMenu.setContentDisplay(ContentDisplay.TOP);
                //subMenu.setEffect(shadow);

                EventHandler actionListener4 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                    if (lMenu1 && (lSubMenu1 || lSubMenu2)){
                        createSceneMenuConfig();
                        lSubMenu1 = false; lSubMenu2 = false;
                    }else{
                        btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
                        btnInventario.setForeground(new java.awt.Color(0, 0, 0));
                        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
                        btnCompras.setForeground(new java.awt.Color(0, 0, 0));
                        btnTaller.setForeground(new java.awt.Color(0, 0, 0));
                        btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
                        btnMaestros.setForeground(new java.awt.Color(0, 0, 0));

                        Animacion.Animacion.subir(20, -204, 12, 11, panelBtn);
                        btnCbtesElectronicos.setVisible(true);
                    }

                    lMenoShow = false;
                };

                subMenu.setOnAction(actionListener4);
                
                rsSubTree2 = controladorCategorias.subMenuTreePrincipal("10");
                rsSubTree2.beforeFirst();

                while(rsSubTree2.next()) {
                    switch (rsSubTree2.getString("MEN_NOMBRE")){
                        case "Usuarios":
                            //Image image = new Image(getClass().getResourceAsStream("/imagenes/usuarios_menu_bar.png"));
                            Image imag4 = new Image(getClass().getResourceAsStream("/imagenes/usuarios_menu_bar.png"), widthImage, heightImage, true, true);
                            subMenu2.setGraphic(new ImageView(imag4));
                            subMenu2.setPrefSize(95, 95);
                            subMenu2.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu2.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu2.setContentDisplay(ContentDisplay.TOP);
                            separador1.setOrientation(Orientation.VERTICAL);
                            //subMenu1.setEffect(shadow);
                            
                            EventHandler actionListener5 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Usuarios");
                                nombreMenu = "Usuarios";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtn);
                            };
                            
                            subMenu2.setOnAction(actionListener5);
                            
                            lViewMenu2 = true;

                            break;
                        case "Grupos de Permisos":
                            lbGrupoPermiso.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            Image imageLabel_1 = new Image(getClass().getResourceAsStream("/imagenes/menu_grupo_permisoa.png"));
                            lbGrupoPermiso.setGraphic(new ImageView(imageLabel_1));
                
                            lbGrupoPermiso.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            //subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            EventHandler actionListener6 = (EventHandler<javafx.scene.input.MouseEvent>) (javafx.scene.input.MouseEvent event) -> {
                                //actionMenu("Grupos de Permisos");
                                nombreMenu = "Grupos de Permisos";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtn);
                            };
                            
                            lbGrupoPermiso.setOnMouseClicked(actionListener6);
                            
                            lViewMenu5 = true;

                            break;
                        case "Seguridad":
                            lbSeguridad.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            Image imageLabel_2 = new Image(getClass().getResourceAsStream("/imagenes/menu_permisos.png"));
                            lbSeguridad.setGraphic(new ImageView(imageLabel_2));
                            lbSeguridad.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            //subMenu3.setContentDisplay(ContentDisplay.TOP);
                            
                            EventHandler actionListener7 = (EventHandler<javafx.scene.input.MouseEvent>) (javafx.scene.input.MouseEvent event) -> {
                                //actionMenu("Seguridad");
                                nombreMenu = "Seguridad";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtn);
                            };
                            
                            lbSeguridad.setOnMouseClicked(actionListener7);
                            
                            lViewMenu6 = true;

                            break;
                        case "Usuarios por Empresas":
                            lbUsuarioEmpresas.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            Image imageLabel_4 = new Image(getClass().getResourceAsStream("/imagenes/folder_user_icon_24.png"));
                            lbUsuarioEmpresas.setGraphic(new ImageView(imageLabel_4));
                            lbUsuarioEmpresas.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            //subMenu4.setContentDisplay(ContentDisplay.TOP);
                            //subMenu4.setEffect(shadow);
                            
                            EventHandler actionListener8 = (EventHandler<javafx.scene.input.MouseEvent>) (javafx.scene.input.MouseEvent event) -> {
                                //actionMenu("Usuarios por Empresas");
                                nombreMenu = "Usuarios por Empresas";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtn);
                            };
                            
                            lbUsuarioEmpresas.setOnMouseClicked(actionListener8);
                            
                            lViewMenu7 = true;

                            break;
                    }
                }

                //vBox.setSpacing(5);
                
                if (lViewMenu5) vBox.getChildren().add(lbGrupoPermiso);
                if (lViewMenu6) vBox.getChildren().add(lbSeguridad);
                if (lViewMenu7) vBox.getChildren().add(lbUsuarioEmpresas);
                        
                root.getChildren().add(subMenu);
                root.getChildren().add(separador);
                if (lViewMenu1) root.getChildren().add(subMenu1); root.getChildren().add(separador1);
                if (lViewMenu2) root.getChildren().add(subMenu2); root.getChildren().add(vBox); root.getChildren().add(separador2);
                if (lViewMenu3) root.getChildren().add(subMenu3); root.getChildren().add(separador3);
                if (lViewMenu4) root.getChildren().add(subMenu4); 
                
                jfxPanel.setScene(scene);
            } catch (SQLException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    rsSubTree2.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void creaSceneMenuInventario(){
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/bar_button_menu.fxml"));
            HBox root = null;
            JFXButton subMenu1 = new JFXButton(); JFXButton subMenu2 = new JFXButton();
            JFXButton subMenu3 = new JFXButton(); JFXButton subMenu4 = new JFXButton();
            JFXButton subMenu5 = new JFXButton(); JFXButton subMenu6 = new JFXButton();
            JFXButton subMenu7 = new JFXButton(); JFXButton subMenu8 = new JFXButton();
            JFXButton subMenu9 = new JFXButton(); JFXButton subMenu10 = new JFXButton();
            JFXButton subMenu = new JFXButton();
            
            boolean lViewMenu1 = false, lViewMenu2 = false, lViewMenu3 = false, lViewMenu4 = false, lViewMenu5 = false,
                    lViewMenu6 = false, lViewMenu7 = false, lViewMenu8 = false, lViewMenu9 = false, lViewMenu10 = false;
            
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            root.setSpacing(10);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(MenuPrincipal.class.getResource("/Vista/styleLogin.css").toExternalForm());

            DropShadow shadow = new DropShadow();
                
            try{
                rsSubTree2 = controladorCategorias.subMenuTreePrincipal("2");
                rsSubTree2.beforeFirst();

                while(rsSubTree2.next()) {
                    System.err.println(rsSubTree2.getString("MEN_NOMBRE"));
                    switch (rsSubTree2.getString("MEN_NOMBRE")){
                        case "Productos y Servicios":
                            Image image = new Image(getClass().getResourceAsStream("/imagenes/articulos_menu_bar.png"));
                            subMenu1.setGraphic(new ImageView(image));
                            subMenu1.setPrefSize(95, 95);
                            //subMenu1.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu1.setText("Productos y\nServicios");
                            subMenu1.setTextAlignment(TextAlignment.CENTER);
                            subMenu1.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu1.setContentDisplay(ContentDisplay.TOP);
                            //subMenu1.setEffect(shadow);
                            
                            EventHandler actionListener = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Productos y Servicios");
                                nombreMenu = "Productos y Servicios";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnInventario);
                            };
                            
                            subMenu1.setOnAction(actionListener);
                            
                            lViewMenu1 = true;

                            break;
                        case "Precios":
                            Image image1 = new Image(getClass().getResourceAsStream("/imagenes/price_menu_bar.png"));
                            subMenu2.setGraphic(new ImageView(image1));
                            subMenu2.setPrefSize(95, 95);
                            subMenu2.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu2.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            EventHandler actionListener1 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Precios");
                                nombreMenu = "Precios";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnInventario);
                            };
                            
                            subMenu2.setOnAction(actionListener1);
                            
                            lViewMenu2 = true;

                            break;
                        case "Marcas de Productos":
                            Image image2 = new Image(getClass().getResourceAsStream("/imagenes/marca_menu_bar.png"));
                            subMenu3.setGraphic(new ImageView(image2));
                            subMenu3.setPrefSize(95, 95);
                            //subMenu3.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu3.setText("Marcas de\nProductos");
                            subMenu3.setTextAlignment(TextAlignment.CENTER);
                            subMenu3.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu3.setContentDisplay(ContentDisplay.TOP);
                            //subMenu3.setEffect(shadow);
                            
                            EventHandler actionListener2 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Marcas de Productos");
                                nombreMenu = "Marcas de Productos";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnInventario);
                            };
                            
                            subMenu3.setOnAction(actionListener2);
                            
                            lViewMenu3 = true;

                            break;
                        case "Grupos":
                            Image image3 = new Image(getClass().getResourceAsStream("/imagenes/inventory_menu_bar_48.png"));
                            subMenu4.setGraphic(new ImageView(image3));
                            subMenu4.setPrefSize(95, 95);
                            subMenu4.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu4.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu4.setContentDisplay(ContentDisplay.TOP);
                            //subMenu4.setEffect(shadow);
                            
                            EventHandler actionListener3 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Grupos");
                                nombreMenu = "Grupos";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnInventario);
                            };
                            
                            subMenu4.setOnAction(actionListener3);
                            
                            lViewMenu4 = true;

                            break;
                        case "SubGrupos":
                            Image image4 = new Image(getClass().getResourceAsStream("/imagenes/inventory_menu_bar_48.png"));
                            subMenu5.setGraphic(new ImageView(image4));
                            subMenu5.setPrefSize(95, 95);
                            subMenu5.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu5.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu5.setContentDisplay(ContentDisplay.TOP);
                            //subMenu5.setEffect(shadow);
                            
                            EventHandler actionListener4 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("SubGrupos");
                                nombreMenu = "SubGrupos";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnInventario);
                            };
                            
                            subMenu5.setOnAction(actionListener4);
                            
                            lViewMenu5 = true;

                            break;
                        case "Unidades de Medida":
                            Image image5 = new Image(getClass().getResourceAsStream("/imagenes/unidad_medida_menu_bar.png"));
                            subMenu6.setGraphic(new ImageView(image5));
                            subMenu6.setPrefSize(95, 95);
                            //subMenu6.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu6.setText("Unidades de\nMedida");
                            subMenu6.setTextAlignment(TextAlignment.CENTER);
                            subMenu6.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu6.setContentDisplay(ContentDisplay.TOP);
                            //subMenu6.setEffect(shadow);
                            
                            EventHandler actionListener5 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Unidades de Medida");
                                nombreMenu = "Unidades de Medida";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnInventario);
                            };
                            
                            subMenu6.setOnAction(actionListener5);
                            
                            lViewMenu6 = true;

                            break;
                        case "Almacen":
                            Image image6 = new Image(getClass().getResourceAsStream("/imagenes/alamacen_menu_bar_2.png"));
                            subMenu7.setGraphic(new ImageView(image6));
                            subMenu7.setPrefSize(95, 95);
                            subMenu7.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu7.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu7.setContentDisplay(ContentDisplay.TOP);
                            //subMenu7.setEffect(shadow);
                            
                            EventHandler actionListener6 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Almacen");
                                nombreMenu = "Almacen";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnInventario);
                            };
                            
                            subMenu7.setOnAction(actionListener6);
                            
                            lViewMenu7 = true;

                            break;
                        case "Reportes de Inventario":
                            Image image7 = new Image(getClass().getResourceAsStream("/imagenes/report_menu_bar.png"));
                            subMenu8.setGraphic(new ImageView(image7));
                            subMenu8.setPrefSize(95, 95);
                            //subMenu8.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu8.setText("Reportes de\nInventario");
                            subMenu8.setTextAlignment(TextAlignment.CENTER);
                            subMenu8.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu8.setContentDisplay(ContentDisplay.TOP);
                            //subMenu8.setEffect(shadow);
                            
                            EventHandler actionListener7 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Reportes de Inventario");
                                nombreMenu = "Reportes de Inventario";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnInventario);
                            };
                            
                            subMenu8.setOnAction(actionListener7);
                            
                            lViewMenu8 = true;

                            break;
                        case "Importar Productos":
                            Image image8 = new Image(getClass().getResourceAsStream("/imagenes/blanco.png"));
                            subMenu9.setGraphic(new ImageView(image8));
                            subMenu9.setPrefSize(95, 95);
                            //subMenu9.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu9.setText("Importar\nProductos");
                            subMenu9.setTextAlignment(TextAlignment.CENTER);
                            subMenu9.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu9.setContentDisplay(ContentDisplay.TOP);
                            //subMenu9.setEffect(shadow);
                            
                            EventHandler actionListener8 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                nombreMenu = "Importar Productos";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnInventario);
                            };
                            
                            subMenu9.setOnAction(actionListener8);
                            
                            lViewMenu9 = true;

                            break;
                        case "Ajuste de Inventario":
                            Image image9 = new Image(getClass().getResourceAsStream("/imagenes/ajuste_inventario_menu_bar.png"));
                            subMenu10.setGraphic(new ImageView(image9));
                            subMenu10.setPrefSize(95, 95);
                            //subMenu10.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu10.setText("Ajuste de\nInventario");
                            subMenu10.setTextAlignment(TextAlignment.CENTER);
                            subMenu10.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu10.setContentDisplay(ContentDisplay.TOP);
                            //subMenu10.setEffect(shadow);
                            
                            EventHandler actionListener9 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Ajuste de Inventario");
                                nombreMenu = "Ajuste de Inventario";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnInventario);
                            };
                            
                            subMenu10.setOnAction(actionListener9);
                            
                            lViewMenu10 = true;

                            break;
                    }
                }
                
                Image imageBack = new Image(getClass().getResourceAsStream("/imagenes/arrows_back_menu_bar_32.png"));
                ImageView imageViewBack = new ImageView(imageBack);
                imageViewBack.setFitWidth(15);
                imageViewBack.setFitHeight(30);
                subMenu.setGraphic(imageViewBack);
                subMenu.setPrefSize(10, 95);
                //subMenu.setContentDisplay(ContentDisplay.TOP);
                //subMenu.setEffect(shadow);

                EventHandler actionListener10 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                    btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
                    btnInventario.setForeground(new java.awt.Color(0, 0, 0));
                    btnVentas.setForeground(new java.awt.Color(0, 0, 0));
                    btnCompras.setForeground(new java.awt.Color(0, 0, 0));
                    btnTaller.setForeground(new java.awt.Color(0, 0, 0));
                    btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
                    btnMaestros.setForeground(new java.awt.Color(0, 0, 0));

                    Animacion.Animacion.subir(20, -204, 12, 11, panelBtnInventario);
                    btnCbtesElectronicos.setVisible(true);

                    lMenoShow = false;
                };

                subMenu.setOnAction(actionListener10);
                            
                root.getChildren().add(subMenu);
                if (lViewMenu1) root.getChildren().add(subMenu1); 
                if (lViewMenu2) root.getChildren().add(subMenu2);
                if (lViewMenu3) root.getChildren().add(subMenu3); 
                if (lViewMenu4) root.getChildren().add(subMenu4);
                if (lViewMenu5) root.getChildren().add(subMenu5); 
                if (lViewMenu6) root.getChildren().add(subMenu6);
                if (lViewMenu7) root.getChildren().add(subMenu7); 
                if (lViewMenu8) root.getChildren().add(subMenu8);
                if (lViewMenu9) root.getChildren().add(subMenu9); 
                if (lViewMenu10) root.getChildren().add(subMenu10);
                
                jfxPanelInventario.setScene(scene);
            } catch (SQLException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    rsSubTree2.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void crearSceneMenuVentas(){
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/bar_button_menu.fxml"));
            HBox root = null;
            VBox vBoxAnulaciones = new VBox();
            JFXButton subMenu1 = new JFXButton(); JFXButton subMenu2 = new JFXButton();
            JFXButton subMenu3 = new JFXButton(); JFXButton subMenu4 = new JFXButton();
            JFXButton subMenu5 = new JFXButton(); JFXButton subMenu6 = new JFXButton();
            JFXButton subMenu7 = new JFXButton(); JFXButton subMenu8 = new JFXButton();
            JFXButton subMenu9 = new JFXButton(); JFXButton subMenu10 = new JFXButton();
            JFXButton subMenu = new JFXButton();
            
            Label lbNotaCredito = new Label();  Label lbNotaDebito = new Label(); 
            
            boolean lViewMenu1 = false, lViewMenu2 = false, lViewMenu3 = false, lViewMenu4 = false, lViewMenu5 = false,
                    lViewMenu6 = false, lViewMenu7 = false, lViewMenu8 = false, lViewMenu9 = false, lViewMenu10 = false;
            
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            root.setSpacing(10);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(MenuPrincipal.class.getResource("/Vista/styleLogin.css").toExternalForm());

            DropShadow shadow = new DropShadow();
                
            try{
                rsSubTree2 = controladorCategorias.subMenuTreePrincipal("3");
                rsSubTree2.beforeFirst();

                while(rsSubTree2.next()) {
                    System.err.println(rsSubTree2.getString("MEN_NOMBRE"));
                    switch (rsSubTree2.getString("MEN_NOMBRE")){
                        case "Clientes":
                            Image image = new Image(getClass().getResourceAsStream("/imagenes/cliente_menu_bar.png"));
                            subMenu1.setGraphic(new ImageView(image));
                            subMenu1.setPrefSize(95, 95);
                            subMenu1.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu1.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu1.setContentDisplay(ContentDisplay.TOP);
                            //subMenu1.setEffect(shadow);
                            
                            EventHandler actionListener = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Clientes");
                                nombreMenu = "Clientes";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnVentas);
                            };
                            
                            subMenu1.setOnAction(actionListener);
                            
                            lViewMenu1 = true;

                            break;
                        case "Documentos de Ventas":
                            Image image1 = new Image(getClass().getResourceAsStream("/imagenes/documents_type_menu_bar.png"));
                            subMenu2.setGraphic(new ImageView(image1));
                            subMenu2.setPrefSize(95, 95);
                            //subMenu2.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu2.setText("Documentos\nde Ventas");
                            subMenu2.setTextAlignment(TextAlignment.CENTER);
                            subMenu2.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            EventHandler actionListener1 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Documentos de Ventas");
                                nombreMenu = "Documentos de Ventas";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnVentas);
                            };
                            
                            subMenu2.setOnAction(actionListener1);
                            
                            lViewMenu2 = true;

                            break;
                        case "FacturaciÃ³n en Ventas":
                            Image image22 = new Image(getClass().getResourceAsStream("/imagenes/factura_electronica_bar_button_2.png"));
                            subMenu3.setGraphic(new ImageView(image22));
                            subMenu3.setPrefSize(95, 95);
                            //subMenu3.setText("Facturación en Ventas");
                            subMenu3.setText("Facturación\nen Ventas");
                            subMenu3.setTextAlignment(TextAlignment.CENTER);
                            subMenu3.setTooltip(new Tooltip("Facturación en Ventas"));
                            subMenu3.setContentDisplay(ContentDisplay.TOP);
                            //subMenu3.setEffect(shadow);
                            
                            EventHandler actionListener22 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Facturación en Ventas");
                                nombreMenu = "Facturación en Ventas";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnVentas);
                            };
                            
                            subMenu3.setOnAction(actionListener22);
                            
                            lViewMenu3 = true;
                            
                            break;
                        case "Facturación en Ventas":
                            Image image2 = new Image(getClass().getResourceAsStream("/imagenes/factura_electronica_bar_button_2.png"));
                            subMenu3.setGraphic(new ImageView(image2));
                            subMenu3.setPrefSize(95, 95);
                            //subMenu3.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu3.setText("Facturación\nen Ventas");
                            subMenu3.setTextAlignment(TextAlignment.CENTER);
                            subMenu3.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu3.setContentDisplay(ContentDisplay.TOP);
                            //subMenu3.setEffect(shadow);
                            
                            EventHandler actionListener2 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Facturación en Ventas");
                                nombreMenu = "Facturación en Ventas";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnVentas);
                            };
                            
                            subMenu3.setOnAction(actionListener2);
                            
                            lViewMenu3 = true;

                            break;
                        case "Pedido":
                            Image image3 = new Image(getClass().getResourceAsStream("/imagenes/pedido_menu_bar.png"));
                            subMenu4.setGraphic(new ImageView(image3));
                            subMenu4.setPrefSize(95, 95);
                            subMenu4.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu4.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu4.setContentDisplay(ContentDisplay.TOP);
                            //subMenu4.setEffect(shadow);
                            
                            EventHandler actionListener3 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Pedido");
                                nombreMenu = "Pedido";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnVentas);
                            };
                            
                            subMenu4.setOnAction(actionListener3);
                            
                            lViewMenu4 = true;

                            break;
                        case "Tipo Cliente":
                            Image image4 = new Image(getClass().getResourceAsStream("/imagenes/blanco.png"));
                            subMenu5.setGraphic(new ImageView(image4));
                            subMenu5.setPrefSize(95, 95);
                            subMenu5.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu5.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu5.setContentDisplay(ContentDisplay.TOP);
                            //subMenu5.setEffect(shadow);
                            
                            EventHandler actionListener4 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Tipo Cliente");
                                nombreMenu = "Tipo Cliente";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnVentas);
                            };
                            
                            subMenu5.setOnAction(actionListener4);
                            
                            lViewMenu5 = true;

                            break;
                        case "DevoluciÃ³n de Venta":
                            Image image55 = new Image(getClass().getResourceAsStream("/imagenes/devolucion_electronica_bar_button.png"));
                            subMenu6.setGraphic(new ImageView(image55));
                            subMenu6.setPrefSize(95, 95);
                            //subMenu6.setText("Devolución de Venta");
                            subMenu6.setText("Devolución\nde Venta");
                            subMenu6.setTextAlignment(TextAlignment.CENTER);
                            subMenu6.setTooltip(new Tooltip("Devolución de Venta"));
                            subMenu6.setContentDisplay(ContentDisplay.TOP);
                            //subMenu6.setEffect(shadow);
                            
                            EventHandler actionListener55 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Devolución de Venta");
                                nombreMenu = "Devolución de Venta";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnVentas);
                            };
                            
                            subMenu6.setOnAction(actionListener55);
                            
                            lViewMenu6 = true;

                            break;
                        case "Devolución de Venta":
                            Image image5 = new Image(getClass().getResourceAsStream("/imagenes/devolucion_electronica_bar_button.png"));
                            subMenu6.setGraphic(new ImageView(image5));
                            subMenu6.setPrefSize(95, 95);
                            //subMenu6.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu6.setText("Devolución\nde Venta");
                            subMenu6.setTextAlignment(TextAlignment.CENTER);
                            subMenu6.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu6.setContentDisplay(ContentDisplay.TOP);
                            //subMenu6.setEffect(shadow);
                            
                            EventHandler actionListener5 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Devolución de Venta");
                                nombreMenu = "Devolución de Venta";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnVentas);
                            };
                            
                            subMenu6.setOnAction(actionListener5);
                            
                            lViewMenu6 = true;

                            break;
                        case "Nota de Crédito / Debito":
//                            Image image6 = new Image(getClass().getResourceAsStream("/imagenes/nota_credito_debito_menu_bar.png"));
//                            subMenu7.setGraphic(new ImageView(image6));
//                            subMenu7.setPrefSize(95, 95);
//                            subMenu7.setText(rsSubTree2.getString("MEN_NOMBRE"));
//                            subMenu7.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
//                            subMenu7.setContentDisplay(ContentDisplay.TOP);
//                            //subMenu7.setEffect(shadow);
//                            
//                            EventHandler actionListener6 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
//                                //actionMenu("Nota de Crédito / Debito");
//                                nombreMenu = "Nota de Crédito / Debito";
//                                
//                                taskActionMenu = new TaskActionMenu();
//                                taskActionMenu.execute();
//                            };
                            
//                            subMenu7.setOnAction(actionListener6);
                            
//                            lViewMenu7 = true;        

                            break;
                        case "Cuentas por Cobrar":
                            Image image7 = new Image(getClass().getResourceAsStream("/imagenes/menu_bar_cxc.png"));
                            subMenu8.setGraphic(new ImageView(image7));
                            subMenu8.setPrefSize(95, 95);
                            //subMenu8.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu8.setText("Cuentas\npor Cobrar");
                            subMenu8.setTextAlignment(TextAlignment.CENTER);
                            subMenu8.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu8.setContentDisplay(ContentDisplay.TOP);
                            //subMenu8.setEffect(shadow);
                            
                            EventHandler actionListener7 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Cuentas por Cobrar");
                                nombreMenu = "Cuentas por Cobrar";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnVentas);
                            };
                            
                            subMenu8.setOnAction(actionListener7);
                            
                            lViewMenu8 = true;

                            break;
                        case "Presupuesto":
                            Image image8 = new Image(getClass().getResourceAsStream("/imagenes/presupuesto_menu_bar_2.png"));
                            subMenu9.setGraphic(new ImageView(image8));
                            subMenu9.setPrefSize(95, 95);
                            subMenu9.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu9.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu9.setContentDisplay(ContentDisplay.TOP);
                            //subMenu9.setEffect(shadow);
                            
                            EventHandler actionListener8 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Presupuesto");
                                nombreMenu = "Presupuesto";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnVentas);
                            };
                            
                            subMenu9.setOnAction(actionListener8);
                            
                            lViewMenu9 = true;

                            break;
                        case "Reportes de Ventas":
                            Image image9 = new Image(getClass().getResourceAsStream("/imagenes/report_menu_bar.png"));
                            subMenu10.setGraphic(new ImageView(image9));
                            subMenu10.setPrefSize(95, 95);
                            //subMenu10.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu10.setText("Reportes\nde Ventas");
                            subMenu10.setTextAlignment(TextAlignment.CENTER);
                            subMenu10.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu10.setContentDisplay(ContentDisplay.TOP);
                            //subMenu10.setEffect(shadow);
                            
                            ContextMenu contextMenuReportesVentas = new ContextMenu();
                            
                            MenuItem menuReporteFAV = new MenuItem("Factura de Ventas");
                            Image imageReporteFAV = new Image(getClass().getResourceAsStream("/imagenes/report_menu_bar_24.png"));
                            ImageView iconReporteFAV = new ImageView(imageReporteFAV);
                            menuReporteFAV.setGraphic(iconReporteFAV);
                            
                            EventHandler actionEventReporteFAV = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                llamaReportesFormFacturacion("FAV", "ReporteFacturacion");
                                
                                cerrarMenuVentas(panelBtnVentas);
                            };
                            
                            menuReporteFAV.setOnAction(actionEventReporteFAV);
                            
                            MenuItem menuReporteDEV = new MenuItem("Devolucion de Ventas");
                            Image imageReporteDEV = new Image(getClass().getResourceAsStream("/imagenes/report_menu_bar_24.png"));
                            ImageView iconReporteDEV = new ImageView(imageReporteDEV);
                            menuReporteDEV.setGraphic(iconReporteDEV);
                            
                            EventHandler actionEventReporteDEV = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                llamaReportesFormFacturacion("DEV", "ReporteFacturacion");
                                
                                cerrarMenuVentas(panelBtnVentas);
                            };
                            
                            menuReporteDEV.setOnAction(actionEventReporteDEV);
                            
                            Menu parentMenuCliente = new Menu("Clientes");
                            Image imageClientes = new Image(getClass().getResourceAsStream("/imagenes/reporte_cliente_proveedor_menu_bar_24.png"));
                            ImageView iconClientes = new ImageView(imageClientes);
                            parentMenuCliente.setGraphic(iconClientes);
                            
                                MenuItem menuReporteListaClientes = new MenuItem("Lista de Clientes");
                                Image imageReporteListaClientes = new Image(getClass().getResourceAsStream("/imagenes/reporte_cliente_proveedor_menu_bar_24.png"));
                                ImageView iconReporteListaClientes = new ImageView(imageReporteListaClientes);
                                menuReporteListaClientes.setGraphic(iconReporteListaClientes);
                            
                                EventHandler actionEventReporteListaClientes = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                    llamaReportesFormFacturacion("CLI", "ListaPersonas");
                                    
                                    cerrarMenuVentas(panelBtnVentas);
                                };
                            
                                menuReporteListaClientes.setOnAction(actionEventReporteListaClientes);
                            
                                MenuItem menuReporteFichaClientes = new MenuItem("Ficha del Clientes");
                                Image imageReporteFichaClientes = new Image(getClass().getResourceAsStream("/imagenes/reporte_cliente_proveedor_menu_bar_24.png"));
                                ImageView iconReporteFichaClientes = new ImageView(imageReporteFichaClientes);
                                menuReporteFichaClientes.setGraphic(iconReporteFichaClientes);
                            
                                EventHandler actionEventReporteFichaClientes = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                    llamaReportesFormFacturacion("CLI_FICHA", "FichaPersona");
                                    
                                    cerrarMenuVentas(panelBtnVentas);
                                };
                            
                                menuReporteFichaClientes.setOnAction(actionEventReporteFichaClientes);
                            
                            parentMenuCliente.getItems().addAll(menuReporteListaClientes, menuReporteFichaClientes);
                            
                            MenuItem menuReporteFAVVencidas = new MenuItem("Facturas Vencidas");
                            Image imageReporteFAVVencidas = new Image(getClass().getResourceAsStream("/imagenes/report_menu_bar_24.png"));
                            ImageView iconReporteFAVVencidas = new ImageView(imageReporteFAVVencidas);
                            menuReporteFAVVencidas.setGraphic(iconReporteFAVVencidas);
                            
                            EventHandler actionEventReporteFAVVencidas = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                llamaReportesFormFacturacion("VTAS", "ReporteFacturasVencidas");
                                
                                cerrarMenuVentas(panelBtnVentas);
                            };
                            
                            menuReporteFAVVencidas.setOnAction(actionEventReporteFAVVencidas);

                            Menu parentMenuCxC = new Menu("Cuetas por Cobrar");
                            Image imageCxC = new Image(getClass().getResourceAsStream("/imagenes/payment_24.png"));
                            ImageView iconCxC = new ImageView(imageCxC);
                            parentMenuCxC.setGraphic(iconCxC);
                            
                                MenuItem menuReporteCxC = new MenuItem("Documentos por Cobrar");
                                Image imageReporteCxC = new Image(getClass().getResourceAsStream("/imagenes/sales_menu_bar_24.png"));
                                ImageView iconReporteCxC = new ImageView(imageReporteCxC);
                                menuReporteCxC.setGraphic(iconReporteCxC);
                            
                                EventHandler actionEventReporteCxC = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                    llamaReportesFormFacturacion("CXC", "CuentasCobrar");
                                    
                                    cerrarMenuVentas(panelBtnVentas);
                                };
                            
                                menuReporteCxC.setOnAction(actionEventReporteCxC);
                            
                                MenuItem menuReportePagoAbono = new MenuItem("Pagos y Abonos de Cuentas por Cobrar");
                                Image imageReportePagoAbono = new Image(getClass().getResourceAsStream("/imagenes/sales_menu_bar_24.png"));
                                ImageView iconReportePagoAbono = new ImageView(imageReportePagoAbono);
                                menuReportePagoAbono.setGraphic(iconReportePagoAbono);
                            
                                EventHandler actionEventReportePagoAbono = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                    llamaReportesFormFacturacion("PagAbo", "PagosAbonos");
                                    
                                    cerrarMenuVentas(panelBtnVentas);
                                };
                            
                                menuReportePagoAbono.setOnAction(actionEventReportePagoAbono);
                            
                            parentMenuCxC.getItems().addAll(menuReporteCxC, menuReportePagoAbono);
                            
                            contextMenuReportesVentas.getItems().addAll(menuReporteFAV, new SeparatorMenuItem(),
                                                                        menuReporteDEV, new SeparatorMenuItem(),
                                                                        parentMenuCliente, new SeparatorMenuItem(),
                                                                        menuReporteFAVVencidas,  new SeparatorMenuItem(),
                                                                        parentMenuCxC);
                            
                            subMenu10.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
                                @Override
                                public void handle(MouseEvent event) {
                                    if (event.getButton() == MouseButton.PRIMARY){
                                        System.err.println(event.getScreenX()+" - "+event.getScreenY()+15);
                                        //contextMenuTaller.show(lbOtros, event.getScreenX(), event.getScreenY()+15);
                                        contextMenuReportesVentas.show(subMenu10, 990.0, 130.0);
                                    }
                                }
                            });
                            
                            
//                            EventHandler actionListener9 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
//                                actionMenu("Reportes de Ventas");
//                            };
//                            
//                            subMenu10.setOnAction(actionListener9);
                            
                            lViewMenu10 = true;

                            break;
                    }
                }
                
                Image imageBack = new Image(getClass().getResourceAsStream("/imagenes/arrows_back_menu_bar_32.png"));
                ImageView imageViewBack = new ImageView(imageBack);
                imageViewBack.setFitWidth(15);
                imageViewBack.setFitHeight(30);
                subMenu.setGraphic(imageViewBack);
                subMenu.setPrefSize(10, 95);
                //subMenu.setContentDisplay(ContentDisplay.TOP);
                //subMenu.setEffect(shadow);

                EventHandler actionListener10 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                    cerrarMenuVentas(panelBtnVentas);
                };

                subMenu.setOnAction(actionListener10);
                
                lbNotaCredito.setText("Nota de Crédito");
                Image imageLabel_1 = new Image(getClass().getResourceAsStream("/imagenes/nota_credito_debito_menu_bar_24.png"));
                lbNotaCredito.setGraphic(new ImageView(imageLabel_1));

                lbNotaCredito.setTooltip(new Tooltip("Nota de Crédito"));

                EventHandler actionListener6 = (EventHandler<javafx.scene.input.MouseEvent>) (javafx.scene.input.MouseEvent event) -> {
                    nombreMenu = "Nota de Crédito";
                    origenDoc = "VTA";

                    //actionMenu(nombreMenu);
                    taskActionMenu = new TaskActionMenu();
                    taskActionMenu.execute();
                    
                    cerrarMenuVentas(panelBtnVentas);
                };

                lbNotaCredito.setOnMouseClicked(actionListener6);           

                lbNotaDebito.setText("Nota de Débito");
                Image imageLabel_2 = new Image(getClass().getResourceAsStream("/imagenes/nota_credito_debito_menu_bar_2_24.png"));
                lbNotaDebito.setGraphic(new ImageView(imageLabel_2));

                lbNotaDebito.setTooltip(new Tooltip("Nota de Débito"));
                //subMenu2.setContentDisplay(ContentDisplay.TOP);
                //subMenu2.setEffect(shadow);

                EventHandler actionListenerND = (EventHandler<javafx.scene.input.MouseEvent>) (javafx.scene.input.MouseEvent event) -> {
                    nombreMenu = "Nota de Débito";
                    origenDoc = "VTA";

                    actionMenu(nombreMenu);
                    //taskActionMenu = new TaskActionMenu();
                    //taskActionMenu.execute();
                    
                    cerrarMenuVentas(panelBtnVentas);
                };

                lbNotaDebito.setOnMouseClicked(actionListenerND);

                lViewMenu7 = true;
                            
                if (lViewMenu7) vBoxAnulaciones.getChildren().add(lbNotaCredito);
                if (lViewMenu7) vBoxAnulaciones.getChildren().add(lbNotaDebito);
                            
                root.getChildren().add(subMenu);
                if (lViewMenu1) root.getChildren().add(subMenu1); 
                if (lViewMenu2) root.getChildren().add(subMenu2);
                if (lViewMenu3) root.getChildren().add(subMenu3); 
                if (lViewMenu4) root.getChildren().add(subMenu4);
                if (lViewMenu5) root.getChildren().add(subMenu5); 
                if (lViewMenu6) root.getChildren().add(subMenu6);
                //if (lViewMenu7) root.getChildren().add(subMenu7);
                if (lViewMenu7) root.getChildren().add(vBoxAnulaciones);
                //if (lViewMenu7) root.getChildren().add(subMenu7); root.getChildren().add(vBoxAnulaciones);
                if (lViewMenu8) root.getChildren().add(subMenu8);
                if (lViewMenu9) root.getChildren().add(subMenu9); 
                if (lViewMenu10) root.getChildren().add(subMenu10);
                
                jfxPanelVentas.setScene(scene);
            } catch (SQLException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    rsSubTree2.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void cerrarMenuVentas(JPanel panel){
        btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
        btnInventario.setForeground(new java.awt.Color(0, 0, 0));
        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
        btnCompras.setForeground(new java.awt.Color(0, 0, 0));
        btnTaller.setForeground(new java.awt.Color(0, 0, 0));
        btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
        btnMaestros.setForeground(new java.awt.Color(0, 0, 0));

        //Animacion.Animacion.subir(20, -204, 12, 11, panelBtnVentas);
        Animacion.Animacion.subir(20, -204, 12, 11, panel);
        btnCbtesElectronicos.setVisible(true);

        lMenoShow = false;
    }
    
    private void crearSceneMenuCompras(){
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/bar_button_menu.fxml"));
            HBox root = null;
            JFXButton subMenu1 = new JFXButton(); JFXButton subMenu2 = new JFXButton();
            JFXButton subMenu3 = new JFXButton(); JFXButton subMenu4 = new JFXButton();
            JFXButton subMenu5 = new JFXButton(); JFXButton subMenu6 = new JFXButton();
            JFXButton subMenu7 = new JFXButton(); JFXButton subMenu8 = new JFXButton();
            JFXButton subMenu9 = new JFXButton(); JFXButton subMenu10 = new JFXButton();
            JFXButton subMenu = new JFXButton();
            
            boolean lViewMenu1 = false, lViewMenu2 = false, lViewMenu3 = false, lViewMenu4 = false, lViewMenu5 = false,
                    lViewMenu6 = false, lViewMenu7 = false, lViewMenu8 = false, lViewMenu9 = false, lViewMenu10 = false;
            
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            root.setSpacing(10);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(MenuPrincipal.class.getResource("/Vista/styleLogin.css").toExternalForm());

            DropShadow shadow = new DropShadow();
                
            try{
                rsSubTree2 = controladorCategorias.subMenuTreePrincipal("4");
                rsSubTree2.beforeFirst();

                while(rsSubTree2.next()) {
                    System.err.println(rsSubTree2.getString("MEN_NOMBRE"));
                    switch (rsSubTree2.getString("MEN_NOMBRE")){
                        case "Proveedores":
                            Image image = new Image(getClass().getResourceAsStream("/imagenes/cliente_menu_bar.png"));
                            subMenu1.setGraphic(new ImageView(image));
                            subMenu1.setPrefSize(95, 95);
                            subMenu1.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu1.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu1.setContentDisplay(ContentDisplay.TOP);
                            //subMenu1.setEffect(shadow);
                            
                            EventHandler actionListener = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Proveedores");
                                nombreMenu = "Proveedores";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuCompras();
                            };
                            
                            subMenu1.setOnAction(actionListener);
                            
                            lViewMenu1 = true;

                            break;
                        case "Documentos de Compras":
                            Image image1 = new Image(getClass().getResourceAsStream("/imagenes/documents_type_menu_bar.png"));
                            subMenu2.setGraphic(new ImageView(image1));
                            subMenu2.setPrefSize(95, 95);
                            //subMenu2.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu2.setText("Documentos\nde Compras");
                            subMenu2.setTextAlignment(TextAlignment.CENTER);
                            subMenu2.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            EventHandler actionListener1 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Documentos de Compras");
                                nombreMenu = "Documentos de Compras";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuCompras();
                            };
                            
                            subMenu2.setOnAction(actionListener1);
                            
                            lViewMenu2 = true;

                            break;
                        case "Facturación en Compras":
                            Image image2 = new Image(getClass().getResourceAsStream("/imagenes/factura_electronica_bar_button_2.png"));
                            subMenu3.setGraphic(new ImageView(image2));
                            subMenu3.setPrefSize(95, 95);
                            //subMenu3.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu3.setText("Facturación\nen Compras");
                            subMenu3.setTextAlignment(TextAlignment.CENTER);
                            subMenu3.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu3.setContentDisplay(ContentDisplay.TOP);
//                            subMenu3.setEffect(shadow);
                            
                            EventHandler actionListener2 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Facturación en Compras");
                                nombreMenu = "Facturación en Compras";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuCompras();
                            };
                            
                            subMenu3.setOnAction(actionListener2);
                            
                            lViewMenu3 = true;

                            break;
                        case "FacturaciÃ³n en Compras":
                            Image image22 = new Image(getClass().getResourceAsStream("/imagenes/factura_electronica_bar_button_2.png"));
                            subMenu3.setGraphic(new ImageView(image22));
                            subMenu3.setPrefSize(95, 95);
                            //subMenu3.setText("Facturación en Compras");
                            subMenu3.setText("Facturación\nen Compras");
                            subMenu3.setTextAlignment(TextAlignment.CENTER);
                            subMenu3.setTooltip(new Tooltip("Facturación en Compras"));
                            subMenu3.setContentDisplay(ContentDisplay.TOP);
//                            subMenu3.setEffect(shadow);
                            
                            EventHandler actionListener22 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Facturación en Compras");
                                nombreMenu = "Facturación en Compras";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuCompras();
                            };
                            
                            subMenu3.setOnAction(actionListener22);
                            
                            lViewMenu3 = true;

                            break;
                        case "DevoluciÃ³n de Compra":
                            Image image33 = new Image(getClass().getResourceAsStream("/imagenes/devolucion_electronica_bar_button.png"));
                            subMenu4.setGraphic(new ImageView(image33));
                            subMenu4.setPrefSize(95, 95);
                            subMenu4.setText("Devolución\nde Compra");
                            subMenu4.setTextAlignment(TextAlignment.CENTER);
                            subMenu4.setTooltip(new Tooltip("Devolución de Compra"));
                            subMenu4.setContentDisplay(ContentDisplay.TOP);
//                            subMenu4.setEffect(shadow);
                            
                            EventHandler actionListener33 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Devolución de Compra");
                                nombreMenu = "Devolución de Compra";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuCompras();
                            };
                            
                            subMenu4.setOnAction(actionListener33);
                            
                            lViewMenu4 = true;

                            break;
                        case "Devolución de Compra":
                            Image image3 = new Image(getClass().getResourceAsStream("/imagenes/devolucion_electronica_bar_button.png"));
                            subMenu4.setGraphic(new ImageView(image3));
                            subMenu4.setPrefSize(95, 95);
                            //subMenu4.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu4.setText("Devolución\nde Compra");
                            subMenu4.setTextAlignment(TextAlignment.CENTER);
                            subMenu4.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu4.setContentDisplay(ContentDisplay.TOP);
//                            subMenu4.setEffect(shadow);
                            
                            EventHandler actionListener3 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Devolución de Compra");
                                nombreMenu = "Devolución de Compra";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuCompras();
                            };
                            
                            subMenu4.setOnAction(actionListener3);
                            
                            lViewMenu4 = true;

                            break;
                        case "RecepciÃ³n de Compras":
                            Image image44 = new Image(getClass().getResourceAsStream("/imagenes/recepcion_compra_menu.png"));
                            subMenu5.setGraphic(new ImageView(image44));
                            subMenu5.setPrefSize(95, 95);
                            subMenu5.setText("Recepción\nde Compras");
                            subMenu5.setTextAlignment(TextAlignment.CENTER);
                            subMenu5.setTooltip(new Tooltip("Recepción de Compras"));
                            subMenu5.setContentDisplay(ContentDisplay.TOP);
//                            subMenu5.setEffect(shadow);
                            
                            EventHandler actionListener44 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Recepción de Compras");
                                nombreMenu = "Recepción de Compras";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuCompras();
                            };
                            
                            subMenu5.setOnAction(actionListener44);
                            
                            lViewMenu5 = true;

                            break;
                        case "Recepción de Compras":
                            Image image4 = new Image(getClass().getResourceAsStream("/imagenes/recepcion_compra_menu.png"));
                            subMenu5.setGraphic(new ImageView(image4));
                            subMenu5.setPrefSize(95, 95);
                            //subMenu5.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu5.setText("Recepción\nde Compras");
                            subMenu5.setTextAlignment(TextAlignment.CENTER);
                            subMenu5.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu5.setContentDisplay(ContentDisplay.TOP);
//                            subMenu5.setEffect(shadow);
                            
                            EventHandler actionListener4 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Recepción de Compras");
                                nombreMenu = "Recepción de Compras";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuCompras();
                            };
                            
                            subMenu5.setOnAction(actionListener4);
                            
                            lViewMenu5 = true;

                            break;
                        case "Orden de Compras":
                            Image image5 = new Image(getClass().getResourceAsStream("/imagenes/pedido_menu_bar.png"));
                            subMenu6.setGraphic(new ImageView(image5));
                            subMenu6.setPrefSize(95, 95);
                            //subMenu6.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu6.setText("Orden de\nCompras");
                            subMenu6.setTextAlignment(TextAlignment.CENTER);
                            subMenu6.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu6.setContentDisplay(ContentDisplay.TOP);
//                            subMenu6.setEffect(shadow);
                            
                            EventHandler actionListener5 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Orden de Compras");
                                nombreMenu = "Orden de Compras";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuCompras();
                            };
                            
                            subMenu6.setOnAction(actionListener5);
                            
                            lViewMenu6 = true;

                            break;
                        case "Cuentas por Pagar":
                            Image image6 = new Image(getClass().getResourceAsStream("/imagenes/blanco.png"));
                            subMenu7.setGraphic(new ImageView(image6));
                            subMenu7.setPrefSize(95, 95);
                            subMenu7.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu7.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu7.setContentDisplay(ContentDisplay.TOP);
//                            subMenu7.setEffect(shadow);
                            
                            EventHandler actionListener6 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Cuentas por Pagar");
                                nombreMenu = "Cuentas por Pagar";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuCompras();
                            };
                            
                            subMenu7.setOnAction(actionListener6);
                            
                            lViewMenu7 = true;

                            break;
                        case "Tipo Proveedor":
                            Image image7 = new Image(getClass().getResourceAsStream("/imagenes/blanco.png"));
                            subMenu8.setGraphic(new ImageView(image7));
                            subMenu8.setPrefSize(95, 95);
                            //subMenu8.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu8.setText("Tipo\nProveedor");
                            subMenu8.setTextAlignment(TextAlignment.CENTER);
                            subMenu8.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu8.setContentDisplay(ContentDisplay.TOP);
//                            subMenu8.setEffect(shadow);
                            
                            EventHandler actionListener7 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Tipo Proveedor");
                                nombreMenu = "Tipo Proveedor";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuCompras();
                            };
                            
                            subMenu8.setOnAction(actionListener7);
                            
                            lViewMenu8 = true;

                            break;
                        case "Retenciones":
                            Image image8 = new Image(getClass().getResourceAsStream("/imagenes/blanco.png"));
                            subMenu9.setGraphic(new ImageView(image8));
                            subMenu9.setPrefSize(95, 95);
                            subMenu9.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu9.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu9.setContentDisplay(ContentDisplay.TOP);
//                            subMenu9.setEffect(shadow);
                            
                            EventHandler actionListener8 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Retenciones");
                                nombreMenu = "Retenciones";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuCompras();
                            };
                            
                            subMenu9.setOnAction(actionListener8);
                            
                            lViewMenu9 = true;

                            break;
                        case "Reportes de Compras":
                            Image image9 = new Image(getClass().getResourceAsStream("/imagenes/report_menu_bar.png"));
                            subMenu10.setGraphic(new ImageView(image9));
                            subMenu10.setPrefSize(95, 95);
                            //subMenu10.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu10.setText("Reportes\nde Compras");
                            subMenu10.setTextAlignment(TextAlignment.CENTER);
                            subMenu10.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu10.setContentDisplay(ContentDisplay.TOP);
                            //subMenu10.setEffect(shadow);
                            
                            ContextMenu contextMenuReportesCompras = new ContextMenu();
                            
                            MenuItem menuReporteFAC = new MenuItem("Factura de Compras");
                            Image imageReporteFAC = new Image(getClass().getResourceAsStream("/imagenes/report_menu_bar_24.png"));
                            ImageView iconReporteFAC = new ImageView(imageReporteFAC);
                            menuReporteFAC.setGraphic(iconReporteFAC);
                            
                            EventHandler actionEventReporteFAC = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                llamaReportesFormFacturacion("FAC", "ReporteFacturacion");
                                
                                cerrarMenuCompras();
                            };
                            
                            menuReporteFAC.setOnAction(actionEventReporteFAC);
                            
                            MenuItem menuReporteDVC = new MenuItem("Devolucion de Compras");
                            Image imageTipoCilindrada = new Image(getClass().getResourceAsStream("/imagenes/report_menu_bar_24.png"));
                            ImageView iconTipoCilindrada = new ImageView(imageTipoCilindrada);
                            menuReporteDVC.setGraphic(iconTipoCilindrada);
                            
                            EventHandler actionEventReporteDVC = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                llamaReportesFormFacturacion("DVC", "ReporteFacturacion");
                                
                                cerrarMenuCompras();
                            };
                            
                            menuReporteDVC.setOnAction(actionEventReporteDVC);
                            
                            Menu parentMenuProveedor = new Menu("Proveedores");
                            Image imageProveedor = new Image(getClass().getResourceAsStream("/imagenes/reporte_cliente_proveedor_menu_bar_24.png"));
                            ImageView iconProveedor = new ImageView(imageProveedor);
                            parentMenuProveedor.setGraphic(iconProveedor);
                            
                                MenuItem menuReporteListaProveedores = new MenuItem("Lista de Proveedores");
                                Image imageReporteListaProveedores = new Image(getClass().getResourceAsStream("/imagenes/reporte_cliente_proveedor_menu_bar_24.png"));
                                ImageView iconReporteListaProveedores = new ImageView(imageReporteListaProveedores);
                                menuReporteListaProveedores.setGraphic(iconReporteListaProveedores);
                            
                                EventHandler actionEventReporteListaProveedores = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                    llamaReportesFormFacturacion("PRO", "ListaPersonas");
                                    
                                    cerrarMenuCompras();
                                };
                            
                                menuReporteListaProveedores.setOnAction(actionEventReporteListaProveedores);
                            
                                MenuItem menuReporteFichaProveedores = new MenuItem("Ficha del Clientes");
                                Image imageReporteFichaProveedores = new Image(getClass().getResourceAsStream("/imagenes/reporte_cliente_proveedor_menu_bar_24.png"));
                                ImageView iconReporteFichaProveedores = new ImageView(imageReporteFichaProveedores);
                                menuReporteFichaProveedores.setGraphic(iconReporteFichaProveedores);
                            
                                EventHandler actionEventReporteFichaProveedores = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                    llamaReportesFormFacturacion("CLI_FICHA", "FichaPersona");
                                    
                                    cerrarMenuCompras();
                                };
                            
                                menuReporteFichaProveedores.setOnAction(actionEventReporteFichaProveedores);
                            
                            parentMenuProveedor.getItems().addAll(menuReporteListaProveedores, menuReporteFichaProveedores);

                            contextMenuReportesCompras.getItems().addAll(menuReporteFAC, new SeparatorMenuItem(),
                                                                         menuReporteDVC, new SeparatorMenuItem(),
                                                                         parentMenuProveedor);
                            
                            subMenu10.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
                                @Override
                                public void handle(MouseEvent event) {
                                    if (event.getButton() == MouseButton.PRIMARY){
                                        System.err.println(event.getScreenX()+" - "+event.getScreenY()+15);
                                        //contextMenuTaller.show(lbOtros, event.getScreenX(), event.getScreenY()+15);
                                        contextMenuReportesCompras.show(subMenu10, 990.0, 130.0);
                                    }
                                }
                            });
                            
//                            EventHandler actionListener9 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
//                                actionMenu("Reportes de Compras");
//                            };
//                            
//                            subMenu10.setOnAction(actionListener9);
                            
                            lViewMenu10 = true;

                            break;
                    }
                }
                
                Image imageBack = new Image(getClass().getResourceAsStream("/imagenes/arrows_back_menu_bar_32.png"));
                ImageView imageViewBack = new ImageView(imageBack);
                imageViewBack.setFitWidth(15);
                imageViewBack.setFitHeight(30);
                subMenu.setGraphic(imageViewBack);
                subMenu.setPrefSize(10, 95);
                //subMenu.setContentDisplay(ContentDisplay.TOP);
                //subMenu.setEffect(shadow);

                EventHandler actionListener10 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                    cerrarMenuCompras();
                };

                subMenu.setOnAction(actionListener10);
                            
                root.getChildren().add(subMenu);
                if (lViewMenu1) root.getChildren().add(subMenu1); 
                if (lViewMenu2) root.getChildren().add(subMenu2);
                if (lViewMenu3) root.getChildren().add(subMenu3); 
                if (lViewMenu4) root.getChildren().add(subMenu4);
                if (lViewMenu5) root.getChildren().add(subMenu5); 
                if (lViewMenu6) root.getChildren().add(subMenu6);
                if (lViewMenu7) root.getChildren().add(subMenu7); 
                if (lViewMenu8) root.getChildren().add(subMenu8);
                if (lViewMenu9) root.getChildren().add(subMenu9); 
                if (lViewMenu10) root.getChildren().add(subMenu10);
                
                jfxPanelCompras.setScene(scene);
            } catch (SQLException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    rsSubTree2.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void cerrarMenuCompras(){
        btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
        btnInventario.setForeground(new java.awt.Color(0, 0, 0));
        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
        btnCompras.setForeground(new java.awt.Color(0, 0, 0));
        btnTaller.setForeground(new java.awt.Color(0, 0, 0));
        btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
        btnMaestros.setForeground(new java.awt.Color(0, 0, 0));

        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnCompras);
        btnCbtesElectronicos.setVisible(true);

        lMenoShow = false;
    }
    
    private void crearSceneMenuTaller(){
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/bar_button_menu_taller.fxml"));
            HBox root = null;
            VBox vBoxMaestros = new VBox();
            VBox vBoxAnulaciones = new VBox();
            JFXButton subMenu1 = new JFXButton(); JFXButton subMenu2 = new JFXButton();
            JFXButton subMenu3 = new JFXButton(); JFXButton subMenu4 = new JFXButton();
            JFXButton subMenu5 = new JFXButton(); JFXButton subMenu = new JFXButton();
            
            Separator separador = new Separator(); Separator separador1 = new Separator();
            Separator separador2 = new Separator(); Separator separador3 = new Separator();
            Separator separador4 = new Separator();
            
            Label lbReversoDespacho = new Label(); Label lbCargaProductos = new Label(); Label lbReaperturaOrdReparacion = new Label();
            
            boolean lViewMenu1 = false, lViewMenu2 = false, lViewMenu3 = false, lViewMenu4 = false, lViewMenu5 = false,
                    lViewMenu6 = false, lViewMenu7 = false, lViewMenu8 = false, lViewMenu9 = false, lViewMenu10 = false,
                    lViewMenu11 = false;
            
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            root.setSpacing(10);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(MenuPrincipal.class.getResource("/Vista/styleLogin.css").toExternalForm());

            DropShadow shadow = new DropShadow();
                
            try{
                rsSubTree2 = controladorCategorias.subMenuTreePrincipal("88");
                rsSubTree2.beforeFirst();

                while(rsSubTree2.next()) {
                    switch (rsSubTree2.getString("MEN_NOMBRE")){
                        case "Maestros de Taller":
                            Image image = new Image(getClass().getResourceAsStream("/imagenes/maestros_taller_menu_bar.png"));
                            subMenu1.setGraphic(new ImageView(image));
                            //subMenu1.setMaxSize(95, 95);
                            subMenu1.setPrefSize(95, 95);
                            //subMenu1.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu1.setText("Maestros\nde Taller");
                            subMenu1.setTextAlignment(TextAlignment.CENTER);
                            subMenu1.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu1.setContentDisplay(ContentDisplay.TOP);
                            //subMenu1.setEffect(shadow);
                            separador.setOrientation(Orientation.VERTICAL);
                            
                            ContextMenu contextMenuMaestrosTaller = new ContextMenu();
                            
                            MenuItem menuTecnicos = new MenuItem("Técnicos");
                            Image imageTecnicos = new Image(getClass().getResourceAsStream("/imagenes/mecanico_24.png"));
                            ImageView iconTecnicos = new ImageView(imageTecnicos);
                            menuTecnicos.setGraphic(iconTecnicos);
                            
                            EventHandler actionEventTecnicos = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Técnicos");
                                nombreMenu = "Técnicos";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                            };
                            
                            menuTecnicos.setOnAction(actionEventTecnicos);
                            
                            MenuItem menuModelosVehiculos = new MenuItem("Modelos de Vehículos");
                            Image imageModelosVehiculos = new Image(getClass().getResourceAsStream("/imagenes/modelos_vehiculos_menu_bar_24.png"));
                            ImageView iconModelosVehiculos = new ImageView(imageModelosVehiculos);
                            menuModelosVehiculos.setGraphic(iconModelosVehiculos);
                            
                            EventHandler actionEventModelosVehiculos = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Modelos de Vehículos");
                            };
                            
                            menuModelosVehiculos.setOnAction(actionEventModelosVehiculos);
                            
                            MenuItem menuCategoriaVehiculos = new MenuItem("Categorias de Vehículos");
                            Image imageCategoriaVehiculos = new Image(getClass().getResourceAsStream("/imagenes/blanco_24.png"));
                            ImageView iconCategoriaVehiculos = new ImageView(imageCategoriaVehiculos);
                            menuCategoriaVehiculos.setGraphic(iconCategoriaVehiculos);
                            
                            EventHandler actionEventCategoriaVehiculos = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Categorias de Vehículos");
                            };
                            
                            menuCategoriaVehiculos.setOnAction(actionEventCategoriaVehiculos);

                            contextMenuMaestrosTaller.getItems().addAll(menuTecnicos, new SeparatorMenuItem(),
                                                                        menuModelosVehiculos, new SeparatorMenuItem(),
                                                                        menuCategoriaVehiculos);
                            
                            subMenu1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
                                @Override
                                public void handle(MouseEvent event) {
                                    if (event.getButton() == MouseButton.PRIMARY){
                                        System.err.println(event.getScreenX()+" - "+event.getScreenY()+15);
                                        //contextMenuTaller.show(lbOtros, event.getScreenX(), event.getScreenY()+15);
                                        contextMenuMaestrosTaller.show(subMenu1, 60.0, 128.0);
                                    }
                                }
                            });
                            
                            lViewMenu1 = true;

                            break;
                        case "Orden de Reparacion (Presupuesto)":
                            Image image1 = new Image(getClass().getResourceAsStream("/imagenes/presupuesto_taller_menu_bar.png"));
                            subMenu2 = new JFXButton();
                            subMenu2.setGraphic(new ImageView(image1));
                            subMenu2.setPrefSize(95, 95);
                            //subMenu2.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu2.setText("Presupuesto");
                            subMenu2.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            separador1.setOrientation(Orientation.VERTICAL);
                            
                            EventHandler actionListener1 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Orden de Reparacion (Presupuesto)");
                            };
                            
                            subMenu2.setOnAction(actionListener1);
                            
                            lViewMenu2 = true;

                            break;
                        case "Orden de Reparación":
                            Image image2 = new Image(getClass().getResourceAsStream("/imagenes/orden_reparacion_menu_barr.png"));
                            subMenu3.setGraphic(new ImageView(image2));
                            subMenu3.setPrefSize(95, 95);
                            //subMenu3.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu3.setText("Orden de\nReparación");
                            subMenu3.setTextAlignment(TextAlignment.CENTER);
                            subMenu3.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu3.setContentDisplay(ContentDisplay.TOP);
                            //subMenu3.setEffect(shadow);
                            separador2.setOrientation(Orientation.VERTICAL);
                            
                            EventHandler actionListener2 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Orden de Reparación");
                                nombreMenu = "Orden de Reparación";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                            };
                            
                            subMenu3.setOnAction(actionListener2);
                            
                            lViewMenu3 = true;

                            break;
                        case "Orden de ReparaciÃ³n":
                            Image image22 = new Image(getClass().getResourceAsStream("/imagenes/orden_reparacion_menu_barr.png"));
                            subMenu3.setGraphic(new ImageView(image22));
                            subMenu3.setPrefSize(95, 95);
                            subMenu3.setText("Orden de\nReparación");
                            subMenu3.setTextAlignment(TextAlignment.CENTER);
                            subMenu3.setTooltip(new Tooltip("Orden de Reparación"));
                            subMenu3.setContentDisplay(ContentDisplay.TOP);
                            //subMenu3.setEffect(shadow);
                            separador2.setOrientation(Orientation.VERTICAL);
                            
                            EventHandler actionListener22 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Orden de Reparación");
                                nombreMenu = "Orden de Reparación";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                            };
                            
                            subMenu3.setOnAction(actionListener22);
                            
                            lViewMenu3 = true;

                            break;
                        case "Cambio de Placa":
                            Image image3 = new Image(getClass().getResourceAsStream("/imagenes/placa_menu_bar.png"));
                            subMenu4.setGraphic(new ImageView(image3));
                            subMenu4.setPrefSize(95, 95);
                            //subMenu4.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu4.setText("Cambio de\nPlaca");
                            subMenu4.setTextAlignment(TextAlignment.CENTER);
                            subMenu4.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu4.setContentDisplay(ContentDisplay.TOP);
                            //subMenu4.setEffect(shadow);
                            separador3.setOrientation(Orientation.VERTICAL);
                            
                            EventHandler actionListener3 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Cambio de Placa");
                            };
                            
                            subMenu4.setOnAction(actionListener3);
                            
                            lViewMenu4 = true;

                            break;
//                        case "Anulaciones":
//                            Image image4 = new Image(getClass().getResourceAsStream("/imagenes/anulaciones_orden_reparacion_menu_bar.png"));
//                            subMenu5.setGraphic(new ImageView(image4));
//                            subMenu5.setPrefSize(95, 95);
//                            subMenu5.setText(rsSubTree2.getString("MEN_NOMBRE"));
//                            subMenu5.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
//                            subMenu5.setContentDisplay(ContentDisplay.TOP);
//                            //subMenu5.setEffect(shadow);
//                            separador4.setOrientation(Orientation.VERTICAL);
//                            
//                            EventHandler actionListener4 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
//                                crearSceneSubMenuAnulaciones();
//                                
//                                lSubMenu2 = true;
//                            };
//                            
//                            subMenu5.setOnAction(actionListener4);
//                            
//                            lViewMenu5 = true;
//
//                            break;
                    }
                }
                
                Image imageBack = new Image(getClass().getResourceAsStream("/imagenes/arrows_back_menu_bar_32.png"));
                ImageView imageViewBack = new ImageView(imageBack);
                imageViewBack.setFitWidth(15);
                imageViewBack.setFitHeight(30);
                subMenu.setGraphic(imageViewBack);
                subMenu.setPrefSize(10, 95);
                //subMenu.setContentDisplay(ContentDisplay.TOP);
                //subMenu.setEffect(shadow);

                EventHandler actionListener5 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                    if (lMenu5 && (lSubMenu1 || lSubMenu2)){
                        if (lMenu5 && lSubSubMenu1){
                            crearSceneSubMenuTaller();
                            lSubSubMenu1 = false;
                        }else{
                            crearSceneMenuTaller();
                            lSubMenu1 = false; lSubMenu2 = false;
                        }
                    }else{
                        btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
                        btnInventario.setForeground(new java.awt.Color(0, 0, 0));
                        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
                        btnCompras.setForeground(new java.awt.Color(0, 0, 0));
                        btnTaller.setForeground(new java.awt.Color(0, 0, 0));
                        btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
                        btnMaestros.setForeground(new java.awt.Color(0, 0, 0));

                        //Animacion.Animacion.subir(20, -204, 12, 11, panelBtn);
                        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTaller);
                        btnCbtesElectronicos.setVisible(true);
                    }

                    lMenoShow = false;
                };

                subMenu.setOnAction(actionListener5);
                
//                if (lViewMenu5) vBox.getChildren().add(lbGrupoPermiso);
//                if (lViewMenu6) vBox.getChildren().add(lbSeguridad);
//                if (lViewMenu7) vBox.getChildren().add(lbUsuarioEmpresas);

                rsSubTree2 = controladorCategorias.subMenuTreePrincipal("106");
                rsSubTree2.beforeFirst();
            
                while(rsSubTree2.next()) {
                    switch (rsSubTree2.getString("MEN_NOMBRE")){
                        case "Anular RequisiciÃ³n de Productos":
                            Image image44 = new Image(getClass().getResourceAsStream("/imagenes/anulaciones_orden_reparacion_menu_bar.png"));
                            subMenu5.setGraphic(new ImageView(image44));
                            subMenu5.setPrefSize(125, 95);
                            //subMenu5.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu5.setText("Anular RequisiciÃ³n\nde Productos");
                            subMenu5.setTextAlignment(TextAlignment.CENTER);
                            subMenu5.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu5.setContentDisplay(ContentDisplay.TOP);
                            //subMenu5.setEffect(shadow);
                            separador4.setOrientation(Orientation.VERTICAL);
                            
                            EventHandler actionListener44 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Anular Requisición de Productos");
                            };
                            
                            subMenu5.setOnAction(actionListener44);
                            
                            lViewMenu5 = true;

                            break;
                        case "Anular Requisición de Productos":
                            Image image4 = new Image(getClass().getResourceAsStream("/imagenes/anulaciones_orden_reparacion_menu_bar.png"));
                            subMenu5.setGraphic(new ImageView(image4));
                            subMenu5.setPrefSize(125, 95);
                            //subMenu5.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu5.setText("Anular Requisición\nde Productos");
                            subMenu5.setTextAlignment(TextAlignment.CENTER);
                            subMenu5.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu5.setContentDisplay(ContentDisplay.TOP);
                            //subMenu5.setEffect(shadow);
                            separador4.setOrientation(Orientation.VERTICAL);
                            
                            EventHandler actionListener4 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Anular Requisición de Productos");
                            };
                            
                            subMenu5.setOnAction(actionListener4);
                            
                            lViewMenu5 = true;

                            break;
                        case "Reverso de Despachos": 
                            lbReversoDespacho.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            Image imageLabel_1 = new Image(getClass().getResourceAsStream("/imagenes/anulacion_documentos_menu_bar_24.png"));
                            lbReversoDespacho.setGraphic(new ImageView(imageLabel_1));
                
                            lbReversoDespacho.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            //subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            EventHandler actionListener6 = (EventHandler<javafx.scene.input.MouseEvent>) (javafx.scene.input.MouseEvent event) -> {
                                actionMenu("Reverso de Despachos");
                            };
                            
                            lbReversoDespacho.setOnMouseClicked(actionListener6);
                            
                            lViewMenu6 = true;

                            break;
                        case "Anular Carga de Productos":
                            lbCargaProductos.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            Image imageLabel_2 = new Image(getClass().getResourceAsStream("/imagenes/anulacion_documentos_menu_bar_24.png"));
                            lbCargaProductos.setGraphic(new ImageView(imageLabel_2));
                
                            lbCargaProductos.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            //subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            EventHandler actionListener7 = (EventHandler<javafx.scene.input.MouseEvent>) (javafx.scene.input.MouseEvent event) -> {
                                actionMenu("Anular Carga de Productos");
                            };
                            
                            lbCargaProductos.setOnMouseClicked(actionListener7);
                            
                            lViewMenu7 = true;

                            break;
                        case "Reapertura de Orden de ReparaciÃ³n":
                            lbReaperturaOrdReparacion.setText("Reapertura de Orden de Reparación");
                            Image imageLabel_33 = new Image(getClass().getResourceAsStream("/imagenes/anulacion_documentos_menu_bar_24.png"));
                            lbReaperturaOrdReparacion.setGraphic(new ImageView(imageLabel_33));
                
                            lbReaperturaOrdReparacion.setTooltip(new Tooltip("Reapertura de Orden de Reparación"));
                            //subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            EventHandler actionListener83 = (EventHandler<javafx.scene.input.MouseEvent>) (javafx.scene.input.MouseEvent event) -> {
                                actionMenu("Reapertura de Orden de Reparación");
                            };
                            
                            lbReaperturaOrdReparacion.setOnMouseClicked(actionListener83);
                            
                            lViewMenu8 = true;

                            break;
                        case "Reapertura de Orden de Reparación":
                            lbReaperturaOrdReparacion.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            Image imageLabel_3 = new Image(getClass().getResourceAsStream("/imagenes/anulacion_documentos_menu_bar_24.png"));
                            lbReaperturaOrdReparacion.setGraphic(new ImageView(imageLabel_3));
                
                            lbReaperturaOrdReparacion.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            //subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            EventHandler actionListener8 = (EventHandler<javafx.scene.input.MouseEvent>) (javafx.scene.input.MouseEvent event) -> {
                                actionMenu("Reapertura de Orden de Reparación");
                            };
                            
                            lbReaperturaOrdReparacion.setOnMouseClicked(actionListener8);
                            
                            lViewMenu8 = true;

                            break;
                    }
                }
                
                if (lViewMenu6) vBoxAnulaciones.getChildren().add(lbReversoDespacho);
                if (lViewMenu7) vBoxAnulaciones.getChildren().add(lbCargaProductos);
                if (lViewMenu8) vBoxAnulaciones.getChildren().add(lbReaperturaOrdReparacion);
            
                Label lbMarcaVehiculo = new Label(); Label lbVehiculo = new Label(); Label lbOtros = new Label();
                rsSubTree2 = controladorCategorias.subMenuTreePrincipal("89");
                rsSubTree2.beforeFirst();

                while(rsSubTree2.next()) {
                    switch (rsSubTree2.getString("MEN_NOMBRE")){
                        case "Marcas de Vehículos":
                            lbMarcaVehiculo.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            Image imageLabel_4 = new Image(getClass().getResourceAsStream("/imagenes/marcas_menu_bar_24.png"));
                            lbMarcaVehiculo.setGraphic(new ImageView(imageLabel_4));
                
                            lbMarcaVehiculo.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            //subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            EventHandler actionListener9 = (EventHandler<javafx.scene.input.MouseEvent>) (javafx.scene.input.MouseEvent event) -> {
                                actionMenu("Marcas de Vehículos");
                            };
                            
                            lbMarcaVehiculo.setOnMouseClicked(actionListener9);
                            
                            lViewMenu9 = true;

                            break;
                        case "Marcas de VehÃ­culos":
                            lbMarcaVehiculo.setText("Marcas de Vehículos");
                            Image imageLabel_44 = new Image(getClass().getResourceAsStream("/imagenes/marcas_menu_bar_24.png"));
                            lbMarcaVehiculo.setGraphic(new ImageView(imageLabel_44));
                
                            lbMarcaVehiculo.setTooltip(new Tooltip("Marcas de Vehículos"));
                            //subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            EventHandler actionListener94 = (EventHandler<javafx.scene.input.MouseEvent>) (javafx.scene.input.MouseEvent event) -> {
                                actionMenu("Marcas de Vehículos");
                            };
                            
                            lbMarcaVehiculo.setOnMouseClicked(actionListener94);
                            
                            lViewMenu9 = true;

                            break;
                        case "Vehículos":
                            lbVehiculo.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            Image imageLabel_5 = new Image(getClass().getResourceAsStream("/imagenes/vehiculo_menu_bar_24.png"));
                            lbVehiculo.setGraphic(new ImageView(imageLabel_5));
                
                            lbVehiculo.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            //subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            EventHandler actionListener10 = (EventHandler<javafx.scene.input.MouseEvent>) (javafx.scene.input.MouseEvent event) -> {
                                actionMenu("Vehículos");
                            };
                            
                            lbVehiculo.setOnMouseClicked(actionListener10);
                            
                            lViewMenu10 = true;

                            break;
                        case "VehÃ­culos":
                            lbVehiculo.setText("Vehículos");
                            Image imageLabel_55 = new Image(getClass().getResourceAsStream("/imagenes/vehiculo_menu_bar_24.png"));
                            lbVehiculo.setGraphic(new ImageView(imageLabel_55));
                
                            lbVehiculo.setTooltip(new Tooltip("Vehículos"));
                            //subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            EventHandler actionListener105 = (EventHandler<javafx.scene.input.MouseEvent>) (javafx.scene.input.MouseEvent event) -> {
                                actionMenu("Vehículos");
                            };
                            
                            lbVehiculo.setOnMouseClicked(actionListener105);
                            
                            lViewMenu10 = true;

                            break;
                        case "Otros":
                            lbOtros.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            Image imageLabel_6 = new Image(getClass().getResourceAsStream("/imagenes/otros_car_menu_bar_24.png"));
                            lbOtros.setGraphic(new ImageView(imageLabel_6));
                
                            lbOtros.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            //subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            ContextMenu contextMenuTaller = new ContextMenu();
                            
                            MenuItem menuMotor = new MenuItem("Motor");
                            Image imageMotor_1 = new Image(getClass().getResourceAsStream("/imagenes/motor_menu_bar_24.png"));
                            ImageView iconMotor = new ImageView(imageMotor_1);
                            menuMotor.setGraphic(iconMotor);
                            
                            EventHandler actionEventMotor = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Motor");
                            };
                            
                            menuMotor.setOnAction(actionEventMotor);
                            
                            MenuItem menuTransmision = new MenuItem("Transmisión");
                            Image imageTransmision = new Image(getClass().getResourceAsStream("/imagenes/transmision_menu_bar_24.png"));
                            ImageView iconTransmision = new ImageView(imageTransmision);
                            menuTransmision.setGraphic(iconTransmision);
                            
                            EventHandler actionEventTransmision = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Transmisión");
                            };
                            
                            menuTransmision.setOnAction(actionEventTransmision);
                            
                            MenuItem menuTraccion = new MenuItem("Tracción");
                            Image imageTraccion = new Image(getClass().getResourceAsStream("/imagenes/traccion_menu_bar_24.png"));
                            ImageView iconTraccion = new ImageView(imageTraccion);
                            menuTraccion.setGraphic(iconTraccion);
                            
                            EventHandler actionEventTraccion = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Tracción");
                            };
                            
                            menuTraccion.setOnAction(actionEventTraccion);
                            
                            MenuItem menuTipoCilindrada = new MenuItem("Tipo de Cilindrada");
                            Image imageTipoCilindrada = new Image(getClass().getResourceAsStream("/imagenes/pistones_menu_bar_24.png"));
                            ImageView iconTipoCilindrada = new ImageView(imageTipoCilindrada);
                            menuTipoCilindrada.setGraphic(iconTipoCilindrada);
                            
                            EventHandler actionEventTipoCilindrada = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Tipo de Cilindrada");
                            };
                            
                            menuTipoCilindrada.setOnAction(actionEventTipoCilindrada);
                            
                            MenuItem menuModeloCilindrada = new MenuItem("Modelo de Cilindrada");
                            Image imageModeloCilindrada = new Image(getClass().getResourceAsStream("/imagenes/modelo_cilindrada_menu_bar_24.png"));
                            ImageView iconModeloCilindrada = new ImageView(imageModeloCilindrada);
                            menuModeloCilindrada.setGraphic(iconModeloCilindrada);
                            
                            EventHandler actionEventModeloCilindrada = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Modelo de Cilindrada");
                            };
                            
                            menuModeloCilindrada.setOnAction(actionEventModeloCilindrada);
                            
                            MenuItem menuCombustible = new MenuItem("Combustible");
                            Image imageCombustible = new Image(getClass().getResourceAsStream("/imagenes/combustible_menu_bar_24.png"));
                            ImageView iconCombustible = new ImageView(imageCombustible);
                            menuCombustible.setGraphic(iconCombustible);
                            
                            EventHandler actionEventCombustible = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Combustible");
                            };
                            
                            menuCombustible.setOnAction(actionEventCombustible);

                            contextMenuTaller.getItems().addAll(menuMotor, new SeparatorMenuItem(),
                                                                menuTransmision, new SeparatorMenuItem(),
                                                                menuTraccion, new SeparatorMenuItem(),
                                                                menuTipoCilindrada, new SeparatorMenuItem(),
                                                                menuModeloCilindrada, new SeparatorMenuItem(),
                                                                menuCombustible);
                            
//                            EventHandler actionListener11 = (EventHandler<javafx.scene.input.MouseEvent>) (javafx.scene.input.MouseEvent event) -> {
//                                crearSceneSubMenuOtrosTaller();
//                                
//                                lSubMenu1 = true;
//                                lSubSubMenu1 = true;
//                            };
//                            
//                            lbOtros.setOnMouseClicked(actionListener11);
                            lbOtros.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
                                @Override
                                public void handle(MouseEvent event) {
                                    if (event.getButton() == MouseButton.PRIMARY){
                                        System.err.println(event.getScreenX()+" - "+event.getScreenY()+15);
                                        //contextMenuTaller.show(lbOtros, event.getScreenX(), event.getScreenY()+15);
                                        contextMenuTaller.show(lbOtros, 167.0, 124.0);
                                    }
                                }
                            });
                            
                            lViewMenu11 = true;

                            break;
                    }
                }
                
                if (lViewMenu9)  vBoxMaestros.getChildren().add(lbMarcaVehiculo);
                if (lViewMenu10) vBoxMaestros.getChildren().add(lbVehiculo);
                if (lViewMenu11) vBoxMaestros.getChildren().add(lbOtros);
                            
                root.getChildren().add(subMenu);
                root.getChildren().add(separador);
                if (lViewMenu1) root.getChildren().add(subMenu1); root.getChildren().add(vBoxMaestros); root.getChildren().add(separador1);
                if (lViewMenu2) root.getChildren().add(subMenu2); root.getChildren().add(separador2);
                if (lViewMenu3) root.getChildren().add(subMenu3); root.getChildren().add(separador3);
                if (lViewMenu4) root.getChildren().add(subMenu4); root.getChildren().add(separador4);
                if (lViewMenu5) root.getChildren().add(subMenu5); root.getChildren().add(vBoxAnulaciones);
                
                jfxPanelTaller.setScene(scene);
            } catch (SQLException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    rsSubTree2.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void createSceneMenuTesoreria(){
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/bar_button_menu.fxml"));
            HBox root = null;
            VBox vBox = new VBox();
            JFXButton subMenu1 = new JFXButton(); JFXButton subMenu2 = new JFXButton();
            JFXButton subMenu3 = new JFXButton(); JFXButton subMenu4 = new JFXButton();
            JFXButton subMenu5 = new JFXButton(); JFXButton subMenu6 = new JFXButton();
            JFXButton subMenu = new JFXButton();
            Separator separador = new Separator();
            
            Label lbGrupoPermiso = new Label(); Label lbSeguridad = new Label(); Label lbUsuarioEmpresas = new Label();
            
            boolean lViewMenu1 = false, lViewMenu2 = false, lViewMenu3 = false, lViewMenu4 = false, lViewMenu5 = false,
                    lViewMenu6 = false, lViewMenu7 = false, lViewMenu = false;
            
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            root.setSpacing(10);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(MenuPrincipal.class.getResource("/Vista/styleLogin.css").toExternalForm());

            DropShadow shadow = new DropShadow();
                
            try{
                rsSubTree2 = controladorCategorias.subMenuTreePrincipal("6");
                rsSubTree2.beforeFirst();
                            
                while(rsSubTree2.next()) {
                    switch (rsSubTree2.getString("MEN_NOMBRE")){
                        case "Bancos":        
                            Image image = new Image(getClass().getResourceAsStream("/imagenes/menu_bar_bank.png"), widthImage, heightImage, true, true);
                            subMenu1.setGraphic(new ImageView(image));
                            //subMenu1.setMaxSize(95, 95);
                            subMenu1.setPrefSize(110, 95);
                            //subMenu1.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu1.setText("Bancos");
                            subMenu1.setTextAlignment(TextAlignment.CENTER);
                            subMenu1.setTooltip(new Tooltip("Bancos"));
                            subMenu1.setContentDisplay(ContentDisplay.TOP);

                            EventHandler actionListener = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                nombreMenu = "Bancos";

                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();

                                cerrarMenuVentas(panelBtnTesoreria);
                            };

                            subMenu1.setOnAction(actionListener);
                            
                            lViewMenu = true;

                            break;
                        case "Instrumentos Bancarios":        
                            Image image1 = new Image(getClass().getResourceAsStream("/imagenes/instrumento_pago_menu_2.png"), widthImage, heightImage, true, true);
                            subMenu2.setGraphic(new ImageView(image1));
                            //subMenu1.setMaxSize(95, 95);
                            subMenu2.setPrefSize(110, 95);
                            //subMenu1.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu2.setText("Instrumentos \nBancarios");
                            subMenu2.setTextAlignment(TextAlignment.CENTER);
                            subMenu2.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu2.setContentDisplay(ContentDisplay.TOP);
                            
                            EventHandler actionListener1 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Cambio de Usuario");
                                nombreMenu = "Instrumentos Bancarios";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnTesoreria);
                            };
                            
                            subMenu2.setOnAction(actionListener1);
                            
                            lViewMenu1 = true;

                            break;
                        case "Movimientos":
                            Image image2 = new Image(getClass().getResourceAsStream("/imagenes/download server.png"), widthImage, heightImage, true, true);
                            subMenu3.setGraphic(new ImageView(image2));
                            subMenu3.setPrefSize(85, 95);
                            subMenu3.setText("Movimientos");
                            subMenu3.setTextAlignment(TextAlignment.CENTER);
                            subMenu3.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu3.setContentDisplay(ContentDisplay.TOP);
                            
                            EventHandler actionListener2 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Actualizar el Sistema");
                                nombreMenu = "Movimientos";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnTesoreria);
                            };
                            
                            subMenu3.setOnAction(actionListener2);
                            
                            lViewMenu2 = true;

                            break;
                        case "Conciliación":
                            Image image3 = new Image(getClass().getResourceAsStream("/imagenes/user_files_menu_bar_48.png"), widthImage, heightImage, true, true);
                            subMenu4.setGraphic(new ImageView(image3));
                            subMenu4.setPrefSize(85, 95);
                            subMenu4.setText("Conciliación");
                            subMenu4.setTooltip(new Tooltip("Conciliación"));
                            subMenu4.setTextAlignment(TextAlignment.CENTER);
                            subMenu4.setContentDisplay(ContentDisplay.TOP);
                            
                            EventHandler actionListener3 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Reportes Usuarios");
                                nombreMenu = "Conciliación";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnTesoreria);
                            };
                            
                            subMenu4.setOnAction(actionListener3);
                            
                            lViewMenu3 = true;

                            break;
                        case "Imprimir Cheques":
                            Image image4 = new Image(getClass().getResourceAsStream("/imagenes/menu_print_cheque.png"), widthImage, heightImage, true, true);
                            subMenu5.setGraphic(new ImageView(image4));
                            subMenu5.setPrefSize(120, 95);
                            subMenu5.setText("Imprimir Cheques");
                            subMenu5.setTooltip(new Tooltip("Imprimir Cheques"));
                            subMenu5.setTextAlignment(TextAlignment.CENTER);
                            subMenu5.setContentDisplay(ContentDisplay.TOP);
                            
                            EventHandler actionListener4 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Reportes Usuarios");
                                nombreMenu = "Imprimir Cheques";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnTesoreria);
                            };
                            
                            subMenu5.setOnAction(actionListener4);
                            
                            lViewMenu4 = true;

                            break;
                        case "Reportes Bancarios":
                            Image image5 = new Image(getClass().getResourceAsStream("/imagenes/user_files_menu_bar_48.png"), widthImage, heightImage, true, true);
                            subMenu6.setGraphic(new ImageView(image5));
                            subMenu6.setPrefSize(120, 95);
                            subMenu6.setText("Reportes Bancarios");
                            subMenu6.setTooltip(new Tooltip("Imprimir Cheques"));
                            subMenu6.setTextAlignment(TextAlignment.CENTER);
                            subMenu6.setContentDisplay(ContentDisplay.TOP);
                            separador.setOrientation(Orientation.VERTICAL);
                            
                            EventHandler actionListener5 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Reportes Usuarios");
                                nombreMenu = "Reportes Bancarios";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnTesoreria);
                            };
                            
                            subMenu6.setOnAction(actionListener5);
                            
                            lViewMenu5 = true;

                            break;
                    }
                }
                
                Image imageBack = new Image(getClass().getResourceAsStream("/imagenes/arrows_back_menu_bar_32.png"));
                ImageView imageViewBack = new ImageView(imageBack);
                imageViewBack.setFitWidth(15);
                imageViewBack.setFitHeight(30);
                subMenu.setGraphic(imageViewBack);
                subMenu.setPrefSize(10, 95);
                //subMenu.setContentDisplay(ContentDisplay.TOP);
                //subMenu.setEffect(shadow);

                EventHandler actionListener6 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                    if (lMenu7 && (lSubMenu1 || lSubMenu2)){
                        createSceneMenuTesoreria();
                        lSubMenu1 = false; lSubMenu2 = false;
                    }else{
                        btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
                        btnInventario.setForeground(new java.awt.Color(0, 0, 0));
                        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
                        btnCompras.setForeground(new java.awt.Color(0, 0, 0));
                        btnTaller.setForeground(new java.awt.Color(0, 0, 0));
                        btnTesoreria.setForeground(new java.awt.Color(0, 0, 0));
                        btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
                        btnMaestros.setForeground(new java.awt.Color(0, 0, 0));

                        Animacion.Animacion.subir(20, -204, 12, 11, panelBtnTesoreria);
                        btnCbtesElectronicos.setVisible(true);
                    }

                    lMenoShow = false;
                };

                subMenu.setOnAction(actionListener6);
                        
                root.getChildren().add(subMenu);
                root.getChildren().add(separador);
                if (lViewMenu) root.getChildren().add(subMenu1); 
                if (lViewMenu1) root.getChildren().add(subMenu2); 
                if (lViewMenu2) root.getChildren().add(subMenu3); 
                if (lViewMenu3) root.getChildren().add(subMenu4); 
                if (lViewMenu4) root.getChildren().add(subMenu5); 
                if (lViewMenu5) root.getChildren().add(subMenu6); 
                
                jfxPanelTesoreria.setScene(scene);
            } catch (SQLException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    rsSubTree2.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void crearSceneMenuContabilidad(){
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/bar_button_menu.fxml"));
            HBox root = null;
            JFXButton subMenu1 = new JFXButton(); JFXButton subMenu2 = new JFXButton();
            JFXButton subMenu3 = new JFXButton(); JFXButton subMenu4 = new JFXButton();
            JFXButton subMenu5 = new JFXButton(); JFXButton subMenu6 = new JFXButton();
            JFXButton subMenu7 = new JFXButton(); JFXButton subMenu8 = new JFXButton();
            JFXButton subMenu9 = new JFXButton(); JFXButton subMenu10 = new JFXButton();
            JFXButton subMenu11 = new JFXButton(); JFXButton subMenu = new JFXButton();
            
            boolean lViewMenu1 = false, lViewMenu2 = false, lViewMenu3 = false, lViewMenu4 = false, lViewMenu5 = false,
                    lViewMenu6 = false, lViewMenu7 = false, lViewMenu8 = false, lViewMenu9 = false, lViewMenu10 = false,
                    lViewMenu11 = false;
            
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            root.setSpacing(10);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(MenuPrincipal.class.getResource("/Vista/styleLogin.css").toExternalForm());

            DropShadow shadow = new DropShadow();
                
            try{
                rsSubTree2 = controladorCategorias.subMenuTreePrincipal("7");
                rsSubTree2.beforeFirst();

                while(rsSubTree2.next()) {
                    System.err.println(rsSubTree2.getString("MEN_NOMBRE"));
                    switch (rsSubTree2.getString("MEN_NOMBRE")){
                        case "Importar Plan de Cuentas":
                            Image image = new Image(getClass().getResourceAsStream("/imagenes/importar_plan_ctas_menu_bar.png"));
                            subMenu1.setGraphic(new ImageView(image));
                            subMenu1.setPrefSize(110, 95);
                            subMenu1.setText("Importar\nPlan de Cuentas");
                            subMenu1.setTextAlignment(TextAlignment.CENTER);
                            subMenu1.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu1.setContentDisplay(ContentDisplay.TOP);
                            //subMenu1.setEffect(shadow);
                            
                            EventHandler actionListener = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Importar Plan de Cuentas");
                            };
                            
                            subMenu1.setOnAction(actionListener);
                            
                            lViewMenu1 = true;

                            break;
                        case "Estructura de Ctas":
                            Image image1 = new Image(getClass().getResourceAsStream("/imagenes/estrucutra_cta_menu_bar_3.png"));
                            subMenu2.setGraphic(new ImageView(image1));
                            subMenu2.setPrefSize(95, 95);
                            subMenu2.setText("Estructura\nde Ctas");
                            subMenu2.setTextAlignment(TextAlignment.CENTER);
                            subMenu2.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            EventHandler actionListener1 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Estructura de Ctas");
                            };
                            
                            subMenu2.setOnAction(actionListener1);
                            
                            lViewMenu2 = true;

                            break;
                        case "Plan de Cuentas":
                            Image image2 = new Image(getClass().getResourceAsStream("/imagenes/plan_ctas_menu_bar.png"));
                            subMenu3.setGraphic(new ImageView(image2));
                            subMenu3.setPrefSize(95, 95);
                            subMenu3.setText("Plan de\nCuentas");
                            subMenu3.setTextAlignment(TextAlignment.CENTER);
                            subMenu3.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu3.setContentDisplay(ContentDisplay.TOP);
                            //subMenu3.setEffect(shadow);
                            
                            EventHandler actionListener2 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Plan de Cuentas");
                            };
                            
                            subMenu3.setOnAction(actionListener2);
                            
                            lViewMenu3 = true;

                            break;
                        case "Configuracion de Asientos":
                            Image image3 = new Image(getClass().getResourceAsStream("/imagenes/conf_plan_ctas_menu_bar.png"));
                            subMenu4.setGraphic(new ImageView(image3));
                            subMenu4.setPrefSize(95, 95);
                            subMenu4.setText("Configuracion\nde Asientos");
                            subMenu4.setTextAlignment(TextAlignment.CENTER);
                            subMenu4.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu4.setContentDisplay(ContentDisplay.TOP);
                            //subMenu4.setEffect(shadow);
                            
                            EventHandler actionListener3 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Configuracion de Asientos");
                            };
                            
                            subMenu4.setOnAction(actionListener3);
                            
                            lViewMenu4 = true;

                            break;
                        case "Asientos Contables":
                            Image image4 = new Image(getClass().getResourceAsStream("/imagenes/asientos_contables_menu_bar.png"));
                            subMenu5.setGraphic(new ImageView(image4));
                            subMenu5.setPrefSize(95, 95);
                            subMenu5.setText("Asientos\nContables");
                            subMenu5.setTextAlignment(TextAlignment.CENTER);
                            subMenu5.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu5.setContentDisplay(ContentDisplay.TOP);
                            //subMenu5.setEffect(shadow);
                            
                            EventHandler actionListener4 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Asientos Contables");
                            };
                            
                            subMenu5.setOnAction(actionListener4);
                            
                            lViewMenu5 = true;

                            break;
                        case "Aprobacion de Comprobantes":
                            Image image5 = new Image(getClass().getResourceAsStream("/imagenes/aprobacion_comprobantes_contables.png"));
                            subMenu6.setGraphic(new ImageView(image5));
                            subMenu6.setPrefSize(115, 95);
                            subMenu6.setText("Aprobacion\nde Comprobantes");
                            subMenu6.setTextAlignment(TextAlignment.CENTER);
                            subMenu6.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu6.setContentDisplay(ContentDisplay.TOP);
                            //subMenu6.setEffect(shadow);
                            
                            EventHandler actionListener5 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Aprobacion de Comprobantes");
                            };
                            
                            subMenu6.setOnAction(actionListener5);
                            
                            lViewMenu6 = true;

                            break;
                        case "Grupos de Asientos":
                            Image image6 = new Image(getClass().getResourceAsStream("/imagenes/nota_credito_debito_menu_bar.png"));
                            subMenu7.setGraphic(new ImageView(image6));
                            subMenu7.setPrefSize(95, 95);
                            subMenu7.setText("Grupos\nde Asientos");
                            subMenu7.setTextAlignment(TextAlignment.CENTER);
                            subMenu7.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu7.setContentDisplay(ContentDisplay.TOP);
                            //subMenu7.setEffect(shadow);
                            
                            EventHandler actionListener6 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Grupos de Asientos");
                            };
                            
                            subMenu7.setOnAction(actionListener6);
                            
                            lViewMenu7 = true;

                            break;
                        case "Cierre Contable":
                            Image image7 = new Image(getClass().getResourceAsStream("/imagenes/cierre_contable_menu_bar.png"));
                            subMenu8.setGraphic(new ImageView(image7));
                            subMenu8.setPrefSize(95, 95);
                            subMenu8.setText("Cierre\nContable");
                            subMenu8.setTextAlignment(TextAlignment.CENTER);
                            subMenu8.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu8.setContentDisplay(ContentDisplay.TOP);
                            //subMenu8.setEffect(shadow);
                            
                            EventHandler actionListener7 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Cierre Contable");
                            };
                            
                            subMenu8.setOnAction(actionListener7);
                            
                            lViewMenu8 = true;

                            break;
                        case "Asientos Directos":
                            Image image8 = new Image(getClass().getResourceAsStream("/imagenes/asientos_directo_menu_bar.png"));
                            subMenu9.setGraphic(new ImageView(image8));
                            subMenu9.setPrefSize(95, 95);
                            subMenu9.setText("Asientos\nDirectos");
                            subMenu9.setTextAlignment(TextAlignment.CENTER);
                            subMenu9.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu9.setContentDisplay(ContentDisplay.TOP);
                            //subMenu9.setEffect(shadow);
                            
                            EventHandler actionListener8 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Asientos Directos");
                            };
                            
                            subMenu9.setOnAction(actionListener8);
                            
                            lViewMenu9 = true;

                            break;
                        case "Cierre Fiscal":
                            Image image9 = new Image(getClass().getResourceAsStream("/imagenes/cierre_fiscal_menu_bar.png"));
                            subMenu10.setGraphic(new ImageView(image9));
                            subMenu10.setPrefSize(95, 95);
                            subMenu10.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu10.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu10.setContentDisplay(ContentDisplay.TOP);
                            //subMenu10.setEffect(shadow);
                            
                            EventHandler actionListener9 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Cierre Fiscal");
                            };
                            
                            subMenu10.setOnAction(actionListener9);
                            
                            lViewMenu10 = true;

                            break;
                        case "Reportes Contables":
                            Image image10 = new Image(getClass().getResourceAsStream("/imagenes/contabilidad_menu_bar_2.png"));
                            subMenu11.setGraphic(new ImageView(image10));
                            subMenu11.setPrefSize(95, 95);
                            subMenu11.setText("Reportes\nContables");
                            subMenu11.setTextAlignment(TextAlignment.CENTER);
                            subMenu11.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu11.setContentDisplay(ContentDisplay.TOP);
                            //subMenu11.setEffect(shadow);
                            
                            EventHandler actionListener10 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                
                            };
                            
                            subMenu11.setOnAction(actionListener10);
                            
                            lViewMenu11 = true;

                            break;
                    }
                }
                
                Image imageBack = new Image(getClass().getResourceAsStream("/imagenes/arrows_back_menu_bar_32.png"));
                ImageView imageViewBack = new ImageView(imageBack);
                imageViewBack.setFitWidth(15);
                imageViewBack.setFitHeight(30);
                subMenu.setGraphic(imageViewBack);
                subMenu.setPrefSize(10, 95);
                //subMenu.setContentDisplay(ContentDisplay.TOP);
                //subMenu.setEffect(shadow);

                EventHandler actionListener11 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                    btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
                    btnInventario.setForeground(new java.awt.Color(0, 0, 0));
                    btnVentas.setForeground(new java.awt.Color(0, 0, 0));
                    btnCompras.setForeground(new java.awt.Color(0, 0, 0));
                    btnTaller.setForeground(new java.awt.Color(0, 0, 0));
                    btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
                    btnMaestros.setForeground(new java.awt.Color(0, 0, 0));

                    Animacion.Animacion.subir(20, -204, 12, 11, panelBtnContabilidad);
                    btnCbtesElectronicos.setVisible(true);

                    lMenoShow = false;
                };

                subMenu.setOnAction(actionListener11);
                            
                root.getChildren().add(subMenu);
                if (lViewMenu1) root.getChildren().add(subMenu1); 
                if (lViewMenu2) root.getChildren().add(subMenu2);
                if (lViewMenu3) root.getChildren().add(subMenu3); 
                if (lViewMenu4) root.getChildren().add(subMenu4);
                if (lViewMenu5) root.getChildren().add(subMenu5); 
                if (lViewMenu6) root.getChildren().add(subMenu6);
                if (lViewMenu7) root.getChildren().add(subMenu7); 
                if (lViewMenu8) root.getChildren().add(subMenu8);
                if (lViewMenu9) root.getChildren().add(subMenu9); 
                if (lViewMenu10) root.getChildren().add(subMenu10);
                if (lViewMenu11) root.getChildren().add(subMenu11);
                
                jfxPanelContabilidad.setScene(scene);
            } catch (SQLException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    rsSubTree2.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void crearSceneMenuMaestros(){
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/bar_button_menu.fxml"));
            HBox root = null;
            JFXButton subMenu1 = new JFXButton(); JFXButton subMenu2 = new JFXButton();
            JFXButton subMenu3 = new JFXButton(); JFXButton subMenu4 = new JFXButton();
            JFXButton subMenu5 = new JFXButton(); JFXButton subMenu6 = new JFXButton();
            JFXButton subMenu7 = new JFXButton(); JFXButton subMenu8 = new JFXButton();
            JFXButton subMenu9 = new JFXButton(); JFXButton subMenu10 = new JFXButton();
            JFXButton subMenu11 = new JFXButton(); JFXButton subMenu12 = new JFXButton();
            JFXButton subMenu = new JFXButton();
            
            boolean lViewMenu1 = false, lViewMenu2 = false, lViewMenu3 = false, lViewMenu4 = false, lViewMenu5 = false,
                    lViewMenu6 = false, lViewMenu7 = false, lViewMenu8 = false, lViewMenu9 = false, lViewMenu10 = false,
                    lViewMenu11 = false, lViewMenu12 = false;
            
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            root.setSpacing(10);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(MenuPrincipal.class.getResource("/Vista/styleLogin.css").toExternalForm());

            DropShadow shadow = new DropShadow();
                
            try{
                rsSubTree2 = controladorCategorias.subMenuTreePrincipal("17");
                rsSubTree2.beforeFirst();

                while(rsSubTree2.next()) {
                    System.err.println(rsSubTree2.getString("MEN_NOMBRE"));
                    switch (rsSubTree2.getString("MEN_NOMBRE")){
                        case "Empresa":
                            Image image = new Image(getClass().getResourceAsStream("/imagenes/company_menu_bar_3.png"));
                            subMenu1.setGraphic(new ImageView(image));
                            subMenu1.setPrefSize(95, 95);
                            subMenu1.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu1.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu1.setContentDisplay(ContentDisplay.TOP);
                            //subMenu1.setEffect(shadow);
                            
                            EventHandler actionListener = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Empresa");
                                nombreMenu = "Empresa";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnMaestrosSitema);
                            };
                            
                            subMenu1.setOnAction(actionListener);
                            
                            lViewMenu1 = true;

                            break;
                        case "Identificador de Equipos":
                            Image image1 = new Image(getClass().getResourceAsStream("/imagenes/id_equipo_menu_bar.png"));
                            subMenu2.setGraphic(new ImageView(image1));
                            subMenu2.setPrefSize(95, 95);
                            subMenu2.setText("Identificador\nde Equipos");
                            subMenu2.setTextAlignment(TextAlignment.CENTER);
                            subMenu2.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            EventHandler actionListener1 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Identificador de Equipos");
                                nombreMenu = "Identificador de Equipos";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnMaestrosSitema);
                            };
                            
                            subMenu2.setOnAction(actionListener1);
                            
                            lViewMenu2 = true;

                            break;
                        case "Consultas SQL":
                            Image image2 = new Image(getClass().getResourceAsStream("/imagenes/conuslta_sql_menu_bar_2.png"));
                            subMenu3.setGraphic(new ImageView(image2));
                            subMenu3.setPrefSize(95, 95);
                            subMenu3.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu3.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu3.setContentDisplay(ContentDisplay.TOP);
                            //subMenu3.setEffect(shadow);
                            
                            EventHandler actionListener2 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Consultas SQL");
                                nombreMenu = "Consultas SQL";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnMaestrosSitema);
                            };
                            
                            subMenu3.setOnAction(actionListener2);
                            
                            lViewMenu3 = true;

                            break;
                        case "Auditorias":
                            Image image3 = new Image(getClass().getResourceAsStream("/imagenes/auditoria_menu_bar.png"));
                            subMenu4.setGraphic(new ImageView(image3));
                            subMenu4.setPrefSize(95, 95);
                            subMenu4.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu4.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu4.setContentDisplay(ContentDisplay.TOP);
                            //subMenu4.setEffect(shadow);
                            
                            EventHandler actionListener3 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Auditorias");
                                nombreMenu = "Auditorias";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnMaestrosSitema);
                            };
                            
                            subMenu4.setOnAction(actionListener3);
                            
                            lViewMenu4 = true;

                            break;
                        case "Descuentos":
                            Image image4 = new Image(getClass().getResourceAsStream("/imagenes/descuestos_menu_bar.png"));
                            subMenu5.setGraphic(new ImageView(image4));
                            subMenu5.setPrefSize(95, 95);
                            subMenu5.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu5.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu5.setContentDisplay(ContentDisplay.TOP);
                            //subMenu5.setEffect(shadow);
                            
                            EventHandler actionListener4 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Descuentos");
                                nombreMenu = "Descuentos";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnMaestrosSitema);
                            };
                            
                            subMenu5.setOnAction(actionListener4);
                            
                            lViewMenu5 = true;

                            break;
                        case "Tipo de Contactos":
                            Image image5 = new Image(getClass().getResourceAsStream("/imagenes/tipo_comtactos_menu_bar_2.png"));
                            subMenu6.setGraphic(new ImageView(image5));
                            subMenu6.setPrefSize(95, 95);
                            subMenu6.setText("Tipo de\nContactos");
                            subMenu6.setTextAlignment(TextAlignment.CENTER);
                            subMenu6.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu6.setContentDisplay(ContentDisplay.TOP);
                            //subMenu6.setEffect(shadow);
                            
                            EventHandler actionListener5 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Tipo de Contactos");
                                nombreMenu = "Tipo de Contactos";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnMaestrosSitema);
                            };
                            
                            subMenu6.setOnAction(actionListener5);
                            
                            lViewMenu6 = true;

                            break;
                        case "Moneda":
                            Image image6 = new Image(getClass().getResourceAsStream("/imagenes/tipo_moneda_menu_bar.png"));
                            subMenu7.setGraphic(new ImageView(image6));
                            subMenu7.setPrefSize(95, 95);
                            subMenu7.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu7.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu7.setContentDisplay(ContentDisplay.TOP);
                            //subMenu7.setEffect(shadow);
                            
                            EventHandler actionListener6 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Moneda");
                                nombreMenu = "Moneda";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnMaestrosSitema);
                            };
                            
                            subMenu7.setOnAction(actionListener6);
                            
                            lViewMenu7 = true;

                            break;
                        case "Vendedores":
                            Image image7 = new Image(getClass().getResourceAsStream("/imagenes/vendedor_menu_bar.png"));
                            subMenu8.setGraphic(new ImageView(image7));
                            subMenu8.setPrefSize(95, 95);
                            subMenu8.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu8.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu8.setContentDisplay(ContentDisplay.TOP);
                            //subMenu8.setEffect(shadow);
                            
                            EventHandler actionListener7 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Vendedores");
                                nombreMenu = "Vendedores";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnMaestrosSitema);
                            };
                            
                            subMenu8.setOnAction(actionListener7);
                            
                            lViewMenu8 = true;

                            break;
                        case "Personalizar":
                            Image image8 = new Image(getClass().getResourceAsStream("/imagenes/blanco.png"));
                            subMenu9.setGraphic(new ImageView(image8));
                            subMenu9.setPrefSize(95, 95);
                            subMenu9.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu9.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu9.setContentDisplay(ContentDisplay.TOP);
                            //subMenu9.setEffect(shadow);
                            
                            EventHandler actionListener8 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Personalizar");
                                nombreMenu = "Personalizar";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnMaestrosSitema);
                            };
                            
                            subMenu9.setOnAction(actionListener8);
                            
                            lViewMenu9 = true;

                            break;
                        case "Impuestos":
                            Image image9 = new Image(getClass().getResourceAsStream("/imagenes/impuesto_menu_bar.png"));
                            subMenu10.setGraphic(new ImageView(image9));
                            subMenu10.setPrefSize(95, 95);
                            subMenu10.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu10.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu10.setContentDisplay(ContentDisplay.TOP);
                            //subMenu10.setEffect(shadow);
                            
                            EventHandler actionListener9 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Impuestos");
                                nombreMenu = "Impuestos";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnMaestrosSitema);
                            };
                            
                            subMenu10.setOnAction(actionListener9);
                            
                            lViewMenu10 = true;

                            break;
                        case "Config. Import. Documentos":
                            Image image10 = new Image(getClass().getResourceAsStream("/imagenes/cierre_fiscal_menu_bar.png"));
                            subMenu11.setGraphic(new ImageView(image10));
                            subMenu11.setPrefSize(105, 95);
                            subMenu11.setText("Config. Import.\nDocumentos");
                            subMenu11.setTextAlignment(TextAlignment.CENTER);
                            subMenu11.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu11.setContentDisplay(ContentDisplay.TOP);
                            //subMenu11.setEffect(shadow);
                            
                            EventHandler actionListener10 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Config. Import. Documentos");
                                nombreMenu = "Config. Import. Documentos";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnMaestrosSitema);
                            };
                            
                            subMenu11.setOnAction(actionListener10);
                            
                            lViewMenu11 = true;

                            break;
                        case "Configurar Caja":
                            Image image11 = new Image(getClass().getResourceAsStream("/imagenes/configurar_caja_menu_bar.png"));
                            subMenu12.setGraphic(new ImageView(image11));
                            subMenu12.setPrefSize(95, 95);
                            subMenu12.setText("Configurar\nCaja");
                            subMenu12.setTextAlignment(TextAlignment.CENTER);
                            subMenu12.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu12.setContentDisplay(ContentDisplay.TOP);
                            //subMenu11.setEffect(shadow);
                            
                            EventHandler actionListener11 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                //actionMenu("Configurar Caja");
                                nombreMenu = "Configurar Caja";
                                
                                taskActionMenu = new TaskActionMenu();
                                taskActionMenu.execute();
                                
                                cerrarMenuVentas(panelBtnMaestrosSitema);
                            };
                            
                            subMenu12.setOnAction(actionListener11);
                            
                            lViewMenu12 = true;

                            break;
                    }
                }
                
                Image imageBack = new Image(getClass().getResourceAsStream("/imagenes/arrows_back_menu_bar_32.png"));
                ImageView imageViewBack = new ImageView(imageBack);
                imageViewBack.setFitWidth(15);
                imageViewBack.setFitHeight(30);
                subMenu.setGraphic(imageViewBack);
                subMenu.setPrefSize(10, 95);
                //subMenu.setContentDisplay(ContentDisplay.TOP);
                //subMenu.setEffect(shadow);

                EventHandler actionListener12 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                    btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
                    btnInventario.setForeground(new java.awt.Color(0, 0, 0));
                    btnVentas.setForeground(new java.awt.Color(0, 0, 0));
                    btnCompras.setForeground(new java.awt.Color(0, 0, 0));
                    btnTaller.setForeground(new java.awt.Color(0, 0, 0));
                    btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
                    btnMaestros.setForeground(new java.awt.Color(0, 0, 0));

                    Animacion.Animacion.subir(20, -204, 12, 11, panelBtnMaestrosSitema);
                    btnCbtesElectronicos.setVisible(true);

                    lMenoShow = false;
                };

                subMenu.setOnAction(actionListener12);
                            
                root.getChildren().add(subMenu);
                if (lViewMenu1) root.getChildren().add(subMenu1); 
                if (lViewMenu2) root.getChildren().add(subMenu2);
                if (lViewMenu3) root.getChildren().add(subMenu3); 
                if (lViewMenu4) root.getChildren().add(subMenu4);
                if (lViewMenu5) root.getChildren().add(subMenu5); 
                if (lViewMenu6) root.getChildren().add(subMenu6);
                if (lViewMenu7) root.getChildren().add(subMenu7); 
                if (lViewMenu8) root.getChildren().add(subMenu8);
                if (lViewMenu9) root.getChildren().add(subMenu9); 
                if (lViewMenu10) root.getChildren().add(subMenu10);
                if (lViewMenu11) root.getChildren().add(subMenu11);
                if (lViewMenu12) root.getChildren().add(subMenu12);
                
                jfxPanelMaestrosSistema.setScene(scene);
            } catch (SQLException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    rsSubTree2.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void crearSceneSubUsuarios(){
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/bar_button_menu.fxml"));
            HBox root = null;
            JFXButton subMenu1 = new JFXButton(); JFXButton subMenu2 = new JFXButton();
            JFXButton subMenu3 = new JFXButton(); JFXButton subMenu4 = new JFXButton();
            JFXButton subMenu = new JFXButton();
            
            boolean lViewMenu1 = false, lViewMenu2 = false, lViewMenu3 = false, lViewMenu4 = false;
            
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            root.setSpacing(10);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(MenuPrincipal.class.getResource("/Vista/styleLogin.css").toExternalForm());

            DropShadow shadow = new DropShadow();
                
            try{
                rsSubTree2 = controladorCategorias.subMenuTreePrincipal("10");
                rsSubTree2.beforeFirst();

                while(rsSubTree2.next()) {
                    switch (rsSubTree2.getString("MEN_NOMBRE")){
                        case "Usuarios":
                            Image image = new Image(getClass().getResourceAsStream("/imagenes/usuarios_menu_bar.png"));
                            subMenu1.setGraphic(new ImageView(image));
                            subMenu1.setPrefSize(95, 95);
                            subMenu1.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu1.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu1.setContentDisplay(ContentDisplay.TOP);
                            //subMenu1.setEffect(shadow);
                            
                            EventHandler actionListener = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Usuarios");
                            };
                            
                            subMenu1.setOnAction(actionListener);
                            
                            lViewMenu1 = true;

                            break;
                        case "Grupos de Permisos":
                            Image image1 = new Image(getClass().getResourceAsStream("/imagenes/grupo_permisos_menu_bar.png"));
                            subMenu2 = new JFXButton();
                            subMenu2.setGraphic(new ImageView(image1));
                            subMenu2.setPrefSize(95, 95);
                            subMenu2.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu2.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            EventHandler actionListener1 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Grupos de Permisos");
                            };
                            
                            subMenu2.setOnAction(actionListener1);
                            
                            lViewMenu2 = true;

                            break;
                        case "Seguridad":
                            Image image2 = new Image(getClass().getResourceAsStream("/imagenes/seguridad_menu_bar.png"));
                            subMenu3.setGraphic(new ImageView(image2));
                            subMenu3.setPrefSize(95, 95);
                            subMenu3.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu3.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu3.setContentDisplay(ContentDisplay.TOP);
                            //subMenu3.setEffect(shadow);
                            
                            EventHandler actionListener2 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Seguridad");
                            };
                            
                            subMenu3.setOnAction(actionListener2);
                            
                            lViewMenu3 = true;

                            break;
                        case "Usuarios por Empresas":
                            Image image3 = new Image(getClass().getResourceAsStream("/imagenes/folder_user_icon_24.png"));
                            subMenu4.setGraphic(new ImageView(image3));
                            subMenu4.setPrefSize(95, 95);
                            subMenu4.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu4.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu4.setContentDisplay(ContentDisplay.TOP);
                            //subMenu4.setEffect(shadow);
                            
                            EventHandler actionListener3 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Usuarios por Empresas");
                            };
                            
                            subMenu4.setOnAction(actionListener3);
                            
                            lViewMenu4 = true;

                            break;
                    }
                }
                
                Image imageBack = new Image(getClass().getResourceAsStream("/imagenes/arrows_back_menu_bar_32.png"));
                ImageView imageViewBack = new ImageView(imageBack);
                imageViewBack.setFitWidth(15);
                imageViewBack.setFitHeight(30);
                subMenu.setGraphic(imageViewBack);
                subMenu.setPrefSize(10, 95);
                //subMenu.setContentDisplay(ContentDisplay.TOP);
                //subMenu.setEffect(shadow);

                EventHandler actionListener4 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                    if (lMenu1 && (lSubMenu1 || lSubMenu2)){
                        createSceneMenuConfig();
                        lSubMenu1 = false; lSubMenu2 = false;
                    }else{
                        btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
                        btnInventario.setForeground(new java.awt.Color(0, 0, 0));
                        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
                        btnCompras.setForeground(new java.awt.Color(0, 0, 0));
                        btnTaller.setForeground(new java.awt.Color(0, 0, 0));
                        btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
                        btnMaestros.setForeground(new java.awt.Color(0, 0, 0));

                        Animacion.Animacion.subir(20, -204, 12, 11, panelBtn);
                        btnCbtesElectronicos.setVisible(true);
                    }

                    lMenoShow = false;
                };

                subMenu.setOnAction(actionListener4);
                            
                root.getChildren().add(subMenu);
                if (lViewMenu1) root.getChildren().add(subMenu1); 
                if (lViewMenu2) root.getChildren().add(subMenu2);
                if (lViewMenu3) root.getChildren().add(subMenu3); 
                if (lViewMenu4) root.getChildren().add(subMenu4);
                
                jfxPanel.setScene(scene);
            } catch (SQLException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    rsSubTree2.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void crearSceneSubMenuTaller(){
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/bar_button_menu.fxml"));
            HBox root = null;
            JFXButton subMenu1 = new JFXButton(); JFXButton subMenu2 = new JFXButton();
            JFXButton subMenu3 = new JFXButton(); JFXButton subMenu4 = new JFXButton();
            JFXButton subMenu5 = new JFXButton(); JFXButton subMenu6 = new JFXButton(); 
            JFXButton subMenu = new JFXButton();
            
            boolean lViewMenu1 = false, lViewMenu2 = false, lViewMenu3 = false, lViewMenu4 = false, lViewMenu5 = false, lViewMenu6 = false;
            
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            root.setSpacing(10);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(MenuPrincipal.class.getResource("/Vista/styleLogin.css").toExternalForm());

            DropShadow shadow = new DropShadow();
                
            try{
                rsSubTree2 = controladorCategorias.subMenuTreePrincipal("89");
                rsSubTree2.beforeFirst();

                while(rsSubTree2.next()) {
                    switch (rsSubTree2.getString("MEN_NOMBRE")){
                        case "Técnicos":
                            Image image = new Image(getClass().getResourceAsStream("/imagenes/mecanico.png"));
                            subMenu1.setGraphic(new ImageView(image));
                            //subMenu1.setMaxSize(95, 95);
                            subMenu1.setPrefSize(95, 95);
                            subMenu1.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu1.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu1.setContentDisplay(ContentDisplay.TOP);
                            //subMenu1.setEffect(shadow);
                            
                            EventHandler actionListener = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Técnicos");
                            };
                            
                            subMenu1.setOnAction(actionListener);
                            
                            lViewMenu1 = true;

                            break;
                        case "TÃ©cnicos":
                            Image image99 = new Image(getClass().getResourceAsStream("/imagenes/mecanico.png"));
                            subMenu1.setGraphic(new ImageView(image99));
                            //subMenu1.setMaxSize(95, 95);
                            subMenu1.setPrefSize(95, 95);
                            subMenu1.setText("Técnicos");
                            subMenu1.setTooltip(new Tooltip("Técnicos"));
                            subMenu1.setContentDisplay(ContentDisplay.TOP);
                            //subMenu1.setEffect(shadow);
                            
                            EventHandler actionListener99 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Técnicos");
                            };
                            
                            subMenu1.setOnAction(actionListener99);
                            
                            lViewMenu1 = true;

                            break;
//                        case "Marcas de Vehículos":
//                            Image image1 = new Image(getClass().getResourceAsStream("/imagenes/marcas_menu_bar.png"));
//                            subMenu2 = new JFXButton();
//                            subMenu2.setGraphic(new ImageView(image1));
//                            subMenu2.setPrefSize(95, 95);
//                            subMenu2.setText(rsSubTree2.getString("MEN_NOMBRE"));
//                            subMenu2.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
//                            subMenu2.setContentDisplay(ContentDisplay.TOP);
//                            //subMenu2.setEffect(shadow);
//                            
//                            EventHandler actionListener1 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
//                                actionMenu("Marcas de Vehículos");
//                            };
//                            
//                            subMenu2.setOnAction(actionListener1);
//                            
//                            lViewMenu2 = true;
//
//                            break;
                        case "Modelos de Vehículos":
                            Image image2 = new Image(getClass().getResourceAsStream("/imagenes/modelos_vehiculos_menu_bar_3.png"));
                            subMenu3.setGraphic(new ImageView(image2));
                            subMenu3.setPrefSize(95, 95);
                            subMenu3.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu3.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu3.setContentDisplay(ContentDisplay.TOP);
                            //subMenu3.setEffect(shadow);
                            
                            EventHandler actionListener2 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Modelos de Vehículos");
                            };
                            
                            subMenu3.setOnAction(actionListener2);
                            
                            lViewMenu3 = true;

                            break;
                        case "Modelos de VehÃ­culos":
                            Image image22 = new Image(getClass().getResourceAsStream("/imagenes/modelos_vehiculos_menu_bar_3.png"));
                            subMenu3.setGraphic(new ImageView(image22));
                            subMenu3.setPrefSize(95, 95);
                            subMenu3.setText("Modelos de Vehículos");
                            subMenu3.setTooltip(new Tooltip("Modelos de Vehículos"));
                            subMenu3.setContentDisplay(ContentDisplay.TOP);
                            //subMenu3.setEffect(shadow);
                            
                            EventHandler actionListener22 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Modelos de Vehículos");
                            };
                            
                            subMenu3.setOnAction(actionListener22);
                            
                            lViewMenu3 = true;

                            break;
                        case "Categorias de Vehículos":
                            Image image3 = new Image(getClass().getResourceAsStream("/imagenes/blanco.png"));
                            subMenu4.setGraphic(new ImageView(image3));
                            subMenu4.setPrefSize(95, 95);
                            subMenu4.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu4.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu4.setContentDisplay(ContentDisplay.TOP);
                            //subMenu4.setEffect(shadow);
                            
                            EventHandler actionListener3 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Categorias de Vehículos");
                            };
                            
                            subMenu4.setOnAction(actionListener3);
                            
                            lViewMenu4 = true;

                            break;
                        case "Categorias de VehÃ­culos":
                            Image image33 = new Image(getClass().getResourceAsStream("/imagenes/blanco.png"));
                            subMenu4.setGraphic(new ImageView(image33));
                            subMenu4.setPrefSize(95, 95);
                            subMenu4.setText("Categorias de Vehículos");
                            subMenu4.setTooltip(new Tooltip("Categorias de Vehículos"));
                            subMenu4.setContentDisplay(ContentDisplay.TOP);
                            //subMenu4.setEffect(shadow);
                            
                            EventHandler actionListener33 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Categorias de Vehículos");
                            };
                            
                            subMenu4.setOnAction(actionListener33);
                            
                            lViewMenu4 = true;

                            break;
//                        case "Vehículos":
//                            Image image4 = new Image(getClass().getResourceAsStream("/imagenes/talle_menu_bar_2.png"));
//                            subMenu5.setGraphic(new ImageView(image4));
//                            subMenu5.setPrefSize(95, 95);
//                            subMenu5.setText(rsSubTree2.getString("MEN_NOMBRE"));
//                            subMenu5.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
//                            subMenu5.setContentDisplay(ContentDisplay.TOP);
//                            //subMenu5.setEffect(shadow);
//                            
//                            EventHandler actionListener4 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
//                                actionMenu("Vehículos");
//                            };
//                            
//                            subMenu5.setOnAction(actionListener4);
//                            
//                            lViewMenu5 = true;
//
//                            break;
//                        case "Otros":
//                            Image image5 = new Image(getClass().getResourceAsStream("/imagenes/otros_car_menu_bar.png"));
//                            subMenu6.setGraphic(new ImageView(image5));
//                            subMenu6.setPrefSize(95, 95);
//                            subMenu6.setText(rsSubTree2.getString("MEN_NOMBRE"));
//                            subMenu6.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
//                            subMenu6.setContentDisplay(ContentDisplay.TOP);
//                            //subMenu6.setEffect(shadow);
//                            
//                            EventHandler actionListener5 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
//                                crearSceneSubMenuOtrosTaller();
//                                
//                                lSubSubMenu1 = true;
//                            };
//                            
//                            subMenu6.setOnAction(actionListener5);
//                            
//                            lViewMenu6 = true;
//
//                            break;
                    }
                }
                
                Image imageBack = new Image(getClass().getResourceAsStream("/imagenes/arrows_back_menu_bar_32.png"));
                ImageView imageViewBack = new ImageView(imageBack);
                imageViewBack.setFitWidth(15);
                imageViewBack.setFitHeight(30);
                subMenu.setGraphic(imageViewBack);
                subMenu.setPrefSize(10, 95);
                //subMenu.setContentDisplay(ContentDisplay.TOP);
                //subMenu.setEffect(shadow);

                EventHandler actionListener6 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                    if (lMenu5 && (lSubMenu1 || lSubMenu2)){
                        if (lMenu5 && lSubSubMenu1){
                            crearSceneSubMenuTaller();
                            lSubSubMenu1 = false;
                        }else{
                            crearSceneMenuTaller();
                            lSubMenu1 = false; lSubMenu2 = false;
                        }
                    }else{
                        btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
                        btnInventario.setForeground(new java.awt.Color(0, 0, 0));
                        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
                        btnCompras.setForeground(new java.awt.Color(0, 0, 0));
                        btnTaller.setForeground(new java.awt.Color(0, 0, 0));
                        btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
                        btnMaestros.setForeground(new java.awt.Color(0, 0, 0));

                        Animacion.Animacion.subir(20, -204, 12, 11, panelBtn);
                        btnCbtesElectronicos.setVisible(true);
                    }

                    lMenoShow = false;
                };

                subMenu.setOnAction(actionListener6);
                            
                root.getChildren().add(subMenu);
                if (lViewMenu1) root.getChildren().add(subMenu1); 
                if (lViewMenu2) root.getChildren().add(subMenu2);
                if (lViewMenu3) root.getChildren().add(subMenu3); 
                if (lViewMenu4) root.getChildren().add(subMenu4);
                if (lViewMenu5) root.getChildren().add(subMenu5);
                if (lViewMenu6) root.getChildren().add(subMenu6);
                
                jfxPanel.setScene(scene);
            } catch (SQLException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    rsSubTree2.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void crearSceneSubMenuAnulaciones(){
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/bar_button_menu.fxml"));
            HBox root = null;
            JFXButton subMenu1 = new JFXButton(); JFXButton subMenu2 = new JFXButton();
            JFXButton subMenu3 = new JFXButton(); JFXButton subMenu4 = new JFXButton();
            JFXButton subMenu = new JFXButton();
            
            boolean lViewMenu1 = false, lViewMenu2 = false, lViewMenu3 = false, lViewMenu4 = false, lViewMenu5 = false;
            
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            root.setSpacing(10);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(MenuPrincipal.class.getResource("/Vista/styleLogin.css").toExternalForm());

            DropShadow shadow = new DropShadow();
                
            try{
                rsSubTree2 = controladorCategorias.subMenuTreePrincipal("106");
                rsSubTree2.beforeFirst();
            
                while(rsSubTree2.next()) {
                    switch (rsSubTree2.getString("MEN_NOMBRE")){
                        case "Anular Requisición de Productos":
                            Image image = new Image(getClass().getResourceAsStream("/imagenes/anulacion_documentos_menu_bar.png"));
                            subMenu1.setGraphic(new ImageView(image));
                            //subMenu1.setMaxSize(95, 95);
                            subMenu1.setPrefSize(95, 95);
                            subMenu1.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu1.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu1.setContentDisplay(ContentDisplay.TOP);
                            //subMenu1.setEffect(shadow);
                            
                            EventHandler actionListener = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Anular Requisición de Productos");
                            };
                            
                            subMenu1.setOnAction(actionListener);
                            
                            lViewMenu1 = true;

                            break;
                        case "Reverso de Despachos":
                            Image image1 = new Image(getClass().getResourceAsStream("/imagenes/anulacion_documentos_menu_bar.png"));
                            subMenu2.setGraphic(new ImageView(image1));
                            subMenu2.setPrefSize(95, 95);
                            subMenu2.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu2.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            EventHandler actionListener1 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Reverso de Despachos");
                            };
                            
                            subMenu2.setOnAction(actionListener1);
                            
                            lViewMenu2 = true;

                            break;
                        case "Anular Carga de Productos":
                            Image image2 = new Image(getClass().getResourceAsStream("/imagenes/anulacion_documentos_menu_bar.png"));
                            subMenu3.setGraphic(new ImageView(image2));
                            subMenu3.setPrefSize(95, 95);
                            subMenu3.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu3.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu3.setContentDisplay(ContentDisplay.TOP);
                            //subMenu3.setEffect(shadow);
                            
                            EventHandler actionListener2 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Anular Carga de Productos");
                            };
                            
                            subMenu3.setOnAction(actionListener2);
                            
                            lViewMenu3 = true;

                            break;
                        case "Reapertura de Orden de Reparación":
                            Image image3 = new Image(getClass().getResourceAsStream("/imagenes/anulacion_documentos_menu_bar.png"));
                            subMenu4.setGraphic(new ImageView(image3));
                            subMenu4.setPrefSize(95, 95);
                            subMenu4.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu4.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu4.setContentDisplay(ContentDisplay.TOP);
                            //subMenu4.setEffect(shadow);
                            
                            EventHandler actionListener3 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Reapertura de Orden de Reparación");
                            };
                            
                            subMenu4.setOnAction(actionListener3);
                            
                            lViewMenu4 = true;

                            break;
                        case "Reapertura de Orden de ReparaciÃ³n":
                            Image image33 = new Image(getClass().getResourceAsStream("/imagenes/anulacion_documentos_menu_bar.png"));
                            subMenu4.setGraphic(new ImageView(image33));
                            subMenu4.setPrefSize(95, 95);
                            subMenu4.setText("Reapertura de Orden de ReparaciÃ³n");
                            subMenu4.setTooltip(new Tooltip("Reapertura de Orden de ReparaciÃ³n"));
                            subMenu4.setContentDisplay(ContentDisplay.TOP);
                            //subMenu4.setEffect(shadow);
                            
                            EventHandler actionListener33 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Reapertura de Orden de Reparación");
                            };
                            
                            subMenu4.setOnAction(actionListener33);
                            
                            lViewMenu4 = true;

                            break;
                    }
                }
            
                Image imageBack = new Image(getClass().getResourceAsStream("/imagenes/arrows_back_menu_bar_32.png"));
                ImageView imageViewBack = new ImageView(imageBack);
                imageViewBack.setFitWidth(15);
                imageViewBack.setFitHeight(30);
                subMenu.setGraphic(imageViewBack);
                subMenu.setPrefSize(10, 95);
                //subMenu.setContentDisplay(ContentDisplay.TOP);
                //subMenu.setEffect(shadow);

                EventHandler actionListener5 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                    if (lMenu5 && (lSubMenu1 || lSubMenu2)){
                        if (lMenu5 && lSubSubMenu1){
                            crearSceneSubMenuTaller();
                            lSubSubMenu1 = false;
                        }else{
                            crearSceneMenuTaller();
                            lSubMenu1 = false; lSubMenu2 = false;
                        }
                    }else{
                        btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
                        btnInventario.setForeground(new java.awt.Color(0, 0, 0));
                        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
                        btnCompras.setForeground(new java.awt.Color(0, 0, 0));
                        btnTaller.setForeground(new java.awt.Color(0, 0, 0));
                        btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
                        btnMaestros.setForeground(new java.awt.Color(0, 0, 0));

                        Animacion.Animacion.subir(20, -204, 12, 11, panelBtn);
                        btnCbtesElectronicos.setVisible(true);
                    }

                    lMenoShow = false;
                };

                subMenu.setOnAction(actionListener5);
                            
                root.getChildren().add(subMenu);
                if (lViewMenu1) root.getChildren().add(subMenu1); 
                if (lViewMenu2) root.getChildren().add(subMenu2);
                if (lViewMenu3) root.getChildren().add(subMenu3); 
                if (lViewMenu4) root.getChildren().add(subMenu4);
                
                jfxPanel.setScene(scene);
            } catch (SQLException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    rsSubTree2.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void crearSceneSubMenuOtrosTaller(){
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/bar_button_menu.fxml"));
            HBox root = null;
            JFXButton subMenu1 = new JFXButton(); JFXButton subMenu2 = new JFXButton();
            JFXButton subMenu3 = new JFXButton(); JFXButton subMenu4 = new JFXButton();
            JFXButton subMenu5 = new JFXButton(); JFXButton subMenu6 = new JFXButton(); 
            JFXButton subMenu = new JFXButton();
            
            boolean lViewMenu1 = false, lViewMenu2 = false, lViewMenu3 = false, lViewMenu4 = false, lViewMenu5 = false, lViewMenu6 = false;
            
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            root.setSpacing(10);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(MenuPrincipal.class.getResource("/Vista/styleLogin.css").toExternalForm());

            DropShadow shadow = new DropShadow();
                
            try{
                rsSubTree2 = controladorCategorias.subMenuTreePrincipal("95");
                rsSubTree2.beforeFirst();

                while(rsSubTree2.next()) {
                    switch (rsSubTree2.getString("MEN_NOMBRE")){
                        case "Motor":
                            Image image = new Image(getClass().getResourceAsStream("/imagenes/motor_menu_bar_3.png"));
                            subMenu1.setGraphic(new ImageView(image));
                            //subMenu1.setMaxSize(95, 95);
                            subMenu1.setPrefSize(95, 95);
                            subMenu1.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu1.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu1.setContentDisplay(ContentDisplay.TOP);
                            //subMenu1.setEffect(shadow);
                            
                            EventHandler actionListener = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Motor");
                            };
                            
                            subMenu1.setOnAction(actionListener);
                            
                            lViewMenu1 = true;

                            break;
                        case "Transmisión":
                            Image image1 = new Image(getClass().getResourceAsStream("/imagenes/transmision_menu_bar.png"));
                            subMenu2 = new JFXButton();
                            subMenu2.setGraphic(new ImageView(image1));
                            subMenu2.setPrefSize(95, 95);
                            subMenu2.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu2.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            EventHandler actionListener1 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Transmisión");
                            };
                            
                            subMenu2.setOnAction(actionListener1);
                            
                            lViewMenu2 = true;

                            break;
                        case "TransmisiÃ³n":
                            Image image15 = new Image(getClass().getResourceAsStream("/imagenes/transmision_menu_bar.png"));
                            subMenu2 = new JFXButton();
                            subMenu2.setGraphic(new ImageView(image15));
                            subMenu2.setPrefSize(95, 95);
                            subMenu2.setText("Transmisión");
                            subMenu2.setTooltip(new Tooltip("Transmisión"));
                            subMenu2.setContentDisplay(ContentDisplay.TOP);
                            //subMenu2.setEffect(shadow);
                            
                            EventHandler actionListener15 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Transmisión");
                            };
                            
                            subMenu2.setOnAction(actionListener15);
                            
                            lViewMenu2 = true;

                            break;
                        case "Tracción":
                            Image image2 = new Image(getClass().getResourceAsStream("/imagenes/traccion_menu_bar.png"));
                            subMenu3.setGraphic(new ImageView(image2));
                            subMenu3.setPrefSize(95, 95);
                            subMenu3.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu3.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu3.setContentDisplay(ContentDisplay.TOP);
                            //subMenu3.setEffect(shadow);
                            
                            EventHandler actionListener2 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Tracción");
                            };
                            
                            subMenu3.setOnAction(actionListener2);
                            
                            lViewMenu3 = true;

                            break;
                        case "TracciÃ³n":
                            Image image22 = new Image(getClass().getResourceAsStream("/imagenes/traccion_menu_bar.png"));
                            subMenu3.setGraphic(new ImageView(image22));
                            subMenu3.setPrefSize(95, 95);
                            subMenu3.setText("Tracción");
                            subMenu3.setTooltip(new Tooltip("Tracción"));
                            subMenu3.setContentDisplay(ContentDisplay.TOP);
                            //subMenu3.setEffect(shadow);
                            
                            EventHandler actionListener22 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Tracción");
                            };
                            
                            subMenu3.setOnAction(actionListener22);
                            
                            lViewMenu3 = true;

                            break;
                        case "Tipo de Cilindrada":
                            Image image3 = new Image(getClass().getResourceAsStream("/imagenes/pistones_menu_bar.png"));
                            subMenu4.setGraphic(new ImageView(image3));
                            subMenu4.setPrefSize(95, 95);
                            subMenu4.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu4.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu4.setContentDisplay(ContentDisplay.TOP);
                            //subMenu4.setEffect(shadow);
                            
                            EventHandler actionListener3 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Tipo de Cilindrada");
                            };
                            
                            subMenu4.setOnAction(actionListener3);
                            
                            lViewMenu4 = true;

                            break;
                        case "Modelos de Cilindrada":
                            Image image4 = new Image(getClass().getResourceAsStream("/imagenes/modelo_cilindrada_menu_bar_2.png"));
                            subMenu5.setGraphic(new ImageView(image4));
                            subMenu5.setPrefSize(95, 95);
                            subMenu5.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu5.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu5.setContentDisplay(ContentDisplay.TOP);
                            //subMenu5.setEffect(shadow);
                            
                            EventHandler actionListener4 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Modelos de Cilindrada");
                            };
                            
                            subMenu5.setOnAction(actionListener4);
                            
                            lViewMenu5 = true;

                            break;
                        case "Combustible":
                            Image image5 = new Image(getClass().getResourceAsStream("/imagenes/combustible_menu_bar.png"));
                            subMenu6.setGraphic(new ImageView(image5));
                            subMenu6.setPrefSize(95, 95);
                            subMenu6.setText(rsSubTree2.getString("MEN_NOMBRE"));
                            subMenu6.setTooltip(new Tooltip(rsSubTree2.getString("MEN_NOMBRE")));
                            subMenu6.setContentDisplay(ContentDisplay.TOP);
                            //subMenu6.setEffect(shadow);
                            
                            EventHandler actionListener5 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                                actionMenu("Combustible");
                            };
                            
                            subMenu6.setOnAction(actionListener5);
                            
                            lViewMenu6 = true;

                            break;
                    }
                }
                
                Image imageBack = new Image(getClass().getResourceAsStream("/imagenes/arrows_back_menu_bar_32.png"));
                ImageView imageViewBack = new ImageView(imageBack);
                imageViewBack.setFitWidth(15);
                imageViewBack.setFitHeight(30);
                subMenu.setGraphic(imageViewBack);
                subMenu.setPrefSize(10, 95);
                //subMenu.setContentDisplay(ContentDisplay.TOP);
                //subMenu.setEffect(shadow);

                EventHandler actionListener6 = (EventHandler<javafx.event.ActionEvent>) (javafx.event.ActionEvent event) -> {
                    if (lMenu5 && (lSubMenu1 || lSubMenu2)){
                        if (lMenu5 && lSubSubMenu1){
//                            crearSceneSubMenuTaller();
                            crearSceneMenuTaller();
                            lSubSubMenu1 = false;
                        }else{
                            crearSceneMenuTaller();
                            lSubMenu1 = false; lSubMenu2 = false;
                        }
                    }else{
                        btnConfiguracion.setForeground(new java.awt.Color(0, 0, 0));
                        btnInventario.setForeground(new java.awt.Color(0, 0, 0));
                        btnVentas.setForeground(new java.awt.Color(0, 0, 0));
                        btnCompras.setForeground(new java.awt.Color(0, 0, 0));
                        btnTaller.setForeground(new java.awt.Color(0, 0, 0));
                        btnContabilidad.setForeground(new java.awt.Color(0, 0, 0));
                        btnMaestros.setForeground(new java.awt.Color(0, 0, 0));

                        Animacion.Animacion.subir(20, -204, 12, 11, panelBtn);
                        btnCbtesElectronicos.setVisible(true);
                    }

                    lMenoShow = false;
                };

                subMenu.setOnAction(actionListener6);
                            
                root.getChildren().add(subMenu);
                if (lViewMenu1) root.getChildren().add(subMenu1); 
                if (lViewMenu2) root.getChildren().add(subMenu2);
                if (lViewMenu3) root.getChildren().add(subMenu3); 
                if (lViewMenu4) root.getChildren().add(subMenu4);
                if (lViewMenu5) root.getChildren().add(subMenu5);
                if (lViewMenu6) root.getChildren().add(subMenu6);
                
                jfxPanel.setScene(scene);
            } catch (SQLException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    rsSubTree2.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void llamaReportesFormFacturacion(String tipoDoc, String form){
//        MaestroDeReportesSistema maestroReportesSistema = new MaestroDeReportesSistema(form, tipoDoc);
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = maestroReportesSistema.getSize();
//        maestroReportesSistema.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(maestroReportesSistema);
//        maestroReportesSistema.toFront();
//        maestroReportesSistema.setVisible(true);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.l2fprod.common.swing.JLinkButton btnCompras;
    private com.l2fprod.common.swing.JLinkButton btnConfiguracion;
    private com.l2fprod.common.swing.JLinkButton btnContabilidad;
    private com.l2fprod.common.swing.JLinkButton btnInventario;
    private com.l2fprod.common.swing.JLinkButton btnMaestros;
    private com.l2fprod.common.swing.JLinkButton btnProduccion;
    private com.l2fprod.common.swing.JLinkButton btnTaller;
    private com.l2fprod.common.swing.JLinkButton btnTesoreria;
    private com.l2fprod.common.swing.JLinkButton btnVentas;
    public static javax.swing.JDesktopPane escritorioERP;
    public static javax.swing.JLabel jLogoBD;
    public static javax.swing.JLabel jLogoCli;
    public static javax.swing.JLabel jLogoEmp;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTreeMenuSistema;
    private javax.swing.JLabel lbHora;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JLabel lbStatusInternet;
    private javax.swing.JPanel panelBtn;
    // End of variables declaration//GEN-END:variables
}