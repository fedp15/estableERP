package Vista;

import Controlador.Controlador_Conf_Conexion;
import Modelos.VariablesGlobales;
import util.Encrip_Desencrip;
import util.GetMacIp;
import Modelos.conexion;
import static Modelos.conexion.lBdExist;
import static Modelos.conexion.lCreaBd;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXComboBox;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import util.AutoComplete;
import util.CargaComboBox;
import static util.ColorApp.colorForm;
import util.Internacionalizacion;

public class ConfigurarConexion extends javax.swing.JFrame {
    public ResultSet rs;
    public static int Tab=0;
    public static String Clave="", clave="", bdNueva="";
    public static boolean lPassPostGre;
    private final conexion conet = new conexion();
    private File carpeta, archivo, archivo2, carpetaLicencia, archivoLicencia, archivoLicencia2, archivo_temp, archivoTempLicen;
    private static boolean lConex=false, lConexSuss=false, lFileLicen = false;
    
    private JXComboBox cmbListBd = new JXComboBox();
    private JTextField txtBaseDatos;
    private DefaultComboBoxModel mdl;    
    private String[] comboBD;
    private static String baseDatos = "";
    private boolean lBdConfigExit = false;

    Controlador_Conf_Conexion ControlConex = new Controlador_Conf_Conexion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();

    public ConfigurarConexion() {
        initComponents();
        idioma.setLocale(VarGlobales.getIdioma());
        
        estiloform();
        cargaidioma();
        loadPosicionComponentes();
        
        if (Tab==0){
            VarGlobales.setManejador("MySQL");
        }
        
        TabConexBd.requestFocus();
        txt_ipconfig.requestFocus();
        txt_confbd.setVisible(false);
        
        String ip = "";
        ip = VarGlobales.getIpServer();
        if(ip==null){
            ip="127.0.0.1:3306";
        }
        txt_ipconfig.setText(ip);

        TabConexBd.setIconAt(0, new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/ic_mysql.png"));
        //TabConexBd.setIconAt(1, new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/ic_postgresql.png"));
        
        setLocationRelativeTo(null);
        
        this.setIconImage(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/icono_app.png").getImage());
        
        txt_confpass.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //txt_confpass.setBackground(Color.decode(VarGlobales.getColorFocusGained()));
            }

            @Override
            public void focusLost(FocusEvent e) {
                //txt_confpass.setBackground(Color.decode(VarGlobales.getColorFocusLost()));
                //ControlConex.listaDataBases(cmbListDb, txt_ipconfig.getText(), txt_confuser.getText(), txt_confpass.getText());

//                comboBaseDatos();
                cmbListBd = new AutoComplete(comboBD);
                
                if(lConexSuss){
                    cmbListBd.requestFocus();
                }
            }
        });
        
        cmbListBd.setEditable(true);
        cmbListBd.setEnabled(true);
        txtBaseDatos = (JTextField) cmbListBd.getEditor().getEditorComponent();

        txtBaseDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                //String bd = (String) cmbListBd.getSelectedItem();
                String bd = txtBaseDatos.getText();
                
