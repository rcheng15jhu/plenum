import React from "react";
import Calendar from "../components/Calendar";
import uploadTemplate from "../services/CalendarManager.js";

const App = () => {

    return (
        <div>
            <Calendar editable={true}/>
<<<<<<< HEAD:iterations/it1/template-view/src/pages/makecalendar.js
=======
            <div className="form-example">
                <label htmlFor="title">Enter your calendar title: </label>
                <input type="text" name="title" id="title"/>
            </div>
>>>>>>> iteration3:iterations/old_js/src/pages/makecalendar.js
            <button onClick={uploadTemplate} >Upload template!</button>
        </div>
    )
};

export default App