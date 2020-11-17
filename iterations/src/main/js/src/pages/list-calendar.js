import React, {useState, useEffect} from 'react'
import ReactDOM from 'react-dom'
import ViewableListItem from "../components/viewable-list-item";
import Header from "../components/header";
import {Typography} from "@material-ui/core";
import AddIcon from '@material-ui/icons/Add';
import Fab from "@material-ui/core/Fab";
import List from "@material-ui/core/List";
import {makeStyles, useTheme} from "@material-ui/core/styles";
import Grid from "@material-ui/core/Grid";
import Calendar from "../components/calendar";
import ViewCalendar from "../components/view-calendar";

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

function fetchAPI(id) {
    fetch("/api/delcalendar?id=" + id, {
            method: 'POST',
            mode: 'cors'
        }
    ).then(data => {
        location.reload();
    })
}

const App = () => {
    const classes = useStyles();
    const [calendars, setCalendars] = useState([])

    const [idToDelete, setIdToDelete] = useState(-1)

    useEffect(() => {
        fetch('/api/calendars', {
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
        if(idToDelete > 0) {
            fetchAPI(idToDelete)
            setIdToDelete(-1)
        }
    }, [idToDelete])

    const handleAdd = () => {
        window.location.assign('/create-calendar')
    }

    const handleDelete = (id) => () => {
        setIdToDelete(id);
    }



    let calendarNames = calendars.map(calendar => {return {id: calendar.id, content: calendar.title}})

    //copy ends

    // let onViewCalendar = (id) => () => {
    //     window.location.assign('/view-calendar?id=' + id)
    // }

    return (
        <div style={{'paddingBottom': '100px'}}>
            <Header />
            <Grid container spacing={3}>
                <Grid item xs={6}>
                <div  className={classes.root}>
                    <Typography variant="h4" className='headingTyp'>
                        Calendar List
                    </Typography>
                    <div className="divContents">
                        <List>
                            {calendarNames.map(el => (
                                <ViewableListItem delete={handleDelete} key={el.id} id={el.id} content={el.content}  />
                            ))}
                        </List>

                    </div>
                    <div className={classes.center}>
                        <Fab color="primary" aria-label="add" onClick={handleAdd}>
                            <AddIcon />
                        </Fab>

                    </div>
                </div>
                </Grid>
                <Grid item xs={6}>
                    <ViewCalendar />
                </Grid>
            </Grid>
        </div>

    )
}

ReactDOM.render(<App />, document.getElementById('root'))