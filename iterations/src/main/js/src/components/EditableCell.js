import React, {useState} from "react";

const EditableCell = (props) => {
    const [avail, setAvail] = useState(props.unavailable === 'A')
    let styles = {border: '1px solid black', width: '25px', height: '25px', textAlign: 'center', outline: 'none'}

    function flipState() {
        setAvail(!avail)
    }

    if (!avail) {
        styles.backgroundColor = 'red'
    }
    return <td><button onClick={flipState} style={styles} className={"day" + props.day} value={avail ? props.time : -1}/></td>
};

export default EditableCell