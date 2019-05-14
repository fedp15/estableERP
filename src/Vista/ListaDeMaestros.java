package Vista;

import Controlador.ControladorBuscarMaestros;
import util.CargaTablas;
import static util.ColorApp.colorForm;
import Modelos.ModelCreaUsuarios;
import Modelos.ModelEmpresas;
import Modelos.ModelListaMaestros;
import Modelos.VariablesGlobales;
import Modelos.conexion;
import static Vista.MenuPrincipal.escritorioERP;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import javax.swing.JDesktopPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import util.Internacionalizacion;

//public class ListaDeMaestros extends javax.swing.JInternalFrame {
public class ListaDeMaestros extends javax.swing.JDialog{
    public DefaultTableModel m;
    CargaTablas cargatabla = null;
    public Object [] opciones={"Aceptar","Cancelar"};
    public boolean lSave=false;
    private String condic=null, tablaBuscar= "", origen, origenDesk, codSelec="";
    private int fsel=-1;
    private static int idRol;
    private DefaultMutableTreeNode NodoSeleccionado;
    private JDesktopPane desktopP;
    
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();

    private final ControladorBuscarMaestros buscarMaestros = ControladorBuscarMaestros.getControladorBuscarMaestros();
    private final ModelListaMaestros modelListaMaestros = ModelListaMaestros.getModelListaMaestros();
//    private final ModelCategorias modelCategorias = ModelCategorias.getModelCategorias();
//    private final ModelCargos modelCargos = ModelCargos.getModelCargos();
//    private final ModelProductos modelProductos = ModelProductos.getModelProductos();
//    private final ModelInventario modelInventario = ModelInventario.getModelInventario();
//    private final ModelDescuentos modelDescuentos = ModelDescuentos.getModelDescuentos();
//    private final ModelDocumentos modelDocumentos = ModelDocumentos.getModelDocumentos();
//    private final ModelRepVentas modelRepVentas = ModelRepVentas.getModelRepVentas();
//    private final ModelRepStock modelRepStock = ModelRepStock.getModelRepStock();
//    private final ModelRepCodBarra modelRepCodBarra = ModelRepCodBarra.getModelRepCodBarra();
//    private final ModelUnidadMedida modelUnidadMedida = ModelUnidadMedida.getModelUnidadMedida();
//    private final ModelBancosInsPagos modelBancosInsPagos = ModelBancosInsPagos.getModelBancosInsPagos();
//    private final ModelJornadas modelJornadas = ModelJornadas.getModelJornadas();
//    private final ModelCreaProveedor modelCreaProveedor = ModelCreaProveedor.getModelCreaProveedor();
//    private final ModelRepEtiquetaCaja modelRepEtiquetaCaja = ModelRepEtiquetaCaja.getModelRepEtiquetaCaja();
//    private final ModelRelacionDocBL modelRelacionDocBL = ModelRelacionDocBL.getModelRelacionDocBL();
//    private final ModelUbicacionProductos modelUbicacionProductos = ModelUbicacionProductos.getmodelUbicacionProductos();
    private final ModelCreaUsuarios modelCreaUsuarios = ModelCreaUsuarios.getModelGrupoPermisos();
//    private final ModelreporteISLRlote modelreporteIslr = ModelreporteISLRlote.getModelreporteISLRlote();
//    private final ModelProveedores modelProveedores = ModelProveedores.getModelProveedores();
        
//    private final ModelPos modelPos = ModelPos.getModelPos();
    //private static ModelActionListener modelUsuario = ModelActionListener.getPrueba();
    private static ModelEmpresas modelEmpresas = ModelEmpresas.getModelEmpresas();
//    private static ModelMarcas modelMarca = ModelMarcas.getModelMarcas();
//    private final ModelMarcasVehiculo modelMarcasVehiculo = ModelMarcasVehiculo.getModelMarcasVehiculo();
//    private final ModelCambiarClave modelCambiarClave = ModelCambiarClave.getModelCambiarClave();
//    private final ModelComisionVendedor modelComisionVendedor = ModelComisionVendedor.getModelComisionVendedor();
//    private final ModelGrupoPermisos modelGrupoPermisos = ModelGrupoPermisos.getModelGrupoPermisos();
//    private final ModelVendedor modelVendedor = ModelVendedor.getModelVendedor();
//    private final ModelPrecio modelPrecio = ModelPrecio.getModelPrecio();
//    private final ModelTipoDocumentos modelTipoDocumentos = ModelTipoDocumentos.getModelTipoDocumentos();
//    private final ModelConfigAsientos modelConfigAsientos = ModelConfigAsientos.getModelConfigAsientos();
//    private final ModelAsientoManual modelAsientoManual = ModelAsientoManual.getModelAsientoManual();
//    private final ModelImprimirCheque modelImprimirCheque = ModelImprimirCheque.getModelImprimirCheque();
//    private final ModelRetencionIVA modelRetencionIva = ModelRetencionIVA.getModelRetencionIVA();
//    private final ModelRetencionISLR modelRetencionIslr = ModelRetencionISLR.getModelRetencionISLR();
    //private final ModelRetencionIvaVentas modelRetencionIvaventas = ModelRetencionIvaVentas.getModelRetencionIvaVentas();
//    private final ModelPagoDocs modelPagoDocs = ModelPagoDocs.getModelPagoDocs();
//    private final ModelRepRetIvaVta modelRepIvaVta = ModelRepRetIvaVta.getModelRepRetIvaVta();
//    private final ModelModeloVehiculo modelModeloVehiculo = ModelModeloVehiculo.getModelModeloVehiculo();
//    private final ModelCategoriasVehiculo modelCategoriasVehiculo = ModelCategoriasVehiculo.getModelCategoriasVehiculo();
//    private final ModelMotor modelMotor = ModelMotor.getModelMotor();
//    private final ModelTipoMotor modelTipoMotor = ModelTipoMotor.getModelTipoMotor();
//    private final ModelTransmision modelTransmision = ModelTransmision.getModelTransmision();
//    private final ModelTraccion modelTraccion = ModelTraccion.getModelTraccion();
//    private final ModelTipoCilindrada modelTipoCilindrada = ModelTipoCilindrada.getModelTipoCilindrada();
//    private final ModelModeloCilindrada modelModeloCilindrada = ModelModeloCilindrada.getModelModeloCilindrada();
//    private final ModelCombustible modelCombustible = ModelCombustible.getModelCombustible();
//    private final ModelVehiculo modelVehiculo = ModelVehiculo.getModelVehiculo();
//    private final ModelTipoCliPro modelTipoCliPro = ModelTipoCliPro.getmodelTipoCliPro();
//    private final ModelImpuestos modelImpuestos = ModelImpuestos.getModelImpuestos();
//    private final ModelTipoContacto modelTipoContacto = ModelTipoContacto.getModelTipoContacto();
//    private final ModelMoneda modelMoneda = ModelMoneda.getModelMoneda();
//    private final ModelCbtesElectronicos modelCbtesElectronicos = ModelCbtesElectronicos.getModelCbtesElectronicos();
//    private final ModelTecnico modelTecnico = ModelTecnico.getModelTecnico();
//    private final ModelMaestroReportesSistema modelMaestroReportesSistema = ModelMaestroReportesSistema.getModelMaestroReportesSistema();
//    private final ModelOrdenReparacion modelOrdenReparacion = ModelOrdenReparacion.getModelOrdenReparacion();
//    private final ModelCuentasPorCobrarPagar modelCuentasPorCobrarPagar = ModelCuentasPorCobrarPagar.getModelCuentasPorCobrarPagar();
    
    private Object aThis;
    private static Boolean lBackSpace = false;
    
//    private final ControladorInventario controladorInventario = new ControladorInventario();

    private conexion conc = new conexion();
    private ResultSet rs = null;
    
//    private final Categorias categorias = null;
//    private final Cargos cargos = null;
//    private final Productos productos = null;
//    private final DescuentosArticulos descuentos = null;
    private final Empresas empresas = null;
//    private final Marcas marca = null;
//    private final MarcasVehiculos  marcasVehiculos = null;
    private final CreaUsuarios usuarios = null;
    
    public ListaDeMaestros(String tabla, String orig, String deskOrigen) {
        initComponents();
        origen = orig;
        this.tablaBuscar = tabla;
        this.origenDesk = deskOrigen;
        
        if(origenDesk.equals("ERP")){
            desktopP = escritorioERP;
        }else if(origen.equals("Welcome")){
//            desktopP = desktop;
        }else{
//            desktopP = escritorio;
        }
        
        jPanel1.setBackground(Color.decode(colorForm));
        
        btnSelect.setBackground(Color.decode(colorForm)); btnCancel.setBackground(Color.decode(colorForm));
        btnAgregarM.setBackground(Color.decode(colorForm));
//        btnSelect.setForeground(Color.decode(colorText)); btnCancel.setForeground(Color.decode(colorText));
//        btnAgregarM.setForeground(Color.decode(colorText));
        
//        jLabel1.setForeground(Color.decode(colorText)); jLabel2.setForeground(Color.decode(colorText));
//        jLeyenda1.setForeground(Color.decode(colorText)); jLeyenda2.setForeground(Color.decode(colorText));
        
//        txt_nombre.setForeground(Color.decode(colorText)); txt_rif.setForeground(Color.decode(colorText));
        
        txt_rif.setText(""); 
        txt_nombre.setText("");
        
        jLabel1.setText(idioma.loadLangComponent().getString("lbId"));
        jLabel2.setText(idioma.loadLangComponent().getString("lbNombre"));
        jLeyenda1.setText(idioma.loadLangComponent().getString("lbLeyenda1"));
        jLeyenda2.setText(idioma.loadLangComponent().getString("lbLeyenda2"));
        btnSelect.setText(idioma.loadLangComponent().getString("btnSelect"));
        btnAgregarM.setText(idioma.loadLangComponent().getString("btnAddMaestro"));
        btnCancel.setText(idioma.loadLangComponent().getString("btnCancelar"));
        
        modelListaMaestros.setTfId(txt_rif);
        modelListaMaestros.setTableMaestros(tbl_maestros);
        loadTablas();
        
//******************** Codigo para Cerrar el Formulario con la tecla ESC ********************
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), "Cancel");
        
        getRootPane().getActionMap().put("Cancel", new javax.swing.AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                close();
            }
        });
//*******************************************************************************************
        
//******************** Codigo para Teclas Rapidas ********************
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0), "Codigo");
        
        getRootPane().getActionMap().put("Codigo", new javax.swing.AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                txt_rif.requestFocus();
                txt_rif.setText(""); txt_nombre.setText("");
            }
        });
        
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0), "Nombre");
        
        getRootPane().getActionMap().put("Nombre", new javax.swing.AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                txt_nombre.requestFocus();
                txt_rif.setText(""); txt_nombre.setText("");
            }
        });
        
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0), "Seleccion");
        
        getRootPane().getActionMap().put("Seleccion", new javax.swing.AbstractAction(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                action_select_maestro();
            }
        });
        
        txt_rif.requestFocus();
//********************************************************************
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_maestros = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jLabel1 = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JTextField();
        txt_rif = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnCancel = new com.l2fprod.common.swing.JLinkButton();
        btnSelect = new com.l2fprod.common.swing.JLinkButton();
        btnAgregarM = new com.l2fprod.common.swing.JLinkButton();
        jLeyenda1 = new javax.swing.JLabel();
        jLeyenda2 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tbl_maestros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbl_maestros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_maestrosMouseClicked(evt);
            }
        });
        tbl_maestros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_maestrosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_maestrosKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_maestros);

        jLabel1.setText("ID: ");

        txt_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nombreKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombreKeyTyped(evt);
            }
        });

        txt_rif.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_rifFocusGained(evt);
            }
        });
        txt_rif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_rifKeyPressed(evt);
            }
        });

        jLabel2.setText("Nombre:");

        jPanel5.setBackground(new java.awt.Color(105, 105, 105));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));

        btnCancel.setBackground(new java.awt.Color(105, 105, 105));
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete_bar_butto_1.png"))); // NOI18N
        btnCancel.setText("Cancelar");
        btnCancel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSelect.setBackground(new java.awt.Color(105, 105, 105));
        btnSelect.setForeground(new java.awt.Color(255, 255, 255));
        btnSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/select_bar_butto.png"))); // NOI18N
        btnSelect.setText("Seleccionar");
        btnSelect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSelect.setPreferredSize(new java.awt.Dimension(86, 74));
        btnSelect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });

        btnAgregarM.setBackground(new java.awt.Color(105, 105, 105));
        btnAgregarM.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add_bar_butto_5.png"))); // NOI18N
        btnAgregarM.setText("Agregar Maestro");
        btnAgregarM.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregarM.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAgregarM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMActionPerformed(evt);
            }
        });

        jLeyenda1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLeyenda1.setForeground(new java.awt.Color(255, 255, 255));
        jLeyenda1.setText("Leyenda 1 de teclas Rapidas");

        jLeyenda2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLeyenda2.setForeground(new java.awt.Color(255, 255, 255));
        jLeyenda2.setText("Leyanda 2 de Teclas Rapidas");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLeyenda2)
                    .addComponent(jLeyenda1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregarM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAgregarM, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btnSelect, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLeyenda1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLeyenda2)))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_rif, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 167, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_rif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombreKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            busquedaNombre();
        }
    }//GEN-LAST:event_txt_nombreKeyPressed

    private void busquedaNombre(){
        String codigo=null, nombre=null;
        double limit_cre=0, Dcto=0, DiasVen=0;
        Object element[][] = null;

        nombre = txt_nombre.getText().toUpperCase();

//        if (modelCategorias.getVista() instanceof Categorias) {
//            switch (tablaBuscar){
//                case "DNCLASIFICACION":{
//                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelCategorias.getVista(), "DNCLASIFICACION");
//
//                    element = elementos;
//                    break;
//                }
//                case "DNCLASIFICACION_PADRE":{
//                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelCategorias.getVista(), "DNCLASIFICACION_PADRE");
//
//                    element = elementos;
//                    break;
//                }
//                case "DNCLASIFICACION_HIJO":{
//                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelCategorias.getVista(), "DNCLASIFICACION_HIJO");
//
//                    element = elementos;
//                    break;
//                }
//            }
//        }else if (modelProductos.getVista() instanceof Productos) {
//            switch (tablaBuscar) {
//                case "DNMARCA":
//                    {
//                        Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelMarca.getVista(), "DNMARCA");
//                        element = elementos;
//                        break;
//                    }
//                case "dnpersonas":
//                    {
//                        Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelProductos.getVista(), "dnpersonas");
//                        element = elementos;
//                        break;
//                    }
//                case "DNPRODUCTO":
//                    {
//                        Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelProductos.getVista(), "DNPRODUCTO");
//                        element = elementos;
//                        break;
//                    }
//                case "GRUPO":
//                    {
//                        Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelProductos.getVista(), "GRUPO");
//                        element = elementos;
//                        break;
//                    }
//                case "SUBGRUPO":
//                    {
//                        Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelProductos.getVista(), "SUBGRUPO");
//                        element = elementos;
//                        break;
//                    }
//            }
////        }else if (modelDescuentos.getVista() instanceof DescuentosArticulos){
////            if (tablaBuscar.equals("DNPRODUCTO")){
////                Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelDescuentos.getVista(), "DNPRODUCTO");
////
////                element = elementos;
////            }
////            if(tablaBuscar.equals("DNDESCUENTOS")){
////                Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelDescuentos.getVista(), "DNDESCUENTOS");
////
////                element = elementos;
////            }
//        }else if (modelInventario.getVista() instanceof Inventario) {
//            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelInventario.getVista(), "DNPRODUCTO");
//
//            element = elementos;
//        }else if (modelDocumentos.getVista() instanceof Facturacion) {
//            if (tablaBuscar.equals("DNPERSONAS")){
//                Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelDocumentos.getVista(), "DNPERSONAS");
//
//                element = elementos;
//            }
//        }else if (modelCbtesElectronicos.getVista() instanceof VisualizarCbteElectronicos) {
//            if (tablaBuscar.equals("DNPERSONAS")){
//                Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelCbtesElectronicos.getVista(), "DNPERSONAS");
//
//                element = elementos;
//            }
//        }else if (modelTecnico.getVista() instanceof Tecnico) {
//            if (tablaBuscar.equals("tecnico")){
//                Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelTecnico.getVista(), "tenico");
//
//                element = elementos;
//            }
//        }else if (modelMaestroReportesSistema.getVista() instanceof MaestroDeReportesSistema) {
//            switch (tablaBuscar) {
//                case "DNPERSONAS":
//                    {
//                        Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelMaestroReportesSistema.getVista(), "DNPERSONAS");
//                        element = elementos;
//                        break;
//                    }
//                case "DNINVENTARIO":
//                    {
//                        Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelMaestroReportesSistema.getVista(), "DNINVENTARIO");
//                        element = elementos;
//                        break;
//                    }
//            }
//        }else if (modelPos.getVista() instanceof POS){
//            if (tablaBuscar.equals("DNVENDEDOR")){
//                Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelDocumentos.getVista(), "DNVENDEDOR");
//
//                element = elementos;
//            }
//        }else if (modelRepVentas.getVista() instanceof ReporteVentas){
//            if (tablaBuscar.equals("DNVENDEDOR")){
//                Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelRepVentas.getVista(), "DNVENDEDOR");
//
//                element = elementos;
//            }
//        }else if (modelRepStock.getVista() instanceof ReporteStock){
//            if (tablaBuscar.equals("DNPRODUCTO")){
//                Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelRepStock.getVista(), "DNPRODUCTO");
//
//                element = elementos;
//            }
//        }else if (modelRepCodBarra.getVista() instanceof ReporteCodBarra){
//            if (tablaBuscar.equals("DNPRODUCTO")){
//                Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelRepCodBarra.getVista(), "DNPRODUCTO");
//
//                element = elementos;
//            }
//        }else 
        if (modelCreaUsuarios.getVista() instanceof CreaUsuarios){
            if (tablaBuscar.equals("DNUSUARIOS")){
                Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelCreaUsuarios.getVista(), "DNUSUARIOS");

                element = elementos;
            }
//        }else if (modelCargos.getVista() instanceof Cargos) {
//            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelCargos.getVista(), "DNCARGOS");
//
//            element = elementos;
//        }else if (modelCreaProveedor.getVista() instanceof CreaProveedor){
//            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelCargos.getVista(), "DNMAESTRO");
//
//            element = elementos;
        }else if (modelEmpresas.getVista() instanceof Empresas){
            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelEmpresas.getVista(), "DNEMPRESAS");

            element = elementos;
