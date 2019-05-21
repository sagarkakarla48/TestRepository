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
<script src="${contextpath}/resources/js/alertify.js"></script>
<style>

.logout {
    padding: 5px 8px;
    font-size: 10px;
    border-radius: 5px;
    float : right;
}
.btnfont {
    font-size: 25px;
}
.btn1 {
    padding: 6px 90px !important;
}
</style>
<script type="text/javascript">
 
function adminLogout(value){
	if(value=="Logout"){
		$("#homeform").attr("action","backTohomePage");
	}
	$("#homeform").submit();
}


function submitform(value){
	if(value=="uploadExcel"){
		$("#homeform").attr("action","uploadProductsExcel");
	}else{
		$("#homeform").attr("action","uploadapk");
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
<div class="well col-sm-12" align="center" style="margin-top:10px;">
<div class="col-sm-4"></div>
<div class="col-sm-4" align="center">
<label style="font-size: 28px;">Admin</label>
</div>
<div class="col-sm-4" align="right" style="font-size: 28px;">
<img height="25" width="25" onclick="adminLogout('Logout')" src="${contextpath}/resources/images/logout.png" title="Logout">
</div>


</div>

<div class="col-sm-12 well" align="center">
<form action="#" method="post" name="homeform" id="homeform">
<div class="col-sm-12" style="">
<button class="addfields btn btnfont btn-primary" onclick="submitform('uploadExcel');">Upload Product-Rack Map File</button>
</div>
<div class="col-sm-12" style="">
<button class="addfields btn btnfont btn1 btn-primary" onclick="submitform('uploadapk');">Upload APK File</button>
</div> 
</form>
</div>
</div>
</div>
<div class="col-sm-12" style="bottom:0 !important;position:fixed;padding-bottom:10px;">
<jsp:include page="Footer.jsp"></jsp:include>
</div>
</body>
</html>