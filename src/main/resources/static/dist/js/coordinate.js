
$(function(){
	
// ファッション雑貨
	$('#fashion-accessories-btn').on('click', function(){
		$.ajax({
			url: "http://localhost:8080/show_clotheslist",
			type: "GET",
			dataType: "json",
			data: {
				categoryId: $('#fashion-accessories-btn').val()
			},
			async: true
		
		}).done(function(data){
			// リストを回して画像表示させる前に、親要素<div>の中にある既存の<img>タグを削除します.
			// 親要素
			var parentDiv = document.getElementById('modal-img');
			// 子要素を全削除
			while(parentDiv.firstChild){
				parentDiv.removeChild(parentDiv.firstChild);
				
			}
			
	
			// <div>タグの中の子要素としてリストの中身分<img>タグを生成します.
			for(let clothes of data.clothesList){
				var newLabel = document.createElement("label");
				newLabel.setAttribute("id", "modal-label" + clothes.id);
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
			
			$('#select-modal-btn').on('click', function(){
				let radioVal = $('input[name="clothesId"]:checked').val();
				console.log(radioVal);
				$('#fashion_accessories').val(radioVal);
				
			});
			
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました。");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("textStatus:" + textStatus);
			console.log("errorThrown" + errorThrown.message);
		
		});
		});
	
// トップス１
	$('#tops1-btn').on('click', function(){
		$.ajax({
			url: "http://localhost:8080/show_clotheslist",
			type: "GET",
			dataType: "json",
			data: {
				categoryId: $('#tops1-btn').val()
			},
			async: true
		
		}).done(function(data){
			
			// リストを回して画像表示させる前に、親要素<div>の中にある既存の<img>タグを削除します.
			// 親要素
			var parentDiv = document.getElementById('modal-img');
			// 子要素
			while(parentDiv.firstChild){
				parentDiv.removeChild( parentDiv.firstChild );
				
			}
			
			
			// <div>タグの中の子要素としてリストの中身分<img>タグを生成します.
			for(let clothes of data.clothesList){
				var newLabel = document.createElement("label");
				newLabel.setAttribute("id", "modal-label" + clothes.id);
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
			
			$('#select-modal-btn').on('click', function(){
				let radioVal = $('input[name="clothesId"]:checked').val();
				$('#tops1').val(radioVal);	
				
			});
			
					
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました。");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("textStatus:" + textStatus);
			console.log("errorThrown" + errorThrown.message);
		
		});
		});
	
// トップス２
	$('#tops2-btn').on('click', function(){
		$.ajax({
			url: "http://localhost:8080/show_clotheslist",
			type: "GET",
			dataType: "json",
			data: {
				categoryId: $('#tops2-btn').val()
			},
			async: true
		
		}).done(function(data){
			// リストを回して画像表示させる前に、親要素<div>の中にある既存の<img>タグを削除します.
			// 親要素
			var parentDiv = document.getElementById('modal-img');
			// 子要素を全削除
			while(parentDiv.firstChild){
				parentDiv.removeChild( parentDiv.firstChild );
				
			}
			
			
			for(let clothes of data.clothesList){
				var newLabel = document.createElement("label");
				newLabel.setAttribute("id", "modal-label" + clothes.id);
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
			
			$('#select-modal-btn').on('click', function(){
				let radioVal = $('input[name="clothesId"]:checked').val();
				console.log(radioVal);
				$('#tops2').val(radioVal);	
				
			});
			
			
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました。");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("textStatus:" + textStatus);
			console.log("errorThrown" + errorThrown.message);
		
		});
		});

// アウター
	$('#outers-btn').on('click', function(){
		$.ajax({
			url: "http://localhost:8080/show_clotheslist",
			type: "GET",
			dataType: "json",
			data: {
				categoryId: $('#outers-btn').val()
			},
			async: true
		
		}).done(function(data){
			// リストを回して画像表示させる前に、親要素<div>の中にある既存の<img>タグを削除します.
			// 親要素
			var parentDiv = document.getElementById('modal-img');
			// 子要素を全削除
			while(parentDiv.firstChild){
				parentDiv.removeChild( parentDiv.firstChild );
				
			}
						
			for(let clothes of data.clothesList){
				var newLabel = document.createElement("label");
				newLabel.setAttribute("id", "modal-label" + clothes.id);
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
			
			$('#select-modal-btn').on('click', function(){
				let radioVal = $('input[name="clothesId"]:checked').val();
				console.log(radioVal);
				$('#outer').val(radioVal);	
				
			});
			
			
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました。");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("textStatus:" + textStatus);
			console.log("errorThrown" + errorThrown.message);
		
		});
		});
	
// ボトムス
	$('#bottoms-btn').on('click', function(){
		$.ajax({
			url: "http://localhost:8080/show_clotheslist",
			type: "GET",
			dataType: "json",
			data: {
				categoryId: $('#bottoms-btn').val()
			},
			async: true
		
		}).done(function(data){
			// リストを回して画像表示させる前に、親要素<div>の中にある既存の<img>タグを削除します.
			// 親要素
			var parentDiv = document.getElementById('modal-img');
			// 子要素を全削除

			while(parentDiv.firstChild){
				parentDiv.removeChild( parentDiv.firstChild );
				
			}
						
			for(let clothes of data.clothesList){
				var newLabel = document.createElement("label");
				newLabel.setAttribute("id", "modal-label" + clothes.id);
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
			
			$('#select-modal-btn').on('click', function(){
				let radioVal = $('input[name="clothesId"]:checked').val();
				console.log(radioVal);
				$('#bottoms').val(radioVal);	
				
			});
			
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました。");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("textStatus:" + textStatus);
			console.log("errorThrown" + errorThrown.message);
		
		});
		});
	
// シューズ
	$('#shoes-btn').on('click', function(){
		$.ajax({
			url: "http://localhost:8080/show_clotheslist",
			type: "GET",
			dataType: "json",
			data: {
				categoryId: $('#shoes-btn').val()
			},
			async: true
		
		}).done(function(data){
			// リストを回して画像表示させる前に、親要素<div>の中にある既存の<img>タグを削除します.
			// 親要素
			var parentDiv = document.getElementById('modal-img');
			// 子要素を全削除
			while(parentDiv.firstChild){
				parentDiv.removeChild( parentDiv.firstChild );
				
			}
						
			for(let clothes of data.clothesList){
				var newLabel = document.createElement("label");
				newLabel.setAttribute("id", "modal-label" + clothes.id);
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
			
			$('#select-modal-btn').on('click', function(){
				let radioVal = $('input[name="clothesId"]:checked').val();
				console.log(radioVal);
				$('#shoes').val(radioVal);	
				
			});
			
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました。");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("textStatus:" + textStatus);
			console.log("errorThrown" + errorThrown.message);
		
		});
		});
	
// バッグ
	$('#bag-btn').on('click', function(){
		$.ajax({
			url: "http://localhost:8080/show_clotheslist",
			type: "GET",
			dataType: "json",
			data: {
				categoryId: $('#bag-btn').val()
			},
			async: true
		
		}).done(function(data){
			// リストを回して画像表示させる前に、親要素<div>の中にある既存の<img>タグを削除します.
			// 親要素
			var parentDiv = document.getElementById('modal-img');
			// 子要素を全削除

			while(parentDiv.firstChild){
				parentDiv.removeChild( parentDiv.firstChild );
				
			}
			
			
			for(let clothes of data.clothesList){
				var newLabel = document.createElement("label");
				newLabel.setAttribute("id", "modal-label" + clothes.id);
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
			
			$('#select-modal-btn').on('click', function(){
				let radioVal = $('input[name="clothesId"]:checked').val();
				console.log(radioVal);
				$('#bag').val(radioVal);	
				
			});
			
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました。");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("textStatus:" + textStatus);
			console.log("errorThrown" + errorThrown.message);
		
		});
		});
	
// ワンピース
	$('#dress-btn').on('click', function(){
		$.ajax({
			url: "http://localhost:8080/show_clotheslist",
			type: "GET",
			dataType: "json",
			data: {
				categoryId: $('#dress-btn').val()
			},
			async: true
		
		}).done(function(data){
			// リストを回して画像表示させる前に、親要素<div>の中にある既存の<img>タグを削除します.
			// 親要素
			var parentDiv = document.getElementById('modal-img');
			
			// 子要素を全削除
			while(parentDiv.firstChild){
				parentDiv.removeChild( parentDiv.firstChild );
				
			}
			
			
			for(let clothes of data.clothesList){
				var newLabel = document.createElement("label");
				newLabel.setAttribute("id", "modal-label" + clothes.id);
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
			
			$('#select-modal-btn').on('click', function(){
				let radioVal = $('input[name="clothesId"]:checked').val();
				console.log(radioVal);
				$('#dress').val(radioVal);	
				
			});
			
			
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました。");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("textStatus:" + textStatus);
			console.log("errorThrown" + errorThrown.message);
		
		});
		});		
});

