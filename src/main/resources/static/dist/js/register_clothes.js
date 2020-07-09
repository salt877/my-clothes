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

});
