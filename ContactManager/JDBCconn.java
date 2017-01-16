/**
 * @author Lopamudra Muduli <lopamudra.muduli@utdallas.edu>
 * 
 */
package contactmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* JDBConn class creates and hold a singleton object of the SQL connection object */
public class JDBCconn {
    private static Connection conn = null;

    public static Connection sqlconn(){
        if(conn == null){
            try
            {
                DriverManager.deregisterDriver(new com.mysql.jdbc.Driver());
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = DriverManager.getConnection("jdbc:mysql://localhost/contact_manager?user=root&password=pass");
            } catch(ClassNotFoundException |IllegalAccessException | InstantiationException| SQLException ex){
                System.out.println("Error in connection: " + ex.getMessage());
            }
        }
        return conn;
    }
}
