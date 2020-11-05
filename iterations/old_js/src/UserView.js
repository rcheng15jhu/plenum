import React from 'react'

const UserView = (props) => {
  const templates = props.templates
  const selectedTemplates = props.selectedTemplates

  let view = new Array(12)

  for (let i = 0; i < view.length; i++) {
    view[i] = new Array(7)
  }

  for (let i = 0; i < 12; i++) {
    for (let j = 0; j < 7; j++) {
      view[i][j] = 'A'
    }
  }

  const templatesToRender = templates.filter(template => selectedTemplates.includes(template.name))

  const datesToRender = []
  for (let i = 0; i < templatesToRender.length; i++) {
    datesToRender.push(templatesToRender[i].dates)
  }

  datesToRender.forEach(template => {
    for (let i = 0; i < template.length; i++) {
      for (let j = 0; j < template[i].times.length; j++) {
        view[template[i].times[j]][template[i].date] = 'U'
      }
    }
  });

  const time = (val) => {
    if (val === 0 || val === 6) {
      return 12
    }
    return (val * 2) % 12
  }

  return (
    <div>
      <table>
        <thead>
        <tr>
          <th colspan="8" text-align='center'>Me</th>
        </tr>
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
            view.map((row, i) => (
              <tr key={i}>
                <td style={{ textAlign: 'right' }}>{time(i)}</td>
                {
                  row.map((cell, j) =>
                    <Cell key={j} unavailable={cell} />
                  )
                }
              </tr>
            ))
          }
        </tbody>
      </table>
    </div>

  )
}


const Cell = (props) => {
  let styles = { border: '1px solid black', width: '25px', height: '25px', textAlign: 'center' }
  if (props.unavailable === 'U') {
    styles.backgroundColor = 'red'
  }
  return <td key={props.key} style={styles}></td>
}

export default UserView