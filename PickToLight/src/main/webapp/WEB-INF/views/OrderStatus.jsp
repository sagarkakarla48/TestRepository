<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
img {
	width: 80px;
	height: 70px
}

body {
	background-color: #FDF3E7;
}
#target{
margin-top:3%;
}
div {
    margin: auto;
	display: table;
	width: 90%;
	/* height: 60px; */
	text-align: center;
	/*padding-top: 1%; */
}

td {
	align: center;
	text-color: white;
	font-size: 200%;
	padding-right: 10px;
}

table {
	/* margin-left: 30%; */
	align: center;
	border: 1px solid;
	border-collapse: separate;
	margin: 0 auto;
	border-spacing: 10px;
}

.borderless td {
	border: none;
}

#div1 {
	 margin-top: 2%; 
	 padding-left : 6%;
}

span {
	background-color: white;
	border: 1px solid white;
	border-radius:1;
	/* padding:2%; */
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/bootstrap.css"></link>
<script
	src="<%=request.getContextPath()%>/resources/js/bootstrap.min.js"></script>
<script>
$(document).ready(function(){
	//Showing order status on TV screen and refreshing page for every 10 seconds
		  var callAjax = function(){
			  $("#target").empty();
	$.ajax({
		 type : "POST",
		 url : "${pageContext.request.contextPath}/getorderstatus",
		 contentType: "application/json",
	     dataType: "json",
	      success : function(data) {
	    	  
	    	  var temp	=	"";
	    	  var temp2	=	"";
	    	  var maindiv	=	"";
	    	  var pcount	=	0;
	    	  
	    	  $.each(data, function(index, data){
	    		  
	    		  if(index==0){
	    			  temp=data.orderid;
	    			  maindiv="<div class='well' id='"+temp+"' style=background-color:"+data.color+";><table class='table table-condensed'>";
	    		  }
	    		  temp2=data.orderid;
	    		  if(temp==temp2){
	    			
	    			  if(data.status!="PICKED"){
	    				  pcount++;
    			  }
	    			
	    		  }else{
	    			  if(pcount==0){
	    			  maindiv=maindiv+"<tr><td><img src='<%=request.getContextPath()%>/resources/images/checkIcon.png'/></td><td><span>&nbsp;&nbsp;ORDER:&nbsp;&nbsp;<b>"+temp+"</b><br>&nbsp;&nbsp;COMPLETED&nbsp;&nbsp;</span></td></tr></table></div>";
	    			  }
	    			  else{
	    				  maindiv=maindiv+"<tr><td><img src='<%=request.getContextPath()%>/resources/images/Red.png'/></td><td><span>&nbsp;&nbsp;ORDER:<b>"+temp+"</b>&nbsp;&nbsp;</br>&nbsp;&nbsp;REMAINING:<b>"+pcount+"</b>&nbsp;&nbsp;</span></td></tr></table></div>";  
	    			  }
	    			  pcount=0;
	    			  temp=temp2;
	    			  maindiv=maindiv+"<div class='well' id='"+temp+"' style=background-color:"+data.color+"><table class=table borderless>";
	    			  if(data.status!="PICKED"){
	    				 pcount++;
	    			  }
	    			
	    			  }
	    		 
	    	  });
	    	  if(pcount==0){
    			  maindiv=maindiv+"<tr><td><img src='<%=request.getContextPath()%>/resources/images/checkIcon.png'/></td><td><span>&nbsp;&nbsp;ORDER:&nbsp;&nbsp;<b>"+temp+"</b>&nbsp;&nbsp;COMPLETED&nbsp;&nbsp;</span></td></tr></table></div>";
    			  }
    			  else{
    				  maindiv=maindiv+"<tr><td><img src='<%=request.getContextPath()%>/resources/images/Red.png'/></td><td><span>&nbsp;&nbsp;ORDER:<b>"
														+ temp
														+ "</b>&nbsp;&nbsp;</br>&nbsp;&nbsp;REMAINING:<b>"
														+ pcount
														+ "</b>&nbsp;&nbsp;</span></td></tr></table></div>";
											}
											$("#target").append(
													maindiv + "</table></div>");
											//alert(maindiv);
										}
									});
						
	         
	
				$.ajax({		
					 type : "POST",
					 url : "${pageContext.request.contextPath}/getBatteryCount",
					 /* contentType: "application/json",
				     dataType: "String", */
				     success : function(data) {	
				    	 if(data > 0){
				    		 $("#div1").show();
					    	 $("#batteryStatus").append("<b>"+data + "</b> - Devices With < 30% Battery.");	    		 
				    	 }
				    	 
				          }
					
				      })
	
            	}
				callAjax();
				//setInterval(callAjax, 10000);
					});
</script>
</head>
<body style="background-color: #FDF3E7;">

	<div id="div1" style = "display:none"><h4  style = "float : left; background-color: #FFFFF0" id = "batteryStatus"></h4></div>
	<div id="target"></div>
</body>
</html>