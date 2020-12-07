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
import {getCalendars} from "../services/calendar-manager";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import {getEvents} from "../services/event-manager";

const useStyles = makeStyles((theme) => ({
    center: {
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
    },
    contentDiv: {
        width: '90%',
        margin: '10px auto',
        padding: '50px 50px',
    },
    innerContent: {
        width: '32%',
    },
    button: {
        marginTop: '30px',
    },
    text: {
        marginBottom: '20px',
    },
    home: {
        position: 'fixed',
    }
}));

const App = () => {

    checkCookie();

    const classes = useStyles();

    const username = cookieManager('username');

    const [calendars, setCalendars] = useState([]);
    const [events, setEvents] = useState([]);

    getCalendars(setCalendars)

    getEvents(setEvents)

    let calendarNames = calendars.map(calendar => {return {id: calendar.id, content: calendar.title}})
    let eventNames = events.map(event => {return {id: event.id, content: event.title}})

    return (
        <ThemeProvider theme={theme}>
            <Header />
            <Container className={classes.root}>
                <div className={classes.contentDiv}>
                    <Typography variant='h3' color="primary">
                        Hi, {username}!
                    </Typography>
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
                        </Grid>
                    </Grid>
                </div>
            </Container>
        </ThemeProvider>
    )
};

ReactDOM.render(<App />, document.getElementById('root'))
