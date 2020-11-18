import React from "react";

import Cell from './cell'

const aggregate_calendar = (props) => {
    let agg = props.agg

    let calendar = new Array(12)

    for (let i = 0; i < calendar.length; i++) {
        calendar[i] = new Array(7)
    }

    for (let i = 0; i < 12; i++) {
        for (let j = 0; j < 7; j++) {
            calendar[i][j] = {
                num_avail: 0,
                users_avail: []
            }
        }
    }
    let users_in_event = Object.keys(agg)
    let num_users_in_event = users_in_event.length

    for (const i in agg) {
        agg[i].dates.forEach(element => {
            element.times.forEach(function(time) {
                calendar[time][element.date].num_avail = calendar[time][element.date].num_avail + 1
                calendar[time][element.date].users_avail.push(agg[i].userName)
            })
        })
    }

    function linspace(startValue, stopValue, cardinality) {
        let arr = [];
        let step = (stopValue - startValue) / (cardinality - 1);
        for (let i = 0; i < cardinality; i++) {
            arr.push(startValue + (step * i));
        }
        return arr;
    }
    // for n users you need n + 1 opacities
    function get_opacity_from_num_avail(num_avail) {
        if (num_users_in_event <= 6) {
            let opacities = linspace(0, 1, num_users_in_event + 1)
            return opacities[num_avail]
        } else {
            let opacities = linspace(0, 1, 8)
            if (num_avail === 0) {
                return 0
            } else if (num_avail === num_users_in_event) {
                return 1
            } else {
                for (let i = 0; i < 6; i++) {
                    if ((num_avail / num_users_in_event) > (i / 6) && (num_avail / num_users_in_event) < ((i + 1) / 6)) {
                        return opacities[i + 1]
                    }
                }
            }
        }
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
                    calendar.map((rows, i) => (
                        <tr key={i}>
                            <td style={{ textAlign: 'right' }}>{time(i)}</td>
                            {rows.map((cell, j) => (
                                <Cell
                                    key={j}
                                    tooltip_id={'' + i + j}
                                    opacity={get_opacity_from_num_avail(calendar[i][j].num_avail)}
                                    users_avail={calendar[i][j].users_avail}
                                />
                                )
                            )}
                        </tr>
                    ))
                }
            </tbody>
        </table>
        
    )
};

export default aggregate_calendar