package Vista;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import static Vista.MenuPrincipal.escritorioERP;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDesktopPane;
import javax.swing.table.DefaultTableModel;
import util.CargaComboBox;
import util.CargaTablas;
import static util.ColorApp.colorForm;
import static util.ColorApp.colorText;
import util.Internacionalizacion;
import util.SQLQuerys;
import util.SQLSelect;

public class Permisologias extends javax.swing.JInternalFrame {
    //public int fila, atras=-2, adelante=0;
    private SQLSelect Registros;
    private boolean Bandera = false, lAgregar, lModificar;
    private ResultSet rs, rs_count, rs_codigo, rs_opc_menus, rs_hijos;
    private int Reg_count, SelectCombo=0;
    private String codigo="", tipomenu="",sql_tabla="";
    private DefaultTableModel modelo;
    private JDesktopPane desktop;
    
    CargaTablas cargatabla = null;
    conexion conet = new conexion();
    
    Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();

    public Permisologias(String origen) {
        initComponents();
        combotipusuario();

        if(origen.equals("ERP")){
            desktop = escritorioERP;
        }else{
//            desktop = escritorio;
        }
        
        jPanel1.setBackground(Color.decode(colorForm));
        
//        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
//        jLabel3.setForeground(Color.decode(colorText)); jLabel4.setForeground(Color.decode(colorText));
//        jLabel5.setForeground(Color.decode(colorText));
        
//        bt_salir.setBackground(Color.decode(colorForm)); bt_salir.setForeground(Color.decode(colorText));
        //bt_salir.setHorizontalAlignment(2);
        
//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        this.setTitle(idioma.loadLangComponent().getString("lbTituloFormPermisologia"));
        jLabel1.setText(idioma.loadLangComponent().getString("lbTituloFormGrupoPermiso"));
        jLabel2.setText(idioma.loadLangComponent().getString("lbMenus")+" "+idioma.loadLangComponent().getString("lbTituloFormGrupoPermiso"));
//        jLabel3.setText((String) elementos.get(2));
//        jLabel4.setText((String) elementos.get(3));
//        jLabel5.setText((String) elementos.get(4));
        
        bt_salir.setText(idioma.loadLangComponent().getString("btnSalir"));
//-----------------------------------------------------------------------------------------------------------------------
        
        Tabla.getColumn("ID Menu").setHeaderValue(idioma.loadLangHeaderTable().getString("id")+" "+idioma.loadLangHeaderTable().getString("menu"));
        Tabla.getColumn("Nombre").setHeaderValue(idioma.loadLangHeaderTable().getString("nombre")+" "+idioma.loadLangHeaderTable().getString("menu"));
        Tabla.getColumn("Selección").setHeaderValue(idioma.loadLangHeaderTable().getString("activo"));

        this.setTitle("Permisologias");
        OcultaCombos();
    }
    
    private void OcultaCombos(){
        jLabel2.setVisible(false); jCboMen_Raiz.setVisible(false);
        jLabel3.setVisible(false); jCboSub_Men_Raiz.setVisible(false);
        jLabel4.setVisible(false); jCboSub_Men_Hijos.setVisible(false);
        jLabel5.setVisible(false); jCboSub_Men_Nietos.setVisible(false);
    }
    
    public void combotipusuario(){
        String sql = "SELECT CONCAT(PER_ID,' - ', PER_NOMBRE) AS DATO1 FROM DNPERMISO_GRUPAL WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PER_ACTIVO=1 AND PER_TIPOMENU='2'";
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
        this.jCboMis_Permisos.setModel(mdl); 
    }

   private void combomenuraiz(){  
       jLabel2.setVisible(true); jCboMen_Raiz.setVisible(true);
       
       String sql = "SELECT CONCAT(MEN_ID,' - ', MEN_NOMBRE) AS DATO1 FROM DNPERMISOLOGIA " +
                    "INNER JOIN DNMENU ON MIS_MENU=MEN_ID " +
                    "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND " +
                    "MIS_TIPOMENU=2 AND SUB_MEN_ID=0 " +
                    "ORDER BY MIS_PERMISO,MIS_MENU";
       // AND MIS_POS=1
       DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
       this.jCboMen_Raiz.setModel(mdl); 
   }

