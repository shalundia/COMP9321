<%@page import="ass1.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.*"%>

<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Welcome to DBLP</title>
<link rel="stylesheet" type="text/css" href="img/style.css" />       
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

<div id="menu" style="background-color:#FFFFFF;height:100% ;width:30%;float:left;margin-top:80px;">
<form action="Search?pageT=Home&pageN=Search" class="searchbar" method="post">
Title:<br>
<input type="text" name="title" >
<br>
Author:<br>
<input type="text" name="author">
<br>
Venue:<br>
<input type="text" name="booktitle">
<br>
Publication:<br>
<input type="text" name="publisher">
<br>
Journal:<br>
<input type="text" name="journal">
<br>
Year:<br>
<input type="text" name="year">
<br>
Type:<br>
<select name="booktype">
<option value =""></option>
<option value ="article">article</option>
  <option value ="inproceedings">inproceedings</option>
  <option value="proceedings">proceedings</option>
  <option value="book">book</option>
  <option value="incollection">incollection</option>
  <option value="phdthesis">phdthesis</option>
  <option value="mastersthesis">mastersthesis</option>
  <option value="www">www</option>
</select>
<br>
<br>
<input type="submit" value="Search" width="80px" height="50px">
<a href="Search.jsp">Advanced Search</a>
</form> 
</div>

<div id="table" style="background-color:#FFFFFF ;height:100%;width:70%;float:left;margin-top:80px;">
<table class="table">
<%
String[] alist = new String[10];
String[] id = new String[10];
String tmp = (String)request.getAttribute("page");
int pg = Integer.parseInt(tmp);
tmp= (String)request.getAttribute("totalpage");
int total = Integer.parseInt(tmp);

alist = (String[])request.getAttribute("list"); 
id = (String[])request.getAttribute("bookid"); 
int i = 0;
for(i=0;i<10;i++){
	out.println("<tr><td><a href=\"Search?pageT=Home&pageN=Detail&index="+id[i]+"\">"+ alist[i] + "</a></td></tr>\n");

}
%>
</table>

<div id="page" style="float:right;width:110px;height:44px;" class="pagebtn">
<% if (pg < total) { %>
<a href="Search?pageT=Home&pageN=next" title="Pagebtn" name="next">Next Page</a>
<% } %>
</div>

<div id="page" style="float:left;width:110px;height:44px;" class="pagebtn">
<% if (pg > 1) { %>
<a href="Search?pageT=Home&pageN=prev" title="Pagebtn" name="prev">Prev Page</a>
<% } %>
</div>

</div>

<div id="footer" style="background-color:#FFA500;clear:both;text-align:center;">
</div>


 </html>