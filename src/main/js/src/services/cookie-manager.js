//This is to get the cookie and pass the name
export function cookieManager(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

//This is to check if the cookie exists and pass the cookie to other functions.
export function checkCookie() {
    if(cookieManager('username') === ""){
        window.location.assign('/')
    }
}
