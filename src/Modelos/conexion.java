package Modelos;

import static Vista.ConfigurarConexion.lPassPostGre;
import static Vista.ConfigurarConexion.Clave;
import static Vista.Login.NumEmp;
import com.sun.rowset.CachedRowSetImpl; 
import javax.sql.rowset.CachedRowSet; 
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import util.Internacionalizacion;

public class conexion {
    static VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    
    public static String bd = VarGlobales.getBaseDatos(), login = VarGlobales.getUserServer(), password = VarGlobales.getPasswServer();
    public static String url = "jdbc:mysql://"+VarGlobales.getIpServer()+"/" + VarGlobales.getBaseDatos();
    public java.sql.Statement stmt;//consultas
    public static java.sql.Connection con;
    public ResultSet rs, rs_mysql;
    public ResultSetMetaData rsMeta;
    public java.sql.Connection conn;
    public PreparedStatement consulta;
    protected ResultSet resultado;
    public Statement statement = null; 
    //private CachedRowSetImpl cachedRowSet = null;
    private CachedRowSet cachedRowSet = null;
    protected java.sql.Statement stSentenciciasSQL;
    protected int registros;
    public static boolean login_acceso = false, lBdExist = false, lCreaBd = false;
//    private Vector Msg, elementos;
    private String FormClass="";
    