   private void combosubmenuraiz(){ 
       //jLabel8.setVisible(false); jCboSub_Men_Hijos.setVisible(false);
       String ID_Sub_Menu = buscasubmenus(jCboMis_Permisos.getSelectedItem().toString().substring(0, 4), jCboMen_Raiz.getSelectedItem().toString().substring(0, jCboMen_Raiz.getSelectedItem().toString().indexOf(" ")).trim());

       if (ID_Sub_Menu.length()>0){
           jLabel3.setVisible(true); jCboSub_Men_Raiz.setVisible(true);
           jLabel3.setText(idioma.loadLangComponent().getString("lbMenus")+" "+
                           jCboMen_Raiz.getSelectedItem().toString().substring(jCboMen_Raiz.getSelectedItem().toString().indexOf("-"), jCboMen_Raiz.getSelectedItem().toString().length()));
           
           String sql = "SELECT CONCAT(MEN_ID,' - ', MEN_NOMBRE) AS DATO1 FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID \n"+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND " +
                        "MIS_TIPOMENU=2 AND MEN_ID IN("+ID_Sub_Menu.substring(0, ID_Sub_Menu.length()-1)+") \n"+
                        "ORDER BY MIS_PERMISO,MIS_MENU";
           DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
           this.jCboSub_Men_Raiz.setModel(mdl); 
       }else{
           //jCboSub_Men_Raiz.removeAllItems();
           jLabel3.setVisible(false); jCboSub_Men_Raiz.setVisible(false);
           jLabel4.setVisible(false); jCboSub_Men_Hijos.setVisible(false);
           jLabel5.setVisible(false); jCboSub_Men_Nietos.setVisible(false);
       }
   }
   
