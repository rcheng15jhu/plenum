import React, {useState, useEffect} from 'react'
import ReactDOM from 'react-dom'
import ViewableListItem from "../components/viewable-list-item";
import Header from "../components/header";
import {Typography} from "@material-ui/core";
import AddIcon from '@material-ui/icons/Add';
import Fab from "@material-ui/core/Fab";
import List from "@material-ui/core/List";
import {makeStyles} from "@material-ui/core/styles";

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

const App = () =>  {
    const classes = useStyles();
    const [events, setEvents] = useState([])

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

    const handleAdd = () => {
        window.location.assign('/create-event')
    }


    let eventNames = events.map(event => {return {id: event.id, content: event.title}})

    //for testing:
    // let eventNames = [
    //     {
    //         id: 1,
    //         content: 'title1'
    //     },
    //     {
    //         id: 2,
    //         content: 'title2'
    //     },
    // ];

    let navToViewPage = (id) => () => {
        window.location.assign('/view-event?id=' + id)
    }

    return (
        <div style={{'paddingBottom': '100px'}}>
            <Header />
            <div  className={classes.root}>
                <Typography variant="h4" className='headingTyp'>
                    Event List
                </Typography>
                <div className="divContents">
                    <List>
                        {eventNames.map(el => (
                            <ViewableListItem route='/api/delevent' key={el.id} id={el.id} content={el.content} clicked={navToViewPage} />
                        ))}
                    </List>

                </div>
                <div className={classes.center}>
                    <Fab color="primary" aria-label="add" onClick={handleAdd}>
                        <AddIcon />
                    </Fab>

                </div>
            </div>
        </div>

    )
}

ReactDOM.render(<App />, document.getElementById('root'))