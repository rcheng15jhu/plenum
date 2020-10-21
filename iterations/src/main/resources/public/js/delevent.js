function deleteEvent(eventId) {
    fetch('http://localhost:7000/delevent?id=' + eventId, {
            method: 'POST',
        }
    ).then(res => window.location.reload(true));
}

let delButtons = document.querySelectorAll("li.event > button")
Array.prototype.forEach.call(delButtons, function(button) {
    button.addEventListener('click', deleteEvent.bind(null, button.className));
});