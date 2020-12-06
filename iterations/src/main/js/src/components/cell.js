import React from "react";
import ReactTooltip from "react-tooltip";


const cell = (props) => {
    let styles = {
        borderLeft: '1px black solid',
        width: '44px',
        height: '9px',
        textAlign: 'center',
        outline: 'none',
        borderCollapse: 'collapse'
    }

    Object.assign(styles, props.moreStyles === undefined ? {} : props.moreStyles)

    // ,
    //         padding: '0px',
    //         fontSize: '0px',
    //         lineHeight: '0px',
    //         borderCollapse: 'collapse'

    if (props.available === 'A') {
        styles.backgroundColor = 'red'
    }

    if (props.opacity !== undefined) {
        styles.backgroundColor = `hsla(120, 50%, 50%, ${props.opacity}`
    }

    let tooltip = (props.users_avail === undefined || !props.users_avail.length)
        ? null 
        : <ReactTooltip id={props.tooltip_id} place="top" effect="solid">
            <ul style={{margin: '0', padding: '0', lineHeight: 'normal'}}>
                {props.users_avail.map(element => <li key={element}>{element}</li>)}
            </ul>
        </ReactTooltip>

    return (
        <td style={styles} data-tip data-for={props.tooltip_id} >
            {tooltip}
        </td>
    )

};

export default cell