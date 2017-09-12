<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextpath" value="<%=request.getContextPath()%>"
	scope="page" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Order History</title>
<!-- <link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon"> -->

<!-- css files -->
<link rel="stylesheet" href="${contextpath}/resources/css/font-awesome.css">
<link rel="stylesheet" href="${contextpath}/resources/css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="${contextpath}/resources/css/bootstrap.css">
<link rel="stylesheet" href="${contextpath}/resources/css/alertify.css">
<link rel="stylesheet" href="${contextpath}/resources/css/bootstrap-responsive-tabs.css">
<link rel="stylesheet" href="${contextpath}/resources/css/responsive.css">
<link rel="stylesheet" href="${contextpath}/resources/css/bootstrap-datetimepicker.min.css" />
<link rel="stylesheet" href="${contextpath}/resources/css/jquery-ui.min.css" />
<link rel="stylesheet" href="${contextpath}/resources/css/analytics.css" />
<!-- //css files -->
<!-- js -->
<script type="text/javascript" src="${contextpath}/resources/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="${contextpath}/resources/js/jquery.bootstrap-responsive-tabs.js"></script>
<script type="text/javascript" src="${contextpath}/resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${contextpath}/resources/js/moment.min.js"></script>
<script type="text/javascript" src="${contextpath}/resources/js/jquery.babypaunch.spinner.min.js"></script>
<script type="text/javascript" src="${contextpath}/resources/js/angular.js"></script>
<script type="text/javascript" src="${contextpath}/resources/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextpath}/resources/js/alertify.js"></script>
<script type="text/javascript" src="${contextpath}/resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="${contextpath}/resources/js/supermarketpickingtimecontroller.js"></script>




<!-- //js -->
<style>
 #spin{
z-index:100 !important;
}
#logestdatatable{
height:10%;

}
</style>
<script type="text/javascript">
var m = moment(new Date());

$(document).ready(function(){
	//---------------------loader-------------------------
	$("#spin").spinner();
	//---------------------end---------------------------
	$("#load").hide();
	$("#listnodata1").hide();
	$("#btn1").click(function(){
		$("#nodata1").show();
		$("#load").show();
		var date=$("#avgTimeId").val();
		if(date==""){
			$("#spin").hide();
			alert("Please select date");
		}
	});
	$("#btn2").click(function(){
		$("#longestlist").show();
		$("#nodata2").show();
		$("#listnodata1").show();
		var date=$("#longTimeId").val();
		if(date==""){
			$("#listnodata1").hide();
			$("#spin").hide();
			alert("Please select date");
		}
	});
	$("#btn3").click(function(){
		$("#nodata3").show();
		var date=$("#marketTimeId").val();
		if(date==""){
			$("#spin").hide();
			alert("Please select date");
		}
	});
//----------------------Date Field start-------------------------------------------------	
$('#datetimepicker1').datetimepicker({
	    ignoreReadonly: true,
	    format: 'YYYY/MM/DD',
	    allowInputToggle: true,
	    defaultDate: false
	  });
$('#datetimepicker2').datetimepicker({
	    ignoreReadonly: true,
	    format: 'YYYY/MM/DD',
	    allowInputToggle: true,
	    defaultDate: false
	  });
$('#datetimepicker3').datetimepicker({
	    ignoreReadonly: true,
	    format: 'YYYY/MM/DD',
	    allowInputToggle: true,
	    defaultDate: false
	  });	  
});
//---------------------------end---------------------------------------------------------
</script>
</head>
<body>
	<div class="container" data-ng-app="analyticsApp" data-ng-controller="analyticsCtrl">
	<div class="well col-sm-12" style="margin-top:10px;">
