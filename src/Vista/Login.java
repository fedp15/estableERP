package Vista;

import Controlador.Controlador_Carga_Idiomas;
import Controlador.Controlador_Login;
import Modelos.VariablesGlobales;
import Modelos.Bitacora;
import Modelos.ControldeInicio;
import util.GetMacIp;
import Modelos.JchomboBox;
import Modelos.conexion;
import Modelos.jComboRenderer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import stored_procedure.SpConfigEmpresa;
import util.CodigoConsecutivo;
import util.Internacionalizacion;
import util.SQLQuerys;
import util.ValidarJornada;

public class Login extends javax.swing.JFrame {
//public class Login extends javax.swing.JInternalFrame {
    public static String Idioma="";
    public static String ultimaEmpresa="", ultimoUsuario="";
    private String[] comboEmpresa, comboUser;
////    private JXComboBox jCmbEmpresa1, jCmbUser2;
    public static JchomboBox jComboIdioma;
   
    public static int NumEmp = 0;
    public static Vector elementos = new Vector();
    //private Vector Msg, Menu;
    private String FormClass="", IdiomaSelect="",buscaPredeterminada="";
    private final String colorForm="#FFFFFF", colorText="#0972ba";
    
    private ResultSet rs = null;
    private CallableStatement cs = null;
        
    private final Controlador_Login ControlLogin = new Controlador_Login();
    private final Controlador_Carga_Idiomas ControlIdioma = new Controlador_Carga_Idiomas();
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    private final SpConfigEmpresa configEmpresa = SpConfigEmpresa.getSpConfigEmpresa();
    private conexion conet = new conexion();
    private boolean lEmpPredeterminado = false;

    public Login() throws MalformedURLException {
        this.setUndecorated(true);
        initComponents();
        
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setLocationRelativeTo(null);
        //this.setVisible(true);
        
//        comboEmpresa();
//        jCmbEmpresa1 = new AutoComplete(comboEmpresa);
//        jCmbEmpresa1.setSelectedIndex(1);
//        jCmbEmpresa1.setEditable(true);
//        jCmbEmpresa1.setEnabled(true);
//        JTextField txtEmpresa = (JTextField) jCmbEmpresa1.getEditor().getEditorComponent();
//
//        txtEmpresa.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                cargaUsuariosEmpresa();
//                jCmbUser2.requestFocus();
//            }
//        });
        
//       comboUser();
//        jCmbUser2 = new AutoComplete(comboUser);
//        jCmbUser2.setEditable(true);
//        jCmbUser2.setEnabled(true);
//        JTextField txtUsuario = (JTextField) jCmbUser2.getEditor().getEditorComponent();
//
//        txtUsuario.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                passFUser2.requestFocus();
//            }
//        });
        
        final Properties p = System.getProperties();
        FormClass = this.getClass().getName();
        FormClass = FormClass.substring(FormClass.indexOf(".")+1, FormClass.length());
        
        setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/icono_app.png").getImage());

        //elementos = ControlIdioma.ListaIdioma();
        //elementos.add(idioma.loadLangIdiomasSist().getString("es_AR"));
        //elementos.add(idioma.loadLangIdiomasSist().getString("pt_BR"));
        //elementos.add(idioma.loadLangIdiomasSist().getString("es_CH"));
        //elementos.add(idioma.loadLangIdiomasSist().getString("zh_CH"));
        //elementos.add(idioma.loadLangIdiomasSist().getString("es_CO"));
        //elementos.add(idioma.loadLangIdiomasSist().getString("es_JP"));
        elementos.add(idioma.loadLangIdiomasSist().getString("es_CR"));
        elementos.add(idioma.loadLangIdiomasSist().getString("es_US"));
        elementos.add(idioma.loadLangIdiomasSist().getString("es_VE"));

        jComboIdioma = new JchomboBox(ControlLogin.BanderasComboIdiomas().length);
        jComboRenderer render = new jComboRenderer(ControlLogin.BanderasComboIdiomas(), elementos);
        jComboIdioma.setRenderer(render);
        jComboIdioma.setEnabled(false);

        comboEmpresa();
        comboUser();
        
        if (!ultimaEmpresa.isEmpty() && !ultimoUsuario.isEmpty()){
            jCmbEmpresa1.setSelectedItem(ultimaEmpresa);
            jCmbUser2.setSelectedItem(ultimoUsuario);
            passFUser2.requestFocus();
        }
       
        NumEmp = jCmbEmpresa1.getItemCount();
        
        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCmbEmpresa1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCmbUser2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(passFUser2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel18))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addGap(0, 240, Short.MAX_VALUE)
                                        .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jComboIdioma, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(5, 5, 5)
                        .addComponent(jCmbEmpresa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel17)
                        .addGap(8, 8, 8)
                        .addComponent(jCmbUser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel18)
                        .addGap(7, 7, 7)
                        .addComponent(passFUser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(jComboIdioma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        
        idioma.setLocale(VarGlobales.getIdioma());
        
        jComboIdioma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int Item = (int) jComboIdioma.getSelectedItem();
                
                IdiomaSelect=(String) elementos.get(Item);
                CargarIdioma();
            }
        });
        
        jComboIdioma.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode()==KeyEvent.VK_ENTER){
                    btnLogin2.requestFocus();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        if (p.get("user.language").equals("es")){
            for (int i=0; i<elementos.size(); i++){
                //System.err.println(elementos.elementAt(i).toString().substring(elementos.elementAt(i).toString().indexOf("("), elementos.elementAt(i).toString().length()));
                String idioma = elementos.elementAt(i).toString().substring(elementos.elementAt(i).toString().indexOf("("), elementos.elementAt(i).toString().length());

                if (idioma.subSequence(1, idioma.indexOf("_")).equals(p.get("user.language"))){
                    jComboIdioma.setSelectedItem(i);
                }
                
//                if (elementos.get(i).equals("Español ("+p.get("user.language").toString().trim()+"_"+p.get("user.country").toString().trim()+")")){
//                    IdiomaSelect=(String) elementos.get(i);
//                    jComboIdioma.setSelectedItem(i);
//                }
            }

            CargarIdioma();
        }
        if (p.get("user.language").equals("en")){
            for (int i=0; i<elementos.size(); i++){
                String idioma = elementos.elementAt(i).toString().substring(elementos.elementAt(i).toString().indexOf("("), elementos.elementAt(i).toString().length());

                if (idioma.subSequence(1, idioma.indexOf("_")).equals(p.get("user.language"))){
                    jComboIdioma.setSelectedItem(i);
                }
            }

            CargarIdioma();
        }
        
//        System.out.println("Idioma del Sistema Operativo: "+p.get("user.language"));
//        System.out.println("Pais del Sistema Operativo: "+p.get("user.country"));
//        System.out.println("Idioma a cargar: "+"Español ("+p.get("user.language")+"_"+p.get("user.country")+")");

        buscaPredeterminada = "SELECT_EMP_PREDETERMINADA";
        cargaUsuariosEmpresa();
        
        if (VarGlobales.getDiasRestantesLicencia()<=30){
            lbTiempoDemo.setText(VarGlobales.getDemo());
            lbTiempoDemo.setForeground(Color.red);
        }
        
        if (VarGlobales.getDiasRestantesLicencia()==0){
            lbTiempoDemo.setText(VarGlobales.getDemo());
        }
        
        AutoCompleteDecorator.decorate(this.jCmbUser2); 
        AutoCompleteDecorator.decorate(this.jCmbEmpresa1);
    }
    
    public final void comboEmpresa() {
        String sql = "SELECT CONCAT(EMP_CODIGO,' - ',EMP_NOMBRE) AS DATO1 FROM dnempresas WHERE EMP_ACTIVO=1 ORDER BY EMP_CODIGO";

        VarGlobales.setlBaseDatosConfiguracion(true);
        DefaultComboBoxModel mdl = ControlLogin.DatosCombo(sql);
        VarGlobales.setlBaseDatosConfiguracion(false);
        
        jCmbEmpresa1.setModel(mdl);
        jCmbEmpresa1.setSelectedIndex(1);

        VarGlobales.setCodEmpresa(jCmbEmpresa1.getSelectedItem().toString().substring(0, 6));
    }
    
