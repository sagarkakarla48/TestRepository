<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextpath" value="<%=request.getContextPath()%>" scope="page" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Order List Confirmation</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="${contextpath}/resources/css/font-awesome.css">
<link rel="stylesheet" href="${contextpath}/resources/css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="${contextpath}/resources/css/jquery-ui.min.css" />
<link rel="stylesheet" href="${contextpath}/resources/css/bootstrap.css">
<link rel="stylesheet" href="${contextpath}/resources/css/productbarcode.css">
<link rel="stylesheet" href="${contextpath}/resources/css/alertify.css">

<!-- jQuery library -->
<script src="${contextpath}/resources/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="${contextpath}/resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${contextpath}/resources/js/jquery.babypaunch.spinner.min.js"></script>
<script src="${contextpath}/resources/js/angular.js"></script>
<!-- Latest compiled JavaScript -->
<script src="${contextpath}/resources/js/bootstrap.js"></script>
<script src="${contextpath}/resources/js/orderlistconfirmation.js"></script>
<script src="${contextpath}/resources/js/alertify.js"></script>
<style>
#spin{
z-index:10000 !important;
}
.highlight {
    color: red;
    font-weight:bold;
}
.errmsg{
color:red;
font-weight:bold;
}
.backgroundcolor{
background-color:orange;
}
</style>
<script>
$(document).ready(function() {
$("#spin").spinner();
});
</script>
<script type="text/javascript">
//override defaults for Alertify alert
alertify.defaults.transition = "slide";
alertify.defaults.theme.ok = "btn btn-primary";
alertify.defaults.theme.cancel = "btn btn-danger";
alertify.defaults.theme.input = "form-control";
</script>
<script type="text/javascript">
var orderlist="";
$(document).ready(function(){
	orderlist=${orderslist};
	var data="";
	$.each(orderlist,function(index,order){
		data=data+"<tr><td>"+order.orderid+"</td><td>"+order.barcode+"</td><td>"+order.quantity+"</td><td>"+order.description+"</td><td>"+order.superMarketDesc+"</td><td>"+order.serialNo+"</td><td>"+order.unitdDesc+"</td><td>"+order.pickerOrder+"</td><td>"+order.iDesc+"</td><td>"+order.installation+"</td></tr>";
	})
$("#order-details").append(data);
})
var app = angular.module('prodApp', []);
app.controller('prodCtrl', ['$scope','$http',function($scope,$http) {
	
	$scope.sendDataToServer = function() {
    $("#spin").show();
	var response = $http.post('productsSave', JSON.stringify(orderlist));
    
	response.success(function(data, status, headers, config) {
		 $("#spin").hide();
		if(data!=null && data!=""){
			var productArr=[];
			var productmarketsarr=[];
			var misMatchProductList=[];
			var ordermarkettemp={};
			var msg="";
			$.each(data,function(checkFor,products){
				
				$.each(products,function(i,productsbean){
					
					if($.inArray(productsbean.barcode, productArr) == -1){
						productArr.push(productsbean.barcode);
						ordermarkettemp[""+productsbean.barcode+""]=productsbean.superMarketDesc;
					 }
					if(i==0){
						msg=msg+"<b>"+productsbean.barcode+"</b>";
					}else if(i!=(data.length-1)){
						msg=msg+", <b>"+productsbean.barcode+"</b>";
					}else{
						msg=msg+" and <b>"+productsbean.barcode+"</b>";
					}
				 });
				productmarketsarr.push(ordermarkettemp);
				
				
				if(checkFor=="product"){
					
					if(data.length>1){
						msg=msg+" are not available. Do you want to continue to save E-File?";
					}else{
						msg=msg+" is not available. Do you want to continue to save E-File?";
					}
					//alertify.alert("",msg);
					alertify.confirm("", msg, function(){
						$.each(productArr,function(key,product){ 
							var indices =orderlist.reduce(function(a, e, i) {
							    if (e.barcode === product)
							        a.push(i);
							    return a;
							}, []);
							$.each(indices,function(indexkey,index){
							misMatchProductList.push(orderlist[index]);
						    orderlist.splice(index,1);
						 }); 
						}); 
						 $http({
				     			method: 'POST',
				     			url: 'saveMisMatchedProducts',
				     			data :JSON.stringify(misMatchProductList),
				     			headers: {
				                 	 'Content-Type': 'application/json'
				                	}
				     		})
				     		.success(function (data, status, headers, config) {
				     			//alert("success");
				     		}).error(function (data, status, headers, config) {
				     			//alert("fail");
				     		});
						 $scope.sendDataToServer();
					}, 
					function(){
						alertify.alert("","You have to remove/change products manually and upload E-File again.");
					});
					
				}else{
					if(data.length>1){
						msg=msg+" super markets are not matched with mapping file. Do you want to replace market name as in mapping file?";
					}else{
						msg=msg+" super market is not matched with mapping file. Do you want to replace market name as in mapping file?";
					}
					alertify.confirm("", msg, function(){
						 $.each(productArr,function(key,product){ 
							var indices =orderlist.reduce(function(a, e, i) {
							    if (e.barcode === product)
							        a.push(i);
							    return a;
							}, []);
							$.each(indices,function(indexkey,index){
						    orderlist[index].superMarketDesc=productmarketsarr[0][product];
						 }); 
						}); 
						 $scope.sendDataToServer();
					}, 
					function(){ 
					});
				}
				
				
			})
			
			}else{
				 $("#spin").hide();
				alertify.alert("","E-File saved successfully.", function(){
					$("#homeform").attr("action","orderExcelUpload");
					  $("#homeform").submit();
					  });
			}
	});
	
	response.error(function(data, status, headers, config) {
		alert( "Exception details: " + JSON.stringify({data: data}));
	});
	}
	
	
}]);
</script>
<script type="text/javascript">
$(document).ready(function(){
	highlightDuplicateRows("#table");
	});
