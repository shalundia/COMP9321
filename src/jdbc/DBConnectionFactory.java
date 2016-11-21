package jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * @author srikumarv
 * This class looks up the database via JNDI and returns a connection to the DAO Implementation class
 * 
 */
public class DBConnectionFactory {
	
	static Logger logger = Logger.getLogger(DBConnectionFactory.class.getName());
	private static DBConnectionFactory factory = null;
	private DataSource ds = null;
	private InitialContext ctx;
	
	private DBConnectionFactory() throws NamingException{
		ctx = new InitialContext();
		ds = (DataSource) ctx.lookup("java:comp/env/jdbc/cs9321");
		logger.info("Database found:"+ds.toString());
	}
	
	public DataSource getDataSource(){
		return ds;
	}
	
	public static Connection getConnection() throws SQLException, NamingException{
		
		if(factory==null)
			factory = new DBConnectionFactory();
		Connection conn = factory.getDataSource().getConnection();
		
		return conn;
	}

}
