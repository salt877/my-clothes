/**
 * 
 */

$(function() {

	// ブラウザ読み込み時のいいねチェック
	$.ajax({
		url : "/show_likes",
		type : "GET",
		async : true

	// 通信成功時の処理
	}).done(function(data) {
		// ログインユーザーIDとコーデIDでいいねの検索をかけ、各公開コーデにそのログインユーザーがいいねしていたら、ページの初期表示（いいねの色・ラジオボタンのチェック）を設定
		for(var like of data.likeList){
			$('.radio').each(function(i, o) {
				if(like.coordinateId == $(o).val()){
					$(this).next('.radio-img').addClass('checked').css('color', 'red');
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

	// いいねされた時（ラジオボタンがチェックされた時）の処理
	$('input:radio[name="radio"]').on('click', function() {
		var coordinateId = $(this).attr('id');
		var $likes = $(this).next('.radio-img').next('.counts');

		// すでにいいねされていた時の処理（ラジオボタンがチェックされていた時の処理）
		// いいね削除
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

		// いいねしていないときの処理
		// いいねする
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
			$(this).next('.radio-img').addClass('checked').css('color', 'red');
			$(this).prop('checked', true);

		}
	});
	
	// 詳細ボタンクリックイベント
	$('.pu_detail-btn').on('click', function() {
		let coordinateId = $(this).val();

		$.ajax({
			url : "/culc_publiccoordinate_price",
			type : "GET",
			data : {
				coordinateId : coordinateId
			},
			async : true

		// 通信成功時の処理
		}).done(function(data) {
			var totalPrice = data.totalPrice;
			$('.total-price').text(totalPrice.toLocaleString() + "円");

		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert("エラーが発生しました。");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("textStatus:" + textStatus);
			console.log("errorThrown" + errorThrown.message);

		});

	});

});
