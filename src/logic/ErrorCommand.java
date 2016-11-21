/*
 * DeleteCommand.java
 *
 * Created on 9 August 2003, 11:21
 */

package logic;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ass1.User;
import service.Service;

/**
 * This command is to remove a record from the database
 * @author  $author
 */
public class ErrorCommand implements Command {

	/**
	 * The helper class to delegate all function calls to
	 */
	private static Service srv;
	private String U_type;
	private User U_id;
	private String Path;

	/** Creates a new instance of DeleteCommand 
	 * @throws NamingException 
	 * @throws SQLException */
	public ErrorCommand() throws SQLException, NamingException {
		srv=new Service();
		U_type = "User";
		U_id = new User();
        // TODO: get the contact delegate using the delegate factory
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
	
		// TODO: get the id from the parameters
		// TODO: find the contact bean from the given id
		// TODO: call on the delete method from the delegate

		return "/Error.jsp";
	}
	public String getUserType(){
		return U_type;
	}
	
	public User getUserInfo(){
		return U_id;
	}
	public void setUserType(String i){
		U_type=i;
	}
	public void setUserInfo(User i){
		U_id=i;
	}
	public void setPath(String i){
		Path=i;
	}
}
