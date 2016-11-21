<%@page import="ass1.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.*"%>

<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Monitor</title>
<link rel="stylesheet" type="text/css" href="img/style.css" />
</head>
<body>
<div id="header" style="position:fixed;top:0px;width:100%;" class = "navi">
<div style="float:left;margin-left:30px;width:80px;height:44px">
<a href="Search?pageT=Home&pageN=List" title="Home">Home</a>
</div>
<div style="float:left;width:80px;height:44px">
<a href="Search?pageT=MonitorSold&pageN=List&index=<%=(String)request.getAttribute("index")%>" title="Admin">Sold</a>
</div>
<div style="float:left;width:80px;height:44px">
<a href="Search?pageT=MonitorRemoved&pageN=List&index=<%=(String)request.getAttribute("index")%>" title="Admin">Remove</a>
</div>
</div>
<%String pageT = (String)request.getAttribute("pageT"); %>
<div id="table" style="background-color:#FFFFFF ;height:100%;width:70%;float:left;margin-top:80px;">
<table class="table">
<%if(pageT.equals("MonitorSold")) {%>
<tr>
    <th>Title</th>
    <th>TimeStamp</th> 
    <th>Price</th>
    <th>Vender</th>
</tr>
<%
int num = (Integer)request.getAttribute("number");
String[] title = (String[])request.getAttribute("title"); 
String[] time = (String[])request.getAttribute("time"); 
String[] price = (String[])request.getAttribute("price"); 
String[] vender = (String[])request.getAttribute("vender"); 

int i = 0;
for(i=0;i<num;i++){
	out.println("<tr><td>"+ title[i] + "</td>");
	out.println("<td>"+ time[i] + "</td>");
	out.println("<td>"+ price[i] + "</td>");
	out.println("<td>"+ vender[i] + "</td>");	
	out.println("</tr>\n");
}
%>
<form action="Search?pageT=Chart&pageN=List" method="post" class="searchbar">
<input type="submit" value="Chart">
</form>
<%}else{ %>
<tr>
    <th>Title</th>
    <th>Time Add</th> 
    <th>Time Removed</th>
</tr>
<%
int num = (Integer)request.getAttribute("number");
String[] title = (String[])request.getAttribute("title"); 
String[] timeA = (String[])request.getAttribute("timeAdd"); 
String[] timeR = (String[])request.getAttribute("timeRmv"); 

int i = 0;
for(i=0;i<num;i++){
	out.println("<tr><td>"+ title[i] + "</td>");
	out.println("<td>"+ timeA[i] + "</td>");
	out.println("<td>"+ timeR[i] + "</td>");
	out.println("</tr>\n");
}
%>
<%} %>
</table>
</div>

<div id="footer" style="background-color:#FFA500;clear:both;text-align:center;">
</div>


 </html>