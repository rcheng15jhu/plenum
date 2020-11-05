<<<<<<< HEAD
document.getElementById("submit-author").onclick = function(e) {
    e.preventDefault()
    if (validateAuthorName()) {
        fetch('/addauthor?' +
        'name=' + document.getElementById("name").value + 
        '&nationality=' + document.getElementById("nationality").value + 
        '&numOfBooks=' + document.getElementById("numOfBooks").value,
            {
                method: 'POST',
            }
    ).then(res => window.location.reload(true));
    }
}
=======
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
>>>>>>> iteration3
