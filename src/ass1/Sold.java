package ass1;

import java.util.Date;
import java.sql.Timestamp;

public class Sold {
	private int id;
	private int buyer;
	private int book;
	private int number;
	private Timestamp soldtime;	
	
	public Sold(){
		this.id=-1;
		this.buyer=-1;
		this.book=-1;
		this.number=0;
		this.soldtime=new Timestamp(0);
	}

	public void setID(int i){
		this.id=i;
	}
	public void setBook(int i){
		this.book=i;
	}
	public void setUser(int i){
		this.buyer=i;
	}
	public void setNum(int i){
		this.number=i;
	}
	public void setSoldTime(Timestamp i){
		this.soldtime=i;
	}
	public int getID(){
		return this.id;
	}

	public int getBook(){
		return this.book;
	}
	
	public int getUser(){
		return this.buyer;
	}
	public int getNum(){
		return this.number;
	}
	public Timestamp getSoldTime(){
		return this.soldtime;
	}
	
	public void cartToSold(Cart c){
		this.buyer=c.getUser();
		this.book=c.getBook();
		this.number=c.getNum();
		this.soldtime.setTime(System.currentTimeMillis());
	}
}
