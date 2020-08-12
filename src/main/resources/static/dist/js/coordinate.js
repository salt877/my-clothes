
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
			
			// モーダル内親要素<div>の中にある既存の<img>タグ等を削除
			// 親要素取得
			var parentDiv = document.getElementById('modal-img');
			// その中の子要素を全削除
			while(parentDiv.firstChild){
				parentDiv.removeChild(parentDiv.firstChild);	
			}
			
			// APIから取得したリスト分label/input/imgタグ生成
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
				newImg.setAttribute("src", clothes.imagePath);
				newImg.setAttribute("class", "radio-img");
				document.getElementById("modal-label" + clothes.id).appendChild(newImg);
			}
			
			// チェックされたradioのvalueを入れる変数checkedVal（radio選択解除の条件分岐にも使用）
			// チェックされた画像のsrcを入れる変数src
			// 動的にimgタグが作られる親要素取得
			// モーダル内選択ボタンをデフォルトで押下不可
			var checkedVal = 0; 
			var src;
			var $imgList = $('.modal-label');			
			$('#select-modal-btn').prop('disabled', true);	

			// モーダル内画像（ラジオボタン）をクリックしたときの処理
			$('input:radio[name="clothesId"]').off('click').on('click', function() {

				// 同じ画像をクリックした場合
				// トップス１と２で同じ画像が選択された際は、選択不可にする.
				// その画像のcheckedクラスを削除
				// ラジオボタンの選択解除
				// 選択ボタン押下不可
				if($(this).val() == checkedVal || $(this).val() == $('#modal-tops1').val() || $(this).val() == $('#modal-tops2').val()) {
					$imgList.find('img.radio-img.checked').removeClass('checked');
					$(this).prop('checked', false);
					$('#select-modal-btn').prop('disabled', true);	
					checkedVal = 0;
					
				// 異なる画像をクリックした場合
				// クリックされた画像にcheckedクラスを付与
				// ラジオボタンのチェックをtrueにする.
				// 選択ボタン押下不可解除
				} else {
					$imgList.find('img.radio-img.checked').removeClass('checked');
					$(this).next('.radio-img').addClass('checked');			
					$(this).prop('checked', true);
					$('#select-modal-btn').prop('disabled', false);		
					src = $(this).next('.radio-img').attr('src');		
					checkedVal = $(this).val();
				}


			});
				
				// 閉じるボタンを押したときの処理（選択したまま閉じた場合、check外す）
				// checkIdを初期化
				$('.close-modal').off('click').on('click', function(){
					$imgList.find('img.radio-img.checked').removeClass('checked');
					$('input[name=clothesId]').prop('checked', false);
					checkId = "";
				
				});
				


				// モーダル内の選択ボタンをクリックしたときの処理.
				// 登録確認モーダル内hiddenのvalueに選択された服IDを付与.
				// 登録確認モーダル＆コーディネート画面にチェックされた画像を表示.
				$('#select-modal-btn').off('click').on('click', function(){
						
					if(checkId === "fashion-accessories"){
						$('#modal-fashion-accessories').val(checkedVal);
						$("#drag-img1").attr("src",src);
						$("#in-modal-img1").attr("src",src);
					
					}else if(checkId === "tops1"){
						$('#modal-tops1').val(checkedVal);	
						$("#drag-img2").attr("src",src);
						$("#in-modal-img2").attr("src",src);
					
					}else if(checkId === "tops2"){
						$('#modal-tops2').val(checkedVal);	
						$("#drag-img3").attr("src",src);
						$("#in-modal-img3").attr("src",src);
					
					}else if(checkId === "outers"){
						$('#modal-outers').val(checkedVal);	
						$("#drag-img4").attr("src",src);
						$("#in-modal-img4").attr("src",src);
					
					}else if(checkId === "bottoms"){
						$('#modal-bottoms').val(checkedVal);	
						$("#drag-img5").attr("src",src);
						$("#in-modal-img5").attr("src",src);
					
					}else if(checkId === "shoes"){
						$('#modal-shoes').val(checkedVal);	
						$("#drag-img6").attr("src",src);
						$("#in-modal-img6").attr("src",src);
					
					}else if(checkId === "bag"){
						$('#modal-bag').val(checkedVal);	
						$("#drag-img7").attr("src",src);
						$("#in-modal-img7").attr("src",src);
					
					}else if(checkId === "dress"){
						$('#modal-dress').val(checkedVal);	
						$("#drag-img8").attr("src",src);
						$("#in-modal-img8").attr("src",src);	
					}	
				
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
	$('.drag-img').on('dblclick', function(){
				
		if($(this).attr('id') === "drag-img1"){
			$("#drag-img1").attr("src", "");
			$("#in-modal-img1").attr("src", "");
			$('#modal-fashion-accessories').val("");
		}
		
		if($(this).attr('id') === "drag-img2"){
			$("#drag-img2").attr("src", "");
			$("#in-modal-img2").attr("src", "");
			$('#modal-tops1').val("");
		}
		
		if($(this).attr('id') === "drag-img3"){
			$("#drag-img3").attr("src", "");
			$("#in-modal-img3").attr("src", "");
			$('#modal-tops2').val("");
		}
		
		if($(this).attr('id') === "drag-img4"){
			$("#drag-img4").attr("src", "");
			$("#in-modal-img4").attr("src", "");
			$('#modal-outers').val("");
		}
		
		if($(this).attr('id') === "drag-img5"){
			$("#drag-img5").attr("src", "");
			$("#in-modal-img5").attr("src", "");
			$('#modal-bottoms').val("");
		}
		
		if($(this).attr('id') === "drag-img6"){
			$("#drag-img6").attr("src", "");
			$("#in-modal-img6").attr("src", "");
			$('#modal-shoes').val("");
		}
		
		if($(this).attr('id') === "drag-img7"){
			$("#drag-img7").attr("src", "");
			$("#in-modal-img7").attr("src", "");
			$('#modal-bag').val("");
		}
		
		if($(this).attr('id') === "drag-img8"){
			$("#drag-img8").attr("src", "");
			$("#in-modal-img8").attr("src", "");
			$('#modal-dress').val("");
		}
		
		
	});
	
	// モバイル端末等でのダブルタップ処理
	$(".drag-img").data("dblTap",false).click(function(){
		if($(this).data("dblTap")){
			// ダブルタップ時の命令
			if($(this).attr('id') === "drag-img1"){
				$("#drag-img1").attr("src", "");
				$("#in-modal-img1").attr("src", "");
				$('#modal-fashion-accessories').val("");
			}
			
			if($(this).attr('id') === "drag-img2"){
				$("#drag-img2").attr("src", "");
				$("#in-modal-img2").attr("src", "");
				$('#modal-tops1').val("");
			}
			
			if($(this).attr('id') === "drag-img3"){
				$("#drag-img3").attr("src", "");
				$("#in-modal-img3").attr("src", "");
				$('#modal-tops2').val("");
			}
			
			if($(this).attr('id') === "drag-img4"){
				$("#drag-img4").attr("src", "");
				$("#in-modal-img4").attr("src", "");
				$('#modal-outers').val("");
			}
			
			if($(this).attr('id') === "drag-img5"){
				$("#drag-img5").attr("src", "");
				$("#in-modal-img5").attr("src", "");
				$('#modal-bottoms').val("");
			}
			
			if($(this).attr('id') === "drag-img6"){
				$("#drag-img6").attr("src", "");
				$("#in-modal-img6").attr("src", "");
				$('#modal-shoes').val("");
			}
			
			if($(this).attr('id') === "drag-img7"){
				$("#drag-img7").attr("src", "");
				$("#in-modal-img7").attr("src", "");
				$('#modal-bag').val("");
			}
			
			if($(this).attr('id') === "drag-img8"){
				$("#drag-img8").attr("src", "");
				$("#in-modal-img8").attr("src", "");
				$('#modal-dress').val("");
			}			
			
			$(this).data("dblTap",false);
		}else{
			$(this).data("dblTap",true);
		}
		setTimeout(function(){
			$(".drag-img").data("dblTap",false);
		},500);
	
	});

	
	// コーデ削除ボタンを押したときの処理
	$('.delete-btn').on('click', function(){
		
		var coordinateId = $(this).attr('id');
				
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
				window.location.href="http://localhost:8080/past-coordinate";
				
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
	
	// 確認モーダルのボタン押下処理
	// デフォルトは押下不可
	$('.coordinate-regis-btn').prop('disabled', true);
	$('small#conf-msg').hide();
	
	// 確認ボタンクリックイベント
	$('#co-confirmation-btn').on('click', function(){
		
		// hidden要素取得
		var hidden = document.getElementsByClassName('modal-input');
		// hiddenのvalueを格納する配列用意
		var hiddenVal = [];
		
		// hidden要素分回し、valueがある場合のみ配列に格納
		for(let i = 0; i < hidden.length; i++){
			if(hidden.item(i).value){
				hiddenVal.push(hidden.item(i).value);
			}				
		}

		// 確認モーダルを開いた時点でアイテム選択済＆コーデ名未入力の場合場合、押下不可
		if(hiddenVal.length > 0 && $('#code-name').val().length == 0){
			$('small#conf-msg').hide();
			$('.coordinate-regis-btn').prop('disabled', true);
		}
		
		
		// 確認モーダルを開いた時点でアイテム選択済＆コーデ名２０文字より多い場合、押下不可
		if(hiddenVal.length > 0 && $('#code-name').val().length > 20){
			$('small#conf-msg').hide();
			$('.coordinate-regis-btn').prop('disabled', true);
		}
		
				
		// 確認モーダルを開いた時点でアイテム未選択＆コーデ名２０文字より多い場合、押下不可
		if(hiddenVal.length == 0 && $('#code-name').val().length > 20){
			$('small#conf-msg').show();
			$('.coordinate-regis-btn').prop('disabled', true);
		}
		
		// 確認モーダルを開いた時点でアイテム選択済＆コーデ名が１文字以上２０文字以下で入力されている場合、押下可
		if(hiddenVal.length > 0 && $('#code-name').val().length > 0 && $('#code-name').val().length <= 20){
			$('small#conf-msg').hide();
			$('.coordinate-regis-btn').prop('disabled', false);
		}
		
		// コーデ名入力欄changeイベント
		$('#code-name').off('change').on('change', function() {
			// コーデ名入力済み＆アイテム未選択の場合、押下不可
		
			if($('#code-name').val().length > 0 && hiddenVal.length == 0){
				$('.coordinate-regis-btn').prop('disabled', true);
				
			}
						
			// コーデ名入力&アイテム選択済みの場合、押下可
			if ($('#code-name').val().length > 0 && $('#code-name').val().length <= 20 && hiddenVal.length > 0) {
				$('.coordinate-regis-btn').prop('disabled', false);
				
			
			}
			
			// コーデ名未入力&アイテム選択済みの場合、押下不可
			if($('#code-name').val().length == 0 && hiddenVal.length > 0){
				$('.coordinate-regis-btn').prop('disabled', true);
			}
			
			

		});
		
	});	
	
	
	
	
});
	