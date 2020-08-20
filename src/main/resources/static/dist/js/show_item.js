$(function() {

	$('.category').hide();
	$('.brand').hide();
	$('.tag').hide();

	$('select').change(function() {

		// 入力したvalue値を変数に格納
		var val = $(this).val();

		if (val === 'category') {
			$('.category').show();
			$('.brand').hide();
			$('.tag').hide();
			console.log('categoryを選択中');

		}
		if (val === 'brand') {
			$('.brand').show();
			$('.category').hide();
			$('.tag').hide();
			console.log('brandを選択中');

		}
		if (val === 'tag') {
			$('.tag').show();
			$('.category').hide();
			$('.brand').hide();
			console.log('tagを選択中');

		}
		if (val === 'all-item') {
			$('.category').hide();
			$('.brand').hide();
			$('.tag').hide();
			console.log('アイテムを絞り込むを選択中');
			location.href = "http://localhost:8080/";
		}

	});

});