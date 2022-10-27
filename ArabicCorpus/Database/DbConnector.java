package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbConnector {
    String URL, user, password;
    static Connection connection;

    public static Connection getConnection() {
		//String CONN_STRING = "jdbc:mysql://localhost:3306/aprog_dict?characterEncoding=UTF-8&serverTimezone=UTC";
		String CONN_STRING = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DbConnector.connection = DriverManager
                    .getConnection(CONN_STRING,"root","");
        } catch (Exception e) {
            System.out.println("Unable to get connection OR Connection string is missing.");
        }

        return DbConnector.connection;
    }
}
