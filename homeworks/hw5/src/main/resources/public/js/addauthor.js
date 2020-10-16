function addAuthor(name, nationality, numOfBooks) {
    fetch('http://localhost:7000/addauthor?name=' + name + '&nationality=' + nationality + '&numOfBooks=' + numOfBooks, {
            method: 'POST',
        }
    ).then(res => window.location.reload(true));
}

let addButton = document.querySelectorAll("li.book > button")
Array.prototype.forEach.call(delButtons, function(button) {
    button.addEventListener('click', deleteBook.bind(null, button.className));
});