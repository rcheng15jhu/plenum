import React from "react";
import ReactDOM from 'react-dom'
import Calendar from "../components/calendar";
import Header from "../components/header";
import uploadTemplate from "../services/calendar-manager.js";

const App = () => {

    return (
        <div>
            <Header></Header>
            <Calendar editable={true}/>
            <div className="form-example">
                <label htmlFor="title">Enter your calendar title: </label>
                <input type="text" name="title" id="title"/>
            </div>
            <button onClick={uploadTemplate} >Upload template!</button>
        </div>
    )
};

ReactDOM.render(<App />, document.getElementById('root'))