    public static void Conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, login, password);
            System.out.println("CONEXION EXITOSA");
        }catch(ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e){
            System.out.println("ERROR DE CONEXION: " + e.getMessage());
        }
    }
    
    public static boolean Conectar(String Driver, String User, String Pass){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(Driver, User, Pass);
            System.out.println("CONEXION EXITOSA");
            
            return true;
        }catch(ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e){
            System.out.println("ERROR DE CONEXION: " + e.getMessage());
            return false;
        }
    }
    
    public void creaConexion() throws ClassNotFoundException, SQLException{
        try {
            switch (VarGlobales.getManejador()) {
                case "PostGreSQL":
                    Class.forName("org.postgresql.Driver");
                    //this.conn = DriverManager.getConnection("jdbc:postgresql://192.168.1.103:5432/dnpos","dnet","123");
                    this.conn = DriverManager.getConnection("jdbc:postgresql://"+VarGlobales.getIpServer()+"/"+VarGlobales.getBaseDatos(), VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                    stSentenciciasSQL = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    break;
                case "MySQL":
                    Class.forName("com.mysql.jdbc.Driver");
                    //this.conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/dnpos","root","");
                    if (VarGlobales.islBaseDatosConfiguracion()){
                        bd = VarGlobales.getBaseDatosConfiguracion();
                    }else{
                        if (VarGlobales.getCodEmpresaConsulta().isEmpty()){
                            bd = VarGlobales.getBaseDatos();
                        }else{
                            if (VarGlobales.getCodEmpresaConsulta().equals("000001")){
                                bd = "establedemo";
                            }else{
                                bd = "estableerp_"+VarGlobales.getCodEmpresaConsulta();
                            }
                        }
                    }
                    
                    this.conn = DriverManager.getConnection("jdbc:mysql://"+VarGlobales.getIpServer()+"/"+bd+
                                                            "?allowMultiQueries=true&useUnicode=true&characterEncoding=utf-8", 
                                                            VarGlobales.getUserServer(), 
                                                            VarGlobales.getPasswServer());
                    stSentenciciasSQL = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    break;
            }
            lCreaBd=false; lBdExist = true;
        } catch (ClassNotFoundException | SQLException | HeadlessException e) {
            switch (VarGlobales.getManejador()) {
                case "PostGreSQL":
                    {
//******************** Codigo para solicitar la clave de Superusuario de PostGreSQL ********************
                        if (lPassPostGre==true){
                            JPanel PassPanel = new JPanel();
                            PassPanel.setLayout(new GridLayout(2, 2));

//                            JLabel titulo = new JLabel((String) elementos.get(0));
                            JPasswordField pf = new JPasswordField();
                
//                            PassPanel.add(titulo);
                            PassPanel.add(pf);
                
//                            int okCxl = JOptionPane.showConfirmDialog(null, PassPanel, (String) Msg.get(0), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    
//                            if (okCxl== JOptionPane.OK_OPTION){
//                                Clave = new String(pf.getPassword());
//                            }
                        }
                        System.out.println("Tu clave es: "+Clave);
//******************************************************************************************************
                        this.conn = DriverManager.getConnection("jdbc:postgresql://"+VarGlobales.getIpServer()+"/postgres", "postgres", Clave);
                        stSentenciciasSQL = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        String sql = "SELECT datname AS DataBase FROM pg_database WHERE datistemplate = false;";
                        this.consulta = this.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                        resultado = this.consulta.executeQuery();
                        resultado.last();
                        break;
                    }
                case "MySQL":
                    {
                        this.conn = DriverManager.getConnection("jdbc:mysql://"+VarGlobales.getIpServer()+"/mysql"+"?allowMultiQueries=true", VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                        stSentenciciasSQL = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        String sql = "SHOW DATABASES;";
                        this.consulta = this.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                        resultado = this.consulta.executeQuery();
                        resultado.last();
                        break;
                    }
            }

            resultado.first();
            for (int i=0; i<resultado.getRow(); i++){
                //if (VarGlobales.getBaseDatos().equals(resultado.getString("DataBase"))){
                if (bd.equals(resultado.getString("DataBase"))){
                    lBdExist = true;
                }
                resultado.next();
            }
            
            if (lBdExist==false && lCreaBd==false){
                try {
                    int eleccion = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgDataBaseNotFound"), 
                                                                       idioma.loadLangComponent().getString("lbConexionBD"), JOptionPane.ERROR_MESSAGE);
                
                    if ( eleccion == 0) {
                        new Vista.ProgressBarCreaBd().setVisible(true);
                        lCreaBd=true;
                    }
                } catch (Exception ex) {
                    Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public java.sql.Connection conectado(){
        return con;
    }
    
    /**
     * @param sql sentencia sql como insertar, actualizar y eliminar
     * @return 0 si no hubo ningun cambio, devuelve 1 si hubo algun cambio 
     * en los registros
     */
    public static int guardarRegistro(String sql){
        conexion.Conectar();
        
        try{
            int eu;
            try (java.sql.Statement st = conexion.con.createStatement()) {
                eu = st.executeUpdate(sql);
            }
            conexion.con.close();
            
            return eu;    
        }catch(SQLException ex){
        }
        
        return 0;
    }
    
    public static ResultSet getRegistros(String sql){
        conexion.Conectar();
        
        try{
            java.sql.Statement st = conexion.con.createStatement();
            ResultSet eq = st.executeQuery(sql);            
            return eq;
            
        }catch(SQLException ex){
        }
        
        return null;
    }
    
    public boolean access (String txt_user, String txt_pass, String cod_empresa) {
        //***** Se declaran las variables de conexion en null
        java.sql.Connection Conn = null;
        ResultSet rsExiste = null;
        ResultSet rs = null;
        java.sql.Statement consulta = null;   
    
        //Guardo la Consulta en una variable String en este caso la llamo "sql"
        //String sql=("SELECT OPE_NOMBRE, OPE_CLAVE FROM "+basedatos+".dnusuarios WHERE OPE_USUARIO='"+txt_user+"' AND OPE_CLAVE='"+txt_pass+"'");
        //String sql=("SELECT OPE_NOMBRE,OPE_CLAVE FROM dnusuarios WHERE OPE_EMPRESA='"+cod_empresa+"' AND "+
        String sql=("SELECT ope_numero,OPE_NOMBRE,OPE_CLAVE FROM dnusuarios WHERE OPE_USUARIO='"+txt_user+"' AND "+
                    "OPE_CLAVE='"+txt_pass+"' AND OPE_ACTIVO=1");
        System.out.println(sql);
        
        try{
            switch (VarGlobales.getManejador()) {
                case "PostGreSQL":
                    Class.forName("org.postgresql.Driver");
                    //Conn = DriverManager.getConnection("jdbc:postgresql://192.168.1.103:5432/dnpos","dnet","123");
                    Conn = DriverManager.getConnection("jdbc:postgresql://"+VarGlobales.getIpServer()+"/"+VarGlobales.getBaseDatos(), VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                    consulta=(java.sql.Statement) Conn.createStatement();
                    break;
                case "MySQL":
                    Class.forName("com.mysql.jdbc.Driver");
                    
                    if (VarGlobales.islBaseDatosConfiguracion()){
                        bd = VarGlobales.getBaseDatosConfiguracion();
                    }else{
                        bd = VarGlobales.getBaseDatos();
                    }
                    
                    //Conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/dnpos","root","");
                    Conn = DriverManager.getConnection("jdbc:mysql://"+VarGlobales.getIpServer()+"/"+bd, VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                    consulta=(java.sql.Statement) Conn.createStatement();
                    break;
            }

            String sqlExiste=("SELECT COUNT(*) AS REGISTROS FROM dnusuarios WHERE OPE_USUARIO='"+txt_user+"' AND OPE_CLAVE='"+txt_pass+"'");
            int userExist = Count_Reg(sqlExiste);
            //rs = consulta.executeQuery(sql);
            rs = consultar(sql);
            
            //if(rs.getRow()>0){
            if (userExist>0){
                //rs.next();
                String idUser = rs.getString("ope_numero");
                ResultSet rsAutoriza = consultar("SELECT * FROM relac_usuario_grupo_permisos\n" +
                                                "INNER JOIN dnpermiso_grupal on dnpermiso_grupal.per_id=relac_usuario_grupo_permisos.per_id "+
                                                "WHERE ope_numero="+idUser+" AND emp_codigo='"+cod_empresa+"'");
                
                if(rsAutoriza.getRow()>0){
                    login_acceso = true;
                    
                    System.out.println("Login Correcto");
                }else{
                    login_acceso = false;
                }
            }else{ 
                if (NumEmp>2){
//                    JOptionPane.showMessageDialog(null, (String) Msg.get(3));
                }else{
                    if (userExist>0){
//                        JOptionPane.showMessageDialog(null, (String) Msg.get(5));
                    }else{
//                        JOptionPane.showMessageDialog(null, (String) Msg.get(6));
                    }
                }
                login_acceso = false;
            }

            while(rs.next()){ 
                System.out.println(rs.getString(1));
            }
        }catch (ClassNotFoundException |SQLException e) {
//            JOptionPane.showMessageDialog(null, (String) Msg.get(4));
        }finally{
            try {
                rs.close();
                Conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return(login_acceso);
    }    
    
    public ResultSet consultar(String sql) throws ClassNotFoundException{
        try{
            this.creaConexion();
            //this.consulta = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            //resultado = this.consulta.executeQuery();
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            resultado = statement.executeQuery(sql);

            //resultado.last();
            //resultado.first();
            
            cachedRowSet = new CachedRowSetImpl(); 
            cachedRowSet.populate(resultado);
            cachedRowSet.last();
            
            //cachedRowSet = (CachedRowSet) Class.forName("com.sun.rowset.CachedRowSetImpl").newInstance();
            //cachedRowSet.populate(resultado);
        }catch (ClassNotFoundException |SQLException e){
            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                resultado.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //return resultado;
        return cachedRowSet;
    }
    
    public ResultSet consultarStoreProcedure(CallableStatement cs) throws ClassNotFoundException{
        try{
            cs.executeQuery();
            resultado = cs.getResultSet();
            
            cachedRowSet = new CachedRowSetImpl(); 
            cachedRowSet.populate(resultado);
            cachedRowSet.last();
        }catch (SQLException e){
            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                resultado.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return cachedRowSet;
    }
    
    public void insertDeleteUpdate_StoreProcedure(CallableStatement cs) throws ClassNotFoundException{
        try{
            cs.execute();
        }catch (SQLException e){
            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public int Count_Reg (String Sql) {
        try {
            this.creaConexion();
            this.consulta = this.conn.prepareStatement(Sql,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultado = this.consulta.executeQuery();
            System.out.println(Sql);

            while(resultado.next()) {
                if (resultado.getInt("REGISTROS") > 0){
                    registros = resultado.getInt("REGISTROS");
                }else{
                    registros = 0;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, e);
            System.err.println(e.getMessage());
            registros = -1;
        }finally{
            try {
                resultado.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return registros;
    }   
}