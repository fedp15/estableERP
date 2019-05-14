package util;

import Modelos.conexion;
import java.math.BigDecimal;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.text.DecimalFormat;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CargaTablas extends conexion {
    private int i, numColum = 0;
    private boolean alinear = false;
    public String [] columnas;
    public String [] filas;
    
    public void cargatablas(JTable tabla, String SQL, String[] column) {
        columnas = column;
        
        JTable tblEjemplo = new JTable();
        JScrollPane scpEjemplo= new JScrollPane();

        DefaultTableModel dtm = new DefaultTableModel(null,columnas);

        //***** Se declaran las variables de conexion en null
        Connection Conn = null;
        ResultSet rs = null;
        Statement consulta = null;   
    
        //Guardo la Consulta en una variable String en este caso la llamo "sql"        
        String sql=(SQL);
        System.out.println("CargaTabla: "+sql);
        
        try{
            this.creaConexion();
            this.consulta = this.conn.prepareStatement(sql);
            rs = this.consulta.executeQuery();
            
            while(rs.next()) {
                if (rs.getMetaData().getColumnCount()==1){
                    String [] filas={rs.getString(1)};
                    dtm.addRow(filas);
                }
                if (rs.getMetaData().getColumnCount()==2){
                    String [] filas={rs.getString(1),rs.getString(2)};
                    dtm.addRow(filas);                    
                }
                if (rs.getMetaData().getColumnCount()==3){
                    String [] filas={rs.getString(1),rs.getString(2),rs.getString(3)};
                    dtm.addRow(filas);                    
                }
                if (rs.getMetaData().getColumnCount()==4){
                    String [] filas={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)};
                    dtm.addRow(filas);                    
                }
                if (rs.getMetaData().getColumnCount()==5){
                    String [] filas={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)};
                    dtm.addRow(filas);                    
                }
                if (rs.getMetaData().getColumnCount()==6){
                    String [] filas={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)};
                    dtm.addRow(filas);                    
                }
                if (rs.getMetaData().getColumnCount()==7){
                    String [] filas={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)};
                    dtm.addRow(filas);                    
                }
                if (rs.getMetaData().getColumnCount()==8){
                    String [] filas={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)};
                    dtm.addRow(filas);                    
                }
                if (rs.getMetaData().getColumnCount()==9){
                    String [] filas={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)};
                    dtm.addRow(filas);                    
                }
                if (rs.getMetaData().getColumnCount()==10){
                    DecimalFormat df = new DecimalFormat("#,###.00");
                    String precio, impuesto, total;
                    numColum = 10;
                    
                    try {
                        Double.parseDouble(rs.getString(7));
                        precio = df.format(new BigDecimal(rs.getString(7)).doubleValue());
                    } catch (NumberFormatException nfe){
                        precio = rs.getString(7);
                    }
                    
                    try {
                        Double.parseDouble(rs.getString(7));
                        impuesto = df.format(new BigDecimal(rs.getString(8)).doubleValue());
                    } catch (NumberFormatException nfe){
                        impuesto = rs.getString(8);
                    }
                    
                    try {
                        Double.parseDouble(rs.getString(7));
                        total = df.format(new BigDecimal(rs.getString(10)).doubleValue());
                    } catch (NumberFormatException nfe){
                        total = rs.getString(10);
                    }
                        
                    String [] filas={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),precio,impuesto,rs.getString(9),total};
                    dtm.addRow(filas);                    
                }
                if (rs.getMetaData().getColumnCount()==11){
                    String [] filas={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11)};
                    dtm.addRow(filas);                    
                }
                if (rs.getMetaData().getColumnCount()==12){
                    String [] filas={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12)};
                    dtm.addRow(filas);                    
                }
            }            
        }catch (ClassNotFoundException | SQLException e){
        }
        //JOptionPane.showMessageDialog(null, "Error SQL Exception");
        
        tabla.setModel(dtm);
    }   
}