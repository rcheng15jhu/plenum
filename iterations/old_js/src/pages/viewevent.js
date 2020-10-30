import React, {useState} from "react";
import Calendar from "../components/Calendar";

const App = () => {

    const [data, setData] = useState([])

    const handleChange = e => {
        const fileReader = new FileReader()
        fileReader.readAsText(e.target.files[0], "UTF-8");
        fileReader.onload = e => {
            console.log("e.target.result", e.target.result);
            setData(data.concat(JSON.parse(e.target.result).dates));
        }
    }

    return (
        <div>
            <input type='file' onChange={handleChange}/>
            <Calendar file={data}/>
        </div>
    )
};

export default App