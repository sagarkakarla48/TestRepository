<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">

google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawVisualization);

function drawVisualization() {
  // Some raw data (not necessarily accurate)
 /*  var array_1 = ['Date','M06E02B0','M06E02B1', 'M06E02B2', 'M06E02B3', 'M06E02B4', 'M06E02B5', 'M06E02B6', 'M06E02B7', 'M06E02B8', 'M06E02B9', 'M06E02B10', 'M06E02B11', 'M06E02B12','M06E02B13', 'M06E02B14', 'M06E02B15', 'M06E02B16', 'M06E02B17'];
  alert(array_1);
  var array_2 = ['02/05/2017', 0.50, 1.24,      3.45,         5.40,    6.78,           5.60,      18.56,  12,      6,         7,             8,           2.30,      18, 		14,         16,             20,           21.30,      8.30];
  alert(array_2);
  var data = google.visualization.arrayToDataTable([array_1,array_2]); */ 
  
  var array_1 = ['Date'];
  var array_2 = ['02/05/2017', 0.50, 1.24,      3.45,         5.40,    6.78,           5.60,      18.56,  12,      6,         7,             8,           2.30,      18, 		14,         16,             20,           21.30,      8.30];
  for(var i =0; i < 18; i++){
	   var str = "M06E02B"+i;
	  array_1.push(str);
   }  
  alert(array_1);
  alert(array_2);
  var data = google.visualization.arrayToDataTable([array_1,array_2]); 
  
var options = {
title : 'Order completion time chart',
vAxis: {title: 'Time of Day',
	 format: 'h:mm a',
     viewWindow: {
       min: [7, 30, 0],
       max: [17, 30, 0]
     }
  },
hAxis: {title: 'Date'},
seriesType: 'bars',
series: {24: {type: 'line'}}
};

var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
chart.draw(data, options);
}

</script>
</head>
<body>
<h1>Chart Example</h1>
<div id="chart_div" style="width: 900px; height: 500px;"></div>
</body>
</html> 

<%-- <html>
<head>  
  <script type="text/javascript">
  window.onload = function () {
    var chart = new CanvasJS.Chart("chartContainer",
    {
      title:{
        text: "Converting in Local Time"
      },

      axisX:{
        title: "time",
        gridThickness: 2,
        interval:2, 
        intervalType: "hour",        
        valueFormatString: "hh TT K", 
        labelAngle: -20
      },
      axisY:{
        title: "distance"
      },
      data: [
      {        
        type: "line",
        dataPoints: [//array
        {x: new Date(Date.UTC (2012, 01, 1, 1,0) ), y: 26 },
        {x: new Date( Date.UTC (2012, 01, 1,2,0) ), y: 38  },
        {x: new Date( Date.UTC(2012, 01, 1,3,0) ), y: 43 },
        {x: new Date( Date.UTC(2012, 01, 1,4,0) ), y: 29},
        {x: new Date( Date.UTC(2012, 01, 1,5,0) ), y: 41},
        {x: new Date( Date.UTC(2012, 01, 1,6,0) ), y: 54},
        {x: new Date( Date.UTC(2012, 01, 1,7,0) ), y: 66},
        {x: new Date( Date.UTC(2012, 01, 1,8,0) ), y: 60},
        {x: new Date( Date.UTC(2012, 01, 1,9,0) ), y: 53},
        {x: new Date( Date.UTC(2012, 01, 1,10,0) ), y: 60}
        ]
      }
      ]
    });

chart.render();
}
</script>
<script type="text/javascript" src="${contextpath}/resources/js/canvasjs.min.js"></script>
<script type="text/javascript" src="${contextpath}/resources/js/jquery.canvasjs.min.js"></script>
</head>
<body>
  <div id="chartContainer" style="height: 300px; width: 100%;">
  </div>
</body>
</html> --%>