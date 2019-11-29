window.onload = function () {

    var messages = [];
    var message = document.getElementById("message");
    var sendButton = document.getElementById("send");
    var content = document.getElementById("content");
    var name = document.getElementById("name");

    var socket = io.connect('http://localhost:3700', {
        'reconnection delay': 2000,
        'force new connection': true
    });

    socket.on('connect', function () {
        console.log('connected');
    });

    socket.on('message', function (data) {
        if (data.message) {
            messages.push(data);
            var html = '';
            for (var i = 0; i < messages.length; i++) {
                html += '<b>' + (messages[i].name ? messages[i].name : 'Server') + ': </b>';
                html += messages[i].message + '<br />';
            }
            content.innerHTML = html;
            content.scrollTop = content.scrollHeight;
        } else {
            console.log("There is a problem:", data);
        }
    });

    sendButton.onclick = function () {
        if (name.value == "") {
            alert("Please type your name!");
        } else {
            var text = message.value;
            console.log(name.value + ': ' + text);
            socket.emit('send', {message: text, name: name.value});
        }
    };

}