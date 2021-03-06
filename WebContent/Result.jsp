<%@page import="ass1.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Search Results</title>
<link rel="stylesheet" type="text/css" href="img/style.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script> 
function fun(i) { 
   document.forms[0].action='Search?pageT=Seller&pageN=Add&index='+i; 
   document.forms[0].submit(); 
}   
</script>
</head>
<body>

<% 
int u_id = ((User)request.getAttribute("U_id")).getID();
String U_type = (String)request.getAttribute("U_type");
%>
<div id="header" style="position:fixed;top:0px;width:100%;" class = "navi">
<div style="float:left;margin-left:30px;width:80px;height:44px">
<a href="Search?pageT=Home&pageN=List" title="Home">Home</a>
</div>
<div style="float:left;width:80px;height:44px">
<a href="Search?pageT=Admin&pageN=Login" title="Admin">Admin</a>
</div>
<div style="float:left;width:80px;height:44px">
<a href="Search?pageT=Graph&pageN=Search" title="Graph">Graph</a>
</div>
<% if (u_id > -1) { %>
<div style="float:left;width:80px;height:44px">
<a href="Search?pageT=User&pageN=Change" title="Admin">Manage</a>
</div>
<%}%>
<div style="float:right;width:80px;height:44px">
<a href="Search?pageT=Home&pageN=Register" title="Admin">Regist</a>
</div>

<div style="float:right;width:80px;height:44px;">
<% if (u_id > -1) { %>
<a href="Search?pageT=Home&pageN=Login" title="Login">Logout</a>
<%}else{%>
<a href="Search?pageT=Home&pageN=Login" title="Login">Login</a>
<%}%>
</div>

<div style="float:right;width:80px;height:44px;">
<% if (U_type.equals("User")) { %>
<a href="Search?pageT=Cart&pageN=List" title="Cart">Cart</a>
<%}else{%>
<a href="Search?pageT=Depot&pageN=List" title="Depot">Depot</a>
<%}%>


</div>
</div>

<div id="table" style="background-color:#FFFFFF ;height:100%;width:70%;float:left;margin-top:80px;">
<table class="table">
<%
String[] alist = new String[10];
String[] id = new String[10];
String[] img = new String[10];
int pg = 0;
long total = 0;
int item=0;
pg=Integer.parseInt((String)request.getAttribute("page"));
total=Long.parseLong((String)request.getAttribute("totalpage"));
alist = (String[])request.getAttribute("list"); 
id = (String[])request.getAttribute("bookid"); 
img = (String[])request.getAttribute("img"); 

int i = 0;
for(i=0;i<10;i++){
	  if(!alist[i].equals("")){
		out.println("<tr>");
	    out.println("<td><img width=\"50\" height=\"60\" src=\""+img[i]+ "\"</td>\n");
      	out.println("<td><a href=\"Search?pageT=Home&pageN=Detail&index="+id[i]+"\">"+ alist[i] + "</a></td>\n");
      	out.println("<td>");
      	out.println("<form action=\"Search?pageN=Add&pageT=Result&index="+id[i]+"\" class=\"searchbar\" method=\"post\">");
      	out.println("<input type=\"submit\" value=\"Add to Cart\">");
      	out.println("</form>");		
      	out.println("</td>");
      	out.println("</tr>");
      	item++;
	  }
}
if(item==0){
	out.println("<tr><td>"+"Sorry, no matching datasets found!" + "</td></tr>\n");
}
   
%>
</table>

<div id="page" style="float:right;width:110px;height:44px;" class="pagebtn">
<% if (pg < total) { %>
<a href="Search?pageT=Result&pageN=next" title="Pagebtn" name="next">Next Page</a>
<% } %>

</div>

<div id="page" style="float:left;width:110px;height:44px;" class="pagebtn">
<% if (pg > 1) { %>
<a href="Search?pageT=Result&pageN=prev" title="Pagebtn" name="prev">Prev Page</a>
<% } %>
</div>
</div>

<div id="footer" style="background-color:#FFA500;clear:both;text-align:center;">
</div>


 </html>