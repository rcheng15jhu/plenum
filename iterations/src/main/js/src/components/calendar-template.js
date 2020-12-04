import React from "react";

const calendarTemplate = (body) => {
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
                {body}
            }
            </tbody>
        </table>
    )
}

export default calendarTemplate;