package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopiarFicheros {
    Boolean lCopia = false;

    public Boolean CopiarFicheros(String ori, String desti) throws IOException{
        desti = desti.replaceAll("/", "\\\\");
        String orig = ori.replace("\\","\\\\");
        String dest = desti.replace("\\","\\\\");
            
        System.out.println(orig);
        System.out.println(dest);
            
        File origen = new File(orig);
        File destino = new File(dest);
            
        if(!destino.exists()){
            destino.createNewFile();
        }

        try {
            InputStream in = new FileInputStream(origen);
            OutputStream out = new FileOutputStream(destino);
                                
            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
                
            in.close();
            out.close();
                 
            System.out.println("File is copied successful!");
            lCopia = true;
        } catch (IOException ioe){
            ioe.printStackTrace();
            
            lCopia = false;
        }
        
        return lCopia;
    }
}
