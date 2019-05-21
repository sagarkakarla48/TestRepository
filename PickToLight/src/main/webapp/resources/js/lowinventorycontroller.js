var app = angular.module('lowInventoryInfoApp', []);

app.controller('lowInventoryInfoCtrl', ['$scope','$http','$location',function($scope,$http,$location) {
	
	$scope.lowinventorylist=[];
	
	$scope.getLowInventoryInfo=function(){
	$("#spin").show();
	$http({
			method: 'POST',
			url: 'getLowInventoryInfo',
			headers: {
            	 'Content-Type': 'application/json'
           	}
		})
		.success(function (data, status, headers, config) {  
			$scope.lowinventorylist=data;
			$("#spin").hide();
		}).error(function (data, status, headers, config) {
			$scope.lowinventorylist=data;
			$("#spin").hide();
		alertify.alert("Somthing went wrong. Try again!",function(){
		});
		});
	}
	
	$scope.getLowInventoryInfo();
	
	$scope.updateLowInventory=function(){
		
		var count=0;
		$scope.updatedinventorylist=[];
		 $("input[class='lowInventoryClass']:checked").each(function(i){
			 $scope.lowinventorylist[$(this).val()].status="PLACED";
			 $scope.updatedinventorylist.push($scope.lowinventorylist[$(this).val()]);
			 count++;
        	 })
		if(count>0){
        	 $http({
     			method: 'POST',
     			url: 'updateLowInventoryInfo',
     			data : JSON.stringify($scope.updatedinventorylist),
     			headers: {
                 	 'Content-Type': 'application/json'
                	}
     		})
     		.success(function (data, status, headers, config) {  
     			$scope.getLowInventoryInfo();
     			$("#spin").hide();
     			alertify.alert("Inventory updated successfully!",function(){
         		});
     		}).error(function (data, status, headers, config) {
     			$("#spin").hide();
     		alertify.alert("Somthing went wrong. Try again!",function(){
     		});
     		});
		}else{
			alertify.alert("Please select atleast one product",function(){
     		});
		}
	}

}]);
