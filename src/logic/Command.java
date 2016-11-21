/*
 * Command.java
 *
 * Created on 9 August 2003, 11:03
 */

package logic;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ass1.User;

/**
 *
 * @author  yunki
 */
public interface Command {
	
	String execute(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException, SQLException, NamingException;
	public String getUserType();
	public User getUserInfo();
	public void setUserType(String i);
	public void setUserInfo(User i);
	public void setPath(String i);

}
