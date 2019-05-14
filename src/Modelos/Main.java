package Modelos;

import java.util.Properties;
//import org.jvnet.substance.SubstanceLookAndFeel;
import javax.swing.*;
import util.Internacionalizacion;

public class Main {
    //public static String idioma="";

    public static void main(String[] args) {
        VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
        Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
	}

        System.out.println("Nombre del Sistema Operativo: "+System.getProperty("os.name"));
        System.out.println("Version del Sistema Operativo: "+System.getProperty("os.version"));
        Properties p = System.getProperties();
        System.out.println("Idioma del Sistema Operativo: "+p.get("user.language"));
        System.out.println("Pais del Sistema Operativo: "+p.get("user.country"));
        
        if (p.get("user.language").equals("es")){
            //idioma="Español ("+p.get("user.language")+"_"+p.get("user.country")+")";
            //idioma="English (UK)";
        }
        System.out.println("Idioma a cargar: "+"Español ("+p.get("user.language")+"_"+p.get("user.country")+")");
        
        System.out.println("Version de Java #: "+System.getProperty("java.version"));
        System.out.println("Directorio de Instalacion de Java: "+System.getProperty("java.home"));
        System.out.println("Nombre del Distribuidor de Java: "+System.getProperty("java.vendor"));
        System.out.println("Pagina Web del Distribuidor de Java: "+System.getProperty("java.vendor.url"));
        
        VarGlobales.setIdioma("es");
	idioma.setLocale("es");
        
        new ProgressBar().animar();   
         //SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.BusinessBlackSteelSkin");  
    }   
}