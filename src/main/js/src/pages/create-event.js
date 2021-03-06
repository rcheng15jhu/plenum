import React, {useState} from "react";
import ReactDOM from 'react-dom'
import Header from "../components/header";
import {uploadEvent} from "../services/event-manager.js";
import { makeStyles } from '@material-ui/core/styles';
import {TextField, Container, Button} from '@material-ui/core';
import SaveIcon from '@material-ui/icons/Save';
import Typography from "@material-ui/core/Typography";
import newTheme from "../components/baseline-theme";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import {checkCookie} from "../services/cookie-manager";
import NativeSelect from "@material-ui/core/NativeSelect";
import InputLabel from "@material-ui/core/InputLabel";

const useStyles = makeStyles((theme) => ({
    root: {
        '& > *': {
            margin: theme.spacing(1),
            width: '25ch',
            marginTop: theme.spacing(2)
        },
    },
    center: {
        display: "flex",
        alignItems: "center",
        justifyContent: "center"
    },
    contentDiv: {
        marginTop: "5%",
    },
    bold: {
        fontWeight: "bold",
    },
    button: {
        marginLeft: theme.spacing(2),
        marginTop: "10px"
    },
    title: {
        fontWeight: "bold",
        marginBottom: theme.spacing(4),
    }
}));


const setTimeFromString = (setTime, initTime) => (event) => {
    console.log(event.target.value)
    let hour = parseInt(event.target.value.substring(0,2))
    let minute = parseInt(event.target.value.substring(3,5))
    setTime(isNaN(hour) ? initTime : hour)
    setTime(hour)
}

const useTime = (initTime) => {
    const [time, setTime] = useState(initTime)
    const timeString = (time < 10 ? "0" : '') + time + ":00"
    const isPM = time > 12
    return [time, timeString, setTimeFromString(setTime, initTime, isPM)]
}

const App = () => {

    checkCookie();

    const classes = useStyles();

    const timeChoices = (() => {
        let timeChoices = ['midnight']
        for (let i = 1; i < 12; i++) {
            timeChoices.push(i +' AM')
        }
        timeChoices.push('noon')
        for (let i = 1; i < 12; i++) {
            timeChoices.push(i +' PM')
        }
        return timeChoices
    })()

    const timeComponents = timeChoices.map((time, i) => (
        <option value={i} key={time}>{time}</option>
    ))


    return (
        <Container style={{'paddingBottom': '50px'}}>
            <ThemeProvider theme={newTheme}>
                <Header />
                <div className={classes.contentDiv} id="content">
                    <Typography component="h4" variant="h4" className={classes.title}>
                        Create New Event
                    </Typography>
                    <form className={classes.root} noValidate autoComplete="off" className={classes.center}>
                        <TextField label="Event Title" variant="outlined" id="title"/>
                        <InputLabel htmlFor="uncontrolled-native">No Earlier Than</InputLabel>
                        <NativeSelect
                            inputProps={{name: "startTime", id: 'startTime'}}
                            variant="outlined" defaultValue="8" id="startTime"
                        >
                            {timeComponents}
                        </NativeSelect>
                        <InputLabel htmlFor="uncontrolled-native">No Later Than</InputLabel>
                        <NativeSelect
                            inputProps={{name: "endTime", id: 'endTime'}}
                            variant="outlined" defaultValue="17" id="endTime"
                        >
                            {timeComponents}
                        </NativeSelect>
                    </form>
                    <ol>
                        <li>Give a title to your event above.</li>
                        <li>Choose a time range that your event will most likely be between.</li>
                        <li>
                            When you are done, click on "Publish Event!" button at the bottom of the page.
                        </li>
                    </ol>
                    <Button
                        variant="contained"
                        color="secondary"
                        onClick={uploadEvent}
                        className={classes.button}
                        startIcon={<SaveIcon />}>
                        Publish Event!
                    </Button>
                </div>
            </ThemeProvider>
        </Container>
    )
};

ReactDOM.render(<App />, document.getElementById('root'))
