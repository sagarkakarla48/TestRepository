/**
 * Checking for the duplicate data in the excel uploaded
 * If any cell content is duplicate then we will highlite its text with RED color
 * If any cell content is missing we will highlite its background with ORANGE
 * @param selector
 */
function highlightDuplicateRows(selector) {
    var index = {},
    getText = function () {
        return $.trim( $(this).text() );
    };
    $(selector).find("tr").each(function (tr) {
        var rowKey = $(this).find("td").map(getText).toArray();
        if(rowKey.length>0){
        	if(rowKey[0].length<=0 || rowKey[1].length<=0 || rowKey[2].length<=0 || rowKey[4].length<=0 || rowKey[5].length<=0){
        		//setting background color to the cells whose content is empty
        		$(this).find("td").addClass("backgroundcolor");
        		$("#errmsgdiv1").show();
        		$("#submitid").attr("disabled","disabled");
        	}
        }
        rowKey=rowKey[1];
        if (index.hasOwnProperty(rowKey)) {
            index[rowKey].push(this);
        } else {
            index[rowKey] = [this];
        }
    });
    //If any duplicate data occurs,disable the submit button
    $.each(index, function (rowKey, rows) {
        //$(rows).toggleClass("highlight", rows.length > 1);
        if(rows.length > 1){
        	//$("#submitid").attr("disabled","disabled");
        	//$("#errmsgdiv").show();
        }
    });
}

/**
 * uploading excel sheet on submit
 * @param value
 */
function submitform(value){
	if(value=="excel"){
		$("#homeform").attr("action","orderExcelUpload");
	}
	$("#homeform").submit();
}
