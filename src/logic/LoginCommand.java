/*
 * LoginCommand.java
 *
 * Created on 9 August 2003, 11:12
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
import ass1.UserList;
import service.Service;



/**
 * This is the command that will be used for logging in users.
 * If logon is successful, the command should place a list of phonebook entries
 * in the request attriubutes.
 * @author  yunki
 */
public class LoginCommand implements Command {
	/**
	 * The helper class to delegate all function calls to
	 */
	final private static int Max=10;
	private static Service srv;
	private String U_type;
	private User U_id;
	private String Path;

	private static String[] title = new String[Max];
	private static String[] id = new String[Max];
	private static int curPage=1;
	private static int talPage=100;
	
	/** Creates a new instance of LoginCommand 
	 * @throws NamingException 
	 * @throws SQLException */
	public LoginCommand() throws SQLException, NamingException {
		srv=new Service();
		U_type = "User";
		U_id = new User();
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException, SQLException, NamingException {
		String pageT=request.getParameter("pageT");
		clean();
		if(pageT.equals("Home")){
	    	request.setAttribute("msg", "");
			return "/Login.jsp";
		}else if(pageT.equals("Admin")){
	    	request.setAttribute("msg", "");
			return "/AdminLogin.jsp";
		}else if(pageT.equals("AdminLogin")){
			return AdminLog(request);
		}else if(pageT.equals("Verify")){
			return verify(request);
		}else{
			return LoginAction(request,response);
		}
	}
	public String verify(HttpServletRequest request) throws SQLException, NamingException{
		
		int id = Integer.parseInt(request.getParameter("index"));
		String type = request.getParameter("UserType");
		User u = new User();
		
		if(type.equals("User")){
			u = srv.UserInfo(id);
		    if(u.getID()!=-1){
		    	U_type= "User"; 
		    	U_id = u;
		    	return userMainPage(request);
		    }else{
		    	request.setAttribute("msg", "Verify error!");
		    	return "/Error.jsp";
		    }
		}else{
			u=srv.SellerInfo(id);
		    if(u.getID()!=-1){
		    	U_type= "Seller"; 
		    	U_id = u;
		    	return sellerMainPage(request);
		    }else{
		    	request.setAttribute("msg", "Verify error!");
		    	return "/Error.jsp";
		    }	
		}
		
	}
	
	public String AdminLog(HttpServletRequest request){
		String password = request.getParameter("psw");
		if(password.equals("123")){
			UserList ul = new UserList();
			User u = new User();
			ul = srv.ListUser();
			
			int i;
			int min;
			
			if(ul.size()>Max){
				min=Max;
			}else{
				min=ul.size();
			}
			
			for(i=0;i<min;i++){
				u=ul.get(i);
				title[i]=u.toString();
				id[i]=Integer.toString(u.getID());
			}
			talPage=ul.size()/Max+1;
			request.setAttribute("pageT", "AdminUser");
			request.setAttribute("list", title);
			request.setAttribute("bookid",id);
			request.setAttribute("page", Integer.toString(curPage));
			request.setAttribute("totalpage", Integer.toString(talPage));
			return "/Admin.jsp";
		}else{
	    	request.setAttribute("msg", "Login error!");
	    	return "/AdminLogin.jsp";
		}
		
		
	}
	public String LoginAction(HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException{
		String username = request.getParameter("name");
		String password = request.getParameter("psw");
		String email = request.getParameter("email");
		User user = new User();
		String type = request.getParameter("UserType");
		if (type.equals("User")){
			    user =  srv.UserLogin(username, password, email);
			    
			    if(user.getID()!=-1){
			    	U_type= "User"; 
			    	U_id = user;
			    	return userMainPage(request);
			    }else{
			    	request.setAttribute("msg", "Login error!");
			    	return "/Login.jsp";
			    }
			
		}else if(type.equals("Seller")){
			 
				user =  srv.SellerLogin(username, password, email);
			    if(user.getID()!=-1){
			    	U_type= "Seller"; 
			    	U_id = user;
			    	return sellerMainPage(request);
			    }else{
			    	request.setAttribute("msg", "Login error!");
			    	return "/Login.jsp";
			    }		
		}else{
	    	request.setAttribute("msg", "Login error!");
	    	return "/Login.jsp";
		}
	}
	public String userMainPage(HttpServletRequest request) throws SQLException, NamingException{
		BookList bk = srv.getRandamPage();
		Bookinfo bf = new Bookinfo();
		
		int i;
		for(i=0;i<Max;i++){
			bf=bk.get(i).getBookinfo();
			title[i]=bf.getAttrVal(bf.TITLE);
			id[i]=Integer.toString(bk.get(i).getID());
			
		}
		request.setAttribute("list", title);
		request.setAttribute("bookid",id);
		request.setAttribute("page", Integer.toString(curPage));
		request.setAttribute("totalpage", Integer.toString(talPage));
    	return "/User.jsp";
	}
	public String sellerMainPage(HttpServletRequest request){
    	BookList bk = srv.ListSeller(U_id.getID());
		Bookinfo bf = new Bookinfo();
		int i;
		int min;
		if(bk.size()>Max){
			min=Max;
		}else{
			min=bk.size();
		}

		for(i=0;i<min;i++){
			bf=bk.get(i).getBookinfo();
			title[i]=bf.getAttrVal(bf.TITLE);
			id[i]=Integer.toString(bk.get(i).getID());
			
		}
		talPage=bk.size()/Max+1;
		request.setAttribute("list", title);
		request.setAttribute("bookid",id);
		request.setAttribute("page", Integer.toString(curPage));
		request.setAttribute("totalpage", Integer.toString(talPage));
		
    	return "/Seller.jsp";
	}
	public void clean(){
		int i;
		for(i=0;i<Max;i++){			
			title[i]="";
			id[i]="";
		}
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
