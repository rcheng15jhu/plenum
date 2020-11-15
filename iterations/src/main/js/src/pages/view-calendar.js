import React, {useState, useEffect} from "react";
import ReactDOM from 'react-dom';
import Calendar from "../components/calendar";
import Viewable_list from "../components/viewable-list-item";
import Header from "../components/header";
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
        fontWeight: "bold",
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

const App = () => {

    let getInitId = () => {
        let paramId = parseInt(new URLSearchParams(document.location.search.substring(1)).get("id"));
        if(isNaN(paramId)) {
            window.history.replaceState({id: -1},'','/view-calendar')
            return -1;
        }
        else {
            window.history.replaceState({id: paramId},'','/view-calendar?id=' + paramId)
            return paramId;
        }
    }

    const [id, setId] = useState(getInitId)

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
        if(id > 0) {
            fetch('/api/calendar?id=' + id, {
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
    }, [id, editable])

    let updateActive = (id) => () => {
        window.history.pushState({id: id},'','/view-calendar?id=' + id)
        setId(id)
    }

    let clearCalendarView = () => {
        window.history.pushState({id: -1},'','/view-calendar')
        setId(-1)
    }

    window.onpopstate = (e) => {
        setId(e.state.id)
    }

    let onAvailChange = (date, qHour, state) => () => {
        fetch('/api/updateavailability?calendarId=' + id + "&date=" + date + "&qHour=" + qHour + "&state=" + (state ? 1 : 0), {
            method: 'POST',
            mode: 'cors'
        }).then(res => undefined)
    }

    let calendarNames = calendars.map(calendar => {return {id: calendar.id, content: calendar.title}})

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


    return (
        <div>
            <Header></Header>
            <Card className={classes.root}>
                <div className={classes.details}>
                    <CardContent className={classes.content}>
                        <Typography component="h4" variant="h4" className={classes.title}>
                            Calendar: {file.calendarTitle}
                        </Typography>
                        {/*<Typography variant="subtitle1" color="textSecondary">*/}
                        {/*    description*/}
                        {/*</Typography>*/}
                    </CardContent>
                    <Card>
                        <Calendar editable={editable} onAvailChange={onAvailChange} file={file}/>
                    </Card>
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
                                <Fab aria-label={fab.label} className={fab.className} color={fab.color} onClick={handleChangeIndex}>
                                    {fab.icon}
                                </Fab>
                            </Zoom>
                        ))}
                    </div>
                </div>
            </Card>
        </div>
    )
};

ReactDOM.render(<App />, document.getElementById('root'))