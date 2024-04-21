// var map = new google.maps.Map(document.getElementById('map'), {
//     zoom: 5,
//     center: {"lat": 39.317779, "lng": -101.459656},
//     mapTypeId: 'roadmap',
// });

const {Map} = await google.maps.importLibrary("maps");
var myLatLang = {"lat": 52.21833, "lng": 6.89583}
var options = {
    center: myLatLang,
    zoom: 7,
    mapTypeId: google.maps.MapTypeId.ROADMAP
};
var map = new google.maps.Map(document.getElementById("map"),options);


var directionsService = new google.maps.DirectionsService();
var directionsDisplay = new google.maps.DirectionsRenderer();
directionsDisplay.setMap(map);
