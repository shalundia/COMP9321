<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
 <%@page import="ass1.*"%>
    
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Detail</title>
<link rel="stylesheet" type="text/css" href="img/style.css" />

<script src="/jquery/jquery-1.11.1.min.js">
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

<div id="image" style="background-color:#FFFFFF ;float:left;">
<%
String img =(String)request.getAttribute("image");
out.println("<img src="+"\""+img+"\""+" height=\"300\" width=\"240\">");
%>
</div>

<div id="menu" class="detail" style="background-color:#FFFFFF;margin-top:80px;margin-left:300px;">

<%
String list = (String)request.getAttribute("detail");
String index = Integer.toString((Integer)request.getAttribute("index"));
String action="Search?pageN=Add&pageT=Detail&index="+index;
int from=0;
int to=0;
int i=0;
while(to>-1){
	to=list.indexOf("\n", from);
	if(to>-1)
		out.println("<p>" + list.subSequence(from, to) + "</p>\n");
	from=to+1;
}
%>

<% if (u_id > -1) { %>
<form action="<%=action %>" class="searchbar" method="post">
<input type="submit" value="Add to Cart" width="80px" height="50px">
</form>
</div>
<%} %>



<div id="footer" style="background-color:#FFA500;clear:both;text-align:center;">
</div>
</body>
</html>