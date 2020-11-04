import React from "react";
import ReactDOM from 'react-dom'
import Calendar from "../components/calendar";
import Header from "../components/header";
import uploadEvent from "../services/event-manager.js";
import { makeStyles } from '@material-ui/core/styles';
import {TextField, Container, Button} from '@material-ui/core';
import SaveIcon from '@material-ui/icons/Save';
import Typography from "@material-ui/core/Typography";
import newTheme from "../components/baseline-theme";
import ThemeProvider from "@material-ui/styles/ThemeProvider";

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
    const classes = useStyles();

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
                    </form>
                    <ol>
                        <li>Give a title to your event above.</li>
                        <li>
                            When you are done, click on "Publish Event!" button at the bottom of the page.
                        </li>
                    </ol>

                    <Calendar editable={true} className={classes.center}/>
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
