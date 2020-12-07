import React, {useState} from "react";
import ReactDOM from 'react-dom'
import makeStyles from "@material-ui/core/styles/makeStyles";
import Container from "@material-ui/core/Container";
import {Typography} from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import Button from "@material-ui/core/Button";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import theme from "../components/baseline-theme";
import Header from "../components/header";
import {cookieManager, checkCookie} from "../services/cookie-manager";
import profileImage from '../resources/profile.png';

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
        border: '2px solid black',
        borderRadius: '10px',
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

    const [values, setValues] = React.useState({
        email: '',
        affil: '',
        title: '',
        description: '',
    });
    
    function fetchProfile() {
        fetch("/api/getprofile", {
                method: 'POST',
                mode: 'cors'
            }
        ).then(res => {
            return res.json()
        }).then(data => {
            setValues([...data])
        })
    }

    fetchProfile();

    const classes = useStyles();

    const username = cookieManager('username');

    return (
        <ThemeProvider theme={theme}>
            <Header />
            <Container className={classes.root}>
                <div className={classes.contentDiv}>
                    <Grid container direction="row" justify="space-evenly" alignItems="center" display="flex">
                        <Grid className={classes.innerContent} container direction="column" justify="flex-start" alignItems="center">
                            <img src = {profileImage}/>
                            <Button
                                href='#top'
                                variant="contained"
                                color="secondary"
                                className={classes.button}
                            >
                                Change Profile Picture
                            </Button>
                        </Grid>

                        <Grid className={classes.innerContent} container direction="column" justify="flex-start" alignItems="center">
                            <Grid container direction="row" justify="flex-start" display="flex">
                                <Typography variant='h6'>
                                    Username:&nbsp;
                                </Typography>
                                <Typography variant='h6' color='primary'>
                                    {username}
                                </Typography>
                            </Grid>
                            <Grid container direction="row" justify="flex-start" display="flex">
                                <Typography variant='h6'>
                                    Email:&nbsp;
                                </Typography>
                                <Typography variant='h6' color='primary'>
                                    {values.email}
                                </Typography>
                            </Grid>
                            <Grid container direction="row" justify="flex-start" display="flex">
                                <Typography variant='h6'>
                                    Affiliation:&nbsp;
                                </Typography>
                                <Typography variant='h6' color='primary'>
                                    {values.affil}
                                </Typography>
                            </Grid>
                            <Grid container direction="row" justify="flex-start" display="flex">
                                <Typography variant='h6'>
                                    Title:&nbsp;
                                </Typography>
                                <Typography variant='h6' color='primary'>
                                    {values.title}
                                </Typography>
                            </Grid>
                            <Button
                                href='/change-password'
                                variant="contained"
                                color="secondary"
                                className={classes.button}
                            >
                                Change Password
                            </Button>
                        </Grid>

                        <Grid className={classes.innerContent} container direction="column" justify="flex-start" alignItems="center">
                            <Grid container direction="row" justify="flex-start" display="flex">
                                <Typography variant='h6'>
                                    Description:&nbsp;
                                </Typography>
                                <Typography variant='h6' color='primary'>
                                    {values.description}
                                </Typography>
                            </Grid>
                            <Button
                                href='/change-profile'
                                variant="contained"
                                color="secondary"
                                className={classes.button}
                            >
                                Edit Profile
                            </Button>
                        </Grid>
                    </Grid>
                </div>
            </Container>
        </ThemeProvider>
    )
};

ReactDOM.render(<App />, document.getElementById('root'))
