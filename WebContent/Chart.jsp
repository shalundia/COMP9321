<!DOCTYPE HTML>
<html>
<head>
    <title>Sale Chart</title>

    <style type="text/css">
        body, html {
            font-family: sans-serif;
        }
    </style>

    <script src="img/vis.js"></script>
    <link href="img/vis.css" rel="stylesheet" type="text/css" />
</head>
<body>
<h2>Sale Chart</h2>
<div style="width:700px; font-size:14px; text-align: justify;">
   
</div>
<br />

<div id="visualization"></div>

<script type="text/javascript">
<%
Integer num=(Integer)request.getAttribute("number");
String[] time =(String[])request.getAttribute("time");
Float[] pr=(Float[])request.getAttribute("price");

%>
    var container = document.getElementById('visualization');
    var items = [
<%	
int i;
for(i=0;i<num-1;i++){
	out.println("{x:'"+time[i]+"',y:"+pr[i]+"},");
}
out.println("{x:'"+time[i]+"',y:"+pr[i]+"}");
%>
    ];

    var dataset = new vis.DataSet(items);
    var options = {
        style:'bar',
        barChart: {width:50, align:'center'}, // align: left, center, right
        drawPoints: false,
        dataAxis: {
            icons:true
        },
        orientation:'top',
        start: '2016-10-01',
        end: '2016-11-01'
    };
    var graph2d = new vis.Graph2d(container, items, options);

</script>
</body>
</html>