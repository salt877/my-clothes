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

		}
		if (val === 'brand') {
			$('.brand').show();
			$('.category').hide();
			$('.tag').hide();

		}
		if (val === 'tag') {
			$('.tag').show();
			$('.category').hide();
			$('.brand').hide();

		}
		if (val === 'all-item') {
			$('.category').hide();
			$('.brand').hide();
			$('.tag').hide();
			location.href = "http://localhost:8080/";
		}

	});

});