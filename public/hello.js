var table;

$(document).ajaxStart(function() {
	$( ".log" ).text( "Triggered ajaxStart handler." );	
	$("#loading").show();
});

$( document ).ajaxStop(function() {
	$("#loading").hide();
});

$(document).ready(function() {

	
	loadDatatables();



	// process the form
	$('form').submit(function(event) {

		// get the form data
		// there are many ways to get this data using jQuery (you can use the class or id also)
		var formData = {
			'term'              : $('input[name=term]').val(),
			'year_begin'        : $('input[name=year_begin]').val(),
			'year_end'    		: $('input[name=year_end]').val(),
			'key'				: $('input[name=key]').val()
		};

		// process the form
		$.ajax({
			type        : 'POST', // define the type of HTTP verb we want to use (POST for our form)
			contentType	: 'application/json',
			url         : '/api/v1/results', // the url where we want to POST
			data        : JSON.stringify(formData), // our data object
			dataType    : 'json', // what type of data do we expect back from the server
			encode          : true,

			success: function( data, textStatus, jQxhr ){
				loadDatatables();
			},
			error: function( jqXhr, textStatus, errorThrown ){
				console.log(errorThrown);
			}

		})
		// using the done promise callback
		/*.done(function(data) {

		// log data to the console so we can see
		console.log(data);
				loadDatatables();
		// here we will handle errors and validation messages
	    });*/

		// stop the form from submitting the normal way and refreshing the page
		event.preventDefault();
	});


	$('.topnav').on('click', '#quit',function () {
		console.log("Quitting...");
		$.ajax({
			url: 'actuator/shutdown',
			type: 'POST',
			success: function ()
			{
				console.log("Shutdown completed.");
				document.write('<html><body><pre>Your server has been shut down.</pre></body></html>');
				document.close();
			}
		});

	});


	$('#targetTable tbody').on('click', '.delete', function () {
		var row = $(this).closest('tr');

		var id = table.row( row ).data().id;
		var url = 'api/v1/results/'+id;
		console.log(url);
		$.ajax({
			url: url,
			type: 'DELETE',
			success: function ()
			{
				console.log("Entry Deleted.");
				loadDatatables();
			}


		}); 
	});

});


function loadDatatables() {
	$.ajax({

		url: '/api/v1/results',
		method: 'get',
		dataType: 'json',
		success: function (data){

			table =	$('#targetTable').DataTable( {
				destroy: true, 
				dom: 'Bfrtip',  
				buttons: [
					'csv','pdf'
				],   
				data: data,                         
				columns: [

					{"data": "id" },
					{"data": "term" },
					{"data": "hitsPerYear" },
					{"data": "year_begin" },
					{"data": "year_end" },
					{defaultContent: '<input type="button" class="delete" value="Delete"/>'} 
				]
			}); 
		}
	});
}


