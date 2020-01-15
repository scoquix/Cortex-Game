window.onload = function () {
    var answers = [];
    var imageButton = document.getElementById("imageButton");
    var content = document.getElementById("content");
    var answersBlock = document.getElementById("answers");
    var timer = document.getElementById("timer");
    var socket = io.connect('http://localhost:3700', {
        'reconnection delay': 2000,
        'force new connection': true
    });
    const cookies = document.cookie.split(/; */); //dopasuje "; " ale też ";"
    const sessID = cookies[0].split("=")[1];
    const lobbyID = cookies[1].split("=")[1];
    socket.on('connect', function () {
        console.log("Client connected");
        console.log("sessID: " + sessID);
        console.log("lobby: " + lobbyID);
        socket.emit('socketGameInit', {name: sessID, message: lobbyID});
        if (sessID != lobbyID)
            imageButton.hidden = true;
    });

    imageButton.onclick = function () {
        // TODO: autentykacja usera, tak aby nie mial dostepu do panelu usera bez zalogowania
        // name => room ID
        // Set the date we're counting down to
        socket.emit('timerStart', {name: "", message: lobbyID});

        var countDownDate = new Date().getTime();
        var countToFive = 0;
        // Update the count down every 1 second
        var x = setInterval(function () {
            // Get today's date and time
            var now = new Date().getTime();

            // Find the distance between now and the count down date
            var distance = now - countDownDate;

            // Time calculations for days, hours, minutes and seconds
            var seconds = Math.floor((distance % (1000 * 60)) / 1000);

            // Output the result in an element with id="demo"
            timer.innerHTML = seconds + "s ";

            // If the count down is over, write some text
            if (distance > 15000) {
                countToFive++;
                countDownDate = new Date().getTime();
                socket.emit('riddle', {name: sessID, message: lobbyID});
            }
            if (countToFive === 5) {
                clearInterval(x);
            }
        }, 1000);
    };

    //------------------------------------------------
    // Funkcja przygotowana do startu gry
    //------------------------------------------------
    socket.on('eventTimerStart', function (data) {
        if (data.message) {
            if (sessID != lobbyID) {
                var countDownDate = new Date().getTime();
                var countToFive = 0;
                // Update the count down every 1 second
                var x = setInterval(function () {
                    // Get today's date and time
                    var now = new Date().getTime();

                    // Find the distance between now and the count down date
                    var distance = now - countDownDate;

                    // Time calculations for days, hours, minutes and seconds
                    var seconds = Math.floor((distance % (1000 * 60)) / 1000);

                    // Output the result in an element with id="demo"
                    timer.innerHTML = seconds + "s ";
                    // If the count down is over, write some text
                    if (distance > 15000) {
                        countToFive++;
                        countDownDate = new Date().getTime();
                        clearInterval(x);
                    }
                }, 1000);
            }
        } else {
            console.log("There is a problem:", data);
        }
    });

    //------------------------------------------------
    // Funkcja przygotowana do odebrania obrazku z bazy danych
    // Docelowo bedzie to element gameview
    //------------------------------------------------
    socket.on('eventImage', function (data) {
        console.log(data);
        if (data.message) {
            var encodedImage = '';
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
                socket.emit("answer", {name: lobbyID, message: imagesAsBitmaps[this.id]});
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