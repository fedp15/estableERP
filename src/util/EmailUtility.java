package util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;


public class EmailUtility { 
    private static boolean lSendCorreo = true;
    
    public static void sendEmail(Properties smtpProperties, InternetAddress[] toAddresses,
                    String subject, String message, File[] attachFiles, boolean isTest, boolean viewMessage, 
                    String correo, String clave, String claceCbteElectronico) throws AddressException, MessagingException, IOException {
                    
//*****************************************************************************************************
        Session session = Session.getDefaultInstance(smtpProperties);
            session.setDebug(true);
            
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(correo));
            //msg.addRecipients(Message.RecipientType.TO, toAddresses);
            
            String correoTo = "", correoCC = "", correoCCO = "";
            for (int i = 0; i < toAddresses.length; i++){
                if (i==0){
                    correoTo = toAddresses[i].getAddress();
                    msg.addRecipient(Message.RecipientType.TO, new InternetAddress(correoTo));
                }else{
                    if (i==toAddresses.length-1){
                        correoCCO = toAddresses[i].getAddress();
                        msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(correoCCO));
                    }else{
//                        if (i==1){
//                            correoCC = toAddresses[i].getAddress();
//                        }else{
//                            correoCC = correoCC+";"+toAddresses[i].getAddress();
//                        }
                        
                        correoCC = toAddresses[i].getAddress();
                        msg.addRecipient(Message.RecipientType.CC, new InternetAddress(correoCC));
                    }
                }
            }
            //msg.addRecipient(Message.RecipientType.TO, new InternetAddress(correoTo));
//            msg.addRecipients(Message.RecipientType.TO, correoTo);
//            msg.addRecipients(Message.RecipientType.CC, correoCC);
//            if (!correoCCO.isEmpty()){
//                msg.addRecipients(Message.RecipientType.BCC, correoCCO);
//            }
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            //msg.setText(message, "ISO-8859-1", "html");

            // creates message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message, "text/html; charset=utf-8");

            // creates multi-part
            Multipart multiPart = new MimeMultipart();
            multiPart.addBodyPart(messageBodyPart);

            // adds attachments
            //System.err.println(attachFiles.length);
            if (attachFiles != null && attachFiles.length > 0) {
                for (File aFile : attachFiles) {
                    MimeBodyPart attachPart = new MimeBodyPart();

                    try {
                        System.err.println(aFile);
                        attachPart.attachFile(aFile);
                    } catch (IOException ex) {
                        //throw ex;
                        ex.printStackTrace();
                    }

                    multiPart.addBodyPart(attachPart);
                }
            }

            // sets the multi-part as e-mail's content
            msg.setContent(multiPart);

            // sends the e-mail
            Transport transport = null;
            try {
                transport = session.getTransport("smtp");
                //transport.connect();
                transport.connect(correo, clave);
                //transport.connect("smtp.gmail.com",587,correo, clave);
                //transport.sendMessage(msg, toAddresses);
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close();
/*
                transport.send(msg);
*/
            } catch (Exception e) {
                Logger.getLogger(EmailUtility.class.getName()).log(Level.SEVERE, null, e);

                lSendCorreo = false;
                JOptionPane.showMessageDialog(null, "El correo no pudo ser enviado por la Siguiente razon: \n\n"+e.getMessage(), 
                                             "Error de Envio de Correo", JOptionPane.ERROR_MESSAGE);
            }

            if (lSendCorreo){
                if (isTest && viewMessage){
                    JOptionPane.showMessageDialog(null, "El correo de prueba le fue enviado, por favor confirme la recepción", "Notificación", 
                                                  JOptionPane.INFORMATION_MESSAGE);
                }

                if (!isTest && viewMessage){
                    JOptionPane.showMessageDialog(null, "Enviado correo con los archivos de la factura electronica", "Notificación", 
                                                  JOptionPane.INFORMATION_MESSAGE);
                    
                    
                    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    //Date fch = new Date();
                    //String fecha = sdf.format(fch);
                    if (!claceCbteElectronico.isEmpty()){
                        SQLQuerys update = new SQLQuerys();
                    
                        //update.SqlUpdate("UPDATE status_cbtes_electronicos SET enviado_cliente=1 WHERE clave_cbte_electronico='"+claceCbteElectronico+"'");
                    }
                }
            }
//*****************************************************************************************************
//            Session session = Session.getDefaultInstance(smtpProperties);
//            session.setDebug(true);
//            
//            MimeMessage msg = new MimeMessage(session);
//            msg.setFrom(new InternetAddress(correo));
//            //msg.setRecipients(Message.RecipientType.TO, toAddresses);
//            msg.addRecipient(Message.RecipientType.TO, new InternetAddress("riky10a@gmail.com"));
//            msg.setSubject(subject);
//            msg.setSentDate(new Date());
//            msg.setText(message, "ISO-8859-1", "html");
//
//            // sends the e-mail
//            Transport transport = null;
//            try {
//                transport = session.getTransport("smtp");
//                //transport.connect();
//                transport.connect(correo, clave);
//                //transport.connect("smtp.gmail.com",587,correo, clave);
//                
//                //transport.sendMessage(msg, toAddresses);
//                transport.sendMessage(msg, msg.getAllRecipients());
//                
//                transport.close();
//            } catch (Exception e) {
//                Logger.getLogger(EmailUtility.class.getName()).log(Level.SEVERE, null, e);
//
//                lSendCorreo = false;
//                JOptionPane.showMessageDialog(null, "El correo no pudo ser enviado por la Siguiente razon: \n\n"+e.getMessage(), 
//                                             "Error de Envio de Correo", JOptionPane.ERROR_MESSAGE);
//            }
//            
//            if (lSendCorreo){
//                if (isTest && viewMessage){
//                    JOptionPane.showMessageDialog(null, "El correo de prueba le fue enviado, por favor confirme la recepción", "Notificación", 
//                                                  JOptionPane.INFORMATION_MESSAGE);
//                }
//            
//                if (!isTest && viewMessage){
//                    JOptionPane.showMessageDialog(null, "El correo fue enviado correcte mente", "Notificación", 
//                                                  JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
    }
}