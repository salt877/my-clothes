
$(function ($) {
	var token = $("input:hidden[name='_csrf']").val();
    var header = $("meta[name='_csrf_header']").attr("content");
    
    $(document).ajaxSend(function(e, xhr, options) {
      xhr.setRequestHeader(header, token);
    });


	$("#myqloId").on("keyup", function() {
		
		var myqloId = $("#myqloId").val();
		
		$.ajax({
			url : '/input_myqloId/myqloId_check',
			type : 'POST',
			data : {
				myqloId : myqloId
			},
			async : true
		}).done(function(data) {
			
			$("#duplicateMessage").html(data.duplicateMessage);
			
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus : " + textStatus);
			console.log("errorThrown : " + errorThrown.message);
		});
	});
});
