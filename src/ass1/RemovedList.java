package ass1;

import java.util.ArrayList;
import java.util.List;

public class RemovedList {
	
	private List<Removed> list;
	public RemovedList(){
		this.list = new ArrayList<Removed>();
	}
	
	public void add(Removed b){
		Removed rm = new Removed();
		rm.setAddTime(b.getAddTime());
		rm.setBook(b.getBook());
		rm.setID(b.getID());
		rm.setRmvTime(b.getRmvTime());
		rm.setUser(b.getUser());
		this.list.add(rm);
	}
	public void merge(RemovedList bk){
		int i;
		for(i=0;i<bk.size();i++){
			this.add(bk.get(i));
		}
	}
	public Removed get(int index){
		return this.list.get(index);
	}
	
	public void delete(int index){
		this.list.remove(index);
	}
	
	public int size(){
		return this.list.size();
	}

}
