function joinMatchmaking() {
    ws.stompClient.send("/app/matchmaking/join", {}, {});
}

function waitForConnection(callback, interval = 100) {
    const intervalId = setInterval(() => {
        if (ws.stompClient && ws.stompClient.connected) {
            clearInterval(intervalId);
            callback();
        }
    }, interval);
}

document.addEventListener("DOMContentLoaded", () => {

    ws.initialize(iwconfig.socketUrl, ["/user/queue/match"]);

    ws.receive = (message) => {
        const gameRoom = message.body;
        console.log("You have been matched! Join game room: " + gameRoom);
        // window.location.href = `/game/${gameRoom}`;
    };

    // Esperar hasta que el WebSocket esté conectado antes de enviar
    waitForConnection(() => joinMatchmaking());
});
