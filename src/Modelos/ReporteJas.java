package Modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Kelvin Marcano
 */
public class ReporteJas {
        public static final String DRIVER="com.mysql.jdbc.Driver";
        public static final String RUTA="jdbc:mysql://127.0.0.1:3307/omnix_db";
        public static final String USER="root";
        public static final String PASSWORD="";
	public static Connection CONEXION;
        
    public void startReportProve(){
        try{
            Class.forName(DRIVER);
            CONEXION = DriverManager.getConnection(RUTA,USER,PASSWORD);
            javax.swing.JOptionPane.showMessageDialog(null,"´Procesando Reporte......");
            
            String template="\\informes\\Factura.jasper";
            JasperReport reporte=null;
            reporte=(JasperReport) JRLoader.loadObjectFromLocation(template);
           
            Map param=new HashMap();
            
            
            
            JasperPrint jasperprint= JasperFillManager.fillReport(reporte,param,CONEXION);
            JasperViewer visor=new JasperViewer(jasperprint,false);
            visor.setTitle("Factura");
            visor.setVisible(true);
            visor.show();
            

        }catch(Exception e){
             System.out.println("hola2");
            javax.swing.JOptionPane.showMessageDialog(null, e);

        }
    }   
}