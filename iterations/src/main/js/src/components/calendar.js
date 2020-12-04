import React from "react";

import Cell from './cell'
import EditableCell from './editable-cell'
import {getTime, populateCalendar} from "../services/calendar-manager";
import CalendarTemplate from "./calendar-template";

const calendar = (props) => {
    let template = props.file

    let calendar = new Array(12)

    calendar = populateCalendar(calendar);

    //set up calendar
    for (let i = 0; i < 12; i++) {
        for (let j = 0; j < 7; j++) {
            calendar[i][j] = 'U'
        }
    }

    //set up display layout
    if(template !== undefined && template.dates !== undefined) {
        let dates = template.dates;
        for (let i = 0; i < dates.length; i++) {
            for (let j = 0; j < dates[i].times.length; j++) {
                calendar[dates[i].times[j]][dates[i].date] = 'A'
            }
        }
    }

    return (
        <CalendarTemplate>
            {calendar.map((keyList, i) => (
                <tr key={i}>
                    <td style={{textAlign: 'right'}}>{getTime(i)}</td>
                    {(() => {
                        if(props.editable !== undefined && props.editable === true)
                            return keyList.map((key, j) =>
                                <EditableCell key={j} onAvailChange={props.onAvailChange(j, i)} unavailable={key} time={i} day={j}/>
                            )
                        else
                            return keyList.map((key, j) =>
                                <Cell key={j} unavailable={key} time={i} day={j}/>
                            )
                    })()}
                </tr>))}
        </CalendarTemplate>
    )
};

export default calendar