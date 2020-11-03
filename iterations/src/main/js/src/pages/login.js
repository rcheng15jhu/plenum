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
import Divider from "@material-ui/core/Divider";

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
    const options = [
        {
            value: 'Login',
        },
        {
            value: 'Sign-up',
        }];

    return (

        <ThemeProvider theme={theme}>
            <Typography variant="h6" className={classes.home}>
                <Button variant='contained' size='large' href="/" color="primary">Plenum</Button>
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
                                <TextField required label="required" />
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
                                <TextField required label="required" />
                            </Grid>
                        </Grid>
                    </div>

                    <div className={classes.center}>
                        <Button
                            variant="contained"
                            color="primary"
                            className={classes.button}
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
