import React from "react";



const viewable_list = (props) => {
return (
    <ul>
        <p>
        {
            props.list.map(el => (
                <li key={el.id} className="content list">
                    {el.content}
                    <button onClick={props.clicked(el.id)} className={el.id}>View</button>
                </li>
            ))
        }
        </p>
    </ul>
)
}

export default viewable_list

