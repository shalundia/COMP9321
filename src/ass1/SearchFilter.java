package ass1;

import java.io.IOException;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class SearchFilter {
	private  Bookinfo tar;
	private InputSource xmlFile;
	private MyContentHandler contentHandler;
	
	public SearchFilter(){
		contentHandler = new MyContentHandler();
		tar = new Bookinfo();
	}
	
	public void setAttribute(String attname,String attval){	
		tar.setAttrClean(attname, attval);
	}
	
	public String getAttributeVal(int index){
		return tar.getAttrVal(index);
		
	}
	public String getAttributeName(int index){
		return tar.getAttrName(index);
	}	
	
	public void setInputSource(InputSource xml){
		xmlFile = xml;
	}
	
	public void parser(int page) throws IOException{
		int i=0;
		
		SAXParserFactory saxPF = SAXParserFactory.newInstance();
		XMLReader reader = null;
		try {
			reader = saxPF.newSAXParser().getXMLReader();		    
	     } catch (Exception e) {
		    e.printStackTrace();
         }
		
		MyFilter oldFil=null;
		MyFilter firstFil=null;

		for(i=0;i<22;i++){
			if(!tar.getAttrVal(i).equals("")){
				MyFilter filter = new MyFilter(tar.getAttrName(i),tar.getAttrVal(i));
				
				if(oldFil!=null)
					oldFil.setParent(filter);	
				
				if(firstFil==null)
					firstFil=filter;
				
				oldFil=filter;
			}
		}
		
		if(oldFil==null){
			parserAll(page);
		}else{
			oldFil.setParent(reader);
			contentHandler = new MyContentHandler();
			contentHandler.setPage(page);
			firstFil.setContentHandler(contentHandler);
			firstFil.setErrorHandler(contentHandler);
		
			try {
			 firstFil.parse(xmlFile);
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void parserAll(int page) throws IOException{
		SAXParserFactory saxPF = SAXParserFactory.newInstance();
		XMLReader reader = null;
		try {
			reader = saxPF.newSAXParser().getXMLReader();		    
	     } catch (Exception e) {
		    e.printStackTrace();
         }
		
		contentHandler = new MyContentHandler();
		contentHandler.setPage(page);
		reader.setContentHandler(contentHandler);
		reader.setErrorHandler(contentHandler);
		
		 try {
			 reader.parse(xmlFile);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
	}
	public Bookinfo getBook(int index){
		if(index<contentHandler.booklist.size()){
			return contentHandler.booklist.get(index);
		}else{
			return null;
		}
	}
	
	public int getNumberPerPage(){
		if(contentHandler.booklist!=null)
			return contentHandler.booklist.size();
		else
			return 0;
	}
	
	public long getTotalPage(){
		return contentHandler.getTotalPage();
	}
	
	
	
	
}