//        }else if (modelMarca.getVista() instanceof Marcas){
//            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelMarca.getVista(), "DNMARCA");
//
//            element = elementos;
//        }else if (modelMarcasVehiculo.getVista() instanceof MarcasVehiculos){
//            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelMarcasVehiculo.getVista(), "DNMARCA");
//
//            element = elementos;
//        }else if (modelModeloVehiculo.getVista() instanceof ModeloVehiculo){
//            switch(tablaBuscar){
//                case "DNMARCA":{
//                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelModeloVehiculo.getVista(), "DNMARCA");
//                    element = elementos;
//
//                    break;
//                }
//                case "MODELO_VEHICULO":{
//                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelModeloVehiculo.getVista(), "MODELO_VEHICULO");
//                    element = elementos;
//
//                    break;
//                }
//                case "MODELO_VEHICULO_2":{
//                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelModeloVehiculo.getVista(), "MODELO_VEHICULO_2");
//                    element = elementos;
//
//                    break;
//                }
//            }
//        }else if (modelCategoriasVehiculo.getVista() instanceof CategoriasVehiculos){
//            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelCategoriasVehiculo.getVista(), "CATEGORIAS_VEHICULO");
//
//            element = elementos;
//        }else if (modelUbicacionProductos.getVista() instanceof UbicacionProductos){
//            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelUbicacionProductos.getVista(), "DNUBICACION_PRODUCTOS");
//
//            element = elementos;
//        }else if (modelCambiarClave.getVista() instanceof CambiarClave){
//            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelCambiarClave.getVista(), "DNUSUARIOS");
//
//            element = elementos;
        }else if (modelCreaUsuarios.getVista() instanceof CreaUsuarios){
            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelCreaUsuarios.getVista(), "DNUSUARIOS");

            element = elementos;
        }
//        else if (modelComisionVendedor.getVista() instanceof ComisionVendedor){
//            switch(tablaBuscar){
//                case "DNVENDEDOR":{
//                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelComisionVendedor.getVista(), "DNVENDEDOR");
//                    element = elementos;
//
//                    break;
//                }
//                case "DNVENDEDOR2":{
//                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelComisionVendedor.getVista(), "DNVENDEDOR2");
//                    element = elementos;
//
//                    break;
//                }
//            }
//        }else if (modelGrupoPermisos.getVista() instanceof GruposPermisos){
//            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelGrupoPermisos.getVista(), "DNPERMISO_GRUPAL");
//            element = elementos;
////        }else if (modelVendedor.getVista() instanceof Vendedor){
////            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelVendedor.getVista(), "DNVENDEDOR");
////            element = elementos;
//        }else if (modelPrecio.getVista() instanceof Precio){
//            switch(tablaBuscar){
//                case "DNPRODUCTO":{
//                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelPrecio.getVista(), "DNPRODUCTO");
//                    element = elementos;
//                    break;
//                }
//                case "DNLISTPRE":{
//                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelPrecio.getVista(), "DNLISTPRE");
//                    element = elementos;
//                    break;
//                }
//                case "DNUNDMEDIDA":{
//                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelPrecio.getVista(), "DNUNDMEDIDA");
//                    element = elementos;
//                    break;
//                }
//            }
//        }else if(modelTipoDocumentos.getVista() instanceof TipoDocumentos){
//            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelTipoDocumentos.getVista(), "DNDOCUMENTOS");
//            element = elementos;
//        }else if(modelConfigAsientos.getVista() instanceof ConfigAsientos){
//            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelConfigAsientos.getVista(), "DNCONFIG_CONTABLE");
//            element = elementos;
//        }else if(modelAsientoManual.getVista() instanceof AsientoManual){
//            switch(tablaBuscar){
//                case "DNINVENTARIO":{
//                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelAsientoManual.getVista(), "DNINVENTARIO");
//                    element = elementos;
//
//                    break;
//                }
//                case "DNPERSONAS":{
//                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelAsientoManual.getVista(), "DNPERSONAS");
//                    element = elementos;
//
//                    break;
//                }
//            }
//        }else if(modelImprimirCheque.getVista() instanceof ImprimirCheque){
//            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelImprimirCheque.getVista(), "DNPERSONAS");
//            element = elementos;
//        }else if(modelRetencionIva.getVista() instanceof RetencionIVA){
//            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelRetencionIva.getVista(), "DNPERSONAS");
//            element = elementos;
//        }else if(modelRetencionIslr.getVista() instanceof RetencionISLR){
//            switch(tablaBuscar){
//                case "DNPERSONAS":{
//                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelRetencionIslr.getVista(), "DNPERSONAS");
//                    element = elementos;
//                    break;
//                }
//                case "DNEMPRESAS":{
//                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelRetencionIslr.getVista(), "DNEMPRESAS");
//                    element = elementos;
//                    break;
//                }
//            }
//        }else if(modelreporteIslr.getVista() instanceof reporteISLRlote){
//            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelreporteIslr.getVista(), "DNPERSONAS");
//            element = elementos;
//        }
////            else if(modelRetencionIvaventas.getVista() instanceof RetencionIVAventas){
////                Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelRetencionIvaventas.getVista(), "DNPERSONAS");
////                element = elementos;
////            }
//        else if(modelPagoDocs.getVista() instanceof PagoDocs){
//            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelPagoDocs.getVista(), "DNPERSONAS");
//            element = elementos;
//        }else if(modelProveedores.getVista() instanceof Proveedores){
//            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelProveedores.getVista(), "DNPERSONAS");
//            element = elementos;
//        }else if(modelRepIvaVta.getVista() instanceof ReporteRetIVAventas){
//            switch(tablaBuscar){
//                case "DNEMPRESAS":{
//                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelRepIvaVta.getVista(), "DNEMPRESAS");
//                    element = elementos;
//                    break;
//                }
//                case "DNPERSONAS":{
//                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelRepIvaVta.getVista(), "DNPERSONAS");
//                    element = elementos;
//                    break;
//                }
//            }
//        }

        codigo = element[0][0].toString().trim();
        nombre = element[0][1].toString().trim();

        this.txt_rif.setText(codigo.trim());
        this.txt_nombre.setText(nombre.trim());

        ActualizaJtable(txt_rif.getText().trim());
    }
    
    private void txt_rifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rifKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            ActualizaJtable(txt_rif.getText().trim());
        }
    }//GEN-LAST:event_txt_rifKeyPressed

    private void tbl_maestrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_maestrosMouseClicked
        System.out.println(tbl_maestros.getSelectedRow());
        if(tbl_maestros.getSelectedRow()>-1){
            if (evt.getButton()==1){
                action_tablas(tbl_maestros.getSelectedRow());
            }
        
            if(evt.getClickCount()==2){
                action_select_maestro();
            }
        }
    }//GEN-LAST:event_tbl_maestrosMouseClicked

    private void tbl_maestrosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_maestrosKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            action_tablas(tbl_maestros.getSelectedRow());
            tbl_maestros.getSelectionModel().setSelectionInterval(tbl_maestros.getSelectedRow(), tbl_maestros.getSelectedRow());
        }
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            action_tablas(tbl_maestros.getSelectedRow());
            tbl_maestros.getSelectionModel().setSelectionInterval(tbl_maestros.getSelectedRow(), tbl_maestros.getSelectedRow());
        }
    }//GEN-LAST:event_tbl_maestrosKeyReleased

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        loadTablas();
        txt_rif.requestFocus();
    }//GEN-LAST:event_formInternalFrameActivated

    private void txt_rifFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_rifFocusGained
        System.err.println("Gano el foco");
        txt_rif.requestFocus();
        loadTablas();
    }//GEN-LAST:event_txt_rifFocusGained

    private void tbl_maestrosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_maestrosKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            action_select_maestro();
        }
    }//GEN-LAST:event_tbl_maestrosKeyPressed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        close();
        
//        modelCargos.setVista(null);
//        modelProductos.setVista(null);        
//        modelCategorias.setVista(null);
//        modelComisionVendedor.setVista(null);
//        modelCreaProveedor.setVista(null);
//        modelDescuentos.setVista(null);
//        modelBancosInsPagos.setVista(null);
//        modelUbicacionProductos.setVista(null);
//        modelMarca.setVista(null);
//        modelMarcasVehiculo.setVista(null);
//        modelCargos.setVista(null);
        modelCreaUsuarios.setVista(null);
        modelEmpresas.setVista(null);
//        modelInventario.setVista(null);
//        modelJornadas.setVista(null);
//        modelRelacionDocBL.setVista(null);
//        modelRepCodBarra.setVista(null);
//        modelRepEtiquetaCaja.setVista(null);
//        modelRepStock.setVista(null);
//        modelRepVentas.setVista(null);
//        modelUnidadMedida.setVista(null);
//        modelGrupoPermisos.setVista(null);
//        modelVendedor.setVista(null);
//        modelPrecio.setVista(null);
//        modelTipoDocumentos.setVista(null);
//        modelConfigAsientos.setVista(null);
//        modelAsientoManual.setVista(null);
//        modelImprimirCheque.setVista(null);
//        modelRetencionIva.setVista(null);
//        modelRetencionIslr.setVista(null);
//        modelreporteIslr.setVista(null);
//        modelRetencionIvaventas.setVista(null);
//        modelPagoDocs.setVista(null);
//        modelRepIvaVta.setVista(null);
//        modelModeloVehiculo.setVista(null);
//        modelCategoriasVehiculo.setVista(null);
//        modelMotor.setVista(null);
//        modelTipoMotor.setVista(null);
//        modelTransmision.setVista(null);
//        modelTraccion.setVista(null);
//        modelTipoCilindrada.setVista(null);
//        modelModeloCilindrada.setVista(null);
//        modelCombustible.setVista(null);
//        modelVehiculo.setVista(null);
//        modelTipoCliPro.setVista(null);
//        modelImpuestos.setVista(null);
//        modelTipoContacto.setVista(null);
//        modelMoneda.setVista(null);
//        modelCbtesElectronicos.setVista(null);
//        modelTecnico.setVista(null);
//        modelMaestroReportesSistema.setVista(null);
//        modelOrdenReparacion.setVista(null);
//        modelDocumentos.setVista(null);
//        modelCuentasPorCobrarPagar.setVista(null);
        
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        action_select_maestro();
    }//GEN-LAST:event_btnSelectActionPerformed

    private void btnAgregarMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMActionPerformed
        agregarMaestro();
    }//GEN-LAST:event_btnAgregarMActionPerformed

    private void txt_nombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombreKeyTyped
