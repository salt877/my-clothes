/**
 * 
 */
$(function() {

	$.ajax({
		url : "/find_coordinate",
		type : "GET",
		async : true

	// 通信成功時の処理
	}).done(function(data) {
		
		$('.checkbox').each(function(i, o){
			for(var coordinate of data.coordinateListByUserId){
				if($(o).val() == coordinate.id && coordinate.isPublic == true){
					$(this).prop('checked', true);
			}
		}
			
		});
		

	}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
		alert("エラーが発生しました。");
		console.log("XMLHttpRequest:" + XMLHttpRequest.status);
		console.log("textStatus:" + textStatus);
		console.log("errorThrown" + errorThrown.message);

	});

	// listの個数を取得しておく
	var listContents = $("#past-coordinate-list .past-coordinates").length;
	$("#past-coordinate-list").each(
			function() {
				// 最初に表示させるアイテムの数
				var num = 6;
				// 最初はmoreボタン表示にし、
				$(this).find('#more_btn').show();
				$(this).find('#close_btn').hide();
				// 6番目まで表示
				$(this).find(".past-coordinates:not(:lt(" + num + "))").hide();

				if (listContents <= num) {
					$('#close_btn').hide();
					$('#more_btn').hide();
				}

				// moreボタンがクリックされた時
				$('#more_btn').click(
						function() {
							// numに+6ずつしていく = 6件ずつ追加する
							num += 6;
							$(this).parents('#past-coordinate-list').find(
									".past-coordinates:lt(" + num + ")")
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
													'#past-coordinate-list')
													.find(
															".past-coordinates:gt("
																	+ gtNum
																	+ ")")
													.slideUp();// 11以降は非表示にし、
											$(this).hide();// 閉じるボタンを非表示
											$('#more_btn').show();// moreボタン表示に
										});
							}
						});
			});

	// 詳細ボタンクリックイベント
	$('.detail-btn').on('click', function() {
		var coordinateId = $(this).val();

		$.ajax({
			url : "/culc_pastcoordinate_price",
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
		
		$('.checkbox').off('change').on('change', function(){
			alert("クリック");
			let isPublic = $(this).prop('checked');
			alert("コーデID:" + coordinateId);
			alert(isPublic + 'に変わる');
			
			$.ajax({
				url : "/update_isPublic",
				type : "GET",
				data : {
					coordinateId : coordinateId,
					isPublic : isPublic
				},
				async : true

				// 通信成功時の処理
				}).done(function() {
					alert("公開情報を変更しました。");
					

				}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
					alert("エラーが発生しました。");
					console.log("XMLHttpRequest:" + XMLHttpRequest.status);
	 				console.log("textStatus:" + textStatus);
	 				console.log("errorThrown" + errorThrown.message);

				});		
			
	 });
	});

	

	

});