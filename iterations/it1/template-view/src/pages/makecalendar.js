import React from "react";
import Calendar from "../components/Calendar";
import uploadTemplate from "../services/CalendarManager.js";

const App = () => {

    return (
        <div>
            <Calendar editable={true}/>
            <div className="form-example">
                <label htmlFor="title">Enter your calendar title: </label>
                <input type="text" name="title" id="title"/>
            </div>
            <button onClick={uploadTemplate} >Upload template!</button>
        </div>
    )
};

export default App