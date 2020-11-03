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
    const [values, setValues] = React.useState({
        username: 'name',
        password: '1320',
    });

    function handleLogin(username, password){
        return null;
    }

    function handleSignup(username, password){
        fetch('/adduser?username=' + username + '&password=' + password, {
                method: 'POST',
                mode: 'cors'
            }
        ).then(res => {
            return res.json()
        })
    }

    const options = [
        {
            value: 'Login',
            clicked: handleLogin
        },
        {
            value: 'Sign-up',
            clicked: handleSignup
        }];

    const handleChange = (event) => {
        setValues({
            ...values,
            [event.target.name]: event.target.value,
        });
    }

    return (

        <ThemeProvider theme={theme}>
            <Typography variant="h6" className={classes.home}>
                <Button variant='contained' size='large' href="/" color="primary">
                    <HomeRoundedIcon style={{'marginRight': '5px'}} />
                    Plenum
                </Button>
            </Typography>
            <Container className={classes.root}>
                {options.map(opt => (
                <div key={opt.value} className={classes.contentDiv} id="content">
                    {opt.value === 'Sign-up' ?
                        <Typography variant='h5' color='secondary' className={classes.text}>
                            Do not have an account?
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
                                <TextField name='password' onChange={handleChange} required label="required" />
                            </Grid>
                        </Grid>
                    </div>

                    <div className={classes.center}>
                        <Button
                            variant="contained"
                            color="secondary"
                            className={classes.button}
                            onClick={opt.clicked(values.username, values.password)}
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
