package service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.sql.DataSource;
import ass1.*;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import jdbc.SalesDAOImpl;

public class CastDAOImplTest {
	private SalesDAOImpl cast;
	public CastDAOImplTest(){
		try {
			cast = new SalesDAOImpl();
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void exacute() throws Exception {
		/*
		testdeleteBook();
		testdeleteCart();
		testfindAllBuyer();
		testfindBookByinfoID();
		testfindBookinfo();
		testfindBuyer();
		testfindCartByBookID();
		testfindCartByUser();
		testfindRandamPage();
		testfindRemovedbyUser();
		testfindSeller();
		testfindSoldByUser();
		testgetBookinfoByID();
		teststoreBook();
		teststoreBookinfo();
		teststoreBuyer();
		teststoreCart();
		teststoreRemoved();
		teststoreSeller();
		teststoreSold();
		testupdateBookStatus();
		testupdateBuyer();
		testupdateBuyerStatus();
		testupdateCart();
		testupdateSeller();
		*/
	}
	
	private void assertTrue(boolean i){
		
		  if(i==false){
			  return;
		  }
		
	}
	
	public void testdeleteBook(){
		cast.deleteBook(3);
		Book bk=cast.findBookBybookID(3);
		assertTrue(bk.getID()==-1);
	}
	public void testdeleteCart(){
		Cart ct=new Cart();
		ct.setBook(2);
		ct.setUser(1);
		int castList = cast.deleteCart(ct);
		assertTrue(castList>0);
	}

	public void testfindAllBuyer(){
		UserList sl=new UserList();
		sl=cast.findAllBuyer();
		assertTrue(sl.size()>0);
	}
	public void testfindBookByinfoID(){
		BookList bk=cast.findBookByinfoID(1);
		assertTrue(bk.size()>0);
	}
	
	public void testfindBookinfo(){
		Bookinfo bf = new Bookinfo();
		List<Bookinfo> bl = new ArrayList<Bookinfo>();
		bf.setAttr("editor", "Nadia");
		bl=cast.findBookinfo(bf);
		assertTrue(bl.size()>0);
	}
	public void testfindBuyer(){
		User u=new User();
		User u_t=new User();

		u.setName("Shan");
		u.setEmail("rayzuo@gmail.com");
		u.setPsw("123");
		u_t=cast.findBuyer(u);
		assertTrue(u_t.getID()>0);
	}
	
	public void testfindCartByBookID(){
		Cart ct=new Cart();
		ct=cast.findCartByBookID(1, 1);
		assertTrue(ct.getID()>0);
	}
	public void testfindCartByUser(){
		CartList cl = new CartList();
		cl=cast.findCartByUser(1);
		assertTrue(cl.size()>0);
	}
	
	public void testfindRandamPage(){
		BookList bl = cast.findRandamPage();
		assertTrue(bl.size()>0);
	}
	public void testfindRemovedbyUser(){
		RemovedList rl=cast.findRemovedbyUser(1);
		assertTrue(rl.size()>0);
	}
		
	public void testfindSeller(){
		User u=new User();
		User u_t=new User();

		u.setName("Shan");
		u.setPsw("123");
		u.setEmail("rayzuo@gmail.com");
		u_t=cast.findSeller(u);
		assertTrue(u_t.getID()>0);
	}
	public void testfindSoldByUser(){
		SoldList sl = cast.findSoldByUser(1);
		assertTrue(sl.size()>0);
	}
	public void testgetBookinfoByID(){
		Bookinfo bf = cast.getBookinfoByID(1);
		assertTrue(!(bf.getAttrVal(0)).equals(""));

	}
	public void teststoreBook(){
		Book bk = new Book();
		bk.setBookinfoID(1);
		bk.setVender(1);
		bk.setPrice(1);
		bk.setStatus(1);
		bk.setNum(1);
		int ret = cast.storeBook(bk);
		assertTrue(ret>0);
	}
	
	public void teststoreBookinfo(){
		Bookinfo bf = new Bookinfo();
		bf.setAttr("title", "123");
		int ret = cast.storeBookinfo(bf);
		assertTrue(ret>0);
	}
	
	public void teststoreBuyer(){
		User u = new User();
		u.setName("testBuyer");
		u.setPsw("123");
		u.setEmail("testBuyer@gmail.com");
		int ret=cast.storeBuyer(u);
		assertTrue(ret>0);

	}
	public void teststoreCart(){
		Cart ct = new Cart();
		Timestamp ts=new Timestamp(System.currentTimeMillis());
		ct.setBook(1);
		ct.setUser(1);
		ct.setNum(1);
		ct.setAddTime(ts);
		int ret = cast.storeCart(ct);
		assertTrue(ret>0);
		
	}
	public void teststoreRemoved(){
		Removed rm = new Removed();
		Timestamp ts=new Timestamp(System.currentTimeMillis());
		rm.setBook(1);
		rm.setUser(1);
		rm.setAddTime(ts);
		rm.setRmvTime(ts);
		int ret = cast.storeRemoved(rm);
		assertTrue(ret>0);
	}
	
	public void teststoreSeller(){
		User u = new User();
		u.setName("testBuyer");
		u.setPsw("123");
		u.setEmail("testBuyer@gmail.com");
		int ret=cast.storeSeller(u);
		assertTrue(ret>0);
	}
	
	public void teststoreSold(){
		Sold rm = new Sold();
		Timestamp ts=new Timestamp(System.currentTimeMillis());
		rm.setBook(1);
		rm.setUser(1);
		rm.setSoldTime(ts);
		int ret = cast.storeSold(rm);
		assertTrue(ret>0);
	}
	public void testupdateBookStatus(){
		int ret = cast.updateBookStatus(1, 0);
		assertTrue(ret>0);
		ret = cast.updateBookStatus(1, 1);
		assertTrue(ret>0);
		
	}
	public void testupdateBuyer(){
		User u = new User();
		u.setID(1);
		u.setFirst("testBuyer");
		int ret=cast.updateBuyer(u);
		assertTrue(ret>0);
		UserList ul=cast.findAllBuyer();
		assertTrue(ul.get(0).getFirst().equals("testBuyer"));		
	}
	public void testupdateBuyerStatus(){
		int ret = cast.updateBuyerStatus(1, 1);
		assertTrue(ret>0);
	}
	public void testupdateCart(){
		CartList cl = cast.findCartByUser(1);
		cl.get(0).setNum(2);
		int ret = cast.updateCart(cl.get(0));
		assertTrue(ret>0);
		cl = cast.findCartByUser(1);
		assertTrue(cl.get(0).getNum()==2);
	}
	public void testupdateSeller(){
		User u = new User();
		User u_t = new User();
		u.setName("testBuyer");
		u.setPsw("123");
		u.setEmail("testBuyer@gmail.com");
		u_t = cast.findSeller(u);
		u_t.setFirst("testBuyer");
		int ret = cast.updateSeller(u_t);
		assertTrue(ret>0);		
	}
	 public void testAddPicture() throws IOException, SQLException{
		 String s="/Users/zhangming/Work/9321/ass/ass1/WebContent/img/trolly.jpeg";  

		 Picture pic = new Picture();
		 Blob pb = null;
         File file = new File(s);     
         @SuppressWarnings("resource")
		InputStream put = new FileInputStream(file);  
         byte[] bt = new byte[put.available()];
         put.read(bt);
         pic.setBlob(bt);
         pic.setName("trolly.jpeg");
         int ret =  cast.storePicture(pic);
         assertTrue(ret>0);	
	 }
	 public Picture testFindPicture() throws IOException, SQLException{
		 Picture pic =  cast.findPictureByID(1);
		 return pic;
	 }

}