//        char c=evt.getKeyChar();
//        String cad = "";
//
//        if (Character.isLowerCase(c)){
//            cad = (""+c).toUpperCase();
//            c=cad.charAt(0);
//            evt.setKeyChar(c);
//            
//            if(!lBackSpace){
//                modelVehiculo.buscaMotorSerial(cad);
//            }else{
//                modelVehiculo.setMotorSerial(txt_nombre.getText());
//                modelVehiculo.buscaMotorSerial(cad);
//            }
//        }else{
//            cad = (""+c);
//
//            if(!lBackSpace){
//                modelVehiculo.buscaMotorSerial(cad);
//            }else{
//                lBackSpace=false;
//                modelVehiculo.setMotorSerial(txt_nombre.getText());
//                modelVehiculo.buscaMotorSerial(cad);
//            }
//        }
    }//GEN-LAST:event_txt_nombreKeyTyped

    private void loadTablas(){
//        if (modelCategorias.getVista() instanceof Categorias) {
//            switch (tablaBuscar) {
//                case "DNCLASIFICACION":
//                    buscarMaestros.cargarTabla(modelCategorias.getVista(), tbl_maestros, "DNCLASIFICACION", null, null);
//                    btnAgregarM.setVisible(false);
//                    
//                    break;
//                case "DNCLASIFICACION_PADRE":
//                    buscarMaestros.cargarTabla(modelCategorias.getVista(), tbl_maestros, "DNCLASIFICACION_PADRE", null, null);
//                    
//                    break;
//                case "DNCLASIFICACION_HIJO":
//                    buscarMaestros.cargarTabla(modelCategorias.getVista(), tbl_maestros, "DNCLASIFICACION_HIJO", null, null);
//                    
//                    break;
//            }
//        }else if (modelProductos.getVista() instanceof Productos) {
//            switch (tablaBuscar) {
//                case "DNMARCA":
//                    //buscarMaestros.cargarTabla(modelMarca.getVista(), tbl_maestros, "DNMARCA", null, null);
//                    buscarMaestros.cargarTabla(modelProductos.getVista(), tbl_maestros, "DNMARCA", null, null);
//                    break;
//                case "dnpersonas":
//                    buscarMaestros.cargarTabla(modelProductos.getVista(), tbl_maestros, "dnpersonas", null, null);
//                    break;
//                case "DNPRODUCTO":
//                    buscarMaestros.cargarTabla(modelProductos.getVista(), tbl_maestros, "DNPRODUCTO", null, null);
//                    break;
//                case "GRUPO":
//                    buscarMaestros.cargarTabla(modelProductos.getVista(), tbl_maestros, "GRUPO", null, null);
//                    break;
//                case "SUBGRUPO":
//                    buscarMaestros.cargarTabla(modelProductos.getVista(), tbl_maestros, "SUBGRUPO", null, null);
//                    break;
//            }
////        }else if (modelDescuentos.getVista() instanceof DescuentosArticulos){
////            switch (tablaBuscar) {
////                case "DNPRODUCTO":
////                    buscarMaestros.cargarTabla(modelDescuentos.getVista(), tbl_maestros, "DNPRODUCTO", null, null);
////                    
////                    break;
////                case "DNDESCUENTOS":
////                    buscarMaestros.cargarTabla(modelDescuentos.getVista(), tbl_maestros, "DNDESCUENTOS", null, null);
////                    
////                    break;
////            }
//        }else if (modelInventario.getVista() instanceof Inventario){
//            switch (tablaBuscar) {
//                case "DNINVENTARIO":
//                    buscarMaestros.cargarTabla(modelInventario.getVista(), tbl_maestros, "DNINVENTARIO", null, null);
//                    break;
//                case "DNPRODUCTO":
//                    buscarMaestros.cargarTabla(modelInventario.getVista(), tbl_maestros, "DNPRODUCTO", null, null);
//                    break;
//            }
//        }else if (modelDocumentos.getVista() instanceof Facturacion){
//            switch (tablaBuscar) {
//                case "DNPERSONAS":
//                    buscarMaestros.cargarTabla(modelDocumentos.getVista(), tbl_maestros, "DNPERSONAS", "", "");
//                    //buscarMaestros.cargarTabla(modelDocumentos.getVista(), tbl_maestros, "DNPERSONAS", modelDocumentos.getTipomae(), "");
//                    break;
//                case "DNINVENTARIO":
//                    System.out.println("Origen Listar: "+origen);
//                    if(origen.equals("Doc")){
//                        jLabel1.setText(idioma.loadLangHeaderTable().getString("documento"));
//                        txt_nombre.setEnabled(false);
//                                
//                        buscarMaestros.cargarTabla(modelDocumentos.getVista(), tbl_maestros, "DNINVENTARIO", origen, origenDesk);
//                    }else{
////                        buscarMaestros.cargarTabla(modelDocumentos.getVista(), tbl_maestros, "DNINVENTARIO", modelDocumentos.getDocimport(), 
////                                                   modelDocumentos.getTxtProveedor().getText());
//                    }
//
//                    break;
//                case "DNCONFIGIMPORT":
//                    buscarMaestros.cargarTabla(modelDocumentos.getVista(), tbl_maestros, "DNCONFIGIMPORT", modelDocumentos.getTipdoc(), null);
//                    break;
//                case "import":
//                    buscarMaestros.cargarTabla(modelDocumentos.getVista(), tbl_maestros, "import", modelDocumentos.getTipdoc(), null);
//                    break;
//                case "dnproducto":
//                    buscarMaestros.cargarTabla(modelDocumentos.getVista(), tbl_maestros, "DNPRODUCTO", null, null);
//                    break;
//            }       
//        }else if (modelCuentasPorCobrarPagar.getVista() instanceof CuentasPorCobrarPagar){
//            switch (tablaBuscar) {
//                case "DNPERSONAS":
//                    buscarMaestros.cargarTabla(modelCuentasPorCobrarPagar.getVista(), tbl_maestros, "DNPERSONAS", "", "");
//                    
//                    break;
//            }       
//        }else if (modelCbtesElectronicos.getVista() instanceof VisualizarCbteElectronicos) {
//            switch (tablaBuscar) {
//                case "DNPERSONAS":
//                    buscarMaestros.cargarTabla(modelCbtesElectronicos.getVista(), tbl_maestros, "DNPERSONAS", "", "");
//
//                    break;
//            }       
//        }else if (modelTecnico.getVista() instanceof Tecnico) {
//            buscarMaestros.cargarTabla(modelTecnico.getVista(), tbl_maestros, "tecnico", "", "");
//        }else if (modelMaestroReportesSistema.getVista() instanceof MaestroDeReportesSistema) {
//            switch (tablaBuscar) {
//                case "DNPERSONAS":
//                    buscarMaestros.cargarTabla(modelMaestroReportesSistema.getVista(), tbl_maestros, "DNPERSONAS", "", "");
//
//                    break;
//                case "DNINVENTARIO":
//                    buscarMaestros.cargarTabla(modelMaestroReportesSistema.getVista(), tbl_maestros, "DNINVENTARIO", "", "");
//
//                    break;
//            }
//        }else if (modelRepVentas.getVista() instanceof ReporteVentas){
//            if(tablaBuscar.equals("DNVENDEDOR")){
//                buscarMaestros.cargarTabla(modelRepVentas.getVista(), tbl_maestros, "DNVENDEDOR", null, null);
//            }
//        }else if (modelPos.getVista() instanceof POS){
//            buscarMaestros.cargarTabla(modelPos.getVista(), tbl_maestros, "DNVENDEDOR", null, null);
//        }else if (modelRepStock.getVista() instanceof ReporteStock){
//            if (tablaBuscar.equals("DNPRODUCTO")){
//                buscarMaestros.cargarTabla(modelRepStock.getVista(), tbl_maestros, "DNPRODUCTO", null, null);
//            }
//        }else if (modelRepCodBarra.getVista() instanceof ReporteCodBarra){
//            if (tablaBuscar.equals("DNPRODUCTO")){
//                buscarMaestros.cargarTabla(modelRepCodBarra.getVista(), tbl_maestros, "DNPRODUCTO", null, null);
//            }
//        }else if (modelUnidadMedida.getVista() instanceof UnidadMedida){
//            if(tablaBuscar.equals("DNUNDMEDIDA")){
//                buscarMaestros.cargarTabla(modelUnidadMedida.getVista(), tbl_maestros, "DNUNDMEDIDA", null, null);
//            }
//        }else if (modelBancosInsPagos.getVista() instanceof BancosInsPagos){
//            if(tablaBuscar.equals("DNBANCOS")){
//                buscarMaestros.cargarTabla(modelBancosInsPagos.getVista(), tbl_maestros, "DNBANCOS", null, null);
//            }else if(tablaBuscar.equals("DNINSTRUMENTOPAGO")){
//                buscarMaestros.cargarTabla(modelBancosInsPagos.getVista(), tbl_maestros, "DNINSTRUMENTOPAGO", null, null);
//            }
//        }else if (modelJornadas.getVista() instanceof Jornadas){
//            if(tablaBuscar.equals("DNJORNADA")){
//                buscarMaestros.cargarTabla(modelJornadas.getVista(), tbl_maestros, "DNBANCOS", null, null);
//            }
//        }else if (modelCargos.getVista() instanceof Cargos) {
//            buscarMaestros.cargarTabla(modelCargos.getVista(), tbl_maestros, "DNCARGOS", null, null);
//        }else 
        if (modelCreaUsuarios.getVista() instanceof CreaUsuarios){
            if(tablaBuscar.equals("DNUSUARIOS")){
                buscarMaestros.cargarTabla(modelCreaUsuarios.getVista(), tbl_maestros, "DNUSUARIOS", null, null);
            }
//        }else if (modelCreaProveedor.getVista() instanceof CreaProveedor){
//            buscarMaestros.cargarTabla(modelCreaProveedor.getVista(), tbl_maestros, "DNMAESTRO", null, null);
        }else if (modelEmpresas.getVista() instanceof Empresas){
            buscarMaestros.cargarTabla(modelEmpresas.getVista(), tbl_maestros, "DNEMPRESAS", null, null);
//        }else if (modelMarca.getVista() instanceof Marcas){
//            buscarMaestros.cargarTabla(modelMarca.getVista(), tbl_maestros, "DNMARCA", null, null);
//        }else if (modelMarcasVehiculo.getVista() instanceof MarcasVehiculos){
//            buscarMaestros.cargarTabla(modelMarcasVehiculo.getVista(), tbl_maestros, "DNMARCA", null, null);
//        }else if (modelModeloVehiculo.getVista() instanceof ModeloVehiculo){
//            switch(tablaBuscar){
//                case "DNMARCA":{
//                    buscarMaestros.cargarTabla(modelModeloVehiculo.getVista(), tbl_maestros, "DNMARCA", null, null);
//
//                    break;
//                }
//                case "MODELO_VEHICULO":{
//                    buscarMaestros.cargarTabla(modelModeloVehiculo.getVista(), tbl_maestros, "MODELO_VEHICULO", null, null);
//
//                    break;
//                }
//                case "MODELO_VEHICULO_2":{
//                    buscarMaestros.cargarTabla(modelModeloVehiculo.getVista(), tbl_maestros, "MODELO_VEHICULO_2", null, null);
//
//                    break;
//                }
//            }
//        }else if (modelCategoriasVehiculo.getVista() instanceof CategoriasVehiculos){
//            buscarMaestros.cargarTabla(modelCategoriasVehiculo.getVista(), tbl_maestros, "CATEGORIAS_VEHICULO", null, null);
//        }else if (modelMotor.getVista() instanceof Motor){
//            switch(tablaBuscar){
//                case "MOTOR":{
//                    buscarMaestros.cargarTabla(modelMotor.getVista(), tbl_maestros, "MOTOR", null, null);
//
//                    break;
//                }
//                case "TIPO_MOTOR":{
//                    buscarMaestros.cargarTabla(modelMotor.getVista(), tbl_maestros, "TIPO_MOTOR", null, null);
//
//                    break;
//                }
//                case "MARCA":{
//                    buscarMaestros.cargarTabla(modelMotor.getVista(), tbl_maestros, "MARCA", null, null);
//
//                    break;
//                }
//                case "TIPO_CILINDRADA":{
//                    buscarMaestros.cargarTabla(modelMotor.getVista(), tbl_maestros, "TIPO_CILINDRADA", null, null);
//
//                    break;
//                }
//                case "COMBUSTIBLE":{
//                    buscarMaestros.cargarTabla(modelMotor.getVista(), tbl_maestros, "COMBUSTIBLE", null, null);
//
//                    break;
//                }
//            }
//        }else if (modelVehiculo.getVista() instanceof Vehiculo){
//            switch(tablaBuscar){
//                case "VEHICULO":{
//                    buscarMaestros.cargarTabla(modelVehiculo.getVista(), tbl_maestros, "VEHICULO", null, null);
//
//                    break;
//                }
//                case "DNPERSONAS":{
//                    buscarMaestros.cargarTabla(modelVehiculo.getVista(), tbl_maestros, "DNPERSONAS", "", "");
//                    break;
//                }
//                case "DNMARCA":{
//                    buscarMaestros.cargarTabla(modelVehiculo.getVista(), tbl_maestros, "DNMARCA", null, null);
//
//                    break;
//                }
//                case "MODELO_VEHICULO":{
//                    buscarMaestros.cargarTabla(modelVehiculo.getVista(), tbl_maestros, "MODELO_VEHICULO", null, null);
//
//                    break;
//                }
//                case "CATEGORIAS_VEHICULO":{
//                    buscarMaestros.cargarTabla(modelVehiculo.getVista(), tbl_maestros, "CATEGORIAS_VEHICULO", null, null);
//
//                    break;
//                }
//                case "MOTOR":{
//                    buscarMaestros.cargarTabla(modelVehiculo.getVista(), tbl_maestros, "MOTOR", null, null);
//
//                    break;
//                }
//                case "TIPO_MOTOR":{
//                    buscarMaestros.cargarTabla(modelVehiculo.getVista(), tbl_maestros, "TIPO_MOTOR", null, null);
//
//                    break;
//                }
//                case "TRANSMISION":{
//                    buscarMaestros.cargarTabla(modelVehiculo.getVista(), tbl_maestros, "TRANSMISION", null, null);
//
//                    break;
//                }
//                case "TRACCION":{
//                    buscarMaestros.cargarTabla(modelVehiculo.getVista(), tbl_maestros, "TRACCION", null, null);
//
//                    break;
//                }
//                case "TIPO_CILINDRADA":{
//                    buscarMaestros.cargarTabla(modelVehiculo.getVista(), tbl_maestros, "TIPO_CILINDRADA", null, null);
//
//                    break;
//                }
//                case "MODELO_CILINDRADA":{
//                    buscarMaestros.cargarTabla(modelVehiculo.getVista(), tbl_maestros, "MODELO_CILINDRADA", null, null);
//
//                    break;
//                }
//                case "COMBUSTIBLE":{
//                    buscarMaestros.cargarTabla(modelVehiculo.getVista(), tbl_maestros, "COMBUSTIBLE", null, null);
//
//                    break;
//                }
//            }
//        }else if (modelOrdenReparacion.getVista() instanceof OrdenReparacion){
//            switch(tablaBuscar){
//                case "ORDE_REPARACION":{
//                    if (modelOrdenReparacion.getTipdoc().equals("ORR")){
//                        this.setTitle("Orden de Reparación");
//                    }else if (modelOrdenReparacion.getTipdoc().equals("POR")){
//                        this.setTitle("Presupuestos Orden de Reparación");
//                    }
//                        
//                    buscarMaestros.cargarTabla(modelOrdenReparacion.getVista(), tbl_maestros, "ORDE_REPARACION", origen, origenDesk);
//
//                    break;
//                }
//                case "VEHICULO":{
//                    this.setTitle("Vehiculos");
//                    buscarMaestros.cargarTabla(modelOrdenReparacion.getVista(), tbl_maestros, "VEHICULO", null, null);
//
//                    break;
//                }
//                case "TECNICO":{
//                    this.setTitle("Tecnicos");
//                    buscarMaestros.cargarTabla(modelOrdenReparacion.getVista(), tbl_maestros, "TECNICO", "", "");
//
//                    break;
//                }
//                case "USUARIO":{
//                    this.setTitle("Reponsables");
//                    buscarMaestros.cargarTabla(modelOrdenReparacion.getVista(), tbl_maestros, "USUARIO", "", "");
//
//                    break;
//                }
//                case "import":{
//                    if (modelOrdenReparacion.getTipdoc().equals("ORR"))
//                        this.setTitle("Importar Presupuesto de Orden de Reparación");
//                        buscarMaestros.cargarTabla(modelOrdenReparacion.getVista(), tbl_maestros, "import", "", "POR");
//                    break;
//                }
//            }
//        }else if (modelTipoCliPro.getVista() instanceof TipoCliPro){
//            buscarMaestros.cargarTabla(modelTipoCliPro.getVista(), tbl_maestros, "TYPE_PERSON", null, null);
////        }else if (modelImpuestos.getVista() instanceof Impuestos){
////            buscarMaestros.cargarTabla(modelImpuestos.getVista(), tbl_maestros, "dntipiva", null, null);
////        }else if (modelTipoContacto.getVista() instanceof TipoContacto){
////            buscarMaestros.cargarTabla(modelTipoContacto.getVista(), tbl_maestros, "dntipcontacto", null, null);
////        }else if (modelMoneda.getVista() instanceof Moneda){
////            buscarMaestros.cargarTabla(modelMoneda.getVista(), tbl_maestros, "dnmoneda", null, null);
//        }else if (modelTipoMotor.getVista() instanceof TipoMotor){
//            buscarMaestros.cargarTabla(modelTipoMotor.getVista(), tbl_maestros, "TIPO_MOTOR", null, null);
//        }else if (modelTransmision.getVista() instanceof Transmision){
//            buscarMaestros.cargarTabla(modelTransmision.getVista(), tbl_maestros, "TRANSMISION", null, null);
//        }else if (modelTraccion.getVista() instanceof Traccion){
//            buscarMaestros.cargarTabla(modelTraccion.getVista(), tbl_maestros, "TRACCION", null, null);
//        }else if (modelTipoCilindrada.getVista() instanceof TipoCilindrada){
//            buscarMaestros.cargarTabla(modelTipoCilindrada.getVista(), tbl_maestros, "TIPO_CILINDRADA", null, null);
//        }else if (modelModeloCilindrada.getVista() instanceof ModeloCilindrada){
//            buscarMaestros.cargarTabla(modelModeloCilindrada.getVista(), tbl_maestros, "MODELO_CILINDRADA", null, null);
//        }else if (modelCombustible.getVista() instanceof Combustible){
//            buscarMaestros.cargarTabla(modelCombustible.getVista(), tbl_maestros, "COMBUSTIBLE", null, null);
//        }else if (modelUbicacionProductos.getVista() instanceof UbicacionProductos){
//            buscarMaestros.cargarTabla(modelUbicacionProductos.getVista(), tbl_maestros, "", null, null);
//        }else if (modelRepEtiquetaCaja.getVista() instanceof ReporteEtiquetasCajas){
//            buscarMaestros.cargarTabla(modelRepEtiquetaCaja.getVista(), tbl_maestros, "", null, null);
//        }else if (modelRelacionDocBL.getVista() instanceof RelacionDoc_BL) {
//            //aThis = modelRelacionDocBL.getVista();
//            switch (tablaBuscar) {
//                case "DNINVENTARIO":
//                    buscarMaestros.cargarTabla(modelRelacionDocBL.getVista(), tbl_maestros, "DNINVENTARIO", null, null);
//
//                    break;
//                case "DNMAESTRO":
//                    buscarMaestros.cargarTabla(modelRelacionDocBL.getVista(), tbl_maestros, "DNMAESTRO", null, null);
//
//                    break;
//                case "DNRELACION_DOCUMENTOS":
//                    buscarMaestros.cargarTabla(modelRelacionDocBL.getVista(), tbl_maestros, "DNRELACION_DOCUMENTOS", null, null);
//
//                    break;
//            }
//        }else if (modelCambiarClave.getVista() instanceof CambiarClave){
//            buscarMaestros.cargarTabla(modelCambiarClave.getVista(), tbl_maestros, "DNUSUARIOS", null, null);
        }else if (modelCreaUsuarios.getVista() instanceof CreaUsuarios){
            buscarMaestros.cargarTabla(modelCreaUsuarios.getVista(), tbl_maestros, "DNUSUARIOS", null, null);
        }
//        else if (modelComisionVendedor.getVista() instanceof ComisionVendedor){
//            switch(tablaBuscar){
//                case "DNVENDEDOR":
//                    buscarMaestros.cargarTabla(modelComisionVendedor.getVista(), tbl_maestros, "DNVENDEDOR", null, null);
//                    break;
//                case "DNVENDEDOR2":
//                    buscarMaestros.cargarTabla(modelComisionVendedor.getVista(), tbl_maestros, "DNVENDEDOR2", null, null);
//                    break;
//            }
//        }else if (modelGrupoPermisos.getVista() instanceof GruposPermisos){
//            buscarMaestros.cargarTabla(modelGrupoPermisos.getVista(), tbl_maestros, "DNPERMISO_GRUPAL", null, null);
////        }else if (modelVendedor.getVista() instanceof Vendedor){
////            buscarMaestros.cargarTabla(modelVendedor.getVista(), tbl_maestros, "DNVENDEDOR", null, null);
//        }else if (modelPrecio.getVista() instanceof Precio){
//            switch(tablaBuscar){
//                case "DNPRODUCTO":{
//                    buscarMaestros.cargarTabla(modelPrecio.getVista(), tbl_maestros, "DNPRODUCTO", null, null);
//                    break;
//                }
//                case "DNLISTPRE":{
//                    buscarMaestros.cargarTabla(modelPrecio.getVista(), tbl_maestros, "DNLISTPRE", null, null);
//                    break;
//                }
//                case "DNUNDMEDIDA":{
//                    buscarMaestros.cargarTabla(modelPrecio.getVista(), tbl_maestros, "DNUNDMEDIDA", null, null);
//                    break;
//                }
//                case "DNPRECIO":{
//                    buscarMaestros.cargarTabla(modelPrecio.getVista(), tbl_maestros, "DNPRECIO", null, null);
//                    break;
//                }
//            }
//        }else if(modelTipoDocumentos.getVista() instanceof TipoDocumentos){
//            buscarMaestros.cargarTabla(modelTipoDocumentos.getVista(), tbl_maestros, "DNDOCUMENTOS", null, null);
//        }else if(modelConfigAsientos.getVista() instanceof ConfigAsientos){
//            buscarMaestros.cargarTabla(modelConfigAsientos.getVista(), tbl_maestros, "DNCONFIG_CONTABLE", null, null);
//        }else if(modelAsientoManual.getVista() instanceof AsientoManual){
//            switch(tablaBuscar){
//                case "DNINVENTARIO":{
//                    buscarMaestros.cargarTabla(modelAsientoManual.getVista(), tbl_maestros, "DNINVENTARIO", null, null);
//                    break;
//                }
//                case "DNPERSONAS":{
//                    buscarMaestros.cargarTabla(modelAsientoManual.getVista(), tbl_maestros, "DNPERSONAS", null, null);
//                    break;
//                }
//            }
//        }else if(modelImprimirCheque.getVista() instanceof ImprimirCheque){
//            buscarMaestros.cargarTabla(modelImprimirCheque.getVista(), tbl_maestros, "DNPERSONAS", null, null);
//        }else if(modelRetencionIva.getVista() instanceof RetencionIVA){
//            buscarMaestros.cargarTabla(modelRetencionIva.getVista(), tbl_maestros, "DNPERSONAS", null, null);
//        }else if(modelRetencionIslr.getVista() instanceof RetencionISLR){
//            switch(tablaBuscar){
//                case "DNPERSONAS":{
//                    buscarMaestros.cargarTabla(modelRetencionIslr.getVista(), tbl_maestros, "DNPERSONAS", null, null);
//                    break;
//                }
//                case "DNEMPRESAS":{
//                    buscarMaestros.cargarTabla(modelRetencionIslr.getVista(), tbl_maestros, "DNEMPRESAS", null, null);
//                    break;
//                }
//            }
//        }else if(modelreporteIslr.getVista() instanceof reporteISLRlote){
//            buscarMaestros.cargarTabla(modelreporteIslr.getVista(), tbl_maestros, "DNPERSONAS", null, null);
//        }
////        else if(modelRetencionIvaventas.getVista() instanceof RetencionIVAventas){
////            buscarMaestros.cargarTabla(modelRetencionIvaventas.getVista(), tbl_maestros, "DNPERSONAS", null, null);
////        }
//        else if(modelPagoDocs.getVista() instanceof PagoDocs){
//            buscarMaestros.cargarTabla(modelPagoDocs.getVista(), tbl_maestros, "DNPERSONAS", null, null);
//        }else if(modelProveedores.getVista() instanceof Proveedores){
//            idRol = modelProveedores.getIdRol();
//            buscarMaestros.cargarTabla(modelProveedores.getVista(), tbl_maestros, "DNPERSONAS", String.valueOf(idRol), null);
//        }else if(modelRepIvaVta.getVista() instanceof ReporteRetIVAventas){
//            switch(tablaBuscar){
//                case "DNEMPRESAS":{
//                    buscarMaestros.cargarTabla(modelRepIvaVta.getVista(), tbl_maestros, "DNEMPRESAS", null, null);
//                    break;
//                }
//                case "DNPERSONAS":{
//                    buscarMaestros.cargarTabla(modelRepIvaVta.getVista(), tbl_maestros, "DNPERSONAS", null, null);
//                    break;
//                }
//            }
//        }
    }
    
    private void close(){
        //java.awt.Toolkit.getDefaultToolkit().beep();

        dispose();
        txt_rif.setText(""); txt_nombre.setText("");
        
//        if (modelDocumentos.getVista() instanceof Facturacion) {
//            Facturacion facturacion = (Facturacion) modelDocumentos.getVista();
//            facturacion.requestFocus();
//            
//            if (modelDocumentos.islRif()){
//                facturacion.tfRif.requestFocus();
//            }else if (modelDocumentos.islProducto()){
//                //facturacion.tbDetalleFacturacion.requestFocus();
//                //facturacion.tfCodeArticulo.requestFocus();
//                modelDocumentos.getCmbUndMed().requestFocus();
//            }
//        }else if (modelCuentasPorCobrarPagar.getVista() instanceof CuentasPorCobrarPagar) {
//            CuentasPorCobrarPagar cuentasPorCobarPagar = (CuentasPorCobrarPagar) modelCuentasPorCobrarPagar.getVista();
//            cuentasPorCobarPagar.requestFocus();
//            
//            modelCuentasPorCobrarPagar.getTxtPers().requestFocus();
////        }else if (modelTipoContacto.getVista() instanceof TipoContacto) {
////            TipoContacto tipoContacto = (TipoContacto) modelTipoContacto.getVista();
////            tipoContacto.requestFocus();
////            
////            modelTipoContacto.getTxtDescri().requestFocus();
////        }else if (modelMoneda.getVista() instanceof Moneda) {
////            Moneda moneda = (Moneda) modelMoneda.getVista();
////            moneda.requestFocus();
////            
////            modelMoneda.getTxtDescri().requestFocus();
//        }else if (modelCbtesElectronicos.getVista() instanceof VisualizarCbteElectronicos) {
//            VisualizarCbteElectronicos visualizarCbteElectronicos = (VisualizarCbteElectronicos) modelCbtesElectronicos.getVista();
//            visualizarCbteElectronicos.requestFocus();
//            
//            modelCbtesElectronicos.getTfIdCliente().requestFocus();
//        }else if (modelTecnico.getVista() instanceof Tecnico) {
//            Tecnico tecnico = (Tecnico) modelTecnico.getVista();
//            tecnico.requestFocus();
//            
//            modelTecnico.getTxtCodigo().requestFocus();
//        }else if (modelMaestroReportesSistema.getVista() instanceof MaestroDeReportesSistema) {
//            MaestroDeReportesSistema maestroReportesSistema = (MaestroDeReportesSistema) modelMaestroReportesSistema.getVista();
//            
//            switch (tablaBuscar) {
//                case "DNPERSONAS":
//                    maestroReportesSistema.requestFocus();
//
//                    modelMaestroReportesSistema.getTfCodPersona().requestFocus();
//
//                    break;
//                case "DNINVENTARIO":
//                    maestroReportesSistema.requestFocus();
//            
//                    modelMaestroReportesSistema.getTfNumDoc().requestFocus();
//
//                    break;
//            }
//        }else if (modelVehiculo.getVista() instanceof Vehiculo) {
//            Vehiculo vehiculo = (Vehiculo) modelVehiculo.getVista();
//            vehiculo.requestFocus();
//            
//            switch(tablaBuscar){
//                case "VEHICULO":{
//                        modelVehiculo.getTfPlaca().requestFocus();
//
//                        break;
//                    }
//                    case "DNPERSONAS":{
//                        modelVehiculo.getTfSerialMotor1().requestFocus();
//
//                        break;
//                    }
//                    case "DNMARCA":{
//                        modelVehiculo.getTfTipCilindrada().requestFocus();
//
//                        break;
//                    }
//                    case "MODELO_VEHICULO":{
//                        modelVehiculo.getTfCategoriaVeh().requestFocus();
//
//                        break;
//                    }
//                    case "CATEGORIAS_VEHICULO":{
//                        modelVehiculo.getTfTransmision().requestFocus();
//                        
//                        break;
//                    }
//                    case "MOTOR":{
//                        modelVehiculo.getTfModelVeh().requestFocus();
//
//                        break;
//                    }
//                    case "TIPO_MOTOR":{
//                        modelVehiculo.getTfMarcaVeh().requestFocus();
//
//                        break;
//                    }
//                    case "TRANSMISION":{
//                        modelVehiculo.getTfTraccion().requestFocus();
//
//                        break;
//                    }
//                    case "TRACCION":{
//                        modelVehiculo.getTfColorVeh().requestFocus();
//
//                        break;
//                    }
//                    case "TIPO_CILINDRADA":{
//                        modelVehiculo.getTfCombustible().requestFocus();
//
//                        break;
//                    }
//                    case "COMBUSTIBLE":{
//                        modelVehiculo.getTfModelVeh().requestFocus();
//
//                        break;
//                    }
//            }
//        }else if (modelOrdenReparacion.getVista() instanceof Vehiculo) {
//            OrdenReparacion ordenReparacion = (OrdenReparacion) modelOrdenReparacion.getVista();
//            ordenReparacion.requestFocus();
//            
//            modelOrdenReparacion.getTfPlaca().requestFocus();
//        }
    }
    
    private void ActualizaJtable(String Codigo){
        int item=0;
        String Ultimo_Codigo = "";
        
        Ultimo_Codigo= txt_rif.getText().trim();

        for (int i = 0; i < tbl_maestros.getRowCount(); i++){
//            if (modelDocumentos.getVista() instanceof Facturacion) {
//                if(tbl_maestros.getValueAt(i, 2).toString().trim().toUpperCase().equals(Ultimo_Codigo.toUpperCase())){
//                    item = i;
//                
//                    txt_rif.setText((String) tbl_maestros.getValueAt(item,2).toString().trim());
//                    txt_nombre.setText((String) tbl_maestros.getValueAt(item,1).toString().trim());
//                }
//            }else{
                if(tbl_maestros.getValueAt(i, 0).toString().trim().toUpperCase().equals(Ultimo_Codigo.toUpperCase())){
                    item = i;
                
                    txt_rif.setText((String) tbl_maestros.getValueAt(item,0).toString().trim());
                    txt_nombre.setText((String) tbl_maestros.getValueAt(item,1).toString().trim());
                }
//            }
        }

        tbl_maestros.getSelectionModel().setSelectionInterval(item, item);
        tbl_maestros.scrollRectToVisible(new Rectangle(tbl_maestros.getCellRect(item, 0, true)));
        
        action_tablas(item);
        tbl_maestros.requestFocus();
        fsel=item;
    }

    private void action_tablas(int Row){
        String codigo=null, nombre=null;
        double limit_cre=0, Dcto=0, DiasVen=0;
        Object element[][] = null;

        codigo = (String) tbl_maestros.getValueAt(tbl_maestros.getSelectedRow(),0).toString().trim();
        nombre = (String) tbl_maestros.getValueAt(tbl_maestros.getSelectedRow(),1).toString().trim();
        
//        if (modelCategorias.getVista() instanceof Categorias) {
//            switch (tablaBuscar){
//                case "DNCLASIFICACION":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelCategorias.getVista(), "DNCLASIFICACION");
//
//                    element = elementos;
//                    break;
//                }
//                case "DNCLASIFICACION_PADRE":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelCategorias.getVista(), "DNCLASIFICACION_PADRE");
//
//                    element = elementos;
//                    break;
//                }
//                case "DNCLASIFICACION_HIJO":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelCategorias.getVista(), "DNCLASIFICACION_HIJO");
//
//                    element = elementos;
//                    break;
//                }
//            }
//        }else if (modelProductos.getVista() instanceof Productos) {
//            switch (tablaBuscar) {
//                case "DNMARCA":
//                    {
//                        Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelProductos.getVista(), "DNMARCA");
//                        element = elementos;
//                        break;
//                    }
//                case "dnpersonas":
//                    {
//                        Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelProductos.getVista(), "dnpersonas");
//                        element = elementos;
//                        break;
//                    }
//                case "DNPRODUCTO":
//                    {
//                        Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelProductos.getVista(), "DNPRODUCTO");
//                        element = elementos;
//                        break;
//                    }
//                case "GRUPO":
//                    {
//                        Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelProductos.getVista(), "GRUPO");
//                        element = elementos;
//                        break;
//                    }
//                case "SUBGRUPO":
//                    {
//                        Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelProductos.getVista(), "SUBGRUPO");
//                        element = elementos;
//                        break;
//                    }
//            }
////        }else if (modelDescuentos.getVista() instanceof DescuentosArticulos){
////            switch (tablaBuscar){
////                case "DNPRODUCTO":{
////                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelDescuentos.getVista(), "DNPRODUCTO");
////                
////                    element = elementos;
////                    break;
////                }
////                case "DNDESCUENTOS":{
////                    Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelDescuentos.getVista(), "DNDESCUENTOS");
////                
////                    element = elementos;
////                    break;
////                }
////            }
//        }else if (modelInventario.getVista() instanceof Inventario) {
//            switch (tablaBuscar) {
//                case "DNINVENTARIO":
//                    {
//                        Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelInventario.getVista(), "DNINVENTARIO");
//                        element = elementos;
//                        break;
//                    }
//                case "DNPRODUCTO":
//                    {
//                        Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelInventario.getVista(), "DNPRODUCTO");
//                        element = elementos;
//                        break;
//                    }
//            }
//        }else if (modelDocumentos.getVista() instanceof Facturacion) {
//            if (tablaBuscar.equals("DNPERSONAS")){
//                Object elementos[][] = buscarMaestros.actionTabla(codigo,nombre, modelDocumentos.getVista(), "DNPERSONAS");
//                    
//                element = elementos;
//            }else if(tablaBuscar.equals("DNINVENTARIO")){
//                Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelDocumentos.getVista(), "DNINVENTARIO");
//                    
//                element = elementos;
//            }else if(tablaBuscar.equals("DNCONFIGIMPORT")){
//                Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelDocumentos.getVista(), "DNCONFIGIMPORT");
//                    
//                element = elementos;
//            }else if(tablaBuscar.equals("import")){
//                codigo = (String) tbl_maestros.getValueAt(tbl_maestros.getSelectedRow(),3).toString().trim();
//                
//                Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelDocumentos.getVista(), "import");
//                    
//                element = elementos;
//            }else if (tablaBuscar.toUpperCase().equals("DNPRODUCTO")){
//                Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelDocumentos.getVista(), "DNPRODUCTO");
//                    
//                element = elementos;
//            }
//        }else if (modelCuentasPorCobrarPagar.getVista() instanceof CuentasPorCobrarPagar) {
//            if (tablaBuscar.equals("DNPERSONAS")){
//                Object elementos[][] = buscarMaestros.actionTabla(codigo,nombre, modelCuentasPorCobrarPagar.getVista(), "DNPERSONAS");
//                    
//                element = elementos;
//            }
//        }else if (modelCbtesElectronicos.getVista() instanceof VisualizarCbteElectronicos) {
//            switch (tablaBuscar) {
//                case "DNPERSONAS":
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo,nombre, modelCbtesElectronicos.getVista(), "DNPERSONAS");
//                    
//                    element = elementos;
//
//                    break;
//            }
//        }else if (modelTecnico.getVista() instanceof Tecnico) {
//            Object elementos[][] = buscarMaestros.actionTabla(codigo,nombre, modelTecnico.getVista(), "tecnico");
//                    
//            element = elementos;
//        }else if (modelMaestroReportesSistema.getVista() instanceof MaestroDeReportesSistema) {
//            switch(tablaBuscar){
//                case "DNPERSONAS":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelMaestroReportesSistema.getVista(), "DNPERSONAS");
//                    element = elementos;
//
//                    break;
//                }
//                case "DNINVENTARIO":{
//                    codigo = (String) tbl_maestros.getValueAt(tbl_maestros.getSelectedRow(),0).toString().trim();
//                    nombre = (String) tbl_maestros.getValueAt(tbl_maestros.getSelectedRow(),2).toString().trim();
//                    
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelMaestroReportesSistema.getVista(), "DNINVENTARIO");
//                    element = elementos;
//
//                    break;
//                }
//            }
//        }else if (modelRepVentas.getVista() instanceof ReporteVentas){
//            if (tablaBuscar.equals("DNVENDEDOR")){
//                Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelRepVentas.getVista(), "DNVENDEDOR");
//                    
//                element = elementos;
//            }
//        }else if (modelRepStock.getVista() instanceof ReporteStock){
//            if (tablaBuscar.equals("DNPRODUCTO")){
//                Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelRepStock.getVista(), "DNPRODUCTO");
//                    
//                element = elementos;
//            }
//        }else if (modelRepCodBarra.getVista() instanceof ReporteCodBarra){
//            if (tablaBuscar.equals("DNPRODUCTO")){
//                Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelRepCodBarra.getVista(), "DNPRODUCTO");
//                    
//                element = elementos;
//            }
//        }else if (modelUnidadMedida.getVista() instanceof UnidadMedida){
//            if(tablaBuscar.equals("DNUNDMEDIDA")){
//                Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelUnidadMedida.getVista(), "DNUNDMEDIDA");
//                    
//                element = elementos;
//            }
//        }else if (modelBancosInsPagos.getVista() instanceof BancosInsPagos){
//            if(tablaBuscar.equals("DNBANCOS")){
//                Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelBancosInsPagos.getVista(), "DNBANCOS");
//                    
//                element = elementos;
//            }else if(tablaBuscar.equals("DNINSTRUMENTOPAGO")){
//                Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelBancosInsPagos.getVista(), "DNINSTRUMENTOPAGO");
//                    
//                element = elementos;
//            }
//        }else if(modelJornadas.getVista() instanceof Jornadas){
//            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelJornadas.getVista(), "DNJORNADA");
//                    
//            element = elementos;
//        }else  if (modelCargos.getVista() instanceof Cargos) {
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelCargos.getVista(), "DNCARGOS");
//
//            element = elementos;
//        }else 
        if(modelCreaUsuarios.getVista() instanceof CreaUsuarios){
            Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelCreaUsuarios.getVista(), "DNUSUARIOS");
                    
            element = elementos;
