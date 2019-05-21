<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<!-- <script type="text/javascript">
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
	    		  alertify.alert("",msg, function(){
   				  });	    		 
	    	  }else{
	    	  }
	    }
	});
	setTimeout(function(){ 
		checkMobiles();
	}, 60000);
	}
	checkMobiles();
})

</script> -->
<script type="text/javascript">
//override defaults for Alertify alert
alertify.defaults.transition = "slide";
alertify.defaults.theme.ok = "btn btn-primary";
alertify.defaults.theme.cancel = "btn btn-danger";
alertify.defaults.theme.input = "form-control";
</script>
</head>
<body>
<div class="container" style="background-color:#f5f5f5;border: 1px solid hsla(0, 0%, 81%, 0.14);;">
<div class="col-sm-6">© 2016 LARSEN & TOUBRO LIMITED. All rights reserved.</div>
<div class="col-sm-6" align="right">Version 1.0</div>
</div>
</body>
</html>