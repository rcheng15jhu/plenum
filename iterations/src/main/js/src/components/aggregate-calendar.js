import React from "react";

import Cell from './cell'
import EditableCell from './editable-cell'

let sample_data = {
    "01": [
        {
            "date": 1,
            "times": [0, 1, 2, 3]
        },
        {
            "date": 4,
            "times": [5, 7, 8, 10]
        },
        {
            "date": 5,
            "times": [5]
        }
    ],
    "02": [
        {
            "date": 1,
            "times": [0, 1, 2, 3]
        },
        {
            "date": 4,
            "times": [5, 7, 8, 10]
        },
        {
            "date": 6,
            "times": [6]
        }
    ],
    "03": [
        {
            "date": 1,
            "times": [0, 1, 2, 3]
        },
        {
            "date": 4,
            "times": [5, 7, 8, 10]
        },
        {
            "date": 0,
            "times": [0]
        }
    ]
}

const aggregate_calendar = (props) => {
    // let template = props.file

    let calendar = new Array(12)

    for (let i = 0; i < calendar.length; i++) {
        calendar[i] = new Array(7)
    }

    for (let i = 0; i < 12; i++) {
        for (let j = 0; j < 7; j++) {
            calendar[i][j] = 0
        }
    }

    // if (template !== undefined && template.dates !== undefined) {
    //     console.log("test")
    //     let dates = template.dates;
    //     for (let i = 0; i < dates.length; i++) {
    //         for (let j = 0; j < dates[i].times.length; j++) {
    //             calendar[dates[i].times[j]][dates[i].date] = 'A'
    //         }
    //     }
    // }

    let users_in_event = Object.keys(sample_data).length
    console.log(users_in_event)
    for (const i in sample_data) {
        console.log(sample_data[i])
        sample_data[i].forEach(element => {
            console.log(element.date, element.times)
            element.times.forEach(time => calendar[time][element.date] = calendar[time][element.date] + 1)
        })
    }

    const time = (val) => {
        if (val === 0 || val === 6) {
            return 12
        }
        return (val * 2) % 12
    }

    return (
        <table>
            <thead>
                <tr>
                    <th />
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
                            <td style={{ textAlign: 'right' }}>{time(i)}</td>
                            {keyList.map((key, j) =>
                                <td>key={j}{calendar[i][j]}</td> 
                            )}
                        </tr>
                    ))
                }
            </tbody>
        </table>
    )
};

export default aggregate_calendar