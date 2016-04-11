package es.unizar.eina.allpc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Adrian on 06/04/2016.
 */
public class DBConnection {
    //PHPmyADMIN ->http://www.phpmyadmin.co/
    private static final String URL = "sql7.freemysqlhosting.net";
    private static final String DATABASE = "sql7114788";
    private static final String USER = "sql7114788";
    private static final String PASS = "VtugCHPbLC";
    private static final int PUERTO = 3306;
    Connection conexionMySQL = null;

    public DBConnection(){

    }
    public void conectar(){
        if (conexionMySQL == null) {
            String urlConexionMySQL = "jdbc:mysql://" + URL + ":" +	PUERTO + "/" + DATABASE;

            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                conexionMySQL =	DriverManager.getConnection(urlConexionMySQL,
                        USER, PASS);
                System.out.println("GG EASY");
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("ERRRROOOOOR TOAAAST 1");
                /*e.printStackTrace();*/
            }
            catch (SQLException e)
            {
                System.out.println("ERRRROOOOOR TOAAAST 2");
                e.printStackTrace();
            }
        }
    }


}
