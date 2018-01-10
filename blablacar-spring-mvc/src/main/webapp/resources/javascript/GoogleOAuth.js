
(function($)
{

    $( document ).ready(function() {
        $("#sign-out-btn").on("click",function (e) {
            signOut();
        });
        $('#sign-in-out-button').on('click', function (e) {
        })

    });

})(jQuery);


function onSignIn(googleUser) {

    var id_token = googleUser.getAuthResponse().id_token;

    //AJAX on server for token validation
    // var xhr = new XMLHttpRequest();
    // xhr.open('POST', 'http://localhost:8080/pa165/tokensignin');
    // xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    // xhr.send('idtoken=' + id_token);
    $.ajax({
        type: "POST",
        beforeSend: function(request) {
            request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        },
        url: "/pa165/tokensignin",
        data: 'idtoken=' + id_token,
        processData: false,
        success: function(msg) {
            window.location = "/pa165";
        }
    });

}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    var id_token = auth2.currentUser.get().getAuthResponse().id_token;

    auth2.signOut().then(function () {

        //AJAX on server for token validation
        // var xhr = new XMLHttpRequest();
        // xhr.open('POST', 'http://localhost:8080/pa165/tokensignout');
        // xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        // xhr.send('idtoken=' + id_token);

        $.ajax({
            type: "POST",
            beforeSend: function(request) {
                request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            },
            url: "/pa165/tokensignoutsecure",
            data: 'idtoken=' + id_token,
            processData: false,
            success: function(msg) {
                window.location = "/pa165";
            }
        });
    });
}

function onLoad() {
    gapi.load('auth2', function() {
        gapi.auth2.init({
            client_id: '332736943859-mrr2173fc1kseq1l2i4h0na68mnpmbp3.apps.googleusercontent.com',
            scope: 'profile'
        });
    });
}