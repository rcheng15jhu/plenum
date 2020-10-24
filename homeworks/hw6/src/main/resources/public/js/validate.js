function validateUsername() {
    const name = document.getElementById("username");
    if (name.value.length < 1) {
        alert("Username cannot be empty!");
        return false;
    } else {
        return true;
    }
}

function validateAuthorName() {
    const name = document.getElementById("name").value;
    const re = /^([A-Za-z]+) +([A-Za-z]+)[ A-Za-z]*$/;
    if (re.test(name)) {
        return true
    }
    alert("Invalid name")
    return false

}

function validateISBN() {
    const isbn = document.getElementById("isbn").value;
    const re = /(^[0-9]{10,10}$)|(^[0-9]{3,3}-[0-9]{10,10}$)/;
    if (re.test(isbn)) {
        return true;
    }
    alert("Invalid ISBN")
    return false;
}

function validateTitle() {
    if (document.getElementById("title").value.length > 0) {
        return true
    }
    alert("Title cannot be empty")
    return false;
}

