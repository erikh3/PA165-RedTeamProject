function onSignIn(googleUser) {

    var id_token = googleUser.getAuthResponse().id_token;

    //AJAX on server for token validation
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8080/pa165/tokensignin');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send('idtoken=' + id_token);

}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    var id_token = auth2.currentUser.get().getAuthResponse().id_token;

    auth2.signOut().then(function () {

        //AJAX on server for token validation
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'http://localhost:8080/pa165/tokensignout');
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.send('idtoken=' + id_token);

    });
}