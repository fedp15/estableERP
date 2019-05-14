package Modelos;

import Controlador.ControladorAdministrador;
import Vista.Empresas;
import Vista.MenuPrincipal;
import static Vista.MenuPrincipal.Maestro;
import static Vista.MenuPrincipal.Menus;
import static Vista.MenuPrincipal.SubMenus;
import static Vista.MenuPrincipal.SubMenus2;
import static Vista.MenuPrincipal.SubMenus3;
import static Vista.MenuPrincipal.barra;
import static Vista.MenuPrincipal.escritorioERP;
import Vista.PermisologiaUsuarioEmpresas;
import Vista.Permisologias;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.plaf.basic.BasicMenuBarUI;
import static util.ColorApp.colorForm;
import static util.ColorApp.colorText;
import util.Internacionalizacion;
import util.ValidarJornada;

/**
 *
 * @author Andres
 */
public class CargaMenu extends conexion {
    private ResultSet rs_raiz=null, rs_hijos=null, rs_nietos=null, rs_bisnietos=null, rs_bisnietos1=null, rs_bisnietos2=null;
    private Vector Msg, elementos, header_table, Menu;
    private int veces=0, PosIniParamet=0, PosFinParamet=0, pos=0;
    
    private char _toCompare;
    private char [] caracteres;
    private JSeparator sep = new JSeparator();
    
    private String Evento, metodo = "", clase="", Paramet="", pr="", Dato="", Dato1="";;
    private String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
    private String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
    //private String nombreOpc;
    
    ModelMenuERP modelMenuERP = ModelMenuERP.getModelMenuERP();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    private final ControladorAdministrador controladorAdministrador = new ControladorAdministrador();
    
    private MenuPrincipal menuERP = null;
    
