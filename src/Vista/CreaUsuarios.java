package Vista;

import Listener.ListenerBtnCreaUsuarios;
import Modelos.ModelCreaUsuarios;
import static util.ColorApp.colorForm;
import util.SQLSelect;
import Modelos.VariablesGlobales;
import Modelos.conexion;
import static Vista.MenuPrincipal.escritorioERP;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import util.CargaComboBox;
import util.Internacionalizacion;

public class CreaUsuarios extends javax.swing.JInternalFrame {
    public String combo;
    public int fila, atras=-2, adelante=0;
    private SQLSelect Registros;
    private boolean Bandera = false, SinRegistros = false, lAgregar, lModificar, lPass=false;
    private ResultSet rs, rs_count, rs_codigo, rs_permiso;
    private int Reg_count;
    public boolean cbo=false;
    private String codigo="", activo = null, Clave="", origen;
    private JDesktopPane deskPane;
    
    private ImageIcon img=null;
    private JPanel panelprincipal;
    private final String extension="JPG";
    private JLabel limg;
    
    private final conexion conet = new conexion();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final ModelCreaUsuarios modelCreaUsuarios = ModelCreaUsuarios.getModelGrupoPermisos();

    public CreaUsuarios(String origen) {
        initComponents();
        this.origen = origen;
        inicializaClase();
        cargaComboCargos();
        
        jPanel1.setBackground(Color.decode(colorForm));
        
//        jChkOpe_AccAndroid.setBackground(Color.decode(colorForm)); jChkOpe_Accweb.setBackground(Color.decode(colorForm));
        jChkOpe_Activo.setBackground(Color.decode(colorForm));
//        jChkOpe_AccAndroid.setForeground(Color.decode(colorText)); jChkOpe_Accweb.setForeground(Color.decode(colorText));
//        jChkOpe_Activo.setForeground(Color.decode(colorText)); jChkOpe_deldoc.setForeground(Color.decode(colorText));
        
//        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
//        jLabel3.setForeground(Color.decode(colorText)); jLabel4.setForeground(Color.decode(colorText));
//        jLabel5.setForeground(Color.decode(colorText)); jLabel6.setForeground(Color.decode(colorText));
//        jLabel7.setForeground(Color.decode(colorText)); jLabel8.setForeground(Color.decode(colorText));
        
//        jTxtOpe_Numero.setForeground(Color.decode(colorText)); jTxtOpe_Nombre.setForeground(Color.decode(colorText));
//        jTxtOpe_Clave1.setForeground(Color.decode(colorText));
//        jTxtOpe_Usuario.setForeground(Color.decode(colorText)); jTxtOpe_Correo.setForeground(Color.decode(colorText));
//        jTxtOpe_RutaImg.setForeground(Color.decode(colorText));
        
//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        this.setTitle(idioma.loadLangComponent().getString("lbTituloFormUsuario"));
        
        jLabel1.setText(idioma.loadLangComponent().getString("lbCodigo"));
        jLabel2.setText(idioma.loadLangComponent().getString("lbNombre")+" "+idioma.loadLangComponent().getString("lbTituloFormUsuario"));
        jLabel3.setText(idioma.loadLangComponent().getString("lbClave"));
        jLabel4.setText(idioma.loadLangComponent().getString("lbUsuarioSistema"));
        jLabel5.setText(idioma.loadLangComponent().getString("lbCargo"));
        jLabel6.setText(idioma.loadLangComponent().getString("lbCorreo"));
        jLabel7.setText(idioma.loadLangComponent().getString("lbGrupoPerm"));
        jLabel8.setText(idioma.loadLangComponent().getString("lbImgRuta"));
        
        jChkOpe_Activo.setText(idioma.loadLangComponent().getString("chkActivo"));
//        jChkOpe_Accweb.setText(idioma.loadLangComponent().getString("chkAccesoWeb"));
//        jChkOpe_AccAndroid.setText(idioma.loadLangComponent().getString("chkAccesoAndroid"));
        jChkOpe_deldoc.setText(idioma.loadLangComponent().getString("chkDeldoc"));
        
        bt_agregar.setText(idioma.loadLangComponent().getString("btnAgregar")); bt_Modificar.setText(idioma.loadLangComponent().getString("btnModificar"));
        bt_Eliminar.setText(idioma.loadLangComponent().getString("btnEliminar")); bt_Atras.setText(idioma.loadLangComponent().getString("btnAnterior")); 
        bt_Siguiente.setText(idioma.loadLangComponent().getString("btnSiguiente")); bt_salir.setText(idioma.loadLangComponent().getString("btnSalir")); 
        bt_save.setText(idioma.loadLangComponent().getString("btnGrabar")); bt_cancel.setText(idioma.loadLangComponent().getString("btnCancelar"));
        bt_find.setText(idioma.loadLangComponent().getString("btnBuscar")); btn_buscarimg.setText(idioma.loadLangComponent().getString("btnBuscar"));
        
        bt_agregar.setActionCommand("Agregar"); bt_Modificar.setActionCommand("Modificar");
        bt_save.setActionCommand("Grabar"); bt_cancel.setActionCommand("Cancelar");
        bt_Eliminar.setActionCommand("Eliminar"); bt_find.setActionCommand("Buscar");
        bt_Atras.setActionCommand("Anterior"); bt_Siguiente.setActionCommand("Adelante"); 
        bt_salir.setActionCommand("Salir"); btn_buscarimg.setActionCommand("Buscar");
        bt_cargos.setActionCommand("Agregar Cargos");
//-----------------------------------------------------------------------------------------------------------------------

        modelCreaUsuarios.cargaTabla();

        if (Tabla.getRowCount()==0){
            modelCreaUsuarios.habilitar("Inicializa");
            modelCreaUsuarios.posicion_botones_2();
            modelCreaUsuarios.getCodConsecutivo();
            jTxtOpe_Nombre.requestFocus();
            
            lAgregar=true;
        }else{
            modelCreaUsuarios.posicion_botones_1();
            modelCreaUsuarios.deshabilitar();
            bt_save.setVisible(false); bt_cancel.setVisible(false);
            
            modelCreaUsuarios.ejecutaHilo("", false);
            Clave = modelCreaUsuarios.getClaveUser();

            CargaImagen imagen = new CargaImagen();
            imagen.start();
            
            Tabla.getSelectionModel().setSelectionInterval(Tabla.getRowCount()-1, Tabla.getRowCount()-1);
        }
        
        bt_agregar.addActionListener(new ListenerBtnCreaUsuarios());
        bt_Modificar.addActionListener(new ListenerBtnCreaUsuarios());
        bt_save.addActionListener(new ListenerBtnCreaUsuarios());
        bt_Eliminar.addActionListener(new ListenerBtnCreaUsuarios());
        bt_cancel.addActionListener(new ListenerBtnCreaUsuarios());
        bt_find.addActionListener(new ListenerBtnCreaUsuarios());
        bt_Atras.addActionListener(new ListenerBtnCreaUsuarios());
        bt_Siguiente.addActionListener(new ListenerBtnCreaUsuarios());
        bt_salir.addActionListener(new ListenerBtnCreaUsuarios());
        
//        if(origen.equals("Welcome")){
//            deskPane = desktop;
//        }else if(origen.equals("ERP")){
            deskPane = escritorioERP;
//        }else{
//            deskPane = escritorio;
//        }
//        modelCreaUsuarios.setDesktop(desktop);
        modelCreaUsuarios.setOrigen(origen);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        bt_salir = new com.l2fprod.common.swing.JLinkButton();
        bt_agregar = new com.l2fprod.common.swing.JLinkButton();
        bt_Modificar = new com.l2fprod.common.swing.JLinkButton();
        bt_save = new com.l2fprod.common.swing.JLinkButton();
        bt_Eliminar = new com.l2fprod.common.swing.JLinkButton();
        bt_cancel = new com.l2fprod.common.swing.JLinkButton();
        bt_find = new com.l2fprod.common.swing.JLinkButton();
        bt_Atras = new com.l2fprod.common.swing.JLinkButton();
        bt_Siguiente = new com.l2fprod.common.swing.JLinkButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTxtOpe_Numero = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTxtOpe_Nombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTxtOpe_Clave1 = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jTxtOpe_Usuario = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jCboOpe_Cargo = new javax.swing.JComboBox();
        bt_cargos = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTxtOpe_Correo = new javax.swing.JTextField();
        jFondo_Preview = new javax.swing.JLabel();
        jTxtOpe_RutaImg = new javax.swing.JTextField();
        btn_buscarimg = new javax.swing.JButton();
        jChkOpe_deldoc = new javax.swing.JCheckBox();
        jChkOpe_Activo = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jCboOpe_tipo = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();

        jTextField1.setText("jTextField1");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("jButton1");

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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

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

        bt_Atras.setBackground(new java.awt.Color(105, 105, 105));
        bt_Atras.setForeground(new java.awt.Color(255, 255, 255));
        bt_Atras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/previous.png"))); // NOI18N
        bt_Atras.setText("Guardar");
        bt_Atras.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_Atras.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        bt_Siguiente.setBackground(new java.awt.Color(105, 105, 105));
        bt_Siguiente.setForeground(new java.awt.Color(255, 255, 255));
        bt_Siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/next.png"))); // NOI18N
        bt_Siguiente.setText("Guardar");
        bt_Siguiente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_Siguiente.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

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
                .addGap(0, 0, 0)
                .addComponent(bt_Atras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(bt_Siguiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bt_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bt_salir, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                    .addComponent(bt_Atras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_agregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_Modificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_find, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_cancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_Eliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bt_Siguiente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Codigo");

        jLabel2.setText("Nombre Usuario");

        jTxtOpe_Nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtOpe_NombreKeyPressed(evt);
            }
        });

        jLabel3.setText("Contraseña");

        jTxtOpe_Clave1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtOpe_Clave1FocusLost(evt);
            }
        });
        jTxtOpe_Clave1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTxtOpe_Clave1MouseReleased(evt);
            }
        });
        jTxtOpe_Clave1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtOpe_Clave1KeyPressed(evt);
            }
        });

        jLabel4.setText("Usuario del Sistema");

        jTxtOpe_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtOpe_UsuarioKeyPressed(evt);
            }
        });

        jLabel5.setText("Cargo");

        jCboOpe_Cargo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jCboOpe_CargoFocusGained(evt);
            }
        });
        jCboOpe_Cargo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCboOpe_CargoMouseClicked(evt);
            }
        });

        bt_cargos.setText("Agregar Cargos");
        bt_cargos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_cargosActionPerformed(evt);
            }
        });

        jLabel6.setText("Correo Electronico");

        jTxtOpe_Correo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtOpe_CorreoKeyPressed(evt);
            }
        });

        jFondo_Preview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jFondo_Preview.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_buscarimg.setBackground(new java.awt.Color(255, 255, 255));
        btn_buscarimg.setText("Buscar");
        btn_buscarimg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarimgActionPerformed(evt);
            }
        });

        jChkOpe_deldoc.setText("Elimina Documentos");
        jChkOpe_deldoc.setOpaque(false);

        jChkOpe_Activo.setBackground(new java.awt.Color(255, 255, 255));
        jChkOpe_Activo.setText("Activo");

        jLabel7.setText("Grupo de Permisologias de Usuario");

        jCboOpe_tipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCboOpe_tipoKeyPressed(evt);
            }
        });

        jLabel8.setText("Ruta de Imagen");

        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
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
        jScrollPane1.setViewportView(Tabla);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTxtOpe_Numero, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTxtOpe_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(9, 9, 9))
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTxtOpe_Clave1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTxtOpe_Usuario, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jCboOpe_Cargo, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bt_cargos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jFondo_Preview, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jTxtOpe_RutaImg, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_buscarimg))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(jCboOpe_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(96, 96, 96)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jChkOpe_deldoc)
                                        .addComponent(jChkOpe_Activo, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtOpe_Correo, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel2)
                        .addGap(6, 6, 6)
                        .addComponent(jTxtOpe_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtOpe_Clave1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(jLabel4)
                        .addGap(6, 6, 6)
                        .addComponent(jTxtOpe_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel5)
                        .addGap(6, 6, 6)
                        .addComponent(jCboOpe_Cargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bt_cargos))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(11, 11, 11)
                        .addComponent(jTxtOpe_Numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jCboOpe_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jChkOpe_Activo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jChkOpe_deldoc)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTxtOpe_RutaImg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_buscarimg))
                        .addGap(12, 12, 12)
                        .addComponent(jFondo_Preview, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTxtOpe_Correo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        jTabbedPane1.addTab("Usuarios", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Privilegios de Usuario", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public class CargaImagen extends Thread{
        public void run(){
            try {
                String SQL = "SELECT * FROM DNUSUARIOS WHERE OPE_NUMERO='"+jTxtOpe_Numero.getText().toString()+"'";

                rs = conet.consultar(SQL);
                
                String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNUSUARIOS WHERE OPE_NUMERO='"+jTxtOpe_Numero.getText().toString()+"'";
                Reg_count = conet.Count_Reg(SQLReg);
                mostrarImagen();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(CreaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }

        } 
    }
    
    public void mostrarImagen() throws SQLException{
        if (Reg_count > 0){
            String ruta_img_fondo = rs.getString("OPE_RUTAIMG"); 
            jTxtOpe_RutaImg.setText(ruta_img_fondo);
            try{
                if (rs.getString("OPE_RUTAIMG")==null || ruta_img_fondo.trim().isEmpty()){
                    //Image preview = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"/build/classes/imagenes/fondo_dnet.png");
                    //ImageIcon icon = new ImageIcon(preview.getScaledInstance(jFondo_Preview.getWidth(), jFondo_Preview.getHeight(), Image.SCALE_AREA_AVERAGING));
                    //jFondo_Preview.setIcon(icon);
                }else{
                    Image preview = Toolkit.getDefaultToolkit().getImage(rs.getString("OPE_RUTAIMG").trim());
                    ImageIcon icon = new ImageIcon(preview.getScaledInstance(jFondo_Preview.getWidth(), jFondo_Preview.getHeight(), Image.SCALE_AREA_AVERAGING));
                    jFondo_Preview.setIcon(icon);
                }
            }catch (Exception ex){
                //Image preview = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"/build/classes/imagenes/fondo_dnet.png");
                //ImageIcon icon = new ImageIcon(preview.getScaledInstance(jFondo_Preview.getWidth(), jFondo_Preview.getHeight(), Image.SCALE_AREA_AVERAGING));
                //jFondo_Preview.setIcon(icon);
            }
        }
    }
    
    public void buscarimagen(){
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")+"\\")); 
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagenes", "bmp", "jpge", "jpg", "png");
        fc.setFileFilter(filter);
        fc.setDialogTitle("Seleccione el Archivo de Imagen");
        int r = fc.showOpenDialog(null);
        
        if(r==JFileChooser.APPROVE_OPTION){
            File s = fc.getSelectedFile();
            String l = s.getAbsoluteFile().toString();
                
            jTxtOpe_RutaImg.setText(l);
            
            Image preview = Toolkit.getDefaultToolkit().getImage(jTxtOpe_RutaImg.getText());
            ImageIcon icon = new ImageIcon(preview.getScaledInstance(jFondo_Preview.getWidth(), jFondo_Preview.getHeight(), Image.SCALE_DEFAULT));
            jFondo_Preview.setIcon(icon);
                
            System.out.println(s);
        }
    }
    
    public void cargaComboCargos(){
        String sql = "SELECT CAR_DESCRI AS DATO1 FROM dncargos";
        
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
        this.jCboOpe_Cargo.setModel(mdl); 
    }

    private void btn_buscarimgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarimgActionPerformed
        buscarimagen();        
    }//GEN-LAST:event_btn_buscarimgActionPerformed

    private void TablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaMouseClicked
        modelCreaUsuarios.actualizaJtable(Tabla.getSelectedRow());
    }//GEN-LAST:event_TablaMouseClicked

    private void TablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyPressed
        String nombre = ""; String nomenclatura = ""; String valor = "";
        String fecha = ""; String activo = "";

        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            modelCreaUsuarios.actualizaJtable(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyPressed

    private void TablaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            modelCreaUsuarios.actualizaJtable(Tabla.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            modelCreaUsuarios.actualizaJtable(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyReleased

    private void jTxtOpe_NombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtOpe_NombreKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            if (lPass){
                jTxtOpe_Clave1.requestFocus();
            }
//            }else{
                jTxtOpe_Clave1.requestFocus();
//            }
        }
    }//GEN-LAST:event_jTxtOpe_NombreKeyPressed

    private void jTxtOpe_UsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtOpe_UsuarioKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jCboOpe_Cargo.requestFocus();
        }
    }//GEN-LAST:event_jTxtOpe_UsuarioKeyPressed

    private void jCboOpe_tipoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCboOpe_tipoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            btn_buscarimg.requestFocus();
        }
    }//GEN-LAST:event_jCboOpe_tipoKeyPressed

    private void jTxtOpe_Clave1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtOpe_Clave1KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtOpe_Usuario.requestFocus();
        }
    }//GEN-LAST:event_jTxtOpe_Clave1KeyPressed

    private void jTxtOpe_Clave1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtOpe_Clave1FocusLost
        Clave = jTxtOpe_Clave1.getText();
    }//GEN-LAST:event_jTxtOpe_Clave1FocusLost

    private void jTxtOpe_CorreoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtOpe_CorreoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTxtOpe_Correo.requestFocus();
            jCboOpe_tipo.requestFocus();
        }
    }//GEN-LAST:event_jTxtOpe_CorreoKeyPressed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        inicializaClase();
    }//GEN-LAST:event_formInternalFrameActivated

    private void bt_cargosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_cargosActionPerformed
        // TODO add your handling code here:
        Cargos carg = new Cargos(origen);
        Dimension desktopSize = deskPane.getSize();
        Dimension jInternalFrameSize = carg.getSize();
        carg.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

        deskPane.add(carg);
        carg.toFront();
        carg.setVisible(true);
    }//GEN-LAST:event_bt_cargosActionPerformed

    private void jCboOpe_CargoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCboOpe_CargoMouseClicked
