$(document).ready(function() {
	$(document).on('keydown', "input[type='text']",function (event) {
		// focus to next input when enter key pressed
		var count=0;
		if ( event.keyCode == 13) {
		    var obj = {};
		    var index =0;
		    var focuscheck=false;
		    $(".selector").each(function(){
		    if(obj.hasOwnProperty(this.value) && this.value !== "") {
		    	var currentval=this.value;
		    	
		    	Object.keys(obj).forEach(function(key,keyindex) {
		    		
		    		//checking for the duplicate entry of barcode
		    		if(key==currentval){
		    			alert(currentval+" product already there at S.No " + (keyindex+1));
		    			$("#barcode"+count).val(null);
		    			focuscheck=true;
		    			$('.inputs').eq(keyindex).focus();
		    		}
		    	});
		    } else {
		      obj[this.value] = this.value;
		    }
		    count++;
		   });
		    if(!focuscheck){
		    	index = $('.inputs').index(this) + 1;
		    	$('.inputs').eq(index).focus();
		    }
		}
	});
});
