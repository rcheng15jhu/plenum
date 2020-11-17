import React, { useEffect, useState } from 'react'
import ReactDOM from 'react-dom'
import Aggregate_calendar from "../components/aggregate-calendar";
import List_menu from '../components/list-menu'
import Header from "../components/header";
import { makeStyles, useTheme } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import UploadTemplateAlert from '../components/uploadTemplateAlert'
import createAlert from "../services/create-alert";
import getCookie from "../services/get-cookie";
import Grid from "@material-ui/core/Grid";
import Calendar from "../components/calendar";
import PublishIcon from '@material-ui/icons/Publish';
import Button from "@material-ui/core/Button";
import newTheme from "../components/baseline-theme";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import {Add, Minimize} from "@material-ui/icons";
import Collapse from "@material-ui/core/Collapse";
import ListItemText from "@material-ui/core/ListItemText";
import ListItem from "@material-ui/core/ListItem";
import List from "@material-ui/core/List";
import {grey} from "@material-ui/core/colors";

const useStyles = makeStyles((theme) => ({
    root: {
        display: 'flex',
        paddingLeft: theme.spacing(4)
    },
    details: {
        display: 'flex',
        flexDirection: 'column',
    },
    content: {
        flex: '1 0 auto',
    },
    title: {
        fontWeight: "bold",
        marginBottom: theme.spacing(4),
    },
    button: {
        marginLeft: theme.spacing(2),
        marginTop: "10px"
    },
    listItem: {
        background: grey[200]
    }
}));

const App = () => {

    if(getCookie('username') === ""){
        window.location.assign('/')
    }

    let getInitId = () => {
        let paramId = parseInt(new URLSearchParams(document.location.search.substring(1)).get("id"));
        if (isNaN(paramId)) {
            window.history.replaceState({ id: -1 }, '', '/view-event')
            return -1;
        }
        else {
            window.history.replaceState({ id: paramId }, '', '/view-event?id=' + paramId)
            return paramId;
        }
    }

    const [id, setId] = useState(getInitId)

    const [calendars, setCalendars] = useState({})

    const [calOptions, setCalOptions] = useState(null)

    const [selectedCal, setSelectedCal] = useState(null)

    const [eventTitle, setEventTitle] = useState(null)

    const [file, setFile] = useState({})

    const [open, setOpen] = useState(false)

    const classes = useStyles();
    const theme = useTheme();

    useEffect(() => {
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
    }, [])

    useEffect(() => {
        fetch('/api/calendar', {
            method: 'GET',
            mode: 'cors'
        }
        ).then(res => {
            return res.json()
        }).then(data => {
            setCalOptions(data)
            if (data !== undefined && data.length != 0) {
                setSelectedCal(data[0].title)
            }
        })
    }, [])

    useEffect(() => {


        if(calOptions) {
            let calid = ""
            for (let i = 0; i < calOptions.length; i++) {
                if (calOptions[i].title === selectedCal) {
                    calid = calOptions[i].id
                }
            }

            fetch('/api/calendar?id=' + calid, {
                    method: 'GET',
                    mode: 'cors'
                }
            ).then(res => {
                return res.json()
            }).then(data => {
                setFile(data)
            })
        }
        else {
            setFile({})
        }
    }, [selectedCal])

    let updateActive = (id) => () => {
        window.history.pushState({ id: id }, '', '/view-event?id=' + id)
        setId(id)
    }

    let clearCalendarView = () => {
        window.history.pushState({ id: -1 }, '', '/view-event')
        setId(-1)
    }

    window.onpopstate = (e) => {
        setId(e.state.id)
    }

    function handleMenuChange(cal) {
        setSelectedCal(cal)
    }

    function upload() {
        let a = ""
        for (let i = 0; i < calOptions.length; i++) {
            if (calOptions[i].title === selectedCal) {
                a = calOptions[i].id
            }
        }
        fetch("/api/updateconnection?eventId=" + id +
        "&calendarId=" + a,
        {
            method: 'POST',
        }).then(data => {
            if (data.status !== 201) {
                createAlert('An error occurred while uploading calendar!', 'error');
            } else {
                createAlert("Calendar uploaded successfully!", 'success');
                window.location.assign('/view-event?id=' + id);
            }
        })
    }

    const handleClickList = () => {
        setOpen(!open);
    };
    
    function renderDropdown() {
        if (calOptions === null) {
            return null
        } else if (calOptions.length == 0) {
            return <UploadTemplateAlert msg={<a href='create-calendar'>Create a new calendar as template!</a>} severity={'error'} ></UploadTemplateAlert>
        } else {
            return (
                <div>
                    <List_menu options={calOptions.map(element => element.title)} onChange={handleMenuChange} ></List_menu>


                    {selectedCal ?
                        <Calendar editable={0} onAvailChange={null} file={file}/>
                        :
                        <p>Choose a calendar to view.</p>
                    }
                    <Button
                                variant="contained"
                                color="secondary"
                                onClick={() => upload()}
                                className={classes.button}
                                startIcon={<PublishIcon />}>
                                Use calendar
                            </Button>
                </div>
            )
        }
    } 

    return (
        <div>
            <ThemeProvider theme={newTheme}>

            <Header></Header>
        <Grid container spacing={3} className={classes.root}>
        <Grid item xs={6}  id='content'>

        <Card>
                <div className={classes.details}>
                    <CardContent className={classes.content}>
                        <Typography component="h4" variant="h4" className={classes.title}>
                            Event: {eventTitle}
                        </Typography>
                    </CardContent>
                    <Card>
                        <Aggregate_calendar agg={calendars}> </Aggregate_calendar>
                    </Card>
                </div>
            </Card>

        </Grid>
            <Grid item xs={6}>

                <ListItem
                    className="list"
                    button
                    onClick={handleClickList}
                    className={classes.listItem}
                >
                    <ListItemText
                        primary={'Select calendar template'}
                    />
                    {open ? <Minimize /> : <Add />}
                </ListItem>
                <Collapse in={open} timeout="auto" unmountOnExit>
                    <List component="div" disablePadding>
                        <ListItem button className={classes.nested}>
                            <ListItemText primary={renderDropdown()} />
                        </ListItem>
                    </List>
                </Collapse>
            </Grid>
            <Grid item xs={12}>
                <p className={classes.title}>
                    Share event: {window.location.href}
                </p>
            </Grid>
        </Grid>
            </ThemeProvider>
        </div>
    )
};

ReactDOM.render(<App />, document.getElementById('root'))