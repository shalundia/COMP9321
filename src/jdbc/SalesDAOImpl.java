package jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import ass1.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.sql.DataSource;


public class SalesDAOImpl {

	static Logger logger = Logger.getLogger(SalesDAOImpl.class.getName());
	private Connection connection;
	private DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	public SalesDAOImpl() throws SQLException, NamingException{
		try {
			connection = DBConnectionFactory.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Got connection");
	}
	
	
	public BookList findRandamPage() {
		BookList cast = new BookList();
		ArrayList<Integer> dl = new ArrayList<Integer>();
		Book bk=new Book();
		Random rd;
		try{
			Statement stmnt = connection.createStatement();
			String query_cast = "SELECT BOOK_ID FROM BOOK";					
			ResultSet res = stmnt.executeQuery(query_cast);
			while (res.next()) {  
				dl.add(res.getInt(1));   
			}  
			int i=10;
			rd = new Random();
			
			while(i>0){
				bk = findBookBybookID(dl.get(rd.nextInt(dl.size())));
				if(bk.getStatus()==1){
					cast.add(bk);
					i--;
				}
			}	
			res.close();
			stmnt.close();
		}catch(Exception e){
			System.out.println("Caught Exception");
			e.printStackTrace();
		}
		return cast;
	}
	
	
	public int storeBook(Book b) {
		int result=-1;
		PreparedStatement stmnt = null; 
		try{
			String sqlStr = "INSERT INTO BOOK (INF_ID, VENDER, PRICE, NUMBER, STATUS,PIC_ID1,PIC_ID2,PIC_ID3,PIC_ID4)"+
				"VALUES (?,?,?,?,?,?,?,?,?)";
			stmnt = connection.prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);
			stmnt.setInt(1,b.getBookinfoID());
			stmnt.setInt(2, b.getVender());
			stmnt.setFloat(3, b.getPrice());
			stmnt.setInt(4, b.getNum());
			stmnt.setInt(5, b.getStatus());
			stmnt.setInt(6, b.getPicture(1));
			stmnt.setInt(7, b.getPicture(2));
			stmnt.setInt(8, b.getPicture(3));
			stmnt.setInt(9, b.getPicture(4));

			logger.info("sql string is "+stmnt.toString());
			stmnt.executeUpdate();
            ResultSet rs = stmnt.getGeneratedKeys(); 
            if (rs.next()) {  
                result = rs.getInt(1);   
            }    

			logger.info("Statement successfully executed "+result);
			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store comment! ");
			e.printStackTrace();
		}
		return result;
	}

