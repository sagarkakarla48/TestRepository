<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextpath" value="<%=request.getContextPath()%>" scope="page" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Upload Orders</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="${contextpath}/resources/css/font-awesome.css">
<link rel="stylesheet" href="${contextpath}/resources/css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="${contextpath}/resources/css/jquery-ui.min.css" />
<link rel="stylesheet" href="${contextpath}/resources/css/bootstrap.css">
<link rel="stylesheet" href="${contextpath}/resources/css/responsive.css">
<link rel="stylesheet" href="${contextpath}/resources/css/productbarcode.css">
<link rel="stylesheet" href="${contextpath}/resources/css/alertify.css">
<!-- jQuery library -->
<script type="text/javascript" src="${contextpath}/resources/js/jquery-3.1.1.js"></script>
<!-- Latest compiled JavaScript -->
<script type="text/javascript" src="${contextpath}/resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="${contextpath}/resources/js/jquery.babypaunch.spinner.min.js"></script>
<script src="${contextpath}/resources/js/bootstrap.js"></script>
<script src="${contextpath}/resources/js/alertify.js"></script>
<style>
 #spin{
z-index:10000 !important;
}
.file {
  visibility: hidden;
  position: absolute;
}
.btn{
font-size:20px;
}
</style>
<script>
$(document).ready(function() {
$("#spin").spinner();
});
</script>
<script type="text/javascript">
$(document).ready(function() {
	$(document).on('click', '.browse', function(){
		  var file = $(this).parent().parent().parent().find('.file');
		  file.trigger('click');
		});
		$(document).on('change', '.file', function(){
		  $(this).parent().find('.form-control').val($(this).val().replace(/C:\\fakepath\\/i, ''));
		});
});

function uploadOrdersExcel(){
	
	var file=$('#myFile').val();	
	if (!(/\.(xls|xlsx|csv)$/i).test(file)) {
		alertify.alert("","Upload Valid .xls or .xlsx or .csv file...", function(){
			
			  });		
		}else{		
			 $("#spin").show();
		   $("#orderExcelUploadForm").submit();		
		  
	}	
}
</script>
</head>
<body>
<div align="center" class="container">
<div class="well col-sm-12" style="margin-top:10px;">
<div class="col-sm-4"></div>
<div class="col-sm-4" align="center">
<label style="font-size: 28px;">Upload E-File</label>
</div>
<div class="col-sm-4" align="right" style="font-size: 28px;">
<a href="home">
<img height="35" width="35" src="${contextpath}/resources/images/homemenu.png" title = "Home" alt="Home">
</a>
</div>
</div>
<div class="well col-sm-12">
	<form action = "uploadOrdersFile" method = "post" enctype="multipart/form-data" id = "orderExcelUploadForm">
	<div class="col-sm-4"></div>
	<div class="col-sm-4" align="center">
	<div class="form-group">
    <input type="file" name="myFile" class="file">
    <div class="input-group">
      <input type="text" class="form-control input-lg" disabled placeholder="Upload E-File"  id = "myFile" name = "myFile">
      <span class="input-group-btn">
        <button class="browse btn btn-primary input-lg" type="button"> Browse</button>
      </span>
    </div>
  </div>
      </div>
      <div class="col-sm-4"></div>
      
      <div class="col-sm-12">
        <input type = "button" class="btn btn-primary" value = "Submit" onclick="uploadOrdersExcel()"></input>
      </div>
	</form>  
	</div>
</div>
<div class="col-sm-12" style="bottom:0 !important;position:fixed;padding-bottom:10px;">
<jsp:include page="Footer.jsp"></jsp:include>
</div>   
<div id="spin"></div>       
   </body>
</html>