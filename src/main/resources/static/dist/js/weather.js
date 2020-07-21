/**
 * 
 */

function clickBtn3() {
	var API_KEY = '478ea780a9d4d20c8ddf7539de30ee32';
	const str = document.getElementById("city").value;
	var city = str;
	var city = "東京都";
	var url = 'http://api.openweathermap.org/data/2.5/forecast?q=' + city
			+ ',jp&units=metric&APPID=' + API_KEY;
	console.log(url);
	$.ajax({
		url : url,
		dataType : "json",
		type : 'GET',
	}).done(function(data) {
		var insertHTML = "";
		var cityName = '<h2>' + data.city.name + '</h2>';
		$('#city-name').html(cityName);
		for (var i = 0; i <= 24; i = i + 8) {
			insertHTML += buildHTML(data, i);
		}
		$('#weather').html(insertHTML);
	}).fail(function(data) {
		console.log("失敗しました");
	});
}

function buildHTML(data, i) {
	var Week = new Array("（日）", "（月）", "（火）", "（水）", "（木）", "（金）", "（土）");
	var date = new Date(data.list[i].dt_txt);
	date.setHours(date.getHours() + 9);
	var month = date.getMonth() + 1;
	var day = month + "月" + date.getDate() + "日" + Week[date.getDay()]
			+ date.getHours() + "：00";
	var icon = data.list[i].weather[0].icon;
	var html = '<div class="weather-report">'
			+ '<img src="http://openweathermap.org/img/w/' + icon + '.png">'
			+ '<div class="weather-date">' + day + '</div>'
			+ '<div class="weather-main">' + data.list[i].weather[0].main
			+ '</div>' + '<div class="weather-temp">'
			+ Math.round(data.list[i].main.temp) + '℃</div>' + '</div>';
	return html
}
