
$(function(){
	
	// カテゴリーボタンがクリックされた時のajax通信処理
	$('.category-btn').on('click', function(){
		
		// カテゴリーごとの条件分岐のため、クリックされたボタンのdata-id属性取得
		var checkId = $(this).data('id');
		
		// APIへ飛ばすためのパラメータとして、クリックされたボタンのカテゴリーID取得
		var categoryId = $(this).val();

			$.ajax({
			url: "http://localhost:8080/show_clotheslist",
			type: "GET",
			dataType: "json",
			data: {
				categoryId: categoryId
			},
			async: true
			
		// 通信成功時の処理
		}).done(function(data){
			
			// モーダル内親要素<div>の中にある既存の<img>タグを削除
			// 親要素
			var parentDiv = document.getElementById('modal-img');
			// 子要素を全削除
			while(parentDiv.firstChild){
				parentDiv.removeChild(parentDiv.firstChild);	
			}
			
			// リスト分label/input/imgタグ生成
			for(let clothes of data.clothesList){
				var newLabel = document.createElement("label");
				newLabel.setAttribute("id", "modal-label" + clothes.id);
				newLabel.setAttribute("class", "modal-label");		
				document.getElementById("modal-img").appendChild(newLabel);
				
				var newInput = document.createElement("input");
				newInput.type = "radio";
				newInput.id = "modal-radio" + clothes.id;
				newInput.className = "radio";
				newInput.name = "clothesId";
				newInput.value = clothes.id;
				document.getElementById("modal-label"+ clothes.id).appendChild(newInput);
								
				var newImg = document.createElement("img");
				newImg.setAttribute("src", '/img/'+clothes.imagePath);
				newImg.setAttribute("class", "radio-img");
				document.getElementById("modal-label" + clothes.id).appendChild(newImg);
			}
			
			// 画像をクリックしたときの処理
			$('img.radio-img').on('click', function() {
				// クリックされた画像にcheckedクラスを付与・その画像についているラジオボタンのチェックをtrueにする.
				var $imgList = $('.modal-label');				  
				$imgList.find('img.radio-img.checked').removeClass('checked');
				$(this).prev('input:radio[name="clothesId"]').prop('checked',true);
				$(this).addClass('checked');			
			
				// チェックされたラジオボタンのvalue・imgのsrc取得
				var checkedVal = $('input[name="clothesId"]:checked').val();
				var src = $('img.radio-img.checked').attr('src');

			// モーダル内の選択ボタンをクリックしたときの処理.
			// hiddenのvalueに選択された服IDを付与.
			// 登録確認モーダル＆コーディネート画面にチェックされた画像を表示.
			$('#select-modal-btn').on('click', function(){
				
				if(checkId === "fashion-accessories"){
					$('#modal-fashion-accessories').val(checkedVal);
					$("#drag-img1").attr("src",src);
					$("#in-modal-img1").attr("src",src);
					
				}
					
				if(checkId === "tops1"){
					$('#modal-tops1').val(checkedVal);	
					$("#drag-img2").attr("src",src);
					$("#in-modal-img2").attr("src",src);
				}
				
					
				if(checkId === "tops2"){
					$('#modal-tops2').val(checkedVal);	
					$("#drag-img3").attr("src",src);
					$("#in-modal-img3").attr("src",src);
				}
				
				if(checkId === "outers"){
					$('#modal-outers').val(checkedVal);	
					$("#drag-img4").attr("src",src);
					$("#in-modal-img4").attr("src",src);
				}
				 
				if(checkId === "bottoms"){
					$('#modal-bottoms').val(checkedVal);	
					$("#drag-img5").attr("src",src);
					$("#in-modal-img5").attr("src",src);
				}
				
				if(checkId === "shoes"){
					$('#modal-shoes').val(checkedVal);	
					$("#drag-img6").attr("src",src);
					$("#in-modal-img6").attr("src",src);
				}
				
				if(checkId === "bag"){
					$('#modal-tops1').val(checkedVal);	
					$("#drag-img7").attr("src",src);
					$("#in-modal-img7").attr("src",src);
				}
				
				if(checkId === "dress"){
					$('#modal-dress').val(checkedVal);	
					$("#drag-img8").attr("src",src);
					$("#in-modal-img8").attr("src",src);	
				}
			});
		});
			
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました。");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("textStatus:" + textStatus);
			console.log("errorThrown" + errorThrown.message);
		
		});
	});
	
	// コーデエリア画像のダブルクリック処理
	// コーデエリア・確認モーダル内の画像削除・hiddenのvalue削除
	$('#drag-img1').on('dblclick', function(){
		$("#drag-img1").attr("src", "");
		$("#in-modal-img1").attr("src", "");
		$('#modal-fashion-accessories').val("");	
		
	});
	
	$('#drag-img2').on('dblclick', function(){
		$("#drag-img2").attr("src", "");
		$("#in-modal-img2").attr("src", "");
		$('##modal-tops1').val("");	
		
	});
	
	$('#drag-img3').on('dblclick', function(){
		$("#drag-img3").attr("src", "");
		$("#in-modal-img3").attr("src", "");
		$('##modal-tops2').val("");	
		
	});
	
	$('#drag-img4').on('dblclick', function(){
		$("#drag-img4").attr("src", "");
		$("#in-modal-img4").attr("src", "");
		$('##modal-outers').val("");	
		
	});
	
	$('#drag-img5').on('dblclick', function(){
		$("#drag-img5").attr("src", "");
		$("#in-modal-img5").attr("src", "");
		$('##modal-bottoms').val("");	
		
	});
	
	$('#drag-img6').on('dblclick', function(){
		$("#drag-img6").attr("src", "");
		$("#in-modal-img6").attr("src", "");
		$('##modal-shoes').val("");	
		
	});
	
	$('#drag-img7').on('dblclick', function(){
		$("#drag-img7").attr("src", "");
		$("#in-modal-img7").attr("src", "");
		$('##modal-bag').val("");	
		
	});
	
	$('#drag-img8').on('dblclick', function(){
		$("#drag-img8").attr("src", "");
		$("#in-modal-img8").attr("src", "");
		$('##modal-dress').val("");	
		
	});
	
	// コーデ削除ボタンを押したときの処理
	$('#co-delete-btn').on('click', function(){
		console.log($(this).val());
		
		var coordinateId = $('#co-delete-btn').val();
		console.log("コーデID" + coordinateId);
		
		if(confirm("このコーディネートを削除してもよろしいですか？")){
			$.ajax({
				url: "http://localhost:8080/delete_coordinate",
				type: "GET",
				data :{
					coordinateId : coordinateId
					},
				async: true
				
			// 通信成功時の処理
			}).done(function(){
				window.location.href="http://localhost:8080/coordinate";
				
			}).fail(function(XMLHttpRequest, textStatus, errorThrown){
				alert("エラーが発生しました。");
				console.log("XMLHttpRequest:" + XMLHttpRequest.status);
				console.log("textStatus:" + textStatus);
				console.log("errorThrown" + errorThrown.message);
			
		});	
			
		}else{
			return false;
			}
	});
	
});
	