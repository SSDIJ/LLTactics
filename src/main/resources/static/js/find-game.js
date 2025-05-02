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
        const roomId = message.roomId;
        console.log(roomId)
        window.location.href = `/game/${roomId}`;
    };

    // Esperar hasta que el WebSocket esté conectado antes de enviar
    waitForConnection(() => joinMatchmaking());
});
