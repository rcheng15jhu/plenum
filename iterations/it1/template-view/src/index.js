import React, { useState } from 'react';
import ReactDOM from 'react-dom';

let sampleTemplate = {
  "dates":[{"date":1,"times":[6,7,8]},{"date":5,"times":[4]},{"date":6,"times":[5]}]
}

const Calendar = (props) => {
  let template = props.file

  let calendar = new Array(12)

  for (let i = 0; i < calendar.length; i++) {
    calendar[i] = new Array(7)
  }

  for (let i = 0; i < 12; i++) {
    for (let j = 0; j < 7; j++) {
      calendar[i][j] = 'A'
    }
  }

  for (let i = 0; i < template.length; i++) {
    for (let j = 0; j < template[i].times.length; j++) {
      calendar[template[i].times[j]][template[i].date] = 'U'
    }
  }

  const time = (val) => {
    if (val === 0 || val === 6) {
      return 12
    }
    return (val * 2) % 12
  }

  console.log(calendar)
  return (
    <table>
      <thead>
        <tr>
          <th></th>
          <th>Su</th>
          <th>M</th>
          <th>T</th>
          <th>W</th>
          <th>Th</th>
          <th>F</th>
          <th>S</th>
        </tr>
      </thead>
      <tbody>
        {
          calendar.map((keyList, i) => (
            <tr key={i}>
              <td style={{textAlign:'right'}}>{time(i)}</td>
              {
                keyList.map((key, j) =>
                  <Cell key={j} unavailable={key} />
                )
              }
            </tr>
          ))
        }
      </tbody>
    </table>
  )
}

const Cell = (props) => {
  let styles = { border: '1px solid black', width: '25px', height: '25px', textAlign: 'center' }
  if (props.unavailable === 'U') {
    styles.backgroundColor = 'red'
  }
  return <td key={props.key} style={styles}></td>
}

const App = () => {

  const [data, setData] = useState([])

  const handleChange = e => {
    const fileReader = new FileReader()
    fileReader.readAsText(e.target.files[0], "UTF-8");
    fileReader.onload = e => {
      console.log("e.target.result", e.target.result);
      setData(data.concat(JSON.parse(e.target.result).dates));
    }
  }

  return (
  <div>
    <input type='file' onChange={handleChange}></input>
    <Calendar file={data}/>
  </div>
  )
}


ReactDOM.render(<App />, document.getElementById('root'))
