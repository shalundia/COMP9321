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

import ass1.*;
import service.Service;

/**
 * This command is to remove a record from the database
 * @author  $author
 */
public class DetailCommand implements Command {

	/**
	 * The helper class to delegate all function calls to
	 */
	private static Service srv;
	private String U_type;
	private User U_id;
	private String Path;

	/** Creates a new instance of DeleteCommand */
	public DetailCommand() throws SQLException, NamingException {
		srv=new Service();
		U_type = "User";
		U_id = new User();
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("index"));
		Book bk = srv.getBookByID(id);
		Picture pic = srv.getPictureByID(bk.getPicture(1), Path);
		String tmp = bk.getBookinfo().toStringDetail()+"\nPrice:"+Float.toString(bk.getPrice());
		tmp=tmp+"\nAvailable:"+Integer.toString(bk.getNum())+"\n";
		request.setAttribute("detail", tmp);
		request.setAttribute("index", id);
		request.setAttribute("img", pic.getLocalName());
    	request.setAttribute("msg", "");

		return "/Detail.jsp";
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
