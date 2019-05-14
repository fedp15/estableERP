package Modelos;

import util.Encrip_Desencrip;
import java.awt.*;
import java.awt.SplashScreen;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import stored_procedure.SpFormEmpresas;
import util.CargaComboBox;
import util.Internacionalizacion;

public final class ProgressBar extends conexion{
    //public static String ip=null, user=null, passw=null, basedatos=null, manejador=null;
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    private final SpFormEmpresas formEmpresas = SpFormEmpresas.getFormEmpresas();
    
    public ResultSet rs, rsFile;
    public static boolean Conexion;
    final SplashScreen splash ;
    //texto que se muestra a medida que se va cargando el progressbar
    final String[] texto = {"Imagenes" ,"Configuraciones", "Librerias",
                            "Formularios","Base de Datos","Iconos","Propiedades",
                            "Conexiones",""};
    private File carpeta, archivo, archivo_temp, carpetaLicencia, archivoLicencia, archivoTempLicen;
    private File file_exe;
    private Boolean Aplicacion, lFileLicen = false;

    public ProgressBar() {
         splash = SplashScreen.getSplashScreen();
    }

    public void animar(){
        if (splash != null){
            Graphics2D g = splash.createGraphics();
            
            for(int i=1; i<texto.length; i++){
                //se pinta texto del array
                g.setColor( new Color(4,52,101));//color de fondo
	        g.fillRect(203, 328,280,12);//para tapar texto anterior
                g.setColor(Color.white);//color de texto	        
                g.drawString("Cargando "+texto[i-1]+"...", 203, 338);                
                g.setColor(Color.green);//color de barra de progeso
                g.fillRect(204, 308,(i*307/texto.length), 12);//la barra de progreso
                //se pinta una linea segmentada encima de la barra verde
                float dash1[] = {2.0f};
                BasicStroke dashed = new BasicStroke(9.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,5.0f, dash1, 0.0f);
                g.setStroke(dashed);
                g.setColor(Color.GREEN);//color de barra de progeso
                g.setColor( new Color(4,52,101));
                g.drawLine(205,314, 510, 314);                
                //se actualiza todo
                
                splash.update();
                
		try {
                    Thread.sleep(600);
		} catch(InterruptedException e) { }
            }
            
	    splash.close();
	}
        //una vez terminada la animación se muestra la aplicación principal
            
        carpeta = new File(System.getProperty("user.dir")+"\\"+"Configuracion");
        carpeta.mkdirs();
        archivo = new File(carpeta.getAbsolutePath()+"\\"+"conf_conexion.txt");
        archivo_temp = new File(carpeta.getAbsolutePath()+"\\"+"temp");
        
//        try {
            // Descargar fichero de un servidor FTP
//            Ftp.prueba();
            //Ftp.uploadFile();
            //Ftp.putFile("omnixsolutions.com",21,"android2@omnixsolutions.com","Omnixsolutions2014!",
            //            "e:\\UbicacionProductos.class","/repositorio/actualizaciones_pos/Vista/UbicacionProductos.class");
            //Ftp.downloadFileByFTP("omnixsolutions.com","android2@omnixsolutions.com","Omnixsolutions2014!","e:\\UbicacionProductos.class",
            //                      "/repositorio/actualizaciones_pos/Vista/UbicacionProductos.class");
//        } catch (IOException ex) {
//            Logger.getLogger(ProgressBar.class.getName()).log(Level.SEVERE, null, ex);
//        }

        // Subir un fichero local a un servidorFTP
        //Ftp.uploadFileByFTP("ip_ftp","usuario","password","c:\\ruta_local\\fichero_a_subir.txt",
        //                    "/ruta_ftp/fichero.txt");
//********** Codigo para Encriptar el Archivo SQL de la Base de Datos **********
//        File ArchivoSQL = new File(carpeta.getAbsolutePath()+"\\"+"BD_MySQL_Original1.sql");
//        File Archivo_SQL = new File(carpeta.getAbsolutePath()+"\\BD-MySQL.sql");
//        try {
//            Encrip_Desencrip.encrypt(ArchivoSQL, Archivo_SQL, 12345);
//        } catch (IOException ex) {
////            Logger.getLogger(ProgressBar.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        ArchivoSQL.delete();
        
//        File ArchivoSQL1 = new File(carpeta.getAbsolutePath()+"\\"+"Datos_Iniciales1.sql");
//        File Archivo_SQL1 = new File(carpeta.getAbsolutePath()+"\\Datos_Iniciales.sql");
//        try {
//            Encrip_Desencrip.encrypt(ArchivoSQL1, Archivo_SQL1, 12345);
//        } catch (IOException ex) {
//            Logger.getLogger(ProgressBar.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        ArchivoSQL1.delete();
//******************************************************************************

        String txtTexto="";
        try { 
            txtTexto = Encrip_Desencrip.readEncrypted(archivo, 12345);
            System.out.println(txtTexto);

            BufferedReader bf;
            int linea=0;
            String Read, numLicencia = "", nombreCliente = "", multiempreesa = "", numEmpresas = "", diasLicencia = "", actualiza = "",
                   fechaContrato = "", fechaDesdeContrato = "", fechaHastaContrato = "", urlLogoEmpresa = "";
            
            //Creo el archivo desencriptado temporal
            if (archivo_temp.createNewFile()){    
                FileWriter Arc = new FileWriter(archivo_temp,true);
                BufferedWriter escribir = new BufferedWriter(Arc);

                escribir.write(txtTexto);

                escribir.close();
            }
            
            //Leo el archivo desencriptado temporal
            bf = new BufferedReader(new FileReader(archivo_temp));
            Read = "";
            linea=0;
            
            while((Read = bf.readLine()) != null){
                linea++;
                
                switch (linea) {
                    case 1:
                        //                    ip = Read.trim();
                        VarGlobales.setIpServer(Read.trim());
                        break;
                    case 2:
                        //                    user = Read.trim();
                        VarGlobales.setUserServer(Read.trim());
                        break;
                    case 3:
                        //                    passw = Read.trim();
                        VarGlobales.setPasswServer(Read.trim());
                        break;
                    case 4:
                        //                    basedatos = Read.trim();
                        //VarGlobales.setBaseDatos(Read.trim());
                        VarGlobales.setlBaseDatosConfiguracion(true);
                        VarGlobales.setBaseDatosConfiguracion(Read.trim());
                        VarGlobales.setManejador("MySQL");
//JOptionPane.showMessageDialog(null, Read.trim());
                        if (!Read.trim().equals("adminconfigestableerp")){
//JOptionPane.showMessageDialog(null, "Paso por aqui");
                            JOptionPane.showMessageDialog(null, 
                                                          "<html><P align=\"center\">El sistema y la base de datos sera reconfigurado, para trabajar con la nueva modalidad de "+
                                                          "empresas separadas por bases de datos.</html>", 
                                                          "Error De Conexion", 
                                                          JOptionPane.ERROR_MESSAGE);
                            
                            String sql = "SHOW DATABASES;";

                            Vector elementos = new Vector();
                            elementos = CargaComboBox.ListaBaseDatos(sql, 
                                                                     VarGlobales.getIpServer(), 
                                                                     VarGlobales.getUserServer(), 
                                                                     VarGlobales.getPasswServer());

                            boolean lBdConfigExit = false;
                            
                            for (int i=0; i<elementos.size(); i++){
                                System.err.println((String) elementos.elementAt(i));
                                if (elementos.elementAt(i).toString().equals("adminconfigestableerp")){
                                    lBdConfigExit = true;
                                }
                            }

                            if (!lBdConfigExit){
                                JOptionPane.showMessageDialog(null, "La base de datos de configuracion del sistema no esta creada, se procedera\n"+
                                                                    "a crear ahora para poder continuar con el uso del sistema", 
                                                             "Notificación", 
                                                             JOptionPane.ERROR_MESSAGE);

                                ResultSet rsEmpresas = consultar("SELECT * FROM dnempresas WHERE emp_codigo<>'000001';");
                                
                                VarGlobales.setlBaseDatosConfiguracion(true);
                                VarGlobales.setlReconfiguraBaseDatos(true);
                                VarGlobales.setBaseDatosConfiguracion("adminconfigestableerp");

                                new Vista.ProgressBarCreaBd().setVisible(true);
                                
                                try {
                                    Thread.sleep(20000);
                                } catch (InterruptedException e){
                                }
                                
                                rsEmpresas.beforeFirst();
                                while(rsEmpresas.next()){
                                    formEmpresas.callStoreProcedureEmpresas("INSERT", rsEmpresas);
                                }
                            }
                            
                            carpeta = new File(System.getProperty("user.dir")+"\\"+"Configuracion");
                            carpeta.mkdirs();
                            archivo = new File(carpeta.getAbsolutePath()+"\\"+"conf.txt");
                            File archivo2 = new File(carpeta.getAbsolutePath()+"\\"+"conf_conexion.txt");
                            archivo2.delete();

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
                                    
                                    VarGlobales.setlBaseDatosConfiguracion(true);
                                    VarGlobales.setBaseDatosConfiguracion("adminconfigestableerp");
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
                                Logger.getLogger(ProgressBar.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                
                            //return;
                        }
                        
                        break;
                    case 5:
                        //                    manejador = Read.trim();
                        VarGlobales.setManejador(Read.trim());
                        break;
                    default:
                        break;
                }
            }
            
            getIdioma();
                
            //Cierro y elimino el archivo desencriptado temporar
            bf.close();
            archivo_temp.delete();
            //idioma="";
        
//******************************************************************************
                    String[] nombre = new String[7];
                    nombre[0] = "C:\\EstableERP\\ERP\\EstablaERP.exe";
                    nombre[1] = "";
                    nombre[2] = "";
                    nombre[3] = "";
                    nombre[4] = "";
                    nombre[5] = "";
                    nombre[6] = "";
                    int NumApp = 0;
            
                    for (int i=0; i<nombre.length; i++){
                        System.out.println(nombre[i]);
                        file_exe = new File(nombre[i]);
                        Aplicacion = file_exe.isFile();
                
                        if (Aplicacion==true){
                            NumApp++;
                            System.out.println(Aplicacion);
                    
                            switch(i){
                                case 0:
                                    VarGlobales.setPos(Aplicacion);
                                    break;
                                case 1:
                                    VarGlobales.setErp(Aplicacion);
                                    break;
                                case 2:
                                    VarGlobales.setHcm(Aplicacion);
                                    break;
                                case 3:
                                    VarGlobales.setHcs(Aplicacion);
                                    break;
                                case 4:
                                    VarGlobales.setFuerzadVentas(Aplicacion);
                                    break;
                                case 5:
                                    VarGlobales.setEcommers(Aplicacion);
                                    break;
                                case 6:
                                    VarGlobales.setGestoProyect(Aplicacion);
                                    break;
                            }
                        }
                    }
            
                    Vista.Login.Idioma="EspaÃ±ol (es_VE)";

                    new Vista.Login().setVisible(true);
//******************************************************************************
        } catch (IOException ioe) {
            getIdioma();
            
            String err = "No se ha Configurado la Conexion al Sistema";
            JOptionPane.showMessageDialog(null, err, "Error De Conexion", JOptionPane.ERROR_MESSAGE);

            new Vista.ConfigurarConexion().setVisible(true);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProgressBar.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    private void getIdioma(){
        Properties properties = System.getProperties();

        VarGlobales.setIdioma(properties.get("user.language").toString());
	idioma.setLocale(properties.get("user.language").toString());
    }
    
    private ResultSet validaArranque(Boolean lFlag){
        ResultSet rs = null;
        String sql;
        
        if(lFlag){
            sql = "SELECT EMP_CODIGO, base_datos_empresa FROM dnempresas WHERE EMP_WELCOME=1";
        }else{
            sql = "SELECT COUNT(*) AS CUANTOS FROM dnempresas WHERE EMP_WELCOME=1";
        }
        
        try {
            rs = consultar(sql);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProgressBar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
}