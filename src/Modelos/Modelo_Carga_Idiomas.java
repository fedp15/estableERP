package Modelos;

import java.io.File;
import java.util.Vector;
import util.Internacionalizacion;

public class Modelo_Carga_Idiomas {
    private final File carpeta = new File(System.getProperty("user.dir")+"\\"+"Localizaciones");
    private final File xmlFile = new File(carpeta.getAbsolutePath()+"\\"+"Idiomas_Trl.xml");
    private Vector formidioma, msgidioma, tablaidioma;
    private Vector listaidiomas = new Vector();
    private String Formulario, Componete, Idioma;
    
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    
    public void setFormulario(String form){
        Formulario = form;
    }
    
    public void setComponente(String componen){
        Componete = componen;
    }
    
    public void setIdioma(String idioma){
        Idioma = idioma;
    }
    
    public Vector getListaIdioma(){
        idioma.setLocale(VarGlobales.getIdioma());

        //ReadFileXml xml = new ReadFileXml();
        //listaidiomas = xml.ReadFileXml(xmlFile, "Combos", "row");
        
        return listaidiomas;
    }
}