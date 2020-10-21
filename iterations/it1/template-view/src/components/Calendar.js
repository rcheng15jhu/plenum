import React from "react";

import Cell from './Cell'
import EditableCell from './EditableCell'

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

    if(template !== undefined && template.dates !== undefined) {
        console.log("test")
        let dates = template.dates;
        for (let i = 0; i < dates.length; i++) {
            for (let j = 0; j < dates[i].times.length; j++) {
                calendar[dates[i].times[j]][dates[i].date] = 'U'
            }
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
                <th/>
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
                        <td style={{textAlign: 'right'}}>{time(i)}</td>
                        {(() => {
                            if(props.editable !== undefined && props.editable === true)
                                return keyList.map((key, j) =>
                                    <EditableCell key={j} unavailable={key} time={i} day={j}/>
                                )
                            else
                                return keyList.map((key, j) =>
                                    <Cell key={j} unavailable={key} time={i} day={j}/>
                                )
                        })()}
                    </tr>
                ))
            }
            </tbody>
        </table>
    )
};

export default Calendar