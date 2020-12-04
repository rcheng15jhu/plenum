import React from "react";

const calendarTemplate = (props) => (
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
            {props.children}
        </tbody>
    </table>
)

export default calendarTemplate;