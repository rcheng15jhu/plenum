import React , {useState} from "react";
import ReactDOM from 'react-dom'
import Calendar from "../components/calendar";
import Header from "../components/header";
import uploadTemplate from "../services/calendar-manager.js";
import { makeStyles } from '@material-ui/core/styles';
import {TextField, Container, Button} from '@material-ui/core';
import SaveIcon from '@material-ui/icons/Save';
import Typography from "@material-ui/core/Typography";
import newTheme from "../components/baseline-theme";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import {checkCookie} from "../services/cookie-manager";

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

const App = () => {

    checkCookie();

    const classes = useStyles();

    const createObject = () => {
        let objToSave = {};
        objToSave.dates = [];
        for(let i = 0; i < 7; i++) {
            objToSave.dates.push({
                date: i,
                times: []
            })
        }
        return objToSave
    }


    const [avails, setAvails] = useState(createObject)



    const updateAvailability = (date, qHour) => (state) => {
        setAvails(prevAvail => {
            let times = prevAvail.dates[date].times
            if(state) {
                if (!times.includes(qHour)) {
                    times.push(qHour)
                }
            }
            else {
                let index = times.indexOf(qHour)
                if(index !== -1)  {
                    times.splice(index, 1);
                }
            }
            return prevAvail
        })
    }

    return (
        <Container style={{'paddingBottom': '50px'}}>
            <ThemeProvider theme={newTheme}>
            <Header />
            <div className={classes.contentDiv} id="content">
                <Typography component="h4" variant="h4" className={classes.title}>
                    Create New Calendar
                </Typography>
                <form className={classes.root} noValidate autoComplete="off" className={classes.center}>
                    <TextField label="Calendar Title" variant="outlined" id="title"/>
                </form>
                <ol>
                    <li>Give a title to your calendar above.</li>
                    <li>
                        Indicate your availability on the calendar below.
                        Click to mark boxes red for the time ranges when you are available.
                    </li>
                    <li>
                        When you are done, click on "Upload Template!" button at the bottom of the page.
                    </li>
                </ol>

                <Calendar editable={true} className={classes.center} onAvailChange={updateAvailability}/>
                <Button
                    variant="contained"
                    color="secondary"
                    onClick={() => uploadTemplate(avails)}
                    className={classes.button}
                    startIcon={<SaveIcon />}>
                    Upload Template!
                </Button>
            </div>
            </ThemeProvider>
        </Container>
    )
};

ReactDOM.render(<App />, document.getElementById('root'))
