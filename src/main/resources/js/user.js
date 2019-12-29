window.onload = function () {
    var messages = [];
    var imageButton = document.getElementById("imageButton");
    var message = document.getElementById("message");

    var socket = io.connect('http://localhost:3700', {
        'reconnection delay': 2000,
        'force new connection': true
    });

    socket.on('connect', function () {
        console.log('connected');
    });

    //------------------------------------------------
    // Funkcja przygotowana do zaladowania obrazku z bazy danych
    // Docelowo bedzie to element gameview
    //------------------------------------------------
    socket.on('eventImage', function (data) {
        console.log(data);
        if (data.message) {
            messages.push(data);
            var encodedImage = '';
            for (var i = 0; i < messages.length; i++)
                encodedImage += messages[i].message;

            var html = "<img src=\"data:image/png;base64," + encodedImage + "\" alt=\"Riddle Image\"/>";
            content.innerHTML = html;
            content.scrollTop = content.scrollHeight;
        } else {
            console.log("There is a problem:", data);
        }
    });
    //-----------------------------------------------
    imageButton.onclick = function () {
        // TODO: autentykacja usera, tak aby nie mial dostepu do panelu usera bez zalogowania

        // console.log(name.value)
        // if (name.value == "") {
        //     alert("Please type your name!");
        // } else {
        //     var text = message.value;
        //     console.log(name.value + ': ' + text);
        // }
        socket.emit('image', {name: "", message: ""});
    };

}