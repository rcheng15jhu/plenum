import React from "react";
import ReactDOM from 'react-dom'
import Calendar from "../components/calendar";
import Header from "../components/header";
import uploadTemplate from "../services/calendar-manager.js";
import { makeStyles } from '@material-ui/core/styles';
import {TextField, Container, Button} from '@material-ui/core';
import SaveIcon from '@material-ui/icons/Save';
import Typography from "@material-ui/core/Typography";

const useStyles = makeStyles((theme) => ({
    root: {
        '& > *': {
            margin: theme.spacing(3),
            width: '25ch',
        },
    },
    center: {
        display: "flex",
        alignItems: "center",
        justifyContent: "center"
    },
    contentDiv: {
        margin: theme.spacing(3),
        marginTop: "5%",
    },
    bold: {
        fontWeight: "bold",
    },
    button: {
        marginLeft: "30px",
        marginTop: "10px"
    }
}));

const App = () => {
    const classes = useStyles();

    return (
        <Container>
            <Header></Header>
            <div className={classes.contentDiv} id="content">
                <Typography component="h6" variant="h6">
                    Create New Calendar (this is a test of CI)
                </Typography>
                <form className={classes.root} noValidate autoComplete="off" className={classes.center}>
                    <TextField label="Calendar Title" variant="outlined" id="title"/>
                </form>
                <ol>
                    <li>Give a title to your calendar above.</li>
                    <li>
                        Indicate your unavailability on the calendar below.
                        Click to mark boxes red for the time ranges when you are <span className={classes.bold}>NOT</span> available.
                    </li>
                    <li>
                        When you are done, click on "Upload Template!" button at the bottom of the page.
                    </li>
                </ol>

                <Calendar editable={true} className={classes.center}/>
                <Button
                    variant="contained"
                    color="primary"
                    onClick={uploadTemplate}
                    className={classes.button}
                    startIcon={<SaveIcon />}>
                    Upload Template!
                </Button>
            </div>
        </Container>
    )
};

ReactDOM.render(<App />, document.getElementById('root'))
