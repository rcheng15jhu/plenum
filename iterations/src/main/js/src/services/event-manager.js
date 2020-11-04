import createAlert from "./create-alert";

function upload(title) {
    fetch("/api/addevent?title=" + title + 
    "&startTime=0&endTime=9", //temporary
    {
        method: 'POST',
    }).then(data => {
        //console.log(data);
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