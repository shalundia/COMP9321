<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    <%@page import="ass1.*"%>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Cart</title>
<link rel="stylesheet" type="text/css" href="img/style.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script> 
function fun2() { 
   document.forms[0].action='Search?pageT=Cart&pageN=Delete'; 
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

<div id="menu" class="detail" style="background-color:#FFFFFF;height:100% ;width:70%;float:center;margin-top:80px;margin-left:15%">
<form action="Search?pageT=CartBuy&pageN=Delete" method="post" class="searchbar">
<table>
<%
String[] alist = new String[10];
String[] id = new String[10];
String[] pr = new String[10];

int pg = 0;
long total = 0;
int item = 0;
pg=Integer.parseInt((String)request.getAttribute("page"));
total=Long.parseLong((String)request.getAttribute("totalpage"));
alist = (String[])request.getAttribute("list"); 
id = (String[])request.getAttribute("bookid"); 
pr = (String[])request.getAttribute("price"); 

int i = 0;
for(i=0;i<10;i++){
	  if(!alist[i].equals("")){
		 out.println("<tr><td>");
      	 out.println("<input type=\"checkbox\" name=\"index\" value=\""+id[i]+"\"></td>");
      	 out.println("<td>"+alist[i] +"</td>");
      	 out.println("<td>"+pr[i] +"</td>");
      	 out.println("</tr>");
	  	item++;
	  }
}   
%>
<% if(item>0){%>
<input type="submit" value="Purchase" >
<input type="button" value="Delete" onClick="fun2()" >

<%}else{ %>
<p>Shopping Cart is Empty!</p>
<%} %>
</table>
</form>

<div id="page" style="float:right;width:110px;height:44px;" class="pagebtn">
<% if (pg < total) { %>
<a href="Search?pageT=Cart&pageN=next" title="Pagebtn" name="next">Next Page</a>
<% } %>
</div>

<div id="page" style="float:left;width:110px;height:44px;" class="pagebtn">
<% if (pg > 1) { %>
<a href="Search?pageT=Cart&pageN=prev" title="Pagebtn" name="prev">Prev Page</a>
<% } %>
</div>

</div>



</body>
</html>