<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
        <%@page import="ass1.*"%>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link rel="stylesheet" type="text/css" href="img/style.css" />
<title>
Search
</title>

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
<div id="menu" class="searchpage">
<h1 style="margin-bottom:0;">Advanced Search</h1>
<form action="Search?pageT=Result&pageN=Search" class="searchbar" method="post" >
Title:<br>
<input type="text" name="title">
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
Series:<br>
<input type="text" name="series">
<br>
Year:<br>
<input type="text" name="year">
<br>
School:<br>
<input type="text" name="school">
<br>
ISBN:<br>
<input type="text" name="isbn">
<br>
Volume:<br>
<input type="text" name="volume">
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

<input type="submit" value="Search">

</form> 
</div>

</body>
</html>