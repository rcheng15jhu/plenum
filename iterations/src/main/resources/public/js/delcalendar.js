function deleteCalendar(calId) {
    fetch('http://localhost:7000/delcalendar?id=' + calId, {
            method: 'POST',
        }
    ).then(res => window.location.reload(true));
}

let delButtons = document.querySelectorAll("li.calendar > button")
Array.prototype.forEach.call(delButtons, function(button) {
    button.addEventListener('click', deleteCalendar.bind(null, button.className));
});