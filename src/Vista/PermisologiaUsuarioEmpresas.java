package Vista;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import static Vista.MenuPrincipal.escritorioERP;
import java.awt.Color;
import java.awt.Dimension;
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

public class PermisologiaUsuarioEmpresas extends javax.swing.JInternalFrame {
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

    public PermisologiaUsuarioEmpresas(String origen) {
        initComponents();
        comboUsuarios();
        comboGrupoPermisos();

        if(origen.equals("ERP")){
            desktop = escritorioERP;
        }else{
//            desktop = escritorio;
        }
        
        jPanel1.setBackground(Color.decode(colorForm));
        
        //jLabel1.setForeground(Color.decode(colorText));
        //jLabel2.setForeground(Color.decode(colorText));
        
        //bt_salir.setBackground(Color.decode(colorForm)); bt_salir.setForeground(Color.decode(colorText));
        //bt_salir.setHorizontalAlignment(2);
        
//------------------------------------------- Carga del Idioma del Formulario -------------------------------------------
        this.setTitle(idioma.loadLangComponent().getString("lbTituloPermisosUsuariosEmpresas"));
        jLabel1.setText(idioma.loadLangComponent().getString("lbGrupoPerm"));
        jLabel2.setText(idioma.loadLangComponent().getString("lbUsuario"));
        
        bt_salir.setText(idioma.loadLangComponent().getString("btnSalir"));
//-----------------------------------------------------------------------------------------------------------------------
        
        Tabla.getColumn("Empresa").setHeaderValue(idioma.loadLangComponent().getString("lbTabEmpresas"));
        Tabla.getColumn("Nombre").setHeaderValue(idioma.loadLangHeaderTable().getString("nombre")+" "+idioma.loadLangHeaderTable().getString("menu"));
        Tabla.getColumn("RIF").setHeaderValue(idioma.loadLangComponent().getString("lbEmpRif"));
        Tabla.getColumn("Selección").setHeaderValue(idioma.loadLangHeaderTable().getString("activo"));

        this.setTitle("Permisos de Usuarios por Empresas");
        chkAprobTodo.setVisible(false);
    }
    
    public void comboGrupoPermisos(){
        //String sql = "SELECT CONCAT(PER_ID,' - ', PER_NOMBRE) AS DATO1 FROM DNPERMISO_GRUPAL WHERE PER_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PER_ACTIVO=1 AND PER_TIPOMENU='2'";
        String sql = "SELECT CONCAT(PER_ID,' - ', PER_NOMBRE) AS DATO1 FROM DNPERMISO_GRUPAL WHERE PER_ACTIVO=1 AND PER_TIPOMENU='2'";
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
        this.jCboMis_Permisos.setModel(mdl); 
    }
    
