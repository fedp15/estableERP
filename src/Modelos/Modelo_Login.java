package Modelos;

import util.CargaComboBox;
import util.SQLSelect;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import org.apache.commons.codec.digest.DigestUtils;

public class Modelo_Login {
    private String Usuario, Clave, Empresa;
    private String CosultaEmpresa, CosultaVariables;
    private Vector Empresas;
    private Boolean result_acceso;
    
    public void setUsuario(String usuario){
        Usuario = usuario;
    }
    
    public void setClave(String clave){
        Clave = clave;
    }
    
    public void setEmpresa(String empresa){
        Empresa = empresa;
    }
    
    public void setSqlEmpresa(String sql){
        CosultaEmpresa = sql;
    }
    
    public void setSqlVariables(String sql){
        CosultaVariables = sql;
    }
    
    public DefaultComboBoxModel getEmpresa(){
        String sql = CosultaEmpresa;
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBox.Elementos(sql));    
        
        return mdl;
    }
    
    public Boolean getResultLogin(){
        conexion acceso = new conexion();
        
        Clave = DigestUtils.md5Hex(Clave);
        result_acceso = acceso.access(Usuario, Clave, Empresa);

        return result_acceso;
    }
    
    public Object[][] getCargaVariables(){
        SQLSelect elemen = new SQLSelect();
        Object element[][] = elemen.SQLSelect(CosultaVariables);
        
        return element;
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
        ImageIcon[] Banderas = { new ImageIcon(System.getProperty("user.dir").replace("\\", "/")+"/build/classes/imagenes/costa_rica_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir").replace("\\", "/")+"/build/classes/imagenes/united_states_flag.png"),
                                 new ImageIcon(System.getProperty("user.dir").replace("\\", "/")+"/build/classes/imagenes/venezuela_flag.png")};
        
        return Banderas;
    }
}
