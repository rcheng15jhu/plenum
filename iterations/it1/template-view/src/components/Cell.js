import React, {useState} from "react";

const Cell = (props) => {
    const [avail, setAvail] = useState(props.unavailable === 'A')
    let styles = {border: '1px solid black', width: '25px', height: '25px', textAlign: 'center'}

    function flipState() {
        setAvail(!avail)
    }

    if (!avail) {
        styles.backgroundColor = 'red'
    }
    return <td onClick={flipState} key={props.key} style={styles}/>
};

export default Cell