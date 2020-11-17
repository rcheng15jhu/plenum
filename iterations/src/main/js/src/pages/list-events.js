import React, {useState, useEffect} from 'react'
import ReactDOM from 'react-dom'
import ViewableListItem from "../components/viewable-list-item";
import Header from "../components/header";
import {Typography} from "@material-ui/core";
import AddIcon from '@material-ui/icons/Add';
import Fab from "@material-ui/core/Fab";
import List from "@material-ui/core/List";
import {makeStyles} from "@material-ui/core/styles";
import UploadTemplateAlert from "../components/uploadTemplateAlert";
import Button from "@material-ui/core/Button";
import getCookie from "../services/get-cookie";

const useStyles = makeStyles(() => ({
    root: {
        '& > *': {
            margin: 30,
        },
    },
    center: {
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        width: '90%',
    },
}))

function fetchAPI(id) {
    fetch("/api/delevent?id=" + id, {
            method: 'POST',
            mode: 'cors'
        }
    ).then(data => {
        location.reload();
    })
}

const App = () =>  {

    if(getCookie('username') === ""){
        window.location.assign('/')
    }

    const classes = useStyles();
    const [events, setEvents] = useState([])

    const [idToDelete, setIdToDelete] = useState(-1)

    useEffect(() => {
        fetch('/api/events', {
                method: 'GET',
                mode: 'cors'
            }
        ).then(res => {
            return res.json()
        }).then(data => {
            console.log(data)
            setEvents([...data])
        })
    }, [])

    useEffect(() => {
        if(idToDelete > 0) {
            fetchAPI(idToDelete)
            setIdToDelete(-1)
        }
    }, [idToDelete])

    const handleAdd = () => {
        window.location.assign('/create-event')
    }

    const handleDelete = (id) => () => {
        setIdToDelete(id);
    }

    let eventNames = events.map(event => {return {id: event.id, content: event.title}})


    let navToViewPage = (id) => () => {
        window.location.assign('/view-event?id=' + id)
    }

    return (
        <div style={{'paddingBottom': '100px'}}>
            <Header />
            <div  className={classes.root}>
                <Typography variant="h4" className='headingTyp'>
                    Your Events
                </Typography>
                <Typography variant="h6" className='headingTyp'>
                    Private Events
                </Typography>
                <div className="divContents">
                    <List>
                        {eventNames.map(el => (
                            <ViewableListItem delete={handleDelete} key={el.id} id={el.id} content={el.content} clicked={navToViewPage} />
                        ))}
                    </List>

                </div>
                <div className={classes.center}>
                    <Fab color="primary" aria-label="add" onClick={handleAdd}>
                        <AddIcon />
                    </Fab>

                </div>
                <Typography variant="h6" className='headingTyp'>
                    Public Events
                </Typography>
                <Button variant="outlined" href='list-public-events' color='primary'>View all public events!</Button>
                {/*<UploadTemplateAlert msg={<a href='list-public-events'>Show all public events!</a>} severity={'success'} />*/}
            </div>
        </div>

    )
}

ReactDOM.render(<App />, document.getElementById('root'))