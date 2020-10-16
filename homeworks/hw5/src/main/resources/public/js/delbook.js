function deleteBook(bookIsbn) {
    fetch('http://localhost:7000/delbook?isbn=' + bookIsbn, {
            method: 'POST',
        }
    ).then(res => window.location.reload(true));
}

let delButtons = document.querySelectorAll("li.book > button")
Array.prototype.forEach.call(delButtons, function(button) {
    button.addEventListener('click', deleteBook.bind(null, button.className));
});