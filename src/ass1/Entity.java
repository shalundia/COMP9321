package ass1;

public class Entity {
	private int id;
	private String kind;
	private String e_class;
	private String title;
	
	public Entity(){
		this.id = -1;
		this.kind = "";
		this.e_class="";
		this.title="";
	}
	public void setupPublisher(String t){
		this.kind="Article";
		this.e_class="node";
		this.title=t;
	}
	
	public void setupAuthor(String t){
		this.kind="Author";
		this.e_class="node";
		this.title=t;
	}
	public void setupVenue(String t){
		this.kind="Venue";
		this.e_class="node";
		this.title=t;
	}
	public void setupAuthorby(){
		this.kind="Link";
		this.e_class="edge";
		this.title="authoredBy";
	}
	public void setupPublishedin(){
		this.kind="Link";
		this.e_class="edge";
		this.title="pulishedIn";
	}
	
	public void setID(int id){
		this.id=id;
	}
	public void setKind(String kind){
		this.kind=kind;
	}
	public void setEClass(String e_class){
		this.e_class=e_class;
	}
	public void setTitle(String title){
		this.title = title;
	}
	
	public int getID(){
		return this.id;
	}
	public String getKind(){
		return this.kind;
	}
	public String getEClass(){
		return this.e_class;
	}
	public String getTitle(){
		return this.title;
	}

}
