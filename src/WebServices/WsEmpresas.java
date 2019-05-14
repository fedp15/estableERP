package WebServices;

import Modelos.ModelEmpresas;
import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author RaaG
 */
public class WsEmpresas extends Thread{
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final ModelEmpresas modelEmpresas = ModelEmpresas.getModelEmpresas();

    private final conexion conet = new conexion();
    private HttpGet httpGet;
    private HttpResponse response;
    private HttpEntity entity;
    private JSONObject result;
    private int resp = 0;
    
    private String operacion = "";
    
    private final String URL_NUEVO_CODEMPRESA = VarGlobales.getUrlWebServices()+"getCodConsecutivo";
    private final String URL_BUSCA_EMPRESA = VarGlobales.getUrlWebServices()+"getBuscaCodigo";
    private final String URL_INSERT_EMPRESA = VarGlobales.getUrlWebServices()+"guardarEmpresa";
    private final String URL_UPDATE_EMPRESA = VarGlobales.getUrlWebServices()+"actualizarEmpresa";
    
    ResultSet rs;

    public WsEmpresas(String operacion) {
        this.operacion = operacion;
    }
    
    @Override
    public void run()  //METODO RUN PARA EL HILO
    {  
        HttpClient httpclient = new DefaultHttpClient();       

        try {
            switch (operacion) {
                case "BuscaEmpresa":
                    /*
                      en la parar del where colocar %20 para los espacios en blanco
                    */
                    httpGet = new HttpGet(URL_BUSCA_EMPRESA+"/EMP_CODIGO/dnempresas/WHERE%20EMP_CODIGO='"+modelEmpresas.getTxtCodigo().getText()+"'");
                    response = httpclient.execute(httpGet);
                    entity = response.getEntity();
                    
                    if (response != null) {
                        String retSrc = EntityUtils.toString(entity);
                        
                        result = new JSONObject(retSrc); // Convert
                        System.out.println(result);
                        
                        if (result.getString("estado").equals("correcto")) {
                            resp = JOptionPane.showConfirmDialog(null, "La empresa numero: "+modelEmpresas.getTxtCodigo().getText()+" ya existe en la Nube, "+
                                    "se procedera \na cambiar el código de la empresa que esta creando, de no cambiar \nel Codigo "+
                                    "esta empresa no sincronizara los registros con la Nube"+
                                    "\n\n¿Desea Actualizar el Código de la Empresa?", "Informacion", JOptionPane.YES_NO_OPTION);
                            
                            if(resp == JOptionPane.YES_OPTION){
                                httpGet = new HttpGet(URL_NUEVO_CODEMPRESA+"/6/EMP_CODIGO/dnempresas/%20/");
                                
                                response = httpclient.execute(httpGet);
                                entity = response.getEntity();
                                
                                if (response != null) {
                                    String retSrc1 = EntityUtils.toString(entity);
                                    
                                    result = new JSONObject(retSrc1); // Convert
                                    
                                    if (result.getString("estado").equals("correcto")) {
                                        JSONObject codEmpresa = result.getJSONObject("codempresa"); // Convert
                                        
                                        modelEmpresas.getTxtCodigo().setText((String) codEmpresa.get("CODIGO"));
                                        modelEmpresas.getTxtRif().requestFocus();
                                    } else {
                                        // FAIL
                                    }
                                }
                            }else{
                                
                            }
                        } else {
                            // FAIL
                        }
                    }   
                    
                    break;
                case "GuardarEmpresa":
                    HttpPost httppost = new HttpPost(URL_INSERT_EMPRESA);
                    
                    //List<NameValuePair> nameValuePairs = new ArrayList<>(7);
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
                    nameValuePairs.add(new BasicNameValuePair("codUser"  , VarGlobales.getIdUser()));
                    nameValuePairs.add(new BasicNameValuePair("macPc"    , VarGlobales.getMacPc()));
                    nameValuePairs.add(new BasicNameValuePair("codEmp"   , modelEmpresas.getTxtCodigo().getText()));
                    nameValuePairs.add(new BasicNameValuePair("nombEmp"  , modelEmpresas.getTxtNombre().getText()));
                    nameValuePairs.add(new BasicNameValuePair("idEmp"    , modelEmpresas.getTxtRif().getText()));
                    nameValuePairs.add(new BasicNameValuePair("direcEmp" , modelEmpresas.getTxtDireccion().getText()));
                    nameValuePairs.add(new BasicNameValuePair("activo"   , "1"));
                    
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    
                    // Execute HTTP Post Request
                    response = httpclient.execute(httppost);
                    entity = response.getEntity();
                    
                    if (response != null) {
                        String retSrc = EntityUtils.toString(entity);
                        
                        result = new JSONObject(retSrc); // Convert
                        
                        System.out.println(result);
                        if (result.getString("estado").equals("correcto")) {

                        } else {
                            // FAIL
                        }
                    
                    }   
                    
                    break;
                case "ActualizaEmpresa":
                    String activo="";
                    HttpPut httpPut = new HttpPut(URL_UPDATE_EMPRESA+"/"+modelEmpresas.getTxtCodigo().getText());
                    
                    List<NameValuePair> nameValueUpdate = new ArrayList<NameValuePair>(5);
                    nameValueUpdate.add(new BasicNameValuePair("codEmp", modelEmpresas.getTxtCodigo().getText()));
                    nameValueUpdate.add(new BasicNameValuePair("nomEmp", modelEmpresas.getTxtNombre().getText()));
                    nameValueUpdate.add(new BasicNameValuePair("idEmp", modelEmpresas.getTxtRif().getText()));
                    nameValueUpdate.add(new BasicNameValuePair("direcEmp", modelEmpresas.getTxtDireccion().getText()));
                    
                    if (modelEmpresas.getChkActivo().isSelected()==true){
                        activo="1";
                    }else{
                        activo="0";
                    }
                    nameValueUpdate.add(new BasicNameValuePair("activo", activo));

                    httpPut.setEntity(new UrlEncodedFormEntity(nameValueUpdate));
                    httpPut.addHeader("content-type", "application/json");
                
                    // Execute HTTP Post Request
                    response = httpclient.execute(httpPut);
                    entity = response.getEntity();

                    if (response != null) {
                        String retSrc = EntityUtils.toString(entity);

                        result = new JSONObject(retSrc); // Convert
System.out.println(result);
                        if (result.getString("estado").equals("correcto")) {

                        } else {
                            // FAIL
                        }
                    }
                    
                    break;
            }
        } catch (IOException | JSONException ex) {
            Logger.getLogger(WsEmpresas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
