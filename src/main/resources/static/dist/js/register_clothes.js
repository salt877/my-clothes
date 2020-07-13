$(function() {
	$('#confirm').prop('disabled', true);
	$("#category").on("change",function(){
		change();
	});
	$("#brand").on("change",function(){
		change();
	});
	$("#color").on("change",function(){
		change();
	});
	$("#price").on("change",function(){
		change();
	});
	
});
	
	function change(){
		console.log("aaa");
		　var category = document.getElementById('category').value;
		console.log(category);
		 var color = document.getElementById('color').value;
		 console.log(color);
		 var brand = document.getElementById( "brand" ).value ;
		 brand=brand.replace(/^\s+/,"");
		 console.log(brand);
		 
		 if(!category||!color||!brand){
			 $('#confirm').prop('disabled', true);
			 console.log("bbb");
		 }else{
			 $('#confirm').prop('disabled', false);
			 console.log("ccc");
		 }
	};
	
	/*
	 * $(function() { $('#brand').on("keyup", function() { var hostUrl =
	 * 'http://localhost:8080/check_brand_api/brandcheck';// どのクラスのどのメソッドなのかを指定
	 * var inputBrand = $("#brand").val(); // idと結びついている console.log(inputBrand)
	 * $.ajax({ url : hostUrl, // 三行目 type : 'POST', dataType : 'json',//
	 * 配列形式にする data : { brand : inputBrand // 引数の中身を指定？（イメージはkey,value)keyは自由に設定 },
	 * async: true // 非同期で処理を行う（asyncの部分が非同期的な意味合い） }).done(function(data) {//
	 * dateの部分にmap(APIコントローラーでreturnされたmap)が入る // コンソールに取得データを表示
	 * console.log(data); console.dir(JSON.stringify(data));
	 * $("#duplicateMessage").html(data.duplicateMessage); // jqueryをつかって、htmlの<div //
	 * id="duplicateMessage">部分にhtml形式で、date(map)の中に入っているduplicateMessageを取得してきて入れる。 //
	 * aこの部分を変更していく（今回はlistに入れていくイメージ） }).fail(function(XMLHttpRequest,
	 * textStatus, errorThrown) { alert("エラーが発生しました！");
	 * console.log("XMLHttpRequest : " + XMLHttpRequest.status);
	 * console.log("textStatus : " + textStatus); console.log("errorThrown : " +
	 * errorThrown.message); }); }); });
	 */
	
});
