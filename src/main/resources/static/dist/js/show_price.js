$(function(){
	$('.fc-button-primary').click(
		function(){
			$.ajax({
				url: "/show/perchase/data",
				type: "GET",
				dataType:"json",
				data : {
					userId : $('#loginUserId').val(),
					perchaseDate :$('.fc-toolbar-title').text()
				},
				async: true	
			}).done(function(data){
				var totalPrice = data.totalPrice;
				$('.total_price').text(totalPrice.toLocaleString()+" 円");
				
				var priceAverage = data.priceAverage;
				$('.price_average').text(priceAverage.toLocaleString()+" 円");
				
				var itemQuantity = data.itemQuantity;
				$('.item_quantity').text(itemQuantity.toLocaleString()+" 点");
				
				var month = data.month;
	
				$('.month_total_price').html(month.toLocaleString()+"月の<br>購入金額合計");
				$('.month_price_average').html(month.toLocaleString()+"月の<br>平均購入金額");
				$('.month_item_quantity').html(month.toLocaleString()+"月の<br>購入アイテム数");
				
				
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
	
