<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@page import="ass1.*"%>
<%@page import="java.util.*"%>


<!doctype html>
<html>
<head>
  <title>Network of triple store!</title>

  <script type="text/javascript" src="img/vis.js"></script>
  <link href="img/style.css" rel="stylesheet" type="text/css" />

  <style type="text/css">
    #mynetwork {
      width: 800x;
      height: 480px;
      border: 1px solid lightgray;
    }
  </style>
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

<p>
Network of triple store.
</p>
<%
String pageT=(String)request.getAttribute("pageT");
int[] node,from,to,id_e;
String[] label,edge;
List<Relation> rl = new ArrayList<Relation>();
label =(String[])request.getAttribute("label");
edge =(String[])request.getAttribute("edge");
node =(int[])request.getAttribute("node");
from =(int[])request.getAttribute("from");
to =(int[])request.getAttribute("to");
id_e=(int[])request.getAttribute("id_e");
rl=(ArrayList<Relation>)request.getAttribute("rl");
int arr_num=rl.size();
int nd_num=node.length;
%>
<div id="mynetwork"></div>
<script type="text/javascript">
  // create an array with nodes

  var nodeArray =[
<%
  int i=0;
  for(i=0;i<nd_num-1;i++){
	  out.println("{id:"+node[i]+", label:'"+label[i]+"\\n id="+node[i]+"',shape:'box',color: 'rgb(255,168,7)'},");
  }
  out.println("{id:"+node[i]+", label:'"+label[i]+"\\n id="+node[i]+"',shape:'box',color: 'rgb(255,168,7)'}");
 %>
 ];
  var arrorArray=[
 <%
  for(i=0;i<arr_num-1;i++){
	  out.println("{from:"+from[i]+" , to:"+to[i]+", arrows:'to',label:'"+edge[i]+"\\n id="+id_e[i]+"'},");
  }
 out.println("{from:"+from[i]+" , to:"+to[i]+", arrows:'to',label:'"+edge[i]+"\\n id="+id_e[i]+"'}");
 %>
];
  var nodes = new vis.DataSet(nodeArray);
  var edges = new vis.DataSet(arrorArray);

  // create a network
  var container = document.getElementById('mynetwork');
  var data = {
    nodes: nodes,
    edges: edges
  };
  var options = {};
  var network = new vis.Network(container, data, options);
</script>

<script src="../../googleAnalytics.js"></script>
</body>
</html>
