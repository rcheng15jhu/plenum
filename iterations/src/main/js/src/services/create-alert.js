import React from 'react';
import ReactDOM from 'react-dom';
import CollapsibleAlert from "../components/collapsibleAlert";

export default function createAlert(msg, severity) {
    let temp = document.createElement('alert');
    ReactDOM.render(<CollapsibleAlert severity={severity} msg={msg}></CollapsibleAlert>, temp);
    document.getElementById("content").appendChild(temp);
}


