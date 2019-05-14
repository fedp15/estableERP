package Vista;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.Painter;
import javax.swing.SwingWorker;
import javax.swing.UIDefaults;
import javax.swing.table.DefaultTableModel;
import util.CodigoConsecutivo;
import static util.ColorApp.colorText;
import util.CopiarFicheros;
import util.GeneraCbte;
import util.Internacionalizacion;
import util.SQLQuerys;

public class ProgressBarRefrescar extends javax.swing.JInternalFrame implements PropertyChangeListener {
    private TaskMySQL task_mysql;
    private TaskAsociaImagenes taskAsociaImagenes;
    private TaskGeneraCbte task_cbte;
    private TaskBulidStructCtas taskBulidStructCtas;
    private TaskBulidAsientoCierreFiscal taskBulidAsientoCierreFiscal;
    private TaskImpDocConf taskImpDocConf;
    private String tipomenu="", codigo="", NumMenu="", permiPOS="", sDirectorio, codEmpresa = "",
                   tipdoc = "";
    private ResultSet rs_opc_menus, rs_docs, rs_ctas, rs_nivelMaximoCtas,rs_imp_docs, rsExistPermisos;
    private File[] ficheros;
    private int nivelMax = 0;
    private JTable tabla;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
//    private final ControladorPlanCuentas planCuentas = new ControladorPlanCuentas();
    private conexion conet;
    
