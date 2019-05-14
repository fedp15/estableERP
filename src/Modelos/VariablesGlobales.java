package Modelos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import util.EmailUtility;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class VariablesGlobales extends conexion{
    private static VariablesGlobales variablesGlobales;
    private String idUser, userNombre, ipPc, macPc, codEmpresa, grupoPermisos, pais, monedaEmpresa;
    private String ipServer, userServer, passwServer, basedatos, manejador, idioma;
    private String jornada="", turno="", nuevaEmp, baseDatosConfiguracion, urlWebServices, caja, nomEmpresa, demo="";
    
    private String nombPrecio1="", nombPrecio2="", nombPrecio3="", nombPrecio4="", nombPrecio5="", utilidadPrecio1="0,00", utilidadPrecio2="0,00", 
                   utilidadPrecio3="0,00", utilidadPrecio4="0,00", utilidadPrecio5="0,00", proveedServicio, userComprobElectro, claveComprobElectro, 
                   urlApiRest, ubicArchCertificado, claveArchCerticado, ultimoConseCbtElectron, ultimoConsecutivoNotaCredito, ultimoConsecutivoNotaDebito,
                   ultimoConsecutivoTiqueteElectronico, codigoEquipo = "", actualiza = "", urlAccesToken, urlLogoEmpresa="", ultimoConsecutivoReceptor,
                   codEmpresaConsulta = "", numEmpresas = "";
    private int longConsecTaller=2, diasRestantesLicencia = 0;
    private boolean calcUtilCosto, consecutivo, usaFactElectronica, consecutivoGrupoSubGrupo, visualizaUltimoRegForm;
    private boolean lBaseDatosConfiguracion = false, lDetenerEjecucion = false, lReconfiguraBaseDatos = false;
                                                           
    private Boolean lPos=true, lErp=true, lHcm=false, lHcs=false, lFdv=false, 
                    lEcom=false, lGp=false, lconSesion, lAgregaModif = false;
    private Vector errorCreaBd = new Vector();
    private int persId;
    private static final char[] CONSTS_HEX = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };
    private File carpeta, archivoLog;
    private char[] arrayChar;
    private InternetAddress[] toAddresses;
    private int contadorComa = 0, contadorPunto = 0;
    
    //**********Datos Necesarios para el JSON**********
    private String claveDocumento;
    private String fechaComprobante;
    private String tipoIdentificacionEmisor;
    private String numeroIdentificacionEmisor;
    private String tipoIdentificacionReceptor;
    private String numeroIdentificacionReceptor;
    private String pathXmlFirmado;
    private String acessToken;
    private String correoReceptor;
    private String consecutivoRecepTempError = "";
    private String erroresXml = "";
    //*************************************************
    
    //****** Variables para Devoluciones o Notas de Credito desde la anulacion de la Factura ******
    private boolean lNotaCreditoAutomatica = false;
    private String numDocDevAuto = "", tipDocDevAuto = "", codPersonaAuto = "";
    //*********************************************************************************************
    
    private BigInteger valorBigInteger = new BigInteger("0");
    private BigDecimal valorBigDecimal = new BigDecimal("0.00");
    private DecimalFormat valorBigDecimalFormat = new DecimalFormat("#,###.00");
    private DecimalFormat valorBigDecimalFormatXMLCrC_3 = new DecimalFormat("####.000");
    private DecimalFormat valorBigDecimalFormatXMLCrC_5 = new DecimalFormat("####.00000");
    
    private Connection conexion;
    private Statement st;
    
    ArrayList<String> listaLogos = new ArrayList();

    private VariablesGlobales() {
    }
    
    public static VariablesGlobales getDatosGlobales(){
        if (variablesGlobales == null){
            variablesGlobales = new VariablesGlobales();
        }

        return variablesGlobales;
    }

    public String getUrlWebServices() {
        return urlWebServices="http://omnixsolutions.com/omnix_solutions/api/rest/v1.0/";
    }
    
