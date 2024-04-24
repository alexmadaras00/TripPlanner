const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

// Connect to the WebSocket server
stompClient.activate();

// Callback when connected
stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);

    stompClient.subscribe('/routes', (message) => {
        console.log('Received message:', message.body);
    });
};