    public void comboUsuarios(){
        String sql = "SELECT CONCAT(OPE_NUMERO,' - ', OPE_NOMBRE) AS DATO1 FROM dnusuarios WHERE OPE_ACTIVO=1";
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));
        this.jCboUser.setModel(mdl); 
        jCboUser.setSelectedIndex(1);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        jCboUser = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        chkAprobTodo = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
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

        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Empresa", "Nombre", "RIF", "Selección"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
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

        jCboUser.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCboUserItemStateChanged(evt);
            }
        });
        jCboUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCboUserActionPerformed(evt);
            }
        });
        jCboUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCboUserKeyPressed(evt);
            }
        });

        jLabel2.setText("Usuario");

        chkAprobTodo.setBackground(new java.awt.Color(255, 255, 255));
        chkAprobTodo.setText("Autorizar todas las Empresas");
        chkAprobTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAprobTodoActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(105, 105, 105));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bt_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(bt_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCboUser, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCboMis_Permisos, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(chkAprobTodo)))))
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCboMis_Permisos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCboUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkAprobTodo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            if(Tabla.getSelectedColumn()==3){
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

    private void jCboMis_PermisosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCboMis_PermisosItemStateChanged
        int rows_cli = Tabla.getRowCount();
        DefaultTableModel model = (DefaultTableModel) Tabla.getModel();
        for(int i=0;i<rows_cli;i++){
            model.removeRow(0);
        }
        
        System.err.println(jCboMis_Permisos.getSelectedItem());
        if (!jCboMis_Permisos.getSelectedItem().equals("")){
            //sql_tabla = "SELECT DISTINCT dnempresas.emp_codigo,emp_nombre,dnempresas.emp_rif, IF(ISNULL(dnusuarios.ope_numero), 0, 1) as Seleccionado FROM dnempresas\n" +
            sql_tabla = "SELECT DISTINCT dnempresas.emp_codigo,emp_nombre,dnempresas.emp_rif, IF(activo=0, 0, 1) as Seleccionado FROM dnempresas\n" +
                        "LEFT JOIN relac_usuario_grupo_permisos on relac_usuario_grupo_permisos.emp_codigo=dnempresas.emp_codigo \n"+
                        "LEFT JOIN dnusuarios on dnusuarios.ope_numero=relac_usuario_grupo_permisos.ope_numero and "+
                        "dnusuarios.ope_numero="+jCboUser.getSelectedItem().toString().substring(0, 2).trim()+" \n"+
                        "WHERE dnempresas.emp_activo=1 AND "+
                        "relac_usuario_grupo_permisos.ope_numero="+jCboUser.getSelectedItem().toString().substring(0, 2).trim()+" "+
                        "ORDER BY emp_codigo";
                    
            CargaTablaPermisos(sql_tabla, 1);
        }
//        else{
//            for(int i=0;i<rows_cli;i++){
//                modelo.removeRow(0);
//            }
//        }
    }//GEN-LAST:event_jCboMis_PermisosItemStateChanged

    private void jCboUserItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCboUserItemStateChanged
        if(jCboMis_Permisos.getItemCount()>0){
            jCboMis_Permisos.setSelectedIndex(0);
        }
    }//GEN-LAST:event_jCboUserItemStateChanged

    private void jCboUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCboUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCboUserActionPerformed

    private void jCboUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCboUserKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCboUserKeyPressed

    private void chkAprobTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAprobTodoActionPerformed
        //taskApruebaComprob = new TaskApruebaComprob();
        //taskApruebaComprob.execute();
    }//GEN-LAST:event_chkAprobTodoActionPerformed

    private void bt_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_salirActionPerformed
        this.dispose();
    }//GEN-LAST:event_bt_salirActionPerformed

    private void action_tablas(int Row){
        String estadoPermiso = "", activo = "";
        codigo = (String) Tabla.getValueAt(Row,0).toString().trim();
        boolean select = (boolean) Tabla.getValueAt(Row,3);
        
        if (select){
            activo = "1";
        }else{
            activo = "0";
        }
        
        String SQL = "SELECT * FROM relac_usuario_grupo_permisos WHERE emp_codigo='"+codigo+"' and \n"+
                     "per_id="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" and "+
                     "ope_numero="+jCboUser.getSelectedItem().toString().substring(0, 2).trim();
        System.out.println(SQL);
        try {
            rs = conet.consultar(SQL);
            
            SQLQuerys modificar = new SQLQuerys();
            if (rs.getRow()>0){
                modificar.SqlUpdate("UPDATE relac_usuario_grupo_permisos SET activo='"+activo+"' WHERE emp_codigo='"+codigo+"' AND "+
                                    "per_id="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND "+
                                    "ope_numero="+jCboUser.getSelectedItem().toString().substring(0, 2).trim());
                
                estadoPermiso = "1";
                
                if (select){
                    String SQL_Reg = "SELECT COUNT(*) AS REGISTROS FROM dnpermisologia \n"+
                                     "WHERE mis_permiso="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+" AND \n"+
                                     "mis_empresa='"+codigo+"'";
                    Reg_count = conet.Count_Reg(SQL_Reg);
        
                    if (Reg_count==0 || Reg_count==1){
                        tipomenu="2";
                        ProgressBarRefrescar pbr = new ProgressBarRefrescar(tipomenu, 
                                                                        jCboMis_Permisos.getSelectedItem().toString().substring(0, 4),
                                                                        codigo,
                                                                        idioma.loadLangComponent().getString("lbTituloProgresBar"));

                        Dimension desktopSize = escritorioERP.getSize();
                        Dimension jInternalFrameSize = pbr.getSize();
                        pbr.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

                        escritorioERP.add(pbr);
                        pbr.toFront();
                        pbr.setVisible(true);
                    }
                }
            }else{
                String user = jCboUser.getSelectedItem().toString().substring(0, 2).trim();
                modificar.SqlInsert("INSERT INTO relac_usuario_grupo_permisos (emp_codigo,ope_numero,per_id,activo) "+
                                    "VALUES('"+codigo+"','"+user+"',"+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+",1)");
                
                
                modificar.SqlUpdate("UPDATE DNPERMISOLOGIA SET MIS_ACTIVO='0' WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+jCboMis_Permisos.getSelectedItem().toString().substring(0, 4)+
                                    " AND MIS_MENU='"+codigo+"'");
                
                estadoPermiso = "0";
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PermisologiaUsuarioEmpresas.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                model.addRow(new Object[]{rs.getString("emp_codigo"),rs.getString("emp_nombre"),rs.getString("emp_rif"),rs.getBoolean("Seleccionado")});
            }
            SelectCombo=0;
            Item1=0;

            Tabla.getColumnModel().getColumn(0).setPreferredWidth(20);
            Tabla.getColumnModel().getColumn(1).setPreferredWidth(250);
            Tabla.getColumnModel().getColumn(2).setPreferredWidth(30);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PermisologiaUsuarioEmpresas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String buscasubmenus(String Permiso, String Id){
        String ID_Sub_Menu="";
        
        try {
            String sql = "SELECT * FROM DNPERMISOLOGIA " +
                         "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                         "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+Permiso+" AND " +
                         "MIS_TIPOMENU=2 AND SUB_MEN_ID="+Id+" ORDER BY MIS_PERMISO,MIS_MENU";
            
            ResultSet rs_sub_menu = conet.consultar(sql);
            
            rs_sub_menu.beforeFirst();
            while (rs_sub_menu.next()){
                String sql_sub_Menu = "SELECT * FROM DNPERMISOLOGIA " +
                                      "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                                      "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+Permiso+" AND " +
                                      "MIS_TIPOMENU=2 AND SUB_MEN_ID="+rs_sub_menu.getString("MEN_ID")+" "+
                                      "ORDER BY MIS_PERMISO,MIS_MENU";

                ResultSet sub_menu = conet.consultar(sql_sub_Menu);
                
                if (sub_menu.getRow()>0){
                    ID_Sub_Menu = ID_Sub_Menu+"'"+sub_menu.getString("SUB_MEN_ID")+"',";
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PermisologiaUsuarioEmpresas.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("ID_Sub_Menu: "+ID_Sub_Menu);
        return ID_Sub_Menu;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla;
    private com.l2fprod.common.swing.JLinkButton bt_salir;
    private javax.swing.JCheckBox chkAprobTodo;
    private javax.swing.JComboBox jCboMis_Permisos;
    private javax.swing.JComboBox jCboUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}