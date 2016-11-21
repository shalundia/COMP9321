<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@page import="ass1.*"%>
<%@page import="java.util.*"%>

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

<div id="header" style="position:fixed;top:0px;width:100%;" class = "navi">
<div style="float:left;margin-left:30px;width:80px;height:44px">
<a href="Search?pageT=Home&pageN=List" title="Home">Home</a>
</div>
</div>

<div id="menu" style="background-color:#FFFFFF;height:100px;width:300px;margin-left:300px;margin-top:80px;">
<form action="Search?pageT=GraphResult&pageN=Search" class="graphbar" method="post">
Title:<input type="text" name="title" size="60px">
<br>
Type:
<select name="kind">
<option value ="Article">Article</option>
<option value ="Author">Author</option>
<option value ="Venue">Venue</option>
</select><br>
<input type="submit" value="Search" size="50px">
</form> 
</div>

<%
String pageT=(String)request.getAttribute("pageT");
List<Entity> el = new ArrayList<Entity>();
List<Relation> rl = new ArrayList<Relation>();
el=(ArrayList<Entity>)request.getAttribute("el");
rl=(ArrayList<Relation>)request.getAttribute("rl");
%>
<%if(pageT.equals("GraphResult")){ %>
<div id="menu" class="detail" style="background-color:#FFFFFF;height:100% ;width:70%;float:center;margin-top:80px;margin-left:15%">
<form action="Search?pageT=GraphTO&pageN=Search" method="post" class="searchbar">

<table class="table">
<tr>
	<th>ID</th>
	<th>Title</th>
	<th>Class</th>
	<th>Type</th>
</tr>

<%;
int i;
for(i=0;i<el.size();i++){
	out.println("<tr><td>"+ el.get(i).getID() +"</td>");
	out.println("<td>"+ el.get(i).getTitle() +"</td>");
	out.println("<td>"+ el.get(i).getEClass() +"</td>");
	out.println("<td>"+ el.get(i).getKind() +"</td></tr>");
}
%>

</table>

<table class="table">
<tr>
	<th>Subject</th>
	<th>Predicate</th>
	<th>Object</th>
</tr>
<%;
for(i=0;i<rl.size();i++){
	out.println("<tr><td>"+ rl.get(i).getSub() +"</td>");
	out.println("<td>"+ rl.get(i).getPre() +"</td>");
	out.println("<td>"+ rl.get(i).getObj() +"</td></tr>");
}
%>
</table>
<input type="submit" value="Graph" width="80px" height="50px">
</form>

<%} %>

</div>


</body>
</html>