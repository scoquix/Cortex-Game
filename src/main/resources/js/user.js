window.onload = function () {
    let showLobbies = document.getElementById("showLobbies");
    let createLobby = document.getElementById("createLobby");
    let deleteLobby = document.getElementById("deleteLobby");
    let content = document.getElementById("content");
    let socket = io.connect('http://localhost:3700', {
        'reconnection delay': 2000,
        'force new connection': false
    });
    const cookies = document.cookie.split(/; */); //dopasuje "; " ale też ";"
    console.log(cookies[0]); //wypisze nazwacookie1=wartosccookie1
    let sessID;
    if (cookies.length > 1)
        sessID = cookies[1].split("=")[1];
    else
        sessID = cookies[0].split("=")[1];

    socket.on('connect', function () {
        console.log("Client connected");
    });


    //-----------------------------------------------
    // Funkcja przygotowane do stworzenia lobby
    //-----------------------------------------------
    createLobby.onclick = function () {
        socket.emit("createLobby", {name: sessID, message: ""})
    };
    //-----------------------------------------------
    // Funkcja przygotowane do usuniecia lobby
    //-----------------------------------------------
    deleteLobby.onclick = function () {
        socket.emit("deleteLobby", {name: sessID, message: ""})
    };
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
    // Funkcja przygotowane do komunikatu o usunieciu lobby
    //-----------------------------------------------
    socket.on("eventDeleteLobby", function (data) {
        if (data.message) {
            alert(data.message);
            socket.emit("showLobbies", {name: "", message: ""});
            document.cookie = "lobbyId=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/Cortex;"
        }
    });

    //-----------------------------------------------
    // Funkcja przygotowane do wyswietlenia lobbies
    //-----------------------------------------------
    socket.on("eventShowLobbies", function (data) {
        if (data.message) {
            console.log(data.message);
            let roomsHtml = "";
            const rooms = data.message.split(",");
            for (let i = 0; i < rooms.length; i++) {
                let button = "<button class='lobby' id='" + i + "'>" + rooms[i] + "</button>";
                roomsHtml += button;
            }
            content.innerHTML = "";
            content.innerHTML = roomsHtml;

            const buttons = document.getElementsByClassName("lobby");
            for (let j = 0; j < buttons.length; j++) {
                buttons[j].addEventListener("click", function () {
                    console.log("click in button");
                    socket.emit("joinRoom", {name: sessID, message: rooms[j]});
                });
            }
        } else {
            content.innerHTML = "";
        }

    });
    //-----------------------------------------------

    //-----------------------------------------------
    // Funkcja przygotowane do dolaczenia do lobby
    //-----------------------------------------------
    socket.on("eventJoinRoom", function (data) {
        if (data) {
            console.log("Redirect");
            document.cookie = "lobbyId=" + data.message + ";";
            window.location.href = "game.html";
        }
    });
};

