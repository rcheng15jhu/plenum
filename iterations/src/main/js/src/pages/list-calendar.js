import React, {useState, useEffect} from 'react'
import ReactDOM from 'react-dom'
import ViewableListItem from "../components/viewable-list-item";
import Header from "../components/header";
import {Typography} from "@material-ui/core";
import AddIcon from '@material-ui/icons/Add';
import Fab from "@material-ui/core/Fab";
import List from "@material-ui/core/List";
import {makeStyles} from "@material-ui/core/styles";
import Grid from "@material-ui/core/Grid";
import ViewCalendar from "../components/view-calendar";
import getCookie from "../services/get-cookie";

const useStyles = makeStyles(() => ({
    root: {
        '& > *': {
            overflowX: 'hidden'
        },
        overflowX: 'hidden',
        paddingBottom: '100px'
    },
    center: {
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        width: '90%',
        paddingBottom: '30px'
    },
    contents: {
        '& > *': {
            margin: '30px'
        },
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

    if(getCookie('username') === ""){
        window.location.assign('/')
    }

    const classes = useStyles();
    const [calendars, setCalendars] = useState([])

    const [idToDelete, setIdToDelete] = useState(-1)
    const [viewId, setViewId] = useState(-1)

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

    const viewListClicked = (id) => () => {
        setViewId(id);
    }

    let calendarNames = calendars.map(calendar => {return {id: calendar.id, content: calendar.title}})

    return (
        <div className={classes.root}>
            <Header />
            <Grid container spacing={3}>
                <Grid item xs={6}>
                <div  className={classes.contents}>
                    <Typography variant="h4" className='headingTyp'>
                        Your Calendars
                    </Typography>
                    <div className="divContents">
                        <List>
                            {calendarNames.map(el => (
                                <ViewableListItem delete={handleDelete} key={el.id} id={el.id} content={el.content} clicked={viewListClicked} />
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
                    <ViewCalendar id={viewId} />
                </Grid>
            </Grid>
        </div>

    )
}

ReactDOM.render(<App />, document.getElementById('root'))