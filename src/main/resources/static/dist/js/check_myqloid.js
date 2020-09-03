$(function() {
	$("#myqloId").on("keyup", function() {
		
		$.ajax({
			url : "/myqloId_check",
			type : 'POST',
			dataType : 'json',
			data : {
				myqloId : $("#myqloId").val()
			},
			async : true
		}).done(function(data) {
			console.log(data);
			$("#duplicateMessage").html(data.duplicateMessage);
			
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert("エラーが発生しました!");
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus : " + textStatus);
			console.log("errorThrown : " + errorThrown.message);
		});
	});
});