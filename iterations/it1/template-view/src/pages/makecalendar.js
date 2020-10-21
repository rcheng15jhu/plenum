import React from "react";
import Calendar from "../components/Calendar";
import uploadTemplate from "../services/CalendarManager.js";

const App = () => {

    return (
        <div>
            <Calendar editable={true}/>
            <button onClick={uploadTemplate} >Upload template!</button>
        </div>
    )
};

export default App