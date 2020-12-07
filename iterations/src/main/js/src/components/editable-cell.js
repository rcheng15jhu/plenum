import React, {useState, useEffect, useRef} from "react";

const editable_cell = (props) => {
    const [avail, setAvail] = useState(props.available === 'A')
    const firstUpdate = useRef(true);

    let styles = {
        borderLeft: '1px black solid',
        width: '44px',
        height: '9px',
        textAlign: 'center',
        outline: 'none',
        borderCollapse: 'collapse'
    }

    Object.assign(styles, props.moreStyles === undefined ? {} : props.moreStyles)

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
    return <td onClick={flipState} style={styles} />
};

export default editable_cell