<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextpath" value="<%=request.getContextPath()%>" scope="page" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width">
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css"
	rel="stylesheet" type="text/css">
<title>PickToLight</title>

<link rel="stylesheet"
	href="${contextpath}/resources/css/bootstrap.css"></link>
<style type="text/css">
img {
	height: 30px;
	width: 50px;
}
#batInfo{
    height: 20px;
	width: 25px;
}
.home {
	height: 35px !important;
	width: 35px !important;
}

#div1 {
	float: left;
}

#div2 {
	float: left;
}

#div3 {
	float: left;
}

.paging-nav {
	text-align: right;
	padding-top: 2px;
}

.paging-nav a {
	margin: auto 1px;
	text-decoration: none;
	display: inline-block;
	padding: 1px 7px;
	background: #91b9e6;
	color: white;
	border-radius: 3px;
}

.paging-nav .selected-page {
	background: #187ed5;
	font-weight: bold;
}

.paging-nav, #tableData {
	width: 400px;
	margin: 0 auto;
	font-family: Arial, sans-serif;
}
li{
	display:inline;
	
}
ul{
padding-left: 0pt;

}
</style>
</head>

<body>

	<div class="container" class="">
		<div class="well col-sm-12" align="center"
			style="margin-top: 20px; font-weight: bold;">
			<div class="col-sm-4">
			</div>
			<div class="col-sm-4" >
				<label style="font-size: 28px;">Battery Status</label>			
			</div>
			<div class="col-sm-4" align="right" style="font-size: 28px;">
				<a href="home"><img class = "home" src="${contextpath}/resources/images/homemenu.png" title = "Home" alt="Home">
				</a>
			</div>
			
		</div>
		<div class="col-sm-12">	
			
		</div>
		<div class="well">		
		   <div id="div1">
				<img id = "batInfo" src='${contextpath}/resources/images/blow.png' "/><30%
			</div>
			<div id="div2">
				<img id = "batInfo" src='${contextpath}/resources/images/mid1.jpg' "/>>30%
				and <75%
			</div>
			<div id="#div3">
				<img id = "batInfo" src='${contextpath}/resources/images/bfull.ico' "/>>75%
			</div>			
			<div style = "background-color:lightgray; width:40%">
			   <h6><font color="black" size="3px">*&nbsp;&nbsp;To see Rack Id, Please Hover The Mouse on Battery Icon</font></h6>		
			</div>
		
		<br/><br/>
		
			<table id="tableData" class="table table-striped table-bordered"
				cellspacing="0" width="100%">

				<tbody id="tablebodyid">

				</tbody>
			</table>			
		    <div id="div4">
				<b><p id = "deviceInfo"></p></b>
			</div>
		</div>
	</div>
	<div class="col-sm-12" style="bottom:0 !important;position:fixed;padding-bottom:10px;">
<jsp:include page="Footer.jsp"></jsp:include>
</div>
	<!-- <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>  -->
	<script
		src="${contextpath}/resources/js/jquery-3.1.1.js"></script>
	<script
		src="${contextpath}/resources/js/bootstrap.min.js"></script>
	<script
		src="${contextpath}/resources/js/jquery-ui.min.js"></script>
	<script src="${contextpath}/resources/js/paging.js"></script>
	
<script type="text/javascript">
$(document).ready(function(){
	
	function checkMobiles(){
	$.ajax({
		 type : "POST",
		 url : "${pageContext.request.contextPath}/getProductInfoForDeviceTracking",
		 contentType: "application/json",
	     dataType: "json",
	      success : function(data) {
	    	  if(data!=null){
	    		  var msg="Mobile device(s) in ";
	    		  $.each(data,function(index,rack){
	    			  if(index==0){
	    				  msg=msg+rack;
	    			  }else if(data.length-1==index){
	    				  msg=msg+" and "+rack;
	    			  }else{
	    				  msg=msg+", "+rack;
	    			  }
	    		  })
	    		  msg=msg+" is/are not working. Please check device(s).";
	    		  $("#deviceInfo").text(msg);
	    	  }else{
	    		  $("#deviceInfo").text('All Devices Are Working Fine');
	    	  }
	    }
	});
	setTimeout(function(){ 
		checkMobiles();
	}, 60000);
	}
	checkMobiles();	
})

</script>


	
<script>
$(document).ready(function() {
	//getting battery Info from database
	var list = ${list};
	var data ="<tr>";
	var rackid="";
	var count=$( "#tableData tr:visible td" ).length
	//looping through rackid's in battery list
	$.each(list, function( index, batList ) {
		rackid=batList.rackid;
		//displaying battery images based on battery level range
		if(batList.blevel<30){
		data = data+"<td><img src='${contextpath}/resources/images/blow.png' class='rimg1'  title=RackId:"+rackid+"></td>";
		}
		/* else if(batList.blevel>=30 && batList.blevel<75)
			{
			data = data+"<td><img src='${contextpath}/resources/images/mid1.jpg' class='yimg1' title=RackId:"+rackid+"></td>";

			
			}
		else 
		{
		data = data+" <td><img src='${contextpath}/resources/images/bfull.ico' class='gimg1' title=RackId:"
												+ rackid + "></td>";

									} */
									count++;
									//Limiting the number of rackid status for a row
									if (count % 15 == 0) {
										data = data + "</tr><tr>";

									}

								});

				$("#tablebodyid").append(data + "</tr>");
			
				//setting pagination to web page to show abundant number of racks battery Info
				$('#tableData').paging({
					limit : 12
				});

			});
</script>


</body>
</html>