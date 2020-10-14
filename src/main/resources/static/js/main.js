'use strict';

var usernameContainer = document.querySelector('#username-section');
var usernameForm = document.querySelector('#usernameForm');
var usernameInput = document.querySelector('#username');

var chatContainer = document.querySelector('#chat-section');
var chatBlock = document.querySelector('#chat-block');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');

var errorElement = document.querySelector('#error-message');

var stomp = null;
var username = null;

function connect(event) {
    username = usernameInput.value.trim();

    if(username) {
        var socket = new SockJS('/register');
        stomp = Stomp.over(socket);
        stomp.connect({}, onConnected, onError);

        usernameContainer.classList.add('hidden');
        chatContainer.classList.remove('hidden');
        usernameInput.value = '';

        displayHistoricalMessages();

    }
    event.preventDefault();
}

function displayHistoricalMessages() {
     fetch('http://localhost:8080/messages')
     .then((resp) => resp.json())
     .then(function(data) {
       data.map(function(message) {
         addMessageToBlock(message);
       })
     })
     .catch(function(error) {
       console.log(error);
     });
}

function onConnected() {
    stomp.subscribe('/messageTopic', onMessageReceived);
}


function onError(error) {
    errorElement.textContent = 'Error establishing a web socket connection, please refresh the page!';
    errorElement.classList.remove('hidden');
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    addMessageToBlock(message)
}

function addMessageToBlock(message) {
    var messageElement = document.createElement('li');

    var usernameElement = document.createElement('span');
    var usernameText = document.createTextNode(message.sender);
    usernameElement.appendChild(usernameText);
    messageElement.appendChild(usernameElement);

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);
    messageElement.appendChild(textElement);
    chatBlock.appendChild(messageElement);
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stomp) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
        };
        stomp.send("/chat/sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)