package ass1;

import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class MyContentHandler extends DefaultHandler {
	public ArrayList<Bookinfo> booklist;
	private Bookinfo curBook;
	private StringBuffer buf;
	private long index;
	private long page;//10 per page
	private long start;
	private long end;
	private long total;
	private String name;
	
	public void startDocument() throws SAXException {
	        buf=new StringBuffer();
	        booklist = new ArrayList<Bookinfo>();
	        index = 0;
	        total=0;
	        name="";
	}
	public void startElement(String uri, String localName, String name, org.xml.sax.Attributes attributes) throws SAXException {  

    	if(name.equals("article")||name.equals("inproceedings")||name.equals("proceedings")||name.equals("book")||name.equals("incollection")
				||name.equals("phdthesis")||name.equals("mastersthesis")||name.equals("www")){
    		if(index<end&&index>=start) {
    			curBook = new Bookinfo();  
    			curBook.setAttr("booktype", name);
    		}        
    	}
		this.name=name;

    }  
   
    public void endElement(String uri, String localName, String name) throws SAXException{  
        //set name  
    	//end an entry
    	if(name.equals("article")||name.equals("inproceedings")||name.equals("proceedings")||name.equals("book")||name.equals("incollection")
				||name.equals("phdthesis")||name.equals("mastersthesis")||name.equals("www")){
    		
    		if(index<end&&index>=start){
        		booklist.add(curBook);
        	}
    		index++;
    		total++;
    	}else{
    		
    	}
    }  
	
    public void characters (char[] ch, int start, int length)  throws SAXException  
    {  
    	if(index<end && index>=this.start){
    		buf.setLength(0);
    		buf.append(ch,start,length);
        	curBook.setAttr(name, buf.toString().trim());
    	}
        
    }  
    
    
    
    public void setPage(int p){
    	page = p;
    	start=(page-1)*10;
    	end=page*10;
    }
    
    public long getTotalPage(){
    	if(total == 0){
    		return 0;
    	}else{
    		return total/10+1;
    	}
    }
    
    
    

}