//        }else if (modelPos.getVista() instanceof POS){
//            if (tablaBuscar.equals("DNVENDEDOR")){
//                Object elementos[][] = buscarMaestros.buscarNombre(nombre, modelPos.getVista(), "DNVENDEDOR");
//                    
//                element = elementos;
//            }
//        }else if(modelCreaProveedor.getVista() instanceof CreaProveedor){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelCreaProveedor.getVista(), "DNMAESTRO");
//
//            element = elementos;
        }else if(modelEmpresas.getVista() instanceof Empresas){
            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelEmpresas.getVista(), "DNEMPRESAS");

            element = elementos;
//        }else if(modelMarca.getVista() instanceof Marcas){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelMarca.getVista(), "DNMARCA");
//
//            element = elementos;
//        }else if (modelMarcasVehiculo.getVista() instanceof MarcasVehiculos){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelMarcasVehiculo.getVista(), "DNMARCA");
//
//            element = elementos;
//        }else if (modelModeloVehiculo.getVista() instanceof ModeloVehiculo){
//            switch(tablaBuscar){
//                case "DNMARCA":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelModeloVehiculo.getVista(), "DNMARCA");
//                    element = elementos;
//
//                    break;
//                }
//                case "MODELO_VEHICULO":{
//                    codigo = (String) tbl_maestros.getValueAt(tbl_maestros.getSelectedRow(),0).toString().trim();
//                    nombre = (String) tbl_maestros.getValueAt(tbl_maestros.getSelectedRow(),2).toString().trim();
//                    
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelModeloVehiculo.getVista(), "MODELO_VEHICULO");
//                    element = elementos;
//
//                    break;
//                }
//                case "MODELO_VEHICULO_2":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelModeloVehiculo.getVista(), "MODELO_VEHICULO_2");
//                    element = elementos;
//
//                    break;
//                }
//            }
//        }else if (modelCategoriasVehiculo.getVista() instanceof CategoriasVehiculos){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelCategoriasVehiculo.getVista(), "CATEGORIAS_VEHICULO");
//
//            element = elementos;
//        }else if (modelMotor.getVista() instanceof Motor){
//            switch(tablaBuscar){
//                case "MOTOR":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelMotor.getVista(), "MOTOR");
//                    element = elementos;
//
//                    break;
//                }
//                case "TIPO_MOTOR":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelMotor.getVista(), "TIPO_MOTOR");
//                    element = elementos;
//
//                    break;
//                }
//                case "MARCA":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelMotor.getVista(), "MARCA");
//                    element = elementos;
//
//                    break;
//                }
//                case "TIPO_CILINDRADA":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelMotor.getVista(), "TIPO_CILINDRADA");
//                    element = elementos;
//
//                    break;
//                }
//                case "COMBUSTIBLE":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelMotor.getVista(), "COMBUSTIBLE");
//                    element = elementos;
//
//                    break;
//                }
//            }
//        }else if (modelVehiculo.getVista() instanceof Vehiculo){
//
//            switch(tablaBuscar){
//                case "VEHICULO":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelVehiculo.getVista(), "VEHICULO");
//                    element = elementos;
//
//                    break;
//                }
//                case "DNPERSONAS":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo,nombre, modelVehiculo.getVista(), "DNPERSONAS");
//                    element = elementos;
//                    
//                    break;
//                }
//                case "DNMARCA":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelVehiculo.getVista(), "DNMARCA");
//                    element = elementos;
//
//                    break;
//                }
//                case "MODELO_VEHICULO":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelVehiculo.getVista(), "MODELO_VEHICULO");
//                    element = elementos;
//
//                    break;
//                }
//                case "CATEGORIAS_VEHICULO":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelVehiculo.getVista(), "CATEGORIAS_VEHICULO");
//                    element = elementos;
//
//                    break;
//                }
//                case "MOTOR":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelVehiculo.getVista(), "MOTOR");
//                    element = elementos;
//
//                    break;
//                }
//                case "TIPO_MOTOR":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelVehiculo.getVista(), "TIPO_MOTOR");
//                    element = elementos;
//
//                    break;
//                }
//                case "TRANSMISION":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelVehiculo.getVista(), "TRANSMISION");
//                    element = elementos;
//
//                    break;
//                }
//                case "TRACCION":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelVehiculo.getVista(), "TRACCION");
//                    element = elementos;
//
//                    break;
//                }
//                case "TIPO_CILINDRADA":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelVehiculo.getVista(), "TIPO_CILINDRADA");
//                    element = elementos;
//
//                    break;
//                }
//                case "MODELO_CILINDRADA":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelVehiculo.getVista(), "MODELO_CILINDRADA");
//                    element = elementos;
//
//                    break;
//                }
//                case "COMBUSTIBLE":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelVehiculo.getVista(), "COMBUSTIBLE");
//                    element = elementos;
//
//                    break;
//                }
//            }
//        }else if (modelOrdenReparacion.getVista() instanceof OrdenReparacion){
//            switch(tablaBuscar){
//                case "ORDE_REPARACION":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelOrdenReparacion.getVista(), "ORDE_REPARACION");
//                    element = elementos;
//
//                    break;
//                }
//                case "VEHICULO":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelOrdenReparacion.getVista(), "VEHICULO");
//                    element = elementos;
//
//                    break;
//                }
//                case "TECNICO":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo,nombre, modelOrdenReparacion.getVista(), "TECNICO");
//                    element = elementos;
//
//                    break;
//                }
//                case "USUARIO":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo,nombre, modelOrdenReparacion.getVista(), "USUARIO");
//                    element = elementos;
//
//                    break;
//                }
//                case "import":{
//                    //codigo = (String) tbl_maestros.getValueAt(tbl_maestros.getSelectedRow(),3).toString().trim();
//                
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelOrdenReparacion.getVista(), "import");
//                    element = elementos;
//                    
//                    break;
//                }
//            }
//        }else if (modelTipoCliPro.getVista() instanceof TipoCliPro){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelTipoCliPro.getVista(), "TYPE_PERSON");
//
//            element = elementos;
////        }else if (modelImpuestos.getVista() instanceof Impuestos){
////            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelImpuestos.getVista(), "dntipiva");
////
////            element = elementos;
////        }else if (modelTipoContacto.getVista() instanceof TipoContacto){
////            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelTipoContacto.getVista(), "dntipcontacto");
////
////            element = elementos;
////        }else if (modelMoneda.getVista() instanceof Moneda){
////            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelMoneda.getVista(), "dnmoneda");
////
////            element = elementos;
//        }else if (modelTipoMotor.getVista() instanceof TipoMotor){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelTipoMotor.getVista(), "TIPO_MOTOR");
//
//            element = elementos;
//        }else if (modelTransmision.getVista() instanceof Transmision){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelTransmision.getVista(), "TRANSMISION");
//
//            element = elementos;
//        }else if (modelTraccion.getVista() instanceof Traccion){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelTraccion.getVista(), "TRACCION");
//
//            element = elementos;
//        }else if (modelTipoCilindrada.getVista() instanceof TipoCilindrada){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelTipoCilindrada.getVista(), "TIPO_CILINDRADA");
//
//            element = elementos;
//        }else if (modelModeloCilindrada.getVista() instanceof ModeloCilindrada){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelModeloCilindrada.getVista(), "MODELO_CILINDRADA");
//
//            element = elementos;
//        }else if (modelCombustible.getVista() instanceof Combustible){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelCombustible.getVista(), "COMBUSTIBLE");
//
//            element = elementos;
//        }else if(modelUbicacionProductos.getVista() instanceof UbicacionProductos){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelUbicacionProductos.getVista(), "DNUBICACION_PRODUCTOS");
//
//            element = elementos;
//        }else if(modelRepEtiquetaCaja.getVista() instanceof ReporteEtiquetasCajas){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelRepEtiquetaCaja.getVista(), "");
//
//            element = elementos;
//        }else if (modelRelacionDocBL.getVista() instanceof RelacionDoc_BL){
//            if(tablaBuscar.equals("DNINVENTARIO")){
//                Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelRelacionDocBL.getVista(), "DNINVENTARIO");
//                
//                element = elementos;
//            }else if(tablaBuscar.equals("DNMAESTRO")){
//                Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelRelacionDocBL.getVista(), "DNMAESTRO");
//                    
//                element = elementos;
//            }else if(tablaBuscar.equals("DNRELACION_DOCUMENTOS")){
//                Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelRelacionDocBL.getVista(), "DNRELACION_DOCUMENTOS");
//                    
//                element = elementos;
//            }
//        }else if (modelCambiarClave.getVista() instanceof CambiarClave){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelCambiarClave.getVista(), "DNUSUARIOS");
//
//            element = elementos;
        }
