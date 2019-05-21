<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextpath" value="<%=request.getContextPath()%>" scope="page" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Barcode Picker</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="${contextpath}/resources/css/bootstrap.css">
<link rel="stylesheet" href="${contextpath}/resources/css/bootstrap.css.map">
<link rel="stylesheet" href="${contextpath}/resources/css/productbarcode.css">
<link rel="stylesheet" href="${contextpath}/resources/css/alertify.css">
<!-- jQuery library -->
<script src="${contextpath}/resources/js/jquery-3.1.1.js"></script>

<!-- Latest compiled JavaScript -->
<script src="${contextpath}/resources/js/bootstrap.js"></script>
<script src="${contextpath}/resources/js/angular.js"></script>
<script src="${contextpath}/resources/js/dynamicformcontroller.js"></script>
<script src="${contextpath}/resources/js/productbarcode.js"></script>
<script src="${contextpath}/resources/js/jquery.numeric.js"></script>
<script src="${contextpath}/resources/js/alertify.js"></script>
<style>
</style>
<script type="text/javascript">
//override defaults for Alertify alert
alertify.defaults.transition = "slide";
alertify.defaults.theme.ok = "btn btn-primary";
alertify.defaults.theme.cancel = "btn btn-danger";
alertify.defaults.theme.input = "form-control";
</script>
<script type="text/javascript">
$(document).ready(function(){
	
	// this is for gtting color to ssign order 
	$(".numeric").numeric();
	
	function randomString(length, chars) {	  
		var result = '';
	    for (var i = length; i > 0; --i) result += chars[Math.round(Math.random() * (chars.length - 1))];
	    return result;
	}
	var orderid = randomString(10, '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ');
	$('#orderid').val(orderid);
	
	$.ajax({
		 type : "GET",
		 url : "${pageContext.request.contextPath}/getcolor",
		 contentType: "application/json",
	     dataType: "json",
	      success : function(data) {
	    	  //checking for the availability of colors
	    	  if(data!=null){
	    		  var color=data;
	    		  //setting the color as background ti the division
	    			$(".foo").css("background-color",color);
			    	$('#color').val(color);
	    		 
	    	  }else{
	    		  //If no color available we will show this message
	    		  alertify.alert("","Your order limit completed.", function(){
    				  $("#productnumberform").submit();
    				  });
	    	  }
	    }
	});
	});
</script>
<script>
function generateFields(){
	
	//calling angular function to add fields based on products number
	angular.element('#MainCtrl').scope().addBarcodeFields('${number}');
}
</script>
</head>
<body>
<div class="container" align="center">
<div data-ng-app="angularjs-starter" id="MainCtrl" data-ng-controller="MainCtrl">
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
 <div class="col-sm-12 well" >
 <div align="right">
 <p style = "float : left; text : bold"><b>Order Id :</b><input type="text" data-ng-model="orderid" name="orderid" id="orderid" style = "font-weight:bold ;" readonly></p>
 <label class="foo"></label>
 </div>
 <form id="productnumberform" action="orderedProductsNumber" method="post"></form>
 <form name="productform" novalidate>
 <div>
 <input type="text" style="display:none;" data-ng-model="color" name="color" id="color">
 </div>
<table class="table" class="col-sm-12">
<thead>
      <tr>
        <th>S.No</th>
        <th>Barcode</th>
        <th>Quantity</th>
        <th>Delete</th>
      </tr>
    </thead>
    <tbody>
      <tr  data-ng-repeat="choice in choices" >
      <td>{{$index+1}}
      </td>
   <td>
      <input type="text" class="focusnextfield inputs selector" data-ng-model="choice.barcode" name="barcode{{$index}}" id="barcode{{$index}}" placeholder="Enter Barcode" required>
      <p class="help-block red" data-ng-show="productform.barcode{{$index}}.$invalid && productform.barcode{{$index}}.$touched">This Field is Required</p>
      </td>
      <td>
      <input type="text" class="focusnextfield inputs numeric" data-ng-model="choice.quantity" name="quantity{{$index}}" id="quantity{{$index}}" placeholder="Enter Quantity" required>
      <p class="help-block red" data-ng-show="productform.quantity{{$index}}.$invalid && productform.quantity{{$index}}.$touched">This Field is Required</p>
      </td>
      <td>
      <input type="button" class="remove" data-ng-show="true" value="-" data-ng-click="removeChoice($index)">
      </td>
   </tr>
    </tbody>
</table>
</form>
</div> 
<button class="addfields btn btn-primary" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo">Add More Products</button>
<button data-ng-disabled="productform.$invalid" id="addinput" class="addfields btn btn-primary" data-ng-click="sendDataToServer()">Submit Order</button>
</div>
<div class="bd-example">
  <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
          <h4 class="modal-title" id="exampleModalLabel">Add More Products</h4>
        </div>
        <div class="modal-body">
          <form>
            <div class="form-group" align="left">
              <label for="recipient-name" class="form-control-label">Enter Products Number</label>
              <input type="text" class="form-control" name="number" id="number" data-ng-model="number" placeholder="Enter Products Number">
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" data-ng-click="addFields()" data-dismiss="modal">Submit</button>
        </div>
      </div>
    </div>
  </div>
</div>
</div>
</div>
<div class="col-sm-12" style="bottom:0 !important;position:fixed;padding-bottom:10px;">
<jsp:include page="Footer.jsp"></jsp:include>
</div>
</body>
</html>