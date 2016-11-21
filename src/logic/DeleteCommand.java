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

import ass1.Book;
import ass1.BookList;
import ass1.Bookinfo;
import ass1.Cart;
import ass1.CartList;
import ass1.Picture;
import ass1.User;
import service.Service;

/**
 * This command is to remove a record from the database
 * @author  $author
 */
public class DeleteCommand implements Command {

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
	
	/** Creates a new instance of DeleteCommand 
	 * @throws NamingException 
	 * @throws SQLException */
	public DeleteCommand() throws SQLException, NamingException {
		srv=new Service();
		U_type = "User";
		U_id = new User();

	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException, SQLException, NamingException {
		String name = request.getParameter("pageT");
		clean();

		if(name.equals("Cart")){
			return cart(request);
		}else if(name.equals("CartBuy")){
			return buy(request);
			
		}else if(name.equals("AdminSeller")){
			return book(request);
		}

		return "/error.jsp";
	}
	public String buy(HttpServletRequest request) throws SQLException, NamingException{
		String[] indexS;
		indexS = request.getParameterValues("index");
		Cart ct=new Cart();
		CartList cl = new CartList();
		CartList tmp = srv.ShowCart(U_id.getID());
		
		int i=0;
		int j=0;
		
		for(j=0;j<indexS.length;j++){
			for(i=0;i<tmp.size();i++){
				if(tmp.get(i).getBook()==Integer.parseInt(indexS[j])){
					cl.add(tmp.get(i));
					break;
				}
			}
		}
		int ret =srv.Buy(cl);
		if(ret==-1){
			request.setAttribute("msg", "Purchasing error!");
		}else{
			request.setAttribute("msg", "Thanks for purchasing!");

		}
		return "/Error.jsp";
		
	}
	public String book(HttpServletRequest request) throws SQLException, NamingException{
		String[] indexS;
		indexS = request.getParameterValues("index");
		
		int i=0;
		for(i=indexS.length-1;i>=0;i--){
			srv.RemoveBook(Integer.parseInt(indexS[i]));
		}
		
		BookList bk = srv.getRandamPage();
		Bookinfo bf = new Bookinfo();
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
	
	public String cart(HttpServletRequest request){
		String[] indexS;
		indexS = request.getParameterValues("index");
		
		int i=0;
		for(i=indexS.length-1;i>=0;i--){
			srv.RemoveCart(Integer.parseInt(indexS[i]), U_id.getID());
		}
		
		
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
		for(i=0;i<min;i++){			
			bk = srv.getBookByID(cl.get(i).getBook());
			bf=bk.getBookinfo();
			title[i]=bf.getAttrVal(bf.AUTHOR)+":\n"+bf.getAttrVal(bf.TITLE);
			id[i]=Integer.toString(bk.getID());	
			pic=srv.getPictureByID(bk.getPicture(1), Path);
			img[i]=pic.getLocalName();
			pr[i]=Float.toString(bk.getPrice());

		}
		
		talPage=cl.size()/Max+1;
		request.setAttribute("list", title);
		request.setAttribute("img", img);
		request.setAttribute("bookid",id);
		request.setAttribute("price",pr);
		request.setAttribute("page", Integer.toString(curPage));
		request.setAttribute("totalpage", Integer.toString(talPage));
		return "/Cart.jsp";			
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
