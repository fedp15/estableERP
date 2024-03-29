package util;

import Modelos.VariablesGlobales;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.border.Border;
 
public class ImagenFondo extends Modelos.conexion implements Border{
    public BufferedImage back;
    public static String ruta_img_fondo = "", ruta_img_logo_emp = "", ruta_img_logo_cli = "";
    private ResultSet rs_ruta;
    private conexion conet = new conexion();
    private boolean lImage_fondo=false, lFondo_Default=true;
    public static int MAX_WIDTH_FONDO=0; //Ancho máximo
    public static int MAX_HEIGHT_FONDO=0; //Alto máximo
    public static int MAX_WIDTH_LOGO_CLI=307; //Ancho máximo
    public static int MAX_HEIGHT_LOGO_CLI=102; //Alto máximo
    public static int MAX_WIDTH_LOGO_BD=119; //Ancho máximo
    public static int MAX_HEIGHT_LOGO_BD=126; //Alto máximo
    public static int MAX_WIDTH_LOGO_EMP=307; //Ancho máximo
    public static int MAX_HEIGHT_LOGO_EMP=102; //Alto máximo
    
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
 
    public ImagenFondo(String IdUser, JDesktopPane escritorio){
//******************** Seccion para la carga del Fondo de Escritorio por Usuario ********************
        int ancho_pantala = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        int alto_pantala = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        MAX_WIDTH_FONDO = ancho_pantala;
        MAX_HEIGHT_FONDO = alto_pantala;
        //MAX_WIDTH_FONDO = escritorio.getWidth();
        //MAX_HEIGHT_FONDO = alto_pantala;
//***************************************************************************************************
        
//******************** Seccion para la carga del Fondo de Escritorio por Usuario ********************
        //String sql = "SELECT IF(OPE_RUTAIMG IS NULL,'',OPE_RUTAIMG) AS OPE_RUTAIMG FROM DNUSUARIOS WHERE OPE_NUMERO="+IdUser+";";
        String sql = "SELECT OPE_RUTAIMG FROM DNUSUARIOS WHERE OPE_NUMERO="+IdUser+";";
        System.out.println(sql);
        
        try {
            rs_ruta = consultar(sql);
            try {
                if(rs_ruta.getRow()>0){
                    ruta_img_fondo = rs_ruta.getString("OPE_RUTAIMG");
                    lImage_fondo=true;

                    if (ruta_img_fondo==null || ruta_img_fondo.trim().isEmpty()){
                        ruta_img_fondo = System.getProperty("user.dir")+"/build/classes/imagenes/omnix_escritorio.png";
                        
                        String Arch_Foto = ruta_img_fondo;
                        File arch_foto = new File(Arch_Foto);
                        
                        if (arch_foto.exists()){
                        }else{
                            lFondo_Default=false;
                        }
                    }else{
                        String Arch_Foto = ruta_img_fondo;
                        File arch_foto = new File(Arch_Foto);
                        
                        if (arch_foto.exists()){
                        }else{
                            ruta_img_fondo = System.getProperty("user.dir")+"/build/classes/imagenes/omnix_escritorio.png";
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ImagenFondo.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImagenFondo.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            File fondo = new File (ruta_img_fondo);
            back = ImageIO.read(fondo);
   
            if(back.getHeight()>back.getWidth()){
                int heigt = (back.getHeight() * MAX_WIDTH_FONDO) / back.getWidth();
                back = resize(back, MAX_WIDTH_FONDO, heigt);
                int width = (back.getWidth() * MAX_HEIGHT_FONDO) / back.getHeight();
                back = resize(back, width, MAX_HEIGHT_FONDO);
            }else{
                int width = (back.getWidth() * MAX_HEIGHT_FONDO) / back.getHeight();
                back = resize(back, width, MAX_HEIGHT_FONDO);
                int heigt = (back.getHeight() * MAX_WIDTH_FONDO) / back.getWidth();
                back = resize(back, MAX_WIDTH_FONDO, heigt);
            }
        } catch (Exception ex) {            
        }
//**************************************************************************************
    }
    
    public ImagenFondo(JDesktopPane escritorio){
//******************** Seccion para la carga del Fondo de Escritorio por Usuario ********************
        int ancho_pantala = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        int alto_pantala = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        MAX_WIDTH_FONDO = ancho_pantala;
        MAX_HEIGHT_FONDO = alto_pantala;
//***************************************************************************************************
        
//******************** Seccion para la carga del Fondo de Escritorio por Usuario ********************        
        ruta_img_fondo = System.getProperty("user.dir")+"/build/classes/imagenes/fondo_login.jpg";
                        
        String Arch_Foto = ruta_img_fondo;
        File arch_foto = new File(Arch_Foto);
                        
        if (arch_foto.exists()){
        }else{
            lFondo_Default=false;
        }

        try {
            File fondo = new File (ruta_img_fondo);
            back = ImageIO.read(fondo);
   
            if(back.getHeight()>back.getWidth()){
                int heigt = (back.getHeight() * MAX_WIDTH_FONDO) / back.getWidth();
                back = resize(back, MAX_WIDTH_FONDO, heigt);
                int width = (back.getWidth() * MAX_HEIGHT_FONDO) / back.getHeight();
                back = resize(back, width, MAX_HEIGHT_FONDO);
            }else{
                int width = (back.getWidth() * MAX_HEIGHT_FONDO) / back.getHeight();
                back = resize(back, width, MAX_HEIGHT_FONDO);
                int heigt = (back.getHeight() * MAX_WIDTH_FONDO) / back.getWidth();
                back = resize(back, MAX_WIDTH_FONDO, heigt);
            }
        } catch (Exception ex) {            
        }
//**************************************************************************************
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawImage(back, (x + (width - back.getWidth())/2),(y + (height - back.getHeight())/2), null);
    }
 
    public Insets getBorderInsets(Component c) {
        return new Insets(0,0,0,0);
    }
 
    public boolean isBorderOpaque() {
        return false;
        //return true;
    }
    /*
     Este método se utiliza para redimensionar la imagen
    */
    public static BufferedImage resize(BufferedImage bufferedImage, int newW, int newH) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        
        BufferedImage bufim = new BufferedImage(newW, newH, bufferedImage.getType());
        Graphics2D g = bufim.createGraphics();
        
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(bufferedImage, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return bufim;
    }
}