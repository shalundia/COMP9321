package ass1;

import java.sql.Timestamp;

public class Removed {
	private int id;
	private int buyer;
	private int book;
	private Timestamp addtime;
	private Timestamp rmvtime;
	
	public Removed(){
		this.id=-1;
		this.buyer=-1;
		this.book=-1;
		this.addtime=new Timestamp(0);
		this.rmvtime=new Timestamp(0);
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
	public void setAddTime(Timestamp i){
		this.addtime=i;
	}
	public void setRmvTime(Timestamp i){
		this.rmvtime=i;
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
	public Timestamp getRmvTime(){
		return this.rmvtime;
	}
	public Timestamp getAddTime(){
		return this.addtime;
	}
	public void cartToRemoved(Cart ct){
		this.buyer=ct.getUser();
		this.book=ct.getBook();
		this.addtime=ct.getAddTime();
		this.rmvtime.setTime(System.currentTimeMillis());
	}
	
}
