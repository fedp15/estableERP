/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.net.URL;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 * @web http://jc-mouse.blogspot.com/
 * @author Mouse
 */
public class iReportClass {
    //se estable una conexion con la base de datos
    private conexion con = new conexion();    
     /* reporte sencillo con conexion a base de datos MySQL, 
      * el reporte no cuenta con parametros */

    public void reporte() {
        JasperReport reporte;
        JasperPrint reporte_view;
        
         try{
             //direccion del archivo JASPER
              //URL  in = this.getClass().getResource("C:\\Users\\Andres\\Documents\\NetBeansProjects\\pos\\POS_Clon\\src\\rSample.jasper");
              reporte = (JasperReport) JRLoader.loadObject( "C:\\Users\\Andres\\Documents\\NetBeansProjects\\pos\\POS_Clon\\src\\rSample.jasper" );           
              reporte_view= JasperFillManager.fillReport( reporte, new HashMap(), con.getConnection() );
              JasperViewer.viewReport( reporte_view ); 
              //terminamos la conexion a la base de datos
              con.desconectar();
	  }catch (JRException E){
	    E.printStackTrace();
          }
    }
}
