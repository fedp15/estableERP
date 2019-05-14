package util;

import Vista.ConfigurarConexion;
import static Vista.MenuPrincipal.escritorioERP;
import java.awt.Dimension;

public class LlamaFormParamt {
    
    public void Form(String form, String TipForm, String paramet1, String paramet2){
        if (TipForm.trim().equals("Internal")){
            if (form.trim().equals("Documentos")){
//                Documentos fact = new Documentos(paramet1, paramet2, "ERP");
//        
//                //fact = new Documentos();
//                Dimension desktopSize = escritorioERP.getSize();
//                Dimension jInternalFrameSize = fact.getSize();
//                fact.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//        
//                escritorioERP.add(fact);
//                fact.show(); 
            }
        }
        
        if (TipForm.trim().equals("External")){
            if (form.trim().equals("ConfigurarConexion")){
                boolean lDatosConex;
                
                if (paramet1.trim().equals("1")){
                    lDatosConex=true;
                }else{
                    lDatosConex=false;
                }
                
                ConfigurarConexion config = new ConfigurarConexion(lDatosConex);
                config.show();
            }            
        }
    }
}