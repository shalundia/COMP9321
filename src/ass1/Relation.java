package ass1;

public class Relation {
	private int subject;
	private int predicate;
	private int object;
	
	public Relation(){
		subject=-1;
		predicate = -1;
		object = -1;
	}
	
	public void setupRelation(int sub,int pre,int obj){
		subject=sub;
		predicate= pre;
		object=obj;	
	}
	
	public int getSub(){
		return subject;
	}
	
	public int getPre(){
		return predicate;
	}
	
	public int getObj(){
		return object;
	}
	
	public void setSub(int sub){
		subject=sub;
	}
	
	public void setPre(int pre){
		predicate=pre;
	}
	
	public void setObj(int obj){
		object=obj;
	}
}
