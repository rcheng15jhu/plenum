import React, {useState} from "react";

const modulated_cell = (props) => {
    let styles = {border: '1px solid black', width: '25px', height: '25px', textAlign: 'center', outline: 'none'}

    if (props.unavailable === 'A') {
        styles.backgroundColor = 'red'
    }
    return <td><button style={styles} className={"day" + props.day} value={props.time}/></td>
};

export default modulated_cell