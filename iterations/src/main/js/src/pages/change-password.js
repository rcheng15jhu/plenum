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

function fetchChangePasswordAPI(values) {
    if (values.password.normalize() === '') {
        createAlert('Your password is currently blank.', 'error');
        return;
    } else if (values.newpassword.length < 8 || values.newpassword.length > 40) {
        createAlert('Please keep your new password between 8 and 40 characters.', 'error');
        return;
    } else if (values.confirm.normalize() != values.newpassword.normalize()) {
        createAlert('Confirm password is not equal to password!', 'error');
        return;
    }

   fetch('/api/changepassword?password=' + values.password.normalize() + '&newpassword=' + values.newpassword.normalize(), {
           method: 'POST',
           mode: 'cors'
       }
   ).then(data => {
       console.log(data);
       if (data.status === 400) {
           createAlert('Incorrect password!', 'error');
       } else if (data.status === 200) {
           createAlert(`Successfully changed password!`, 'success');
           window.location.assign('/profile')
       }
   })
}


const App = () => {

    checkCookie();

    const classes = useStyles();
    const [values, setValues] = React.useState({
        password: '',
        newpassword: '',
        confirm: '',
    });

    const toggleChangeButtonState = () => {
        fetchChangePasswordAPI(values);
    };

    const handleChange = (event) => {
        setValues({
            ...values,
            [event.target.name]: event.target.value,
        });
    }

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
                    <Typography component="h4" variant="h4" className={classes.title}>Change Password</Typography>
                    <div className={classes.margin}>
                        <Grid container spacing={1} alignItems="flex-end">
                            <Grid item>
                                <Typography variant='h6'>
                                    Current Password:
                                </Typography>
                            </Grid>
                            <Grid item>
                                <TextField name='password' type = 'password' onChange={handleChange} required label="required" />
                            </Grid>
                        </Grid>
                    </div>
                    <div className={classes.margin}>
                        <Grid container spacing={1} alignItems="flex-end">
                            <Grid item>
                                <Typography variant='h6'>
                                    New Password (8-40 chars):
                                </Typography>
                            </Grid>
                            <Grid item>
                                <TextField name='newpassword' type = 'password' onChange={handleChange} required label="required" />
                            </Grid>
                        </Grid>
                    </div>
                    <div className={classes.margin}>
                        <Grid container spacing={1} alignItems="flex-end">
                            <Grid item>
                                <Typography variant='h6'>
                                    Confirm New Password:
                                </Typography>
                            </Grid>
                            <Grid item>
                                <TextField name='confirm' type = 'password' onChange={handleChange} required label="required" />
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
                            Change Password
                        </Button>
                    </div>
                </div>
            </Container>
        </ThemeProvider>
    )
};

ReactDOM.render(<App />, document.getElementById('root'))
