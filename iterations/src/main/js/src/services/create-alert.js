import React from 'react';
import ReactDOM from 'react-dom';
import UploadTemplateAlert from "../components/uploadTemplateAlert";

export default function createAlert(msg, severity) {
    let temp = document.createElement('alert');
    ReactDOM.render(<UploadTemplateAlert severity={severity} msg={msg}></UploadTemplateAlert>, temp);
    document.getElementById("content").appendChild(temp);
}


