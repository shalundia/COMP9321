/*
 * LogoutCommand.java
 *
 * Created on 11 August 2003, 12:37
 */

package logic;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ass1.BookList;
import ass1.Bookinfo;
import ass1.User;
import service.Service;

/**
 *
 * @author $author
 */
public class LogoutCommand implements Command {
	
	/** Creates a new instance of LogoutCommand */
	final private static int Max=10;
	private static Service srv;
	private static String[] title = new String[Max];
	private static String[] id = new String[Max];
	private static String[] img = new String[Max];
	private static int curPage=1;
	private static int talPage=100;
	private String U_type;
	private User U_id;
	private String Path;
	
	public LogoutCommand() throws SQLException, NamingException {
		srv=new Service();
		U_type = "User";
		U_id = new User();
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, NamingException {
		HttpSession session = request.getSession(false);
		U_type = "User";
		U_id=new User();
		
		BookList bk = srv.getRandamPage();
		Bookinfo bf = new Bookinfo();
		int i;
		for(i=0;i<Max;i++){
			bf=bk.get(i).getBookinfo();
			title[i]=bf.getAttrVal(bf.TITLE);
			id[i]=Integer.toString(bk.get(i).getID());
			
		}
		talPage=bk.size()/Max+1;
		request.setAttribute("list", title);
		request.setAttribute("bookid",id);
		request.setAttribute("page", Integer.toString(curPage));
		request.setAttribute("totalpage", Integer.toString(talPage));

		return "/Home.jsp";
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
