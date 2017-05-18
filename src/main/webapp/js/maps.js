var selectedRouteId = 0;
var stops = [];
function loadMap(routeId) {
	if(selectedRouteId == 0) {
		var script = document.createElement("script");
		script.src = "https://maps.googleapis.com/maps/api/js?key=AIzaSyB0Qp5bV34_DfNQk5L9o5b2vVMcT0wrAg4&callback=initMap";
		document.body.appendChild(script);
	}
	selectedRouteId = routeId;
	initMap();
	$('#map-holder').fadeIn('slow');
}
function initMap() {
	$.ajax({
		method : "GET",
		url : "rest/pojo/route",
		data : {
			q : selectedRouteId
		}
	}).done(function(data) {
		console.log('Done calling backend service');
		stops = data.lsStops;
		console.log(data.lsStops);

		if (stops.length > 2) {

			var directionsService = new google.maps.DirectionsService;
			var directionsDisplay = new google.maps.DirectionsRenderer;
			var map = new google.maps.Map(document.getElementById('map'), {
				zoom : 6,
			});
			directionsDisplay.setMap(map);

			calculateAndDisplayRoute(directionsService, directionsDisplay);
		}
	});

}

function calculateAndDisplayRoute(directionsService, directionsDisplay) {
	var waypts = [];

	for (var i = 0; i < stops.length; i++) {
		waypts.push({
			location : new google.maps.LatLng(stops[i].lat, stops[i].lng),
			stopover : true
		});
	}
	directionsService.route({
		origin : {
			lat : stops[0].lat,
			lng : stops[0].lng
		},
		destination : {
			lat : stops[stops.length - 1].lat,
			lng : stops[stops.length - 1].lng
		},
		waypoints : waypts,
		optimizeWaypoints : true,
		travelMode : 'DRIVING'
	}, function(response, status) {
		if (status === 'OK') {
			directionsDisplay.setDirections(response);
		} else {
			window.alert('Directions request failed due to ' + status);
		}
	});
}