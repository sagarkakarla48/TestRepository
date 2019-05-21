<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextpath" value="<%=request.getContextPath()%>" scope="page" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Barcode Picker</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"	href="${contextpath}/resources/css/bootstrap.css">
<link rel="stylesheet"	href="${contextpath}/resources/css/productbarcode.css">
<link rel="stylesheet" href="${contextpath}/resources/css/alertify.css">
<!-- jQuery library -->
<script src="${contextpath}/resources/js/jquery-3.1.1.js"></script>
<!-- Latest compiled JavaScript -->
<script src="${contextpath}/resources/js/bootstrap.js"></script>
<script src="${contextpath}/resources/js/alertify.js"></script>
<style>
.file {
	visibility: hidden;
	position: absolute;
}
label{
font-size: 16px;
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
	$(document).ready(
			function() {
				$(document).on(
						'click',
						'.browse',
						function() {
							var file = $(this).parent().parent().parent().find(
									'.file');
							file.trigger('click');
						});
				$(document).on(
						'change',
						'.file',
						function() {
							$(this).parent().find('.form-control').val(
									$(this).val()
											.replace(/C:\\fakepath\\/i, ''));
						});
			});
	
	//apk file upload 
	function uploadAPK(){
		var file=$('#myFile').val();
		var version=$('#version').val();
		//checking the extension of file 
		if (!(/\.(apk)$/i).test(file)) {
			//if the file extension is not .apk
			alertify.alert("","Upload Valid .apk file");
			}else if(version==null || version.length<=0){
				alertify.alert("","Version Required");
			}else{
				//if file is .apk then upload the file
			   var formData = new FormData($("#homeform")[0]);
			    $.ajax({
			        url: "${pageContext.request.contextPath}/uploadApk",
			        type: 'POST',
			        data: formData,
			        async: false,
			        success: function (data) {
			        	if(data==="Version Not Valid"){
			        		alertify.alert("","New Version should be greater than the Existing Version, Please Change the Version and Submit Again");
			        	}else if(data==="SUCCESS"){
			        		alertify.alert("","APK File has been Uploaded Successfully", function(){
			        			$("#homeform").attr("action","selectUpload");
				        		$("#homeform").submit();
							  });			
			        	}else{
			        		alertify.alert("","APK File Not Uploaded, Please try Again");
			        	}
			        },
			        cache: false,
			        contentType: false,
			        processData: false
			    });

		}	
	}
	
	//logout functionality of admin
	function adminLogout(value){
		if(value=="back"){
			$("#homeform").attr("action","selectUpload");
		}else if(value=="Logout"){
			$("#homeform").attr("action","backTohomePage");
		}
		$("#homeform").submit();
	}
	
</script>
<script type="text/javascript">
// jQuery ".Class" SELECTOR.
$(document).ready(function() {
    $('#version').keypress(function (event) {
        return isNumber(event, this)
    });
});
// THE SCRIPT THAT CHECKS IF THE KEY PRESSED IS A NUMERIC OR DECIMAL VALUE.
function isNumber(evt, element) {

    var charCode = (evt.which) ? evt.which : event.keyCode

    if (
        (charCode != 46 || $(element).val().indexOf('.') != -1) &&      // “.” CHECK DOT, AND ONLY ONE.
        (charCode < 48 || charCode > 57))
        return false;

    return true;
}    
</script>
</head>
<body>
	<div class="container">
		<div align="center" class="well col-sm-12" style="margin-top: 10px;">
			<div class="col-sm-4"></div>
			<div class="col-sm-4" align="center">
				<label style="font-size: 28px;">Upload APK</label>
			</div>
			<div class="col-sm-4" align="right" style="font-size: 28px;">
				<img height="25" width="25" onclick="adminLogout('Logout')"
					src="${contextpath}/resources/images/logout.png" title="Logout">
			</div>
		</div>
		
			<form:form action="uploadApk" method="post" enctype="multipart/form-data" id = "homeform" name = "homeform" modelAttribute="apkdetails">
			<div class="well col-sm-12">
				<div class="col-sm-12">
				<div class="col-sm-4"></div>
				<div class="col-sm-4">
				<label class="col-sm-12" style="padding-left: 0px;">Choose APK File *</label>
					<div class="form-group" class="col-sm-12"  align="center">
						<input type="file" name="myFile" class="file">
						<div class="input-group">
							<input type="text" class="form-control input-lg" disabled
								placeholder="Upload apk file" id = "myFile" name = "myFile"> <span
								class="input-group-btn">
								<button class="browse btn btn-primary input-lg" type="button">
									Browse</button>
							</span>
						</div>
					</div>
				</div>
				<div class="col-sm-4"></div>
				</div>
				<div class="col-sm-12">
				<div class="col-sm-4"></div>
				<div class="col-sm-4">
					<div class="form-group">
						<label class="col-sm-12" style="padding-left: 0px;">Existing Version</label> 
						<form:input class="col-sm-12" type="text" path="existingversion"
							id="existingversion" disabled="true"></form:input>
					</div>
				</div>
				<div class="col-sm-4"></div></div>
				<div class="col-sm-12">
				<div class="col-sm-4"></div>
				<div class="col-sm-4">
					<div class="form-group">
						<label class="col-sm-12" style="padding-left: 0px;padding-top:10px;">New Version *</label> 
						<input class="col-sm-12" type="text" name="version"
							id="version" placeholder="Ex: 1.0" autocomplete="off">
					</div>
				</div>
				<div class="col-sm-4"></div></div>
				<div class="col-sm-12" align="center" style="margin-top: 10px;">
					<input type="button" name="back" class="btn btn-primary" value="Back" onclick="adminLogout('back')">
					<input type = "button" class="btn btn-primary" value = "Submit" onclick="uploadAPK()"></input>
				</div>
				<div class="col-sm-12" style="margin-top: 10px;">
			<p><b>Note : </b></p>
			<p>1. New version should be greater than the existing version.</p>
			<p>2. All * marked fields are mandatory.</p>
			</div>
				</div>
				
			</form:form>
		</div>
		<div class="col-sm-12" style="bottom:0 !important;position:fixed;padding-bottom:10px;">
<jsp:include page="Footer.jsp"></jsp:include>
</div>
</body>
</html>