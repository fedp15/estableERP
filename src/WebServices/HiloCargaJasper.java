package WebServices;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.io.File;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class HiloCargaJasper extends Thread{
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private String codCategoria = "";
    private conexion conex = new conexion();
    
    public HiloCargaJasper(String codCategoria) {
        this.codCategoria = codCategoria;
    }
    
    @Override
    public void run()  //METODO RUN PARA EL HILO
    {  
        try{
            File jasper = new File(System.getProperty("user.dir")+"/build/classes/Reportes/ReportBlanco.jasper");
            
            JasperReport reporteVta;  
            reporteVta = (JasperReport) JRLoader.loadObject(jasper);
            JasperPrint jasperprinter = JasperFillManager.fillReport(reporteVta, null);  

//            JRExporter exporter = new JRPdfExporter();  
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
//            exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new java.io.File("reporte2PDF.pdf")); 
//            exporter.exportReport(); 
            
            JasperViewer vista = new JasperViewer(jasperprinter, false);
            //vista.setExtendedState(MAXIMIZED_BOTH);
            vista.setVisible(false);
        }catch(Exception e){

        }
    }
}
