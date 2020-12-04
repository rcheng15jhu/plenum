import React, {useState, useEffect, useRef} from "react";

const editable_cell = (props) => {
    const [avail, setAvail] = useState(props.unavailable === 'A')
    const firstUpdate = useRef(true);

    let styles = {border: '1px solid black', width: '25px', height: '25px', textAlign: 'center', outline: 'none'}

    let onAvailChange = () => undefined;
    if(props.onAvailChange !== undefined) {
        onAvailChange = props.onAvailChange(props.day, props.time);
    }
    useEffect(() => {
        if(firstUpdate.current) {
            firstUpdate.current = false;
            return;
        }
        onAvailChange(avail)
    }, [avail])

    function flipState() {
        setAvail(!avail)
    }

    if (avail) {
        styles.backgroundColor = 'red'
    }
    return <td><button onClick={flipState} style={styles} className={"day" + props.day} value={avail ? props.time : -1}/></td>
};

export default editable_cell