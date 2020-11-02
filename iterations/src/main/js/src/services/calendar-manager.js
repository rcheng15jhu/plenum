import React from 'react';
import ReactDOM from 'react-dom'
import UploadTemplateAlert from "../components/uploadTemplateAlert";

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
    download(getObjToSave());
    console.log("Test");
}

function upload(title, content) {
    fetch("/api/addcalendar?title=" + title, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },body: JSON.stringify(content)
    }).then(data => {
        console.log(data);
    }).catch(function() {
        //handle error
        console.log("error occurred");
        document.getElementById("content").append("<UploadTemplateAlert severity='error' msg='An error occurred while uploading template!'></UploadTemplateAlert>");
    });
    let temp = document.createElement('alert');
    ReactDOM.render(<UploadTemplateAlert severity='success' msg="Template uploaded successfully!"></UploadTemplateAlert>, temp);
    document.getElementById("content").appendChild(temp);
}

function uploadTemplate() {
    let title = document.getElementById("title").value;
    if (title !== "") {
        upload(title, getObjToSave());
    } else {
        let temp = document.createElement('alert');
        ReactDOM.render(<UploadTemplateAlert severity='error' msg="Calendar title required!"></UploadTemplateAlert>, temp);
        document.getElementById("content").appendChild(temp);
    }
}

export default uploadTemplate