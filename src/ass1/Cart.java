package ass1;

import java.sql.Timestamp;

public class Cart {
	private int id;
	private int buyer;
	private int book;
	private int number;	
	private Timestamp addtime;

	public Cart(){
		this.id=-1;
		this.buyer=-1;
		this.book=-1;
		this.number=0;
		this.addtime=new Timestamp(0);
	}

	public void setID(int i){
		this.id=i;
	}

	public void setUser(int i){
		this.buyer=i;
	}

	public void setBook(int i){
		this.book=i;
	}

	public void setNum(int i){
		this.number=i;
	}

	public void setAddTime(Timestamp i){
		this.addtime=i;
	}
	
	public int getID(){
		return this.id;
	}

	public int getUser(){
		return this.buyer;
	}

	public int getBook(){
		return this.book;
	}

	public int getNum(){
		return this.number;
	}

	public Timestamp getAddTime(){
		return this.addtime;
	}
}
