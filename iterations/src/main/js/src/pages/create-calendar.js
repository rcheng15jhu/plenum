import React from "react";
import ReactDOM from 'react-dom'
import Calendar from "../components/calendar";
import Header from "../components/header";
import uploadTemplate from "../services/calendar-manager.js";
import Box from "@material-ui/core/Box";
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';

const useStyles = makeStyles((theme) => ({
    root: {
        '& > *': {
            margin: theme.spacing(1),
            width: '25ch',
        },
    },
}));

const App = () => {
    const classes = useStyles();

    return (
        <div>
            <Header></Header>
            <Box color="text.primary" justifyContent="center" width={1}>
                <form className={classes.root} noValidate autoComplete="off">
                    <TextField label="Calendar Title" variant="outlined" id="title"/>
                </form>
                    <Calendar editable={true}/>
                <button onClick={uploadTemplate} >Upload template!</button>
            </Box>
        </div>
    )
};

ReactDOM.render(<App />, document.getElementById('root'))
