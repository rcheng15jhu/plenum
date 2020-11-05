import React, {useState} from "react";

<<<<<<< HEAD
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
=======
<<<<<<<< HEAD:iterations/it1/template-view/src/components/Cell.js
const Cell = (props) => {
========
const EditableCell = (props) => {
    const [avail, setAvail] = useState(props.unavailable === 'A')
>>>>>>>> iteration3:iterations/old_js/src/components/EditableCell.js
    let styles = {border: '1px solid black', width: '25px', height: '25px', textAlign: 'center', outline: 'none'}

    if (props.unavailable !== 'A') {
        styles.backgroundColor = 'red'
    }
<<<<<<<< HEAD:iterations/it1/template-view/src/components/Cell.js
    return <td><button style={styles} className={"day" + props.day} value={props.time}/></td>
========
    return <td><button onClick={flipState} style={styles} className={"day" + props.day} value={avail ? props.time : -1}/></td>
>>>>>>>> iteration3:iterations/old_js/src/components/EditableCell.js
>>>>>>> iteration3
};

export default EditableCell