//    public void colorpanel(){
//        CambiarColorJpanel cp = new CambiarColorJpanel();
//        cp.seleccionarcolor();
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel9 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jCmbUser2 = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(2, 0), new java.awt.Dimension(2, 0), new java.awt.Dimension(2, 32767));
        passFUser2 = new javax.swing.JPasswordField();
        jLabel20 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        btnSalir = new com.l2fprod.common.swing.JLinkButton();
        jPanel11 = new javax.swing.JPanel();
        btnLogin2 = new com.l2fprod.common.swing.JLinkButton();
        jCmbEmpresa1 = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbTiempoDemo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 73, 145));
        jLabel16.setText("Acceso al Sistema");

        jPanel10.setBackground(new java.awt.Color(105, 105, 105));

        jCmbUser2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCmbUser2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCmbUser2KeyPressed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Selección del Usuario:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Clave de Acceso:");

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/login_dep.png"))); // NOI18N

        passFUser2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        passFUser2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passFUser2KeyPressed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Pais:");

        jPanel12.setBackground(new java.awt.Color(105, 105, 105));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel12.setForeground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(105, 105, 105));
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("Salir");
        btnSalir.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnSalir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel11.setBackground(new java.awt.Color(105, 105, 105));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel11.setPreferredSize(new java.awt.Dimension(124, 47));

        btnLogin2.setBackground(new java.awt.Color(105, 105, 105));
        btnLogin2.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin2.setText("Ingresar");
        btnLogin2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnLogin2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLogin2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogin2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogin2ActionPerformed(evt);
            }
        });
        btnLogin2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLogin2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 134, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnLogin2, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnLogin2, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
        );

        jCmbEmpresa1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCmbEmpresa1ItemStateChanged(evt);
            }
        });
        jCmbEmpresa1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCmbEmpresa1KeyPressed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Empresa:");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCmbEmpresa1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCmbUser2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(passFUser2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel18))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 244, Short.MAX_VALUE)
                                .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(5, 5, 5)
                        .addComponent(jCmbEmpresa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel17)
                        .addGap(8, 8, 8)
                        .addComponent(jCmbUser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel18)
                        .addGap(7, 7, 7)
                        .addComponent(passFUser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Revisión: 22.04.2019");

        lbTiempoDemo.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lbTiempoDemo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTiempoDemo, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbTiempoDemo, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void passFUser2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passFUser2KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            btnLogin2.requestFocus();
        }
    }//GEN-LAST:event_passFUser2KeyPressed

    private void btnLogin2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogin2ActionPerformed
        try {
            acion_login();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLogin2ActionPerformed

    private void btnLogin2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLogin2KeyPressed
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            try {
                acion_login();
            } catch (MalformedURLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnLogin2KeyPressed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        int salir = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgSalirSistema"));

        if (salir == 0){
            new ControldeInicio().cerrarApp();  
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void jCmbUser2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCmbUser2KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            passFUser2.requestFocus();
        }
    }//GEN-LAST:event_jCmbUser2KeyPressed

    private void jCmbEmpresa1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCmbEmpresa1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCmbEmpresa1KeyPressed

    private void jCmbEmpresa1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCmbEmpresa1ItemStateChanged
        try {
            VarGlobales.setCodEmpresa(jCmbEmpresa1.getSelectedItem().toString().substring(0, 6));
            System.err.println(VarGlobales.getCodEmpresa()+" - "+jCmbEmpresa1.getSelectedItem().toString().substring(0, 6));
            
            if (!VarGlobales.getCodEmpresa().equals(jCmbEmpresa1.getSelectedItem().toString().substring(0, 6))){
                lEmpPredeterminado = false;
            }
        } catch (Exception e) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
        }
        
        //if (!lEmpPredeterminado){
            cargaUsuariosEmpresa();
        //}
    }//GEN-LAST:event_jCmbEmpresa1ItemStateChanged

    private void cargaUsuariosEmpresa(){
        try {
            VarGlobales.setlBaseDatosConfiguracion(true);
            conet.creaConexion();
            String sql = "{call sp_formEmpresas(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            
            try {
                if (!VarGlobales.getCodEmpresa().equals(jCmbEmpresa1.getSelectedItem().toString().substring(0, 6))){
                    getUsuariosEmpresa(sql, jCmbEmpresa1.getSelectedItem().toString().substring(0, 6));
                }else{
                    if (buscaPredeterminada.isEmpty()){
                        getUsuariosEmpresa(sql, jCmbEmpresa1.getSelectedItem().toString().substring(0, 6));
                    }else{
                        cs = conet.conn.prepareCall(sql);
                        cs.setString(1, buscaPredeterminada);
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

                        ResultSet rsBuscaEmpPredetermianda = conet.consultarStoreProcedure(cs);

                        if (rsBuscaEmpPredetermianda.getRow()>0){
                            lEmpPredeterminado = true;
                            buscaPredeterminada = "";

                            VarGlobales.setCodEmpresa(rsBuscaEmpPredetermianda.getString(1));
                            
                            conet.creaConexion();
                            getUsuariosEmpresa(sql, rsBuscaEmpPredetermianda.getString(1));
                            
                            jCmbEmpresa1.setSelectedItem(rsBuscaEmpPredetermianda.getString(2));
                        }else{
                            lEmpPredeterminado = false;
                            
                            conet.creaConexion();
                            getUsuariosEmpresa(sql, jCmbEmpresa1.getSelectedItem().toString().substring(0, 6));
                        }
                    }
                }
            } catch (Exception e) {
                conet.conn.close();
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
            }
            VarGlobales.setlBaseDatosConfiguracion(true);
            
            conet.creaConexion();
            Statement statement;
            try {
                sql = "DROP PROCEDURE IF EXISTS sp_corrigeAcentos;\n";
                conet.consulta = conet.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                conet.consulta.executeUpdate();
            
                conet.consulta.close();
                conet.conn.close();
            } catch (Exception e) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
            }
            //conet.consultar(sql);
            
            sql = //"DELIMITER //\n" +
                  "CREATE DEFINER=`root`@`%` PROCEDURE `sp_corrigeAcentos`()\n" +
                  "BEGIN\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_nombre= REPLACE(men_nombre, 'ÃƒÂ³', 'ó') WHERE men_nombre LIKE '%ÃƒÂ³%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_descripcion= REPLACE(men_descripcion, 'ÃƒÂ³', 'ó') WHERE men_descripcion LIKE '%ÃƒÂ³%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_url= REPLACE(men_url, 'ÃƒÂ³', 'ó') WHERE men_url LIKE '%ÃƒÂ³%';\n" +
                  "\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_nombre= REPLACE(men_nombre, '?Ã¢Ã£Ã†?Ã¥Ã”Ã‡Ã–?Ã¢Ã”Ã‡Ãœ?é??', 'ó') WHERE men_nombre LIKE '?Ã¢Ã£Ã†?Ã¥Ã”Ã‡Ã–?Ã¢Ã”Ã‡Ãœ?é??';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_descripcion= REPLACE(men_descripcion, '?Ã¢Ã£Ã†?Ã¥Ã”Ã‡Ã–?Ã¢Ã”Ã‡Ãœ?é??', 'ó') WHERE men_descripcion LIKE '?Ã¢Ã£Ã†?Ã¥Ã”Ã‡Ã–?Ã¢Ã”Ã‡Ãœ?é??';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_url= REPLACE(men_url, '?Ã¢Ã£Ã†?Ã¥Ã”Ã‡Ã–?Ã¢Ã”Ã‡Ãœ?é??', 'ó') WHERE men_url LIKE '?Ã¢Ã£Ã†?Ã¥Ã”Ã‡Ã–?Ã¢Ã”Ã‡Ãœ?é??';\n" +
                  "\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dncondipago SET cdi_descri= REPLACE(cdi_descri, 'Ã¡', 'á') WHERE cdi_descri LIKE '%Ã¡%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dncondipago SET cdi_descri= REPLACE(cdi_descri, 'Ã©', 'é') WHERE cdi_descri LIKE '%Ã©%';\n" +
                    
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dncondipago SET cdi_descri= REPLACE(cdi_descri, 'Ã­', 'í') WHERE cdi_descri LIKE '%Ã­%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dncondipago SET cdi_descri= REPLACE(cdi_descri, 'ÃƒÂ­', 'í') WHERE cdi_descri LIKE '%ÃƒÂ­%';\n" +
                  "	UPDATE type_person SET name= REPLACE(name, 'ÃƒÂ­', 'í') WHERE name LIKE '%ÃƒÂ­%';\n" +
                    
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dncondipago SET cdi_descri= REPLACE(cdi_descri, 'Ã³', 'ó') WHERE cdi_descri LIKE '%Ã³%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dncondipago SET cdi_descri= REPLACE(cdi_descri, 'Ãº', 'ú') WHERE cdi_descri LIKE '%Ãº%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dncondipago SET cdi_descri= REPLACE(cdi_descri, 'Ã‘', 'ñ') WHERE cdi_descri LIKE '%Ã‘%';\n" +
                    
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dncondipago SET cdi_descri= REPLACE(cdi_descri, 'Ãš', 'Ú') WHERE cdi_descri LIKE '%Ãš%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dncondipago SET cdi_descri= REPLACE(cdi_descri, 'ÃƒÂº', 'Ú') WHERE cdi_descri LIKE '%ÃƒÂº%';\n" +
                  "	UPDATE type_person SET name= REPLACE(name, 'ÃƒÂº', 'Ú') WHERE name LIKE '%ÃƒÂº%';\n" +
                    
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dncondipago SET cdi_descri= REPLACE(cdi_descri, 'Ã“', 'Ó') WHERE cdi_descri LIKE '%Ã“%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dncondipago SET cdi_descri= REPLACE(cdi_descri, 'Ã�', 'Í') WHERE cdi_descri LIKE '%Ã�%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dncondipago SET cdi_descri= REPLACE(cdi_descri, 'Ã‰', 'É') WHERE cdi_descri LIKE '%Ã‰%';\n" +
                  "\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".commissions SET name= REPLACE(name, 'Ã¡', 'á') WHERE name LIKE '%Ã¡%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".commissions SET name= REPLACE(name, 'Ã©', 'é') WHERE name LIKE '%Ã©%';\n" +
                    
                  "	UPDATE "+VarGlobales.getBaseDatos()+".commissions SET name= REPLACE(name, 'Ã­', 'í') WHERE name LIKE '%Ã­%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".commissions SET name= REPLACE(name, 'ÃƒÂ­', 'í') WHERE name LIKE '%ÃƒÂ­%';\n" +
                    
                  "	UPDATE "+VarGlobales.getBaseDatos()+".commissions SET name= REPLACE(name, 'Ã³', 'ó') WHERE name LIKE '%Ã³%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".commissions SET name= REPLACE(name, 'Ãº', 'ú') WHERE name LIKE '%Ãº%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".commissions SET name= REPLACE(name, 'Ã‘', 'ñ') WHERE name LIKE '%Ã‘%';\n" +
                    
                  "	UPDATE "+VarGlobales.getBaseDatos()+".commissions SET name= REPLACE(name, 'Ãš', 'Ú') WHERE name LIKE '%Ãš%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".commissions SET name= REPLACE(name, 'ÃƒÂº', 'Ú') WHERE name LIKE '%ÃƒÂº%';\n" +
                    
                  "	UPDATE "+VarGlobales.getBaseDatos()+".commissions SET name= REPLACE(name, 'Ã“', 'Ó') WHERE name LIKE '%Ã“%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".commissions SET name= REPLACE(name, 'Ã�', 'Í') WHERE name LIKE '%Ã�%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".commissions SET name= REPLACE(name, 'Ã‰', 'É') WHERE name LIKE '%Ã‰%';\n" +
                  "\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dntipcontacto SET tcon_descri= REPLACE(tcon_descri, 'Ã¡', 'á') WHERE tcon_descri LIKE '%Ã¡%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dntipcontacto SET tcon_descri= REPLACE(tcon_descri, 'Ã©', 'é') WHERE tcon_descri LIKE '%Ã©%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dntipcontacto SET tcon_descri= REPLACE(tcon_descri, 'Ã­', 'í') WHERE tcon_descri LIKE '%Ã­%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dntipcontacto SET tcon_descri= REPLACE(tcon_descri, 'Ã³', 'ó') WHERE tcon_descri LIKE '%Ã³%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dntipcontacto SET tcon_descri= REPLACE(tcon_descri, 'Ãº', 'ú') WHERE tcon_descri LIKE '%Ãº%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dntipcontacto SET tcon_descri= REPLACE(tcon_descri, 'Ã‘', 'ñ') WHERE tcon_descri LIKE '%Ã‘%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dntipcontacto SET tcon_descri= REPLACE(tcon_descri, 'Ãš', 'Ú') WHERE tcon_descri LIKE '%Ãš%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dntipcontacto SET tcon_descri= REPLACE(tcon_descri, 'Ã“', 'Ó') WHERE tcon_descri LIKE '%Ã“%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dntipcontacto SET tcon_descri= REPLACE(tcon_descri, 'Ã�', 'Í') WHERE tcon_descri LIKE '%Ã�%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dntipcontacto SET tcon_descri= REPLACE(tcon_descri, 'Ã‰', 'É') WHERE tcon_descri LIKE '%Ã‰%';\n" +
                  "\n" +
                  "	UPDATE dnestados SET est_nombre= REPLACE(est_nombre, 'Ã¡', 'á') WHERE est_nombre LIKE '%Ã¡%';\n" +
                  "	UPDATE dnestados SET est_nombre= REPLACE(est_nombre, 'Ã©', 'é') WHERE est_nombre LIKE '%Ã©%';\n" +
                  "	UPDATE dnestados SET est_nombre= REPLACE(est_nombre, 'Ã­', 'í') WHERE est_nombre LIKE '%Ã­%';\n" +
                  "	UPDATE dnestados SET est_nombre= REPLACE(est_nombre, 'Ã³', 'ó') WHERE est_nombre LIKE '%Ã³%';\n" +
                  "	UPDATE dnestados SET est_nombre= REPLACE(est_nombre, 'Ãº', 'ú') WHERE est_nombre LIKE '%Ãº%';\n" +
                  "	UPDATE dnestados SET est_nombre= REPLACE(est_nombre, 'Ã‘', 'ñ') WHERE est_nombre LIKE '%Ã‘%';\n" +
                  "	UPDATE dnestados SET est_nombre= REPLACE(est_nombre, 'Ãš', 'Ú') WHERE est_nombre LIKE '%Ãš%';\n" +
                  "	UPDATE dnestados SET est_nombre= REPLACE(est_nombre, 'Ã“', 'Ó') WHERE est_nombre LIKE '%Ã“%';\n" +
                  "	UPDATE dnestados SET est_nombre= REPLACE(est_nombre, 'Ã�', 'Í') WHERE est_nombre LIKE '%Ã�%';\n" +
                  "	UPDATE dnestados SET est_nombre= REPLACE(est_nombre, 'Ã‰', 'É') WHERE est_nombre LIKE '%Ã‰%';\n" +
                  "\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dninstrumentopago SET ins_descri= REPLACE(ins_descri, 'Ã¡', 'á') WHERE ins_descri LIKE '%Ã¡%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dninstrumentopago SET ins_descri= REPLACE(ins_descri, 'Ã©', 'é') WHERE ins_descri LIKE '%Ã©%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dninstrumentopago SET ins_descri= REPLACE(ins_descri, 'Ã­', 'í') WHERE ins_descri LIKE '%Ã­%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dninstrumentopago SET ins_descri= REPLACE(ins_descri, 'Ã³', 'ó') WHERE ins_descri LIKE '%Ã³%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dninstrumentopago SET ins_descri= REPLACE(ins_descri, 'Ãº', 'ú') WHERE ins_descri LIKE '%Ãº%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dninstrumentopago SET ins_descri= REPLACE(ins_descri, 'Ã‘', 'ñ') WHERE ins_descri LIKE '%Ã‘%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dninstrumentopago SET ins_descri= REPLACE(ins_descri, 'Ãš', 'Ú') WHERE ins_descri LIKE '%Ãš%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dninstrumentopago SET ins_descri= REPLACE(ins_descri, 'Ã“', 'Ó') WHERE ins_descri LIKE '%Ã“%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dninstrumentopago SET ins_descri= REPLACE(ins_descri, 'Ã�', 'Í') WHERE ins_descri LIKE '%Ã�%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dninstrumentopago SET ins_descri= REPLACE(ins_descri, 'Ã‰', 'É') WHERE ins_descri LIKE '%Ã‰%';\n" +
                  "\n" +
                  "	UPDATE dnmunicipios SET mun_nombre= REPLACE(mun_nombre, 'Ã¡', 'á') WHERE mun_nombre LIKE '%Ã¡%';\n" +
                  "	UPDATE dnmunicipios SET mun_nombre= REPLACE(mun_nombre, 'Ã©', 'é') WHERE mun_nombre LIKE '%Ã©%';\n" +
                  "	UPDATE dnmunicipios SET mun_nombre= REPLACE(mun_nombre, 'Ã­', 'í') WHERE mun_nombre LIKE '%Ã­%';\n" +
                  "	UPDATE dnmunicipios SET mun_nombre= REPLACE(mun_nombre, 'Ã³', 'ó') WHERE mun_nombre LIKE '%Ã³%';\n" +
                  "	UPDATE dnmunicipios SET mun_nombre= REPLACE(mun_nombre, 'Ãº', 'ú') WHERE mun_nombre LIKE '%Ãº%';\n" +
                  "	UPDATE dnmunicipios SET mun_nombre= REPLACE(mun_nombre, 'Ã‘', 'ñ') WHERE mun_nombre LIKE '%Ã‘%';\n" +
                  "	UPDATE dnmunicipios SET mun_nombre= REPLACE(mun_nombre, 'Ãš', 'Ú') WHERE mun_nombre LIKE '%Ãš%';\n" +
                  "	UPDATE dnmunicipios SET mun_nombre= REPLACE(mun_nombre, 'Ã“', 'Ó') WHERE mun_nombre LIKE '%Ã“%';\n" +
                  "	UPDATE dnmunicipios SET mun_nombre= REPLACE(mun_nombre, 'Ã�', 'Í') WHERE mun_nombre LIKE '%Ã�%';\n" +
                  "	UPDATE dnmunicipios SET mun_nombre= REPLACE(mun_nombre, 'Ã‰', 'É') WHERE mun_nombre LIKE '%Ã‰%';\n" +
                  "\n" +
                  "	UPDATE dnparroquias SET par_nombre= REPLACE(par_nombre, 'Ã¡', 'á') WHERE par_nombre LIKE '%Ã¡%';\n" +
                  "	UPDATE dnparroquias SET par_nombre= REPLACE(par_nombre, 'Ã©', 'é') WHERE par_nombre LIKE '%Ã©%';\n" +
                  "	UPDATE dnparroquias SET par_nombre= REPLACE(par_nombre, 'Ã­', 'í') WHERE par_nombre LIKE '%Ã­%';\n" +
                  "	UPDATE dnparroquias SET par_nombre= REPLACE(par_nombre, 'Ã³', 'ó') WHERE par_nombre LIKE '%Ã³%';\n" +
                  "	UPDATE dnparroquias SET par_nombre= REPLACE(par_nombre, 'Ãº', 'ú') WHERE par_nombre LIKE '%Ãº%';\n" +
                  "	UPDATE dnparroquias SET par_nombre= REPLACE(par_nombre, 'Ã‘', 'ñ') WHERE par_nombre LIKE '%Ã‘%';\n" +
                  "	UPDATE dnparroquias SET par_nombre= REPLACE(par_nombre, 'Ãš', 'Ú') WHERE par_nombre LIKE '%Ãš%';\n" +
                  "	UPDATE dnparroquias SET par_nombre= REPLACE(par_nombre, 'Ã“', 'Ó') WHERE par_nombre LIKE '%Ã“%';\n" +
                  "	UPDATE dnparroquias SET par_nombre= REPLACE(par_nombre, 'Ã�', 'Í') WHERE par_nombre LIKE '%Ã�%';\n" +
                  "	UPDATE dnparroquias SET par_nombre= REPLACE(par_nombre, 'Ã‰', 'É') WHERE par_nombre LIKE '%Ã‰%';\n" +
                  "	\n" +
                  "	UPDATE dnsector SET sbs_nombre= REPLACE(sbs_nombre, 'Ã¡', 'á') WHERE sbs_nombre LIKE '%Ã¡%';\n" +
                  "	UPDATE dnsector SET sbs_nombre= REPLACE(sbs_nombre, 'Ã©', 'é') WHERE sbs_nombre LIKE '%Ã©%';\n" +
                  "	UPDATE dnsector SET sbs_nombre= REPLACE(sbs_nombre, 'Ã­', 'í') WHERE sbs_nombre LIKE '%Ã­%';\n" +
                  "	UPDATE dnsector SET sbs_nombre= REPLACE(sbs_nombre, 'Ã³', 'ó') WHERE sbs_nombre LIKE '%Ã³%';\n" +
                  "	UPDATE dnsector SET sbs_nombre= REPLACE(sbs_nombre, 'Ãº', 'ú') WHERE sbs_nombre LIKE '%Ãº%';\n" +
                  "	UPDATE dnsector SET sbs_nombre= REPLACE(sbs_nombre, 'Ã‘', 'ñ') WHERE sbs_nombre LIKE '%Ã‘%';\n" +
                  "	UPDATE dnsector SET sbs_nombre= REPLACE(sbs_nombre, 'Ã±', 'ñ') WHERE sbs_nombre LIKE '%Ã±%';\n" +
                  "	UPDATE dnsector SET sbs_nombre= REPLACE(sbs_nombre, 'Ãš', 'Ú') WHERE sbs_nombre LIKE '%Ãš%';\n" +
                  "	UPDATE dnsector SET sbs_nombre= REPLACE(sbs_nombre, 'Ã“', 'Ó') WHERE sbs_nombre LIKE '%Ã“%';\n" +
                  "	UPDATE dnsector SET sbs_nombre= REPLACE(sbs_nombre, 'Ã�', 'Í') WHERE sbs_nombre LIKE '%Ã�%';\n" +
                  "	UPDATE dnsector SET sbs_nombre= REPLACE(sbs_nombre, 'Ã‰', 'É') WHERE sbs_nombre LIKE '%Ã‰%';\n" +
                  "\n" +
                  "	UPDATE dnrol SET rol_nombre= REPLACE(rol_nombre, 'Ã¡', 'á') WHERE rol_nombre LIKE '%Ã¡%';\n" +
                  "	UPDATE dnrol SET rol_nombre= REPLACE(rol_nombre, 'Ã©', 'é') WHERE rol_nombre LIKE '%Ã©%';\n" +
                  "	UPDATE dnrol SET rol_nombre= REPLACE(rol_nombre, 'Ã­', 'í') WHERE rol_nombre LIKE '%Ã­%';\n" +
                  "	UPDATE dnrol SET rol_nombre= REPLACE(rol_nombre, 'Ã³', 'ó') WHERE rol_nombre LIKE '%Ã³%';\n" +
                  "	UPDATE dnrol SET rol_nombre= REPLACE(rol_nombre, 'Ãº', 'ú') WHERE rol_nombre LIKE '%Ãº%';\n" +
                  "	UPDATE dnrol SET rol_nombre= REPLACE(rol_nombre, 'Ã‘', 'ñ') WHERE rol_nombre LIKE '%Ã‘%';\n" +
                  "	UPDATE dnrol SET rol_nombre= REPLACE(rol_nombre, 'Ãš', 'Ú') WHERE rol_nombre LIKE '%Ãš%';\n" +
                  "	UPDATE dnrol SET rol_nombre= REPLACE(rol_nombre, 'Ã“', 'Ó') WHERE rol_nombre LIKE '%Ã“%';\n" +
                  "	UPDATE dnrol SET rol_nombre= REPLACE(rol_nombre, 'Ã�', 'Í') WHERE rol_nombre LIKE '%Ã�%';\n" +
                  "	UPDATE dnrol SET rol_nombre= REPLACE(rol_nombre, 'Ã‰', 'É') WHERE rol_nombre LIKE '%Ã‰%';\n" +
                  "\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_nombre= REPLACE(men_nombre, 'Ã¡', 'á') WHERE men_nombre LIKE '%Ã¡%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_nombre= REPLACE(men_nombre, 'Ã©', 'é') WHERE men_nombre LIKE '%Ã©%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_nombre= REPLACE(men_nombre, 'Ã­', 'í') WHERE men_nombre LIKE '%Ã­%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_nombre= REPLACE(men_nombre, 'Ã³', 'ó') WHERE men_nombre LIKE '%Ã³%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_nombre= REPLACE(men_nombre, 'Ãº', 'ú') WHERE men_nombre LIKE '%Ãº%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_nombre= REPLACE(men_nombre, 'Ã‘', 'ñ') WHERE men_nombre LIKE '%Ã‘%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_nombre= REPLACE(men_nombre, 'Ãš', 'Ú') WHERE men_nombre LIKE '%Ãš%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_nombre= REPLACE(men_nombre, 'Ã“', 'Ó') WHERE men_nombre LIKE '%Ã“%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_nombre= REPLACE(men_nombre, 'Ã�', 'Í') WHERE men_nombre LIKE '%Ã�%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_nombre= REPLACE(men_nombre, 'Ã‰', 'É') WHERE men_nombre LIKE '%Ã‰%';\n" +
                  "\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_descripcion= REPLACE(men_descripcion, 'Ã¡', 'á') WHERE men_descripcion LIKE '%Ã¡%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_descripcion= REPLACE(men_descripcion, 'Ã©', 'é') WHERE men_descripcion LIKE '%Ã©%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_descripcion= REPLACE(men_descripcion, 'Ã­', 'í') WHERE men_descripcion LIKE '%Ã­%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_descripcion= REPLACE(men_descripcion, 'Ã³', 'ó') WHERE men_descripcion LIKE '%Ã³%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_descripcion= REPLACE(men_descripcion, 'Ãº', 'ú') WHERE men_descripcion LIKE '%Ãº%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_descripcion= REPLACE(men_descripcion, 'Ã‘', 'ñ') WHERE men_descripcion LIKE '%Ã‘%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_descripcion= REPLACE(men_descripcion, 'Ãš', 'Ú') WHERE men_descripcion LIKE '%Ãš%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_descripcion= REPLACE(men_descripcion, 'Ã“', 'Ó') WHERE men_descripcion LIKE '%Ã“%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_descripcion= REPLACE(men_descripcion, 'Ã�', 'Í') WHERE men_descripcion LIKE '%Ã�%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_descripcion= REPLACE(men_descripcion, 'Ã‰', 'É') WHERE men_descripcion LIKE '%Ã‰%';\n" +
                  "\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_url= REPLACE(men_url, 'Ã¡', 'á') WHERE men_url LIKE '%Ã¡%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_url= REPLACE(men_url, 'Ã©', 'é') WHERE men_url LIKE '%Ã©%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_url= REPLACE(men_url, 'Ã­', 'í') WHERE men_url LIKE '%Ã­%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_url= REPLACE(men_url, 'Ã³', 'ó') WHERE men_url LIKE '%Ã³%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_url= REPLACE(men_url, 'Ãº', 'ú') WHERE men_url LIKE '%Ãº%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_url= REPLACE(men_url, 'Ã‘', 'ñ') WHERE men_url LIKE '%Ã‘%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_url= REPLACE(men_url, 'Ãš', 'Ú') WHERE men_url LIKE '%Ãš%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_url= REPLACE(men_url, 'Ã“', 'Ó') WHERE men_url LIKE '%Ã“%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_url= REPLACE(men_url, 'Ã�', 'Í') WHERE men_url LIKE '%Ã�%';\n" +
                  "	UPDATE "+VarGlobales.getBaseDatos()+".dnmenu SET men_url= REPLACE(men_url, 'Ã‰', 'É') WHERE men_url LIKE '%Ã‰%';\n" +
                  "END";
                  //"END//\n" +
                  //"DELIMITER ;";
            
            conet.creaConexion();
            conet.consulta = conet.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            conet.consulta.executeUpdate();
            conet.consulta.close();
            conet.conn.close();
            
            conet.creaConexion();
            sql = "{call sp_corrigeAcentos()}";
            cs = conet.conn.prepareCall(sql);
            conet.insertDeleteUpdate_StoreProcedure(cs);
            
            VarGlobales.setlBaseDatosConfiguracion(false);
            conet.creaConexion();
            try {
                sql = "DROP PROCEDURE IF EXISTS sp_datos_xml_factura_electronica_CRC;\n";
                conet.consulta = conet.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                conet.consulta.executeUpdate();
            
                conet.consulta.close();
                conet.conn.close();
            } catch (Exception e) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
            }
            
            sql = "CREATE DEFINER=`root`@`%` PROCEDURE sp_datos_xml_factura_electronica_CRC(\n" +
                  "	IN `codEmp` VARCHAR(6),\n" +
                  "	IN `nunDocumento` VARCHAR(50),\n" +
                  "	IN `tipDoc` VARCHAR(5)\n" +
                  ")\n" +
                  "LANGUAGE SQL\n" +
                  "NOT DETERMINISTIC\n" +
                  "CONTAINS SQL\n" +
                  "SQL SECURITY DEFINER\n" +
                  "COMMENT ''\n" +
                  "BEGIN\n" +
                  "	SELECT \n" +
                  "# *********** datos de Emisor ***********\n" +
                  "			 emp_nombre AS nombreEmisor,\n" +
                  "  			# ********** Identificacion Receptor **********\n" +
                  "  			 IF((SELECT name FROM adminconfigestableerp.type_person WHERE adminconfigestableerp.type_person.id_type_person=adminconfigestableerp.dnempresas.emp_tipoidentificador)='Cedula Fisica' OR (SELECT name FROM adminconfigestableerp.type_person WHERE adminconfigestableerp.type_person.id_type_person=adminconfigestableerp.dnempresas.emp_tipoidentificador)='Fisica', '01',\n" +
                  "				IF((SELECT name FROM adminconfigestableerp.type_person WHERE adminconfigestableerp.type_person.id_type_person=adminconfigestableerp.dnempresas.emp_tipoidentificador)='Cedula Juridica' OR \n" +
                  "				   (SELECT name FROM adminconfigestableerp.type_person WHERE adminconfigestableerp.type_person.id_type_person=adminconfigestableerp.dnempresas.emp_tipoidentificador)='Juridica'  OR \n" +
                  "					(SELECT name FROM adminconfigestableerp.type_person WHERE adminconfigestableerp.type_person.id_type_person=adminconfigestableerp.dnempresas.emp_tipoidentificador)='Jurídica','02',\n" +
                  "					if((SELECT name FROM adminconfigestableerp.type_person WHERE adminconfigestableerp.type_person.id_type_person=adminconfigestableerp.dnempresas.emp_tipoidentificador)='DIMEX','03',\n" +
                  "						IF((SELECT name FROM adminconfigestableerp.type_person WHERE adminconfigestableerp.type_person.id_type_person=adminconfigestableerp.dnempresas.emp_tipoidentificador)='NITE','04','')))) AS tipoEmisor,\n" +
                  "			 emp_rif AS identificadorEmisor,\n" +
                  "			# ********** datos Ubicacion Receptor ********** \n" +
                  "			 IF(ISNULL((SELECT est_nombre FROM adminconfigestableerp.dnestados WHERE est_id=adminconfigestableerp.dnempresas.emp_estado)),'',(SELECT est_nombre FROM adminconfigestableerp.dnestados WHERE est_id=adminconfigestableerp.dnempresas.emp_estado)) AS nombreProvinciaEmisor,  \n" +
                  "			 IF(ISNULL((SELECT codigo FROM adminconfigestableerp.dnestados WHERE est_id=adminconfigestableerp.dnempresas.emp_estado)),'', (SELECT codigo FROM adminconfigestableerp.dnestados WHERE est_id=adminconfigestableerp.dnempresas.emp_estado)) AS xmlCodProvinciaEmisor, \n" +
                  "			 IF(ISNULL((SELECT mun_nombre FROM adminconfigestableerp.dnmunicipios WHERE mun_id=adminconfigestableerp.dnempresas.emp_municipio)), '', (SELECT mun_nombre FROM adminconfigestableerp.dnmunicipios WHERE mun_id=adminconfigestableerp.dnempresas.emp_municipio)) AS nombreCantonEmisor,  \n" +
                  "			 IF(ISNULL((SELECT codigo FROM adminconfigestableerp.dnmunicipios WHERE mun_id=adminconfigestableerp.dnempresas.emp_municipio)), '', (SELECT codigo FROM adminconfigestableerp.dnmunicipios WHERE mun_id=adminconfigestableerp.dnempresas.emp_municipio)) AS xmlCodCantonEmisor,\n" +
                  "			 IF(ISNULL((SELECT par_nombre FROM adminconfigestableerp.dnparroquias WHERE par_id=adminconfigestableerp.dnempresas.emp_parroquia)), '', (SELECT par_nombre FROM adminconfigestableerp.dnparroquias WHERE par_id=adminconfigestableerp.dnempresas.emp_parroquia)) AS nombreDistritoEmisor,  \n" +
                  "			 IF(ISNULL((SELECT codigo FROM adminconfigestableerp.dnparroquias WHERE par_id=adminconfigestableerp.dnempresas.emp_parroquia)), '', (SELECT codigo FROM adminconfigestableerp.dnparroquias WHERE par_id=adminconfigestableerp.dnempresas.emp_parroquia)) AS xmlCodDistritoEmisor,\n" +
                  "			 IF(ISNULL((SELECT sbs_nombre FROM adminconfigestableerp.dnsector WHERE sbs_id=adminconfigestableerp.dnempresas.emp_sector)), '', (SELECT sbs_nombre FROM adminconfigestableerp.dnsector WHERE sbs_id=adminconfigestableerp.dnempresas.emp_sector)) AS nombreBarrioEmisor,  \n" +
                  "			 IF(ISNULL((SELECT codigo FROM adminconfigestableerp.dnsector WHERE sbs_id=adminconfigestableerp.dnempresas.emp_sector)), '', (SELECT codigo FROM adminconfigestableerp.dnsector WHERE sbs_id=adminconfigestableerp.dnempresas.emp_sector)) AS xmlCodBarrioEmisor,\n" +
                  "			 emp_direccion AS direccionEmisor,\n" +
                  "			# ********** telefono Receptor **********\n" +
                  "			 IF(ISNULL(emp_telefono), '', emp_telefono) AS telefonoEmisor,\n" +
                  "			# ********** fax Receptor **********\n" +
                  "	 		 IF(ISNULL(emp_fax), '', emp_fax) AS faxEmisor,\n" +
                  "			# ********** correo Receptor **********\n" +
                  "	 		 IF(ISNULL(emp_correo), '', emp_correo) AS correoEmisor,\n" +
                  "#****************************************\n" +
                  "\n" +
                  "# ********** datos de Receptor **********\n" +
                  "	       razon_social AS nombreReceptor, \n" +
                  "  			# ********** Identificacion Receptor **********\n" +
                  "  			 IF((SELECT name FROM adminconfigestableerp.type_person WHERE adminconfigestableerp.type_person.id_type_person=dnpersonas.tip_per)='Cedula Fisica' OR \n" +
                  "				 (SELECT name FROM adminconfigestableerp.type_person WHERE adminconfigestableerp.type_person.id_type_person=dnpersonas.tip_per)='Fisica', '01',\n" +
                  "				IF((SELECT name FROM adminconfigestableerp.type_person WHERE adminconfigestableerp.type_person.id_type_person=dnpersonas.tip_per)='Cedula Juridica' OR \n" +
                  "			      (SELECT name FROM adminconfigestableerp.type_person WHERE adminconfigestableerp.type_person.id_type_person=dnpersonas.tip_per)='Juridica' OR \n" +
                  "					(SELECT name FROM adminconfigestableerp.type_person WHERE adminconfigestableerp.type_person.id_type_person=dnpersonas.tip_per)='Jurídica','02',\n" +
                  "					if((SELECT name FROM adminconfigestableerp.type_person WHERE adminconfigestableerp.type_person.id_type_person=dnpersonas.tip_per)='DIMEX','03',\n" +
                  "						IF((SELECT name FROM adminconfigestableerp.type_person WHERE adminconfigestableerp.type_person.id_type_person=dnpersonas.tip_per)='NITE','04','')))) AS tipoReceptor,\n" +
                  "			 rif AS identificadorReceptor, \n" +
                  "			# ********** datos Ubicacion Receptor ********** \n" +
                  "			 IF(ISNULL((SELECT est_nombre FROM adminconfigestableerp.dnestados WHERE est_id=dir_codest)),'',(SELECT est_nombre FROM adminconfigestableerp.dnestados WHERE est_id=dir_codest)) AS nombreProvinciaRecptor,  \n" +
                  "			 IF(ISNULL((SELECT codigo FROM adminconfigestableerp.dnestados WHERE est_id=dir_codest)),'', (SELECT codigo FROM adminconfigestableerp.dnestados WHERE est_id=dir_codest)) AS xmlCodProvinciaReceptor, \n" +
                  "			 IF(ISNULL((SELECT mun_nombre FROM adminconfigestableerp.dnmunicipios WHERE mun_id=dir_codmun)), '', (SELECT mun_nombre FROM adminconfigestableerp.dnmunicipios WHERE mun_id=dir_codmun)) AS nombreCantonReceptor,  \n" +
                  "			 IF(ISNULL((SELECT codigo FROM adminconfigestableerp.dnmunicipios WHERE mun_id=dir_codmun)), '', (SELECT codigo FROM adminconfigestableerp.dnmunicipios WHERE mun_id=dir_codmun)) AS xmlCodCantonReceptor,\n" +
                  "			 IF(ISNULL((SELECT par_nombre FROM adminconfigestableerp.dnparroquias WHERE par_id=dir_codpar)), '', (SELECT par_nombre FROM adminconfigestableerp.dnparroquias WHERE par_id=dir_codpar)) AS nombreDistritoReceptor,  \n" +
                  "			 IF(ISNULL((SELECT codigo FROM adminconfigestableerp.dnparroquias WHERE par_id=dir_codpar)), '', (SELECT codigo FROM adminconfigestableerp.dnparroquias WHERE par_id=dir_codpar)) AS xmlCodDistritoReceptor,\n" +
                  "			 IF(ISNULL((SELECT sbs_nombre FROM adminconfigestableerp.dnsector WHERE sbs_id=dir_codsbs)), '', (SELECT sbs_nombre FROM adminconfigestableerp.dnsector WHERE sbs_id=dir_codsbs)) AS nombreBarrioReceptor,  \n" +
                  "			 IF(ISNULL((SELECT codigo FROM adminconfigestableerp.dnsector WHERE sbs_id=dir_codsbs)), '', (SELECT codigo FROM adminconfigestableerp.dnsector WHERE sbs_id=dir_codsbs)) AS xmlCodBarrioReceptor,\n" +
                  "			 dir_descri AS direccionReceptor,\n" +
                  "			# ********** telefono Receptor **********\n" +
                  "			 IF(ISNULL((SELECT con_contac FROM dncontactos LEFT JOIN dntipcontacto ON dntipcontacto.tcon_id=2 WHERE con_pers_id=dninventario.pers_id AND con_codtipc=2 LIMIT 1)), '',\n" +
                  "			           (SELECT con_contac FROM dncontactos LEFT JOIN dntipcontacto ON dntipcontacto.tcon_id=2 WHERE con_pers_id=dninventario.pers_id AND con_codtipc=2 LIMIT 1)) AS telefonoReceptor,\n" +
                  "			# ********** fax Receptor **********\n" +
                  "	 		 IF(ISNULL((SELECT con_contac FROM dncontactos LEFT JOIN dntipcontacto ON dntipcontacto.tcon_id=9 WHERE con_pers_id=dninventario.pers_id AND con_codtipc=9 LIMIT 1)), '',\n" +
                  "			           (SELECT con_contac FROM dncontactos LEFT JOIN dntipcontacto ON dntipcontacto.tcon_id=9 WHERE con_pers_id=dninventario.pers_id AND con_codtipc=9 LIMIT 1)) AS faxReceptor,\n" +
                  "			# ********** correo Receptor **********\n" +
                  "	 		 #IF(ISNULL((SELECT con_contac FROM dncontactos LEFT JOIN dntipcontacto ON dntipcontacto.tcon_id=1 WHERE con_pers_id=dninventario.pers_id AND con_codtipc=1 LIMIT 1)), '', \n" +
                  "			 #          (SELECT con_contac FROM dncontactos LEFT JOIN dntipcontacto ON dntipcontacto.tcon_id=1 WHERE con_pers_id=dninventario.pers_id AND con_codtipc=1 LIMIT 1)) AS correoReceptor,\n" +
                  "			 correo_envio_cnbt_electronic AS correoReceptor,\n" +
                  "# ***************************************\n" +
                  "\n" +
                  "# ********** CondicionVenta **********\n" +
                  "			 IF(inv_condic='Contado', '01',\n" +
                  "				IF(inv_condic='Crédito','02',\n" +
                  "					if(inv_condic='Consignación','03',\n" +
                  "						IF(inv_condic='Apartado','04',\n" +
                  "							IF(inv_condic='Arrendamiento con opción de compra','05',\n" +
                  "								IF(inv_condic='Arrendamiento en función financiera','06',\n" +
                  "									IF(inv_condic='Otros','99',''))))))) \n" +
                  "			 AS codicionVenta,\n" +
                  "# ************************************\n" +
                  "\n" +
                  "# *********** PlazoCredito ***********\n" +
                  "			 #IF((SELECT cdi_descri FROM dncondipago WHERE dncondipago.cdi_id=dninventario.inv_condic)='CrÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â©dito',pers_cred_dias,'') AS plazoCredito,\n" +
                  "			 inv_cred_dias AS plazoCredito,\n" +
                  "# ************************************\n" +
                  "\n" +
                  "# ************ MedioPago *************\n" +
                  "			 IF((SELECT ins_descri FROM dninstrumentopago WHERE dninstrumentopago.ins_codigo=(SELECT type_doc_ori FROM dninventario WHERE inv_empresa=codEmp AND inv_coddoc=inv_coddoc AND numdoc_orig=nunDocumento AND inv_activo=1 AND inv_status<>'Anulado' LIMIT 1) LIMIT 1)='Efectivo', '01',\n" +
                  "				IF((SELECT ins_descri FROM dninstrumentopago WHERE dninstrumentopago.ins_codigo=(SELECT type_doc_ori FROM dninventario WHERE inv_empresa=codEmp AND inv_coddoc=inv_coddoc AND numdoc_orig=nunDocumento AND inv_activo=1 AND inv_status<>'Anulado' LIMIT 1) LIMIT 1)='Tarjeta','02',\n" +
                  "					if((SELECT ins_descri FROM dninstrumentopago WHERE dninstrumentopago.ins_codigo=(SELECT type_doc_ori FROM dninventario WHERE inv_empresa=codEmp AND inv_coddoc=inv_coddoc AND numdoc_orig=nunDocumento AND inv_activo=1 AND inv_status<>'Anulado' LIMIT 1) LIMIT 1)='Cheque','03',\n" +
                  "						IF((SELECT ins_descri FROM dninstrumentopago WHERE dninstrumentopago.ins_codigo=(SELECT type_doc_ori FROM dninventario WHERE inv_empresa=codEmp AND inv_coddoc=inv_coddoc AND numdoc_orig=nunDocumento AND inv_activo=1 AND inv_status<>'Anulado' LIMIT 1) LIMIT 1)='Transferencia - depósito bancario','04',\n" +
                  "							IF((SELECT ins_descri FROM dninstrumentopago WHERE dninstrumentopago.ins_codigo=(SELECT type_doc_ori FROM dninventario WHERE inv_empresa=codEmp AND inv_coddoc=inv_coddoc AND numdoc_orig=nunDocumento AND inv_activo=1 AND inv_status<>'Anulado' LIMIT 1) LIMIT 1)='Recaudado por terceros','05',\n" +
                  "								IF((SELECT ins_descri FROM dninstrumentopago WHERE dninstrumentopago.ins_codigo=(SELECT type_doc_ori FROM dninventario WHERE inv_empresa=codEmp AND inv_coddoc=inv_coddoc AND numdoc_orig=nunDocumento AND inv_activo=1 AND inv_status<>'Anulado' LIMIT 1) LIMIT 1)='Otros','99','')))))) \n" +
                  "			 AS medioPago,\n" +
                  "# ************************************\n" +
                  "\n" +
                  "# ************ DetalleServicio *************\n" +
                  "			# ********** NumeroLinea ********** \n" +
                  "			 inv_item AS numeroLinea,\n" +
                  "			 \n" +
                  "			# ********** Codigo Tipo ********** \n" +
                  "			 IF((SELECT doc_cxc FROM dndocumentos WHERE doc_codigo=dninventario.inv_coddoc)=1, '01',\n" +
                  "			 	IF((SELECT doc_cxp FROM dndocumentos WHERE doc_codigo=dninventario.inv_coddoc)=1,'02','')) AS tipo,\n" +
                  "			 	\n" +
                  "			# ********** Codigo Producto ********** \n" +
                  "			 inv_codpro AS codProducto,\n" +
                  "\n" +
                  "			# ********** Cantidad Producto ********** \n" +
                  "			 inv_cantid AS cantidad,\n" +
                  "\n" +
                  "			# ********** Unidad de Medida Producto ********** 			 \n" +
                  "			 inv_unidad AS unidad,\n" +
                  "			 \n" +
                  "			# ********** Detalle Producto ********** \n" +
                  "			 (SELECT dnproducto.pro_nombre FROM dnproducto WHERE dnproducto.pro_codigo=inv_codpro AND dnproducto.pro_empresa=codEmp ) AS nombreProducto,\n" +
                  "			 \n" +
                  "			# ********** Precio Unitario ********** \n" +
                  "			 inv_precio AS precioUnitario,\n" +
                  "\n" +
                  "			# ********** MontoTotal ********** 			 \n" +
                  "			 (inv_base*inv_cantid) AS montoTotal,\n" +
                  "			 \n" +
                  "			# ********** MontoDescuento **********\n" +
                  "			 #inv_despro,inv_dessub,inv_desdoc,\n" +
                  "			 inv_despro,\n" +
                  "			 \n" +
                  "			# ********** NaturalezaDescuento **********\n" +
                  "			inv_natural_dscto,\n" +
                  "\n" +
                  "			# ********** SubTotal **********\n" +
                  "			 inv_base AS subTotal,\n" +
                  "			 \n" +
                  "			# ********** Codigo Impuesto **********\n" +
                  "			 inv_tipiva,\n" +
                  "			 \n" +
                  "			# ********** Valor Impuesto **********\n" +
                  "			 IF(ISNULL((SELECT dntipiva.tiva_valven FROM dntipiva WHERE dntipiva.tiva_empresa=codEmp AND dntipiva.tiva_codigo=dninventario.inv_tipiva)),\n" +
                  "			    0.00,\n" +
                  "				 (SELECT dntipiva.tiva_valven FROM dntipiva WHERE dntipiva.tiva_empresa=codEmp AND dntipiva.tiva_codigo=dninventario.inv_tipiva)) \n" +
                  "			 AS tarifaImpuesto,\n" +
                  "			 \n" +
                  "			# ********** Monto Impuesto **********\n" +
                  "			 inv_mtoiva AS montoImpuesto,\n" +
                  "\n" +
                  "			# ********** Exoneracion **********			 \n" +
                  "			 \n" +
                  "			 \n" +
                  "			# ********** MontoTotalLinea **********\n" +
                  "			 inv_total AS totalLinea,\n" +
                  "# ******************************************\n" +
                  "\n" +
                  "# ************* ResumenFactura *************\n" +
                  "			# ********** CodigoMoneda **********\n" +
                  "\n" +
                  "			# ********** TipoCambio **********\n" +
                  "			\n" +
                  "			# ********** TotalServGravados **********\n" +
                  "			 IF(ISNULL((SELECT SUM(inv_base*inv_cantid) FROM dninventario \n" +
                  "			            INNER JOIN dnproducto ON pro_codigo=dninventario.inv_codpro\n" +
                  "			            WHERE inv_empresa=codEmp AND dninventario.inv_numdoc=nunDocumento AND dnproducto.pro_miscel=1\n" +
                  "			            HAVING (SUM(inv_mtoiva))>0)), \n" +
                  "\n" +
                  "							0.00000, \n" +
                  "\n" +
                  "			  		     (SELECT SUM(inv_base*inv_cantid) FROM dninventario \n" +
                  "  					      INNER JOIN dnproducto ON pro_codigo=dninventario.inv_codpro\n" +
                  "					  		WHERE inv_empresa=codEmp AND dninventario.inv_numdoc=nunDocumento AND dnproducto.pro_miscel=1\n" +
                  "						   HAVING (SUM(inv_mtoiva))>0)) \n" +
                  "			 AS totalServGravados,\n" +
                  "			\n" +
                  "			# ********** TotalServExentos **********\n" +
                  "			 IF(ISNULL((SELECT SUM(inv_base*inv_cantid) FROM dninventario \n" +
                  "			            INNER JOIN dnproducto ON pro_codigo=dninventario.inv_codpro\n" +
                  "			            WHERE inv_empresa=codEmp AND dninventario.inv_numdoc=nunDocumento AND dnproducto.pro_miscel=1\n" +
                  "			            HAVING (SUM(inv_mtoiva))=0)), \n" +
                  "\n" +
                  "							0.00000, \n" +
                  "\n" +
                  "			  		     (SELECT SUM(inv_base*inv_cantid) FROM dninventario \n" +
                  "  					      INNER JOIN dnproducto ON pro_codigo=dninventario.inv_codpro\n" +
                  "					  		WHERE inv_empresa=codEmp AND dninventario.inv_numdoc=nunDocumento AND dnproducto.pro_miscel=1\n" +
                  "						   HAVING (SUM(inv_mtoiva))=0))\n" +
                  "			  \n" +
                  "			  AS totalServExentos,\n" +
                  "			\n" +
                  "			# ********** TotalMercanciasGravadas **********\n" +
                  "			 IF(ISNULL((SELECT SUM(inv_base*inv_cantid) FROM dninventario \n" +
                  "			            INNER JOIN dnproducto ON pro_codigo=dninventario.inv_codpro\n" +
                  "			            WHERE inv_empresa=codEmp AND dninventario.inv_numdoc=nunDocumento AND dnproducto.pro_miscel=0\n" +
                  "			            HAVING (SUM(inv_mtoiva))>0)), \n" +
                  "\n" +
                  "							0.00000, \n" +
                  "\n" +
                  "			  		     (SELECT SUM(inv_base*inv_cantid) FROM dninventario \n" +
                  "  					      INNER JOIN dnproducto ON pro_codigo=dninventario.inv_codpro\n" +
                  "					  		WHERE inv_empresa=codEmp AND dninventario.inv_numdoc=nunDocumento AND dnproducto.pro_miscel=0\n" +
                  "						   HAVING (SUM(inv_mtoiva))>0))\n" +
                  "			  \n" +
                  "			  AS totalMercanciasGravadas,\n" +
                  "			\n" +
                  "			# ********** TotalMercanciasExentas **********\n" +
                  "			 IF(ISNULL((SELECT SUM(inv_base*inv_cantid) FROM dninventario \n" +
                  "			            INNER JOIN dnproducto ON pro_codigo=dninventario.inv_codpro\n" +
                  "			            WHERE inv_empresa=codEmp AND dninventario.inv_numdoc=nunDocumento AND dnproducto.pro_miscel=0\n" +
                  "			            HAVING (SUM(inv_mtoiva))=0)), \n" +
                  "\n" +
                  "							0.00000, \n" +
                  "\n" +
                  "			  		     (SELECT SUM(inv_base*inv_cantid) FROM dninventario \n" +
                  "  					      INNER JOIN dnproducto ON pro_codigo=dninventario.inv_codpro\n" +
                  "					  		WHERE inv_empresa=codEmp AND dninventario.inv_numdoc=nunDocumento AND dnproducto.pro_miscel=0\n" +
                  "						   HAVING (SUM(inv_mtoiva))=0))\n" +
                  "			  \n" +
                  "			  AS totalMercanciasExentas,\n" +
                  "			\n" +
                  "			# ********** TotalGravado **********\n" +
                  "			 #(SELECT SUM(inv_base) FROM dninventario WHERE inv_empresa=codEmp AND inv_numdoc=nunDocumento AND inv_mtoiva>0) \n" +
                  "			 IF(ISNULL((SELECT SUM(inv_base*inv_cantid) FROM dninventario \n" +
                  "			            INNER JOIN dnproducto ON pro_codigo=dninventario.inv_codpro\n" +
                  "			            WHERE inv_empresa=codEmp AND dninventario.inv_numdoc=nunDocumento AND inv_mtoiva>0)), \n" +
                  "\n" +
                  "							0.00000, \n" +
                  "\n" +
                  "			  		     (SELECT SUM(inv_base*inv_cantid) FROM dninventario \n" +
                  "  					      INNER JOIN dnproducto ON pro_codigo=dninventario.inv_codpro\n" +
                  "					  		WHERE inv_empresa=codEmp AND dninventario.inv_numdoc=nunDocumento AND inv_mtoiva>0))\n" +
                  "			 \n" +
                  "			 AS totalGravado,\n" +
                  "			\n" +
                  "			# ********** TotalExento **********\n" +
                  "			 #(SELECT SUM(inv_base) FROM dninventario WHERE inv_empresa=codEmp AND inv_numdoc=nunDocumento AND inv_mtoiva=0) \n" +
                  "			 IF(ISNULL((SELECT SUM(inv_base*inv_cantid) FROM dninventario \n" +
                  "			            INNER JOIN dnproducto ON pro_codigo=dninventario.inv_codpro\n" +
                  "			            WHERE inv_empresa=codEmp AND dninventario.inv_numdoc=nunDocumento AND inv_mtoiva=0)), \n" +
                  "\n" +
                  "							0.00000, \n" +
                  "\n" +
                  "			  		     (SELECT SUM(inv_base*inv_cantid) FROM dninventario \n" +
                  "  					      INNER JOIN dnproducto ON pro_codigo=dninventario.inv_codpro\n" +
                  "					  		WHERE inv_empresa=codEmp AND dninventario.inv_numdoc=nunDocumento AND inv_mtoiva=0))\n" +
                  "			 \n" +
                  "			 AS totalExcento,\n" +
                  "			\n" +
                  "			# ********** TotalVenta **********\n" +
                  "			 (SELECT SUM(inv_base) FROM dninventario WHERE inv_empresa=codEmp AND inv_numdoc=nunDocumento) AS totalVenta,\n" +
                  "			\n" +
                  "			# ********** TotalDescuentos **********\n" +
                  "			(SELECT SUM(inv_despro) FROM dninventario WHERE inv_empresa=codEmp AND inv_numdoc=nunDocumento) AS totalDescuentos,\n" +
                  "			\n" +
                  "			# ********** TotalVentaNeta **********\n" +
                  "			 (SELECT SUM(inv_base) FROM dninventario WHERE inv_empresa=codEmp AND inv_numdoc=nunDocumento) AS totalVentaNeta,\n" +
                  "			 			\n" +
                  "			# ********** TotalImpuesto **********\n" +
                  "			 (SELECT SUM(inv_mtoiva) FROM dninventario WHERE inv_empresa=codEmp AND inv_numdoc=nunDocumento) AS totalImpuesto,\n" +
                  "			\n" +
                  "			# ********** TotalComprobante **********\n" +
                  "			 (SELECT SUM(inv_base)+SUM(inv_mtoiva) FROM dninventario WHERE inv_empresa=codEmp AND inv_numdoc=nunDocumento) AS totalComprobante,\n" +
                  "# ******************************************\n" +
                  "# ********** Comprueba si el articulo es un Servicio **********\n" +
                  " 			 (SELECT pro_miscel FROM dnproducto WHERE pro_codigo=inv_codpro) AS isService\n" +
                  "# *************************************************************\n" +
                  "	FROM dninventario \n" +
                  "	LEFT JOIN dnpersonas ON dnpersonas.pers_id=dninventario.pers_id\n" +
                  "	LEFT JOIN dndireccion ON dndireccion.dir_pers_id=dninventario.pers_id\n" +
                  "	INNER JOIN adminconfigestableerp.dnempresas ON adminconfigestableerp.dnempresas.emp_codigo=inv_empresa\n" +
                  "	WHERE inv_empresa=codEmp AND inv_numdoc=nunDocumento AND inv_coddoc=tipDoc AND dninventario.numdoc_orig IS NULL;\n" +
                  "END";
              
            conet.creaConexion();
            conet.consulta = conet.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            conet.consulta.executeUpdate();
            conet.consulta.close();
            conet.conn.close();
            
