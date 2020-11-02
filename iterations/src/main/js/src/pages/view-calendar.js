import React, {useState, useEffect} from "react";
import ReactDOM from 'react-dom';
import Calendar from "../components/calendar";
import Viewable_list from "../components/viewable-list";
import Header from "../components/header";

const App = () => {

    let getInitId = () => {
        let paramId = parseInt(new URLSearchParams(document.location.search.substring(1)).get("id"));
        if(isNaN(paramId)) {
            window.history.replaceState({id: -1},'','/view-calendar')
            return -1;
        }
        else {
            window.history.replaceState({id: paramId},'','/view-calendar?id=' + paramId)
            return paramId;
        }
    }

    const [id, setId] = useState(getInitId)

    const [calendars, setCalendars] = useState([])

    console.log(calendars)

    const [file, setFile] = useState({})

    const [editable, setEditable] = useState(false)

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
        else {
            setFile({})
        }
    }, [id, editable])

    let updateActive = (id) => () => {
        window.history.pushState({id: id},'','/view-calendar?id=' + id)
        setId(id)
    }

    let clearCalendarView = () => {
        window.history.pushState({id: -1},'','/view-calendar')
        setId(-1)
    }

    window.onpopstate = (e) => {
        setId(e.state.id)
    }

    let onAvailChange = (date, qHour, state) => () => {
        fetch('/api/updateavailability?calendarId=' + id + "&date=" + date + "&qHour=" + qHour + "&state=" + (state ? 1 : 0), {
            method: 'POST',
            mode: 'cors'
        }).then(res => undefined)
    }

    let calendarNames = calendars.map(calendar => {return {id: calendar.id, content: calendar.title}})

    return (
        <div>
            <Header></Header>
            <button onClick={() => setEditable(!editable)}>{editable ? "Stop Edit" : "Edit"}</button>
            <Calendar editable={editable} onAvailChange={onAvailChange} file={file}/>
            <div className="divContents">
                <Viewable_list list={calendarNames} clicked={updateActive} />
            </div>
        </div>
    )
};

ReactDOM.render(<App />, document.getElementById('root'))