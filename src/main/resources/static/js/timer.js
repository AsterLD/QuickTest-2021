let timeCounter = 0;
let seconds = 0;
let minutes = 0;
let hours = 0;

setInterval(function() {
    timeCounter++
    seconds = parseInt(timeCounter % 60, 10);
    minutes = parseInt((timeCounter / 60) % 60, 10);
    hours = parseInt((timeCounter / 3600) % 60, 10);
    document.getElementById("resultTime").value = hours + ":" +  minutes  + ":" + seconds;
}, 1000);
