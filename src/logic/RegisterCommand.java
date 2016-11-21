/*
 * ListCommand.java
 *
 * Created on 9 August 2003, 11:46
 */

package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ass1.*;
import jdbc.*;
import service.*;

/**
 * This command finds all the phone book entries for a particular user
 * unless they are admin
 * @author  yunki
 */
public class RegisterCommand implements Command {
	/**
	 * The helper class to delegate all function calls to
	 */
	final private static int Max=10;
	private static Service srv;
	private static String[] title = new String[Max];
	private static String[] id = new String[Max];
	private DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	private String U_type;
	private User U_id;
	private String Path;
	private static int curPage=1;
	private static int talPage=100;
	/** Creates a new instance of ListCommand 
	 * @throws NamingException 
	 * @throws SQLException */
	public RegisterCommand() throws SQLException, NamingException {
		srv=new Service();
		U_type = "User";
		U_id = new User();
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException, SQLException, NamingException {
		String name = request.getParameter("pageT");
		clean();
		if(name.equals("Home")){
			return "/Register.jsp";
		}else if (name.equals("Register")){
			try {
				return signup(request,response);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			request.setAttribute("msg", "Resgiter error!");
			return "/Error.jsp";
		}
		return "/test.jsp";
		
	}
	public String signup(HttpServletRequest request,HttpServletResponse response) throws SQLException, NamingException, ParseException {
		String username = request.getParameter("name");	
		String nickname = request.getParameter("nick");
		String firstname = request.getParameter("first");
		String lastname = request.getParameter("last");
		String email = request.getParameter("email");
		String address = request.getParameter("addr");
		String card = request.getParameter("card");
		String password = request.getParameter("psw");
		String type = request.getParameter("UserType");
		String Birthday = request.getParameter("birth");
		Date birthday = new Date(0);
		if(!Birthday.equals("")){
			birthday = fmt.parse(Birthday);
		}
			User user = new User();
			user.setAddr(address);
			user.setBirth(birthday);
			user.setCard(card);
			user.setEmail(email);
			user.setFirst(firstname);
			user.setLast(lastname);
			user.setName(username);
			user.setNick(nickname);
			user.setPsw(password);
			user.setStatus(1);
				
			if (type.equals("User")){
					srv.UserRegist(user);
					U_id = srv.UserLogin(username, password, email);
					U_type="User";
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
				
			}else {
					srv.SellerRegist(user);
					U_id = srv.SellerLogin(username, password, email);
					U_type="Seller";
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
						
						title[i]=bf.getAttrVal(bf.AUTHOR)+":\n"+bf.getAttrVal(bf.TITLE);
						id[i]=Integer.toString(bk.get(i).getID());	
			
					}
					talPage=bk.size()/Max+1;
					request.setAttribute("list", title);
					request.setAttribute("bookid",id);
					request.setAttribute("page", Integer.toString(curPage));
					request.setAttribute("totalpage", Integer.toString(talPage));
					return "/Seller.jsp";
			}
		
	}	
	
	public void clean(){
		int i;
		for(i=0;i<Max;i++){			
			title[i]="";
			id[i]="";
		}
	}
	public String[] getTitle(){
		return title;
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
