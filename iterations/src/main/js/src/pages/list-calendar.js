import React, {useState, useEffect} from 'react'
import ReactDOM from 'react-dom'
import ViewableListItem from "../components/viewable-list-item";
import Header from "../components/header";
import {Typography} from "@material-ui/core";
import AddIcon from '@material-ui/icons/Add';
import Fab from "@material-ui/core/Fab";
import List from "@material-ui/core/List";
import {makeStyles} from "@material-ui/core/styles";

const useStyles = makeStyles(() => ({
    root: {
        '& > *': {
            margin: 30,
        },
    },
    center: {
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        width: '90%',
    },
}))

const App = () =>  {
    const classes = useStyles();
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
        <div style={{'paddingBottom': '100px'}}>
            <Header />
            <div  className={classes.root}>
                <Typography variant="h4" className='headingTyp'>
                    Calendar List
                </Typography>
                <div className="divContents">
                    <List>
                        {calendarNames.map(el => (
                            <ViewableListItem route='delcalendar' key={el.id} id={el.id} content={el.content} clicked={navToViewPage} />
                        ))}
                    </List>

                </div>
                <div className={classes.center}>
                    <Fab color="primary" aria-label="add" onClick={handleAdd}>
                        <AddIcon />
                    </Fab>

                </div>
            </div>
        </div>

    )
}

ReactDOM.render(<App />, document.getElementById('root'))