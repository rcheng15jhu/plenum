import React from 'react'
import ReactDOM from 'react-dom'
import Aggregate_calendar from "../components/aggregate-calendar";

const App = () => (
    <div>
      <Aggregate_calendar/>
    </div>
  )

ReactDOM.render(<App />, document.getElementById('root'))