/*
    Implementancion de Metos Setter y Getter
*/
    public void setValorBigInteger(String valor) {
        if (valor.isEmpty()){
            valor = "0";
        }
        
        valorBigInteger = new BigInteger(valor);
    }
    
    public BigInteger getValorBigInteger(){
        return valorBigInteger;
    }
    
    public void setValorBigDecimal(String valor) {
        if (valor.isEmpty()){
            valor = "0.00";
        }

        valor = ajustaFormato(valor, false, "2");

        valorBigDecimal = new BigDecimal(valor);
    }
    
    public BigDecimal getValorBigDecimal(){
        return valorBigDecimal;
    }
    
    public String getValorBigDecimalFormat(String valor) {
        valorBigDecimalFormat.setParseBigDecimal(true);
        
        valor = ajustaFormato(valor, false, "2");
        valorBigDecimal = new BigDecimal(valor);
        String valorFormat = valorBigDecimalFormat.format(valorBigDecimal.doubleValue());

        valorFormat = ajustaFormato(valorFormat, true, "2");

        if (valorFormat.substring(0, 1).equals(",")){
            valorFormat = "0,00";
        }
        if (valorFormat.substring(0, 1).equals(".")){
            valorFormat = "0,00";
        }

        return valorFormat;
    }
    
    public String getValorBigDecimalFormat(String valor, String decimal) {
        String valorFormat = "";
        valorBigDecimalFormat.setParseBigDecimal(true);
        
        valor = ajustaFormato(valor, false, decimal);
        valorBigDecimal = new BigDecimal(valor);
        
        if(decimal.equals("2")){
            valorFormat = valorBigDecimalFormat.format(valorBigDecimal.doubleValue());
        }
        
        if(decimal.equals("3")){
            valorFormat = valorBigDecimalFormatXMLCrC_3.format(valorBigDecimal.doubleValue());
        }
        
        if(decimal.equals("5")){
            valorFormat = valorBigDecimalFormatXMLCrC_5.format(valorBigDecimal.doubleValue());
        }

        valorFormat = ajustaFormato(valorFormat, true, decimal);

        if (valorFormat.substring(0, 1).equals(",")){
            if(decimal.equals("2")){
                valorFormat = "0,00";
            }
            
            if(decimal.equals("3")){
                valorFormat = "0,000";
            }
            
            if(decimal.equals("5")){
                valorFormat = "0,00000";
            }
        }
        if (valorFormat.substring(0, 1).equals(".")){
            if(decimal.equals("2")){
                valorFormat = "0,00";
            }
            
            if(decimal.equals("3")){
                valorFormat = "0,000";
            }
            
            if(decimal.equals("5")){
                valorFormat = "0,00000";
            }
        }
        
        valorFormat = valorFormat.replace(".", "");
        valorFormat = valorFormat.replace(",", ".");

        return valorFormat;
    }
    
    private String ajustaFormato(String valor, boolean lMostrar, String numDecimal){
        arrayChar = valor.toCharArray();
        int subEntero = 0, subDecimal = 0;
        
        if (numDecimal.equals("2")){
            subEntero = 3;
            subDecimal = 2;
        }
        
        if (numDecimal.equals("3")){
            subEntero = 4;
            subDecimal = 3;
        }
        
        if (numDecimal.equals("5")){
            subEntero = 6;
            subDecimal = 5;
        }

        for(int i=0; i<arrayChar.length; i++){
            if( arrayChar[i] == ','){
                contadorComa++;
            }
            
            if( arrayChar[i] == '.'){
                contadorPunto++;
            }
        }
    
        if (contadorComa>contadorPunto){
            if(lMostrar){
                valor = valor.replace(",", ".");
                //valor = valor.replace(".", ",");
                String entero = valor.substring(0,valor.length()-subEntero);
                if (entero.isEmpty()){
                    entero = "0";
                }
                String decimal = valor.substring(valor.length()-subDecimal);
                valor = entero+","+decimal;
            }else{
                if(contadorComa==1){
                    valor = valor.replace(",", ".");
                }else{
                    valor = valor.replace(",", "");
                    valor = valor.replace(".", ".");
                }
            }
        }
        
        if (contadorPunto>contadorComa){
            if(!lMostrar){
                if(contadorPunto==1){
                    valor = valor.replace(".", ".");
                }else{
                    valor = valor.replace(".", "");
                    valor = valor.replace(",", ".");
                }
            }
        }
        
        if (contadorPunto==contadorComa){
            try {
                for(int i=0; i<arrayChar.length; i++){
                    if(arrayChar[i] == ','){
                        if(lMostrar){
                            valor = valor.replace(",", ".");
                            //valor = valor.replace(".", ",");

                            String entero = valor.substring(0,valor.length()-subEntero);
                            if (entero.isEmpty()){
                                entero = "0";
                            }
                            String decimal = valor.substring(valor.length()-subDecimal);
                            valor = entero+","+decimal;
                        }else{
                            valor = valor.replace(",", "");
                            valor = valor.replace(".", ".");
                        }

                        i=arrayChar.length;
                    }

                    if(arrayChar[i] == '.'){
                        if(!lMostrar){
                            valor = valor.replace(".", "");
                            valor = valor.replace(",", ".");
                        }

                        i=arrayChar.length;
                    }
                }
            } catch (Exception e) {
            }
        }
        
        contadorComa=0;
        contadorPunto=0;

        return valor;
    }
    
    public void setIdUser(String idUser){
        this.idUser = idUser;
    }
    
    public String getIdUser(){
        return idUser;
    }
    
    public void setUserName(String userName){
        this.userNombre = userName;
    }
    
    public String getUserName(){
        return userNombre;
    }
    
    public void setIpPc(String ipPc){
        this.ipPc = ipPc;
    }
    
    public String getIpPc(){
        return ipPc;
    }
    
    public void setMacPc(String macPc){
        this.macPc = macPc;
    }
    
    public String getMacPc(){
        return macPc;
    }
    
    public void setCodEmpresa(String codEmpresa){
        this.codEmpresa = codEmpresa;
    }
    
    public String getCodEmpresa(){
        return codEmpresa;
    }

    public String getCodEmpresaConsulta() {
        return codEmpresaConsulta;
    }

    public void setCodEmpresaConsulta(String codEmpresaConsulta) {
        this.codEmpresaConsulta = codEmpresaConsulta;
    }
    
    public void setGrupPermiso(String grupoPermiso){
        this.grupoPermisos = grupoPermiso;
    }
    
    public String getGrupPermiso(){
        return grupoPermisos;
    }
    
    public void setIpServer(String ipConex){
        this.ipServer = ipConex;
    }
    
    public String getIpServer(){
        return ipServer;
    }
    
    public void setUserServer(String userConex){
        this.userServer = userConex;
    }
    
    public String getUserServer(){
        return userServer;
    }
    
    public void setPasswServer(String passwConex){
        this.passwServer = passwConex;
    }
    
    public String getPasswServer(){
        return passwServer;
    }
    
    public void setBaseDatos(String bdConex){
        this.basedatos = bdConex;
    }
    
    public String getBaseDatos(){
        return basedatos;
    }

    public boolean islBaseDatosConfiguracion() {
        return lBaseDatosConfiguracion;
    }

    public void setlBaseDatosConfiguracion(boolean lBaseDatosConfiguracion) {
        this.lBaseDatosConfiguracion = lBaseDatosConfiguracion;
    }

    public boolean islReconfiguraBaseDatos() {
        return lReconfiguraBaseDatos;
    }

    public void setlReconfiguraBaseDatos(boolean lReconfiguraBaseDatos) {
        this.lReconfiguraBaseDatos = lReconfiguraBaseDatos;
    }

    public boolean islDetenerEjecucion() {
        return lDetenerEjecucion;
    }

    public void setlDetenerEjecucion(boolean lDetenerEjecucion) {
        this.lDetenerEjecucion = lDetenerEjecucion;
    }
    
    public void setManejador(String manejadorConex){
        this.manejador = manejadorConex;
    }

    public String getActualiza() {
        return actualiza;
    }

    public void setActualiza(String actualiza) {
        this.actualiza = actualiza;
    }

    public String getNumEmpresas() {
        return numEmpresas;
    }

    public void setNumEmpresas(String numEmpresas) {
        this.numEmpresas = numEmpresas;
    }

    public String getUrlLogoEmpresa() {
        return urlLogoEmpresa;
    }

    public ArrayList<String> getListaLogos() {
        return listaLogos;
    }

    public void setUrlLogoEmpresa(String urlLogoEmpresa) {
        this.listaLogos.add(urlLogoEmpresa);
        //this.urlLogoEmpresa = urlLogoEmpresa;
    }
    
    public String getManejador(){
        return manejador;
    }
    
    public void setPos(Boolean lPos){
        this.lPos = lPos;
    }
    
    public Boolean getPos(){
        return lPos;
    }
    
    public void setErp(Boolean lErp){
        this.lErp = lErp;
    }
    
    public Boolean getErp(){
        return lErp;
    }
    
    public void setHcm(Boolean lHcm){
        this.lHcm = lHcm;
    }
    
    public Boolean getHcm(){
        return lHcm;
    }
    
    public void setHcs(Boolean lHcs){
        this.lHcs = lHcs;
    }
    
    public Boolean getHcs(){
        return lHcs;
    }
    
    public void setFuerzadVentas(Boolean lFdv){
        this.lFdv = lFdv;
    }
    
    public Boolean getFuerzadVentas(){
        return lFdv;
    }
    
    public void setEcommers(Boolean lEcom){
        this.lEcom = lEcom;
    }
    
    public Boolean getEcommers(){
        return lEcom;
    }
    
    public void setGestoProyect(Boolean lGep){
        this.lGp = lGep;
    }
    
    public Boolean getGestoProyect(){
        return lGp;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
    
    public void setContinuaSesion(Boolean valor){
        this.lconSesion = valor;
    }
    
    public Boolean getContinuaSesion(){
        return lconSesion;
    }
    
    public void setJornada(String valor){
        this.jornada = valor;
    }
    
    public String getJornada(){
        return jornada;
    }
    
    public void setTurno(String valor){
        this.turno = valor;
    }
    
    public String getTurno(){
        return turno;
    }
    
    public  void setNuevaEmpresa(String value){
        this.nuevaEmp = value;
    }
    
    public String getNuevaEmpresa(){
        return nuevaEmp;
    }
    
    public  void setBaseDatosConfiguracion(String value){
        this.baseDatosConfiguracion = value;
    }
    
    public String getBaseDatosConfiguracion(){
        return baseDatosConfiguracion;
    }
    
    public void setCaja(String value){
        this.caja = value;
    }
    
    public String getCaja(){
        return caja;
    }

    public int getPersId() {
        return persId;
    }

    public void setPersId(int persId) {
        this.persId = persId;
    }

    public String getNomEmpresa() {
        return nomEmpresa;
    }

    public void setNomEmpresa(String nomEmpresa) {
        this.nomEmpresa = nomEmpresa;
    }

    public String getMonedaEmpresa() {
        return monedaEmpresa;
    }

    public void setMonedaEmpresa(String monedaEmpresa) {
        this.monedaEmpresa = monedaEmpresa;
    }

    public String getDemo() {
        return demo;
    }

    public int getDiasRestantesLicencia() {
        return diasRestantesLicencia;
    }

    public void setDiasRestantesLicencia(int diasRestantesLicencia) {
        this.diasRestantesLicencia = diasRestantesLicencia;
    }
    
    public void setDemo(String demo) {
        this.demo = demo;
    }

    public void setConfigEmpresa(String nombPrecio1, String nombPrecio2, String nombPrecio3,
                                 String nombPrecio4, String nombPrecio5, String utilidadPrecio1, String utilidadPrecio2,
                                 String utilidadPrecio3, String utilidadPrecio4, String utilidadPrecio5, boolean calcUtilCosto,
                                 boolean consecutivo, int longConsecTaller, boolean usaFactElectronica, String proveedServicio, 
                                 String userComprobElectro, String claveComprobElectro, String urlApiRest, String ubicArchCertificado, 
                                 String claveArchCerticado, String ultimoConseCbtElectron, boolean consecutivoGrupoSubGrupo,
                                 boolean visualizaUltimoRegForm, String consecutivoNotaCredito, String consecutivoNotaDebito,
                                 String consecutivoTiqueteElectronico, String urlAccesToken, String conseReceptor){
        this.nombPrecio1 = nombPrecio1;
        this.nombPrecio2 = nombPrecio2;
        this.nombPrecio3 = nombPrecio3;
        this.nombPrecio4 = nombPrecio4;
        this.nombPrecio5 = nombPrecio5;
        this.utilidadPrecio1 = utilidadPrecio1;
        this.utilidadPrecio2 = utilidadPrecio2;
        this.utilidadPrecio3 = utilidadPrecio3;
        this.utilidadPrecio4 = utilidadPrecio4;
        this.utilidadPrecio5 = utilidadPrecio5;
        
        this.proveedServicio     = proveedServicio;
        this.userComprobElectro  = userComprobElectro;
        this.claveComprobElectro = claveComprobElectro;
        this.urlApiRest          = urlApiRest;
        this.urlAccesToken       = urlAccesToken;
        this.ubicArchCertificado = ubicArchCertificado;
        this.claveArchCerticado  = claveArchCerticado;
        
        this.calcUtilCosto            = calcUtilCosto;
        this.consecutivo              = consecutivo;
        this.usaFactElectronica       = usaFactElectronica;
        this.consecutivoGrupoSubGrupo = consecutivoGrupoSubGrupo;
        
        this.longConsecTaller = longConsecTaller;
        
        this.ultimoConseCbtElectron              = ultimoConseCbtElectron;
        this.ultimoConsecutivoNotaCredito        = consecutivoNotaCredito;
        this.ultimoConsecutivoNotaDebito         = consecutivoNotaDebito;
        this.ultimoConsecutivoTiqueteElectronico = consecutivoTiqueteElectronico;
        this.ultimoConsecutivoReceptor           = conseReceptor;
        
        this.visualizaUltimoRegForm = visualizaUltimoRegForm;
    }

    public String getNombPrecio1() {
        return nombPrecio1;
    }

    public String getNombPrecio2() {
        return nombPrecio2;
    }

    public String getNombPrecio3() {
        return nombPrecio3;
    }

    public String getNombPrecio4() {
        return nombPrecio4;
    }

    public String getNombPrecio5() {
        return nombPrecio5;
    }

    public String getUtilidadPrecio1() {
        return utilidadPrecio1;
    }

    public String getUtilidadPrecio2() {
        return utilidadPrecio2;
    }

    public String getUtilidadPrecio3() {
        return utilidadPrecio3;
    }

    public String getUtilidadPrecio4() {
        return utilidadPrecio4;
    }

    public String getUtilidadPrecio5() {
        return utilidadPrecio5;
    }

    public boolean getCalcUtilCosto() {
        return calcUtilCosto;
    }

    public boolean getConsecutivo() {
        return consecutivo;
    }

    public int getLongConsecTaller() {
        return longConsecTaller;
    }

    public String getUserComprobElectro() {
        return userComprobElectro;
    }

    public String getClaveComprobElectro() {
        return claveComprobElectro;
    }

    public String getUrlApiRest() {
        return urlApiRest;
    }

    public String getUrlAccesToken() {
        return urlAccesToken;
    }

    public String getUbicArchCertificado() {
        return ubicArchCertificado;
    }

    public String getClaveArchCerticado() {
        return claveArchCerticado;
    }

    public boolean isUsaFactElectronica() {
        return usaFactElectronica;
    }

    public String getProveedServicio() {
        return proveedServicio;
    }

    public void setUltimoConseCbtElectron(String ultimoConsCbtElectron) {
        this.ultimoConseCbtElectron = ultimoConsCbtElectron;
    }

    public String getUltimoConseCbtElectron() {
        return ultimoConseCbtElectron;
    }

    public void setUltimoConsecutivoNotaCredito(String ultimoConsNotaCredito) {
        this.ultimoConsecutivoNotaCredito = ultimoConsNotaCredito;
    }

    public String getUltimoConsecutivoNotaCredito() {
        return ultimoConsecutivoNotaCredito;
    }

    public void setUltimoConsecutivoReceptor(String ultimoConsecutivoReceptor) {
        this.ultimoConsecutivoReceptor = ultimoConsecutivoReceptor;
    }

    public String getUltimoConsecutivoReceptor() {
        return ultimoConsecutivoReceptor;
    }

    public void setUltimoConsecutivoNotaDebito(String ultimoConsecNotaDebito) {
        this.ultimoConsecutivoNotaDebito = ultimoConsecNotaDebito;
    }

    public String getUltimoConsecutivoNotaDebito() {
        return ultimoConsecutivoNotaDebito;
    }

    public void setUltimoConsecutivoTiqueteElectronico(String ultimoConsecTiqueteElectronico) {
        this.ultimoConsecutivoTiqueteElectronico = ultimoConsecTiqueteElectronico;
    }

    public String getUltimoConsecutivoTiqueteElectronico() {
        return ultimoConsecutivoTiqueteElectronico;
    }

    public boolean isConsecutivoGrupoSubGrupo() {
        return consecutivoGrupoSubGrupo;
    }

    public boolean isVisualizaUltimoRegForm() {
        return visualizaUltimoRegForm;
    }

    public String getConsecutivoRecepTempError() {
        return consecutivoRecepTempError;
    }

    public void setConsecutivoRecepTempError(String consecutivoRecepTempError) {
        this.consecutivoRecepTempError = consecutivoRecepTempError;
    }

    public void setDatosJSON(String claveDocumento, String fechaComprobante, String tipoIdentificacionEmisor, String numeroIdentificacionEmisor,
                             String tipoIdentificacionReceptor, String numeroIdentificacionReceptor, String correoReceptor, String erroresXml){
        //**********Datos Necesarios para el JSON**********
        this.claveDocumento               = claveDocumento;
        this.fechaComprobante             = fechaComprobante;
        this.tipoIdentificacionEmisor     = tipoIdentificacionEmisor;
        this.numeroIdentificacionEmisor   = numeroIdentificacionEmisor;
        this.tipoIdentificacionReceptor   = tipoIdentificacionReceptor;
        this.numeroIdentificacionReceptor = numeroIdentificacionReceptor;
        this.correoReceptor               = correoReceptor;
        this.erroresXml                   = erroresXml;
    //*************************************************
    }

    public void setAcessToken(String acessToken) {
        this.acessToken = acessToken;
    }

    public void setPathXmlFirmado(String pathXmlFirmado) {
        this.pathXmlFirmado = pathXmlFirmado;
    }

    public String getCorreoReceptor() {
        return correoReceptor;
    }

    public String getErroresXml() {
        return erroresXml;
    }
    
    public String getPathXmlFirmado() {
        return pathXmlFirmado;
    }

    public String getAcessToken() {
        return acessToken;
    }

    public void setClaveDocumento(String claveDocumento) {
        this.claveDocumento = claveDocumento;
    }
    
    public String getClaveDocumento() {
        return claveDocumento;
    }

    public String getFechaComprobante() {
        return fechaComprobante;
    }

    public String getTipoIdentificacionEmisor() {
        return tipoIdentificacionEmisor;
    }

    public String getNumeroIdentificacionEmisor() {
        return numeroIdentificacionEmisor;
    }

    public String getTipoIdentificacionReceptor() {
        return tipoIdentificacionReceptor;
    }

    public String getNumeroIdentificacionReceptor() {
        return numeroIdentificacionReceptor;
    }

    public void datosDevulucionAutomatica(boolean lNotaCreditoAutomatica, String numDocDevAuto, String tipDocDevAuto, String codPersonaAuto){
        this.lNotaCreditoAutomatica = lNotaCreditoAutomatica;
        this.numDocDevAuto = numDocDevAuto;
        this.tipDocDevAuto = tipDocDevAuto;
        this.codPersonaAuto = codPersonaAuto;
    }

    public boolean islNotaCreditoAutomatica() {
        return lNotaCreditoAutomatica;
    }

    public String getNumDocDevAuto() {
        return numDocDevAuto;
    }

    public String getTipDocDevAuto() {
        return tipDocDevAuto;
    }

    public String getCodPersonaAuto() {
        return codPersonaAuto;
    }
    
    public static String encriptaEnMD5(String stringAEncriptar){
        try{
           MessageDigest msgd = MessageDigest.getInstance("MD5");
           byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
           StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
           
           for (int i = 0; i < bytes.length; i++){
               int bajo = (int)(bytes[i] & 0x0f);
               int alto = (int)((bytes[i] & 0xf0) >> 4);
               strbCadenaMD5.append(CONSTS_HEX[alto]);
               strbCadenaMD5.append(CONSTS_HEX[bajo]);
           }
           
           return strbCadenaMD5.toString();
        } catch (NoSuchAlgorithmException e) {
           return null;
        }
    }

    public Vector getErrorCreaBd() {
        return errorCreaBd;
    }

    public void setErrorCreaBd(String msgErr) {
        //this.errorCreaBd = errorCreaBd;
        errorCreaBd.add(msgErr);
    }
    
    public void creaTxt(String nombLog){
        try {
            carpeta = new File(System.getProperty("user.dir")+"\\Configuracion");
            archivoLog = new File(carpeta.getAbsolutePath()+"\\"+nombLog);
        
            File file = new File(archivoLog.toString());
            Boolean existe = file.isFile();
            
            if (existe==true){
                archivoLog.delete();
            }
                
            //Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
            FileWriter Arc = new FileWriter(archivoLog, true);
            BufferedWriter escribir = new BufferedWriter(Arc);
            
            //Escribimos en el archivo con el metodo write
            for (int i=0; i<errorCreaBd.size(); i++){
                System.out.println(errorCreaBd.get(i));
                escribir.write((String) errorCreaBd.get(i)); escribir.newLine();
            }

            //Cerramos la conexion
            escribir.close();
        } catch (IOException ex) {
            Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public File getArchivoLog() {
        return archivoLog;
    }
    
    public ImageIcon[] getBanderas(){
/*
        ImageIcon[] Banderas = { new ImageIcon(System.getProperty("user.dir").replace("\\", "/")+"/build/classes/imagenes/argentina_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir").replace("\\", "/")+"/build/classes/imagenes/brasil_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir").replace("\\", "/")+"/build/classes/imagenes/chile_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir").replace("\\", "/")+"/build/classes/imagenes/china_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir").replace("\\", "/")+"/build/classes/imagenes/colombia_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir").replace("\\", "/")+"/build/classes/imagenes/japon_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir").replace("\\", "/")+"/build/classes/imagenes/mexico_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir").replace("\\", "/")+"/build/classes/imagenes/united_states_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir").replace("\\", "/")+"/build/classes/imagenes/venezuela_flag.png")};
*/
        ImageIcon[] Banderas = { new ImageIcon(System.getProperty("user.dir").replace("\\", "/")+"/build/classes/imagenes/united_states_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir").replace("\\", "/")+"/build/classes/imagenes/venezuela_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir").replace("\\", "/")+"/build/classes/imagenes/costa_rica_flag.png")};
        
        return Banderas;
    }
    
    public void sendMail(String codDoc, File[] file, String codPersona, String claceCbteElectronico, boolean isTest, boolean viewMessage,
                         String estadoDocElectronico, String origen, String cbtElectronico, String respuesReceptor){
        try {
            Properties smtpProperties = new Properties();
            
            String sql = "SELECT correo_origen, clave_correo_origen, servidor_smtp, puerto_servidor_smtp, asunto_correo FROM confi_empresa \n"+
                         "WHERE id_empresa='"+getCodEmpresa()+"';";
            
            ResultSet rsDatosCorreo = consultar(sql);
            
            if (!rsDatosCorreo.getString("correo_origen").isEmpty() && !rsDatosCorreo.getString("clave_correo_origen").isEmpty() &&
                !rsDatosCorreo.getString("servidor_smtp").isEmpty() && !rsDatosCorreo.getString("puerto_servidor_smtp").isEmpty() &&
                !rsDatosCorreo.getString("asunto_correo").isEmpty()){
                
                smtpProperties.clear();
                smtpProperties.setProperty("mail.smtp.host", rsDatosCorreo.getString("servidor_smtp"));
                smtpProperties.setProperty("mail.smtp.port", rsDatosCorreo.getString("puerto_servidor_smtp"));  //465 o 587
                smtpProperties.setProperty("mail.user", rsDatosCorreo.getString("correo_origen"));
                smtpProperties.setProperty("mail.password", rsDatosCorreo.getString("clave_correo_origen"));
                smtpProperties.setProperty("mail.smtp.starttls.enable", "true");
                smtpProperties.setProperty("mail.smtp.auth", "true");
                smtpProperties.setProperty("mail.smtp.ssl.trust", rsDatosCorreo.getString("servidor_smtp"));
                //smtpProperties.setProperty("mail.smtp.starttls.enable", "true");

    //                smtpProperties.put("mail.smtp.socketFactory.port", "587");
    //                smtpProperties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
    //                smtpProperties.put("mail.smtp.socketFactory.fallback", "false");

                sql = "SELECT DISTINCT correo_envio_cnbt_electronic FROM dnpersonas\n" +
                      "LEFT JOIN dncontactos ON con_pers_id=pers_id\n" +
                      "WHERE rif LIKE '%"+codPersona.substring(3, codPersona.length())+"';";
                      //"WHERE rif='"+codPersona+"';";

                ResultSet rsCorreos = consultar(sql);

                if (rsCorreos.getRow()>0){
                    int RegCorreos = rsCorreos.getRow();
                    toAddresses = new InternetAddress[rsCorreos.getRow()];

                    rsCorreos.beforeFirst();
                    for (int i=0; i<RegCorreos; i++){
                        rsCorreos.next();
                        toAddresses[i] = new InternetAddress(rsCorreos.getString("correo_envio_cnbt_electronic"));
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "No se ha ingresado el correo del destinatario, por favor revise la ficha del cliente", 
                                                  "Notificación", JOptionPane.WARNING_MESSAGE);
                    
                    return;
                }

                //toAddresses = new InternetAddress[] {
                //    new InternetAddress("kerwin.urdaneta@resetconsultores.com"),
                //    new InternetAddress("ugarmig@gmail.com")
                //};

                //String mensajeHtml = new String("<html><font color='red'> "
                String mensajeHtml = new String("<html>"
                                              + "<br> "
                                              + "Estimado(a) KERWIN URDANETA NAVARRO por este medio le brindamos los Documentos correspondientes de su "
                                              + "Factura Electrónica Nº "+codDoc
                                              + "<br> "
                                              + "<br> "
                                              + "Documento Emitido a través de: Estable ERP, una aplicacion de Reset Consultes "
                                              + "<br> "
                                              + "Visitenos en: "
                                              + "<a href=\"http://www.resetconsultores.com\""
                                              + "target=\"_blank\">"
                                              + "www.resetconsultores.com"
                                              + "</a>"
                                              + "<br> "
                                              + "Que pase un Buen Día."
                                              + "<br> "
                                              + "<br> "
                                              + "<h2>QUE TALCO COMO TE QUEDO EL OJO</h2>"
                                              //+ "</font>"
                                              + "</html>");
                
                File archivoHtml = new File(System.getProperty("user.dir")+"\\sendMaiNotificacion.html");
                BufferedReader bf = new BufferedReader(new FileReader(archivoHtml));
                String Read;
                int linea=0;

                String html = new String(), textMail = "", textTitulo="", rutaLogo = "";
                while((Read = bf.readLine()) != null){
                    linea++;

                    if (linea==503){
                        //if (!VarGlobales.getUrlLogoEmpresa().isEmpty()){
                        if (VarGlobales.getListaLogos().size()>0){
                            VarGlobales.setlBaseDatosConfiguracion(true);
                            sql = "SELECT emp_rif FROM dnempresas WHERE emp_codigo='"+getCodEmpresa()+"';";
            
                            ResultSet rsIdEmpresa = consultar(sql);
                            
                            VarGlobales.setlBaseDatosConfiguracion(false);
            
                            for(int i = 0; i<VarGlobales.getListaLogos().size(); i++){
                                String idEmpresa = VarGlobales.getListaLogos().get(i).substring(VarGlobales.getListaLogos().get(i).indexOf("_"),
                                                                                                VarGlobales.getListaLogos().get(i).length());
                                
                                idEmpresa = idEmpresa.substring(1, idEmpresa.indexOf("."));
                                
                                if (rsIdEmpresa.getString("emp_rif").equals(idEmpresa)){
                                    rutaLogo = VarGlobales.getListaLogos().get(i);
                                }
                            }
                            
                            html = html+Read.trim()+"\n";
                            textTitulo = "<td s=\"\" align=\"left\" valign=\"middle\" id=\"clear-padding\">\n" +
                                         //"<a href=\"#\" style=\"text-decoration: none;\"><img src=\"http://phpstack-117815-635148.cloudwaysapps.com/repositorio_img/logo_dep_2.png\"  style=\"max-width:200px;\" border=\"0\" hspace=\"0\" vspace=\"0\"></a> \n" +
                                         "<a href=\"#\" style=\"text-decoration: none;\"><img src=\""+rutaLogo+"\"  style=\"max-width:200px;\" border=\"0\" hspace=\"0\" vspace=\"0\"></a> \n" +
                                         //"<a href=\"#\" style=\"text-decoration: none;\"><img src=\""+VarGlobales.getUrlLogoEmpresa()+"\"  style=\"max-width:200px;\" border=\"0\" hspace=\"0\" vspace=\"0\"></a> \n" +
                                         "</td>";
                            html = html+textTitulo;
                        }
                    }

                    if (linea==505){
                        html = html+Read.trim()+"\n";
                        
                        if (origen.equals("Ventas")){
                            if (variablesGlobales.islNotaCreditoAutomatica()){
                                textTitulo = "<h1 style=\"color:#004991\";>Nota de Credito Electronica</h1>";
                            }else{
                                textTitulo = "<h1 style=\"color:#004991\";>Facturacion Electronica</h1>";
                            }
                        }else if (origen.equals("Compras")){
                            textTitulo = "<h1 style=\"color:#004991\";>Repuesta del Receptor</h1>";
                        }
                        html = html+textTitulo;
                    }
                    
                    String mensajeEstado = "";
                    if (estadoDocElectronico.toLowerCase().equals("procesando")){
                        String estado = "<h5 style=\"color:#FF0000\";><b>''PROCESANDO''</b></h5>";
                        //String estado = "<FONT SIZE=6 COLOR=\"red\"><b>''PROCESANDO''</b></FONT>";
                        
                        //mensajeEstado = "<h5 style=\"color:#000000\";><u>Nota adicional:</u> el documento el electronico emitido se encuenta en estado: "+estado+" "+
                        //                "<h5 style=\"color:#000000\";>por tal razon los archivos XML correspondientes no se adjuntan en este correo, al cambiar su estado "+
                        //                "se procedera con el correspondiente envio de los archivos XML</h5>";
                        
                        mensajeEstado = "<h4 style=\"color:#000000\";><u>Nota adicional:</u> el documento electronico emitido se encuenta en "+
                                        "estado: <b style=\"color:#FF0000\";>''PROCESANDO''</b> por tal razon los archivos XML correspondientes no se adjuntan en este correo, "+
                                        "al cambiar su estado se procedera con el correspondiente envio de los archivos XML</h4>";
                    }
                    
                    if (linea==626){
                        String contenidoCorreo = "";
                        if (origen.equals("Ventas")){
                            contenidoCorreo = "Estimado(a) por este medio le brindamos los documentos correspondientes de su " +
                                              "Factura Electrónica Nº: "+codDoc+"</h3> ";
                        }else if (origen.equals("Compras")){
                            contenidoCorreo = "Estimado(a) por este medio se le notifica la respuesta por parte del Receptor sobre el " +
                                              "comprobante electronico N° "+cbtElectronico+" que fue enviado, mas el archivo xml con la Respuesta. <br><br>" +
                                              "La respuesta que el receptor selecciono fue: <h3 style=\"color:#FF0000\";><b>''"+respuesReceptor+"''</b></h3></h3> ";
                                              //"La respuesta que el receptor selecciono fue: "+respuesReceptor+"</h3> "+
                        }
                        
                        html = html+Read.trim()+"\n";
                        textMail = "<h3 style=\"color:#004991\";>"+
                                   "<br> " +
                                   contenidoCorreo +
                                   "<br> "+
                                   "<h4>Documento emitido a través de: Estable ERP, una aplicacion de Reset Consultes </h4>\n" +
                                   //"<br> "+
                                   "<h4>Visitenos en: </h2> <a href=\\\"http://www.resetconsultores.com\\\" target=\\\"_blank\\\"> www.resetconsultores.com </a>"+
                                   "<br> "+
//                                   "<br> "+
                                   "<h4>Que pase un buen Día.</h4>"+
                                   "<br> "+
//                                   "<br> "+
                                   "<h2>GRACIAS POR SU TIEMPO...!!!</h2>"+
                                   "<br> "+
                                   mensajeEstado;                      
  
                        html = html+textMail;
                    }else{
                        html = html+Read.trim()+"\n";
                    }
                }

                //Cierro y elimino el archivo desencriptado temporar
                bf.close();

                EmailUtility emailUtility = new EmailUtility();
                
                String asunto = "";
                if (origen.equals("Ventas")){
                    asunto = (variablesGlobales.islNotaCreditoAutomatica() ? "Nota de Credito Electrónica Nº ": "Factura Electrónica Nº ")+codDoc;
                }else if (origen.equals("Compras")){
                    asunto = "Respuesta del Receptor N° "+codDoc;
                }
                
                emailUtility.sendEmail(smtpProperties,
                                       toAddresses,
                                       asunto,
                                       html,
                                       file, isTest, viewMessage,
                                       rsDatosCorreo.getString("correo_origen"),
                                       rsDatosCorreo.getString("clave_correo_origen"),
                                       claceCbteElectronico);
            }
        } catch (AddressException ex) {
            Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException | IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //public void sendTestMail(String smtpHost, String smtpPort, String userMail, String passMail, File[] file){
    public void sendTestMail(String smtpHost, String smtpPort, String userMail, String passMail, String mensajeHtml, String asunto,
                             boolean isTest, boolean viewMessage){
        try {
            Properties smtpProperties = new Properties();
/*          
            smtpProperties.put("mail.smtp.host", smtpHost);
            smtpProperties.put("mail.smtp.starttls.enable", "true");
            smtpProperties.put("mail.smtp.port", smtpPort);  //465 o 587
            smtpProperties.put("mail.user", userMail);
            smtpProperties.put("mail.password", passMail);
            smtpProperties.put("mail.smtp.auth", "true");
            smtpProperties.put("mail.smtp.ssl.trust", smtpHost);
*/
            smtpProperties.clear();
            smtpProperties.setProperty("mail.smtp.host", smtpHost);
            smtpProperties.setProperty("mail.smtp.port", smtpPort);
            smtpProperties.setProperty("mail.smtp.user", userMail);
            smtpProperties.setProperty("mail.password", passMail);
            smtpProperties.setProperty("mail.smtp.auth", "true");
            //if (smtpPort.equals("587")){
                smtpProperties.setProperty("mail.smtp.starttls.enable", "true");
            //}
            //if (smtpPort.equals("465")){
                smtpProperties.setProperty("mail.smtp.ssl.trust", smtpHost);
            //}

/*            
            // Nombre del host de correo, es smtp.gmail.com
            smtpProperties.setProperty("mail.smtp.host", smtpHost);
            // TLS si está disponible
            smtpProperties.setProperty("mail.smtp.starttls.enable", "true");
            // Puerto del correo para envio de correos
            smtpProperties.setProperty("mail.smtp.port", smtpPort);  //465 o 587
            // Si requiere o no usuario y password para conectarse.
            smtpProperties.setProperty("mail.smtp.auth", "true");
            smtpProperties.setProperty("mail.smtp.ssl.trust", smtpHost);
            
            // Nombre del usuario
            //smtpProperties.setProperty("mail.smtp.user", userMail);
            //smtpProperties.setProperty("mail.user", userMail);
            //smtpProperties.setProperty("mail.password", passMail);
            smtpProperties.setProperty("mail.transport.protocol", "smtp");
            
            
            //smtpProperties.setProperty("mail.smtp.socketFactory.port", smtpPort);
            //smtpProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            //smtpProperties.setProperty("mail.smtp.socketFactory.fallback", "false");
            
//                smtpProperties.put("mail.smtp.socketFactory.port", "587");
//                smtpProperties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
//                smtpProperties.put("mail.smtp.socketFactory.fallback", "false");
*/          
            toAddresses = new InternetAddress[] {
                new InternetAddress(userMail)
            };

            EmailUtility emailUtility = new EmailUtility();
            emailUtility.sendEmail(smtpProperties,
                                   toAddresses,
                                   asunto,
                                   mensajeHtml,
                                   null, isTest, viewMessage,
                                   userMail,
                                   passMail, "");
        } catch (AddressException ex) {
            Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException | IOException ex) {
            Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendMail(boolean isTest, boolean viewMessage){
        try {
            Properties smtpProperties = new Properties();
            String ip = "";
            
            smtpProperties.clear();
            smtpProperties.setProperty("mail.smtp.host", "smtp.gmail.com");
            smtpProperties.setProperty("mail.smtp.port", "587");  //465 o 587
            //smtpProperties.setProperty("mail.user", "riky10a@gmail.com");
            smtpProperties.setProperty("mail.user", "info.estableerp@gmail.com");
            //smtpProperties.setProperty("mail.password", "15195133raag14136747");
            smtpProperties.setProperty("mail.password", "r@@g15195133");
            smtpProperties.setProperty("mail.smtp.auth", "true");
            smtpProperties.setProperty("mail.smtp.starttls.enable", "true");
            smtpProperties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
                
            //smtpProperties.setProperty("mail.smtp.starttls.enable", "true");
            
//                smtpProperties.put("mail.smtp.socketFactory.port", "587");
//                smtpProperties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
//                smtpProperties.put("mail.smtp.socketFactory.fallback", "false");

            //InternetAddress[] toAddresses = new InternetAddress[] {
            //    new InternetAddress("riky10a@gmail.com"),
            //    new InternetAddress("kerwin.urdaneta@resetconsultores.com")
            //};
            
            toAddresses = new InternetAddress[2];
            for (int i=0; i<2; i++){
                if (i==0){
                    toAddresses[i] = new InternetAddress("riky10a@gmail.com");
                }
                if (i==1){
                    toAddresses[i] = new InternetAddress("kerwin.urdaneta@resetconsultores.com");
                }
            }

            try {
                URL whatismyip = new URL("http://checkip.amazonaws.com");

                BufferedReader in = new BufferedReader(new InputStreamReader(

                whatismyip.openStream()));     

                ip = in.readLine();
                System.out.println("My Public ip is = "+ip);
                
//                whatismyip = new URL("https://norfipc.com/internet/direccion-ip.php");
//                
//                in = new BufferedReader(new InputStreamReader(
//
//                whatismyip.openStream()));     
//
//                ip = in.readLine();
//                System.out.println(in.lines());

                in.close();
            } catch (MalformedURLException e) {
                Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, e);
            } catch (IOException e) {
                Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, e);
            }
            
            Properties p = System.getProperties();        
            //String mensajeHtml = new String("<html><font color='red'> "
            String otrosDatos = new String("<html>"
                                         + "<br> "
                                         + "Nombre del Sistema Operativo: "+System.getProperty("os.name")
                                         + "<br> "
                                         + "Version del Sistema Operativo: "+System.getProperty("os.version")
                                         + "<br> "
                                         + "Idioma del Sistema Operativo: "+p.get("user.language")
                                         + "<br> "
                                         + "Pais del Sistema Operativo: "+p.get("user.country")
                                         + "<br> "
                                         + "Usuario Actual de Windows: "+System.getProperty("user.name")
                                         + "<br>"
                                         + "Version de Java #: "+System.getProperty("java.version")
                                         + "<br>"
                                         + "IP Local: "+getIpPc()
                                         + "<br>"
                                         + "MAC-Adrress: "+getMacPc()
                                         + "<br>"
                                         + "IP Publica: "+ip
                                         + "<br>"
                                         + "Localización de la IP: https://geoiptool.com/es/?ip="+ip
                                         + "</html>");

            File archivoHtml = new File(System.getProperty("user.dir")+"\\sendMaiNotificacion.html");
            BufferedReader bf = new BufferedReader(new FileReader(archivoHtml));
            String Read;
            int linea=0;

            String html = new String(), textMail = "", textTitulo = "";
            while((Read = bf.readLine()) != null){
                linea++;
                
                if (linea==504){
                    html = html+Read.trim()+"\n";
                    textTitulo = "<h1 style=\"color:#004991\";>DEMO del Sistema</h1>";
                    html = html+textTitulo;
                }
                
                if (linea==626){
                    html = html+Read.trim()+"\n";
                    textMail = "<h3 style=\"color:#004991\";> \n"+
                               "    EXPIRO el tiempo de uso del Sistema...!!!"+
                               "</h3>\n";
                    html = html+textMail+otrosDatos;
                }else{
                    html = html+Read.trim()+"\n";
                }
            }
                
            //Cierro y elimino el archivo desencriptado temporar
            bf.close();

            EmailUtility emailUtility = new EmailUtility();
            emailUtility.sendEmail(smtpProperties,
                                   toAddresses,
                                   "Finalización del Uso del DEMO",
                                   html,
                                   null, isTest, viewMessage,
                                   "info.estableerp@gmail.com",
                                   "r@@g15195133", "");
        } catch (AddressException ex) {
            Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException | IOException ex) {
            Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean getConnectionStatus() {
        boolean conStatus = false;
        
        try {
            //URL u = new URL("https://www.google.es/");
            URL u = new URL("https://www.google.com/");
            HttpsURLConnection huc = (HttpsURLConnection) u.openConnection();
            huc.connect();
            
            //conStatus = "Online";
            conStatus = true;
        } catch (Exception e) { 
            //conStatus = "Offline";
            conStatus = false;
        }        
        
        return conStatus;
    }
    
    public void jsonMensajeHacienda(String jsonString){        
        try{
            FileWriter out = new FileWriter("C:/EstableERP/ERP/FacturaElectronica/CostaRica/XML Recibidos/json_mensajehacienda_"+claveDocumento+".json");
            out.write(jsonString);
            out.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void jsonMensajeHaciendaReceptor(String jsonString, String numeroConsecutivoReceptor){
        try{
            FileWriter out = null;
            if (numeroConsecutivoReceptor.isEmpty()){
                out = new FileWriter("C:/EstableERP/ERP/FacturaElectronica/CostaRica/XML Compras/json_mensajehaciendaReceptor_"+claveDocumento+".json");
            }else{
                out = new FileWriter("C:/EstableERP/ERP/FacturaElectronica/CostaRica/XML Compras/json_mensajehaciendaReceptor_"+claveDocumento+"_"+numeroConsecutivoReceptor+".json");
            }
            out.write(jsonString);
            out.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
        
    public void decodeStringToXML(String xmlString){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        
        try{
            FileWriter out = new FileWriter("C:/EstableERP/ERP/FacturaElectronica/CostaRica/XML Recibidos/MensajeHacienda_"+claveDocumento+".xml");
            out.write(xmlString);
            out.close();
            
//            builder = factory.newDocumentBuilder();
//            // Use String reader
//            Document document = (Document) builder.parse(new InputSource(new StringReader(xmlString)));
// 
//            TransformerFactory tranFactory = TransformerFactory.newInstance();
//            Transformer aTransformer = tranFactory.newTransformer();
//            Source src = new DOMSource( (Node) document);
//            Result dest = new StreamResult(new File("C:/EstableERP/ERP/FacturaElectronica/CostaRica/XML Recibidos/MensajeHacienda_"+claveDocumento+".xml"));
//            aTransformer.transform( src, dest );
//                        
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            DOMSource source = new DOMSource((Node) document);
//            StreamResult result = new StreamResult(new File("C:/EstableERP/ERP/FacturaElectronica/CostaRica/XML Recibidos/MensajeHacienda_"+claveDocumento+".xml"));
//
//            transformer.transform(source, result);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
        
    public void decodeStringToXMLReceptor(String xmlString, String numeroConsecutivoReceptor){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        
        try{
            FileWriter out = null;
            if (numeroConsecutivoReceptor.isEmpty()){
                out = new FileWriter("C:/EstableERP/ERP/FacturaElectronica/CostaRica/XML Compras/MensajeHacienda_respuestaReceptor_"+claveDocumento+".xml");
            }else{
                out = new FileWriter("C:/EstableERP/ERP/FacturaElectronica/CostaRica/XML Compras/MensajeHacienda_respuestaReceptor_"+claveDocumento+"-"+numeroConsecutivoReceptor+".xml");
            }
            out.write(xmlString);
            out.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String decodeBase64ToString(String valor){
        String decodedBase64 = "";

        decodedBase64 = new String(Base64.getDecoder().decode(valor));
        decodedBase64 = new String(decodedBase64.getBytes(StandardCharsets.UTF_8));
        
//        new String(decodedBase64.getBytes(ISO), UTF_8);
        
        return decodedBase64;
    }

    public String encodeStrToBase64(){
        FileInputStream fileInputStreamReader = null;
        String encodedBase64 = "";
        
        try {            
            File originalFile = new File(getPathXmlFirmado());
            
            fileInputStreamReader = new FileInputStream(originalFile);
            byte[] bytes = new byte[(int) originalFile.length()];
            fileInputStreamReader.read(bytes);
            
            encodedBase64 = new String(Base64.getEncoder().encode(bytes));
            
            //encodedBase64.getBytes("UTF-8");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileInputStreamReader.close();
            } catch (IOException ex) {
                Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return encodedBase64;
    }

    public void setCodigoEquipo(String codigoEquipo) {
        this.codigoEquipo = codigoEquipo;
    }

    public String getCodigoEquipo() {
        return codigoEquipo;
    }
    
    public boolean guardarImagen(String ruta, String nombre, String codEmpresa){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //conexion = DriverManager.getConnection("jdbc:mysql://"+host+"/pruebas", user, pass); 
            conexion = DriverManager.getConnection("jdbc:mysql://"+variablesGlobales.getIpServer()+"/"+variablesGlobales.getBaseDatos(), 
                                                                   variablesGlobales.getUserServer(), variablesGlobales.getPasswServer());
            st = conexion.createStatement();
        } catch (Exception ex) {
            Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String insert = "";
        VarGlobales.setlBaseDatosConfiguracion(true);
        if (codEmpresa.equals(getCodEmpresa())){
            insert = "UPDATE dnempresas SET imagen = ?, nombre_foto_logo = ? WHERE emp_codigo='"+getCodEmpresa()+"'"; 
        }else{
            insert = "UPDATE dnempresas SET imagen = ?, nombre_foto_logo = ? WHERE emp_codigo='"+codEmpresa+"'"; 
        }
        VarGlobales.setlBaseDatosConfiguracion(false);
        
        FileInputStream fis = null;
        PreparedStatement ps = null;
     
        try {
            conexion.setAutoCommit(false);
            File file = new File(ruta);
            fis = new FileInputStream(file);
            
            ps = conexion.prepareStatement(insert);
            
            ps.setBinaryStream(1,fis,(int)file.length());
            ps.setString(2, nombre);
            
            ps.executeUpdate();
            conexion.commit();
             
            return true;
         } catch (Exception ex) {
            Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, ex);
         }finally{
             try {
                ps.close();
                fis.close();
             } catch (Exception ex) {
                Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
        
        return false;
    }
    
    public ArrayList<Imagen> getImagenes(String codEmpresa){
        ArrayList<Imagen> lista = new ArrayList();
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //conexion = DriverManager.getConnection("jdbc:mysql://"+host+"/pruebas", user, pass); 
            VarGlobales.setlBaseDatosConfiguracion(true);
            
            if (VarGlobales.islBaseDatosConfiguracion()){
                bd = VarGlobales.getBaseDatosConfiguracion();
            }else{
                bd = VarGlobales.getBaseDatos();
            }
            
            conexion = DriverManager.getConnection("jdbc:mysql://"+variablesGlobales.getIpServer()+"/"+bd, 
                                                                   variablesGlobales.getUserServer(), variablesGlobales.getPasswServer());
            st = conexion.createStatement();
        } catch (Exception ex) {
            Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            //String sql = "SELECT imagen,nombre_foto_logo FROM dnempresas WHERE emp_codigo='"+getCodEmpresa()+"'";
            String sql = "SELECT imagen,nombre_foto_logo FROM dnempresas WHERE emp_codigo='"+codEmpresa+"'";
            ResultSet rs = st.executeQuery(sql); 
            
            while (rs.next()){
                Imagen imagen = new Imagen();
                
                if (rs.getBlob("imagen") != null){
                    Blob blob = rs.getBlob("imagen");
                    String nombre = rs.getObject("nombre_foto_logo").toString();
                    byte[] data = blob.getBytes(1, (int)blob.length());

                    BufferedImage img = null;

                    try {
                        img = ImageIO.read(new ByteArrayInputStream(data));
                    } catch (IOException ex) {
                        Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    imagen.setImagen(img);
                    imagen.setNombre(nombre);
                    lista.add(imagen);

                    File carpetaLogos = new File(System.getProperty("user.dir")+"\\build\\classes\\imagenes\\logos_empresas\\");
                    carpetaLogos.mkdirs();

                    String path = carpetaLogos.toString();

                    String aux = lista.get(0).getNombre();
                    StringTokenizer token = new StringTokenizer(aux,".");
                    token.nextToken();
                    
                    //String formato = token.nextToken();
                    //ImageIO.write((RenderedImage) lista.get(0).getImagen(), formato, new File(path+"\\"+lista.get(0).getNombre()));
                    
                    try {
                        String formato = token.nextToken();
                        ImageIO.write((RenderedImage) lista.get(0).getImagen(), formato, new File(path+"\\"+lista.get(0).getNombre()));
                    } catch (Exception e) {
                        Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            }
            VarGlobales.setlBaseDatosConfiguracion(false);
            
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        return lista;
    }
    
    public void validaActualizacionEstructuraBaseDatos(){
        String nameFile = "";
        File carpeta = new File("C:\\EstableERP");
        File[] listOfFiles = carpeta.listFiles();
        int fileExecute=0;

        ArrayList<FileUpdateStructBd> arrayFileUpdateStructBd = null;
        arrayFileUpdateStructBd = new ArrayList();

        try {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()){
                    if (listOfFiles[i].getName().toLowerCase().endsWith(".sql")){
                        System.out.println("File " + listOfFiles[i].getName());

                        String sql = "SELECT COUNT(*) AS REGISTROS FROM script_alter_table WHERE nameFile='"+listOfFiles[i].getName()+"'";
                        try {
                            if(Count_Reg(sql)==0){
                                fileExecute++;
                                nameFile = listOfFiles[i].getName();

                                FileUpdateStructBd arrayFile = new FileUpdateStructBd();
                                arrayFile.setFilePath(nameFile);
                                arrayFileUpdateStructBd.add(arrayFile);
                                //arrayFileUpdateStructBd.setFilePath(nameFile);
                            }
                        } catch (Exception e) {
                            fileExecute++;
                            Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, e);
                        }
                    }
                }
            }

            if (fileExecute>0){
                //JOptionPane.showMessageDialog(null, "Hay una actualización de la estructura de la base de datos, se procedera a Actualizar la misma", "Notificación", JOptionPane.WARNING_MESSAGE);
                JOptionPane.showMessageDialog(null, "<html><P align=\"center\">Hay una actualización de la estructura de la base de datos "+
                                                    "''<u>"+VarGlobales.getBaseDatos()+"</u>'', se procedera a Actualizar la misma</html>", 
                                              "Notificación", JOptionPane.WARNING_MESSAGE);
                
                //JOptionPane.showMessageDialog(null, "<html><P align=\"center\">Este documento ya posee un comprobante Electronico asociado, para mas informacion consulte <br>"+
                //                                    "el formulario de los ''<u>Comprobantes Electronicos</u>''</html>", "Notificacion", JOptionPane.WARNING_MESSAGE);

                setlDetenerEjecucion(true);
                new Vista.ProgressBarCreaBd(true, nameFile, arrayFileUpdateStructBd).setVisible(true);
            }else{

            }

            ResultSet rsCorrecion = consultar("SELECT pro_codigo FROM dnproducto WHERE RIGHT(pro_codigo,16)='                ';");
            if (rsCorrecion.getRow()>0){

                rsCorrecion.beforeFirst();
                while(rsCorrecion.next()) {
                    String code = rsCorrecion.getString("pro_codigo");
                    String newCode = code.replace("                ", "");

    //                            for (int x=0; x < code.length(); x++) {
    //                                if (code.charAt(x) != ' ')
    //                                    newCode += code.charAt(x);
    //                            }

                    creaConexion();
                    Statement s = conn.createStatement();
                    s.execute("SET FOREIGN_KEY_CHECKS=0");

                    String sqlUpdate = "UPDATE dnproducto SET pro_codigo='"+newCode.trim()+"' WHERE pro_codigo='"+code+"' AND "+
                                       "RIGHT(pro_codigo,16)='                ';";
                    s.executeUpdate(sqlUpdate);

                    s.execute("SET FOREIGN_KEY_CHECKS=1");
                    s.close();
                    conn.close();
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(VariablesGlobales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}