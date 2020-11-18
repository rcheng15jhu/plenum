import React from "react";
import ReactDOM from 'react-dom'
import makeStyles from "@material-ui/core/styles/makeStyles";
import Container from "@material-ui/core/Container";
import {Typography} from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import Button from "@material-ui/core/Button";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import theme from "../components/baseline-theme";
import Header from "../components/header";
import getCookie from "../services/get-cookie";

const useStyles = makeStyles((theme) => ({
    root: {
        '& > *': {
            display: "flex",
            flexDirection: "column"
        },
    },
    margin: {
        margin: theme.spacing(1),
    },
    center: {
        display: "flex",
        justifyContent: "center",
        alignItems: "center"
    },
    contentDiv: {
        margin: '10px auto',
        padding: '50px 50px',
        width: '50%',
        border: '2px solid black',
        borderRadius: '10px',
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

    if(getCookie('username') === ""){
        window.location.assign('/')
    }

    const classes = useStyles();

    const username = getCookie('username');

    return (

        <ThemeProvider theme={theme}>
            <Header />
            <Container className={classes.root}>
                <div className={classes.contentDiv}>
                    <div className={classes.margin}>
                        <Grid container spacing={1} direction="column" justify="flex-start" alignItems="flex-start">
                            <Grid container direction="row">
                                <Typography variant='h6'>
                                    Username:&nbsp;
                                </Typography>
                                <Typography variant='h6' color='primary'>
                                    {username}
                                </Typography>
                            </Grid>
                            <Grid container direction="row">
                                <Typography variant='h6'>
                                    Email:&nbsp;
                                </Typography>
                                <Typography variant='h6' color='primary'>
                                    hardcoded@example.com
                                </Typography>
                            </Grid>
                            <Grid container direction="row">
                                <Typography variant='h6'>
                                    Affiliation:&nbsp;
                                </Typography>
                                <Typography variant='h6' color='primary'>
                                    Loremipsum University
                                </Typography>
                            </Grid>
                        </Grid>
                    </div>
                </div>

                <div className={classes.contentDiv}>
                    <div className={classes.center}>
                        <Button
                            href='/list-calendar'
                            variant="contained"
                            color="secondary"
                            className={classes.button}
                        >
                            View Calendars
                        </Button>
                    </div>

                    <div className={classes.center}>
                        <Button
                            href='/list-events'
                            variant="contained"
                            color="secondary"
                            className={classes.button}
                        >
                            View Events
                        </Button>
                    </div>
                </div>
            </Container>
        </ThemeProvider>
    )
};

ReactDOM.render(<App />, document.getElementById('root'))
