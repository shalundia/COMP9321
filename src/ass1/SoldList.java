package ass1;

import java.util.ArrayList;
import java.util.List;

public class SoldList {
	private List<Sold> list;

	public SoldList(){
		this.list = new ArrayList<Sold>();
	}
	
	public void add(Sold b){
		Sold sd = new Sold();
		sd.setBook(b.getBook());
		sd.setID(b.getID());
		sd.setNum(b.getNum());
		sd.setSoldTime(b.getSoldTime());
		sd.setUser(b.getUser());
		this.list.add(sd);
	}
	public void merge(SoldList bk){
		int i;
		for(i=0;i<bk.size();i++){
			this.add(bk.get(i));
		}
	}
	public Sold get(int index){
		return this.list.get(index);
	}
	
	public void delete(int index){
		this.list.remove(index);
	}
	
	public int size(){
		return this.list.size();
	}
}
