import React from "react";
import Calendar from "../components/Calendar";
import uploadTemplate from "../services/CalendarManager.js";

const App = () => {

    return (
        <div>
            <Calendar />
            <div className="form-example">
                <label htmlFor="event">Enter your event id: </label>
                <input type="text" name="event" id="event"/>
            </div>
            <button onClick={uploadTemplate} >Upload template!</button>
        </div>
    )
};

export default App