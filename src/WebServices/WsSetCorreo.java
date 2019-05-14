

package WebServices;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

/**
 *
 * @author Andres
 */
public class WsSetCorreo extends Thread{
    private String URL_SendMail = "";
    private final String SERVER_MAIL="www.omnixsolutions.com/app/android/omnix_android";
    private final String MAIL_REMITENTE = "info@omnixsolutions.com";
    
    public WsSetCorreo(String detinatario, String asunto, String nom_remitente, String codEmp, String tipDoc, String numDoc) throws IOException, JSONException{
        URL_SendMail="http://"+SERVER_MAIL+"/sendCorreoPedido.php?destina="+detinatario.replace(" ", "+")+
                     "&asunto="+asunto.replace(" ", "+")+
                     "&nom_remitente="+nom_remitente.replace(" ", "+")+
                     "&correo_remitente="+MAIL_REMITENTE.replace(" ", "+")+
                     "&empresa="+codEmp.replace(" ", "+")+
                     "&t_documento="+tipDoc.replace(" ", "+")+
                     "&npedido="+numDoc.replace(" ", "+");
        
        System.out.println(URL_SendMail);
    }
    
    @Override
    public void run()  //METODO RUN PARA EL HILO
    { 
        try //METODO RUN PARA EL HILO
        {
            HttpClient httpClientCorreo = new DefaultHttpClient();
            HttpPost httppostCorreo = new HttpPost(URL_SendMail);
            
            HttpResponse responseCorreo = httpClientCorreo.execute(httppostCorreo);
            HttpEntity entityCorreo = responseCorreo.getEntity();
            
            if (entityCorreo != null) {
                String retSrc1 = EntityUtils.toString(entityCorreo);
                //JSONObject result1 = new JSONObject(retSrc1); // Convert
            }
        } catch (IOException ex) {
            Logger.getLogger(WsSetCorreo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