    public ProgressBarRefrescar(String tipomenu, String codigo, String titulo) {
        initComponents();
        
        codEmpresa = VarGlobales.getCodEmpresa();
        this.tipomenu = tipomenu;
        this.codigo = codigo;
        jLbTitulo.setText(titulo);
        
        jProgressBar1.setMaximum(99);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        UIDefaults defaults = new UIDefaults();
        defaults.put("ProgressBar[Enabled].foregroundPainter", new MyPainter(Color.decode(colorText)));
        defaults.put("ProgressBar[Enabled+Finished].foregroundPainter", new MyPainter(Color.decode(colorText)));
        
        jProgressBar1.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
        jProgressBar1.putClientProperty("Nimbus.Overrides", defaults);
        
        String sql_menus = "SELECT MEN_ID,MEN_EXTERNO FROM DNMENU WHERE MEN_TIPO="+tipomenu+" ORDER BY MEN_ID ASC;";
                
        conet = new conexion();
                
        try {
            rs_opc_menus = conet.consultar(sql_menus);
            jProgressBar1.setMaximum(rs_opc_menus.getRow());
            System.err.println(rs_opc_menus.getRow());
            rs_opc_menus.beforeFirst();
            
            rsExistPermisos =  conet.consultar("SELECT * FROM dnpermisologia WHERE mis_empresa='"+codEmpresa+"'");
                
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            task_mysql = new TaskMySQL();
            task_mysql.addPropertyChangeListener(this);
            task_mysql.execute();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProgressBarRefrescar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ProgressBarRefrescar(String tipdoc,JTable tabla,String titulo, String filtro){
        initComponents();
        
        jLbTitulo.setText(titulo);
        jProgressBar1.setMaximum(99);
        this.tipdoc = tipdoc;
        this.tabla = tabla;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        UIDefaults defaults = new UIDefaults();
        defaults.put("ProgressBar[Enabled].foregroundPainter", new MyPainter(Color.decode(colorText)));
        defaults.put("ProgressBar[Enabled+Finished].foregroundPainter", new MyPainter(Color.decode(colorText)));
        
        jProgressBar1.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
        jProgressBar1.putClientProperty("Nimbus.Overrides", defaults);
        
        conet = new conexion();
        
        if (filtro.equals("Ventas")){
            filtro = " doc_cxc=1 ";
        }else if (filtro.equals("Compras")){
            filtro = " doc_cxp=1 ";
        }
        String sql = "SELECT DISTINCT DOC_CODIGO,DOC_DESCRI FROM DNDOCUMENTOS "+
                     "WHERE DOC_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                     "DOC_CODIGO<>'"+tipdoc+"' AND "+filtro+
                     "ORDER BY DOC_CODIGO";
        System.out.println(sql);
        try {
           rs_imp_docs = conet.consultar(sql);
           
           jProgressBar1.setMaximum(rs_imp_docs.getRow());
           
           rs_imp_docs.beforeFirst();
           setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
           taskImpDocConf = new TaskImpDocConf();
           taskImpDocConf.addPropertyChangeListener(this);
           taskImpDocConf.execute();
           
        } catch (Exception ex) {
            Logger.getLogger(ProgressBarRefrescar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ProgressBarRefrescar(String tipomenu, String codigo, String codEmpresa, String titulo) {
        initComponents();
        
        this.tipomenu = tipomenu;
        this.codigo = codigo;
        this.codEmpresa = codEmpresa;
        jLbTitulo.setText(titulo);
        
        jProgressBar1.setMaximum(99);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        UIDefaults defaults = new UIDefaults();
        defaults.put("ProgressBar[Enabled].foregroundPainter", new MyPainter(Color.decode(colorText)));
        defaults.put("ProgressBar[Enabled+Finished].foregroundPainter", new MyPainter(Color.decode(colorText)));
        
        jProgressBar1.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
        jProgressBar1.putClientProperty("Nimbus.Overrides", defaults);
        
        //String sql_menus = "SELECT * FROM DNMENU WHERE MEN_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MEN_TIPO="+tipomenu+" ORDER BY MEN_ID ASC;";
        String sql_menus = "SELECT MEN_ID,MEN_EXTERNO FROM DNMENU WHERE MEN_TIPO="+tipomenu+" ORDER BY MEN_ID ASC;";
                
        conet = new conexion();
                
        try {
            rs_opc_menus = conet.consultar(sql_menus);
            jProgressBar1.setMaximum(rs_opc_menus.getRow());
            System.err.println(rs_opc_menus.getRow());
            rs_opc_menus.beforeFirst();
            
            rsExistPermisos =  conet.consultar("SELECT * FROM dnpermisologia WHERE mis_empresa='"+this.codEmpresa+"'");
                
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            task_mysql = new TaskMySQL();
            task_mysql.addPropertyChangeListener(this);
            task_mysql.execute();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProgressBarRefrescar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ProgressBarRefrescar(String titulo, String sDirectorio, File[] ficheros) {
        initComponents();
        jLbTitulo.setText(titulo);
        
        this.ficheros = ficheros;
        this.sDirectorio = sDirectorio;
        
        jProgressBar1.setMaximum(99);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        UIDefaults defaults = new UIDefaults();
        defaults.put("ProgressBar[Enabled].foregroundPainter", new MyPainter(Color.decode(colorText)));
        defaults.put("ProgressBar[Enabled+Finished].foregroundPainter", new MyPainter(Color.decode(colorText)));
        
        jProgressBar1.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
        jProgressBar1.putClientProperty("Nimbus.Overrides", defaults);
        
        String sql_menus = "SELECT MEN_ID,MEN_EXTERNO FROM DNMENU WHERE MEN_TIPO="+tipomenu+" ORDER BY MEN_ID ASC;";
                
        jProgressBar1.setMaximum(this.ficheros.length);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        taskAsociaImagenes = new TaskAsociaImagenes();
        taskAsociaImagenes.addPropertyChangeListener(this);
        taskAsociaImagenes.execute();
    }
    
    public ProgressBarRefrescar(String cTipdoc, String fecha){
        initComponents();
        
        jLbTitulo.setText(idioma.loadLangMsg().getString("MsgGenerandoCbte"));
        this.tipdoc = cTipdoc;
        
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        UIDefaults defaults = new UIDefaults();
        defaults.put("ProgressBar[Enabled].foregroundPainter", new MyPainter(Color.decode(colorText)));
        defaults.put("ProgressBar[Enabled+Finished].foregroundPainter", new MyPainter(Color.decode(colorText)));
        
        jProgressBar1.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
        jProgressBar1.putClientProperty("Nimbus.Overrides", defaults);
        
        String sql_docs;
        
        if(!fecha.equals("")){
            sql_docs = "SELECT * FROM dninventario WHERE "+
                       "inv_coddoc='"+tipdoc+"' AND "+
                       //"inv_activo=1 AND "+
                       "inv_empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
                       "inv_fecha>='"+fecha+"' AND "+
                       "inv_compcontab IS NULL GROUP BY inv_numdoc,inv_orden "+
                       "ORDER BY inv_numdoc";
        }else{
            sql_docs = "SELECT * FROM dninventario WHERE "+
                       "inv_coddoc='"+tipdoc+"' AND "+
                       //"inv_activo=1 AND "+
                       "inv_empresa='"+VarGlobales.getCodEmpresa()+"' AND "+
                       "inv_compcontab IS NULL GROUP BY inv_numdoc,inv_orden "+
                       "ORDER BY inv_numdoc";
        }
        System.out.println(sql_docs);
        
        conet = new conexion();
        
        try {
            try {
                rs_docs = conet.consultar(sql_docs);
                
//                if(rs_docs.getRow()>0){
                    jProgressBar1.setMaximum(rs_docs.getRow());

                    rs_docs.beforeFirst();

                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    task_cbte = new TaskGeneraCbte();
                    task_cbte.addPropertyChangeListener(this);
                    task_cbte.execute();
//                }
            } catch (SQLException ex) {
                Logger.getLogger(ProgressBarRefrescar.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProgressBarRefrescar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ProgressBarRefrescar(){
        initComponents();
        
        jLbTitulo.setText(idioma.loadLangMsg().getString("MsgConstEstructuraCtas"));
        
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        UIDefaults defaults = new UIDefaults();
        defaults.put("ProgressBar[Enabled].foregroundPainter", new MyPainter(Color.decode(colorText)));
        defaults.put("ProgressBar[Enabled+Finished].foregroundPainter", new MyPainter(Color.decode(colorText)));
        
        jProgressBar1.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
        jProgressBar1.putClientProperty("Nimbus.Overrides", defaults);
        
        String sql_docs = "select * from dncta where cta_nivel=1";
        System.out.println(sql_docs);
        
        conet = new conexion();
        
        try {
            rs_nivelMaximoCtas = conet.consultar("select max(cta_nivel) as nivelMax from dncta");
            rs_nivelMaximoCtas.beforeFirst();
            rs_nivelMaximoCtas.next();
            
            nivelMax = rs_nivelMaximoCtas.getInt("nivelMax");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProgressBarRefrescar.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                rs_nivelMaximoCtas.close();
                conet.conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProgressBarRefrescar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
            try {
                rs_ctas = conet.consultar(sql_docs);
                
//                if(rs_docs.getRow()>0){
                    jProgressBar1.setMaximum(rs_ctas.getRow());

                    rs_ctas.beforeFirst();

                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    taskBulidStructCtas = new TaskBulidStructCtas();
                    taskBulidStructCtas.addPropertyChangeListener(this);
                    taskBulidStructCtas.execute();
//                }
            } catch (SQLException ex) {
                Logger.getLogger(ProgressBarRefrescar.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProgressBarRefrescar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ProgressBarRefrescar(int maxNivel){
        initComponents();
        
        jLbTitulo.setText(idioma.loadLangMsg().getString("MsgConstEstructuraCtas"));
        
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        UIDefaults defaults = new UIDefaults();
        defaults.put("ProgressBar[Enabled].foregroundPainter", new MyPainter(Color.decode(colorText)));
        defaults.put("ProgressBar[Enabled+Finished].foregroundPainter", new MyPainter(Color.decode(colorText)));
        
        jProgressBar1.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
        jProgressBar1.putClientProperty("Nimbus.Overrides", defaults);
        
        String sql_docs = "select * from dncta where cta_nivel="+maxNivel+" and SUBSTRING(cta_codigo, 1,1)>=4";
        System.out.println(sql_docs);
        
        conet = new conexion();
        
        try {
            try {
                rs_ctas = conet.consultar(sql_docs);
                
//                if(rs_docs.getRow()>0){
                    jProgressBar1.setMaximum(rs_ctas.getRow());

                    rs_ctas.beforeFirst();

                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    taskBulidAsientoCierreFiscal = new TaskBulidAsientoCierreFiscal();
                    taskBulidAsientoCierreFiscal.addPropertyChangeListener(this);
                    taskBulidAsientoCierreFiscal.execute();
//                }
            } catch (SQLException ex) {
                Logger.getLogger(ProgressBarRefrescar.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProgressBarRefrescar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            jProgressBar1.setValue(progress);
            jProgressBar1.setStringPainted(true);
        } 
    }

    class TaskAsociaImagenes extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
            String codigo = "";
            int count = 0;
            SQLQuerys modificar = new SQLQuerys();
            
            for (int x=0;x<ficheros.length;x++){
                count++;
                //System.out.println(ficheros[x].getName());
                try {
                    codigo = ficheros[x].getName().substring(0, ficheros[x].getName().indexOf("."));
                    
                    String SQL = "SELECT * FROM dnproducto WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRO_CODIGO='"+codigo+"'";
                    
                    conet = new conexion();
                    ResultSet rs = conet.consultar(SQL);
                    
                    if(rs.getRow()==0){
                        String sqlProduct = "";
                        
                        try {
                            sqlProduct = "SELECT * FROM dnproducto WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRO_CODIGO='0"+codigo+"'";
                
                            if (conet.consultar(sqlProduct).getRow()>0){
                                codigo = "0"+ficheros[x].getName().substring(0, ficheros[x].getName().indexOf("."));
                            }else{
                                sqlProduct = "SELECT * FROM dnproducto WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRO_CODIGO='00"+codigo+"'";
                    
                                if (conet.consultar(sqlProduct).getRow()>0){
                                    codigo = "00"+ficheros[x].getName().substring(0, ficheros[x].getName().indexOf("."));
                                }else{
                                    File ArchivoErrado = new File(sDirectorio+"/"+ficheros[x].getName());
                                    ArchivoErrado.delete();
                    
                                    x++;
                        
                                    codigo = ficheros[x].getName().substring(0, ficheros[x].getName().indexOf("."));
                                }
                            }
                        } catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(ProgressBarRefrescar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (Exception e) {
                    File ArchivoErrado = new File(sDirectorio+"/"+ficheros[x].getName());
                    ArchivoErrado.delete();
                    
                    x++;
                    codigo = ficheros[x].getName().substring(0, ficheros[x].getName().indexOf("."));
                }
                
                //System.out.println(codigo);
                
                File carpeta = new File(System.getProperty("user.dir")+"/build/classes/fotos_productos/");
                carpeta.mkdirs();
                
                String origen = sDirectorio+"/"+ficheros[x].getName();
                //System.out.println("Archivo origen "+ficheros[x].getName());
                String destino = carpeta.getAbsolutePath()+"/"+ficheros[x].getName();
                
                CopiarFicheros copiar = new CopiarFicheros();
                try {
                    Boolean lcopia = copiar.CopiarFicheros(origen,destino);
                    
                    if (lcopia==true){
                        File ArchivoCopiado = new File(origen);
                        ArchivoCopiado.delete();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ProgressBarRefrescar.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                String sql = "UPDATE DNPRODUCTO SET PRO_RUTAIMG='"+ficheros[x].getName()+"' "+
                             "WHERE PRO_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND PRO_CODIGO='"+codigo+"'";
                
                modificar.SqlUpdate(sql);
                
                jProgressBar1.setValue(count);
                jProgressBar1.setStringPainted(true);
            }
            
            return null;
        }
 
        @Override
        public void done() {
            dispose();
        }
    }
    
    class TaskMySQL extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
            String SqlInsert = "";
            
            try {
                SQLQuerys permisologias = new SQLQuerys();

                int count = 0, numReg = 0;
                System.err.println(rs_opc_menus.getRow());
                while (rs_opc_menus.next()){
                    count++;
                    
                    //NumMenu=String.format("%06d", rs_opc_menus.getInt("MEN_ID"));
                    NumMenu=String.format("%06d", rs_opc_menus.getInt("MEN_ID"));
                    
                    if (rs_opc_menus.getString("MEN_EXTERNO").equals("1")){
                        permiPOS="1";
                    }else{
                        permiPOS="0";
                    }
                    
                    if (codEmpresa.equals("")){
                        SqlInsert = "INSERT INTO DNPERMISOLOGIA (MIS_EMPRESA,MIS_ID,MIS_PERMISO,MIS_MENU,MIS_TIPOMENU,MIS_TIPOPER,"+
                                    "MIS_POS,MIS_ERP,MIS_ACTIVO) "+
                                    "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+NumMenu+"_"+codigo+"_"+tipomenu+"_"+"0_"+codEmpresa+"',"+
                                             codigo+","+rs_opc_menus.getInt("MEN_ID")+","+tipomenu+",0,'"+permiPOS+"','0','1');";
                    }else{
                        SqlInsert = "INSERT INTO DNPERMISOLOGIA (MIS_EMPRESA,MIS_ID,MIS_PERMISO,MIS_MENU,MIS_TIPOMENU,MIS_TIPOPER,"+
                                    "MIS_POS,MIS_ERP,MIS_ACTIVO) "+
                                    "VALUES ('"+codEmpresa+"','"+NumMenu+"_"+codigo+"_"+tipomenu+"_"+"0_"+codEmpresa+"',"+
                                             codigo+","+rs_opc_menus.getInt("MEN_ID")+","+tipomenu+",0,'"+permiPOS+"','0','1');";
                    }
                    
                    permisologias.SqlInsert(SqlInsert);
                    
                    jProgressBar1.setValue(count);
                    jProgressBar1.setStringPainted(true);
                    
                }   //*********************************************************************************
            } catch (SQLException ex) {
                Logger.getLogger(ProgressBarRefrescar.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return null;
        }
 
        @Override
        public void done() {
            dispose();
        }
    }
    
    class TaskGeneraCbte extends SwingWorker<Void, Void>{
        @Override
        protected Void doInBackground() throws Exception {
            GeneraCbte comprobante = new GeneraCbte();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String fecha = sdf.format(date);
            String cbte;
            int count = 0;
            
            while(rs_docs.next()){
                count++;
                String numdoc = rs_docs.getString("INV_NUMDOC");
                String codmae = rs_docs.getString("INV_CODMAE");
                String action = rs_docs.getString("INV_STATUS_ACTION");
                int orden = rs_docs.getInt("INV_ORDEN");
                
                String monto_t = String.valueOf(comprobante.getMontoDoc(codmae, numdoc, tipdoc, orden));
                
                if(action.equals("UPD")){
                    cbte = comprobante.getCbte(codmae, numdoc, tipdoc);
                    
                    if(!cbte.equals("")){
                        comprobante.updateCbte(cbte, monto_t);
                    }
                }else{
                    cbte = comprobante.generarNumeroCbte(codmae,numdoc,tipdoc);
                    comprobante.creaCbte(cbte, codmae, numdoc, tipdoc, monto_t, false, "", fecha);
                }
                if(!cbte.equals("")){
                    comprobante.updateDocCbte(codmae, numdoc, tipdoc, cbte, orden);
                }
                jProgressBar1.setValue(count);
                jProgressBar1.setStringPainted(true);
            }
            return null;
        }

        @Override
        protected void done() {
            dispose();
//            super.done(); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    class TaskBulidStructCtas extends SwingWorker<Void, Void>{
        @Override
        protected Void doInBackground() throws Exception {
            String SqlInsert = "", cta = "";
            int longPrefijo = 0, nivelPrefijo = 0;
            
            try {
                SQLQuerys permisologias = new SQLQuerys();

                int count = 0;
                
                while (rs_ctas.next()){
                    count++;
                    
                    try {
                        rs_nivelMaximoCtas = conet.consultar("select cta_codigo,cta_nivel_"+(nivelMax-1)+" from dncta "+
                                                             "where cta_nivel="+nivelMax+" limit 1");
                        rs_nivelMaximoCtas.beforeFirst();
                        rs_nivelMaximoCtas.next();
            
                        cta = rs_nivelMaximoCtas.getString("cta_codigo");
                        cta =  cta.substring(0,  cta.indexOf("0")+2);
                        
                        if (cta.substring(cta.indexOf("0")-1,  cta.indexOf("0")).equals(".")){
                            if (cta.substring(cta.length()-1,  cta.length()).equals("0")){
                                longPrefijo = 2;
                                
                                cta = rs_nivelMaximoCtas.getString("cta_codigo");
                                cta =  cta.substring(0,  cta.indexOf("0")+3);
                                
                                if (cta.substring(cta.length()-1,  cta.length()).equals("0")){
                                    longPrefijo = 3;
                                }else{
                                    cta = rs_nivelMaximoCtas.getString("cta_codigo");
                                    cta =  cta.substring(0,  cta.indexOf("0")+2);
                                }
                            }else{
                                longPrefijo = 1;
                            }
                        }
                        
//                        nivelPrefijo = planCuentas.countOccurrences(cta, '.', 0, "")+1;
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(ProgressBarRefrescar.class.getName()).log(Level.SEVERE, null, ex);
                    }finally{
                        try {
                            rs_nivelMaximoCtas.close();
                            conet.conn.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(ProgressBarRefrescar.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    SqlInsert = "INSERT INTO dnestructura_cta (STR_EMPRESA,STR_USER,STR_MACPC,STR_PADRE,STR_NIVEL_MAX,"+
                                "STR_NIVEL_PREFIJO,STR_PREFIJO,STR_LONG_PREF,STR_ACTIVO) "+
                                "VALUES ('"+VarGlobales.getCodEmpresa()+"', '"+VarGlobales.getIdUser()+"', '"+VarGlobales.getMacPc()+"',"+
                                        "'"+rs_ctas.getString("CTA_PADRE")+"', '"+nivelMax+"', '"+nivelPrefijo+"', '0', '"+longPrefijo+"', '1');";
                    
                    permisologias.SqlInsert(SqlInsert);
                    
                    jProgressBar1.setValue(count);
                    jProgressBar1.setStringPainted(true);
                    
                }   //*********************************************************************************
            } catch (SQLException ex) {
                Logger.getLogger(ProgressBarRefrescar.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return null;
        }

        @Override
        protected void done() {
            dispose();
//            super.done(); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    class TaskBulidAsientoCierreFiscal extends SwingWorker<Void, Void>{
        @Override
        protected Void doInBackground() throws Exception {
            String SqlInsert = "", cta = "", debeHaber = "", haberDebe = "", numConfAsient = "";
            int longPrefijo = 0, nivelPrefijo = 0;
            
            try {
                SQLQuerys permisologias = new SQLQuerys();

                int count = 0;
                
                while (rs_ctas.next()){
                    count++;
                    
                    CodigoConsecutivo codigo = new CodigoConsecutivo();
                    numConfAsient = codigo.CodigoConsecutivo("ccont_num_config","dnconfig_contable",10,"WHERE ccont_empresa='"+VarGlobales.getCodEmpresa()+"' ");
                    
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date fch = new Date();
                    String fecha = sdf.format(fch);
        
                    if (rs_ctas.getString("cta_naturaleza").equals("Deudor")){
                        debeHaber = "Haber";
                    }
                    if (rs_ctas.getString("cta_naturaleza").equals("Acreedor")){
                        debeHaber = "Debe";
                    }
                    
                    SqlInsert = "INSERT INTO dnconfig_contable (ccont_empresa, ccont_user, ccont_macpc, ccont_num_config, ccont_asiento_c, ccont_documento,"+
                                                   "ccont_transaccion, ccont_tipo_contab, ccont_categ_prod, ccont_clien_prov, ccont_periodicidad,"+
                                                   "ccont_condic, ccont_debe_haber, ccont_cuenta, ccont_monto,ccont_activo, ccont_fecha, ccont_orden,"+
                                                   "ccont_status_action) "+
                                          "VALUES ('"+VarGlobales.getCodEmpresa()+"', '"+VarGlobales.getIdUser()+"', '"+VarGlobales.getMacPc()+"',"+
                                                  "'"+numConfAsient+"', '8', 'CFI', 'Aplicacion', 'Transaccional', '', '', 'N/A', null, "+
                                                  "'"+debeHaber+"', '"+rs_ctas.getString("cta_codigo")+"', 'SALDO FINAL CTAS', '1', "+
                                                  "'"+fecha+"', '1', 'INS');";
                    
                    permisologias.SqlInsert(SqlInsert);
                    
                    jProgressBar1.setValue(count);
                    jProgressBar1.setStringPainted(true);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProgressBarRefrescar.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return null;
        }

        @Override
        protected void done() {
            dispose();
//            super.done(); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    class TaskImpDocConf extends SwingWorker<Void, Void>{
        @Override
        protected Void doInBackground() throws Exception {
            int count = 0;
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(30);
           
            while (rs_imp_docs.next()){
                count++;
                String sqlinsert = "INSERT INTO dnconfigimport (IMP_EMPRESA,IMP_CODDOC,IMP_DOCIMP,IMP_SELECT) "+
                                   "VALUES ('"+VarGlobales.getCodEmpresa()+"','"+tipdoc+"','"+rs_imp_docs.getString("DOC_CODIGO")+"',"+0+")";

                System.out.println(sqlinsert);
                SQLQuerys insert = new SQLQuerys();
                insert.SqlInsert(sqlinsert);
            
                modelo.addRow(new Object[]{rs_imp_docs.getString("DOC_CODIGO"),rs_imp_docs.getString("DOC_DESCRI"),false});
                
                jProgressBar1.setValue(count);
                jProgressBar1.setStringPainted(true);
           }
           return null;
        }

        @Override
        protected void done() {
            dispose();
//            super.done(); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jLbTitulo = new javax.swing.JLabel();

        jProgressBar1.setForeground(new java.awt.Color(105, 105, 105));
        jProgressBar1.setMaximum(983);
        jProgressBar1.setAutoscrolls(true);

        jLbTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbTitulo.setText("Creando registros de Permisologias");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLbTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLbTitulo;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}

class MyPainter implements Painter<JProgressBar> {

    private final Color color;

    public MyPainter(Color c1) {
        this.color = c1;
    }
    @Override
    public void paint(Graphics2D gd, JProgressBar t, int width, int height) {
        gd.setColor(color);
        gd.fillRect(0, 0, width, height);
    }
}