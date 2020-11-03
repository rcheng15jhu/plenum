import React from 'react'
import ReactDOM from 'react-dom'
import Header from "../components/header";
import {Typography} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import HomeRoundedIcon from "@material-ui/icons/HomeRounded";

const App = () => {
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

    const handleLogin = () => {
        if(getCookie('username') !== ""){
            window.location.assign('/profile')
        } else {
            window.location.assign('/login')
        }
    }

    return (
        <div>
            <div style={{'marginTop': '60px'}}>
                <Typography variant='h3' align='center' style={{'margin': '20% 0 30px 0'}}>
                    Welcome to Plenum!
                </Typography>
                <div style={{'display': "flex",
                            'alignItems': "center",
                            'justifyContent': "center",
                            'width': '100%',}}>
                    <Button variant='contained' color='primary' onClick={handleLogin}>
                        Login
                    </Button>
                </div>
            </div>
        </div>
    )
}

ReactDOM.render(<App />, document.getElementById('root'))