package ass1;

import java.util.ArrayList;
import java.util.List;

public class BookList{
	private List<Book> list;
	
	public BookList(){
		this.list = new ArrayList<Book>();
	}
	
	public void add(Book b){
		Book bk=new Book();
		bk.setBookinfo(b.getBookinfo());
		bk.setBookinfoID(b.getBookinfoID());
		bk.setID(b.getID());
		bk.setNum(b.getNum());
		bk.setPicture(1,b.getPicture(1));
		bk.setPicture(2,b.getPicture(2));
		bk.setPicture(3,b.getPicture(3));
		bk.setPicture(4,b.getPicture(4));
		bk.setPrice(b.getPrice());
		bk.setStatus(b.getStatus());
		bk.setVender(b.getVender());
		this.list.add(bk);
	}
	public void merge(BookList bk){
		int i;
		for(i=0;i<bk.size();i++){
			this.add(bk.get(i));
		}
	}
	public Book get(int index){
		return this.list.get(index);
	}
	
	public void delete(int index){
		this.list.remove(index);
	}
	
	public int size(){
		return this.list.size();
	}
}
