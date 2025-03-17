async function getCoordinates(location, apiKey) {
    const url = `https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(location)}&key=${apiKey}`;
    console.log("Fetching coordinates for:", location);
    console.log("Request URL:", url);

    try {
        const response = await fetch(url);
        const data = await response.json();
        console.log("API response:", data);

        if (data.status === "OK") {
            const latLng = data.results[0].geometry.location;
            return `${latLng.lat},${latLng.lng}`; // Return "lat,lng" format
        } else {
            console.error("Geocoding failed:", data.status);
            return null;
        }
    } catch (error) {
        console.error("Error fetching coordinates:", error);
        return null;
    }
}

let map, directionsService, directionsRenderer;

async function initMap() {
    let sourceName = document.body.getAttribute("data-source");
    let destinationName = document.body.getAttribute("data-destination");
    let apiKey = document.body.getAttribute("data-apikey"); // Get API key from HTML
    const mapId = 'YOUR_MAP_ID'; // Replace with your Map ID

    console.log("Source:", sourceName);
    console.log("Destination:", destinationName);
    console.log("Using API Key:", apiKey);
    console.log("Using Map ID:", mapId);

    if (!apiKey) {
        console.error("Google Maps API key is missing!");
        return;
    }

    const source = await getCoordinates(sourceName, apiKey);
    console.log("Source coordinates:", source);
    const destination = await getCoordinates(destinationName, apiKey);
    console.log("Destination coordinates:", destination);

    if (!source || !destination) {
        console.error("Could not retrieve coordinates for source or destination.");
        return;
    }

    document.body.setAttribute("data-source-coordinates", source);
    document.body.setAttribute("data-destination-coordinates", destination);

    const [sourceLat, sourceLng] = source.split(",");
    const [destinationLat, destinationLng] = destination.split(",");

    const sourceLatLng = new google.maps.LatLng(parseFloat(sourceLat), parseFloat(sourceLng));
    const destinationLatLng = new google.maps.LatLng(parseFloat(destinationLat), parseFloat(destinationLng));

    map = new google.maps.Map(document.getElementById("map"), {
        center: sourceLatLng,
        zoom: 6,
        mapId: mapId, // Include the Map ID here
    });

    console.log("Map initialized");

    new google.maps.marker.AdvancedMarkerElement({
        position: sourceLatLng,
        map: map,
        title: "Source",
    });

    new google.maps.marker.AdvancedMarkerElement({
        position: destinationLatLng,
        map: map,
        title: "Destination",
    });

    // Initialize Directions Service and Renderer
    directionsService = new google.maps.DirectionsService();
    directionsRenderer = new google.maps.DirectionsRenderer({
        map: map,
    });

    // Default to DRIVING route
    calculateRoute('DRIVING');
}

window.initMap = initMap;

window.calculateRoute = function(travelMode) {
    const source = document.body.getAttribute("data-source-coordinates");
    const destination = document.body.getAttribute("data-destination-coordinates");

    console.log("Source coordinates for route:", source);
    console.log("Destination coordinates for route:", destination);

    if (!source || !destination) {
        console.error("Source or Destination coordinates are missing.");
        return;
    }

    const [sourceLat, sourceLng] = source.split(",");
    const [destinationLat, destinationLng] = destination.split(",");

    const sourceLatLng = new google.maps.LatLng(parseFloat(sourceLat), parseFloat(sourceLng));
    const destinationLatLng = new google.maps.LatLng(parseFloat(destinationLat), parseFloat(destinationLng));

    const request = {
        origin: sourceLatLng,
        destination: destinationLatLng,
        travelMode: google.maps.TravelMode[travelMode], // Travel mode based on user selection
    };

    directionsService.route(request, (result, status) => {
        if (status === google.maps.DirectionsStatus.OK) {
            directionsRenderer.setDirections(result);
            console.log("Directions:", result);
        } else {
            console.error("Directions request failed due to", status);
        }
    });
};
