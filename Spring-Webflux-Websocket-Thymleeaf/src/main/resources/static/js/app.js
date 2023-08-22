const messagesDiv = document.getElementById('messages');
const messageInput = document.getElementById("messageInput");
const sendButton = document.getElementById("sendButton");

var clientWebSocket = new WebSocket(`ws://localhost:8080/ws`);

clientWebSocket.onopen = function () {
    console.log("clientWebSocket.onopen", clientWebSocket);
    console.log("clientWebSocket.readyState", "websocketstatus");
    clientWebSocket.send("Hello, my friends!");
}

clientWebSocket.onclose = function (event) {
    if (event.wasClean) {
        console.log(`Connection closed cleanly, code=${event.code}, reason=${event.reason}`);
    } else {
        console.error(`Connection died`);
    }
    console.log("WebSocket connection closed");
    events(event.data);
}

clientWebSocket.onerror = function (error) {
    console.log("clientWebSocket.onerror", clientWebSocket, error);
    events("An error occured");
}

clientWebSocket.onmessage = function (event) {
    console.log("clientWebSocket.onmessage", clientWebSocket, event);
    events(event.data);
}

function events(responseEvent) {
    const messageText = document.createElement("p");
    messageText.innerHTML = responseEvent;

    messagesDiv.appendChild(messageText);
}

function closeConnection() {
    if (clientWebSocket) clientWebSocket.close();
}

window.onbeforeunload = closeConnection;

sendButton.addEventListener("click", () => {
    const message = messageInput.value;
    if (message) {
        clientWebSocket.send(message);
        messageInput.value = "";
    }
});