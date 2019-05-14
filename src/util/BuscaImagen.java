package util;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BuscaImagen {
    private String imgFile = "", rutaImg = "";
    private File file;

    public BuscaImagen() {
    }
    
    public String buscarimagen(JTextField ruta, JLabel img){
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")+"\\")); 
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagenes", "bmp", "jpge", "jpg", "png");
        fc.setFileFilter(filter);
        fc.setDialogTitle("Seleccione el Archivo de Imagen");
        int r = fc.showOpenDialog(null);
        
        if(r==JFileChooser.APPROVE_OPTION){
            file = fc.getSelectedFile();
            rutaImg = file.getAbsoluteFile().toString();
            imgFile = file.getName().toString();
            //System.out.println(file.getPath().toString());
            //System.out.println(file.getParent().toString());
                
            ruta.setText(imgFile);
            
            Image preview = Toolkit.getDefaultToolkit().getImage(rutaImg);
            ImageIcon icon = new ImageIcon(preview.getScaledInstance(img.getWidth(), img.getHeight(), Image.SCALE_DEFAULT));
            img.setIcon(icon);
        }
        
        return rutaImg;
    }
    
    public String buscarimagen(JLabel img){
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")+"\\")); 
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagenes", "bmp", "jpge", "jpg", "png");
        fc.setFileFilter(filter);
        fc.setDialogTitle("Seleccione el Archivo de Imagen");
        int r = fc.showOpenDialog(null);
        
        if(r==JFileChooser.APPROVE_OPTION){
            file = fc.getSelectedFile();
            rutaImg = file.getAbsoluteFile().toString();
            imgFile = file.getName().toString();
            //System.out.println(file.getPath().toString());
            //System.out.println(file.getParent().toString());
            
            Image preview = Toolkit.getDefaultToolkit().getImage(rutaImg);
            ImageIcon icon = new ImageIcon(preview.getScaledInstance(img.getWidth(), img.getHeight(), Image.SCALE_DEFAULT));
            img.setIcon(icon);
        }
        
        return rutaImg;
    }
}
