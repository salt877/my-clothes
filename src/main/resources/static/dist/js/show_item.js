$(function(){
	
	$('.category').hide();
	$('.brand').hide();
	$('.tag').hide();
	
	$('select').change(function(){
		
		//入力したvalue値を変数に格納
		var val = $(this).val();
		
			if(val === 'category'){				
				$('.category').show();
				$('.brand').hide();
				$('.tag').hide();
				console.log('categoryを選択中');
			}  
			else if (val === 'brand'){
				$('.brand').show();
				$('.category').hide();
				$('.tag').hide();
				console.log('brandを選択中');
			} 
			else if (val == 'tag'){
				$('.tag').show();	
				$('.category').hide();
				$('.brand').hide();
				console.log('tagを選択中');
			}
		
	});
});