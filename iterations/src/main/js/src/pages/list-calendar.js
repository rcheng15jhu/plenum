import React, {useState, useEffect} from 'react'
import ReactDOM from 'react-dom'
import Viewable_list from "../components/viewable-list";

const App = () =>  {

    const [calendars, setCalendars] = useState([])

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


    let calendarNames = calendars.map(calendar => {return {id: calendar.id, content: calendar.title}})

    let navToViewPage = (id) => () => {
        window.location.assign('/view-calendar?id=' + id)
    }


    return (
        <div className="divContents">
            <Viewable_list list={calendarNames} clicked={navToViewPage} />
        </div>

    )
}

ReactDOM.render(<App />, document.getElementById('root'))