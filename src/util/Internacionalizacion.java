package util;

import java.util.Locale;
import java.util.ResourceBundle;

public class Internacionalizacion {
	private static Internacionalizacion internacionalizacion;
	private ResourceBundle bundle;
	private Locale locale;
	
	private Internacionalizacion() {
		
	}
	
	public static Internacionalizacion geInternacionalizacion(){
        if (internacionalizacion == null){
        	internacionalizacion = new Internacionalizacion();
        }

        return internacionalizacion;
    }
	
	public void setLocale(String lang) {
		this.locale = new Locale(lang);
	}
	
	public ResourceBundle loadLangMsg() {
		bundle = ResourceBundle.getBundle("idiomas.msg", locale);
		
		return bundle;
	}
        
        public ResourceBundle loadLangHeaderTable() {
		bundle = ResourceBundle.getBundle("idiomas.headerTable", locale);
		
		return bundle;
	}
        
        public ResourceBundle loadLangComponent() {            
            bundle = ResourceBundle.getBundle("idiomas.component", locale);
		
            return bundle;
	}
        
        public ResourceBundle loadLangIdiomasSist() {
		bundle = ResourceBundle.getBundle("idiomas.Idiomas", locale);
		
		return bundle;
	}
        
        public ResourceBundle loadLangPanelAdmin() {
		bundle = ResourceBundle.getBundle("idiomas.panelAdmi", locale);
		
		return bundle;
	}
        
        public ResourceBundle loadLangIdiomasMenu() {
		bundle = ResourceBundle.getBundle("idiomas.IdiomasMenu", locale);
		
		return bundle;
	}
}
