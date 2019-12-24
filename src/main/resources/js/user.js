window.onload = function () {
    var imageButton = document.getElementById("imageButton");
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
        console.log(name.value)
        if (name.value == "") {
            alert("Please type your name!");
        } else {
            var text = message.value;
            console.log(name.value + ': ' + text);
            socket.emit('image', {login: text, password: name.value});
        }
    };

}