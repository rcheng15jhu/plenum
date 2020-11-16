import React from 'react'
import ReactDOM from 'react-dom'
import {Typography} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import getCookie from "../services/get-cookie";

const App = () => {

    const handleLogin = () => {
        // if(getCookie('username') !== ""){
        //     window.location.assign('/list-calendar')
        // } else {
        //     window.location.assign('/login')
        // }
        window.location.assign('/list-calendar')
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