package Proyect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/*this class will*/
public class ConnectionPool {

    public Connection con = null;

    // JDBC - JAVA Data base Connection , connection parameters
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/company1";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "Rawadab99rm";

    private static Set<Connection> connections = new HashSet<Connection>();

    //singleton
    private static ConnectionPool instance;

    //singleton
    private ConnectionPool()
    {

        connections = new HashSet<>();
        int i =10;

        try {
            while (i>0)
            {
                con = creatConnection();
                connections.add(con);

                i--;
            }


        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }
    public static  ConnectionPool getInstance()
    {
        if( instance == null)
        {
            instance = new ConnectionPool();
        }

        return instance;
    }
    public  Connection creatConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }
    public synchronized Connection getConnection() throws SQLException {

            while (connections.size() == 0){
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Connection connection = connections.stream().findFirst().get();
            return connection;

    }

    public synchronized void restoreConnection(Connection connection){

        connections.add(connection);

        // Notify any threads that are waiting for a connection
        notify();
    }




    public void closeConnection()  throws SQLException {
        Iterator<Connection> it = connections.iterator();

        while (it.hasNext()) {
            Connection con = it.next();
            if(con!=null) {

                con.close();
                it.remove();
            }
        }

    }

}
