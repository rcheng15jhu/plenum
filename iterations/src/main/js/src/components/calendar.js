import React from "react";

import Cell from './cell'
import EditableCell from './editable-cell'
import {getTime, populateCalendar} from "../services/calendar-manager";
import CalendarTemplate from "./calendar-template";

const calendar = (props) => {
    let template = props.file

    let calendar = new Array(24 * 4)

    calendar = populateCalendar(calendar);

    //set up calendar
    for (let i = 0; i < 24 * 4; i++) {
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

    const timeRange = props.timeRange === undefined ? [0, 24] : props.timeRange
    calendar = calendar.slice(timeRange[0]*4, timeRange[1]*4)

    return (
        <CalendarTemplate startTime={timeRange[0]}>
        {calendar.map((keyList, i) => (
            <React.Fragment key={i}>
            {(() => {
                if(props.editable !== undefined && props.editable === true) {
                    return keyList.map((key, j) =>
                        <EditableCell
                            key={j}
                            onAvailChange={props.onAvailChange}
                            available={key}
                            time={i} day={j}
                        />
                    )
                }
                else {
                    return keyList.map((key, j) =>
                        <Cell key={j}
                              available={key}
                              time={i} day={j}
                        />
                    )
                }
            })()}
            </React.Fragment>
        ))}
        </CalendarTemplate>
    )
};

export default calendar