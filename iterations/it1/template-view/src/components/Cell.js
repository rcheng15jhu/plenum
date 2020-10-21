import React from "react";

const Cell = (props) => {
    let styles = {border: '1px solid black', width: '25px', height: '25px', textAlign: 'center'}
    if (props.unavailable === 'U') {
        styles.backgroundColor = 'red'
    }
    return <td key={props.key} style={styles}/>
};

export default Cell