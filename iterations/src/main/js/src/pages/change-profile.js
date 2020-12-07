import React from "react";
import ReactDOM from 'react-dom'
import makeStyles from "@material-ui/core/styles/makeStyles";
import Container from "@material-ui/core/Container";
import TextField from "@material-ui/core/TextField";
import {Typography} from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import Button from "@material-ui/core/Button";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import theme from "../components/baseline-theme";
import HomeRoundedIcon from "@material-ui/icons/HomeRounded";
import {checkCookie} from "../services/cookie-manager";
import createAlert from "../services/create-alert";

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

    checkCookie();

    const [values, setValues] = React.useState({
        email: '',
        affil: '',
        title: '',
        description: '',
    });

    let fetchChangeProfileAPI = () => { 
        if (values.email.length > 100) {
            createAlert('Your email is too long!', 'error');
            return;
        } else if (values.affil.length > 100) {
            createAlert('Your affiliation is too long!', 'error');
            return;
        } else if (values.title.length > 200) {
            createAlert('Your title is too long!', 'error');
            return;
        } else if (values.email.length > 1000) {
            createAlert('Your description is too long!', 'error');
            return;
        }
    
        fetch('/api/editprofile?email=' + values.email.normalize() + '&affil=' + values.affil.normalize()
                + '&title=' + values.title.normalize() + '&description=' + values.description.normalize(),
            {
               method: 'POST',
               mode: 'cors'
            }
        ).then(data => {
            console.log(data);
            if (data.status === 201) {
               createAlert(`Successfully changed profile!`, 'success');
               window.location.assign('/profile')
            } else {
               createAlert(`Was not able to change profile.`, 'error');
            }
        })
    }

    const toggleChangeButtonState = () => {
        fetchChangeProfileAPI(values);
    };

    const classes = useStyles();

    const handleChange = (event) => {
        setValues({
            ...values,
            [event.target.name]: event.target.value,
        });
    }

    React.useEffect(() => {
        fetch("/api/getprofile", {
                method: 'POST',
                mode: 'cors'
            }
        ).then(res => {
            return res.json()
        }).then(data => {
            setValues([data])
        })
    },[])

    return (
        <ThemeProvider theme={theme}>
            <Typography variant="h6" className={classes.home} id='content'>
                <Button variant='contained' size='large' href="/profile" color="primary">
                    <HomeRoundedIcon style={{'marginRight': '5px'}} />
                    Back to Profile
                </Button>
            </Typography>
            <Container className={classes.root}>
                <div className={classes.contentDiv}>
                    <Typography component="h4" variant="h4" className={classes.title}>Change Profile</Typography>
                    <div className={classes.margin}>
                        <Grid container spacing={1} alignItems="flex-end">
                            <Grid item>
                                <Typography variant='h6'>
                                    Email (100 chars max.):
                                </Typography>
                            </Grid>
                            <Grid item>
                                <TextField name='email' value={values.email} onChange={handleChange} />
                            </Grid>
                        </Grid>
                    </div>
                    <div className={classes.margin}>
                        <Grid container spacing={1} alignItems="flex-end">
                            <Grid item>
                                <Typography variant='h6'>
                                    Affiliation (100 chars max.):
                                </Typography>
                            </Grid>
                            <Grid item>
                                <TextField name='affil' value={values.affil} onChange={handleChange} />
                            </Grid>
                        </Grid>
                    </div>
                    <div className={classes.margin}>
                        <Grid container spacing={1} alignItems="flex-end">
                            <Grid item>
                                <Typography variant='h6'>
                                    Title (200 chars max.):
                                </Typography>
                            </Grid>
                            <Grid item>
                                <TextField name='title' value={values.title} onChange={handleChange} />
                            </Grid>
                        </Grid>
                    </div>
                    <div className={classes.margin}>
                        <Grid container spacing={1} alignItems="flex-end">
                            <Grid item>
                                <Typography variant='h6'>
                                    Description (1000 chars max.):
                                </Typography>
                            </Grid>
                            <Grid item>
                                <TextField name='description' value={values.description} size='medium' onChange={handleChange} />
                            </Grid>
                        </Grid>
                    </div>

                    <div className={classes.center}>
                        <Button
                            variant="contained"
                            color="secondary"
                            className={classes.button}
                            onClick={toggleChangeButtonState}
                            id={`${classes.button}button` }
                        >
                            Change Profile
                        </Button>
                    </div>
                </div>
            </Container>
        </ThemeProvider>
    )
};

ReactDOM.render(<App />, document.getElementById('root'))
