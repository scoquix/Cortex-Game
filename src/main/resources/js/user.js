window.onload = function () {
    var showLobbies = document.getElementById("showLobbies");
    var createLobby = document.getElementById("createLobby");
    var content = document.getElementById("content");
    var socket = io.connect('http://localhost:3700', {
        'reconnection delay': 2000,
        'force new connection': true
    });

    socket.on('connect', function () {
        console.log('connected');
    });


    //-----------------------------------------------
    // Funkcja przygotowane do stworzenia lobby
    //-----------------------------------------------
    createLobby.onclick = function () {
        socket.emit("createLobby", {name: "", message: ""})
    };
    //-----------------------------------------------
    //-----------------------------------------------
    // Funkcja przygotowane do wyswietlenia lobbies
    //-----------------------------------------------
    showLobbies.onclick = function () {
        socket.emit("showLobbies", {name: "", message: ""})
    };
    //-----------------------------------------------

    //-----------------------------------------------
    // Funkcja przygotowane do wyswietlenia lobbies
    //-----------------------------------------------
    socket.on("eventCreateLobby", function (data) {
        if (data.message) {
            alert(data.message);
        }
    });

    //-----------------------------------------------
    // Funkcja przygotowane do wyswietlenia lobbies
    //-----------------------------------------------
    socket.on("eventShowLobbies", function (data) {
        if (data.message) {
            console.log(data.message);
            var roomsHtml = "";
            var rooms = data.message.split(",");
            for (var i = 1; i < rooms.length; i++) {
                var button = "<a href='game.html'><button class='answers' id='" + i + "'>" + rooms[i] + "</button></a>";
                roomsHtml += button;
            }
            content.innerHTML = "";
            content.innerHTML = roomsHtml;
        }

    });
    //-----------------------------------------------

};

