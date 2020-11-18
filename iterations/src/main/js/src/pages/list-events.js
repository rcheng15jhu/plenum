import React, {useState, useEffect} from 'react'
import ReactDOM from 'react-dom'
import ViewableListItem from "../components/viewable-list-item";
import Header from "../components/header";
import {Typography} from "@material-ui/core";
import AddIcon from '@material-ui/icons/Add';
import Fab from "@material-ui/core/Fab";
import List from "@material-ui/core/List";
import {makeStyles} from "@material-ui/core/styles";
import UploadTemplateAlert from "../components/uploadTemplateAlert";
import Button from "@material-ui/core/Button";
import getCookie from "../services/get-cookie";
import Grid from "@material-ui/core/Grid";
import Aggregate_calendar from "../components/aggregate-calendar";

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

    if(getCookie('username') === ""){
        window.location.assign('/')
    }

    const classes = useStyles();
    const [events, setEvents] = useState([])

    const [idToDelete, setIdToDelete] = useState(-1)

    const [calendars, setCalendars] = useState([])

    const [eventTitle, setEventTitle] = useState(null)

    const [id, setId] = useState(-1)

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
        setId(id)
    }

    let navToViewPage = (id) => () => {
            window.location.assign('/view-event?id=' + id)
    }


    useEffect(() => {
        if(id > 0) {
            fetch('/api/aggregate?id=' + id, {
                    method: 'GET',
                    mode: 'cors'
                }
            ).then(res => {
                return res.json()
            }).then(data => {
                setEventTitle(data.eventTitle)
                setCalendars(data.calendars)
            })
        }
    }, [id])


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

                            <div className="divContents">
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
                            </div>
                        </Grid>
                        <Grid item xs={6}>
                            <Aggregate_calendar agg={calendars}> </Aggregate_calendar>
                            {id > 0?
                                <Button style={{'margin' : '30px 0 0 50px'}} variant='contained' color='primary' onClick={navToViewPage(id)}>Go to Event</Button>
                                :
                                <div></div>
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