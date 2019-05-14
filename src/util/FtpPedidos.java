package util;

import java.io.FileInputStream;
import java.io.IOException;
 
//import org.apache.commons.net.ftp.FTP;
//import org.apache.commons.net.ftp.FTPClient;
 
public class FtpPedidos {
 
    public static void main(String[] args) {
 
        // Creando nuestro objeto ClienteFTP
//        FTPClient client = new FTPClient();
// 
//        // Datos para conectar al servidor FTP
//        String ftp = "mi.servidor.ftp"; // También puede ir la IP
//        String user = "mi.usuario";
//        String password = "mi.contraseña";
// 
//        try {
//            // Conactando al servidor
//            client.connect(ftp);
//  
// 
//            // Logueado un usuario (true = pudo conectarse, false = no pudo
//            // conectarse)
//            if (client.login(user, password))
//            {
//                client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
//                client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
//                client.enterLocalPassiveMode();
//     
//                String filename = "mi.fichero";
//                
//                FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//"+filename);
//     
//                // Guardando el archivo en el servidor
//               if (client.changeWorkingDirectory("mi.carpeta.ftp"))
//                    if (client.storeFile(filename, fis))
//                        System.out.println("Se ha grabado el fichero");
//                    else
//                        System.out.println("No se ha grabado el fichero");
//     
//                // Cerrando sesión
//                client.logout();
//     
//                // Desconectandose con el servidor
//                client.disconnect();
//            }   
//        } catch (IOException ioe) {
//            System.out.println(ioe.getMessage());
//        }
    }
}