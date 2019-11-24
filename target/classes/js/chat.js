window.onload = function () {

    var messages = [];
    var answers = [];
    var message = document.getElementById("message");
    var sendButton = document.getElementById("send");
    var answerButtonA = document.getElementById("buttonA");
    var answerButtonB = document.getElementById("buttonB");
    var answerButtonC = document.getElementById("buttonC");
    var answerButtonD = document.getElementById("buttonD");
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
    socket.on("wrongAnswers", function (data) {
        if (data) {
            var tmp = data.message.split(" ");
            answers=tmp;
            //answers.push(tmp);
            console.log(answers);
            answerButtonA.innerHTML = tmp[0];
            answerButtonB.innerHTML = tmp[1];
            answerButtonC.innerHTML = tmp[2];
            answerButtonD.innerHTML = tmp[3];
        } else {
            console.log("There is a problem with wrong answers:", data);
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
    answerButtonA.onclick = function () {
        if (name.value == "") {
            alert("Please type your name!");
        } else {
            var text = message.value;
            console.log(name.value + ': ' + text);
            socket.emit('send', {message: text, name: name.value});
        }
    };

    answerButtonB.onclick = function () {
        if (name.value == "") {
            alert("Please type your name!");
        } else {
            var text = this.innerHTML;
            console.log(name.value + ': ' + text);
            socket.emit('send', {message: text, name: name.value});
        }
    };
    answerButtonC.onclick = function () {
        if (name.value == "") {
            alert("Please type your name!");
        } else {
            var text = this.innerHTML;
            console.log(name.value + ': ' + text);
            socket.emit('send', {message: text, name: name.value});
        }
    };
    answerButtonD.onclick = function () {
        if (name.value == "") {
            alert("Please type your name!");
        } else {
            var text = this.innerHTML;
            console.log(name.value + ': ' + text);
            socket.emit('send', {message: text, name: name.value});
        }
    };
}