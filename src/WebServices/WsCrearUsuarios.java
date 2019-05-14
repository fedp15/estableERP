package WebServices;

import Modelos.ModelCreaUsuarios;
import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import util.Internacionalizacion;

/**
 *
 * @author RaaG
 */
public class WsCrearUsuarios extends Thread{
    //private static WsPermisologias wsPermisologias;
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    private final ModelCreaUsuarios modelCreaUsuarios = ModelCreaUsuarios.getModelGrupoPermisos();

    private final conexion conet = new conexion();
    private HttpPost httppost;
    private HttpGet httpGet;
    private HttpPut httpPut;
    private HttpResponse response;
    private HttpEntity entity;
    private JSONObject result;
    private ResultSet rs_opc_menus;
    private int resp = 0;
    
    private String operacion = "", activo = "", accweb = "", accandroid = "";
    
    private final String URL_INSERT_USUARIO = VarGlobales.getUrlWebServices()+"crearUsuario";
    private final String URL_UPDATE_USUARIO = VarGlobales.getUrlWebServices()+"actualizarUsuario";
    private final String URL_BUSCA_CODIGO = VarGlobales.getUrlWebServices()+"getBuscaCodigo";
    private final String URL_NUEVO_CODIGO = VarGlobales.getUrlWebServices()+"getCodConsecutivo";
    
    ResultSet rs;
    
    public WsCrearUsuarios(){
    }
    
    public void setParametros(String operacion){
        this.operacion = operacion;
    }
    
