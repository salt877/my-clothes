$(function(){
	
	// カテゴリー別合計金額グラフ
	$('#chartListedByCategoryPrice').ready(function(){
		$.ajax({
			url: "/myqlo/get_category_price_data",
			type: "GET",
			dataType: "json",
			async: true
							
		}).done(function(data){
								
			let categoryNames = []
			let sums = [];
													
			for(let statsByCategorySum of data.statsByCategorySumList){
				categoryNames.push(statsByCategorySum.categoryName);
				sums.push(statsByCategorySum.categorySum);
			}
										
			const ctx = document.getElementById('chartListedByCategoryPrice');
			const doughnutChart = new Chart(ctx, {
				type: 'doughnut',
				data : {
					labels : categoryNames,
					datasets : [{
						data : sums,
					}]
				},
				options : {
					maintainAspectRatio: false,
					title : {
						display : true,
						text : 'カテゴリー'
					},
					tooltips: { 
						callbacks: {
							label: function(tooltipItem, data) {
										let value = data.datasets[0].data[tooltipItem.index];
										let name = data.labels[tooltipItem.index];
	                 		 	 			if(parseInt(value) >= 1000){
	                 		 	 		　　		return name + '：' + value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '円';
	                 		 	 			} else {
	                 		 	 		　　		return name + '：' + value + '円';
	                 		 	 			}
	                 	 			}
						}
					}
				},
				plugins: {
		            colorschemes: {
		                scheme: 'brewer.Paired12'
			            }
			        }											
		       })
			
									
									
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました。");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("textStatus:" + textStatus);
			console.log("errorThrown" + errorThrown.message);
		});
	});
	
	// カテゴリー別アイテム数グラフ
	$('#chartListedByCategoryCount').ready(function(){
		$.ajax({
			url: "/myqlo/get_category_count_data",
			type: "GET",
			dataType: "json",
			async: true
				
			}).done(function(data){
				let categoryNames = [];
				var categoryCounts = [];
								
				for(let categoryCount of data.categoryCountList){
					categoryNames.push(categoryCount.categoryName);
					categoryCounts.push(categoryCount.categoryCount);
					
				}
									
				const ctx = document.getElementById('chartListedByCategoryCount');
				const doughnutChart = new Chart(ctx, {
					type: 'doughnut',
					data : {
						labels : categoryNames,
						datasets : [{
							data : categoryCounts,
							}]
					},
					options : {
						maintainAspectRatio: false,
						title : {
							display : true,
							text : 'カテゴリー'
						},
						tooltips: { /* ここです */
				             callbacks: {
				            	 label: function(tooltipItem, data) {
				            	 		let value = data.datasets[0].data[tooltipItem.index];
				            	 		let name = data.labels[tooltipItem.index];
				            	 			if(parseInt(value) >= 1000){
				            	 				return name + '：' + value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '点';
					                        } else {
					                            return name + '：' + value + '点';
					                        }
					                	 }
					              }
					    }
					},
					plugins: {
						colorschemes: {
							scheme: 'brewer.Paired12'
						}
				    }
		        })
			}).fail(function(XMLHttpRequest, textStatus, errorThrown){
				alert("エラーが発生しました。");
					console.log("XMLHttpRequest:" + XMLHttpRequest.status);
					console.log("textStatus:" + textStatus);
					console.log("errorThrown" + errorThrown.message);
			});
	});
	
	// ブランド別合計金額グラフ
	$('#chartListedByBrandPrice').ready(function(){
		$.ajax({
			url: "/myqlo/get_brand_price_data",
			type: "GET",
			dataType: "json",
			async: true
				
			}).done(function(data){
				let brandNames = []; 
				let brandSums = [];
								
				for(let brandSum of data.brandSumList){
					brandNames.push(brandSum.brandName);
					brandSums.push(brandSum.brandSum);
				}
									
				const ctx = document.getElementById('chartListedByBrandPrice');
				const doughnutChart = new Chart(ctx, {
					type: 'doughnut',
					data : {
						labels : brandNames,
						datasets : [{
							data : brandSums,
							}]
					},
					options : {
						maintainAspectRatio: false,
						title : {
							display : true,
							text : 'ブランド'
						},
					tooltips: {	
						callbacks: {
							label: function(tooltipItem, data) {
								var value = data.datasets[0].data[tooltipItem.index];
								let name = data.labels[tooltipItem.index];
					            	if(parseInt(value) >= 1000){
					            		return name + '：' + value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '円';
					                } else {
					                    return name + '：' + value + '円';
					                }
					           	 }
					         }
						}
					 },
					plugins: {
						colorschemes: {
							scheme: 'brewer.Paired12'
				        }
			        }
		       })
			}).fail(function(XMLHttpRequest, textStatus, errorThrown){
				alert("エラーが発生しました。");
					console.log("XMLHttpRequest:" + XMLHttpRequest.status);
					console.log("textStatus:" + textStatus);
					console.log("errorThrown" + errorThrown.message);
			});
	});
	
	// ブランド別アイテム数グラフ
	$('#chartListedByBrandCount').ready(function(){
		$.ajax({
			url: "/myqlo/get_brand_count_data",
			type: "GET",
			dataType: "json",
			async: true
				
			}).done(function(data){
				let brandNames = [];
				let brandCounts = [];
								
				for(let brandCount of data.brandCountList){
					brandNames.push(brandCount.brandName);
					brandCounts.push(brandCount.brandCount);
				}
									
				const ctx = document.getElementById('chartListedByBrandCount');
				const doughnutChart = new Chart(ctx, {
					type: 'doughnut',
					data : {
						labels : brandNames,
						datasets : [{
							data : brandCounts,
							}]
					},
					options : {
						maintainAspectRatio: false,
						title : {
							display : true,
							text : 'ブランド'
						},
						tooltips: {
							callbacks: {
								label: function(tooltipItem, data) {
									var value = data.datasets[0].data[tooltipItem.index];
									let name = data.labels[tooltipItem.index];
										if(parseInt(value) >= 1000){
											return name + '：' + value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '点';
										} else {
											return name + '：' + value + '点';
										}
								}
							}
						}
					},
					plugins: {
						colorschemes: {
							scheme: 'brewer.Paired12'
				        }
					}
				})
			}).fail(function(XMLHttpRequest, textStatus, errorThrown){
				alert("エラーが発生しました。");
					console.log("XMLHttpRequest:" + XMLHttpRequest.status);
					console.log("textStatus:" + textStatus);
					console.log("errorThrown" + errorThrown.message);
			});
	});
	
});