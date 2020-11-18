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
import profileImage from '../resources/profile.png';

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1
    },
    center: {
        display: "flex",
        justifyContent: "center",
        alignItems: "center"
    },
    contentDiv: {
        width: '90%',
        margin: '10px auto',
        padding: '50px 50px',
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
                    <Grid container spacing={2} direction="row" justify="space-evenly" alignItems="center">
                        <Grid container spacing={2} direction="column" justify="flex-start" alignItems="flex-start">
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
                                    Institution:&nbsp;
                                </Typography>
                                <Typography variant='h6' color='primary'>
                                    Hardcoded University
                                </Typography>
                            </Grid>
                            <Grid container direction="row">
                                <Typography variant='h6'>
                                    Title:&nbsp;
                                </Typography>
                                <Typography variant='h6' color='primary'>
                                    Computer Science Major
                                </Typography>
                            </Grid>
                        </Grid>
                        <Grid container spacing={2} direction="column" justify="flex-start" alignItems="flex-end">
                            <Grid container>
                                <Typography variant='h6'>
                                    Description:&nbsp;
                                </Typography>
                                <Typography variant='h6' color='primary'>
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                                    Fusce iaculis pulvinar justo, accumsan feugiat massa faucibus vel.
                                </Typography>
                            </Grid>
                        </Grid>
                        <Grid container spacing={2} direction="column" justify="flex-start" alignItems="flex-end">
                            <Grid container>
                                <img src = {profileImage}/>
                            </Grid>
                            <Grid container>
                                <Button
                                    href='/'
                                    variant="contained"
                                    color="secondary"
                                    className={classes.button}
                                >
                                    Change Profile Picture
                                </Button>
                            </Grid>
                        </Grid>
                    </Grid>
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
