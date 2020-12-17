import React, {useState} from "react";
import ReactDOM from 'react-dom'
import makeStyles from "@material-ui/core/styles/makeStyles";
import Container from "@material-ui/core/Container";
import {Typography} from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import theme from "../components/baseline-theme";
import Header from "../components/header";
import {cookieManager, checkCookie} from "../services/cookie-manager";
import {useGetCalendars} from "../services/calendar-manager";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import {getEvents} from "../services/event-manager";
import {indigo} from "@material-ui/core/colors";
import VisibilityIcon from '@material-ui/icons/Visibility';
import Button from "@material-ui/core/Button";

const useStyles = makeStyles((theme) => ({
    contentDiv: {
        width: '90%',
        margin: '10px auto',
        padding: '50px 50px',
    },
    button: {
        marginTop: '30px',
    },
    title: {
        marginBottom: '20px',
        borderBottom: `5px solid ${indigo[600]}`,
    },
    subtitle: {
        color: `${indigo[200]}`
    }
}));

const App = () => {

    checkCookie();

    const classes = useStyles();

    const username = cookieManager('username');

    const [calendars, setCalendars] = useState([]);
    const [events, setEvents] = useState([]);
    const [publicEvents, setPublicEvents] = useState([]);

    useGetCalendars(setCalendars)

    getEvents(setEvents)

    getEvents(setPublicEvents, '?all=true')

    let calendarNames = calendars.map(calendar => {return {id: calendar.id, content: calendar.title}})
    let eventNames = events.map(event => {return {id: event.id, content: event.title}})
    let publicEventNames = publicEvents.map(event => {return {id: event.id, content: event.title}})

    return (
        <ThemeProvider theme={theme}>
            <Header />
            <Container className={classes.root}>
                <div className={classes.contentDiv}>
                    <div className={classes.title}>
                        <Typography variant='h3' color='secondary'>
                            Hi, {username}!
                        </Typography>
                        <Typography variant='subtitle1' className={classes.subtitle}>
                            Here is an overview of your items
                        </Typography>
                    </div>
                    <Grid container spacing={3}>
                        <Grid item xs={6}>
                            <Typography variant='h5'>
                                Your Calendars
                            </Typography>
                            <List>
                                {calendarNames.map(el => (
                                    <ListItem>
                                        <Typography variant='h6'>
                                            {el.content}
                                        </Typography>
                                    </ListItem>
                                ))}
                            </List>
                            <Button variant='contained' size='large' href="/list-calendar" color="secondary">
                                <VisibilityIcon style={{'marginRight': '5px'}} />
                                Manage Calendars
                            </Button>
                        </Grid>
                        <Grid item xs={6}>
                            <Typography variant='h5'>
                                Your Events
                            </Typography>
                            <List>
                                {eventNames.map(el => (
                                    <ListItem>
                                        <Typography variant='h6'>
                                            {el.content}
                                        </Typography>
                                    </ListItem>
                                ))}
                            </List>
                            <Button variant='contained' size='large' href="/list-events" color="secondary">
                                <VisibilityIcon style={{'marginRight': '5px'}} />
                                Manage Events
                            </Button>
                        </Grid>
                        <Grid item xs={6}>
                            <Typography variant='h5'>
                                Public Events
                            </Typography>
                            <List>
                                {publicEventNames.map(el => (
                                    <ListItem>
                                        <Typography variant='h6'>
                                            {el.content}
                                        </Typography>
                                    </ListItem>
                                ))}
                            </List>
                            <Button variant='contained' size='large' href="/list-public-events" color="secondary">
                                <VisibilityIcon style={{'marginRight': '5px'}} />
                                View Public Events
                            </Button>
                        </Grid>
                    </Grid>
                </div>
            </Container>
        </ThemeProvider>
    )
};

ReactDOM.render(<App />, document.getElementById('root'))
