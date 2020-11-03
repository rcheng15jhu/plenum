import React, {useEffect} from "react";
import ReactDOM from 'react-dom'
import makeStyles from "@material-ui/core/styles/makeStyles";
import Container from "@material-ui/core/Container";
import {Typography} from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import Button from "@material-ui/core/Button";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import theme from "../components/baseline-theme";
import Header from "../components/header";

const useStyles = makeStyles((theme) => ({
    root: {
        '& > *': {
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "center"
        },
    },
    margin: {
        margin: theme.spacing(1),
    },
    center: {
        display: "flex",
        alignItems: "center",
        justifyContent: "center"
    },
    contentDiv: {
        margin: '10px auto',
        padding: '50px 0',
        width: '50%',
        border: '2px solid black',
        borderRadius: '10',
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
    const classes = useStyles();

    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for(var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) == 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    }

    const username = getCookie('username');

    return (

        <ThemeProvider theme={theme}>
            <Header />
            <Container className={classes.root}>
                    <div className={classes.contentDiv} id="content">

                        <div className={classes.margin}>
                            <Grid container spacing={1} alignItems="flex-end">
                                <Grid item>
                                    <Typography variant='h6'>
                                        Username:
                                    </Typography>
                                </Grid>
                                <Grid item>
                                    <Typography variant='h6' color='primary'>
                                        {username}
                                    </Typography>
                                </Grid>
                            </Grid>
                        </div>

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
