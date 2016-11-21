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

<div id="header" style="position:fixed;top:0px;width:100%;" class = "navi">
<div style="float:left;margin-left:30px;width:80px;height:44px">
<a href="Search?pageT=Home&pageN=List" title="Home">Home</a>
</div>
<div style="float:left;width:80px;height:44px">
<a href="Search?pageT=AdminUser&pageN=List" title="Admin">User</a>
</div>
<div style="float:left;width:80px;height:44px">
<a href="Search?pageT=AdminSeller&pageN=List" title="Admin">Book</a>
</div>
</div>
<%
String pageT=(String)request.getAttribute("pageT");
String action = "";
String value="";
String next="";
String prev="";
if(pageT.equals("AdminUser")){
	action="Search?pageT=AdminUser&pageN=Change";
	next="Search?pageT=AdminUser&pageN=List";
	prev="Search?pageT=AdminUser&pageN=List";
	value="Ban User";
}else{
	action="Search?pageT=AdminSeller&pageN=Delete";
	next="Search?pageT=AdminSeller&pageN=List";
	prev="Search?pageT=AdminSeller&pageN=List";
	value="Delete Book";
}

%>
<div id="menu" class="detail" style="background-color:#FFFFFF;height:100% ;width:70%;float:center;margin-top:80px;margin-left:15%">
<form action="<%=action %>" method="post" class="searchbar">
<%
String[] alist = new String[10];
String[] id = new String[10];
int pg = 0;
long total = 0;
int item = 0;
pg=Integer.parseInt((String)request.getAttribute("page"));
total=Long.parseLong((String)request.getAttribute("totalpage"));
alist = (String[])request.getAttribute("list"); 
id = (String[])request.getAttribute("bookid"); 

int i = 0;
for(i=0;i<10;i++){
	  if(!alist[i].equals("")){
		if(pageT.equals("AdminUser")){
		    out.println("<input type=\"checkbox\" name=\"index\" value=\""+id[i]+"\">");
		    out.println("<a href=\"Search?pageT=MonitorSold&pageN=List&index="+id[i]+"\">"+alist[i] + "<br>");
		}else{
	      	 out.println("<input type=\"checkbox\" name=\"index\" value=\""+id[i]+"\">");
			 out.println("<a href=\"Search?pageT=Home&pageN=Detail&index="+id[i]+"\">"+alist[i] + "<br>");

		}

	  	item++;
	  }
}   
%>
<% if(item>0){%>
<input type="submit" value="<%=value %>" width="80px" height="50px">
<%}else{ %>
<p>Shopping Cart is Empty!</p>
<%} %>
</form>

<div id="page" style="float:right;width:110px;height:44px;" class="pagebtn">
<% if (pg < total) { %>
<a href="<%=next %>" title="Pagebtn" name="next">Next Page</a>
<% } %>
</div>

<div id="page" style="float:left;width:110px;height:44px;" class="pagebtn">
<% if (pg > 1) { %>
<a href="<%=prev %>" title="Pagebtn" name="prev">Prev Page</a>
<% } %>
</div>

</div>


</body>
</html>