package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

public class JDBCUtil {
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testjava30", "root", "Caxauxi@9599");
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
    public static void closeConnection(Connection connection){
        try {
            if(connection != null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void getInfo(Connection connection){
        try {
            if(connection != null){
                DatabaseMetaData mtdt = connection.getMetaData();
                System.out.println(mtdt.getDatabaseProductName());
                System.out.println(mtdt.getDatabaseProductVersion());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
