var editor;

$(document).ready(function() {

	$.ajax({

		url: '/api/v1/results',
		method: 'get',
		dataType: 'json',
		success: function (data){

			$('#targetTable').DataTable( {
				dom: 'Bfrtip',  
				buttons: [
					'csv','pdf'
				],   
				data: data,                         
				columns: [

					{"data": "id" },
					{"data": "term" },
					{"data": "hits" },
					{"data": "hitsPerYear" },
					{"data": "year_end" },
					{"data": "year_end" },
					{"data": "metadata" },
				]
			}); 
		}
	});


});