	public int deleteBook(int bookid){
		int result=-1;
		PreparedStatement stmnt = null; 
		try{
			String sqlStr = "DELETE FROM BOOK WHERE BOOK_ID = ?";
			stmnt = connection.prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);
			stmnt.setInt(1,bookid);

			logger.info("sql string is "+stmnt.toString());
			result = stmnt.executeUpdate();

			if(result!=-1){
				logger.info("Statement successfully executed "+result);
			}
			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store comment! ");
			e.printStackTrace();
		}
		return result;
	}
	
	
	public int updateBookStatus(int id,int st){

		int result=-1;
		PreparedStatement stmnt = null; 
		try{
			String sqlStr = "UPDATE BOOK SET STATUS = ? WHERE BOOK_ID = ?";
			stmnt = connection.prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);

			stmnt.setInt(1,st);
			stmnt.setInt(2,id);
			logger.info("sql string is "+stmnt.toString());
			result=stmnt.executeUpdate();
			if(result==1){
				logger.info("Statement successfully executed "+result);
			}
			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store comment! ");
			e.printStackTrace();
		}
		return result;

	}


	public BookList findBookByinfoID(int i){
		BookList cast = new BookList();
		Book bk = new Book();
		int id=0;
		float id2;
		
		PreparedStatement stmnt = null; 
		try{			
			
			String query = "SELECT * FROM BOOK WHERE INF_ID=?";
			stmnt = connection.prepareStatement(query);
			stmnt.setInt(1,i);
			logger.info("sql string is "+stmnt.toString());
			ResultSet res = stmnt.executeQuery();
			logger.info("Statement successfully executed "+res);
			while(res.next()){	
				id = res.getInt("BOOK_ID");
				logger.info(" "+id);
				bk.setID(id);
				
				id = res.getInt("INF_ID");
				logger.info(" "+id);
				bk.setBookinfoID(id);
				
				id = res.getInt("VENDER");
				logger.info(" "+id);
				bk.setVender(id);
				
				id2 = res.getFloat("PRICE");
				logger.info(" "+id2);
				bk.setPrice(id2);
				
				id = res.getInt("NUMBER");
				logger.info(" "+id);
				bk.setNum(id);
				
				id = res.getInt("STATUS");
				logger.info(" "+id);
				bk.setStatus(id);
				
				id = res.getInt("PIC_ID1");
				logger.info(" "+id);
				bk.addPicture(id);
				
				id = res.getInt("PIC_ID2");
				logger.info(" "+id);
				bk.addPicture(id);
				
				id = res.getInt("PIC_ID3");
				logger.info(" "+id);
				bk.addPicture(id);

				id = res.getInt("PIC_ID4");
				logger.info(" "+id);
				bk.addPicture(id);
				cast.add(bk);
			}
			
			res.close();
			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store comment! ");
			e.printStackTrace();
		}
		
		return cast;

	}
	public Book findBookBybookID(int i){
		Book bk = new Book();
		int id=0;
		float id2;
		PreparedStatement stmnt = null; 
		try{			
			String query = "SELECT * FROM BOOK WHERE BOOK_ID=?";
			stmnt = connection.prepareStatement(query);
			stmnt.setInt(1,i);
			logger.info("sql string is "+stmnt.toString());
			ResultSet res = stmnt.executeQuery();
			logger.info("Statement successfully executed "+res);
			while(res.next()){	
				id = res.getInt("BOOK_ID");
				logger.info(" "+id);
				bk.setID(id);
				
				id = res.getInt("INF_ID");
				logger.info(" "+id);
				bk.setBookinfoID(id);
				
				id = res.getInt("VENDER");
				logger.info(" "+id);
				bk.setVender(id);
				
				id2 = res.getFloat("PRICE");
				logger.info(" "+id2);
				bk.setPrice(id2);
				
				id = res.getInt("NUMBER");
				logger.info(" "+id);
				bk.setNum(id);
				
				id = res.getInt("STATUS");
				logger.info(" "+id);
				bk.setStatus(id);
				
				id = res.getInt("PIC_ID1");
				logger.info(" "+id);
				bk.addPicture(id);
				
				id = res.getInt("PIC_ID2");
				logger.info(" "+id);
				bk.addPicture(id);
				
				id = res.getInt("PIC_ID3");
				logger.info(" "+id);
				bk.addPicture(id);

				id = res.getInt("PIC_ID4");
				logger.info(" "+id);
				bk.addPicture(id);
			}
			res.close();
			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store comment! ");
			e.printStackTrace();
		}
		
		return bk;

	}
	
	public BookList findBookBySellerID(int i){
		Book bk = new Book();
		BookList bl = new BookList();

		int id=0;
		float id2;
		PreparedStatement stmnt = null; 
		try{			
			String query = "SELECT * FROM BOOK WHERE VENDER=?";
			stmnt = connection.prepareStatement(query);
			stmnt.setInt(1,i);
			logger.info("sql string is "+stmnt.toString());
			ResultSet res = stmnt.executeQuery();
			logger.info("Statement successfully executed "+res);
			while(res.next()){	
				id = res.getInt("BOOK_ID");
				logger.info(" "+id);
				bk.setID(id);
				
				id = res.getInt("INF_ID");
				logger.info(" "+id);
				bk.setBookinfoID(id);
				
				id = res.getInt("VENDER");
				logger.info(" "+id);
				bk.setVender(id);
				
				id2 = res.getFloat("PRICE");
				logger.info(" "+id2);
				bk.setPrice(id2);
				
				id = res.getInt("NUMBER");
				logger.info(" "+id);
				bk.setNum(id);
				
				id = res.getInt("STATUS");
				logger.info(" "+id);
				bk.setStatus(id);
				
				id = res.getInt("PIC_ID1");
				logger.info(" "+id);
				bk.addPicture(id);
				
				id = res.getInt("PIC_ID2");
				logger.info(" "+id);
				bk.addPicture(id);
				
				id = res.getInt("PIC_ID3");
				logger.info(" "+id);
				bk.addPicture(id);

				id = res.getInt("PIC_ID4");
				logger.info(" "+id);
				bk.addPicture(id);
				
				bl.add(bk);
			}
			res.close();
			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store comment! ");
			e.printStackTrace();
		}
		
		return bl;

	}
	public int storeBookinfo(Bookinfo b){
		String att="";
		int res=-1;
		try{
			int i=0;
			for(i=1;i<b.AttSize();i++){
				if(i==(b.AttSize()-1)){
					att=att+"?";
				}else{
					att=att+"?,";
				}
			}
			
			String sqlStr = 
				"INSERT INTO BOOKINFO VALUES (DEFAULT,"+att+")";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);
			logger.info("sql string is "+stmnt.toString());

			for(i=1;i<b.AttSize();i++){
				stmnt.setString(i,b.getAttrVal(i));
			}
			stmnt.executeUpdate();
			ResultSet rs = stmnt.getGeneratedKeys(); 
            if (rs.next()) {  
                res = rs.getInt(1);   
            }   

			if(res!=0){
				logger.info("Statement successfully executed "+res);
			}else{
				logger.info("Statement failly executed "+res);				
			}
			stmnt.close();
			return res;
		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			e.printStackTrace();
		}
		return res;
	};
	
	public List<Bookinfo> findBookinfo(Bookinfo b){
		ArrayList<Bookinfo> cast = new ArrayList<Bookinfo>();
		int id;		
		String st="";
		int i=0;
		int count=1;
		try{
			for(i=1;i<b.AttSize();i++){
				if(!b.getAttrVal(i).equals("")&&b.getAttrVal(i)!=null){
					if(!st.equals("")){
						st=st+" AND ";
					}
					st=st+b.getAttrName(i)+" LIKE ?";
					
				}
			}
			
			logger.info("The value passed is: "+st);
			
			String query = "SELECT * FROM BOOKINFO WHERE "+st;
			PreparedStatement stmnt = connection.prepareStatement(query);
			
			for(i=1;i<b.AttSize();i++){
				if(!b.getAttrVal(i).equals("")&&b.getAttrVal(i)!=null){
					stmnt.setString(count, "%"+b.getAttrVal(i)+"%");
					count++;
				}
			}				
			ResultSet res = stmnt.executeQuery();
			String att;
			while(res.next()){
				Bookinfo bk=new Bookinfo();

				id = res.getInt("BOOKINFO_ID");
				logger.info(" "+id);
				bk.setAttr("BOOKINFO_ID", Integer.toString(id));
				
				for(i = 1;i<bk.AttSize();i++){
					att=res.getString(i+1);
					logger.info(att);
					bk.setAttr(i, att);
				}
				cast.add(bk);
			}
			stmnt.close();
		}catch(Exception e){
			System.out.println("Caught Exception");
			e.printStackTrace();
		}
		return cast;
	};
	
	public Bookinfo getBookinfoByID(int id){
		Bookinfo bk=new Bookinfo();
		int i=0;
		try{
			logger.info("The value passed is: "+id);
			
			String query = "SELECT * FROM BOOKINFO WHERE BOOKINFO_ID=?";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setInt(1,id);
			ResultSet res = stmnt.executeQuery();
			String att;
			res.next();
			i = res.getInt("BOOKINFO_ID");
			logger.info(" "+i);
			bk.setAttr("BOOKINFO_ID", Integer.toString(i));
	
			for(i = 1;i<bk.AttSize();i++){
				att=res.getString(i+1);
				logger.info(att);
				bk.setAttr(i, att);
			}
			stmnt.close();
		}catch(Exception e){
			System.out.println("Caught Exception");
			e.printStackTrace();
		}
		return bk;
	}

	public int storeBuyer(User b) {
		// TODO Auto-generated method stub
		int res=-1;
		try{	
			
			String sqlStr = "INSERT INTO BUYER VALUES (DEFAULT,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);
			stmnt.setString(1,b.getName());
			stmnt.setString(2,b.getPsw());
			stmnt.setString(3,b.getEmail());
			stmnt.setString(4,b.getNick());
			stmnt.setString(5,b.getFirst());
			stmnt.setString(6,b.getLast());
			stmnt.setString(7,fmt.format(b.getBirth()));
			stmnt.setString(8,b.getAddr());
			stmnt.setString(9,b.getCard());
			stmnt.setInt(10,1);

			logger.info("sql string is "+stmnt.toString());
			stmnt.executeUpdate();
			ResultSet rs = stmnt.getGeneratedKeys(); 
            if (rs.next()) {  
                res = rs.getInt(1);   
            }    

			if(res!=-1){
				logger.info("Statement successfully executed "+res);
			}else{
				logger.info("Statement failly executed "+res);				
			}
			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			
		}
		return res;
	}

	public int updateBuyerStatus(int uid,int stat){

		int result=-1;
		PreparedStatement stmnt = null; 
		try{
			String sqlStr = "UPDATE BUYER SET STATUS=? WHERE BUYER_ID = ?";
			stmnt = connection.prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);

			stmnt.setInt(1,stat);
			stmnt.setInt(2,uid);

			logger.info("sql string is "+stmnt.toString());
			result=stmnt.executeUpdate();
			if(result==1)
				logger.info("Statement successfully executed "+result);
			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store comment! ");
			e.printStackTrace();
		}
		return result;

	}

	public int updateBuyer(User b){
		int result=-1;
		PreparedStatement stmnt = null; 
		try{
			String sqlStr = "UPDATE BUYER SET NICKNAME=?,"+
			"FIRSTNAME=?,LASTNAME=?,BIRTH=?,ADDRESS=?,CARD=?,STATUS=? WHERE BUYER_ID = ?";
			stmnt = connection.prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);

			stmnt.setString(1,b.getNick());
			stmnt.setString(2,b.getFirst());
			stmnt.setString(3,b.getLast());
			stmnt.setString(4,fmt.format(b.getBirth()));
			stmnt.setString(5,b.getAddr());
			stmnt.setString(6,b.getCard());
			stmnt.setInt(7,b.getStatus());
			stmnt.setInt(8,b.getID());

			logger.info("sql string is "+stmnt.toString());
			result=stmnt.executeUpdate();
			logger.info("Statement successfully executed "+result);
			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to update comment! ");
			e.printStackTrace();
		}
		return result;

	}

	public User findBuyer(User b) {
// TODO Auto-generated method stub
		User u_tmp=new User();
		int id;
		String val;
		Date dt=new Date(0);
		try{	
			
			String sqlStr = "SELECT * FROM BUYER WHERE NAME=? AND PASSWORD=? AND EMAIL=?";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr);
			stmnt.setString(1,b.getName());
			stmnt.setString(2,b.getPsw());
			stmnt.setString(3,b.getEmail());

			logger.info("sql string is "+stmnt.toString());
			ResultSet rs=stmnt.executeQuery();
            while (rs.next()) {  
            	id = rs.getInt("BUYER_ID");
				logger.info(" "+id);
				u_tmp.setID(id);

				val=rs.getString("NAME");
				logger.info(val);
				u_tmp.setName(val);

				val=rs.getString("PASSWORD");
				logger.info(val);
				u_tmp.setPsw(val);

				val=rs.getString("EMAIL");
				logger.info(val);
				u_tmp.setEmail(val);

				val=rs.getString("NICKNAME");
				logger.info(val);
				u_tmp.setNick(val);

				val=rs.getString("FIRSTNAME");
				logger.info(val);				
				u_tmp.setFirst(val);

				val=rs.getString("LASTNAME");
				logger.info(val);				
				u_tmp.setLast(val);

				dt=rs.getDate("BIRTH");
				u_tmp.setBirth(dt);
				logger.info(val);

				val=rs.getString("ADDRESS");
				u_tmp.setAddr(val);
				logger.info(val);

				val=rs.getString("CARD");
				u_tmp.setCard(val);
				logger.info(val);

            	id = rs.getInt("STATUS");
				logger.info(" "+id);
				u_tmp.setStatus(id);

            }    

			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			
		}
		return u_tmp;

	}
	public User findBuyerByID(int i){
		User u_tmp=new User();
		int id;
		String val;
		Date dt=new Date(0);
		try{	
			
			String sqlStr = "SELECT * FROM BUYER WHERE BUYER_ID=?";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr);
			stmnt.setInt(1,i);

			logger.info("sql string is "+stmnt.toString());
			ResultSet rs=stmnt.executeQuery();
            while (rs.next()) {  
            	id = rs.getInt("BUYER_ID");
				logger.info(" "+id);
				u_tmp.setID(id);

				val=rs.getString("NAME");
				logger.info(val);
				u_tmp.setName(val);

				val=rs.getString("PASSWORD");
				logger.info(val);
				u_tmp.setPsw(val);

				val=rs.getString("EMAIL");
				logger.info(val);
				u_tmp.setEmail(val);

				val=rs.getString("NICKNAME");
				logger.info(val);
				u_tmp.setNick(val);

				val=rs.getString("FIRSTNAME");
				logger.info(val);				
				u_tmp.setFirst(val);

				val=rs.getString("LASTNAME");
				logger.info(val);				
				u_tmp.setLast(val);

				dt=rs.getDate("BIRTH");
				u_tmp.setBirth(dt);
				logger.info(val);

				val=rs.getString("ADDRESS");
				u_tmp.setAddr(val);
				logger.info(val);

				val=rs.getString("CARD");
				u_tmp.setCard(val);
				logger.info(val);

            	id = rs.getInt("STATUS");
				logger.info(" "+id);
				u_tmp.setStatus(id);

            }    

			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			
		}
		return u_tmp;
		
	}
	
	public UserList findAllBuyer(){
		User u_tmp=new User();
		UserList ul=new UserList();
		int id;
		String val;
		Date dt=new Date(0);
		try{	
			
			String sqlStr = "SELECT * FROM BUYER";
			Statement stmnt = connection.createStatement();

			logger.info("sql string is "+stmnt.toString());
			ResultSet rs=stmnt.executeQuery(sqlStr);
            while (rs.next()) {  
            	
            	id = rs.getInt("BUYER_ID");
				logger.info(" "+id);
				u_tmp.setID(id);

				val=rs.getString("NAME");
				logger.info(val);
				u_tmp.setName(val);

				val=rs.getString("PASSWORD");
				logger.info(val);
				u_tmp.setPsw(val);

				val=rs.getString("EMAIL");
				logger.info(val);
				u_tmp.setEmail(val);

				val=rs.getString("NICKNAME");
				logger.info(val);
				u_tmp.setNick(val);

				val=rs.getString("FIRSTNAME");
				logger.info(val);				
				u_tmp.setFirst(val);

				val=rs.getString("LASTNAME");
				logger.info(val);				
				u_tmp.setLast(val);

				dt=rs.getDate("BIRTH");
				u_tmp.setBirth(dt);
				logger.info(val);

				val=rs.getString("ADDRESS");
				u_tmp.setAddr(val);
				logger.info(val);

				val=rs.getString("CARD");
				u_tmp.setCard(val);
				logger.info(val);

            	id = rs.getInt("STATUS");
				logger.info(" "+id);
				u_tmp.setStatus(id);

				ul.add(u_tmp);

            }    

			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			
		}
		return ul;
	}

	public User findSeller(User b) {
// TODO Auto-generated method stub
		User u_tmp=new User();
		int id;
		String val;
		Date dt=new Date(1950-0-1);
		try{	
			
			String sqlStr = "SELECT * FROM SELLER WHERE NAME=? AND PASSWORD=? AND EMAIL=?";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr);
			stmnt.setString(1,b.getName());
			stmnt.setString(2,b.getPsw());
			stmnt.setString(3,b.getEmail());

			logger.info("sql string is "+stmnt.toString());
			ResultSet rs=stmnt.executeQuery();
            while (rs.next()) {  
            	id = rs.getInt("SELLER_ID");
				logger.info(" "+id);
				u_tmp.setID(id);

				val=rs.getString("NAME");
				logger.info(val);
				u_tmp.setName(val);

				val=rs.getString("PASSWORD");
				logger.info(val);
				u_tmp.setPsw(val);

				val=rs.getString("EMAIL");
				logger.info(val);
				u_tmp.setEmail(val);

				val=rs.getString("NICKNAME");
				logger.info(val);
				u_tmp.setNick(val);

				val=rs.getString("FIRSTNAME");
				logger.info(val);				
				u_tmp.setFirst(val);

				val=rs.getString("LASTNAME");
				logger.info(val);				
				u_tmp.setLast(val);

				dt=rs.getDate("BIRTH");
				u_tmp.setBirth(dt);
				logger.info(val);

				val=rs.getString("ADDRESS");
				u_tmp.setAddr(val);
				logger.info(val);

				val=rs.getString("CARD");
				u_tmp.setCard(val);
				logger.info(val);

            	id = rs.getInt("STATUS");
				logger.info(" "+id);
				u_tmp.setStatus(id);

            }    

			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			
		}
		return u_tmp;

	}
	
	public User findSellerByID(int i){
		User u_tmp=new User();
		int id;
		String val;
		Date dt=new Date(0);
		try{	
			
			String sqlStr = "SELECT * FROM SELLER WHERE SELLER_ID=?";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr);
			stmnt.setInt(1,i);

			logger.info("sql string is "+stmnt.toString());
			ResultSet rs=stmnt.executeQuery();
            while (rs.next()) {  
            	id = rs.getInt("SELLER_ID");
				logger.info(" "+id);
				u_tmp.setID(id);

				val=rs.getString("NAME");
				logger.info(val);
				u_tmp.setName(val);

				val=rs.getString("PASSWORD");
				logger.info(val);
				u_tmp.setPsw(val);

				val=rs.getString("EMAIL");
				logger.info(val);
				u_tmp.setEmail(val);

				val=rs.getString("NICKNAME");
				logger.info(val);
				u_tmp.setNick(val);

				val=rs.getString("FIRSTNAME");
				logger.info(val);				
				u_tmp.setFirst(val);

				val=rs.getString("LASTNAME");
				logger.info(val);				
				u_tmp.setLast(val);

				dt=rs.getDate("BIRTH");
				u_tmp.setBirth(dt);
				logger.info(val);

				val=rs.getString("ADDRESS");
				u_tmp.setAddr(val);
				logger.info(val);

				val=rs.getString("CARD");
				u_tmp.setCard(val);
				logger.info(val);

            	id = rs.getInt("STATUS");
				logger.info(" "+id);
				u_tmp.setStatus(id);

            }    

			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			
		}
		return u_tmp;
		
	}
	
	public int updateSeller(User b){
		int result=-1;
		PreparedStatement stmnt = null; 
		try{
			String sqlStr = "UPDATE SELLER SET NICKNAME=?,"+
			"FIRSTNAME=?,LASTNAME=?,BIRTH=?,ADDRESS=?,CARD=?,STATUS=? WHERE SELLER_ID = ?";
			stmnt = connection.prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);

			stmnt.setString(1,b.getNick());
			stmnt.setString(2,b.getFirst());
			stmnt.setString(3,b.getLast());
			stmnt.setString(4,fmt.format(b.getBirth()));
			stmnt.setString(5,b.getAddr());
			stmnt.setString(6,b.getCard());
			stmnt.setInt(7,b.getStatus());
			stmnt.setInt(8,b.getID());

			logger.info("sql string is "+stmnt.toString());
			result=stmnt.executeUpdate();
			logger.info("Statement successfully executed "+result);
			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store comment! ");
			e.printStackTrace();
		}
		return result;

	}
	public int storeSeller(User b) {
		// TODO Auto-generated method stub
		int res=-1;
		try{	
			String sqlStr = "INSERT INTO SELLER VALUES (DEFAULT,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);
			stmnt.setString(1,b.getName());
			stmnt.setString(2,b.getPsw());
			stmnt.setString(3,b.getEmail());
			stmnt.setString(4,b.getNick());
			stmnt.setString(5,b.getFirst());
			stmnt.setString(6,b.getLast());
			stmnt.setString(7,fmt.format(b.getBirth()));
			stmnt.setString(8,b.getAddr());
			stmnt.setString(9,b.getCard());
			stmnt.setInt(10,1);

			logger.info("sql string is "+stmnt.toString());
			stmnt.executeUpdate();
			ResultSet rs = stmnt.getGeneratedKeys(); 
            if (rs.next()) {  
                res = rs.getInt(1);   
            }	

            if(res!=0){
				logger.info("Statement successfully executed "+res);
			}else{
				logger.info("Statement failly executed "+res);				
			}
			stmnt.close();

		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			e.printStackTrace();
		}
		return res;	
	}
	
	public Cart findCartByBookID(int bookid,int uid){
		Cart ct=new Cart();
		int id;
		Timestamp dt=new Timestamp(0);
		try{	
			
			String sqlStr = "SELECT * FROM CART WHERE C_BUYER=? AND C_BOOK=?";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr);
			stmnt.setInt(1,uid);
			stmnt.setInt(2,bookid);

			logger.info("sql string is "+stmnt.toString());
			ResultSet rs=stmnt.executeQuery();
            if (rs.next()) {  
            	id = rs.getInt("CART_ID");
				logger.info(" "+id);
				ct.setID(id);

				id = rs.getInt("C_BUYER");
				logger.info(" "+id);
				ct.setUser(id);

				id = rs.getInt("C_BOOK");
				logger.info(" "+id);
				ct.setBook(id);

				id=rs.getInt("NUMBER");
				ct.setNum(id);
				logger.info(" "+id);
				
				dt=rs.getTimestamp("TIME_A");
				ct.setAddTime(dt);
				logger.info(" "+dt.toString());
            }    

			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			e.printStackTrace();
		}
		return ct;

	}

	public CartList findCartByUser(int uid){
		Cart ct=new Cart();
		CartList cl = new CartList();
		Timestamp dt=new Timestamp(0);
		int id;
		try{	
			
			String sqlStr = "SELECT * FROM CART WHERE C_BUYER=?";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr);
			stmnt.setInt(1,uid);

			logger.info("sql string is "+stmnt.toString());
			ResultSet rs=stmnt.executeQuery();
            while (rs.next()) {  
            	id = rs.getInt("CART_ID");
				logger.info(" "+id);
				ct.setID(id);

				id = rs.getInt("C_BUYER");
				logger.info(" "+id);
				ct.setUser(id);

				id = rs.getInt("C_BOOK");
				logger.info(" "+id);
				ct.setBook(id);

				id=rs.getInt("NUMBER");
				ct.setNum(id);
				logger.info(" "+id);
				
				dt=rs.getTimestamp("TIME_A");
				ct.setAddTime(dt);
				logger.info(" "+dt.toString());
				
				cl.add(ct);
            }    

			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			e.printStackTrace();
		}
		return cl;
	}

	public int storeCart(Cart ct){
		int res=-1;
		try{	
			String sqlStr = "INSERT INTO CART VALUES (DEFAULT,?,?,?,?)";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);
			stmnt.setInt(1,ct.getUser());
			stmnt.setInt(2,ct.getBook());
			stmnt.setInt(3,ct.getNum());
			stmnt.setString(4, ts.format(ct.getAddTime()));
			
			logger.info("sql string is "+stmnt.toString());
			stmnt.executeUpdate();
			ResultSet rs = stmnt.getGeneratedKeys(); 
            if (rs.next()) {  
                res = rs.getInt(1);   
            }	

            if(res!=0){
				logger.info("Statement successfully executed "+res);
			}else{
				logger.info("Statement failly executed "+res);				
			}
			stmnt.close();

		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			e.printStackTrace();
		}
		return res;	
	
	}
	public int updateCart(Cart ct){
		int res=-1;
		try{	
			String sqlStr = "UPDATE CART SET NUMBER=? WHERE C_BUYER = ? AND C_BOOK=?";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);
			stmnt.setInt(2,ct.getUser());
			stmnt.setInt(3,ct.getBook());
			stmnt.setInt(1,ct.getNum());
			
			logger.info("sql string is "+stmnt.toString());
			res=stmnt.executeUpdate();
		
            if(res!=0){
				logger.info("Statement successfully executed "+res);
			}else{
				logger.info("Statement failly executed "+res);				
			}
			stmnt.close();

		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			e.printStackTrace();
		}
		return res;	

	}
	public int deleteCart(Cart ct){
		int res=-1;
		try{	
			String sqlStr = "DELETE FROM CART WHERE C_BOOK=? AND C_BUYER=?";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);
			stmnt.setInt(1,ct.getBook());
			stmnt.setInt(2,ct.getUser());

			logger.info("sql string is "+stmnt.toString());
			res = stmnt.executeUpdate();
            if(res!=0){
				logger.info("Statement successfully executed "+res);
			}else{
				logger.info("Statement failly executed "+res);				
			}
			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			e.printStackTrace();
		}
		return res;	
	}

	public SoldList findSoldByUser(int uid){
		Sold sd=new Sold();
		SoldList sl = new SoldList();
		Timestamp st= new Timestamp(0);
		int id;
		try{	
			
			String sqlStr = "SELECT * FROM SOLD WHERE S_BUYER=?";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr);
			stmnt.setInt(1,uid);

			logger.info("sql string is "+stmnt.toString());
			ResultSet rs=stmnt.executeQuery();
            while (rs.next()) {  
            	id = rs.getInt("SOLD_ID");
				logger.info(" "+id);
				sd.setID(id);

				id = rs.getInt("S_BUYER");
				logger.info(" "+id);
				sd.setUser(id);

				id = rs.getInt("S_BOOK");
				logger.info(" "+id);
				sd.setBook(id);

				id = rs.getInt("NUMBER");
				logger.info(" "+id);
				sd.setNum(id);
				
				st=rs.getTimestamp("TIME_S");
				sd.setSoldTime(st);
				logger.info(" "+st.toString());
				sl.add(sd);
            }    

			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			e.printStackTrace();
		}
		return sl;
	}

	public int storeSold(Sold sd){
		int res=-1;
		try{	
			String sqlStr = "INSERT INTO SOLD VALUES (DEFAULT,?,?,?,?)";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);
			stmnt.setInt(1,sd.getUser());
			stmnt.setInt(2,sd.getBook());
			stmnt.setInt(3,sd.getNum());
			stmnt.setString(4,ts.format(sd.getSoldTime()));
			
			logger.info("sql string is "+stmnt.toString());
			stmnt.executeUpdate();
			ResultSet rs = stmnt.getGeneratedKeys(); 
            if (rs.next()) {  
                res = rs.getInt(1);   
            }	

            if(res!=0){
				logger.info("Statement successfully executed "+res);
			}else{
				logger.info("Statement failly executed "+res);				
			}
			stmnt.close();

		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			e.printStackTrace();
		}
		return res;	

	}


	public RemovedList findRemovedbyUser(int uid){
		Removed rm=new Removed();
		RemovedList rl = new RemovedList();
		Timestamp st= new Timestamp(0);
		int id;
		try{	
			
			String sqlStr = "SELECT * FROM REMOVED WHERE R_BUYER=?";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr);
			stmnt.setInt(1,uid);

			logger.info("sql string is "+stmnt.toString());
			ResultSet rs=stmnt.executeQuery();
            while (rs.next()) {  
            	id = rs.getInt("REMOVED_ID");
				logger.info(" "+id);
				rm.setID(id);

				id = rs.getInt("R_BUYER");
				logger.info(" "+id);
				rm.setUser(id);

				id = rs.getInt("R_BOOK");
				logger.info(" "+id);
				rm.setBook(id);

				st=rs.getTimestamp("TIME_ADD");
				rm.setAddTime(st);
				logger.info(" "+st.toString());

				st=rs.getTimestamp("TIME_RMV");
				rm.setRmvTime(st);
				logger.info(" "+st.toString());

				rl.add(rm);
            }    

			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			e.printStackTrace();
		}
		return rl;
	}
	
	public int storeRemoved(Removed rm){
		int res=-1;
		try{	
			String sqlStr = "INSERT INTO REMOVED VALUES (DEFAULT,?,?,?,?)";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);
			stmnt.setInt(1,rm.getUser());
			stmnt.setInt(2,rm.getBook());
			stmnt.setString(3,ts.format(rm.getAddTime()));
			stmnt.setString(4,ts.format(rm.getRmvTime()));
			
			logger.info("sql string is "+stmnt.toString());
			stmnt.executeUpdate();
			ResultSet rs = stmnt.getGeneratedKeys(); 
            if (rs.next()) {  
                res = rs.getInt(1);   
            }	

            if(res!=0){
				logger.info("Statement successfully executed "+res);
			}else{
				logger.info("Statement failly executed "+res);				
			}
			stmnt.close();

		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			e.printStackTrace();
		}
		return res;	
	}
	
	public Picture findPictureByID(int pid){
		Picture pic = new Picture();
		int id;
		String val;
		try{	
			
			String sqlStr = "SELECT * FROM PICTURE WHERE PIC_ID=?";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr);
			stmnt.setInt(1,pid);

			logger.info("sql string is "+stmnt.toString());
			ResultSet rs=stmnt.executeQuery();
            while (rs.next()) {  
            	id = rs.getInt("PIC_ID");
				logger.info(" "+id);
				pic.setID(id);

				val = rs.getString("NAME");
				logger.info(val);
				pic.setName(val);
				
				Blob bl;
				bl = rs.getBlob("PIC");
				byte[] bt= new byte[(int) bl.length()];
				bt=bl.getBytes(1,(int)bl.length());
                pic.setBlob(bt); 
                
            }    

			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			e.printStackTrace();
		}
		return pic;
	}
	
	public int storePicture(Picture pc){
		int res=-1;
		try{	
			String sqlStr = "INSERT INTO PICTURE VALUES (DEFAULT,?,?)";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);
		
			stmnt.setString(1,pc.getName());
			stmnt.setBytes(2,pc.getBlob());
			
			logger.info("sql string is "+stmnt.toString());
			stmnt.executeUpdate();
			ResultSet rs = stmnt.getGeneratedKeys(); 
            if (rs.next()) {  
                res = rs.getInt(1);   
            }	

            if(res!=0){
				logger.info("Statement successfully executed "+res);
			}else{
				logger.info("Statement failly executed "+res);				
			}
			stmnt.close();

		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			e.printStackTrace();
		}
		return res;	
	}
	
	public Entity findEntityByID(int i){
		Entity pic = new Entity();
		String val;
		int id;
		try{	
			
			String sqlStr = "SELECT * FROM ENTITY WHERE ID=?";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr);
			stmnt.setInt(1,i);

			logger.info("sql string is "+stmnt.toString());
			ResultSet rs=stmnt.executeQuery();
            while (rs.next()) {  
            	id = rs.getInt("id");
				logger.info(" "+id);
				pic.setID(id);

				val = rs.getString("kind");
				logger.info(val);
				pic.setKind(val);
				
				val = rs.getString("e_class");
				logger.info(val);
				pic.setEClass(val);
				
				val = rs.getString("title");
				logger.info(val);
				pic.setTitle(val);
            }    

			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			e.printStackTrace();
		}
		return pic;
	}

	public List<Entity> findEntity(String name ,String t){
		List<Entity> el = new ArrayList<Entity>();
		String val;
		int id;
		try{	
			
			String sqlStr = "SELECT * FROM ENTITY WHERE TITLE LIKE ? AND KIND = ?";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr);
			stmnt.setString(1,"%"+name+"%");
			stmnt.setString(2,t);

			logger.info("sql string is "+stmnt.toString());
			ResultSet rs=stmnt.executeQuery();
            while (rs.next()) {  
        		Entity pic = new Entity();

            	id = rs.getInt("id");
				logger.info(" "+id);
				pic.setID(id);

				val = rs.getString("kind");
				logger.info(val);
				pic.setKind(val);
				
				val = rs.getString("e_class");
				logger.info(val);
				pic.setEClass(val);
				
				val = rs.getString("title");
				logger.info(val);
				pic.setTitle(val);

				el.add(pic);
            }    

			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to find Entity! ");
			e.printStackTrace();
		}
		return el;
	}
	public int storeEntity(Entity ent){
		int res=-1;
		Entity tmp = new Entity();
		try{	
			
			String sqlStr = "SELECT * FROM ENTITY WHERE KIND=? AND E_CLASS=? AND TITLE=?";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr);
			stmnt.setString(1,ent.getKind());
			stmnt.setString(2,ent.getEClass());
			stmnt.setString(3,ent.getTitle());
			logger.info("sql string is "+stmnt.toString());
			ResultSet rs =stmnt.executeQuery();
			if(rs.next()){
				res = rs.getInt("id");
				logger.info(" "+res);
				tmp.setID(res);
			}
			rs.close();
			stmnt.close();
			if(res>0){
				return res;
			}
			
			sqlStr = "INSERT INTO ENTITY VALUES (DEFAULT,?,?,?)";
			stmnt = connection.prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);
		
			stmnt.setString(1,ent.getKind());
			stmnt.setString(2,ent.getEClass());
			stmnt.setString(3,ent.getTitle());
			logger.info("sql string is "+stmnt.toString());
			stmnt.executeUpdate();
			rs = stmnt.getGeneratedKeys(); 
            if (rs.next()) {  
                res = rs.getInt(1);   
            }	

            if(res!=0){
				logger.info("Statement successfully executed "+res);
			}else{
				logger.info("Statement failly executed "+res);				
			}
            rs.close();
			stmnt.close();

		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			e.printStackTrace();
		}
		return res;	
		
	}
	
	public ArrayList<Relation> findRelationAll(){
		ArrayList<Relation> rl = new ArrayList<Relation>();
		int id;
		try{	
			
			String sqlStr = "SELECT * FROM RELATION";
			Statement stmnt = connection.createStatement();
			logger.info("sql string is "+stmnt.toString());
			ResultSet rs=stmnt.executeQuery(sqlStr);
            while (rs.next()) {  
        		Relation pic = new Relation();
            	id = rs.getInt("subject");
				logger.info(" "+id);
				pic.setSub(id);

				id = rs.getInt("predict");
				logger.info(" "+id);
				pic.setPre(id);
				
				id = rs.getInt("object");
				logger.info(" "+id);
				pic.setObj(id);
				
				rl.add(pic);
            }    

			stmnt.close();
		}catch(Exception e){
			logger.severe("Unable to store Bookinfo! ");
			e.printStackTrace();
		}
		return rl;
		
	}
	public List<Relation> findRelation(Entity e) throws SQLException{
		ArrayList<Relation> rl = new ArrayList<Relation>();
		Relation pic ;
		String sql;
		int id;
		if(e.getEClass().equals("node")){
			sql="SELECT * FROM RELATION WHERE SUBJECT=? OR OBJECT=?";
			PreparedStatement stmnt = connection.prepareStatement(sql);
			stmnt.setInt(1, e.getID());
			stmnt.setInt(2, e.getID());

			logger.info("sql string is "+stmnt.toString());
			ResultSet rs=stmnt.executeQuery();
            while (rs.next()) {  
            	pic = new Relation();
            	
            	id = rs.getInt("subject");
				logger.info(" "+id);
				pic.setSub(id);

				id = rs.getInt("predict");
				logger.info(" "+id);
				pic.setPre(id);
				
				id = rs.getInt("object");
				logger.info(" "+id);
				pic.setObj(id);
				
				rl.add(pic);
            }    
			stmnt.close();

		}else{
			sql="SELECT * FROM RELATION WHERE PREDICT=?";
			PreparedStatement stmnt = connection.prepareStatement(sql);
			stmnt.setInt(1, e.getID());
			logger.info("sql string is "+stmnt.toString());
			ResultSet rs=stmnt.executeQuery();
            while (rs.next()) {  
            	pic = new Relation();
            	
            	id = rs.getInt("subject");
				logger.info(" "+id);
				pic.setSub(id);

				id = rs.getInt("predict");
				logger.info(" "+id);
				pic.setPre(id);
				
				id = rs.getInt("object");
				logger.info(" "+id);
				pic.setObj(id);
				
				rl.add(pic);
            }    
			stmnt.close();
		}
		return rl;
	}
	
	public int storeRelation(Relation re){
		
		int res=-1;
		try{	
			String sqlStr = "INSERT INTO RELATION VALUES (?,?,?)";
			PreparedStatement stmnt = connection.prepareStatement(sqlStr);
		
			stmnt.setInt(1,re.getSub());
			stmnt.setInt(2,re.getPre());
			stmnt.setInt(3,re.getObj());

			logger.info("sql string is "+stmnt.toString());
			res=stmnt.executeUpdate();
            if(res!=0){
				logger.info("Statement successfully executed "+res);
			}else{
				logger.info("Statement failly executed "+res);				
			}
			stmnt.close();

		}catch(Exception e){
			logger.severe("Unable to store Relation! ");
			e.printStackTrace();
		}
		return res;	
		
	}
	
	

}

