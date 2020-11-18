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

    let getInitId = () => {
        let paramId = parseInt(new URLSearchParams(document.location.search.substring(1)).get("id"));
        if(isNaN(paramId)) {
            window.history.replaceState({id: -1},'','/list-calendar')
            return -1;
        }
        else {
            window.history.replaceState({id: paramId},'','/list-calendar?id=' + paramId)
            return paramId;
        }
    }

    const [idToDelete, setIdToDelete] = useState(-1)
    const [viewId, setViewId] = useState(getInitId)

    useEffect(() => {
        if(idToDelete > 0) {
            fetchAPI(idToDelete)
            setIdToDelete(-1)
        }
    }, [idToDelete])

    const viewListClicked = (id) => () => {
        window.history.pushState({id: id},'','/list-calendar?id=' + id)
        setViewId(id);
    }

    let clearCalendarView = () => {
        window.history.pushState({id: -1},'','/list-calendar')
        setViewId(-1)
    }

    window.onpopstate = (e) => {
        setViewId(e.state.id)
    }

    const handleAdd = () => {
        window.location.assign('/create-calendar')
    }

    const handleDelete = (id) => () => {
        setIdToDelete(id);
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