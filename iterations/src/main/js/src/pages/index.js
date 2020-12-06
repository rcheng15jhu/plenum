import React from 'react'
import ReactDOM from 'react-dom'
import {Typography} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import {cookieManager, checkCookie} from "../services/cookie-manager";

const App = () => {

    const handleLogin = () => {
        if(cookieManager('username') !== ""){
            window.location.assign('/list-calendar')
        } else {
            window.location.assign('/login')
        }
    }

    return (
        <div style={{'marginTop': '60px'}}>
            <Typography variant='h3' align='center' style={{'margin': '20% 0 30px 0'}}>
                Welcome to Plenum!
            </Typography>
            <div style={{'display': "flex",
                        'alignItems': "center",
                        'justifyContent': "center",
                        'width': '100%',
            }}>
                {username != '' ?
                    <Button variant='contained' color='primary' onClick={handleLogin} style={{'margin': '0 10px'}}>
                        Login
                    </Button>
                    :
                    <Button variant='contained' color='inherit' href='list-calendar'>
                        Your calendar
                    </Button>
                }
                <Button variant='contained' color='inherit' href='list-public-events'>
                    View public events
                </Button>
            </div>
        </div>
    )
}

ReactDOM.render(<App />, document.getElementById('root'))