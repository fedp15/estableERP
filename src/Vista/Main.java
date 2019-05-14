package Vista;

import Modelos.ProgressBar;
import WebServices.HiloCargaJasper;
import java.util.Properties;
//import org.jvnet.substance.SubstanceLookAndFeel;
import javax.swing.*;

public class Main {
    //public static String idioma="";

    public static void main(String[] args) {
        //try {
        //    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	//} catch (Exception e) {
        //    e.printStackTrace();
	//}
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
	}

        System.out.println("Nombre del Sistema Operativo: "+System.getProperty("os.name"));
        System.out.println("Version del Sistema Operativo: "+System.getProperty("os.version"));
        Properties p = System.getProperties();
        System.out.println("Idioma del Sistema Operativo: "+p.get("user.language"));
        System.out.println("Pais del Sistema Operativo: "+p.get("user.country"));
        System.out.println("Usuario Actual de Windows: "+System.getProperty("user.name"));
        
        if (p.get("user.language").equals("es")){
            //idioma="Español ("+p.get("user.language")+"_"+p.get("user.country")+")";
            //idioma="English (UK)";
        }
        System.out.println("Idioma a cargar: "+"Español ("+p.get("user.language")+"_"+p.get("user.country")+")");
        
        System.out.println("Version de Java #: "+System.getProperty("java.version"));
        System.out.println("Directorio de Instalacion de Java: "+System.getProperty("java.home"));
        System.out.println("Nombre del Distribuidor de Java: "+System.getProperty("java.vendor"));
        System.out.println("Pagina Web del Distribuidor de Java: "+System.getProperty("java.vendor.url"));
        
        HiloCargaJasper cargaJasper = new HiloCargaJasper("");
        cargaJasper.start(); //inicia hilo1
        
        ProgressBar progressbar = new ProgressBar();
        progressbar.animar();   
         //SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.BusinessBlackSteelSkin");  
    }   
}