    @Override
    public void run()  //METODO RUN PARA EL HILO
    {  
        HttpClient httpclient = new DefaultHttpClient();

        try {
            switch (operacion) {
                case "BuscaUsuario":
                    /*
                      en la parar del where colocar %20 para los espacios en blanco
                    */
                    System.err.println(modelCreaUsuarios.getTxtCodigo().getText());
                    httpGet = new HttpGet(URL_BUSCA_CODIGO+"/OPE_NUMERO/dnusuarios/WHERE%20OPE_NUMERO='"+modelCreaUsuarios.getTxtCodigo().getText()+"'");
                    response = httpclient.execute(httpGet);
                    entity = response.getEntity();
                    
                    if (response != null) {
                        String retSrc = EntityUtils.toString(entity);
                        
                        result = new JSONObject(retSrc); // Convert
                        System.out.println(result);
                        
                        if (result.getString("estado").equals("correcto")) {
                            httpGet = new HttpGet(URL_NUEVO_CODIGO+"/1/OPE_NUMERO/dnusuarios/%20/");
                                
                            response = httpclient.execute(httpGet);
                            entity = response.getEntity();
                                
                            if (response != null) {
                                String retSrc1 = EntityUtils.toString(entity);
                                    
                                result = new JSONObject(retSrc1); // Convert
                                    
                                if (result.getString("estado").equals("correcto")) {
                                    JSONObject codGrupoPermiso = result.getJSONObject("codempresa"); // Convert
                                        
                                    modelCreaUsuarios.getTxtCodigo().setText((String) codGrupoPermiso.get("CODIGO"));
                                    modelCreaUsuarios.getTxtNombre().requestFocus();
                                } else {
                                    // FAIL
                                }
                            }
                        } else {
                            // FAIL
                        }
                    }
                    
                    this.finalize();
                    
                    break;
                case "GuardarUsuario":
                    httppost = new HttpPost(URL_INSERT_USUARIO);
            
                    List<NameValuePair> nameValuePairs = new ArrayList<>(11);
                    //List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                    nameValuePairs.add(new BasicNameValuePair("codEmp"     , VarGlobales.getCodEmpresa()));
                    nameValuePairs.add(new BasicNameValuePair("nombUser"   , modelCreaUsuarios.getTxtNombre().getText()));
                    nameValuePairs.add(new BasicNameValuePair("cargoUser"  , modelCreaUsuarios.getCbCargo().getSelectedItem().toString()));
                    nameValuePairs.add(new BasicNameValuePair("passw"      , modelCreaUsuarios.getTxtClaveUser2().getText()));
                    
                    if (modelCreaUsuarios.getChkActivo().isSelected()==true){
                        activo = "1";
                    }else{
                        activo = "0";
                    }
                    nameValuePairs.add(new BasicNameValuePair("activo"     , activo));
        
//                    if (modelCreaUsuarios.getChkWeb().isSelected()==true){
//                        accweb = "1";
//                    }else{
//                        accweb = "0";
//                    }
                    nameValuePairs.add(new BasicNameValuePair("accweb"     , accweb));
        
//                    if (modelCreaUsuarios.getChkAndroid().isSelected()==true){
//                        accandroid = "1";
//                    }else{
//                        accandroid = "0";
//                    }
                    nameValuePairs.add(new BasicNameValuePair("accandroid" , accandroid));
                    nameValuePairs.add(new BasicNameValuePair("permiso"    , modelCreaUsuarios.getCbTipo().getSelectedItem().toString().substring(0, 4)));
                    nameValuePairs.add(new BasicNameValuePair("user"       , modelCreaUsuarios.getTxtUsuario().getText()));
                    nameValuePairs.add(new BasicNameValuePair("email"      , modelCreaUsuarios.getTxtCorreo().getText()));
                    nameValuePairs.add(new BasicNameValuePair("rutaImg"    , modelCreaUsuarios.getTxtRutaImg().getText()));
            
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            
                    // Execute HTTP Post Request
                    response = httpclient.execute(httppost);
                    entity = response.getEntity();
            
                    if (response != null) {
                        String retSrc2 = EntityUtils.toString(entity);
                
                        result = new JSONObject(retSrc2); // Convert
                        System.out.println(result);
                
                        if (result.getString("estado").equals("correcto")) {
                            
                        } else {
                            // FAIL
                        }
                    }
                    
                    this.finalize();
                    
                    break;
                case "ActualizaUsuario":
                    httpPut = new HttpPut(URL_UPDATE_USUARIO+"/"+VarGlobales.getCodEmpresa()+"/"+modelCreaUsuarios.getTxtCodigo().getText());
                    
                    List<NameValuePair> nameValueUpdate = new ArrayList<>(12);
                    nameValueUpdate.add(new BasicNameValuePair("codEmp"   , VarGlobales.getCodEmpresa()));
                    nameValueUpdate.add(new BasicNameValuePair("codUser"  , modelCreaUsuarios.getTxtCodigo().getText()));
                    nameValueUpdate.add(new BasicNameValuePair("nombUser" , modelCreaUsuarios.getTxtNombre().getText()));
                    nameValueUpdate.add(new BasicNameValuePair("cargo"    , modelCreaUsuarios.getCbCargo().getSelectedItem().toString()));
                    nameValueUpdate.add(new BasicNameValuePair("passw"    , modelCreaUsuarios.getTxtClaveUser2().getText()));
                    
                    if (modelCreaUsuarios.getChkActivo().isSelected()==true){
                        activo = "1";
                    }else{
                        activo = "0";
                    }
                    nameValueUpdate.add(new BasicNameValuePair("activo"     , activo));
        
//                    if (modelCreaUsuarios.getChkWeb().isSelected()==true){
//                        accweb = "1";
//                    }else{
//                        accweb = "0";
//                    }
                    nameValueUpdate.add(new BasicNameValuePair("accweb"     , accweb));
        
//                    if (modelCreaUsuarios.getChkAndroid().isSelected()==true){
//                        accandroid = "1";
//                    }else{
//                        accandroid = "0";
//                    }
                    nameValueUpdate.add(new BasicNameValuePair("accandroid" , accandroid));
                    nameValueUpdate.add(new BasicNameValuePair("permiso"    , modelCreaUsuarios.getCbTipo().getSelectedItem().toString().substring(0, 4)));
                    nameValueUpdate.add(new BasicNameValuePair("usuario"    , modelCreaUsuarios.getTxtUsuario().getText()));
                    nameValueUpdate.add(new BasicNameValuePair("email"      , modelCreaUsuarios.getTxtCorreo().getText()));
                    nameValueUpdate.add(new BasicNameValuePair("rutaImg"    , modelCreaUsuarios.getTxtRutaImg().getText()));

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
        } catch (IOException | JSONException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(WsCrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(WsCrearUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}