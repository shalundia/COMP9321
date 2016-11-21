package ass1;

import java.util.Date;

public class User {
	private int id;
	private String name;
	private String psw;
	private String email;
	private String nick;
	private String first;
	private String last;
	private Date birth;
	private String addr;
	private String card;
	private int status;
	
	@SuppressWarnings("deprecation")
	public User(){
		this.id=-1;
		this.status=1;
		this.name="";
		this.psw ="";
		this.email="";
		this.nick="";
		this.first="";
		this.last="";
		this.birth=new Date(0);
		this.addr="";
		this.card="";	
	}
	
	public void setName(String att){
		this.name=att;
	}
	
	public void setPsw(String att){
		this.psw=att;
	}
	public void setEmail(String att){
		this.email=att;
	}
	public void setNick(String att){
		this.nick=att;
	}
	public void setFirst(String att){
		this.first=att;
	}
	public void setLast(String att){
		this.last=att;
	}
	public void setBirth(Date dt){
		this.birth=(Date) dt;
	}
	public void setAddr(String att){
		this.addr=att;
	}
	public void setCard(String att){
		this.card=att;
	}
	public void setID(int att){
		this.id=att;
	}
	
	public void setStatus(int att){
		this.status=att;
	}
	
	public String getName(){
		return this.name;
	}
	public String getPsw(){
		return this.psw;
	}
	public String getEmail(){
		return this.email;
	}
	public String getNick(){
		return this.nick;
	}
	public String getFirst(){
		return this.first;
	}
	public String getLast(){
		return this.last;
	}
	public Date getBirth(){
		return this.birth;
	}
	public String getAddr(){
		return this.addr;
	}
	public String getCard(){
		return this.card;
	}
	public int getID(){
		return this.id;
	}
	public int getStatus(){
		return this.status;
	}
	public String toString(){
		return this.name+": "+this.nick+this.first+this.last+this.addr+this.birth.toString()+this.email;
	}
}
