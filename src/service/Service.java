package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.naming.NamingException;

import ass1.*;
import jdbc.SalesDAOImpl;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
public class Service {
	
	private final int ERR=-1;
	private final int OK=1;
	private final int ACTIVE=1;
	private final int INACTIVE=0;
	private String admin_pwd;
	private SalesDAOImpl slDao;
	
	public Service() throws SQLException, NamingException{
		slDao = new SalesDAOImpl();
		admin_pwd="123";
	}

	public int UserRegist(User b){
		int ret=slDao.storeBuyer(b);
		if(ret!=-1){
			User u = UserInfo(ret);
			Confirm(ret,u.getEmail(),"User");
			return ret;
		}else{
			return ERR;
		}
	}
	
	public User UserInfo(int i){
		return slDao.findBuyerByID(i);
	}
	
	public User SellerInfo(int i){
		return slDao.findSellerByID(i);
	}
	
	public int SellerRegist(User b){
		int ret=slDao.storeSeller(b);
		if(ret!=-1){
			User u = SellerInfo(ret);
			Confirm(ret,u.getEmail(),"Seller");
			return ret;
		}else{
			return ERR;
		}	
	}
	
	public int AddBook(Book bk){
		int ret;
		ret=slDao.storeBookinfo(bk.getBookinfo());
		if(ret==-1){
			return ERR;
		}
		bk.setBookinfoID(ret);
		ret=slDao.storeBook(bk);
		if(ret==-1){
			return ERR;		
		}
		return ret;
	}
	
	public int PauseBook(int id){
		return slDao.updateBookStatus(id,INACTIVE);
	}
	
	public User UserLogin(String name,String pwd,String email){
		User u=new User();
		u.setName(name);
		u.setPsw(pwd);
		u.setEmail(email);
		u = slDao.findBuyer(u);
		if(u.getStatus()==INACTIVE){
			return new User();
		}else{
			return u;
		}	
	}
	
	public User SellerLogin(String name,String pwd,String email){
		User u=new User();
		u.setName(name);
		u.setPsw(pwd);
		u.setEmail(email);
		
		return slDao.findSeller(u);
	}

	public int AdminLogin(String name,String pwd,String email){
		if(name.equals("admin")&&pwd.equals(this.admin_pwd)){
			return OK;
		}else{
			return ERR;
		}
	}	

	public BookList getRandamPage() throws SQLException, NamingException{
		BookList bklist = new BookList();
		Bookinfo bf=new Bookinfo();
		Picture pic = new Picture();
		bklist=slDao.findRandamPage();
		int i=0;
		for(i=0;i<bklist.size();i++){
			
			bf=slDao.getBookinfoByID(bklist.get(i).getBookinfoID());
			bklist.get(i).setBookinfo(bf);
		}
		return bklist;
	}
	
	public BookList Search(Bookinfo bk) throws SQLException, NamingException{
		ArrayList<Bookinfo> bflist = new ArrayList<Bookinfo>();
		BookList bklist = new BookList();
		BookList bklist_tmp = new BookList();
		Book b_tmp=new Book();
		Bookinfo bf_tmp=new Bookinfo();
		bflist=(ArrayList<Bookinfo>) slDao.findBookinfo(bk);
		int i;
		int j;
		for(i=0;i<bflist.size();i++){
			bf_tmp=bflist.get(i);
			bklist_tmp=slDao.findBookByinfoID(Integer.parseInt(bf_tmp.getAttrVal(0)));
			for(j=0;j<bklist_tmp.size();j++){
				if(bklist_tmp.get(j).getStatus()==1){
					bklist_tmp.get(j).setBookinfo(bf_tmp);
					bklist.add(bklist_tmp.get(j));
				}
			}
		}
		return bklist;
	}

	public int AddCart(int bookid,int userid){
		int ret=ERR;
		Cart ct=new Cart();
		ct = slDao.findCartByBookID(bookid,userid);

		if(ct.getID()==-1){
			ct.setBook(bookid);
			ct.setUser(userid);
			ct.setNum(1);
			ret = slDao.storeCart(ct);
		}else{
			ct.setNum(ct.getNum()+1);
			ret = slDao.updateCart(ct);

		}
		return ret;
	}
	
	public int RemoveCart(int bookid,int userid){
		Cart ct=new Cart();
		Removed rm = new Removed();
		ct.setBook(bookid);
		ct.setUser(userid);
		rm.cartToRemoved(ct);
		int ret = slDao.storeRemoved(rm);
		if(ret != ERR){
			return 	slDao.deleteCart(ct);
		}
		return ret;
	}
	
	public int Buy(CartList ct){
		String item="";
		Sold sd = new Sold();
		Book bk = new Book();
 		/*remove from cart*/
		int i=0;
		int ret=-1;
		for(i=0;i<ct.size();i++){
			slDao.deleteCart(ct.get(i));
			/*add to sold*/
			sd.cartToSold(ct.get(i));
			ret = slDao.storeSold(sd);
			bk=getBookByID(sd.getBook());
			item ="Book: "+ bk.getBookinfo().getAttrVal(3)+"\n"+"Price: "+Float.toString(bk.getPrice())+"\n\n";
			if(ret==-1){
				return ERR;
			}
		}
		User u = UserInfo(sd.getUser());
		Notice(u.getEmail(),item);
		return ret;
	}

