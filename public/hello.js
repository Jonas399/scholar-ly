var table;

$(document).ajaxStart(function() {
	//$("#loading").show();
	//var loading = document.getElementById("loading");
	//loading.style.display = "block";


});

$( document ).ajaxStop(function() {
	//$("#loading").hide();
	
});

$(document).ready(function() {

	// Get the modal
	var modal = document.getElementById("myModal");

	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("close")[0];

	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
		modal.style.display = "none";
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	}

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
		var loading = document.getElementById("loading");
		loading.style.display = "block";
		var heading = loading.children[0].children[0];
		var div = loading.children[0].children[1];
		var years = ""; 
		// process the form
		$.ajax({
			type        : 'POST', // define the type of HTTP verb we want to use (POST for our form)
			contentType	: 'application/json',
			url         : '/api/v1/results', // the url where we want to POST
			data        : JSON.stringify(formData), // our data object
			dataType    : 'json', // what type of data do we expect back from the server
			encode          : true,

			success: function( data, textStatus, jQxhr ){
				console.log(data);
				for(i=data.hitsPerYear.length-1; i>=0; i--) {
					years += data.year_end-i + ": " + data.hitsPerYear[i] + " hits" + "<br>";
				}
				heading.innerText = "Success";
				div.innerHTML = "Term: " + data.term + ", Period:  " + data.year_begin +
					"-" + data.year_end + ", Date: " + data.timestamp + ".<hr>"+
					years + "<hr>"+
					"Do you wish to keep this result?" + "<br>" +
					'<button id="save" class="btn btn-success">Yes</button>' +
					'<button id="discard" class="btn btn-danger">Discard</button>'; 
				//Delete functionality when discarding
				$("#discard").click(function (){
					var url = 'api/v1/results/'+data.id;
					deleteEntry(url);
					loading.style.display = "none";
					heading.innerText = "Loading...";
					div.innerHTML = "";
				});

				$("#save").click(function () {
					loadDatatables();
					loading.style.display = "none";
					heading.innerText = "Loading...";
					div.innerHTML = "";

				});
				//loadDatatables();
			},
			error: function( jqXhr, textStatus, errorThrown ){
				console.log(errorThrown);
				heading.innerText = "Error";
				div.innerHTML = "An error occurred. Please restart the search. <hr>" +
					'<button id="agree" class="btn btn-warning">Ok</button>';
				$("#agree").click(function (){
					loading.style.display = "none";				
				});
			}

		});
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


	//Delete Button in Datatables
	$('#targetTable tbody').on('click', '.delete', function () {
		var row = $(this).closest('tr');

		var id = table.row( row ).data().id;
		var url = 'api/v1/results/'+id;
		console.log(url);
		deleteEntry(url);

	});




	$('#targetTable tbody').on('click', 'tr', function () {
		var row = $(this);
		var data = table.row(row).data();
		console.log(data);
		var modal_content = document.querySelector(".modal-content");
		var div = modal_content.children[2];
		var years = ""; 
		for(i=data.hitsPerYear.length-1; i>=0; i--) {
			years += data.year_end-i + ": " + data.hitsPerYear[i] + " hits" + "<br>";
		}
		div.innerHTML = "Term: " + data.term + ", Period:  " + data.year_begin +
			"-" + data.year_end + ", Date: " + data.timestamp + ".<hr>"+
			years + "<hr>";


		modal.style.display = "block";
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
					{"data": "timestamp" },
					{defaultContent: '<input type="button" class="delete" value="Delete"/>'} 
				]
			}); 
		}
	});
}

function deleteEntry(url) {
	$.ajax({
		url: url,
		type: 'DELETE',
		success: function ()
		{
			console.log("Entry Deleted.");
			loadDatatables();
		}


	}); 
}