    public void CargaMenuPrincipal(boolean lMenu) throws SQLException {
        //********************************* Menu Raiz *********************************
        try {
            sep.setForeground(Color.decode(colorText));
            sep.setBackground(Color.WHITE);

            String sql_raiz = "SELECT MEN_ID,SUB_MEN_ID,MEN_NOMBRE,MEN_DESCRIPCION,MEN_URL,MEN_ORDEN,MEN_TIPO,"+
                              "MEN_IMG,MEN_EXTERNO,MEN_ESTMENU FROM DNPERMISOLOGIA "+
                              "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                              "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND MIS_PERMISO="+VarGlobales.getGrupPermiso()+" AND "+
                              "SUB_MEN_ID=0 AND MEN_TIPO=2 AND MIS_ACTIVO='1' ORDER BY MEN_ID ASC;";
            rs_raiz = consultar(sql_raiz);
            
            rs_raiz.beforeFirst();
            while (rs_raiz.next()){
                String Menu_Raiz = rs_raiz.getString("MEN_NOMBRE").trim();
    
                String output_Menu_Raiz = Menu_Raiz;
                for (int i=0; i<original.length(); i++) {
                    // Reemplazamos los caracteres especiales.
                    output_Menu_Raiz = output_Menu_Raiz.replace(original.charAt(i), ascii.charAt(i));
                }
                /*
                //***************** Obtengo el Idioma del Menu *****************
                File xmlFileMenu = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Menu.xml");
                ReadFileXml xml_menu = new ReadFileXml();
                Menu = xml_menu.ReadFileXml(xmlFileMenu, "Menu", "form_principal_"+output_Menu_Raiz.replace(" ", "_"));
                //**************************************************************
                
                Menus = new JMenu((String) Menu.get(0));   // Agrego cada unos de los Menus Raiz
                */
                String nombreOpc = traduccionPanel(rs_raiz.getString("MEN_NOMBRE").trim());
                //Menus = new JMenu(rs_raiz.getString("MEN_NOMBRE").trim());
                Menus = new JMenu(nombreOpc);
Menus.setOpaque(true);
Menus.setBackground(Color.decode(colorText));
Menus.setForeground(Color.decode(colorForm));

//********************************* Menu Hijos *********************************
                //Busca si el Menu Raiz posee 1er SubMenu
                String sql_hijo = "SELECT MEN_ID,SUB_MEN_ID,MEN_NOMBRE,MEN_DESCRIPCION,MEN_URL,MEN_ORDEN,MEN_TIPO,"+
                                  "MEN_IMG,MEN_EXTERNO,MEN_ESTMENU FROM DNPERMISOLOGIA "+
                                  "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                                  "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND SUB_MEN_ID="+rs_raiz.getString("MEN_ID")+" AND "+
                                  "MIS_PERMISO="+VarGlobales.getGrupPermiso()+" AND MEN_TIPO=2 AND MIS_ACTIVO='1' ORDER BY MEN_ID ASC;";
                rs_hijos = consultar(sql_hijo);
            
                rs_hijos.beforeFirst();
                while (rs_hijos.next()){
                    final String Menu_Hijo = rs_hijos.getString("MEN_NOMBRE").trim();
                    final String Evento1 = rs_hijos.getString("MEN_URL");

                    metodo = Evento1; veces=0; PosIniParamet=0; PosFinParamet=0;
                    CargaDatos(rs_hijos);

                    final String Evt_Ext = rs_hijos.getString("MEN_EXTERNO");
                    final String Img_Menu = rs_hijos.getString("MEN_IMG");
                    final int NumParamet=veces;
                    final String Parametros=Paramet;
                    final String Metodo = metodo;
                    final String Clase = clase;

//********************************* Menu Nietos *********************************
                    //Busca si el 1er SubMenu del Menu Raiz tiene un 2do SubMenus
                    String sql_nietos = "SELECT MEN_ID,SUB_MEN_ID,MEN_NOMBRE,MEN_DESCRIPCION,MEN_URL,MEN_ORDEN,MEN_TIPO,"+
                                        "MEN_IMG,MEN_EXTERNO,MEN_ESTMENU FROM DNPERMISOLOGIA "+
                                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                                        "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND SUB_MEN_ID="+rs_hijos.getString("MEN_ID")+" AND "+
                                        "MIS_PERMISO="+VarGlobales.getGrupPermiso()+" AND MEN_TIPO=2 AND MIS_ACTIVO='1' ORDER BY MEN_ID ASC;";
                    rs_nietos = consultar(sql_nietos);
                    
                    String output_Menu_Hijo = Menu_Hijo;
                    for (int i=0; i<original.length(); i++) {
                        // Reemplazamos los caracteres especiales.
                        output_Menu_Hijo = output_Menu_Hijo.replace(original.charAt(i), ascii.charAt(i));
                    }
                    /*
                    //***************** Obtengo el Idioma del Menu *****************
                    File xmlFileMenu1 = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Menu.xml");
                    ReadFileXml xml_menu1 = new ReadFileXml();
                    Menu = xml_menu1.ReadFileXml(xmlFileMenu1, "Menu", "form_principal_"+output_Menu_Hijo.replace(" ", "_"));
                    //**************************************************************
*/
                    String nombreOpc1 = traduccionPanel(rs_hijos.getString("MEN_NOMBRE").trim());
                    if (rs_nietos.getRow()>0){
                        // Si el 1er SubMenu posee un 2do SubMenu agrego los datos
                        SubMenus = new JMenu(rs_hijos.getString("MEN_NOMBRE").trim());   //Agrego el 2do SubMenus del Menu Raiz

                        SubMenus.setOpaque(true);
                        //SubMenus.setBackground(Color.decode(colorText));
                        SubMenus.setBackground(Color.WHITE);
                        //SubMenus.setForeground(Color.decode(colorForm));
                        SubMenus.setForeground(Color.decode(colorText));

                        if (rs_hijos.getString("MEN_ESTMENU").trim().equals("0")){
                            SubMenus.setEnabled(false);
                        }

                        SubMenus.setText(nombreOpc1);
                        Menus.add(SubMenus);   //Agrego las Opciones del 2do SubMenus del Menu Raiz correspondiente

                        //Menus.addSeparator();
                        Menus.add(sep);
                        
                        if (Img_Menu!=null){
                            SubMenus.setIcon(new ImageIcon(System.getProperty("user.dir")+Img_Menu.trim()));
                        }
                    }else{
                        // Si el 1er SubMenu no posee un 2do SubMenu le agrego a las Opciones el evento que realizara
                        SubMenus = new JMenuItem(rs_hijos.getString("MEN_NOMBRE").trim());   //Agrego la Opcion al 1er SubMenu del Menu Raiz

                        SubMenus.setOpaque(true);
                        //SubMenus.setBackground(Color.decode(colorText));
                        //SubMenus.setForeground(Color.decode(colorForm));
                        SubMenus.setBackground(Color.WHITE);
                        SubMenus.setForeground(Color.decode(colorText));

                        if (rs_hijos.getString("MEN_ESTMENU").trim().equals("0")){
                            SubMenus.setEnabled(false);
                        }

                        SubMenus.setText(nombreOpc1);
                        Menus.add(SubMenus);   //Agrego la Opcion al 1er SubMenu del Menu Raiz correspodiente

                        //Menus.addSeparator();
                        Menus.add(sep);
                        
                        if (Img_Menu!=null){
                            SubMenus.setIcon(new ImageIcon(System.getProperty("user.dir")+Img_Menu.trim()));
                        }
                            
                        SubMenus.addActionListener(new ActionListener() {  //Cargamos el Evento de la Opcion del Menu
                            public void actionPerformed( ActionEvent evento ){
                                System.out.println("Listener: "+Menu_Hijo);
                                CargarEventos(Evento1, Evt_Ext, Metodo, Clase, NumParamet, Parametros, Menu_Hijo);
                            }
                        });
                    }
            
                    rs_nietos.beforeFirst();
                    while (rs_nietos.next()){
                        final String Menu_Nietos = rs_nietos.getString("MEN_NOMBRE").trim();
                        final String Evento2 = rs_nietos.getString("MEN_URL");

                        metodo = Evento2; veces=0; PosIniParamet=0; PosFinParamet=0;
                        CargaDatos(rs_nietos);

                        final String Evt_Ext1 = rs_nietos.getString("MEN_EXTERNO");
                        final String Img_Menu1 = rs_nietos.getString("MEN_IMG");
                        final int NumParamet1=veces;
                        final String Parametros1=Paramet;
                        final String Metodo1 = metodo;
                        final String Clase1 = clase;

//********************************* Menu Bisnietos *********************************
                        //Busca si el 2do SubMenu del Menu Raiz tiene un 3er SubMenus
                        String sql_bisnietos = "SELECT MEN_ID,SUB_MEN_ID,MEN_NOMBRE,MEN_DESCRIPCION,MEN_URL,MEN_ORDEN,MEN_TIPO,"+
                                               "MEN_IMG,MEN_EXTERNO,MEN_ESTMENU FROM DNPERMISOLOGIA "+
                                               "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                                               "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND SUB_MEN_ID="+rs_nietos.getString("MEN_ID")+" AND MIS_PERMISO="+VarGlobales.getGrupPermiso()+" AND "+
                                               "MEN_TIPO=2 AND MIS_ACTIVO='1' ORDER BY MEN_ID ASC;";
                        rs_bisnietos = consultar(sql_bisnietos);

                        String output_Menu_Nietos = Menu_Nietos;
                        for (int i=0; i<original.length(); i++) {
                            // Reemplazamos los caracteres especiales.
                            output_Menu_Nietos = output_Menu_Nietos.replace(original.charAt(i), ascii.charAt(i));
                        }
                        /*
                        //***************** Obtengo el Idioma del Menu *****************
                        File xmlFileMenu2 = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Menu.xml");
                        ReadFileXml xml_menu2 = new ReadFileXml();
                        Menu = xml_menu2.ReadFileXml(xmlFileMenu2, "Menu", "form_principal_"+output_Menu_Nietos.replace(" ", "_"));
                        //**************************************************************
*/
                        String nombreOpc2 = traduccionPanel(rs_nietos.getString("MEN_NOMBRE").trim());
                        if (rs_bisnietos.getRow()>0){
                            // Si el 2do SubMenu posee un 3er SubMenu agrego los datos
                            SubMenus2 = new JMenu(rs_nietos.getString("MEN_NOMBRE").trim());   //Agrego el 3er SubMenus del Menu Raiz

                            SubMenus2.setOpaque(true);
                            //SubMenus2.setBackground(Color.decode(colorText));
                            //SubMenus2.setForeground(Color.decode(colorForm));
                            SubMenus2.setBackground(Color.WHITE);
                            SubMenus2.setForeground(Color.decode(colorText));

                            if (rs_nietos.getString("MEN_ESTMENU").trim().equals("0")){
                                SubMenus2.setEnabled(false);
                            }
                        
                            SubMenus2.setText(nombreOpc2);
                            SubMenus.add(SubMenus2);   //Agrego las Opciones del 3er SubMenus del Menu Raiz correspondiente

                            //SubMenus.add(new JSeparator());
                            SubMenus.add(sep);
                            
                            if (Img_Menu1!=null){
                                SubMenus2.setIcon(new ImageIcon(System.getProperty("user.dir")+Img_Menu1.trim()));
                            }
                        }else{
                            // Si el 2do SubMenu no posee un 3er SubMenu le agrego a las Opciones el evento que realizara
                            SubMenus2 = new JMenuItem(rs_nietos.getString("MEN_NOMBRE").trim());   //Agrego la Opcion al 2do SubMenu del Menu Raiz

                            SubMenus2.setOpaque(true);
                            //SubMenus2.setBackground(Color.decode(colorText));
                            //SubMenus2.setForeground(Color.decode(colorForm));
                            SubMenus2.setBackground(Color.WHITE);
                            SubMenus2.setForeground(Color.decode(colorText));

                            if (rs_nietos.getString("MEN_ESTMENU").trim().equals("0")){
                                SubMenus2.setEnabled(false);
                            }
                            
                            SubMenus2.setText(nombreOpc2);
                            SubMenus.add(SubMenus2);   //Agrego la Opcion al 2do SubMenu del Menu Raiz correspodiente

                            //SubMenus.add(new JSeparator());
                            SubMenus.add(sep);
                            
                            if (Img_Menu1!=null){
                                SubMenus2.setIcon(new ImageIcon(System.getProperty("user.dir")+Img_Menu1.trim()));
                            }
                            
                            SubMenus2.addActionListener(new ActionListener() {  // clase interna anónima
                                public void actionPerformed( ActionEvent evento ){
                                    CargarEventos(Evento2, Evt_Ext1, Metodo1, Clase1, NumParamet1, Parametros1, Menu_Nietos);
                                }
                            });
                        }
                        
                        rs_bisnietos.beforeFirst();
                        while (rs_bisnietos.next()){
                            final String Menu_Bisnietos = rs_bisnietos.getString("MEN_NOMBRE").trim();
                            final String Evento3 = rs_bisnietos.getString("MEN_URL");

                            metodo = Evento3; veces=0; PosIniParamet=0; PosFinParamet=0;
                            CargaDatos(rs_bisnietos);

                            final String Evt_Ext2 = rs_bisnietos.getString("MEN_EXTERNO");
                            final String Img_Menu2 = rs_bisnietos.getString("MEN_IMG");
                            final int NumParamet2=veces;
                            final String Parametros2=Paramet;
                            final String Metodo2 = metodo;
                            final String Clase2 = clase;

//********************************* Menu Bisnietos1 *********************************
                            //Busca si el 3er SubMenu del Menu Raiz tiene un 4to SubMenus
                            String sql_bisnietos2 = "SELECT MEN_ID,SUB_MEN_ID,MEN_NOMBRE,MEN_DESCRIPCION,MEN_URL,MEN_ORDEN,MEN_TIPO,"+
                                                    "MEN_IMG,MEN_EXTERNO,MEN_ESTMENU FROM DNPERMISOLOGIA "+
                                                    "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                                                    "WHERE MIS_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND SUB_MEN_ID="+rs_bisnietos.getString("MEN_ID")+" AND MIS_PERMISO="+VarGlobales.getGrupPermiso()+" AND "+
                                                    "MEN_TIPO=2 AND MIS_ACTIVO='1' ORDER BY MEN_ID ASC;";
                            rs_bisnietos1 = consultar(sql_bisnietos2);
                            
                            String output_Menu_Bisnietos = Menu_Bisnietos;
                            for (int i=0; i<original.length(); i++) {
                                // Reemplazamos los caracteres especiales.
                                output_Menu_Bisnietos = output_Menu_Bisnietos.replace(original.charAt(i), ascii.charAt(i));
                            }
/*
                            //***************** Obtengo el Idioma del Menu *****************
                            File xmlFileMenu3 = new File(carpeta.getAbsolutePath()+"\\"+Idioma+"_Menu.xml");
                            ReadFileXml xml_menu3 = new ReadFileXml();
                            Menu = xml_menu3.ReadFileXml(xmlFileMenu3, "Menu", "form_principal_"+output_Menu_Bisnietos.replace(" ", "_"));
                            //**************************************************************
  */                      
                            String nombreOpc3 = traduccionPanel(rs_bisnietos.getString("MEN_NOMBRE").trim());
                            if (rs_bisnietos1.getRow()>0){
                                // Si el 3er SubMenu posee un 4to SubMenu agrego los datos
                                SubMenus3 = new JMenu(rs_bisnietos.getString("MEN_NOMBRE").trim());   //Agrego el 4to SubMenus del Menu Raiz

                                SubMenus3.setOpaque(true);
                                //SubMenus3.setBackground(Color.decode(colorText));
                                //SubMenus3.setForeground(Color.decode(colorForm));
                                SubMenus3.setBackground(Color.WHITE);
                                SubMenus3.setForeground(Color.decode(colorText));

                                if (rs_bisnietos.getString("MEN_ESTMENU").trim().equals("0")){
                                    SubMenus3.setEnabled(false);
                                }

                                SubMenus3.setText(nombreOpc3);
                                SubMenus2.add(SubMenus3);   //Agrego las Opciones del 4to SubMenus del Menu Raiz correspondiente

                                //SubMenus2.add(new JSeparator());
                                SubMenus2.add(sep);
                                
                                if (Img_Menu2!=null){
                                    SubMenus3.setIcon(new ImageIcon(System.getProperty("user.dir")+Img_Menu2.trim()));
                                }
                            }else{
                                // Si el 3er SubMenu no posee un 4to SubMenu le agrego a las Opciones el evento que realizara
                                SubMenus3 = new JMenuItem(rs_bisnietos.getString("MEN_NOMBRE").trim());   //Agrego la Opcion al 3er SubMenu del Menu Raiz
                                
                                SubMenus3.setOpaque(true);
                                //SubMenus3.setBackground(Color.decode(colorText));
                                //SubMenus3.setForeground(Color.decode(colorForm));
                                SubMenus3.setBackground(Color.WHITE);
                                SubMenus3.setForeground(Color.decode(colorText));

                                if (rs_bisnietos.getString("MEN_ESTMENU").trim().equals("0")){
                                    SubMenus3.setEnabled(false);
                                }
                                
                                SubMenus3.setText(nombreOpc3);
                                SubMenus2.add(SubMenus3);   //Agrego la Opcion al 3er SubMenu del Menu Raiz correspodiente

                                //SubMenus2.add(new JSeparator());
                                SubMenus2.add(sep);
                                
                                if (Img_Menu2!=null){
                                    SubMenus3.setIcon(new ImageIcon(System.getProperty("user.dir")+Img_Menu2.trim()));
                                }
                                                        
                                SubMenus3.addActionListener(new ActionListener() {  // clase interna anónima
                                    public void actionPerformed( ActionEvent evento ){
                                        CargarEventos(Evento3, Evt_Ext2, Metodo2, Clase2, NumParamet2, Parametros2, Menu_Bisnietos);
                                    }
                                });
                            }
/*
                            SubMenus4 = new JMenuItem[rs_bisnietos1.getRow()];
                            int ind_bisnietos1 = 0;
                        
                            rs_bisnietos1.beforeFirst();
                            while (rs_bisnietos1.next()){
                                String Menu_Bisnietos1 = rs_bisnietos1.getString("MEN_NOMBRE").trim();
                                final String Evento4 = rs_bisnietos1.getString("MEN_URL");
                                ind_bisnietos1++;

                                metodo = Evento4; veces=0; PosIniParamet=0; PosFinParamet=0;
                                CargaDatos(rs_bisnietos1);

                                final String Evt_Ext3 = rs_bisnietos1.getString("MEN_EXTERNO");
                                final int NumParamet3=veces;
                                final String Parametros3=Paramet;
                                final String Metodo3 = metodo;
                                final String Clase3 = clase;

//********************************* Menu Bisnietos2 *********************************
                                //Busca si el 4to SubMenu del Menu Raiz tiene un 5to SubMenus
                                //String sql_bisnietos3 = "SELECT * FROM DNMENU WHERE SUB_MEN_ID="+rs_bisnietos2.getString("MEN_ID")+" AND MEN_TIPO=2 ORDER BY MEN_ID ASC;";
                                String sql_bisnietos3 = "SELECT MEN_ID,SUB_MEN_ID,MEN_NOMBRE,MEN_DESCRIPCION,MEN_URL,MEN_ORDEN,MEN_TIPO,"+
                                                        "MEN_IMG,MEN_EXTERNO FROM DNPERMISOLOGIA "+
                                                        "INNER JOIN DNMENU ON MIS_MENU=MEN_ID "+
                                                        "WHERE SUB_MEN_ID="+rs_bisnietos2.getString("MEN_ID")+" AND MIS_PERMISO="+Grupo_Permisos+" AND "+
                                                        "MEN_TIPO=2 AND MIS_ACTIVO='1' ORDER BY MEN_ID ASC;";
                                rs_bisnietos2 = conet.consultar(sql_bisnietos2);
                            
                                if (rs_bisnietos2.getRow()>0){
                                    // Si el 4to SubMenu posee un 5to SubMenu agrego los datos
                                    this.SubMenus4[ind_bisnietos1-1] = new JMenu(Menu_Bisnietos1);   //Agrego el 5to SubMenus del Menu Raiz
                                    this.SubMenus3[ind_bisnietos-1].add(this.SubMenus4[ind_bisnietos1-1]);   //Agrego las Opciones del 5to SubMenus del Menu Raiz correspondiente
                                }else{
                                    // Si el 4to SubMenu no posee un 5to SubMenu le agrego a las Opciones el evento que realizara
                                    this.SubMenus4[ind_bisnietos1-1] = new JMenuItem(Menu_Bisnietos1);   //Agrego la Opcion al 4to SubMenu del Menu Raiz
                                    this.SubMenus3[ind_bisnietos-1].add(this.SubMenus4[ind_bisnietos1-1]);   //Agrego la Opcion al 4to SubMenu del Menu Raiz correspodiente
                                
                                    this.SubMenus4[ind_bisnietos1-1].addActionListener(new ActionListener() {  // clase interna anónima
                                        public void actionPerformed( ActionEvent evento ){
                                            CargarEventos(Evento4, Evt_Ext3, Metodo3, Clase3, NumParamet3, Parametros3);
                                        }
                                    });
                                }
                            }
*/                            
                        }
                    }
//******************************************************************************                
                }
//******************************************************************************

                barra.setUI ( new BasicMenuBarUI (){
                    public void paint ( Graphics g, JComponent c ){
                        g.setColor(Color.decode(colorText));
                        g.fillRect( 0, 0, c.getWidth (), c.getHeight () );
                    }
                } );
                
                barra.add(Menus);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CargaMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
//*****************************************************************************
        
//*****************************************************************************

    }
    
    private void CargaDatos(ResultSet rs_datos) throws SQLException{
        if (metodo==null || metodo.isEmpty()){
            metodo="";
        }else{
            pr=metodo.trim();
            PosIniParamet = pr.indexOf("(");
            PosFinParamet = pr.indexOf(")");
                       
            _toCompare=',';
            caracteres=pr.toCharArray();
            for (int i=0; i<=caracteres.length-1;i++){
                if(_toCompare == caracteres[i]){
                    veces++;
                }
            }
                        
            if (PosIniParamet>0 && PosFinParamet>0){
                Paramet=pr.substring(PosIniParamet+1, PosFinParamet);
            }
                        
            if (veces>0){
                veces=veces+1;
            }
        }

        pos = 0;
        pos = metodo.indexOf(";");

        if (pos>0){
            clase = rs_datos.getString("MEN_URL").substring(0,(rs_datos.getString("MEN_URL").indexOf(";"))).trim();

            if (PosIniParamet>0){
                metodo = rs_datos.getString("MEN_URL").substring((rs_datos.getString("MEN_URL").indexOf(";")+1),PosIniParamet).trim();
            }else{
                metodo = rs_datos.getString("MEN_URL").substring((rs_datos.getString("MEN_URL").indexOf(";")+1),rs_datos.getString("MEN_URL").trim().length()).trim();
            }
        }else{
            metodo=null;
        }
    }
    
    private void CargarEventos(String Evento, String Evt_Ext, String Metodo, String Clase, int NumParamet, String Parametros, String Nombre_Menu){
        System.out.println(Evento.trim());
        System.out.println("Nombre: "+Nombre_Menu);
        System.out.println("Evento: "+Evento);
        System.out.println("Metodo: "+Metodo);
        System.out.println("Evt_Ext: "+Evt_Ext);
        
        if (Evento!=null || !Evento.isEmpty()){
            Class Formulario;
            try {
                if (Evt_Ext.trim().equals("1")){
                    Formulario = Class.forName(Evento.trim());
                    JFrame frame = (JFrame) Formulario.newInstance();
                    frame.setVisible(true);
                }else{
                    if (Metodo!=null){
                        try{
                            Object tempClass = Class.forName(Clase.trim()).newInstance();
                                        
                            Class claseCargada = tempClass.getClass();
                                                            
                            Method meth;
                            if (NumParamet>0){
                                Class Param[] = new Class[NumParamet];
                                                                
                                for (int i=0; i<=NumParamet-1;i++){
                                    Param[i]= String.class;
                                }
                                
                                meth = claseCargada.getDeclaredMethod(Metodo.trim(), Param);
                                Object arglist[] = new Object[NumParamet];
                                                                
                                Dato=""; Dato1="";
                                if (Parametros.length()>0){
                                    for (int i=0; i<=NumParamet-1;i++){
                                        if (i==0){
                                            Dato=Parametros.substring(0,Parametros.indexOf(","));
                                            Dato1=Parametros.substring(Parametros.indexOf(",")+1,Parametros.length());
                                        }else{
                                            if (Dato1.indexOf(",")>0){
                                                Dato=Dato1.substring(0,Dato1.indexOf(","));
                                                Dato1=Dato1.substring(Dato1.indexOf(",")+1,Dato1.length());
                                            }else{
                                                Dato=Dato1.substring(0,Dato1.length());
                                            }
                                        }
                                        arglist[i] = new String(Dato);
                                    }
                                }
                                                                
                                meth.invoke(tempClass, arglist);
                            }else{
//                                meth = claseCargada.getDeclaredMethod(Metodo.trim(), null);
//                                meth.invoke(tempClass, null);
                            }
                        }catch (Throwable e){
                            System.err.println(e);
                        }
                    }else{
                        if (Nombre_Menu.equals("Clientes")){
                            Maestro="Clie";
                        }
                        if (Nombre_Menu.equals("Proveedores")){
                            Maestro="Prov";
                        }

//                        Formulario = Class.forName(Vista.trim());
//                        JInternalFrame Form = (JInternalFrame) Formulario.newInstance();
//                        Dimension desktopSize = escritorio.getSize();
//                        Dimension jInternalFrameSize = Form.getSize();
//                        Form.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
                
//                        escritorio.add(Form);
//                        Form.show();
//                        
//                        if(Evento.trim().equals("POS.POS")){
//                            try { 
//                                Form.setMaximum(true);
//                            } catch (PropertyVetoException ex) {
//                                Logger.getLogger(MenuERP.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
                        switch(Nombre_Menu){
                            case "Productos":
                                productos();
                                
                                break;
                            case "Usuarios":
                                usuarios();
                                
                                break;
                            case "Cambio de Usuario":
                                cambioUsuario();
                                
                                break;
                            case "Grupos de Permisos":
                                grupoPermisos();
                                
                                break;
                            case "Permisologias":
                                permisologias();
                                
                                break;
                            case "Permisos de Usuarios por Empresas":
                                permisologiasGrupoEmpresas();
                                
                                break;
                            case "Clientes":
                                personas(2);
                                
                                break;
                            case "Proveedores":
                                personas(3);
                                
                                break;
                            case "Factura de Ventas":
                                documentos("FAV", Nombre_Menu);
                                
                                break;
                            case "Facturas de Compras":
                                documentos("FAC", Nombre_Menu);
                                
                                break;
                            case "Nota de Credito":
                                documentos("CRE", Nombre_Menu);
                                
                                break;
                            case "Estructura de Ctas":
                                estructura();
                                
                                break;
                            case "Plan de Cuentas":
                                plancta();
                                
                                break;
                            case "Importar Plan de Cuentas":
                                importPlanCtas();
                                
                                break;
                            case "Grupos de Asientos":
                                gruposAsientos();
                                
                                break;
                            case "Configuracion de Asientos":
                                configAsientos();
                                
                                break;
                            case "Asientos Contables":
                                asientosContables();
                                
                                break;
                            case "Aprobacion de Comprobantes":
                                apruebaComprobantes();
                                
                                break;
                            case "Asientos Directos":
                                asientoManual();
                                
                                break;
                            case "Tipo de Maestros":
                                tipomaestro();
                                
                                break;
                            case "Tipo de Contactos":
                                tipocontacto();
                                
                                break;
                            case "Actividad":
                                actividad();
                                
                                break;
                            case "Moneda":
                                moneda();
                                
                                break;
                            case "Precios":
                                precio();
                                
                                break;
                            case "Tipo de Documentos":
                                configdoc();
                                
                                break;
                            case "Configurar Documentos a Importar":
                                configImpdoc();
                                
                                break;
                            case "Cierre Contable":
                                cierrecontable();
                                
                                break;
                            case "Cierre Fiscal":
                                cierreFiscal();
                                
                                break;
                            case "Balance General":
                                reportContabilidad("Balance General");
                                
                                break;
                            case "Balance de Comprobacion":
                                reportContabilidad("Balance de Comprobacion");
                                
                                break;
                            case "Mayor Analitico":
                                reportContabilidad("Mayor Analitico");
                                
                                break;
                            case "Libro Diario":
                                reportContabilidad("Libro Diario");
                                
                                break;
                            case "Estado de Resultados":
                                reportContabilidad("Estado de Resultados");
                                
                                break;
                            case "Imprimir Cheques":
                                imprimirCheques();
                                
                                break;
                            case "Generar txt de Retencion de IVA":
                                generarTxt();
                                
                                break;
                            case "Calculo de Retenciones de IVA":
                                reporteRetIVAvta();
                                
                                break;
                            case "Generar xml de ISLR":
                                generarXml();
                                
                                break;
                            case "Imprimir ISLR por lote":
                                imprimirISLR();
                                
                                break;
                            case "Libro de Compras":
                                libro("COM");
                                
                                break;
                            case "Libro de Ventas":
                                libro("VTA");
                                
                                break;
                            case "Bancos e Instrumentos de Pago":
                                bancos();
                                
                                break;
                            case "Empresa":
                                empresa();
                                
                                break;
                            case "Punto de Venta":
                                pos();
                                
                                break;
                            case "Actualizar el Sistema":
                            
                                break;
                            case "Salir":
                                salir();
                                
                                break;
                        }
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException ex) {
                Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private String traduccionPanel(String opc){
        String traduccion = "";
        boolean lExiste = false;
        
        switch (opc) {
            case "Sistema":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuSistema");
                lExiste = true;
                
                break;
            case "Modulos":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuModulos");
                lExiste = true;
                
                break;
            case "Reportes":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuReportes");
                lExiste = true;
                
                break;
            case "Configuracion":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuConfiguracion");
                lExiste = true;
                
                break;
            case "Ayuda":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuAyuda");
                lExiste = true;
                
                break;
            case "Usuarios":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuUsuarios");
                lExiste = true;
                
                break;
            case "Cambio de Usuario":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuCambioUsuario");
                lExiste = true;
                
                break;
            case "Configurar Pemisologias":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuConfigurarPemisologias");
                lExiste = true;
                
                break;
            case "Salir":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuSalir");
                lExiste = true;
                
                break;
            case "Inventario":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuInventario");
                lExiste = true;
                
                break;
            case "Facturacion":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuFacturacion");
                lExiste = true;
                
                break;
            case "Contabilidad":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuContabilidad");
                lExiste = true;
                
                break;
            case "Ubicaciones":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuUbicaciones");
                lExiste = true;
                
                break;
            case "menuImprimirCheques":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuImprimirCheques");
                lExiste = true;
                
                break;
            case "Retenciones":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuRetenciones");
                lExiste = true;
                
                break;
            case "Conexion":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuConexion");
                lExiste = true;
                
                break;
            case "Empresa":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuEmpresa");
                lExiste = true;
                
                break;
            case "Sucursal":
                traduccion = idioma.loadLangIdiomasMenu().getString("mennuSucursal");
                lExiste = true;
                
                break;
            case "Importar Productos":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuImportarProductos");
                lExiste = true;
                
                break;
            case "Auditorias":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuAuditorias");
                lExiste = true;
                
                break;
            case "Relacion de Documentos":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuRelacionDocumentos");
                lExiste = true;
                
                break;
            case "Actualizar el Sistema":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuActualizarSistema");
                lExiste = true;
                
                break;
            case "Acerca De":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuAcercaDe");
                lExiste = true;
                
                break;
            case "Punto de Venta":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuPuntoVenta");
                lExiste = true;
                
                break;
            case "Gestor de Proyectos":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuGestorProyectos");
                lExiste = true;
                
                break;
            case "Fuerza de Ventas":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuFuerzaVentas");
                lExiste = true;
                
                break;
            case "Grupos de Permisos":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuGruposPermisos");
                lExiste = true;
                
                break;
            case "Permisologias":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuPermisologias");
                lExiste = true;
                
                break;
            case "Permisos de Usuarios por Empresas":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuPermisosUsuariosEmpresas");
                lExiste = true;
                
                break;
            case "Productos":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuProductos");
                lExiste = true;
                
                break;
            case "Ventas":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuVentas");
                lExiste = true;
                
                break;
            case "Compras":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuCompras");
                lExiste = true;
                
                break;
            case "Estructura de Ctas":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuEstructuraCtas");
                lExiste = true;
                
                break;
            case "menuPlanCuentas":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuPlanCuentas");
                lExiste = true;
                
                break;
            case "Configuracion de Asientos":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuConfiguracionAsientos");
                lExiste = true;
                
                break;
            case "Asientos Contables":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuAsientosContables");
                lExiste = true;
                
                break;
            case "Aprobacion de Comprobantes":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuAprobacionComprobantes");
                lExiste = true;
                
                break;
            case "Grupos de Asientos":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuPuntoVenta");
                lExiste = true;
                
                break;
            case "Cierre Contable":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuCierreContable");
                lExiste = true;
                
                break;
            case "Asientos Directos":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuAsientosDirectos");
                lExiste = true;
                
                break;
            case "Cierre Fiscal":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuCierreFiscal");
                lExiste = true;
                
                break;
            case "Tipo de Maestros":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuTipoMaestros");
                lExiste = true;
                
                break;
            case "Tipo de Contactos":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuTipoContactos");
                lExiste = true;
                
                break;
            case "Actividad":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuActividad");
                lExiste = true;
                
                break;
            case "Moneda":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuMoneda");
                lExiste = true;
                
                break;
            case "Precios":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuPrecios");
                lExiste = true;
                
                break;
            case "Unidad de Medida":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuUnidadMedida");
                lExiste = true;
                
                break;
            case "Grupo":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuGrupo");
                lExiste = true;
                
                break;
            case "Tipo Documentos":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuTipoDocumentos");
                lExiste = true;
                
                break;
            case "Vendedores":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuVendedores");
                lExiste = true;
                
                break;
            case "Personalizar":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuPersonalizar");
                lExiste = true;
                
                break;
            case "Marcas":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuMarcas");
                lExiste = true;
                
                break;
            case "Categorias":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuCategorias");
                lExiste = true;
                
                break;
            case "Descuentos":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuDescuentos");
                lExiste = true;
                
                break;
            case "Vendedor":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuVendedor");
                lExiste = true;
                
                break;
            case "Crear Conexion a Base de Datos":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuCrearConexionBaseDatos");
                lExiste = true;
                
                break;
            case "Respaldar Base de Datos":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuRespaldarBaseDatos");
                lExiste = true;
                
                break;
            case "Restaurar Base de Datos":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuRestaurarBaseDatos");
                lExiste = true;
                
                break;
            case "Importar Plan de Cuentas":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuImportarPlanCuentas");
                lExiste = true;
                
                break;
            case "Clientes":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuClientes");
                lExiste = true;
                
                break;
            case "Factura de Ventas":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuFacturaVentas");
                lExiste = true;
                
                break;
            case "Proveedores":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuProveedores");
                lExiste = true;
                
                break;
            case "Facturas de Compras":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuFacturasCompras");
                lExiste = true;
                
                break;
            case "Documentos":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuDocumentos");
                lExiste = true;
                
                break;
            case "Devolucion de Compras":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuDevolucionCompras");
                lExiste = true;
                
                break;
            case "Balance General":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuBalanceGeneral");
                lExiste = true;
                
                break;
            case "Balance de Comprobacion":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuBalanceComprobacion");
                lExiste = true;
                
                break;
            case "Mayor Analitico":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuMayorAnalitico");
                lExiste = true;
                
                break;
            case "Libro Diario":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuLibroDiario");
                lExiste = true;
                
                break;
            case "Estado de Resultados":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuEstadoResultados");
                lExiste = true;
                
                break;
            case "Control de Supervisor":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuControlSupervisor");
                lExiste = true;
                
                break;
            case "Configurar Documentos":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuConfigurarDocumentos");
                lExiste = true;
                
                break;
            case "Configurar Caja":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuConfigurarCaja");
                lExiste = true;
                
                break;
            case "Unidades de Medida":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuUnidadesMedida");
                lExiste = true;
                
                break;
            case "Bancos e Instrumentos de Pago":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuBancosInstrumentosPago");
                lExiste = true;
                
                break;
            case "Jornadas y Turnos":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuJornadasTurnos");
                lExiste = true;
                
                break;
            case "Clientes/Proveedores":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuClientesProveedores");
                lExiste = true;
                
                break;
            case "Imprimir ISLR por lote":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuImprimirISLRlote");
                lExiste = true;
                
                break;
            case "Generar xml de ISLR":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuGenerarxmlISLR");
                lExiste = true;
                
                break;
            case "Generar txt de Retencion de IVA":
                traduccion = idioma.loadLangIdiomasMenu().getString("menuGenerartxtRetencionIVA");
                lExiste = true;
                
                break;
        }
        if(!lExiste){
            traduccion = opc;
        }
        return traduccion;
    }
    
    private void usuarios(){
//        CreaUsuarios crearUsuarios = new CreaUsuarios("ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = crearUsuarios.getSize();
//        crearUsuarios.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(crearUsuarios);
//        crearUsuarios.toFront();
//        crearUsuarios.setVisible(true);
    }
    
    private void grupoPermisos(){
//        GruposPermisos gruposPermisos = new GruposPermisos("ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = gruposPermisos.getSize();
//        gruposPermisos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(gruposPermisos);
//        gruposPermisos.toFront();
//        gruposPermisos.setVisible(true);
    }
    
    private void permisologias(){
        Permisologias permisologias = new Permisologias("ERP");

        Dimension desktopSize = escritorioERP.getSize();
        Dimension jInternalFrameSize = permisologias.getSize();
        permisologias.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

        escritorioERP.add(permisologias);
        permisologias.toFront();
        permisologias.setVisible(true);
    }
    
    private void permisologiasGrupoEmpresas(){
        PermisologiaUsuarioEmpresas permisologiaUsuarioEmpresas = new PermisologiaUsuarioEmpresas("ERP");

        Dimension desktopSize = escritorioERP.getSize();
        Dimension jInternalFrameSize = permisologiaUsuarioEmpresas.getSize();
        permisologiaUsuarioEmpresas.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

        escritorioERP.add(permisologiaUsuarioEmpresas);
        permisologiaUsuarioEmpresas.toFront();
        permisologiaUsuarioEmpresas.setVisible(true);
    }
    
    private void marcas(){
//        Marcas marcas = new Marcas(false, "ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = marcas.getSize();
//        marcas.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(marcas);
//        marcas.toFront();
//        marcas.setVisible(true);
    }
    
    private void bancos(){
//        BancosInsPagos bancos = new BancosInsPagos("ERP", "");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = bancos.getSize();
//        bancos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(bancos);
//        bancos.toFront();
//        bancos.setVisible(true);
    }
    
    private void categorias(){
//        Categorias categorias = new Categorias(false, "ERP", "");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = categorias.getSize();
//        categorias.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(categorias);
//        categorias.toFront();
//        categorias.setVisible(true);
    }
    
    private void descuentos(){
//        PlanDeCuentas desc = new PlanDeCuentas();
//        DescuentosArticulos desc = new DescuentosArticulos("ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = desc.getSize();
//        desc.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(desc);
//        desc.toFront();
//        desc.setVisible(true);
    }
    
    private void configdoc(){
//        TipoDocumentos tipdoc = new TipoDocumentos("ERP", "");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = tipdoc.getSize();
//        tipdoc.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(tipdoc);
//        tipdoc.toFront();
//        tipdoc.setVisible(true);
    }
    
    private void configImpdoc(){
//        ConfigImport config = new ConfigImport();
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = config.getSize();
//        config.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(config);
//        config.toFront();
//        config.setVisible(true);
    }
    
    private void configasiento(){
//        ConfigAsientos config = new ConfigAsientos("ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = config.getSize();
//        config.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(config);
//        config.toFront();
//        config.setVisible(true);
    }
    
    private void productos(){
//        Productos pro = new Productos(false, "ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = pro.getSize();
//        pro.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(pro);
//        pro.toFront();
//        pro.setVisible(true);
    }
    
    private void estructura(){
//        EstructuraCtas estr = new EstructuraCtas();
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = estr.getSize();
//        estr.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(estr);
//        estr.toFront();
//        estr.setVisible(true);
    }
    
    private void plancta(){
//        PlanDeCuentas plan = new PlanDeCuentas();
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = plan.getSize();
//        plan.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(plan);
//        plan.toFront();
//        plan.setVisible(true);
    }
    
    private void importPlanCtas(){
//        ProgressImportCtas impCta = new ProgressImportCtas();
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = impCta.getSize();
//        impCta.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(impCta);
//        impCta.toFront();
//        impCta.setVisible(true);
    }
    
    private void configAsientos(){
//        ConfigAsientos configAsientos = new ConfigAsientos("ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = configAsientos.getSize();
//        configAsientos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(configAsientos);
//        configAsientos.toFront();
//        configAsientos.setVisible(true);
    }
    
    private void gruposAsientos(){
//        GrupoAsientos grupoAsientos = new GrupoAsientos();
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = grupoAsientos.getSize();
//        grupoAsientos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(grupoAsientos);
//        grupoAsientos.toFront();
//        grupoAsientos.setVisible(true);
    }
    
    private void asientosContables(){
        //AsientosContables asientosContables = new AsientosContables();

        //Dimension desktopSize = escritorioERP.getSize();
        //Dimension jInternalFrameSize = asientosContables.getSize();
        //asientosContables.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

        //escritorioERP.add(asientosContables);
        //asientosContables.toFront();
        //asientosContables.setVisible(true);
        
        JOptionPane.showMessageDialog(null, "En desarrollo pronto para Mostrar", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void apruebaComprobantes(){
//        AprobacionComprobates aprobacionComprobates = new AprobacionComprobates();
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = aprobacionComprobates.getSize();
//        aprobacionComprobates.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(aprobacionComprobates);
//        aprobacionComprobates.toFront();
//        aprobacionComprobates.setVisible(true);
    }
    
    private void asientoManual(){
//        AsientoManual asientoManual = new AsientoManual("ERP");
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = asientoManual.getSize();
//        asientoManual.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(asientoManual);
//        asientoManual.toFront();
//        asientoManual.setVisible(true);
    }
    
    private void tipomaestro(){
//        TipoCliPro tipo = new TipoCliPro(8);
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = tipo.getSize();
//        tipo.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(tipo);
//        tipo.toFront();
//        tipo.setVisible(true);
    }
    
    private void precio(){
//        Precio precio = new Precio("ERP", "");
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = precio.getSize();
//        precio.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(precio);
//        precio.toFront();
//        precio.setVisible(true);
    }
    
    private void tipocontacto(){
//        TipoContacto tipo = new TipoContacto();
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = tipo.getSize();
//        tipo.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(tipo);
//        tipo.toFront();
//        tipo.setVisible(true);
    }
    
    private void cierrecontable(){
//        CierreContable cierre = new CierreContable("ERP");
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = cierre.getSize();
//        cierre.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(cierre);
//        cierre.toFront();
//        cierre.setVisible(true);
    }
    
    private void cierreFiscal(){
//        CierreFiscal cierreFiscal = new CierreFiscal();
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = cierreFiscal.getSize();
//        cierreFiscal.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(cierreFiscal);
//        cierreFiscal.toFront();
//        cierreFiscal.setVisible(true);
    }
    
    private void pos(){
        menuERP = (MenuPrincipal) modelMenuERP.getVista();
        menuERP.dispose();
        
        ResultSet jornada = null;
        int hora, minutos, dia, result,apertura,confirm;
        String horaSis, diaSis = "";
        Calendar horario = new GregorianCalendar();
        
        hora = horario.get(Calendar.HOUR_OF_DAY);
        minutos = horario.get(Calendar.MINUTE);
        dia = horario.get(Calendar.DAY_OF_WEEK);
        
        horaSis = String.valueOf(hora)+":"+String.valueOf(minutos);
        
        if(dia==1){
            diaSis = "Domingo";
        }
        if(dia==2){
            diaSis = "Lunes";
        }
        if(dia==3){
            diaSis = "Martes";
        }
        if(dia==4){
            diaSis = "Miercoles";
        }
        if(dia==5){
            diaSis = "Jueves";
        }
        if(dia==6){
            diaSis = "Viernes";
        }
        if(dia==7){
            diaSis = "Sabado";
        }
        
        ValidarJornada valida = new ValidarJornada(horaSis, diaSis);
        result = valida.consulta();
        
        if(result==1){
            apertura = controladorAdministrador.validaApertura(diaSis);
            
            if(apertura == 1){
//                POS pos = new POS();
//                pos.show();
//                pos.setExtendedState(pos.MAXIMIZED_BOTH); // Para mostrer el Formulario Maximizado
//                pos.setVisible(true);
            }else{
               confirm = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgNoHayApert"),idioma.loadLangMsg().getString("MsgTituloNotif"),JOptionPane.YES_NO_OPTION);
                if(confirm==0){
//                    ConfigSuper supervisor = new ConfigSuper();
//                    supervisor.Verifica("EMP_APERT", "NoAsignaApertura");
                } 
            }
        }
    }
    
    private void reportContabilidad(String reporte){
//        ReporteContabilidad reporteContabilidad = null;
//        
//        switch(reporte){
//            case "Balance General":
//                reporteContabilidad = new ReporteContabilidad("Balance General", "Balance General");
//                
//                break;
//            case "Balance de Comprobacion":
//                reporteContabilidad = new ReporteContabilidad("Balance de Comprobacion", "Balance de Comprobacion");
//                
//                break;
//            case "Mayor Analitico":
//                reporteContabilidad = new ReporteContabilidad("Mayor Analitico", "Mayor Analitico");
//                
//                break;
//            case "Libro Diario":
//                reporteContabilidad = new ReporteContabilidad("Libro Diario", "Libro Diario");
//                
//                break;
//            case "Estado de Resultados":
//                reporteContabilidad = new ReporteContabilidad("Estado de Resultados", "Estado de Resultados");
//                
//                break;
//        }
        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = reporteContabilidad.getSize();
//        reporteContabilidad.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(reporteContabilidad);
//        reporteContabilidad.toFront();
//        reporteContabilidad.setVisible(true);
    }
    
    private void imprimirCheques(){
//        ImprimirCheque cheques = new ImprimirCheque("ERP");
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = cheques.getSize();
//        cheques.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(cheques);
//        cheques.toFront();
//        cheques.setVisible(true);
    }
    
    private void generarTxt(){
//        txtRetencionesIVA txtIva = new txtRetencionesIVA();
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = txtIva.getSize();
//        txtIva.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(txtIva);
//        txtIva.toFront();
//        txtIva.setVisible(true);
    }
    
    private void reporteRetIVAvta(){
//        ReporteRetIVAventas repVta = new ReporteRetIVAventas();
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = repVta.getSize();
//        repVta.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(repVta);
//        repVta.toFront();
//        repVta.setVisible(true);
    }
    
    private void generarXml(){
//        xmlISLR xmlIslr = new xmlISLR();
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = xmlIslr.getSize();
//        xmlIslr.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(xmlIslr);
//        xmlIslr.toFront();
//        xmlIslr.setVisible(true);
    }
    
    private void imprimirISLR(){
//        reporteISLRlote repIslr = new reporteISLRlote("ERP");
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = repIslr.getSize();
//        repIslr.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(repIslr);
//        repIslr.toFront();
//        repIslr.setVisible(true);
    }
    
    private void libro(String origen){
//        LibroComVta libro = new LibroComVta(origen);
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = libro.getSize();
//        libro.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(libro);
//        libro.toFront();
//        libro.setVisible(true);
    }
    
    private void empresa(){
        Empresas empresas = new Empresas(original);
        
        Dimension desktopSize = escritorioERP.getSize();
        Dimension jInternalFrameSize = empresas.getSize();
        empresas.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

        escritorioERP.add(empresas);
        empresas.toFront();
        empresas.setVisible(true);
    }
    
    private void moneda(){
//        Moneda moneda = new Moneda("", "");
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = moneda.getSize();
//        moneda.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(moneda);
//        moneda.toFront();
//        moneda.setVisible(true);
    }
    
    private void importarplan(){
//        ProgressImportCtas impCta = new ProgressImportCtas();
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = impCta.getSize();
//        impCta.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(impCta);
//        impCta.toFront();
//        impCta.setVisible(true);
    }
    
    private void personas(int idRol){
        //Proveedor cli = new Proveedor("ERP");
//        Proveedores cli = new Proveedores(idRol, false);
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = cli.getSize();
//        cli.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(cli);
//        cli.toFront();
//        cli.setVisible(true);
    }
    
    private void actividad(){
//        Actividad actividad = new Actividad();
//
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = actividad.getSize();
//        actividad.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(actividad);
//        actividad.toFront();
//        actividad.setVisible(true);
    }
    
    private void documentos(String tipdoc, String titulo){
//        Documentos docs = new Documentos(tipdoc, titulo, "ERP");
//        
//        Dimension desktopSize = escritorioERP.getSize();
//        Dimension jInternalFrameSize = docs.getSize();
//        docs.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//        escritorioERP.add(docs);
//        docs.toFront();
//        docs.setVisible(true);
    }
    
    private void salir(){
        JInternalFrame[] array = escritorioERP.getAllFrames();
        int FormActivos = array.length;

        if (FormActivos>0){
            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgCloseForm"), 
                                                idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                                JOptionPane.WARNING_MESSAGE);
            return;
        }
        int salir = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgSalirSistema"),
                                                    idioma.loadLangMsg().getString("MsgNotificacion"),
                                                    JOptionPane.YES_NO_OPTION);

        if(salir == 0){
            System.exit(0);
        }
    }
    
    private void cambioUsuario(){
        JInternalFrame[] array = escritorioERP.getAllFrames();
        int FormActivos = array.length;

        if (FormActivos>0){
            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgCloseForm"), 
                                                idioma.loadLangMsg().getString("MsgTituloNotif"), 
                                                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        menuERP = (MenuPrincipal) modelMenuERP.getVista();
        menuERP.dispose();
        
        modelMenuERP.setlReloadMenu(false);

        try {
            new Vista.Login().setVisible(true);
        } catch (MalformedURLException ex) {
            Logger.getLogger(CargaMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}