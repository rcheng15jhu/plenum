import React, {useState, useEffect} from "react";
import ReactDOM from 'react-dom';
import Calendar from "../components/calendar";
import Viewable_list from "../components/viewable-list";

const App = () => {

    const [id, setId] = useState(-1)

    const [calendars, setCalendars] = useState([])

    console.log(calendars)

    const [file, setFile] = useState({})

    useEffect(() => {
        fetch('/api/calendar', {
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
            fetch('/api/calendar?id=' + id, {
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

    let calendarNames = calendars.map(calendar => {return {id: calendar.id, content: calendar.title}})

    return (
        <div>
            <Calendar editable={false} file={file}/>
            <div className="divContents">
                <Viewable_list list={calendarNames} clicked={updateActive} />
            </div>
        </div>
    )
};

ReactDOM.render(<App />, document.getElementById('root'))