import createAlert from "./create-alert";
import {useEffect} from "react";

//This function uploads events according to the title.
function upload(title, startTime, endTime) {
    fetch("/api/addevent?title=" + title +
    "&startTime=" + startTime + "&endTime=" + endTime, //temporary
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

//This function implements the process of uploading events.
export function uploadEvent() {
    let title = document.getElementById("title").value;
    let startTime = document.getElementById("startTime").value;
    let endTime = document.getElementById("endTime").value;
    if (title !== "") {
        upload(title, startTime, endTime);
    } else {
        createAlert("Event title required!", 'error');
    }
}

export function fetchAggregate(id, setEventTitle, setCalendars) {
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

export function getEvents(setEvents, param=''){
    const request = `/api/events${param}`
    useEffect(() => {
        fetch(request, {
                method: 'GET',
                mode: 'cors'
            }
        ).then(res => {
            return res.json()
        }).then(data => {
            console.log(data)
            setEvents([...data])
        })
    }, [])
}