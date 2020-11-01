import React from 'react'
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

    return (
        <div className="divContents">
            <Viewable_list list={calendarNames} clicked={updateActive} />
        </div>
    )
}

ReactDOM.render(<App />, document.getElementById('root'))