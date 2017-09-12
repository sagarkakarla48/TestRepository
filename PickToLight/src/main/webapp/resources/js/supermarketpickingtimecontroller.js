var app = angular.module('analyticsApp', []);

app.controller('analyticsCtrl', ['$scope','$http','$location',function($scope,$http,$location) {
	
	 $scope.query = {};
	 $scope.queryBy = '$';
	 
	$scope.orderMarketTime=[];
	$scope.longestPicking=[];
	$scope.averagePickingTime=[];
	$scope.longestSupermarketdata=[];
	var orderArr=[];
	$scope.getPickingTimeDetails=function(){
		$("#spin").show();
		var date= $("#marketTimeId").val();
		date=date.replace("/","-");
		date=date.replace("/","-");
        	 $http({
     			method: 'POST',
     			url: 'getPickingTimeDetails',
     			params :{
     				"date" :date
     			},
     			headers: {
                 	 'Content-Type': 'application/json'
                	}
     		})
     		.success(function (data, status, headers, config) {
     			$scope.orderMarketTime=[];
     			$scope.orderMarketTime=$scope.superMarketAnalytics(data);
     			$("#spin").hide();
     		}).error(function (data, status, headers, config) {
     			
     		});
	}
	
	//$scope.getPickingTimeDetails(null);
	
	$scope.superMarketAnalytics=function(data){
		
		var timeJsonArr=[];
		var marketArr=[];
		orderArr=[];
		var totalOrderMarketTime=[];
	
		angular.forEach(data, function(value, key) {
		var marketDesc=$.trim(value.superMarketDesc);
		
		if($.inArray(value.orderid, orderArr) == -1){
			orderArr.push(value.orderid);
		 }
		if($.inArray(marketDesc, marketArr) == -1){
			marketArr.push(marketDesc);
		 }
			
			var sec=null;
			var now  = value.pickEndTime;
			var then = value.pickStartTime;
	
			var time=moment.utc(moment(now, "YYYY-MM-DD HH:mm:ss").diff(moment(then, "YYYY-MM-DD HH:mm:ss"))).format("HH:mm:ss");
			sec=hmsToSeconds(time);
			var arrElement={
					"market" : marketDesc,
					"time" : sec,
					"product" : value.barcode,
					"orderid" : value.orderid
			}
			timeJsonArr.push(arrElement);
			
		});
		var orderTimeArr=[];
		angular.forEach(orderArr, function(orderid, key) {
			
			var orderMarket=[];
			
			angular.forEach(timeJsonArr, function(timeDetails, key) {
				var currentorderid=timeDetails.orderid;
				var market=timeDetails.market;
				if(orderid==currentorderid){
					if($.inArray(market, orderMarket) == -1){
						orderMarket.push(market);
					}
				}
			});
			
			angular.forEach(orderMarket, function(marketname, key) {
				
				var time=0;
				
				angular.forEach(timeJsonArr, function(timeDetails, key) {
					
					var currentorderid=timeDetails.orderid;
					var market=timeDetails.market;
					if(orderid==currentorderid && market==marketname){
						var currenttime=timeDetails.time;
						if(currenttime>time){
							time=currenttime
						}
					}
					
				});
				
				if(time!=0){
				var finalOrderMarketTimeDetails={
						"orderid":orderid,
						"market":marketname,
						"time":secondsTimeSpanToHMS(time)
				}
				totalOrderMarketTime.push(finalOrderMarketTimeDetails);
				}
				
			});
		

		});
		return totalOrderMarketTime;
	}
	
	$scope.getLongestPickingofDay=function(){
		$('#spin').show();
		var date= $("#longTimeId").val();
		date=date.replace("/","-");
		date=date.replace("/","-");
		 $http({
  			method: 'POST',
  			url: 'getPickingTimeDetails',
  			params :{
 				"date" :date
 			},
  			headers: {
              	 'Content-Type': 'application/json'
             	}
  		})
  		.success(function (data, status, headers, config) {  
  			$scope.longestSupermarketdata=[];
  			$scope.longestSupermarketdata=$scope.superMarketAnalytics(data);
  			$scope.findLongestPicking($scope.longestSupermarketdata);
  			$("#spin").hide();
  		}).error(function (data, status, headers, config) {
  			
  		});
		
	}
	
$scope.findLongestPicking=function(data){

	var time=0;
	var index=0;
	var orderWiseTimeArr=[];
	$scope.longestPicking=[];
	
	angular.forEach(orderArr, function(orderid, key) {
		var ordertime=0;
		var markets=null;
		angular.forEach(data, function(value, key) {
			var sec=hmsToSeconds(value.time);
			if(orderid==value.orderid){
				ordertime=ordertime+sec;
				if(markets==null){
					markets=value.market;
				}else{
					markets=markets+", "+value.market;
				}
			}
		});
		
		var orderTime={
				"orderid":orderid,
				"time" : secondsTimeSpanToHMS(ordertime),
				"markets" : markets
		}
		orderWiseTimeArr.push(orderTime);
	});
	
	angular.forEach(orderWiseTimeArr, function(value, key) {
			var sec=hmsToSeconds(value.time);
			if(sec>time){
				time=sec;
			}
		});

	angular.forEach(orderWiseTimeArr, function(value, key) {
		var sec=hmsToSeconds(value.time);
		if(sec==time){
			$scope.longestPicking.push(value);
		}
	});
	
	}

//$scope.getLongestPickingofDay();

$scope.getPickingDetailsForAverageTime=function(){
	$("#spin").show();
	var date= $("#avgTimeId").val();
	date=date.replace("/","-");
	date=date.replace("/","-");
	 $http({
			method: 'POST',
			url: 'getPickingTimeDetails',
			params :{
 				"date" :date
 			},
			headers: {
         	 'Content-Type': 'application/json'
        	}
		})
		.success(function (data, status, headers, config) { 
			$scope.findAveragePickingofDay($scope.superMarketAnalytics(data));
			$("#spin").hide();
		}).error(function (data, status, headers, config) {
			
		});
	
}

$scope.findAveragePickingofDay=function(data){

	var time=0;
	var totalmarketArray=[];
	var marketArray=[];
	$scope.averagePickingTime=[];
	var supermarketname;
angular.forEach(data, function(value, key) {
		var marketDesc=value.market;
		if($.inArray(marketDesc, marketArray) == -1){
			marketArray.push(marketDesc);
		}
	});
angular.forEach(marketArray, function(marketname, key) {
	 var orderCount=0;
	 supermarketname=marketname;
	 angular.forEach(data, function(value, key) {
		 if(supermarketname==value.market){
			 orderCount++;
			 var sec=parseInt(hmsToSeconds(value.time));
			 time=time+sec;
			 
		 }
	 });
	 if(time!=0){
			var avgTime=time/orderCount;
			var avgDetails={
					"orders" : orderCount,
					"marketname" : supermarketname,
					"time" : secondsTimeSpanToHMS(avgTime)
			}
			$scope.averagePickingTime.push(avgDetails);
		} 
 });
}
//$scope.getPickingDetailsForAverageTime();
function secondsTimeSpanToHMS(s) {
    var h = Math.floor(s/3600); //Get whole hours
    s -= h*3600;
    var m = Math.floor(s/60); //Get remaining minutes
    s -= m*60;
    return h+":"+(m < 10 ? '0'+m : m)+":"+(s < 10 ? '0'+s : s); //zero padding on minutes and seconds
}

function hmsToSeconds(s) {
	var timeArr=s.split(":");
    return parseInt(timeArr[0])*60*60+parseInt(timeArr[1])*60+parseInt(timeArr[2]);
}

$scope.clearValue=function(){
	$scope.query = {};
}
}]);
