package Modelos;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Andres
 */
 public class Imagen extends javax.swing.JPanel {
     private String file;
    private int width = 0, height = 0;
    private Image imagen;
    private String nombre;

    public Imagen() {
    
    }
    
    public Imagen(String file, int width, int height) {
        this.file = file;
        this.width = width;
        this.height = height;

        this.setSize(width,height);
    }

    //Se crea un método cuyo parámetro debe ser un objeto Graphics

    public void paint(Graphics grafico) {
        Dimension height = getSize();

        //Se selecciona la imagen que tenemos en el paquete de la //ruta del programa
        ImageIcon Img = new ImageIcon(System.getProperty("user.dir")+"/build/classes/imagenes/"+file);
        //se dibuja la imagen que tenemos en el paquete Images //dentro de un panel
        grafico.drawImage(Img.getImage(), 0, 0, height.width, height.height, null);

        setOpaque(false);
        super.paintComponent(grafico);
    }
    
    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Image getImagen() {
        return imagen;
    }

    public String getNombre() {
        return nombre;
    }
}
