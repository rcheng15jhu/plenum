import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import DateRangeIcon from "@material-ui/icons/DateRange";
import ListItemText from "@material-ui/core/ListItemText";
import {ExpandLess, ExpandMore} from "@material-ui/icons";
import ListItemSecondaryAction from "@material-ui/core/ListItemSecondaryAction";
import IconButton from "@material-ui/core/IconButton";
import DeleteIcon from "@material-ui/icons/Delete";
import Collapse from "@material-ui/core/Collapse";
import List from "@material-ui/core/List";
import React, {useEffect} from "react";
import {makeStyles} from "@material-ui/core/styles";
import {blue} from "@material-ui/core/colors";
import VisibilityIcon from '@material-ui/icons/Visibility';
import VisibilityOffIcon from '@material-ui/icons/VisibilityOff';


const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
        maxWidth: 752,
    },
    demo: {
        backgroundColor: theme.palette.background.paper,
    },
    title: {
        margin: theme.spacing(4, 0, 2),
    },
    rounded: {
        color: '#fff',
        backgroundColor: blue[500],
    },
    nested: {
        paddingLeft: theme.spacing(4),
    },
}))

const viewable_list_item = (props) => {
    const classes = useStyles();
    // const [open, setOpen] = React.useState(false);

    // const handleClickList = () => {
    //     setOpen(!open);
    //     props.clicked();
    // };

    return (
        <div>
            <ListItem
                key={props.id}
                className="content list"
                divider={true}
                button
                onClick={props.clicked(props.id)}
                className={`${props.id}`}>
                <ListItemAvatar>
                    <Avatar variant="rounded" className={classes.rounded}>
                        <DateRangeIcon />
                    </Avatar>
                </ListItemAvatar>
                <ListItemText
                    primary={`${props.content}`}
                />
                {/*{open ? <VisibilityIcon /> : <VisibilityOffIcon />}*/}
                {/*<ListItemSecondaryAction>*/}
                {/*    <IconButton edge="end" aria-label="delete">*/}
                {/*        <DeleteIcon onClick={props.delete(props.id)}/>*/}
                {/*    </IconButton>*/}
                {/*</ListItemSecondaryAction>*/}
            </ListItem>
            {/*<Collapse in={open} timeout="auto" unmountOnExit>*/}
            {/*    <List component="div" disablePadding>*/}
            {/*        /!*<ListItem button className={classes.nested} onClick={props.clicked(props.id)}>*!/*/}
            {/*        /!*    <ListItemText primary="View calendar" />*!/*/}
            {/*        /!*</ListItem>*!/*/}
            {/*    </List>*/}
            {/*</Collapse>*/}
        </div>
    )
}

export default viewable_list_item