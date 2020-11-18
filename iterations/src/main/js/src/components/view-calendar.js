import React, {useState, useEffect} from "react";
import ReactDOM from 'react-dom';
import Calendar from "./calendar";
import Viewable_list from "./viewable-list-item";
import Header from "./header";
import { makeStyles, useTheme } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import EditIcon from '@material-ui/icons/Edit';
import DoneIcon from '@material-ui/icons/Done';
import Zoom from "@material-ui/core/Zoom";
import Fab from "@material-ui/core/Fab";
import { green } from '@material-ui/core/colors';
import clsx from "clsx";

const useStyles = makeStyles((theme) => ({
    root: {
        display: 'flex',
    },
    details: {
        display: 'flex',
        flexDirection: 'column',
    },
    content: {
        flex: '1 0 auto',
    },
    controls: {
        margin: "auto",
        height: 60,
        paddingLeft: theme.spacing(1),
        paddingBottom: theme.spacing(1),
        paddingTop: theme.spacing(1),
    },
    title: {
        marginBottom: theme.spacing(4),
    },
    fab: {
        display: 'flex',
        alignItems: 'center',
        paddingLeft: theme.spacing(1),
        paddingBottom: theme.spacing(1),
    },
    fabGreen: {
        color: theme.palette.common.white,
        backgroundColor: green[500],
        '&:hover': {
            backgroundColor: green[600],
        },
    },
}));

const ViewCalendar = (props) => {

    const [calendars, setCalendars] = useState([])

    console.log(calendars)

    const [file, setFile] = useState({})

    const [editable, setEditable] = useState(false)
    const [value, setValue] = React.useState(0);

    const classes = useStyles();
    const theme = useTheme();
    const transitionDuration = {
        enter: theme.transitions.duration.enteringScreen,
        exit: theme.transitions.duration.leavingScreen,
    };

    useEffect(() => {
        fetch('/api/calendar', {
                method: 'GET',
                mode: 'cors'
            }
        ).then(res => {
            return res.json()
        }).then(data => {
            console.log(data)
            setCalendars([...data])
        })
    }, [])

    useEffect(() => {
        if(props.id > 0) {
            fetch('/api/calendar?id=' + props.id, {
                    method: 'GET',
                    mode: 'cors'
                }
            ).then(res => {
                return res.json()
            }).then(data => {
                console.log(data)
                setFile(data)
            })
        }
        else {
            setFile({})
        }
    }, [props.id, editable])

    const fabs = [
        {
            color: 'primary',
            className: classes.fab,
            icon: <EditIcon className={classes.icon}/>,
            label: 'Edit Calendar',
        },
        {
            color: 'inherit',
            className: clsx(classes.fab, classes.fabGreen),
            icon: <DoneIcon className={classes.icon}/>,
            label: 'Save Edits',
        },
    ];

    const handleChangeIndex = () => {
        editable ? setValue(0) : setValue(1);
        setEditable(!editable);
    };

    let onAvailChange = (date, qHour, state) => () => {
        fetch('/api/updateavailability?calendarId=' + props.id + "&date=" + date + "&qHour=" + qHour + "&state=" + (state ? 1 : 0), {
            method: 'POST',
            mode: 'cors'
        }).then(res => undefined)
    }


    return (
        <div>
            <Card className={classes.root}>
                <div className={classes.details}>
                    <CardContent className={classes.content}>
                        <Typography component="h4" variant="h4" className={classes.title}>
                            Viewing {file.calendarTitle}
                        </Typography>
                        {/*<Typography variant="subtitle1" color="textSecondary">*/}
                        {/*    description*/}
                        {/*</Typography>*/}
                    </CardContent>
                    <Card>
                        <Calendar editable={editable} onAvailChange={onAvailChange} file={file}/>
                    </Card>
                    {props.id > 0 ?
                        <div className={classes.controls}>
                            {fabs.map((fab, index) => (
                                <Zoom
                                    key={fab.color}
                                    in={value === index}
                                    timeout={transitionDuration}
                                    style={{
                                        transitionDelay: `${value === index ? transitionDuration.exit : 0}ms`,
                                    }}
                                    unmountOnExit
                                >
                                    <Fab aria-label={fab.label} className={fab.className} color={fab.color}
                                         onClick={handleChangeIndex}>
                                        {fab.icon}
                                    </Fab>
                                </Zoom>
                            ))}
                        </div>
                        :
                        <div></div>
                    }
                </div>
            </Card>
        </div>
    )
};
export default ViewCalendar;