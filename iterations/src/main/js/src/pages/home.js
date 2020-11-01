import React from 'react'
import ReactDOM from 'react-dom'
import Header from "../components/header";

const App = () => {
    return (
        <div>
            <Header></Header>
            <div>
                <p>This is the home page</p>
            </div>
        </div>
    )
}

ReactDOM.render(<App />, document.getElementById('root'))