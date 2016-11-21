package jdbc;

import java.util.List;

import ass1.Book;
import ass1.BookList;
import ass1.Bookinfo;
import ass1.User;
import ass1.UserList;

public interface SalesDAO {
	
	public BookList findRandamPage();
	
	public int storeBook(Book b);
	
	public BookList findBookByinfoID(int i);
	
	public int storeBookinfo(Bookinfo b);
	
	public List<Bookinfo> findBookinfo(Bookinfo b);
	
	public int storeBuyer(User b);
		
	public int storeSeller(User b);
	
}
