document.getElementById("submit-author").onclick = function(e) {
    fetch('http://localhost:7000/addauthor?' +
        'name=' + document.getElementById("name").value + 
        '&nationality=' + document.getElementById("nationality").value + 
        '&numOfBooks=' + document.getElementById("numOfBooks").value, {
            method: 'POST',
        }
    ).then(res => window.location.reload(true));
}
