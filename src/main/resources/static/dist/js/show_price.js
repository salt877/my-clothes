$(function(){
	$('.fc-button-primary').click(
		function(){
			$.ajax({
				url: "http://localhost:8080/show/perchase/data",
				type: "GET",
				dataType:"json",
				data : {
					userId : $('#loginUserId').val(),
					perchaseDate :$('.fc-toolbar-title').text()
				},
				async: true	
			}).done(function(data){
				console.log("成功です！！");
				var totalPrice = data.totalPrice;
				$('.total_price').text("¥"+totalPrice.toLocaleString());
				
				var priceAverage = data.priceAverage;
				$('.price_average').text("¥"+priceAverage.toLocaleString());
				
				var itemQuantity = data.itemQuantity;
				$('.item_quantity').text(itemQuantity.toLocaleString()+"点");
				
				var month = data.month;
				$('.month_total_price').text(month.toLocaleString()+"月の購入金額合計");
				$('.month_price_average').text(month.toLocaleString()+"月の平均購入金額");
				$('.month_item_quantity').text(month.toLocaleString()+"月の購入アイテム数");
				
				
			}).fail(function(XMLHttpRequest, textStatus, errorThrown){
				// HTTPリクエストのステータス情報 
				console.log("XMLHttpRequest : " + XMLHttpRequest.status);
				// エラー情報
				console.log("textStatus     : " + textStatus);
				// 例外情報
				console.log("errorThrown    : " + errorThrown.message);
			}).always(function(data){
			});
		});
});
	
