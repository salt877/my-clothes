$(function() {

		//listの個数を取得しておく
		var listContents = $("#cloth_list .portfolio-item").length;
		console.log("リストコンテンツの個数"+listContents);
		$("#cloth_list").each(function(){

		//最初に表示させるアイテムの数
			var num = 9;
			console.log("listContents:"+listContents);	
			console.log("num:"+num);	
			//最初はmoreボタン表示にし、
			$(this).find('#more_btn').show();
			$(this).find('#close_btn').hide();
			//6番目まで表示
			$(this).find(".portfolio-item:not(:lt("+num+"))").hide();
			
			if (listContents <= num) {
				$('#close_btn').hide();
				$('#more_btn').hide();
				console.log("リストコンテンツ:"+listContents);
				console.log("数:"+num);
			}

			//moreボタンがクリックされた時
			$('#more_btn').click(function(){
				//numに+3ずつしていく = 3行ずつ追加する
				num +=9;
				$(this).parents("#cloth_list").find(".portfolio-item:lt("+num+")").slideDown(); //スライドダウンさせる

				//listの個数よりNumが多い時、
				if(listContents <= num){
					num = 9;//「閉じる」がクリックされた後、表示させるアイテムの数
					gtNum = num-1;
					$('#close_btn').show();
					$('#more_btn').hide();

				//「閉じる」がクリックされたら、
					$('#close_btn').click(function(){
						$(this).parents("#cloth_list").find(".portfolio-item:gt("+gtNum+")").slideUp();//11以降は非表示にし、
						$(this).hide();//閉じるボタンを非表示
						$('#more_btn').show();//moreボタン表示に
					});
				}
			});
		});
	});