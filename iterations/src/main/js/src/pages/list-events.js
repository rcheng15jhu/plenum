import React, {useState, useEffect} from 'react'
import ReactDOM from 'react-dom'
import ViewableListItem from "../components/viewable-list-item";
import Header from "../components/header";
import {Typography} from "@material-ui/core";
import AddIcon from '@material-ui/icons/Add';
import Fab from "@material-ui/core/Fab";
import List from "@material-ui/core/List";
import {makeStyles} from "@material-ui/core/styles";
import CollapsibleAlert from "../components/collapsibleAlert";
import Button from "@material-ui/core/Button";
import {checkCookie} from "../services/cookie-manager";
import Grid from "@material-ui/core/Grid";
import Aggregate_calendar from "../components/aggregate-calendar";
import {fetchAggregate} from "../services/event-manager";

const useStyles = makeStyles(() => ({
    root: {
        '& > *': {
            margin: 30,
        },
        overflowX: 'hidden',
    },
    center: {
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        width: '90%',
    },
}))

function fetchAPI(id) {
    fetch("/api/delevent?id=" + id, {
            method: 'POST',
            mode: 'cors'
        }
    ).then(data => {
        location.reload();
    })
}

const App = () =>  {

    checkCookie();

    const classes = useStyles();
    const [events, setEvents] = useState([])

    useEffect(() => {
        fetch('/api/events', {
                method: 'GET',
                mode: 'cors'
            }
        ).then(res => {
            return res.json()
        }).then(data => {
            console.log(data)
            setEvents([...data])
        })
    }, [])

    let getInitId = () => {
        let paramId = parseInt(new URLSearchParams(document.location.search.substring(1)).get("id"));
        if(isNaN(paramId)) {
            window.history.replaceState({id: -1},'','/list-events')
            return -1;
        }
        else {
            window.history.replaceState({id: paramId},'','/list-events?id=' + paramId)
            return paramId;
        }
    }

    const [id, setId] = useState(getInitId)

    const [idToDelete, setIdToDelete] = useState(-1)

    const [calendars, setCalendars] = useState([])

    const [eventTitle, setEventTitle] = useState(null)

    useEffect(() => {
        if(idToDelete > 0) {
            fetchAPI(idToDelete)
            setIdToDelete(-1)
        }
    }, [idToDelete])

    const handleAdd = () => {
        window.location.assign('/create-event')
    }

    const handleDelete = (id) => () => {
        setIdToDelete(id);
    }

    let eventNames = events.map(event => {return {id: event.id, content: event.title}})


    let updatePreview = (id) => () => {
        window.history.pushState({id: id},'','/list-events?id=' + id)
        setId(id)
    }

    let clearCalendarView = () => {
        window.history.pushState({id: -1},'','/list-events')
        setId(-1)
    }

    window.onpopstate = (e) => {
        setId(e.state.id)
    }

    let navToViewPage = (id) => () => {
            window.location.assign('/view-event?id=' + id)
    }


    fetchAggregate(setEventTitle, setCalendars);


    return (
        <div style={{'paddingBottom': '100px'}}>
            <Header />
            <div  className={classes.root}>
                <Typography variant="h4" className='headingTyp'>
                    Your Events
                </Typography>

                <Grid container spacing={3}>
                    <Grid item xs={6}>
                        <Typography variant="h6" className='headingTyp'>
                            Private Events
                        </Typography>
                    </Grid>
                    <Grid item xs={6}>
                        <Typography variant="h6" className='headingTyp'>
                            Previewing: {eventTitle}
                        </Typography>
                    </Grid>
                    <Grid item xs={6}>
                        <List>
                            {eventNames.map(el => (
                                <ViewableListItem delete={handleDelete} key={el.id} id={el.id} content={el.content} clicked={updatePreview} />
                            ))}
                        </List>
                        <div className={classes.center}>
                            <Fab color="primary" aria-label="add" onClick={handleAdd}>
                                <AddIcon />
                            </Fab>

                        </div>
                    </Grid>
                    <Grid item xs={6}>
                        <Aggregate_calendar agg={calendars}> </Aggregate_calendar>
                        {id > 0?
                            <Button style={{'margin' : '30px 0 0 50px'}} variant='contained' color='primary' onClick={navToViewPage(id)}>Go to Event</Button>
                            :
                            <div/>
                        }
                    </Grid>
                </Grid>
                <Typography variant="h6" className='headingTyp'>
                    Public Events
                </Typography>
                <Button variant="outlined" href='list-public-events' color='primary'>View all public events!</Button>
                {/*<UploadTemplateAlert msg={<a href='list-public-events'>Show all public events!</a>} severity={'success'} />*/}
            </div>
        </div>

    )
}

ReactDOM.render(<App />, document.getElementById('root'))