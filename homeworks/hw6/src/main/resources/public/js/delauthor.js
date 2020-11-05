function deleteAuthor(authorName) {
<<<<<<< HEAD
    fetch('/delauthor?name=' + authorName, {
=======
    fetch('http://localhost:7000/delauthor?name=' + authorName, {
>>>>>>> iteration3
            method: 'POST',
        }
    ).then(res => window.location.reload(true));
}

let delButtons = document.querySelectorAll("li.author > button")
Array.prototype.forEach.call(delButtons, function(button) {
    button.addEventListener('click', deleteAuthor.bind(null, button.className));
});