<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextpath" value="<%=request.getContextPath()%>" scope="page" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Pick To Light</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="${contextpath}/resources/css/bootstrap.css">
<link rel="stylesheet" href="${contextpath}/resources/css/productbarcode.css">
<link rel="stylesheet" href="${contextpath}/resources/css/adminlogin.css">
<link rel="stylesheet" href="${contextpath}/resources/css/alertify.css">
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextpath}/resources/js/jquery-3.1.1.js"></script>
<script src="${contextpath}/resources/js/bootstrap.js"></script>
<script src="${contextpath}/resources/js/bootstrap-hover-dropdown.js"></script>
<script src="${contextpath}/resources/js/alertify.js"></script>
<style>
.btnfont{
font-size:25px;
}

</style>
<!-- <script type="text/javascript">
    window.onload = maxWindow;

    function maxWindow() {
        window.moveTo(0, 0);


        if (document.all) {
            top.window.resizeTo(screen.availWidth, screen.availHeight);
        }

        else if (document.layers || document.getElementById) {
            if (top.window.outerHeight < screen.availHeight || top.window.outerWidth < screen.availWidth) {
                top.window.outerHeight = screen.availHeight;
                top.window.outerWidth = screen.availWidth;
            }
        }
    }

</script>  -->
<script>
    // very simple to use!
    $(document).ready(function() {
      $('.js-activated').dropdownHover().dropdown();
    });
  </script>
<script type="text/javascript">
function adminLogin(value){	
	var userName = $('#userName').val();
	var password = $('#password').val();
	//Cheking for the credentials of admin 
	$.ajax({
		 type : "POST",
		 data: "userName=" + userName + "&password=" + password,
		 url : "${pageContext.request.contextPath}/adminLogin",		 
	      success : function(data) {
	    	  //If the username and password are authorized then navigates to selectUpload page
	    	  if(data == "SUCCESS"){
	    		  if(value == "login"){
	    			  $("#loginForm").attr("action","selectUpload");
	    		  }
	    		  $("#loginForm").submit();		  
	    	  }else if(data == "FAILURE"){
	    		  $("#failureMesg").show();
	    	  }
	    }
	});
}
 
 
//checking for the button action
function submitform(value){
	if(value=="products"){
		$("#homeform").attr("action","orderedProductsNumber");
	}else if(value=="battery"){
		$("#homeform").attr("action","getBatteryStatus");
	}else if(value=="lowinventory"){
		$("#homeform").attr("action","lowInventoryInfoHome");
	}else if(value=="pickingAnalytics"){
		$("#homeform").attr("action","pickingAnalytics");
	}else if(value=="orderstatus"){
		$("#homeform").attr("method","GET");
		$("#homeform").attr("action","tvstatus");
	}else{
		$("#homeform").attr("action","orderExcelUpload");
	}
	$("#homeform").submit();
}

var modal = document.getElementById('myModal');

//Get the button that opens the modal
var btn = document.getElementById("myBtn");

//Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

//When the user clicks the button, open the modal 
btn.onclick = function() {
 modal.style.display = "block";
}

//When the user clicks on <span> (x), close the modal
span.onclick = function() {
 modal.style.display = "none";
}

//When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
 if (event.target == modal) {
     modal.style.display = "none";
 }
}

</script>
</head>
<body>
<div class="container" >
<div>
<div class="col-sm-12 well" style="margin-top:10px;">
<div class="col-sm-4"></div>
<div class="col-sm-4" align="center">
<label style="font-size: 28px;">Pick to Light</label>
</div>
<div class="col-sm-4" align="right" style="font-size: 28px;">
<%-- <img data-toggle="modal" data-target="#login-modal" height="25" width="25" src="${contextpath}/resources/images/settings.png"> --%>
<div class="btn-group" style="padding-right: 20px;">
<button class="btn dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"><img data-toggle="modal"  height="25" width="25" src="${contextpath}/resources/images/settings.png"></button>
 <ul class="dropdown-menu">
            <li><a tabindex="-1" data-toggle="modal" data-target="#login-modal" href="#">Admin Login</a></li>
            <li><a tabindex="-1" href="#" onclick="submitform('battery')">Battery Level</a></li>
            <li><a tabindex="-1" href="#" onclick="submitform('lowinventory')">Low Inventory</a></li>
            <li><a tabindex="-1" href="#" onclick="submitform('pickingAnalytics')">Picking Time Analytics</a></li>
            <!-- <li><a tabindex="-1" href="#" onclick="submitform('orderstatus')">Order Status</a></li> -->
          </ul>
          </div>
</div>
</div>
<div class="col-sm-12 well" align="center">
<form action="#" method="post" name="homeform" id="homeform">
<%-- <div class="col-sm-12" >
<img data-toggle="modal" data-target="#login-modal" align = "right" height="30" width="30" src="${contextpath}/resources/images/settings.png">
</div> --%>
<div class="col-sm-12" style="">
<label style="font-size: 21px;">Please use the below button to upload E-File </label>
</div>
<!-- <div class="col-sm-12" style="">
<button class="addfields btn btnfont btn-primary" onclick="submitform('products');">Scan Barcodes</button>
</div> -->
<div class="col-sm-12" style="">
<button class="addfields btn btnfont btn-primary" style="padding: 6px 22px !important;" onclick="submitform('orders');">Upload E-File</button>
</div> 
</form>
</div>

<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
<div class="modal-dialog">
	<div class="loginmodal-container" align="center">
		<h1>Admin Login</h1><br>				
		<form  action = "adminLogin" method = "post" name = "loginForm" id="loginForm">
			<input type="text" name="userName" id = "userName" placeholder="Username">
			<input type="password" name="password" id = "password" placeholder="Password">
			<input type="button" name="login" class="login btn btn-primary" value="Login" onclick="adminLogin('login')">
	    </form><br>
		<label style="color : red; display : none" id = "failureMesg">Invalid UserName or Password Please ReEnter</label>
    </div>
</div>
</div>
</div>
<footer>
<div>
<div>
</div>
<div>
</div>
</div>
</footer>
</div>
<div class="col-sm-12" style="bottom:0 !important;position:fixed;padding-bottom:10px;">
<jsp:include page="Footer.jsp"></jsp:include>
</div>
</body>
</html>