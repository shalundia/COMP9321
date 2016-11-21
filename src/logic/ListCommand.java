/*
 * ListCommand.java
 *
 * Created on 9 August 2003, 11:46
 */

package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
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
public class ListCommand implements Command {
	/**
	 * The helper class to delegate all function calls to
	 */
	final private static int Max=10;
	private static Service srv;
	private static String[] title = new String[Max];
	private static String[] id = new String[Max];
	private static String[] img = new String[Max];
	private static String[] pr = new String[Max];
	private static int curPage=1;
	private static int talPage=100;
	private String U_type;
	private User U_id;
	private String Path;
	private DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	private static int monitorID=0;

	/** Creates a new instance of ListCommand 
	 * @throws NamingException 
	 * @throws SQLException */
	public ListCommand() throws SQLException, NamingException {
		srv=new Service();
		U_type = "User";
		U_id = new User();
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException, SQLException, NamingException {
		String name = request.getParameter("pageT");
		clean();
		if(name==null){
			return book(request);
		}
		if(name.equals("Home")){
			return book(request);
		}else if(name.equals("Cart")){
			return cart(request);
		}else if(name.equals("Depot")){
			return seller(request);
		}else if(name.equals("AdminUser")){
			return adminuser(request);
		}else if(name.equals("AdminSeller")){
			return adminseller(request);
		}else if(name.equals("MonitorSold")){
			return sold(request);
		}else if(name.equals("MonitorRemoved")){
			return removed(request);
		}else if(name.equals("Chart")){
			return chartsold(request);
		}
		return "/Error.jsp";

	}
	
	public String removed(HttpServletRequest request) throws SQLException, NamingException{
		int id=Integer.parseInt(request.getParameter("index"));
		RemovedList sl = srv.ListHistoryRemoved(id);
		String[] pub =new String[1];
		String[] ta=new String[1];
		String[] tr=new String[1];
		
		Book bk=new Book();
		User u = new User();
		if(sl.size()>0){
			pub= new String[sl.size()];
			ta= new String[sl.size()];
			tr= new String[sl.size()];
			int i;
			for(i=0;i<sl.size();i++){
				bk=srv.getBookByID(sl.get(i).getBook());
				pub[i]=bk.getBookinfo().getAttrVal(3);
				
				ta[i]=sl.get(i).getAddTime().toString();
				tr[i]=sl.get(i).getRmvTime().toString();
			}
		}
		request.setAttribute("pageT", "MonitorRemoved");
		request.setAttribute("index", request.getParameter("index"));

		request.setAttribute("title", pub);
		request.setAttribute("timeAdd", ta);
		request.setAttribute("timeRmv", tr);
		request.setAttribute("number",sl.size());
		return "/Monitor.jsp";
		
	}
	public String chartsold(HttpServletRequest request) throws SQLException, NamingException{
		int id=monitorID;
		SoldList sl = srv.ListHistorySold(id);
		String[] ts=new String[1];
		Float[] pr=new Float[1];
		Book bk=new Book();
		User u = new User();
		if(sl.size()>0){
			ts= new String[sl.size()];
			pr= new Float[sl.size()];
			int i;
			for(i=0;i<sl.size();i++){
				bk=srv.getBookByID(sl.get(i).getBook());
				ts[i]=fmt.format(sl.get(i).getSoldTime());
				pr[i]=bk.getPrice();
				
				u=srv.UserInfo(sl.get(i).getUser());
			}
		}
		request.setAttribute("pageT", "Chart");
		request.setAttribute("time", ts);
		request.setAttribute("price", pr);
		request.setAttribute("number",sl.size());
		return "/Chart.jsp";
	}
	public String sold(HttpServletRequest request) throws SQLException, NamingException{
		int id=Integer.parseInt(request.getParameter("index"));
		monitorID=id;
		SoldList sl = srv.ListHistorySold(id);
		String[] pub =new String[1];
		String[] ts=new String[1];
		String[] pr=new String[1];
		String[] vr=new String[1];
		Book bk=new Book();
		User u = new User();
		if(sl.size()>0){
			pub= new String[sl.size()];
			ts= new String[sl.size()];
			pr= new String[sl.size()];
			vr= new String[sl.size()];
			int i;
			for(i=0;i<sl.size();i++){
				bk=srv.getBookByID(sl.get(i).getBook());
				pub[i]=bk.getBookinfo().getAttrVal(3);
				
				ts[i]=sl.get(i).getSoldTime().toString();
				
				pr[i]=Float.toString(bk.getPrice());
				
				u=srv.UserInfo(sl.get(i).getUser());
				vr[i]=u.getName();
			}
		}
		request.setAttribute("pageT", "MonitorSold");
		request.setAttribute("index", request.getParameter("index"));

		request.setAttribute("title", pub);
		request.setAttribute("time", ts);
		request.setAttribute("price", pr);
		request.setAttribute("vender", vr);
		request.setAttribute("number",sl.size());
		return "/Monitor.jsp";
		
	}
	public String adminuser(HttpServletRequest request) throws SQLException, NamingException{
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
	}
	
	public String adminseller(HttpServletRequest request) throws SQLException, NamingException{
		BookList bk = srv.getRandamPage();
		Bookinfo bf = new Bookinfo();
		int i;
		for(i=0;i<Max;i++){
			bf=bk.get(i).getBookinfo();
			title[i]=bf.getAttrVal(bf.TITLE);
			id[i]=Integer.toString(bk.get(i).getID());
			
		}
		talPage=bk.size()/Max+1;
		request.setAttribute("pageT", "AdminSeller");
		request.setAttribute("list", title);
		request.setAttribute("bookid",id);
		request.setAttribute("page", Integer.toString(curPage));
		request.setAttribute("totalpage", Integer.toString(talPage));

		return "/Admin.jsp";
	}
	
	
	
	public String[] getTitle(){
		return title;
	}
	
	public String book(HttpServletRequest request) throws SQLException, NamingException{
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
	public String cart(HttpServletRequest request) {
		Book bk = new Book();
		Bookinfo bf = new Bookinfo();
		Picture pic = new Picture();
		
		int min;

	    CartList cl = srv.ShowCart(U_id.getID());
		if(cl.size()>Max){
			min=Max;
		}else{
			min=cl.size();
		}
		int i;
		
		for(i=0;i<min;i++){
			bk = srv.getBookByID(cl.get(i).getBook());
			bf=bk.getBookinfo();
			title[i]=bf.getAttrVal(bf.AUTHOR)+":\n"+bf.getAttrVal(bf.TITLE);
			id[i]=Integer.toString(cl.get(i).getBook());	
			pic=srv.getPictureByID(bk.getPicture(1), Path);
			img[i]=pic.getLocalName();
			pr[i]=Float.toString(bk.getPrice());
		}
		
		talPage=cl.size()/Max+1;
		request.setAttribute("list", title);
		request.setAttribute("price", pr);
		request.setAttribute("img", img);
		request.setAttribute("bookid",id);
		request.setAttribute("page", Integer.toString(curPage));
		request.setAttribute("totalpage", Integer.toString(talPage));
		return "/Cart.jsp";		
	}
	
	public String seller(HttpServletRequest request) {	
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
			img[i]="";
			pr[i]="";
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
