package ass1;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;

public class Picture {
	private int id;
	private String name;
	private  byte[] pic;
	private String localname;

	public Picture(){
		this.id=-1;
		this.name="";
		this.localname="img/trolly.jpeg";
	}
	
	public void setID(int i){
		this.id=i;
	}
	
	public void setName(String name){
		this.name=name;
	}
	public void setLocalName(String name){
		this.localname=name;
	}
	
	public void setBlob(byte[] b){
		this.pic=b;
	}
	
	public int getID(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	public String getLocalName(){
		return this.localname;
	}
	public byte[] getBlob(){
		return this.pic;
	}
}
