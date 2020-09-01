/**
 * 
 */

$(function() {

	var checkVal = 0;

	$('input:radio[name="radio"]').on('click', function() {
		var coordinateId = $(this).attr('id');
		var $likes = $(this).next('.radio-img').next('.counts');
		var $imgList = $('.modal-label');

		if ($(this).val() == checkVal) {

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
				alert("エラーが発生しました。");
				console.log("XMLHttpRequest:" + XMLHttpRequest.status);
				console.log("textStatus:" + textStatus);
				console.log("errorThrown" + errorThrown.message);

			});

			$imgList.find('img.radio-img.checked').removeClass('checked');
			$(this).prop('checked', false);
			checkVal = 0;

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
				alert("エラーが発生しました。");
				console.log("XMLHttpRequest:" + XMLHttpRequest.status);
				console.log("textStatus:" + textStatus);
				console.log("errorThrown" + errorThrown.message);

			});

			$imgList.find('img.radio-img.checked').removeClass('checked');
			$(this).next('.radio-img').addClass('checked');
			$(this).prop('checked', true);
			checkVal = $(this).val();

		}
	});

});