//        else if (modelComisionVendedor.getVista() instanceof ComisionVendedor){
//            switch(tablaBuscar){
//                case "DNVENDEDOR":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelComisionVendedor.getVista(), "DNVENDEDOR");
//                    element = elementos;
//                    
//                    break;
//                }
//                case "DNVENDEDOR2":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelComisionVendedor.getVista(), "DNVENDEDOR2");
//                    element = elementos;
//                    
//                    break;
//                }
//            }
//        }else if (modelGrupoPermisos.getVista() instanceof GruposPermisos){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelGrupoPermisos.getVista(), "DNPERMISO_GRUPAL");
//            element = elementos;
////        }else if (modelVendedor.getVista() instanceof Vendedor){
////            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelVendedor.getVista(), "DNVENDEDOR");
////            element = elementos;
//        }else if (modelPrecio.getVista() instanceof Precio){
//            switch(tablaBuscar){
//                case "DNPRODUCTO":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelPrecio.getVista(), "DNPRODUCTO");
//                    element = elementos;
//                    break;
//                }
//                case "DNLISTPRE":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelPrecio.getVista(), "DNLISTPRE");
//                    element = elementos;
//                    break;
//                }
//                case "DNUNDMEDIDA":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelPrecio.getVista(), "DNUNDMEDIDA");
//                    element = elementos;
//                    break;
//                }
//                case "DNPRECIO":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelPrecio.getVista(), "DNPRECIO");
//                    element = elementos;
//                    break;
//                }
//            }
//        }else if(modelTipoDocumentos.getVista() instanceof TipoDocumentos){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelTipoDocumentos.getVista(), "DNDOCUMENTOS");
//            element = elementos;
//        }else if(modelConfigAsientos.getVista() instanceof ConfigAsientos){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelConfigAsientos.getVista(), "DNCONFIG_CONTABLE");
//            element = elementos;
//        }else if(modelAsientoManual.getVista() instanceof AsientoManual){
//            switch(tablaBuscar){
//                case "DNINVENTARIO":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelAsientoManual.getVista(), "DNINVENTARIO");
//                    element = elementos;
//                    break;
//                }
//                case "DNPERSONAS":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelAsientoManual.getVista(), "DNPERSONAS");
//                    element = elementos;
//                    break;
//                }
//            }
//        }else if(modelImprimirCheque.getVista() instanceof ImprimirCheque){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelImprimirCheque.getVista(), "DNPERSONAS");
//            element = elementos;
//        }else if(modelRetencionIva.getVista() instanceof RetencionIVA){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelRetencionIva.getVista(), "DNPERSONAS");
//            element = elementos;
//        }else if(modelRetencionIslr.getVista() instanceof RetencionISLR){
//            switch(tablaBuscar){
//                case "DNPERSONAS":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelRetencionIslr.getVista(), "DNPERSONAS");
//                    element = elementos;
//                    break;
//                }
//                case "DNEMPRESAS":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelRetencionIslr.getVista(), "DNEMPRESAS");
//                    element = elementos;
//                    break;
//                }
//            }
//        }else if(modelreporteIslr.getVista() instanceof reporteISLRlote){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelreporteIslr.getVista(), "DNPERSONAS");
//            element = elementos;
//        }
////        else if(modelRetencionIvaventas.getVista() instanceof RetencionIVAventas){
////            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelRetencionIvaventas.getVista(), "DNPERSONAS");
////            element = elementos;
////        }
//        else if(modelPagoDocs.getVista() instanceof PagoDocs){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelPagoDocs.getVista(), "DNPERSONAS");
//            element = elementos;
//        }else if(modelProveedores.getVista() instanceof Proveedores){
//            Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelProveedores.getVista(), "DNPERSONAS");
//            element = elementos;
//        }else if(modelRepIvaVta.getVista() instanceof ReporteRetIVAventas){
//            switch(tablaBuscar){
//                case "DNEMPRESAS":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelRepIvaVta.getVista(), "DNEMPRESAS");
//                    element = elementos;
//                    break;
//                }
//                case "DNPERSONAS":{
//                    Object elementos[][] = buscarMaestros.actionTabla(codigo, nombre, modelRepIvaVta.getVista(), "DNPERSONAS");
//                    element = elementos;
//                    break;
//                }
//            }
//        }
        
        codigo = element[0][0].toString().trim();
        nombre = element[0][1].toString().trim();
        
