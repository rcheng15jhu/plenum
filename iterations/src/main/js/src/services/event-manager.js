import createAlert from "./create-alert";

function upload(title) {
    fetch("/api/addevent?title=" + title,
    //"&startTime=1&endTime=9", //temporary
    {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    }).then(data => {
        if (data.status !== 201) {
            createAlert('An error occurred while creating event!', 'error');
        } else {
            createAlert("Event created successfully!", 'success');
            window.location.assign('/list-events');
        }
    });
}

function uploadEvent() {
    let title = document.getElementById("title").value;
    if (title !== "") {
        upload(title);
    } else {
        createAlert("Event title required!", 'error');
    }
}

export default uploadEvent