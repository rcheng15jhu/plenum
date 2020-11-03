import React, {useState, useEffect} from 'react'
import ReactDOM from 'react-dom'
import Viewable_list from "../components/viewable-list";
import Header from "../components/header";
import {Typography} from "@material-ui/core";
import AddIcon from '@material-ui/icons/Add';
import Fab from "@material-ui/core/Fab";

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

    const handleAdd = () => {
        window.location.assign('/create-calendar')
    }


    let calendarNames = calendars.map(calendar => {return {id: calendar.id, content: calendar.title}})

    //for testing:
    // let calendarNames = [
    //     {
    //         id: 1,
    //         content: 'title1'
    //     },
    //     {
    //         id: 2,
    //         content: 'title2'
    //     },
    // ];

    let navToViewPage = (id) => () => {
        window.location.assign('/view-calendar?id=' + id)
    }

    return (
        <div>
            <Header />
            <Typography variant="h6">
                Calendar List
            </Typography>
            <div className="divContents">
                <Viewable_list list={calendarNames} clicked={navToViewPage} />
            </div>
            <Fab color="primary" aria-label="add" onClick={handleAdd}>
                <AddIcon />
            </Fab>

        </div>

    )
}

ReactDOM.render(<App />, document.getElementById('root'))