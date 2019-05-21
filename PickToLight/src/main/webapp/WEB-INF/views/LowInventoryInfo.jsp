<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextpath" value="<%=request.getContextPath()%>" scope="page" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="${contextpath}/resources/css/bootstrap.css">
<link rel="stylesheet" href="${contextpath}/resources/css/bootstrap.css.map">
<link rel="stylesheet" href="${contextpath}/resources/css/alertify.css">
<link rel="stylesheet" href="${contextpath}/resources/css/responsive.css">
<link rel="stylesheet" href="${contextpath}/resources/css/lowinventory.css">
<!-- jQuery library -->
<script src="${contextpath}/resources/js/jquery-3.1.1.js"></script>
<script type="text/javascript" src="${contextpath}/resources/js/jquery.babypaunch.spinner.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="${contextpath}/resources/js/bootstrap.js"></script>
<script src="${contextpath}/resources/js/angular.js"></script>
<script src="${contextpath}/resources/js/lowinventorycontroller.js"></script>
<script src="${contextpath}/resources/js/jquery.numeric.js"></script>
<script src="${contextpath}/resources/js/alertify.js"></script>
<style>
.btn{
padding :6px 12px !important;
}
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
	$("#spin").spinner();
});
</script>
</head>
<body ng-app="lowInventoryInfoApp" ng-controller="lowInventoryInfoCtrl">
<div class="container">
<div class="well col-sm-12" style="margin-top:10px;">
<div class="col-sm-4"></div>
<div class="col-sm-4" align="center">
<label style="font-size: 22px;">Low Inventory Info</label>
</div>
<div class="col-sm-4" align="right" style="font-size: 28px;">
<a href="home">
<img height="35" width="35" src="${contextpath}/resources/images/homemenu.png" title = "Home" alt="Home">
</a>
</div>
</div>
<div class="col-sm-12">
			<form name="uploadForm" id="uploadForm" action="#" method="post">
		  <div class="col-sm-4 heading" align = "center" style = "width:100%; font-size: 2em;padding-top:2%;">
				<!-- <span style = "color: #055671;margin-top:20px;">Approval Manager</span> -->
		  </div>

			<div class="tab-content">
				<div id="cegEmpData" class="tab-pane fade in active">
					<div class="col-sm-12" align="left" style="padding: 1%">
							<div class="col-sm-12" align="center">
								<!-- <span style="font-size: 20px; color: #02468E">Resource Approval Pending List</span> -->
							</div>
							<!-- <div class="col-sm-12" align="Right">
										<span class="fontclass">Search :</span>
										<select class="searchclass" ng-model="queryBy" style="height: 26px;" ng-change="clearValue();">
												<option value="primarySkill">Primary Skills</option>
												<option value="psNumber">PS Number</option>
												<option value="resourceName">Resource Name</option> 
												<option value="pmPSNumber">PM PS Number</option> 
												<option value="pmName">Requested By</option> 
												<option value="clientName">Client Name</option>
										</select>
										<input type="text" class="searchclass" placeholder="Search here" ng-model="query[queryBy]" />								
							</div> -->
						</div>
						<div class="col-sm-12 table-responsive">
						<table id="myTable" class="table">
							<thead style = "background-color:#4c79ab;color: white;">
								<tr>
									<th>S.No</th>
								    <th>Product</th>
								    <th>Rack Id</th>
									<th>Super Market</th>
									<th>Fill</th>
								</tr>
							</thead>
							<tbody>								
								<tr ng-repeat="product in lowinventorylist">
									<td>{{$index+1}}</td>
									<td>{{product.productName}}</td>
									<td>{{product.rackId}}</td>
									<td>{{product.superMarketDesc}}</td>	
									<td class="vcenter" style="">
									<input type="checkbox" id="checkbox{{$index}}" class="lowInventoryClass" value="{{$index}}"/>
									</td>
								</tr> 								
							</tbody>
						</table>
						</div>
						<div align="center">
						<button type="button" class="btn btn-primary btn-lg" ng-click="updateLowInventory();">Submit</button>
						</div>
				</div>					
				
			</div>
			</form>
		</div>
		<div id="spin"></div>
	</div>
	<div class="col-sm-12" style="bottom:0 !important;position:fixed;padding-bottom:10px;">
<jsp:include page="Footer.jsp"></jsp:include>
</div>
</body>
</html>