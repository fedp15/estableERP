package Vista;

import Modelos.FileUpdateStructBd;
import Modelos.VariablesGlobales;
import static Modelos.conexion.bd;
import util.Encrip_Desencrip;
import static Vista.ConfigurarConexion.Tab;
import static Vista.ConfigurarConexion.clave;
import util.SQLQuerys;
import static Modelos.conexion.lBdExist;
import static Modelos.conexion.lCreaBd;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.Painter;
import javax.swing.SwingWorker;
import javax.swing.UIDefaults;
import javax.swing.WindowConstants;
import static util.ColorApp.colorForm;

public class ProgressBarCreaBd extends javax.swing.JFrame implements PropertyChangeListener {
    private TaskMySQL task_mysql;
    private TaskPostGreSQL task_postgresql;
    private int num_crea = 0, num_insert = 0;
    private JScrollPane scrollPane;
    private boolean alterTable = false;
    private String nameFile;
    private ArrayList<FileUpdateStructBd> arrayFileUpdateStructBd = null;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();

    public ProgressBarCreaBd() {
        initComponents();

        jPanel1.setBackground(Color.decode(colorForm));   
        //jLbl_DescriProgre.setForeground(Color.decode(colorText)); jLbl_DescriProgre1.setForeground(Color.decode(colorText));
        
        this.setIconImage(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/icono_app.png").getImage());
        
//******************* Validacion sobre el Boton Cerrar del la Barra de Titulo *******************
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation (WindowConstants.DO_NOTHING_ON_CLOSE);

	addWindowListener(new WindowAdapter(){
            @Override
	    public void windowClosing(WindowEvent we){
                if (num_crea<1110){
                    JOptionPane.showMessageDialog(null, "No se puede cerrar el formulario porque el proceso de contrucción \n"+
                                                        "de la base de datos no ha concluido aun...!!", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
	    }
	});
//***********************************************************************************************
        
        jLbl_DescriProgre.setText("Proceso: ");
        jLbl_DescriProgre1.setText("Registros Iniciales: ");
        setLocationRelativeTo(null);
        
        switch (VarGlobales.getManejador()) {
            case "MySQL":
                if (VarGlobales.islBaseDatosConfiguracion()){
                    bd = VarGlobales.getBaseDatosConfiguracion();
                }else{
                    bd = VarGlobales.getBaseDatos();
                }
                
                if (bd.equals("adminconfigestableerp")){
                    jProgressBar1.setMaximum(399);
                    jProgressBar2.setMaximum(8720);
                }else{
//                    jProgressBar1.setMaximum(8505);
//                    jProgressBar2.setMaximum(9215);
                    jProgressBar1.setMaximum(7534);
                    jProgressBar2.setMaximum(503);
                }
                
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                task_mysql = new TaskMySQL();
                task_mysql.addPropertyChangeListener(this);
                task_mysql.execute();
                break;
            case "PostGreSQL":
                jProgressBar1.setMaximum(1168);
                jProgressBar2.setMaximum(1626);
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                task_postgresql = new TaskPostGreSQL();
                task_postgresql.addPropertyChangeListener(this);
                task_postgresql.execute();
                break;
        }
        
        UIDefaults defaults = new UIDefaults();
        defaults.put("ProgressBar[Enabled].foregroundPainter", new MyPainter(Color.decode("696969")));
        defaults.put("ProgressBar[Enabled+Finished].foregroundPainter", new MyPainter(Color.decode("696969")));
        
        jProgressBar1.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
        jProgressBar1.putClientProperty("Nimbus.Overrides", defaults);
        
        jProgressBar2.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
        jProgressBar2.putClientProperty("Nimbus.Overrides", defaults);
    }
    
    public ProgressBarCreaBd(boolean alterTable, String nameFile, ArrayList<FileUpdateStructBd> arrayFileUpdateStructBd) {
        initComponents();
        this.alterTable = alterTable;
        this.nameFile = nameFile;
        this.arrayFileUpdateStructBd = arrayFileUpdateStructBd;
        
        jPanel1.setBackground(Color.decode(colorForm));   
        //jLbl_DescriProgre.setForeground(Color.decode(colorText)); jLbl_DescriProgre1.setForeground(Color.decode(colorText));
        
        this.setIconImage(new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/icono_app.png").getImage());
        
//******************* Validacion sobre el Boton Cerrar del la Barra de Titulo *******************
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation (WindowConstants.DO_NOTHING_ON_CLOSE);

	addWindowListener(new WindowAdapter(){
            @Override
	    public void windowClosing(WindowEvent we){
                if (num_crea<1110){
                    JOptionPane.showMessageDialog(null, "No se puede cerrar el formulario porque el proceso de contrucción \n"+
                                                        "de la base de datos no ha concluido aun...!!", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
	    }
	});
//***********************************************************************************************
        
        jLbl_DescriProgre.setText("Proceso: ");
        jLbl_DescriProgre1.setVisible(false);
        setLocationRelativeTo(null);
        
        switch (VarGlobales.getManejador()) {
            case "MySQL":
                if (VarGlobales.islBaseDatosConfiguracion()){
                    bd = VarGlobales.getBaseDatosConfiguracion();
                }else{
                    bd = VarGlobales.getBaseDatos();
                }
                
                if (bd.equals("adminconfigestableerp")){
                    jProgressBar1.setMaximum(285);
                    jProgressBar2.setMaximum(8672);
                }else{
//                    jProgressBar1.setMaximum(8505);
//                    jProgressBar2.setMaximum(9215);
                    jProgressBar1.setMaximum(7534);
                    jProgressBar2.setMaximum(503);
                }
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                task_mysql = new TaskMySQL();
                task_mysql.addPropertyChangeListener(this);
                task_mysql.execute();
                break;
            case "PostGreSQL":
                jProgressBar1.setMaximum(1168);
                jProgressBar2.setMaximum(1626);
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                task_postgresql = new TaskPostGreSQL();
                task_postgresql.addPropertyChangeListener(this);
                task_postgresql.execute();
                break;
        }
        
        UIDefaults defaults = new UIDefaults();
        defaults.put("ProgressBar[Enabled].foregroundPainter", new MyPainter(Color.decode("696969")));
        defaults.put("ProgressBar[Enabled+Finished].foregroundPainter", new MyPainter(Color.decode("696969")));
        
        jProgressBar1.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
        jProgressBar1.putClientProperty("Nimbus.Overrides", defaults);
        
        jProgressBar2.setVisible(false);
    }
    
    class TaskMySQL extends SwingWorker<Void, Void> {
        File Archivo_SQL_Temp = null;
        String ScriptSQL="";
        SQLQuerys insertarconex = new SQLQuerys();
        BufferedReader pr;
            
        boolean storedProcedure=false;
        String Read2="", Crea_Tabla="", Create_Unique="", Create_Index = "", Alter_Table = "", truncate = "";
        String AreaDetalle="", Trigger="", Insert_into="", Update_Reg="", set = "", rename_Table="", stored_procedure=""; 
        int Linea_Declare=0; int Linea_Set=0; int Linea_If=0, lineaInicioSp=0, lineaFinSp=0;
            
        @Override
        public Void doInBackground() {
            VarGlobales.getErrorCreaBd().clear();
            
//*********************************************************************************
            if (!alterTable){
                File carpeta, Archivo_SQL;
                
                if (VarGlobales.islBaseDatosConfiguracion()){
                    bd = VarGlobales.getBaseDatosConfiguracion();
                }else{
                    bd = VarGlobales.getBaseDatos();
                }
                
                if (bd.equals("adminconfigestableerp")){
                    carpeta = new File(System.getProperty("user.dir")+"\\Configuracion");
                    Archivo_SQL_Temp = new File(carpeta.getAbsolutePath()+"\\adminconfigestableerp.sql");
                }else{
                    carpeta = new File(System.getProperty("user.dir")+"\\Configuracion");
                    Archivo_SQL = new File(carpeta.getAbsolutePath()+"\\BD-MySQL.sql");
                    Archivo_SQL_Temp = new File(carpeta.getAbsolutePath()+"\\"+"temp");

//********** Codigo para Desencriptar el Archivo SQL de la Base de Datos **********
                    try { 
                        ScriptSQL = Encrip_Desencrip.readEncrypted(Archivo_SQL, 12345);
            
                        //Creo el archivo desencriptado temporal
                        if (Archivo_SQL_Temp.createNewFile()){    
                            FileWriter Arc = new FileWriter(Archivo_SQL_Temp,true);
                            BufferedWriter escribir = new BufferedWriter(Arc);

                            escribir.write(ScriptSQL);

                            escribir.close();
                        }           
                    } catch (IOException ioe) {
                    }
                }
                
                //String DropDataBase = "drop database if exists `"+VarGlobales.getBaseDatos()+"`;";
                String DropDataBase = "drop database if exists `"+bd+"`;";
                jLbl_DescriProgre.setText("Proceso: "+DropDataBase);
                try {
                    insertarconex.SqlInsert(DropDataBase, "ProgressBarCreaBd");
                } catch (Exception e) {
                    Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, e);
                }
//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException e){
//                }

                //String CreateDataBase = "create database `"+VarGlobales.getBaseDatos()+"` /*!40100 COLLATE 'utf8_general_ci' */;";
                String CreateDataBase = "create database `"+bd+"` /*!40100 COLLATE 'utf8_general_ci' */;";
                jLbl_DescriProgre.setText("Proceso: "+CreateDataBase);
                insertarconex.SqlInsert(CreateDataBase, "ProgressBarCreaBd");
//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException e){
//                }
                
                //String UseDataBase = "use `"+VarGlobales.getBaseDatos()+"`;";
                String UseDataBase = "use `"+bd+"`;";
                jLbl_DescriProgre.setText("Proceso: "+UseDataBase);
                insertarconex.SqlInsert(UseDataBase, "ProgressBarCreaBd");
//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException e){
//                }
                
                lBdExist = true;
//*********************************************************************************
            }else{
                int numLineas = 0;
                
                for (int i=0; i<arrayFileUpdateStructBd.size(); i++){
                    try {
                        //arrayFileUpdateStructBd.get(i).getFilePath();
                        System.out.println(arrayFileUpdateStructBd.get(i).getFilePath());
                        File carpeta = new File("C:/EstableERP");
                        Archivo_SQL_Temp = new File(carpeta.getAbsolutePath()+"\\"+arrayFileUpdateStructBd.get(i).getFilePath());
                        
                        pr = new BufferedReader(new FileReader(Archivo_SQL_Temp));
                        
                        while((Read2 = pr.readLine()) != null){
                            numLineas++;
                        }
                        pr.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                jProgressBar1.setMaximum(numLineas);
            }
            
            if (!alterTable){
                readFile();
            }else{
                for (int i=0; i<arrayFileUpdateStructBd.size(); i++){
                    File carpeta = new File("C:/EstableERP");
                    Archivo_SQL_Temp = new File(carpeta.getAbsolutePath()+"\\"+arrayFileUpdateStructBd.get(i).getFilePath());
                    readFile();
                }
            }
//*********************************************************************************
            return null;
        }
        
        private void readFile(){
            try {
                pr = new BufferedReader(new FileReader(Archivo_SQL_Temp));

                //jAreaDetalle.setLineWrap(true);   //Ajusta las lineas al Ancho del jTextArea

//                if (alterTable){
//                    int numLineas = 0;
//                    while((Read2 = pr.readLine()) != null){
//                        numLineas++;
//                    }
//                    pr.close();
//                    
//                    jProgressBar1.setMaximum(numLineas);
//                    pr = new BufferedReader(new FileReader(Archivo_SQL_Temp));
//                }
                
                while((Read2 = pr.readLine()) != null){
                    num_crea++;
                
                    jProgressBar1.setValue(num_crea);
                    jProgressBar1.setStringPainted(true);

                    if (Read2.startsWith("drop")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println(Read2);
                        jLbl_DescriProgre.setText("Proceso: "+Read2);
                        
                        String Drop = Read2;
                        if (!alterTable){
                           insertarconex.SqlInsert(Drop, "ProgressBarCreaBd");
                        }else{
                            insertarconex.SqlInsertUpdateStruct(Drop);
                        }
                    
//                        try {
//                           Thread.sleep(200);
//                        } catch (InterruptedException e){
//                        }
                    }
                    if (Read2.startsWith("create table")==true){
                        Crea_Tabla = "";
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        Crea_Tabla = Read2+"(";
                    }
                    if (Read2.startsWith("CREATE TABLE")==true){
                        Crea_Tabla = "";
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        Crea_Tabla = Read2+"\n";
                    }
                    if (Read2.startsWith("   ")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        if (Crea_Tabla.length()>0){
                            Crea_Tabla = Crea_Tabla+Read2.trim()+"\n";
                        }
                        if (Create_Unique.length()>0){
                            Create_Unique = Create_Unique+Read2.trim();
                        }
                        if (Create_Index.length()>0){
                            Create_Index = Create_Index+Read2.trim();
                        }
                    }
                    if (Read2.endsWith("InnoDB;")==true){
                        if (Crea_Tabla.length()>0){
                            Crea_Tabla = Crea_Tabla+Read2.trim()+"\n";
                            System.out.println(Crea_Tabla);
                            jLbl_DescriProgre.setText("Proceso: "+Crea_Tabla);
                            
                            String CreaTabla = Crea_Tabla;
                            System.err.println(CreaTabla);
                            
                            if (!CreaTabla.trim().isEmpty()){
                                if (!alterTable){
                                   insertarconex.SqlInsert(CreaTabla, "ProgressBarCreaBd");
                                }else{
                                    insertarconex.SqlInsertUpdateStruct(CreaTabla);
                                }
                            }
                        
                            Crea_Tabla = "";

//                            try {
//                              Thread.sleep(200);
//                            } catch (InterruptedException e){
//                            }
                        }
                    }
                    if (Read2.endsWith("AUTO_INCREMENT=0;")==true){
                        if (Crea_Tabla.length()>0){
                            Crea_Tabla = Crea_Tabla+Read2.trim()+"\n";
                            System.out.println(Crea_Tabla);
                            jLbl_DescriProgre.setText("Proceso: "+Crea_Tabla);
                            
                            String CreaTabla = Crea_Tabla;
                            System.err.println(CreaTabla);
                            
                            if (!CreaTabla.trim().isEmpty()){
                                if (!alterTable){
                                   insertarconex.SqlInsert(CreaTabla, "ProgressBarCreaBd");
                                }else{
                                    insertarconex.SqlInsertUpdateStruct(CreaTabla);
                                }
                            }
                        
                            Crea_Tabla = "";

//                            try {
//                              Thread.sleep(200);
//                            } catch (InterruptedException e){
//                            }
                        }
                    }
                    if (Read2.startsWith(");")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        if (Crea_Tabla.length()>0){
                            Crea_Tabla = Crea_Tabla+Read2.trim();
                            System.out.println(Crea_Tabla);
                            jLbl_DescriProgre.setText("Proceso: "+Crea_Tabla);
                            
                            String CreaTabla = Crea_Tabla;
                            
                            if (!CreaTabla.trim().isEmpty()){
                                if (!alterTable){
                                    insertarconex.SqlInsert(CreaTabla, "ProgressBarCreaBd");
                                }else{
                                    insertarconex.SqlInsertUpdateStruct(CreaTabla);
                                }
                            }
                        
                            Crea_Tabla = "";

//                            try {
//                              Thread.sleep(200);
//                            } catch (InterruptedException e){
//                            }
                        }
                        if (Create_Unique.length()>0){
                            Create_Unique = Create_Unique+Read2.trim();
                            System.out.println(Create_Unique);
                            jLbl_DescriProgre.setText("Proceso: "+Create_Unique);

                            String CreaUnico = Create_Unique;
                            
                            if (!CreaUnico.trim().isEmpty()){
                                insertarconex.SqlInsert(CreaUnico, "ProgressBarCreaBd");
                            }
                            
                            Create_Unique = "";
                        
//                            try {
//                              Thread.sleep(200);
//                            } catch (InterruptedException e){
//                            }
                        }
                        if (Create_Index.length()>0){
                            Create_Index = Create_Index+Read2.trim();
                            System.out.println(Create_Index);
                            jLbl_DescriProgre.setText("Proceso: "+Create_Index);
                        
                            String CreaIndice = Create_Index;
                            
                            if (!CreaIndice.trim().isEmpty()){
                                insertarconex.SqlInsert(CreaIndice, "ProgressBarCreaBd");
                            }
                            
                            Create_Index = "";
                        
//                            try {
//                               Thread.sleep(200);
//                            } catch (InterruptedException e){
//                            }
                        }
                    }
                    if ((Read2.startsWith("alter")==true || Read2.startsWith("ALTER")==true) && Read2.endsWith(";")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println(Read2);
                        jLbl_DescriProgre.setText("Proceso: "+Read2);
                        
                        String Alter = Read2;
                        
                        if (!Alter.trim().isEmpty()){
                            insertarconex.SqlInsert(Alter, "ProgressBarCreaBd");
                        }
                    
//                        try {
//                           Thread.sleep(200);
//                        } catch (InterruptedException e){
//                        }
                    }
                    if (Read2.startsWith("create unique")==true){
                        Create_Unique = "";
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        Create_Unique = Read2+"(";
                    }
                    if (Read2.startsWith("create index")==true){
                        Create_Index = "";
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        Create_Index = Read2+"(";
                    }
                    if ((Read2.startsWith("alter")==true || Read2.startsWith("ALTER")==true) && Read2.endsWith(")")!=true){
                        Alter_Table = "";
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        Alter_Table = Read2;
                    }
                    if ((Read2.startsWith("alter")==true || Read2.startsWith("ALTER")==true) && Read2.endsWith(")")==true){
                        Alter_Table = "";
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        Alter_Table = Read2;
                    }
                    if (Read2.startsWith("      ")==true && Read2.endsWith(",")==true){
                        if (Alter_Table.length()>0){
                            Alter_Table = Alter_Table+" "+Read2.trim();
                        }
                    }else if (Read2.startsWith("      ")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        Alter_Table = Alter_Table+" "+Read2.trim();
                        System.out.println(Alter_Table);
                        jLbl_DescriProgre.setText("Proceso: "+Alter_Table);
                        
                        String AlterTabla = Alter_Table;
                        
                        if (!AlterTabla.trim().isEmpty() && !AlterTabla.trim().startsWith("(") && !AlterTabla.trim().startsWith("from dninventario") &&
                                !AlterTabla.trim().startsWith("where CASE") && !AlterTabla.trim().startsWith("group by cuenta") &&
                                !AlterTabla.startsWith(" ") && !AlterTabla.trim().startsWith("UNION") && !AlterTabla.trim().endsWith(",' ',") &&
                                !AlterTabla.trim().startsWith("order")){
                            if (!alterTable){
                                insertarconex.SqlInsert(AlterTabla, "ProgressBarCreaBd");
                            }else{
                                insertarconex.SqlInsertUpdateStruct(AlterTabla);
                            }
                        }
                    
                        Alter_Table = "";
                    
//                        try {
//                           Thread.sleep(200);
//                        } catch (InterruptedException e){
//                        }
                    }
                    if (Read2.startsWith("CREATE TRIGGER")==true){
                        Trigger = Read2+"\n";
                    }
                    if (Read2.startsWith("  DECLARE")==true){
                        Trigger = Trigger+Read2+"\n";
                    }
                    if (Read2.startsWith("  SET")==true){
                        Linea_Set++;
                        
                        if (Linea_Set==1){
                            Trigger = Trigger+"\n"+Read2+"\n";
                        }else{
                            Trigger = Trigger+Read2+"\n";
                        }
                    }
                    if (Read2.startsWith("  IF")==true){
                        Linea_If++;
                        
                        if (Linea_If==1){
                            Trigger = Trigger+"\n"+Read2+"\n";
                        }else{
                            Trigger = Trigger+Read2+"\n";
                        }
                    }
                    if (Read2.startsWith("  ELSE")==true){
                        Trigger = Trigger+Read2+"\n";
                    }
                    if (Read2.startsWith("     SET")==true){
                        Trigger = Trigger+Read2+"\n";
                    }                    
                    if (Read2.startsWith("  END IF;")==true){
                        Trigger = Trigger+Read2+"\n";
                    }    
                    if (Read2.startsWith("END;;")==true){
                        Trigger = Trigger+Read2;
                        System.out.println(Trigger);
                        jLbl_DescriProgre.setText("Proceso: "+Trigger);
                        
                        String CreaTriger = Trigger;
                        
                        if (!CreaTriger.trim().isEmpty()){
                            insertarconex.SqlInsert(CreaTriger, "ProgressBarCreaBd");
                        }
                    
                        Trigger = ""; Linea_Set=0; Linea_If=0;
                    
//                        try {
//                            Thread.sleep(200);
//                        } catch (InterruptedException e){
//                        }
                    }
                    if (Read2.startsWith("DROP PROCEDURE")==true){
                        insertarconex.SqlInsert(Read2, "ProgressBarCreaBd");
                    }
                    //if (Read2.startsWith("DELIMITER //")==true){
                    if (Read2.startsWith("CREATE DEFINER")==true){
                        stored_procedure = Read2+"\n";
                        lineaInicioSp=num_crea;
                        storedProcedure = true;
                    }
                    //if (Read2.startsWith("DELIMITER ;")==true){
                    if (Read2.startsWith("END//")==true){
                        Read2 = Read2.replace("//", ";;");
                        stored_procedure = stored_procedure+Read2;
                        System.out.println(stored_procedure);
                        jLbl_DescriProgre.setText("Proceso: "+stored_procedure);
                        
                        String sP = stored_procedure;
                        
                        if (!sP.trim().isEmpty()){
                            insertarconex.SqlInsert(sP, "ProgressBarCreaBd");
                        }
                        //insertarconex.SqlInsertUpdateStruct(sP);
                    
                        sP = ""; stored_procedure=""; lineaInicioSp=0; lineaFinSp=0;
                        storedProcedure=false;
                    
//                        try {
//                            Thread.sleep(200);
//                        } catch (InterruptedException e){
//                        }
                    }
                    if (storedProcedure){
                        if (!stored_procedure.trim().equals(Read2)){
                            stored_procedure = stored_procedure+Read2+"\n";
                        }
                    }
                    if (Read2.startsWith("INSERT INTO")==true && Read2.endsWith(";")==true){
                        Insert_into = Read2.trim();
                        System.out.println(Insert_into);
                        jLbl_DescriProgre.setText("Proceso: "+Insert_into);
                        
                        String InsertInto = Insert_into;
                        
                        if (!InsertInto.trim().isEmpty()){
                            if (alterTable){
                                insertarconex.SqlInsertUpdateStruct(InsertInto);
                            }
                        }
                    
                        Insert_into = "";
                    }
                    if (Read2.startsWith("UPDATE")==true && Read2.endsWith(";")==true){
                        Update_Reg = Read2.trim();
                        System.out.println(Update_Reg);
                        jLbl_DescriProgre.setText("Proceso: "+Update_Reg);
                        
                        String UpdateReg = Update_Reg;
                        
                        if (!UpdateReg.trim().isEmpty()){
                            if (alterTable){
                                insertarconex.SqlInsertUpdateStruct(UpdateReg);
                            }
                        }
                    
                        Update_Reg="";
                    }
                    if (Read2.startsWith("SET")==true && Read2.endsWith(";")==true){
                        set = Read2.trim();
                        System.out.println(set);
                        jLbl_DescriProgre.setText("Proceso: "+set);
                        
                        String setQuery = set;
                        
                        if (!setQuery.trim().isEmpty()){
                            if (alterTable){
                                insertarconex.SqlInsertUpdateStructExecute(setQuery);
                            }
                        }
                    
                        set="";
                    }
                    if (Read2.startsWith("RENAME TABLE")==true && Read2.endsWith(";")==true){
                        rename_Table = Read2.trim();
                        System.out.println(rename_Table);
                        jLbl_DescriProgre.setText("Proceso: "+rename_Table);
                        
                        String renameTable = rename_Table;
                        
                        if (!renameTable.trim().isEmpty()){
                            if (alterTable){
                                insertarconex.SqlInsertUpdateStruct(renameTable);
                            }
                        }
                    
                        rename_Table="";
                    }
                    if (Read2.startsWith("TRUNCATE")==true && Read2.endsWith(";")==true){
                        truncate = Read2.trim();
                        System.out.println(truncate);
                        jLbl_DescriProgre.setText("Proceso: "+truncate);
                        
                        String renameTable = truncate;
                        
                        if (!renameTable.trim().isEmpty()){
                            insertarconex.SqlInsertUpdateStruct(truncate);
                        }
                    
                        truncate="";
                    }
                    
                    //Para Desplázar hasta el final del JTextArea
                }

                jLbl_DescriProgre.setText("Proceso: Finalizado...!!");
                
                if (!alterTable){
                    Cargado_Datos_Iniciales();
                }

                System.out.println("ProgressBar 1: "+num_crea);

                //Cierro y elimino el archivo desencriptado temporar
                pr.close();
                if (!alterTable){
                    if (VarGlobales.islBaseDatosConfiguracion()){
                        bd = VarGlobales.getBaseDatosConfiguracion();
                    }else{
                        bd = VarGlobales.getBaseDatos();
                    }
                    
                    if (!bd.equals("adminconfigestableerp")){
                        Archivo_SQL_Temp.delete();
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error en la ejecucion", "Error SQL 1", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error en la ejecucion", "Error SQL 2", JOptionPane.INFORMATION_MESSAGE);
            }
        }
 
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            setCursor(null); //turn off the wait cursor
            jLbl_DescriProgre.setText("Proceso: Finalizado...!!");
            lCreaBd=false; lBdExist = true;
            
            if (VarGlobales.islBaseDatosConfiguracion()){
                bd = VarGlobales.getBaseDatosConfiguracion();
            }else{
                bd = VarGlobales.getBaseDatos();
            }
            
            if (!alterTable){
                JOptionPane.showMessageDialog(null, "El proceso de Creacion de la Base de datos: ''"+bd+"'' finalizo correctamente", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "El proceso de Actualización de la estrutura de la Base de datos: ''"+bd+"'' finalizo correctamente", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            }
            dispose();

            if (!alterTable){
                if (bd.equals("adminconfigestableerp")){
                    if (!VarGlobales.islReconfiguraBaseDatos()){
                        ConfigurarConexion prueba_metodo = new ConfigurarConexion();
                        Tab=0;
                        clave=VarGlobales.getPasswServer();
                        
                        try {
                            prueba_metodo.action_bt_save(true);
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }else{
                try {
                    SQLQuerys insertar = new SQLQuerys();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date fch = new Date();
                    String fecha = sdf.format(fch);

                    for (int i=0; i<arrayFileUpdateStructBd.size(); i++){
                        System.out.println(arrayFileUpdateStructBd.get(i).getFilePath());
                        insertar.SqlInsert("INSERT INTO script_alter_table (nameFile, fechaExucute, execute) "+
                                           "VALUES ('"+arrayFileUpdateStructBd.get(i).getFilePath()+"', '"+fecha+"', '1');");
                    }
                    
                    VarGlobales.setlDetenerEjecucion(false);
                    //Login login = new Vista.Login();
                    //login.acion_login();
                    new Vista.Login().setVisible(true);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (VarGlobales.getErrorCreaBd().size()>0){
                VarGlobales.creaTxt("errorCreaBd.log");
                        
                JOptionPane.showMessageDialog(null, "Se creo un archivo log con varios errores en el proceso de creacion de la base de datos\n"+
                                                    "en la siguiente direccion: \n\n"+VarGlobales.getArchivoLog().toString(), "Notificación", JOptionPane.INFORMATION_MESSAGE);
                        
                try {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+VarGlobales.getArchivoLog().toString());
                } catch (IOException ex) {
                    Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    class TaskPostGreSQL extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
            File carpeta = new File(System.getProperty("user.dir")+"\\Configuracion");
            File Archivo_SQL = new File(carpeta.getAbsolutePath()+"\\BD-PostGreSQL.sql");
            File Archivo_SQL_Temp = new File(carpeta.getAbsolutePath()+"\\"+"temp");
            
            String ScriptSQL="";
            try { 
                ScriptSQL = Encrip_Desencrip.readEncrypted(Archivo_SQL, 12345);
            
                //Creo el archivo desencriptado temporal
                if (Archivo_SQL_Temp.createNewFile()){    
                    FileWriter Arc = new FileWriter(Archivo_SQL_Temp,true);
                    BufferedWriter escribir = new BufferedWriter(Arc);

                    escribir.write(ScriptSQL);

                    escribir.close();
                }           
            } catch (IOException ioe) {
            }
//*********************************************************************************
            
            BufferedReader pr;
            try {
                pr = new BufferedReader(new FileReader(Archivo_SQL_Temp));
            
                String Read2="", Crea_Tabla="", Create_Unique="", Create_Index = "", Alter_Table = "";
                String AreaDetalle=""; String Trigger=""; String comment=""; String Funcion="";
                int Linea_Declare=0; int Linea_Set=0; int Linea_If=0;

                SQLQuerys insertarconex = new SQLQuerys();
            
                String DropDataBase = "drop database if exists "+VarGlobales.getBaseDatos()+";";
                jLbl_DescriProgre.setText("Proceso: "+DropDataBase);
                insertarconex.SqlInsert(DropDataBase);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e){
                }

                String CreateDataBase = "create database "+VarGlobales.getBaseDatos()+" with encoding='UTF8' owner="+VarGlobales.getIdUser()+";";
                jLbl_DescriProgre.setText("Proceso: "+CreateDataBase);
                insertarconex.SqlInsert(CreateDataBase);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e){
                }
                
                lBdExist = true;
                //jAreaDetalle.setLineWrap(true);   //Ajusta las lineas al Ancho del jTextArea

                while((Read2 = pr.readLine()) != null){
                    num_crea++;
                
                    jProgressBar1.setValue(num_crea);
                    jProgressBar1.setStringPainted(true);

                    if (Read2.startsWith("drop")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println(Read2);
                        jLbl_DescriProgre.setText("Proceso: "+Read2);
                        
                        String Drop = Read2;
                        insertarconex.SqlInsert(Drop);
                    
                        try {
                           Thread.sleep(200);
                        } catch (InterruptedException e){
                        }
                    }
                    if (Read2.startsWith("create table")==true || Read2.startsWith("CREATE TABLE")==true){
                        Crea_Tabla = "";
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        //Crea_Tabla = Read2+"(";
                        Crea_Tabla = Read2;
                    }
                    if (Read2.startsWith("   ")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        if (Crea_Tabla.length()>0){
                            Crea_Tabla = Crea_Tabla+Read2.trim();
                        }
                        if (Create_Unique.length()>0){
                            Create_Unique = Create_Unique+Read2.trim();
                        }
                        if (Create_Index.length()>0){
                            Create_Index = Create_Index+Read2.trim();
                        }
                    }
                    if (Read2.startsWith(");")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        if (Crea_Tabla.length()>0){
                            Crea_Tabla = Crea_Tabla+Read2.trim();
                            System.out.println(Crea_Tabla);
                            jLbl_DescriProgre.setText("Proceso: "+Crea_Tabla);
                            
                            String CreaTabla = Crea_Tabla;
                            insertarconex.SqlInsert(CreaTabla);
                        
                            Crea_Tabla = "";

                            try {
                              Thread.sleep(200);
                            } catch (InterruptedException e){
                            }
                        }
                    }
                    if (Read2.startsWith("comment")==true){                        
                        System.out.println(Read2);
                        jLbl_DescriProgre.setText("Proceso: "+Read2);
                        
                        String Comment = Read2;
                        insertarconex.SqlInsert(Comment);
                    
                        try {
                           Thread.sleep(200);
                        } catch (InterruptedException e){
                        }
                    }
                    if (Read2.startsWith("alter")==true && Read2.endsWith(";")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println(Read2);
                        jLbl_DescriProgre.setText("Proceso: "+Read2);
                        
                        String Alter = Read2;
                        insertarconex.SqlInsert(Alter);
                    
                        try {
                           Thread.sleep(200);
                        } catch (InterruptedException e){
                        }
                    }
                    if (Read2.startsWith("create unique")==true){
                        System.out.println(Read2);
                        jLbl_DescriProgre.setText("Proceso: "+Read2);
                        
                        String Unique = Read2;
                        insertarconex.SqlInsert(Unique);
                    
                        try {
                           Thread.sleep(200);
                        } catch (InterruptedException e){
                        }
                    }
                    if (Read2.startsWith("create  index")==true){
                        System.out.println(Read2);
                        jLbl_DescriProgre.setText("Proceso: "+Read2);
                        
                        String Create_Indice = Read2;
                        insertarconex.SqlInsert(Create_Indice);
                    
                        try {
                           Thread.sleep(200);
                        } catch (InterruptedException e){
                        }
                    }
//*************************** Creacion de Funciones ****************************
                    if (Read2.startsWith("create or replace function")==true){
                        Funcion = Read2+"\n";
                    }
                    if (Read2.startsWith("  DECLARE")==true){
                        Funcion = Funcion+Read2;
                    }
                    if (Read2.startsWith("       ")==true && Read2.endsWith(";")==true){
                        Linea_Set++;
                        
                        if (Linea_Set==1){
                            Funcion = Funcion+"\n"+Read2+"\n";
                        }else{
                            if (Read2.startsWith("       RETURN NEW;")==true){
                                Funcion = Funcion+"\n"+Read2+"\n";
                            }else{
                                Funcion = Funcion+Read2+"\n";
                            }
                        }
                    }
                    if (Read2.startsWith("  BEGIN")==true){
                        Funcion = Funcion+Read2+"\n";
                    }
                    if (Read2.startsWith("       IF")==true){
                        Funcion = Funcion+"\n"+Read2+"\n";
                    }
                    if (Read2.startsWith("       ELSE")==true){
                        Funcion = Funcion+Read2+"\n";
                    }
                    if (Read2.startsWith("  END IF;")==true){
                        Funcion = Funcion+Read2+"\n";
                    }    
                    if (Read2.startsWith("  END;")==true){
                        Funcion = Funcion+Read2+"\n";
                    }
                    if (Read2.startsWith("$")==true){
                        Funcion = Funcion+Read2;
                        System.out.println(Funcion);
                        jLbl_DescriProgre.setText("Proceso: "+Funcion);
                        
                        String CreaFuncion = Funcion;
                        insertarconex.SqlInsert(CreaFuncion);
                    
                        Funcion = ""; Linea_Set=0; Linea_If=0;
                    
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e){
                        }
                    }
//******************************************************************************
//**************************** Creacion de Trigger *****************************
                    if (Read2.startsWith("CREATE TRIGGER")==true){
                        Trigger = Read2+"\n";
                    }
                    if (Read2.startsWith("    ON")==true){
                        Trigger = Trigger+Read2+"\n";
                    }
                    if (Read2.startsWith("    EXECUTE PROCEDURE")==true){
                        Trigger = Trigger+Read2;
                        System.out.println(Trigger);
                        jLbl_DescriProgre.setText("Proceso: "+Trigger);
                        
                        String CreaTriger = Trigger;
                        insertarconex.SqlInsert(CreaTriger);
                    
                        Trigger = "";
                    
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e){
                        }
                    }                                        
//******************************************************************************
                }
                
                jLbl_DescriProgre.setText("Proceso: Finalizado...!!");

                Cargado_Datos_Iniciales();

                System.out.println("ProgressBar 1: "+num_crea);

                //Cierro y elimino el archivo desencriptado temporar
                pr.close();
                
                if (VarGlobales.islBaseDatosConfiguracion()){
                    bd = VarGlobales.getBaseDatosConfiguracion();
                }else{
                    bd = VarGlobales.getBaseDatos();
                }
                
                if (!bd.equals("adminconfigestableerp")){
                    Archivo_SQL_Temp.delete();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error en la ejecucion", "Error SQL 1", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error en la ejecucion", "Error SQL 2", JOptionPane.INFORMATION_MESSAGE);
            }
                
            return null;    
        }
        
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            setCursor(null); //turn off the wait cursor
            jLbl_DescriProgre.setText("Proceso: Finalizado...!!");
            lCreaBd=false; lBdExist = true;
            
            JOptionPane.showMessageDialog(null, "El proceso de Creacion de la Base de datos: "+VarGlobales.getBaseDatos()+" finalizo correctamente", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            dispose();

            ConfigurarConexion prueba_metodo = new ConfigurarConexion();
            Tab=1;
            clave=VarGlobales.getPasswServer();
            try {
                prueba_metodo.action_bt_save(true);
            } catch (MalformedURLException ex) {
                Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            jProgressBar1.setValue(progress);
            jProgressBar1.setStringPainted(true);
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
        jProgressBar1 = new javax.swing.JProgressBar();
        jLbl_DescriProgre = new javax.swing.JLabel();
        jProgressBar2 = new javax.swing.JProgressBar();
        jLbl_DescriProgre1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jProgressBar1.setForeground(new java.awt.Color(105, 105, 105));
        jProgressBar1.setMaximum(983);
        jProgressBar1.setAutoscrolls(true);

        jLbl_DescriProgre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLbl_DescriProgre.setText("jLabel1");

        jProgressBar2.setForeground(new java.awt.Color(105, 105, 105));
        jProgressBar2.setMaximum(983);
        jProgressBar2.setAutoscrolls(true);

        jLbl_DescriProgre1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLbl_DescriProgre1.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLbl_DescriProgre1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLbl_DescriProgre, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLbl_DescriProgre)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLbl_DescriProgre1)
                    .addGap(205, 205, 205)))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(ProgressBarCreaBd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProgressBarCreaBd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProgressBarCreaBd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProgressBarCreaBd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProgressBarCreaBd().setVisible(true);
            }
        });
    }
    
    private void Cargado_Datos_Iniciales(){
        File carpeta, Archivo_SQL, Archivo_SQL_Temp;
        
        if (VarGlobales.islBaseDatosConfiguracion()){
            bd = VarGlobales.getBaseDatosConfiguracion();
        }else{
            bd = VarGlobales.getBaseDatos();
        }
        
        if (bd.equals("adminconfigestableerp")){
            carpeta = new File(System.getProperty("user.dir")+"\\Configuracion");
            Archivo_SQL_Temp = new File(carpeta.getAbsolutePath()+"\\adminconfigestableerp_datos_iniciales.sql");
        }else{
            carpeta = new File(System.getProperty("user.dir")+"\\Configuracion");
            Archivo_SQL = new File(carpeta.getAbsolutePath()+"\\Datos_Iniciales.sql");
            Archivo_SQL_Temp = new File(carpeta.getAbsolutePath()+"\\"+"temp2");
            
            String ScriptSQL="";
            try { 
                ScriptSQL = Encrip_Desencrip.readEncrypted(Archivo_SQL, 12345);
           
                //Creo el archivo desencriptado temporal
                if (Archivo_SQL_Temp.createNewFile()){    
                    FileWriter Arc = new FileWriter(Archivo_SQL_Temp,true);
                    BufferedWriter escribir = new BufferedWriter(Arc);

                    escribir.write(ScriptSQL);

                    escribir.close();
                }           
            } catch (IOException ioe) {
            }
        }
//*********************************************************************************
            
        BufferedReader pr;
        try {
            pr = new BufferedReader(new FileReader(Archivo_SQL_Temp));
            
            String Read2=""; num_insert=0; 
            String insert_tabla = "", update_tabla = "";
            
            SQLQuerys insertarconex = new SQLQuerys();
            
            while((Read2 = pr.readLine()) != null){
                num_insert++;
                
                jProgressBar2.setValue(num_insert);
                jProgressBar2.setStringPainted(true);

                if (Read2.startsWith("INSERT INTO")==true || Read2.startsWith("insert into")==true){
                    insert_tabla = "";
                    //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                    insert_tabla = Read2+"\n";
                }
                if (Read2.startsWith("   ")==true){
                    //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                    if (insert_tabla.length()>0){
                        insert_tabla = insert_tabla+Read2.trim()+"\n";
                    }
                
                    if (Read2.endsWith(");")==true){
                        //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                        if (insert_tabla.length()>0){
                            //insert_tabla = insert_tabla+Read2.trim();
                            System.out.println(insert_tabla);
                            jLbl_DescriProgre1.setText("Registros Iniciales: "+Read2);

                            String insertTabla = insert_tabla;
                            //insertarconex.SqlInsert(insertTabla, "ProgressBarCreaBd");
                            insertarconex.SqlInsert(insertTabla);

                            insertTabla = "";
                        }
                    }
                }
                if (Read2.startsWith("UPDATE")==true || Read2.startsWith("update")==true){
                    update_tabla = "";
                    //JOptionPane.showMessageDialog(null, Read2, "Linea SQL", JOptionPane.INFORMATION_MESSAGE);
                    update_tabla = Read2;
                    
                    insertarconex.SqlUpdate(update_tabla);
                }
//                if (Read2.startsWith("INSERT INTO")==true){
//                    System.out.println(Read2);
//                    jLbl_DescriProgre1.setText("Registros Iniciales: "+Read2);
//                        
//                    String InsertReg = Read2;
//                    insertarconex.SqlInsert(InsertReg);
//                    
////                    try {
////                       Thread.sleep(50);
////                    } catch (InterruptedException e){
////                    }
//                }
            }
            
            SQLQuerys actualizar = new SQLQuerys();
            
            if (bd.equals("adminconfigestableerp")){
                actualizar.SqlUpdate("UPDATE DNEMPRESAS SET EMP_USER='1',EMP_MACPC='"+VarGlobales.getMacPc()+"';");
                actualizar.SqlUpdate("UPDATE DNPAIS SET PAI_USER='1',PAI_MACPC='"+VarGlobales.getMacPc()+"';");
                actualizar.SqlUpdate("UPDATE DNESTADOS SET EST_USER='1',EST_MACPC='"+VarGlobales.getMacPc()+"';");
                actualizar.SqlUpdate("UPDATE DNMUNICIPIOS SET MUN_USER='1',MUN_MACPC='"+VarGlobales.getMacPc()+"';");
                actualizar.SqlUpdate("UPDATE DNPARROQUIAS SET PAR_USER='1',PAR_MACPC='"+VarGlobales.getMacPc()+"';");
            }else{
                actualizar.SqlUpdate("UPDATE DNCONDIPAGO SET CDI_USER='1',CDI_MACPC='"+VarGlobales.getMacPc()+"';");
                actualizar.SqlUpdate("UPDATE DNDOCUMENTOS SET DOC_USER='1',DOC_MACPC='"+VarGlobales.getMacPc()+"';");
                actualizar.SqlUpdate("UPDATE DNLISTPRE SET LIS_USER='1',LIS_MACPC='"+VarGlobales.getMacPc()+"';");
                actualizar.SqlUpdate("UPDATE DNMONEDA SET MON_USER='1',MON_MACPC='"+VarGlobales.getMacPc()+"';");
                actualizar.SqlUpdate("UPDATE DNTIPIVA SET TIVA_USER='1',TIVA_MACPC='"+VarGlobales.getMacPc()+"';");
                actualizar.SqlUpdate("UPDATE DNTIPOMAESTRO SET TMA_USER='1',TMA_MACPC='"+VarGlobales.getMacPc()+"';");
                actualizar.SqlUpdate("UPDATE DNUNDMEDIDA SET MED_USER='1',MED_MACPC='"+VarGlobales.getMacPc()+"';");
            }
            
            if (!VarGlobales.getCodEmpresa().equals("000001")){
                actualizar.SqlUpdate("UPDATE dndocumentos SET doc_empresa='"+VarGlobales.getCodEmpresa()+"';");
                actualizar.SqlUpdate("UPDATE dnpermiso_grupal SET per_empresa='"+VarGlobales.getCodEmpresa()+"';");
                
                actualizar.SqlDelete("DELETE FROM dnusuarios", "", "");
                actualizar.SqlDelete("DELETE FROM dntipiva", "", "");
        
                //actualizar.SqlUpdate("UPDATE dntipiva SET tiva_empresa='"+VarGlobales.getCodEmpresa()+"';");
                actualizar.SqlUpdate("UPDATE dntipomaestro SET tma_empresa='"+VarGlobales.getCodEmpresa()+"';");
                actualizar.SqlUpdate("UPDATE dnundmedida SET med_empresa='"+VarGlobales.getCodEmpresa()+"';");
                actualizar.SqlUpdate("UPDATE grupo_asientos_contables SET cod_empresa='"+VarGlobales.getCodEmpresa()+"';");
                actualizar.SqlUpdate("UPDATE relac_usuario_grupo_permisos SET emp_codigo='"+VarGlobales.getCodEmpresa()+"';");
                actualizar.SqlUpdate("UPDATE dncondipago SET cdi_empresa='"+VarGlobales.getCodEmpresa()+"';");
                actualizar.SqlUpdate("UPDATE dninstrumentopago SET ins_empresa='"+VarGlobales.getCodEmpresa()+"';");
                actualizar.SqlUpdate("UPDATE dntipcontacto SET tcon_empresa='"+VarGlobales.getCodEmpresa()+"';");
                actualizar.SqlUpdate("UPDATE dntipcontacto SET tcon_empresa='"+VarGlobales.getCodEmpresa()+"';");
            }
            
            jLbl_DescriProgre1.setText("Registros Iniciales: Cargados Correctamente...!!");
            
            System.out.println("ProgressBar 2: "+num_insert);

            //Cierro y elimino el archivo desencriptado temporar
            pr.close();
            if (!bd.equals("adminconfigestableerp")){
                Archivo_SQL_Temp.delete();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProgressBarCreaBd.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLbl_DescriProgre;
    private javax.swing.JLabel jLbl_DescriProgre1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    // End of variables declaration//GEN-END:variables
}