function joinMatchmaking() {
    go("/game/matchmaking", "POST");
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

    ws.receive = (message) => {

        const type = message["mm-type"]

        if (type === "waiting") {
            // Recibimos que hay un jugador esperando, respondemos aceptando
            const playerId = message["mm-playerId"]
            go(`/game/matchmaking?accept=${playerId}`, "POST");
        }

        if (type === "start") {
            const roomId = message["mm-roomId"];
            sessionStorage.setItem('roomId', roomId);
            sessionStorage.setItem('gameId', roomId);
            sessionStorage.setItem("socketUrl", iwconfig.socketUrl);
            window.location.href = `/game/${roomId}`;
        }
    };

    waitForConnection(() => joinMatchmaking());
});
