package ass1;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.xml.sax.InputSource;

import logic.*;
import service.*;


/**
 * Servlet implementation class Search
 */
@MultipartConfig
@WebServlet("/Search")
public class Search extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private static int page = 1;  
    private static int index = 1;  
    private static ArrayList<Bookinfo> cartList;
	private SearchFilter filter;
	private InputSource xmlFile;
	private String U_type="User";//User,Seller,Admin
	private User UserInfo = new User();
	private final String userID = "anonymous";
	private final String password = "";
	private DateFormat fmt = new SimpleDateFormat("yyyy-MM--dd");
	private HashMap<String, Command> commands;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        
        super();
        filter = new SearchFilter();
        cartList = new ArrayList<Bookinfo>();
        // TODO Auto-generated constructor stub
    }
	/** Initializes the servlet.
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		try {
			commands = new HashMap<String, Command>();
			commands.put("Add", new AddCommand());
			commands.put("Delete", new DeleteCommand());
			commands.put("List", new ListCommand());
			commands.put("Login", new LoginCommand());
			commands.put("Logout", new LogoutCommand());
			commands.put("Register", new RegisterCommand());
			commands.put("Search", new SearchCommand());
			commands.put("Change", new ChangeCommand());
			commands.put("Detail", new DetailCommand());
			commands.put("PAGE_NOT_FOUND", new ErrorCommand());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws NamingException 
	 * @throws SQLException 
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException, SQLException, NamingException {
		Command cmd = resolveRequestCommand(request);
		ServletContext context = getServletContext();
		String s=context.getRealPath("/");
		
		cmd.setUserInfo(UserInfo);
		cmd.setUserType(U_type);
		cmd.setPath(s);
		
		String next = cmd.execute(request, response);
		
		if (next.indexOf('.') < 0) {
			cmd = (Command) commands.get(next);
			next = cmd.execute(request, response);
		}		
		U_type=cmd.getUserType();
		UserInfo=cmd.getUserInfo();
		request.setAttribute("U_type", U_type);
		request.setAttribute("U_id",UserInfo);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(next);
		dispatcher.forward(request, response);
		
	}
	
	private Command resolveRequestCommand(HttpServletRequest request) {
		String s =request.getParameter("pageN");
		Command cmd = (Command) commands.get(s);
		if (cmd == null) {
			cmd = (Command) commands.get("List");
		}
		return cmd;
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		try {
			insertData();
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Service srv = null;
		try {
			srv = new Service();
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		srv.GraphStore();
		*/
		try {
			processRequest(request,response);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (NamingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		/*
		try {
			//insertData();
			Service srv = new Service();
			srv.GraphStore();
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			processRequest(request,response);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (NamingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	
	public void insertData() throws IOException, SQLException, NamingException{
		ServletContext context = getServletContext();
		Service srv = new Service();
        int i;
        int j;
        float pr=(float) 12.9;
        for(i=1;i<250;i++){
            xmlFile = new InputSource(context.getResourceAsStream("/WEB-INF/new_dblp.xml"));
            filter.setInputSource(xmlFile);
			filter.parserAll(i);
			for(j=0;j<10;j++){
				Bookinfo bf= new Bookinfo();
				bf=filter.getBook(j);
				Book bk = new Book();
				bk.setNum(1);
				bk.setBookinfo(bf);
				bk.setPrice(pr);
				bk.setStatus(1);
				bk.setPicture(1,1);
				srv.AddBook(bk);
			}
        }
	}
}