                if (!bd.trim().equals("")){
                    //tbDetalleFacturacion.changeSelection(tbDetalleFacturacion.getSelectedRow(), 1, false, false);
                    bt_save.requestFocus();
                }
            }
        });
        
        //cmbListDb.setModel(new javax.swing.DefaultComboBoxModel(new String[] {" "}));
        cmbListBd.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                //cmbListDb.setBackground(Color.decode(VarGlobales.getColorFocusGained()));

                comboBaseDatos();
                cmbListBd = new AutoComplete(comboBD);
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                //cmbListDb.setBackground(Color.decode(VarGlobales.getColorFocusLost()));
            }
        });
        
        AutoCompleteDecorator.decorate(cmbListBd);
    }
    
    public ConfigurarConexion(boolean Conexion) {
        initComponents();
        
        estiloform();
        cargaidioma();
        
        lConex=Conexion;
        TabConexBd.requestFocus();

        TabConexBd.setIconAt(0, new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/ic_mysql.png"));
        //TabConexBd.setIconAt(1, new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/ic_postgresql.png"));
        
        if (Conexion==true){
            cargartxt();
        }
        
        setLocationRelativeTo(null);
        
        this.setIconImage(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/icono_app.png").getImage());
    }
    
    public void cargartxt() {
        String sql = ("SELECT * FROM DNCONEXION");
            
        try {
            rs = conet.consultar(sql);
            try {
                if (VarGlobales.getManejador().equals("mySQL")){
                    TabConexBd.setSelectedIndex(0);
                    TabConexBd.setEnabledAt(1, false);
                    
                    VarGlobales.setIpServer(rs.getString("CONF_IP").trim()); txt_ipconfig.setText(VarGlobales.getIpServer());
                    VarGlobales.setUserServer(rs.getString("CONF_USER").trim()); txt_confuser.setText(VarGlobales.getUserServer());
                    VarGlobales.setPasswServer(rs.getString("CONF_PASS").trim()); txt_confpass.setText(VarGlobales.getPasswServer());

                    //VarGlobales.setBaseDatos(rs.getString("CONF_BD").trim()); cmbListBd.setSelectedItem(VarGlobales.getBaseDatos());
                    VarGlobales.setBaseDatosConfiguracion(rs.getString("CONF_BD").trim()); cmbListBd.setSelectedItem(VarGlobales.getBaseDatos());
                }
            } catch (SQLException ex) {                    
                Logger.getLogger(ConfigurarConexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConfigurarConexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void estiloform(){
        jPanel1.setBackground(Color.decode(colorForm)); jPanel3.setBackground(Color.decode(colorForm)); 
        
//        bt_save.setBackground(Color.decode(colorForm)); bt_save1.setBackground(Color.decode(colorForm));
//        bt_save.setForeground(Color.decode(colorText)); bt_save1.setForeground(Color.decode(colorText));
//        
//        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
//        jLabel3.setForeground(Color.decode(colorText)); jLabel4.setForeground(Color.decode(colorText)); 
        
//        txt_confbd.setForeground(Color.decode(colorText)); 
//        txt_confpass.setForeground(Color.decode(colorText));
//        txt_confuser.setForeground(Color.decode(colorText));
//        txt_ipconfig.setForeground(Color.decode(colorText));
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
        TabConexBd = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_confpass = new javax.swing.JPasswordField();
        jPanel5 = new javax.swing.JPanel();
        bt_save1 = new com.l2fprod.common.swing.JLinkButton();
        bt_save = new com.l2fprod.common.swing.JLinkButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        TabConexBd.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        TabConexBd.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                TabConexBdStateChanged(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Localhost/IP-SERVIDOR+Puerto");

        txt_ipconfig.setText("127.0.0.1:3306");
        txt_ipconfig.setToolTipText("127.0.0.1:3306 o el puerto que usted selecciono");
        txt_ipconfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ipconfigKeyPressed(evt);
            }
        });

        jLabel2.setText("Usuario");

        txt_confuser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_confuserKeyPressed(evt);
            }
        });

        jLabel3.setText("Contraseña");

        jLabel4.setText("Base de Datos");

        txt_confbd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_confbdKeyPressed(evt);
            }
        });

        txt_confpass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_confpassKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(txt_ipconfig, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(txt_confuser, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(txt_confbd, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(txt_confpass))
                .addGap(45, 45, 45))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addComponent(txt_ipconfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel2)
                .addGap(6, 6, 6)
                .addComponent(txt_confuser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_confpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel4)
                .addGap(32, 32, 32)
                .addComponent(txt_confbd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        TabConexBd.addTab("Conexión MySQL ", jPanel3);

        jPanel5.setBackground(new java.awt.Color(105, 105, 105));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));

        bt_save1.setBackground(new java.awt.Color(105, 105, 105));
        bt_save1.setForeground(new java.awt.Color(255, 255, 255));
        bt_save1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete_bar_butto_1.png"))); // NOI18N
        bt_save1.setText("Cancelar");
        bt_save1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_save1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bt_save1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_save1ActionPerformed(evt);
            }
        });

        bt_save.setBackground(new java.awt.Color(105, 105, 105));
        bt_save.setForeground(new java.awt.Color(255, 255, 255));
        bt_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        bt_save.setText("Seleccionar");
        bt_save.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bt_save.setPreferredSize(new java.awt.Dimension(86, 74));
        bt_save.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bt_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_saveActionPerformed(evt);
            }
        });
        bt_save.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bt_saveKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(bt_save, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(bt_save1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bt_save, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(bt_save1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TabConexBd, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(73, 73, 73)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(257, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(TabConexBd, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(85, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TabConexBdStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_TabConexBdStateChanged
        Tab = TabConexBd.getSelectedIndex();
        if (Tab==0){
            VarGlobales.setManejador("MySQL");
        }
    }//GEN-LAST:event_TabConexBdStateChanged

    private void txt_ipconfigKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ipconfigKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txt_confuser.requestFocus();
        }
    }//GEN-LAST:event_txt_ipconfigKeyPressed

    private void txt_confuserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_confuserKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txt_confpass.requestFocus();
        }
    }//GEN-LAST:event_txt_confuserKeyPressed

    private void txt_confbdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_confbdKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            bt_save.requestFocus();
        }
    }//GEN-LAST:event_txt_confbdKeyPressed

    private void txt_confpassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_confpassKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            //txt_confbd.requestFocus();
            cmbListBd.requestFocus();
        }
    }//GEN-LAST:event_txt_confpassKeyPressed

    private void bt_save1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_save1ActionPerformed
        dispose();
    }//GEN-LAST:event_bt_save1ActionPerformed

    private void bt_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_saveActionPerformed
        try {
            action_bt_save(false);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ConfigurarConexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_saveActionPerformed

    private void bt_saveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bt_saveKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            try {
                action_bt_save(false);
            } catch (MalformedURLException ex) {
                Logger.getLogger(ConfigurarConexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_bt_saveKeyPressed

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
            java.util.logging.Logger.getLogger(ConfigurarConexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfigurarConexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfigurarConexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfigurarConexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConfigurarConexion().setVisible(true);
            }
        });
    }
    
    private final void comboBaseDatos(){
        String sql = "SHOW DATABASES;";
        
        cmbListBd.removeAllItems();
        for (int i=0; i<cmbListBd.getItemCount(); i++){
            cmbListBd.remove(i);
            cmbListBd.removeItemAt(i);
        }
        comboBD = null;
        
        mdl = new DefaultComboBoxModel(CargaComboBox.ListaBaseDatos(sql, txt_ipconfig.getText(), txt_confuser.getText(), txt_confpass.getText()));
        cmbListBd.setModel(mdl);
        cmbListBd.setSelectedIndex(0);
            
        if (mdl.getSize()>1){
            lConexSuss=true;
        }else{
            lConexSuss=false;
        }

        comboBD = new String[mdl.getSize()];
        
        for (int i=0; i<mdl.getSize(); i++){
            System.err.println((String) mdl.getElementAt(i));
            if (mdl.getElementAt(i).toString().equals("adminconfigestableerp")){
                lBdConfigExit = true;
            }
            
            comboBD[i] = (String) mdl.getElementAt(i);
        }
        
        if (!lBdConfigExit){
            JOptionPane.showMessageDialog(null, "La base de datos de configuracion del sistema no esta creada, se procedera\n"+
                                                "a crear ahora para poder continuar con el uso del sistema", 
                                         idioma.loadLangMsg().getString("MsgErrorReader"), 
                                         JOptionPane.ERROR_MESSAGE);
            
            txtBaseDatos.setText("adminconfigestableerp");
            cmbListBd.setEnabled(false);
            
            try {
                action_bt_save(false);
            } catch (MalformedURLException ex) {
                Logger.getLogger(ConfigurarConexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            cmbListBd.setSelectedItem("adminconfigestableerp");
            cmbListBd.setEnabled(false);
            
            int op = JOptionPane.showConfirmDialog(null,"Se encontro la base de datos de configuracion del sistema, ¿Desea continuar?", 
                                                  idioma.loadLangMsg().getString("MsgTituloNotif"),
                                                  JOptionPane.YES_NO_OPTION);
        
            if(op == 0) {
                bt_save.requestFocus();
                
                try {
                    action_bt_save(false);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ConfigurarConexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void action_bt_save(boolean lPass) throws MalformedURLException{
        //Se le asignan a los text una variable publica
        if (Tab==0){
            if (txt_ipconfig.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgAddIp"));
                txt_ipconfig.requestFocus();
                return;
            }

            if (txt_confuser.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgAddUserMySQL"));
                txt_confuser.requestFocus();
                return;
            }
            
            //String baseDatos = (String) cmbListBd.getSelectedItem();
            String bd = (String) cmbListBd.getSelectedItem();
            if(!baseDatos.trim().isEmpty()){
               bdNueva = baseDatos;
            }else{
                baseDatos = txtBaseDatos.getText();
                if(!baseDatos.equals(" ")){
                    bdNueva = baseDatos;
                }
            }
            
            if (bdNueva.equals("")){
                JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgAddBaseDatos"));
                cmbListBd.requestFocus();
                return;
            }
            if (!lConexSuss){
                JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgAddBaseDatos"));
                cmbListBd.requestFocus();
                return;
            }
        }

        GetMacIp macip = new GetMacIp();
        Object MacIp[][] = macip.GetMacIp();

        VarGlobales.setMacPc(MacIp[0][0].toString());
        VarGlobales.setIpPc(MacIp[0][1].toString());

        if (Tab==0){
            VarGlobales.setIpServer(txt_ipconfig.getText());
            VarGlobales.setUserServer(txt_confuser.getText());
            VarGlobales.setPasswServer(txt_confpass.getText());
            //VarGlobales.setBaseDatos(txt_confbd.getText());
            //VarGlobales.setBaseDatos(bdNueva);
            VarGlobales.setlBaseDatosConfiguracion(true);
            VarGlobales.setBaseDatosConfiguracion(bdNueva);
            VarGlobales.setManejador("MySQL");

            //ControlConex.setDatosConex(VarGlobales.getIpServer(), VarGlobales.getUserServer(), VarGlobales.getPasswServer(), VarGlobales.getBaseDatos(), "MySQL", VarGlobales.getMacPc(), VarGlobales.getIpPc());
            ControlConex.setDatosConex(VarGlobales.getIpServer(), VarGlobales.getUserServer(), VarGlobales.getPasswServer(), VarGlobales.getBaseDatosConfiguracion(), "MySQL", VarGlobales.getMacPc(), VarGlobales.getIpPc());
        }

        if (lConex==true){
            int Reg_count = ControlConex.ExisteConex();

            if (Reg_count>0){
                JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgConfigServerExist"));
                return;
            }
        }

        if (lPass==true){
            VarGlobales.setPasswServer(clave);
        }

        lPassPostGre=true;
        int Reg_count = ControlConex.ExisteConexLocal(VarGlobales.getMacPc());

        if (Reg_count==0){
            lPassPostGre=false;
//        }else{
            ControlConex.GuardaConexion();
        }
        if (lBdExist==false){
            if (lCreaBd==true){
                dispose();
            }
            return;
        }
        carpeta = new File(System.getProperty("user.dir")+"\\"+"Configuracion");
        carpeta.mkdirs();
        archivo = new File(carpeta.getAbsolutePath()+"\\"+"conf.txt");
        archivo2 = new File(carpeta.getAbsolutePath()+"\\"+"conf_conexion.txt");

        try {
            if (archivo.createNewFile()){
                System.out.println("Archivo creado");

                //Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
                FileWriter Arc = new FileWriter(archivo,true);
                BufferedWriter escribir = new BufferedWriter(Arc);

                //Escribimos en el archivo con el metodo write
                escribir.write(VarGlobales.getIpServer()); escribir.newLine();
                escribir.write(VarGlobales.getUserServer()); escribir.newLine();
                escribir.write(VarGlobales.getPasswServer()); escribir.newLine();
                //escribir.write(VarGlobales.getBaseDatos()); escribir.newLine();
                escribir.write(VarGlobales.getBaseDatosConfiguracion()); escribir.newLine();
                escribir.write(VarGlobales.getManejador());

                //Cerramos la conexion
                escribir.close();
            }

            try {
                Encrip_Desencrip.encrypt(archivo, archivo2, 12345);
            } catch (IOException ioe) {
                String err = "Error De Lectura\n" +
                             "Probablemente el disco esta lleno o protegido contra escritura";
                JOptionPane.showMessageDialog(null, err, idioma.loadLangMsg().getString("MsgErrorReader"), JOptionPane.ERROR_MESSAGE);
            }

            archivo.delete();
        } catch (IOException ex) {
            Logger.getLogger(ConfigurarConexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        

            BufferedReader bf;
            int linea=0;
            String Read, numLicencia = "", nombreCliente = "", multiempreesa = "", numEmpresas = "", diasLicencia = "", actualiza = "",
                   fechaContrato = "", fechaDesdeContrato = "", fechaHastaContrato = "", urlLogoEmpresa = "";
            

        dispose();
        if (lConex==false){
//            ResultSet rs = ControlConex.Arranque(false);
//            try {
//                if(rs.getInt("CUANTOS")>1){
//                    EmpresasNuevas emp = new EmpresasNuevas();
//                    emp.setLocationRelativeTo(null);
//                    emp.show();
//                    emp.setVisible(true);
//                }else if(rs.getInt("CUANTOS")==1){
//                    rs = ControlConex.Arranque(true);
//                    
//                    VarGlobales.setNuevaEmpresa(rs.getString("EMP_CODIGO"));
//                    VarGlobales.setBaseDatos(rs.getString("base_datos_empresa"));
//                    
//                    Welcome form = new Welcome();
//                    form.setLocationRelativeTo(null);
//                    form.show();
//                    form.setVisible(true);
//                }else{
                    new Vista.Login().setVisible(true);
//                }
//            } catch (SQLException | IOException ex) {
//                Logger.getLogger(ConfigurarConexion.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }        
    }
    
    private void cargaidioma(){
//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        TabConexBd.setTitleAt(0, idioma.loadLangComponent().getString("lbConexionBD")+"MySQL");
        //TabConexBd.setTitleAt(1, idioma.loadLangComponent().getString("lbConexionBD")+"PostGreSQL");
        
        jLabel1.setText(idioma.loadLangComponent().getString("lbIp"));
        jLabel2.setText(idioma.loadLangComponent().getString("lbUsuario"));
        jLabel3.setText(idioma.loadLangComponent().getString("lbClave"));
        jLabel4.setText(idioma.loadLangComponent().getString("lbBaseDatos"));

        bt_save.setText(idioma.loadLangComponent().getString("btnGrabar"));
        bt_save1.setText(idioma.loadLangComponent().getString("btnCancelar"));
//-----------------------------------------------------------------------------------------------------------------------
    }
    
    private void loadPosicionComponentes(){
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(txt_ipconfig, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(txt_confuser, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(txt_confbd, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addComponent(txt_confpass)
                    .addComponent(cmbListBd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(45, 45, 45))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addComponent(txt_ipconfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel2)
                .addGap(6, 6, 6)
                .addComponent(txt_confuser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_confpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbListBd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_confbd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabConexBd;
    private com.l2fprod.common.swing.JLinkButton bt_save;
    private com.l2fprod.common.swing.JLinkButton bt_save1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    public static final javax.swing.JTextField txt_confbd = new javax.swing.JTextField();
    private javax.swing.JPasswordField txt_confpass;
    public static final javax.swing.JTextField txt_confuser = new javax.swing.JTextField();
    public static final javax.swing.JTextField txt_ipconfig = new javax.swing.JTextField();
    // End of variables declaration//GEN-END:variables
}