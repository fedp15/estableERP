package util;

import java.sql.*;

public class conexion {
   static String bd = "omnix_db";
   static String login = "root";
   static String password = "";
   static String url = "jdbc:mysql://localhost:3307/"+bd;

   Connection conn = null;

   /** Constructor de DbConnection */
   public conexion() {
      try{
         //obtenemos el driver de para mysql
         Class.forName("com.mysql.jdbc.Driver");
         //obtenemos la conexión
         conn = DriverManager.getConnection(url,login,password);
         if (conn!=null){
            System.out.println("Conexión a base de datos "+bd+". listo");
         }
      }catch(SQLException e){
         System.out.println(e);
      }catch(ClassNotFoundException e){
         System.out.println(e);
      }
   }
   /* Permite retornar la conexión */
   public Connection getConnection(){
      return conn;
   }
   /* termina la conexion a la base de datos */
   public void desconectar(){
      conn = null;
      System.out.println("La conexion a la  base de datos "+bd+" a terminado");
   }
}