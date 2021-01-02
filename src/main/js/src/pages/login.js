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

function fetchAddUserAPI(values) {
    if (values.username.normalize() === '' || values.password.normalize() === '') {
        createAlert('Username and password cannot be blank.', 'error');
        return;
    } else if (values.username.length < 8 || values.username.length > 40 || values.password.length < 8 || values.password.length > 40) {
        createAlert('Please keep your username and password between 8 and 40 characters.', 'error');
        return;
    } else if (values.confirm.normalize() != values.password.normalize()) {
        createAlert('Confirm password is not equal to password!', 'error');
        return;
    }

   fetch('/api/adduser?username=' + values.username.normalize() + '&password=' + values.password.normalize(), {
           method: 'POST',
           mode: 'cors'
       }
   ).then(data => {
       console.log(data);
       if (data.status === 401 || data.status === 500) {
           createAlert('Username taken!', 'error');
       } else if (data.status === 200) {
           createAlert(`Successfully signed up!`, 'success');
           document.cookie = "username=" + values.username.normalize() + "; path=/;";
           window.location.assign('/dashboard')
       }
   })
}

function fetchAPI(values) {
    if (values.username.normalize() === '' || values.password.normalize() === '') {
        createAlert('Username and password cannot be blank!', 'error');
        return;
    }

    fetch('/api/login?username=' + values.username.normalize() + '&password=' + values.password.normalize(), {
            method: 'POST',
            mode: 'cors'
        }
    ).then(data => {
        console.log(data);
        if (data.status === 401) {
            createAlert('Incorrect login information!', 'error');
        } else if (data.status === 200) {
            createAlert(`Successfully logged in!`, 'success');
            document.cookie = "username=" + values.username.normalize() + "; path=/;";
            window.location.assign('/list-calendar')
        }
    })
}


const App = () => {
    const classes = useStyles();
    const [values, setValues] = React.useState({
        username: '',
        password: '',
        confirm: '',
    });

    const state = { result: null };

    const toggleLoginButtonState = () => {
        fetchAPI(values);
    };

    const toggleSignupButtonState = () => {
        fetchAddUserAPI(values);
    };

    const options = [
        {
            value: 'Login',
            clicked: toggleLoginButtonState
        },
        {
            value: 'Sign-up',
            clicked: toggleSignupButtonState
        }];

    const handleChange = (event) => {
        setValues({
            ...values,
            [event.target.name]: event.target.value,
        });
    }

    return (

        <ThemeProvider theme={theme}>
            <Typography variant="h6" className={classes.home} id='content'>
                <Button variant='contained' size='large' href="/" color="primary">
                    <HomeRoundedIcon style={{'marginRight': '5px'}} />
                    Plenum
                </Button>
            </Typography>
            <Container className={classes.root}>
                {options.map(opt => (
                <div key={opt.value} className={classes.contentDiv}>
                    {opt.value === 'Sign-up' ?
                        <Typography variant='h5' color='secondary' className={classes.text}>
                            Don't have an account?
                        </Typography>
                        :
                        <div></div>}
                    <Typography component="h4" variant="h4" className={classes.title}>
                        {opt.value}
                    </Typography>
                    <div className={classes.margin}>
                        <Grid container spacing={1} alignItems="flex-end">
                            <Grid item>
                                <Typography variant='h6'>
                                    Username:
                                </Typography>
                            </Grid>
                            <Grid item>
                                <TextField name='username' onChange={handleChange} required label="required" />
                            </Grid>
                        </Grid>
                    </div>
                    <div className={classes.margin}>
                        <Grid container spacing={1} alignItems="flex-end">
                            <Grid item>
                                <Typography variant='h6'>
                                    Password:
                                </Typography>
                            </Grid>
                            <Grid item>
                                <TextField name='password' type = 'password' onChange={handleChange} required label="required" />
                            </Grid>
                        </Grid>
                    </div>

                    <div className={classes.margin}>
                        {opt.value === 'Sign-up' ?
                            <div>
                                <Grid item>
                                    <Typography variant='h6'>
                                        Confirm password:
                                    </Typography>
                                </Grid>
                                <Grid item>
                                    <TextField name='confirm' type = 'password' onChange={handleChange} required label="required" />
                                </Grid>
                            </div>
                            : <div></div>
                        }
                    </div>

                    <div className={classes.center}>
                        <Button
                            variant="contained"
                            color="secondary"
                            className={classes.button}
                            onClick={opt.clicked}
                            id={`${classes.button}button` }
                        >
                            {opt.value}
                        </Button>
                    </div>
                </div>
            ))}
            </Container>
        </ThemeProvider>
    )
};

ReactDOM.render(<App />, document.getElementById('root'))