//        if (modelModeloVehiculo.getVista() instanceof ModeloVehiculo){
//            switch(tablaBuscar){
//                case "MODELO_VEHICULO":{
//                    nombre = element[0][2].toString().trim();
//
//                    break;
//                }
//            }
//        }
        
        this.txt_rif.setText(codigo);
        this.txt_nombre.setText(nombre);
        
//        if(modelInventario.getVista() instanceof Inventario){
//            if(tablaBuscar.equals("DNINVENTARIO")){
//                this.txt_nombre.setText("");
//            }
//        }

//        if(modelDocumentos.getVista() instanceof Facturacion){
//            if(tablaBuscar.contains("DNINVENTARIO")){
//                if(origen.equals("Doc")){
//                    modelDocumentos.buscaDocumento(codigo);
//                }else{
//                    this.txt_nombre.setText("");
//                }
//            }
//        }

//        if(modelPrecio.getVista() instanceof Precio){
//            switch(tablaBuscar){
//                case "DNPRECIO":
//                    this.txt_nombre.setText("");
//                    break;
//            }
//        }
    }
    
    private void action_select_maestro(){
        fsel = tbl_maestros.getSelectedRow();
        try {
            System.out.println(fsel);
            String id, nombre;

//            if (modelCategorias.getVista() instanceof Categorias) {
//                switch (tablaBuscar){
//                    case "DNCLASIFICACION":
//                        if(fsel<0){
//                            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgAddCodePadreCate"));
//                        }else{
//                            m = (DefaultTableModel) tbl_maestros.getModel();
//                            id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                            nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//
//                            modelCategorias.setCodigo(id);
//                            modelCategorias.setNombreCat(nombre);
//                            modelCategorias.cargaCategPadre();
//                        }
//                        break;
//                    case "DNCLASIFICACION_PADRE":
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                            
//                        modelCategorias.buscaCodigo(id);
//                        
//                        break;
//                    case "DNCLASIFICACION_HIJO":
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                        
//                        modelCategorias.buscaCodigo(id);
//                        
//                        break;
//                }
//                modelCategorias.setVista(null);
//                close();
//            }else if (modelProductos.getVista() instanceof Productos) {
//                if(fsel<0){
//                    JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgSelectItem"));
//                }else{
//                    m = (DefaultTableModel) tbl_maestros.getModel();
//                    id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                    nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//
//                    modelProductos.setCodigoMar(id);
//                    modelProductos.setNombreMar(nombre);
//                    modelProductos.calculoCostoPromPrecioVentas();
//                    
//                    switch (tablaBuscar) {
//                        case "DNMARCA":
//                            //modelProductos.cargaCodMarca();
//                            modelProductos.buscaMarcaArticulo(id);
//                            break;
//                        case "dnpersonas":
//                            String idFisacal = tbl_maestros.getValueAt(fsel, 2).toString().trim();
//                    
//                            modelProductos.buscaProveedor(idFisacal);
//                            
//                            modelProductos.setCodigoMar(idFisacal);
//                            modelProductos.setLbCodProveedor(id);
//                            modelProductos.setLbNombreProvee(nombre);
//                            
//                            modelProductos.cargaCodMaestro();
//                            break;
//                        case "DNPRODUCTO":
//                            modelProductos.cargaCodMaestro();
//                            break;
//                        case "GRUPO":
//                            modelProductos.buscaGrupo(id);
//                            break;
//                        case "SUBGRUPO":
//                            modelProductos.buscaSubGrupo(id);
//                            break;
//                    }
//                    modelProductos.setVista(null);
//                    close();
//                }
////            }if (modelDescuentos.getVista() instanceof DescuentosArticulos) {
////                switch (tablaBuscar) {
////                    case "DNPRODUCTO":
////                        m = (DefaultTableModel) tbl_maestros.getModel();
////                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
////                        modelDescuentos.setCodigo(id);
////                        modelDescuentos.cargaProducto();
////                        
////                        break;
////                    case "DNDESCUENTOS":
////                        m = (DefaultTableModel) tbl_maestros.getModel();
////                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
////                        modelDescuentos.buscaCodigo(id);
////                        
////                        break;
////                }
////                modelDescuentos.setVista(null);
////                close();
//            }else if(modelInventario.getVista() instanceof Inventario) {
//                if(fsel<0){
//                    JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgSelectItem"));
//                }else{
//                    switch (tablaBuscar) {
//                        case "DNINVENTARIO":
//                            m = (DefaultTableModel) tbl_maestros.getModel();
//                            id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                            nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                            
//                            //modelInventario.setNumdoc(id);
//                            //modelInventario.cargaTablas();
//                            modelInventario.Buscar(id);
//                            //controladorInventario.cargaTabla(modelInventario.getTblInventario(),id);
//                            
//                            break;
//                        case "DNPRODUCTO":
//                            m = (DefaultTableModel) tbl_maestros.getModel();
//                            id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                            nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//
//                            modelInventario.buscaPro(id, "");
//                            break;
//                    }
//                    modelInventario.setVista(null);
//                    close();
//                }
//            }else if (modelDocumentos.getVista() instanceof Facturacion){
//                if(fsel<0){
//                    JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgSelectItem"));
//                }else{
//                    m = (DefaultTableModel) tbl_maestros.getModel();
//                    id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                    nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                    if (tablaBuscar.equals("DNPERSONAS")){
//                        id = tbl_maestros.getValueAt(fsel, 2).toString().trim();
//                        modelDocumentos.validaFacturasVencidas(id);
//                        boolean lLimiteCredito = false;
//                        if (modelDocumentos.islPagoCobro()){
//                            lLimiteCredito = modelDocumentos.validaLimiteCredito(id);
//                        }
//                        
//                        if (!lLimiteCredito){
//                            modelDocumentos.setIdPersona(tbl_maestros.getValueAt(fsel, 0).toString().trim());
//                            modelDocumentos.setTfRif(id);
//                            modelDocumentos.setLbNomProv(nombre);
//                        }
////                      modelDocumentos.buscaMaestro();
////                      if(modelDocumentos.getTipdoc().equals("CRE")){
////                                modelDocumentos.validaConfiguracion();
////                      }
//                    }else if (tablaBuscar.equals("DNINVENTARIO")){
//                        modelDocumentos.buscaDocumento(id);
//                        
//                        if (modelDocumentos.getTblDetalle().getRowCount()>0){
//                            if (!VarGlobales.isVisualizaUltimoRegForm()){
//                                if (modelDocumentos.getLbStatus().getText().equals("Anulado") || modelDocumentos.getLbStatus().getText().equals("Pagado")
//                                         || modelDocumentos.getLbStatus().getText().equals("Aplicada")){
//                                    modelDocumentos.getBtnEliminar().setEnabled(true); modelDocumentos.getBtnImprimir().setEnabled(true); 
//                                    modelDocumentos.getBtnXml().setVisible(true);
//                                }else{
//                                    modelDocumentos.getBtnGrabar().setEnabled(true); modelDocumentos.getBtnImportar().setEnabled(true);
//                                    modelDocumentos.getBtnEliminar().setEnabled(true); modelDocumentos.getBtnImprimir().setEnabled(true); 
//                                    modelDocumentos.getBtnPagoCobro().setEnabled(true); 
//                                    modelDocumentos.getBtnXml().setVisible(true);
//                                
//                                    modelDocumentos.getCmbAlmacen().requestFocus();
//                                }
//                            }
//                        }
//                    }else if (tablaBuscar.equals("import")){
//                        if (modelDocumentos.getcTipDoc().equals("DEV")){
//                            modelDocumentos.buscaDocumento(tbl_maestros.getValueAt(fsel, 3).toString().trim(), "FAV");;
//                        }else if (modelDocumentos.getcTipDoc().equals("DVC")){
//                            modelDocumentos.buscaDocumento(tbl_maestros.getValueAt(fsel, 3).toString().trim(), "FAC");;
//                        }else{
//                            
//                        }
//                        //modelDocumentos.buscaDocImport(id, tbl_maestros.getValueAt(fsel, 2).toString().trim(),
//                        //                               tbl_maestros.getValueAt(fsel, 4).toString().trim());
//                    }else if (tablaBuscar.equals("dnproducto")){
//                        //modelDocumentos.getTblDetalle().setValueAt(id, modelDocumentos.getTblDetalle().getSelectedRow(), 1);
//                        //modelDocumentos.getTblDetalle().setValueAt(nombre, modelDocumentos.getTblDetalle().getSelectedRow(), 2);
//                        modelDocumentos.getTfCodeArticulo().setText(id);
//                        modelDocumentos.getTfNombArticulo().setText(nombre);
//                        modelDocumentos.cargaDatosArticulo(id);
//                        
//                        //modelDocumentos.getTblDetalle().changeSelection(modelDocumentos.getTblDetalle().getSelectedRow(), 3, false, false);
//                        modelDocumentos.getCmbUndMed().requestFocus();
//                        codSelec = id;
//
//                        //modelDocumentos.buscaProducto(codSelec, "", "codigo", false);
//                        //modelDocumentos.action_bt_grabar(id, "", "", "codigo");   
//                    }
//
//                    //modelDocumentos.setVista(null);
//                    close();
//                }
//            }else if (modelCuentasPorCobrarPagar.getVista() instanceof CuentasPorCobrarPagar){
//                if(fsel<0){
//                    JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgSelectItem"));
//                }else{
//                    m = (DefaultTableModel) tbl_maestros.getModel();
//                    id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                    nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                    if (tablaBuscar.equals("DNPERSONAS")){
//                        id = tbl_maestros.getValueAt(fsel, 2).toString().trim();
//                        //modelCuentasPorCobrarPagar.buscaCuentasPorCobrarPagar(id);
//                        modelCuentasPorCobrarPagar.cargaModelTable(id);
//        
////                        modelCuentasPorCobrarPagar.setIdPersona(tbl_maestros.getValueAt(fsel, 0).toString().trim());
//                        modelCuentasPorCobrarPagar.getTxtPers().setText(id);
//                        modelCuentasPorCobrarPagar.getTxtNombre().setText(nombre);
//                    }
//
//                    //modelDocumentos.setVista(null);
//                    close();
//                }
//            }else if (modelCbtesElectronicos.getVista() instanceof VisualizarCbteElectronicos) {
//                if(fsel<0){
//                    JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgSelectItem"));
//                }else{
//                    m = (DefaultTableModel) tbl_maestros.getModel();
//                    id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                    nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                    if (tablaBuscar.equals("DNPERSONAS")){
//                        modelCbtesElectronicos.getTfIdCliente().setText(id);
//                        modelCbtesElectronicos.getLbNobreCliente().setText(nombre);
//                        modelCbtesElectronicos.cargaTabla();
//                    }
//                    
//                    close();
//                }
//            }else if (modelTecnico.getVista() instanceof Tecnico) {
//                if(fsel<0){
//                    JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgSelectItem"));
//                }else{
//                    m = (DefaultTableModel) tbl_maestros.getModel();
//                    id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                    nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                    modelTecnico.buscaTecnico(id);
//                    
//                    close();
//                }
//            }else if (modelMaestroReportesSistema.getVista() instanceof MaestroDeReportesSistema) {
//                if(fsel<0){
//                    JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgSelectItem"));
//                }else{
//                    m = (DefaultTableModel) tbl_maestros.getModel();
//                    id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                    nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                    
//                    if (tablaBuscar.equals("DNPERSONAS")){
//                        id = tbl_maestros.getValueAt(fsel, 2).toString().trim();
//                        modelMaestroReportesSistema.getTfCodPersona().setText(id);
//                        modelMaestroReportesSistema.getLbNombrePersona().setText(nombre);
//                        modelMaestroReportesSistema.getTfNumDoc().setText("");
//                    }else if (tablaBuscar.equals("DNINVENTARIO")){
//                        String idPersona = tbl_maestros.getValueAt(fsel, 2).toString().trim();
//                        modelMaestroReportesSistema.getTfCodPersona().setText(idPersona);
//                        modelMaestroReportesSistema.getTfNumDoc().setText(id);
//                        
//                        ((JTextField) modelMaestroReportesSistema.getJdate_desde().getDateEditor().getUiComponent()).setText("");
//                        ((JTextField) modelMaestroReportesSistema.getJdate_hasta().getDateEditor().getUiComponent()).setText("");
//                        //modelMaestroReportesSistema.getJdate_desde().setCalendar(null);
//                        //modelMaestroReportesSistema.getJdate_hasta().setCalendar(null);
//                    }
//                    
//                    close();
//                }
//            }else if(modelRepVentas.getVista() instanceof ReporteVentas){
//                if(tablaBuscar.equals("DNVENDEDOR")){
//                    m = (DefaultTableModel) tbl_maestros.getModel();
//                    id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                    nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                    
//                    modelRepVentas.setUsuario(id);
//                    modelRepVentas.setNombUser(nombre);
//                    
//                    modelRepVentas.setVista(null);
//                    close();
//                }
//            }else if (modelRepStock.getVista() instanceof ReporteStock){
//                if (tablaBuscar.equals("DNPRODUCTO")){
//                    m = (DefaultTableModel) tbl_maestros.getModel();
//                    id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                    nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                    
//                    modelRepStock.setProducto(id);
//                    modelRepStock.setNombProduct(nombre);
//                    
//                    modelRepStock.setVista(null);
//                    close();
//                }
//            }else if (modelRepCodBarra.getVista() instanceof ReporteCodBarra){
//                if (tablaBuscar.equals("DNPRODUCTO")){
//                    m = (DefaultTableModel) tbl_maestros.getModel();
//                    id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                    nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                    
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    Date fecha = new Date();
//                    String Fch = sdf.format(fecha);
//                        
//                    String sqlStock = "SELECT PRO_NOMBRE,INV_CODPRO,SUM(IF(INV_FECHA<='"+Fch+"',INV_CANTID*INV_FISICO*INV_CANUND,0)) AS STOCK FROM dninventario\n" +
//                                      "INNER JOIN dnproducto ON INV_CODPRO=PRO_CODIGO \n" +
//                                      "INNER JOIN dnempresas ON INV_EMPRESA=EMP_CODIGO\n" +
//                                      "WHERE INV_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND INV_CODPRO='"+id+"' GROUP BY INV_CODPRO";
//                    
//                    rs = conc.consultar(sqlStock);
//                    
//                    modelRepCodBarra.setProducto(id, rs.getInt("STOCK"));
//                    
//                    close();
//                }
//                modelRepCodBarra.setVista(null);
//            }else if (modelUnidadMedida.getVista() instanceof UnidadMedida){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                    
//                modelUnidadMedida.buscaCodigo(id);
//                
//                modelUnidadMedida.setVista(null);
//                close();
//            }else if (modelBancosInsPagos.getVista() instanceof BancosInsPagos){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                modelBancosInsPagos.buscaCodigo(id);
//                
//                modelBancosInsPagos.setVista(null);
//                close();
//            }else if (modelJornadas.getVista() instanceof Jornadas){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                modelJornadas.buscaCodigo(id);
//                modelJornadas.setVista(null);
//                close();
//            }else if (modelCargos.getVista() instanceof Cargos) {
//                if(fsel<0){
//                    JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgAddCodePadreCargo"));
//                }else{
//                    m = (DefaultTableModel) tbl_maestros.getModel();
//                    id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                    nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//
////                    modelCargos.setCodigo(id);
////                    modelCargos.setNombreCar(nombre);
//                    modelCargos.buscaCodigo(id);
//                        
//                    modelCargos.setVista(null);
//                    close();
//                }
//            }else 
            if (modelCreaUsuarios.getVista() instanceof CreaUsuarios){
                m = (DefaultTableModel) tbl_maestros.getModel();
                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
                
                modelCreaUsuarios.buscaUsuario(id);
                
                modelCreaUsuarios.setVista(null);
                close();
//            }else if(modelPos.getVista() instanceof POS){
//                if(tablaBuscar.equals("DNVENDEDOR")){
//                    m = (DefaultTableModel) tbl_maestros.getModel();
//                    id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                    nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                    
//                    modelPos.setVendedor(id);
//                    modelPos.updateVendedor();
//                    
//                    close();
//                }
//            }else if(modelCreaProveedor.getVista() instanceof CreaProveedor){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                modelCreaProveedor.buscaCodigo(id);
//                
//                modelCreaProveedor.setVista(null);
//                close();
            }else if(modelEmpresas.getVista() instanceof Empresas){
                m = (DefaultTableModel) tbl_maestros.getModel();
                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
                
                modelEmpresas.buscaCodigo(id);
                modelEmpresas.setVista(null);
                close();
//            }else if(modelMarca.getVista() instanceof Marcas){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                modelMarca.buscaCodigo(id);
//                
//                modelMarca.setVista(null);
//                close();
//            }else if(modelMarcasVehiculo.getVista() instanceof MarcasVehiculos){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                modelMarcasVehiculo.buscaCodigo(id);
//                
//                modelMarcasVehiculo.setVista(null);
//                close();
//            }else if (modelModeloVehiculo.getVista() instanceof ModeloVehiculo){
//                switch(tablaBuscar){
//                    case "DNMARCA":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelModeloVehiculo.setInfoMarca(id, nombre);
//
//                        break;
//                    }
//                    case "MODELO_VEHICULO":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelModeloVehiculo.buscaCodigo(id, nombre);
//
//                        break;
//                    }
//                    case "MODELO_VEHICULO_2":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelModeloVehiculo.setInfoModeloVeh(id, nombre);
//
//                        break;
//                    }
//                }
//                
//                modelModeloVehiculo.setVista(null);
//                close();
//            }else if (modelMotor.getVista() instanceof Motor){
//                switch(tablaBuscar){
//                    case "MOTOR":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelMotor.buscaCodigo(id);
//
//                        break;
//                    }
//                    case "TIPO_MOTOR":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelMotor.setInfoTipoMotor(id, nombre);
//
//                        break;
//                    }
//                    case "MARCA":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelMotor.setInfoMarcaVeh(id, nombre);
//
//                        break;
//                    }
//                    case "TIPO_CILINDRADA":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelMotor.setInfoTipoCilindrada(id, nombre);
//
//                        break;
//                    }
//                    case "COMBUSTIBLE":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelMotor.setInfoCombustible(id, nombre);
//
//                        break;
//                    }
//                }
//                
//                //modelMotor.setVista(null);
//                close();
//            }else if (modelVehiculo.getVista() instanceof Vehiculo){
//                switch(tablaBuscar){
//                    case "VEHICULO":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelVehiculo.buscaCodigo(id);
//
//                        break;
//                    }
//                    case "DNPERSONAS":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelVehiculo.setInfoCliente(id, nombre);
//
//                        break;
//                    }
//                    case "DNMARCA":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelVehiculo.setInfoMarca(id, nombre);
//
//                        break;
//                    }
//                    case "MODELO_VEHICULO":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelVehiculo.setInfoModeloVeh(id, nombre);
//
//                        break;
//                    }
//                    case "CATEGORIAS_VEHICULO":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelVehiculo.setInfoCategoriaVeh(id, nombre);
//                        
//                        break;
//                    }
//                    case "MOTOR":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelVehiculo.setInfoMotor(id, nombre);
//
//                        break;
//                    }
//                    case "TIPO_MOTOR":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelVehiculo.setInfoTipoMotor(id, nombre);
//
//                        break;
//                    }
//                    case "TRANSMISION":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelVehiculo.setInfoTransmision(id, nombre);
//
//                        break;
//                    }
//                    case "TRACCION":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelVehiculo.setInfoTraccion(id, nombre);
//
//                        break;
//                    }
//                    case "TIPO_CILINDRADA":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelVehiculo.setInfoTipoCilindrada(id, nombre);
//
//                        break;
//                    }
//                    case "COMBUSTIBLE":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelVehiculo.setInfoCombustible(id, nombre);
//
//                        break;
//                    }
//                }
//                
//                //modelMotor.setVista(null);
//                close();
//            }else if (modelOrdenReparacion.getVista() instanceof OrdenReparacion){
//                switch(tablaBuscar){
//                    case "ORDE_REPARACION":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                        String placa = tbl_maestros.getValueAt(fsel, 2).toString().trim();
//                
//                        modelOrdenReparacion.buscaDocumento(id);
//                        modelOrdenReparacion.buscaCodigo(placa);
//                        modelOrdenReparacion.getTfKilometraje().requestFocus();
//                        
//                        if (!VarGlobales.isVisualizaUltimoRegForm()){
//                            if (modelOrdenReparacion.getLbTotalDoc().getText().equals("Anulado")){
//                                    
//                            }else{
//                                modelOrdenReparacion.setlAgregar(false);
////                                modelOrdenReparacion.getGrabar().setEnabled(false);
//                            }
//                        }
//
//                        break;
//                    }
//                    case "VEHICULO":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelOrdenReparacion.buscaCodigo(id);
//                        modelOrdenReparacion.getTfKilometraje().requestFocus();
//
//                        break;
//                    }
//                    case "USUARIO":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelOrdenReparacion.getTfResponsable().setText(nombre);
//                        modelOrdenReparacion.setCodResponable(id);
//                        modelOrdenReparacion.getTfResponsable().requestFocus();
//
//                        break;
//                    }
//                    case "TECNICO":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                
//                        modelOrdenReparacion.getTfTecnico().setText(nombre);
//                        modelOrdenReparacion.setCodTecnico(id);
//                        modelOrdenReparacion.getTfCantidad().requestFocus();
//
//                        break;
//                    }
//                    case "import":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                        String placa = tbl_maestros.getValueAt(fsel, 2).toString().trim();
//                        
//                        if (modelOrdenReparacion.getTipdoc().equals("ORR")){
//                            modelOrdenReparacion.buscaDocumentoImport(id, "POR");
//                            modelOrdenReparacion.buscaCodigo(placa);
//                        }
//                        
//                        break;
//                    }
//                }
//                
//                //modelMotor.setVista(null);
//                close();
//            }else if (modelTipoCliPro.getVista() instanceof TipoCliPro){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                modelTipoCliPro.buscaCodigo(id);
//                
//                modelTipoCliPro.setVista(null);
//                close();
////            }else if (modelImpuestos.getVista() instanceof Impuestos){
////                m = (DefaultTableModel) tbl_maestros.getModel();
////                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
////                
////                modelImpuestos.buscaImpuesto(id);
////                
////                modelImpuestos.setVista(null);
////                close();
////            }else if (modelTipoContacto.getVista() instanceof TipoContacto){
////                m = (DefaultTableModel) tbl_maestros.getModel();
////                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
////                
////                modelTipoContacto.buscarTipoContacto(id);
////                modelTipoContacto.getTxtDescri().requestFocus();
////                //modelTipoContacto.setVista(null);
////                
////                close();
////            }else if (modelMoneda.getVista() instanceof Moneda){
////                m = (DefaultTableModel) tbl_maestros.getModel();
////                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
////                
////                modelMoneda.buscarMoneda(id);
////                modelMoneda.getTxtDescri().requestFocus();
////                //modelMoneda.setVista(null);
////                
////                close();
//            }else if (modelTipoMotor.getVista() instanceof TipoMotor){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                modelTipoMotor.buscaCodigo(id);
//                
//                modelTipoMotor.setVista(null);
//                close();
//            }else if (modelTransmision.getVista() instanceof Transmision){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                modelTransmision.buscaCodigo(id);
//                
//                modelTransmision.setVista(null);
//                close();
//            }else if (modelTraccion.getVista() instanceof Traccion){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                modelTraccion.buscaCodigo(id);
//                
//                modelTraccion.setVista(null);
//                close();
//            }else if (modelTipoCilindrada.getVista() instanceof TipoCilindrada){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                modelTipoCilindrada.buscaCodigo(id);
//                
//                modelTipoCilindrada.setVista(null);
//                close();
//            }else if (modelModeloCilindrada.getVista() instanceof ModeloCilindrada){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                modelModeloCilindrada.buscaCodigo(id);
//                
//                modelModeloCilindrada.setVista(null);
//                close();
//            }else if (modelCombustible.getVista() instanceof Combustible){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                modelCombustible.buscaCodigo(id);
//                
//                modelCombustible.setVista(null);
//                close();
//            }else if (modelCategoriasVehiculo.getVista() instanceof CategoriasVehiculos){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                modelCategoriasVehiculo.buscaCodigo(id);
//                
//                modelCategoriasVehiculo.setVista(null);
//                close();
//            }else if(modelUbicacionProductos.getVista() instanceof UbicacionProductos){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                modelUbicacionProductos.buscaCodigo(id);
//                
//                modelUbicacionProductos.setVista(null);
//                close();
//            }else if (modelRepEtiquetaCaja.getVista() instanceof ReporteEtiquetasCajas) {
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                if (modelRepEtiquetaCaja.getjTxtDesde().getText().equals("")){
//                    modelRepEtiquetaCaja.getjTxtDesde().setText(id);
//                }
//                
//                if (!modelRepEtiquetaCaja.getjTxtDesde().getText().equals("")){
//                    modelRepEtiquetaCaja.getjTxtHasta().setText(id);
//                }
//                modelRepEtiquetaCaja.setVista(null);
//                close();
//            }else if(modelRelacionDocBL.getVista() instanceof RelacionDoc_BL) {
//                if(fsel<0){
//                    JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgSelectItem"));
//                }else{
//                    switch (tablaBuscar) {
//                        case "DNINVENTARIO":
//                            m = (DefaultTableModel) tbl_maestros.getModel();
//                            id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                            nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                            
//                            modelRelacionDocBL.setTxtNunInvDoc(id);
//                            modelRelacionDocBL.buscarDocumento(id);
//                            
//                            break;
//                        case "DNMAESTRO":
//                            m = (DefaultTableModel) tbl_maestros.getModel();
//                            id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                            nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                            
//                            if (origen.equals("OrgMae")){
//                                modelRelacionDocBL.setTxtOrigen(id);
//                                modelRelacionDocBL.setLbOrigen(nombre);
//                            }else{
//                                modelRelacionDocBL.setTxtDestino(id);
//                                modelRelacionDocBL.setLbDestino(nombre);
//                            }
//                            
//                            break;
//                        case "DNRELACION_DOCUMENTOS":
//                            m = (DefaultTableModel) tbl_maestros.getModel();
//                            id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                            //nombre = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                            
//                            modelRelacionDocBL.setTxtNumdoc(id);
//                            modelRelacionDocBL.muestraDocBuscado();
//                            //modelRelacionDocBL.buscarDocumento(id);
//                            
//                            break;    
//                    }
//                    modelRelacionDocBL.setVista(null);
//                    close();
//                }
//            }else if (modelCambiarClave.getVista() instanceof CambiarClave){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                modelCambiarClave.buscaReg(id);
//                
//                modelCambiarClave.setVista(null);
//                close();
            }
//            else if (modelComisionVendedor.getVista() instanceof ComisionVendedor){
//                switch(tablaBuscar){
//                    case "DNVENDEDOR":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                        modelComisionVendedor.setCodDesde(id);
//                
//                        break;
//                    }
//                    case "DNVENDEDOR2":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                        modelComisionVendedor.setCodHasta(id);
//                        
//                        break;
//                    }
//                }
//                modelComisionVendedor.setVista(null);
//                close();
//            }else if (modelGrupoPermisos.getVista() instanceof GruposPermisos){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                modelGrupoPermisos.buscaCodigo(id);
//                
//                modelGrupoPermisos.setVista(null);
//                close();
////            }else if (modelVendedor.getVista() instanceof Vendedor){
////                m = (DefaultTableModel) tbl_maestros.getModel();
////                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
////                
////                modelVendedor.buscaCodigo(id);
////                
////                modelVendedor.setVista(null);
////                close();
//            }else if (modelPrecio.getVista() instanceof Precio){
//                switch(tablaBuscar){
//                    case "DNPRODUCTO":
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                        modelPrecio.setCodpro(id);
//                        break;
//                    case "DNLISTPRE":
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                        modelPrecio.setCodlis(id);
//                        break;
//                    case "DNUNDMEDIDA":
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                        modelPrecio.setUndmed(id);
//                        break;
//                    case "DNPRECIO":
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        String codpro = tbl_maestros.getValueAt(fsel, 1).toString().trim();
//                        String codlis = tbl_maestros.getValueAt(fsel, 2).toString().trim();
//                
//                        modelPrecio.buscarPrecio(id, codpro, codlis);
//                        break;
//                }
//                modelPrecio.setVista(null);
//                close();        
//            }else if(modelTipoDocumentos.getVista() instanceof TipoDocumentos){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                modelTipoDocumentos.setBuscaCodigo(id);
//                
//                modelTipoDocumentos.setVista(null);
//                close();
//            }else if(modelConfigAsientos.getVista() instanceof ConfigAsientos){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                
//                modelConfigAsientos.buscaConfig(id);
//                
//                modelConfigAsientos.setVista(null);
//                close();
//            }else if(modelAsientoManual.getVista() instanceof AsientoManual){
//                switch(tablaBuscar){
//                    case "DNINVENTARIO":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        String tipdoc = tbl_maestros.getValueAt(fsel, 1).toString();
//                        String clipro = tbl_maestros.getValueAt(fsel, 2).toString();
//                        
//                        modelAsientoManual.setTipdoc(tipdoc);
//                        modelAsientoManual.setCodmae(clipro);
//                        modelAsientoManual.buscaAsiento(id);
//                        
//                        break;
//                    }
//                    case "DNPERSONAS":{
//                        m = (DefaultTableModel) tbl_maestros.getModel();
//                        id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                        nombre = tbl_maestros.getValueAt(fsel, 1).toString();
//                        
//                        modelAsientoManual.setMaestro(id,nombre);
//                        break;
//                    }
//                }
//                modelAsientoManual.setVista(null);
//                close();
//            }else if(modelImprimirCheque.getVista() instanceof ImprimirCheque){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                nombre = tbl_maestros.getValueAt(fsel, 1).toString();
//                
//                modelImprimirCheque.setMaestro(id, nombre);
//                
//                modelImprimirCheque.setVista(null);
//                close();
//            }else if(modelRetencionIva.getVista() instanceof RetencionIVA){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                nombre = tbl_maestros.getValueAt(fsel, 1).toString();
//                
//                modelRetencionIva.setPersona(id, nombre);
//                
//                modelRetencionIva.setVista(null);
//                close();
//            }else if(modelRetencionIslr.getVista() instanceof RetencionISLR){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                nombre = tbl_maestros.getValueAt(fsel, 1).toString();
//                
//                modelRetencionIslr.setPersona(id, nombre);
//                
//                modelRetencionIslr.setVista(null);
//                close();
//            }else if(modelreporteIslr.getVista() instanceof reporteISLRlote){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                nombre = tbl_maestros.getValueAt(fsel, 1).toString();
//                
//                modelreporteIslr.setPersona(id,nombre);
//                
//                modelreporteIslr.setVista(null);
//                close();
//            }
////            else if(modelRetencionIvaventas.getVista() instanceof RetencionIVAventas){
////                m = (DefaultTableModel) tbl_maestros.getModel();
////                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
////                nombre = tbl_maestros.getValueAt(fsel, 1).toString();
////                
////                modelRetencionIvaventas.setPersona(id,nombre);
////                
////                modelRetencionIvaventas.setVista(null);
////                close();
////            }
//            else if(modelPagoDocs.getVista() instanceof PagoDocs){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                nombre = tbl_maestros.getValueAt(fsel, 1).toString();
//                
//                modelPagoDocs.setPersona(id,nombre);
//                
//                modelPagoDocs.setVista(null);
//                close();
//            }else if(modelProveedores.getVista() instanceof Proveedores){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                nombre = tbl_maestros.getValueAt(fsel, 1).toString();
//                
//                modelProveedores.setPersona(id,nombre);
//                if (!VarGlobales.isVisualizaUltimoRegForm()){
//                    modelProveedores.getBtnEliminar().setEnabled(true); modelProveedores.getBtnImprimir().setEnabled(true);
//                    
//                    modelProveedores.setlAgregar(false);
//                }
//                
//                modelProveedores.setVista(null);
//                close();
//            }else if(modelRepIvaVta.getVista() instanceof ReporteRetIVAventas){
//                m = (DefaultTableModel) tbl_maestros.getModel();
//                id = tbl_maestros.getValueAt(fsel, 0).toString().trim();
//                nombre = tbl_maestros.getValueAt(fsel, 1).toString();
//                
//                switch(tablaBuscar){
//                    case "DNEMPRESAS":{
//                        modelRepIvaVta.setEmpresa(id,nombre);
//                        break;
//                    }
//                    case "DNPERSONAS":{
//                        modelRepIvaVta.setPersona(id,nombre);
//                        break;
//                    }
//                }
//                modelRepIvaVta.setVista(null);
//                close();
//            }
        } catch (HeadlessException | NumberFormatException e) {
        
//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(ListaDeMaestros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void agregarMaestro(){
        Dimension desktopSize, jInternalFrameSize;
        String ori;
//        Categorias categorias;
       
        switch (tablaBuscar) {
            case "DNMAESTRO":
                //BuscaCliPos maestro = new BuscaCliPos(origen);
                if(origenDesk.equals("ERP")){
                    ori = origenDesk;
                }else{
                    ori = "";
                }
//                CreaProveedor maestro = new CreaProveedor(ori);
//                
////                desktopSize = escritorio.getSize();
//                desktopSize = desktopP.getSize();
//                jInternalFrameSize = maestro.getSize();
//                maestro.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
////                escritorio.add(maestro);
//                desktopP.add(maestro);
//                maestro.toFront();
//                maestro.setVisible(true);

                break;
            case "GRUPO":
//                categorias = new Categorias(true, "ERP", "Grupos");
//
//                desktopSize = escritorioERP.getSize();
//                jInternalFrameSize = categorias.getSize();
//                categorias.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//                escritorioERP.add(categorias);
//                categorias.toFront();
//                categorias.setVisible(true);
//                
                break;
            case "SUBGRUPO":
//                categorias = new Categorias(true, "ERP", "SubGrupos");
//
//                desktopSize = escritorioERP.getSize();
//                jInternalFrameSize = categorias.getSize();
//                categorias.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//                escritorioERP.add(categorias);
//                categorias.toFront();
//                categorias.setVisible(true);
//                
                break;
            case "DNPRODUCTO":
                if(origenDesk.equals("ERP")){
                    ori = origenDesk;
                }else{
                    ori = "";
                }
//                Productos produc = new Productos(true, ori);
//
////                desktopSize = escritorio.getSize();
//                desktopSize = desktopP.getSize();
//                jInternalFrameSize = produc.getSize();
//                produc.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
////                escritorio.add(produc);
//                desktopP.add(produc);
//                produc.toFront();
//                produc.setVisible(true);
                
                break;
            case "DNCLASIFICACION":
                if(origenDesk.equals("ERP")){
                    ori = origenDesk;
                }else{
                    ori = "";
                }
                
//                categorias = new Categorias(true, ori, "");
//
////                desktopSize = escritorio.getSize();
//                desktopSize = desktopP.getSize();
//                jInternalFrameSize = categorias.getSize();
//                categorias.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
////                escritorio.add(categorias);
//                desktopP.add(categorias);
//                categorias.toFront();
//                categorias.setVisible(true);
                        
                break;
            case "DNCARGOS":
                if(origenDesk.equals("ERP")){
                    ori = origenDesk;
                }else{
                    ori = "";
                }
//                Cargos cargos = new Cargos(ori);
//
////                desktopSize = escritorio.getSize();
//                desktopSize = desktopP.getSize();
//                jInternalFrameSize = cargos.getSize();
//                cargos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
////                escritorio.add(cargos);
//                desktopP.add(cargos);
//                cargos.toFront();
//                cargos.setVisible(true);
                        
                break;
            case "DNUNDMEDIDA":
                if(origenDesk.equals("ERP")){
                    ori = origenDesk;
                }else{
                    ori = "";
                }
//                UnidadMedida undmed = new UnidadMedida(ori);
//
////                desktopSize = escritorio.getSize();
//                desktopSize = desktopP.getSize();
//                jInternalFrameSize = undmed.getSize();
//                undmed.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
////                escritorio.add(cargos);
//                desktopP.add(undmed);
//                undmed.toFront();
//                undmed.setVisible(true);
                
                break;
            case "MOTOR":
//                Motor motor = new Motor(false, "ERP");
//
//                desktopSize = escritorioERP.getSize();
//                jInternalFrameSize = motor.getSize();
//                motor.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//                escritorioERP.add(motor);
//                motor.toFront();
//                motor.setVisible(true);

                break;
            case "DNMARCA":
//                MarcasVehiculos marcasVehiculos = new MarcasVehiculos(false, "ERP");
//
//                desktopSize = escritorioERP.getSize();
//                jInternalFrameSize = marcasVehiculos.getSize();
//                marcasVehiculos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//                escritorioERP.add(marcasVehiculos);
//                marcasVehiculos.toFront();
//                marcasVehiculos.setVisible(true);
                
                break;
            case "CATEGORIAS_VEHICULO":
//                CategoriasVehiculos categoriasVehiculos = new CategoriasVehiculos(false, "ERP");
//
//                desktopSize = escritorioERP.getSize();
//                jInternalFrameSize = categoriasVehiculos.getSize();
//                categoriasVehiculos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//                escritorioERP.add(categoriasVehiculos);
//                categoriasVehiculos.toFront();
//                categoriasVehiculos.setVisible(true);
        
                break;
            case "MODELO_VEHICULO":
//                ModeloVehiculo modeloVehiculo = new ModeloVehiculo(false, "ERP");
//
//                desktopSize = escritorioERP.getSize();
//                jInternalFrameSize = modeloVehiculo.getSize();
//                modeloVehiculo.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//                escritorioERP.add(modeloVehiculo);
//                modeloVehiculo.toFront();
//                modeloVehiculo.setVisible(true);
                
                break;
            case "TRANSMISION":
//                Transmision transmision = new Transmision(false, "ERP");
//
//                desktopSize = escritorioERP.getSize();
//                jInternalFrameSize = transmision.getSize();
//                transmision.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//                escritorioERP.add(transmision);
//                transmision.toFront();
//                transmision.setVisible(true);
                
                break;
            case "TRACCION":
//                Traccion traccion = new Traccion(false, "ERP");
//
//                desktopSize = escritorioERP.getSize();
//                jInternalFrameSize = traccion.getSize();
//                traccion.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//                escritorioERP.add(traccion);
//                traccion.toFront();
//                traccion.setVisible(true);
        
                break;
            case "TIPO_CILINDRADA":
//                TipoCilindrada tipoCilindrada = new TipoCilindrada(false, "ERP");
//
//                desktopSize = escritorioERP.getSize();
//                jInternalFrameSize = tipoCilindrada.getSize();
//                tipoCilindrada.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//                escritorioERP.add(tipoCilindrada);
//                tipoCilindrada.toFront();
//                tipoCilindrada.setVisible(true);
                
                break;
            case "TIPO_MOTOR":
//                TipoMotor tipoMotor = new TipoMotor(false, "ERP");
//
//                desktopSize = escritorioERP.getSize();
//                jInternalFrameSize = tipoMotor.getSize();
//                tipoMotor.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//                escritorioERP.add(tipoMotor);
//                tipoMotor.toFront();
//                tipoMotor.setVisible(true);
                
                break;
            case "COMBUSTIBLE":
//                Combustible combustible = new Combustible(false, "ERP");
//
//                desktopSize = escritorioERP.getSize();
//                jInternalFrameSize = combustible.getSize();
//                combustible.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//                escritorioERP.add(combustible);
//                combustible.toFront();
//                combustible.setVisible(true);
                
                break;
        }
        
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.l2fprod.common.swing.JLinkButton btnAgregarM;
    private com.l2fprod.common.swing.JLinkButton btnCancel;
    private com.l2fprod.common.swing.JLinkButton btnSelect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLeyenda1;
    private javax.swing.JLabel jLeyenda2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl_maestros;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_rif;
    // End of variables declaration//GEN-END:variables
}
