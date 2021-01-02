import React from 'react'
import ReactDOM from 'react-dom'
import {Typography} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import {cookieManager, checkCookie} from "../services/cookie-manager";
import makeStyles from "@material-ui/core/styles/makeStyles";
import theme from "../components/baseline-theme";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import backgroundPhoto from '../resources/background.jpg';

const useStyles = makeStyles((theme) => ({
    backgroundDiv: {
        height: '100%',
        width: '100%',
        top: '0px',
        left:'0px',
        position: 'fixed',
    },
}));

const App = () => {

    const classes = useStyles();

    const username = cookieManager('username');

    const handleLogin = () => {
        if(username !== ""){
            window.location.assign('/list-calendar')
        } else {
            window.location.assign('/login')
        }
    }

    return (
        <ThemeProvider theme={theme}>
            <img src={backgroundPhoto} className={classes.backgroundDiv} style={{zIndex:-1}}/>
            <div style={{'marginTop': '20%'}}>
                <Typography variant='h3' align='center' style={{'margin':'20% 0 30px 0',zIndex:0,color:'#706070'}}>
                    Welcome to Plenum!
                </Typography>
                <div style={{'display': "flex",'alignItems': "center",'justifyContent': "center",'width': '100%',zIndex:1}}>
                    {username === '' ?
                        <Button variant='contained' color='primary' onClick={handleLogin} style={{'margin': '0 10px'}}>
                            Login
                        </Button>
                        :
                        <Button variant='contained' color='primary' href='list-calendar'>
                            Your calendars
                        </Button>
                    }
                    <Button variant='contained' color='inherit' href='list-public-events'>
                        View public events
                    </Button>
                </div>
            </div>
        </ThemeProvider>
    )
}

ReactDOM.render(<App />, document.getElementById('root'))