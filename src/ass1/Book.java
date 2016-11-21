package ass1;

import java.util.ArrayList;
import java.util.List;

public class Book extends Object{
	private int id;
	private Bookinfo info;
	private int inf_id;
	private int vender;
	private float price;
	private int num;
	private int stat;
	private int[] pic;
	
	public Book(){
		int i;
		pic=new int[4];
		for(i=0;i<4;i++){
			pic[i]=-1;
		}
		this.info=new Bookinfo();
		this.id=-1;
		this.inf_id=-1;
		this.vender=-1;
		this.price=0;
		this.num=0;
		this.stat=0;
		
	}
	
	public void setBookinfo(Bookinfo i){
		this.info=i;
	}
	
	public Bookinfo getBookinfo(){
		return this.info;
	}
	public void setBookinfoID(int i){
		this.inf_id = i;
	}
	public int getBookinfoID(){
		return this.inf_id;
	}
	public void setID(int id){
		this.id=id;
	}
	
	public int getID(){
		return this.id;
	}
	public void setVender(int vd){
		this.vender=vd;
	}
	
	public int getVender(){
		return this.vender;
	}
	public void setPrice(float pr){
		this.price=pr;
	}
	
	public float getPrice(){
		return this.price;
	}
	public void setNum(int num){
		this.num=num;
	}
	
	public int getNum(){
		return this.num;
	}
	public void setStatus(int st){
		this.stat=st;
	}
	
	public int getStatus(){
		return this.stat;
	}
	
	public void setPicture(int idx,int px){
		this.pic[idx-1]=px;
	}
	public void addPicture(int px){
		int i;
		for(i=0;i<4;i++){
			if(pic[i]==-1){
				this.pic[i]=px;
				return;
			}
		}
		
	}
	
	public int getPicture(int idx){
		return this.pic[idx-1];
	}
	
}
