/**
 * 
 */

// 詳細ボタンクリックイベント
$('.detail-btn').on('click', function() {
	var coordinateId = $(this).val();

	// コーデ合計金額計算処理
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

});