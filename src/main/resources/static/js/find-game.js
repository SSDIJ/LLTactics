function joinMatchmaking() {
    joinGame = true
    go("/game/matchmaking", "POST");
}

let joinGame = false

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

        if (!joinGame) return;

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
});

const findNormalGameBtn = document.getElementById('find-normal-game-btn');
const customGameBtn = document.getElementById('custom-game-btn');
const btnContainer = document.getElementById('actions-first-container');
const findingContainer = document.getElementById("searching-game");
const customRoomContainer = document.getElementById("custom-room-container")

function hideButtons() {
    btnContainer.style.display = 'none';
}

findNormalGameBtn.addEventListener('click', () => {
    hideButtons();
    findingContainer.classList.remove("hidden");
    joinMatchmaking();
});

customGameBtn.addEventListener('click', () => {
    hideButtons();
    customRoomContainer.classList.remove("hidden");
});


// Asignar al cargar
document.addEventListener('DOMContentLoaded', () => {


    // Enviar roomId y password al backend
    document.getElementById('create-room-form').addEventListener('submit', function (e) {
        e.preventDefault();

        const password = document.getElementById('room-password').value;

        const roomData = {
            password: password
        };

        console.log("Datos de sala a enviar:", roomData);

        go("/game/createPrivateRoom", "POST", roomData)
            .then(data => {
                console.log("Sala creada con ID:", data.roomId);
                customRoomContainer.classList.add("hidden");
                findingContainer.classList.remove("hidden");

                document.getElementById('display-room-id').value = data.roomId;
                document.getElementById('display-room-password').value = roomData.password;

                // Mostrar solo el contenedor de sala creada
                document.getElementById('custom-room-container').classList.add('hidden');
                document.getElementById('created-room-info').classList.remove('hidden');

                ws.initialize(iwconfig.socketUrl, ["/topic/game/privateRoom/" + data.roomId]);
                ws.receive = (message) => {
                    // Validar tipo de mensaje
                    if (message.type === "join" && message.roomId) {
                        const roomId = message.roomId;

                        // Guardar en sessionStorage
                        sessionStorage.setItem("roomId", roomId);
                        sessionStorage.setItem("gameId", roomId);
                        sessionStorage.setItem("socketUrl", iwconfig.socketUrl);

                        // Redirigir al juego
                        window.location.href = `/game/${roomId}`;
                    }
                }
            });

    });
});

const joinForm = document.getElementById('join-room-form');

joinForm.addEventListener('submit', function (event) {
    event.preventDefault();

    const roomCode = document.getElementById('room-code-input').value;
    const password = document.getElementById('join-room-password').value;

    console.log('Unirse a sala:', roomCode, '| Contraseña:', password);
    const joinData = {
        roomId: roomCode,
        password: password
    };

    go("/game/joinPrivateRoom", "POST", joinData)
        .then(data => {
            console.log("Unido correctamente a sala:", data);

            ws.initialize(iwconfig.socketUrl, ["/topic/game/privateRoom/" + data.roomId]);

            ws.receive = (message) => {
                console.log("Mensaje recibido:", message);

                if (message.type === "join" && message.roomId) {
                    sessionStorage.setItem('roomId', message.roomId);
                    sessionStorage.setItem('gameId', message.roomId);
                    sessionStorage.setItem("socketUrl", iwconfig.socketUrl);
                    window.location.href = `/game/${message.roomId}`;
                }
            };
        })
        .catch(error => {
            console.error("Error al unirse a la sala:", error);

            let errorMessage = "Error desconocido";
            if (error.status === 400) {
                errorMessage = "Faltan parámetros para unirse a la sala.";
            } else if (error.status === 401) {
                errorMessage = "Contraseña incorrecta o usuario no válido.";
            } else if (error.status === 404) {
                errorMessage = "Sala no encontrada o no es privada.";
            } else if (error.text) {
                errorMessage = error.text;
            }

            document.getElementById("errorModalMessage").textContent = errorMessage;
            const errorModal = new bootstrap.Modal(document.getElementById("errorModal"));
            errorModal.show();
        });
});

