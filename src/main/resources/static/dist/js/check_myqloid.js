
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
			
			var message = data.duplicateMessage;
			console.log(message);
			var mark = document.createElement("span");
			var newContent = document.createTextNode("");
			mark.appendChild(newContent);
			
			if (message == '使用できます'){
				mark.setAttribute("class","checkmark001");
				$("#duplicateMessage").html(data.duplicateMessage);
				var parent = document.getElementById("duplicateMessage");
				parent.insertBefore(mark,parent.firstChild);
				parent.style.color = "green";
				
			}  else {
				$("#duplicateMessage").html(data.duplicateMessage);
				var parent = document.getElementById("duplicateMessage");
				parent.insertBefore(mark,parent.firstChild);
				parent.style.color = "red";
			}
			
			
			
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus : " + textStatus);
			console.log("errorThrown : " + errorThrown.message);
		});
	});
});
