import React, { useState } from 'react';
import ReactDOM from 'react-dom';

let sampleTemplate = {
  "dates": [
    {
      "date": 1,
      "times": [0, 1, 2, 3]
    },
    {
      "date": 4,
      "times": [5, 7, 8, 10]
    }
  ]
}

const Calendar = () => {
  let template = sampleTemplate.dates
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
    if (val === 0) {
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
              <td>{time(i)}</td>
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

ReactDOM.render(<Calendar />, document.getElementById('root'))
