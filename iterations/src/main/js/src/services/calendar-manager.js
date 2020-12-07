import React, {useEffect} from 'react';
import createAlert from "./create-alert";

function download(content) {
    const a = document.createElement("a");
    const file = new Blob([JSON.stringify(content)], {type: "application/json"});
    a.href = URL.createObjectURL(file);
    a.download = "template.json";
    a.click();
    URL.revokeObjectURL(a.href);
}

function getObjToSave() {
    let objToSave = {};
    objToSave.dates = [];
    for (let i = 0; i < 7; i++) {
        const buttons = [...document.getElementsByClassName("day" + i)];
        console.log(buttons);
        let times = [];
        buttons.forEach(button => {
            let value = parseInt(button.value);
            console.log(value);
            if(value !== -1)
            times.push(value);
        });
        if(times.length !== 0) {
            objToSave.dates.push({
                date: i,
                times: times
            })
        }
    }
    return objToSave;
}

function downloadTemplate() {
    download(arguments[0] === undefined ? getObjToSave() : arguments[0]);
    console.log("Test");
}

//This function is the one for uploading the calendar according to the calendar and title.
function upload(title, content) {
    fetch("/api/addcalendar?title=" + title, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },body: JSON.stringify(content)
    }).then(data => {
        console.log(data);
        if (data.status !== 201) {
            createAlert('An error occurred while uploading template!', 'error');
        } else {
            createAlert("Template uploaded successfully!", 'success');
            window.location.assign('/list-calendar');
        }


    });
}

//This function is to implement the process of uploading a calendar and alerts if the title is empty.
export default function uploadTemplate() {
    let title = document.getElementById("title").value;
    if (title !== "") {
        upload(title, arguments[0] === undefined ? getObjToSave() : arguments[0]);
    } else {
        createAlert("Calendar title required!", 'error');
    }
}

// This function is used in calendar.js and aggregate-calendar.js.
// It takes the time slot and returns the correct time to display
export function getTime(val) {
    let isAM = val < 12 * 4
    return ((val / 4 + 11) % 12 + 1) + ' ' + (isAM ? 'am' : 'pm')
}

// This function is used in calendar.js and aggregate-calendar.js.
// It makes 1D array into 2D
export function populateCalendar(calendar){
    for (let i = 0; i < calendar.length; i++) {
        calendar[i] = new Array(7)
    }
    return calendar;
}

//Get all calendars from backend
export function getCalendars(setCalendars){
    useEffect(() => {
        fetch('/api/calendars', {
                method: 'GET',
                mode: 'cors'
            }
        ).then(res => {
            return res.json()
        }).then(data => {
            console.log(data)
            setCalendars([...data])
        })
    }, [])
}
