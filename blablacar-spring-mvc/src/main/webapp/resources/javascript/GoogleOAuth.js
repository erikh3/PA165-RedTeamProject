function onSignIn(googleUser) {
    // var profile = googleUser.getBasicProfile();
    // console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    // console.log('Name: ' + profile.getName());
    // console.log('Image URL: ' + profile.getImageUrl());
    // console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.

    var id_token = googleUser.getAuthResponse().id_token;
    console.log('Token: ' + id_token);

    //TODO: AJAX on server
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8080/pa165/tokensignin');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function() {
        console.log('Signed in as: ' + xhr.responseText);
    };
    xhr.send('idtoken=' + id_token);

    //TODO: Response bude:
    // @RequestMapping(value = "/patientdetails", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    // public @ResponseBody List<PatientProfileDto> getPatientDetails(
    //     PatientProfileDto name) {
    //
    //
    //     List<PatientProfileDto> list = new ArrayList<PatientProfileDto>();
    //     list = service.getPatient(name);
    //     return list;
    // }
}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        console.log('User signed out.');

        //TODO: AJAX on server
    });
}