package Vista;

import Listener.ListenerBtnMoneda;
import Modelos.ModelMoneda;
import Modelos.VariablesGlobales;
import static Vista.MenuPrincipal.escritorioERP;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JDesktopPane;
import util.FullSelectorListener;
import util.Internacionalizacion;
import util.LimitarCaracteres;
import util.ValidaCodigo;

public class Moneda extends javax.swing.JInternalFrame {
    private String orgCall = "", orgCodEmp="";
    private final ModelMoneda modelMoneda = ModelMoneda.getModelMoneda();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    private final VariablesGlobales variablesGlobales = VariablesGlobales.getDatosGlobales();
    private JDesktopPane desktopForm;
    
    private Empresas empresas;
    
    public Moneda(Object otherThis) {
        modelMoneda.setOtherThis(otherThis);
    }
    
    public Moneda(String org, String codEmp) {
        orgCall = org;
        orgCodEmp = codEmp;
        initComponents();
        
        if (orgCall.equals("Welcome")){
//            desktopForm = desktop;
        }else{
            desktopForm = escritorioERP;
        }
        
        inicializaClase();
        Limitar();
        accionTeclasRapidas();
        
        this.setTitle(idioma.loadLangComponent().getString("lbMoneda"));
        jLabel2.setText(idioma.loadLangComponent().getString("lbCodigo")+":");
        jLabel3.setText(idioma.loadLangComponent().getString("lbDescrip")+":");
        jLabel4.setText(idioma.loadLangComponent().getString("lbSimbolo"));
        jLabel5.setText(idioma.loadLangComponent().getString("lbValor")+":");
        jLabel6.setText(idioma.loadLangComponent().getString("lbFecha")+":");
        jLabel7.setText(idioma.loadLangComponent().getString("lbCodISOMoneda"));
        lbCalculo.setText(idioma.loadLangComponent().getString("lbCalculo")+":");
        
        chkActivo.setText(idioma.loadLangComponent().getString("chkActivo"));
        
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, idioma.loadLangComponent().getString("panelDatos"), 0, 0, null, null));

        txtValor.addFocusListener(new FullSelectorListener());
        txtValor.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt){
                char let=evt.getKeyChar();

                if(Character.isLetter(let)){
                    evt.consume();
                }
            }
        });
        
        if (variablesGlobales.isVisualizaUltimoRegForm()){
            if(tbTabla.getRowCount()==0){
                modelMoneda.habilitar("Inicializa");
                modelMoneda.posicion_botones_2();
                modelMoneda.setConsecutivo();
            }else{
                modelMoneda.posicion_botones_1();
                modelMoneda.deshabilitar();
                bt_save.setVisible(false); bt_cancel.setVisible(false);

                modelMoneda.ejecutaHilo();

                tbTabla.getSelectionModel().setSelectionInterval(tbTabla.getRowCount()-1, tbTabla.getRowCount()-1);
            }
        }else{
            modelMoneda.habilitar("Inicializa");
            modelMoneda.posicion_botones_2();
            modelMoneda.setConsecutivo();
        }
        
        bt_agregar.setText(idioma.loadLangComponent().getString("btnAgregar"));
        bt_Modificar.setText(idioma.loadLangComponent().getString("btnModificar"));
        bt_save.setText(idioma.loadLangComponent().getString("btnGrabar"));
        bt_Eliminar.setText(idioma.loadLangComponent().getString("btnEliminar"));
        bt_find.setText(idioma.loadLangComponent().getString("btnBuscar"));
        bt_Atras.setText(idioma.loadLangComponent().getString("btnAnterior"));
        bt_Siguiente.setText(idioma.loadLangComponent().getString("btnSiguiente"));
        bt_salir.setText(idioma.loadLangComponent().getString("btnSalir"));
        
        bt_agregar.setActionCommand("Agregar"); bt_Modificar.setActionCommand("Modificar");
        bt_save.setActionCommand("Grabar"); bt_cancel.setActionCommand("Cancelar");
        bt_Eliminar.setActionCommand("Eliminar"); bt_find.setActionCommand("Buscar");
        bt_Atras.setActionCommand("Anterior"); bt_Siguiente.setActionCommand("Adelante"); 
        bt_salir.setActionCommand("Salir");
        
        bt_agregar.addActionListener(new ListenerBtnMoneda());
        bt_Modificar.addActionListener(new ListenerBtnMoneda());
        bt_save.addActionListener(new ListenerBtnMoneda());
        bt_Eliminar.addActionListener(new ListenerBtnMoneda());
        bt_cancel.addActionListener(new ListenerBtnMoneda());
        bt_find.addActionListener(new ListenerBtnMoneda());
        bt_Atras.addActionListener(new ListenerBtnMoneda());
        bt_Siguiente.addActionListener(new ListenerBtnMoneda());
        bt_salir.addActionListener(new ListenerBtnMoneda());
        
        rbMultiplica.setSelected(true);
    }
    
    public void Limitar(){
        txtCodigo.setDocument(new LimitarCaracteres(txtCodigo, 6));
        txtDescri.setDocument(new LimitarCaracteres(txtDescri, 40));
        txtNomen.setDocument(new LimitarCaracteres(txtNomen, 6));
                txtValor.setDocument(new LimitarCaracteres(txtValor, 11));
    }
    
    private void inicializaClase(){
        modelMoneda.setVista(this);
        modelMoneda.setOrgCall(orgCall);
        
        modelMoneda.setComponent(txtCodigo, txtDescri, txtNomen, txtValor, dFecha, tbTabla, chkActivo, cbIsoMoneda, rbMultiplica,
                                 rbDivide, lbCalculo);
        modelMoneda.setButton(bt_agregar, bt_Modificar, bt_save, bt_Eliminar, bt_cancel, bt_find, bt_Atras, bt_Siguiente, bt_salir);
        modelMoneda.setDesktop(desktopForm);
    //    modelTipoMaestros.setOrigen(origen);
    
        if (orgCall.isEmpty()){
            modelMoneda.cargaTabla();
        }else{
            modelMoneda.cargaTabla();
            modelMoneda.setOrgCodEmp(orgCodEmp);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dFecha = new com.toedter.calendar.JDateChooser();
        chkActivo = new javax.swing.JCheckBox();
        txtCodigo = new javax.swing.JTextField();
        txtDescri = new javax.swing.JTextField();
        txtNomen = new javax.swing.JTextField();
        txtValor = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbIsoMoneda = new org.jdesktop.swingx.JXComboBox();
        rbMultiplica = new javax.swing.JRadioButton();
        rbDivide = new javax.swing.JRadioButton();
        lbCalculo = new javax.swing.JLabel();
        jLeyenda1 = new javax.swing.JLabel();
        jLeyenda2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbTabla = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };

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
        bt_find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_findActionPerformed(evt);
            }
        });

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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Codigo:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Descripcion:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Simbolo:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Valor:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Fecha:");

        dFecha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        chkActivo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        chkActivo.setText("Activo");
        chkActivo.setOpaque(false);

        txtCodigo.setEditable(false);
        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCodigo.setEnabled(false);
        txtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoFocusLost(evt);
            }
        });
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
        });

        txtDescri.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDescri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescriKeyPressed(evt);
            }
        });

        txtNomen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomenKeyPressed(evt);
            }
        });

        txtValor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtValor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValorFocusLost(evt);
            }
        });
        txtValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorActionPerformed(evt);
            }
        });
        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValorKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Código ISO Moneda:");

        cbIsoMoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "VEF", "USD", "CRC" }));
        cbIsoMoneda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        rbMultiplica.setBackground(new java.awt.Color(255, 255, 255));
        rbMultiplica.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rbMultiplica.setText("Multiplica por la moneda Nacional");
        rbMultiplica.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbMultiplicaMouseClicked(evt);
            }
        });

        rbDivide.setBackground(new java.awt.Color(255, 255, 255));
        rbDivide.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rbDivide.setText("Divide entre la moneda Nacional");
        rbDivide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbDivideMouseClicked(evt);
            }
        });

        lbCalculo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbCalculo.setText("Cálculo:");

        jLeyenda1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLeyenda1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLeyenda1.setText("F2: Buscar Tipo Contacto;    F3: Eliminar Tipo Contacto");
        jLeyenda1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLeyenda2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLeyenda2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLeyenda2.setText("F5: Guardar;    ESC: Cancelar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(6, 6, 6))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(lbCalculo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rbDivide)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rbMultiplica)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCodigo)
                                    .addComponent(txtDescri, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtValor)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtNomen, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(dFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(114, 114, 114)
                                        .addComponent(chkActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbIsoMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(43, 43, 43))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLeyenda2, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLeyenda1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCodigo)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(chkActivo))
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNomen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbIsoMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rbMultiplica)
                        .addGap(0, 0, 0)
                        .addComponent(rbDivide, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lbCalculo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(jLeyenda1)
                .addGap(2, 2, 2)
                .addComponent(jLeyenda2))
        );

        jTabbedPane1.addTab("Datos", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tbTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbTabla);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Lista", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTablaMouseClicked
        if(tbTabla.getSelectedRow()!=-1){
            modelMoneda.actualizaJTable(tbTabla.getSelectedRow());
        }
    }//GEN-LAST:event_tbTablaMouseClicked

    private void txtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFocusLost
        int registros;

        ValidaCodigo consulta = new ValidaCodigo();
        registros = consulta.ValidaCodigo("SELECT COUNT(*) AS REGISTROS FROM DNMONEDA WHERE MON_CODIGO='"+txtCodigo.getText()+"'",false,"");

        if (registros==1){
            txtCodigo.requestFocus();
            txtCodigo.setText("");
        }
    }//GEN-LAST:event_txtCodigoFocusLost

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtDescri.requestFocus();
        }
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void txtDescriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescriKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtNomen.requestFocus();
        }
    }//GEN-LAST:event_txtDescriKeyPressed

    private void txtNomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomenKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtValor.requestFocus();
        }
    }//GEN-LAST:event_txtNomenKeyPressed

    private void txtValorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFocusLost
        String valor="";
        
        if ("".equals(txtValor.getText())){
            txtValor.setText("0,00");
        }else{
            valor=txtValor.getText();
            valor=valor.replace(".",",");
            txtValor.setText(valor);
        }
    }//GEN-LAST:event_txtValorFocusLost

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        //inicializaClase();
        modelMoneda.setVista(this);
    }//GEN-LAST:event_formInternalFrameActivated

    private void txtValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorActionPerformed

    private void bt_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_salirActionPerformed
        this.dispose();
    }//GEN-LAST:event_bt_salirActionPerformed

    private void bt_findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_findActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bt_findActionPerformed

    private void rbMultiplicaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbMultiplicaMouseClicked
        if (rbMultiplica.isSelected()){
            rbDivide.setSelected(false);
        }
    }//GEN-LAST:event_rbMultiplicaMouseClicked

    private void rbDivideMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbDivideMouseClicked
        if (rbDivide.isSelected()){
            rbMultiplica.setSelected(false);
        }
    }//GEN-LAST:event_rbDivideMouseClicked

    private void txtValorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            dFecha.requestFocus();
        }
    }//GEN-LAST:event_txtValorKeyPressed

    private void accionTeclasRapidas(){
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0), "BuscarContacto");

        getRootPane().getActionMap().put("BuscarContacto", new javax.swing.AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                modelMoneda.actionBtnBuscar();
            }
        });

        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0), "EliminarrArticulo");

        getRootPane().getActionMap().put("EliminarrArticulo", new javax.swing.AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e){
                modelMoneda.action_bt_eliminar();
            }
        });
        
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0), "Guardar");

        getRootPane().getActionMap().put("Guardar", new javax.swing.AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e){
                modelMoneda.action_bt_grabar();
            }
        });
            
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), "Cancel");

        getRootPane().getActionMap().put("Cancel", new javax.swing.AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e){
                modelMoneda.action_bt_cancelar();
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.l2fprod.common.swing.JLinkButton bt_Atras;
    private com.l2fprod.common.swing.JLinkButton bt_Eliminar;
    private com.l2fprod.common.swing.JLinkButton bt_Modificar;
    private com.l2fprod.common.swing.JLinkButton bt_Siguiente;
    private com.l2fprod.common.swing.JLinkButton bt_agregar;
    private com.l2fprod.common.swing.JLinkButton bt_cancel;
    private com.l2fprod.common.swing.JLinkButton bt_find;
    private com.l2fprod.common.swing.JLinkButton bt_salir;
    private com.l2fprod.common.swing.JLinkButton bt_save;
    private org.jdesktop.swingx.JXComboBox cbIsoMoneda;
    private javax.swing.JCheckBox chkActivo;
    private com.toedter.calendar.JDateChooser dFecha;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLeyenda1;
    private javax.swing.JLabel jLeyenda2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbCalculo;
    private javax.swing.JRadioButton rbDivide;
    private javax.swing.JRadioButton rbMultiplica;
    private javax.swing.JTable tbTabla;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescri;
    private javax.swing.JTextField txtNomen;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}