   private void combosubmenuhijos(){      
       String ID_Sub_Menu = buscasubmenus(jCboMis_Permisos.getSelectedItem().toString().substring(0, 4), jCboSub_Men_Raiz.getSelectedItem().toString().substring(0, jCboSub_Men_Raiz.getSelectedItem().toString().indexOf(" ")).trim());

       if (ID_Sub_Menu.length()>0){
           jLabel4.setVisible(true); jCboSub_Men_Hijos.setVisible(true);
           jLabel4.setText(idioma.loadLangComponent().getString("lbMenus")+" "+
                           jCboSub_Men_Raiz.getSelectedItem().toString().substring(jCboSub_Men_Raiz.getSelectedItem().toString().indexOf("-"), jCboSub_Men_Raiz.getSelectedItem().toString().length()));
           
           String sql = "SELECT CONCAT(MEN_ID,' - ', MEN_NOMBRE) AS DATO1 FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID \n"+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND " +
                        "MIS_TIPOMENU=2 AND MEN_ID IN("+ID_Sub_Menu.substring(0, ID_Sub_Menu.length()-1)+") \n"+
                        "ORDER BY MIS_PERMISO,MIS_MENU";
           DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
           this.jCboSub_Men_Hijos.setModel(mdl);
       }else{
           if (jCboSub_Men_Hijos.getItemCount()>0){
               //jCboSub_Men_Hijos.removeAllItems();
               jLabel4.setVisible(false); jCboSub_Men_Hijos.setVisible(false);
               jLabel5.setVisible(false); jCboSub_Men_Nietos.setVisible(false);
           }
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
        jLabel1 = new javax.swing.JLabel();
        jCboMis_Permisos = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jCboMen_Raiz = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jCboSub_Men_Raiz = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jCboSub_Men_Hijos = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jCboSub_Men_Nietos = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        bt_salir = new com.l2fprod.common.swing.JLinkButton();

        jLabel1.setText("Grupo de Permisologias de Usuario");

        jCboMis_Permisos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCboMis_PermisosItemStateChanged(evt);
            }
        });
        jCboMis_Permisos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCboMis_PermisosActionPerformed(evt);
            }
        });
        jCboMis_Permisos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCboMis_PermisosKeyPressed(evt);
            }
        });

        jLabel2.setText("Menus Raiz Padre");

        jCboMen_Raiz.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCboMen_RaizItemStateChanged(evt);
            }
        });
        jCboMen_Raiz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCboMen_RaizActionPerformed(evt);
            }
        });
        jCboMen_Raiz.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCboMen_RaizKeyPressed(evt);
            }
        });

        jLabel3.setText("Sub-Menus Padre");

        jCboSub_Men_Raiz.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCboSub_Men_RaizItemStateChanged(evt);
            }
        });
        jCboSub_Men_Raiz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCboSub_Men_RaizActionPerformed(evt);
            }
        });
        jCboSub_Men_Raiz.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCboSub_Men_RaizKeyPressed(evt);
            }
        });

        jLabel4.setText("Sub-Menus Hijos");

        jCboSub_Men_Hijos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCboSub_Men_HijosItemStateChanged(evt);
            }
        });
        jCboSub_Men_Hijos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCboSub_Men_HijosActionPerformed(evt);
            }
        });
        jCboSub_Men_Hijos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCboSub_Men_HijosKeyPressed(evt);
            }
        });

        jLabel5.setText("Sub-Menus Nietos");

        jCboSub_Men_Nietos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCboSub_Men_NietosActionPerformed(evt);
            }
        });
        jCboSub_Men_Nietos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCboSub_Men_NietosKeyPressed(evt);
            }
        });

        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Menu", "Nombre", "Selección"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
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
        jScrollPane1.setViewportView(Tabla);

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bt_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(bt_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jCboMen_Raiz, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jCboSub_Men_Raiz, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jCboSub_Men_Hijos, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jCboSub_Men_Nietos, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jCboMis_Permisos, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(6, 6, 6)
                        .addComponent(jCboMis_Permisos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(6, 6, 6)
                        .addComponent(jCboMen_Raiz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(6, 6, 6)
                        .addComponent(jCboSub_Men_Raiz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(6, 6, 6)
                        .addComponent(jCboSub_Men_Hijos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(6, 6, 6)
                        .addComponent(jCboSub_Men_Nietos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TablaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            action_tablas(Tabla.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            action_tablas(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyReleased

    private void TablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            action_tablas(Tabla.getSelectedRow());
        }
    }//GEN-LAST:event_TablaKeyPressed

    private void TablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaMouseClicked
        if(Tabla != null){
            if(Tabla.getSelectedColumn()==2){
                action_tablas(Tabla.getSelectedRow());
            }
        }
    }//GEN-LAST:event_TablaMouseClicked

    private void jCboMis_PermisosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCboMis_PermisosKeyPressed

    }//GEN-LAST:event_jCboMis_PermisosKeyPressed

    private void jCboMis_PermisosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCboMis_PermisosActionPerformed
//        if (!jCboMis_Permisos.getSelectedItem().equals("")){
//            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
//                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
//                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
//                        "MIS_TIPOMENU=2 AND MIS_POS=1 ORDER BY MIS_PERMISO,MIS_MENU;";
//                    
//            CargaTablaPermisos(sql_tabla, 1);
//        
//            combomenuraiz();
//        }else{
//            int rows_cli = Tabla.getRowCount();
//
//            for(int i=0;i<rows_cli;i++){
//                modelo.removeRow(0);
//            }
//        
//            OcultaCombos();
//        }
    }//GEN-LAST:event_jCboMis_PermisosActionPerformed

    private void jCboMen_RaizActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCboMen_RaizActionPerformed
        if (!jCboMen_Raiz.getSelectedItem().equals("")){
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 AND SUB_MEN_ID="+jCboMen_Raiz.getSelectedItem().toString().substring(0, jCboMen_Raiz.getSelectedItem().toString().indexOf(" ")).trim()+" "+
                        "AND MIS_POS=1 ORDER BY MIS_PERMISO,MIS_MENU;";
        
            CargaTablaPermisos(sql_tabla, 2);
        
            combosubmenuraiz();
        }else{
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 AND MIS_POS=1 ORDER BY MIS_PERMISO,MIS_MENU;";
                    
            CargaTablaPermisos(sql_tabla, 1);
            
            jLabel3.setVisible(false); jCboSub_Men_Raiz.setVisible(false);
            jLabel4.setVisible(false); jCboSub_Men_Hijos.setVisible(false);
            jLabel5.setVisible(false); jCboSub_Men_Nietos.setVisible(false);
        }
    }//GEN-LAST:event_jCboMen_RaizActionPerformed

    private void jCboMen_RaizKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCboMen_RaizKeyPressed

    }//GEN-LAST:event_jCboMen_RaizKeyPressed

    private void jCboSub_Men_RaizActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCboSub_Men_RaizActionPerformed
        if (!jCboSub_Men_Raiz.getSelectedItem().equals("")){
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 AND SUB_MEN_ID="+jCboSub_Men_Raiz.getSelectedItem().toString().substring(0, jCboSub_Men_Raiz.getSelectedItem().toString().indexOf(" ")).trim()+" "+
                        "AND MIS_POS=1 ORDER BY MIS_PERMISO,MIS_MENU;";
            
            CargaTablaPermisos(sql_tabla, 3);

            combosubmenuhijos();
        }else{
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 AND SUB_MEN_ID="+jCboMen_Raiz.getSelectedItem().toString().substring(0, jCboMen_Raiz.getSelectedItem().toString().indexOf(" ")).trim()+
                        "AND MIS_POS=1 ORDER BY MIS_PERMISO,MIS_MENU;";
        
            CargaTablaPermisos(sql_tabla, 2);
            
            jLabel4.setVisible(false); jCboSub_Men_Hijos.setVisible(false);
            jLabel5.setVisible(false); jCboSub_Men_Nietos.setVisible(false);
        }
    }//GEN-LAST:event_jCboSub_Men_RaizActionPerformed

    private void jCboSub_Men_RaizKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCboSub_Men_RaizKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCboSub_Men_RaizKeyPressed

    private void jCboSub_Men_HijosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCboSub_Men_HijosActionPerformed
        if(!jCboSub_Men_Hijos.getSelectedItem().equals("")){
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 AND SUB_MEN_ID="+jCboSub_Men_Hijos.getSelectedItem().toString().substring(0, jCboSub_Men_Hijos.getSelectedItem().toString().indexOf(" ")).trim()+" "+
                        "AND MIS_POS=1 ORDER BY MIS_PERMISO,MIS_MENU;";
            
            CargaTablaPermisos(sql_tabla, 4);
        }else{
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 AND SUB_MEN_ID="+jCboSub_Men_Raiz.getSelectedItem().toString().substring(0, jCboSub_Men_Raiz.getSelectedItem().toString().indexOf(" ")).trim()+
                        "AND MIS_POS=1 ORDER BY MIS_PERMISO,MIS_MENU;";
            
            CargaTablaPermisos(sql_tabla, 3);
            
            jLabel5.setVisible(false); jCboSub_Men_Nietos.setVisible(false);
            jLabel5.setText(idioma.loadLangComponent().getString("lbMenus")+" "+
                            jCboSub_Men_Hijos.getSelectedItem().toString().substring(jCboSub_Men_Hijos.getSelectedItem().toString().indexOf("-"), jCboSub_Men_Hijos.getSelectedItem().toString().length()));
        }
    }//GEN-LAST:event_jCboSub_Men_HijosActionPerformed

    private void jCboSub_Men_HijosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCboSub_Men_HijosKeyPressed
        
    }//GEN-LAST:event_jCboSub_Men_HijosKeyPressed

    private void jCboSub_Men_NietosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCboSub_Men_NietosActionPerformed
        
    }//GEN-LAST:event_jCboSub_Men_NietosActionPerformed

    private void jCboSub_Men_NietosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCboSub_Men_NietosKeyPressed
        
    }//GEN-LAST:event_jCboSub_Men_NietosKeyPressed

    private void jCboSub_Men_RaizItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCboSub_Men_RaizItemStateChanged
        int rows_cli = Tabla.getRowCount();
        DefaultTableModel model = (DefaultTableModel) Tabla.getModel();
        for(int i=0;i<rows_cli;i++){
            model.removeRow(0);
        }
        
        if (!jCboSub_Men_Raiz.getSelectedItem().equals("")){
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID \n"+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 AND SUB_MEN_ID="+jCboSub_Men_Raiz.getSelectedItem().toString().substring(0, jCboSub_Men_Raiz.getSelectedItem().toString().indexOf(" ")).trim()+" \n"+
                        "ORDER BY MIS_PERMISO,MIS_MENU;";
            
            CargaTablaPermisos(sql_tabla, 3);

            combosubmenuhijos();
        }else{
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 AND SUB_MEN_ID="+jCboMen_Raiz.getSelectedItem().toString().substring(0, jCboMen_Raiz.getSelectedItem().toString().indexOf(" ")).trim()+
                        "ORDER BY MIS_PERMISO,MIS_MENU;";
        
            CargaTablaPermisos(sql_tabla, 2);
            
            jLabel4.setVisible(false); jCboSub_Men_Hijos.setVisible(false);
            jLabel5.setVisible(false); jCboSub_Men_Nietos.setVisible(false);
        }
    }//GEN-LAST:event_jCboSub_Men_RaizItemStateChanged

    private void jCboMis_PermisosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCboMis_PermisosItemStateChanged
        int rows_cli = Tabla.getRowCount();
        DefaultTableModel model = (DefaultTableModel) Tabla.getModel();
        for(int i=0;i<rows_cli;i++){
            model.removeRow(0);
        }
        
        if (!jCboMis_Permisos.getSelectedItem().equals("")){
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA \n" +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID \n"+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND sub_men_id=0 AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND \n"+
                        "MIS_TIPOMENU=2 ORDER BY MIS_PERMISO,MIS_MENU;";
                    
            CargaTablaPermisos(sql_tabla, 1);
        
            combomenuraiz();
        }else{
            for(int i=0;i<rows_cli;i++){
                modelo.removeRow(0);
            }
        
            OcultaCombos();
        }
