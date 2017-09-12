<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextpath" value="<%=request.getContextPath()%>" scope="page" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Barcode Picker</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="${contextpath}/resources/css/bootstrap.css">
<link rel="stylesheet" href="${contextpath}/resources/css/productbarcode.css">
<link rel="stylesheet" href="${contextpath}/resources/css/alertify.css">

<!-- jQuery library -->
<script src="${contextpath}/resources/js/jquery-3.1.1.js"></script>
<!-- Latest compiled JavaScript -->
<script src="${contextpath}/resources/js/bootstrap.js"></script>
<script src="${contextpath}/resources/js/jquery.numeric.js"></script>
<script src="${contextpath}/resources/js/angular.js"></script>
<script src="${contextpath}/resources/js/alertify.js"></script>
<style>
.btn{
font-size:18px;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	$("#number").focus();
	$(".numeric").numeric();
});
</script>
</head>
<body >
<div class="container" align="center" data-ng-app="prodApp" id="prodCtrl" data-ng-controller="prodCtrl" style="font-size: 18px;">
<div>
<div class="well col-sm-12" style="margin-top:10px;">
<div class="col-sm-4"></div>
<div class="col-sm-4" align="center">
<label style="font-size: 28px;">Products Order</label>
</div>
<div class="col-sm-4" align="right" style="font-size: 28px;">
<a href="home">
<img height="35" width="35" src="${contextpath}/resources/images/homemenu.png" title = "Home" alt="Home">
</a>
</div>
</div>
<div class="col-sm-12 well">
<form action="createOrder" method="post" name="productform" id="productform" novalidate>
<div class="col-sm-12" style="">
<label>Enter Number of Products : </label>
<input type="text" class="numeric" name="number" id="number" data-ng-model="number" placeholder="Enter Products Number" autocomplete="off" maxlength="3" required>
<p class="help-block red" data-ng-show="productform.number.$invalid && productform.number.$touched">This Field is Required</p>
</div>
<div class="col-sm-12" style="margin-top:10px;">
<input type="submit" data-ng-disabled="productform.$invalid" class="addfields btn btn-primary" value="Submit">
</div> 
</form>
</div>
</div>
</div>
<div class="col-sm-12" style="bottom:0 !important;position:fixed;padding-bottom:10px;">
<jsp:include page="Footer.jsp"></jsp:include>
</div>
<script type="text/javascript">
var app = angular.module('prodApp', []);
app.controller('prodCtrl', ['$scope','$http',function($scope,$http) {
	$scope.number;
	
}]);
</script>
</body>
</html>