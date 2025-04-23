export class RouteDisplay {
    constructor() {
        this.directionsService = new google.maps.DirectionsService();
        this.directionsRenderer = new google.maps.DirectionsRenderer({
            preserveViewport: true,
            suppressMarkers: true
        });
        this.map = null;
        this.origin = null;
        this.destination = null;
    }

    setMap(map) {
        this.map = map;
        this.directionsRenderer.setMap(map);
    }

    setPoints(origin, destination) {
        this.origin = origin;
        this.destination = destination;
    }

    render() {
        if (this.directionsRenderer.getMap() === null) {
            this.directionsRenderer.setMap(this.map);
        }

        this.directionsService.route({
            origin: this.origin,
            destination: this.destination,
            travelMode: 'DRIVING'
        }, (response, status) => {
            if (status === 'OK') {
                this.directionsRenderer.setDirections(response);
                const bounds = new google.maps.LatLngBounds();
                const route = response.routes[0].overview_path;
                route.forEach(point => bounds.extend(point));
                this.map.fitBounds(bounds);
            } else {
                window.alert('Directions request failed due to ' + status);
            }
        });
    }

    hide() {
        this.directionsRenderer.setMap(null);
    }

    show() {
        this.directionsRenderer.setMap(this.map);
    }

    toggleShow() {
        this.directionsRenderer.setMap(
            this.directionsRenderer.getMap() === null ? this.map : null
        );
    }

    isAlreadyRendered(origin, destination) {
        return origin === this.origin && destination === this.destination;
    }
}
