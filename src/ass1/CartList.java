package ass1;


import java.util.ArrayList;
import java.util.List;

	
public class CartList {
	private List<Cart> list;
		
		public CartList(){
			this.list = new ArrayList<Cart>();
		}
		
		public void add(Cart b){
			Cart ct = new Cart();
			ct.setAddTime(b.getAddTime());
			ct.setBook(b.getBook());
			ct.setID(b.getID());
			ct.setNum(b.getNum());
			ct.setUser(b.getUser());
			this.list.add(ct);
		}
		public void merge(CartList bk){
			int i;
			for(i=0;i<bk.size();i++){
				this.add(bk.get(i));
			}
		}
		public Cart get(int index){
			return this.list.get(index);
		}
		
		public void delete(int index){
			this.list.remove(index);
		}
		
		public int size(){
			return this.list.size();
		}

	
}


