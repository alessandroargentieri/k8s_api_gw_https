
var securedCallUrl = window["env"]["securedCallUrl"];

$(document).ready(function(){
    
    //get temporary code query param from url
    var urlParams = new URLSearchParams(window.location.search);
    var code = urlParams.get('code');

    var clientId = window["env"]["clientId"];
    var gatewayLoginUrl = window["env"]["gatewayLoginUrl"];
    var redirectUrl = window["env"]["redirectUrl"];
    var state = window["env"]["state"];	

    securedCallUrl = window["env"]["securedCallUrl"];

    console.log(code);
    getAccessToken(gatewayLoginUrl, clientId, code, redirectUrl, state)

});

function getAccessToken(gatewayLoginUrl, clientId, code, redirectUrl, state) {
            $.ajax({
                url: gatewayLoginUrl,
                type: "POST",
                contentType: 'application/json',
                data: JSON.stringify( { "clientId": clientId, "code": code, "redirectUri": redirectUrl, "state": state } ),
                processData: false,
                dataType: "json",

                success: function (data, status, xhr) {
                    console.log(data);
                    $('#username').append(data.name);
                    document.cookie = "jwt=" + data.jwt;
                    localStorage.setItem("jwt", data.jwt);
                    console.log(document.cookie);
                },
                error: function(result) {
                    alert("Error!");
                    console.log(result);
                }
            });

        }

        $("#securedCall").click(function(e) {
            e.preventDefault();
            alert("securedCall" + securedCallUrl);
            console.log(securedCallUrl);

            $.ajax({
                url: securedCallUrl,
                type: "POST",
                headers: {
                    Authorization : "Bearer " + localStorage.getItem("jwt"),
                },
                contentType: 'application/json',
                data: JSON.stringify( {"title":"mr.","name":"alessandro","surname":"argentieri"} ),
                processData: false,
                dataType: "json",

                success: function (data, status, xhr) {
                    console.log(data);
                    $('#surname').empty();
                    $('#surname').append(data.surname);
                },
                error: function(result) {
                    alert("Error!");
                    console.log(result);
                }
            });

        });
