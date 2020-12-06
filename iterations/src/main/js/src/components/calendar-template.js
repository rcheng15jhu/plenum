import React from "react";
import {getTime} from "../services/calendar-manager";

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
        {props.children.map((row, i) => {
            let moreStyles = {borderTop: '1px white solid'}
            if(i % 4 === 0) {
                moreStyles = {borderTop: '1px black solid'}
            }
            else if (i % 2 === 0) {
                moreStyles = {borderTop: '1px black dotted'}
            }
            return (
                <tr key={i}>
                    {i % 4 === 0
                        ? <td style={{textAlign: 'right', verticalAlign: 'top'}} rowSpan={4}>{getTime(i)}</td>
                        : null
                    }
                    {row.map(cell => React.cloneElement(cell, {moreStyles: moreStyles}))}
                    <td style={{borderLeft: '1px black solid', width: '0px'}} />
                </tr>
            )
        })}
            <tr>
                {(() => {
                    let hi = [<td key={0} />]
                    hi.push([0, 0, 0, 0, 0, 0, 0].map((trash, i) => (
                        <td key={i + 1} style={{borderTop: '1px black solid', height: '0px'}}/>
                    )))
                    hi.push(<td key={8} />)
                    return hi
                })()}
            </tr>
        </tbody>
    </table>
)

export default calendarTemplate;