/*
        int rows_cli = Tabla.getRowCount();
        DefaultTableModel model = (DefaultTableModel) Tabla.getModel();
        for(int i=0;i<rows_cli;i++){
            model.removeRow(0);
        }
        if (!jCboMis_Permisos.getSelectedItem().equals("")){
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 AND MIS_POS=1 ORDER BY MIS_PERMISO,MIS_MENU;";
                    
            CargaTablaPermisos(sql_tabla, 1);
        
            combomenuraiz();
        }else{
            OcultaCombos();
        }
*/
    }//GEN-LAST:event_jCboMis_PermisosItemStateChanged

    private void jCboMen_RaizItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCboMen_RaizItemStateChanged
        int rows_cli = Tabla.getRowCount();
        DefaultTableModel model = (DefaultTableModel) Tabla.getModel();
        for(int i=0;i<rows_cli;i++){
            model.removeRow(0);
        }
        
        if (!jCboMen_Raiz.getSelectedItem().equals("")){
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA \n" +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID \n"+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND \n"+
                        "MIS_TIPOMENU=2 AND SUB_MEN_ID="+jCboMen_Raiz.getSelectedItem().toString().substring(0, jCboMen_Raiz.getSelectedItem().toString().indexOf(" ")).trim()+" \n"+
                        "ORDER BY MIS_PERMISO,MIS_MENU;";
        
            CargaTablaPermisos(sql_tabla, 2);
        
            combosubmenuraiz();
        }else{
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 ORDER BY MIS_PERMISO,MIS_MENU;";
                    
            CargaTablaPermisos(sql_tabla, 1);
            
            jLabel3.setVisible(false); jCboSub_Men_Raiz.setVisible(false);
            jLabel4.setVisible(false); jCboSub_Men_Hijos.setVisible(false);
            jLabel5.setVisible(false); jCboSub_Men_Nietos.setVisible(false);
        }
    }//GEN-LAST:event_jCboMen_RaizItemStateChanged

    private void jCboSub_Men_HijosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCboSub_Men_HijosItemStateChanged
        int rows_cli = Tabla.getRowCount();
        DefaultTableModel model = (DefaultTableModel) Tabla.getModel();
        for(int i=0;i<rows_cli;i++){
            model.removeRow(0);
        }
        
        if(!jCboSub_Men_Hijos.getSelectedItem().equals("")){
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 AND SUB_MEN_ID="+jCboSub_Men_Hijos.getSelectedItem().toString().substring(0, jCboSub_Men_Hijos.getSelectedItem().toString().indexOf(" ")).trim()+" "+
                        "ORDER BY MIS_PERMISO,MIS_MENU;";
            
            CargaTablaPermisos(sql_tabla, 4);
        }else{
            sql_tabla = "SELECT MEN_ID,MEN_NOMBRE,MIS_ACTIVO FROM DNPERMISOLOGIA " +
                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                        "MIS_TIPOMENU=2 AND SUB_MEN_ID="+jCboSub_Men_Raiz.getSelectedItem().toString().substring(0, jCboSub_Men_Raiz.getSelectedItem().toString().indexOf(" ")).trim()+
                        "ORDER BY MIS_PERMISO,MIS_MENU;";
            
            CargaTablaPermisos(sql_tabla, 3);
            
            jLabel5.setVisible(false); jCboSub_Men_Nietos.setVisible(false);
        }
    }//GEN-LAST:event_jCboSub_Men_HijosItemStateChanged

    private void bt_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_salirActionPerformed
        this.dispose();
    }//GEN-LAST:event_bt_salirActionPerformed

    private void action_tablas(int Row){
        String estadoPermiso = "";
        codigo = (String) Tabla.getValueAt(Row,0).toString().trim();
        boolean select = (boolean) Tabla.getValueAt(Row,2);
        
        String SQL = "SELECT * FROM DNPERMISOLOGIA WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_MENU='"+codigo+"' "+
                     "AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4);
        System.out.println(SQL);
        try {
            rs = conet.consultar(SQL);
            
            SQLQuerys modificar = new SQLQuerys();
            if (rs.getBoolean("MIS_ACTIVO")==false){
                modificar.SqlUpdate("UPDATE DNPERMISOLOGIA SET MIS_ACTIVO='1' WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+
                                    " AND MIS_MENU='"+codigo+"'");
                
                estadoPermiso = "1";
            }else if (rs.getBoolean("MIS_ACTIVO")==true){
                modificar.SqlUpdate("UPDATE DNPERMISOLOGIA SET MIS_ACTIVO='0' WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+
                                    " AND MIS_MENU='"+codigo+"'");
                
                estadoPermiso = "0";
            }

            //CargaTablaPermisos(sql_tabla, 0);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Permisologias.class.getName()).log(Level.SEVERE, null, ex);
        }
//        String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNPERMISO_GRUPAL WHERE PER_ID='"+codigo+"'";
//        Reg_count = conet.Count_Reg(SQLReg);
//        try {
//            mostrar();
//        } catch (SQLException ex) {
//            Logger.getLogger(Permisologias.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        this.Bandera=true;
        

//        String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNPERMISO_GRUPAL WHERE PER_ID='"+codigo+"'";
//        Reg_count = conet.Count_Reg(SQLReg);

//        try {
//            mostrar();
//        } catch (SQLException ex) {
//            Logger.getLogger(Permisologias.class.getName()).log(Level.SEVERE, null, ex);
//        }

//        this.Bandera=true;
    }
    
    private void ActualizaJtable(int item){
        action_tablas(item);
        Tabla.getSelectionModel().setSelectionInterval(item, item);
    }
    
    private void CargaTablaPermisos(String Sql, int Combo){
        try {
            DefaultTableModel model = (DefaultTableModel) Tabla.getModel();
            rs = conet.consultar(Sql);
            int Item1=0;
                    
            rs.beforeFirst();
            while (rs.next()){
                model.addRow(new Object[]{rs.getString("MEN_ID"),rs.getString("MEN_NOMBRE"),rs.getBoolean("MIS_ACTIVO")});
            }
            SelectCombo=0;
            Item1=0;

            Tabla.getColumnModel().getColumn(0).setPreferredWidth(20);
            Tabla.getColumnModel().getColumn(1).setPreferredWidth(250);
            Tabla.getColumnModel().getColumn(2).setPreferredWidth(30);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Permisologias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String buscasubmenus(String Permiso, String Id){
        String ID_Sub_Menu="";
        
        try {
            String sql = "SELECT MEN_ID FROM DNPERMISOLOGIA \n" +
                         "INNER JOIN DNMENU ON MIS_MENU=MEN_ID \n"+
                         "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+Permiso+" AND \n" +
                         "MIS_TIPOMENU=2 AND SUB_MEN_ID="+Id+" ORDER BY MIS_PERMISO,MIS_MENU";
            
            ResultSet rs_sub_menu = conet.consultar(sql);
            
            if(rs_sub_menu.getRow()>0){
                rs_sub_menu.beforeFirst();
                while (rs_sub_menu.next()){
                    String sql_sub_Menu = "SELECT SUB_MEN_ID FROM DNPERMISOLOGIA " +
                                          "INNER JOIN DNMENU ON MIS_MENU=MEN_ID \n"+
                                          "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+Permiso+" AND " +
                                          "MIS_TIPOMENU=2 AND SUB_MEN_ID="+rs_sub_menu.getString("MEN_ID")+" \n"+
                                          "ORDER BY MIS_PERMISO,MIS_MENU";

                    ResultSet sub_menu = conet.consultar(sql_sub_Menu);

                    if (sub_menu.getRow()>0){
                        ID_Sub_Menu = ID_Sub_Menu+"'"+sub_menu.getString("SUB_MEN_ID")+"',";
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Permisologias.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("ID_Sub_Menu: "+ID_Sub_Menu);
        return ID_Sub_Menu;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla;
    private com.l2fprod.common.swing.JLinkButton bt_salir;
    private javax.swing.JComboBox jCboMen_Raiz;
    private javax.swing.JComboBox jCboMis_Permisos;
    private javax.swing.JComboBox jCboSub_Men_Hijos;
    private javax.swing.JComboBox jCboSub_Men_Nietos;
    private javax.swing.JComboBox jCboSub_Men_Raiz;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}