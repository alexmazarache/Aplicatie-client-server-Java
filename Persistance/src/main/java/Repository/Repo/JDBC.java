package Repository.Repo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBC {
    private Properties JDBCprops;


    private static final Logger logger = LogManager.getLogger(JDBC.class);
    public JDBC(Properties props){
        JDBCprops=props;
    }
    private  Connection instance=null;

    private Connection getNewConnection(){
        logger.traceEntry();
        //String driver=JDBCprops.getProperty("Persoane.jdbc.driver");

        String url=JDBCprops.getProperty("Moto.jdbc.url");

        logger.info("trying to connect to database ... {}",url);

        Connection con=null;
        try {
            //Class.forName(driver);

                con=DriverManager.getConnection(url);
        }
        /*catch (ClassNotFoundException e) {
            logger.error(e);
            System.out.println("Error loading driver " + e);
        }*/
        catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error getting connection "+ex);
        }
        return con;
    }

    public Connection getConnection(){
        logger.traceEntry();

        try {
            if (instance==null || instance.isClosed())
                instance=getNewConnection();

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DataBase "+e);
        }
        logger.traceExit(instance);

        return instance;
    }

}
