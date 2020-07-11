var temporaryCodeUrl  = "";

$(document).ready(function(){
    temporaryCodeUrl =  window["env"]["temporaryCodeUrl"];           

    var a = document.getElementById('githublink');
    a.href = temporaryCodeUrl;

});