<div class="col-sm-4"></div>
<div class="col-sm-4" align="center">
<label style="font-size: 22px;">Order Picking Analytics</label>
</div>
<div class="col-sm-4" align="right" style="font-size: 28px;">
<a href="home">
<img height="35" width="35" src="${contextpath}/resources/images/homemenu.png" title = "Home" alt="Home">
</a>
</div>
</div>	
				
		<div class="col-sm-12 well" style="width:100%;">
			<form name="HistoryForm" id="HistoryForm" action="#" method="post">
				<ul class="nav nav-tabs responsive-tabs" id="myTab">
					<li class="active"><a data-toggle="tab" role="tab" href="#avgPickingData">Average Picking Time</a></li>
					<li><a data-toggle="tab" role="tab" href="#longestPickingTime">Longest picking of the day</a></li>
					<li><a data-toggle="tab" role="tab" href="#marketWisePicking">Supermarket wise picking of an order</a></li>
				</ul>

				<div class="tab-content">
					<div id="avgPickingData" class="tab-pane active">
					  <div class="row" align="center" style="margin-bottom: 20px;margin-top: 20px;">
					  <form id="dateform1">
					  <div class='col-sm-2'>
					       <h5>Select Date:</h5>
					  </div>
					   <div class='col-sm-2'>
                             <div class='input-group date' id='datetimepicker1'>
                                <input type='text' id="avgTimeId" name="avgTimeId" class="form-control" data-ng-model='pickDate1'/>
                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
                   				</span>
                  			 </div>
                	   </div>
                      <div class='col-sm-3'>
                              <button type="button" id="btn1" class="btn btn-primary" data-ng-click="getPickingDetailsForAverageTime()">Submit</button>
                      </div>
                      </form>
                   </div>  
                  
						<div class="col-sm-12 table-responsive"
							style="margin-bottom: 20px;align:center;">
							<table id="myTable" class="table table-hover table-condensed"
								style="overflow-x: auto !important" height="10%">
								<thead style = "background-color:#4c79ab">
								<tr>
									<th>S.No</th>
									<th>Total Orders</th>
									<th>Super Markets</th>
									<th>Average Time (HH:MM:SS)</th>
								</tr>
							</thead>
							<tbody>								
								<tr data-ng-repeat="details in averagePickingTime">
									<td>{{$index+1}}</td>
									<td>{{details.orders}}</td>
									<td>{{details.marketname}}</td>
									<td>{{details.time}}</td>
								</tr>
								<tr  data-ng-if="averagePickingTime == null || averagePickingTime.length === 0">
           						 	<td align="center" id="nodata1" colspan="4" style="display: table-cell;"> No Record Found</td>
           						</tr> 
							</tbody>
							</table>
						</div>
					</div>
					<div id="longestPickingTime" class="tab-pane">
					  <form id="dateform2">
				<div class="row" align="center" style="margin-bottom: 20px;margin-top: 20px;">
					  <div class='col-sm-2'>
					       <h5>Select Date:</h5>
					  </div>
					   <div class='col-sm-2'>
                             <div class='input-group date' id='datetimepicker2'>
                                <input type='text' id="longTimeId" name="longTimeId" data-ng-model='pickDate2' class="form-control" />
                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
                   				</span>
                  			 </div>
                	   </div>
                      <div class='col-sm-3'>
                              <button  id="btn2" type="button" class="btn btn-primary" data-ng-click="getLongestPickingofDay()">Submit</button>
                      </div>
                 </div>   
                      </form>
						<div class="col-sm-6 table-responsive" id="logestdatatable"
							style="margin-bottom: 20px;align:center;">
							<table id="myTable" class="table table-hover table-condensed"
								style="overflow-x: auto !important">
								<thead style = "background-color:#4c79ab">
								<tr>
									<th>S.No</th>
									<th>Order Id</th>
									<th>Super Market</th>
									<th>Highest Time (HH:MM:SS)</th>
								</tr>
							</thead>
							<tbody>								
								<tr data-ng-repeat="details in longestPicking">
									<td>{{$index+1}}</td>
									<td>{{details.orderid}}</td>
									<td>{{details.markets}}</td>
									<td>{{details.time}}</td>
								</tr>
								 <tr data-ng-if="longestPicking == null || longestPicking.length === 0">
                					<td align="center" id="nodata2" colspan="4" style="display: table-cell;"> No Record Found</td>
           						 </tr>															
							</tbody>
							</table>
						</div>
						<div class="well col-sm-12" id="longestlist" style="text-align: center;solid:1px;display:none;" data-ng-init="total=0">
						<p><b><i>Picking time of each Supermarket</i></b></p><div data-ng-repeat="longestdetails in longestPicking" style="border-bottom: 1px solid grey;text-align:left;"><h4>{{longestdetails.orderid}}</h4>
							<ul data-ng-repeat="details in longestSupermarketdata" style="display:inline;float:left; padding-left:10%;margin-top: 15px;" data-ng-if="details.orderid == longestdetails.orderid">
        							<!-- <li>{{details.orderid}}</li> -->
        							<li>{{details.market}}</li>
        							<li >{{details.time}}</li>
     						</ul>
     					</div>
     						<ul data-ng-repeat="details in longestPicking" style="margin-top: 15px;">
     							<li>Total time</li>
     							<li>{{details.time}}</li>
     						</ul>
     						<ul  data-ng-if="longestSupermarketdata == null || longestSupermarketdata.length === 0">
           						 	<li  id="listnodata1"> No Record Found</li>
           					</ul> 
						</div>
					</div>
					<div id="marketWisePicking" class="tab-pane">
			 <div class="row" align="center" style="margin-bottom: 20px;margin-top: 20px;">
					  <form id="dateform3">
					  <div class='col-sm-2'>
					       <h5>Select Date:</h5>
					  </div>
					   <div class='col-sm-2'>
                             <div class='input-group date' id='datetimepicker3'>
                               <input type='text' id="marketTimeId" name="marketTimeId" data-ng-model='pickDate3' class="form-control" />
                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
                   				</span>
                  			 </div>
                	   </div>
                      <div class='col-sm-3'>
                              <button id="btn3" type="button" class="btn btn-primary" data-ng-click="getPickingTimeDetails()">Submit</button>
                      </div>
                      <div class="col-sm-12 background" align="center" style="padding: 1%">
							<div class="col-sm-12" align="left" style="">
								<!-- <h1 style="font-size: 20px; color: #02468E">CEG Employee List</h1> -->
							</div>
							<div class="col-sm-12" align="Right">
										<span class="fontclass">Search :</span>
										<select class="searchclass" ng-model="queryBy" style="height: 26px;" ng-change="clearValue();">
												<option value="orderid">Order Id</option>
												<option value="market">Super Market</option>
												<!-- <option value="secondarySkill">Secondary Skills</option> -->
										</select>
										<input type="text" class="searchclass" placeholder="Search here" ng-model="query[queryBy]" />								
							</div>
						</div>
                      </form>
                   </div>   
						<div class="col-sm-12 table-responsive" style="margin-bottom:20px;">
						<table id="myTable" class="table table-hover table-condensed">
							<thead style = "background-color:#4c79ab">
								<tr>
									<th>S.No</th>
									<th>Order Id</th>
									<th>Super Market</th>
									<th>Time (HH:MM:SS)</th>
								</tr>
							</thead>
							<tbody>								
								<tr data-ng-repeat="details in orderMarketTime | filter:query">
									<td>{{$index+1}}</td>
									<td>{{details.orderid}}</td>
									<td>{{details.market}}</td>
									<td>{{details.time}}</td>
								</tr>
								 <tr  data-ng-if="orderMarketTime == null || orderMarketTime.length === 0">
                					<td align="center" id="nodata3" colspan="4" style="display: table-cell;"> No Record Found</td>
           						 </tr>																		
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</form>
		</div>
	</div>
	<div id="spin"></div>
</body>
</html>