//            jCmbUser2 = new AutoComplete(comboUser);
//            jCmbUser2.setEditable(true);
//            jCmbUser2.setEnabled(true);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getUsuariosEmpresa(String sql, String codEmpresa){
        try {
            cs = conet.conn.prepareCall(sql);
            
            cs.setString(1, "SELECT");
            cs.setString(2, codEmpresa);
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
            
            rs = conet.consultarStoreProcedure(cs);
            
            int itemPais = 0;
            String pais = (String)rs.getString("emp_pais");
            String monedaEmpresa = (String)rs.getString("emp_moneda").trim();
            VarGlobales.setMonedaEmpresa(monedaEmpresa);
            VarGlobales.setBaseDatos(rs.getString("base_datos_empresa"));
            
            if (pais!=null){
                for (int i=0; i<elementos.size(); i++){
                    if (pais.equals((String) elementos.get(i))){
                        itemPais = i;
                    }
                }
                
                jComboIdioma.setSelectedItem(itemPais);
                CargarIdioma();
            }else{
                jComboIdioma.setSelectedItem(2);
                CargarIdioma();
            }
            
            VarGlobales.setlBaseDatosConfiguracion(false);
            comboUser();
            buscaPredeterminada = "";
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void acion_login() throws MalformedURLException{
        boolean login = false;
        
        //CrearVistaAsientosContables asientosContables = new CrearVistaAsientosContables();
        //asientosContables.armarVista();
        
        int Item = (int) jComboIdioma.getSelectedItem();
        Idioma=(String) elementos.get(Item);

        VarGlobales.setCodEmpresa(jCmbEmpresa1.getSelectedItem().toString().substring(0, 6));
        VarGlobales.setNomEmpresa(jCmbEmpresa1.getSelectedItem().toString().substring(jCmbEmpresa1.getSelectedItem().toString().indexOf("-")+1,jCmbEmpresa1.getSelectedItem().toString().length()));
        //login = ControlLogin.Login(txt_user.getText().toUpperCase(), txt_pass.getText().toUpperCase(), VarGlobales.getCodEmpresa());
        login = ControlLogin.Login(jCmbUser2.getSelectedItem().toString(), passFUser2.getText().toUpperCase(), VarGlobales.getCodEmpresa());

        if (login == true){
            //CargaRegVista cargaRegVista = new CargaRegVista();
            //cargaRegVista.start();

//**************************** valida Actualizacion de Estructura de Base de datos ****************************
            VarGlobales.validaActualizacionEstructuraBaseDatos();

            if (VarGlobales.islDetenerEjecucion()){
                dispose();
                ultimaEmpresa = jCmbEmpresa1.getSelectedItem().toString();
                ultimoUsuario = jCmbUser2.getSelectedItem().toString();
                return;
            }
//*************************************************************************************************************

//**************************** Carga Configuracion Empresa ****************************
            ResultSet rsConfEmp = configEmpresa.callStoreProcedureConfEmpresa("SELECT", VarGlobales.getCodEmpresa(), "", "", "", "", "", "", "", "",
                                                                              "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                                                                              "", "", "", "", "", "", "", "");

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
                                                 rsConfEmp.getString("consecutivoNotaCreditoElectrico"),
                                                 rsConfEmp.getString("consecutivoNotaDebitoElectrico"),
                                                 rsConfEmp.getString("consecutivoTiqueElectrico"),
                                                 rsConfEmp.getString("urlAccesToken"),
                                                 rsConfEmp.getString("consecutivoReceptor"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
//*****************************************************************************************
//*****************************************************************************************
//*****************************************************************************************
//*****************************************************************************************

//**************************** Asignacion de Variables publica ****************************
            //"WHERE OPE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND " +
            String sql = "SELECT DISTINCT dnusuarios.OPE_NUMERO,OPE_NOMBRE,OPE_USUARIO,PER_ID FROM dnusuarios \n" +
                         "inner join relac_usuario_grupo_permisos on relac_usuario_grupo_permisos.ope_numero=dnusuarios.ope_numero\n" +
                         "WHERE OPE_USUARIO='"+jCmbUser2.getSelectedItem().toString()+"' AND emp_codigo='"+VarGlobales.getCodEmpresa()+"'";
            Object element[][] = ControlLogin.VariablesP(sql);

            insertPermis(jCmbEmpresa1.getSelectedItem().toString().substring(0, 6) ,element[0][3].toString());
            //VarGlobales.setCodEmpresa(element[0][0].toString());
            VarGlobales.setIdUser(element[0][0].toString());
            VarGlobales.setUserName(element[0][1].toString());
            VarGlobales.setGrupPermiso(element[0][3].toString());

            String pais = (String) elementos.get(Item);
            VarGlobales.setPais(pais);

            GetMacIp macip = new GetMacIp();
            Object MacIp[][] = macip.GetMacIp();
               
            VarGlobales.setMacPc(MacIp[0][0].toString());
            VarGlobales.setIpPc(MacIp[0][1].toString());

            VarGlobales.setContinuaSesion(false);
            
//****************** Registro Automatico de Equipo ******************
            SQLQuerys insert = new SQLQuerys();
            String consecutivoEquipo = "", hostname = "";

            try {
                try {
                    InetAddress addr = InetAddress.getByName(VarGlobales.getIpPc());
                    hostname = addr.getHostName();
                } catch (UnknownHostException e){
                    System.err.println("ERROR!! :"+e.getMessage());
                }

                ResultSet rsBuscaEquipo = conet.consultar("SELECT * FROM identificacion_euipos \n"+
                                                          "WHERE codEmpresa='"+VarGlobales.getCodEmpresa()+"' AND "+
                                                          "(mac_address='"+VarGlobales.getMacPc()+"' OR "+
                                                          "nombre_equipo='"+hostname+"');");
                
                if (rsBuscaEquipo.getRow()==0){
                    CodigoConsecutivo codigo = new CodigoConsecutivo();
//                    consecutivoEquipo = codigo.CodigoConsecutivo("CAST(REPLACE(id_equipo,'-','') AS DECIMAL)","identificacion_euipos",5,
//                                                                 "WHERE codEmpresa='"+VarGlobales.getCodEmpresa()+"' AND "+
//                                                                 "mac_address='"+VarGlobales.getMacPc()+"' AND "+
//                                                                 "nombre_equipo='"+hostname+"'");
                    consecutivoEquipo = codigo.CodigoConsecutivo("CAST(REPLACE(id_equipo,'-','') AS DECIMAL)","identificacion_euipos",5,
                                                                 "WHERE codEmpresa='"+VarGlobales.getCodEmpresa()+"'");

                    if(consecutivoEquipo==null || consecutivoEquipo.equals("")){
                        consecutivoEquipo = "00001";
                    }
        
//                    sql = "REPLACE INTO identificacion_euipos (codEmpresa, id_equipo, mac_address, consecutivo_equipo_fe, consecutivo_equipo_te,"+
//                                                              "consecutivo_equipo_nc, consecutivo_equipo_nd, nombre_equipo)"+
//                          "                             VALUES('"+VarGlobales.getCodEmpresa()+"','"+consecutivoEquipo+"','"+VarGlobales.getMacPc()+"',"+
//                                                              "'','','','','"+hostname+"');";
                    sql = "REPLACE INTO identificacion_euipos (codEmpresa, id_equipo, mac_address, nombre_equipo)"+
                          "                             VALUES('"+VarGlobales.getCodEmpresa()+"','"+consecutivoEquipo+"','"+VarGlobales.getMacPc()+"',"+
                                                              "'"+hostname+"');";

                    insert.SqlInsert(sql);
                }

                ResultSet codEquipo = conet.consultar("SELECT id_equipo FROM identificacion_euipos \n"+
                                                      "WHERE codEmpresa='"+VarGlobales.getCodEmpresa()+"' AND "+
                                                      "(mac_address='"+VarGlobales.getMacPc()+"' OR "+
                                                      "nombre_equipo='"+hostname+"');");
                VarGlobales.setCodigoEquipo(codEquipo.getString("id_equipo"));
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
//*******************************************************************
            
            try {
                VarGlobales.setlBaseDatosConfiguracion(true);
                ResultSet rsIdPersEmp = conet.consultar("SELECT pers_id FROM dnempresas WHERE emp_codigo='"+VarGlobales.getCodEmpresa()+"'");
                VarGlobales.setlBaseDatosConfiguracion(false);
                
                if (rsIdPersEmp.getRow()>0){
                    VarGlobales.setPersId(rsIdPersEmp.getInt("pers_id"));
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
//*****************************************************************************************
//*****************************************************************************************
//*****************************************************************************************
//*****************************************************************************************
            
//*****************************************************************************************

// LLamar al POS o al Formulario de Seleccion
//            principal prin = new principal();
//            prin.show();
//            prin.setExtendedState(principal.MAXIMIZED_BOTH); // Para mostrer el Formulario Maximizado
//            prin.setVisible(true);
            
            VarGlobales.getImagenes(VarGlobales.getCodEmpresa());

            ResultSet rsPerUser = null;
            try {
                String sqlConsult = "SELECT MIS_PERMISO,MIS_MENU,MIS_POS,MIS_ACTIVO,MEN_NOMBRE FROM dnpermisologia \n" +
                                    "INNER JOIN dnmenu ON MEN_ID=MIS_MENU \n"+
                                    "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+element[0][3].toString()+" AND \n"+
                                    "MIS_POS=1 AND MIS_ACTIVO=1";
                rsPerUser = conet.consultar(sqlConsult);
                
                if(rsPerUser.getRow()>0){
                    actualizaCaja();
                    if (rsPerUser.getRow()==1 && rsPerUser.getString("MEN_NOMBRE").equals("Punto de Venta")){
//                        JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgAccesoSistemaPOS"),
//                                    idioma.loadLangMsg().getString("MsgTituloNotif"), JOptionPane.INFORMATION_MESSAGE);
//                        
                        validaHoraJornada();
//                        POS pos = new POS();
//                        pos.show();
//                        pos.setExtendedState(POS.MAXIMIZED_BOTH); // Para mostrer el Formulario Maximizado
//                        pos.setVisible(true);
                    }else{
                        MenuPrincipal apos = new MenuPrincipal();
                        apos.show();
                        apos.setExtendedState(MenuPrincipal.MAXIMIZED_BOTH); // Para mostrer el Formulario Maximizado
                        apos.setVisible(true);
                    }
                    
                    validaAñoFiscal();
                }else{
                    JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgPermisoActivos"), 
                                idioma.loadLangMsg().getString("MsgTituloNotif"), JOptionPane.ERROR_MESSAGE);
                }
                
                conet.conn.close();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }

            Bitacora insertar = new Bitacora(VarGlobales.getCodEmpresa(), VarGlobales.getMacPc(), VarGlobales.getIdUser(),
                                             VarGlobales.getUserName(), "inicio sesión", 
                                             "Usuario "+VarGlobales.getUserName().trim()+" ha iniciado sesión");
            //txt_user.setText(""); 
            jCmbUser2.setSelectedIndex(0);
            passFUser2.setText("");
            
            dispose();
            
//            VarGlobales.getImagenes(VarGlobales.getCodEmpresa());
        }else{
            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgUserIncorrect"), 
                                idioma.loadLangMsg().getString("MsgTituloNotif"), JOptionPane.ERROR_MESSAGE);
            
            passFUser2.requestFocus();
        }
    }
    
    public final void comboUser() {
//        String sql = "SELECT CONCAT(OPE_USUARIO) AS DATO1 FROM dnusuarios WHERE OPE_ACTIVO=1";
        String sql = "SELECT CONCAT(OPE_USUARIO) AS DATO1 FROM dnusuarios\n" +
                     "INNER JOIN relac_usuario_grupo_permisos ON relac_usuario_grupo_permisos.ope_numero=dnusuarios.ope_numero\n" +
                     "WHERE OPE_ACTIVO=1 AND emp_codigo='"+jCmbEmpresa1.getSelectedItem().toString().substring(0, 6)+"'";
        DefaultComboBoxModel mdl = ControlLogin.DatosCombo(sql);
        
        jCmbUser2.setModel(mdl);
        jCmbUser2.setSelectedIndex(0);
        
//        comboUser = new String[mdl.getSize()];
//        for (int i=0; i<mdl.getSize(); i++){
//            comboUser[i] = (String) mdl.getElementAt(i);
//        }
        
        VarGlobales.setCodEmpresa(jCmbEmpresa1.getSelectedItem().toString().substring(0, 6));
        //VarGlobales.setCodEmpresa("000001");
    }
    
    private void validaAñoFiscal(){
        ResultSet rs = null;
        GregorianCalendar calendario = new GregorianCalendar();
        int año = calendario.get(Calendar.YEAR);
        String sql = "SELECT * FROM cierres_contable WHERE emp_codigo='"+VarGlobales.getCodEmpresa()+"' and "+
                     "cierre_fiscal='"+año+"'";
        
        try {
            rs = conet.consultar(sql);
            
            try {
                if(rs.getRow()==0){
                    for(int i=1;i<13;i++){
                        String desde = String.valueOf(año);
                        String hasta = String.valueOf(año);
                        
                        switch(i){
                            case 1:
                                desde = desde+"-0"+String.valueOf(i)+"-01";
                                hasta = hasta+"-0"+String.valueOf(i)+"-31";
                                
                                break;
                            case 2:
                                String dia="28";
                                desde = desde+"-0"+String.valueOf(i)+"-01";
                                
                                if (calendario.isLeapYear(año)){
                                    dia="29";
                                }
                                hasta = hasta+"-0"+String.valueOf(i)+"-"+dia;
                                
                                break;
                            case 3:
                                desde = desde+"-0"+String.valueOf(i)+"-01";
                                hasta = hasta+"-0"+String.valueOf(i)+"-31";
                                
                                break;
                            case 4:
                                desde = desde+"-0"+String.valueOf(i)+"-01";
                                hasta = hasta+"-0"+String.valueOf(i)+"-30";
                                
                                break;
                            case 5:
                                desde = desde+"-0"+String.valueOf(i)+"-01";
                                hasta = hasta+"-0"+String.valueOf(i)+"-31";
                                
                                break;
                            case 6:
                                desde = desde+"-0"+String.valueOf(i)+"-01";
                                hasta = hasta+"-0"+String.valueOf(i)+"-30";
                                
                                break;
                            case 7:
                                desde = desde+"-0"+String.valueOf(i)+"-01";
                                hasta = hasta+"-0"+String.valueOf(i)+"-31";
                                
                                break;
                            case 8:
                                desde = desde+"-0"+String.valueOf(i)+"-01";
                                hasta = hasta+"-0"+String.valueOf(i)+"-31";
                                
                                break;
                            case 9:
                                desde = desde+"-0"+String.valueOf(i)+"-01";
                                hasta = hasta+"-0"+String.valueOf(i)+"-30";
                                
                                break;
                            case 10:
                                desde = desde+"-"+String.valueOf(i)+"-01";
                                hasta = hasta+"-"+String.valueOf(i)+"-31";
                                
                                break;
                            case 11:
                                desde = desde+"-"+String.valueOf(i)+"-01";
                                hasta = hasta+"-"+String.valueOf(i)+"-30";
                                
                                break;
                            case 12:
                                desde = desde+"-"+String.valueOf(i)+"-01";
                                hasta = hasta+"-"+String.valueOf(i)+"-31";
                                
                                break;
                        }
                        String sql_insert= "INSERT INTO cierres_contable (emp_codigo,"+
                                           "cod_user,macpc,fch_desde,fch_hasta,tipo_cierre,"+
                                           "cierre_fiscal,activo) VALUES ('"+VarGlobales.getCodEmpresa()+"',"+
                                           "'"+VarGlobales.getIdUser()+"','"+VarGlobales.getMacPc()+"',"+
                                           "'"+desde+"','"+hasta+"',"+2+","+año+",0);";
                        
                        SQLQuerys insert = new SQLQuerys();
                        insert.SqlInsert(sql_insert);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void validaHoraJornada(){
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
            apertura = validaApertura(diaSis);
            
            if(apertura == 1){
                JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgAccesoSistemaPOS"),
                    idioma.loadLangMsg().getString("MsgTituloNotif"), JOptionPane.INFORMATION_MESSAGE);
                        
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
        }else{
            confirm = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgNoHayApert"),idioma.loadLangMsg().getString("MsgTituloNotif"),JOptionPane.YES_NO_OPTION);
            if(confirm==0){
//                ConfigSuper supervisor = new ConfigSuper();
//                supervisor.Verifica("EMP_SESION", "NoAsignaApertura");
            }
        }
        
    }
    
    private int validaApertura(String diaSis){
        ResultSet rs = null;
        int hora, minutos, dia, result=0;
        String horaSis, jornada="", turno="", sql="", pcName="", caja="";
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar horario = new GregorianCalendar();
        
        hora = horario.get(Calendar.HOUR_OF_DAY);
        minutos = horario.get(Calendar.MINUTE);
        dia = horario.get(Calendar.DAY_OF_WEEK);
        horaSis = String.valueOf(hora)+":"+String.valueOf(minutos);
        
        Date h = null;
        Date fch = new Date();
        String fecha = sdf.format(fch);
        
        InetAddress direccion = null;
        try {
            direccion = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        pcName = direccion.getHostName();
        
        try {
            h = formato.parse(horaSis);
        } catch (ParseException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        sql = "SELECT CAJ_NUMCAJ FROM dncaja WHERE CAJ_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
              "CAJ_PCNAME='"+pcName+"' AND CAJ_MACPC='"+VarGlobales.getMacPc()+"'";
        System.out.println(sql);
        try {
            rs = conet.consultar(sql);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if(rs.getRow()>0){
                caja = rs.getString("CAJ_NUMCAJ");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        sql = "SELECT JOR_CODIGO,JOR_TURDESCRI,JOR_TURINI,JOR_TURFIN FROM dnjornada WHERE "+
              "JOR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND JOR_"+diaSis.toUpperCase()+"=1 ORDER BY JOR_TURDESCRI";
        System.out.println(sql);
        try {
            rs = conet.consultar(sql);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if(rs.getRow()>0){
                rs.beforeFirst();
                while(rs.next()){
                    if(!rs.getString("JOR_TURINI").equals("") && !rs.getString("JOR_TURFIN").equals("")){
                        try {
                            if(h.after(formato.parse(rs.getString("JOR_TURINI"))) && h.before(formato.parse(rs.getString("JOR_TURFIN")))){
                                jornada = rs.getString("JOR_CODIGO");
                                turno = rs.getString("JOR_TURDESCRI");
                                
                                break;
                            }
                        } catch (ParseException ex) {
                            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!jornada.equals("") && !turno.equals("")){
            sql = "SELECT * FROM dnaperturacaja WHERE APE_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                  "APE_CAJA='"+caja+"' AND APE_JORNADA='"+jornada+"' AND APE_TURNO='"+turno+"' AND "+
                  "APE_FECHA='"+fecha+"'";
            System.out.println(sql);
            try {
                rs = conet.consultar(sql);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if(rs.getRow()>0){
                    result = 1;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    private void actualizaCaja(){
        ResultSet rs = null; 
        String caja = "", pcName = "";
        String sql = "SELECT * FROM DNCAJA WHERE CAJ_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                     "CAJ_MACPC='"+VarGlobales.getMacPc()+"'";
        
        try {
            rs = conet.consultar(sql);
            try {
                if(rs.getRow()>0){
                    sql = "UPDATE dncaja SET CAJ_IP='"+VarGlobales.getIpPc()+"' WHERE "+
                          "CAJ_MACPC='"+VarGlobales.getMacPc()+"'";
                    
                    SQLQuerys update = new SQLQuerys();
                    update.SqlUpdate(sql);
                    VarGlobales.setCaja(rs.getString("CAJ_NUMCAJ"));
                }else{
                    try {
                        CodigoConsecutivo codigo = new CodigoConsecutivo();
                        caja = codigo.CodigoConsecutivo("CAJ_NUMCAJ", "DNCAJA", 2, "WHERE CAJ_EMPRESA='"+VarGlobales.getCodEmpresa()+"'");
                        
                        InetAddress direccion = InetAddress.getLocalHost();
                        pcName = direccion.getHostName();
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    sql = "INSERT INTO dncaja (CAJ_EMPRESA,CAJ_PCNAME,CAJ_IP,CAJ_MACPC,CAJ_NUMCAJ,CAJ_ACTIVO)"+
                          "VALUES('"+VarGlobales.getCodEmpresa()+"','"+pcName+"','"+VarGlobales.getIpPc()+"',"+
                          "'"+VarGlobales.getMacPc()+"','"+caja+"',1)";
                    
                    SQLQuerys insert = new SQLQuerys();
                    insert.SqlInsert(sql);
                    VarGlobales.setCaja(caja);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Login().setVisible(true);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void CargarIdioma(){
        switch ((int) jComboIdioma.getSelectedItem()) {
            case 0:
                idioma.setLocale("CR");
                VarGlobales.setIdioma("CR");
			
		break;
            case 1:
                idioma.setLocale("EN");
                VarGlobales.setIdioma("EN");
			
		break;
            case 2:
                idioma.setLocale("ES");
                VarGlobales.setIdioma("ES");
			
		break;
            default:
                break;
	}

//        jLabel1.setText(idioma.loadLangComponent().getString("lbUsuario"));
//        jLabel2.setText(idioma.loadLangComponent().getString("lbClave"));
//        jLabel4.setText(idioma.loadLangComponent().getString("lbLenguaje"));
//        jLabel5.setText(idioma.loadLangComponent().getString("lbEmpresa"));
//        jButton1.setText(idioma.loadLangComponent().getString("btnEntrar"));
//        jButton2.setText(idioma.loadLangComponent().getString("btnSalir"));
    }
    
    private void insertPermis(String empresa, String permiso){
        SQLQuerys insertar = new SQLQuerys();
        ResultSet rs_menu, rs_permisologias;
        String Permis = "SELECT * FROM dnpermisologia WHERE mis_empresa='"+empresa+"' and mis_permiso="+permiso;

        try {
            rs_permisologias = conet.consultar(Permis);
            int nunRegPermisos = conet.Count_Reg("SELECT COUNT(*) AS REGISTROS FROM dnpermisologia WHERE mis_empresa='"+empresa+"' and mis_permiso="+permiso);
            int nunRegMenu = conet.Count_Reg("SELECT COUNT(*) AS REGISTROS FROM DNMENU WHERE MEN_TIPO=2");

            if (rs_permisologias.getRow()>0 && nunRegPermisos==nunRegMenu){
                rs_permisologias.first();

                for (int i=0; i<rs_permisologias.getRow(); i++){
//                    SQLQuerys modificar = new SQLQuerys();
//                    modificar.SqlUpdate("UPDATE dnpermisologia SET MIS_ACTIVO=1 "+
//                                        "WHERE MIS_EMPRESA='"+empresa+"' AND "+
//                                        "MIS_MENU="+rs_menu.getInt("MEN_ID")+" AND "+
//                                        "MIS_TIPOMENU=2");
//
//                    rs_permisologias.next();
                }
            }else{
                String Id_Menu2 = "SELECT men_id, sub_men_id, men_nombre, men_descripcion, men_url, men_orden, men_orden_pos, "
                                + "       men_tipo, men_img, men_imgmenu,men_externo, men_urlamigable, men_icon, men_estmenu "
                                + "FROM dnmenu WHERE men_tipo=2";
                rs_menu = conet.consultar(Id_Menu2);
                int prueba = rs_menu.getRow();

                //String idMenu = String.format("%06d", rs_menu.getInt("MEN_ID"));
                Permis = "SELECT * FROM relac_usuario_grupo_permisos WHERE emp_codigo='"+empresa+"' AND per_id="+permiso;
                rs_permisologias = conet.consultar(Permis);

                //if (rs_permisologias.getRow()>0){
                if (rs_permisologias.getRow()>0){
                    rs_permisologias.first();
                    rs_menu.first();

                    String registros = "SELECT COUNT(*) AS REGISTROS FROM dnpermisologia WHERE mis_empresa='"+empresa+"' AND mis_permiso="+permiso;
                    int numReg = conet.Count_Reg(registros);
                    
                    if (numReg==0){
                        for (int i=0; i<rs_menu.getRow(); i++){
                            registros = "SELECT COUNT(*) AS REGISTROS FROM dnpermisologia";
                            numReg = conet.Count_Reg(registros);
                            String idMenu = String.format("%06d", numReg+1);
                        
                            String sql = "INSERT INTO DNPERMISOLOGIA (MIS_EMPRESA,MIS_ID,MIS_PERMISO,MIS_MENU,MIS_TIPOMENU,MIS_TIPOPER,"+
                                                              "MIS_POS,MIS_ERP,MIS_ACTIVO) "+
                                                      "VALUES ('"+empresa+"','"+idMenu+"_"+permiso+"_2_0_"+empresa+"',"+
                                                               permiso+","+rs_menu.getInt("MEN_ID")+","+
                                                               "2,0,1,1,1);";
                            
                            insertar.SqlInsert(sql);
                            rs_permisologias.next();
                        }
                    }else{
                        for (int i=0; i<rs_menu.getRow(); i++){
                            //registros = "SELECT COUNT(*) AS REGISTROS FROM dnpermisologia";
                            registros = "SELECT mis_id FROM dnpermisologia ORDER BY mis_id DESC LIMIT 1;";
                            ResultSet rsUltimoPermiso = conet.consultar(registros);
                            
                            if(rsUltimoPermiso.getRow()>0){
                                String registro = rsUltimoPermiso.getString("mis_id");
                                registro = registro.substring(0, 6);
                                numReg = Integer.valueOf(registro);
                            }
                            
                            //numReg = conet.Count_Reg(registros);
                            
                            registros = "SELECT COUNT(*) AS REGISTROS FROM dnpermisologia WHERE mis_empresa='"+empresa+"' AND "+
                                        "mis_permiso="+permiso+" AND mis_menu="+rs_menu.getInt("MEN_ID");
                            int numRegExist = conet.Count_Reg(registros);
                            
                            if(numRegExist==0){
                                String idMenu = String.format("%06d", numReg+1);
                        
                                String sql = "INSERT INTO DNPERMISOLOGIA (MIS_EMPRESA,MIS_ID,MIS_PERMISO,MIS_MENU,MIS_TIPOMENU,MIS_TIPOPER,"+
                                                                  "MIS_POS,MIS_ERP,MIS_ACTIVO) "+
                                                          "VALUES ('"+empresa+"','"+idMenu+"_"+permiso+"_2_0_"+empresa+"',"+
                                                                   permiso+","+rs_menu.getInt("MEN_ID")+","+
                                                                   "2,0,1,1,1);";

                                int valor = insertar.SqlInsert(sql);
                                //rs_permisologias.next();
                            }
                            
                            rs_menu.next();
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public class CargaRegVista extends Thread{
        @Override
        public void run(){ 
            try{
                switch (VarGlobales.getManejador()) {
                    case "PostGreSQL":
                        Class.forName("org.postgresql.Driver");
                        conet.conn = DriverManager.getConnection("jdbc:postgresql://"+VarGlobales.getIpServer()+"/"+VarGlobales.getBaseDatos(), VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                        break;
                    case "MySQL":
                        Class.forName("com.mysql.jdbc.Driver");
                        conet.conn = DriverManager.getConnection("jdbc:mysql://"+VarGlobales.getIpServer()+"/"+VarGlobales.getBaseDatos()+"?allowMultiQueries=true", VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                        break;
                }
            
                String loadRegView = "SELECT * FROM asientos_contables WHERE comp_empresa='"+VarGlobales.getCodEmpresa()+"'";
                
                conet.consultar(loadRegView);
            }catch(ClassNotFoundException | SQLException e){
                javax.swing.JOptionPane.showMessageDialog(null, e);
            }finally{
                try {
                    conet.conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.l2fprod.common.swing.JLinkButton btnLogin2;
    private com.l2fprod.common.swing.JLinkButton btnSalir;
    private javax.swing.Box.Filler filler3;
    public static javax.swing.JComboBox jCmbEmpresa1;
    private javax.swing.JComboBox jCmbUser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lbTiempoDemo;
    private javax.swing.JPasswordField passFUser2;
    // End of variables declaration//GEN-END:variables
}