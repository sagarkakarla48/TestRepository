var app = angular.module('angularjs-starter', []);

app.controller('MainCtrl', ['$scope','$http','$location',function($scope,$http,$location) {
	
$scope.choices = [];
$scope.number;
//adding input fields dynamically based on the number entered by user
$scope.addFields = function() {
	
	  for(var i=$scope.number;i>0;i--){
		  $scope.choices.push({});
	  }
	  $scope.number="";  
};
	  
	  
//to add input fields based on products	
	  
$scope.addBarcodeFields=function(number){
		  for(var i=number;i>0;i--){
			    $scope.choices.push({});
			    
		  }
	  }


//to remove input fields

$scope.removeChoice = function(id) {
	if($scope.choices.length>1){
		$scope.choices.splice(id,1);
	}
};


//to add input fields based on number of products in order initially

generateFields();

//to save order
$scope.sendDataToServer = function() {
	var color=$('#color').val();
	var orderid = $('#orderid').val();
	var ddata=$scope.choices;
	$scope.formData = [];
	
	for(var i = 0; i < ddata.length; i++) {
	    var data= ddata[i];
	    
	  //create JSON object which holds product details
	    
	    var myObj = {
	        "barcode" : data.barcode,    
	        "quantity" : data.quantity,	
	        "orderid" : orderid,
	        "color": color
	    };
	    
	    //push the object to your array
	    
	    $scope.formData.push( myObj );
	}
	
	//sending form data to controller to save in Database
    var response = $http.post('productsSave', $scope.formData);
    
	response.success(function(data, status, headers, config) {
		if(data!=null && data!=""){
		var msg="";
		$.each(data,function(i,productsbean){
			if(i==0){
				msg=msg+"<b>"+productsbean.barcode+"</b>";
			}else if(i!=(data.length-1)){
				msg=msg+", <b>"+productsbean.barcode+"</b>";
			}else{
				msg=msg+" and <b>"+productsbean.barcode+"</b>";
			}
		})
		//checking for the wrong barcode which is not in the product list
		if(data.length>1){
			//if the product is not there in the product list
			msg=msg+" are not available. Remove these products and submit order again.";
		}else{
			msg=msg+" is not available. Remove this product and submit order again.";
		}
		
		alertify.alert("",msg);
		
		}else{
			//If the product is there in the product list we will save the details
			alertify.alert("","Order saved successfully.", function(){
				  $("#productnumberform").submit();
				  });
		}
		
	});
	
	response.error(function(data, status, headers, config) {
		alert( "Exception details: " + JSON.stringify({data: data}));
	});
	
};
}]);
