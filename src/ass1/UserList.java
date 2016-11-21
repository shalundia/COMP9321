package ass1;

import java.util.ArrayList;
import java.util.List;

public class UserList {

private List<User> list;
	
	public UserList(){
		this.list = new ArrayList<User>();
	}
	
	public void add(User b){
		User c = new User();
		c.setAddr(b.getAddr());
		c.setBirth(b.getBirth());
		c.setCard(b.getCard());
		c.setEmail(b.getEmail());
		c.setFirst(b.getFirst());
		c.setID(b.getID());
		c.setLast(b.getLast());
		c.setName(b.getName());
		c.setNick(b.getNick());
		c.setPsw(b.getPsw());
		c.setStatus(b.getStatus());
		this.list.add(c);
	}
	public void merge(UserList bk){
		int i;
		for(i=0;i<bk.size();i++){
			this.add(bk.get(i));
		}
	}
	public User get(int index){
		return this.list.get(index);
	}
	
	public void delete(int index){
		this.list.remove(index);
	}
	
	public int size(){
		return this.list.size();
	}
}
