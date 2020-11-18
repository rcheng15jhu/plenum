import ListItem from "@material-ui/core/ListItem";
import ListItemAvatar from "@material-ui/core/ListItemAvatar";
import Avatar from "@material-ui/core/Avatar";
import DateRangeIcon from "@material-ui/icons/DateRange";
import ListItemText from "@material-ui/core/ListItemText";
import ListItemSecondaryAction from "@material-ui/core/ListItemSecondaryAction";
import IconButton from "@material-ui/core/IconButton";
import DeleteIcon from "@material-ui/icons/Delete";
import React, {useEffect} from "react";
import {makeStyles} from "@material-ui/core/styles";
import {blue} from "@material-ui/core/colors";


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

    return (
        <div className={classes.root}>
            <ListItem
                key={props.id}
                className="content list"
                divider={true}
                button
                onClick={props.clicked(props.id)}
                className={`${props.id}`}
                >
                <ListItemAvatar>
                    <Avatar variant="rounded" className={classes.rounded}>
                        <DateRangeIcon />
                    </Avatar>
                </ListItemAvatar>
                <ListItemText className={classes.title}
                    primary={`${props.content}`}
                />
                {/*{open ? <VisibilityIcon /> : <VisibilityOffIcon />}*/}
                <ListItemSecondaryAction onClick={props.delete(props.id)}>
                    <IconButton edge="end" aria-label="delete">
                        <DeleteIcon />
                    </IconButton>
                </ListItemSecondaryAction>
            </ListItem>
        </div>
    )
}

export default viewable_list_item