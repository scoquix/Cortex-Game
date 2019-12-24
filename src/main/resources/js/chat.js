window.onload = function () {

    var messages = [];
    var message = document.getElementById("message");
    var registerButton = document.getElementById("registerID");
    var loginButton = document.getElementById("loginID");
    var content = document.getElementById("content");
    var name = document.getElementById("name");

    var socket = io.connect('http://localhost:3700', {
        'reconnection delay': 2000,
        'force new connection': true
    });

    socket.on('connect', function () {
        console.log('connected');
    });

    /*socket.on('message', function (data) {
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
    });*/
    socket.on('eventRegister', function (data) {
        console.log(data);
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

    socket.on('eventLogging', function (data) {
        console.log(data);
        if (data.message) {
            messages.push(data);
            var html = '';
            for (var i = 0; i < messages.length; i++) {
                html += '<b>' + (messages[i].name ? messages[i].name : 'Server') + ': </b>';
                html += messages[i].message + '<br />';
            }
            content.innerHTML = html;
            content.scrollTop = content.scrollHeight;
            //redirect
            window.location.href = "user.html";

        } else {
            console.log("There is a problem:", data);
        }
    });

    registerButton.onclick = function () {
        console.log(name.value)
        if (name.value === "") {
            alert("Please type your name!");
        } else {
            var text = message.value;
            console.log(name.value + ': ' + text);
            socket.emit('register', {login: text, password: name.value});
        }
    };

    loginButton.onclick = function () {
        console.log(name.value)
        if (name.value === "") {
            alert("Please type your name!");
        } else {
            var text = message.value;
            console.log(name.value + ': ' + text);
            //socket.emit('send', {name: text, message: name.value});
            socket.emit('logging', {login: text, password: name.value});
        }
    };
};