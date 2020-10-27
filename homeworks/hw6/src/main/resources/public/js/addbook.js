document.getElementById("submit-book").onclick = function(e) {
    if (validateISBN() && validateTitle() && validateAuthorName()) {
        fetch('/addbook?' +
        'title=' + document.getElementById("title").value + 
        '&isbn=' + document.getElementById("isbn").value + 
        '&year=' + document.getElementById("year").value + 
        '&publisher=' + document.getElementById("publisher").value + 
        '&name=' + document.getElementById("name").value + 
        '&nationality=' + document.getElementById("nationality").value + 
        '&numOfBooks=' + document.getElementById("numOfBooks").value, {
        method: 'POST',
        }
    ).then(res => window.location.reload(true));
    }
}
