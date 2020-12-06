import React from "react";

const calendarTemplate = (props) => (
    <table style={{borderCollapse: 'collapse', margin: '5px 10px 10px 0px'}}>
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