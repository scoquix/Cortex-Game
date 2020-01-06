window.onload = function () {
    var answers = [];
    var imageButton = document.getElementById("imageButton");
    var content = document.getElementById("content");
    var answersBlock = document.getElementById("answers");
    var socket = io.connect('http://localhost:3700', {
        'reconnection delay': 2000,
        'force new connection': true
    });

    socket.on('connect', function () {
        console.log('connected');
    });

    imageButton.onclick = function () {
        // TODO: autentykacja usera, tak aby nie mial dostepu do panelu usera bez zalogowania

        // console.log(name.value)
        // if (name.value == "") {
        //     alert("Please type your name!");
        // } else {
        //     var text = message.value;
        //     console.log(name.value + ': ' + text);
        // }
        socket.emit('riddle', {name: "", message: ""});
    };

    //------------------------------------------------
    // Funkcja przygotowana do odebrania obrazku z bazy danych
    // Docelowo bedzie to element gameview
    //------------------------------------------------
    socket.on('eventImage', function (data) {
        console.log(data);
        if (data.message) {
            //messages.push(data);
            var encodedImage = '';
            //for (var i = 0; i < messages.length; i++)
            encodedImage += data.message;

            var html = "<img src=\"data:image/png;base64," + encodedImage + "\" alt=\"Riddle Image\"/>";
            content.innerHTML = "";
            content.innerHTML = html;
            content.scrollTop = content.scrollHeight;
        } else {
            console.log("There is a problem:", data);
        }
    });
    //-----------------------------------------------
    // Funkcja przygotowana do pobrania odpowiedzi w formie obrazkow
    // Docelowo bedzie to element gameview
    //-----------------------------------------------
    socket.on('eventAnswers', function (data) {
        if (!data.message) {
            return;
        }
        var imagesAsBitmaps = data.message.split("|");
        answers = [];
        for (var i = 0; i < imagesAsBitmaps.length; i++) {
            var image = "<img src=\"data:image/png;base64," + imagesAsBitmaps[i] + "\" alt=\"Answer\"/>";
            var button = "<button class='answers' id='" + i + "'>" + image + "</button>";
            answers += button;
        }
        answersBlock.innerHTML = "";
        answersBlock.innerText = "";
        answersBlock.innerHTML = answers;
        var buttons = document.getElementsByClassName("answers");
        for (var j = 0; j < buttons.length; j++) {
            buttons[j].addEventListener("click", function () {
                socket.emit("answer", {name: "", message: imagesAsBitmaps[this.id]});
            });
        }

    });
    //-----------------------------------------------
    // Funkcje przygotowane do wyswietlenia blednej i poprawnej odpowiedzi
    // Docelowo bedzie to element gameview
    //-----------------------------------------------
    socket.on("eventWrongAnswer", function (data) {
        alert("Muahahahahah you are wrong!!!!");
    });
    socket.on("eventCorrectAnswer", function (data) {
        alert("Correct! You are awesome, my friend!")
    });
    //-----------------------------------------------
};