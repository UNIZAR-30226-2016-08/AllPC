package es.unizar.eina.allpc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Adrian on 06/04/2016.
 */
public class DBConnection {
    private static final String URL = "sql7.freemysqlhosting.net";
    private static final String USER = "sql7111177";
    private static final String PASS = "CGEtcz8cQS";
    private static final int PUERTO = 3306;
    Connection conexionMySQL = null;

    public DBConnection(){

    }
    public void conectar(){
        if (conexionMySQL == null) {
            String urlConexionMySQL = "jdbc:mysql://" + URL + ":" +	PUERTO + "/" + USER;

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
