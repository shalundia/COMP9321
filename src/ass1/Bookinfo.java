package ass1;

public class Bookinfo extends Object{
	private  BookAttribute[] BookAtt;
	public final int ATTNUM=24;
	public final int TITLE=3;
	public final int AUTHOR=2;
	public final int PUBLISHER=6;
	
	public Bookinfo(){
		
		BookAtt=new BookAttribute[ATTNUM];
		String[] namelist={"BOOKINFO_ID","booktype","author","title","booktitle","publisher","journal","volume","pages","years","isbn","series","school",
				"address","number","month","editor","url","ee","cdrom","cite","note",
				"crossref","chapter"
				};
		int i=0;
		for(i=0;i<ATTNUM;i++){
			BookAtt[i]=new BookAttribute(namelist[i],"");
		}
	}
	
	public int AttSize(){
		return this.ATTNUM;
	}
	
	public void setAttr(String attname,String attval){
		int i = 0;
		for(i=0;i<ATTNUM;i++){
			if (attname.equals( BookAtt[i].getName())){
				BookAtt[i].setVal(attval);
			}
		}
	}
	
	public void setAttr(int i,String attval){
		BookAtt[i].setVal(attval);
	}
	
	public void setAttrClean(String attname,String attval){
		int i = 0;
		for(i=0;i<ATTNUM;i++){
			if (attname.equals( BookAtt[i].getName())){
				BookAtt[i].setValClean(attval);
			}
		}
	}	
	
	public String toString(){
		int i =0;
		String tmp=BookAtt[2].getVal()+':'+"\n";
		
		for(i=3;i<12;i++){
			tmp=tmp+BookAtt[i].getVal();
		}
		return tmp;
	}
	
	public String toStringDetail(){
		String tmp=BookAtt[0].getVal()+":"+"\n";
		int i=0;
		for(i=2;i<ATTNUM;i++){
			if(!BookAtt[i].getVal().equals("")){
				tmp=tmp+BookAtt[i].getName()+":"+BookAtt[i].getVal()+"\n";
			}
		}
		return tmp;
	}
	
	public String getAttrName(int index){
		if(index<ATTNUM){
			return BookAtt[index].getName();
		}else{
			return "";
		}
	}
	
	public String getAttrVal(int index){
		if(index<ATTNUM){
			return BookAtt[index].getVal();
		}else{
			return "";
		}
	}
	
}

class BookAttribute{
	private String name;
	private String val;
	
	public BookAttribute(String name ,String val){
		this.name=name;
		this.val=val;
	}
	
	public void setVal(String val){
		if(this.val!=null&&!this.val.equals("")){
			this.val=this.val+' ';
		}
		this.val=this.val+val;

	}
	
	public void setValClean(String val){
		this.val=val;
	}
	
	public String getVal(){
		return this.val;
	}
	public String getName(){
		return this.name;
	}
}




