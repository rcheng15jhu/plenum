import React from 'react'
import ReactDOM from 'react-dom'
import Header from "../components/header";
import {Typography} from "@material-ui/core";
import Button from "@material-ui/core/Button";

const App = () => {

    const handleLogin = () => {
        window.location.assign('/login')
    }

    return (
        <div>
            <Header></Header>
            <div style={{'marginTop': '60px'}}>
                <Typography variant='h3' align='center' style={{'marginBottom': '30px'}}>
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