/*
 * ListCommand.java
 *
 * Created on 9 August 2003, 11:46
 */

package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ass1.*;
import jdbc.*;
import service.*;

/**
 * This command finds all the phone book entries for a particular user
 * unless they are admin
 * @author  yunki
 */
public class SearchCommand implements Command {
	/**
	 * The helper class to delegate all function calls to
	 */
	final private static int Max=10;
	private static Service srv;
	private static String[] title = new String[Max];
	private static String[] img = new String[Max];
	private static String[] id = new String[Max];
	private static List<Relation> rl = new ArrayList<Relation>();
	private static int curPage=1;
	private static int talPage=1;
	private String Path;
	private String U_type;
	private User U_id;
	/** Creates a new instance of ListCommand 
	 * @throws NamingException 
	 * @throws SQLException */
	public SearchCommand() throws SQLException, NamingException {
		srv=new Service();
		U_type = "User";
		U_id = new User();
		int i;
		for(i=0;i<Max;i++){
			title[i]="";
			id[i]="-1";
			img[i]="img/trolly.jpeg";
		}
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException, SQLException, NamingException {
		BookList bk = new BookList();
		Bookinfo bf = new Bookinfo();
		Picture pic = new Picture();
		String name = request.getParameter("pageT");
		List<Entity> el = new ArrayList<Entity>();
		List<Relation> tmp = new ArrayList<Relation>();

		clean();
		
		if(name.equals("Graph")){
			request.setAttribute("pageT", "Graph");
			return "/Graph.jsp";
		}else if(name.equals("GraphResult")){
			String n,t;
			n=request.getParameter("title");
			t=request.getParameter("kind");
			el=srv.SearchEntity(n,t);
			int i;
			int j;
			for(i=0;i<el.size();i++){
				tmp=srv.SearchRelation(el.get(i));
				for(j=0;j<tmp.size();j++){
					rl.add(tmp.get(j));
				}
			}
			
			request.setAttribute("el", el);
			request.setAttribute("rl", rl);
			request.setAttribute("pageT", "GraphResult");
			return "/Graph.jsp";
		}else if(name.equals("GraphTO")){
			Entity en;
			List<Integer> tmpN = new ArrayList<Integer>();
			int[] node,from,to,id_n,id_e;
			String[] label,edge;
			int i;
			for(i=0;i<rl.size();i++){
				if(!tmpN.contains(rl.get(i).getSub())){
					tmpN.add(rl.get(i).getSub());
				}
				if(!tmpN.contains(rl.get(i).getObj())){
					tmpN.add(rl.get(i).getObj());
				}
			}
			
			label = new String[tmpN.size()];
			node = new int[tmpN.size()];
			id_n = new int[tmpN.size()];
			for(i=0;i<tmpN.size();i++){
				en=srv.GetEntity(tmpN.get(i));
				label[i]=en.getKind();
				node[i]=en.getID();
			}
			from = new int[rl.size()];
			to = new int[rl.size()];
			id_e = new int[rl.size()];

			edge = new String[rl.size()];
			for(i=0;i<rl.size();i++){
				//from,to and edge label
				from[i]=rl.get(i).getSub();
				to[i]=rl.get(i).getObj();
				en=srv.GetEntity(rl.get(i).getPre());
				edge[i]=en.getTitle();
				id_e[i]=en.getID();
			}
			
			request.setAttribute("node", node);
			request.setAttribute("label", label);
			request.setAttribute("from", from);
			request.setAttribute("to", to);
			request.setAttribute("id_e", id_e);
			request.setAttribute("edge", edge);
			request.setAttribute("rl", rl);
			request.setAttribute("pageT", "GraphResult");
			return "/Node.jsp";	
			
		}else{
			bf = constructBookinfo(request);
			try {
				bk = srv.Search(bf);
				
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int i;
			int min;
			if(bk.size()>Max){
				min=Max;
			}else{
				min=bk.size();
			}
			for(i=0;i<min;i++){
				bf=bk.get(i).getBookinfo();
				
				title[i]=bf.getAttrVal(bf.AUTHOR)+":\n"+bf.getAttrVal(bf.TITLE);
				id[i]=Integer.toString(bk.get(i).getID());	
				pic=srv.getPictureByID(bk.get(i).getPicture(1), Path);
				img[i]=pic.getLocalName();
			}
			talPage=bk.size()/Max+1;
			request.setAttribute("list", title);
			request.setAttribute("img", img);
			request.setAttribute("bookid",id);
			request.setAttribute("page", Integer.toString(curPage));
			request.setAttribute("totalpage", Integer.toString(talPage));
			return "/Result.jsp";
		}
			
	}
	
	public Bookinfo constructBookinfo(HttpServletRequest request){
		Bookinfo bf = new Bookinfo();
		String name;
		String val;
		Enumeration<String> t2 = request.getParameterNames() ;
		while(t2.hasMoreElements()){
			name=(String)t2.nextElement();
			val=request.getParameter(name);
			bf.setAttr(name, val);
		}
		return bf;
		
	}
	public void clean(){
		int i;
		for(i=0;i<Max;i++){			
			title[i]="";
			id[i]="";
		}
		for(i=0;i<rl.size();i++){
			rl.remove(i);
		}
	}
	public String[] getTitle(){
		return title;
	}
	
	public String getUserType(){
		return U_type;
	}
	
	public User getUserInfo(){
		return U_id;
	}
	public void setUserType(String i){
		U_type=i;
	}
	public void setUserInfo(User i){
		U_id=i;
	}
	public void setPath(String i){
		Path=i;
	}
}
