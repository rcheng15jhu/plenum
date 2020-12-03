import createAlert from "./create-alert";

function upload(title) {
    fetch("/api/addevent?title=" + title +
    "&startTime=1&endTime=9", //temporary
    {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    }).then(res => {
        if (res.status !== 201) {
            createAlert('An error occurred while creating event!', 'error');
            return undefined
        } else {
            createAlert("Event created successfully!", 'success');
            return res.json();
        }
    }).then(data => {
        console.log(data)
        console.log('/view-event?id=' + data.id)
        window.location.assign('/view-event?id=' + data.id);
    });
}

export function uploadEvent() {
    let title = document.getElementById("title").value;
    if (title !== "") {
        upload(title);
    } else {
        createAlert("Event title required!", 'error');
    }
}

export function fetchAggregate(setEventTitle, setCalendars) {
    fetch('/api/aggregate?id=' + id, {
            method: 'GET',
            mode: 'cors'
        }
    ).then(res => {
        return res.json()
    }).then(data => {
        setEventTitle(data.eventTitle)
        setCalendars(data.calendars)
    })
}