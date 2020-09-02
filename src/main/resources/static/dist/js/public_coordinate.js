/**
 * 
 */

$(function() {

	$.ajax({
		url : "/show_likes",
		type : "GET",
		async : true

	// 通信成功時の処理
	}).done(function(data) {
		for(var like of data.likeList){
			$('.radio').each(function(i, o) {
				if(like.coordinateId == $(o).val()){
					$(this).next('.radio-img').addClass('checked');
					$(this).next('.radio-img').css('color', 'red');
					$(this).prop('checked', true);
					
				}
			});			
			
		}

	}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
		alert("ブラウザを再読み込みしてください。");
		console.log("XMLHttpRequest:" + XMLHttpRequest.status);
		console.log("textStatus:" + textStatus);
		console.log("errorThrown" + errorThrown.message);

	});

	$('input:radio[name="radio"]').on('click', function() {
		var coordinateId = $(this).attr('id');
		var $likes = $(this).next('.radio-img').next('.counts');

		if ($(this).next().hasClass('checked')) {

			$.ajax({
				url : "/deleteLike",
				type : "GET",
				data : {
					coordinateId : coordinateId
				},
				async : true

			// 通信成功時の処理
			}).done(function(data) {
				var likes = data.likes;
				$likes.text(likes);

			}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
				alert("ブラウザを再読み込みしてください。");
				console.log("XMLHttpRequest:" + XMLHttpRequest.status);
				console.log("textStatus:" + textStatus);
				console.log("errorThrown" + errorThrown.message);

			});

			$(this).parent().find('.radio-img.checked').removeClass('checked');
			$(this).next('.radio-img').css('color', 'black');
			$(this).prop('checked', false);

		} else {

			$.ajax({
				url : "/like",
				type : "GET",
				data : {
					coordinateId : coordinateId
				},
				async : true

			// 通信成功時の処理
			}).done(function(data) {
				var likes = data.likes;
				$likes.text(likes);

			}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
				alert("ブラウザを再読み込みしてください。");
				console.log("XMLHttpRequest:" + XMLHttpRequest.status);
				console.log("textStatus:" + textStatus);
				console.log("errorThrown" + errorThrown.message);

			});

			$(this).parent().find('.radio-img.checked').removeClass('checked');
			$(this).next('.radio-img').addClass('checked');
			$(this).next('.radio-img').css('color', 'red');
			$(this).prop('checked', true);

		}
	});

});
