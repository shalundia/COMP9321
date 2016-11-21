
package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
public class ChangeCommand implements Command {
	/**
	 * The helper class to delegate all function calls to
	 */
	final private static int Max=10;
	private static Service srv;
	private static String[] title = new String[Max];
	private DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	private String U_type;
	private User U_id;
	private String Path;

	/** Creates a new instance of ListCommand 
	 * @throws NamingException 
	 * @throws SQLException */
	public ChangeCommand() throws SQLException, NamingException {
		srv=new Service();
		U_type = "User";
		U_id = new User();
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException, SQLException, NamingException {
		if(request.getParameter("pageT").equals("User")||request.getParameter("pageT").equals("Seller")){
			request.setAttribute("msg", "");
			return "/Change.jsp";
		}else if(request.getParameter("pageT").equals("Pause")){
			//pause
			return pause(request);
		}else if (request.getParameter("pageT").equals("AdminUser")){
			return inactive(request);
		}else{
			return change(request);
		}
	}
	
	public String[] getTitle(){
		return title;
	}
	public String inactive(HttpServletRequest request) throws NamingException {
		String[] indexS;
		indexS = request.getParameterValues("index");
		
		int i=0;
		for(i=indexS.length-1;i>=0;i--){
			srv.BanUser(Integer.parseInt(indexS[i]));
		}
		request.setAttribute("msg", "Ban Succeed!");
		return "/Error.jsp";
	}
	
	public String change(HttpServletRequest request) throws NamingException {
		String nickname = request.getParameter("nick");
		String firstname = request.getParameter("first");
		String lastname = request.getParameter("last");
		String email = request.getParameter("email");
		String address = request.getParameter("addr");
		String card = request.getParameter("card");
		String password = request.getParameter("psw");
		String Birthday = request.getParameter("birth");
		Date birthday=new Date();
		
		try {
			birthday = fmt.parse(Birthday);
			U_id.setAddr(address);
			U_id.setBirth(birthday);
			U_id.setCard(card);
			U_id.setEmail(email);
			U_id.setFirst(firstname);
			U_id.setLast(lastname);
			U_id.setNick(nickname);
			U_id.setPsw(password);
			if (U_type.equals("User")){
				srv.ChangeUser(U_id);
				request.setAttribute("msg", "User information Update Succeed!");
				return "/Error.jsp";
			}else if(U_type.equals("Seller")) {
				srv.ChangeSeller(U_id);
				request.setAttribute("msg", "Seller information Update Succeed!");
				return "/Error.jsp";
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		request.setAttribute("msg", "Update fail!");
		return "/Error.jsp";
	}		


	public String pause(HttpServletRequest request) {

		String[] indexS;
		indexS = request.getParameterValues("index");
		
		int i=0;
		for(i=indexS.length-1;i>=0;i--){
			srv.PauseBook(Integer.parseInt(indexS[i]));
		}
		request.setAttribute("msg", "Pause Succeed!");
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