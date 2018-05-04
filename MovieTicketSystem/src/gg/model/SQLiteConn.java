/**
 * The SQLiteConn program implements an application that can connect to database.
 *  
 * @author  b03505051
 * @version 1.0
 * @since   2017-06-01
 */
package gg.model;

import java.sql.* ;

import javax.swing.JOptionPane;


public class SQLiteConn {
	/**this is variable**/
	public static Connection conn  ;
	/**
	* This method is used to get connection to database.
	* @param conn The Connection.
	* @throws ClassNotFoundException, SQLException
	* @return Connection Return conn if connect successfully.
	*/
	public Connection ConnDB() throws ClassNotFoundException, SQLException{
		try{
			Class.forName("org.sqlite.JDBC") ;
			conn = DriverManager.getConnection("jdbc:sqlite:movie_2.db") ;
			//JOptionPane.showMessageDialog(null,"Connection Succeed !!");
			
			return conn ; 
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e);
			return null ;
		}
	}
}
