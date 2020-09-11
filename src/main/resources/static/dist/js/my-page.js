$(function() {

	// もっと見る設定
	// listの個数を取得しておく
	var listContents = $("#liked-coordinate-list .liked-coordinates").length;
	$("#liked-coordinate-list").each(
			function() {
				// 最初に表示させるアイテムの数
				var num = 6;
				// 最初はmoreボタン表示にし、
				$(this).find('#more_btn').show();
				$(this).find('#close_btn').hide();
				// 6番目まで表示
				$(this).find(".liked-coordinates:not(:lt(" + num + "))").hide();

				if (listContents <= num) {
					$('#close_btn').hide();
					$('#more_btn').hide();
				}

				// moreボタンがクリックされた時
				$('#more_btn').click(
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
								$('#close_btn').show();
								$('#more_btn').hide();

								// 「閉じる」がクリックされたら、
								$('#close_btn').click(
										function() {
											$(this).parents(
													'#liked-coordinate-list')
													.find(
															".liked-coordinates:gt("
																	+ gtNum
																	+ ")")
													.slideUp();// 11以降は非表示にし、
											$(this).hide();// 閉じるボタンを非表示
											$('#more_btn').show();// moreボタン表示に
										});
							}
						});
			});
		
});