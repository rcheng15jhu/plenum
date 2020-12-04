import React from "react";
import ReactTooltip from "react-tooltip";


const cell = (props) => {
    let styles = { border: '1px solid black', width: '25px', height: '25px', textAlign: 'center', outline: 'none' }

    if (props.unavailable === 'A') {
        styles.backgroundColor = 'red'
    }

    if (props.opacity !== undefined) {
        styles.backgroundColor = `hsla(120, 50%, 50%, ${props.opacity}`
    }

    let tooltip = (props.users_avail === undefined || !props.users_avail.length)
        ? null 
        : <ReactTooltip id={props.tooltip_id} place="top" effect="solid">
            <ul style={{margin: '0', padding: '0'}}>
                {props.users_avail.map(element => <li key={element}>{element}</li>)}
            </ul>
        </ReactTooltip>

    return (
        <td>
            <button style={styles} className={"day" + props.day} value={props.time} data-tip data-for={props.tooltip_id} />
            {tooltip}
        </td>
    )

};

export default cell