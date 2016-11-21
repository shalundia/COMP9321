/*
 * AddCommand.java
 *
 * Created on 9 August 2003, 11:20
 */

package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ass1.Book;
import ass1.Bookinfo;
import ass1.Picture;
import ass1.SoldList;
import ass1.User;
import service.Service;

/**
 * This command adds a new contact entry
 * @author $author 
 */
public class AddCommand implements Command {
	/**
	 * The helper class to delegate all function calls to
	 */
	private static Service srv;
	private String U_type;
	private String Path;
	private User U_id;
	/** Creates a new instance of AddCommand 
	 * @throws NamingException 
	 * @throws SQLException */
	public AddCommand() throws SQLException, NamingException {
		srv=new Service();
		U_type = "User";
		U_id = new User();
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {

		String pageT= request.getParameter("pageT");
		if(pageT.equals("Detail")||pageT.equals("Result")){
			return addbooktocart(request);
		}else if(pageT.equals("Addbook")){
			try {
				return SellerAddBook(request);
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.setAttribute("msg", "add book error");
				return "/Error.jsp";
			}
		}else if(pageT.equals("Seller")){
			return "/Addbook.jsp";
		}else{
	    	request.setAttribute("msg", "add to cart error");
			return "/Error.jsp";
		}
	}
	public Bookinfo constructBookinfo(HttpServletRequest request){
		Bookinfo bf = new Bookinfo();
		String name;
		String val;
		Enumeration<String> t2 = request.getParameterNames() ;
		while(t2.hasMoreElements()){
			name=(String)t2.nextElement();
			val=request.getParameter(name);
			bf.setAttr(name, val);
		}
		return bf;
		
	}
	private static String getName(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}
	public String SellerAddBook(HttpServletRequest request) throws IOException, ServletException, FileUploadException {
		Bookinfo bf = new Bookinfo();
		Book book = new Book();
		InputStream put;

		float pr = 0;
		if(!request.getParameter("price").equals("")){
			pr=Float.parseFloat(request.getParameter("price"));
		}
		bf = constructBookinfo(request);
		Part filePart = request.getPart("picture");
		put =filePart.getInputStream();
				
		if(put.available()>0){
			Picture pic = new Picture(); 
			byte[] bt = new byte[put.available()];
			put.read(bt);
			pic.setBlob(bt);
			pic.setName(getName(filePart));
			int ret =  srv.AddPicture(pic);
			book.setPicture(1, ret);
		}else{
			book.setPicture(1, -1);
		}
			
		
		book.setBookinfo(bf);
		book.setStatus(1);
		book.setNum(1);
		book.setPrice(pr);
		book.setVender(U_id.getID());
		srv.AddBook(book);
		request.setAttribute("msg", "Add book successful!");
		return "/Error.jsp";
	}
	
	public String addbooktocart(HttpServletRequest request){

	    int bookID = Integer.parseInt(request.getParameter("index"));
	    int ret = -1;
	    ret = srv.AddCart(bookID, U_id.getID());
	    if(ret==-1){
	    	request.setAttribute("msg", "Add to cart error!");
	    }else{
	    	request.setAttribute("msg", "Add to cart successfull!");
	    }
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
