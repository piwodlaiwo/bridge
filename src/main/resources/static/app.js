var stompClient = null;

function setConnected(connected) 
{
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {$("#conversation").show();}
    else {$("#conversation").hide();}
    $("#messages").html("");
}

function connect() 
{
    var socket = new SockJS('/kadziela-bridge-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) 
    {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/chat', function (message) 
        {
            console.log('Message: '+message);
            showMessage(JSON.parse(message.body).content);
        });
        stompClient.subscribe('/topic/room', function (players) 
        {
            console.log('Players: '+players);
            console.log('Players Body: '+players.body);
            showPlayers(JSON.parse(players.body));
        });        
    });
}

function disconnect() 
{
    if (stompClient !== null) {stompClient.disconnect();}
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {stompClient.send("/app/chat", {}, JSON.stringify({'content': $("#content").val()}));}

function enterRoom() {stompClient.send("/app/room", {}, JSON.stringify({'name': $("#playerName").val()}));}

function showMessage(message) {$("#messages").append("<tr><td>" + message + "</td></tr>");}

function showPlayers(players)
{
	console.log("Players:"+ players);
	$("#players").empty();
	players.forEach(player => showPlayer(player));
}

function showPlayer(player) 
{
	console.log("Player: "+player);
	$("#players").append("<tr><td>" + player.name + "</td></tr>");
}


$(function () 
{
    $("form").on('submit', function (e) {e.preventDefault();});
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#enterRoom" ).click(function() { enterRoom(); });    
});