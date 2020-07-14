//listの個数を取得しておく
var listContents = $("#cloth_list .img-fluid").length;
$("#cloth_list").each(function(){

//最初に表示させるアイテムの数
var Num = 6;
if(listContents <= Num){
	$(this).find('#more_btn').hide();
	$(this).find('#close_btn').hide();
}

console.log(listContents);

//最初はmoreボタン表示にし、
$(this).find('#more_btn').show();
$(this).find('#close_btn').hide();
//16番目まで表示
$(this).find(".img-fluid:not(:lt("+Num+"))").hide();

//moreボタンがクリックされた時
$('#more_btn').click(function(){
//Numに+3ずつしていく = 3行ずつ追加する
Num +=3;
$(this).parent().find(".img-fluid:lt("+Num+")").slideDown(); //スライドダウンさせる

//listの個数よりNumが多い時、
if(listContents <= Num){
Num = 6;//「閉じる」がクリックされた後、表示させるアイテムの数
gtNum = Num-1;
$('#more_btn').hide();
$('#close_btn').show();

//「閉じる」がクリックされたら、
$('#close_btn').click(function(){
$(this).parent().find(".img-fluid:gt("+gtNum+")").slideUp();//11以降は非表示にし、
$(this).hide();//閉じるボタンを非表示
$('#more_btn').show();//moreボタン表示に
});
}
});
});