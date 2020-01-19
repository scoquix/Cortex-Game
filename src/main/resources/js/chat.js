window.onload = function () {

    let messages = [];
    let message = document.getElementById("message");
    let registerButton = document.getElementById("registerID");
    let loginButton = document.getElementById("loginID");
    let content = document.getElementById("content");
    let name = document.getElementById("name");

    let socket = io.connect('http://localhost:3700', {
        'reconnection delay': 2000,
        'force new connection': true
    });

    socket.on('connect', function () {
        console.log("Client connected");
    });

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
            if (data.message !== "Authentication failed") {
                document.cookie = "sessID=" + data.name + "; ";
                window.location.href = "user.html";
            }
        } else {
            console.log("There is a problem:", data);
        }
    });

    registerButton.onclick = function () {
        if (name.value === "") {
            alert("Please type your name!");
        } else {
            let text = message.value;
            console.log(name.value + ': ' + text);
            socket.emit('register', {login: name.value, password: text});
        }
    };

    loginButton.onclick = function () {
        if (name.value === "") {
            alert("Please type your name!");
        } else {
            let text = message.value;
            console.log(name.value + ': ' + text);
            socket.emit('logging', {login: name.value, password: text});
        }
    };
};