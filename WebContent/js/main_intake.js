// The root URL for the RESTful services
var rootURL = "/ToolCloud/tc/intake/details/json/";


// Retrieve wine list when application starts 
var id = GetURLParameter("id");
getMachineDetails(id);


function getMachineDetails(id) {
	console.log('getMachineDetails for id' +id);	
	$.ajax({
		type: 'GET',
		url:  rootURL + id,
		dataType: "json", // data type of response
		success: renderData
	});
}



function renderData(data) {
	console.log(data)
	var intakes = data["Results"].Intakes;
	var intake = intakes.Intake;
	

	console.log(intake.Intake_name);
	// set the machine values
	$('#inId').val(intake.Intake_intakeId);
	$('#inName').val(intake.Intake_name);
	$('#inHeight').val(intake.Intake_height);
	$('#inLength').val(intake.Intake_length);
	$('#inCad').attr("href", intake.Intake_cad);
	
	
	
}


function GetURLParameter(sParam)
{
  var sPageURL = window.location.search.substring(1);
  var sURLVariables = sPageURL.split('&');
  for (var i = 0; i < sURLVariables.length; i++)
  {
      var sParameterName = sURLVariables[i].split('=');
      if (sParameterName[0] == sParam)
      {
          return sParameterName[1];
      }
  }
}
