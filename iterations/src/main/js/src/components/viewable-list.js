import React from "react";
import List from '@material-ui/core/List';
import ListItem from '../components/list-item';



const viewable_list = (props) => {

    return (
        <List>
            {props.list.map(el => (
                <ListItem el={el} clicked={props.clicked}></ListItem>
            ))}

        </List>
    )
}

export default viewable_list

