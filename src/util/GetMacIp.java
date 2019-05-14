package util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

public class GetMacIp {
    String MAC = "";
    String IP = "";
    
    public Object[][] GetMacIp(){
        Object MacIp[][]=null;
        MacIp=new Object[1][2];
        
        InetAddress ip;
	try {
            ip = InetAddress.getLocalHost();
            System.out.println("Current IP address : " + ip.getHostAddress());
 
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
 
            byte[] mac = network.getHardwareAddress();
 
            System.out.print("Current MAC address : ");
 
            StringBuilder sb = new StringBuilder();
            
            if(mac!=null){
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
                }
            }
            System.out.println(sb.toString());
                
            MAC = sb.toString();
            IP = ip.getHostAddress();
                
            MacIp[0][0] = MAC;
            if(MacIp[0][0].equals("")){
                MacIp[0][0]=IP;
            }
            MacIp[0][1] = IP;
	}catch (UnknownHostException e) {
            e.printStackTrace();
	}catch (SocketException e){
            e.printStackTrace();
	}
        
        return MacIp;
    }
}