</script>
<!-- <script type="text/javascript">
$(document).ready(function(){
	
	// this is for 
	$.ajax({
		 type : "GET",
		 url : "${pageContext.request.contextPath}/getcolor",
		 contentType: "application/json",
	     dataType: "json",
	      success : function(data) {
	    	  if(data!=null){
	    		  var color=data;
	    			$(".foo").css("background-color",color);
			    	$('#color').val(color);
	    		 
	    	  }else{
	    		  alertify.alert("","Your order limit completed.", function(){
    				  $("#productnumberform").submit();
    				  });
	    	  }
	    }
	});
	});
</script> -->
</head>
<body>
<div class="container" align="center"  data-ng-app="prodApp" id="prodCtrl" data-ng-controller="prodCtrl">
<div>
<div class="well col-sm-12" style="margin-top:10px;">
<div class="col-sm-4"></div>
<div class="col-sm-4" align="center">
<label style="font-size: 28px;">Uploaded E-File</label>
</div>
<div class="col-sm-4" align="right" style="font-size: 28px;">
<a href="home">
<img height="35" width="35" src="${contextpath}/resources/images/homemenu.png" title = "Home" alt="Home">
</a>
</div>
</div>
<div class="col-sm-12 well">
<!-- <div align="right">
 <label class="foo"></label>
 </div> -->
<form action="#" method="post" name="homeform" id="homeform">
<!-- <input type="text" style="display:none;" data-ng-model="color" name="color" id="color"> -->
<div class="col-sm-12" style="">
<div align="left" id="errmsgdiv" style="display:none;">
<label class="errmsg">Please Remove Duplicates in E-File and Upload it Again </label>
</div>
<div align="left" id="errmsgdiv1" style="display:none;">
<label class="errmsg">Order Id, Product Id, Quantity, Super Market Description and Serial No Should Not be Empty </label>
</div>
<div class="table-responsive">
<table class="table" id="table">
<thead>
<tr>
<th>Order Id</th>
<th>Product Id</th>
<th>Quantity</th>
<th>Description</th>
<th>Super Market Desc</th>
<th>Serial No</th>
<th>Unitd Desc</th>
<th>Picker Order</th>
<th>i-Desc</th>
<th>Installation</th>
</tr>
</thead>
<tbody id="order-details">

</tbody>
</table>
</div>
</div>
</form>
<div class="col-sm-12" style="">
<button class="addfields btn btn-primary" onclick="submitform('excel');">Upload E-File</button>
<button class="addfields btn btn-primary" id="submitid" data-ng-click="sendDataToServer();">Submit</button>
</div> 
</div>
</div>
</div>
<div class="col-sm-12" style="bottom:0 !important;position:fixed;padding-bottom:10px;">
<jsp:include page="Footer.jsp"></jsp:include>
</div>
<script type="text/javascript">

</script>
<div id="spin"></div> 
</body>
</html>