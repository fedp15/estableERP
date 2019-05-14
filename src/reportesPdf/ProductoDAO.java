package reportesPdf;

import Modelos.conexion;
//import database.DBconexion;
//import factura.ProductosCanasta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductoDAO extends conexion{
    
    public List <ProductoVO> buscarProductoFacturaFormato(String codEmpresa,String tipDoc, String numDoc){
        CallableStatement cs = null;
        List <ProductoVO> productos = new ArrayList<>();
        // No ingreso ningun dato
        if (codEmpresa.equals("") && tipDoc.equals("") && numDoc.equals("")){
            return null;
        }
        String sql = "{call buscarProductoFormatoUnoJ(?,?,?)}";
        try{
            //DBconexion con = new DBconexion();
            creaConexion();
            
            cs = conn.prepareCall(sql);
            //cs = con.getConnection().prepareCall(sql);
            cs.setString(1, codEmpresa);
            cs.setString(2, tipDoc);
            cs.setString(3, numDoc);
            cs.executeQuery();
            try(ResultSet rset = cs.getResultSet()){
                while(rset.next())
                {
                    if(rset.getInt(5) == 0){
                        continue;
                    }
                    ProductoVO producto  = new ProductoVO();
                    producto.setId(rset.getString(1));
                    producto.setNombre(rset.getString("pro_nombre"));
                    producto.setCantidad(rset.getInt("inv_cantid"));
                    producto.setImagen(rset.getString(4));
                    producto.setStock(rset.getInt(5));
                    producto.setPrecio_inicial(rset.getBigDecimal("inv_precio"));
                    producto.setPrecio_venta(rset.getBigDecimal("inv_precio"));
                    productos.add(producto);
                }
            }
            
            //conn = null;
            conn.close();
            cs.close();
        }catch(SQLException e){
            System.out.println("Clases.ProductoDAO.buscarProductoFacturaFormato ERROR");
            System.err.println(e.getMessage());
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return productos;
    }
    
//     public static void actualizarProducto(String id_producto, int cantidad){
//        CallableStatement cs = null;
//        String sql = "{call actualizarProducto (?,?)}";
//        try {
//            DBconexion con = new DBconexion();
//            cs = con.getConnection().prepareCall(sql);
//            cs.setString(1, id_producto);
//            cs.setInt(2, cantidad);
//            cs.executeQuery();
//        } catch (SQLException e) {
//        }
//    }
}
