var table; //Need this global to access contents of table

$(document).ready(function() {

	// Get the modal
	var modal = document.getElementById("myModal");

	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("close")[0];

	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
		modal.style.display = "none";
		document.getElementById("targetTable").style.position = "absolute";
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
			document.getElementById("targetTable").style.position = "absolute";

		}
	}

	//Always load datatable contents when page is ready
	loadDatatables();



	// process the form
	$('form').submit(function(event) {

		// get the form data
		var formData = {
			'term'              : $('input[name=term]').val(),
			'year_begin'        : $('input[name=year_begin]').val(),
			'year_end'    		: $('input[name=year_end]').val(),
			'key'				: $('input[name=key]').val()
		};

		//Setting up content for modal to display info during and after post request
		var loading = document.getElementById("loading");
		document.getElementById("targetTable").style.position = "";
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
					resetModal();
					loading.style.display = "none";
					heading.innerText = "Loading...";
					div.innerHTML = "";
					document.getElementById("targetTable").style.position = "absolute";
				});

				$("#save").click(function () {
					loadDatatables();
					loading.style.display = "none";
					heading.innerText = "Loading...";
					div.innerHTML = "";
					document.getElementById("targetTable").style.position = "absolute";
				});
			},
			error: function( jqXhr, textStatus, errorThrown ){
				console.log(errorThrown);
				heading.innerText = "Error";
				div.innerHTML = "An error occurred. Please restart the search. <hr>" +
					'<button id="agree" class="btn btn-warning">Ok</button>';
				$("#agree").click(function (){
					loading.style.display = "none";				
					heading.innerText = "Loading...";
					div.innerHTML = "";
					document.getElementById("targetTable").style.position = "absolute";
				});
			}

		});
		// stop the form from submitting the normal way and refreshing the page
		event.preventDefault();
	});


	//Server shutdown functionality
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
	$('#targetTable tbody').on('click', '.delete', function (event) {
		var row = $(this).closest('tr');

		var id = table.row( row ).data().id;
		var url = 'api/v1/results/'+id;
		console.log(url);
		deleteEntry(url);
		event.stopPropagation(); //So we don't "also" click on the table column

	});


	//Click on column shows info in modal
	$('#targetTable tbody').on('click', 'tr', function () {
		document.getElementById("targetTable").style.position = "";
		var row = $(this);
		var data = table.row(row).data();
		console.log(data);
		var modal_content = document.querySelector(".modal-content");
		var div = modal_content.children[2];
		var years = ""; 
		var eachYear = [];
		for(i=data.hitsPerYear.length-1; i>=0; i--) {
			years += data.year_end-i + ": " + data.hitsPerYear[i] + " hits" + "<br>";
			eachYear.push(data.year_begin+i);

		}
		div.innerHTML = "Term: " + data.term + ", Period:  " + data.year_begin +
			"-" + data.year_end + ", Date: " + data.timestamp + ".<hr>"+
			years + "<hr>" + 
			'<div id="plot"></div>';
		if(data.hitsPerYear.length > 1) {
			var trace1 = {
				x: eachYear,
				y: data.hitsPerYear,
				name: 'Hits per Year',
				type: 'scatter'
			};

			var results = [trace1];

			Plotly.newPlot('plot', results);
		}


		modal.style.display = "block";
	});



});


//Function do load data via ajax http request into databales
function loadDatatables() {
	$.ajax({

		url: '/api/v1/results',
		method: 'get',
		dataType: 'json',
		success: function (data){

			table =	$('#targetTable').DataTable( {
				destroy: true, 
				dom: 'Bfrtip',  
				scrollY:        200,
				deferRender:    true,
				scroller:       true,
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

//Function to delete an entry
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