//        prueba.cargaCombo(jCboOpe_Cargo);
    }//GEN-LAST:event_jCboOpe_CargoMouseClicked

    private void jCboOpe_CargoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jCboOpe_CargoFocusGained
        cargaComboCargos();
    }//GEN-LAST:event_jCboOpe_CargoFocusGained

    private void jTxtOpe_Clave1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTxtOpe_Clave1MouseReleased
        if(jTxtOpe_Clave1.isEnabled()){
            jTxtOpe_Clave1.selectAll();
        }
    }//GEN-LAST:event_jTxtOpe_Clave1MouseReleased

    private void bt_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_salirActionPerformed

    }//GEN-LAST:event_bt_salirActionPerformed

    private void inicializaClase(){
        modelCreaUsuarios.setButton(bt_agregar, bt_Modificar, bt_save, bt_Eliminar, bt_cancel, bt_find, bt_Atras, bt_Siguiente, bt_salir);
        modelCreaUsuarios.setVista(this);
        modelCreaUsuarios.setButtonOther(btn_buscarimg);
//        modelCreaUsuarios.setJCheckBox(jChkOpe_Activo, jChkOpe_AccAndroid, jChkOpe_Accweb, jChkOpe_deldoc);
        modelCreaUsuarios.setJCheckBox(jChkOpe_Activo, jChkOpe_deldoc);
        modelCreaUsuarios.setJTable(Tabla);
        modelCreaUsuarios.setImgDesktop(jFondo_Preview);
        modelCreaUsuarios.setJTextField(jTxtOpe_Numero, jTxtOpe_Nombre, jTxtOpe_Clave1, jTxtOpe_Usuario, jTxtOpe_Correo, jTxtOpe_RutaImg);
        modelCreaUsuarios.setJComboBox(jCboOpe_Cargo, jCboOpe_tipo);
        modelCreaUsuarios.setOrigen(origen);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla;
    private com.l2fprod.common.swing.JLinkButton bt_Atras;
    private com.l2fprod.common.swing.JLinkButton bt_Eliminar;
    private com.l2fprod.common.swing.JLinkButton bt_Modificar;
    private com.l2fprod.common.swing.JLinkButton bt_Siguiente;
    private com.l2fprod.common.swing.JLinkButton bt_agregar;
    private com.l2fprod.common.swing.JLinkButton bt_cancel;
    private javax.swing.JButton bt_cargos;
    private com.l2fprod.common.swing.JLinkButton bt_find;
    private com.l2fprod.common.swing.JLinkButton bt_salir;
    private com.l2fprod.common.swing.JLinkButton bt_save;
    private javax.swing.JButton btn_buscarimg;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jCboOpe_Cargo;
    private javax.swing.JComboBox jCboOpe_tipo;
    private javax.swing.JCheckBox jChkOpe_Activo;
    private javax.swing.JCheckBox jChkOpe_deldoc;
    private javax.swing.JComboBox jComboBox1;
    public static javax.swing.JLabel jFondo_Preview;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPasswordField jTxtOpe_Clave1;
    private javax.swing.JTextField jTxtOpe_Correo;
    private javax.swing.JTextField jTxtOpe_Nombre;
    private javax.swing.JTextField jTxtOpe_Numero;
    private javax.swing.JTextField jTxtOpe_RutaImg;
    private javax.swing.JTextField jTxtOpe_Usuario;
    // End of variables declaration//GEN-END:variables
}