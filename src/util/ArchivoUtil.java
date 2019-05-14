package util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author omoreno
 */
public class ArchivoUtil {
   
    public static void abrirPDF(File file) throws IOException {
        if (Desktop.isDesktopSupported()) {
         //       File myFile = new File(path);
                Desktop.getDesktop().open(file);
        }
    }
   
    public static int abrirPDFs(File[] files) throws IOException {
        if (Desktop.isDesktopSupported()) {
            for(int i = 0; i < files.length; i ++) {
                Desktop.getDesktop().open(files[i]);
            }
            return 0;
        }else return -1;//no soportado
    }
  
    public static File exportarPDF (JasperPrint print, String nombreParaArchivo) {
        File pdf = null;
        try {
            pdf = File.createTempFile(nombreParaArchivo, ".pdf");
//            pdf.deleteOnExit();
            //pdf = new File( nombreParaArchivo + ".pdf");
            FileOutputStream fos = new FileOutputStream(pdf);
            JasperExportManager.exportReportToPdfStream(print, fos);
            fos.close();
        } catch (JRException | IOException ex) {
            Logger.getLogger(ArchivoUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pdf;
    }
}