	public CartList ShowCart(int userid){
		
		return slDao.findCartByUser(userid);
	}
	
	public int RemoveBook(int bookid){

		return slDao.deleteBook(bookid);
	}
	
	public int BanUser(int userid){
		return slDao.updateBuyerStatus(userid,INACTIVE);
	}
	
	public int ChangeUser(User u){
		return slDao.updateBuyer(u);
	}

	public int ChangeSeller(User u){
		return slDao.updateSeller(u);
	}
	
	public UserList ListUser(){
		return slDao.findAllBuyer();
	}
	
	public SoldList ListHistorySold(int uid){
		return slDao.findSoldByUser(uid);
		
	}
	public RemovedList ListHistoryRemoved(int uid){
		return slDao.findRemovedbyUser(uid);
	}
	
	public Picture  getPictureByID(int id,String path){
		Picture pic = slDao.findPictureByID(id);
		String s1=path+"/img/"+Integer.toString(id)+pic.getName();
		String s2="img/"+Integer.toString(id)+pic.getName();

		if(pic.getID()!=-1){
			File file2 = new File(s1);     
	        OutputStream out = null;
			try {
				out = new FileOutputStream(file2);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				out.write(pic.getBlob());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pic.setLocalName(s2);
		
		return pic;
	}
	
	public Book getBookByID(int id){
		Book bk = new Book();
		bk=slDao.findBookBybookID(id);
		Bookinfo bf =slDao.getBookinfoByID(bk.getBookinfoID());
		bk.setBookinfo(bf);
		return bk;
	}
	public BookList ListSeller(int id){
		BookList bk = new BookList();
		bk=slDao.findBookBySellerID(id);
		int i;
		for(i=0;i<bk.size();i++){
			Bookinfo bf =new Bookinfo();
			bf=slDao.getBookinfoByID(bk.get(i).getBookinfoID());
			bk.get(i).setBookinfo(bf);
		}
		return bk;
	}
	
	 public int AddPicture(Picture pic){
         return  slDao.storePicture(pic);
	 }
	 
	 public void Confirm(int i,String to,String Ut){
			//send a email to the user...
			
			String subject = new String("Verify your BookVender account");

			String message = new String("Thanks for signing up for BookVender, please follow this link to  " +
		         		 "verify your account: http://localhost:8080/ass1/Search?pageT=Verify&pageN=Login&index="+i+"&UserType="+Ut);
			sendEmail(to, subject, message);
			System.out.println("Sent verification email...");
	}
	 
	 public void Notice(String to,String msg){
			String subject = new String("Notices of your Purchase");
			sendEmail(to, subject, msg);
	}


	public void sendEmail(String to, String subject, String sendMessage) {	
			final String from = "comp9321.zz@hotmail.com";
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.live.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.user", from);
			props.put("mail.smtp.password", "zzn123456");
			
			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, "zzn123456");
				}
			  });

			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(to));
				message.setSubject(subject);
				message.setText(sendMessage);
				Transport.send(message);
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}	
		}
	 
	 
	public List<Entity> SearchEntity(String name , String type){
		return slDao.findEntity(name , type);
	}
	public Entity GetEntity(int id){
		return slDao.findEntityByID(id);
	}
	public List<Relation> SearchRelation(Entity e) throws SQLException{
		return slDao.findRelation(e);
	}
	public void GraphStore(){
		Bookinfo bf = new Bookinfo();
		Entity au = new Entity();
		Entity pub = new Entity();
		Entity ven = new Entity();
		Entity ab = new Entity();
		Entity pi = new Entity();
		Relation r1 = new Relation();
		Relation r2 = new Relation();
		int i_au=bf.AUTHOR;
		int i_t=bf.TITLE;
		int i_p=bf.PUBLISHER;
		int retA=-1;
		int retV=-1;
		int retP=-1;
		int ret=-1;
		int i = 0;
		for(i=1;i<100;i++){
			bf=slDao.getBookinfoByID(i);
			if(!bf.getAttrVal(0).equals("")){
				if(bf.getAttrVal(1).equals("article")){
					//setup entity
					au.setupAuthor(bf.getAttrVal(i_au));
					pub.setupPublisher(bf.getAttrVal(i_t));
					ven.setupVenue(bf.getAttrVal(i_p));
					
					retA=slDao.storeEntity(au);
					retP=slDao.storeEntity(pub);
					retV=slDao.storeEntity(ven);
					
					if(retA!=-1&&retP!=-1){
						ab.setupAuthorby();
						ret=slDao.storeEntity(ab);
						if(ret!=-1){
							r1.setupRelation(retP,ret,retA);
							slDao.storeRelation(r1);
						}
					}
					if(retV!=-1&&retP!=-1){
						pi.setupPublishedin();
						ret=slDao.storeEntity(pi);
						if(ret!=-1){
							r2.setupRelation(retP, ret, retV);
							slDao.storeRelation(r2);
						}
					}
				}
			}
		}
	}

}
