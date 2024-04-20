// var map = new google.maps.Map(document.getElementById('map'), {
//     zoom: 5,
//     center: {"lat": 39.317779, "lng": -101.459656},
//     mapTypeId: 'roadmap',
// });

const { Map } = await google.maps.importLibrary("maps");
const map = new Map(document.getElementById("map"), {
    zoom: 11,
    center: {"lat": 39.317779, "lng": -101.459656},
    mapId: "4504f8b37365c3d0",
});