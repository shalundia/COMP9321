package ass1;


import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.SAXException;


public class MyFilter extends XMLFilterImpl {
	private String element;
	private String value;
	private boolean startCmp;
	private boolean match;
	
	   public MyFilter(String element,String value) {
		      this.element=element;
		      this.value=value;
		      startCmp=false;
		      match=false;
	   }
	   
	   public void startElement( String namespaceURI, String localName,
		                             String name, org.xml.sax.Attributes attributes )
		      throws SAXException {
		        
		   		 if(element.equals("type")){
		   			 if(name.equals(value)){
			        	 startCmp=true;
			        	 match=true;
		   			 }
		   		 }else{
		   		 
			         if(name.equals("article")||name.equals("inproceedings")||name.equals("proceedings")||name.equals("book")||name.equals("incollection")
								||name.equals("phdthesis")||name.equals("mastersthesis")||name.equals("www")){
			        	
			        	 match=false;
			         }
			         
		   			 if ( element.equals(name) ) {
		   				 startCmp=true;
		   			 }
		   		 }

		         super.startElement(namespaceURI, localName, name, attributes);

		      }
		  
		   public void endElement(String namespaceURI, String localName, String
		                          name)
		      throws SAXException {
			   if(name.equals("article")||name.equals("inproceedings")||name.equals("proceedings")||name.equals("book")||name.equals("incollection")
						||name.equals("phdthesis")||name.equals("mastersthesis")||name.equals("www")){

			         if (match) {
			            super.endElement(namespaceURI, localName, name);
			         
			         }
			         match=false;
			   }
			 
		    }
		   
		    public void characters(char[] buffer, int start, int length) throws SAXException {
		    	String str = new String(buffer,start,length);
		    	if(startCmp&&!match){
		    		match= str.contains(value);
		    		startCmp=false;
		    	}
		    	
		    	super.characters( buffer,start,length );
		    }

		    
}


