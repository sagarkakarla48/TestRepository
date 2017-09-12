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


<script>
$(document).ready(function() {
$("#spin").spinner();
});
</script>
<script type="text/javascript">
var prodList = "";
$(document).ready(function(){
	prodList = ${list};
	var data_one ="";
	$.each(prodList, function( index, productLilst ) {
		data_one = data_one+"<tr><td>"+productLilst.productId+"</td><td>"+productLilst.rackNum+"</td><td>"+productLilst.description+"</td><td>"+productLilst.superDescription+"</td><td>"+productLilst.pickOrder+"</td></tr>";
	    });
	$("#product_data").append(data_one);	
	highlightDuplicateRows("#duplicate_products");	
 })

function highlightDuplicateRows(selector) {
	var index = {},
    getText = function () {
        return $.trim( $(this).text() );
    };
    $(selector).find("tr").each(function (tr) {
        var rowKey = $(this).find("td").map(getText).toArray();
        if(rowKey.length>0){
        	if(rowKey[0].length<=0 || rowKey[1].length<=0 || rowKey[3].length<=0){
        		$(this).find("td").addClass("backgroundcolor");
        		$("#errMesgDiv_two").show();
        		$("#sub_button").attr("disabled","disabled");
        	}
        }
        rowKey=rowKey[1];
        if (index.hasOwnProperty(rowKey)) {
            index[rowKey].push(this);
        } else {
            index[rowKey] = [this];
        }
    });
    $.each(index, function (rowKey, rows) {
        $(rows).toggleClass("highlight", rows.length > 1);
        if(rows.length > 1){
        	$("#sub_button").attr("disabled","disabled");
        	$("#errMesgDiv_one").show();
        }
    });
    
} 

function reUploadForm(value){	
	if(value=="excel"){
		$("#homeform").attr("action","productsExcelUpload");
	}
	$("#homeform").submit();
}

function adminLogout(value){
	if(value=="Logout"){
		$("#homeform").attr("action","backTohomePage");
	}
	$("#homeform").submit();
}

var app = angular.module('prodcutApp', []);
app.controller('productCtrl', ['$scope','$http',function($scope,$http) {
	
	$scope.sendData = function() {	
		$("#spin").show();
	var response = $http.post('saveProducts', JSON.stringify(prodList));
	response.success(function(data, status, headers, config) {
		$("#spin").hide();
		if(data == null || data == ""){
			alertify.alert("","Some Orders are in Progress, Make Sure Orders Should be Complete.", function(){
				
				  });			
		}else{
			alertify.alert("","Products saved successfully.", function(){
				$("#homeform").attr("action","productsexcel");
				  $("#homeform").submit();
				  });			
		}
		
	});	
	response.error(function(data, status, headers, config) {
		$("#spin").hide();
		alert( "Exception details: " + JSON.stringify({data: data}));
	});
	}
	
	
}]);

</script>
<script type="text/javascript">
//override defaults for Alertify alert
alertify.defaults.transition = "slide";
alertify.defaults.theme.ok = "btn btn-primary";
alertify.defaults.theme.cancel = "btn btn-danger";
alertify.defaults.theme.input = "form-control";
</script>
<style>

.highlight {
  color: red;
    font-weight:bold;
}

.backgroundcolor{
background-color:orange;
}

.errMsg_one {
   color: red;
    font-weight:bold;
}

.errmsg_two {
   color: red;
    font-weight:bold;
}
/* .logout {
    padding: 5px 8px;
    font-size: 10px;
    border-radius: 5px;
    float : right;
}
 */
</style>

</head>


<body>
<div class="container" align="center"  data-ng-app="prodcutApp" id="prodcutCtrl" data-ng-controller="productCtrl">
<div>
<div class="well col-sm-12" style="margin-top:10px;">
<label style="font-size: 20px;">Uploaded Products</label>
<div style = "float:right">
<img height="25" width="25" onclick="adminLogout('Logout')"
					src="${contextpath}/resources/images/logout.png" title="Logout">
</div>
</div>
<div class="col-sm-12 well">
<form action="#" method="post" name="homeform" id="homeform">
<input type="hidden" id="temp">
<div class="col-sm-12" style="">
<div align="left" id="errMesgDiv_one" style="display:none;">
<label class="errMsg_one">Rack Number Should not be duplicate.Please Remove duplicates in Product-Rackid mapping sheet and Upload it Again </label>
</div>
<div align="left" id="errMesgDiv_two" style="display:none;">
<label class="errmsg_two">Product Id, Rack Number and Super Market Description Should Not be Empty </label>
</div>
<div class="table-responsive">
<table class="table" id="duplicate_products">
<thead>
<tr>
<th>Product Id</th>
<th>Rack Number</th>
<th>Description</th>
<th>Super Description</th>
<th>Pick Order</th>
</tr>
</thead>
<tbody id="product_data">

</tbody>
</table>
</div>
</div>
</form>
<div class="col-sm-12" style="">
<button class="addfields btn btn-primary" onclick="reUploadForm('excel');">Upload Product-Rack Excel</button>
<button class="addfields btn btn-primary" id="sub_button" data-ng-click="sendData()">Submit</button>
</div> 
</div>
</div>
</div>
<script type="text/javascript">

</script>
<div id="spin"></div>  
</body>
</html>