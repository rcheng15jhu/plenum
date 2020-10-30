import React, {useState, useEffect} from "react";
import Calendar from "../components/Calendar";
import uploadTemplate from "../services/CalendarManager.js";

const App = () => {

    const [id, setId] = useState(-1)

    const [calendars, setCalendars] = useState([])

    console.log(calendars)

    const [file, setFile] = useState({})

    useEffect(() => {
        fetch('/calendar', {
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

    useEffect(() => {
        if(id > 0) {
            fetch('/calendar?id=' + id, {
                    method: 'GET',
                    mode: 'cors'
                }
            ).then(res => {
                return res.json()
            }).then(data => {
                console.log(data)
                setFile(data)
            })
        }
    }, [id])

    let updateActive = (id) => () => {
        setId(id)
    }


    return (
        <div>
            <Calendar editable={false} file={file}/>
            <div className="divContents">
                <ol>
                    <p>
                        {
                            calendars.map(calendar => (
                                <li key={calendar.id} className="content calendar">
                                    <i>{calendar.name}</i> Owned by
                                    {" " + calendar.userId}.
                                    <button onClick={updateActive(calendar.id)} className={calendar.userId}>View</button></li>
                            ))
                        }
                    </p>
                </ol>
            </div>
        </div>
    )
};

export default App

