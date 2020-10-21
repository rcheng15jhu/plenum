import React from "react";
import Calendar from "../components/Calendar";
import uploadTemplate from "../services/CalendarManager.js";

const App = () => {

    fetch()

    return (
        <div>
            <Calendar editable={false} file={undefined}/>
            <li className="content calendar"><i>$calendar.name</i> Owned by $calendar.userId. <button
                className="$calendar.id">X</button></li>

        </div>
    )
};

export default App

