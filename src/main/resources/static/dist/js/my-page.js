$(function(){
	
	// トグルボタンと削除ボタンの表示・非表示設定
	$('.detail-btn').on('click',function(){
		var coordinateId = $(this).val();
		
		$.ajax({
			url : "/show_button",
			type : "GET",
			data : {
				coordinateId : coordinateId
			},
			async : true
			
		// 通信成功時の処理
		}).done(function(data){
			console.log(data);
			if(data === false){
				$('.like-item-frame').hide();
				$('.like-delete-btn').hide();
			} else if(data === true){
				$('.like-item-frame').show();
				$('.like-delete-btn').show();
			}
			
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert("エラーが発生しました。");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
				console.log("textStatus:" + textStatus);
				console.log("errorThrown" + errorThrown.message);

		});
	});
	
	// いいねされたコーデ一覧のもっと見る設定
	// listの個数を取得しておく
	var listContents = $("#liked-coordinate-list .liked-coordinates").length;
	$("#liked-coordinate-list").each(
			function() {
				// 最初に表示させるアイテムの数
				var num = 6;
				// 最初はmoreボタン表示にし、
				$(this).find('#liked_more_btn').show();
				$(this).find('#liked_close_btn').hide();
				// 6番目まで表示
				$(this).find(".liked-coordinates:not(:lt(" + num + "))").hide();

				if (listContents <= num) {
					$('#liked_close_btn').hide();
					$('#liked_more_btn').hide();
				}

				// moreボタンがクリックされた時
				$('#liked_more_btn').click(
						function() {
							// numに+6ずつしていく = 6件ずつ追加する
							num += 6;
							$(this).parents('#liked-coordinate-list').find(
									".liked-coordinates:lt(" + num + ")")
									.slideDown(); // スライドダウンさせる

							// listの個数よりnumが多い時、
							if (listContents <= num) {
								num = 6;// 「閉じる」がクリックされた後、表示させるアイテムの数
								gtNum = num - 1;
								$('#liked_close_btn').show();
								$('#liked_more_btn').hide();

								// 「閉じる」がクリックされたら、
								$('#liked_close_btn').click(
										function() {
											$(this).parents(
													'#liked-coordinate-list')
													.find(
															".liked-coordinates:gt("
																	+ gtNum
																	+ ")")
													.slideUp();// 11以降は非表示にし、
											$(this).hide();// 閉じるボタンを非表示
											$('#liked_more_btn').show();// moreボタン表示に
										});
							}
						});
			});
	
	// いいねしたコーデ一覧のもっと見る設定
	// listの個数を取得しておく
	var listContents = $("#like-coordinate-list .like-coordinates").length;
	$("#like-coordinate-list").each(
			function() {
				// 最初に表示させるアイテムの数
				var num = 6;
				// 最初はmoreボタン表示にし、
				$(this).find('#like_more_btn').show();
				$(this).find('#like_close_btn').hide();
				// 6番目まで表示
				$(this).find(".like-coordinates:not(:lt(" + num + "))").hide();

				if (listContents <= num) {
					$('#like_close_btn').hide();
					$('#like_more_btn').hide();
				}

				// moreボタンがクリックされた時
				$('#like_more_btn').click(
						function() {
							// numに+6ずつしていく = 6件ずつ追加する
							num += 6;
							$(this).parents('#like-coordinate-list').find(
									".like-coordinates:lt(" + num + ")")
									.slideDown(); // スライドダウンさせる

							// listの個数よりnumが多い時、
							if (listContents <= num) {
								num = 6;// 「閉じる」がクリックされた後、表示させるアイテムの数
								gtNum = num - 1;
								$('#like_close_btn').show();
								$('#like_more_btn').hide();

								// 「閉じる」がクリックされたら、
								$('#like_close_btn').click(
										function() {
											$(this).parents(
													'#like-coordinate-list')
													.find(
															".like-coordinates:gt("
																	+ gtNum
																	+ ")")
													.slideUp();// 11以降は非表示にし、
											$(this).hide();// 閉じるボタンを非表示
											$('#like_more_btn').show();// moreボタン表示に
										});
							}
						});
			});
});