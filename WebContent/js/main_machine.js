// The root URL for the RESTful services
var rootURL = "/ToolCloud/tc/machine/details/json/";

var currentObject;



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
	var machine = data["Results"].Machine;
	var intakes = data["Results"].Tools
	

	console.log(machine.name);
	// set the machine values
	$('#mId').val(machine.machineId);
	$('#mName').val(machine.name);
	$('#mDer').val(machine.der);
	$('#mLocation').val(machine.companyId);
	$('#mCad').attr("href", machine.cad);
	
	// scnd tab
	
	$.each(intakes, function(index, element) {
		$('#intakesList').append('<li>'+element.Tool_name+'</li>');
	});
	
	
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
